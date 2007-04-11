/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.validator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

import javax.xml.namespace.QName;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.xsd.XSDConcreteComponent;

/**
 * Dependency on the BPEL Validator model 
 */

import org.eclipse.bpel.validator.model.Filters;
import org.eclipse.bpel.validator.model.IConstants;
import org.eclipse.bpel.validator.model.IFilter;
import org.eclipse.bpel.validator.model.IFunctionMeta;
import org.eclipse.bpel.validator.model.IModelQuery;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.Selector;
import org.eclipse.bpel.validator.model.UndefinedNode;
import org.eclipse.bpel.validator.model.XNotImplemented;

/** 
 * Dependency on the platform adapter manager
 */

import org.eclipse.core.runtime.IAdapterManager;

/** 
 * Dependency on the DOM Elements 
 */

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 * Implementation of the IModelQuery interface for the EMF BPEL 
 * object model used in the designer.
 * <p>
 * Basically, the validation model is much thinner as the BPEL EMF model.
 * Because of this, various BPEL entity models (such as the EMF) one
 * can be adapted to the validator BPEL model. 
 *  
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 21, 2006
 *
 */

@SuppressWarnings("nls")

public class ModelQuery implements IModelQuery, IConstants {	
	
	/**
	 * The handle to the EMF root of the BPEL process that we will
	 * validate.
	 * 
	 */
	
	Selector mSelector;
		
	
	/**
	 * Protected constructor, just to initialize the basics the basics.
	 *
	 */
	
	public ModelQuery ( ) {
		mSelector = Selector.getInstance();
	}
			
	/**
	 * Return an answer that decides whether the model has support for 
	 * the given aspects that the validator wants.
	 * @param item 
	 * @param value 
	 * @return true if support present, false otherwise.
	 * 
	 * @see org.eclipse.bpel.validator.model.IModelQuery#hasSupport(int, java.lang.String)
	 */
	
	public boolean hasSupport (int item, String value) {
		
		switch (item) {
		case SUPPORT_QUERY_LANGUAGE :		
			return 
			XMLNS_XPATH_QUERY_LANGUAGE.equals ( value ) ||
			XMLNS_XPATH_QUERY_LANGUAGE_2.equals( value );
		
		case SUPPORT_EXPRESSION_LANGUAGE :
			return 
			XMLNS_XPATH_EXPRESSION_LANGUAGE.equals ( value ) ||
			XMLNS_XPATH_EXPRESSION_LANGUAGE_2.equals( value );			

		case SUPPORT_IMPORT_TYPE :
			return AT_VAL_IMPORT_XSD.equals ( value ) || 
					AT_VAL_IMPORT_WSDL.equals ( value ) ;
		
		case SUPPORT_EXTENSION :
			// by default we have no extensions that we support
			return false;
		}
				
		throw new XNotImplemented("Not implemented: hasSupport(item=" + item + ")");
	}

	
	/**
	 * Answer if these two nodes are the same thing.
	 * @param test the test to perform 
	 * @param n1 node 1
	 * @param n2 node 2
	 * 
	 * @return true/false depending if the objects are the same.
	 */
	
	public boolean check ( int test, INode n1, INode n2 ) {
		
		switch (test) {
		case TEST_EQUAL :

			// is this enough for EMF model ?
			if (n1 == n2) {
				return true;
			}
			
			if (n1 == null || n2 == null) {
				return false;
			}

			// TODO: this is not this simple ...
			
			Object v1 = n1.nodeValue();
			Object v2 = n2.nodeValue();
			
			if (v1 != null) {
				return v1.equals (v2) ;
			} else if (v2 != null) {
				return v2.equals(v1) ;
			} else {
				return true;
			}
		
		case TEST_COMPATIBLE_PARTNER_ACTIVITY_MESSAGE:
			// n1 is source 
			// n2 is destination
			return EmfModelQuery.compatiblePartnerActivityMessages ( getEObject(n1), getEObject(n2) );
			
		case TEST_COMPATIBLE_TYPE :
			// n1 is the source
			// n2 is the destination
			return EmfModelQuery.compatibleType ( getEObject(n1), getEObject(n2)); 
			
		case TEST_IS_SIMPLE_TYPE : 
			if (n1 == null || n1.isResolved() == false) {
				return false;
			}
			return EmfModelQuery.isSimpleType ( getEObject ( n1 ) ) ;
			
		case TEST_RESOVLED :
			if (n1 == null) {
				return false;
			}
			return n1.isResolved();
		}
		
		throw new XNotImplemented("Not implemented: check(test=" + test + ")");		
	}

