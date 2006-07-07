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


import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.MessageTypeContentProvider;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.providers.VariableTypeTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.XSDTypeOrElementContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.filedialog.FileSelectionGroup;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.xsd.XSDSchema;



/**
 * Browse for complex/simple types available in the procoess and choose
 * that simple type.
 * 
 */

public class SchemaImportDialog extends SelectionStatusDialog {
	
	/** Browse local resource */
	protected final static int BID_BROWSE_RESOURCE = IDialogConstants.CLIENT_ID + 1;
	
	/* Button id for browsing URLs */
	protected final static int BID_BROWSE_URL = IDialogConstants.CLIENT_ID + 2;
	
	/* Button id for browse files */
	protected final static int BID_BROWSE_FILE = IDialogConstants.CLIENT_ID + 3;

	/* Browse button  */
	protected static final int BID_BROWSE = IDialogConstants.CLIENT_ID + 4;

	/* The import source setting, rememebered in the dialog settings */
	private static final String IMPORT_KIND = "ImportKind";  //$NON-NLS-1$

	private static final String EMPTY = ""; //$NON-NLS-1$
	
			
	protected EObject modelObject;
	
	protected Text filterText;
		
	static protected XSDTypeOrElementContentProvider xsdTypeProvider = new XSDTypeOrElementContentProvider();
	static protected MessageTypeContentProvider messageTypeProvider = new MessageTypeContentProvider();
	
	
	protected Tree dataTypeTree;
	protected TreeViewer dataTypeTreeViewer;

	private Text fLocation;

	private Composite fLocationComposite;
	private FileSelectionGroup fResourceComposite;
	
    
	private Button fBrowseButton;

	private Group fGroup;
    	
	private IDialogSettings fSettings ;
	
	private int KIND = BID_BROWSE_RESOURCE ; 	
		
	
	public SchemaImportDialog (Shell parent) {
		super(parent);
		setStatusLineAboveButtons(true);		
		int shellStyle = getShellStyle();		
        setShellStyle(shellStyle | SWT.MAX | SWT.RESIZE);
        
        fSettings = BPELUIPlugin.getPlugin().getDialogSettingsFor( this );

        try {
        	KIND = fSettings.getInt(IMPORT_KIND);
        } catch (java.lang.NumberFormatException nfe) {
        	KIND = BID_BROWSE_RESOURCE;
        }
        
        setDialogBoundsSettings ( fSettings, getDialogBoundsStrategy()  );
	}
	/**
	 * The modelObject is the model element that indicates the scope in which the
	 * variable should be visible.
	 */
	public SchemaImportDialog (Shell parent, EObject eObject) {
		this(parent);
		
		this.modelObject = eObject;		
		setTitle(Messages.SchemaImportDialog_2);					
	}
	
	
	/*
     * @see Dialog#createDialogArea(Composite)
     */
	
