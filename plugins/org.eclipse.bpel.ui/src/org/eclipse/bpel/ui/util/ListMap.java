/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * A little bit of LISP.
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jul 19, 2006
 *
 */
public class ListMap {
	
	/**
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Jul 19, 2006
	 *
	 */
	
	static final public Object IGNORE = new Object();
	
	static public interface Visitor {
		public Object visit ( Object obj );		
	}
	 		
	static public Object Map (List list, Visitor visitor) {
		return Map(list,visitor,null);
	}
	
	
	static public Object Map ( List list, Visitor visitor , Object[] ret ) {
		List output = new ArrayList(list.size());
		Iterator it = list.iterator();
		while (it.hasNext()) {			
			Object result = visitor.visit ( it.next() );
			if (result != IGNORE) {
				output.add( result );
			}
		}
		if (ret == null) {
			return output;
		}
		return output.toArray(ret);		
	}
	
	
	static public Object Map ( Object[] list, Visitor visitor ) {
		return Map(list,visitor,null);
	}
	
	
	static public Object Map (Object[] list, Visitor visitor, Object[] ret) {
		List l2 = new ArrayList(list.length);
		for(int i=0; i<list.length; i++) {
			l2.add(list[i]);
		}
		return Map ( l2, visitor , ret);
	}


	static public final Object Find (List list, Visitor visitor) {
		Iterator it = list.iterator();
		while (it.hasNext()) {			
			Object result = visitor.visit ( it.next() );
			if (result != IGNORE) {
				return result;
			}
		}
		return null;					
	}
	
	
	
	/**
	 * @param list
	 * @param next
	 * @param comparator
	 */
	
	public static Object findElement (List list, Object key, Comparator comparator) {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object element = it.next();
			if (comparator.compare(element, key) == 0) {
				return element;
			}						
		}
		return null;			
	}
	
	
}
