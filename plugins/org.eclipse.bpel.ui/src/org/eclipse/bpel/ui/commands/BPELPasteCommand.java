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

import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.util.TransferBuffer;
import org.eclipse.emf.ecore.EObject;


/**
 * This command is used to implement the Paste action for BPEL model objects.
 */
public class BPELPasteCommand extends AutoUndoCommand {

	BPELEditor bpelEditor;
	EObject targetObject;
	
	public BPELPasteCommand(BPELEditor bpelEditor) {
		// TODO: hack: use process as modelRoot
		super(bpelEditor.getProcess());
		this.bpelEditor = bpelEditor;
	}
	
	public boolean canDoExecute() {
		if (targetObject == null) return false;
		return bpelEditor.getTransferBuffer().canCopyTransferBufferToIContainer(targetObject);
	}
	
	public void doExecute() {
		TransferBuffer transferBuffer = bpelEditor.getTransferBuffer();
		transferBuffer.copyTransferBufferToIContainer(targetObject, bpelEditor.getExtensionMap());
	}
	
	public void setTargetObject(EObject targetObject) {
		this.targetObject = targetObject;
	}
}