    public Control createDialogArea(Composite parent) {
    	    	    	
        Composite contents = (Composite) super.createDialogArea(parent);
        
        createImportLocation (contents);
        createImportStructure (contents);
            
        buttonPressed(KIND,true);
        return contents;
    }
	
		
	protected void buttonPressed (int buttonId) {
		switch (buttonId) {
		case BID_BROWSE : 
			FileDialog fileDialog = new FileDialog(getShell());
			fileDialog.setFilterNames( new String[] { "*.xsd" }); //$NON-NLS-1$
			String path = fileDialog.open();
			if (path == null) {
				return;
			}
			fLocation.setText( path );
			attemptSchemaLoad ( path );
			break;
		}
		super.buttonPressed(buttonId);
	}
	
	
	protected void buttonPressed (int id, boolean checked) {
		
		if (id == BID_BROWSE_FILE || id == BID_BROWSE_RESOURCE || id == BID_BROWSE_URL) {
			if (checked == false) {
				return ;
			}
			
			setVisibleControl (fResourceComposite, id == BID_BROWSE_RESOURCE );			
			setVisibleControl (fLocationComposite ,
					  id == BID_BROWSE_URL || id == BID_BROWSE_FILE );
			
			fBrowseButton.setEnabled( id == BID_BROWSE_FILE );
			fLocation.setText(EMPTY); 
			dataTypeTreeViewer.setInput(null);
			updateOK(false);
			fSettings.put(IMPORT_KIND, KIND);
			KIND = id;
			
			fGroup.getParent().layout(true);								
		}
		
	}
	
	
	protected void setVisibleControl (Control c, boolean b) {
		Object layoutData = c.getLayoutData();
		
		if (layoutData instanceof GridData) {
			GridData data = (GridData) layoutData;
			data.exclude = ! b;
		}		
		c.setVisible(b);
	}
	
	
	public void create() {		
		super.create();
		
		
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
	
	protected void createImportLocation (Composite parent) {
		
		fGroup = new Group(parent,SWT.SHADOW_ETCHED_IN);
		fGroup.setText(Messages.SchemaImportDialog_4);			
		GridLayout layout = new GridLayout(1,true);
		GridData data = new GridData();        
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        
        fGroup.setLayout( layout );
        fGroup.setLayoutData( data );
		
		
        Composite container = new Composite(fGroup, SWT.NONE);
        
        layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;		
		layout.numColumns = 3;		
		container.setLayout(layout);
		data = new GridData();        
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.CENTER;
        container.setLayoutData(data);
        
		createRadioButton(container,Messages.SchemaImportDialog_5, BID_BROWSE_RESOURCE, 
				KIND == BID_BROWSE_RESOURCE );
		createRadioButton(container,Messages.SchemaImportDialog_6, BID_BROWSE_FILE, 
				KIND == BID_BROWSE_FILE  );
		createRadioButton(container,Messages.SchemaImportDialog_7, BID_BROWSE_URL, 
				KIND == BID_BROWSE_URL  );
				
		
		// Create  location variant 
		fLocationComposite  = new Composite(fGroup, SWT.NONE);
		
        layout = new GridLayout();			
		layout.numColumns = 3;		
		fLocationComposite.setLayout(layout);
		data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        fLocationComposite.setLayoutData(data);
        
        Label location = new Label(fLocationComposite,SWT.NONE);
        location.setText(Messages.SchemaImportDialog_8);
        
        fLocation = new Text(fLocationComposite,SWT.BORDER);
        fLocation.setText(EMPTY);
		data = new GridData();        
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;        
        fLocation.setLayoutData(data);
        fLocation.addListener(SWT.FocusOut, new Listener() {

			public void handleEvent(Event event) {
				attemptSchemaLoad( fLocation.getText() );				
			}        	
        });
                		
        fBrowseButton = createButton(fLocationComposite, BID_BROWSE, Messages.SchemaImportDialog_9, false);
        
        // End of location variant
        
        // Start Resource Variant
        fResourceComposite  = new FileSelectionGroup(fGroup,
        		new Listener() {
					public void handleEvent(Event event) {
						IPath path = fResourceComposite.getResourceFullPath();
						if (path == null) {
							updateStatus ( Status.OK_STATUS );
							updateOK(false);
							return;
						}						
						// only attempt to load a resource which is not a container
						attemptSchemaLoad ( path.toString() );
					}        	
        		},        		
        		Messages.SchemaImportDialog_10,
        		IBPELUIConstants.EXTENSION_DOT_XSD);        
        
        TreeViewer viewer = fResourceComposite.getTreeViewer();        
        viewer.setAutoExpandLevel(2);
        
        // End resource variant               
	}


	protected Object createImportStructure (Composite parent) {
		
		 Label location = new Label(parent,SWT.NONE);
	     location.setText(Messages.SchemaImportDialog_11);
	        
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


	protected XSDSchema getSchemaFromLocation ( URI uri ) {
		updateStatus ( Status.OK_STATUS );		
		try {
			ResourceSet resourceSet = BPELUtil.createResourceSetImpl();	
			Resource xsdResource = resourceSet.getResource(uri, true);
			return  (XSDSchema) xsdResource.getContents().get(0);				
		} catch (Exception ex) {
			updateStatus ( new Status(Status.ERROR, BPELUIPlugin.getPlugin().getID(),0,Messages.SchemaImportDialog_12,ex) );			
		}
		return null;
	}
	
		
	private void attemptSchemaLoad ( String path ) {

		URI uri = convertToURI ( path );
		if (uri == null) {
			return ;
		}
		
		XSDSchema schema = getSchemaFromLocation( uri );
		dataTypeTreeViewer.setInput(schema);
		
		if (schema != null) {
			dataTypeTree.getVerticalBar().setSelection(0);
		}
	}

	
	private URI convertToURI (String path ) {
		
		try {
			switch (KIND) {
			case BID_BROWSE_FILE : 
				return URI.createFileURI( path );				
			
			case BID_BROWSE_RESOURCE :
				return URI.createPlatformResourceURI(path);				
			
			case BID_BROWSE_URL :
				return URI.createURI(path);
				
			default :
				return null;
			}
			
		} catch (Exception ex) {
			updateStatus ( new Status(Status.ERROR,BPELUIPlugin.getPlugin().getID(),0,Messages.SchemaImportDialog_13,ex) );			
			return null;
		}
	}

	
	public void updateOK ( boolean state ) {
		Button okButton = getOkButton();
        if (okButton != null && !okButton.isDisposed()) {
			okButton.setEnabled(state);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.SelectionStatusDialog#computeResult()
	 */
	
	protected void computeResult() {
		Object schema = dataTypeTreeViewer.getInput();
		if (schema == null) {
			return ;
		}
		setSelectionResult(new Object[]{ schema });		
	}
	
	
}
