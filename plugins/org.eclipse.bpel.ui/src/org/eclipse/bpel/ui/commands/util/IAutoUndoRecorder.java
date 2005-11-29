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
package org.eclipse.bpel.ui.commands.util;

import java.util.List;

public interface IAutoUndoRecorder {

	// These methods are used by AutoUndoCommandStack and AutoUndoCompoundCommand.
	public void startChanges(List modelRoots);
	public List finishChanges();
	public void addModelRoots(List modelRoots);
	public void insertUndoHandler(IUndoHandler undoHandler);
	public boolean isRecordingChanges();
	
	// These methods are used by AutoUndoCommand.
	public void undo(List changes);
	public void redo(List changes);	
}
