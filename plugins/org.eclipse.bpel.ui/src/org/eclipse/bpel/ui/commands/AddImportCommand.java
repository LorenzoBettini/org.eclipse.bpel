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

import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.IBPELUIConstants;


/**
 * Adds an Import to the Imports contained in the Process.
 */
public class AddImportCommand extends AddToListCommand {

	Process process;
	
	public AddImportCommand(Process process, Import imp) {
		super(process, imp, IBPELUIConstants.CMD_ADD_IMPORT);
		this.process = process;
	}
	
	protected List getList() {
		return process.getImports();
	}
}
