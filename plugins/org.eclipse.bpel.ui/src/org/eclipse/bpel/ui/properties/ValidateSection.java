/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
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
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetValidateCommand;
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
 * Details section for the "validate" property of Assign activities.
 */
public class ValidateSection extends BPELPropertySection  {

	protected Button validateXMLButton;
	protected ChangeTracker validateXMLTracker;

	protected boolean isValidateXMLAffected(Object context, Notification n) {
		if (context instanceof Assign) {
			return (n.getFeatureID(Assign.class) == BPELPackage.ASSIGN__VALIDATE);
		}
		throw new IllegalArgumentException();
	}
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (isValidateXMLAffected(getInput(), n)) {
						updateValidateXMLWidgets();
					} 
				}
			},
		};
	}

	protected void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			public String getLabel() {
				return IBPELUIConstants.CMD_VALIDATE_XML;
			}
			public Command createApplyCommand() {
				return wrapInShowContextCommand(new SetValidateCommand(
					getInput(), validateXMLButton.getSelection()? Boolean.TRUE : null));
			}
			public void restoreOldState() {
				updateValidateXMLWidgets();
			}
		};
		validateXMLTracker = new ChangeTracker(validateXMLButton, change, getCommandFramework());
	}

	protected void createValidateWidgets(Composite composite) {
		FlatFormData data;
		
		Composite parent = wf.createComposite(composite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		parent.setLayoutData(data);
		parent.setLayout(new FillLayout());

		validateXMLButton = wf.createButton(parent, Messages.Validate, SWT.CHECK); 
	}
	
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		// HACK: the checkbox by itself looks cramped..give it a little extra space
		((FlatFormLayout)composite.getLayout()).marginHeight += 3;

		createValidateWidgets(composite);
		createChangeTrackers();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			composite, IHelpContextIds.PROPERTY_PAGE_VALIDATE_DETAILS);
	}

	protected void updateValidateXMLWidgets() {
		validateXMLTracker.stopTracking();
		try {
			boolean modelValue = Boolean.TRUE.equals(ModelHelper.getValidate(getInput()));
			if (validateXMLButton.getSelection() != modelValue) {
				validateXMLButton.setSelection(modelValue);
			}
		} finally {
			validateXMLTracker.startTracking();
		}
	}

	public void refresh() {
		super.refresh();
		updateValidateXMLWidgets();
	}

	public Object getUserContext() {
		return null;
	}
	
	public void restoreUserContext(Object userContext) {
		validateXMLButton.setFocus();
	}
}
