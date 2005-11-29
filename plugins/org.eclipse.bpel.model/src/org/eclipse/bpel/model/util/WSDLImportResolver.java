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

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Import;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.wst.wsdl.util.WSDLResourceImpl;


public class WSDLImportResolver implements ImportResolver {
        
    public final static String getImportType() {
        return WSDLConstants.WSDL_NAMESPACE_URI;
    }
    
    public EObject resolve(Import imp, QName qname, String name, String refType) {
        EObject result = null;
        
        if (getImportType().equals(imp.getImportType())) {            
            if (WSDLUtil.isWSDLType(refType)) {
                
                Resource baseResource = imp.eResource();
                String location = imp.getLocation();
                if (!baseResource.getURI().isRelative()) {
                    location = URI.createURI(location).resolve(baseResource.getURI()).toString();
                }
                
                URI locationURI = URI.createURI(location);
                try {
                    ResourceSet resourceSet = baseResource.getResourceSet();
                    WSDLResourceImpl resource = (WSDLResourceImpl) resourceSet.getResource(locationURI, true);
                    Definition definition = resource.getDefinition();
                    result = WSDLUtil.resolveWSDLReference(definition, qname, name, refType);
                } catch (Exception e) {
                    // TODO : Handle this better
                }
            }
        }
        
        return result;
    }
}
