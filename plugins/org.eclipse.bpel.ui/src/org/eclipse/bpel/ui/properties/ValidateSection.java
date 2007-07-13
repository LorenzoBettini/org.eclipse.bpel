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
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetCommand;
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

	
	@Override
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				@Override
				public void notify(Notification n) {
					if (n.getFeature() == BPELPackage.eINSTANCE.getAssign_Validate() ) {
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
				
				return wrapInShowContextCommand(
						new SetCommand(getInput(), 
									validateXMLButton.getSelection()? Boolean.TRUE : null, 
									BPELPackage.eINSTANCE.getAssign_Validate() ));
			}
			
			public void restoreOldState() {
				updateValidateXMLWidgets();
			}
		};
		validateXMLTracker = new ChangeTracker(validateXMLButton, change, getCommandFramework());
	}

	protected void createValidateWidgets(Composite composite) {
		FlatFormData data;
		
		Composite parent = fWidgetFactory.createComposite(composite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		parent.setLayoutData(data);
		parent.setLayout(new FillLayout());

		validateXMLButton = fWidgetFactory.createButton(parent, Messages.Validate, SWT.CHECK); 
	}
	
	@Override
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

	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		updateValidateXMLWidgets();
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#getUserContext()
	 */
	@Override
	public Object getUserContext() {
		return null;
	}
	
	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#restoreUserContext(java.lang.Object)
	 */
	@Override
	public void restoreUserContext(Object userContext) {
		validateXMLButton.setFocus();
	}
}
