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
 * Java JDK dependency 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BPEL Validation model dependency 
 */

import org.eclipse.bpel.validator.model.Rules.Rule;

/**
 * The base Validator class.
 * 
 * A validator basically encapsulates an INode and runs some special methods (called rules)
 * to check the INode element. The output is a set of IProblem (s).
 * 
 * <p>
 * 
 * Rules are special methods that are discovered by reflection in two ways:
 * <ol>
 *  <li> They either have form "rule_<name>_<index>", or
 *  <li> The have the ARule annotation on them.
 * </ol>
 * 
 * <p>
 * 
 * Order of execution of the rules on a given validator is user defined - 
 * that's what the "index" means above. A rule may also be turned off by
 * the validator code during execution, so that logically exclusive conditions
 * can be simply "turned" off by rules.
 * <p>
 * If the ARule annotation is used then the order() method returns the order of execution.
 * Rules are simply discovered for every validator (only once), then sorted, and then run
 * during invocations.
 * 
 * <p>
 * 
 * Beyond that, there are just 2 other items that govern how rules are executed. 
 * <ol>
 *  <li> The rule tag (simple string), and
 *  <li> the arguments (if any) to the rule method.
 * </ol>
 * <p>
 * Rule tags are just strings which help organize the rules in some way and force their execution
 * at specific times. There are two tags reserved for the system, one is "pass1", the other is "pass2". 
 * User that write validators can invoke other rule sets by calling {@see runRules() }
 * and passing the appropriate tag and arguments. Return values from rule methods are never used.
 * 
 * <p>
 * Validators can be chained, so that you have the following scenario:
 * <pre>
 *   1 <-> 2 <-> 3 <-> 4 ... N
 * </pre>
 * For every INode there is the first validator that is always created. Other validators for the same 
 * INode can be created by simply calling the factory and then attaching the returned validator to the chain.
 * This allows for separation of concerns. For example, "query" nodes may validate in the BPEL and WSDL contexts, and
 * also validate in the Query language context (the same physical node).
 * 
 * <p>
 * 
 * Validators can keep certain state between passes, in the data hash map which is available to
 * the super-classes. This data is erased every time a "pass1" tag is triggered on the validator but
 * remains on for the duration of the object's lifetime.
 * 
 * <p>
 * This simple hash map mechanism is the way that various validators pass data to each other. This is primitive
 * but allows for very loose coupling between the code. When a validator asks for getData() the query goes
 * to its state data and if not found travels in the validator chain in the opposite direction to 
 * execution (always to previous).
 * 
 * <p> 
 * And finally a note about INode. INode represents the generic tree node that some source material
 * is sitting behind. This could be BPEL of course, but it could be any other thing as well. There are
 * adapters which adapt DOM nodes to INodes and EMF nodes to INodes (though there is fewer of those). 
 *
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 14, 2006
 *
 */

@SuppressWarnings({"unchecked","nls","boxing"})

public class Validator implements IConstants {
	
	Logger mLogger = Logger.getLogger( getClass().getName() );
		
	/** The rules which this class exposes to be run */
	Rules mRules;
	
	/** Problems produced by these rules */	
	List<IProblem> mProblems = new ArrayList<IProblem>(4);
	
	/** An empty problem array */
	final static IProblem EMPTY_ARRAY[] = {};
	
	/** The node that we are validating */
	protected INode mNode ;
	
	/** Answer interesting things about the model */
	protected IModelQuery mModelQuery;
	
	/** Currently executing rules ... */
	Stack<Rule> mRuleStack = new Stack<Rule>();	
	
	Rule mCurrentRule = null;
	
	/** Tells us which rules to skip */
	List<IFilter> mRuleFilter = new ArrayList<IFilter>(4) ;
		
	/** The delegated and common checks are placed in this helper class */
	protected CoreChecks mChecks = null;

	/** a list of state information that any validator can keep */
	final Map<String,Object> mData = new HashMap<String,Object>(11);	
	
	/** The selector that can be used to query the INode facade */
	static protected Selector mSelector = Selector.getInstance();
	
