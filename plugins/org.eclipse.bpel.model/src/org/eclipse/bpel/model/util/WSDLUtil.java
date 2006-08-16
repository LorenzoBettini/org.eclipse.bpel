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

import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.messageproperties.MessagepropertiesPackage;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.Service;
import org.eclipse.wst.wsdl.Types;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.wst.wsdl.internal.impl.ImportImpl;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDPackage;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDConstants;


public class WSDLUtil
{	 
    public static final String WSDL_MESSAGE = WSDLPackage.eINSTANCE.getMessage().getName();
    
    public static final String WSDL_PORT_TYPE = WSDLPackage.eINSTANCE.getPortType().getName();
    
    public static final String WSDL_OPERATION = WSDLPackage.eINSTANCE.getOperation().getName();
    
    public static final String WSDL_PART = WSDLPackage.eINSTANCE.getPart().getName();
    
    public static final String WSDL_SERVICE = WSDLPackage.eINSTANCE.getService().getName();
    
    public static final String XSD_TYPE_DEFINITION = XSDPackage.eINSTANCE.getXSDTypeDefinition().getName();
    
    public static final String XSD_ELEMENT_DECLARATION = XSDPackage.eINSTANCE.getXSDElementDeclaration().getName();
    
    public static final String BPEL_PARTNER_LINK_TYPE = PartnerlinktypePackage.eINSTANCE.getPartnerLinkType().getName();
    
    public static final String BPEL_ROLE = PartnerlinktypePackage.eINSTANCE.getRole().getName();

    public static final String BPEL_PROPERTY = MessagepropertiesPackage.eINSTANCE.getProperty().getName();

    // TODO: This should be a preference that can be easily turned on/off
	private static boolean RESOLVING_DEEPLY = true;
    
	
    public static boolean isWSDLType(String typeName)
    {
        return typeName == null ? false :
            WSDL_MESSAGE.equals(typeName)
            || WSDL_PORT_TYPE.equals(typeName)
            || WSDL_OPERATION.equals(typeName)
            || WSDL_PART.equals(typeName)
            || WSDL_SERVICE.equals(typeName)
            || XSD_TYPE_DEFINITION.equals(typeName)
            || XSD_ELEMENT_DECLARATION.equals(typeName)
            || BPEL_PARTNER_LINK_TYPE.equals(typeName)
            || BPEL_ROLE.equals(typeName)
            || BPEL_PROPERTY.equals(typeName);
    }

    public static void setResolveDeeply ( boolean resolveDeeply ) {
    	RESOLVING_DEEPLY = resolveDeeply;
    }
    public static boolean isResolvingDeeply () {
    	return RESOLVING_DEEPLY;
    }
    
    /**
     * Resolve with the given definition.
     */
    public static EObject resolveWSDLReference(Definition definition, QName qname, String name, String refType)
    {
	    EObject resolvedObject = null;
	    
	    if (WSDL_PORT_TYPE.equals(refType))
	    {
	        resolvedObject = resolvePortType(definition, qname);
	    }
	    else if (WSDL_MESSAGE.equals(refType))
	    {
	        resolvedObject = resolveMessage(definition, qname);
	    }
	    else if (WSDL_OPERATION.equals(refType))
	    {
	        resolvedObject = resolveOperation(definition, qname, name);
	    }
	    else if (WSDL_PART.equals(refType))
	    {
	        resolvedObject = resolvePart(definition, qname, name);
	    }
        else if (WSDL_SERVICE.equals(refType))
        {
            resolvedObject = resolveService(definition, qname);
        }
	    else if (XSD_TYPE_DEFINITION.equals(refType))
	    {
	        resolvedObject = resolveXSDTypeDefinition(definition, qname);
	    }
	    else if (XSD_ELEMENT_DECLARATION.equals(refType))
	    {
	        resolvedObject = resolveXSDElementDeclaration(definition, qname);
	    }
	    else if (BPEL_PARTNER_LINK_TYPE.equals(refType))
	    {
	        resolvedObject = resolveBPELPartnerLinkType(definition, qname);
	    }	    
        else if (BPEL_ROLE.equals(refType))
        {
            resolvedObject = resolveBPELRole(definition, qname, name);
        }       
	    else if (BPEL_PROPERTY.equals(refType))
	    {
	        resolvedObject = resolveBPELProperty(definition, qname);
	    }
	    else
	    {
	        System.err.println(WSDLUtil.class.getName() + ": unrecognized refType: " + refType);
	    }
	    
	    return resolvedObject;
    }
    
    
    public static PortType resolvePortType (Definition definition, QName qname)
    {    	
    	PortType result = (PortType) definition.getPortType(qname);
    	
    	if (result != null || RESOLVING_DEEPLY == false) {
    		return result;
    	}
    	
    	Iterator it = definition.getImports(qname.getNamespaceURI()).iterator();
    	while (it.hasNext() && result == null) {
             ImportImpl imp = (ImportImpl) it.next();
             imp.importDefinitionOrSchema();               
             Definition importedDefinition = (Definition) imp.getDefinition();
             if (importedDefinition != null)
             {
                 result = resolvePortType (importedDefinition, qname);                          
             }
        }
        return result;
    }
    
    
    public static Message resolveMessage(Definition definition, QName qname)
    {
    	Message result = (Message) definition.getMessage(qname);
    	if (result != null || RESOLVING_DEEPLY == false) {
    		return result;
    	}
    	
    	Iterator it = definition.getImports(qname.getNamespaceURI()).iterator();
    	while (it.hasNext() && result == null) {
             ImportImpl imp = (ImportImpl) it.next();
             imp.importDefinitionOrSchema();               
             Definition importedDefinition = (Definition) imp.getDefinition();
             if (importedDefinition != null)
             {
                 result = resolveMessage (importedDefinition, qname);                          
             }
        }        
        return result;
    }

    
    
