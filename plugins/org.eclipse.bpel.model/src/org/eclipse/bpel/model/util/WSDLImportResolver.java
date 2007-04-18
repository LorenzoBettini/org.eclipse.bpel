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

import org.eclipse.bpel.model.Import;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.util.WSDLConstants;


/**
 * Resolve items from the imported WSDLs.
 * 
 * @author IBM
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Feb 27, 2007
 *
 */

@SuppressWarnings("nls")

public class WSDLImportResolver extends XSDImportResolver {
        	
	
    /** 
     * @return The import type for which this resolver resolves.
     */
    @Override
	public String getImportType() {
        return WSDLConstants.WSDL_NAMESPACE_URI;
    }
    
    /**
     * @see org.eclipse.bpel.model.util.ImportResolver#resolve(org.eclipse.bpel.model.Import, javax.xml.namespace.QName, java.lang.String, java.lang.String)
     */
    @Override
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
        
        Definition definition = findAndLoad ( imp , "wsdl" );
        
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
	 */
    
	@Override	
	public List<Object> resolve (Import imp, int what ) {
		
		if (getImportType().equals(imp.getImportType()) == false) {
        	return Collections.emptyList();
        }
		
        Definition definition = findAndLoad ( imp , "wsdl" );
        
        if (definition == null) {
        	return Collections.emptyList();
        }
        
        if (what == RESOLVE_DEFINITION) {
        	ArrayList<Object> al = new ArrayList<Object>(1);
        	al.add(definition);
        	return al;
        }
        
        if (definition.getETypes() == null) {
        	return Collections.emptyList();
        }
        
        return definition.getETypes().getSchemas();        	
	}
    
}
