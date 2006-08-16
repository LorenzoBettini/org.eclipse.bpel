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
package org.eclipse.bpel.ui.properties;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.details.viewers.CComboViewer;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.commands.AddPartnerLinkCommand;
import org.eclipse.bpel.ui.commands.SetPartnerLinkTypeCommand;
import org.eclipse.bpel.ui.commands.SetRoleCommand;
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.details.providers.AddNullFilter;
import org.eclipse.bpel.ui.details.providers.AddSelectedObjectFilter;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.details.providers.OperationsTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkTypeContentProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkTypeTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.PortTypeContentProvider;
import org.eclipse.bpel.ui.proposal.providers.CommandProposal;
import org.eclipse.bpel.ui.proposal.providers.ModelContentProposalProvider;
import org.eclipse.bpel.ui.proposal.providers.RunnableProposal;
import org.eclipse.bpel.ui.proposal.providers.Separator;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.BatchedMultiObjectAdapter;
import org.eclipse.bpel.ui.util.BrowseUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.fieldassist.ContentAssistCommandAdapter;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.AbstractHyperlink;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.PortType;

/**
 * VariableTypeSection provides viewing and editing of the type of a BPEL variable
 * (whether that be an XSD type, WSDL message, or built-in simple type).
 */
public class PartnerLinkImplSection extends BPELPropertySection {

	protected static final int INTERFACE_COMBO_CONTEXT = 1;
	protected static final int INTERFACE_BROWSE_CONTEXT = 2;
	protected static final int REFERENCE_COMBO_CONTEXT = 3;
	protected static final int REFERENCE_BROWSE_CONTEXT = 4;
	
	
	
	private static final int BID_MY_ROLE_1 = 1;
	private static final int BID_MY_ROLE_2 = 2;
	private static final int BID_MY_ROLE_NONE = 3;
	
	private static final int BID_PARTNER_ROLE_1 = 101;
	private static final int BID_PARTNER_ROLE_2 = 102;
	private static final int BID_PARTNER_ROLE_NONE = 103;
	
	
	
	private static final Role[] NO_ROLES = {};		
	
	
	public boolean shouldUseExtraSpace() { return true; }
	
	protected boolean inUpdate = false;
	
	protected Composite interfaceComposite, referenceComposite;

	protected Composite parentComposite;
	
	protected CComboViewer interfaceViewer;
	protected Button interfaceBrowseButton;
	protected AddSelectedObjectFilter interfaceAddSelectedObjectFilter;

	protected CComboViewer referenceViewer;
	protected Button referenceBrowseButton;
	protected AddSelectedObjectFilter referenceAddSelectedObjectFilter;

	protected int lastChangeContext = -1;
	private CComboViewer partnerLinkTypeViewer;
	private Button partnerLinkTypeBrowseButton;
	private CComboViewer myRoleViewer;
	private CComboViewer partnerRoleViewer;
	private Text partnerLinkTypeName;
	private Button fMyRole1;
	private Button fMyRole2;
	private Button fMyRoleNone;
	private Button fPartnerRole1;
	private Button fPartnerRole2;
	private Button fPartnerRoleNone;
	private Role[] fRoles;
	
	private TreeViewer fMyOperationsTreeViewer;
	private TreeViewer fPartnerOperationsTreeViewer;
	
	private Hyperlink fPartnerLinkTypeHref;
	private TextContentAdapter fTextContentAdapter;
	

	protected boolean isPartnerLinkAffected(Notification n) {
		if (n.getNotifier() instanceof PartnerLink) {
			return ((n.getFeatureID(PartnerLink.class) == BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE)
				|| (n.getFeatureID(PartnerLink.class) == BPELPackage.PARTNER_LINK__MY_ROLE)
				|| (n.getFeatureID(PartnerLink.class) == BPELPackage.PARTNER_LINK__PARTNER_ROLE));
		}
		return false;
	}
	
	protected boolean isPartnerLinkTypeAffected(Notification n) {
		if (n.getNotifier() instanceof PartnerLinkType) {
			return n.getFeatureID(PartnerLinkType.class) == PartnerlinktypePackage.PARTNER_LINK_TYPE__ROLE;
		}
		return false;
	}
	
