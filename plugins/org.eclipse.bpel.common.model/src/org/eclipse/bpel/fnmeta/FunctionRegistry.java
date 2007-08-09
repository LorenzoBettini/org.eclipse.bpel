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
package org.eclipse.bpel.fnmeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.common.BPELResourceSet;
import org.eclipse.bpel.common.extension.model.Activator;
import org.eclipse.bpel.fnmeta.model.Function;
import org.eclipse.bpel.fnmeta.model.Registry;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Aug 3, 2007 
 */

@SuppressWarnings("nls")
public class FunctionRegistry {

	static private String FUNCTION_REGISTRY = "functionRegistry";
	static private String AT_REGISTRY_NAME_SPACE = "namespace";
	static private String AT_URI = "uri";
	
	static final Map<String,FunctionRegistry> LANGUAGES = new HashMap<String,FunctionRegistry>();
	
	List<Function> fRegistry = null;
	Map<String,Map<String,Function>> fRegistryIndex = new HashMap<String,Map<String,Function>>();
	
	String fLanguageNamespace ;
	
	/**
	 * @param languageNamespace
	 */
	
	public FunctionRegistry (String languageNamespace ) {
		fLanguageNamespace = languageNamespace;
	}
	
	/**
	 *   
	 * @return the registry for that function namespace 
	 */
	
	public List<Function> getRegistry () {
		
		if (fRegistry != null) {
			return fRegistry;
		}		
		doLoad ();
		return fRegistry;
				
	}
	
	synchronized void doLoad () {
		if (fRegistry != null) {
			return ;
		}
		
		/** Lazy load this ... */		
		fRegistry = new ArrayList<Function>();
		
		for (IConfigurationElement elm : Activator.INSTANCE.getConfigurationElements( FUNCTION_REGISTRY )) {
			
			if (fLanguageNamespace.equals(elm.getAttribute(AT_REGISTRY_NAME_SPACE)) == false) {
				continue;					
			}
			String location = elm.getAttribute(AT_URI);
			URI uri = URI.createURI( location ) ;
		
			BPELResourceSet rs = new BPELResourceSet();
			Resource resource = rs.getResource(uri, true, "fnmeta");
			
			if (resource.getContents().size() > 0) {
				Registry registry = (Registry) resource.getContents().get(0);			
				fRegistry.addAll( registry.getFunctions() );				
			}			
		}				
		
		/** Build the index for lookups */
		fRegistryIndex = new HashMap<String,Map<String,Function>>();
		for(Function fn : fRegistry) {
			Map<String,Function> map = fRegistryIndex.get(fn.getNamespace());
			if (map == null) {
				map  = new HashMap<String,Function>();
				fRegistryIndex.put (fn.getNamespace(),map);
			}
			map.put(fn.getName(), fn);
		}
		
	}
	
	
	/**
	 * 
	 * @param language the language.
	 * @return the function registry for the given language.
	 */
	
	static public final FunctionRegistry getRegistryForLanguage ( String language ) {
		FunctionRegistry registry = LANGUAGES.get(language);
		if (registry != null) {
			return registry;
		}
		
		synchronized(LANGUAGES) {
			registry = LANGUAGES.get(language);
			if (registry != null) {
				return registry;
			}
			
			registry = new FunctionRegistry(language);			
			LANGUAGES.put(language, registry);
		}
		
		registry.doLoad();		
		return registry;
	}

	/**
	 * 
	 * @param ns
	 * @param name
	 * @return the function for the given namespace and name
	 *  
	 */
	
	public Function lookupFunction (String ns, String name) {
		
		Map<String,Function> map = fRegistryIndex.get(ns);
		if (map == null) {
			return null;
		}
		return map.get(name);
	}
	
}
