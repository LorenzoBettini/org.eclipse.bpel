package org.eclipse.bpel.extensionssample.ui.properties;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.extensionsample.model.SampleSimpleActivity;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetCommand;
import org.eclipse.bpel.ui.dialogs.VariableSelectorDialog;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.BatchedMultiObjectAdapter;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/*
 * Bug 120110
 * This class implements the detail property tab for the "elemental" extension activity.
 * This property detail tab allows the user to select a variable in scope for this activity.
 * 
 * Note that validation of this activity is not yet implemented.
 */
public class SampleSimpleActivityPropertySection extends BPELPropertySection {

	protected Composite parentComposite;
	protected Label variableName;
	protected Button variableBrowseButton;

	private SampleSimpleActivity getActivity() {
		return (SampleSimpleActivity)getInput();
	}

	@Override
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new BatchedMultiObjectAdapter() {
				
				@Override
				public void notify (Notification n) {
				}
				
				@Override
				public void finish() {
					updateVariableWidgets();
				}
			}
		};
	}
	
	public class SetVariableCommand extends SetCommand {

		public String getDefaultLabel() { return IBPELUIConstants.CMD_SELECT_VARIABLE; }

		public SetVariableCommand(EObject target, Variable newVariable)  {
			super(target, newVariable);
		}

		@Override
		public Object get() {
			return ((SampleSimpleActivity)fTarget).getVariable();
		}
		@Override
		public void set(Object o) {
			((SampleSimpleActivity)fTarget).setVariable((Variable)o);
		}
	}

	@Override
	protected void createClient(Composite parent) {
		FlatFormData data;

		final Composite composite = parentComposite = createFlatFormComposite( createFlatFormComposite(parent) );
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(composite, IDetailsAreaConstants.VSPACE);
		composite.setLayoutData(data);

		Label variableLabel = fWidgetFactory.createLabel(composite, "Variable:"); 
		variableName = fWidgetFactory.createLabel(composite, "", SWT.NONE); //$NON-NLS-1$
		variableBrowseButton = fWidgetFactory.createButton(composite, Messages.FaultThrowNameSection_Browse_1, SWT.PUSH); 

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(variableLabel, STANDARD_LABEL_WIDTH_SM));
		data.right = new FlatFormAttachment(variableBrowseButton, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0, 0);
		data.height = FigureUtilities.getTextExtents(variableBrowseButton.getText(), variableBrowseButton.getFont()).height + 4;
		variableName.setLayoutData(data);

		data = new FlatFormData();
		data.top = new FlatFormAttachment(variableName, 0, SWT.TOP);
		data.bottom = new FlatFormAttachment(variableName, 2, SWT.BOTTOM);
		data.left = new FlatFormAttachment(50, -BPELUtil.calculateButtonWidth(variableBrowseButton, SHORT_BUTTON_WIDTH)-IDetailsAreaConstants.CENTER_SPACE);
		data.right = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE);
		variableBrowseButton.setLayoutData(data);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(variableName, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(variableName, 0, SWT.CENTER);
		variableLabel.setLayoutData(data);
		
		variableBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Shell shell = composite.getShell();
				EObject model = getInput();
				VariableSelectorDialog dialog = new VariableSelectorDialog(shell, model, ModelHelper.getVariableType(model, ModelHelper.OUTGOING));
				dialog.setTitle(Messages.FaultThrowNameSection_Select_Fault_Variable_2); 
				if (dialog.open() == Window.OK) {
					Variable variable = dialog.getVariable();
					Command command = new SetVariableCommand(model, variable);
					getCommandFramework().execute(wrapInShowContextCommand(command));
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
	}
	
	public void updateVariableWidgets() {
		if (getActivity() != null ) {
			Variable variable = getActivity().getVariable();
			if (variable!=null) {
				String name = variable.getName();
				if (name==null)
					name = "";
				variableName.setText(name);
			}
		}
	}
	
	@Override
	protected void basicSetInput(EObject newInput) {
		if ( newInput instanceof SampleSimpleActivity) {
			super.basicSetInput(newInput);
			updateVariableWidgets();
		}
	}
}
