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
package org.eclipse.bpel.validator.rules;

import java.lang.reflect.Field;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.validator.model.ARule;
import org.eclipse.bpel.validator.model.Filters;
import org.eclipse.bpel.validator.model.IFilter;
import org.eclipse.bpel.validator.model.IModelQueryLookups;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.IProblem;
import org.eclipse.bpel.validator.model.RuleFactory;
import org.eclipse.bpel.validator.model.Validator;


/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Oct 12, 2006
 *
 */

@SuppressWarnings("nls")

public class CValidator extends Validator {
	
	/** The parent node */
	protected INode fParentNode;

	/** Children nodes */
	protected List<INode> fChildren;

	List<INode> fTypeToCheckList;
	
	
	/** (non-Javadoc)
	 * @see org.eclipse.bpel.validator.model.Validator#start()
	 */
	@Override
	public void start () {
		
		super.start();
		
		fParentNode = mNode.parentNode();
		
		// get the children, as we will be checking them
		fChildren = mNode.children() ;		
	}
	/**
	 * Check to make sure that parent node 
	 * of my node is within the set of nodes
	 * that is allowed.  
	 */
	
	@ARule(
		sa = 2001,
		desc = "Check to make sure that my parent node is within a set of allowed nodes",
		author = "michal.chmielewski@oracle.com",
		date = "02/15/2007"
	)
	public void rule_CheckParentNode_1 () {
		
		checkParentNode ();
	}
	
	
	/**
	 * Check the parent Node
	 */
	public void checkParentNode () {
		IFilter<INode> filter = parentNodeNames();
		
		if (fParentNode == null) {
			return ;
		}
		
		if (filter.select(fParentNode) ) {
			return ;
		}
		
		// Otherwise, we have a problem ...
		
		IProblem problem;
		problem = createError();
		problem.fill("BPELC__WRONG_PARENT", //$NON-NLS-1$
				mNode.nodeName(),
				fParentNode.nodeName(),
				filter);
		
		// Disable all the rules from being run, if we get here.
		disableRules();	
			
	}
	
	
	/**
	 * Check the children nodes, to make sure that they are present
	 */
	
	@ARule(
		sa = 2002,
		desc = "Check my children nodes (types and occurances)",
		author = "michal.chmielewski@oracle.com",
		date = "02/15/2007"
	)
	public void rule_CheckChildrenNodes_0 () {
		checkChildren();
	}
	
	
	
	/**
	 * Check the children nodes
	 *
	 */
	
	public void checkChildren () {
		
	}

	
	/**
	 * Check to make sure that the node nodeName appears in the 
	 * children as specified by  he min/max parameters.
	 * @param nodeName
	 * @param min
	 * @param max
	 * @return the number of occurances of this child.
	 */
	@SuppressWarnings("nls")
	
	public int checkChild (String nodeName, int min, int max) {
		return checkChild ( new Filters.NodeNameFilter(nodeName),min,max) ;
	}
	
	/**
	 * @param filter
	 * @param min
	 * @param max
	 * @return the # of occurances of this child
	 */
	
	
	@SuppressWarnings("boxing")
	public int checkChild ( IFilter<INode> filter, int min, int max) {
		int count = 0;		
		for(INode n : fChildren) {
			if (filter.select(n)) {
				count += 1;
			}
		}
		
		IProblem problem;
		
		if (count < min) {
			problem = createError();
			problem.fill("BPELC__MIN_IN_PARENT",
					filter,
					mNode.nodeName(),
					count,
					min
			);			
			
		} else if (count > max) {
			problem = createError();
			problem.fill("BPELC__MAX_IN_PARENT",
					filter,					
					mNode.nodeName(),
					count,
					max
			);
		}
		
		return count;		
	}
	
	
	
	/**
	 * Answer with my parent nodes. Subclasses must override this
	 * because most nodes can only be children of certain nodes.
	 * 
	 * @return an array of valid parent nodes.
	 */
		
	
	@SuppressWarnings("unchecked")
	public  IFilter<INode> parentNodeNames () {
		try {
			Field f = getClass().getField("PARENTS");
			return (IFilter) f.get(null);	
		} catch (java.lang.NoSuchFieldException nsfe) {
			// do nothing.
		} catch (Throwable t) {
			t.printStackTrace();
		}		
		return Filters.EMPTY ;
	}	
	
	
	/**
	 * Return the value of getExitOnStandardFault
	 * 
	 * @param node
	 * @return either yes or no, depending on the setting in the scopes.
	 */
	
