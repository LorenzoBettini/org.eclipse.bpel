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
import org.eclipse.bpel.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * An AssignCategory representing opaque="yes" in a From object.
 * TODO: This should only appear for abstract processes
 */
public class OpaqueAssignCategory extends AssignCategoryBase {

	protected OpaqueAssignCategory(boolean isFrom, BPELPropertySection ownerSection) {
		super(isFrom, ownerSection);
	}

	public String getName() { return Messages.OpaqueAssignCategory_Opaque_1; } 

	protected void createClient2(Composite parent) {
		super.createClient2(parent);
		Label opaqueLabel = wf.createLabel(composite,
			Messages.OpaqueAssignCategory_An_Opaque_value_is_used_in_abstract_processes__2, SWT.CENTER); 
		FlatFormData data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(50, 0);
		opaqueLabel.setLayoutData(data);
	}
	
	public boolean isCategoryForModel(To toOrFrom) {
		if (toOrFrom == null)  return false;
		if (!isFrom)  return false;
		From from = (From)toOrFrom;
		if (Boolean.TRUE.equals(from.getOpaque()))  return true;
		return false;
	}
	
	protected void loadToOrFrom(To toOrFrom) {
	}
	
	protected void storeToOrFrom(To toOrFrom) {
		if (!isFrom)  return;
		From from = (From)toOrFrom;
		from.setOpaque(Boolean.TRUE);
	}

	public Object getUserContext() {
		return null;
	}
	
	public void restoreUserContext(Object userContext) {
	}

}
