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
package org.eclipse.bpel.ui.details.providers;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Variable;


/**
 * Content provider for variables.
 * 
 * Expects a Process or a Scope as input.
 */
public class LocalVariableContentProvider extends AbstractContentProvider  {

	protected boolean needValidMessage; // if true, shows ONLY variables with valid messages
	
	/**
	 * @param needValidMessage if true, shows ONLY variables with valid messages
	 */
	public LocalVariableContentProvider(boolean needValidMessage) {
		this.needValidMessage = needValidMessage;
	}
	
	public LocalVariableContentProvider() {
		this.needValidMessage = false;
	}
	
	public Object[] getElements(Object input)  {
		if (input instanceof Process) {
			if (!needValidMessage)
				return ((Process)input).getVariables().getChildren().toArray();

			Vector v = new Vector();
			Iterator it = ((Process)input).getVariables().getChildren().iterator();
			while (it.hasNext()) {
				Variable var = (Variable) it.next();
				if (var.getMessageType() != null)
					v.add(var);
			}
			
			return v.toArray();
		}
		
		if (input instanceof Scope) {
			if(!(((Scope)input).getVariables() == null) ){
				Vector v = new Vector();
				Iterator it = ((Scope)input).getVariables().getChildren().iterator();
				while (it.hasNext()) {
					Variable var = (Variable) it.next();
					if (var.getMessageType() != null || var.getType() != null)
						v.add(var);
				}
				return v.toArray();
			}
		}
		return EMPTY_ARRAY;
	}

}
