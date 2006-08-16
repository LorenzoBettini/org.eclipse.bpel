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

import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;


/**
 * Adds a Variable to either a process or scope.
 * 
 */
public class AddVariableCommand extends AddToListCommand {
		
	public AddVariableCommand (EObject context, Variable var) {
		super(ModelHelper.getContainingScope(context), var, IBPELUIConstants.CMD_ADD_VARIABLE);	
	}
	
	protected List getList() {
		return ModelHelper.getVariables( target ).getChildren();		
	}
}
