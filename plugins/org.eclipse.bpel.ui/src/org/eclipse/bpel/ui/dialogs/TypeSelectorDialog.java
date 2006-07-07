/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.dialogs;


import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.util.BPELUtils;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.AddImportCommand;
import org.eclipse.bpel.ui.details.providers.MessageTypeContentProvider;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.providers.VariableTypeTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.XSDSchemaContentProvider;
import org.eclipse.bpel.ui.details.providers.XSDTypeOrElementContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDConstants;


/**
 * Browse for data types available and choose that type.
 * 
 * In BPEL, types are just an aggragation of "visible" XML schemas. Visible schemas are
 * schemas imported in the BPEL source file via the import construct and schemas
 * present in WSDL files (which are also imported via the import construct).
 * 
 * When browsing for types, two modes are supported by this dialog.
 * 1) Browsing types which are already imported (constitute the type system of the process)
 * 2) Browsing for available types. This includes XML types visible to designer.
 * 
 * In addtion, one can arbitrarily add an import to the process if the XML schema
 * representing the types is somewhere else (URL, outside file, etc).
 * 
 * It should be noted, that currently, no attampt is made to resolve duplicate type entries
 * in the schemas. This is not necessarily a bug.
 * 
 *  @author Michal Chmielewski (michal.chmielewski@oracle.com)  
 * 
 */

public class TypeSelectorDialog extends ListAndViewDialog {
	
	/* Button id for add xsd import */
	protected final static int BID_ADD_XSD_IMPORT = IDialogConstants.CLIENT_ID + 1;
	
	/* Button id for complex types */
	protected final static int BID_COMPLEX_TYPES = IDialogConstants.CLIENT_ID + 2;
	
	/* Button id for simple types */
	protected final static int BID_SIMPLE_TYPES = IDialogConstants.CLIENT_ID + 3;
	
	/* Button id for element declarations */
	protected final static int BID_ELEMENT_DECLARATIONS = IDialogConstants.CLIENT_ID + 4;
	
	/* Button id for messages */
	protected final static int BID_MESSAGES = IDialogConstants.CLIENT_ID + 5;
	
	/* Button id for primitives */
	protected final static int BID_XSD_PRIMITIVES = IDialogConstants.CLIENT_ID + 6;

	/* Buttons for viewing types from various sources */
	protected final static int BID_TYPES_FROM_IMPORTS_ONLY = IDialogConstants.CLIENT_ID + 7;
	protected final static int BID_TYPES_FROM_PROJECT = IDialogConstants.CLIENT_ID + 8;
	protected final static int BID_TYPES_FROM_WORKSPACE = IDialogConstants.CLIENT_ID + 9;
	protected final static int BID_TYPES_FROM_XML_CATALOG = IDialogConstants.CLIENT_ID + 10;
	protected final static int BID_TYPES_FROM_REPOSITORY = IDialogConstants.CLIENT_ID + 11;
	
	/* Show duplicats */
	private static final int BID_DUPLICATES = IDialogConstants.CLIENT_ID + 20;

	
	/* Do not show duplicates */
	private boolean showDuplicates = false;
		
	/* Whether messages ought to be shown */	
	private boolean showMessages = true;
	
	
	protected EObject modelObject;
	
	protected Text filterText;
	
	protected XSDTypeOrElementContentProvider xsdTypeProvider = new XSDTypeOrElementContentProvider();
	protected MessageTypeContentProvider messageTypeProvider = new MessageTypeContentProvider();
	
	protected XSDSchemaContentProvider xsdSchemaContentProvider = null;
	
	protected Tree dataTypeTree;
	protected TreeViewer dataTypeTreeViewer;

	private Button fTypesFromImportsRadio;
	private Button fTypesFromProjectRadio;
	private Button fTypesFromWorkspaceRadio;
//	private Button fTypesFromCatalogRadio;
//	private Button fTypesFromRepositoryRadio;
	
	
	
	/* View types dialog preference setting */
	private static final String VIEW_TYPES_KEY = "ViewTypes"; //$NON-NLS-1$
	
