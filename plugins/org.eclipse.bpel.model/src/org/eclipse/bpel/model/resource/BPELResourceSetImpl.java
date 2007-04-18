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
package org.eclipse.bpel.model.resource;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Apr 17, 2007
 *
 */
public class BPELResourceSetImpl extends ResourceSetImpl {

	/**
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#getResource(org.eclipse.emf.common.util.URI, boolean)
	 */
	@Override
	public Resource getResource(URI arg0, boolean arg1) {		
		return super.getResource(arg0, arg1);
	}

	
	/**
	 * Load the resource from the resource set, assuming that it is the kind
	 * indicated by the last argument. The "kind" parameter is the extension 
	 * without the . of the resource.
	 * 
	 * This forces the right resource to be loaded even if the URI of the resource
	 * is "wrong".
	 * 
	 * @param uri the URI of the resource.
	 * @param b
	 * @param kind the resource kind. It has to be of the form "*.wsdl", or "*.xsd", or "*.bpel"
	 * @return the loaded resource.
	 * @throws IOException 
	 */
	
	public Resource getResource (URI uri, boolean b, String kind) throws IOException {
		
		Resource result = createResource(URI.createURI(kind)); 
		result.setURI(uri);		
		result.load( getLoadOptions() );
		return result;
		
	}
}
