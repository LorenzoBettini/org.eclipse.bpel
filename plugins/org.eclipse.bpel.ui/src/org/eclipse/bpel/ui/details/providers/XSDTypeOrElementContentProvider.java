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
package org.eclipse.bpel.ui.details.providers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.util.XSDConstants;

import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Types;

/**
 * Content provider for XSDComplexType. It also handles built-in types.
 * 
 * Expects an XSDSchema as input.
 */
public class XSDTypeOrElementContentProvider extends AbstractContentProvider  {

	// TODO: We should use a common simple type selection mechanism
	protected static List xsdPrimitiveTypes = new ArrayList(12);
	static {
		xsdPrimitiveTypes.add("string"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("int"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("long"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("short"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("decimal"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("float"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("double"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("integer"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("boolean"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("byte"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("QName"); //$NON-NLS-1$
		xsdPrimitiveTypes.add("dateTime"); //$NON-NLS-1$
	}

	public Object[] getElements(Object input)  {
		ArrayList list = new ArrayList();
		if (input instanceof Definition) {
			Types types = ((Definition)input).getETypes();
			if (types != null) {
				Iterator schemaIt = types.getSchemas().iterator();
				while (schemaIt.hasNext()) {
					addSchemaElements(list, (XSDSchema)schemaIt.next());
				}
			}
		}
		if (input instanceof XSDSchema) {
			XSDSchema schema = (XSDSchema)input;
			addSchemaElements(list, schema);
		}
		return list.isEmpty()? EMPTY_ARRAY : list.toArray();
	}

	protected void addSchemaElements(List list, XSDSchema schema) {
		boolean builtInTypesSchema = XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001.equals(schema.getTargetNamespace());
		for (Iterator it = schema.getContents().iterator(); it.hasNext(); ) {
			Object object = it.next();
			if ((object instanceof XSDTypeDefinition) || (object instanceof XSDElementDeclaration)) {
				if (builtInTypesSchema) {
					if (object instanceof XSDElementDeclaration
						|| !xsdPrimitiveTypes.contains(((XSDTypeDefinition) object).getName())) {
						continue; // from this schema we only want the built-in types
					}
				}
				list.add(object); 
			}
		}		
	}
	
	/**
	 * Helper method for identifying if a given type is a built-in type.
	 */
	public static boolean isBuiltInType(XSDTypeDefinition target) {
		XSDSchema schema = (XSDSchema) target.eContainer();
		if (!XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001.equals(schema.getTargetNamespace())) {
			return false;
		}
		return xsdPrimitiveTypes.contains(target.getName());
	}
}
