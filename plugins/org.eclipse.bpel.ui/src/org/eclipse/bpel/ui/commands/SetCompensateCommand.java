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

import org.eclipse.bpel.model.Compensate;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets a compensate activity link property of a model object.
 * Supported for compensate activity
 */
public class SetCompensateCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_SELECT_COMPENSATE; }

	public SetCompensateCommand(Compensate target, EObject newActivity)  {
		super(target, newActivity);				
	}

	public Object get() {		
		return ModelHelper.getCompensated(target);
	}
	public void set(Object o) {
		if (target!=null){		
		  ModelHelper.setCompensated(target,(EObject)o);
		}
	}
	public Object getTarget(){
		return target;
	}
}
