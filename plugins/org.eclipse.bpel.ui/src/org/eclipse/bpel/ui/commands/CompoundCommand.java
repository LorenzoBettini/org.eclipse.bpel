package org.eclipse.bpel.ui.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.gef.commands.Command;

public class CompoundCommand extends AutoUndoCommand {
	private List<Command> commandList = new ArrayList<Command>();
	
	public CompoundCommand() { 
		super(new ArrayList<Object>());
	}

	public CompoundCommand(String label) {
		super(label, new ArrayList<Object>());
	}
	
	public void add(Command command) {
		if (command != null) {
			if (command instanceof AutoUndoCommand) {
				addModelRoots(((AutoUndoCommand)command).getModelRoots());
			}
			commandList.add(command);
		}
	}
	
	public boolean canExecute() {
		if (commandList.size() == 0)
			return false;
		for (Command cmd : commandList) {
			if (cmd == null)
				return false;
			if (!cmd.canExecute())
				return false;
		}
		return true;
	}
	
	public void dispose() {
		for (Command cmd : commandList)
			cmd.dispose();
	}
	
	public void doExecute() {
		for (Command cmd : commandList) {
			if (cmd instanceof AutoUndoCommand) {
				((AutoUndoCommand)cmd).doExecute();
			} else {
				cmd.execute();
			}			
		}
	}
	
	public String getLabel() {
		String label = super.getLabel();
		if (label == null)
			if (commandList.isEmpty())
				return null;
		if (label != null)
			return label;
		return commandList.get(0).getLabel();
	}
	
	public boolean isEmpty() {
		return commandList.isEmpty();
	}
	
	public List<Command> getCommands() {
		return commandList;
	}
}
