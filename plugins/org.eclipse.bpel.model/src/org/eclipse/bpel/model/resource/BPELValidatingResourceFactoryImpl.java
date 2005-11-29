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
package org.eclipse.bpel.model.resource;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

public class BPELValidatingResourceFactoryImpl extends BPELResourceFactoryImpl {
	
	// Properties for validating bpel document
	protected boolean validating = false;
	protected EntityResolver entityResolver = null;
	protected ErrorHandler errorHandler = null;
	protected ResourceSet resourceSet = null;
	
	protected BPELValidatingResourceFactoryImpl() {	
		validating = false;
		entityResolver = null;
		errorHandler = null;
		resourceSet = null;
	}
	
	public BPELValidatingResourceFactoryImpl(ResourceSet resourceSet, EntityResolver entityResolver, ErrorHandler errorHandler) {	
		
		validating = true;
		
		this.entityResolver = entityResolver;
		this.errorHandler = errorHandler;
		this.resourceSet = resourceSet;
		
		Resource.Factory.Registry resourceFactoryRegistry = resourceSet.getResourceFactoryRegistry();
		resourceFactoryRegistry.getExtensionToFactoryMap().put("bpel", this);
	}
	
	public Resource createResource(URI uri) {
		try {
			return new BPELResourceImpl(uri, entityResolver, errorHandler);
		} catch (IOException exc) {
			return null;
		}
	}
}
