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
 * Sets the "queryLanguage" property of a process.
 */
public class SetQueryLanguageCommand extends SetCommand {

	/**
	 * @param target
	 * @param newQueryLanguage
	 */
	public SetQueryLanguageCommand (EObject target, String newQueryLanguage)  {
		super(target, newQueryLanguage, BPELPackage.eINSTANCE.getProcess_QueryLanguage() );
	}
}
