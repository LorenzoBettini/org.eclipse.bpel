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
package org.eclipse.bpel.validator.helpers;

/** JDK stuff */
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.validator.model.IConstants;
import org.eclipse.bpel.validator.model.IModelQuery;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.RuleFactory;
import org.eclipse.bpel.validator.model.Validator;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Oct 17, 2006
 *
 */


@SuppressWarnings("nls")

public class DOMNodeAdapter implements INode, IConstants {
	
	/**  */
	public static final String KEY = DOMNodeAdapter.class.getName();
				
	/** The validator that we want */
	Validator mValidator = null;
	
	/** The target node for the facade */
	Node targetNode;
	
	/** The target element for the facade */
	Element targetElement;

	String fNodeName;
	
	/**
	 * Create hew DOMNodeAdapter wrapper for this dom node.
	 * @param node
	 */
	
	public DOMNodeAdapter ( Node node ) {
		targetNode = node;
		fNodeName = targetNode.getNodeName();

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			targetElement = (Element) node;
			fNodeName = targetElement.getLocalName();
		}
	}
	

	/**
	 *  (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.INode#children()
	 * @return the children of this node, INode facaded.
	 */	

	public List<INode> children() {
		if (targetElement == null) {
			return Collections.emptyList();
		}
		Node node = targetElement.getFirstChild();
		LinkedList<INode> list = new LinkedList<INode>();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				list.addLast( adapt ( node, INode.class ));
			}
			node = node.getNextSibling();
		}
		return list;
	}

	/** (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.INode#getAttribute(java.lang.String)
	 * 
	 */
	public String getAttribute (String name) {
		if (targetElement == null) {
			return null;
		}
		if (targetElement.hasAttribute(name)) {
			return targetElement.getAttribute(name);
		}
		return null;
	}	
		
	/**
	 * 
	 * @see org.eclipse.bpel.validator.model.INode#getNode(java.lang.String)
	 */
	public INode getNode (String name) {
		List<INode> list = getNodeList(name);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Get node which match this local name
	 * @see org.eclipse.bpel.validator.model.INode#getNodeList(java.lang.String)
	 * @return a list of nodes that match this name
	 */
	public List<INode> getNodeList (String name) {
				
		if (targetElement == null) {
			return Collections.emptyList();
		}
		
		LinkedList<INode> list = new LinkedList<INode>();
		
		Node node = targetElement.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				if (name.equals( e.getLocalName() )) {
					list.addLast( adapt ( node, INode.class ) );
				}
			}
			node = node.getNextSibling();
		}
		return list;
	}

	/**
	 * Answer if this node is resolved.
	 * 
	 * @see org.eclipse.bpel.validator.model.INode#isResolved()
	 * @return true if resolved, false otherwise
	 */
	
	public boolean isResolved() { 
		return (targetElement != null) ;
	}

	/**
	 * Return the node name.
	 * @see org.eclipse.bpel.validator.model.INode#nodeName()
	 * @return the node name of this node
	 */
	
	public String nodeName() {
		return fNodeName;				
	}

	/** (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.INode#nodeValidator()
	 */
	
	public Validator nodeValidator() {
		if (mValidator == null) {
			mValidator = createValidator();
			if (mValidator != null) {
				mValidator.setNode ( this );
			}
		}
		return mValidator;		
	}

	/**
	 * Return the node value.
	 * 
	 * @see org.eclipse.bpel.validator.model.INode#nodeValue()
	 */
	
	public Object nodeValue() {
		return targetNode;
	}

	/**
	 * Return the parent node (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.INode#parentNode()
	 */
	public INode parentNode() {
		if (targetNode == null) {
			return null;
		}
		Node parent = targetNode.getParentNode();
		
		// Some DOM implementation return the document, some do not.
		if (parent == targetNode.getOwnerDocument()) {
			return null;
		}
		return adapt ( parent, INode.class );
	}

	/** (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.INode#rootNode()
	 * @return the root node
	 */
	public INode rootNode() {
		if (targetNode == null) {
			return null;
		}
		Node node = targetNode.getOwnerDocument().getDocumentElement();
		return adapt ( node, INode.class );
	}

	/**
	 * Adapt to whatever class we need.
	 * 
	 * @param target
	 * @param type
	 * @return the object adapted.
	 */
		
	protected <T extends Object> T adapt ( Object target, Class<T> type) {
		IModelQuery mq = ModelQueryImpl.getModelQuery();
		return mq.adapt(target,type);
	}
	
	
	/** 
	 * Since we are "adapting" the DOM model, the convention here is to use
	 * the validator classes defined in org.eclipse.bpel.validator.rules.
	 * <p>
	 * The name of the class which contain the rules is the name of the DOM node
	 * name followed by the word "Validator".
	 * <p>
	 * Clearly, any adapters can override this validator creation step.
	 * 
	 * @return returns a suitable validator, null, if one cannot be created. 
	 */
		
	Validator createValidator () {		
		QName qname = new QName ( targetElement.getNamespaceURI(), targetElement.getLocalName() );
		return RuleFactory.createValidator( qname );
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		
		String nsURI = targetElement.getNamespaceURI();
		
		if (false && nsURI != null && nsURI.length() > 0) {
			sb.append ( "{");
			sb.append( nsURI );
			sb.append ( "}");
			sb.append ( ":" );
		}
		String pfx = targetElement.getPrefix();
		if (pfx != null && pfx.length() > 0) {
			sb.append( pfx );
			sb.append( ":");
		}
		
		sb.append( targetElement.getLocalName() );
		String vName = getAttribute(AT_NAME);
		if (vName != null) {
			sb.append(" \"").append(vName).append("\"");
		}
		sb.append(">");
		return sb.toString();
	}

}
