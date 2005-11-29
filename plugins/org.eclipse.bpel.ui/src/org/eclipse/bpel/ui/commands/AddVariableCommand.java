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

import java.util.List;

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.IBPELUIConstants;


/**
 * Adds a Variable to the Process.
 */
public class AddVariableCommand extends AddToListCommand {

	Process process;
	
	public AddVariableCommand(Process process, Variable var) {
		super(process, var, IBPELUIConstants.CMD_ADD_VARIABLE);
		this.process = process;
	}
	
	protected List getList() {
		return process.getVariables().getChildren();
	}
}
