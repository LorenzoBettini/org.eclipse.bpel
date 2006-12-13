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

import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Branches;
import org.eclipse.bpel.model.CompletionCondition;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetCountSuccessfulBranchesOnlyCommand;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Completion section for the ForEach activity
 */
public class ForEachSuccessfulBranchesOnlySection extends BPELPropertySection {

	private Button			successfulBranchesOnlyCheckbox;
	private ChangeTracker	successfulBranchesOnlyCheckboxChangeTracker;
	private Composite		successfulBranchesOnlyComposite;

	@Override
	public void refresh() {
		super.refresh();
		updateSuccessfulBranchesOnlyWidgets();
	}

	@Override
	public void restoreUserContext(Object userContext) {
		updateSuccessfulBranchesOnlyWidgets();
		successfulBranchesOnlyCheckbox.setFocus();
	}

	private void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			public Command createApplyCommand() {
				return wrapInShowContextCommand(new SetCountSuccessfulBranchesOnlyCommand(
						((ForEach) getInput()).getCompletionCondition()
								.getBranches(), successfulBranchesOnlyCheckbox
								.getSelection()));
			}

			public String getLabel() {
				return IBPELUIConstants.CMD_SET_SUCCESSFUL_BRANCHES_ONLY;
			}

			public void restoreOldState() {
				updateSuccessfulBranchesOnlyWidgets();
			}
		};
		successfulBranchesOnlyCheckboxChangeTracker = new ChangeTracker(
				this.successfulBranchesOnlyCheckbox, change,
				getCommandFramework());
	}

	private void createSuccessfulBranchesOnlyWidgets(Composite parentComposite) {
		FlatFormData data;

		successfulBranchesOnlyComposite = createFlatFormComposite(parentComposite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		successfulBranchesOnlyComposite.setLayoutData(data);

		successfulBranchesOnlyCheckbox = wf
				.createButton(
						successfulBranchesOnlyComposite,
						Messages.ForEachSuccessfulBranchesOnlySection_1,
						SWT.CHECK);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		successfulBranchesOnlyCheckbox.setLayoutData(data);
	}

	private void updateSuccessfulBranchesOnlyWidgets() {
		successfulBranchesOnlyCheckboxChangeTracker.stopTracking();
		try {
			CompletionCondition completionCondition = ((ForEach) getInput())
					.getCompletionCondition();
			if (completionCondition == null) {
				this.successfulBranchesOnlyCheckbox.setEnabled(false);
			} else {
				Branches branches = completionCondition.getBranches();
				if (branches == null) {
					this.successfulBranchesOnlyCheckbox.setEnabled(false);
				} else {
					this.successfulBranchesOnlyCheckbox.setEnabled(true);
					boolean successfulBranchesOnly = branches
							.isSetCountCompletedBranchesOnly();
					this.successfulBranchesOnlyCheckbox
							.setSelection(successfulBranchesOnly);
				}
			}
		} finally {
			successfulBranchesOnlyCheckboxChangeTracker.startTracking();
		}
	}

	@Override
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] { new MultiObjectAdapter() {

			public void notify(Notification n) {
				if (n.getFeature() == BPELPackage.eINSTANCE
						.getBranches_CountCompletedBranchesOnly()) {
					updateSuccessfulBranchesOnlyWidgets();
				}
				if (n.getFeature() == BPELPackage.eINSTANCE.getForEach_CompletionCondition()) {
					updateSuccessfulBranchesOnlyWidgets();
				}
			}

		}};
	}

	protected void createClient(Composite parent) {
		Composite parentComposite = createFlatFormComposite(parent);
		createSuccessfulBranchesOnlyWidgets(parentComposite);

		createChangeTrackers();
	}
}
