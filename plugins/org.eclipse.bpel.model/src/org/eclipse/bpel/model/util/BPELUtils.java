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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.proxy.MessageProxy;
import org.eclipse.bpel.model.proxy.OperationProxy;
import org.eclipse.bpel.model.proxy.PortTypeProxy;
import org.eclipse.bpel.model.reordering.IExtensibilityElementListHandler;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.bpel.model.resource.BPELResourceSetImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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

/**
 * 
 * @author (IBM) 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * 
 * @date Feb 27, 2007
 *
 */

@SuppressWarnings("nls")

public class BPELUtils {
	
	/** Namespace attribute */
	public static final String ATTR_XMLNS = "xmlns"; //$NON-NLS-1$
	
	
	/**
	 * Lookup an externally defined object by its QName by using a ref object 
	 * (any object from the BPEL EMF model) using a refType and name.
	 * 
	 * This is a general query mechanism used by the import resolvers to resolve
	 * anything from the perspective of the BPEL process.
	 * 
	 * @param ref the reference object
	 * @param qname the QName to resolve.
	 * @param name the name within the QName to resolve (can be null)
	 * @param refType the thing to look up (look in WSDLUtil and XSDUtil for the possible values)
	 * @return the looked up object (or null).
	 */
	
	static public EObject lookup (EObject ref, QName qname, String name, String refType) {
		
		Process process = getProcess ( ref );
		if (process == null) {
			return null;
		}
		
		Iterator<?> it = process.getImports().iterator();
		EObject result = null;
		
        while ( it.hasNext() ) {
            Import imp = (Import) it.next();            
            
            // The null and "" problem ...
            String ns = imp.getNamespace();
            if (ns == null) {
            	ns = javax.xml.XMLConstants.DEFAULT_NS_PREFIX;
            }
            
            if (ns.equals(qname.getNamespaceURI()) == false || imp.getLocation() == null ) {
            	continue;
            }
                        
    	    ImportResolver[] resolvers = ImportResolverRegistry.INSTANCE.getResolvers(imp.getImportType());
            for (int i = 0; i < resolvers.length; i++)  {            	
                result = resolvers[i].resolve(imp, qname, name, refType);
                if (result != null) {
                    return result;
                }
            }
        }
        
        return result;
	}

	/**
	 * Get name namespace associated to the prefix.
	 *  
	 * @param resource the BPEL resource
	 * @param eObject the reference object
	 * @param prefix the prefix to lookup
	 * @return the namespace associated with the prefix.
	 */
	
