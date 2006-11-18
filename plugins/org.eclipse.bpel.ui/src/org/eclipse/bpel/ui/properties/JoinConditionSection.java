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
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.expressions.IEditorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * Details section for the JoinCondition of an activity (a boolean expression).
 */
public class JoinConditionSection extends ExpressionSection {

	protected Label label2, label3;
	protected Button createDefaultButton;

	protected void addAllAdapters() {
		super.addAllAdapters();
		Activity activity = (Activity)getInput();
		Targets targets = activity.getTargets();
		if (targets != null) {
			adapters[0].addToObject(targets);
		}
	}
	
	protected String getExpressionType() { return IEditorConstants.ET_BOOLEAN; }
	protected String getExpressionContext() { return IEditorConstants.EC_JOIN; }
	
	protected boolean isExpressionOptional() { return true; }
	
	
	@Override
	protected Composite createNoEditorWidgets(Composite composite) {
	
		Composite section = wf.createComposite(composite);
		section.setLayout(new FlatFormLayout());
	
	    FlatFormData ffdata;
		
		Label label1 = wf.createLabel(section,
			Messages.JoinConditionSection_No_condition_specified_1); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(0, 0);
		ffdata.right = new FlatFormAttachment(100, 0);
		label1.setLayoutData(ffdata);

		label2 = wf.createLabel(section,
			Messages.JoinConditionSection_Optional_condition_text_2); 
			ffdata = new FlatFormData();
			ffdata.left = new FlatFormAttachment(0, 0);
			ffdata.top = new FlatFormAttachment(label1, IDetailsAreaConstants.VSPACE);
			ffdata.right = new FlatFormAttachment(100, 0);
			label2.setLayoutData(ffdata);
			
		label3 = wf.createLabel(section,
				Messages.JoinConditionSection_No_incoming_links_text_1); 
			ffdata = new FlatFormData();
			ffdata.left = new FlatFormAttachment(0, 0);
			ffdata.top = new FlatFormAttachment(label1, IDetailsAreaConstants.VSPACE);
			ffdata.right = new FlatFormAttachment(100, 0);
			label3.setLayoutData(ffdata);
		label3.setVisible(false);
			
		createDefaultButton = wf.createButton(section, Messages.JoinConditionSection_Create_a_New_Condition_3, SWT.PUSH); 
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
	
	/**
	 * Update the widgets based on the last input set.
	 * 
	 */
	@Override
	protected void updateWidgets() {
		
		super.updateWidgets();
		
		Activity activity = (Activity)getInput();
		Targets targets = activity.getTargets();
		boolean enable = (targets != null);
				
		expressionLanguageViewer.getCombo().setEnabled(enable);
		
		if (hasEditor() == false) {
			label2.setVisible(enable);
			label3.setVisible(!enable);
			createDefaultButton.setVisible(enable);
			createDefaultButton.setEnabled(enable);
		}
	}

	
	@Override
	protected void basicSetInput(EObject newInput) {		
		super.basicSetInput(newInput);
		
		// update the widgets
		updateWidgets();
	}

	
	@Override
	protected boolean isValidClientUseType(String useType) {
		return IBPELUIConstants.USE_TYPE_JOIN_CONDITION.equals(useType);
	}
}