	public String getExitOnStandardFault ( INode node ) {
		
		INode nn = mSelector.selectParent(node, new IFilter<INode>() {

			public boolean select(INode n) {
				String name = n.nodeName();
				if (name.equals(ND_SCOPE) == false && name.equals(ND_PROCESS) == false) {
					return false;
				}
				String value = n.getAttribute(AT_EXIT_ON_STANDARD_FAULT);
				if (isEmpty(value) == false) {
					return true;					
				}
				return false;
			}		
		});
		
		if (nn != null) {
			return nn.getAttribute(AT_EXIT_ON_STANDARD_FAULT);			
		}
		return NO;
	}
	
	
	/**
	 * Register a type to check.
	 * @param node
	 */
	
	public void registerTypeToCheck (INode node) {
		
		if (isUndefined(node)) {
			return;
		}
		
		if ( fTypeToCheckList == null) {
			
			INode process = mNode.rootNode();
			fTypeToCheckList = getValue(process, "types.to.check", null);
		}
		
		if (fTypeToCheckList == null) {
			return ;
		}
		
		if ( fTypeToCheckList.contains ( node ) == false ) {
			fTypeToCheckList.add ( node );
		}
		
	}

	

	/**
	 * Check if the copy is compatible 
	 * 
	 * @param fromNode
	 * @param toNode
	 */
	public void compatibleCopyCheck ( INode fromNode, INode toNode ) {
		
		INode fromTypeNode = getValue(fromNode,"type",null);
		INode toTypeNode = getValue(toNode,"type",null);
		
		if (hasProblems(fromNode) || hasProblems(toNode)) { 
			return ;
		}
		
		if (fromTypeNode == null && toTypeNode == null) {
			return ;
		}
				
		/** Compatible assign */
		IProblem problem ;
		if (fromTypeNode == null || toTypeNode == null) {
			
			problem = createInfo();
			problem.fill("BPELC_COPY__NOT_CHECKED",
					mNode.nodeName(),
					"text.term.from",					
					fromTypeNode == null ? "text.term.unspecified" : fromTypeNode ,
					"text.term.to",
					toTypeNode == null ? "text.term.unspecified" : toTypeNode ); 
			return ;
		}
		
		
		// source -> destination		
		boolean bCompatible = mModelQuery.check(IModelQueryLookups.TEST_COMPATIBLE_TYPE, fromTypeNode, toTypeNode);
		
		// if these are simple types, warn if incompatibility found as engines may perform implicit conversion
		// much like XPath does.
		
		if (mModelQuery.check(IModelQueryLookups.TEST_IS_SIMPLE_TYPE,fromTypeNode,null) &&
			mModelQuery.check(IModelQueryLookups.TEST_IS_SIMPLE_TYPE,toTypeNode,null)) {
			
			if (bCompatible == false) {
				
				problem = createWarning();
				problem.fill("BPELC_COPY__INCOMPATIBLE_SIMPLE",
						mNode.nodeName(),
						"text.term.from",					
						fromTypeNode,
						"text.term.to",
						toTypeNode
				);
				
			}
			
			
		} else if (bCompatible == false) {
			
			problem = createError();
			problem.fill("BPELC_COPY__INCOMPATIBLE",
					mNode.nodeName(),
					"text.term.from",					
					fromTypeNode,
					"text.term.to",
					toTypeNode
			);
			
		}		
		
	}
	
	
	protected Validator createExpressionValidator ( QName qname ) {

		Validator object = RuleFactory.createValidator ( qname ); 
	
		IProblem problem;
	
		if  (object == null) {
		
			problem = createWarning();
			problem.fill("BPELC__NO_EXPRESSION_VALIDATOR",  //$NON-NLS-1$					
					mNode.nodeName(),
					qname.getNamespaceURI()
			);			
			return null;
		}
		return object;
	}
	
}
