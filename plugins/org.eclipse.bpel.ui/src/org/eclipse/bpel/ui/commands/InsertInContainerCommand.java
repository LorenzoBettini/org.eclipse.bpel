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

import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;


/** 
 * This command is used to add a child into a parent object which supports IContainer. 
 */
public class InsertInContainerCommand extends AutoUndoCommand {
	
	protected EObject child, parent, before;
	protected Rectangle rect;
	
	public InsertInContainerCommand(EObject parent, EObject child, EObject before) {
		super(Messages.InsertInContainerCommand_Add_Node_1, parent); 
		this.parent = parent;
		this.child = child;
		this.before = before;
		ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(child, ILabeledElement.class);
		String childType = null;
		if (labeledElement != null) childType = labeledElement.getTypeLabel(child);
		if (childType == null) childType = Messages.InsertInContainerCommand_Node_3; 
		setLabel(NLS.bind(Messages.InsertInContainerCommand_Add_1, (new Object[] { childType }))); 
	}

	public boolean canDoExecute() {
		IContainer container = (IContainer)BPELUtil.adapt(parent, IContainer.class);
		return container.canAddObject(parent, child, before);
	}

	public void doExecute() {
		IContainer container = (IContainer)BPELUtil.adapt(parent, IContainer.class);		
		container.addChild(parent, child, before);
	}
	
	public EObject getChild() { return child; }
}
