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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDConstants;
import org.eclipse.xsd.util.XSDResourceImpl;


public class XSDImportResolver implements ImportResolver {
    
    public final static String getImportType() {
        return XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001;
    }
    
    /**
     * Find and load the schema based on the import statement
     * 
     * @param imp the import statement from the bpel source
     * @return the schema that it references
     */
    
    protected XSDSchema findAndLoadSchema ( Import imp ) 
    {
    	Resource baseResource = imp.eResource();
        String location = imp.getLocation();
        if (!baseResource.getURI().isRelative()) {
            location = URI.createURI(location).resolve(baseResource.getURI()).toString();
        }
        
        URI locationURI = URI.createURI(location);
        
        ResourceSet resourceSet = baseResource.getResourceSet();
        XSDResourceImpl resource = (XSDResourceImpl) resourceSet.getResource(locationURI, true);        
        return resource.getSchema();
    }

    
    
    public EObject resolve(Import imp, QName qname, String name, String refType) {
        EObject result = null;
        
        if (getImportType().equals(imp.getImportType()) == false || 
        		XSDUtil.isSchemaType(refType) == false) {
        	return result;            
        }        
        XSDSchema schema = findAndLoadSchema( imp );                    
        result = XSDUtil.resolve(schema, qname, name, refType);
        
        return result;
    }

    
	/** 
	 * Each XSDImport currently contributes only 1 schema.
	 *  
	 * @see org.eclipse.bpel.model.util.ImportResolver#resolveSchemas(org.eclipse.bpel.model.Import)
	 */
    
	public List resolve (Import imp, int what) {
				
		if (getImportType().equals(imp.getImportType()) == false) {
			return Collections.EMPTY_LIST;
		}
		if (what == RESOLVE_DEFINITION) {
			return Collections.EMPTY_LIST;
		}
		
		List list = new ArrayList(1);
		list.add ( findAndLoadSchema( imp ) );
		return list;
	}
    
    
}
