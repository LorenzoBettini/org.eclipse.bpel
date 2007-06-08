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
	
	/**
	 * 
	 * @param aParent parent container
	 * @param aChild the child object
	 * @param aBeforeMarker the before marker object
	 */
	public InsertInContainerCommand(EObject aParent, EObject aChild, EObject aBeforeMarker) {
		super(Messages.InsertInContainerCommand_Add_Node_1, aParent); 
		
		this.parent = aParent;
		this.child = aChild;
		this.before = aBeforeMarker;
		
		ILabeledElement labeledElement = BPELUtil.adapt(child, ILabeledElement.class);
		String childType = null;
		
		if (labeledElement != null) {
			childType = labeledElement.getTypeLabel(child);
		}
		if (childType == null) {
			childType = Messages.InsertInContainerCommand_Node_3; 
		}
		
		setLabel(NLS.bind(Messages.InsertInContainerCommand_Add_1, (new Object[] { childType }))); 
	}

	/**
	 * @see org.eclipse.bpel.ui.commands.util.AutoUndoCommand#canDoExecute()
	 */
	@Override
	public boolean canDoExecute() {
		IContainer container = BPELUtil.adapt(parent, IContainer.class);
		return container.canAddObject(parent, child, before);
	}

	/**
	 * @see org.eclipse.bpel.ui.commands.util.AutoUndoCommand#doExecute()
	 */
	@Override
	public void doExecute() {
		IContainer container = BPELUtil.adapt(parent, IContainer.class);		
		container.addChild(parent, child, before);
	}
	
	/**
	 * @return the child object.
	 */
	public EObject getChild() { return child; }
}
