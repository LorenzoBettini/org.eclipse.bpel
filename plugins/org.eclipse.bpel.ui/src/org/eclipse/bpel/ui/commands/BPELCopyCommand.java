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
package org.eclipse.bpel.ui.commands;

import java.util.List;

import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.util.TransferBuffer;


/**
 * This command is used to implement the Cut and Copy actions for BPEL model objects.
 */
public class BPELCopyCommand extends AutoUndoCommand {

	TransferBuffer.Contents undo, redo;
	BPELEditor bpelEditor;
	List originalObjects;
	
	public BPELCopyCommand(BPELEditor bpelEditor) {
		// TODO: hack: use process as modelRoot
		super(bpelEditor.getProcess());
		this.bpelEditor = bpelEditor;
	}
	
	public boolean canDoExecute() {
		return originalObjects != null && originalObjects.size() > 0;
	}
	
	public void doExecute() {
		TransferBuffer transferBuffer = bpelEditor.getTransferBuffer();
		undo = transferBuffer.getContents();
		transferBuffer.copyObjectsToTransferBuffer(originalObjects, bpelEditor.getExtensionMap());
		redo = transferBuffer.getContents();
	}
	
	public void setObjectList(List originalObjects) {
		this.originalObjects = originalObjects;
	}
}
