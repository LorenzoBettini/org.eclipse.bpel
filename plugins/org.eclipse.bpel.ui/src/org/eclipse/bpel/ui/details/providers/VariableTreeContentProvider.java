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
import org.eclipse.bpel.ui.details.tree.BPELVariableTreeNode;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;


/**
 * Provides a tree of model objects representing some expansion of the underlying graph
 * of model objects whose roots are the Variables of a Process. 
 */
public class VariableTreeContentProvider extends ModelTreeContentProvider {

	boolean isPropertyTree;
	boolean displayParticles;
	
	public VariableTreeContentProvider(boolean isCondensed, boolean isPropertyTree, boolean displayParticles) {
		super(isCondensed);
		this.isPropertyTree = isPropertyTree;
		this.displayParticles = displayParticles;
	}

	public boolean isPropertyTree() { return isPropertyTree; }

	public Object[] primGetElements(Object inputElement) {
		Variable[] vars = BPELUtil.getVisibleVariables((EObject)inputElement);
		// TODO: can this code be moved to a filter?
		Vector returnVector = new Vector();
		for (int i = 0; i<vars.length; i++) {
			if (vars[i] instanceof Variable) {
				returnVector.add(new BPELVariableTreeNode((Variable)vars[i], isCondensed, isPropertyTree, displayParticles));
			} else {
				throw new IllegalStateException();
			}										
		}				
		return returnVector.toArray();
	}	
}