	/**
	 * Lookup the function meta which is identified by this QName.
	 * @return the function name 
	 * @see org.eclipse.bpel.validator.model.IModelQuery#lookupFunction(String ns, String name)
	 */
	
	public IFunctionMeta lookupFunction (String ns, String name) {
		// TODO Fill in function meta
		return null;
	}

	/**
	 * Lookup the variable which has this name, starting at the current 
	 * context node. Since this is a local BPEL file lookup, we simply
	 * do it using the INode facade.
	 * 
	 * @see org.eclipse.bpel.validator.model.IModelQuery#lookupVariable(org.eclipse.bpel.validator.model.INode, java.lang.String)
	 */
		
	INode lookupVariable (INode context, final String name) {
		
		IFilter<INode> aFilter = new Filters.NodeAttributeValueFilter(AT_NAME, name );
		
		while (context != null) {				
			if (Filters.SCOPE_OR_PROCESS.select (context) ) {				
				INode var = mSelector.selectNode(context,ND_VARIABLES,ND_VARIABLE,aFilter);								
				if (var != null) {
					return var;
				}
			}
			context = context.parentNode();
		}
		return null;
	}
	
	
	/**
	 * Lookup the partner link specified using the context node. Scoping
	 * rules as in variables are used. The lookup is in the local BPEL file,
	 * so we simply use the INode facade for that.
	 * 
	 * @param context
	 * @param name
	 * @return the partner link found, or nothing.
	 */
	
	INode lookupPartnerLink (INode context, String name) {
		IFilter<INode> aFilter = new Filters.NodeAttributeValueFilter(AT_NAME,name );
		while (context != null) {								
			if (Filters.SCOPE_OR_PROCESS.select(context) ) {
				INode obj = mSelector.selectNode(context, ND_PARTNER_LINKS, ND_PARTNER_LINK, aFilter);
				if (obj != null) {
					return obj; 					
				}
			}			
			context = context.parentNode();
		}
		return null;
					
	}

	/**
	 * Lookup the correlation set by the name given starting at current 
	 * reference object.
	 * 
	 * @param context
	 * @param name
	 * @return the correlation set or null
	 */
	
	INode lookupCorrelationSet (INode context, String name) {
		IFilter<INode> aFilter = new Filters.NodeAttributeValueFilter(AT_NAME,name );
		while (context != null) {								
			if (Filters.SCOPE_OR_PROCESS.select(context) ) {
				INode obj = mSelector.selectNode(context, ND_CORRELATION_SETS, ND_CORRELATION_SET, aFilter);
				if (obj != null) {
					return obj; 					
				}
			}			
			context = context.parentNode();
		}
		return null;

	}
	
	/**
	 * Adapt the target object to INode. 
	 * 
	 * @param target the target object to adapt
	 * @return the adapter for the EObject
	 */
	
	INode adapt (EObject target) {
		
		if (target == null) {
			return null;
		}
		Element domElement = null;
		
		// Try to adapt the DOM element first.
		
		if (target instanceof WSDLElement) {						
			domElement = ((WSDLElement)target).getElement();
		} else if (target instanceof XSDConcreteComponent) {
			domElement = ((XSDConcreteComponent)target).getElement();
		}
		
		if (domElement != null) {
			ensureEMFReference( target,domElement );
			return (INode) adapt (domElement, INode.class);			
		}		

		// Adapt the EObject to the INode interface if we can't get the DOM element.
		return (INode) adapt(target,INode.class);		
	}


	 /** Make sure there is a reference from the element
	  * to the EMF model.
	  * 
	  * @param eObj
	  * @param elm
	  */
	 

	void ensureEMFReference (EObject eObj, Element elm) {
		 
		 Object obj = elm.getUserData("emf.model");
		 
		 // check if already set
		 if (obj != null && obj == eObj) {
			 return ;
		 }
		 		 
		 // set it.
		 elm.setUserData("emf.model", eObj, null);

		 
		 // set it on all the children of this element as well.
		 //
		 Iterator<Object> it = eObj.eAllContents();
		 
		 while (it.hasNext()) {
			 Object next = it.next();
			 Element domElement = null;
			 
			 if (next instanceof WSDLElement) {
				 domElement = ((WSDLElement)next).getElement();
			 } else if (next instanceof XSDConcreteComponent) {
				 domElement = ((XSDConcreteComponent)next).getElement();
			 } 
			 if ( domElement != null ) {
				 domElement.setUserData("emf.model",next,null);
			 }			 
		 }
	 }
	
	 

