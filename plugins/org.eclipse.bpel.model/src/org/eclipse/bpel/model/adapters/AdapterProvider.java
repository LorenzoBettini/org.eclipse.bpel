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
package org.eclipse.bpel.model.adapters;

import java.util.HashMap;

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

	/**
	 * The list of adapter singletons that we have created.
	 */
	
	final HashMap<String,Adapter> map = new HashMap<String,Adapter>();
	
	/**
	 * The package list that we search for adapters.
	 */
	String [] packageList = {};
	
	/**
	 * Return a brand new shiny adapter provider.
	 *
	 */
	public AdapterProvider () {
		
	}
	
	/**
	 * Return a brand new shiny adapter provider which searches for
	 * adapters in the packages specified.
	 * @param args
	 */
	
	public AdapterProvider (String ... args) {
		packageList = args;
	}
	
	/**
	 * Return an adapter for the given class
	 * 
	 * @param name the name of the adapter (class name)
	 * @return the Adapter, or null, if the adapter cannot be found.
	 */
	
	
	public Adapter getAdapter ( String name ) {
		
		Adapter singleton = null;
		int absNameIdx = name.indexOf('.');
		String className = name ;
		
		if (absNameIdx > 0) {
			// absolute specification
			singleton = map.get(className);			
		} else {
			for(int i=0; i < packageList.length; i++) {
				className = packageList[i] + "." + name; //$NON-NLS-1$
				singleton = map.get( className );
			}
		}
		
		
		if (singleton == null) {
			
			if (absNameIdx < 0) {
				
			} 
			
		}
		
		
		if (singleton instanceof IStatefullAdapter) {
			return newAdapter( singleton.getClass() );
		}
		
		return singleton;		
	}
	
	
	/**
	 * Get an adapter of the class passed. 
	 *   
	 * @param adapterClass
	 * @return the appropriate adapter
	 */
	
	public Adapter getAdapter ( Class<? extends Adapter> adapterClass ) {
		
		String name = adapterClass.getName();
		Adapter singleton = map.get( name );
		if (singleton == null) {
			singleton = newAdapter( adapterClass );			  
			map.put(name,singleton);
		}
		
		if (singleton instanceof IStatefullAdapter) {
			return newAdapter(adapterClass);
		}
		
		return singleton;
	}
	
	
	
	Adapter newAdapter ( Class<? extends Adapter> clazz ) {
		try {
			return clazz.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException( ex );
		}
	}


	
}
