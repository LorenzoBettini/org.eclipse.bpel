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

/*******************************************************************************
 * A newer version of DeleteAction
 * 
 * This version customizes the selection behavior after the delete has happened
 * For example, we want to select a sibling after a particular object has been
 * deleted as a usability enhancement.
 * 
 * This version customizes the label behaviour of the DeleteAction.
 * 
 *******************************************************************************/

import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.common.ui.editmodel.AbstractEditModelCommand;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.commands.DeleteChildCommand;
import org.eclipse.bpel.ui.commands.DeleteLinkCommand;
import org.eclipse.bpel.ui.commands.RestoreSelectionCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.SharedImages;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;


/**
 * An action to delete selected objects.
 */
public class BPELDeleteAction extends SelectionAction {

	public BPELDeleteAction(IWorkbenchPart editor) {
		super(editor);
	}
	
	public BPELDeleteAction(IWorkbenchPart editor, String label) {
		super(editor);
		setText(label);
	}

	protected void init() {
		super.init();
		setText(Messages.DeleteSelectedAction_Delete_1); 
		setToolTipText(Messages.DeleteSelectedAction_Delete_2); 
		setId(ActionFactory.DELETE.getId());
		setImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_DELETE));
		setDisabledImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_DELETE_DISABLED));
		setEnabled(false);
	}

	protected Command createDeleteCommand(List objects) {
		if (objects.isEmpty()) return null;
		if (!(getWorkbenchPart() instanceof BPELEditor)) return null;

		Command cmd = null;
		CompoundCommand compoundCmd = new CompoundCommand(Messages.DeleteSelectedAction_Delete_3); 
	
		final BPELEditor bpelEditor = (BPELEditor) getWorkbenchPart();
		compoundCmd.add(new RestoreSelectionCommand(
			bpelEditor.getAdaptingSelectionProvider(), true, false));

		// workaround: deselect all the objects first,
		// avoiding the untimely notification which leads to an NPE.
		// TODO: is this still needed?  might not be, with batched adapters
		compoundCmd.add(new AbstractEditModelCommand() {
			public void execute() { bpelEditor.getAdaptingSelectionProvider().setSelection(StructuredSelection.EMPTY); }
			public Resource[] getResources() { return EMPTY_RESOURCE_ARRAY; }
			public Resource[] getModifiedResources() { return EMPTY_RESOURCE_ARRAY; }
		});
		
		int commandCount = 0;
		for (int i = 0; i < objects.size(); i++) {
			Object object = objects.get(i);
			EObject parent = null;
			if (object instanceof EObject) parent = BPELUtil.getIContainerParent((EObject)object);
			if (parent != null) {
				if (object instanceof Link) {
					cmd = new DeleteLinkCommand((Link)object);
				} else {
					cmd = new DeleteChildCommand((EObject)object);
				}
				++commandCount;
				compoundCmd.add(cmd);
			}
		}
		// override default label
		if (commandCount == 1) {
			compoundCmd.setLabel(cmd.getLabel());
		} else if (commandCount > 1) {
			compoundCmd.setLabel(NLS.bind(Messages.DeleteSelectedAction_Delete_Items_3, (new Object[] { new Integer(commandCount) }))); 
		} else {
			// commandCount is 0
			return UnexecutableCommand.INSTANCE;
		}

		return compoundCmd;
	}

	protected boolean calculateEnabled() {
		if (!(getWorkbenchPart() instanceof BPELEditor)) return false;
		
		List objects = getSelectedObjects();
		if (objects.isEmpty()) return false;
		
		// make sure at least some of the selected objects are deletable!
		for (Iterator it = objects.iterator(); it.hasNext(); ) {
			Object object = it.next();
			if (object instanceof EObject) {
				if (BPELUtil.getIContainerParent((EObject)object) != null) return true;
			}
		}
		return false;
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
		
		execute(createDeleteCommand(selList));

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