	@SuppressWarnings("nls")
	EObject getEObject ( INode context ) {
		
		Object value = context.nodeValue();
		if (value instanceof Element) {
			Element elm = (Element) value;
			return  (EObject) elm.getUserData("emf.model"); 
		} else if (value instanceof EObject) {
			return (EObject) value;
		} else if (context.isResolved() == false) {
			return null;
		}
		
		throw new RuntimeException("getEObject() - cannot find EMF Object");
	}
	
	
	Element getElement ( INode context ) {
		
		Object value = context.nodeValue();
		if (value instanceof Element) {
			return (Element) value;
		} else if (value instanceof WSDLElement) {
			return ((WSDLElement)value).getElement();			
		} else if (value instanceof XSDConcreteComponent) {
			return ((XSDConcreteComponent) value).getElement();
		}
		return null;		
	}
	
	/**
	 * Lookup and return the specified link name based on the context node.
	 * @param context
	 * @param name
	 * @return the link node or null,
	 */
	
	public INode lookupLink ( INode context, String name ) {
		IFilter<INode> aFilter = new Filters.NodeAttributeValueFilter(AT_NAME, name );
		while (context != null) {
			String contextNodeName = context.nodeName();
			if (contextNodeName.equals(ND_FLOW)) {
				INode link = mSelector.selectNode(context,ND_LINKS,ND_LINK, aFilter);
				if (link != null) {
					return link;
				}
			}
			context = context.parentNode();
		}
		
		return null;
	}
		
	
	/**
	 * General node lookup.
	 * 
	 * @param context
	 * @param what
	 * @param name
	 * @return the result of the lookup
	 */
	
	public INode lookup (INode context, int what, String name ) {
		if (isEmpty(name)) {
			return null;			
		}
		//
		if (name.indexOf(':') >= 0) {
			return lookup(context,what,createQName(context, name));
		}
		return lookup (context, what, new QName(name) );
	}

	/**
	 * General node lookup.
	 * 
	 * @param context the context node, it cannot be null.
	 * @param what the thing to lookup
	 * @param qname the QName of the node to lookup
	 * @return the result of the lookup
	 */
	
