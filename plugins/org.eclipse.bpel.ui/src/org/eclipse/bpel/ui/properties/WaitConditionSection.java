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
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.expressions.IEditorConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;


/**
 * Details section for the duration/date of a Wait activity.
 */
public class WaitConditionSection extends RadioChoiceExpressionSection {
	
	protected void createNoEditorWidgets(Composite composite) {
	    super.createNoEditorWidgets(composite);
	    
		FlatFormData ffdata;
		
		Label label1 = wf.createLabel(composite,
			Messages.WaitConditionSection_No_condition_specified_1); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(0, 0);
		ffdata.right = new FlatFormAttachment(100, 0);
		label1.setLayoutData(ffdata);

		Label label2 = wf.createLabel(composite,
			Messages.WaitConditionSection_Create_condition_text_2); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(label1, IDetailsAreaConstants.VSPACE);
		ffdata.right = new FlatFormAttachment(100, 0);
		label2.setLayoutData(ffdata);
		
		Button createDefaultButton = wf.createButton(composite, Messages.WaitConditionSection_Create_a_New_Condition_2, SWT.PUSH); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(label2, IDetailsAreaConstants.VSPACE);
		createDefaultButton.setLayoutData(ffdata);
		
		// TODO: two buttons, one for duration and one for deadline ?
		
		createDefaultButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				doChooseExpressionLanguage(SAME_AS_PARENT);
			}
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
	}
	
	// Returns an array of translated labels, one per button.
	protected String[] getButtonLabels() {
		final String[] result = { Messages.WaitConditionSection_Date_1, Messages.WaitConditionSection_Duration_2 }; 
		return result;
	}
	
	protected int getModelExpressionType() {
		return super.getModelExpressionType();
	}

	protected String getButtonExprType(int buttonIndex) {
		if (buttonIndex == 0) return IEditorConstants.ET_DATETIME;
		if (buttonIndex == 1) return IEditorConstants.ET_DURATION;
		throw new IllegalArgumentException();
	}
	protected String getButtonExprContext(int buttonIndex) {
		return IEditorConstants.EC_WAIT;
	}
	
	protected int getButtonIndexFromModel() {
		return (ModelHelper.getExpressionSubKind(getInput(), ModelHelper.WAIT_EXPR) == ModelHelper.ESUB_FOR)? 1:0;
	}

	protected boolean isValidClientUseType(String useType) {
		return IBPELUIConstants.USE_TYPE_DEADLINE_CONDITION.equals(useType)
			|| IBPELUIConstants.USE_TYPE_DURATION_CONDITION.equals(useType);
	}
	
	protected void createClient(Composite parent) {
		super.createClient(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parentComposite, IHelpContextIds.PROPERTY_PAGE_WAIT);
	}
}
