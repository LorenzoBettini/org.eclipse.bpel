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
package org.eclipse.bpel.model.extensions;

import java.util.HashMap;
import java.util.Map;

import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.xml.namespace.QName;

import org.eclipse.emf.ecore.EPackage;

/**
 * An extension registry for BPEL extensions instead of WSDL extensions.
 */
public class BPELExtensionRegistry extends ExtensionRegistry 
{
	protected Map serviceReferenceSerializers;
	protected Map serviceReferenceDeserializers;
	
	public static BPELExtensionRegistry INSTANCE;

	private BPELExtensionRegistry() {
		serviceReferenceSerializers = new HashMap();
		serviceReferenceDeserializers = new HashMap();
	}
	
	/**
	 * Returns a singleton instance.
	 */
	public static BPELExtensionRegistry getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new BPELExtensionRegistry();
			INSTANCE.setDefaultDeserializer(new BPELUnknownExtensionDeserializer());
			INSTANCE.setDefaultSerializer(new BPELUnknownExtensionSerializer());
		}
		return INSTANCE;
	}
	
	/**
	 * @see javax.wsdl.extensions.ExtensionRegistry#createExtension(Class, QName)
	 */
	public ExtensibilityElement createExtension(Class parentType, QName qname) throws WSDLException {
		
		// Make sure that the EMF package corresponding to the given namespace is initialized
		EPackage.Registry.INSTANCE.getEPackage(qname.getNamespaceURI());
		
		return super.createExtension(parentType, qname);
	}

	/**
	 * @see javax.wsdl.extensions.ExtensionRegistry#queryDeserializer(Class, QName)
	 */
	public ExtensionDeserializer queryDeserializer(Class parentType, QName qname) throws WSDLException {

		// Make sure that the EMF package corresponding to the given namespace is initialized
		EPackage.Registry.INSTANCE.getEPackage(qname.getNamespaceURI());
		
		return super.queryDeserializer(parentType, qname);
	}

	/**
	 * @see javax.wsdl.extensions.ExtensionRegistry#querySerializer(Class, QName)
	 */
	public ExtensionSerializer querySerializer(Class parentType, QName qname) throws WSDLException {

		// Make sure that the EMF package corresponding to the given namespace is initialized
		EPackage.Registry.INSTANCE.getEPackage(qname.getNamespaceURI());
		
		return super.querySerializer(parentType, qname);
	}

	/**
	 * @param es must be a {@link BPELExtensionSerializer}
	 */
	public void registerSerializer(Class parentType, QName elementType, ExtensionSerializer es) {
        if (!(es instanceof BPELExtensionSerializer)) {
            throw new IllegalArgumentException();
        }
        super.registerSerializer(parentType, elementType, es);
    }
	
	/**
	 * @param deserializer must be a {@link BPELExtensionDeserializer}
	 */
	public void registerDeserializer(Class parentType, QName elementType, ExtensionDeserializer ed) {
	    if (!(ed instanceof BPELExtensionDeserializer)) {
	        throw new IllegalArgumentException();
	    }
        super.registerDeserializer(parentType, elementType, ed);
    }
	
	public void registerServiceReferenceSerializer(String referenceScheme, ServiceReferenceSerializer serializer) {
		serviceReferenceSerializers.put(referenceScheme, serializer);
	}

	public void registerServiceReferenceDeserializer(String referenceScheme, ServiceReferenceDeserializer deserializer) {
		serviceReferenceDeserializers.put(referenceScheme, deserializer);
	}
	
	public ServiceReferenceSerializer getServiceReferenceSerializer(String referenceScheme) {
		if (referenceScheme == null) return null;
		return (ServiceReferenceSerializer)serviceReferenceSerializers.get(referenceScheme);
	}

	public ServiceReferenceDeserializer getServiceReferenceDeserializer(String referenceScheme) {
		if (referenceScheme == null) return null;
		return (ServiceReferenceDeserializer)serviceReferenceDeserializers.get(referenceScheme);
	}
}