	public INode lookup ( INode context, int what, QName qname ) {
				
		String name = qname.getLocalPart();			
				
		EObject eObj = null;
		INode result = null;
		 
		// Make sure all the lookup items are switched.
		switch (what) {
		
		case LOOKUP_NODE_VARIABLE :
			if (context.isResolved()) {
				result = lookupVariable(context,name);
			}			
			if (result == null) {
				result =  new UndefinedNode(ND_VARIABLE, AT_NAME, name);
			}
			break;
			
		case LOOKUP_NODE_LINK :
			if (context.isResolved()) {
				result = lookupLink(context, name);	
			}			
			if (result == null) {
				result = new UndefinedNode(ND_LINK,AT_NAME,name);
			}
			break;
			
			
		case LOOKUP_NODE_IMPORT :
			
			if (context.isResolved()) {
				eObj = EmfModelQuery.lookupImport(getEObject(context), name );				
			}
			
			if (eObj == null) {
				result = new UndefinedNode(ND_IMPORT);
			}
			break;
			
		case LOOKUP_NODE_PARTNER_LINK :
			if (context.isResolved() ) {
				result = lookupPartnerLink (context, name);				
			}						
			if (result == null) {
				result = new UndefinedNode(ND_PARTNER_LINK,AT_NAME,name);
			}
			break;
					
			
		case LOOKUP_NODE_CORRELLETION_SET :
			if (context.isResolved()) {
				result = lookupCorrelationSet (context,name);
			}
			if (result == null) {
				result = new UndefinedNode(ND_CORRELATION_SET,AT_NAME,name);
			}
			break;	
			
			
			/**
			 * The items below are queried from the EMF model. These are largely resolved
			 * by the model and we rely on that resolution to produce the right result.
			 * 
			 */
			
		case LOOKUP_NODE_PARTNER_LINK_TYPE :	
			if ( context.isResolved() ) {
				eObj = EmfModelQuery.lookupPartnerLinkType ( getEObject(context), qname ); 				
			}			
			if (eObj == null) {
				result = new UndefinedNode(WSDL_ND_PARTNER_LINK_TYPE, AT_NAME, qname.getLocalPart() );
			}
			break;
			
		case LOOKUP_NODE_ROLE :
			if ( context.isResolved() ) {
				eObj =  EmfModelQuery.lookupRole ( getEObject(context), name ) ;
			}			
			if (eObj == null) {
				result = new UndefinedNode(WSDL_ND_PARTNER_LINK_TYPE, AT_NAME, name );				
			}
			break;				
			
		case LOOKUP_NODE_OPERATION :
			if ( context.isResolved()) {				
				eObj = EmfModelQuery.lookupOperation ( getEObject(context), name );
			}			
			if (eObj == null) {
				result = new UndefinedNode ( WSDL_ND_OPERATION, AT_NAME, name );
			}
			break;			
			
		case LOOKUP_NODE_PORT_TYPE :	
			if ( context.isResolved() ) {
				eObj = EmfModelQuery.lookupPortType ( getEObject(context), qname) ;					
			}				
			if (eObj == null) {
				result = new UndefinedNode ( WSDL_ND_PORT_TYPE, AT_NAME, qname.getLocalPart() );
			}
			break;
			
		case LOOKUP_NODE_MESSAGE_TYPE :	
			if ( context.isResolved() ) {
				eObj = EmfModelQuery.lookupMessage ( getEObject(context), qname) ;				
			}					
			if (eObj == null) {
				result = new UndefinedNode(WSDL_ND_MESSAGE, AT_NAME, qname.getLocalPart() );
			}
			break;			

		case LOOKUP_NODE_MESSAGE_PART :
			return adapt(EmfModelQuery.lookupMessagePart ( getEObject(context), name));
			
		case LOOKUP_NODE_XSD_ELEMENT :
			if ( context.isResolved() )  {
				eObj = EmfModelQuery.lookupXSDElement ( getEObject(context), qname);
			}
			if (eObj == null) {				
				result = new UndefinedNode(AT_ELEMENT,AT_NAME, qname.getLocalPart());
			}
			break;
			
		case LOOKUP_NODE_XSD_TYPE :			
			if (context.isResolved()) {				
				eObj = EmfModelQuery.lookupXSDType ( getEObject(context), qname);				
			}
			if (eObj == null) {
				result = new UndefinedNode(AT_TYPE,AT_NAME, qname.getLocalPart());	
			}
			break;
			
		case LOOKUP_NODE_PROPERTY :
			if (context.isResolved()) {				
				eObj = EmfModelQuery.lookupProperty ( getEObject(context), qname);				
			}
			if (eObj == null) {
				result = new UndefinedNode(EXT_ND_PROPERTY, AT_NAME, qname.getLocalPart());	
			}
			break;
			
		case LOOKUP_NODE_NAME_STEP :			
			if (context.isResolved()) {
				eObj = EmfModelQuery.lookupNameStep( getEObject(context), qname);
			}
			if (eObj == null) {
				result =  new UndefinedNode(AT_ELEMENT,AT_NAME,qname.getLocalPart() );	
			}
			break;
		
			
		case LOOKUP_NODE_TYPE_OF_PART : 
			if (context.isResolved()) {
				eObj = EmfModelQuery.lookupTypeOfPart ( getEObject(context), qname );
			}
			if (eObj == null) {
				result = new UndefinedNode(AT_ELEMENT,AT_NAME,"Unknown");
			}
			break ;
		
		default :
			throw new XNotImplemented("Not implemented: lookupNode(item=" + what + ")");
		}

		if (eObj == null) {
			return result ;
		}
		return adapt ( eObj );
				
	}	
	
	
	/**
	 * Lookup some text related item in the model object.
	 * 
	 * @param context the context node
	 * @param what what to lookup
	 * @param key the value to lookup
	 * @param def the default value to return
	 * @return the looked up value.
	 * 
	 */
	
