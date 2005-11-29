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

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets the "queryLanguage" property of a process.
 */
public class SetQueryLanguageCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_QUERYLANGUAGE; }

	public SetQueryLanguageCommand(EObject target, String newQueryLanguage)  {
		super(target, newQueryLanguage);
	}

	public Object get() {
		return ((Process)target).getQueryLanguage();
	}
	public void set(Object o) {
		((Process)target).setQueryLanguage((String)o);
	}
}
