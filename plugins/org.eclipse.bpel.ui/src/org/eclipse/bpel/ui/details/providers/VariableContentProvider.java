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

import java.util.Vector;

import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;


/**
 * Content provider for variables.
 * 
 * Provides all the Variables visible in a given context.
 */
public class VariableContentProvider extends AbstractContentProvider  {

	public Object[] getElements(Object input)  {
		Variable[] vars = BPELUtil.getVisibleVariables((EObject)input);
		// TODO: I think this code should be moved to a filter?
		Vector returnVector = new Vector();
		for (int i = 0; i<vars.length; i++) {
			Variable var = (Variable)vars[i];
			if (var.getMessageType() != null || var.getType() != null) {
				returnVector.add(vars[i]);
			}
		}
		return returnVector.toArray();
	}
}
