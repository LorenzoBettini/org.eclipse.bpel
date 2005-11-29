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

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.ui.IBPELUIConstants;


/**
 * Adds a new Copy to an Assign activity.
 */
public class AddCopyCommand extends AddToListCommand {
	public AddCopyCommand(Assign target, Copy newCopy) {
		super(target, newCopy, IBPELUIConstants.CMD_ADD_COPY);
	}

	protected List getList() {
		return ((Assign)target).getCopy();
	}
}
