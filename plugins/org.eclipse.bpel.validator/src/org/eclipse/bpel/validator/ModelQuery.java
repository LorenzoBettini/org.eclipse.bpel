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

import java.util.Iterator;

import javax.xml.namespace.QName;

import org.eclipse.bpel.validator.helpers.ModelQueryImpl;
import org.eclipse.bpel.validator.model.IFunctionMeta;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.UndefinedNode;
import org.eclipse.bpel.validator.model.XNotImplemented;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.xsd.XSDConcreteComponent;
import org.w3c.dom.Element;



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

public class ModelQuery extends ModelQueryImpl {		
	
	/**
	 * Return an answer that decides whether the model has support for 
	 * the given aspects that the validator wants.
	 * @param item 
	 * @param value 
	 * @return true if support present, false otherwise.
	 * 
	 * @see org.eclipse.bpel.validator.model.IModelQuery#hasSupport(int, java.lang.String)
	 */
	
	@Override
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
	
	@Override
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
			return EmfModelQuery.compatiblePartnerActivityMessages ( 
					adapt(n1,EObject.class), 
					adapt(n2,EObject.class) );
			
		case TEST_COMPATIBLE_TYPE :
			// n1 is the source
			// n2 is the destination
			return EmfModelQuery.compatibleType ( adapt(n1,EObject.class), adapt(n2,EObject.class)); 
			
		case TEST_IS_SIMPLE_TYPE : 
			if (n1 == null || n1.isResolved() == false) {
				return false;
			}
			return EmfModelQuery.isSimpleType ( adapt(n1,EObject.class) ) ;
			
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
	
	@Override
	public IFunctionMeta lookupFunction (String ns, String name) {
		// TODO Fill in function meta
		return null;
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
		 Iterator<EObject> it = eObj.eAllContents();
		 
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
	
	 
	
	
	/**
	 * General node lookup.
	 * 
	 * @param context the context node, it cannot be null.
	 * @param what the thing to lookup
	 * @param qname the QName of the node to lookup
	 * @return the result of the lookup
	 */
	
	@Override
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
				eObj = EmfModelQuery.lookupImport(adapt(context,EObject.class), name );				
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
				eObj = EmfModelQuery.lookupPartnerLinkType ( adapt(context,EObject.class), qname ); 				
			}			
			if (eObj == null) {
				result = new UndefinedNode(WSDL_ND_PARTNER_LINK_TYPE, AT_NAME, qname.getLocalPart() );
			}
			break;
			
		case LOOKUP_NODE_ROLE :
			if ( context.isResolved() ) {
				eObj =  EmfModelQuery.lookupRole ( adapt(context,EObject.class), name ) ;
			}			
			if (eObj == null) {
				result = new UndefinedNode(WSDL_ND_PARTNER_LINK_TYPE, AT_NAME, name );				
			}
			break;				
			
		case LOOKUP_NODE_OPERATION :
			if ( context.isResolved()) {				
				eObj = EmfModelQuery.lookupOperation ( adapt(context,EObject.class), name );
			}			
			if (eObj == null) {
				result = new UndefinedNode ( WSDL_ND_OPERATION, AT_NAME, name );
			}
			break;			
			
		case LOOKUP_NODE_PORT_TYPE :	
			if ( context.isResolved() ) {
				eObj = EmfModelQuery.lookupPortType ( adapt(context,EObject.class), qname) ;					
			}				
			if (eObj == null) {
				result = new UndefinedNode ( WSDL_ND_PORT_TYPE, AT_NAME, qname.getLocalPart() );
			}
			break;
			
		case LOOKUP_NODE_MESSAGE_TYPE :	
			if ( context.isResolved() ) {
				eObj = EmfModelQuery.lookupMessage ( adapt(context,EObject.class), qname) ;				
			}					
			if (eObj == null) {
				result = new UndefinedNode(WSDL_ND_MESSAGE, AT_NAME, qname.getLocalPart() );
			}
			break;			

		case LOOKUP_NODE_MESSAGE_PART :
			return adapt(EmfModelQuery.lookupMessagePart ( adapt(context,EObject.class), name),INode.class );
			
		case LOOKUP_NODE_XSD_ELEMENT :
			if ( context.isResolved() )  {
				eObj = EmfModelQuery.lookupXSDElement ( adapt(context,EObject.class), qname);
			}
			if (eObj == null) {				
				result = new UndefinedNode(AT_ELEMENT,AT_NAME, qname.getLocalPart());
			}
			break;
			