	protected boolean isRoleAffected(Notification n) {
		if (n.getNotifier() instanceof Role) {
			return ((n.getFeatureID(Role.class) == PartnerlinktypePackage.ROLE__PORT_TYPE)
				|| (n.getFeatureID(Role.class) == PartnerlinktypePackage.ROLE__NAME));
		}
		return false;
	}
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new BatchedMultiObjectAdapter() {
				boolean updateInterface = false;
				boolean refreshAdapters = false;
				public void notify(Notification n) {
					updateInterface = updateInterface
						|| isPartnerLinkAffected(n)
						|| isPartnerLinkTypeAffected(n)
						|| isRoleAffected(n);
					refreshAdapters = refreshAdapters || updateInterface;
				}
				public void finish() {
					if (updateInterface) {
						updatePartnerLinkTypeWidgets();
						doChildLayout();
						updateInterface = false;
					}
					if (refreshAdapters) {
						refreshAdapters();
						refreshAdapters = false;
					}
				}
			}
		};
	}

	protected void addAllAdapters() {
		super.addAllAdapters();
		if (adapters.length > 0) {
			PartnerLink partnerLink = (PartnerLink)getInput();
			if (partnerLink.getPartnerLinkType() != null) {
				adapters[0].addToObject(partnerLink.getPartnerLinkType());
			}
			Role[] roles = {partnerLink.getMyRole(), partnerLink.getPartnerRole()};
			for (int i = 0; i < roles.length; i++) {
				Role role = roles[i];
				if (role != null) {
					adapters[0].addToObject(role);
				}
			}
		}
	}
	
	
	
	protected Composite createPartnerLinkTypeWidgets (Composite top, final Composite parent) {
		
		FlatFormData data;

		Composite composite = createFlatFormComposite(parent);
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VMARGIN);
		} else {
			data.top = new FlatFormAttachment(top,IDetailsAreaConstants.VMARGIN);
		}
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		
		composite.setLayoutData(data);
			
		partnerLinkTypeBrowseButton = wf.createButton(composite, Messages.PartnerLinkImplSection_Browse_1, SWT.PUSH); 

		Label partnerLinkTypeLabel = wf.createLabel(composite, Messages.PartnerLinkImplSection_0 ); 

//		
//		CommandProposal cmdProposal = new CommandProposal ( getCommandFramework() ) {
//			
//			public Command getCommand () {
//				if (fCmd == null) {
//					fCmd = new AddPartnerLinkCommand( 
//							ModelHelper.getProcess( getInput() ), 
//							BPELFactory.eINSTANCE.createPartnerLink(),
//							null );
//				}
//				return fCmd;
//			}			
//		};
//		
//		
//		// Runnable proposal.
//		RunnableProposal runProposal = new RunnableProposal () {			
//			public String getLabel () {
//				return "Browse for Partner Link Type ...";
//			}			
//			public void run() {
//				browseForPartnerLink();				
//			}			
//		};
//		
//		fTextContentAdapter = new TextContentAdapter() {
//			public void insertControlContents(Control control, String text, int cursorPosition) {
//				if (text != null) {
//					super.insertControlContents(control, text, cursorPosition);
//				}
//			}
//
//			public void setControlContents(Control control, String text, int cursorPosition) {
//				if (text != null) {
//					super.setControlContents(control, text, cursorPosition);
//				}
//			}			
//		};
//		
//		
//		PartnerLinkTypeContentProvider pltcp = new PartnerLinkTypeContentProvider();
//		ModelContentProposalProvider proposalProvider = new ModelContentProposalProvider( new ModelContentProposalProvider.ValueProvider () {
//			public Object value() {
//				return getInput();
//			}			
//		}, pltcp);
//		proposalProvider.addProposalToEnd( new Separator () );
//		proposalProvider.addProposalToEnd( cmdProposal );
//		proposalProvider.addProposalToEnd( runProposal );
//		
//		partnerLinkTypeName = wf.createText(composite, "", SWT.NONE);
//		ContentAssistCommandAdapter contentAssist = new ContentAssistCommandAdapter (
//				partnerLinkTypeName, 
//				fTextContentAdapter, 
//				proposalProvider, 
//				null, 	
//				null );
//		// 
//		contentAssist.setLabelProvider( new ModelLabelProvider () );		
//		contentAssist.setPopupSize( new Point(300,100) );
//		contentAssist.setFilterStyle(ContentProposalAdapter.FILTER_CUMULATIVE);
//		contentAssist.setProposalAcceptanceStyle( ContentProposalAdapter.PROPOSAL_REPLACE );
//		contentAssist.addContentProposalListener( cmdProposal );
//		contentAssist.addContentProposalListener( runProposal );		
		
		fPartnerLinkTypeHref = wf.createHyperlink(composite, "", SWT.NONE); //$NON-NLS-1$
		fPartnerLinkTypeHref.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				PartnerLinkType plt = getPartnerLinkType();
				if (plt == null) {
					return ;
				}				
				BPELUtil.openEditor(getPartnerLinkType(), getBPELEditor());
			}
		});
		fPartnerLinkTypeHref.setToolTipText(Messages.PartnerLinkImplSection_1);
				
		 
		partnerLinkTypeBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {				
				browseForPartnerLink();
			}
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
		

		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		// data.bottom = new FlatFormAttachment(fPartnerLinkTypeHref, -2, SWT.BOTTOM);
		data.right = new FlatFormAttachment(100,-IDetailsAreaConstants.HSPACE);
		partnerLinkTypeBrowseButton.setLayoutData(data);		
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0,IDetailsAreaConstants.HSPACE);		
		data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE );
		partnerLinkTypeLabel.setLayoutData(data);
	
