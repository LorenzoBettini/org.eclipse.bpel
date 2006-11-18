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

import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Import;
import org.eclipse.emf.ecore.EObject;


public interface ImportResolver
{   
	/** Resolve the schema from the import */	
	int RESOLVE_SCHEMA = 1;
	
	/** Resolve the definition from the import */
	int RESOLVE_DEFINITION = 2;
	
	/** The top element, which implies the model behind the import */
	String TOP = "top.element"; //$NON-NLS-1$
	
	
    EObject resolve(Import imp, QName qname, String name, String refType);
    
    /** Resolve something from the import */
    List resolve ( Import imp , int what );    
}
