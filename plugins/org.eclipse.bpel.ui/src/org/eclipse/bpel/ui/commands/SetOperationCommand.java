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
package org.eclipse.bpel.ui.commands;

import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.wst.wsdl.Operation;

/** 
 * Sets the faultName property of a model object.  This is supported for
 * Catch objects and Throw activities.
 */
public class SetOperationCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_SELECT_OPERATION; }

	public SetOperationCommand(EObject target, Operation newOperation)  {
		super(target, newOperation);
	}

	public Object get() {
		return ModelHelper.getOperation(target);
	}
	public void set(Object o) {
		ModelHelper.setOperation(target, (Operation)o);
	}
}
