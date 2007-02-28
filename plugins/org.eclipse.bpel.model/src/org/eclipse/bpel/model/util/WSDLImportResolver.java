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
package org.eclipse.bpel.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELPlugin;
import org.eclipse.bpel.model.Import;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;


/**
 * Resolve items from the imported WSDLs.
 * 
 * @author IBM
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Feb 27, 2007
 *
 */

@SuppressWarnings("nls")

public class WSDLImportResolver implements ImportResolver {
        	
	
    /** 
     * @return The import type for which this resolver resolves.
     */
    public final static String getImportType() {
        return WSDLConstants.WSDL_NAMESPACE_URI;
    }

    
	protected Definition findAndLoadWSDL ( Import imp) {
        Resource baseResource = imp.eResource();
        String location = imp.getLocation();
        if (!baseResource.getURI().isRelative()) {
            location = URI.createURI(location).resolve(baseResource.getURI()).toString();
        }
        
        URI locationURI = URI.createURI(location);
        ResourceSet resourceSet = baseResource.getResourceSet();
        Resource resource = null;
        try {
        	resource = resourceSet.getResource(locationURI, true);
        } catch (Exception ex) {
        	BPELPlugin.log("The resource " + locationURI + " cannot be read.",ex,IStatus.WARNING) ;
        	return null;
        }
        
        if (resource instanceof WSDLResourceImpl) {
        	return ((WSDLResourceImpl)resource).getDefinition();
        }
        
        if (resource != null) {
        	BPELPlugin.log(null, new Exception("The resource " + locationURI + " is not a WSDL definition."),IStatus.WARNING );
        } else  {
        	BPELPlugin.log(null, new Exception("The resource " + locationURI + " cannot be read." ),IStatus.WARNING) ;
        }
        return null;
    }
    
    /**
     * @see org.eclipse.bpel.model.util.ImportResolver#resolve(org.eclipse.bpel.model.Import, javax.xml.namespace.QName, java.lang.String, java.lang.String)
     */
    public EObject resolve(Import imp, QName qname, String name, String refType) {
    	
        EObject result = null;
        
        if ( getImportType().equals(imp.getImportType()) == false ) {
        	return result;
        }
        
        if (WSDLUtil.isWSDLType(refType) == false && refType.equals(TOP) == false) {
        	return result;
        }
        
        if (imp.getLocation() == null) { 
        	return result ; 
        }
        
        Definition definition = findAndLoadWSDL ( imp );
        
        if (refType.equals(TOP)) {
        	return definition;
        }
        
        if (definition != null) {
        	result = WSDLUtil.resolveWSDLReference(definition, qname, name, refType);
        }
        
        return result;
    }

	/**
	 * @param imp the import
	 * @param what what to resolve (WSDLs or Schemas)
	 * @return a list containing the resolved items (either WSDLs or schemas)
	 * 
	 * @see org.eclipse.bpel.model.util.ImportResolver#resolveSchemas(org.eclipse.bpel.model.Import)
	 */
    
	@SuppressWarnings("unchecked")
	public List resolve (Import imp, int what ) {
		
		if (getImportType().equals(imp.getImportType()) == false) {
        	return Collections.EMPTY_LIST;
        }
		
        Definition definition = findAndLoadWSDL ( imp );
        
        if (definition == null) {
        	return Collections.EMPTY_LIST;
        }
        
        if (what == RESOLVE_DEFINITION) {
        	ArrayList al = new ArrayList(1);
        	al.add(definition);
        	return al;
        }
        
        if (definition.getETypes() == null) {
        	return Collections.EMPTY_LIST;
        }
        
        return definition.getETypes().getSchemas();        	
	}
    
}
