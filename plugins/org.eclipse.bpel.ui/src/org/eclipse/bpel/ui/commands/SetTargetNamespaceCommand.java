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


public class SetTargetNamespaceCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_SELECT_TARGETNAMESPACE; }

	public SetTargetNamespaceCommand(EObject target, String newName) {
		super(target, newName);
	}

	public Object get() {
		return	((Process)target).getTargetNamespace();
	}

	public void set(Object o) {
		((Process)target).setTargetNamespace((String)o);
	}
}