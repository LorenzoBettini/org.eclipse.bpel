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
package org.eclipse.bpel.validator.xpath;

/**
 * Java JDK dependencies ...
 */
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.xml.namespace.QName;

/**
 * Dependency on the validation model 
 */
import org.eclipse.bpel.validator.model.IConstants;
import org.eclipse.bpel.validator.model.IFunctionMeta;
import org.eclipse.bpel.validator.model.IModelQuery;
import org.eclipse.bpel.validator.model.IModelQueryLookups;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.IProblem;



/**
 * Dependency on JAXEN 
 */

import org.jaxen.NamespaceContext;
import org.jaxen.SimpleFunctionContext;
import org.jaxen.UnresolvableException;
import org.jaxen.XPathFunctionContext;
import org.jaxen.expr.AllNodeStep;
import org.jaxen.expr.BinaryExpr;
import org.jaxen.expr.CommentNodeStep;
import org.jaxen.expr.EqualityExpr;
import org.jaxen.expr.FilterExpr;
import org.jaxen.expr.FunctionCallExpr;
import org.jaxen.expr.LiteralExpr;
import org.jaxen.expr.LocationPath;
import org.jaxen.expr.LogicalExpr;
import org.jaxen.expr.NameStep;
import org.jaxen.expr.NumberExpr;
import org.jaxen.expr.PathExpr;
import org.jaxen.expr.Predicate;
import org.jaxen.expr.ProcessingInstructionNodeStep;
import org.jaxen.expr.RelationalExpr;

import org.jaxen.expr.TextNodeStep;
import org.jaxen.expr.UnaryExpr;
import org.jaxen.expr.UnionExpr;
import org.jaxen.expr.VariableReferenceExpr;
import org.jaxen.saxpath.Axis;

/**
 * Dependency on the ParserTool
 */



/**
 * This XPathExprValidationVisitor performs minimal statical analysis on
 * the XPath expression that is given to it.
 * <p>
 * Java types are used and mapped onto XPath types in the following way:
 * <ol>
 * <li> String.class   is XPath literal
 * <li> Number.class   is XPath number
 * <li> Boolean.class  is XPath boolean
 * <li> List.class     is a NodeSet
 * <li> Object.class   is a Node
 * </ol> 
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @author Alex Yiu (alex.yiu@oracle.com)
 * 
 * @date Sep 29, 2006
 *
 */

@SuppressWarnings({"nls","boxing"})