	/** Pass 1 tag for rules */
	static public final String PASS1 = "pass1";
	
	/** PAss 2 tag for rules */
	static public final String PASS2 = "pass2";
	
	/** Support chains of validators for the same element */
	Validator fNext = null;	
	Validator fPrev = null;
	
	/** A set denoting the list of Static Analysis checks actually done */
	Set<ARule> mSAChecks = null;
	
	
	/**
	 * Create an instance of the validator.
	 * Discover and setup the rules that we will be running. Primarily this 
	 * includes roaming through the methods and ordering them in the correct
	 * order.
	 */
	
	protected Validator () {
		mRules = Rules.getRules ( getClass() );		
		mChecks = new CoreChecks(this);
	}
	

	/**
	 * @param node  
	 */
	
	public void setNode (INode node) {
		mNode = node;
	}

	/**
	 * Set the model query.
	 * @param query
	 */
	
	public void setModelQuery (IModelQuery query) {
		mModelQuery = query;
	}
	
	
	/**
	 * Use this set to determine coverage of the validators. 
	 * 
	 * @param ruleSet
	 */
	
	public void setSAChecks ( Set<ARule> ruleSet ) {
		mSAChecks = ruleSet;
	}
	
	
	/**
	 * Add a validator to the chain. It is always added to the end of the validator chain.
	 * The validators form a chain starting at the very first one like so
	 * <pre>
	 *   1 <-> 2 <-> 3 <-> 4 <-> 5 ... N
	 * </pre> 
	 * 
	 * When the main dispatcher code runs the validator for the given node, it starts running it at 1 
	 * and continues to N.
	 * 
	 * @param next
	 */
	
	protected void attach ( Validator next ) {
		// no duplicates in chain
		if (this == next) {
			return ;
		}
		
		if (fNext == null) {
			fNext = next;
			
			next.fNext = null;
			next.fPrev = this;
			
			next.mSAChecks = mSAChecks;
		} else {
			fNext.attach ( next );
		}		
	}
			
	
	/**
	 * Validate the node using the rules provided in this validator.
	 * 
	 * @param tag the rules marked with the tag will be run
	 * model   
	 */
		
	final public void validate ( String tag ) {
				
		try {
			if (tag.equals(PASS1)) {
				start();
			}			
			runRules ( tag );			
			if (tag.equals(PASS2)) {				
				end();
			}
		} catch (Throwable t) {			
			mLogger.logp(Level.SEVERE, getClass().getName(), 
					"validate", 
					"Problem executing this validator.",
					t);
		}
		
		if (fNext != null) {
			fNext.validate(tag);
		}
		
	}
	
	
	/**
	 * Return the problems found as a result of validation
	 * in the last validation pass. 
	 * 
	 * @return the list of problems found
	 */
	
	@SuppressWarnings("unchecked")
	final public IProblem[] getProblems () {
		
		if (fNext == null) {
			// Next is empty, just return what we have.
			if (mProblems.size() == 0) {
				return EMPTY_ARRAY;
			}
			return mProblems.toArray( EMPTY_ARRAY );
		}

		// Collect all.
		ArrayList<IProblem> list = new ArrayList<IProblem>();
		Validator p = this;
		while ( p != null ) {
			list.addAll( p.mProblems );
			p = p.fNext;
		}
		return list.toArray( EMPTY_ARRAY );		
	}
	
	
	/**
	 * 
	 * Return true if this node validator has captured any problems regarding
	 * the node in question.
	 * 
	 * Chained validators are also consulted.
	 *  
	 * @return true if there are problems, false if there are no problems reported.
	 * 
	 */
	
	public boolean hasProblems () {
		if (mProblems.size() > 0) {
			return true;
		}
		if (fNext != null) {
			return fNext.hasProblems();
		}
		return false;
	}
	
	
	/** 
	 * 
	 * @param node
	 * @return true if there are problems, false otherwise.
	 */
	
