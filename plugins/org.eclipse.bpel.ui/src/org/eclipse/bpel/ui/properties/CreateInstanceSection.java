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
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetCreateInstanceCommand;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;


/**
 * Details section for the "createInstance" property of Receive and Pick activities.
 */
public class CreateInstanceSection extends BPELPropertySection  {

	protected Button createInstanceButton;
	protected ChangeTracker createInstanceTracker;

	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (ModelHelper.isCreateInstanceAffected(getInput(), n)) {
						updateCreateInstanceWidgets();
					} 
				}
			},
		};
	}

	protected void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			public String getLabel() {
				return IBPELUIConstants.CMD_SELECT_CREATEINSTANCE;
			}
			public Command createApplyCommand() {
				return wrapInShowContextCommand(new SetCreateInstanceCommand(
					getInput(), createInstanceButton.getSelection()? Boolean.TRUE : null));
			}
			public void restoreOldState() {
				updateCreateInstanceWidgets();
			}
		};
		createInstanceTracker = new ChangeTracker(createInstanceButton, change, getCommandFramework());
	}

	protected void createCreateInstanceWidgets(Composite composite) {
		FlatFormData data;
		
		Composite parent = fWidgetFactory.createComposite(composite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0, IDetailsAreaConstants.VSPACE);
		
		parent.setLayoutData(data);
		parent.setLayout(new FillLayout());

		createInstanceButton = fWidgetFactory.createButton(parent, Messages.CreateInstanceDetails_Create_a_new_Process_instance_if_one_does_not_already_exist_1, SWT.CHECK); 
	}
	
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		// HACK: the checkbox by itself looks cramped..give it a little extra space
		((FlatFormLayout)composite.getLayout()).marginHeight += 3;

		createCreateInstanceWidgets(composite);
		createChangeTrackers();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			composite, IHelpContextIds.PROPERTY_PAGE_PICK_IMPLEMENTATION);
	}

	protected void updateCreateInstanceWidgets() {
		createInstanceTracker.stopTracking();
		try {
			boolean modelValue = Boolean.TRUE.equals(ModelHelper.getCreateInstance(getInput()));
			if (createInstanceButton.getSelection() != modelValue) {
				createInstanceButton.setSelection(modelValue);
			}
		} finally {
			createInstanceTracker.startTracking();
		}
	}

	public void refresh() {
		super.refresh();
		updateCreateInstanceWidgets();
	}

	public Object getUserContext() {
		return null;
	}
	
	public void restoreUserContext(Object userContext) {
		createInstanceButton.setFocus();
		
	}
	
	public boolean shouldUseExtraSpace () {
		return false;
	}
	
	public int getMinimumHeight () {
		return 40;
	}
}
