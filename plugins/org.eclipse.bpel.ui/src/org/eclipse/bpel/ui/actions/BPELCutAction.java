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
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.commands.BPELCopyCommand;
import org.eclipse.bpel.ui.commands.DeleteChildCommand;
import org.eclipse.bpel.ui.commands.RestoreSelectionCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.SharedImages;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;


public class BPELCutAction extends SelectionAction {
	
	List objects;
	Command command;
	
	public BPELCutAction(IWorkbenchPart editorPart) {
		super(editorPart);
	}

	protected void init() {
		super.init();
		setText(Messages.BPELCutAction_Cut_1); 
		setToolTipText(Messages.BPELCutAction_Cut_2); 
		setId(ActionFactory.CUT.getId());
		setImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_CUT_DISABLED));
		setEnabled(false);
	}

	protected Command createCutCommand(List objects) {
		if (objects.isEmpty()) return null;
		if (!(getWorkbenchPart() instanceof BPELEditor)) return null;

		Command cmd = null;
		CompoundCommand compoundCmd = new CompoundCommand(Messages.BPELCutAction_Cut_3); 
		
		BPELEditor bpelEditor = (BPELEditor)getWorkbenchPart();
		compoundCmd.add(new RestoreSelectionCommand(
			bpelEditor.getAdaptingSelectionProvider(), true, false));

		List modelObjects = new ArrayList();
		for (Iterator it = objects.iterator(); it.hasNext(); ) {
			Object object = it.next();
			if (object instanceof EObject) modelObjects.add(object);
		}
		
		BPELCopyCommand copyCmd = new BPELCopyCommand(bpelEditor);
		copyCmd.setObjectList(modelObjects);
		compoundCmd.add(copyCmd);

		// workaround: deselect all the objects first,
		// avoiding the untimely notification which leads to an NPE.
		// TODO: is this still needed?  might not be, with batched adapters
//		compoundCmd.add(new AbstractEditModelCommand() {
//			public void execute() { viewer.deselectAll(); }
//			public Resource[] getResources() { return EMPTY_RESOURCE_ARRAY; }
//			public Resource[] getModifiedResources() { return EMPTY_RESOURCE_ARRAY; }
//		});

		int commandCount = 0;
		for (int i = 0; i < objects.size(); i++) {
			Object object = objects.get(i);
			EObject parent = null;
			if (object instanceof EObject) parent = BPELUtil.getIContainerParent((EObject)object);
			if (parent != null) {
				cmd = new DeleteChildCommand((EObject)object);
				++commandCount;
				compoundCmd.add(cmd);
			}
		}

		return compoundCmd;
	}

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
	
	public void run() {
		List selList = getSelectedObjects();

		// get the sibling of the last selected object
		Object sibling = null;
		int i = selList.size()-1;
		if (i >= 0) {
			Object modelObject = selList.get(i);
			EObject parent = null;
			if (modelObject instanceof EObject) parent = BPELUtil.getIContainerParent((EObject)modelObject);
			if (parent != null) {
				IContainer container = (IContainer)BPELUtil.adapt(parent, IContainer.class);
				if (container != null) {
					sibling = container.getNextSiblingChild(parent, modelObject);
					// make sure that this sibling isn't contained by anything in the selection
					for (int j = 0; j < selList.size(); j++) {
						if (j != i) {							
							if (ModelHelper.isChildContainedBy(selList.get(j), modelObject)) {
								sibling = null;
								break;
							}
						}
					}
				}
			}
			if (sibling == null) sibling = parent;
		}
		
		execute(createCutCommand(selList));
		
		BPELEditor bpelEditor = (BPELEditor)getWorkbenchPart();
		// TODO: make sure setFocus() hack makes it into RestoreSelectionCommand
		bpelEditor.setFocus();
		if (sibling == null) {
			bpelEditor.getAdaptingSelectionProvider().setSelection(StructuredSelection.EMPTY);
		} else {
			bpelEditor.getAdaptingSelectionProvider().setSelection(new StructuredSelection(sibling));
		}
	}
	
}
