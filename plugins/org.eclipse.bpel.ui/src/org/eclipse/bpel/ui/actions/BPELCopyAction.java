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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.BPELCopyCommand;
import org.eclipse.bpel.ui.editparts.ActivityEditPart;
import org.eclipse.bpel.ui.util.SharedImages;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;


public class BPELCopyAction extends SelectionAction {

	List objects;
	Command command;
	
	public BPELCopyAction(IWorkbenchPart editorPart) {
		super(editorPart);
	}

	protected void init() {
		super.init();
		setText(Messages.BPELCopyAction_Copy_1); 
		setToolTipText(Messages.BPELCopyAction_Copy_2); 
		setId(ActionFactory.COPY.getId());
		setImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	protected Command createCopyCommand(List objects) {
		if (objects.isEmpty()) return null;
		if (!(getWorkbenchPart() instanceof BPELEditor)) return null;

		CompoundCommand compoundCmd = new CompoundCommand(Messages.BPELCopyAction_Copy_3); 
		
		List modelObjects = new ArrayList();
		for (Iterator it = objects.iterator(); it.hasNext(); ) {
			Object object = it.next();
			if (object instanceof EObject) modelObjects.add(object);
		}
		
		BPELEditor bpelEditor = (BPELEditor)getWorkbenchPart();
		//compoundCmd.add(new RestoreSelectionCommand(viewer, true, false));
		
		BPELCopyCommand cmd = new BPELCopyCommand(bpelEditor);
		cmd.setObjectList(modelObjects);
		compoundCmd.add(cmd);
		
		return compoundCmd;
	}

	/**
	 * Returns <code>true</code> if the selected objects can
	 * be copied.  Returns <code>false</code> if there are
	 * no objects selected or the selected objects are not
	 * {@link ActivityEditPart}s.
	 */
	protected boolean calculateEnabled() {
		if (!(getWorkbenchPart() instanceof BPELEditor)) return false;
		
		List objects = getSelectedObjects();
		if (objects.isEmpty()) return false;

		// make sure all the selected objects are activities!
		for (Iterator it = objects.iterator(); it.hasNext(); ) {
			Object object = it.next();
			if (!(object instanceof Activity)) return false;
		}
		return true;
	}
	
	/**
	 * Performs the copy action on the selected objects.
	 */
	public void run() {
		List selList = getSelectedObjects();
		execute(createCopyCommand(selList));
	}
}
