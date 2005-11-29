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

import java.util.EventObject;

import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.bpel.common.ui.editmodel.EditModelCommandStack;
import org.eclipse.bpel.common.ui.editmodel.PlaceHolderCommand;
import org.eclipse.bpel.common.ui.editmodel.EditModelCommandStack.SharedCommandStackChangedEvent;
import org.eclipse.bpel.common.ui.editmodel.EditModelCommandStack.SharedCommandStackListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;


/**
 * Support for using IOngoingChange with the EditModel framework.
 */
public class EditModelCommandFramework implements ICommandFramework {

	IOngoingChange currentChange;
	CommandStack commandStack;
	
	boolean ignoreEvents = false;
	
	public EditModelCommandFramework(EditModelCommandStack editModelCommandStack) {
		this.commandStack = editModelCommandStack;
		editModelCommandStack.addCommandStackListener(new CommandStackListener() {
			public void commandStackChanged(EventObject event) {
				if (ignoreEvents) return;
				if (event instanceof SharedCommandStackChangedEvent) {
					SharedCommandStackChangedEvent e = (SharedCommandStackChangedEvent)event;
					// Finish up the change in progress before we execute something else
					if (e.getProperty() == SharedCommandStackListener.EVENT_START_EXECUTE) {
						applyCurrentChange();
					}
					// FIXME: what about redo?
					if(e.getProperty() == SharedCommandStackListener.EVENT_START_UNDO) {
						if(commandStack.getUndoCommand() instanceof PlaceHolderCommand) {
							// TODO: what is this for again?
							if(currentChange != null)
								currentChange.restoreOldState();
						}
					}
				}
			}
		});
	}
	// Forward these to the implementation.
	public void abortCurrentChange() {
		finishCurrentChange(true);
	}
	public void applyCurrentChange() {
		finishCurrentChange(false);
	}
	public void notifyChangeInProgress(IOngoingChange ongoingChange) {
		if (currentChange != ongoingChange) {
			applyCurrentChange();
			if (commandStack.getUndoCommand() instanceof PlaceHolderCommand) {
				throw new IllegalStateException();
			}
			PlaceHolderCommand placeholderCommand = new PlaceHolderCommand(ongoingChange.getLabel());
			ignoreEvents = true;
			try {
				commandStack.execute(placeholderCommand);
			} finally {
				ignoreEvents = false;
			}
			currentChange = ongoingChange;
		}
	}
	public void notifyChangeDone(IOngoingChange ongoingChange) {
		if (currentChange == ongoingChange)
			applyCurrentChange();
	}
	public void execute(Command command) {
		commandStack.execute(command);
	}
	protected void finishCurrentChange(boolean changeUndone) {
		if (currentChange == null) return;
		
		IOngoingChange change = currentChange;
		currentChange = null;
		// Make sure there's a placeholder on the stack.
		if (ignoreEvents) {
			throw new IllegalStateException();
		}
		if (!(commandStack.getUndoCommand() instanceof PlaceHolderCommand)) {
			throw new IllegalStateException();
		}
		ignoreEvents = true;
		try {
			commandStack.undo(); // Remove placeholder.
		} finally {
			ignoreEvents = false;
		}
		
		Command cmd = change.createApplyCommand();
		if (cmd != null) {
			cmd.setLabel(change.getLabel());
			if (changeUndone) {
				change.restoreOldState();
			} else {
				// TODO: if the command is not actually executable, should we call
				// restoreOldState() instead?  I'm inclined not to because we've been
				// using !canExecute() to elide no-op commands.  But maybe we should
				// rethink that (especially since IOngoingChange makes it much less
				// common, and maybe confusing, to elide a command in that way?).

				// TODO: above comment is obsolete.  Now that no-ops are handled in
				// a different way by EditModelCommandStack, we should consider calling
				// restoreOldState() if canExecute() fails.
				commandStack.execute(cmd);
			}
		} else {
			change.restoreOldState();
		}
	}
}
