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

import org.eclipse.bpel.model.adapters.IStatefullAdapter;
import org.eclipse.emf.common.notify.Adapter;

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

	HashMap<Class<? extends Adapter>,Adapter> map = new HashMap<Class<? extends Adapter>,Adapter>();
	
	/**
	 * Get an adapter of the class passed. 
	 * 
	 *  
	 * @param adapterClass
	 * @return the appropriate adapter
	 */
	
	public Adapter getAdapter ( Class<? extends Adapter> adapterClass ) {
		
		Adapter singleton = map.get(adapterClass);
		if (singleton == null) {
			singleton = newAdapter( adapterClass );			  
			map.put(adapterClass,singleton);
		}
		
		if (singleton instanceof IStatefullAdapter) {
			return newAdapter(adapterClass);
		}
		
		return singleton;
	}
	
	
	/**
	 * Get an adapter of the class passed. 
	 * 
	 *  
	 * @param adapterClass
	 * @param target the target object
	 * @return the appropriate adapter
	 */
	
	public Adapter getAdapter ( Class<? extends Adapter> adapterClass , Object target ) {
		
		Adapter adapter = getAdapter(adapterClass);
		
		if (adapter == null) {
			return null;
		}
		
		if (adapter instanceof IStatefullAdapter) {
			IStatefullAdapter statefullAdapter = (IStatefullAdapter) adapter;
			statefullAdapter.setTarget(target);
		}
		
		return adapter;
	}
	
	
	Adapter newAdapter ( Class<? extends Adapter> clazz ) {
		try {
			return clazz.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException( ex );
		}
	}


	
}
