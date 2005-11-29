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

import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.Messages;


/**
 * This AssignCategory is shown when no other category applies. 
 */
public class NullAssignCategory extends AssignCategoryBase {

	protected NullAssignCategory(boolean isFrom, BPELPropertySection ownerSection) {
		super(isFrom, ownerSection);
	}

	public String getName() { return Messages.NullAssignCategory____None____1; } 

	public boolean isCategoryForModel(To toOrFrom) {
		if (toOrFrom == null)  return true;
		return false;
	}
	protected void loadToOrFrom(To toOrFrom) {
	}
	protected void storeToOrFrom(To toOrFrom) {
	}
	
	public Object getUserContext() {
		return null;
	}
	public void restoreUserContext(Object userContext) {
	}

	protected boolean isDefaultCompositeOpaque() { return false; }
}