	public boolean hasProblems ( INode node ) {
		if (isDefined(node)) {
			Validator validator = node.nodeValidator();
			if (validator != null) {
				return validator.hasProblems();
			}
			return false;
		}
		
		return true;		
	}
		
	
	/**
	 * Called just before rules are run on this class.
	 * 
	 * @param nextRule the next rule which will be run
	 * @return true if the nextRule can be run, false otherwise.
	 */
		
	boolean startRule ( Rule nextRule ) {
						
		if (mRuleFilter.size() > 0) {
			for(IFilter f: mRuleFilter) {
				if (f.select(nextRule)) {
					return false;
				}
			}			
		}
		mRuleStack.push(nextRule);
		mCurrentRule = nextRule;
		return true;
	}
	
	
	/**
	 * 
	 * @param nextRule
	 */
	void endRule ( Rule nextRule ) {
		mCurrentRule = null;
		
		if (mRuleStack.empty() == false) {
			mRuleStack.pop();						
		}
		
		if (mRuleStack.empty() == false) {
			mCurrentRule = mRuleStack.peek();
		}		
	}
	
	
	
	/**
	 * Disable all rules.
	 */
	
	protected void disableRules ( ) {
		disableRules(0,65536);
	}
	
	/**
	 * @param startIdx
	 * @param endIdx
	 */
	
	protected void disableRules (int startIdx, int endIdx ) {
		mRuleFilter.add( new Rules.IndexFilter ( startIdx,endIdx) );
	}
	
	
	/**
	 * Start the validation pass. This is run before all the rules are run
	 */
	
	protected void start () {				
		
		mRuleFilter.clear();
		mProblems.clear();
		mData.clear();
		
		// Attempt to set mModelQuery and mNode from another validator in the
		// chain, if not already set
		
		Validator p = fPrev;
		while (p != null && (mModelQuery == null || mNode == null)) {
			mNode = p.mNode;
			mModelQuery = p.mModelQuery;
			p = p.fPrev;
		}			
	}
	
	

	/**
	 *  The validation pass has ended for this object
	 */
	
	protected void end ( ) {
		
	}

	
	/**
	 * Runs the rules matching the tag and and the arguments given.
	 * 
	 * @param tag
	 * @param args
	 */
	
	protected void runRules ( String tag, Object ... args ) {
		mRules.runRules(this, tag, args);
	}
	
	
	/**
	 * Add problems derived from contained validators into the
	 * problems we are reporting.
	 * 
	 * @param problems
	 */
		
	protected void addProblems ( IProblem[] problems ) {
		for(IProblem p : problems) {
			mProblems.add(p);
		}
	}
	
	
	protected void internalProblem ( Rule rule, Throwable t ) {
		
		IProblem problem = createWarning();		
		problem.fill("BPELC__INTERNAL",
				mNode.nodeName(),
				rule.getFullName(),
				rule.getIndex(),
				rule.getTag(),
				t );
	}
	
	/**
	 * Mark that an SA check has been performed. 
	 * This is done automatically when create*() methods are called to
	 * create an error/warning/info  but you can call this method directly 
	 * too.
	 * 
	 * The is purely for testing reasons to make sure that cases 
	 * are correctly run. This method may be a noop if the validator does not
	 * have the property "internal.sa.checks" set to the right value.
	 * 
	 * @param arule the annotation to record as having been executed
	 */
	
	protected void markSAExecution (ARule arule) {
		if (mSAChecks == null) {
			return ;
		}
		if (arule == null) {
			arule = mCurrentRule.method.getAnnotation( ARule.class );
		}
		if (arule != null) {
			mSAChecks.add(arule);	
		}		
	}
	
	
	/**
	 * Create a problem based on the context node passed.
	 * 
	 * @param node the context node from which the problem will be 
	 * created.
	 * @return the IProblem marker for the node indicated. 
	 */
	

