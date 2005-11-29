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
package org.eclipse.bpel.common.ui.details;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;

/**
 * TODO: need class comment for OngoingChangeManager!
 */
public class OngoingChangeManager {

	protected static class DelegatedCommandStack extends CommandStack {
		CommandStack delegate;

		public DelegatedCommandStack(CommandStack delegate) {
			this.delegate = delegate;
		}
		public void addCommandStackListener(CommandStackListener listener) {
			delegate.addCommandStackListener(listener);
		}
		public boolean canRedo() {
			return delegate.canRedo();
		}
		public boolean canUndo() {
			return delegate.canUndo();
		}
		public void dispose() {
			delegate.dispose();
		}
		public void execute(Command command) {
			delegate.execute(command);
		}
		public void flush() {
			delegate.flush();
		}
		public Object[] getCommands() {
			return delegate.getCommands();
		}
		public Command getRedoCommand() {
			return delegate.getRedoCommand();
		}
		public Command getUndoCommand() {
			return delegate.getUndoCommand();
		}
		public int getUndoLimit() {
			return delegate.getUndoLimit();
		}
		public boolean isDirty() {
			return delegate.isDirty();
		}
		public void markSaveLocation() {
			delegate.markSaveLocation();
		}
		public void redo() {
			delegate.redo();
		}
		public void removeCommandStackListener(CommandStackListener listener) {
			delegate.removeCommandStackListener(listener);
		}
		public void setUndoLimit(int undoLimit) {
			delegate.setUndoLimit(undoLimit);
		}
		public String toString() {
			return "(" + super.toString() + " --> " + delegate.toString() + ")";
		} //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		public void undo() {
			delegate.undo();
		}
		// NOTE: there is no point in increasing the visibility of notifyListeners()
		// here because we can't call the protected method delegate.notifyListeners().
		// See HACK in setDelayedRedoCommand().
	}

	// see class comment in MandatoryCommand.
	protected static class PlaceholderCommand extends Command { }

	protected PlaceholderCommand createPlaceholderCommand() {
		return new PlaceholderCommand();
	}
	
	protected class ManagedCommandStack extends DelegatedCommandStack {
		// This can hold a command which is conceptually at the top of the redo stack,
		// but has not actually executed.  The first time a redo() is invoked with
		// this present, the command's execute() will be called and thereafter it will
		// be undone/redone normally. 
		Command delayedRedoCommand;
		PlaceholderCommand delayHack = createPlaceholderCommand();

		public void setDelayedRedoCommand(Command cmd) {
			this.delayedRedoCommand = cmd;
			// fuggly HACK: we need to inform the delegate CommandStack's listeners that
			// the Undo/Redo enablement, labels etc. may have changed, but we can't call
			// the delegate's protected notifyListeners() method.
			//
			// So we assume that the old contents of redo stack are no longer useful:
			super.execute(delayHack);
			super.undo();
		}
		public ManagedCommandStack(CommandStack delegate) {
			super(delegate);
			delayedRedoCommand = null;
		}
		public void execute(Command command) {
			applyCurrentChange();
			if (delayedRedoCommand != null && command.canExecute()) {
				// super.execute() will discard the redo stack.
				delayedRedoCommand = null;
			}
			super.execute(command);
		}
		public void undo() {
			if (currentChange != null)
				undoCurrentChange();
			else
				super.undo();
		}
		public boolean canRedo() {
			if (delayedRedoCommand != null)
				return delayedRedoCommand.canExecute();
			//if (currentChange != null) return false;
			return !(super.getRedoCommand() instanceof PlaceholderCommand)
				&& super.canRedo();
		}
		public Command getRedoCommand() {
			if (delayedRedoCommand != null)
				return delayedRedoCommand;
			//if (currentChange != null) return null;
			Command redoCmd = super.getRedoCommand();
			return (redoCmd instanceof PlaceholderCommand) ? null : redoCmd;
		}
		public void redo() {
			if (delayedRedoCommand != null) {
				execute(delayedRedoCommand);
			} else {
				super.redo();
			}
		}

	}

	protected IOngoingChange currentChange;
	protected PlaceholderCommand placeholderCommand;
	protected ManagedCommandStack commandStack;

	/**
	 * Constructs an OngoingChangeManager wrapped around the given commandStack.
	 * 
	 * IMPORTANT: Before using the OngoingChangeManager, you should replace the
	 * original CommandStack with the one returned by getCommandStack(). 
	 */
	public OngoingChangeManager(CommandStack commandStack) {
		this.commandStack = new ManagedCommandStack(commandStack);
		placeholderCommand = createPlaceholderCommand();
	}

	public CommandStack getCommandStack() {
		return commandStack;
	}

	public void notifyChangeInProgress(IOngoingChange ongoingChange) {
		if (currentChange != ongoingChange) {
			//System.out.println("notifyChangeInProgress("+ongoingChange+")");
			applyCurrentChange();
			// Careful: currentChange must be null while we execute the placeholder.
			placeholderCommand.setLabel(ongoingChange.getLabel());
			commandStack.execute(placeholderCommand);
			currentChange = ongoingChange;
		}
	}

	public void notifyChangeDone(IOngoingChange ongoingChange) {
		if (currentChange == ongoingChange)
			applyCurrentChange();
	}

	protected void finishCurrentChange(boolean changeUndone) {
		if (currentChange != null) {
			IOngoingChange change = currentChange;
			currentChange = null;
			if (!(commandStack.getUndoCommand()
				instanceof PlaceholderCommand)) {
				throw new IllegalStateException();
			}
			commandStack.undo(); // remove the PlaceholderCommand
			Command cmd = change.createApplyCommand();
			if (cmd != null) {
				cmd.setLabel(placeholderCommand.getLabel());
				if (changeUndone) {
					commandStack.setDelayedRedoCommand(cmd);
					change.restoreOldState();
				} else {
					// TODO: if the command is not actually executable, should we call
					// restoreOldState() instead?  I'm inclined not to because we've been
					// using !canExecute() to elide no-op commands.  But maybe we should
					// rethink that (especially since IOngoingChange makes it much less
					// common, and maybe confusing, to elide a command in that way?).
					commandStack.execute(cmd);
				}
			} else {
				change.restoreOldState();
			}
		}
	}

	public void applyCurrentChange() {
		finishCurrentChange(false);
	}

	// TODO: consider not exposing this (the only sender is in this class, right?).
	public void undoCurrentChange() {
		finishCurrentChange(true);
	}

}