	@SuppressWarnings("nls")
	public String lookup ( INode context, int what, String key, String def) {
				
		Element elm ;
		EObject eObj;
		
		switch (what) {
		
		case LOOKUP_TEXT_LOCATION :
			// Should this be anything else ?
			return context.nodeName();
			
		case LOOKUP_TEXT_NS2PREFIX : 
			return def;
			
		case LOOKUP_TEXT_PREFIX2NS :
			if (key == null) {
				return def;
			}			
			String nsKey = "xmlns:" + key;
			while (context != null) {								
				String result = context.getAttribute( nsKey );
				if (result != null) {
					return result;
				}
				context = context.parentNode();
			}
			break;
			
		case LOOKUP_TEXT_TEXT :
			elm = getElement(context);
			if (elm == null) {
				return def;
			}			
			StringBuilder text = new StringBuilder();
			Node n = elm.getFirstChild();
			while (n != null) {
				text.append( n.getTextContent() );
				n = n.getNextSibling();
			}
			return text.toString();		
 
		case LOOKUP_TEXT_HREF :
			eObj = getEObject(context);
			if (eObj == null || eObj.eResource() == null) {
				break;
			}			
			return eObj.eResource().getURIFragment(eObj);
			
		case LOOKUP_TEXT_RESOURCE_PATH :
			eObj = getEObject(context);
			if (eObj == null || eObj.eResource() == null ) {
				break;
			}
			URI uri = eObj.eResource().getURI();
			if (uri.isFile()) {
				return uri.toFileString();
			}
			return uri.toString();					
			
		case LOOKUP_TEXT_HREF_XPATH :
			elm = getElement(context);
			if (elm == null) {
				return def;
			}			
			return computeXPathTo ( elm );
			
		default : 
			throw new XNotImplemented("Not implemented: lookupText(item=" + what + ")");
		}
		
		return def;
	}
	
	
	/**
	 * Lookup a number value or parameter in the model.
	 * @param context 
	 * @param what 
	 * @param def 
	 * @return the looked-up value, or the default return value passed.
	 * 
	 */
	@SuppressWarnings("nls")
	
	public int lookup ( INode context, int what, int def ) {
		
		Element elm = getElement(context);
		if (elm == null) {
			return def;
		}
		
		String key = null;
		switch (what) {
		case LOOKUP_NUMBER_LINE_NO :
			key = "location.line";
			break;		
		case LOOKUP_NUMBER_COLUMN_NO :
			key = "location.column";
			break;		
		case LOOKUP_NUMBER_CHAR_START :
			key = "location.charStart";
			break;
		case LOOKUP_NUMBER_CHAR_END :
			key = "location.charEnd";
			break;
		
		case LOOKUP_NUMBER_LINE_NO_2 :
			key = "location2.line";
			break;
		case LOOKUP_NUMBER_COLUMN_NO_2 :
			key = "location2.column";
			break;
		case LOOKUP_NUMBER_CHAR_START_2 :
			key = "location2.charStart";
			break;
		case LOOKUP_NUMBER_CHAR_END_2 :
			key = "location2.charEnd";
			break;
			
		default : 
			throw new XNotImplemented("Not implemented: lookupNumber(item=" + what + ")");			
		}
		
		try {
			return ((Number)elm.getUserData(key)).intValue();
		} catch (Throwable t) {
			// 
		}			
		return def;
	}
	
	
	/**
	 * Lookup an object in the model. 
	 * @param context the context node 
	 * @param clazz the class of the object
	 * @return return the object instance if found in the mode, null otherwise.
	 */
	
	public Object lookup (INode context, Class<?> clazz) {
		return null;
	}
	
	
	/**
	 * Create the QName from the name indicated. The context node is used to resolve 
	 * namespaces prefixes. 
	 * 
	 * @param context the context node
	 * @param name
	 * @return the QName parsed out from name.
	 */
	@SuppressWarnings("nls")
	public QName createQName ( INode context, String name ) {
		
		int index = name.indexOf(':');
		String prefix = (index != -1)? name.substring(0, index): "";
		String localPart = name.substring(index + 1);
		
		String namespaceURI = lookup(context, LOOKUP_TEXT_PREFIX2NS, prefix, null );
	
		// namespaceURI may be null. That's okay.
		return new QName(namespaceURI, localPart, prefix );
	}
	
	
	
	/**
	 * Adapt the target to a type. This goes through the platform adapter 
	 * mechanism.
	 * 
	 * @param target
	 * @param type
	 * @return an object that is the adapter the target with the given class.
	 */
		
	Object adapt (Object target, Class<?> type) {
		IAdapterManager manager = AdapterManagerHelper.getAdapterManager();
		return manager.getAdapter(target, type);		
	}
	
	
	static boolean isEmpty ( String value ) {
    	return value == null || value.length() == 0;
    }
	
