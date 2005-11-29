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
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.commands.SetOnEventVariableTypeCommand;
import org.eclipse.bpel.ui.commands.SetOperationCommand;
import org.eclipse.bpel.ui.commands.SetPartnerLinkCommand;
import org.eclipse.bpel.ui.commands.SetPortTypeCommand;
import org.eclipse.bpel.ui.commands.SetWSDLFaultCommand;
import org.eclipse.bpel.ui.details.providers.AddNullFilter;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.details.providers.OperationContentProvider;
import org.eclipse.bpel.ui.details.providers.WSDLFaultContentProvider;
import org.eclipse.bpel.ui.dialogs.PartnerLinkSelectorDialog;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

/**
 * Details section for the Partner/PortType/Operation properties of a
 * Receive/Reply/Invoke activity and OnMessage.
 */
public class InvokeImplSection extends BPELPropertySection {

	protected static final int PARTNER_CONTEXT = 0;
	protected static final int OPERATION_CONTEXT = 1;
	protected static final int NORMALRADIO_CONTEXT = 2;
	protected static final int FAULTRADIO_CONTEXT = 3;
	protected static final int FAULTNAME_CONTEXT = 4;
	
	protected int lastChangeContext = -1;

	protected Composite parentComposite;
	protected Composite partnerComposite, portTypeComposite, operationComposite;
//	protected Composite[] variableComposite = new Composite[2];
	protected Composite replyTypeComposite, faultNameComposite;

	protected Label partnerLabel;
	protected Label partnerName;
	protected CComboViewer operationViewer;
	protected Label interfaceLabel, operationLabel;
	protected Label interfaceName;
	protected Button partnerBrowseButton;
	protected CCombo operationCombo;

	boolean blockOperationUpdates;

	protected Button normalRadio, faultRadio;
	protected CCombo faultNameCombo;
	protected CComboViewer faultNameViewer;
	
//	protected boolean[] variableEnabled = new boolean[2];
//	protected Button[] variableBrowseButton = new Button[2];
//	protected Label[] variableLabel = new Label[2];
//	protected Label[] variableName = new Label[2];
	protected boolean isInvoke;

	public String labelWordFor(int direction) {
		if (isInvoke) return (direction == 0)? Messages.InvokeImplDetails_Request_3:Messages.InvokeImplDetails_Response_4; 
		return (direction == 0)? Messages.InvokeImplDetails_Response_4:Messages.InvokeImplDetails_Request_3;  
	}

	/**
	 * The same as labelWordFor(), except these strings don't contain mnemonics!
	 */
	public String plainLabelWordFor(int direction) {
		if (isInvoke) return (direction == 0)? Messages.InvokeImplDetails_Request_3_Plain:Messages.InvokeImplDetails_Response_4_Plain; 
		return (direction == 0)? Messages.InvokeImplDetails_Response_4_Plain:Messages.InvokeImplDetails_Request_3_Plain;  
	}

