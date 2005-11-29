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

import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.ui.IBPELUIConstants;


/** 
 * Sets the From property of a Copy model object.
 */
public class SetCopyFromCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_ASSIGNCOPY; }

	public SetCopyFromCommand(Copy target, From newFrom)  {
		super(target, newFrom);
	}

	public Object get() {
		return ((Copy)target).getFrom();
	}
	public void set(Object o) {
		((Copy)target).setFrom((From)o);
	}
}
