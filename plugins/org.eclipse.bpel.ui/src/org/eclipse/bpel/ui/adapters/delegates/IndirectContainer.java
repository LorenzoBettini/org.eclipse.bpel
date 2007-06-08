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

	protected EReference fIndirectionFeature;
	protected AbstractContainer fInnerContainer;
	
	/**
	 * Brand new shiny IndirectContainer 
	 * @param feature
	 * @param innerContainer
	 */
	public IndirectContainer (EReference feature, AbstractContainer innerContainer) {
		if (feature.isMany()) throw new IllegalArgumentException();
		this.fIndirectionFeature = feature;
		this.fInnerContainer = innerContainer;
	}

	protected EObject getTarget(Object object) {
		return (EObject)((EObject)object).eGet(fIndirectionFeature);
	}
	
	// TODO: this is kind of a hack.  We shouldn't need to know it's an AbstractContainer :(
	
	@Override
	protected boolean isValidChild(Object object, EObject child) {
		return fInnerContainer.isValidChild(object, child);
	}

	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#addChild(java.lang.Object, java.lang.Object, java.lang.Object)
	 */	
	
	public boolean addChild(Object object, Object child, Object insertBefore) {
		EObject target = getTarget(object);
		return (target == null)? false : fInnerContainer.addChild(target, child, insertBefore);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#getChildren(java.lang.Object)
	 */
	
	public List<?> getChildren(Object object) {
		EObject target = getTarget(object);
		return (target == null)? Collections.EMPTY_LIST : fInnerContainer.getChildren(target);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#removeChild(java.lang.Object, java.lang.Object)
	 */
	public boolean removeChild(Object object, Object child) {
		EObject target = getTarget(object);
		return (target == null)? false : fInnerContainer.removeChild(target, child);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#replaceChild(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public boolean replaceChild(Object object, Object oldChild, Object newChild) {		
		EObject target = getTarget(object);
		return (target == null)? false : fInnerContainer.replaceChild(target, oldChild, newChild);
	}

	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#canRemoveChild(java.lang.Object, java.lang.Object)
	 */
	public boolean canRemoveChild (Object object, Object child) {
		EObject target = getTarget(object);
		return (target == null)? false : fInnerContainer.canRemoveChild(target, child);
	}
	
	
}