	 /** 
	  * Compute the the XPath to the node child from the root of the XML
     * tree. Since this is just based on the instance of this document,
     * we must proceed every element selection by an index [] of the element.
     * @param child
     * @return the XPath from the root of the document tree.
     */

    @SuppressWarnings("nls")
	static public String computeXPathTo (Node child) {
    	
        Stack<String> stack = new Stack<String>();
        
        Node parent = null;
        if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
            return "--No XPath to CDATA NODE exists --";
        }

        // Attribute nodes are special since they are children of
        // the element node.
        if (child.getNodeType() == Node.ATTRIBUTE_NODE) {
            Attr att = (Attr) child;
            stack.push("/@" + att.getName());
            child = att.getOwnerElement();
        }
        parent = child.getParentNode();

        while (parent != null && parent != child) {
        	
            NodeList nl = parent.getChildNodes();
            int idx = 1;
            int cnt = 0;
            for (int i = 0, j = nl.getLength(); i < j; i++) {
                Node anode = nl.item(i);

                if (anode.getNodeType() != child.getNodeType()) {
                    continue;
                }
                if (anode.getNodeName().equals(child.getNodeName()) == false) {
                    continue;
                }
                // We have seen cnt nodes that match the type and the name
                // to the one that we are. In other words, our parent
                // has "cnt" children nodes.
                cnt += 1;
                if (anode.equals(child)) {
                    idx = cnt;
                }
            }
            // this is the node, figure how to get here.
            // Since this is based on the instance of XML document,
            // we can figure out the path by doing some shortcuts.
            String cmd = "";
            switch (child.getNodeType()) {
                case Node.TEXT_NODE:
                    cmd = "/text()";
                    break;
                case Node.COMMENT_NODE:
                    cmd = "/comment()";
                    break;
                default :
                    String ns = child.getNamespaceURI();
                    if (ns == null || ns.length() == 0) {
                        cmd = "/" + child.getNodeName();
                    } else if (child.getPrefix() == null) {
                        cmd = "/:" + child.getLocalName();
                    } else {
                        cmd = "/" + child.getPrefix() + ":" + child.getLocalName();
                    }
                    break;
            }
            stack.push(cmd + (cnt == 1 ? "" : "[" + idx + "]"));

            // Go up
            parent = parent.getParentNode();
            child = child.getParentNode();
        }
        // Now we are done.
        StringBuilder xpath = new StringBuilder(256);
        while (stack.isEmpty() == false) {
            xpath.append(stack.pop());
        }
        return xpath.toString();
    }
    
    
    /**
     * Return our priority.
     * @return we are at priority 0
     */
    
    public int priority () {
    	return 0;
    }
    
    
    
    
    
    
    /**
     * The rest is a static implementation which deals with collecting model query
     * implementations from other plugins.
     */
    
    
    static IModelQuery   gModelQuery = null;
    static IModelQuery[] gaModelQuery = {};
    
    static ArrayList<IModelQuery> gModelQueryList = new ArrayList<IModelQuery> (8) ;
    
    
    static protected void register ( IModelQuery mq ) {
    	gModelQuery = mq;
    	gModelQueryList.add(mq);
    }
    
    
    /**
     * @return
     */
    
    static public final IModelQuery getModelQuery () {
    	
    	if (gModelQuery != null) {    		
    		return gModelQuery;
    	}
    	
    	gaModelQuery = gModelQueryList.toArray(gaModelQuery);
    	
    	if (gaModelQuery.length == 0) {
    		
    		gModelQuery = new ModelQuery();
    		
    	} else if (gaModelQuery.length == 1) {
    		
    		gModelQuery = gaModelQuery[0];
    		
    	} else {
    		
    		Arrays.sort(gaModelQuery, new Comparator<IModelQuery>() {
				public int compare(IModelQuery o1, IModelQuery o2) {
					return o1.priority() - o2.priority();
				}    			
    		});
    		
    		gModelQuery = (IModelQuery) java.lang.reflect.Proxy.newProxyInstance( 
    				ModelQuery.class.getClassLoader(), 
    				
    				new Class<?>[]{ IModelQuery.class },
    				
    				new InvocationHandler () {

						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							
							Object result = null;
							for(IModelQuery mq : gaModelQuery) {
								try {
									return method.invoke(mq, args);
								} catch (XNotImplemented xnotImpl) {
									// not implemented by this model query instance
								}
							}
							return result;
						}    					
    				});
    		
    	}
    	
    	
    	// Return the model query object
    	return gModelQuery;
    }
    
    
}
		