	/* Filter types key */
	private static final String FILTER_TYPES_KEY = "FilterTypes"; //$NON-NLS-1$
	
	/* Show messages key in dialog settings */
	private static final String SHOW_MESSAGES_KEY = "ShowMessages"; //$NON-NLS-1$
	
	/* Show duplicates key in dialog settings */
	private static final String SHOW_DUPLICATES_KEY = "ShowDuplicates"; //$NON-NLS-1$

	
	
	/* by default, view types from imports only */
	private int VIEW_TYPES = BID_TYPES_FROM_IMPORTS_ONLY;
	
	/* Which types to filter ? */
	private int FILTER_TYPES = XSDTypeOrElementContentProvider.INLCUDE_ALL;

	
	private Object[] fWorkspaceSchemas;
	
	private Object[] fProjectSchemas;

	private Label filterLabel;
	

	
	/**
	 * The modelObject is the model element that indicates the scope in which the
	 * variable should be visible.
	 */
	
	public TypeSelectorDialog (Shell parent, EObject modelObject ) {
		super(parent, new ModelLabelProvider(modelObject));
		this.modelObject = modelObject;

		xsdSchemaContentProvider = new XSDSchemaContentProvider( modelObject.eResource().getResourceSet() );
		
		setTitle(Messages.TypeSelectorDialog_4);
		setMessage(Messages.TypeSelectorDialog_5); 
		setUpperListLabel(Messages.TypeSelectorDialog_6); 
		setLowerViewLabel(Messages.TypeSelectorDialog_7);
		
		// Restore some dialog settings ...
		IDialogSettings settings = getDialogSettings();
		
		try {
			VIEW_TYPES = settings.getInt(VIEW_TYPES_KEY);
		} catch (Exception ex) {
			VIEW_TYPES = BID_TYPES_FROM_IMPORTS_ONLY;
		}	
		
		
		try {
			FILTER_TYPES = settings.getInt(FILTER_TYPES_KEY);
		} catch (Exception ex) {
			FILTER_TYPES = XSDTypeOrElementContentProvider.INLCUDE_ALL;
		}		
		
		try {
			showDuplicates = settings.getBoolean(SHOW_DUPLICATES_KEY);
		} catch (Exception ex) {
			showDuplicates = false;
		}
		
		try {
			showMessages = settings.getBoolean(SHOW_MESSAGES_KEY);
		} catch (Exception ex) {
			showMessages = false;
		}
		
	}
	

	
	/** 
	 * Hook a load on the defaults into this dialog upon create.
	 * 
	 */
	
	protected Control createContents(Composite parent) {
		
		Control control = super.createContents(parent);
			
		// Re-Create the state of the provider from the dialog settings.
		xsdTypeProvider.setFilter( FILTER_TYPES );
		
		refresh();		

		return control;
	}



	
	
