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
import org.eclipse.emf.ecore.EObject;

/**
 * Sets the "exitOnStandardFault" property of the process object.
 * 
 */
public class SetExitOnStandardFaultCommand extends SetCommand {

	/**
	 * Return the command label.
	 * 
	 * @see org.eclipse.bpel.ui.commands.SetCommand#getDefaultLabel()
	 */
	@Override
	public String getDefaultLabel() {
		return IBPELUIConstants.CMD_SELECT_STANDARD_FAULT;
	}

	/**
	 * Return new shiny SetExitOnStandardFaultCommand 
	 * @param t the target object
	 * @param v the new value
	 */
	
	public SetExitOnStandardFaultCommand(EObject t, Boolean v) {
		super(t, v);
	}

	/**
	 * @see org.eclipse.bpel.ui.commands.SetCommand#get()
	 */
	@Override
	public Object get() {
		org.eclipse.bpel.model.Process process = (org.eclipse.bpel.model.Process) target;
		return process.getExitOnStandardFault();
	}

	/**
	 * @see org.eclipse.bpel.ui.commands.SetCommand#set(java.lang.Object)
	 */
	@Override
	public void set(Object o) {
		org.eclipse.bpel.model.Process process = (org.eclipse.bpel.model.Process) target;
		process.setExitOnStandardFault((Boolean) o);		
	}
}
