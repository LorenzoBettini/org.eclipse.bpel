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
package org.eclipse.bpel.ui.adapters.delegates;


import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.emf.ecore.EObject;


/**
 * Common base class for IContainer implementations.
 * 
 * Implementors of IContainer can use this class to simplify implementation.  They
 * also might want to subclass this if they plan to interact with the Actions returned
 * by other subclasses of AbstractContainer.
 */
public abstract class AbstractContainer implements IContainer {

	protected abstract boolean isValidChild(Object object, EObject child);

	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#canAddObject(java.lang.Object, java.lang.Object, java.lang.Object)
	 */	

	public boolean canAddObject(Object object, Object child, Object insertBefore) {
		if (child instanceof EObject == false) {
			return false;
		}
		return isValidChild(object, (EObject) child);
	}

	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#getNextSiblingChild(java.lang.Object, java.lang.Object)
	 */
	
	public final Object getNextSiblingChild(Object object, Object child) {
		
		boolean bNext = false;
		for(Object next : getChildren(object)) {
			if (next == child) {
				bNext = true;
			} else if (bNext) {
				return next;
			}
		}
		return null;
	}
}
