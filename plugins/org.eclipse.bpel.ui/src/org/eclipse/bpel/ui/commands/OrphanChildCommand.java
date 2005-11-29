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

import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;


public class OrphanChildCommand extends AutoUndoCommand {

	private EObject child;
	private EObject parent;
	
	public OrphanChildCommand(Object child) {
		super(BPELUtil.getIContainerParent((EObject)child));
		this.child = (EObject)child;
		this.parent = BPELUtil.getIContainerParent(this.child);
	}

	public boolean canDoExecute() {
		if ((child==null) || (parent==null))  return false;
		if (BPELUtil.adapt(parent, IContainer.class) == null) return false;
		return true;
	}

	public void doExecute() {
		if (!canExecute())  throw new IllegalStateException();
		IContainer container = (IContainer)BPELUtil.adapt(parent, IContainer.class);
		container.removeChild(parent, child);
	}
}
