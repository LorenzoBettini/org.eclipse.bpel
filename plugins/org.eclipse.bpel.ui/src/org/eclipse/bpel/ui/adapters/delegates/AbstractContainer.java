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

import java.util.Iterator;

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

	/* IContainer */

	public boolean canAddObject(Object object, Object child, Object insertBefore) {
		if (!(child instanceof EObject)) return false;
		return isValidChild(object, (EObject)child);
	}

	public final Object getNextSiblingChild(Object object, Object child) {
		for (Iterator it = getChildren(object).iterator(); it.hasNext(); ) {
			if (child == it.next()) {
				if (it.hasNext()) return it.next();
				break;
			}
		}
		return null;
	}
}
