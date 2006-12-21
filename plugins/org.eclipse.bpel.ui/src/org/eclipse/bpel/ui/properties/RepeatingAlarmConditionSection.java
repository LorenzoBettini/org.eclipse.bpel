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
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.expressions.IEditorConstants;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;


public class RepeatingAlarmConditionSection extends ExpressionSection {
	
	protected Label label1, label2, label3;
	protected Button createDefaultButton;

	protected String getExpressionType() { return IEditorConstants.ET_DURATION; }
	protected String getExpressionContext() { return IEditorConstants.EC_ONALARM_REPEATEVERY; }
	
	
	@Override
	protected Composite createNoEditorWidgets(Composite composite) {
		
		Composite section = wf.createComposite(composite);
		section.setLayout(new FlatFormLayout());
	
		FlatFormData ffdata;
		
		label1 = wf.createLabel(section,
			Messages.RepeatingAlarmConditionSection_No_condition_specified_1); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(0, 0);
		ffdata.right = new FlatFormAttachment(100, 0);
		label1.setLayoutData(ffdata);

		label2 = wf.createLabel(section,
			Messages.RepeatingAlarmConditionSection_Choose_type_text_2); 
			ffdata = new FlatFormData();
			ffdata.left = new FlatFormAttachment(0, 0);
			ffdata.top = new FlatFormAttachment(label1, IDetailsAreaConstants.VSPACE);
			ffdata.right = new FlatFormAttachment(100, 0);
			label2.setLayoutData(ffdata);
			
		label3 = wf.createLabel(section,
			Messages.RepeatingAlarmConditionSection_Not_supported_in_Pick_text_3); 
			ffdata = new FlatFormData();
			ffdata.left = new FlatFormAttachment(0, 0);
			ffdata.top = new FlatFormAttachment(0, 0);
			ffdata.right = new FlatFormAttachment(100, 0);
			label3.setLayoutData(ffdata);
				
		createDefaultButton = wf.createButton(section, Messages.RepeatingAlarmConditionSection_Create_a_New_Repeat_Condition_3, SWT.PUSH); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(label2, IDetailsAreaConstants.VSPACE);
		createDefaultButton.setLayoutData(ffdata);
		
		createDefaultButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				doChooseExpressionLanguage(SAME_AS_PARENT);
			}
			public void widgetDefaultSelected(SelectionEvent e) { }
		});
		return section;
	}

	protected void updateWidgets() {
		super.updateWidgets();
		boolean enable = (BPELUtil.getIContainerParent(getInput()) instanceof EventHandler);
		expressionLanguageViewer.getCombo().setEnabled(enable);
		if (editor == null) {
			expressionLanguageViewer.getCombo().setEnabled(enable);
			label1.setVisible(enable);
			label2.setVisible(enable);
			label3.setVisible(!enable);
			createDefaultButton.setVisible(enable);
			createDefaultButton.setEnabled(enable);
		}
	}

	protected boolean isValidClientUseType(String useType) {
		return IBPELUIConstants.USE_TYPE_DURATION_CONDITION.equals(useType);
	}
	
	protected void createClient(Composite parent) {
		super.createClient(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			fParentComposite, IHelpContextIds.PROPERTY_PAGE_REPEAT_ALARM);
	}
}
