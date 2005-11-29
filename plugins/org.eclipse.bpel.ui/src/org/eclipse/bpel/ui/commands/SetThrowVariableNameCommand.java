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

import org.eclipse.bpel.model.Throw;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets the variableName property of a model object.  This is supported for
 * Throw, Catch and Reply activities.
 */
public class SetThrowVariableNameCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_FAULT_VARIABLE_NAME; }

	public SetThrowVariableNameCommand(EObject target, String newFaultVariableName)  {
		super(target, newFaultVariableName);
	}

	public Object get() {
		return ((Throw)target).getFaultVariable().getName();
	}
	
	public void set(Object o) {
		((Throw)target).getFaultVariable().setName((String)o);
	}
}