//		data = new FlatFormData();
//		data.left = new FlatFormAttachment(partnerLinkTypeLabel, IDetailsAreaConstants.HSPACE);
//		data.top = new FlatFormAttachment(partnerLinkTypeLabel, -4 , SWT.TOP);
//		// data.bottom = new FlatFormAttachment(partnerLinkTypeLabel, 4 , SWT.BOTTOM);
//		data.right = new FlatFormAttachment( partnerLinkTypeBrowseButton, -30 );
//		partnerLinkTypeName.setLayoutData(data);		
						
		data = new FlatFormData();
		data.right = new FlatFormAttachment( 60, 0 );
		data.left = new FlatFormAttachment ( partnerLinkTypeLabel , 20);
		// data.bottom = new FlatFormAttachment ( partnerLinkTypeLabel, 0 , SWT.BOTTOM);
		fPartnerLinkTypeHref.setLayoutData(data);
		
		return composite;
		
	}

	protected void browseForPartnerLink ( ) {
		
		Object obj = BrowseUtil.browseForPartnerLinkType( getInput() , 
				partnerLinkTypeBrowseButton.getShell() );
		if (obj == null) {
			return;
		}
		
		if (obj instanceof PartnerLinkType) {
			setPartnerLinkType ( (PartnerLinkType) obj);
		}						
	}


	protected Composite createMyRolePartnerRoleWidgets ( Composite top, Composite parent) {

		Composite composite = wf.createComposite(parent);
		FlatFormData data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0,5);
		} else {
			data.top = new FlatFormAttachment(top,5);
		}		
		
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);		
		composite.setLayoutData(data);

		
		GridLayout layout = new GridLayout();
		layout.makeColumnsEqualWidth = true;		
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		GridData gridData;        
				
		Group myGroup = wf.createGroup(composite,Messages.PartnerLinkImplSection_2);		
		layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;		
		layout.numColumns = 3;		
		myGroup.setLayout(layout);
		gridData = new GridData();        
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalIndent = 10;
		gridData.minimumWidth = 150;
		myGroup.setLayoutData(gridData);

		fMyRole1 = createRadioButton(myGroup, null, BID_MY_ROLE_1,    false);
		fMyRole2 = createRadioButton(myGroup, null, BID_MY_ROLE_2,    false);
		fMyRoleNone = createRadioButton(myGroup, Messages.PartnerLinkImplSection_10, BID_MY_ROLE_NONE, true);
		
		updateRadio(fMyRole1, null);
		updateRadio(fMyRole2, null);
		
		Group partnerGroup = wf.createGroup(composite, Messages.PartnerLinkImplSection_4);		
		
		layout = new GridLayout();
		layout.makeColumnsEqualWidth = false;		
		layout.numColumns = 3;		
		partnerGroup.setLayout(layout);
		gridData = new GridData();        
		gridData.grabExcessVerticalSpace = false;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalIndent = 10;
		gridData.minimumWidth = 150;
		partnerGroup.setLayoutData(gridData);
		
		fPartnerRole1 = createRadioButton(partnerGroup, null, BID_PARTNER_ROLE_1,    false);
		fPartnerRole2 = createRadioButton(partnerGroup, null, BID_PARTNER_ROLE_2,    false);
		fPartnerRoleNone = createRadioButton(partnerGroup, Messages.PartnerLinkImplSection_5, BID_PARTNER_ROLE_NONE, true);
		
		updateRadio(fPartnerRole1, null);
		updateRadio(fPartnerRole2, null);		
		
		return composite;
	}
	
	protected Composite createRoleWidgets ( Composite top, Composite parent) {
		return createMyRolePartnerRoleWidgets(top, parent);
		// Composite ref = createMyRoleWidgets(top,parent);
		// ref = createPartnerRoleWidgets(ref, parent);
		// return ref;
	}
	
	
	protected Composite createPartnerLinkStructureWidgets (Composite top, Composite parent) {
		
		Composite composite = createFlatFormComposite(parent);
		
		FlatFormData data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0,5);
		} else {
			data.top = new FlatFormAttachment(top,5);
		}		
		
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100,0);
		
		composite.setLayoutData(data);
			
		Label treeLabel = wf.createLabel(composite, Messages.PartnerLinkImplSection_6); 
		
		// Tree viewer for variable structure ...
		Tree tree  = wf.createTree(composite, SWT.NONE);		
		fMyOperationsTreeViewer = new TreeViewer( tree );
		fMyOperationsTreeViewer.setContentProvider(  new OperationsTreeContentProvider(true) );
		fMyOperationsTreeViewer.setLabelProvider(new ModelTreeLabelProvider());
		fMyOperationsTreeViewer.setInput ( null );
		fMyOperationsTreeViewer.setAutoExpandLevel(4);
		
		// end tree viewer for variable structure
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE + 10 );
		data.top = new FlatFormAttachment(treeLabel, IDetailsAreaConstants.VSPACE, SWT.BOTTOM);
		treeLabel.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE + 10 );
		data.top = new FlatFormAttachment(treeLabel,IDetailsAreaConstants.HSPACE, SWT.BOTTOM);
		data.right = new FlatFormAttachment(50,  -IDetailsAreaConstants.HSPACE) ;		
		data.bottom = new FlatFormAttachment(100, -IDetailsAreaConstants.HSPACE);	
		tree.setLayoutData(data);

		
		treeLabel = wf.createLabel(composite, Messages.PartnerLinkImplSection_7); 
		
		// Tree viewer for variable structure ...
		tree = wf.createTree(composite, SWT.NONE);		
		fPartnerOperationsTreeViewer = new TreeViewer( tree );
		fPartnerOperationsTreeViewer.setContentProvider(new OperationsTreeContentProvider(true));
		fPartnerOperationsTreeViewer.setLabelProvider(new ModelTreeLabelProvider());
		fPartnerOperationsTreeViewer.setInput ( null );
		fPartnerOperationsTreeViewer.setAutoExpandLevel(4);
		
		// end tree viewer for variable structure
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(50, IDetailsAreaConstants.HSPACE + 10 );
		data.top = new FlatFormAttachment(treeLabel, IDetailsAreaConstants.VSPACE, SWT.BOTTOM);
		treeLabel.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(50, IDetailsAreaConstants.HSPACE + 10 );
		data.top = new FlatFormAttachment(treeLabel,IDetailsAreaConstants.HSPACE, SWT.BOTTOM);
		data.right = new FlatFormAttachment(100,  -IDetailsAreaConstants.HSPACE) ;		
		data.bottom = new FlatFormAttachment(100, -IDetailsAreaConstants.HSPACE);	
		tree.setLayoutData(data);
				
		return null;
	}
	
	
	protected void createClient(Composite parent) {
		
		Composite composite = parentComposite = createFlatFormComposite(parent);
				
//		FlatFormData data = new FlatFormData();
//		data.top = new FlatFormAttachment(0,0);				
//		data.left = new FlatFormAttachment(0, 0);
//		data.right = new FlatFormAttachment(100, 0);
//		data.bottom = new FlatFormAttachment(100,0);		
//		composite.setLayoutData(data);		
		
		Composite ref = createPartnerLinkTypeWidgets(null,composite);
		
		ref = createRoleWidgets ( ref, composite );
				
		createPartnerLinkStructureWidgets( ref, composite  );
		
		// createInterfaceWidgets(composite);
		// createReferenceWidgets(composite);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parentComposite, IHelpContextIds.PROPERTY_PAGE_PARTNER_IMPLEMENTATION);
	}
	
	
	
	
	protected void doChildLayout() {
		// PartnerLink partnerLink = (PartnerLink)getInput();
		// boolean showInterface = ModelHelper.isInterfacePartnerLink(partnerLink);
		// boolean showReference = ModelHelper.isReferencePartnerLink(partnerLink);
		// interfaceComposite.setVisible(showInterface);
		// referenceComposite.setVisible(showReference);
		// FlatFormData data = (FlatFormData)referenceComposite.getLayoutData();
		// if (showInterface) {
		//	data.top = new FlatFormAttachment(interfaceComposite, IDetailsAreaConstants.VSPACE);
		//} else {
		//	data.top = new FlatFormAttachment(0,0);
		//}
		parentComposite.layout(true,true);
	}	
	
	protected void updateInterfaceWidgets()  {
		if (getInput() == null)  throw new IllegalStateException();
		PartnerLink partnerLink = (PartnerLink)getInput();

		PortType portType = ModelHelper.getPartnerPortType(partnerLink, ModelHelper.INCOMING);
		
		interfaceViewer.setInput(getProcess());
		interfaceAddSelectedObjectFilter.setSelectedObject(portType);
		inUpdate = true;
		try {
			refreshCCombo(interfaceViewer, portType);
		} finally {
			inUpdate = false;
		}
	}

	protected void updateReferenceWidgets()  {
		if (getInput() == null)  throw new IllegalStateException();
		PartnerLink partnerLink = (PartnerLink)getInput();		
		PortType portType = ModelHelper.getPartnerPortType(partnerLink,
			ModelHelper.OUTGOING);
		
		referenceViewer.setInput(getProcess());
		referenceAddSelectedObjectFilter.setSelectedObject(portType);
		inUpdate = true;
		try {
			refreshCCombo(referenceViewer, portType);
		} finally {
			inUpdate = false;
		}
	}

	
	protected void updateMyRolePartnerRoleWidgets ( PartnerLink pl ) {
		
		PartnerLinkType plt = pl.getPartnerLinkType();
		fRoles = NO_ROLES ;
		if (plt != null) {
			fRoles = (Role[]) plt.getRole().toArray( NO_ROLES );			
		}
		
		Role myRole = pl.getMyRole();
		Role partnerRole = pl.getPartnerRole();
		
		
		updateRadio(fMyRole1,null);
		updateRadio(fMyRole2,null);
		updateRadio(fPartnerRole1,null);
		updateRadio(fPartnerRole2,null);

		selectRadio(fPartnerRoleNone);
		selectRadio(fMyRoleNone);
		
		// Roles are 1..2, so technically, this should never happen.

		// If at least 1 role specified ...
		if (fRoles.length >= 1) {
			String role1 = fRoles[0].getName();
			
			updateRadio (fMyRole1, role1);
			updateRadio (fPartnerRole1,role1);
			
			if ( fRoles[0].equals( myRole ) ) {
				selectRadio (fMyRole1);
			} 
			if (fRoles[0].equals(partnerRole)) {
				selectRadio (fPartnerRole1);
			} 
		}
		
		// If at most 2 roles specified ...
		if (fRoles.length >= 2) {
			String role2 = fRoles[1].getName();
			updateRadio (fMyRole2, role2);
			updateRadio (fPartnerRole2,role2);
			
			if (fRoles[1].equals ( myRole) ) {
				selectRadio(fMyRole2);
			} 
			if (fRoles[1].equals ( partnerRole )) {
				selectRadio(fPartnerRole2);
			} 
		}
	}
	
	protected void updatePartnerLinkType (PartnerLink pl) {
		PartnerLinkType plt = pl.getPartnerLinkType();
		if (plt == null) {
			fPartnerLinkTypeHref.setText(Messages.PartnerLinkImplSection_8);
			// partnerLinkTypeName.setText("(None)");
			return;
		}
		
		String name = null;
		ILabeledElement label =  (ILabeledElement) BPELUtil.adapt(plt, ILabeledElement.class  );
		if (label != null) {
			name = label.getLabel( plt );
		} 	
		fPartnerLinkTypeHref.setText( name );
		// partnerLinkTypeName.setText(name);
	}
	
	protected void updatePartnerLinkTypeWidgets() {
		Object obj = getInput();
		if (obj == null) {
			throw new IllegalStateException();
		}
		PartnerLink pl = (PartnerLink) obj;
		updateMyRolePartnerRoleWidgets( pl );
		updatePartnerLinkType (pl);
		
		
		fMyOperationsTreeViewer.setInput( ModelHelper.getPartnerPortType(pl, ModelHelper.INCOMING) );
		fPartnerOperationsTreeViewer.setInput( ModelHelper.getPartnerPortType(pl, ModelHelper.OUTGOING ) );
	}
	
	
	protected PartnerLinkType getPartnerLinkType () {
		PartnerLink pl = (PartnerLink) modelObject;
		return pl.getPartnerLinkType();
	}
	
	
	protected void basicSetInput(EObject newInput) {
		
		super.basicSetInput(newInput);
		
		updatePartnerLinkTypeWidgets();
		doChildLayout();		
	}

	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	
	public void restoreUserContext(Object userContext) {
		
		
//		int i = ((Integer)userContext).intValue();
//		switch (i) {
//		case INTERFACE_COMBO_CONTEXT: interfaceViewer.getCCombo().setFocus(); return;
//		case INTERFACE_BROWSE_CONTEXT: interfaceBrowseButton.setFocus(); return;
//		case REFERENCE_COMBO_CONTEXT: referenceViewer.getCCombo().setFocus(); return;
//		case REFERENCE_BROWSE_CONTEXT: referenceBrowseButton.setFocus(); return;
//		}
//		throw new IllegalStateException();
	}

	public void storeInterface(final PortType portType, final int whichRole) {
		final PartnerLink partnerLink = (PartnerLink)getInput();
		CompoundCommand cmd = new CompoundCommand();
		PartnerLinkType plt = partnerLink.getPartnerLinkType();
		if (((plt == null) || plt.eIsProxy()) && (portType != null)) {
			// need a PLT.
			plt = PartnerlinktypeFactory.eINSTANCE.createPartnerLinkType();
			Definition artifactsDefinition = getBPELEditor().getArtifactsDefinition();
			cmd.add(ModelHelper.getCreatePartnerLinkTypeCommand(getProcess(), partnerLink, plt, artifactsDefinition, whichRole));
		}
		if (plt != null) {
			// set port type
			cmd.add(new AutoUndoCommand(plt) {
	            public void doExecute() {
	            	Role role = (whichRole == ModelHelper.MY_ROLE) ? partnerLink.getMyRole() : partnerLink.getPartnerRole();
	            	role.setPortType(portType);
	            }
	        });
		}
		
		// lastChangeContext is set by caller
		if (!cmd.isEmpty()) getCommandFramework().execute(wrapInShowContextCommand(cmd));
	}
	
	protected Button createRadioButton (Composite parent, String label, int id, boolean checked) {
		
		Button button = new Button(parent,SWT.RADIO);
		if (label != null) {
			button.setText(label);
		}		
		button.setData(new Integer(id));		
		button.setSelection( checked );
		
		button.addSelectionListener (new SelectionAdapter() {
			public void widgetSelected (SelectionEvent event) {
				Button button = (Button) event.widget;
				int id = ((Integer) button.getData()).intValue();
				
				buttonPressed(id, button.getSelection(),true);
			}
		});
		
		return button;	
	}

	
	
	void updateRadio ( Button button, String text ) {
		
		if ( text == null ) {
			button.setText(Messages.PartnerLinkImplSection_9);
			button.setEnabled(false);
			button.setSelection(false);
		} else {
			button.setText (text);
			button.setEnabled (true);
		}
	}
	
	
	
	void selectRadio ( Button button ) {
				
		Control [] children = button.getParent().getChildren ();		
		for (int i=0; i<children.length; i++) {
			Control child = children [i];
			if (button != child && child instanceof Button) {
				unselectRadio((Button)child);
			}
		}
		
		button.setSelection(true);
		int id = ((Integer)button.getData()).intValue();
		buttonPressed( id, true, false ) ;
	}
	
	
	void unselectRadio (Button button) {
		if (button.getSelection () ) {
			button.setSelection (false);
			int id = ((Integer)button.getData()).intValue();
			buttonPressed( id, false , false ) ;
		}
	}
	
	
	/**
	 * @param id
	 * @param selection
	 */
	protected void buttonPressed(int id, boolean selection, boolean event ) {
		
		if (!selection || !event ) {
			// only respond to events
			return ;
		}
		
		PartnerLink pl = (PartnerLink) getInput();
		SetRoleCommand cmd = null;
		
		// Current myRole or partnerRole may be null
		Role myRole = pl.getMyRole();
		Role partnerRole = pl.getPartnerRole();
				
		switch (id) {
		case BID_MY_ROLE_1 :
			
			if (myRole == null || myRole.equals( fRoles[0]) == false ) {
				cmd = new SetRoleCommand(pl,fRoles[0],ModelHelper.MY_ROLE);	
			}			
			break;
			
		case BID_MY_ROLE_2 :
			if (myRole == null || myRole.equals( fRoles[1]) == false ) {
				cmd = new SetRoleCommand(pl,fRoles[1],ModelHelper.MY_ROLE);
			}
			break;
			
		case BID_MY_ROLE_NONE :
			if (myRole != null) {
				cmd = new SetRoleCommand(pl,null,ModelHelper.MY_ROLE);
			}
			break;
			
		case BID_PARTNER_ROLE_1 :
			if (partnerRole == null || partnerRole.equals( fRoles[0]) == false) {
				cmd = new SetRoleCommand (pl,fRoles[0],ModelHelper.PARTNER_ROLE);
			}
			break;
			
		case BID_PARTNER_ROLE_2 :
			if (partnerRole == null || partnerRole.equals( fRoles[1]) == false) {
				cmd = new SetRoleCommand (pl,fRoles[1],ModelHelper.PARTNER_ROLE);
			}
			break;
					
		case BID_PARTNER_ROLE_NONE :
			if (partnerRole != null) {
				cmd = new SetRoleCommand(pl,null,ModelHelper.PARTNER_ROLE);
			}
			break;
		}
		
		if (cmd != null) {
			getCommandFramework().execute( cmd );
		}
		
		// System.out.println("Buttonid=" + id + "; selection=" + selection);
	}
	
	
	
	private void setPartnerLinkType (PartnerLinkType type) {
		
		PartnerLink pl = (PartnerLink) getInput();
		
		CompoundCommand cmd = new CompoundCommand ();				
		cmd.add(  new SetPartnerLinkTypeCommand(pl, type) );				
		cmd.add ( new SetRoleCommand(pl,null,ModelHelper.MY_ROLE) ) ;		
		cmd.add ( new SetRoleCommand(pl,null,ModelHelper.PARTNER_ROLE) );
				
		getCommandFramework().execute(cmd);	
	}

	
}
