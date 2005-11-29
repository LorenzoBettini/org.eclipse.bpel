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
package org.eclipse.bpel.model.reordering.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.model.BPELPlugin;
import org.eclipse.bpel.model.reordering.IExtensibilityElementListHandler;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


public class ExtensionFactory {
	private static ExtensionFactory instance;
	
	private static final String ATTRIBUTE_CLASS = "class";
	public static final String ID_EXTENSION_REORDERING = "extensions_reordering";
			
	public static ExtensionFactory instance() {
		if (instance == null)
			instance = new ExtensionFactory();
		return instance;
	}
	
	public List createHandlers(String type) {
		
		List handlers = new ArrayList();
		
		List elements = findExtensionConfigurationElementsByID(type);
		if (elements != null && !elements.isEmpty()){
			for (Iterator iter = elements.iterator(); iter.hasNext();) {
				IConfigurationElement element = (IConfigurationElement) iter.next();
				IExtensibilityElementListHandler handler = _createExtensibilityElementListHandler(element);
				if(handler != null)
					handlers.add(handler);	
			}		
		}
		return handlers;
	}
	
	private IExtensibilityElementListHandler _createExtensibilityElementListHandler(IConfigurationElement element) {	
		if (element == null) return null;
		IExtensibilityElementListHandler classifier = null; 
		try {
			classifier =  (IExtensibilityElementListHandler)(element.createExecutableExtension(ATTRIBUTE_CLASS));
		} catch (Exception e) {
			classifier = null;	
		}
		return classifier;
	}

	
	private List findExtensionConfigurationElementsByID(String extensionpointID) {
		
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(BPELPlugin.PLUGIN_ID, extensionpointID);
		if (elements == null) return null;
		
		List elementsFound = null;
		
		IConfigurationElement nextElement = null;
		for (int i = 0; i<elements.length; i++) {
			nextElement = elements[i];
			if (nextElement.getName().equals(extensionpointID)) {
				if (elementsFound == null) elementsFound = new ArrayList();
				elementsFound.add(nextElement);			
			}
		}
		
		return elementsFound;
	}
	
}
