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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportResolverRegistry
{
    public final static ImportResolverRegistry INSTANCE = new ImportResolverRegistry();
    
    /**
     * Maps from {@link String} import type to a {@link List} of {@link ImportResolver}s.
     */
    private Map registry = new HashMap();

    private final static ImportResolver[] EMPTY_RESOLVER_ARRAY = new ImportResolver[] {};
    
    /**
     * Hide the constructor.
     */
    private ImportResolverRegistry() {
        // Register the default resolvers. 
        registerResolver(XSDImportResolver.getImportType(), new XSDImportResolver());
        registerResolver(WSDLImportResolver.getImportType(), new WSDLImportResolver());
    }
    
    public void registerResolver(String importType, ImportResolver resolver)
    {
        List resolvers = (List) registry.get(importType);
        if (resolvers == null)
        {
            resolvers = new ArrayList();
            registry.put(importType, resolvers);
        }
        resolvers.add(resolver);
    }
    
    public ImportResolver[] getResolvers(String importType)
    {
        List resolvers = (List) registry.get(importType);
        if (resolvers == null)
        {
            return EMPTY_RESOLVER_ARRAY;
        }
        else
        {
            return (ImportResolver[]) resolvers.toArray(EMPTY_RESOLVER_ARRAY);
        }
    }
}
