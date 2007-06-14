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

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.emf.ecore.EObject;

/**
 * 
 */

public class SetTargetNamespaceCommand extends SetCommand {

	/**
	 * Brand new shiny SetTargetNamespaceCommand.
	 * 
	 * @param target
	 * @param newName
	 */
	public SetTargetNamespaceCommand(EObject target, String newName) {
		super(target, newName,BPELPackage.eINSTANCE.getProcess_TargetNamespace() );
	}
}