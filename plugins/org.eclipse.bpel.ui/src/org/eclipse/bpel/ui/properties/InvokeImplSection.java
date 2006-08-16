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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.common.ui.assist.FieldAssistAdapter;
import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.details.viewers.CComboViewer;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.commands.AddPartnerLinkCommand;
import org.eclipse.bpel.ui.commands.AddVariableCommand;
import org.eclipse.bpel.ui.commands.SetCommand;
import org.eclipse.bpel.ui.commands.SetOnEventVariableTypeCommand;
import org.eclipse.bpel.ui.commands.SetOperationCommand;
import org.eclipse.bpel.ui.commands.SetPartnerLinkCommand;
import org.eclipse.bpel.ui.commands.SetVariableCommand;
import org.eclipse.bpel.ui.commands.SetWSDLFaultCommand;
import org.eclipse.bpel.ui.details.providers.AddNullFilter;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.details.providers.OperationContentProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkContentProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.PartnerRoleFilter;
import org.eclipse.bpel.ui.details.providers.VariableContentProvider;
import org.eclipse.bpel.ui.details.providers.VariableFilter;
import org.eclipse.bpel.ui.details.providers.WSDLFaultContentProvider;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.dialogs.PartnerLinkSelectorDialog;
import org.eclipse.bpel.ui.dialogs.PartnerLinkTypeSelectorDialog;
import org.eclipse.bpel.ui.proposal.providers.CommandProposal;
import org.eclipse.bpel.ui.proposal.providers.ModelContentProposalProvider;
import org.eclipse.bpel.ui.proposal.providers.RunnableProposal;
import org.eclipse.bpel.ui.proposal.providers.Separator;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ListMap;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.bpel.ui.util.NameDialog;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.IControlContentAdapter;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.wst.wsdl.Fault;
import org.eclipse.wst.wsdl.Input;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Output;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.WSDLFactory;

/**
 * Details section for the Partner/PortType/Operation properties of a
 * Receive/Reply/Invoke activity and OnMessage.
 */
public class InvokeImplSection extends BPELPropertySection {

	static final String NONE = Messages.InvokeImplSection_None_1;
	
	protected static final int PARTNER_CONTEXT = 0;
	protected static final int OPERATION_CONTEXT = 1;
	protected static final int NORMALRADIO_CONTEXT = 2;
	protected static final int FAULTRADIO_CONTEXT = 3;
	protected static final int FAULTNAME_CONTEXT = 4;
	
	protected int lastChangeContext = -1;

	protected Composite parentComposite;
	protected Composite partnerComposite, portTypeComposite, operationComposite;
	protected Composite replyTypeComposite, faultNameComposite;

	protected Label partnerLabel;
	protected Text partnerName;
	protected CComboViewer operationViewer;
	protected Label interfaceLabel, operationLabel;
	protected Hyperlink interfaceName;
	protected Button partnerBrowseButton;
	protected Text operationText;

	boolean blockOperationUpdates;

	protected Button normalRadio, faultRadio;
	protected CCombo faultNameCombo;
	protected CComboViewer faultNameViewer;
	
//	protected boolean[] variableEnabled = new boolean[2];
//	protected Button[] variableBrowseButton = new Button[2];
//	protected Label[] variableLabel = new Label[2];
//	protected Label[] variableName = new Label[2];
	protected boolean isInvoke;
	private Composite inputVariableComposite;
	private Composite outputVariableComposite;	
	private Label inputVariableLabel;
	private Text inputVariableText;
	private Label outputVariableLabel;
	private Text outputVariableText;
	
	private PartnerRoleFilter fPartnerRoleFilter = new PartnerRoleFilter();
	private VariableFilter fInputVariableFilter = new VariableFilter();
	private VariableFilter fOutputVariableFilter = new VariableFilter();
	
	private Label quickPickLabel;
	private Tree quickPickTree;
	private Composite quickPickComposite;
	private TreeViewer quickPickTreeViewer;
	private Composite faultComposite;
	private Label faultLabel;
	private Text faultText;
	
	private IControlContentAdapter fTextContentAdapter = new TextContentAdapter() {
		public void insertControlContents(Control control, String text, int cursorPosition) {
			if (text != null) {
				super.insertControlContents(control, text, cursorPosition);
			}
		}
		public void setControlContents(Control control, String text, int cursorPosition) {
			if (text != null) {
				super.setControlContents(control, text, cursorPosition);
			}
		}			
	};
	
	
	private Button inputVariableButton;
	private Button outputVariableButton;
	private Button faultButton;

	private Button operationButton;

	RunnableProposal fWSDLEditRunnableProposal;
		
	static final int SPLIT_POINT = 55;
	static final int SPLIT_POINT_OFFSET = 3 * IDetailsAreaConstants.HSPACE;
	
	
	static final PartnerLink IGNORE_PARTNER_LINK = BPELFactory.eINSTANCE.createPartnerLink();
	
	static final Operation IGNORE_OPERATION = WSDLFactory.eINSTANCE.createOperation();
	
	/** 
	 * Stretch this section to maximum use of space
	 */
	public boolean shouldUseExtraSpace () {
		return true;
	}
	
	
	public String labelWordFor(int direction) {
		if (isInvoke) {
			return (direction == ModelHelper.OUTGOING || direction == ModelHelper.NOT_SPECIFIED)? Messages.InvokeImplDetails_Request_3:Messages.InvokeImplDetails_Response_4; 
		}
		return (direction == ModelHelper.OUTGOING || direction == ModelHelper.NOT_SPECIFIED)? Messages.InvokeImplDetails_Response_4:Messages.InvokeImplDetails_Request_3;  
	}

	/**
	 * The same as labelWordFor(), except these strings don't contain mnemonics!
	 */
	public String plainLabelWordFor (int direction) {
		if (isInvoke) {
			return (direction == ModelHelper.OUTGOING || direction == ModelHelper.NOT_SPECIFIED)? Messages.InvokeImplDetails_Request_3_Plain:Messages.InvokeImplDetails_Response_4_Plain; 
		}
		return (direction == ModelHelper.OUTGOING || direction == ModelHelper.NOT_SPECIFIED)? Messages.InvokeImplDetails_Response_4_Plain:Messages.InvokeImplDetails_Request_3_Plain;  
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
							updateInputVariableWidgets();
							updateOutputVariableWidgets();
							updateFaultWidgets();
						} else if (ModelHelper.isOperationAffected(input, n)) {
							upp = true;
							updatePortTypeWidgets();
							updateOperationWidgets();
							updateInputVariableWidgets();
							updateOutputVariableWidgets();								
							updateFaultWidgets();
						} else {
							updateInputVariableWidgets();
							updateOutputVariableWidgets();								
							updateFaultWidgets();								
						}
						// Update the response variable widgets, because if the operation
						// is one-way, these widgets should be disabled.
						rearrangeWidgets();
												