	protected void saveSettings () {
		
		IDialogSettings settings = getDialogSettings();
		
		settings.put ( FILTER_TYPES_KEY, FILTER_TYPES );
		settings.put ( VIEW_TYPES_KEY, VIEW_TYPES );
		settings.put ( SHOW_MESSAGES_KEY, showMessages);
		settings.put ( SHOW_DUPLICATES_KEY, showDuplicates );
	}

	
	
	
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, 
				BID_ADD_XSD_IMPORT,
				Messages.TypeSelectorDialog_8, 
				false);		
		super.createButtonsForButtonBar(parent);
	}

	
	protected void buttonPressed(int buttonId) {
		if (buttonId == BID_ADD_XSD_IMPORT) {
			handleAddXSDImport();
			return ;
		}
		super.buttonPressed(buttonId);
	}


	/**
	 * Check to make sure that the mappings for the XML namespace exist
	 */
	
	protected void okPressed() {
		computeResult();
		Object obj[] = getResult();
		
		if (obj != null || obj.length > 0) {
			if (ensureXSDTypeNamespaceMappings ( obj[0] ) == false) {
				return ;
			}
			
			checkAndOrAddImport ( obj[0] );
			
			// only if we have a mapping do we dismiss the dialog
			super.okPressed();
		}
		
	}



	/**
	 * Ensure that the prefix mapping exists for the given namespace
	 * in the BPEL source.
	 * 
	 * @param the schema component for which to create a BPEL prefix mappings.
	 */
	
	private boolean ensureXSDTypeNamespaceMappings (Object obj)  {
		
		String targetNamespace = null;
		
		if (obj instanceof XSDNamedComponent ) {
			XSDNamedComponent namedComponent = (XSDNamedComponent) obj; 
			targetNamespace = namedComponent.getTargetNamespace();
		}
		
		if (targetNamespace == null) {
			return true;
		}
		
		// Now check if the target namespace has a prefix mappings.
		String prefix = BPELUtils.getNamespacePrefix (modelObject, targetNamespace);
		if (prefix != null) {
			return true;
		}
		
		// We have to map the namespace to a prefix. 
		NamespaceMappingDialog dialog = new NamespaceMappingDialog(getShell(),modelObject);
		dialog.setNamespace( targetNamespace );
		
		if (dialog.open() == Window.CANCEL) {
			return false;
		}
		
		// define the prefix
		BPELUtils.setNamespacePrefix( ModelHelper.getProcess(modelObject), targetNamespace, dialog.getPrefix()); 		
		
		return true;
	}


	
	/**
	 * Handle the checkbutton and radio button callbacks.
	 * 
	 * @param id
	 * @param checked
	 */
	
	protected void buttonPressed (int id, boolean checked) {
		
		int bits = 0;
				
		switch (id) {
		case BID_SIMPLE_TYPES :
			bits = XSDTypeOrElementContentProvider.INCLUDE_SIMPLE_TYPES;
			break;
		
		case BID_COMPLEX_TYPES :
			bits = XSDTypeOrElementContentProvider.INCLUDE_COMPLEX_TYPES;
			break;

		case BID_ELEMENT_DECLARATIONS :
			bits = XSDTypeOrElementContentProvider.INCLUDE_ELEMENT_DECLARATIONS;
			break;

		case BID_XSD_PRIMITIVES :
			bits = XSDTypeOrElementContentProvider.INCLUDE_PRIMITIVES;
			break;
			
		case BID_MESSAGES :
			showMessages = checked;
			break;
		
		case BID_DUPLICATES :
			showDuplicates = checked;
			break;
		
			
		case BID_TYPES_FROM_WORKSPACE :
		case BID_TYPES_FROM_XML_CATALOG :
		case BID_TYPES_FROM_PROJECT :
		case BID_TYPES_FROM_REPOSITORY :	
		case BID_TYPES_FROM_IMPORTS_ONLY :			
			if (!checked) {
				return ;
			}			
			if (checked) {
				VIEW_TYPES = id;									
			}
			break;
					
		default : 
				break;
		}

		
		// adjust the filter
		if (bits != 0) {
			if (checked) {
				FILTER_TYPES |= bits;
			} else {
				FILTER_TYPES &= ~bits;
			}
			xsdTypeProvider.setFilter (FILTER_TYPES);
		}
		
		
		refresh();
	}
	
	
	/**
	 * Add an import using an explict import dialog selection. 
	 * 
	 * We safeguard against adding duplicate types to the BPEL model here as well.
	 * 
	 */
	
	private void handleAddXSDImport() {

		SchemaImportDialog dialog = new SchemaImportDialog(getShell(),modelObject);
		if (dialog.open() == Window.CANCEL) {
			return ;
		}
		
		Object obj[] = dialog.getResult();
		if (obj == null || obj.length < 1 || (obj[0] instanceof XSDSchema) == false) {
			return ;
		}
		 		
		checkAndOrAddImport ( (XSDSchema) obj[0] );		
		// Now refresh the view to imported types.
		showImportedTypes();		
	}

	
	protected void checkAndOrAddImport ( Object obj ) {
		if (obj instanceof XSDSchema) {
			checkAndOrAddImport ((XSDSchema) obj);
		} else if ( (obj instanceof XSDNamedComponent) == false ) {
			return ;			
		}
		
		XSDNamedComponent xsdObj = (XSDNamedComponent) obj;
		checkAndOrAddImport( xsdObj.getSchema() );
	}
	
	
	protected void checkAndOrAddImport (XSDSchema schema) {
		Import imp = createImportFrom ( schema );

		Process process = ModelHelper.getProcess(modelObject);
		if (ModelHelper.containsImport( modelObject, imp ) == false) {
			AddImportCommand cmd = new AddImportCommand(process,imp);		
			ModelHelper.getBPELEditor(process).getCommandStack().execute(cmd);			
		}
	}
	
	
	protected Import createImportFrom (XSDSchema schema) {
		
		Import imp = BPELFactory.eINSTANCE.createImport();

		// namespace
		String t = schema.getTargetNamespace();
		if (t != null) {
			imp.setNamespace( t );
		}
		// location
		Resource resource = modelObject.eResource();		
		URI schemaURI = URI.createURI(schema.getSchemaLocation());
				
		imp.setLocation( schemaURI.deresolve(resource.getURI()).toString() );
		
		// importType (the XSD kind)
		imp.setImportType( XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001);

		return imp;
	}

	
	protected Button createRadioButton (Composite parent, String label, int id, boolean checked) {
		
		Button button = new Button(parent,SWT.RADIO);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));		
		button.setSelection( checked );
		
		button.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected (SelectionEvent event) {
				Button button = (Button) event.widget;
				int id = ((Integer) button.getData()).intValue();
				
				buttonPressed(id, button.getSelection());
			}
		});
		
		return button;
	
	}
	

	protected Button createCheckButton (Composite parent, String label, int id, boolean checked) {
		
		Button button = new Button(parent,SWT.CHECK);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.setSelection( checked );
		
		button.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected (SelectionEvent event) {
				Button button = (Button) event.widget;
				int id = ((Integer) button.getData()).intValue();
				
				buttonPressed(id, button.getSelection());
			}
		});
		
		return button;
	
	}
	
	
	protected Text createFilterText(Composite parent) {
		filterText = super.createFilterText(parent);
		
		createIncludeFilterGroup ( parent );
		createBrowseFilterGroup( parent );
		
		return filterText;		
	}
	
	


	protected Label createMessageArea(Composite composite) {
		filterLabel = super.createMessageArea(composite);
		return filterLabel;
	}



	protected void createIncludeFilterGroup ( Composite parent ) {
		Group group = new Group(parent,SWT.SHADOW_ETCHED_IN);
		group.setText(Messages.TypeSelectorDialog_9);
		
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;		
		layout.numColumns = 3;		
		group.setLayout(layout);
		GridData data = new GridData();        
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        group.setLayoutData(data);

		fTypesFromImportsRadio = createRadioButton(group,Messages.TypeSelectorDialog_10, BID_TYPES_FROM_IMPORTS_ONLY,  
				 VIEW_TYPES ==  BID_TYPES_FROM_IMPORTS_ONLY);
		fTypesFromProjectRadio = createRadioButton(group,Messages.TypeSelectorDialog_11, BID_TYPES_FROM_PROJECT, 
				VIEW_TYPES == BID_TYPES_FROM_PROJECT );
		fTypesFromWorkspaceRadio = createRadioButton(group,Messages.TypeSelectorDialog_12, BID_TYPES_FROM_WORKSPACE, 
				VIEW_TYPES == BID_TYPES_FROM_WORKSPACE );
		
		
//		fTypesFromCatalogRadio = createRadioButton(group,"From XML Catalog", BID_TYPES_FROM_XML_CATALOG, 
//				VIEW_TYPES == BID_TYPES_FROM_XML_CATALOG );
//		// TODO: Fix this or remove at some point
//		fTypesFromCatalogRadio.setVisible(false);
//		
//		fTypesFromRepositoryRadio = createRadioButton(group,"From Repository", BID_TYPES_FROM_REPOSITORY, 
//				VIEW_TYPES == BID_TYPES_FROM_REPOSITORY );
//		// TODO: Fix this or remove at some point.
//		fTypesFromRepositoryRadio.setVisible(false);

	}
	
	
	protected void createBrowseFilterGroup ( Composite parent ) {
		Group group = new Group(parent,SWT.SHADOW_ETCHED_IN);
		group.setText(Messages.TypeSelectorDialog_13);
		
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;		
		layout.numColumns = 3;		
		group.setLayout(layout);
		GridData data = new GridData();        
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        group.setLayoutData(data);

		createCheckButton(group,Messages.TypeSelectorDialog_14, BID_XSD_PRIMITIVES, 
				(FILTER_TYPES & XSDTypeOrElementContentProvider.INCLUDE_PRIMITIVES) > 0  );
		createCheckButton(group,Messages.TypeSelectorDialog_15, BID_SIMPLE_TYPES, 
				(FILTER_TYPES &  XSDTypeOrElementContentProvider.INCLUDE_SIMPLE_TYPES) > 0   );
		createCheckButton(group,Messages.TypeSelectorDialog_16, BID_COMPLEX_TYPES, 
				(FILTER_TYPES &  XSDTypeOrElementContentProvider.INCLUDE_COMPLEX_TYPES ) > 0 );
		createCheckButton(group,Messages.TypeSelectorDialog_17, BID_ELEMENT_DECLARATIONS,
				(FILTER_TYPES &  XSDTypeOrElementContentProvider.INCLUDE_ELEMENT_DECLARATIONS ) > 0 );
		createCheckButton(group,Messages.TypeSelectorDialog_18, BID_MESSAGES,
				showMessages);
		createCheckButton(group,Messages.TypeSelectorDialog_19, BID_DUPLICATES,
				showDuplicates);	
	}
	
	
	protected Object createLowerView (Composite parent) {
		//	Tree viewer for variable structure ...
		dataTypeTree = new Tree(parent, SWT.BORDER );
		VariableTypeTreeContentProvider variableContentProvider = new VariableTypeTreeContentProvider(true, true);
		dataTypeTreeViewer = new TreeViewer(dataTypeTree);
		dataTypeTreeViewer.setContentProvider(variableContentProvider);
		dataTypeTreeViewer.setLabelProvider(new ModelTreeLabelProvider());
		dataTypeTreeViewer.setInput ( null );
		dataTypeTreeViewer.setAutoExpandLevel(3);
		// end tree viewer for variable structure
		GridData data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.minimumHeight = 200;
        dataTypeTree.setLayoutData(data);
        
		return dataTypeTree;
	}


	protected void updateLowerViewWidget (Object[] elements) {
				
		if (elements == null || elements.length < 1) {
			dataTypeTreeViewer.setInput(null);
			return;
		}
		
		dataTypeTreeViewer.setInput ( elements[0] );
		ScrollBar bar = dataTypeTree.getVerticalBar();
		if (bar != null) {
			bar.setSelection(0);
		}			
	}

	protected Object[] merge ( Object[] a1, Object a2[] ) {
		Object[] result = new Object[ a1.length + a2.length];
		System.arraycopy(a1, 0, result, 0, a1.length);
		System.arraycopy(a2, 0, result, a1.length, a2.length);
		return result;
	}

	protected void refresh () {						
		
		if (VIEW_TYPES == BID_TYPES_FROM_IMPORTS_ONLY) {
			refreshFromImports ();
		} else if (VIEW_TYPES == BID_TYPES_FROM_PROJECT) {
			refreshFromProject();
		} else if (VIEW_TYPES == BID_TYPES_FROM_WORKSPACE) {
			refreshFromWorkspace();
		} else if (VIEW_TYPES == BID_TYPES_FROM_XML_CATALOG) {
			refreshFromXMLCatalog ();
		} else if (VIEW_TYPES == BID_TYPES_FROM_REPOSITORY ) {
			refreshFromRepository ();
		} else {
			return ;
		}
		
		if (fFilteredList != null) {
			if (fFilteredList.isEmpty()) {
				handleEmptyList();
			} else {
				handleNonEmptyList();
			}
		}		
	}

	
	/**
	 * Refresh the available types from the BPEL imports list. This is the 
	 * list of types/elements visible in the process so far (the working set so to speak).
	 */
	
	protected void refreshFromImports () {
		
		Object elements [] = null;
		Object types[] = xsdTypeProvider.getElements( ModelHelper.getSchemas(modelObject,true) );
		Object msgs[] = null;
		
		if (showMessages) {
			msgs = messageTypeProvider.getElements( ModelHelper.getDefinitions(modelObject) );
			elements = merge ( types, msgs );
		} else {
			elements = types;
		}		
				
		if (fFilteredList != null) {
			fFilteredList.setAllowDuplicates(showDuplicates);
			fFilteredList.setElements(elements);
			fFilteredList.setEnabled(true);		
		}
				
	}

	
	/**
	 * Show the types which are available in the current workspace. 
	 * This inlcudes all the open projects ...
	 */
	
	protected void refreshFromWorkspace () {

		if (dataTypeTreeViewer != null) {
			dataTypeTreeViewer.setInput(null);
		}		

		if (fWorkspaceSchemas == null ) {
			fWorkspaceSchemas = xsdSchemaContentProvider.getElements( ResourcesPlugin.getWorkspace().getRoot() );
		}		
				 	
		if (fFilteredList != null) {
			
			fFilteredList.setEnabled(true);
			fFilteredList.setAllowDuplicates(showDuplicates);
			fFilteredList.setElements(xsdTypeProvider.getElements( fWorkspaceSchemas ));						
		}		
	}

	
	/**
	 * Show the types which are available in the current project only.
	 *  
	 */
	
	protected void refreshFromProject () {

		if (dataTypeTreeViewer != null) {
			dataTypeTreeViewer.setInput(null);
		}		
		
		if (fProjectSchemas == null ) {
			Resource resource = modelObject.eResource();
			IFile file = BPELUtil.getFileFromURI(resource.getURI());					
			fProjectSchemas = xsdSchemaContentProvider.getElements( file.getProject() );			
		}
		
		if (fFilteredList != null) {
			
			fFilteredList.setEnabled(true);
			fFilteredList.setAllowDuplicates(showDuplicates);
			fFilteredList.setElements( xsdTypeProvider.getElements( fProjectSchemas ) );						
		}		
	}

	protected void refreshFromXMLCatalog () {

		// TODO: At some point I had the idea to gather types from 
		// XML catalog. But not sure if this is such a good idea anymore
		
		Object[] elements = new Object[]{};
		modelObject.eContainer();
		
		if (fFilteredList != null) {
			
			fFilteredList.setAllowDuplicates(showDuplicates);
			fFilteredList.setElements(elements);
			fFilteredList.setEnabled(true);
			
		}
		
		if (dataTypeTreeViewer != null) {
			dataTypeTreeViewer.setInput(null);
		}		
	}
	
	
	protected void refreshFromRepository () {
		
		// TODO: Some repository of some kind ?
		
		Object[] elements = new Object[]{};
		modelObject.eContainer();
		
		if (fFilteredList != null) {
			
			fFilteredList.setAllowDuplicates(showDuplicates);
			fFilteredList.setElements(elements);
			fFilteredList.setEnabled(true);
			
		}
		
		if (dataTypeTreeViewer != null) {
			dataTypeTreeViewer.setInput(null);
		}		
	}


	

	protected void showImportedTypes () {
		
		fTypesFromImportsRadio.setSelection(true);
		fTypesFromProjectRadio.setSelection(false);
		fTypesFromWorkspaceRadio.setSelection(false);
		
//		fTypesFromRepositoryRadio.setSelection(false);
//		fTypesFromCatalogRadio.setSelection(false);
		
		fTypesFromImportsRadio.forceFocus();
		buttonPressed(BID_TYPES_FROM_IMPORTS_ONLY, true);
		
	}
	
	protected void handleEmptyList() {
		dataTypeTreeViewer.setInput ( null );		
		super.handleEmptyList();
	}
	
	
	protected void handleNonEmptyList () {			
	
		setEnabled( filterText, true);
		setEnabled( filterLabel, true);
		setEnabled(fFilteredList, true);				
	}



	/**
	 * @param
	 * @param b
	 */
	void setEnabled(Control control, boolean b) {
		if (control != null) {
			control.setEnabled(b);
		}		
	}
	
	
	
	
	
	
			
}
