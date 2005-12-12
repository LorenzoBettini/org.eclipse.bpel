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
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.details.providers.AddNullFilter;
import org.eclipse.bpel.ui.details.providers.AddSelectedObjectFilter;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.details.providers.PortTypeContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.BatchedMultiObjectAdapter;
import org.eclipse.bpel.ui.util.BrowseUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
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
						updateInterfaceWidgets();
						updateReferenceWidgets();
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
	
	protected void createInterfaceWidgets(Composite parent) {
		FlatFormData data;

		Composite composite = interfaceComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		interfaceComposite.setLayoutData(data);
		
		interfaceBrowseButton = wf.createButton(composite, Messages.PartnerLinkImplSection_Browse_1, SWT.PUSH); 

		Label interfaceLabel = wf.createLabel(composite, Messages.PartnerLinkImplSection_Interface_1); 
		CCombo interfaceCombo = wf.createCCombo(composite);
		interfaceViewer = new CComboViewer(interfaceCombo);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 2);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(interfaceLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(interfaceBrowseButton, -IDetailsAreaConstants.HSPACE);
		interfaceCombo.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(interfaceCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(interfaceCombo, 0, SWT.CENTER);
		interfaceLabel.setLayoutData(data);

		interfaceAddSelectedObjectFilter = new AddSelectedObjectFilter();
		interfaceViewer.addFilter(AddNullFilter.getInstance());
		interfaceViewer.addFilter(interfaceAddSelectedObjectFilter);
		interfaceViewer.setLabelProvider(new ModelLabelProvider());
		interfaceViewer.setContentProvider(new PortTypeContentProvider());
		interfaceViewer.setSorter(ModelViewerSorter.getInstance());
		interfaceViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				PortType portType = (PortType)selection.getFirstElement();

				lastChangeContext = INTERFACE_COMBO_CONTEXT;
				storeInterface(portType, ModelHelper.MY_ROLE);
			}
		});
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE + IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(interfaceCombo, -1, SWT.TOP);
		data.bottom = new FlatFormAttachment(interfaceCombo, +1, SWT.BOTTOM);
		interfaceBrowseButton.setLayoutData(data);

		final Shell shell = composite.getShell();
		interfaceBrowseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
            	PortType portType = BrowseUtil.browseForPortType(getBPELEditor().getResourceSet(), shell);
            	if (portType != null) {
					lastChangeContext = INTERFACE_BROWSE_CONTEXT;
					storeInterface(portType, ModelHelper.MY_ROLE);
			    }		
			}
		});
	}

	protected void createReferenceWidgets(Composite parent) {
		FlatFormData data;

		Composite composite = referenceComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		referenceComposite.setLayoutData(data);
		
		referenceBrowseButton = wf.createButton(composite, Messages.PartnerLinkImplSection_Browse_1, SWT.PUSH); 

		Label referenceLabel = wf.createLabel(composite, Messages.PartnerLinkImplSection_Reference_Interface_2); 
		CCombo referenceCombo = wf.createCCombo(composite);
		referenceViewer = new CComboViewer(referenceCombo);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 2);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(referenceLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(interfaceBrowseButton, -IDetailsAreaConstants.HSPACE);
		referenceCombo.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(referenceCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(referenceCombo, 0, SWT.CENTER);
		referenceLabel.setLayoutData(data);

		referenceAddSelectedObjectFilter = new AddSelectedObjectFilter();
		referenceViewer.addFilter(AddNullFilter.getInstance());
		referenceViewer.addFilter(referenceAddSelectedObjectFilter);
		referenceViewer.setLabelProvider(new ModelLabelProvider());
		referenceViewer.setContentProvider(new PortTypeContentProvider());
		referenceViewer.setSorter(ModelViewerSorter.getInstance());
		referenceViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection = (StructuredSelection)event.getSelection();
				PortType portType = (PortType)selection.getFirstElement();

				lastChangeContext = REFERENCE_COMBO_CONTEXT;
				storeInterface(portType, ModelHelper.PARTNER_ROLE);
			}
		});
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE + IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(referenceCombo, -1, SWT.TOP);
		data.bottom = new FlatFormAttachment(referenceCombo, +1, SWT.BOTTOM);
		referenceBrowseButton.setLayoutData(data);

		final Shell shell = composite.getShell();
		referenceBrowseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
            	PortType portType = BrowseUtil.browseForPortType(getBPELEditor().getResourceSet(), shell);
            	if (portType != null) {
					lastChangeContext = REFERENCE_BROWSE_CONTEXT;
					storeInterface(portType, ModelHelper.PARTNER_ROLE);
			    }		
			}
		});
	}

	protected void createClient(Composite parent) {
		Composite composite = parentComposite = createFlatFormComposite(parent);
		createInterfaceWidgets(composite);
		createReferenceWidgets(composite);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parentComposite, IHelpContextIds.PROPERTY_PAGE_PARTNER_IMPLEMENTATION);
	}
	
	protected void doChildLayout() {
		PartnerLink partnerLink = (PartnerLink)getInput();
		boolean showInterface = ModelHelper.isInterfacePartnerLink(partnerLink);
		boolean showReference = ModelHelper.isReferencePartnerLink(partnerLink);
		interfaceComposite.setVisible(showInterface);
		referenceComposite.setVisible(showReference);
		FlatFormData data = (FlatFormData)referenceComposite.getLayoutData();
		if (showInterface) {
			data.top = new FlatFormAttachment(interfaceComposite, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment(0,0);
		}
		parentComposite.layout(true);
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

	public void refresh() {
		super.refresh();
		updateInterfaceWidgets();
		updateReferenceWidgets();
		doChildLayout();
	}

	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	public void restoreUserContext(Object userContext) {
		int i = ((Integer)userContext).intValue();
		switch (i) {
		case INTERFACE_COMBO_CONTEXT: interfaceViewer.getCCombo().setFocus(); return;
		case INTERFACE_BROWSE_CONTEXT: interfaceBrowseButton.setFocus(); return;
		case REFERENCE_COMBO_CONTEXT: referenceViewer.getCCombo().setFocus(); return;
		case REFERENCE_BROWSE_CONTEXT: referenceBrowseButton.setFocus(); return;
		}
		throw new IllegalStateException();
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
}
