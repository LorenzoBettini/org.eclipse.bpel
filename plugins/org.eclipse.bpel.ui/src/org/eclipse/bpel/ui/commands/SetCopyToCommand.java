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
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.IBPELUIConstants;


/** 
 * Sets the To property of a Copy model object.
 */
public class SetCopyToCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_ASSIGNCOPY; }

	public SetCopyToCommand(Copy target, To newTo)  {
		super(target, newTo);
	}

	public Object get() {
		return ((Copy)target).getTo();
	}
	public void set(Object o) {
		((Copy)target).setTo((To)o);
	}
}