    public static Operation resolveOperation(Definition definition, QName portTypeQName, String operationName)
    {
        PortType portType = resolvePortType(definition, portTypeQName);
        return findOperation(portType, operationName);
    }

    public static Part resolvePart(Definition definition, QName qname, String name)
    {
        Part part = null;
        Message message = resolveMessage(definition, qname);
        if (message != null) 
        {
            part = (Part) message.getPart(name);
        }
        return part;
    }

    private static Service resolveService(Definition definition, QName qname) {
        return (Service) definition.getService(qname);
    }

    private static interface SchemaResolver
    {
        public EObject resolve(XSDSchema schema, QName qname);
    }
    
    // Common code for resolving XSDTypeDefinitions and XSDElementDeclarations
    private static EObject resolveSchemaElement(Definition definition, QName qname, SchemaResolver resolver)
    {
        EObject result = null;
        
        // Check for built-in types
        // TODO Slightly inefficient to evaluate this when recursing

        XSDSchema schema = null;
        String namespace = qname.getNamespaceURI();
        if ("".equals(namespace))
        {
            namespace = null;
        }
        if (XSDConstants.isSchemaForSchemaNamespace(namespace))
        {
            schema = XSDSchemaImpl.getSchemaForSchema(namespace);
        } else if (XSDConstants.isSchemaInstanceNamespace(namespace))
        {
            schema = XSDSchemaImpl.getSchemaInstance(namespace);
        }
        if (schema != null)
        {
            result = resolver.resolve(schema, qname);
            if (result != null)
                return result;
        }

        // Check inline schema

        Types types = definition.getETypes();
        if (types != null)
        {
            List extensibilityElements = types.getExtensibilityElements();
            for (Iterator i = extensibilityElements.iterator(); i.hasNext();)
            {
                Object e = i.next();
                if (e instanceof XSDSchemaExtensibilityElement)
                {
                    XSDSchemaExtensibilityElement schemaEE = (XSDSchemaExtensibilityElement) e;
                    schema = schemaEE.getSchema();
                    if (schema != null)
                    {
                        result = resolver.resolve(schema, qname);
                        if (result != null)
                            return result;
                    }
                }
            }
        }

        // Check imported schemas and definitions

        if (result == null)
        {
            // TODO: I think I need to check all imports, not just those
            // matching the same namespace...
            for (Iterator impIterator = definition.getImports(qname.getNamespaceURI()).iterator(); impIterator.hasNext();)
            {
                ImportImpl imp = (ImportImpl) impIterator.next();
                imp.importDefinitionOrSchema();
                schema = imp.getESchema();
                if (schema != null)
                {
                    result = resolver.resolve(schema, qname);
                    if (result != null)
                        return result;
                }
                Definition importedDefinition = imp.getEDefinition();
                if (importedDefinition != null)
                {
                    result = resolveSchemaElement(importedDefinition, qname, resolver);
                    if (result != null)
                        break;
                }
            }
        }
        return result;        
    }
    
