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
package org.eclipse.bpel.ui.actions;

import java.util.List;

import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.InsertInContainerCommand;
import org.eclipse.bpel.ui.commands.SetNameAndDirectEditCommand;
import org.eclipse.bpel.ui.editparts.BPELEditPart;
import org.eclipse.bpel.ui.editparts.OutlineTreeEditPart;
import org.eclipse.bpel.ui.editparts.util.ReferencedAddRequest;
import org.eclipse.bpel.ui.factories.AbstractUIObjectFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchPart;


/**
 * This action allows the user to insert a new object into a container at a
 * specific spot 
 */
public class InsertNewAction extends SelectionAction {

	protected AbstractUIObjectFactory factory;
	
	protected void init() {
		super.init();
		setEnabled(false);
	}

	public InsertNewAction(IWorkbenchPart editor, AbstractUIObjectFactory factory) {
		this(editor, factory, factory.getTypeLabel());
	}
	
	public InsertNewAction(IWorkbenchPart editor, AbstractUIObjectFactory factory, String label) {
		super(editor);
		this.factory = factory;
		setId(calculateID());
		setText(label);
		setToolTipText(NLS.bind(Messages.InsertNewAction_Insert_a, (new Object[] { getText() }))); 
		setImageDescriptor(factory.getSmallImageDescriptor());
	}

	protected String calculateID() {
		return "insertNew." + factory.getUniqueIdString(); //$NON-NLS-1$
	}

	// TODO: fix the getCreateCommand() story so this code can go away
	protected Object findModelObject(Command cmd) {
		if (cmd instanceof InsertInContainerCommand) {
			return ((InsertInContainerCommand)cmd).getChild();
		}
		if (cmd instanceof CompoundCommand) {
			CompoundCommand compoundCmd = (CompoundCommand)cmd;
			Object[] cmds = compoundCmd.getChildren();
			for (int i = 0; i<cmds.length; i++) {
				Object result = findModelObject((Command)cmds[i]);
				if (result != null)  return null;
			}
		}
		return null;
	}

	public Command createInsertCommand(List objects) {
		if (objects.isEmpty())
			return null;
		if (!(objects.get(0) instanceof BPELEditPart) &&
				(!(objects.get(0) instanceof OutlineTreeEditPart)))
				return null;

		final EditPartViewer viewer = ((EditPart)objects.get(0)).getViewer();
			
		ReferencedAddRequest insertionRequest = new ReferencedAddRequest();
		insertionRequest.setType(ReferencedAddRequest.typeString);
		insertionRequest.setFactory(factory);
		CompoundCommand compoundCmd = new CompoundCommand();
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			
			insertionRequest.setReferencedObject(object.getModel());
			
			// the container should be the one that's allowed to contribute
			// the insertion command 
			object = object.getParent();
			
			final Command cmd = object.getCommand(insertionRequest);
			if (cmd != null) {
				compoundCmd.add(cmd);
				compoundCmd.setLabel(cmd.getLabel());
				
				// hack!
				final Object model = findModelObject(cmd);
				if (model != null) {
					compoundCmd.add(new SetNameAndDirectEditCommand(model, viewer));
				}

			}
		}
		return compoundCmd;
	}

	protected boolean calculateEnabled() {
		Command cmd = createInsertCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	public void run() {
		execute(createInsertCommand(getSelectedObjects()));
	}
	
}