	public static String getNamespace (BPELResource resource, EObject eObject, String prefix) {
		
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
	
	/**
	 * Given an eObject, and a prefix, get the mapped prefix namespace.
	 * 
	 * @param eObject the object
	 * @param prefix the prefix  
	 * @return the namespace or null, if the namespace is not mapped
	 */
	
	public static String getNamespace ( EObject eObject, String prefix) {
		Resource resource = eObject.eResource();
		if (resource instanceof BPELResource) {
			return getNamespace ( (BPELResource) resource, eObject, prefix);
		}
		throw new IllegalArgumentException("EMF object is not a BPEL resource.");	
	}
	

	/**
	 * Set the namespace prefix on the EObject passed. The namespace -> prefix mapping is defined
	 * on the object passed. Note that this is different then getPrefix(), where the prefix mapping
	 * is searched via the hierarchy of the EMF model.
	 * 
	 * @param resource the BPEL resource
	 * @param eObject an object at which level the prefix mapping ought to be set.
	 * @param namespaceURI namespace URI
	 * @param prefix the prefix.
	 * @throws RuntimeException if the prefix is already set.
	 */
	
	
	@SuppressWarnings("unchecked")
	public static void setPrefix (BPELResource resource, EObject eObject, String namespaceURI, String prefix) {
		
		BPELResource.NotifierMap prefixNSMap = resource.getPrefixToNamespaceMap(eObject);
		if (prefixNSMap.containsKey(prefix)) {
			throw new RuntimeException("Prefix is already mapped!");
		}
		prefixNSMap.put(prefix, namespaceURI);
	}	


	
	/**
	 * Get prefix associated with a namespace.
	 * @param resource the resource 
	 * @param eObject the reference object
	 * @param namespaceURI the namesaceURI
	 * @return the namespace prefix associated to that namespace URI.
	 */
	
	public static String getPrefix (BPELResource resource, EObject eObject, String namespaceURI) {
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
	 * Given a starting object from within our EMF model return the prefix 
	 * associated with the namespace passed. The object tree is traversed up to the
	 * root to find the associated mapping and first such association is returned.
	 *   
	 * @param eObject the EMF object to start at.
	 * @param namespace the XML namespace to query for prefix mapping
	 * @return the prefix name or null of no mapping exists
	 */
	
	public static String getNamespacePrefix (EObject eObject, String namespace) {
		Resource resource = eObject.eResource();
		if (resource instanceof BPELResource) {
			return getPrefix ( (BPELResource) resource, eObject, namespace);
		}
		throw new IllegalArgumentException("EMF object is not a BPEL resource.");	
	}
	
	
	
	/**
	 * Given a starting object from within our EMF model set the prefix 
	 * associated with the namespace passed. The object tree is traversed up to the
	 * root to find the associated mapping and first such association is returned.
	 *   
	 * @param eObject the EMF object to start at.
	 * @param namespace the XML namespace to query for prefix mapping
	 * @param prefix the prefix to set 
	 * 
	 */
	
	public static void setNamespacePrefix (EObject eObject, String namespace, String prefix) {
		Resource resource = eObject.eResource();
		if (resource instanceof BPELResource) {
			setPrefix ( (BPELResource) resource, getNearestScopeOrProcess(eObject) , namespace, prefix) ;
			return ;
		}
		throw new IllegalArgumentException("EMF object is not a BPEL resource.");
	}
	
	/**
	 * Return the process object, the root of the EMF BPEL model, from the reference
	 * object passed.
	 * 
	 * @param eObj the reference object.
	 * @return the process object, or null
	 */
	
	static public Process getProcess ( EObject eObj ) {
		EObject context = eObj;
		
		while (context != null) {
			if (context instanceof Process) {
				return (Process) context;
			}
			context = context.eContainer();
		}
		return null;
	}
	/**
	 * Return the closest Scope or Process objects from the EMF object
	 * hierarchy. We use this code to assign namespace prefix mappings when they
	 * don't exist.
	 * 
	 * @param eObject
	 * @return closest scope or process object.
	 */
	
	public static EObject getNearestScopeOrProcess (EObject eObject) {
		
		EObject context = eObject;
		while (context != null) {
			if (context instanceof Scope) {
				return context;
			}
			if (context instanceof Process) {
				return context;
			}
			context = context.eContainer();
		}
		return null;
	}
	
	
	 
	/**
	 * Return all global and local namespaces of this context
	 * @param eObject reference object
	 * @return the prefix map.
	 */
	@SuppressWarnings("unchecked")
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
	
	
	/**
	 * Reorder extensibility list.
	 * 
	 * @param extensibilityElementListHandlers
	 * @param parent
	 * @return the reordered list.
	 */
	@SuppressWarnings("unchecked")
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
		
	
	/**
	 * Convert string to a BPEL DOM node.
	 * 
	 * @param s the string
	 * @param bpelResource the BPEL resource
	 * @return the node
	 */
	
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
			StringReader sr = new StringReader(s);
			DocumentBuilder builder = factory.newDocumentBuilder();		
			InputSource source = new InputSource(sr);
			source.setEncoding("UTF8");
			Document document = builder.parse(source);
			return document.getDocumentElement();
		}
		catch (Exception e) {
			return null;
		}		
		
	}
	
	/**
	 * Convert an element to string.
	 * @param element the element
	 * @return string version of the element XML source
	 */
	
	public static String elementToString(Element element) {
		StringWriter writer = new StringWriter();
		DOM2Writer.serializeAsXML(element, writer);
		return writer.getBuffer().toString();
	}
	
	
	
	/**
	 * Create attribute value.
	 * 
	 * @param element
	 * @param attribute
	 * @return QName for the attribute value.
	 */
	
	public static QName createAttributeValue(Element element, String attribute) {
		String prefixedValue = element.getAttribute(attribute);
		return createQName(element, prefixedValue);
	}

	/**
	 * Create QName
	 * 
	 * @param element the element used as a reference for namespace reference.
	 * @param prefixedValue the prefixed value (NCName as a QName, that is "foo:bar")
	 * @return the QName created
	 */
	
