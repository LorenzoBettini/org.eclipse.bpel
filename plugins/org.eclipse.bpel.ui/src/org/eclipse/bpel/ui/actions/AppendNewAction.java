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

import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.commands.InsertInContainerCommand;
import org.eclipse.bpel.ui.commands.SetNameAndDirectEditCommand;
import org.eclipse.bpel.ui.editparts.BPELEditPart;
import org.eclipse.bpel.ui.editparts.OutlineTreeEditPart;
import org.eclipse.bpel.ui.editparts.util.ReferencedAddRequest;
import org.eclipse.bpel.ui.factories.AbstractUIObjectFactory;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchPart;



public class AppendNewAction extends SelectionAction {
	
	protected AbstractUIObjectFactory factory;
	protected Object cachedInstance;  // used for IContainer.canAddObject()
	
	public AppendNewAction(IWorkbenchPart editor, AbstractUIObjectFactory factory) {
		this(editor, factory, factory.getTypeLabel());
	}
	
	public AppendNewAction(IWorkbenchPart editor, AbstractUIObjectFactory factory, String label) {
		super(editor);
		this.factory = factory;
		cachedInstance = factory.getNewObject();
		setId(calculateID());
		setText(label);
		setToolTipText(NLS.bind(Messages.AppendNewAction_Add_a_1, (new Object[] { getText() }))); 
		setImageDescriptor(factory.getSmallImageDescriptor());
	}

	protected void init() {
		super.init();
		setEnabled(false);
	}

	protected String calculateID() {
		return "appendNew."+factory.getUniqueIdString(); //$NON-NLS-1$
	}
	
	// TODO: fix the getCreateCommand() story so this can go away
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

	public Command createAppendNewCommand(List objects) {
		if (objects.isEmpty()) return null;
		if (!(getWorkbenchPart() instanceof BPELEditor)) return null;

		if (!(objects.get(0) instanceof BPELEditPart) &&
			(!(objects.get(0) instanceof OutlineTreeEditPart)))
			return null;

		// TODO: how to do this without relying on the viewer??

		BPELEditor bpelEditor = (BPELEditor)getWorkbenchPart();
		EditPartViewer viewer = bpelEditor.getGraphicalViewer();
			
		ReferencedAddRequest addreq = new ReferencedAddRequest();
		addreq.setType(ReferencedAddRequest.typeString);
		addreq.setFactory(factory);
		CompoundCommand compoundCmd = new CompoundCommand();
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			Command cmd = object.getCommand(addreq);
			if (cmd != null) {
				compoundCmd.add(cmd);
				compoundCmd.setLabel(cmd.getLabel());
				// hack!
				Object model = findModelObject(cmd);
				if (model != null) {
					compoundCmd.add(new SetNameAndDirectEditCommand(model, viewer));
				}
			}
		}
		return compoundCmd;
	}

	protected boolean calculateEnabled() {
		if (!(getWorkbenchPart() instanceof BPELEditor)) return false;

		List objects = getSelectedObjects();
		if (objects.size() != 1) return false;

		if (!(objects.get(0) instanceof BPELEditPart)
			&& (!(objects.get(0) instanceof OutlineTreeEditPart)))
			return false;

		// not enabled unless the selected object is a container
		Object modelObject = ((EditPart)objects.get(0)).getModel();
		if (!(modelObject instanceof EObject)) return false;
		IContainer container = (IContainer)BPELUtil.adapt(modelObject, IContainer.class);
		if (container == null) return false;
		// make sure it can contain something of this type
		return (container.canAddObject(modelObject, cachedInstance, null));
	}

	public void run() {
		execute(createAppendNewCommand(getSelectedObjects()));
	}
	
}