						if (replyTypeEnabled() && ModelHelper.isFaultNameAffected(input, n)) {
							updateFaultWidgets();
							if (!upp) {
								updateFaultWidgets();
							}
						}
						
				    } catch (Exception e) {
						BPELUIPlugin.log(e);
				    }
				}
			},
		};
	}

	protected boolean replyTypeEnabled () {
		
		if ((getInput() instanceof Reply) == false) {
			return false;
		}
		return true;
	}
	
	
	protected boolean faultNameEnabled() {
		return replyTypeEnabled(); 
	}

	protected void doChildLayout() {
						
		// Only Invoke needs output variable				
		outputVariableComposite.setVisible( isInvoke );		
		faultComposite.setVisible( replyTypeEnabled() );		
		parentComposite.layout(true);
	}

	protected void basicSetInput(EObject input) {
		super.basicSetInput(input);	
		rearrangeWidgets();
	}
	
	protected void rearrangeWidgets() {
		
		Object input = getInput();		
		isInvoke = (input instanceof Invoke);
		
		fPartnerRoleFilter.setRequireMyRole( !isInvoke );
		fPartnerRoleFilter.setRequirePartnerRole( isInvoke );
		
		doChildLayout();
	}
	

	protected Composite createPartnerWidgets(Composite top, Composite parent) {
		
		FlatFormData data;
		
		final Composite composite = partnerComposite = createFlatFormComposite(parent);
		
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment(top, IDetailsAreaConstants.VSPACE);
		}		
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);		
		data.right = new FlatFormAttachment(SPLIT_POINT, -SPLIT_POINT_OFFSET);
		
		composite.setLayoutData(data);

		partnerLabel = wf.createLabel(composite, Messages.InvokeImplDetails_Partner__10); 
		partnerName = wf.createText(composite, "", SWT.NONE); //$NON-NLS-1$
		partnerBrowseButton = wf.createButton(composite,"",SWT.ARROW | SWT.DOWN | SWT.RIGHT ); //$NON-NLS-1$				

		// Content Assist for Partner Link

		RunnableProposal proposal = new RunnableProposal() {
			
			public String getLabel() {
				return Messages.InvokeImplSection_0;
			}
			public void run() {
				createPartnerLink ( ModelHelper.getProcess( getInput () ) , null );
			}			
		};

		RunnableProposal proposal2 = new RunnableProposal() {
			
			public String getLabel() {
				return Messages.InvokeImplSection_1;
			}
			public void run() {
				createPartnerLink ( ModelHelper.getContainingScope( getInput()), null);
			}			
		};

		RunnableProposal proposal3 = new RunnableProposal() {			
			public String getLabel() {
				return Messages.InvokeImplSection_2;
			}
			public void run() {				
				CompoundCommand cmd = new CompoundCommand();
				cmd.getCommands().addAll( basicCommandList( getInput() , null, null )) ;
				getCommandFramework().execute( cmd );
			}			
		};
		
		PartnerLinkContentProvider provider = new PartnerLinkContentProvider();
		ModelContentProposalProvider proposalProvider;
		proposalProvider = new ModelContentProposalProvider( new ModelContentProposalProvider.ValueProvider () {
			public Object value() {
				return getInput();
			}			
		}, provider,fPartnerRoleFilter );
		
		proposalProvider.addProposalToEnd( new Separator () );
		proposalProvider.addProposalToEnd( proposal );
		proposalProvider.addProposalToEnd( proposal2 );
		proposalProvider.addProposalToEnd( proposal3 );
		proposalProvider.addProposalToEnd(  getWSDLEdit() );
		
		final FieldAssistAdapter contentAssist = new FieldAssistAdapter (
				partnerName, 
				fTextContentAdapter, 
				proposalProvider, 
				null, 
				null );
		// 
		contentAssist.setLabelProvider( new ModelLabelProvider () );		
		contentAssist.setPopupSize( new Point(300,100) );
		contentAssist.setFilterStyle(ContentProposalAdapter.FILTER_CUMULATIVE);
		contentAssist.setProposalAcceptanceStyle( ContentProposalAdapter.PROPOSAL_REPLACE);
		contentAssist.addContentProposalListener( proposal );	
		contentAssist.addContentProposalListener( proposal2 );
		contentAssist.addContentProposalListener( proposal3 );
		contentAssist.addContentProposalListener( getWSDLEdit() );
		contentAssist.addContentProposalListener(new IContentProposalListener () {

			public void proposalAccepted(IContentProposal chosenProposal) {
				if (chosenProposal.getContent() == null) {
					return ;
				}
				PartnerLink pl = null;
				try {
					pl = (PartnerLink) ((Adapter)chosenProposal).getTarget();
				} catch (Throwable t) {
					return ;
				}
				CompoundCommand cmd = new CompoundCommand();
				cmd.getCommands().addAll( basicCommandList(getInput(),pl,null) );
				getCommandFramework().execute(cmd);
			}			
		});
		
		// End of Content Assist for variable
		partnerBrowseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				contentAssist.openProposals();
			}			
		});
		
		partnerName.addListener(SWT.KeyDown, new Listener () {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.CR) {
					findAndSetOrCreatePartnerLink( partnerName.getText() );
				}
			}        	
		});		
		// End of content assist for partner link
		

		data = new FlatFormData();
		data.right = new FlatFormAttachment(100, 0);		
		data.top = new FlatFormAttachment(partnerName,+2,SWT.TOP);
		data.bottom = new FlatFormAttachment(partnerName,-2,SWT.BOTTOM);		
		partnerBrowseButton.setLayoutData(data);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(partnerLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(partnerBrowseButton, 0);
		partnerName.setLayoutData(data);

		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(partnerName, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(partnerName, 0, SWT.CENTER);
		partnerLabel.setLayoutData(data);

		partnerBrowseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				contentAssist.openProposals();
			}			
		});
		
		return composite;
	}

	
	void browseForPartnerLink () {
		
		Shell shell = partnerLabel.getShell();
		EObject model = getInput();
		PartnerLinkSelectorDialog dialog = new PartnerLinkSelectorDialog(shell, model);
		if (dialog.open() == Window.OK) {
			PartnerLink partner = dialog.getPartnerLink();
			
			List cmds = basicCommandList( model , null, null );
			
			SetCommand cmd = (SetCommand) ListMap.Find(cmds, new ListMap.Visitor() {
				public Object visit(Object obj) {
					return (obj instanceof SetPartnerLinkCommand ? obj : ListMap.IGNORE );
				}				
			});
			
			cmd.setNewValue( partner );
			CompoundCommand ccmd = new CompoundCommand();
			ccmd.getCommands().addAll(cmds);
			lastChangeContext = PARTNER_CONTEXT;
			getCommandFramework().execute(wrapInShowContextCommand(ccmd));
		}
	}
	
	
	
	protected Composite createPortTypeWidgets(Composite top, Composite parent) {
		
		FlatFormData data;
		
		final Composite composite = portTypeComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment(top, IDetailsAreaConstants.VSPACE);
		}
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.right = new FlatFormAttachment(SPLIT_POINT, -SPLIT_POINT_OFFSET);		
		composite.setLayoutData(data);
		
		interfaceLabel = wf.createLabel(composite, Messages.InvokeImplSection_3); 
		interfaceName = wf.createHyperlink(composite, "", SWT.NONE); //$NON-NLS-1$
		interfaceName.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) { 
				PortType pt =  ModelHelper.getPortType(getInput());
				if (pt != null) {
					BPELUtil.openEditor( pt, getBPELEditor() );
				}
			}
		});
		
		interfaceName.setToolTipText(Messages.InvokeImplSection_4);
		
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(interfaceLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(100,0);
		interfaceName.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(interfaceName, 0);
		data.top = new FlatFormAttachment(interfaceName, 0, SWT.CENTER);
		interfaceLabel.setLayoutData(data);
		
		return composite;
	}

	
	protected Composite createOperationWidgets(Composite top, Composite parent) {
		FlatFormData data;
		
		final Composite composite = operationComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment(top,  IDetailsAreaConstants.VSPACE );
		}
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.right = new FlatFormAttachment(SPLIT_POINT, -SPLIT_POINT_OFFSET);
		composite.setLayoutData(data);
		
		operationLabel = wf.createLabel(composite, Messages.InvokeImplDetails_Operation__19); 
		operationText = wf.createText(composite,"",SWT.NONE);				 //$NON-NLS-1$
		operationButton = wf.createButton(composite,"",SWT.ARROW|SWT.CENTER|SWT.DOWN); //$NON-NLS-1$
			
