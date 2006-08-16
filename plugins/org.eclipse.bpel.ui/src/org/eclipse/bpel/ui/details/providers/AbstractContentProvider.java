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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Common base class for our content providers.
 */
public abstract class AbstractContentProvider implements IStructuredContentProvider {

	protected static final Object[] EMPTY_ARRAY = new Object[0];
	
	public void dispose()  { }
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)  { }
	
	
	/**
	 * Provide the elements to be shown from this content provider.
	 * The generic implementation creates a list, then calls collectElements, and
	 * subsequently returns the array created from the list or the empty array if no 
	 * elements are collected. 
	 * 
	 * Under no circumstances null is returned.
	 * 
	 */
	
	public Object[] getElements(Object input)  {
		if (input == null) {
			return EMPTY_ARRAY;
		}
		
		LinkedList list = new LinkedList();			
		collectElements ( input, list );
		
		return list.isEmpty()? EMPTY_ARRAY : list.toArray();		
	}	
	
	
	/**
	 * Collect the elements to be returned by this provider. 
	 * Subclasses will override this method.
	 * 
	 * @param input the object to provide elements from (input)
	 * @param list the list in which the elements are to be inserted.
	 */
	
	public void collectElements (Object input, List list) {
		// need to override this method.
	}
	
	
	
	/**
	 * Collect elements from the input object if the object is a list or an array.
	 * The method returns true if it discovers that the passed object is "complex", 
	 * meaning it is either an array or a list of objects.
	 * 
	 * @param input the complex object to look at.
	 * @param list the list to collect elements into
	 */
	
	public boolean collectComplex ( Object input, List list) {
		
		Object[] arr = null;
		
		if (input.getClass().isArray()) {			
			arr = (Object[]) input;
		} else if (input instanceof List) {
			arr = ((List)input).toArray();
		}
		
		if (arr == null) {
			return false;
		}
		
		for(int i=0; i < arr.length; i++) {
			collectElements ( arr[i], list );
		}
		
		return arr.length > 0;
	}
}
