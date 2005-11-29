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
package org.eclipse.bpel.common.ui.command;

import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.bpel.common.ui.details.OngoingChangeManager;
import org.eclipse.gef.commands.Command;


/**
 * This class captures command-related functionality (and other miscellaneous
 * functionality of DetailsArea which is not supported by xtools.common.ui.properties).
 * 
 * It will be a base class of the old DetailsArea, and editors porting to the xtools
 * TabbedPropertySheet stuff can also use it directly.
 */
public class CommandFramework implements ICommandFramework {

	protected OngoingChangeManager ongoingChangeManager;
	
	// Install an OngoingChangeManager (only necessary if clients plan to call
	// the IOngoingChange management functions below).
	public void setOngoingChangeManager(OngoingChangeManager manager) {
		this.ongoingChangeManager = manager;
	}

	// Forward these to the implementation.
	public void abortCurrentChange() {
		if (ongoingChangeManager != null)  ongoingChangeManager.undoCurrentChange();
	}
	public void applyCurrentChange() {
		if (ongoingChangeManager != null)  ongoingChangeManager.applyCurrentChange();
	}
	public void notifyChangeInProgress(IOngoingChange ongoingChange) {
		if (ongoingChangeManager == null)  throw new IllegalStateException();
		ongoingChangeManager.notifyChangeInProgress(ongoingChange);
	}
	public void notifyChangeDone(IOngoingChange ongoingChange) {
		if (ongoingChangeManager == null)  throw new IllegalStateException();
		ongoingChangeManager.notifyChangeDone(ongoingChange);
	}

	public void execute(Command command) {
		if (ongoingChangeManager == null)  throw new IllegalStateException();
		ongoingChangeManager.getCommandStack().execute(command);
	}
}
