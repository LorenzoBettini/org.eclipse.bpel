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
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.emf.ecore.EObject;


/** 
 * Generic "model-setting" command.  Subclasses only need to implement get() and set()
 * in terms of the particular model property they set.
 */
public abstract class SetCommand extends AutoUndoCommand {

	protected Object target, newValue, oldValue;
	boolean executeWasSkipped = false;

	// TODO: THIS SHOULDN'T EXIST.  FIX.
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	public String getDefaultLabel() { return Messages.SetCommand_Change_1; } 

	/**
	 * Brand new shiny set command.
	 * @param target
	 * @param newValue
	 */
	
	public SetCommand (EObject target, Object newValue)  {
		super(target);
		this.target = target;
		this.newValue = newValue;
		setLabel(getDefaultLabel());
	}

	public abstract Object get();
	public abstract void set(Object o);


	protected boolean hasNoEffect()  {
		if (oldValue == null) return (newValue == null);
		if (newValue == null) return false;
		return newValue.equals(oldValue);
	}

	/**
	 * @see org.eclipse.bpel.ui.commands.util.AutoUndoCommand#canDoExecute()
	 */
	@Override
	public boolean canDoExecute() {
		return true;
	}

	/**
	 * @see org.eclipse.bpel.ui.commands.util.AutoUndoCommand#doExecute()
	 */
	@Override
	public void doExecute() {
		oldValue = get();
		if (hasNoEffect()) {
			executeWasSkipped = true;
		} else {
			set(newValue);
		}
	}
	
	
	// TODO!
//	public Resource[] getModifiedResources() {
//		if (executeWasSkipped) return EMPTY_RESOURCE_ARRAY;
//		return getResources();
//	}
}
