/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation
 *******************************************************************************/

package org.eclipse.bpel.ui.wizards;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.bpel.common.wsdl.importhelpers.WsdlImportHelper;
import org.eclipse.bpel.common.wsdl.parsers.WsdlParser;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Pick;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.model.resource.BPELResourceFactoryImpl;
import org.eclipse.bpel.model.resource.BPELWriter;
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.Templates.TemplateResource;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.wizards.NewBpelFileFirstPage.BpelCreationMode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.WSDLFactory;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.xml.sax.SAXException;

/**
 * The creation wizard for a new BPEL process.
 * @author Michal Chmielewski - Oracle
 * @author Edwin Khodabakchian - Oracle
 * @author Vincent Zurczak - EBM WebSourcing
 */
public class NewBpelFileWizard extends Wizard implements INewWizard {

	static final String DIALOG_SETTINGS_PROCESS_NAME = "process-name";
	static final String DIALOG_SETTINGS_PROCESS_TPL_KEY = "process-template-key";

	private IStructuredSelection selection;
	private IWorkbench fWorkbench;

	private NewBpelFileFirstPage firstPage;
	private NewBpelFileLocationPage locationPage;
	private NewBpelFileTemplatePage wsdlPage;
	private NewBpelFilePortTypePage portTypePage;