	protected Role getActiveRole() {
		PartnerLink partnerLink = ModelHelper.getPartnerLink(getInput());
		if (partnerLink == null)  return null;
		return isInvoke? partnerLink.getPartnerRole() : partnerLink.getMyRole();
	}

	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
				    try {
						Object input = getInput();
						boolean upp = false;
						// TODO: can the calls to updatePortTypeWidgets() be improved?
						if (ModelHelper.isPartnerAffected(input, n)) {
							upp = true;
							updatePartnerWidgets();
							updatePortTypeWidgets();
							updateOperationWidgets();
							updateFaultNameWidgets();
						} 
						if (ModelHelper.isOperationAffected(input, n)) {
							if (!upp) {
								upp = true;
								updatePortTypeWidgets();
								updateOperationWidgets();
								updateFaultNameWidgets();
							}
							// Update the response variable widgets, because if the operation
							// is one-way, these widgets should be disabled.
							rearrangeWidgets();
						} 
						if (replyTypeEnabled() && ModelHelper.isFaultNameAffected(input, n)) {
							updateReplyTypeWidgets();
							if (!upp) updateFaultNameWidgets();
						}
				    } catch (Exception e) {
						BPELUIPlugin.log(e);
				    }
				}
			},
		};
	}

	protected boolean replyTypeEnabled() {
		if (isInvoke) return false;
		if (!(getInput() instanceof Reply)) return false;
		// Only enabled if there is a fault in the operation,
		// or if no operation is specified at all.
		Operation op = ModelHelper.getOperation(getInput());
		if (op == null) return true;
		return !op.getFaults().isEmpty();
	}
	protected boolean faultNameEnabled() {
		return replyTypeEnabled() && faultRadio.getSelection();
	}

	protected void doChildLayout() {
//		if (variableViewer == null)  return;
		
		portTypeComposite.setVisible(true);
		operationComposite.setVisible(true);
		partnerComposite.setVisible(true);
		
		// attach visible widgets to the things above them..
		FlatFormAttachment top = new FlatFormAttachment(0, 0);
		replyTypeComposite.setVisible(replyTypeEnabled());
		if (replyTypeEnabled()) {
			FlatFormData data = (FlatFormData)replyTypeComposite.getLayoutData();
			data.top = top;
			top = new FlatFormAttachment(replyTypeComposite, IDetailsAreaConstants.VSPACE);
		}
		faultNameComposite.setVisible(faultNameEnabled());
		if (faultNameEnabled()) {
			FlatFormData data = (FlatFormData)faultNameComposite.getLayoutData();
			data.top = top;
			top = new FlatFormAttachment(faultNameComposite, IDetailsAreaConstants.VSPACE);
		}
		parentComposite.layout(true);
	}

	protected void basicSetInput(EObject input) {
		super.basicSetInput(input);
		rearrangeWidgets();
	}
	
	protected void rearrangeWidgets() {
		isInvoke = (getInput() instanceof Invoke);
		doChildLayout();
	}

	protected void createPartnerWidgets(Composite parent) {
		FlatFormData data;
		
		final Composite composite = partnerComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE);
		composite.setLayoutData(data);
		
		partnerLabel = wf.createLabel(composite, Messages.InvokeImplDetails_Partner__10); 
		partnerName = wf.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		partnerBrowseButton = wf.createButton(composite, Messages.InvokeImplSection_Browse_1, SWT.PUSH); 

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(partnerLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(partnerBrowseButton, -IDetailsAreaConstants.HSPACE);
		data.height = FigureUtilities.getTextExtents(partnerBrowseButton.getText(), partnerBrowseButton.getFont()).height + 4;
		partnerName.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(100, -BPELUtil.calculateButtonWidth(partnerBrowseButton, SHORT_BUTTON_WIDTH));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(partnerName, 0, SWT.TOP);
		data.bottom = new FlatFormAttachment(partnerName, 4, SWT.BOTTOM);
		partnerBrowseButton.setLayoutData(data);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(partnerName, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(partnerName, 0, SWT.CENTER);
		partnerLabel.setLayoutData(data);

		partnerBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Shell shell = composite.getShell();
				EObject model = getInput();
				PartnerLinkSelectorDialog dialog = new PartnerLinkSelectorDialog(shell, model);
				if (dialog.open() == Window.OK) {
					PartnerLink partner = dialog.getPartnerLink();
					Command command = new SetPartnerLinkCommand(model, partner);
					lastChangeContext = PARTNER_CONTEXT;
					getCommandFramework().execute(wrapInShowContextCommand(command));
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
	}
	
	protected void createPortTypeWidgets(Composite parent) {
		FlatFormData data;
		
		final Composite composite = portTypeComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(partnerComposite, IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE);
		composite.setLayoutData(data);
		
		interfaceLabel = wf.createLabel(composite, Messages.InvokeImplSection_Interface_1); 
		interfaceName = wf.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, +1);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(interfaceLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(partnerBrowseButton, -IDetailsAreaConstants.HSPACE);
		interfaceName.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(interfaceName, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(interfaceName, 0, SWT.CENTER);
		interfaceLabel.setLayoutData(data);
	}

	protected void createOperationWidgets(Composite parent) {
		FlatFormData data;
		
		final Composite composite = operationComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(portTypeComposite, IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE);
		composite.setLayoutData(data);
		
		operationLabel = wf.createLabel(composite, Messages.InvokeImplDetails_Operation__19); 
		operationCombo = wf.createCCombo(composite);
		operationViewer = new CComboViewer(operationCombo);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(operationLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(partnerBrowseButton, -IDetailsAreaConstants.HSPACE);
		operationCombo.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(operationCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(operationCombo, 0, SWT.CENTER);
		operationLabel.setLayoutData(data);

		operationViewer.addFilter(AddNullFilter.getInstance());			
		operationViewer.setLabelProvider(new ModelLabelProvider());
		operationViewer.setContentProvider(new OperationContentProvider());
		operationViewer.setSorter(ModelViewerSorter.getInstance());
		operationViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection)operationViewer.getSelection();
				// TODO: if changing to a one-way operation, clear the response variable (if any)
				PartnerLink partnerLink = ModelHelper.getPartnerLink(getInput());

				CompoundCommand cmd = new CompoundCommand();
				Command child = null;
				PortType portType = getEffectivePortType(partnerLink);
				Operation op = (Operation)sel.getFirstElement();
				
				if (op == null)  portType = null;
				
				child = new SetPortTypeCommand(getInput(), portType);
				if (child.canExecute())  cmd.add(child);
				
				child = new SetOperationCommand(getInput(), op);
				if (child.canExecute()) {
					if (replyTypeEnabled()) {
						Command setFaultCommand = new SetWSDLFaultCommand(getInput(), null);
						if (setFaultCommand.canExecute())  cmd.add(setFaultCommand);
					}
					cmd.add(child); cmd.setLabel(child.getLabel());
				} 

				if (getInput() instanceof OnEvent) {
					child = new SetOnEventVariableTypeCommand((OnEvent)getInput());
					cmd.add(child);
				}
				blockOperationUpdates = true;
				lastChangeContext = OPERATION_CONTEXT;
				try {
					getCommandFramework().execute(wrapInShowContextCommand(cmd));
				} finally {
					blockOperationUpdates = false;
				}
			}
		});
	}
	
	protected void createReplyTypeWidgets(Composite parent) {
		FlatFormData data;
		
		Composite composite = replyTypeComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		// hack: match our height to the partnerComposite (which is to the left of us)
		data.bottom = new FlatFormAttachment(partnerComposite, 0, SWT.BOTTOM);
		data.left = new FlatFormAttachment(50, IDetailsAreaConstants.CENTER_SPACE);
		data.right = new FlatFormAttachment(100, 0);
		composite.setLayoutData(data);
		
		Label replyTypeLabel = wf.createLabel(composite, Messages.InvokeImplDetails_Reply_Type__20); 
		
		normalRadio = wf.createButton(composite, Messages.InvokeImplDetails_Normal_21, SWT.RADIO); 
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(replyTypeLabel, STANDARD_LABEL_WIDTH_SM));
		// hack: fudge vertical alignment.
		data.top = new FlatFormAttachment(0, 1);
		normalRadio.setLayoutData(data);
		
//		data = new FlatFormData();
//		data.left = new FlatFormAttachment(normalRadio, 0, SWT.RIGHT);
//		// hack: fudge vertical alignment.
//		data.top = new FlatFormAttachment(0, 0);
//		normalRadioLabel.setLayoutData(data);
		
		SelectionListener normalSelectionListener = new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!normalRadio.getSelection()) return;
				doChildLayout();
				CompoundCommand cmd = new CompoundCommand();
				cmd.add(new SetWSDLFaultCommand(getInput(), null));
				/* WDG: removed command */
				lastChangeContext = NORMALRADIO_CONTEXT;
				getCommandFramework().execute(wrapInShowContextCommand(cmd));
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		};
		normalRadio.addSelectionListener(normalSelectionListener);
		
		faultRadio = wf.createButton(composite, Messages.InvokeImplDetails_Fault_22, SWT.RADIO); 
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(normalRadio, IDetailsAreaConstants.HSPACE, SWT.RIGHT);
		data.top = new FlatFormAttachment(normalRadio, 0, SWT.TOP);
		data.bottom = new FlatFormAttachment(normalRadio, 0, SWT.BOTTOM);
		faultRadio.setLayoutData(data);
		
