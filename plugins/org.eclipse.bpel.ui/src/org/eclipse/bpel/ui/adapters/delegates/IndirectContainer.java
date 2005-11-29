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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * A container which allows you to re-map the target object for other containers.
 * 
 * In particular, this class allows you to specify a single-valued EReference feature
 * and use the value read from that as the target object.
 */
public class IndirectContainer extends AbstractContainer {

	protected EReference indirectionFeature;
	protected AbstractContainer innerContainer;
	
	public IndirectContainer(EReference feature, AbstractContainer innerContainer) {
		if (feature.isMany()) throw new IllegalArgumentException();
		this.indirectionFeature = feature;
		this.innerContainer = innerContainer;
	}

	protected EObject getTarget(Object object) {
		return (EObject)((EObject)object).eGet(indirectionFeature);
	}
	
	// TODO: this is kind of a hack.  We shouldn't need to know it's an AbstractContainer :(
	protected boolean isValidChild(Object object, EObject child) {
		return innerContainer.isValidChild(object, child);
	}

	/* IContainer */
	
	public boolean addChild(Object object, Object child, Object insertBefore) {
		EObject target = getTarget(object);
		return (target == null)? false : innerContainer.addChild(target, child, insertBefore);
	}
	
	public List getChildren(Object object) {
		EObject target = getTarget(object);
		return (target == null)? Collections.EMPTY_LIST : innerContainer.getChildren(target);
	}
	
	public boolean removeChild(Object object, Object child) {
		EObject target = getTarget(object);
		return (target == null)? false : innerContainer.removeChild(target, child);
	}
	
	public boolean replaceChild(Object object, Object oldChild, Object newChild) {
		EObject target = getTarget(object);
		return (target == null)? false : innerContainer.replaceChild(target, oldChild, newChild);
	}
}
