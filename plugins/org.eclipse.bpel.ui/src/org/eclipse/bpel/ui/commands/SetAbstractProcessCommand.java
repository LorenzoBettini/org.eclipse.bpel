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

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets the "abstract" property of a process.
 */
public class SetAbstractProcessCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_PROCESSTYPE; }

	public SetAbstractProcessCommand(Object target, Boolean newProcessType)  {
		super((EObject)target, newProcessType);
	}

	public Object get() {
		return ((Process)target).getAbstractProcess();
	}
	public void set(Object o) {
		((Process)target).setAbstractProcess((Boolean)o);
	}
}