	protected IProblem createProblem ( INode node ) {
		
		IProblem problem = new Problem();
		
		// remember it 
		mProblems.add(problem);
		
		problem.setAttribute(IProblem.NODE, node );
		
		problem.setAttribute(IProblem.LINE_NUMBER, 
				mModelQuery.lookup(node, IModelQueryLookups.LOOKUP_NUMBER_LINE_NO,-1));
		
		problem.setAttribute(IProblem.COLUMN_NUMBER, 
				mModelQuery.lookup(node, IModelQueryLookups.LOOKUP_NUMBER_COLUMN_NO,-1));
		
		problem.setAttribute(IProblem.CHAR_END,    
				mModelQuery.lookup(node, IModelQueryLookups.LOOKUP_NUMBER_CHAR_END,-1));
		
		problem.setAttribute(IProblem.CHAR_START,  
				mModelQuery.lookup(node, IModelQueryLookups.LOOKUP_NUMBER_CHAR_START,-1));
				
		problem.setAttribute(IProblem.LOCATION,    
				mModelQuery.lookup(node, IModelQueryLookups.LOOKUP_TEXT_LOCATION,null,null));
		
		problem.setAttribute(IProblem.ADDRESS_MODEL,        
				mModelQuery.lookup(node, IModelQueryLookups.LOOKUP_TEXT_HREF,null,null));		
		
		problem.setAttribute(IProblem.ADDRESS_XPATH,        
				mModelQuery.lookup(node, IModelQueryLookups.LOOKUP_TEXT_HREF_XPATH,null,null));		
		
		
		problem.setAttribute(IProblem.BUNDLE_CLAZZ, getClass());
		
		
		if (mCurrentRule != null) {
			
			problem.setAttribute(IProblem.RULE, mCurrentRule.getFullName() );
			
			ARule a = mCurrentRule.method.getAnnotation( ARule.class );
			
			if (a != null) {
				
				markSAExecution(a);
				
				problem.setAttribute( IProblem.SA_CODE, a.sa() );
				problem.setAttribute( IProblem.RULE_DESC, a.desc() );
			}
		}
		
		return problem;
	}
	
	
	/**
	 * Create an error problem on the current node
	 * 
	 * @return the problem created
	 */
	protected IProblem createError ( ) {
		return createError ( mNode );
	}
	
	
	/**
	 * Create an error problem. This does the same as this as
	 * createProblem plus it sets the problem object to severity error.
	 * 
	 * @param node
	 * @return the problem to be recorded.
	 */
	
	protected IProblem createError ( INode node ) {
		IProblem problem = createProblem (node);
		problem.setAttribute(IProblem.SEVERITY, IProblem.SEVERITY_ERROR);
		return problem;
	}

	
	/**
	 * Create a warning problem on the current node
	 * 
	 * @return the problem created
	 */
	
	protected IProblem createWarning( ) {
		return createWarning ( mNode );
	}
	
	/**
	 * Create a warning problem. This does the same as this as
	 * createProblem plus it sets the problem object to severity warning.
	 * 
	 * @param node
	 * @return the problem to be recorded.
	 */
	protected IProblem createWarning ( INode node ) {
		IProblem problem = createProblem (node);
		problem.setAttribute(IProblem.SEVERITY, IProblem.SEVERITY_WARNING);
		return problem;		
	}
	
	
	/**
	 * Create an information problem. This does the same as this as
	 * createProblem plus it sets the problem object to severity information.
	 * 
	 * @param node
	 * @return the problem to be recorded.
	 */
	
	protected IProblem createInfo ( INode node ) {
		IProblem problem = createProblem (node);
		problem.setAttribute(IProblem.SEVERITY, IProblem.SEVERITY_INFO);
		return problem;
		
	}
	
	
	
	/**
	 * Create an information problem on the current node
	 * 
	 * @return the problem created
	 */
	protected IProblem createInfo ( ) {
		return createInfo ( mNode );
	}	
	
	
	/**
	 * Is the node defined ? The check is to see if the node is empty and is resolved. 
	 * If so, then we return true. Otherwise we return false.
	 * 
	 * @param node
	 * @return true if defined, false otherwise
	 */
	
	protected boolean isDefined ( INode node ) {
		return node != null && node.isResolved();
	}
	
