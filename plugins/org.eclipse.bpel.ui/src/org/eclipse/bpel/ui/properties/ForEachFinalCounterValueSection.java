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
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.expressions.IEditorConstants;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class ForEachFinalCounterValueSection extends ExpressionSection {

	protected String getExpressionType() { return IEditorConstants.ET_UNSIGNED_INT; }
	protected String getExpressionContext() { return IEditorConstants.EC_FOREACH_FINAL_COUNTER_VALUE; }
	
	@Override
	protected Composite createNoEditorWidgets(Composite composite) {
			    	    
		FlatFormData ffdata;
		
		Composite section = createFlatFormComposite(composite);		
		
		Label label1 = wf.createLabel(section,
			Messages.ForEachFinalCounterValuesSection_No_Expression_specified_1); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		if (this.title != null) {
			ffdata.top = new FlatFormAttachment(this.titleLabel, IDetailsAreaConstants.VSPACE);
		} else {
			ffdata.top = new FlatFormAttachment(0, 0);
		}
		ffdata.right = new FlatFormAttachment(100, 0);
		label1.setLayoutData(ffdata);

		Label label2 = wf.createLabel(section,
			Messages.ForEachCounterValuesSection_Mandatory_Expression_2); 
		ffdata = new FlatFormData();
		ffdata.left = new FlatFormAttachment(0, 0);
		ffdata.top = new FlatFormAttachment(label1, IDetailsAreaConstants.VSPACE);
		ffdata.right = new FlatFormAttachment(100, 0);
		label2.setLayoutData(ffdata);
		
		Button createDefaultButton = wf.createButton(section, Messages.ForEachCounterValuesSection_Create_a_New_Expression_3, SWT.PUSH); 
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

	protected boolean isValidClientUseType(String useType) {
		return IBPELUIConstants.USE_TYPE_CONDITION.equals(useType);
	}
	
	/**
	 * Return true if the marker is valid for this section.
	 * @return true if so, false otherwise.
	 */
	public boolean isValidMarker (IMarker marker ) {
		
		return false;
	}
	
	@Override
	protected void createClient(Composite parent) {
		this.title = Messages.ForEachFinalCounterValuesSection_TITLE_3;	
		super.createClient(parent);
	}
}