//		operationText.addHyperlinkListener(new HyperlinkAdapter() {
//			public void linkActivated(HyperlinkEvent e) { 
//				PortType pt =  ModelHelper.getPortType(getInput());
//				if (pt != null) {
//					BPELUtil.openEditor( pt, getBPELEditor() );
//				}
//			}
//		});
		
//		operationText.setToolTipText("Click here to edit Operation.");
	
		
		// Provide Content Assist for the variables		
		OperationContentProvider provider = new OperationContentProvider();
		ModelContentProposalProvider proposalProvider;
		proposalProvider = new ModelContentProposalProvider( new ModelContentProposalProvider.ValueProvider () {
			public Object value() {
				return getInput();
			}			
		}, provider);
		
		proposalProvider.addProposalToEnd( new Separator () );
		proposalProvider.addProposalToEnd( getWSDLEdit() );
		
		final FieldAssistAdapter contentAssist = new FieldAssistAdapter (
				operationText, 
				fTextContentAdapter, 
				proposalProvider, 
				null, 
				null );
		// 
		contentAssist.setLabelProvider( new ModelLabelProvider () );		
		contentAssist.setPopupSize( new Point(300,100) );
		contentAssist.setFilterStyle(ContentProposalAdapter.FILTER_CUMULATIVE);
		contentAssist.setProposalAcceptanceStyle( ContentProposalAdapter.PROPOSAL_REPLACE);
		contentAssist.addContentProposalListener( getWSDLEdit() );	
	
		contentAssist.addContentProposalListener(new IContentProposalListener () {

			public void proposalAccepted(IContentProposal chosenProposal) {
				if (chosenProposal.getContent() == null) {
					return ;
				}
				Operation oper = null;
				try {
					oper = (Operation) ((Adapter)chosenProposal).getTarget();
				} catch (Throwable t) {
					return ;
				}
				List list = basicCommandList(getInput(), IGNORE_PARTNER_LINK , oper );
				CompoundCommand cmd = new CompoundCommand();
				cmd.getCommands().addAll (list);				
				getCommandFramework().execute( cmd );
			}			
		});
		
		// End of Content Assist for variable
		
		operationButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				contentAssist.openProposals();
			}			
		});
		
		operationText.addListener(SWT.KeyDown, new Listener () {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.CR) {
					findAndSetOperation ( operationText.getText() );
				}
			}        	
		});
		
		// end of content assist
		

		data = new FlatFormData();		
		data.right = new FlatFormAttachment(100,0);
		data.top = new FlatFormAttachment(operationText,+2,SWT.TOP);
		data.bottom = new FlatFormAttachment(operationText,-2,SWT.BOTTOM);
		operationButton.setLayoutData(data);
		

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(operationLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(operationButton, 0);
		operationText.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(operationText, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(operationText, 0, SWT.CENTER);
		operationLabel.setLayoutData(data);

		
		
//		operationViewer.addFilter(AddNullFilter.getInstance());			
//		operationViewer.setLabelProvider(new ModelLabelProvider());
//		operationViewer.setContentProvider(new OperationContentProvider());
//		operationViewer.setSorter(ModelViewerSorter.getInstance());
//		operationViewer.addSelectionChangedListener(new ISelectionChangedListener() {
//			public void selectionChanged(SelectionChangedEvent event) {
//				IStructuredSelection sel = (IStructuredSelection)operationViewer.getSelection();
//				// TODO: if changing to a one-way operation, clear the response variable (if any)
//				PartnerLink partnerLink = ModelHelper.getPartnerLink(getInput());
//
//				CompoundCommand cmd = new CompoundCommand();
//				Command child = null;
//				PortType portType = getEffectivePortType(partnerLink);
//				Operation op = (Operation)sel.getFirstElement();
//				
//				if (op == null)  portType = null;
//				
//				child = new SetPortTypeCommand(getInput(), portType);
//				if (child.canExecute())  cmd.add(child);
//				
//				child = new SetOperationCommand(getInput(), op);
//				if (child.canExecute()) {
//					if (replyTypeEnabled()) {
//						Command setFaultCommand = new SetWSDLFaultCommand(getInput(), null);
//						if (setFaultCommand.canExecute())  cmd.add(setFaultCommand);
//					}
//					cmd.add(child); cmd.setLabel(child.getLabel());
//				} 
//
//				if (getInput() instanceof OnEvent) {
//					child = new SetOnEventVariableTypeCommand((OnEvent)getInput());
//					cmd.add(child);
//				}
//				blockOperationUpdates = true;
//				lastChangeContext = OPERATION_CONTEXT;
//				try {
//					getCommandFramework().execute(wrapInShowContextCommand(cmd));
//				} finally {
//					blockOperationUpdates = false;
//				}
//			}
//		});
		
		return composite;
	}
	
	
	
	protected Composite createInputVariableWidgets(Composite top, Composite parent) {
		FlatFormData data;
		
		final Composite composite = inputVariableComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment(top, IDetailsAreaConstants.VSPACE);
		}		
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.right = new FlatFormAttachment(SPLIT_POINT, -SPLIT_POINT_OFFSET);
		composite.setLayoutData(data);
		
		inputVariableLabel = wf.createLabel(composite, Messages.InvokeImplSection_7); 
		inputVariableText = wf.createText(composite,""); //$NON-NLS-1$
		inputVariableButton = wf.createButton(composite, "", SWT.ARROW|SWT.DOWN|SWT.CENTER); //$NON-NLS-1$
				
		// Provide Content Assist for the variables
		// Content assist on partnerName
		RunnableProposal proposal = new RunnableProposal() {
			
			public String getLabel() {
				return Messages.InvokeImplSection_10;
			}
			public void run() {
				createVariable (  
						ModelHelper.getProcess( getInput () ),
						null,
						isInvoke ? ModelHelper.OUTGOING : ModelHelper.INCOMING);
			}			
		};

		RunnableProposal proposal2 = new RunnableProposal() {
			
			public String getLabel() {
				return Messages.InvokeImplSection_11;
			}
			public void run() {
				createVariable ( 
					getInput (),
					null,
					isInvoke ? ModelHelper.OUTGOING : ModelHelper.INCOMING);
			}			
		};

		RunnableProposal proposal3 = new RunnableProposal() {			
			public String getLabel() {
				return Messages.InvokeImplSection_12;
			}
			public void run() {
				int direction = isInvoke ? ModelHelper.OUTGOING : ModelHelper.INCOMING;
				getCommandFramework().execute( new SetVariableCommand(getInput(),null,direction) );
			}			
		};
		
		VariableContentProvider provider = new VariableContentProvider();
		ModelContentProposalProvider proposalProvider;
		proposalProvider = new ModelContentProposalProvider( new ModelContentProposalProvider.ValueProvider () {
			public Object value() {
				return getInput();
			}			
		}, provider,fInputVariableFilter);
		
		proposalProvider.addProposalToEnd( new Separator () );
		proposalProvider.addProposalToEnd( proposal );
		proposalProvider.addProposalToEnd( proposal2 );
		proposalProvider.addProposalToEnd( proposal3 );
		
		final FieldAssistAdapter contentAssist = new FieldAssistAdapter (
				inputVariableText, 
				fTextContentAdapter, 
				proposalProvider, 
				null, 
				null );
		// 
		contentAssist.setLabelProvider( new ModelLabelProvider () );		
		contentAssist.setPopupSize( new Point(300,100) );
		contentAssist.setFilterStyle(ContentProposalAdapter.FILTER_CUMULATIVE);
		contentAssist.setProposalAcceptanceStyle( ContentProposalAdapter.PROPOSAL_REPLACE);
		contentAssist.addContentProposalListener( proposal );	
		contentAssist.addContentProposalListener( proposal2 );
		contentAssist.addContentProposalListener( proposal3 );
		contentAssist.addContentProposalListener(new IContentProposalListener () {

			public void proposalAccepted(IContentProposal chosenProposal) {
				if (chosenProposal.getContent() == null) {
					return ;
				}
				Variable variable = null;
				try {
					variable = (Variable) ((Adapter)chosenProposal).getTarget();
				} catch (Throwable t) {
					return ;
				}
				SetVariableCommand cmd = new SetVariableCommand(getInput(),variable);
				getCommandFramework().execute( cmd );
			}			
		});
		
		// End of Content Assist for variable
		inputVariableButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				contentAssist.openProposals();
			}			
		});
		
		inputVariableText.addListener(SWT.KeyDown, new Listener () {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.CR) {
					findAndSetOrCreateVariable( inputVariableText.getText(),
							isInvoke ? ModelHelper.OUTGOING : ModelHelper.INCOMING );
				}
			}        	
		});
		
		data = new FlatFormData();
		data.right = new FlatFormAttachment(100,0);
		data.top = new FlatFormAttachment(inputVariableText,+2,SWT.TOP);
		data.bottom = new FlatFormAttachment(inputVariableText,-2,SWT.BOTTOM);
		inputVariableButton.setLayoutData(data);
	
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(operationLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(inputVariableButton, 0);
		inputVariableText.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(inputVariableText, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(inputVariableText, 0, SWT.CENTER);
		inputVariableLabel.setLayoutData(data);
		
		return composite;

		
	}

	/**
	 * The output variable widgets are only pertaining to Invoke activity, if there is
	 * an output message on the partner link
	 * 
	 * @param top
	 * @param parent
	 * @return
	 */
	
	protected Composite createOutputVariableComposite (Composite top, Composite parent) {
		FlatFormData data;
		
		final Composite composite = outputVariableComposite = createFlatFormComposite(parent);
		
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment( top, IDetailsAreaConstants.VSPACE);
		}		
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.right = new FlatFormAttachment(SPLIT_POINT, -SPLIT_POINT_OFFSET);
		composite.setLayoutData(data);
		
		outputVariableLabel = wf.createLabel(composite, Messages.InvokeImplSection_13); 
		outputVariableText = wf.createText(composite,""); //$NON-NLS-1$
		outputVariableButton = wf.createButton(composite, "", SWT.ARROW|SWT.DOWN|SWT.CENTER); //$NON-NLS-1$
				
		// Provide Content Assist for the operation
		
		// Runnable proposal.
		RunnableProposal proposal = new RunnableProposal() {
			
			public String getLabel() {
				return Messages.InvokeImplSection_16;
			}
			public void run() {
				createVariable ( ModelHelper.getProcess( getInput () ),null,ModelHelper.INCOMING );
			}			
		};

		RunnableProposal proposal2 = new RunnableProposal() {			
			public String getLabel() {
				return "Create Local Output Variable"; //$NON-NLS-1$
			}
			public void run() {
				createVariable (getInput (),null,ModelHelper.INCOMING);
			}			
		};


		RunnableProposal proposal3 = new RunnableProposal() {			
			public String getLabel() {
				return "Clear Output Variable"; //$NON-NLS-1$
			}
			public void run() {
				getCommandFramework().execute( new SetVariableCommand(getInput(),null,ModelHelper.INCOMING) );
			}			
		};
		
		VariableContentProvider provider = new VariableContentProvider();
		ModelContentProposalProvider proposalProvider;
		proposalProvider = new ModelContentProposalProvider( new ModelContentProposalProvider.ValueProvider () {
			public Object value() {
				return getInput();
			}			
		}, provider,fOutputVariableFilter);
		
		proposalProvider.addProposalToEnd( new Separator () );
		proposalProvider.addProposalToEnd( proposal );
		proposalProvider.addProposalToEnd( proposal2 );
		proposalProvider.addProposalToEnd( proposal3 );
		
		final FieldAssistAdapter contentAssist = new FieldAssistAdapter (
				outputVariableText, 
				fTextContentAdapter, 
				proposalProvider, 
				null, 
				null );
		// 
		contentAssist.setLabelProvider( new ModelLabelProvider () );		
		contentAssist.setPopupSize( new Point(300,100) );
		contentAssist.setFilterStyle(ContentProposalAdapter.FILTER_CUMULATIVE);
		contentAssist.setProposalAcceptanceStyle( ContentProposalAdapter.PROPOSAL_REPLACE );
		contentAssist.addContentProposalListener( proposal );
		contentAssist.addContentProposalListener( proposal2 );
		contentAssist.addContentProposalListener( proposal3 );
		contentAssist.addContentProposalListener(new IContentProposalListener () {

			public void proposalAccepted (IContentProposal chosenProposal) {
				if (chosenProposal.getContent() == null) {
					return ;
				}
				Variable variable = null;
				try {
					variable = (Variable) ((Adapter)chosenProposal).getTarget();
				} catch (Throwable t) {
					return ;
				}
				SetVariableCommand cmd = new SetVariableCommand(getInput(),variable, ModelHelper.INCOMING );
				getCommandFramework().execute( cmd );
			}			
		});
		
		// End of Content Assist for operation
		
		outputVariableButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				contentAssist.openProposals();
			}
		});
		outputVariableText.addListener(SWT.KeyDown, new Listener () {
			public void handleEvent(Event event) {
				if (event.keyCode == SWT.CR) {
					findAndSetOrCreateVariable( outputVariableText.getText(),ModelHelper.INCOMING );
				}
			}        	
		});

		data = new FlatFormData();
		data.right = new FlatFormAttachment(100,0);
		data.top = new FlatFormAttachment(outputVariableText,+2,SWT.TOP);
		data.bottom = new FlatFormAttachment(outputVariableText,-2,SWT.BOTTOM);
		outputVariableButton.setLayoutData(data);
			
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(operationLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(outputVariableButton,0);
		outputVariableText.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(outputVariableText, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(outputVariableText, 0, SWT.CENTER);
		outputVariableLabel.setLayoutData(data);
		
		return composite;
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
	
	
	protected Composite createFaultComposite (Composite top, Composite parent) {
		FlatFormData data;
		
		final Composite composite = faultComposite = createFlatFormComposite(parent);
		
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment( top, IDetailsAreaConstants.VSPACE);
		}		
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.right = new FlatFormAttachment(SPLIT_POINT, -SPLIT_POINT_OFFSET);
		composite.setLayoutData(data);
		
		faultLabel = wf.createLabel(composite,  Messages.InvokeImplDetails_Fault_Name__25); 
		faultText = wf.createText(composite,""); //$NON-NLS-1$
		faultButton = wf.createButton(composite, "", SWT.ARROW|SWT.DOWN|SWT.CENTER); //$NON-NLS-1$
		// Provide Content Assist for the operation
		
		
		WSDLFaultContentProvider provider = new WSDLFaultContentProvider();
		ModelContentProposalProvider proposalProvider;
		proposalProvider = new ModelContentProposalProvider( new ModelContentProposalProvider.ValueProvider () {
			public Object value() {
				return ModelHelper.getOperation( getInput() );
			}			
		}, provider);
		
		proposalProvider.addProposalToEnd( new Separator () );
				
		final FieldAssistAdapter contentAssist = new FieldAssistAdapter (
				faultText, 
				fTextContentAdapter, 
				proposalProvider, 
				null, 
				null );
		// 
		contentAssist.setLabelProvider( new ModelLabelProvider () );		
		contentAssist.setPopupSize( new Point(300,100) );
		contentAssist.setFilterStyle(ContentProposalAdapter.FILTER_CUMULATIVE);
		contentAssist.setProposalAcceptanceStyle( ContentProposalAdapter.PROPOSAL_REPLACE );
		// End of Content Assist for operation
		
		faultButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				contentAssist.openProposals();				
			}			
		});
		
		data = new FlatFormData();
		data.right = new FlatFormAttachment(100,0);
		data.top = new FlatFormAttachment(faultText,+2,SWT.TOP);
		data.bottom = new FlatFormAttachment(faultText,-2,SWT.BOTTOM);
		faultButton.setLayoutData(data);
					
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(operationLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(faultButton,0);
		faultText.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(faultText, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(faultText, 0, SWT.CENTER);
		faultLabel.setLayoutData(data);
		
		return composite;
	}
	
	protected Composite createFaultNameWidgets(Composite top, Composite parent) {
		FlatFormData data;

		Composite composite = faultNameComposite = createFlatFormComposite(parent);
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment (0,IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment (top,IDetailsAreaConstants.VSPACE);
		}		
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.right = new FlatFormAttachment(SPLIT_POINT, -SPLIT_POINT_OFFSET);
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
		
		return composite;
	}

	
	protected Composite createQuickPick (Composite top, Composite parent) 
	{
		FlatFormData data;
		
		final Composite composite = quickPickComposite = createFlatFormComposite(parent);
		
		data = new FlatFormData();
		if (top == null) {
			data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		} else {
			data.top = new FlatFormAttachment(top,IDetailsAreaConstants.VSPACE);
		}
		
		data.left = new FlatFormAttachment(SPLIT_POINT, SPLIT_POINT_OFFSET);		
		data.right = new FlatFormAttachment(100, -IDetailsAreaConstants.HSPACE);
		data.bottom = new FlatFormAttachment(100,-IDetailsAreaConstants.HSPACE);
		composite.setLayoutData(data);
				
		quickPickLabel = wf.createLabel(composite, "Quick Pick:");  //$NON-NLS-1$
				
		// Tree viewer for variable structure ...
		quickPickTree = wf.createTree(composite, SWT.NONE);
		PartnerLinkTreeContentProvider treeContentProvider = new PartnerLinkTreeContentProvider(true);
		quickPickTreeViewer = new TreeViewer(quickPickTree);
		quickPickTreeViewer.setContentProvider(treeContentProvider);
		quickPickTreeViewer.setLabelProvider(new ModelTreeLabelProvider());
		quickPickTreeViewer.addFilter( fPartnerRoleFilter );
		quickPickTreeViewer.setInput ( null );
		quickPickTreeViewer.setAutoExpandLevel(3);
		// end tree viewer for variable structure
		
		data = new FlatFormData();
		data.top = new FlatFormAttachment ( 0,0);
		data.left = new FlatFormAttachment(0,0);
		quickPickLabel.setLayoutData(data);
		
		data = new FlatFormData();
		data.top = new FlatFormAttachment( quickPickLabel, IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.right = new FlatFormAttachment(100,-IDetailsAreaConstants.HSPACE);
		data.bottom = new FlatFormAttachment(100,-IDetailsAreaConstants.HSPACE);
		quickPickTree.setLayoutData(data);
				
		quickPickTreeViewer.addSelectionChangedListener( new ISelectionChangedListener () {

			public void selectionChanged(SelectionChangedEvent event) {
				quickPickSelectionChanged ( event.getSelection() );
				
			}
			
		});
		return composite;
	}
	
	protected void createClient(Composite parent)  {
		
		Composite composite = parentComposite = createFlatFormComposite(parent);

		
		Composite ref = createPartnerWidgets(null,composite);
		ref = createPortTypeWidgets(ref, composite);
		ref = createOperationWidgets(ref,composite);
		ref = createInputVariableWidgets(ref,composite);
		ref = createOutputVariableComposite(ref,composite);
		ref = createFaultComposite ( ref, composite );
		
		
		// This creates it on the top
		ref = createQuickPick(null,composite);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parentComposite, IHelpContextIds.PROPERTY_PAGE_INVOKE_IMPLEMENTATION);
	}
	
	protected void updatePartnerWidgets() {
				
		PartnerLink partnerLink = ModelHelper.getPartnerLink(getInput());
		if (partnerLink == null) {
			partnerName.setText(EMPTY_STRING); 			
		} else {
			ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(partnerLink, ILabeledElement.class);
			partnerName.setText(labeledElement.getLabel(partnerLink));			
		}
	}
	
	protected void updateInputVariableWidgets () {
		
		if (isInvoke) {
			inputVariableLabel.setText(Messages.InvokeImplSection_22);
		} else {
			inputVariableLabel.setText(Messages.InvokeImplSection_23);
		}
		
		Variable inputVar = ModelHelper.getVariable(getInput(), 
				isInvoke ? ModelHelper.OUTGOING : ModelHelper.INCOMING);
		
		if (inputVar != null) {
			inputVariableText.setText( inputVar.getName() );
		} else {
			inputVariableText.setText(EMPTY_STRING);
		}
		
		//Figure out the type of the message.		
		fInputVariableFilter.clear(); 
		Object type = ModelHelper.getVariableType( getInput(), isInvoke ? ModelHelper.OUTGOING : ModelHelper.INCOMING );
		if (type != null && type instanceof Message) {
			fInputVariableFilter.setType( (Message) type );
		}		
	}
	
	protected void updateOutputVariableWidgets () {
		
		outputVariableComposite.setVisible( isInvoke );
		
		if ( !isInvoke ) {			
			return ;
		}				
		
		Variable outputVar = ModelHelper.getVariable(getInput(), ModelHelper.INCOMING);
		if (outputVar != null) {
			outputVariableText.setText(outputVar.getName());
		} else {
			outputVariableText.setText(EMPTY_STRING);
		}
		
		//Figure out the type of the message.		
		fOutputVariableFilter.clear(); 
		Object type = ModelHelper.getVariableType( getInput(), ModelHelper.INCOMING );
		if (type != null) {
			if (type instanceof Message) {
				fOutputVariableFilter.setType( (Message) type );
			}
		}		
	}
	
	
	// TODO: move these to ModelHelper?
	
	PortType getEffectivePortType (PartnerLink partnerLink) {
		if (partnerLink != null) {
			Role role = isInvoke? partnerLink.getPartnerRole() : partnerLink.getMyRole();
			return ModelHelper.getRolePortType(role);
		}
		return null;		
	}

	protected void updateQuickPickWidgets () {
		Object myInput = getInput();
		if ( myInput != quickPickTreeViewer.getInput() ) {
			quickPickTreeViewer.setInput( myInput );			
			if (myInput != null) {
				quickPickTree.getVerticalBar().setSelection(0);
			}						
		}
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
		
		Operation operation = ModelHelper.getOperation(getInput());
		if (operation != null) {
			operationText.setText( operation.getName() );
		} else {
			operationText.setText ( EMPTY_STRING );
		}
	}
	
	protected void updateFaultWidgets () {
		
	}

	public void aboutToBeShown() {
		super.aboutToBeShown();
		doChildLayout();
	}
	
	public void refresh() {
		super.refresh();
		
		updateQuickPickWidgets();
		updatePartnerWidgets();
		updatePortTypeWidgets();
		updateOperationWidgets();
		updateInputVariableWidgets();
		updateOutputVariableWidgets();
		updateFaultWidgets();		
	}

	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	
	public void restoreUserContext(Object userContext) {
		
		if (false) {
			return;
		}
		
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


	
	void findAndSetOperation (String text) {
		
		text = text.trim();
		EObject model = getInput();
		PortType portType = ModelHelper.getPortType( model );
		
		List cmdList = basicCommandList( model , IGNORE_PARTNER_LINK, null );		
		
		if (text.length() > 0) {
			
			Operation op = (Operation) ModelHelper.findElementByName(
					portType , 
					text,
					Operation.class);
			
			if (op != null) {			
				// set that operation
				SetOperationCommand cmd = (SetOperationCommand) ListMap.Find(cmdList, new ListMap.Visitor () {
					public Object visit(Object obj) {
						return (obj instanceof SetOperationCommand ? obj : ListMap.IGNORE );
					}						
				});
				cmd.setNewValue(op);
			} 		
		}
		
		CompoundCommand cmd = new CompoundCommand();
		cmd.getCommands().addAll( cmdList );
		getCommandFramework().execute ( cmd );
	}

	void findAndSetOrCreateVariable (String text, int direction) {
		
		text = text.trim();
		EObject model = getInput();
		
		SetVariableCommand cmd = new SetVariableCommand(getInput(),null,direction);
		if (text.length() > 0) {
			Variable variable = (Variable) ModelHelper.findElementByName(ModelHelper.getContainingScope(model), 
					text, Variable.class);			
			if (variable == null) {
			
				createVariable (getInput(), text,direction );						
				return ;			
			} 
			
			cmd.setNewValue( variable );
		}
		
		getCommandFramework().execute ( cmd );
	}
	
	/**
	 * Create an input variable, set it to the right type, and set the input variable
	 * pon the activity. 
	 * 
	 * @param ref the object on which to create the variable (Scope or Process)
	 * @param name the name of the variable, or null
	 * @param the direction of the variable
	 */
	
	void createVariable ( EObject ref, String name,  int direction ) {
		
		Variable variable = BPELFactory.eINSTANCE.createVariable();
		
		if (name == null) {
			name = plainLabelWordFor( direction );
		}

		// ask for the name, we know the type.
		NameDialog nameDialog = new NameDialog( 
				inputVariableComposite.getShell(), 
				Messages.VariableSelectorDialog_New_Variable_4, 
				Messages.VariableSelectorDialog_Variable_Name_5, 
				name, 
				BPELUtil.getNCNameValidator());
		
		if (nameDialog.open() == Window.CANCEL) {
			return ;
		}		
		
		// set name and type
		variable.setName ( nameDialog.getValue() );
		
		Object type = ModelHelper.getVariableType( getInput(), direction );
		if (type != null && type instanceof Message) {
			variable.setMessageType ( (Message) type);
		}		
		
		// create the variable and then set the input variable to it.
		CompoundCommand cmd = new CompoundCommand();
		cmd.add ( new AddVariableCommand(ref,variable) );
		cmd.add ( new SetVariableCommand(getInput(), variable, direction) );
		
		getCommandFramework().execute( cmd );				
	}
	
	
	void findAndSetOrCreatePartnerLink ( String name ) {
		name = name.trim();
		EObject model = getInput();
					
		PartnerLink pl = null;
		if (name.length() > 0) {
			
			pl = (PartnerLink) ModelHelper.findElementByName(ModelHelper.getContainingScope(model),
					name, PartnerLink.class);
			// does not exist
			if (pl == null) {			
				createPartnerLink ( ModelHelper.getContainingScope(model), name );						
				return ;			
			}			
		}
		
		CompoundCommand cmd = new CompoundCommand();
		cmd.getCommands().addAll( basicCommandList(model, pl, null));
		getCommandFramework().execute ( cmd );		
		
	}
	
	void createPartnerLink ( EObject ref , String name ) {
		PartnerLink pl = BPELFactory.eINSTANCE.createPartnerLink();
		
		if (name == null) {
			name = EMPTY_STRING;
		}

		// ask for the name, we know the type.
		NameDialog nameDialog = new NameDialog( 
				inputVariableComposite.getShell(), 
				Messages.PartnerLinkSelectorDialog_5, 
				Messages.PartnerLinkSelectorDialog_6, 
				name, 
				BPELUtil.getNCNameValidator());
		
		if (nameDialog.open() == Window.CANCEL) {
			return ;
		}		
		
		PartnerLinkTypeSelectorDialog dialog = new PartnerLinkTypeSelectorDialog(
				partnerName.getShell(),
				getInput());
		if (dialog.open() == Window.CANCEL) {
			return ;
		}
		Object result = dialog.getFirstResult();
		PartnerLinkType plt = null;
		if (result != null && result instanceof PartnerLinkType) {
			plt = (PartnerLinkType) result;
		}
		
		// set name and type
		pl.setName ( nameDialog.getValue() );
		pl.setPartnerLinkType( plt );
		
		// ask for partner link type
		
		List cmds = basicCommandList(getInput(), pl, null);
		cmds.add(0, new AddPartnerLinkCommand( ref, pl ));
		//
		CompoundCommand cmd = new CompoundCommand();
		cmd.getCommands().addAll(cmds);
		getCommandFramework().execute(cmd);					
	}
	
	/** 
	 * Handle the quick pick from the partner link tree.
	 * 
	 * The logic here will attempt to fill in as much of the details 
	 * as possible. It may create variables necessary to for the partner 
	 * activity to make sense.
	 * 
	 * @param selection the selection from the tree
	 */
	
	void quickPickSelectionChanged ( ISelection selection ) {
		
		if (selection.isEmpty()) {
			return ;
		}		
		ITreeSelection treeSelection = (ITreeSelection) selection;		
		quickPickSelectionChanged ( treeSelection.getPaths() );
	}
	
	
	void quickPickSelectionChanged ( TreePath[] paths ) {
	
		// Assumption is that we are single selection ...
		if (paths.length > 0) {
			quickPickSelectionChanged ( paths[0] );
		}
	}
	
	
	
	void quickPickSelectionChanged ( TreePath path ) {				
	
		// The tree view contains nodes which may have multiple parents
		// so we have to walk the selection using the visual elements
		
//		org.eclipse.bpel.ui.details.tree.PartnerLinkTreeNode@3905e3
//		org.eclipse.bpel.ui.details.tree.PortTypeTreeNode@13d765c
//		org.eclipse.bpel.ui.details.tree.OperationTreeNode@fcc070
//		org.eclipse.bpel.ui.details.tree.MessageTypeTreeNode@1167d36
//		org.eclipse.bpel.ui.details.tree.PartTreeNode@33910a
//		org.eclipse.bpel.ui.details.tree.XSDElementDeclarationTreeNode@1e96ffd
//		
		
		EObject input = getInput();
		List cmdList = basicCommandList( input , null, null);
		
		PartnerLink pl = null;
		Operation op = null;		
		SetCommand setCommand = null;
									
		for (int i=0,j=path.getSegmentCount(); i < j; i++) {
			
			Object model = null;
			try {
				model = ((ITreeNode) path.getSegment(i)).getModelObject();
			} catch (Exception ex) {				
				// should not happen
			    BPELUIPlugin.log(ex);
				break;
			}
									
			if (model instanceof PartnerLink) {
							
				pl = (PartnerLink) model;
				setCommand = (SetCommand) ListMap.Find ( cmdList, new ListMap.Visitor() {
					public Object visit(Object obj) {
						return (obj instanceof SetPartnerLinkCommand ? obj : ListMap.IGNORE);						
					}										
				});
				setCommand.setNewValue( pl );
				
			} else if (model instanceof PortType) {
				
				// we don't do anything here ...
				
			} else if (model instanceof Operation) {			
				
				op = (Operation) model;
				
				setCommand = (SetCommand) ListMap.Find ( cmdList, new ListMap.Visitor() {
					public Object visit(Object obj) {
						return (obj instanceof SetOperationCommand ? obj : ListMap.IGNORE);						
					}										
				});
				setCommand.setNewValue( op );
				
				// attempt to locate a variable matching the type
				alterCommands (cmdList,input,pl,op,(Input) op.getInput()  );
				alterCommands (cmdList,input,pl,op,(Output)op.getOutput() );
												
			} else { 
				break;
			}			
			// System.out.println( "segment[" + i + "]=" + path.getSegment( i ));
		}

		CompoundCommand cmd = new CompoundCommand ();
		cmd.getCommands().addAll (cmdList) ;
		getCommandFramework().execute( cmd );
	}
	

	
	void alterCommands (List list, EObject input, PartnerLink pl , Operation op, Input msg ) {
		
		if (input instanceof Receive || input instanceof OnMessage || 
			input instanceof OnEvent || input instanceof Reply)
		{  		
			if (pl.getMyRole() == null || msg == null) {
				return ;
			}			
			alterCommands(list, input, msg.getEMessage() , pl);
		}					
			
		if (input instanceof Invoke) {			
			if (pl.getPartnerRole() == null || msg == null) {
				return ;
			}				
			alterCommands(list, input,msg.getEMessage(),pl);
		}							
	}
	
		
	void alterCommands (List cmds, EObject input, PartnerLink pl, Operation op, Output msg) {
		if (input instanceof Reply) {
			if (pl.getMyRole() == null || msg == null) {
				return ;
			}
			alterCommands(cmds,input,msg.getEMessage(),pl);
		}
		if (input instanceof Invoke) {
			if (pl.getPartnerRole() == null || msg == null) {
				return;
			}
			alterCommands(cmds,input,msg.getEMessage(),pl,ModelHelper.INCOMING);
		}				
	}
	
	
	void alterCommands ( List cmds, EObject input, Message msg, PartnerLink pl) {
		alterCommands ( cmds, input,msg,pl, ModelHelper.NOT_SPECIFIED);
	}
	
	
	void alterCommands (List cmds, EObject input, Message msg, PartnerLink pl, final int direction) {			
								 
		Variable variable = findVariable(input, msg, pl);
		
		if (variable == null) {
			// no such variable, create one
			variable = BPELFactory.eINSTANCE.createVariable();
			String name = pl.getName() + plainLabelWordFor(direction);			
			variable.setName ( name );			
			variable.setMessageType( msg );
			cmds.add(0, new AddVariableCommand(input, variable));
		}
		
		SetVariableCommand cmd = (SetVariableCommand) ListMap.Find(cmds, new ListMap.Visitor() {
			public Object visit(Object obj) {
				if (obj instanceof SetVariableCommand) {
					SetVariableCommand svc = (SetVariableCommand) obj;
					if (svc.getDirection() == direction) {
						return svc;
					}
				}
				return ListMap.IGNORE;
			}			
		});
						
		cmd.setNewValue( variable );							
	}
		


	/**
	 * Find and select an appropriate variable based on the Message type passed.
	 * The partner link is passed to be used a heuristic in the name search to decided
	 * between several variables that match the requested type.
	 * 
	 * @param input the input element
	 * @param msg the message whose variable type we have to find.
	 * @param pl partner link, optional
	 * @return the chosen variable or null
	 */
	
	Variable findVariable ( EObject input, Message msg , PartnerLink pl ) {
		
		Variable list[] = ModelHelper.getVariablesOfType( input, msg );			
				
		if ( list.length == 1 ) {
			return list[0];			
		}
		
		if ( list.length > 0 ) {
			
			if (pl == null) {
				return list[0];
			}
			
			// apply a simple heuristic based on having the partner link in the name.
			String plName = pl.getName();
			for(int i=0; i < list.length; i++) {
				if ( list[i].getName().indexOf( plName ) >= 0) {
					return list[i];
				}
			}
			// nothing matched better on name
			return list[0];
		}			
		
		// can't find anything matching.
		return null;	
	}
	
	
	/**
	 * Return the basic command list for attempting to manipulate the partner activity.
	 * The list returned must be ordered, in the sense that the commands with the most 
	 * significance are done first. All the commands will generally be executed, the idea
	 * being that if only partner link is selected, then the reset of the attributes of the
	 * partner activity will be reset. 
	 * 
	 * @param input 
	 * @return the list of basic commands that are used to manipulate the partner activity
	 */
		
	
	List basicCommandList (EObject input, PartnerLink pl, Operation op) {
		List list = new ArrayList(8);
	
		
		if (pl != IGNORE_PARTNER_LINK) {
			list.add ( new SetPartnerLinkCommand(input, pl) );
		}
		
		if (op != IGNORE_OPERATION) {
			list.add ( new SetOperationCommand(input, op) );
		}
		
		// These are leaf commands, that can be executed on their own
		list.add ( new SetVariableCommand(input,null) );		
		
		if (input instanceof OnEvent) {
			list.add( new SetOnEventVariableTypeCommand((OnEvent)input) );
		} else if (input instanceof Reply) {
			list.add ( new SetWSDLFaultCommand (input, null) );
		} else if (input instanceof Invoke) {
			list.add ( new SetVariableCommand (input, null, ModelHelper.INCOMING) );
		}
		return list;
	}
	
	/**
	 * This shows up in a couple of places, so we share it.
	 * @return the runnable proposal for WSDL editing.
	 */
	
	RunnableProposal getWSDLEdit ( ) {
		
		if (fWSDLEditRunnableProposal == null) {
			fWSDLEditRunnableProposal = new RunnableProposal() {				
				public String getLabel() {
					return Messages.InvokeImplSection_24;
				}
				public void run() {
					PortType pt =  ModelHelper.getPortType(getInput());
					if (pt != null) {
						BPELUtil.openEditor( pt, getBPELEditor() );
					}
				}			
			};
		}
		return fWSDLEditRunnableProposal;
	}
	
}