		case LOOKUP_NODE_XSD_TYPE :			
			if (context.isResolved()) {				
				eObj = EmfModelQuery.lookupXSDType ( adapt(context,EObject.class), qname);				
			}
			if (eObj == null) {
				result = new UndefinedNode(AT_TYPE,AT_NAME, qname.getLocalPart());	
			}
			break;
			
		case LOOKUP_NODE_PROPERTY :
			if (context.isResolved()) {				
				eObj = EmfModelQuery.lookupProperty ( adapt(context,EObject.class), qname);				
			}
			if (eObj == null) {
				result = new UndefinedNode(EXT_ND_PROPERTY, AT_NAME, qname.getLocalPart());	
			}
			break;
			
		case LOOKUP_NODE_NAME_STEP :			
			if (context.isResolved()) {
				eObj = EmfModelQuery.lookupNameStep( adapt(context,EObject.class), qname);
			}
			if (eObj == null) {
				result =  new UndefinedNode(AT_ELEMENT,AT_NAME,qname.getLocalPart() );	
			}
			break;
		
			
		case LOOKUP_NODE_TYPE_OF_PART : 
			if (context.isResolved()) {
				eObj = EmfModelQuery.lookupTypeOfPart ( adapt(context,EObject.class), qname );
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
		return adapt ( eObj , INode.class );
				
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
	
	@Override
	@SuppressWarnings("nls")
	public String lookup ( INode context, int what, String key, String def) {
						
		EObject eObj;
		
		switch (what) {
		
		case LOOKUP_TEXT_LOCATION :
			// Should this be anything else ?
			return context.nodeName();
			
		case LOOKUP_TEXT_NS2PREFIX : 
			return def;
			
		case LOOKUP_TEXT_PREFIX2NS :
			return super.lookup(context, what,key,def);
			
		case LOOKUP_TEXT_TEXT :
			return super.lookup(context, what,key,def);
 
		case LOOKUP_TEXT_HREF :
			eObj = adapt(context,EObject.class);
			if (eObj == null || eObj.eResource() == null) {
				break;
			}			
			return eObj.eResource().getURIFragment(eObj);
			
		case LOOKUP_TEXT_RESOURCE_PATH :
			eObj = adapt(context,EObject.class);
			if (eObj == null || eObj.eResource() == null ) {
				break;
			}
			URI uri = eObj.eResource().getURI();
			if (uri.isFile()) {
				return uri.toFileString();
			}
			return uri.toString();					
			
		case LOOKUP_TEXT_HREF_XPATH :
			return super.lookup(context, what,key,def);
			
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
	@Override
	@SuppressWarnings("nls")
	
	public int lookup ( INode context, int what, int def ) {
		
		Element elm = adapt(context,Element.class);
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
	 * Adapt the target to a type. This goes through the platform adapter 
	 * mechanism.
	 * 
	 * @param target
	 * @param type
	 * @return an object that is the adapter the target with the given class.
	 */
		
	@Override
	public <T extends Object> T adapt (Object target, Class<T> type) {
		
		// short cut
		if (type.isInstance( target ) ||  target == null) {
			return type.cast(target);
		}
		
		T result = super.adapt(target,type);
		if (result != null) {
			return result;
		}
		
		if (target instanceof INode) {
			
			INode aTarget = (INode) target;
			// INode -> Element
			
			if (type == Element.class) {
				return type.cast(adaptINode2Element ( aTarget ));
			}
			
			if (type == EObject.class) {
				return type.cast( adaptINode2EObject ( aTarget ) );
			}						
		}
		
		if (target instanceof EObject) {	
			EObject aTarget = (EObject)target;
			if (type == INode.class) {
				return type.cast(adaptEObject2INode ( aTarget ));
			}
		}
		
		IAdapterManager manager = AdapterManagerHelper.getAdapterManager();
		return type.cast(manager.getAdapter(target, type));		
	}
	

    
	
	Element adaptINode2Element ( INode context ) {
		
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
	 * Adapt the target object to INode. 
	 * 
	 * @param target the target object to adapt
	 * @return the adapter for the EObject
	 */
	
	INode adaptEObject2INode (EObject target) {
		
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
			return adapt (domElement, INode.class);			
		}		

		// Adapt the EObject to the INode interface if we can't get the DOM element.
		return adapt(target,INode.class);		
	}
	
 


	@SuppressWarnings("nls")
	EObject adaptINode2EObject ( INode context ) {
		
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
    
}
		
