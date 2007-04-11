package org.eclipse.bpel.validator;

/**
 * Java JDK dependencies 
 */

import java.util.Date;
import java.util.Iterator;

import java.util.Map;


import org.eclipse.bpel.model.Process;


import org.eclipse.bpel.validator.factory.AdapterFactory;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.IProblem;
import org.eclipse.bpel.validator.model.Messages;
import org.eclipse.bpel.validator.model.Runner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.wsdl.WSDLElement;
import org.w3c.dom.Element;


/**
 * A builder which is invoked to build (in this case validate), the BPEL files
 * in the projects in which the builder is installed.
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 19, 2006
 *
 */

@SuppressWarnings("nls")
public class Builder extends IncrementalProjectBuilder {

	Date created = new Date();
	
	boolean bDebug = false;
	
	/** Empty problems list */
	IProblem[] EMPTY_PROBLEMS = {};
	
	/** The adapter manager for the platform */
	IAdapterManager fAdapterManager = Platform.getAdapterManager();		

	/**
	 * Create brand new shiny BPEL Builder.
	 */
	
	public Builder() {
		p("Created on " + created);		
	}

	

	@SuppressWarnings("unchecked")
	@Override
	
	protected IProject[] build (int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		
		long started = System.currentTimeMillis();
		
		if (args != null) {
			bDebug = toBoolean(args.get("debug"),false);
		}
		
		AdapterFactory.DEBUG = bDebug;
		if (bDebug) {
			p("Clear error messages from the cache ... (will re-load)");
			Messages.clear();			
		} 

		IProject myProject = this.getProject();
		IResourceDelta resourceDelta = this.getDelta(myProject);
								
		if (resourceDelta == null) {
						
			// Now find all the BPEL files in the project and validate them
			validate ( myProject, monitor );
			
		} else {
			
			IResourceDelta[] deltas = resourceDelta.getAffectedChildren(IResourceDelta.ADDED | IResourceDelta.CHANGED );
									
			p("Looking at deltas ...");
			for(IResourceDelta delta : deltas) {
				IResource resource = delta.getResource();
				validate ( resource, monitor );
			}
		}
				
		long ended = System.currentTimeMillis();
		p(" Validation Ended: " + (ended-started) + "ms");
		return new IProject[] { myProject };
	}

	
	/**
	 * Validate the resource using the monitor passed.
	 * 
	 * @param resource (File or Folder)
	 * @param monitor the monitor to use.
	 * @throws CoreException
	 */
	
	@SuppressWarnings("unchecked")
	public void validate (IResource resource, IProgressMonitor monitor) throws CoreException {
		
		switch (resource.getType()) {
		
		case IResource.FOLDER :
			IFolder folder = (IFolder) resource;
			
			IResource[] list = folder.members();
			for(IResource next : list) {
				validate (next,monitor);
			}
			
			break;
		case IResource.FILE :			
			IFile file = (IFile) resource;
			
			p("File Resource : " + file.getName() );
			// TODO: This should be a better check
			if ( file.getName().endsWith(".bpel")) {
				file.deleteMarkers(IBPELMarker.ID, false,  IResource.DEPTH_ZERO);
				makeMarkers ( validate (  file, monitor  ) );	
			} 
			break;
		}
	}
	
	
	/**
	 * @param file
	 * @param monitor
	 * @return return the list of problems found 
	 */
	
	@SuppressWarnings("unchecked")
	public IProblem[] validate (IFile file, IProgressMonitor monitor  ) {
		
		
		p("Validating BPEL Resource : " + file.getName() );
		
		// Step 1. Read the BPEL process using the Model API.
		
		BPELReader reader = new BPELReader();
		reader.read( file, new ResourceSetImpl() );
		Process process = reader.getProcess();
		
		if (process == null) {
			p ("Cannot read BPEL Process !!!");
			return EMPTY_PROBLEMS ;
		}
		
		p("Read in BPEL Model OK" );
		
		// Step 2. Preparation for the validator.
		linkModels ( process );
		p("Models Linked" );		
		
		// Process as INode 
		INode node = (INode) fAdapterManager.getAdapter( process.getElement(), INode.class );
		
		// Debug: Dump the dom from the reader, just to see what we have 
		// p( org.eclipse.bpel.model.util.BPELUtils.elementToString(process.getElement())); 
		
		// Step 4. Run the validator.
		
		IProblem[] problemList = new Runner (ModelQuery.getModelQuery(), node ).run();
		p("Validator Executed" );
		return problemList;	
	}

	
	
	public void makeMarkers ( IProblem [] problemList ) {

		if (problemList.length < 1) {
			return ;
		}

		// Step 5. Adapt problems to markers.		
		for(IProblem problem : problemList) {
			fAdapterManager.getAdapter(problem, IMarker.class);			
		}
		
		p( "Markers Created " );
		p( " ------ Done" );
		
		// done.
	}
	
	
	
	void linkModels ( EObject process ) {
		
		// 
		// Each extensible element points to the DOM element that 
		// comprises it. This is done in the BPEL reader as well as
		// the WSDL readers. Here we add a pointer to the 
		// emf objects from the DOM objects.
	
		Iterator<?> emfIterator = process.eAllContents();
		while (emfIterator.hasNext()) {
			Object obj = emfIterator.next();
			// This is because only WSDLElement has a reference to
			// a DOM element.
			if (obj instanceof WSDLElement) {
				WSDLElement wsdle = (WSDLElement) obj;
				Element el = wsdle.getElement();
				if (el != null) {
					el.setUserData("emf.model", obj, null); //$NON-NLS-1$
				}
			}
		}
	}
	
	
	@SuppressWarnings("boxing")	
	boolean toBoolean ( Object obj , boolean def) {
		if (obj instanceof String) {
			return new Boolean((String)obj);			
		}
		return def;
	}
	
	
		
	void p (String msg ) {
		if (bDebug) {
			System.out.printf( "[%1$s]>> %2$s\n", getClass().getName(), msg);
			System.out.flush();
		}
	}


}
