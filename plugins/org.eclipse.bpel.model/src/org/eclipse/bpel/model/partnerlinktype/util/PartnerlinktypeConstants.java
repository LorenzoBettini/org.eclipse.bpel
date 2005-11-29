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
package org.eclipse.bpel.model.partnerlinktype.util;

import org.w3c.dom.Element;

public final class PartnerlinktypeConstants
{
	/**
	 * The BPEL namespace.
	 */
	public static final String NAMESPACE_2004 = "http://schemas.xmlsoap.org/ws/2004/03/partner-link/";

	/**
	 * The current namespace.
	 */
	public static String NAMESPACE = NAMESPACE_2004;

	/**
	 * Tests if <code>namespace</code> equals any of the valid namespaces.
	 */
	public static boolean isPartnerLinkTypeNamespace(String namespace)
	{
		return namespace != null
			&& namespace.equals(NAMESPACE_2004);
	}
	
    public static final String PARTNER_LINK_TYPE_ELEMENT_TAG = "partnerLinkType";
    public static final String ROLE_ELEMENT_TAG = "role";
    public static final String PORT_TYPE_ELEMENT_TAG = "portType";
    
    public static final String ROLE_ATTRIBUTE = "role";
    public static final String NAME_ATTRIBUTE = "name";
    
    public static String getAttribute(Element element, String attributeName)
    {
        return element.hasAttribute(attributeName) ? element.getAttribute(attributeName) : null;
    }
}
