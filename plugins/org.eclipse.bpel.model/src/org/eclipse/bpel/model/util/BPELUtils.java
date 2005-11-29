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

import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Links;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.proxy.MessageProxy;
import org.eclipse.bpel.model.proxy.OperationProxy;
import org.eclipse.bpel.model.proxy.PortTypeProxy;
import org.eclipse.bpel.model.reordering.IExtensibilityElementListHandler;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.ExtensibleElement;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.ibm.wsdl.util.xml.DOM2Writer;
import com.ibm.wsdl.util.xml.DOMUtils;

public class BPELUtils {
	public static final String ATTR_XMLNS = "xmlns";
	
	public static PartnerLink getPartnerLink(Process process, String name) {
		if (process.getPartnerLinks() == null)	
			return null;
		EList partnerLinkList = process.getPartnerLinks().getChildren();
		PartnerLink partnerLink = null;

		for (int i = 0; i < partnerLinkList.size() && partnerLink == null; i++) {
			PartnerLink obj = (PartnerLink)partnerLinkList.get(i);
	
			if (obj.getName().equals(name)) {
				partnerLink = obj;
			}		
		}
		return partnerLink;
	}
	
	public static Link getLink(Activity activity, String linkName) {
		if (activity != null) {
			EObject container = activity.eContainer();
			while (container != null) {
				if (container instanceof Flow) {
					Links links =((Flow)container).getLinks();
					if (links != null) {
						for (Iterator it = links.getChildren().iterator(); it.hasNext(); ) {
							Link candidate = (Link)it.next(); 		
							if (candidate.getName().equals(linkName)) 
								return candidate;
						}
					}
				}
				container = container.eContainer();
			}
		}
		return null;		
	}	

	public static String getNamespace(BPELResource resource, EObject eObject, String prefix) {
		BPELResource.NotifierMap prefixNSMap = null;
		EObject context = eObject;
				
		while (context != null) {
			prefixNSMap = resource.getPrefixToNamespaceMap(context);
			if (!prefixNSMap.isEmpty()) {
				if (prefixNSMap.containsKey(prefix)) {
					return (String)prefixNSMap.get(prefix);
				}	
			}	
			context = context.eContainer();						
		}
		return null;		
	}	
	
	public static String getPrefix(BPELResource resource, EObject eObject, String namespaceURI) {
		BPELResource.NotifierMap prefixNSMap = null;
		EObject context = eObject;
										
		while (context != null) {
			prefixNSMap = resource.getPrefixToNamespaceMap(context);
			if (!prefixNSMap.isEmpty()) {
				if (prefixNSMap.containsValue(namespaceURI)) {			
					BPELResource.NotifierMap nsPrefixMap = prefixNSMap.reserve();						
					return (String)nsPrefixMap.get(namespaceURI);
				}				
			}
			context = context.eContainer();
		}
		return null;
	}	
	
	/**
	 * Return all global and local namespaces of this context
	 */
	public static Map getAllNamespacesForContext(EObject eObject) {
		Map nsMap = new HashMap();
		BPELResource resource = (BPELResource)eObject.eResource();
		EObject context = eObject;
		
		while (context != null) {
			Map localNSMap = resource.getPrefixToNamespaceMap(context);
			if (!localNSMap.isEmpty()) {
				for (Iterator i=localNSMap.entrySet().iterator(); i.hasNext(); ) {
					Map.Entry entry = (Map.Entry)i.next();
					if (!nsMap.containsKey(entry.getKey()))
						nsMap.put(entry.getKey(), entry.getValue());
				}
			}
			context = context.eContainer();
		}
		return nsMap;
	}								
	
	public static List reorderExtensibilityList(List extensibilityElementListHandlers, ExtensibleElement parent){
		
		List tempExtensibilityElementList = new BasicEList();
		tempExtensibilityElementList.addAll(parent.getExtensibilityElements());
		
		if(extensibilityElementListHandlers.isEmpty() ||
			parent.getExtensibilityElements() == null ||
			parent.getExtensibilityElements().size() <= 1) 
			return tempExtensibilityElementList;
		
		for (Iterator iter = extensibilityElementListHandlers.iterator(); iter.hasNext();) {
			IExtensibilityElementListHandler element = (IExtensibilityElementListHandler) iter.next();
			element.orderList(parent, tempExtensibilityElementList);			
		}
		
		return tempExtensibilityElementList;
		
	}		
		
	public static Node convertStringToNode(String s, BPELResource bpelResource) {
		// Create DOM document
		DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		
		String namespaceURI = bpelResource.getNamespaceURI();
		if (bpelResource.getOptionUseNSPrefix()) {
            String prefix = "bpws";
            s = "<"+prefix+":from xmlns:"+prefix+"=\""+namespaceURI+"\">" + s + "</"+prefix+":from>";
		} else {
			s = "<from xmlns=\""+namespaceURI+"\">" + s + "</from>";
		}
		
		try {
			StringBufferInputStream stream = new StringBufferInputStream(s);
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStreamReader reader = new InputStreamReader(stream, "UTF8");
			InputSource source = new InputSource(reader);
			source.setEncoding("UTF8");
			Document document = builder.parse(source);
			return document.getDocumentElement();
		}
		catch (Exception e) {
			return null;
		}		
		
	}
	
