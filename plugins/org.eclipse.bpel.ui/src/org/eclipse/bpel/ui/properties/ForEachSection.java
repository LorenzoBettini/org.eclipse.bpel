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
import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetForEachIsParallelCommand;
import org.eclipse.bpel.ui.commands.SetNameCommand;
import org.eclipse.bpel.ui.commands.SetVariableCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.bpel.ui.util.XSDUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xsd.XSDTypeDefinition;

/**
 * Details section for the ForEach activity
 */
public class ForEachSection extends BPELPropertySection {

	public static class ForEachContext {
		public int		context;

		public Object	innerContext;

		public ForEachContext(int context) {
			this.context = context;
		}
	}

	protected static final int	IS_PARALLEL_CONTEXT				= 1;
	protected static final int	COUNTER_NAME_VARIABLE_CONTEXT	= 2;
	protected static final String COUNTER_VARIABLE_TYPE = "unsignedInt";

	private Text				counterNameText;
	private Composite			counterNameVariableComposite;
	private Button				isParallelCheckbox;
	private ChangeTracker		isParallelCheckboxChangeTracker;
	private Composite			isParallelComposite;
	private ChangeTracker		variableNameChangeTracker;
	protected int				lastChangeContext				= -1;

	@Override
	public Object getUserContext() {
		ForEachContext result = new ForEachContext(lastChangeContext);
		return result;
	}

	@Override
	public void refresh() {
		super.refresh();
		updateIsParallelWidgets();
		updateCounterNameWidgets();
	}

	@Override
	public void restoreUserContext(Object userContext) {
		ForEachContext c = (ForEachContext) userContext;
		switch (c.context) {
		case IS_PARALLEL_CONTEXT:
			isParallelCheckbox.setFocus();
			return;
		case COUNTER_NAME_VARIABLE_CONTEXT:
			counterNameText.setFocus();
			return;
		}
		throw new IllegalStateException();
	}

	private void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			public Command createApplyCommand() {
				lastChangeContext = IS_PARALLEL_CONTEXT;
				return wrapInShowContextCommand(new SetForEachIsParallelCommand(
						getInput(), isParallelCheckbox.getSelection()));
			}

			public String getLabel() {
				return IBPELUIConstants.CMD_SET_PARALLEL_EXECUTION;
			}

			public void restoreOldState() {
				updateIsParallelWidgets();
			}
		};
		isParallelCheckboxChangeTracker = new ChangeTracker(
				this.isParallelCheckbox, change, getCommandFramework());

		change = new IOngoingChange() {

			public Command createApplyCommand() {
				ForEach forEach = (ForEach) getInput();
				String variableName = counterNameText.getText();
				if ("".equals(variableName)) {
					variableName = null;
				}
				CompoundCommand result = new CompoundCommand();
				Variable variable = forEach.getCounterName();
				if (variableName != null) {
					if (variable == null) {
						variable = BPELFactory.eINSTANCE.createVariable();
						XSDTypeDefinition varType = XSDUtils.getPrimitive(COUNTER_VARIABLE_TYPE);
						variable.setType(varType);
						result.add(new SetVariableCommand(forEach, variable,
								ModelHelper.INCOMING));
					}
					result.add(new SetNameCommand(variable, variableName));
				} else {
					if (variable != null) {
						result.add(new SetVariableCommand(forEach, null,
								ModelHelper.INCOMING));
					}
				}
				lastChangeContext = COUNTER_NAME_VARIABLE_CONTEXT;
				return wrapInShowContextCommand(result);
			}

			public String getLabel() {
				return IBPELUIConstants.CMD_SET_COUNTER_VARIABLE_NAME;
			}

			public void restoreOldState() {
				updateCounterNameWidgets();
			}

		};
		variableNameChangeTracker = new ChangeTracker(this.counterNameText,
				change, getCommandFramework());
	}

	private void createCounterNameWidgets(Composite parentComposite) {
		FlatFormData data;

		counterNameVariableComposite = createFlatFormComposite(parentComposite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(isParallelComposite,
				IDetailsAreaConstants.VSPACE);
		data.right = new FlatFormAttachment(100, 0);
		counterNameVariableComposite.setLayoutData(data);

		Label variableLabel = wf
				.createLabel(
						counterNameVariableComposite,
						Messages.ForEachSection_COUNTER_NAME);

		counterNameText = wf.createText(counterNameVariableComposite, "");
		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(
				variableLabel, STANDARD_LABEL_WIDTH_AVG));
		data.right = new FlatFormAttachment(100, 0);
		counterNameText.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(counterNameText,
				-IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(counterNameText, 0, SWT.CENTER);
		variableLabel.setLayoutData(data);

	}

	private void createIsParallelWidgets(Composite parentComposite) {
		FlatFormData data;

		isParallelComposite = createFlatFormComposite(parentComposite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		isParallelComposite.setLayoutData(data);

		isParallelCheckbox = wf
				.createButton(
						isParallelComposite,
						Messages.ForEachSection_IS_PARALLEL,
						SWT.CHECK);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		isParallelCheckbox.setLayoutData(data);
	}

	private void updateCounterNameWidgets() {
		variableNameChangeTracker.stopTracking();
		try {
			ForEach forEach = (ForEach) getInput();
			Variable variable = forEach.getCounterName();
			if (variable == null) {
				counterNameText.setText(""); //$NON-NLS-1$
			} else {
				counterNameText
						.setText(variable.getName() == null ? "" : variable.getName()); //$NON-NLS-1$
			}
		} finally {
			variableNameChangeTracker.startTracking();
		}
	}

	private void updateIsParallelWidgets() {
		isParallelCheckboxChangeTracker.stopTracking();
		try {
			boolean isParallel = ((ForEach) getInput()).getParallel()
					.booleanValue();
			this.isParallelCheckbox.setSelection(isParallel);
		} finally {
			isParallelCheckboxChangeTracker.startTracking();
		}
	}

	@Override
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
				new MultiObjectAdapter() {

					public void notify(Notification n) {
						if (n.getFeature() == BPELPackage.eINSTANCE.getForEach_Parallel()) {
							updateIsParallelWidgets();
						}
						if (n.getFeature() == BPELPackage.eINSTANCE.getForEach_CounterName()) {
							updateCounterNameWidgets();
						}
					}
					
				}
		};
	}

	protected void createClient(Composite parent) {
		Composite parentComposite = createFlatFormComposite(parent);
		createIsParallelWidgets(parentComposite);
		createCounterNameWidgets(parentComposite);

		createChangeTrackers();
	}
}