public class XPathVisitor  
    implements IConstants {
	
	private SimpleFunctionContext mFunctionContext;

	private NamespaceContext mNamespaceContext;

	/** The XPathExpression Validator that is running this */
	XPathValidator mValidator ;
	
	/** The IModelQuery to ask certain things of the model */
	IModelQuery mModelQuery;
	
	/** The context node on which the expression is attached to */
	INode mContextNode ;
	
	/** The node name on which the expression is attached. */	
	String mNodeName;
	
	// The four types that XPath evaluates into.
	/** An object representing an XPath boolean */ 
	// static final public Object BOOLEAN = new String("#boolean");
	// static final public Object NUMBER  = new String("#number");
	// static final public Object TEXT = new String("#literal");
	// static final public Object Collections.EMPTY_LIST = new String("#node-set");	
	 
	/** For static checking */
	Stack<Object> mContext = new Stack<Object>();
	
	/**
	 * Create a brand new shiny XPathExpression visitor. It is used to visit 
	 * the parsed XPath expression nodes and perform some static analysis on the 
	 * expression.
	 * 
	 * @param validator the XPath expression validator.
	 */
	
	
	public XPathVisitor ( XPathValidator validator )
    {	
		/** The XPath Expression validator */
		mValidator = validator;
		
		/** The access to the model data */
		mModelQuery = validator.getModelQuery();
	
		/** The context INode */
		mContextNode = mValidator.getNode();
		
		/** The context INode node name */
		mNodeName = mContextNode.nodeName();
		
		/** 
		 * Attempt to retrieve this from the model. 
		 * If it fails then we create our own function context
		 */		
		Object context = null; 
		// mModelQuery.lookup(mContextNode, SimpleFunctionContext.class);		
		
		if (context != null && context instanceof SimpleFunctionContext) {
			mFunctionContext = (SimpleFunctionContext) context;
		} else {
			mFunctionContext = (SimpleFunctionContext) XPathFunctionContext.getInstance();
		}

		// we set the initial namespace context for JAXEN to use the namespace
		// look ups behind the Node facade.
		
		setNamespaceContext ( new NamespaceContext () {
			
				public String translateNamespacePrefixToUri(String arg0) {
					
					return mModelQuery.lookup( mContextNode, 
							IModelQueryLookups.LOOKUP_TEXT_PREFIX2NS,  
							arg0,
							null );					
				}			
			} 
		);
		
		
	
   	}

	
	
	/**
	 * Sets the namespace context of the current node.
	 * 
	 * @param nsCtx
	 */
	
	public void setNamespaceContext (NamespaceContext nsCtx) {
		mNamespaceContext = nsCtx;
	}		
	
	


	
	protected void visit (PathExpr expr) {
		
		mValidator.runRules("pathexpr",expr);
		
		visit ( expr.getFilterExpr() );
		
		Object last = contextPeek();
		
		if (isSimpleType(last)) {
			
		}
		visit( expr.getLocationPath() );
	}
		
	
	protected void visit (LocationPath expr ) {
		mValidator.runRules("location",expr);
		visitList( expr.getSteps() );
	}


		
	protected void visit (FilterExpr expr) {		
		visit(expr.getExpr());		
		visitList( expr.getPredicates() );				
	}

	protected void visit (BinaryExpr expr) {
		
		visit(expr.getLHS());			
		Object lhsType = contextPop();
		
		visit(expr.getRHS());		
		Object rhsType = contextPop();
		
		if (expr instanceof LogicalExpr || expr instanceof RelationalExpr || expr instanceof EqualityExpr) {
			mContext.push( true );			
		} else {
			mContext.push( 0.0 );
		}
	}
	
	
	protected void visit(UnaryExpr expr) {
		visit(expr.getExpr());
		Object obj = contextPop();
		if (obj instanceof Number) {
			mContext.push(  ((Number)obj).doubleValue() * (-1) );
		} else {
			mContext.push(0.0);
		}
	}

	
	protected void visit(UnionExpr expr) {		
		
		visit(expr.getLHS());			
		Object lhs = contextPop();
		
		visit(expr.getRHS());		
		Object rhs = contextPop();
		
		//	the union expression must be between Collections.EMPTY_LISTS.
		IProblem problem ;		
		
		if ( isSimpleType(rhs) ) {
			problem = mValidator.createError();
			problem.fill("XPATH_NOT_A_Collections.EMPTY_LIST",  //$NON-NLS-1$
					0, expr.getLHS().getText() );
		}
			
		if (isSimpleType(lhs)) {
			problem = mValidator.createError();
			problem.fill("XPATH_NOT_A_Collections.EMPTY_LIST",  //$NON-NLS-1$
					1,expr.getLHS().getText() );			
		}
		
		mContext.push( Collections.EMPTY_LIST );
	}
	
	protected void visit (NumberExpr expr) {
		mContext.push( expr.getNumber() );
	}
	
	
	protected void visit (LiteralExpr expr) {
		mContext.push( expr.getLiteral() );
	}

	
	@SuppressWarnings("nls")
	protected void visit (VariableReferenceExpr expr) {
		mValidator.runRules("variables",expr);
	}

	
	protected void visit (FunctionCallExpr expr) {
		
		IProblem problem;
					
		String functionName = expr.getFunctionName();
		String functionPrefix = expr.getPrefix();	
		
		String namespaceUri = null;
		
		mValidator.runRules("functions",expr);	
		
		
		// Standard XPath functions
		if ( isEmpty( functionPrefix ) == false) {
					
			namespaceUri = mNamespaceContext.translateNamespacePrefixToUri(functionPrefix);
			checkPrefix(functionPrefix,functionName);
			
			if (namespaceUri != null) {				
				checkDeprecatedFunction(functionName,namespaceUri);
			}
		}
					
		// Try and get the function from the context.
		
		try {
			mFunctionContext.getFunction(namespaceUri, functionPrefix, functionName );
		} catch (UnresolvableException ure) {
			
			if (mValidator.duplicateThing("unresolved.function." + namespaceUri + ":" + functionName) == false ) {				
				problem = mValidator.createError();
				problem.fill("XPATH_UNRESOLVED_FUNCTION",  //$NON-NLS-1$
						functionName,
						ure.getMessage()
				);
			}
		}

		// TODO: Try to figure out what functions return. Most likely these would be
		// node-sets, but the meta data could be more specific in telling us what type
		// does a function return.
		
		mContext.push( Collections.EMPTY_LIST );
		
		// Now process the arguments
		visitList( expr.getParameters() );
	}
	

	/**
	 * Steps generally involve "stepping" over the elements in the query.
	 * 
	 * For this we use the value that was last on the stack as a reference point
	 * to evaluate the "next step". We query the model to make sure that the "next 
	 * step" makes sense until such point that it either does and we are done
	 * or it stops making sense at which point we abandon the effort.
	 */
	
		
	protected void visit (NameStep step) {
		
		String prefix = step.getPrefix();
		IProblem problem;
		
		Object last = contextPop();
		
		// We throw our hands in the air when the prefix does not resolve
		// and we are being asked to do a name step on an axis that we don't support
		// in static checking.
		
		if ( checkPrefix ( prefix , step.getLocalName() ) == false) {
			mContext.push(Collections.EMPTY_LIST);
			return ;
		}

				
		if ( last instanceof List ) {
			mContext.push( Collections.EMPTY_LIST );
		} else if (last instanceof INode) {
			
			// walk the step
			INode context = (INode) last;
			int axis = step.getAxis();
			if (axis != Axis.CHILD) {
				// information and ignore
				problem = mValidator.createWarning();
				problem.fill("XPATH_AXIS_NOT_CHECKED", //$NON-NLS-1$
						step.getText()						
				);
				mContext.push(Collections.EMPTY_LIST);
				
			} else {
			
				String nsURI = mNamespaceContext.translateNamespacePrefixToUri(prefix);
				QName qname = new QName(nsURI,step.getLocalName(),prefix);
	
				// attempt to "step" using the model's meta data.
				
				INode result = mModelQuery.lookup (context, 
						IModelQueryLookups.LOOKUP_NODE_NAME_STEP , qname );
				if (result.isResolved()) {
					mContext.push(result);
				} else {
					mContext.push(Collections.EMPTY_LIST);
					// information and ignore
					problem = mValidator.createError();
					problem.fill("XPATH_NAME_STEP", //$NON-NLS-1$							
							step.getText()
					);
				}
			}

		} else {
			
			problem = mValidator.createError();
			problem.fill("XPATH_NAME_STEP",
					step.getText() );
			
			mContext.push(Collections.EMPTY_LIST);
		}
		
		
		// predicates work on the currently selected node as the context node.
		if (mContext.size() > 0) {
			mContext.push(mContext.peek());
		}
		
		visitList(step.getPredicates());
		contextPop();
		
	}

	protected void visit(ProcessingInstructionNodeStep step) {
		visitList(step.getPredicates());
		
	}

	protected void visit(AllNodeStep step) {
		
		if (mContext.size() > 0) {
			mContext.pop();
		}
		mContext.push(Collections.EMPTY_LIST);
		
		visitList(step.getPredicates());
	}

	
	protected void visit (TextNodeStep step) {
		visitList(step.getPredicates());
		contextPop();
		mContext.push ( new String("text()") );		
	}

	protected void visit (CommentNodeStep step) {
		visitList(step.getPredicates());
		contextPop();
		mContext.push (new String("comment()")) ;
		
	}

	protected void visit (Predicate predicate) {
		
		visit(predicate.getExpr());		
		
		Object result = contextPop();
		
		// Predicates expressions are either boolean or numeric types.
		if (result instanceof Boolean || result instanceof Number ) {
			// warn about predicates.
		}
	}
	

	void visitList ( List<?> list) {
		for(Object next: list) {
			visit(next);
		}
	}

	
	/**
	 * @param obj
	 */
	public void visit ( Object obj ) {
		
		if (obj instanceof PathExpr) {
			visit((PathExpr) obj);
		} else if (obj instanceof LocationPath) {
			visit((LocationPath) obj);
		} else if (obj instanceof BinaryExpr) {
			visit((BinaryExpr) obj);
		} else if (obj instanceof FilterExpr) {
			visit((FilterExpr)obj);			
		} else if (obj instanceof UnaryExpr) {
			visit((UnaryExpr)obj);			
		} else if (obj instanceof NumberExpr) {
			visit((NumberExpr)obj);
		} else if (obj instanceof LiteralExpr) {
			visit((LiteralExpr)obj);			
		} else if (obj instanceof VariableReferenceExpr) {
			visit((VariableReferenceExpr)obj);
		} else if (obj instanceof FunctionCallExpr) {
			visit((FunctionCallExpr) obj);			
		} else if (obj instanceof List) { 
			visitList((List) obj);
		} else if (obj instanceof NameStep) {
			visit((NameStep)obj);
		} else if (obj instanceof ProcessingInstructionNodeStep) {
			visit((ProcessingInstructionNodeStep) obj);
		} else if (obj instanceof AllNodeStep) {
			visit((AllNodeStep)obj);			
		} else if (obj instanceof TextNodeStep) {
			visit((TextNodeStep)obj);
		} else if (obj instanceof CommentNodeStep) {
			visit((CommentNodeStep)obj);
		} else if (obj instanceof Predicate) {
			visit((Predicate)obj);
		} else {
			// ignore or throw error ... ?
		}
	}
	/**
	 * Private methods follow
	 */
	
	
	
	/**
	 * Check if the function is a boolean function.
	 * @param functionExpr
	 * @return true if a function is boolean, false otherwise.
	 */
	boolean isBooleanFunction(FunctionCallExpr functionExpr) {
		
		boolean isBoolean = false;
		String functionName = functionExpr.getFunctionName();
		String prefix = functionExpr.getPrefix();
		
		if ( isEmptyOrWhitespace(prefix) ) {
			
			// these are the function that always returns boolean value in XPath
			// 1.0
			// not(), true(), false(), boolean(), starts-with(), contains()
			// 
			
			isBoolean = "not".equals(functionName)  //$NON-NLS-1$
				|| "true".equals(functionName) //$NON-NLS-1$
				|| "false".equals(functionName) //$NON-NLS-1$
				|| "boolean".equals(functionName) //$NON-NLS-1$
				|| "starts-with".equals(functionName) //$NON-NLS-1$
				|| "getLinkStatus".equals(functionName) //$NON-NLS-1$
				|| "contains".equals(functionName); //$NON-NLS-1$
		} else {
			
			// TODO: The function meta ought to return a return type
			String nsURI = mNamespaceContext.translateNamespacePrefixToUri( prefix );
			IFunctionMeta meta = mModelQuery.lookupFunction( 
												nsURI,
												functionExpr.getFunctionName() );				
			IProblem problem;
			if (meta != null) {
				//
			}
		}
		return isBoolean;
	}

	
	
	void checkDeprecatedFunction(String functionName, String namespaceUri) {
		
		QName qname = new QName(namespaceUri, functionName);
				
		IFunctionMeta meta = mModelQuery.lookupFunction( qname.getNamespaceURI(),
														qname.getLocalPart() );				
		IProblem problem;
		
		if (meta != null && meta.isDeprecated()) {
			problem = mValidator.createWarning();
			problem.fill("XPATH_DEPRECATED_FUNCTION", //$NON-NLS-1$
					qname,
					meta.getDeprecateComment()
			);
		}
	}
	
	
	boolean checkPrefix ( String prefix, String name ) {
		if (isEmptyOrWhitespace(prefix)) {
			return true;
		}
		
		// check to make sure it is resolved to something.
		String nsURI = mNamespaceContext.translateNamespacePrefixToUri(prefix);
		
		if (isEmpty(nsURI)) {
			IProblem problem = mValidator.createError();
			problem.fill("XPATH_UNRESOLVED_NAMESPACE_PREFIX",   //$NON-NLS-1$
					prefix,
					name 
				);
		
			return false;
		}
		
		return true;
	}
		
	protected Object contextPush ( Object obj ) {
		return mContext.push( obj );
	}
	
	protected Object contextPeek () {
		return mContext.size() > 0 ? mContext.peek() : null;
	}

	protected Object contextPop () {
		return mContext.size() > 0 ? mContext.pop() : null;
	}

	static boolean isSimpleType ( Object obj ) {
		if (obj == null) {
			return false;
		}
		
		return (obj instanceof Number  || obj instanceof String || 
				obj instanceof Boolean );
	}
	
	static boolean isBPELNS ( String namespaceUri ) {
		
		return XMLNS_BPEL.equals(namespaceUri) || 
				XMLNS_BPEL20_OLD.equals(namespaceUri);
	}
	
	
    /**
     * Returns true if the string is either null or contains just whitespace.
     */
	
    static boolean isEmptyOrWhitespace( String value )
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
    
    /**
     * Test to see if a string is empty or has a value that is empty.
     * 
     * @param value
     * @return true if empty or null, false otherwise.
     */
    
    static boolean isEmpty ( String value ) {
    	return value == null || value.length() == 0;
    }
}