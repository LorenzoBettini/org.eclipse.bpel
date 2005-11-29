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

import java.util.Iterator;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.expressions.IEditorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * Details section for the TransitionCondition of a link source (a boolean expression).
 */
public class TransitionConditionSection extends ExpressionSection {

	protected void addAllAdapters() {
		super.addAllAdapters();
		Link link = (Link)getInput();
		Iterator it = link.getSources().iterator();
		while (it.hasNext()) {
			Source source = (Source)it.next();
			adapters[0].addToObject(source);
		}
	}
	
	protected String getExpressionType() { return IEditorConstants.ET_BOOLEAN; }
	protected String getExpressionContext() { return IEditorConstants.EC_TRANSITION; }
	
	protected boolean isExpressionOptional() { return true; }
	
	protected void createNoEditorWidgets(Composite composite) {
	    super.createNoEditorWidgets(composite);
		FlatFormData ffdata;
		
		Label label1 = wf.createLabel(composite,
			Messages.TransitionConditionSection_No_condition_specified_1); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(0, 0);
		ffdata.right = new FlatFormAttachment(100, 0);
		label1.setLayoutData(ffdata);

		Label label2 = wf.createLabel(composite,
			Messages.TransitionConditionSection_Optional_condition_text_2); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(label1, IDetailsAreaConstants.VSPACE);
		ffdata.right = new FlatFormAttachment(100, 0);
		label2.setLayoutData(ffdata);
		
		Button createDefaultButton = wf.createButton(composite, Messages.TransitionConditionSection_Create_a_New_Condition_3, SWT.PUSH); 
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

	}

	protected boolean isValidClientUseType(String useType) {
		return IBPELUIConstants.USE_TYPE_TRANSITION_CONDITION.equals(useType);
	}
}
