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
package org.eclipse.bpel.ui.adapters;

import java.util.HashMap;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;

/**
 * The primary motivation for this class is to delegate the decision
 * of whether an adapter is  stateless or statefull. 
 * 
 * In the case of a stateless adapter, the singleton instance is
 * always returned.
 * 
 * The factory classes simply call
 * <pre> 
 *   adapter.getAdatper ( Class )
 * </pre>
 * 
 * An adapter is considered stateful if it implements IStatefullAdapter
 *
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jul 21, 2006
 *
 */

public final class AdapterProvider {

	HashMap map = new HashMap();
	
	/**
	 * Get an adapter of the class passed. 
	 * 
	 *  
	 * @param clazz
	 * @return the appropriate adapter
	 */
	
	public Adapter getAdapter ( Class adapterClass ) {
		
		Adapter singleton = (Adapter) map.get(adapterClass);
		if (singleton == null) {
			singleton = newAdapter( adapterClass );			  
			map.put(adapterClass,singleton);
		}
		
		if (singleton instanceof IStatefullAdapter) {
			return newAdapter(adapterClass);
		}
		
		return singleton;
	}
	
	
	Adapter newAdapter ( Class clazz ) {
		try {
			return (Adapter) clazz.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException( ex );
		}
	}


	
}
