/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.commands;

import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets the "validate" property of an Assign object.
 */
public class SetValidateCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_VALIDATE_XML; }

	public SetValidateCommand(EObject target, Boolean newValue)  {
		super(target, newValue);
	}

	public Object get() {
		return ModelHelper.getValidate(target);
	}
	public void set(Object o) {
		ModelHelper.setValidate(target, (Boolean)o);
	}
}