//		data = new FlatFormData();
//		data.left = new FlatFormAttachment(faultRadio, 0, SWT.RIGHT);
//		data.top = new FlatFormAttachment(faultRadio, 0, SWT.TOP);
//		faultRadioLabel.setLayoutData(data);
		
		SelectionListener faultSelectionListener = new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if (!faultRadio.getSelection()) return;
				doChildLayout();
				// Try to select the first fault in our Operation!  If there isn't any,
				// then the command won't be executed, so we must update widgets manually.
				Operation operation = ModelHelper.getOperation(getInput());
				Fault fault = null;
				if (operation != null && !operation.getEFaults().isEmpty()) {
					fault = (Fault)operation.getEFaults().get(0); 
				}
				CompoundCommand cmd = new CompoundCommand();
				cmd.add(new SetWSDLFaultCommand(getInput(), fault));
				if (cmd.canExecute()) {
					/* WDG: removed command */
					lastChangeContext = FAULTRADIO_CONTEXT;
					getCommandFramework().execute(wrapInShowContextCommand(cmd));
				} else {
					updateFaultNameWidgets();
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		};
		faultRadio.addSelectionListener(faultSelectionListener);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(normalRadio, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(normalRadio, 0, SWT.CENTER);
		replyTypeLabel.setLayoutData(data);
	}

	protected String getVariableDialogTitle(EObject target, int direction) {
		if (modelObject instanceof Invoke) {
			return (direction == ModelHelper.OUTGOING) ?
				Messages.InvokeImplSection_Select_Request_Variable_1 : 
				Messages.InvokeImplSection_Select_Response_Variable_1; 
		}
		return (direction == ModelHelper.OUTGOING) ?
			Messages.InvokeImplSection_Select_Response_Variable_1 : 
			Messages.InvokeImplSection_Select_Request_Variable_1; 
	}
	
	protected void createFaultNameWidgets(Composite parent) {
		FlatFormData data;

		Composite composite = faultNameComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(50, IDetailsAreaConstants.CENTER_SPACE);
		data.right = new FlatFormAttachment(100, 0);
		composite.setLayoutData(data);

		Label faultNameLabel = wf.createLabel(composite, Messages.InvokeImplDetails_Fault_Name__25); 
		
		faultNameCombo = wf.createCCombo(composite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(faultNameLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(100, -SHORT_BUTTON_WIDTH-IDetailsAreaConstants.HSPACE);
		faultNameCombo.setLayoutData(data);
		
		faultNameViewer = new CComboViewer(faultNameCombo);
		faultNameViewer.setLabelProvider(new ModelLabelProvider());
		faultNameViewer.setContentProvider(new WSDLFaultContentProvider());
		faultNameViewer.setSorter(ModelViewerSorter.getInstance());
		faultNameViewer.addFilter(AddNullFilter.getInstance());
		faultNameViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection)faultNameViewer.getSelection();
				Fault fault = (Fault)sel.getFirstElement();
				CompoundCommand cmd = new CompoundCommand();
				cmd.add(new SetWSDLFaultCommand(getInput(), fault));
				/* WDG: removed command */
				lastChangeContext = FAULTNAME_CONTEXT;
				getCommandFramework().execute(wrapInShowContextCommand(cmd));
			}
		});
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(faultNameCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(faultNameCombo, 0, SWT.CENTER);
		faultNameLabel.setLayoutData(data);
	}

	protected void createClient(Composite parent)  {
		Composite composite = parentComposite = createFlatFormComposite(parent);

		createPartnerWidgets(composite);
		createPortTypeWidgets(composite);
		createOperationWidgets(composite);
		createReplyTypeWidgets(composite);
		createFaultNameWidgets(composite);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parentComposite, IHelpContextIds.PROPERTY_PAGE_INVOKE_IMPLEMENTATION);
	}
	
	protected void updatePartnerWidgets() {
		PartnerLink partnerLink = ModelHelper.getPartnerLink(getInput());
		if (partnerLink == null) {
			partnerName.setText(Messages.InvokeImplSection_None_1); 
			partnerName.setEnabled(false);
		} else {
			ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(partnerLink, ILabeledElement.class);
			partnerName.setText(labeledElement.getLabel(partnerLink));
			partnerName.setEnabled(true);
		}
	}
	
	// TODO: move these to ModelHelper?
	
	PortType getEffectivePortType(PartnerLink partnerLink) {
		if (partnerLink != null) {
			Role role = isInvoke? partnerLink.getPartnerRole() : partnerLink.getMyRole();
			return ModelHelper.getRolePortType(role);
		}
		return null;		
	}

	protected void updatePortTypeWidgets() {
		PartnerLink partnerLink = ModelHelper.getPartnerLink(getInput());
		PortType portType = getEffectivePortType(partnerLink);
		if (portType == null) {
			interfaceName.setText(Messages.InvokeImplSection_None_1); 
			interfaceName.setEnabled(false);
		} else {
			ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(portType, ILabeledElement.class);
			interfaceName.setText(labeledElement.getLabel(portType));
			interfaceName.setEnabled(true);
		}
	}
	
	protected void updateOperationWidgets() {
		if (blockOperationUpdates)  return;
		
		PartnerLink partnerLink = ModelHelper.getPartnerLink(getInput());
		operationViewer.getControl().setVisible(true);
		PortType portType = getEffectivePortType(partnerLink);
		if (portType == null) {
			operationCombo.setEnabled(false);
		} else {
			operationCombo.setEnabled(true);
		}
		Operation operation = ModelHelper.getOperation(getInput());
		operationViewer.setInput(portType);
		refreshCCombo(operationViewer, operation);
		//TODO: changing operation here might affect which widgets should be shown.
		//I.e. if input is an Invoke.
		//rearrangeWidgets();
	}
	
	protected void updateReplyTypeWidgets() {
		if (replyTypeEnabled()) {
			boolean faultEnabled = (ModelHelper.getFaultName(getInput()) != null);
			normalRadio.setSelection(!faultEnabled);
			faultRadio.setSelection(faultEnabled);
			updateFaultNameWidgets();
			doChildLayout();
		}
	}
	
	protected void updateFaultNameWidgets() {
		if (faultNameEnabled()) {
			Operation operation = ModelHelper.getOperation(getInput());
			Fault fault = ModelHelper.getWSDLFault(getInput());
			faultNameViewer.setInput(operation);
			// TODO: find the proper value to select!
			refreshCCombo(faultNameViewer, fault);
		}
	}

	public void aboutToBeShown() {
		super.aboutToBeShown();
		doChildLayout();
	}
	
	public void refresh() {
		super.refresh();
		updatePartnerWidgets();
		updatePortTypeWidgets();
		updateOperationWidgets();
		updateReplyTypeWidgets();
		updateFaultNameWidgets();
	}

	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	public void restoreUserContext(Object userContext) {
		int i = ((Integer)userContext).intValue();
		switch (i) {
		case PARTNER_CONTEXT: partnerName.setFocus(); return;
		case OPERATION_CONTEXT: operationViewer.getCCombo().setFocus(); return;
		case NORMALRADIO_CONTEXT: normalRadio.setFocus(); return;
		case FAULTRADIO_CONTEXT: faultRadio.setFocus(); return;
		case FAULTNAME_CONTEXT: faultNameCombo.setFocus(); return;
		}
		throw new IllegalStateException();
	}

}
