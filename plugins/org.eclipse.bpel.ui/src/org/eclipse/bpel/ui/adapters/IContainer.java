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
package org.eclipse.bpel.ui.adapters;

import java.util.List;

/**
 * An IContainer is an element that has one or more children in an ordered
 * list.
 */
public interface IContainer {
	
	/**
	 * Return an ordered list of all children of the container.
	 * The returned list may or may not be a copy of the list held
	 * by the model, and as such should be considered read-only.
	 */
	public List getChildren(Object object);
	
	/** 
	 * returns the next sibling in the list of children of this parent
	 * returns null if the object is a singular child and has no direct
	 * siblings.
	 */
	public Object getNextSiblingChild(Object object, Object child);
	
	/**
	 * Adds the given child to the container before the object insertBefore.  If insertBefore
	 * is null, or if insertBefore is invalid (i.e. not a child in the container), the object
	 * should be inserted at the end of the list.
	 * 
	 * NOTE: some implementors may impose restrictions on where a certain child may appear
	 * within the total ordering; for example, a Process might require all Partners to
	 * appear before Containers, etc.  These implementors should add the child as close to
	 * the requested position as possible.  e.g. inserting a Container after a Partner might
	 * cause the insertion to occur between the last Partner and the first Container.
	 * 
	 * Returns true if the child is added successfully, otherwise false.
	 */
	public boolean addChild(Object object, Object child, Object insertBefore);
	
	/** 
	 * this method allows us to test if we can actually add the object to the container
	 * at the specified location
	 * 
	 * for this function to be more useful, always try to pass a value for the
	 * insertBefore so we can do addition validity checks
	 */
	
	public boolean canAddObject(Object object, Object child, Object insertBefore);

	/**
	 * Removes the given child from the container.
	 * 
	 * Returns true if the child is removed successfully, otherwise false.
	 */
	public boolean removeChild(Object object, Object child); 

	/**
	 * Replace the old child with the new child. In the case of ordered
	 * containers, this must insert the new child at the same index as
	 * the old child and return a Token which can be passed to the undo()/redo() methods.
	 * 
	 * Returns true if the child is replaced successfully, otherwise false.
	 */
	public boolean replaceChild(Object object, Object oldChild, Object newChild);
	
}