    public static XSDTypeDefinition resolveXSDTypeDefinition(Definition definition, QName qname)
    {
        final SchemaResolver resolver = new SchemaResolver() {
    	    public EObject resolve(XSDSchema schema, QName qname)
    	    {
    	        return schema.resolveTypeDefinition(qname.getNamespaceURI(), qname.getLocalPart());
    	    }
        };
        return (XSDTypeDefinition) resolveSchemaElement(definition, qname ,resolver);
    }
    
    public static XSDElementDeclaration resolveXSDElementDeclaration(Definition definition, QName qname)
    {
        final SchemaResolver resolver = new SchemaResolver() {
    	    public EObject resolve(XSDSchema schema, QName qname)
    	    {
    	        return schema.resolveElementDeclaration(qname.getNamespaceURI(), qname.getLocalPart());
    	    }
        };
        return (XSDElementDeclaration) resolveSchemaElement(definition, qname, resolver);
    }

    public static PartnerLinkType resolveBPELPartnerLinkType(Definition definition, QName qname)
    {
        PartnerLinkType result = null;
        if (definition.getTargetNamespace().equals(qname.getNamespaceURI()))
        {
	        List extensibilityElements = definition.getExtensibilityElements();
	        for (Iterator i = extensibilityElements.iterator(); i.hasNext();) 
	        {
	            Object e = i.next();
	            if (e instanceof PartnerLinkType)
	            {
	                PartnerLinkType plt = (PartnerLinkType) e;
	                if (plt.getName().equals(qname.getLocalPart()))
	                {
	                    result = plt;
	                    break;
	                }
	            }
	        }	            
        }
        if (result == null) // check imports
        {
            for (Iterator impIterator = definition.getImports(qname.getNamespaceURI()).iterator(); impIterator.hasNext();)
            {
                ImportImpl imp = (ImportImpl) impIterator.next();
                imp.importDefinitionOrSchema();               
                Definition importedDefinition = (Definition) imp.getDefinition();
                if (importedDefinition != null)
                {
                    result = resolveBPELPartnerLinkType(importedDefinition, qname);
                    if (result != null)
                        break;
                }
            }
        }
        return result;
    }

    public static EObject resolveBPELRole(Definition definition, QName qname, String name)
    {
        Role result = null;
        PartnerLinkType plt = resolveBPELPartnerLinkType(definition, qname);
        if (plt != null)
        {
            for (Iterator iter = plt.getRole().iterator(); iter.hasNext() && result == null;)
            {
                Role role = (Role) iter.next();
                if (name.equals(role.getName()))
                {
                    result = role;
                }
            }            
        }
        return result;
    }

    public static Property resolveBPELProperty(Definition definition, QName qname)
    {
        Property result = null;
        if (definition.getTargetNamespace().equals(qname.getNamespaceURI()))
        {
	        List extensibilityElements = definition.getExtensibilityElements();
	        for (Iterator i = extensibilityElements.iterator(); i.hasNext();) 
	        {
	            Object e = i.next();
	            if (e instanceof Property)
	            {
	                Property property = (Property) e;
	                if (property.getName().equals(qname.getLocalPart()))
	                {
	                    result = property;
	                    break;
	                }
	            }
	        }	            
        }
        if (result == null) // check imports
        {
            for (Iterator impIterator = definition.getImports(qname.getNamespaceURI()).iterator(); impIterator.hasNext();)
            {
                ImportImpl imp = (ImportImpl) impIterator.next();
                imp.importDefinitionOrSchema();               
                Definition importedDefinition = (Definition) imp.getDefinition();
                if (importedDefinition != null)
                {
                    result = resolveBPELProperty(importedDefinition, qname);
                    if (result != null)
                        break;
                }
            }
        }
        return result;
    }
    
    private static Operation findOperation(PortType portType, String operationName) {
        Operation result = null;
        if (portType != null)
        {
            List operations = portType.getOperations();
            for (Iterator i = operations.iterator(); i.hasNext();)
            {
                Operation operation = (Operation) i.next();
                if (operation.getName().equals(operationName))
                {
                    result = operation;
                    break;
                }
            }
        }
        return result;
    }
}
