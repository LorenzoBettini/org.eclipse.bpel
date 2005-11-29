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
package org.eclipse.bpel.ui.adapters;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.swt.graphics.Image;

import org.eclipse.wst.wsdl.Operation;

public class OperationAdapter extends AbstractAdapter implements ILabeledElement {

	/* ILabeledElement */
	
	public Image getSmallImage(Object object) {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_OPERATION_16);
	}
	
	public Image getLargeImage(Object object) {
		return null;
	}
	
	public String getTypeLabel(Object object) {
		return Messages.OperationAdapter_Operation_1; 
	}
	public String getLabel(Object object) {
		Operation op = (Operation)object;
		String name = op.getName();
		if (name != null)  return name;
		return getTypeLabel(object);
	}
}
