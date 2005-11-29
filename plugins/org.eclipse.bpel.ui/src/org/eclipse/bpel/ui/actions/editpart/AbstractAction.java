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
package org.eclipse.bpel.ui.actions.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;


public abstract class AbstractAction implements IEditPartAction {
	protected final Object modelObject;
	protected final EditPart editPart;
	protected final EditPartViewer viewer;

	public AbstractAction(EditPart editPart) {
		super();
		this.modelObject = editPart.getModel();
		this.editPart = editPart;
		this.viewer = editPart.getViewer();
	}

	// default implementations for uncommonly-used methods
	public void onDispose() { }
	public void onCreate() { }
}