	public static QName createQName(Element element, String prefixedValue) {
		int index = prefixedValue.indexOf(':');
		String prefix = (index != -1)? prefixedValue.substring(0, index): null;
		String localPart    = prefixedValue.substring(index + 1);
		String namespaceURI = DOMUtils.getNamespaceURIFromPrefix(element, prefix);
	
		// namespaceURI may be null. That's okay.
		return new QName(namespaceURI, localPart);
	}

	
	/**
	 * Return a partner link whose name is partnerLinkName.
	 * 
	 * @param eObject the reference object
	 * @param partnerLinkName the partner link name.
	 * @return the PartnerLink or null if one does not exist.
	 */
	
	public static PartnerLink getPartnerLink (EObject eObject, String partnerLinkName) {
		
		EObject container = eObject ;
		
		while (container != null) {
			
			PartnerLinks partnerLinks = null;
			
			if (container instanceof Process) {
				partnerLinks = ((Process)container).getPartnerLinks();				
			} else if (container instanceof Scope) { 
				partnerLinks = ((Scope)container).getPartnerLinks();
			}
				
			
			if (partnerLinks != null) {
				Iterator<?> it = partnerLinks.getChildren().iterator();
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

	
	/**
	 * Get the correlation set for an activity.
	 * 
	 * @param correlation the correlation object
	 * @param correlationSetName the name of the correlation set
	 * @return the correlation set for that activity.
	 */
	
	public static CorrelationSet getCorrelationSetForActivity(Correlation correlation, String correlationSetName) {
		EObject container = correlation.eContainer();
		
		while (container != null) {
			CorrelationSets correlationSets = null;
			if (container instanceof Process) 
				correlationSets = ((Process)container).getCorrelationSets();
			else if (container instanceof Scope)
				correlationSets = ((Scope)container).getCorrelationSets();
			else if (container instanceof OnEvent)
				correlationSets = ((OnEvent)container).getCorrelationSets();
			
			if (correlationSets != null) {
				for (Iterator<?> it = correlationSets.getChildren().iterator(); it.hasNext(); ) {
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
	 * @param attrName attribute name
	 * @return the prefix map key
	 */
	public static final String getNSPrefixMapKey(final String attrName) {
	    return ATTR_XMLNS.equals(attrName) ? "" : attrName;
	}

	
	/**
	 * Return a port type proxy for the given URI.
	 * 
	 * @param uri the URI
	 * @param activityElement the reference element for namespace lookups
	 * @param qnameAttribute the QName (as string) representing the portType.
	 * @return the port type proxy.
	 */
	
	public static PortType getPortType(URI uri, Element activityElement, String qnameAttribute) {
		QName qName = createAttributeValue(activityElement, qnameAttribute);
		PortTypeProxy portType = new PortTypeProxy(uri, qName);
		return portType;
	}

		
	/**
	 * Return the message proxy for the given URI object.
	 * 
	 * @param uri the URI
	 * @param activityElement the reference element for namespace lookups
	 * @param qnameAttribute the QName (as string) representing the message.
	 * @return the message proxy.
	 */
	
	public static Message getMessage(URI uri, Element activityElement, String qnameAttribute) {
		QName qName = createAttributeValue(activityElement, qnameAttribute);
		MessageProxy message = new MessageProxy(uri, qName);
		return message;
	}

	/**
	 * Return the operation proxy for the given URI object.
	 * 
	 * @param uri the URI
	 * @param portType the port type that has this operation.
	 * @param activityElement the reference element for namespace lookups
	 * @param operationAttribute the QName (as string) representing the operation.
	 * @return the message proxy.
	 */
	
	public static Operation getOperation(URI uri, PortType portType, Element activityElement, String operationAttribute) {
		if (!activityElement.hasAttribute(operationAttribute)) return null;
		String operationSignature = activityElement.getAttribute(operationAttribute);
		Operation operation = new OperationProxy(uri, portType, operationSignature);
		return operation;
	}
	

	/**
	 * Tests if <code>node</code> is a DOM {@link Element} with a BPEL namespace.
	 * @param node the node to test
	 * @return true if BPEL element, false otherwise.
	 */
	
	public static final boolean isBPELElement(Node node) {
	    return node != null
	    && node.getNodeType() == Node.ELEMENT_NODE 
	    && BPELConstants.isBPELNamespace(node.getNamespaceURI());
	}

	/**
	 * Boolean as XML version string.
	 * @param b boolean (true/false)
	 * @return "yes" or "no"
	 */
	
	public static String boolean2XML( Boolean b ){
		if( b == null || b.equals(Boolean.FALSE) ){
			return "no";
		}
		return "yes";
	}

	/**
	 * Boolean as XML version string.
	 * @param b boolean (true/false)
	 * @return "yes" or "no"
	 */
	
	public static String boolean2XML( boolean b ){
		if( !b ){
			return "no";
		}
		return "yes";
	}

	/**
	 * Create CData section with the string value indicated.
	 * 
	 * @param document
	 * @param value
	 * @return the CData section.
	 */
	
	public static CDATASection createCDATASection(Document document, String value) {
	    return document.createCDATASection(stripExtraCR(value));
	}

	
	
	/**
	 * Strip extra carriage returns.
	 * @param value
	 * @return the "pretty" string
	 */
	
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

	/**
	 * This is a slightly hacked resource set that we will be using for to solve the problem
	 * of loading the right resources from URLs that betray no information on the type of the resource. 
	 * @param resourceSet
	 * 
	 * @return the BPELResourceSetImpl that walks around the problem indicated.
	 *  
	 */
	
	public static BPELResourceSetImpl slightlyHackedResourceSet (ResourceSet resourceSet) {
		
		if (resourceSet instanceof BPELResourceSetImpl) {
			return (BPELResourceSetImpl) resourceSet;
		}
		
		Map map = resourceSet.getLoadOptions();
		BPELResourceSetImpl result = (BPELResourceSetImpl) map.get(BPELResourceSetImpl.SLIGHTLY_HACKED_KEY);
		if (result == null) {
			result = new BPELResourceSetImpl();
			map.put(BPELResourceSetImpl.SLIGHTLY_HACKED_KEY, result);			
		}
		return result;
	}
	
	/**
	 * Return the resource set that we should be using to load "specific" type of resources.
	 * The "slightlyHacked" resource set is kept in the load options map.
	 * 
	 * @param eObj
	 * @return the slightly hacked resource set. 
	 * 
	 */
	public static BPELResourceSetImpl slightlyHackedResourceSet (EObject eObj) {
		return slightlyHackedResourceSet ( eObj.eResource().getResourceSet() );
	}
	
	
	
	static Map <String,Locale> string2Locale = new HashMap<String,Locale>();
	static Map <Locale,String> locale2String = new HashMap<Locale,String>();
	
	static {
		StringBuilder sb = new StringBuilder();
		for (Locale l : Locale.getAvailableLocales()) {
			 sb.setLength(0);
			 sb.append(l.getLanguage());
			 if (isEmptyOrWhitespace( l.getCountry()) == false) {
				 sb.append("-").append(l.getCountry());
			 }
			 
			 String key = sb.toString().toLowerCase();
			 
			 string2Locale.put(key,l);
			 locale2String.put(l,key);
		}
	}
	
	

	
	/**
	 * Lookup the locale for the key.
	 * 
	 * @param key the key (language + country code)
	 * @return the locale or the default locale.
	 */
	
	public static Locale lookupLocaleFor (String key) {
		if (key == null) {
			return Locale.getDefault();		
		}
		key = key.replace('_', '-').toLowerCase();
		Locale locale = string2Locale.get(key);
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}
	
	
	/**
	 * Lookup the key for the locale.
	 * 
	 * @param locale the locale
	 * @return the key associated with it.
	 */
	
	public static String lookupLocaleKeyFor (Locale locale) {
		if (locale == null) {
			return locale2String.get( Locale.getDefault() );		
		}
		String key = locale2String.get(locale);
		if (key == null) {
			key = locale2String.get( Locale.getDefault() );
		}
		return key;
	}
	
	
	/**
     * Returns true if the string is either null or contains just whitespace.
	 * @param value 
	 * @return true if empty or whitespace, false otherwise.
     */
		
   static public boolean isEmptyOrWhitespace( String value )
   {
       if( value == null || value.length() == 0) {
           return true;
       }               
       for( int i = 0, j = value.length(); i < j; i++ )
       {
           if( ! Character.isWhitespace( value.charAt(i) ) ) {
               return false;
           }
       }
       return true;
   }
   
	
}
