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

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.BPELPasteCommand;
import org.eclipse.bpel.ui.commands.RestoreSelectionCommand;
import org.eclipse.bpel.ui.util.SharedImages;
import org.eclipse.bpel.ui.util.TransferBuffer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;


public class BPELPasteAction extends SelectionAction {
	
	List objects;
	Command command;
	
	public BPELPasteAction(IWorkbenchPart editorPart) {
		super(editorPart);
	}

	protected void init() {
		super.init();
		setText(Messages.BPELPasteAction_Paste_1); 
		setToolTipText(Messages.BPELPasteAction_Paste_2); 
		setId(ActionFactory.PASTE.getId());
		setImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(SharedImages.getWorkbenchImageDescriptor(
			ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(false);
	}

	protected static EObject getPasteTarget(BPELEditor bpelEditor, List selectedObjects) {
		if (selectedObjects.size() > 1)  return null;

		EObject model = bpelEditor.getProcess();
		if ((selectedObjects.size() == 1) && (selectedObjects.get(0) instanceof EObject)) {
			model = (EObject)selectedObjects.get(0);
		}

		TransferBuffer tb = bpelEditor.getTransferBuffer();

		if (model instanceof Process) {
			// Special case: if the Process has a child, and this child is an IContainer, and
			// we can paste our stuff into this child successfully, then we should do that
			// instead of pasting it into the Process.  (For top-level sequence/flow, etc).
			// TODO: is this still a good idea?  (maybe not since implicit seqs are supported at
			// the top level)
			Activity activity = ((Process)model).getActivity();

			if (tb.canCopyTransferBufferToIContainer(activity))  return activity;
		}
		if (tb.canCopyTransferBufferToIContainer(model))  return model;
		return null;
	}

	/**
	 * Create a command to paste previously copied objects into the selected object.
	 */
	public static Command createPasteCommand(BPELEditor bpelEditor, EObject pasteTarget) {
		CompoundCommand compoundCmd = new CompoundCommand(Messages.BPELPasteAction_Paste_3); 
		
		compoundCmd.add(new RestoreSelectionCommand(bpelEditor.getAdaptingSelectionProvider(), true, false));

		BPELPasteCommand cmd = new BPELPasteCommand(bpelEditor);
		cmd.setTargetObject(pasteTarget);
		compoundCmd.add(cmd);

		return compoundCmd;
	}

	protected boolean calculateEnabled() {
		if (!(getWorkbenchPart() instanceof BPELEditor)) return false;
		
		BPELEditor bpelEditor = (BPELEditor)getWorkbenchPart();
		if (bpelEditor.getTransferBuffer().getContents() == null) return false;

		return getPasteTarget(bpelEditor, getSelectedObjects()) != null;
	}
	
	public void run() {
		if (!(getWorkbenchPart() instanceof BPELEditor)) return;

		BPELEditor bpelEditor = (BPELEditor)getWorkbenchPart();
		execute(createPasteCommand(bpelEditor, getPasteTarget(bpelEditor, getSelectedObjects())));	
	}
}