	public static String elementToString(Element element) {
		StringWriter writer = new StringWriter();
		DOM2Writer.serializeAsXML(element, writer);
		return writer.getBuffer().toString();
	}
	
	/**
	 * Tests if <code>namespace</code> equals the BPEL namespace.
	 */
	public static final boolean isBPELNamespace(String namespace) {
		return namespace != null
			&& namespace.equals(BPELConstants.NAMESPACE_2004);
	}

	public static QName createAttributeValue(Element element, String attribute) {
		String prefixedValue = element.getAttribute(attribute);
		return createQName(element, prefixedValue);
	}

	public static QName createQName(Element element, String prefixedValue) {
		int index = prefixedValue.indexOf(':');
		String prefix = (index != -1)? prefixedValue.substring(0, index): null;
		String localPart    = prefixedValue.substring(index + 1);
		String namespaceURI = DOMUtils.getNamespaceURIFromPrefix(element, prefix);
	
		// namespaceURI may be null. That's okay.
		return new QName(namespaceURI, localPart);
	}

	public static PartnerLink getPartnerLink(EObject eObject, String partnerLinkName) {
		EObject container = eObject.eContainer();
		while (container != null) {
			PartnerLinks partnerLinks = null;
			if (container instanceof Process) 
				partnerLinks = ((Process)container).getPartnerLinks();				
			else if (container instanceof Scope) 
				partnerLinks = ((Scope)container).getPartnerLinks();
			
			if (partnerLinks != null) {
				Iterator it = partnerLinks.getChildren().iterator();
				while (it.hasNext()) {
					PartnerLink pl = (PartnerLink)it.next();
					if (pl.getName().equals(partnerLinkName))
						return pl;
				}
			}
			container = container.eContainer();	
		}	
		return null;	
	}

	public static CorrelationSet getCorrelationSetForActivity(Correlation correlation, String correlationSetName) {
		EObject container = correlation.eContainer();
		
		while (container != null) {
			CorrelationSets correlationSets = null;
			if (container instanceof Process) 
				correlationSets = ((Process)container).getCorrelationSets();
			else if (container instanceof Scope)
				correlationSets = ((Scope)container).getCorrelationSets();
				
			if (correlationSets != null) {
				for (Iterator it = correlationSets.getChildren().iterator(); it.hasNext(); ) {
					CorrelationSet correlationSet = (CorrelationSet)it.next();
					if (correlationSet.getName().equals(correlationSetName))
						return correlationSet;
				}
			}
			container = container.eContainer();
		}
		return null;					
	}

	/**
	 * Map default namespace attribute "xmlns" to the empty key "" in the prefix-to-namespace map.
	 */
	public static final String getNSPrefixMapKey(final String attrName) {
	    return ATTR_XMLNS.equals(attrName) ? "" : attrName;
	}

	public static PortType getPortType(URI uri, Element activityElement, String qnameAttribute) {
		QName qName = createAttributeValue(activityElement, qnameAttribute);
		PortTypeProxy portType = new PortTypeProxy(uri, qName);
		return portType;
	}

	public static Message getMessage(URI uri, Element activityElement, String qnameAttribute) {
		QName qName = createAttributeValue(activityElement, qnameAttribute);
		MessageProxy message = new MessageProxy(uri, qName);
		return message;
	}

	public static Operation getOperation(URI uri, PortType portType, Element activityElement, String operationAttribute) {
		if (!activityElement.hasAttribute(operationAttribute)) return null;
		String operationSignature = activityElement.getAttribute(operationAttribute);
		Operation operation = new OperationProxy(uri, portType, operationSignature);
		return operation;
	}

	/**
	 * Tests if <code>node</code> is a DOM {@link Element} with a BPEL namespace.
	 */
	public static final boolean isBPELElement(Node node) {
	    return node != null
	    && node.getNodeType() == Node.ELEMENT_NODE 
	    && isBPELNamespace(node.getNamespaceURI());
	}

	public static String boolean2XML( Boolean b ){
		if( b == null || b.equals(Boolean.FALSE) ){
			return "no";
		}
		return "yes";
	}

	public static String boolean2XML( boolean b ){
		if( !b ){
			return "no";
		}
		return "yes";
	}

	public static CDATASection createCDATASection(Document document, String value) {
	    return document.createCDATASection(stripExtraCR(value));
	}

	public static String stripExtraCR(String value) {
	    StringBuffer result = new StringBuffer();
	    char[] chars = value.toCharArray();
	    for (int i = 0; i < chars.length; i++) {
	        result.append(chars[i]);
	        if (i < chars.length - 1) {
	            if (chars[i] == '\r' && chars[i+1] == '\n') i++;
	        }
	    }
	    
	    return result.toString();
	}
}
