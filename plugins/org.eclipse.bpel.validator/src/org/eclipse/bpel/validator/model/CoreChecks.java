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
package org.eclipse.bpel.validator.model;


/**
 * Java JDK dependencies ...
 */

/**
 * Dependency on the BPEL validation model 
 */

/**
 * This class contains the core checks which can be invoked from the higher
 * level validator classes.
 * <p>
 * Any common or core check ought to be placed here. 
 * <p>
 * Most validator classes ought to derive from ValidatorCore
 * <p>
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 21, 2006
 *
 */

@SuppressWarnings( {"nls","boxing"} )


public class CoreChecks  implements IConstants {

	Validator mValidator;
	
	
	/**
	 * Create a CoreChecks helper class. It keeps all the relevant checks
	 * in the same place. 
	 * 
	 * @param validator
	 */
	
	public CoreChecks ( Validator validator ) {
		mValidator = validator;
	}
	
	/**
	 * Check if NCName is valid.
	 * 
	 * @param node the node on which we are checking.
	 * @param ncName the ncName
	 * @param atName the attribute name from where the ncName came from.
	 * @return return true if checkName succeeds, false otherwise.
	 */
	
	
	public boolean checkNCName ( INode node, String ncName , String atName ) {
		
		if (ncName == null || ncName.length() == 0) {
			
			IProblem problem = mValidator.createError( node );
			problem.setAttribute(IProblem.CONTEXT, atName);
			problem.fill("BPELC__UNSET_ATTRIBUTE", 
					node.nodeName() ,
					atName,
					KIND_NODE);
			return false ;
		}
		
		// check if valid NCName
		// TODO: More NCName checks
		if (ncName.indexOf(' ') >= 0) {
			
			IProblem problem = mValidator.createError( node );
			problem.setAttribute(IProblem.CONTEXT, atName);
			problem.fill("General.NCName_Bad", //$NON-NLS-1$
					atName, node.nodeName() , ncName );
			return false;
		}
		
		// Check for uniqueness of name within a scope or process ?
		return true;
	}
	
	
	
	
	/**
	 * A generic check against all attributes that are pointers to other
	 * objects. For example, portType on invoke, partnerLink, etc. are 
	 * represented as attributes but in fact refer to larger objects in the
	 * models. 
	 * 
	 * @param node
	 * @param ref the referenced node 
	 * @param name the name of the attribute that references the node
	 * @param kind 1 for activity node, 0 for generic node
	 * @return true if the attribute pointer can be resolved, false otherwise
	 */
	
	@SuppressWarnings("boxing")
	public boolean checkAttributeNode ( INode node, INode ref, String name, int kind ) {
		
		IProblem problem;
		
		if (ref == null) {
			problem = mValidator.createError(node);
			problem.setAttribute(IProblem.CONTEXT, name);
			problem.fill("BPELC__UNSET_ATTRIBUTE", //$NON-NLS-1$
					node.nodeName(), name, kind );
			return false;				
		}
		
		if (ref.isResolved() == false) {		
			String atValue = node.getAttribute( name );
			
			problem = mValidator.createError(node);
			problem.setAttribute(IProblem.CONTEXT, name);
			problem.fill("BPELC__UNRESOLVED_ATTRIBUTE", //$NON-NLS-1$
					node.nodeName(), name, kind , atValue );
			return false;
		}
		
		return true;				
	}
	
	
	
	/** 
	 * Check the node's validator to see if there are any problems reported on the
	 * node. 
	 * 
	 * @param node the context node
	 * @param ref  the referenced node that comes from the node via an attribute
	 * @param name the name of the attribute reference
	 * @param kind 0 for node, 1 for activity
	 * @return true if there are any problems, false otherwise.
	 */
	
	@SuppressWarnings("boxing")
	public boolean checkValidator ( INode node, INode ref, String name, int kind ) {
		
		if (ref == null) {		
			return false;
		}
		
		Validator validator = ref.nodeValidator();
		if (validator == null) {
			return true;
		}
		
		IProblem problem;
		if (validator.hasProblems()) {
			problem = mValidator.createWarning(node);
			problem.setAttribute(IProblem.CONTEXT, name);
			problem.fill("BPELC_REF_NODE_PROBLEMS", //$NON-NLS-1$					
					node.nodeName(),
					ref.nodeName(),
					name,
					kind);
			
			return false;
		}
		
		return true;		
	}

	/**
	 * A generic check against attributes
	 * 
	 * @param node the context node.
	 * @param name name of the attribute
	 * @param kind 1 for activity node, 0 for generic node
	 * @param filter the filter that checks the allowed values 
	 * @param bMandatory true for mandatory, false otherwise. 
	 * @return the attribute value or null if the value does not exist or is not allowed.
	 */
	
	@SuppressWarnings("boxing")
	public String  getAttribute ( INode node, String name, int kind, IFilter<String> filter , boolean bMandatory  ) {
		
		IProblem problem;
		
		String value = node.getAttribute(name);
		
		if (bMandatory) {
			if (Validator.isEmpty(value)) {
				problem = mValidator.createError(node);
				problem.setAttribute(IProblem.CONTEXT, name);
				problem.fill("BPELC__UNSET_ATTRIBUTE", //$NON-NLS-1$
						node.nodeName(), name, kind );
				return null;				
			}
		}
		
		
		if (filter == null || Validator.isEmpty(value) ) {
			return value;
		}
		
		if (filter.select(value)) {
			return value;
		}
		
		problem = mValidator.createError(node);
		problem.setAttribute(IProblem.CONTEXT, name);
		problem.fill("BPELC__INVALID_ATTRIBUTE_VALUE", //$NON-NLS-1$
				node.nodeName(),
				name, 
				value,				
				filter,
				kind);
		
		return null ;		
	}
	
	/**
	 * 
	 * Return the language (expression or query) from the node.
	 * 
	 * @param node
	 * @param atName the attribute name 
	 * @return the default language.
	 */
	
	public String getLanguage (INode node, String atName) {

		// get the expression language
		String lang = node.getAttribute (atName);		
		if (lang == null) {
			INode process = node.rootNode ();
			lang = process.getAttribute(atName);			
		}
		
		// the default language
		
		if (lang == null) {
			return IConstants.XMLNS_XPATH_EXPRESSION_LANGUAGE;
		}
		return lang;
	}
		
	
	
}

		