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

import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.bpel.model.util.BPELUtils;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


/**
 * An AssignCategory where the user can type in a literal value (note: NOT an expression).
 * 
 * TODO: his could be an XML editor one day ...
 */

public class LiteralAssignCategory extends AssignCategoryBase {

	Text literalText;
	
	protected LiteralAssignCategory (boolean isFrom, BPELPropertySection ownerSection) {
		super(isFrom, ownerSection);
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#getName()
	 */
	
	public String getName() { 
		return Messages.LiteralAssignCategory_Fixed_Value_1;
	} 

	@Override
	protected String getLabelFlatFormatString() {
		return IBPELUIConstants.FORMAT_CMD_EDIT;
	}

	@Override
	protected void createClient2(Composite parent) {
		FlatFormData data;

		
		literalText = wf.createText(parent, EMPTY_STRING, SWT.V_SCROLL | SWT.MULTI);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		//data.top = new FlatFormAttachment(typeComposite, IDetailsAreaConstants.VSPACE);
		data.top = new FlatFormAttachment(0,0);
		data.bottom = new FlatFormAttachment(100, 0);
		literalText.setLayoutData(data);
		
		getChangeHelper().startListeningTo(literalText);
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.bpel.model.To)
	 */
	public boolean isCategoryForModel(To toOrFrom) {
		if (!fIsFrom || toOrFrom == null)  {
			return false;
		}
		From from = (From)toOrFrom;
		if (from.getLiteral() != null) {
			return true;
		}
		return false;
	}
	
	@Override
	protected void loadToOrFrom(To toOrFrom) {
		if (!fIsFrom) {
			return;
		}
		
		From from = (From)toOrFrom;

		getChangeHelper().startNonUserChange();
		try {
			String fromString = EMPTY_STRING;
			if (from != null) {
				fromString = from.getLiteral();
			}
			if (fromString == null) {
				fromString = EMPTY_STRING;
			}
			
			literalText.setText(fromString);			
		} finally {
			getChangeHelper().finishNonUserChange();
		}
	}
	
	
	@Override
	protected void storeToOrFrom(To toOrFrom) {
		if (!fIsFrom) {
			return;
		}
		
		From from = (From)toOrFrom;
		
		String expr = literalText.getText();

		from.setLiteral(expr);

		if (expr == null) {
			from.setUnsafeLiteral(Boolean.FALSE);
		} else {
			// test if the unsafe literal can be converted into an element and serialized safely 
			if (BPELUtils.convertStringToNode(expr, (BPELResource)getBPELEditor().getResource()) != null) {
				from.setUnsafeLiteral(Boolean.TRUE);
			} else {
				from.setUnsafeLiteral(Boolean.FALSE);
				MessageDialog.openWarning(literalText.getShell(),
					Messages.LiteralAssignCategory_Warning_1, 
					Messages.LiteralAssignCategory_Literal_not_XML_2); 
			}
		}
		
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
		literalText.setFocus();
	}

}
