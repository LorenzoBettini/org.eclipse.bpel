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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkTypeTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.VariableTypeTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.WSILContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.filedialog.FileSelectionGroup;
import org.eclipse.bpel.wsil.model.inspection.util.InspectionResourceFactoryImpl;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.wst.wsdl.Definition;


import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.bpel.wsil.model.inspection.Description;
import org.eclipse.bpel.wsil.model.inspection.Inspection;
import org.eclipse.bpel.wsil.model.inspection.Link;
import org.eclipse.bpel.wsil.model.inspection.Name;
import org.eclipse.bpel.wsil.model.inspection.Service;
import org.eclipse.bpel.wsil.model.inspection.TypeOfAbstract;
import org.eclipse.ui.part.DrillDownComposite;
import org.eclipse.jface.viewers.ViewerComparator;


/**
 * Browse for complex/simple types available in the process and choose
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
	
	/* button id for browsing WSIL */
	protected static final int BID_BROWSE_WSIL = IDialogConstants.CLIENT_ID + 5;

	/* The import source setting, remembered in the dialog settings */
	private static final String IMPORT_KIND = "ImportKind";  //$NON-NLS-1$

	private static final String EMPTY = ""; //$NON-NLS-1$
	
	
	private String[] FILTER_EXTENSIONS = { IBPELUIConstants.EXTENSION_STAR_DOT_XSD,
											IBPELUIConstants.EXTENSION_STAR_DOT_WSDL,
											IBPELUIConstants.EXTENSION_STAR_DOT_ANY};
	
	private String[] FILTER_NAMES = { 	IBPELUIConstants.EXTENSION_XSD_NAME,
										IBPELUIConstants.EXTENSION_WSDL_NAME,
										IBPELUIConstants.EXTENSION_ANY_NAME };		                                                    
	
	
	protected EObject modelObject;

	protected Tree fTree;
	protected TreeViewer fTreeViewer;

	Text fLocation;

	private Composite fLocationComposite;
	FileSelectionGroup fResourceComposite;
	
	// Import from WSIL constructs
	private Composite fWSILComposite;
	protected TreeViewer fWSILTreeViewer;
	protected Tree fWSILTree;
	protected Text filterText;
	private String theFilter = ""; //$NON-NLS-1$
	
    
	private Button fBrowseButton;

	private Group fGroup;
    	
	private IDialogSettings fSettings ;
	
	private int KIND = BID_BROWSE_RESOURCE ;

	private String fStructureTitle;

	private ITreeContentProvider fTreeContentProvider;

	protected Object fInput;

	private IRunnableWithProgress fRunnableWithProgress;
	
	private boolean WSDL = true;
	/**
	 * Create a brand new shiny Schema Import Dialog.
	 * 
	 * @param parent
	 */
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
        
        configureAsSchemaImport();
	}
	
	/**
	 * Create a brand new shiny Schema Import Dialog
	 * 
	 * @param parent shell to use
	 * @param eObject the model object to use as reference
	 */
	public SchemaImportDialog (Shell parent, EObject eObject) {
		this(parent);
		
		this.modelObject = eObject;		
		setTitle(Messages.SchemaImportDialog_2);					
	}
	
	
	/**
	 * 
     * @see Dialog#createDialogArea(Composite)
     * 
     * @param parent the parent composite to use
     * @return the composite it created to be used in the dialog area.
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
			fileDialog.setFilterExtensions( FILTER_EXTENSIONS );
			fileDialog.setFilterNames( FILTER_NAMES );
			String path = fileDialog.open();
			if (path == null) {
				return;
			}
			fLocation.setText( path );
			attemptLoad ( path );
			break;
		}
		super.buttonPressed(buttonId);
	}
	
	
	protected void buttonPressed (int id, boolean checked) {

		if (id == BID_BROWSE_FILE || id == BID_BROWSE_RESOURCE || id == BID_BROWSE_URL || id == BID_BROWSE_WSIL) {
			if (checked == false) {
				return ;
			}

			setVisibleControl (fResourceComposite, id == BID_BROWSE_RESOURCE );			
			setVisibleControl (fLocationComposite ,
					  id == BID_BROWSE_URL || id == BID_BROWSE_FILE);
			
			setVisibleControl(fWSILComposite, id == BID_BROWSE_WSIL);
			
			fBrowseButton.setEnabled( (id == BID_BROWSE_FILE) || (id == BID_BROWSE_WSIL) );
			fLocation.setText(EMPTY); 
			fTreeViewer.setInput(null);
			updateOK(false);			
			KIND = id;
			WSDL = !(KIND == BID_BROWSE_WSIL);
			fSettings.put(IMPORT_KIND, KIND);
			
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
	
	
	/**
	 * Create the dialog.
	 * 
	 */
	
	public void create() {		
		super.create();
		buttonPressed(KIND, true);		
	}


	protected Button createRadioButton (Composite parent, String label, int id, boolean checked) {
		
		Button button = new Button(parent,SWT.RADIO);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));		
		button.setSelection( checked );
		
		button.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected (SelectionEvent event) {
				Button b = (Button) event.widget;
				int bid = ((Integer) b.getData()).intValue();
				
				buttonPressed(bid, b.getSelection());
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
		layout.numColumns = 4;		
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
		
		// Add WSIL option
		createRadioButton(container, Messages.SchemaImportDialog_15, BID_BROWSE_WSIL, KIND == BID_BROWSE_WSIL);
				
		
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
				String loc = fLocation.getText();
				if (loc.length() > 0) {
					attemptLoad( loc );
				}
			}        	
        });
        fLocation.addKeyListener( new KeyListener () {

			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.CR) {
					attemptLoad(fLocation.getText());
				}						
			}

			public void keyReleased(KeyEvent e) {
				return ;				
			}
        	
        });
        
                		
        fBrowseButton = createButton(fLocationComposite, BID_BROWSE, Messages.SchemaImportDialog_9, false);
        
        // End of location variant
        
        // Start Resource Variant
        fResourceComposite  = new FileSelectionGroup(fGroup,
        		new Listener() {
					public void handleEvent(Event event) {
						IResource resource = fResourceComposite.getSelectedResource();
						if (resource.getType() == IResource.FILE) {
							// only attempt to load a resource which is not a container
							attemptLoad ( (IFile) resource );							
						} else {						
							updateStatus ( Status.OK_STATUS );
							updateOK(false);
							return ;
						}						
					}        	
        		},
        		
        		Messages.SchemaImportDialog_10,
        		IBPELUIConstants.EXTENSION_DOT_XSD + "," + IBPELUIConstants.EXTENSION_DOT_WSDL );         //$NON-NLS-1$
        
        
        TreeViewer viewer = fResourceComposite.getTreeViewer();        
        viewer.setAutoExpandLevel(2);
        
        // End resource variant
        
        // create WSIL UI widgets
        createWSILStructure(fGroup);

	}

	protected Object createWSILStructure(Composite parent) {
		
        fWSILComposite = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        fWSILComposite.setLayout(layout);
        
		GridData data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.minimumHeight = 300;
        fWSILComposite.setLayoutData(data);
        
		Label location = new Label(fWSILComposite, SWT.NONE);
	    location.setText( Messages.SchemaImportDialog_16 );
	    
	    data = new GridData();
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalAlignment = SWT.LEFT;
	    location.setLayoutData(data);
	    
	    filterText = new Text(fWSILComposite, SWT.BORDER);
	    data = new GridData(GridData.FILL_HORIZONTAL);
	    filterText.setLayoutData(data);
	    
    	filterText.addKeyListener(new KeyListener() {
    		public void keyPressed(KeyEvent e) {
    		}
    		public void keyReleased(KeyEvent e) {
       			theFilter = filterText.getText();
       			if (theFilter.length() > 0) {
       				/* for the time being, only filter 3 levels deep 
       				 * since link references within WSIL are rare at 
       				 * this time.  when adoption of WSIL directories
       				 * take off, this needs to be rehashed */ 
       				fWSILTreeViewer.expandToLevel(3);
       				fWSILTreeViewer.refresh();
       			}
       			else {
       				fWSILTreeViewer.refresh(); 
       				//fWSILTreeViewer.collapseAll();
       			}      				
    		}
    	});
	    
	    DrillDownComposite wsilTreeComposite = new DrillDownComposite(fWSILComposite, SWT.BORDER);
		
		layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.verticalSpacing = 0;
        wsilTreeComposite.setLayout(layout);
        
        data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        wsilTreeComposite.setLayoutData(data);
	        
		//	Tree viewer for variable structure ...
		fWSILTree = new Tree(wsilTreeComposite, SWT.NONE );
		data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.minimumHeight = 200;
        fWSILTree.setLayoutData(data);
		
        String basePath = BPELUIPlugin.getPlugin().getPreferenceStore().getString(IBPELUIConstants.PREF_WSIL_URL);  
		WSILContentProvider wsilCP = new WSILContentProvider(basePath.substring(0, basePath.lastIndexOf('/')+1));
		
		fWSILTreeViewer = new TreeViewer(fWSILTree);
		fWSILTreeViewer.setContentProvider( wsilCP );
		fWSILTreeViewer.setLabelProvider( wsilCP);
		WSDL = false;
		fWSILTreeViewer.setInput ( attemptLoad(URI.createURI(BPELUIPlugin.getPlugin().getPreferenceStore().getString(IBPELUIConstants.PREF_WSIL_URL))) );
		// set default tree expansion to the 2nd level
		fWSILTreeViewer.expandToLevel(2);
		fWSILTreeViewer.addFilter(new TreeFilter());
		fWSILTreeViewer.setComparator(new WSILViewerComparator());
		
		wsilTreeComposite.setChildTree(fWSILTreeViewer);
		
		
		fWSILTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				// TODO Auto-generated method stub
				IStructuredSelection sel = (IStructuredSelection)event.getSelection();
				if (sel.getFirstElement() instanceof Service) {
					Service serv = (Service)sel.getFirstElement();
					Description descr = (Description)serv.getDescription().get(0);
					WSDL = true;
					attemptLoad(descr.getLocation());
				}
			}			
		});
		// end tree viewer for variable structure

        
		return fWSILComposite;
	}

	protected Object createImportStructure (Composite parent) {
		
		Label location = new Label(parent,SWT.NONE);
	    location.setText( fStructureTitle );
	        
		//	Tree viewer for variable structure ...
		fTree = new Tree(parent, SWT.BORDER );
		
		VariableTypeTreeContentProvider variableContentProvider = new VariableTypeTreeContentProvider(true, true);
		fTreeViewer = new TreeViewer(fTree);
		fTreeViewer.setContentProvider( fTreeContentProvider != null ? fTreeContentProvider : variableContentProvider );
		fTreeViewer.setLabelProvider(new ModelTreeLabelProvider());
		fTreeViewer.setInput ( null );		
		fTreeViewer.setAutoExpandLevel(3);
		// end tree viewer for variable structure
		GridData data = new GridData();        
        data.grabExcessVerticalSpace = true;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.FILL;
        data.minimumHeight = 200;
        fTree.setLayoutData(data);
        
		return fTree;
	}


	Object attemptLoad ( URI uri ) {
		ResourceSet resourceSet = BPELUtil.createResourceSetImpl();	
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("wsil", new InspectionResourceFactoryImpl()); //$NON-NLS-1$
		
		/* old code:
 		Resource resource = resourceSet.getResource(uri, true);
		 */
		
		Resource resource = null;

		if (fStructureTitle.compareTo(Messages.SchemaImportDialog_14) == 0) {
			if (WSDL)
				resource = resourceSet.createResource(URI.createURI("*.wsdl")); //$NON-NLS-1$
			else {
				resource = resourceSet.createResource(URI.createURI("*.wsil")); //$NON-NLS-1$
			}
		}
		else if (fStructureTitle.compareTo(Messages.SchemaImportDialog_11) == 0) {
			resource = resourceSet.createResource(URI.createURI("*.xsd")); //$NON-NLS-1$
		}
		else {
			// ask resourceset for "correct" resource
			resource = resourceSet.getResource(uri, true);
		}	
		resource.setURI(uri);
		
		try {
			resource.load(resourceSet.getLoadOptions());
		}
		catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
		
		if (!(resource.getErrors().isEmpty())) {
			/*
			System.out.println("errors detected");
			java.util.Iterator iter = resource.getErrors().iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next().toString());
			}
			*/
			return null;
		}
		
		if (resource.isLoaded()) {
			return  resource.getContents().get(0);
		}
		else
			return null;
	}

	
	void attemptLoad ( IFile file ) {
		attemptLoad ( file.getFullPath().toString() );
	}
	
		
	void attemptLoad ( String path ) {
		if (fRunnableWithProgress != null) {
			return ;
		}
		
		updateStatus ( Status.OK_STATUS );		

		// empty paths are ignored
		if (path.length() == 0) {
			return ;
		}
		

		URI uri = convertToURI ( path );
		if (uri == null) {
			return ;
		}
		
		final URI theURI;
		if (uri.isRelative()) {
			// construct absolute path
	        String basePath = BPELUIPlugin.getPlugin().getPreferenceStore().getString(IBPELUIConstants.PREF_WSIL_URL);   

			String absolutepath = basePath.substring(0, basePath.lastIndexOf('/')+1) + path;
			theURI = URI.createURI(absolutepath);
		}
		else
			theURI = uri;
		
		fRunnableWithProgress = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)  {
				fInput = attemptLoad(theURI);
				
				fTree.getDisplay().asyncExec(new Runnable() {
					public void run() {
						loadDone();						
					}						
				});
			}
		 };
			
		 
		 IProgressService progressService = PlatformUI.getWorkbench().getProgressService();		 
		 try {			 
			progressService.busyCursorWhile(fRunnableWithProgress);			
		} catch (InvocationTargetException ite)  {			
			updateStatus ( new Status(IStatus.ERROR, BPELUIPlugin.getPlugin().getID(),0,Messages.SchemaImportDialog_12,ite.getTargetException() ) );			
			BPELUIPlugin.log( ite.getTargetException() );
			fRunnableWithProgress = null;
		} catch (InterruptedException e) {
			BPELUIPlugin.log( e );
			fRunnableWithProgress = null;
		}
	}

	
	 
	void loadDone () {
		
		fRunnableWithProgress = null;
		
		if (fInput instanceof Definition) {
			if (((Definition)fInput).getTargetNamespace() == null) {
				System.out.println("null targetnamespace");
				updateStatus( new Status(IStatus.ERROR,BPELUIPlugin.getPlugin().getID(),0,"empty message",null) );
			}
		}
		fTreeViewer.setInput(fInput);
		
		
		if (fInput != null) {
			fTree.getVerticalBar().setSelection(0);
		}
		
	}
	
	
	private URI convertToURI (String path ) {
		
		try {
			switch (KIND) {
			case BID_BROWSE_FILE : 
				return URI.createFileURI( path );				
			
			case BID_BROWSE_RESOURCE :
				return URI.createPlatformResourceURI(path);				
			
			case BID_BROWSE_WSIL :
				//return URI.createFileURI( path );
			case BID_BROWSE_URL :
				return URI.createURI(path);
				

				
			default :
				return null;
			}
			
		} catch (Exception ex) {
			updateStatus ( new Status(IStatus.ERROR,BPELUIPlugin.getPlugin().getID(),0,Messages.SchemaImportDialog_13,ex) );			
			return null;
		}
	}

	/**
	 * Update the state of the OK button to the state indicated.
	 * 
	 * @param state false to disable, true to enable.
	 */
	
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
		Object object = fTreeViewer.getInput();
		if (object == null) {
			return ;
		}
		setSelectionResult(new Object[]{ object });		
	}
	
	
	/**
	 * Configure the dialog as a schema import dialog. Set the title and
	 * the structure pane message.
	 *
	 */
	
	public void configureAsSchemaImport ( ) {
		setTitle(Messages.SchemaImportDialog_2);
		fStructureTitle = Messages.SchemaImportDialog_11;		        
	}
	
	/**
	 * Configure the dialog as a WSDL import dialog. Set the title and
	 * the structure pane message.
	 *
	 */
	
	public void configureAsWSDLImport ( ) {
		
		setTitle(Messages.SchemaImportDialog_0);
		fStructureTitle = Messages.SchemaImportDialog_14;
		fTreeContentProvider = new PartnerLinkTypeTreeContentProvider(true);
		
	}
	
	public void configureAsWSILImport() {
        String basePath = BPELUIPlugin.getPlugin().getPreferenceStore().getString(IBPELUIConstants.PREF_WSIL_URL);  
		WSILContentProvider provider = new WSILContentProvider(basePath.substring(0, basePath.lastIndexOf('/')+1));
		fTreeViewer.setLabelProvider(provider);
	}
	
	public class TreeFilter extends ViewerFilter {
	    public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
	        int size = elements.length;
	        ArrayList out = new ArrayList(size);
	        for (int i = 0; i < size; ++i) {
	            Object element = elements[i];
	            if (select(viewer, parent, element)) {
					out.add(element);
				}
	        }
	        return out.toArray();
	    }
	    
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if ((theFilter == null) || (theFilter.length() == 0))
				return true;
			
			if (element instanceof Service) {
				String text = ""; //$NON-NLS-1$
				Service service = (Service)element;
				if (service.getName().size() > 0) {
					Name name = (Name)service.getName().get(0);
					text += name.getValue();
				}
				
				if (service.getAbstract().size() > 0) {
					TypeOfAbstract abst = (TypeOfAbstract)service.getAbstract().get(0);
					text += abst.getValue();
				}
				if (text.indexOf(theFilter) > -1)
					return true;
			}
			
			if (element instanceof Inspection) 
				return true;
			
			if (element instanceof Link) {
				return true;
			}
			
			return false;
		}
	}
	
	public class WSILViewerComparator extends ViewerComparator {
		public int category(Object element) {
			if (element instanceof Inspection)
				return 1;
			if (element instanceof Link)
				return 2;
			if (element instanceof Service)
				return 3;
						
			return 0;
		}
	}
}