	/**
	 * Constructor.
	 */
	public NewBpelFileWizard() {
		this.setWindowTitle( Messages.NewFileWizard_1 );

		setDialogSettings( BPELUIPlugin.INSTANCE.getDialogSettingsFor( this ));
		setHelpAvailable( false );
		setNeedsProgressMonitor( true );
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard
	 * #init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.fWorkbench = workbench;
		this.selection = currentSelection;
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard
	 * #addPages()
	 */
	@Override
	public void addPages() {

		this.firstPage = new NewBpelFileFirstPage();
		this.locationPage = new NewBpelFileLocationPage( this.selection );
		this.wsdlPage = new NewBpelFileTemplatePage();
		this.portTypePage = new NewBpelFilePortTypePage();

		// Add all the pages
		// The pages will then decide which one follows them
		addPage( this.firstPage );
		addPage( this.portTypePage );
		addPage( this.wsdlPage );
		addPage( this.locationPage );
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard
	 * #canFinish()
	 */
	@Override
	public boolean canFinish() {

		boolean complete = this.firstPage.isPageComplete() && this.locationPage.isPageComplete();
		if( complete ) {
			if( this.firstPage.getCreationMode() == BpelCreationMode.CREATE_NEW_BPEL )
				complete = this.wsdlPage.isPageComplete();
			else
				complete = this.portTypePage.isPageComplete();
		}

		return complete;
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard
	 * #performFinish()
	 */
	@Override
	public boolean performFinish() {

		// Prepare the operation
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run( IProgressMonitor monitor )
			throws InvocationTargetException, InterruptedException {

				try {
					monitor.beginTask( "Creating the process...", IProgressMonitor.UNKNOWN );
					if( NewBpelFileWizard.this.firstPage.getCreationMode() == BpelCreationMode.GENERATE_BPEL_FROM_WSDL )
						createResourcesFromWsdl( monitor );
					else
						createResourcesFromTemplate( monitor );

				} catch( IOException e ) {
					throw new InvocationTargetException( e );

				} catch( CoreException e ) {
					throw new InvocationTargetException( e );

				} finally {
					monitor.done();
				}
			}
		};


		// Execute it
		final IFile bpelFile = this.locationPage.getProcessFile();
		boolean success = true;
		try {
			getContainer().run( false, true, runnable );

		} catch( InvocationTargetException e ) {
			success = true;
			BPELUIPlugin.log( e );

		} catch( InterruptedException e ) {
			success = true;
			BPELUIPlugin.log( e );

		} finally {
			// Refresh the workspace
			try {
				bpelFile.getProject().refreshLocal( IResource.DEPTH_INFINITE, null );
			} catch( CoreException e ) {
				BPELUIPlugin.log( e );
			}
		}


		// Open the file?
		if( success ) {
			final IWorkbenchPage page = this.fWorkbench.getActiveWorkbenchWindow().getActivePage();
			getShell().getDisplay().asyncExec( new Runnable() {
				@Override
				public void run() {
					try {
						IDE.openEditor( page, bpelFile );

					} catch( PartInitException e ) {
						BPELUIPlugin.log( e );
					}
				}
			});

			BasicNewResourceWizard.selectAndReveal( bpelFile, page.getWorkbenchWindow());
		}

		return success;
	}


	/**
	 * Creates a BPEL process and the associated resources from the selected template.
	 * @param monitor a progress monitor
	 * @throws CoreException if a resource fails to be created
	 */
	private void createResourcesFromTemplate( IProgressMonitor monitor ) throws CoreException {

		Map<String,Object> tplProperties = this.firstPage.getProcessTemplateProperties();
		tplProperties.putAll( this.wsdlPage.getProcessTemplateProperties());

		List<TemplateResource> tplResources = this.wsdlPage.getSelectedTemplate().getResources();
		monitor.subTask( Messages.BPELCreateOperation_0 );

		for( TemplateResource tplResource : tplResources ) {
        	String result = tplResource.process( tplProperties );
        	IPath path = new Path( tplResource.getName( tplProperties ));
        	IFile targetFile = this.locationPage.getResourceContainer().getFile( path );
        	if( targetFile.exists())
        		targetFile.setContents( new ByteArrayInputStream( result.getBytes()), true, true, monitor );
        	else
        		targetFile.create( new ByteArrayInputStream( result.getBytes()), true, monitor );

        	monitor.worked( 1 );
        }
	}


	/**
	 * Creates a BPEL process from a WSDL definition.
	 * @param monitor a progress monitor
	 * @throws CoreException if a resource fails to be created
	 * @throws IOException
	 * @throws CoreException
	 */
	private void createResourcesFromWsdl( IProgressMonitor monitor ) throws IOException, CoreException {

		// Import the original WSDL?
		monitor.subTask( "Processing the original WSDL definition..." );
		String newWsdlUrl = this.portTypePage.getWsdlUrl();
		if( this.portTypePage.isImportWsdl()) {
			File targetDirectory = this.locationPage.getResourceContainer().getLocation().toFile();
			try {
				Map<String,File> uriToImportedFile = new WsdlImportHelper().importWsdlOrXsdAndDependencies( targetDirectory, newWsdlUrl );
				File importedWsdlFile = uriToImportedFile.get( newWsdlUrl );
				if( importedWsdlFile == null )
					throw new IOException( "The WSDL file could not be found after import." );

				// The URL to put in the imports is the relative location of the WSDL
				// with respect to the process (and they are both in the same directory)
				newWsdlUrl = importedWsdlFile.getName();

			} catch( IllegalArgumentException e ) {
				throw new IOException( "The WSDL could not be imported in the project.", e );

			} catch( URISyntaxException e ) {
				throw new IOException( "The WSDL could not be imported in the project.", e );

			} catch( SAXException e ) {
				throw new IOException( "The WSDL could not be imported in the project.", e );

			} catch( ParserConfigurationException e ) {
				throw new IOException( "The WSDL could not be imported in the project.", e );
			}
		}

		monitor.worked( 2 );


		// Prepare the save operations
		Map<Object,Object> saveOptions = new HashMap<Object,Object> ();
		saveOptions.put( BPELWriter.SKIP_AUTO_IMPORT, Boolean.TRUE );

		ResourceSet resourceSet = WsdlParser.createBasicResourceSetForWsdl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put( "bpel", new BPELResourceFactoryImpl());
		resourceSet.getPackageRegistry().put( PartnerlinktypePackage.eNS_URI, PartnerlinktypePackage.eINSTANCE );
		resourceSet.getPackageRegistry().put( BPELPackage.eNS_URI, BPELPackage.eINSTANCE );


		// Create the WSDL for the artifacts
		monitor.subTask( "Generating the WSDL for the artifacts..." );
		Definition artifactsDefinition = generateWsdlArtifacts( monitor, newWsdlUrl );
		IFile wsdlFile = this.locationPage.getResourceContainer().getFile( new Path( computeWsdlArtifactsName()));
		URI emfUri = URI.createPlatformResourceURI( wsdlFile.getFullPath().toString(), true );

		Resource resource = resourceSet.createResource( emfUri );
		resource.getContents().add( artifactsDefinition );
		resource.save( saveOptions );
		monitor.worked( 2 );


		// Create the process
		monitor.subTask( "Generating the BPEL file..." );
		Process bpelProcess = generateBpelProcess( monitor, artifactsDefinition, newWsdlUrl );
		emfUri = URI.createPlatformResourceURI( this.locationPage.getProcessFile().getFullPath().toString(), true );

		resource = resourceSet.createResource( emfUri );
		resource.getContents().add( bpelProcess );
		resource.save( saveOptions );
		monitor.worked( 2 );


		// Hack: if the process is abstract, replace the name space,
		// since it is not supported by the meta-model
		if( this.firstPage.isAbstractProcess()) {
			java.net.URI fileUri =  this.locationPage.getProcessFile().getLocation().toFile().toURI();
			String newContent = WsdlImportHelper.readResourceContent( fileUri );
			newContent = newContent.replace( BPELConstants.NAMESPACE, BPELConstants.NAMESPACE_ABSTRACT_2007 );

			InputStream is = new ByteArrayInputStream( newContent.getBytes());
			this.locationPage.getProcessFile().setContents( is, true, false, monitor );
			monitor.worked( 2 );
		}
	}


	/**
	 * Generates a WSDL for the artifacts.
	 * @param monitor a progress monitor
	 * @param newWsdlUrl the URL of the business WSDL (as to put in the import declaration)
	 * @return the created definition (to serialize)
	 */
	@SuppressWarnings( "unchecked" )
	private Definition generateWsdlArtifacts( IProgressMonitor monitor, String newWsdlUrl ) {

		PortType portType = this.portTypePage.getPortType();
		Definition businessDefinition = (Definition) portType.eContainer();
		Definition artifactsDefinition = WSDLFactory.eINSTANCE.createDefinition();
		artifactsDefinition.setTargetNamespace( businessDefinition.getTargetNamespace() + "Artifacts" );

		// Hack for the role: we need to define manually the name space prefix for the TNS of the business WSDL
		artifactsDefinition.getNamespaces().put( "tns", businessDefinition.getTargetNamespace());

		// WSDL import
		Import wsdlImport = WSDLFactory.eINSTANCE.createImport();
		wsdlImport.setLocationURI( newWsdlUrl );
		wsdlImport.setNamespaceURI( businessDefinition.getTargetNamespace());
		artifactsDefinition.addImport( wsdlImport );

		// Partner Link Type
		PartnerLinkType plType = PartnerlinktypeFactory.eINSTANCE.createPartnerLinkType();
		plType.setName( portType.getQName().getLocalPart() + "PLT" );

		Role plRole = PartnerlinktypeFactory.eINSTANCE.createRole();
		plRole.setName( portType.getQName().getLocalPart() + "Role" );
		plRole.setPortType( portType );

		plType.getRole().add( plRole );
		artifactsDefinition.getEExtensibilityElements().add( plType );
		plType.setEnclosingDefinition( artifactsDefinition );

		return artifactsDefinition;
	}


	/**
	 * Generates a BPEL process.
	 * @param monitor a progress monitor
	 * @param artifactsDefintion the artifacts definition
	 * @param newWsdlUrl the URL of the business WSDL (as to put in the import declaration)
	 * @return the created process (to serialize)
	 */
	private Process generateBpelProcess( IProgressMonitor monitor, Definition artifactsDefinition, String newWsdlUrl ) {

		PortType portType = this.portTypePage.getPortType();
		Definition businessDefinition = (Definition) portType.eContainer();
		String bpelName = this.locationPage.getFileName();

		Process bpelProcess = BPELFactory.eINSTANCE.createProcess();
		bpelProcess.setName( bpelName );
		bpelProcess.setTargetNamespace( businessDefinition.getTargetNamespace());
		bpelProcess.setPartnerLinks( BPELFactory.eINSTANCE.createPartnerLinks());
		bpelProcess.setVariables( BPELFactory.eINSTANCE.createVariables());


		// Import the business definition
		org.eclipse.bpel.model.Import bpelImport = BPELFactory.eINSTANCE.createImport();
		bpelImport.setLocation( newWsdlUrl );
		bpelImport.setNamespace( businessDefinition.getTargetNamespace());
		bpelImport.setImportType( WSDLConstants.WSDL_NAMESPACE_URI );
		bpelProcess.getImports().add( bpelImport );


		// Import the artifacts definition
		bpelImport = BPELFactory.eINSTANCE.createImport();
		bpelImport.setLocation( computeWsdlArtifactsName());
		bpelImport.setNamespace( businessDefinition.getTargetNamespace() + "Artifacts" );
		bpelImport.setImportType( WSDLConstants.WSDL_NAMESPACE_URI );
		bpelProcess.getImports().add( bpelImport );


		// Create the main partner link
		PartnerLink pl = BPELFactory.eINSTANCE.createPartnerLink();
		for( Object elt : artifactsDefinition.getEExtensibilityElements()) {
			if( ! ( elt instanceof PartnerLinkType ))
				continue;

			pl.setPartnerLinkType((PartnerLinkType) elt);
			pl.setName( "bpelProcessPartner" );
			pl.setMyRole(((PartnerLinkType) elt).getRole().get( 0 ));
			bpelProcess.getPartnerLinks().getChildren().add( pl );
			break;
		}


		// Prepare the flow itself
		Sequence mainSequence = BPELFactory.eINSTANCE.createSequence();
		mainSequence.setName( "MainSequence" );
		bpelProcess.setActivity( mainSequence );

		Pick mainPick = BPELFactory.eINSTANCE.createPick();
		mainPick.setName( "SwitchInvokedOperation" );
		mainPick.setCreateInstance( true );
		mainSequence.getActivities().add( mainPick );


		// Create the variables: they are deduced from the port type to "implement"
		Collection<Definition> definitions = WsdlParser.findAllWsdlDefinitions( businessDefinition );
		for( Object o : portType.getOperations())
			addOperationDerivedElements((Operation) o, bpelProcess, mainPick, pl, definitions );

		return bpelProcess;
	}


	/**
	 * Creates the required variables and activities to handle an invocation to this operation.
	 * @param operation the operation
	 * @param bpelProcess the BPEL process
	 * @param mainPick the main pick
	 * @param partnerLink the partner link associated with the various activities
	 * @param definitions all the definitions related to the business interface (in case information should be searched in them)
	 */
	private void addOperationDerivedElements( Operation operation, Process bpelProcess, Pick mainPick, PartnerLink partnerLink, Collection<Definition> definitions ) {

		String opName = BPELUtil.lowerCaseFirstLetter( operation.getName());

		// Input: create the variable...
		Variable var = BPELFactory.eINSTANCE.createVariable();
		var.setName( opName + "Request" );
		findAndSetVariableXmlType( var, operation.getEInput().getEMessage().getQName(), definitions );
		bpelProcess.getVariables().getChildren().add( var );

		// ... and add an OnMessage activity
		OnMessage onMessage = BPELFactory.eINSTANCE.createOnMessage();
		onMessage.setPartnerLink( partnerLink );
		onMessage.setVariable( var );
		onMessage.setOperation( operation );
		mainPick.getMessages().add( onMessage );


		// Output: if it exists...
		if( operation.getOutput() != null ) {

			// Create the variable...
			var = BPELFactory.eINSTANCE.createVariable();
			var.setName( opName + "Response" );
			findAndSetVariableXmlType( var, operation.getEOutput().getEMessage().getQName(), definitions );
			bpelProcess.getVariables().getChildren().add( var );

			// And add a Reply activity
			Reply reply = BPELFactory.eINSTANCE.createReply();
			reply.setName( "ReplyTo" + BPELUtil.upperCaseFirstLetter( opName ));
			reply.setVariable( var );
			reply.setOperation( operation );
			reply.setPartnerLink( partnerLink );
			onMessage.setActivity( reply );

		} else {
			onMessage.setActivity( BPELFactory.eINSTANCE.createEmpty());
		}


		// Faults
		if( operation.getFaults() != null ) {
			for( Object oo : operation.getFaults().values()) {
				Fault fault = (Fault) oo;
				var = BPELFactory.eINSTANCE.createVariable();
				var.setName( opName + fault.getName());
				findAndSetVariableXmlType( var, fault.getEMessage().getQName(), definitions );
				bpelProcess.getVariables().getChildren().add( var );
			}
		}
	}


	/**
	 * Finds and sets the XML type for a variable associated with a given WSDL message.
	 * @param variable the variable whose XML type must be set
	 * @param messageName the name of the WSDL message
	 * @param definitions the list of definitions the message part
	 */
	private void findAndSetVariableXmlType( Variable variable, QName messageName, Collection<Definition> definitions ) {

		// Find and set the variable's type
		boolean found = false;
		boolean processed = false;
		for( Definition def : definitions ) {
			for( Object o : def.getEMessages()) {
				Message msg = (Message) o;
				if( ! ( messageName.equals( msg.getQName())))
					continue;

				found = true;
				if( msg.getEParts().size() == 1 ) {
					processed = true;

					Part part = (Part) msg.getEParts().get( 0 );
					if( part.getTypeDefinition() != null )
						variable.setType( part.getTypeDefinition());
					else if( part.getElementDeclaration() != null )
						variable.setXSDElement( part.getElementDeclaration());
				}
			}
		}


		// Log possible errors
		if( ! found )
			BPELUIPlugin.log( new Exception( "The message " + messageName + " could not be found." ), IStatus.ERROR );
		else if( ! processed )
			BPELUIPlugin.log( new Exception( "The message " + messageName + " contains more than 1 part. This case is not supported." ), IStatus.ERROR );
		else if( variable.getXSDElement() == null && variable.getType() == null )
			BPELUIPlugin.log( new Exception( "The XML type could not be set for the variable " + variable.getName() + ". Please, report a bug." ), IStatus.ERROR );
	}


	/**
	 * @return the name of the WSDL file for the artifacts
	 */
	private String computeWsdlArtifactsName() {
		IPath path = new Path( this.locationPage.getFileName());
		return path.removeFileExtension().toString() + "Artifacts.wsdl";
	}
}
