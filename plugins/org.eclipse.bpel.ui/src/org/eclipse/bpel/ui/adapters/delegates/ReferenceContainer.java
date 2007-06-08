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
 * An implementation of IContainer which operates on an EReference feature (which may or
 * may not be a list).  This lets us use existing EMF metadata.
 */
public class ReferenceContainer extends AbstractContainer {

	EReference fFeature;
	
	boolean allowMixedTypeReplace = false;
	
	/**
	 * Brand new ReferenceContainer
	 * @param aFeature
	 */
	
	public ReferenceContainer(EReference aFeature) {
		super();
		this.fFeature = aFeature;
	}
	
	protected boolean isMany() { 
		return fFeature.isMany();
	}

	protected List<Object> getChildList(Object object) {
		return (List)((EObject)object).eGet(fFeature);
	}
	
	/**
	 * Return the single child.
	 * 
	 * @param object
	 * @return the single child, or null
	 */
	
	public EObject getSingleChild(Object object) {
		return (EObject)((EObject)object).eGet(fFeature);
	}
	
	/**
	 * Set the single child of this container.
	 * 
	 * @param object the container
	 * @param child the child.
	 */
	
	public void setSingleChild(Object object, Object child) {
		((EObject)object).eSet(fFeature, child);
	}
	
	@Override
	protected final boolean isValidChild (Object object, EObject child) {
		return fFeature.getEReferenceType().isSuperTypeOf(child.eClass());
	}

	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#addChild(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public boolean addChild(Object object, Object child, Object insertBefore) {
		if (!isValidChild(object, (EObject)child)) {
			return false;
		}
		if (isMany()) {
			List<Object> list = getChildList(object);
			if (insertBefore == null) {
				// insert at the end.
				list.add(child);
			} else {
				// find the index of insertBefore and use that.  If it isn't found,
				// insert at the beginning.  (This covers the case where insertBefore is
				// of a different type too, as OrderedMultiContainer passes null when it
				// wants you to insert at the end, so if insertBefore was the wrong type
				// then it must want us to insert at the beginning).
				int index = list.indexOf(insertBefore);
				if (index < 0) index = 0;
				list.add(index, child);
			}
		} else {
			EObject value = getSingleChild(object);
			if (value != null) return false;
			setSingleChild(object, child);
		}
		return true;
	}

	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#getChildren(java.lang.Object)
	 */
	public List<?> getChildren(Object object) {
		if (isMany()) {
			List<?> list = getChildList(object);
			if (list.isEmpty()) {
				return Collections.EMPTY_LIST;
			}
			return Collections.unmodifiableList(list);
		}
		EObject value = getSingleChild(object);
		if (value == null) {
			return Collections.EMPTY_LIST;
		}
		return Collections.singletonList(value);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#removeChild(java.lang.Object, java.lang.Object)
	 */
	public boolean removeChild(Object object, Object child) {
		
		if (!isValidChild(object, (EObject)child)) {
			return false;
		}
		if (isMany()) {
			List<?> list = getChildList(object);
			return list.remove(child);
		}
		EObject value = getSingleChild(object);
		if (value != child) {
			return false;
		}
		setSingleChild(object, null);
		return true;
	}

	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#replaceChild(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public boolean replaceChild(Object object, Object oldChild, Object newChild) {
		
		if (!isValidChild(object, (EObject)oldChild)) return false;
		if (!isValidChild(object, (EObject)newChild)) return false;
		if (isMany()) {
			List list = getChildList(object);
			int index = list.indexOf(oldChild);
			if (index < 0) return false;
			list.set(index, newChild);
			return true;
		}
		EObject value = getSingleChild(object);
		if (value != oldChild) return false;
		setSingleChild(object, newChild);
		return true;
	}

	/**
	 * @see org.eclipse.bpel.ui.adapters.delegates.AbstractContainer#canAddObject(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean canAddObject(Object object, Object child, Object insertBefore) {
		if (!super.canAddObject(object, child, insertBefore)) {
			return false;
		}
		if (isMany()) {
			return true;
		}
		return (getSingleChild(object) == null);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.adapters.IContainer#canRemoveChild(java.lang.Object, java.lang.Object)
	 */
	
	public boolean canRemoveChild (Object object, Object child) {
		
		if (!isValidChild(object, (EObject)child)) {
			return false;
		}
		
		if (isMany() == false) {
			EObject value = getSingleChild(object);
			if (value != child) {
				return false;
			}
		}		
		return true;
	}
}