	/**
	 * Is the node undefined ? The check is to see if the node is empty or it is unresolved.
	 * If that's the case, we return true, otherwise we return false.
	 * 
	 * @param node
	 * @return true of undefined, false if defined.
	 */
	
	protected boolean isUndefined ( INode node ) {
		return node == null || node.isResolved() == false;
	}
	
	
	/**
	 * Is this a duplicate thing ? 
	 * 
	 * @param key
	 * @return true/false 
	 */
	
	
	protected boolean duplicateThing ( String key ) {
		if (mData.containsKey(key)) {
			return true;
		}
		mData.put(key,Boolean.TRUE);
		return false;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param <T>
	 * @param key
	 * @return the value
	 */
	
	public <T extends Object> T getValue ( String key ) {
		return getValue( mData, key , null );
	}

	
	/**
	 * @param <T>
	 * @param key
	 * @param def
	 * @return the value
	 */
	
	public <T extends Object> T getValue ( String key, T def ) {
		return getValue( mData, key , def );
	}
	
	
	/**
	 * Return the data stored under the key keyName for the node node
	 * on its connected validator.
	 * 
	 * @param <T>  
	 * @param node the reference node
	 * @param keyName the key name
	 * @param def the default value
	 * @return the object stored or the default value passed
	 */
	
	public <T extends Object> T getValue ( INode node, String keyName, T def ) {		
		Validator validator = validatorForNode(node);
		if (validator != null) {
			return getValue(validator.mData,keyName,def);
		}
		return def;		
	}

	
	
	<T extends Object> T getValue ( Map<String,Object> data, String keyName, T def ) {
		
		if (data.containsKey(keyName)) {			
			Object obj = data.get(keyName);			
			if (obj instanceof IValue) {
				return (T) ((IValue)obj).get();
			}
			return (T) obj;
		}
		
		if (fPrev != null) {
			return fPrev.getValue (fPrev.mData, keyName,def);
		}
		return def;
	}

	/**
	 * @param <T>  the type of the object
	 * @param keyName the key name to use
	 * @param value the value to set
	 * @return the previous value under the key
	 */
	
	public <T extends Object> T  setValue ( String keyName, T value) {
		return (T) mData.put(keyName, value);
	}
	
	/**
	 * @param <T> the object 
	 * @param node the node on which this value ought to be set
	 * @param keyName the key name to use
	 * @param value the value to set
	 * @return the previous value held under that key or null
	 */
	
	public <T extends Object> T setValue ( INode node, String keyName, T value) {
		
		Validator validator = validatorForNode(node);
		if (validator != null) {
			return (T) validator.mData.put(keyName,value);
		}
		return null;
	}
	
	
	/**
	 * Return true if the value key is present on us
	 * @param key
	 * @return true if present, false if not
	 */
	
	public boolean containsValueKey ( String key ) {
		if (mData.containsKey(key)) {
			return true;
		}
		if (fPrev != null) {
			return fPrev.containsValueKey(key);
		}
		return false;
	}
	
	/**
	 * Return true if the value key is contained on the node node.
	 * @param node the node
	 * @param key the key
	 * @return true if value key is present, false otherwise.
	 */
	
	public boolean containsValueKey ( INode node, String key ) {
		Validator validator = validatorForNode (node);
		return validator != null ? validator.containsValueKey(key) : false;
	}

	
	
	Validator validatorForNode ( INode node ) {
		if (isDefined(node)) {
			return node.nodeValidator();
		}
		return null;
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
    
    
    
    /**
     * Test to see if a string is empty or has a value that is empty.
     * 
     * @param value
     * @return true if empty or null, false otherwise.
     */
    
    
    static public boolean isEmpty ( String value ) {
    	return value == null || value.length() == 0;
    }

    /** Test to see if a string is non empty 
     *
     * @param value the value to test
     * @return true if non empty, false if empty 
     */
    
    
    static public boolean isNonEmpty ( String value ) {
    	return value != null  && value.length () > 0;
    }

}
