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
package org.eclipse.bpel.ui.editors.xpath;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.ui.editors.xpath.rules.AxisRule;
import org.eclipse.bpel.ui.editors.xpath.rules.FloatRule;
import org.eclipse.bpel.ui.editors.xpath.rules.FunctionRule;
import org.eclipse.bpel.ui.editors.xpath.rules.SingleCharRule;
import org.eclipse.bpel.ui.editors.xpath.rules.SingleOperatorRule;
import org.eclipse.bpel.ui.editors.xpath.rules.StringRule;
import org.eclipse.bpel.ui.editors.xpath.rules.WordRule;
import org.eclipse.bpel.ui.preferences.PreferenceConstants;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.swt.SWT;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Oct 25, 2006
 *
 */
public class XPathSourceScanner extends BufferedRuleBasedScanner {
	
	XPathWhitespaceDetector fWhiteSpaceDetector = new XPathWhitespaceDetector ();
	
	IWordDetector fNCNameDetector = new XPathWordDetector.NCNameWordDetector ();
	
	IWordDetector fWordDetector = new XPathWordDetector();
	
	/**
	 * The scanner for the XPath source editor, which provides
	 * syntax coloring based on the default damager and repairer.
	 * 
	 * @param manager
	 */
	
	public XPathSourceScanner( ColorManager manager ) {

		IToken defToken = new Token ( 
					new TextAttribute (
						manager.getColor( PreferenceConstants.DEFAULT  )												
					) );
		
		IToken operator = new Token ( 
				new TextAttribute (
						manager.getColor( PreferenceConstants.OPERAND  ),
						null,
						SWT.BOLD						
				) );
		
		IToken number = new Token (
				new TextAttribute (
						manager.getColor( PreferenceConstants.NUMBER  ),
						null,
						SWT.BOLD						
				) );

					
		IToken string = new Token ( 
				new TextAttribute (  
						manager.getColor( PreferenceConstants.STRING ) ) ) ;
		
		
		IToken brackets = new Token (
				new TextAttribute (  
						manager.getColor( PreferenceConstants.BRACKET ),
						null,
						SWT.BOLD ) 						
				);


		IToken axis = new Token (
				new TextAttribute (  
						manager.getColor( PreferenceConstants.AXIS ),
						null,
						SWT.ITALIC ) 						
				);

		IToken pathSep = new Token (
					new TextAttribute (  
							manager.getColor( PreferenceConstants.PATH_SEPARATOR ),
							null,
							SWT.BOLD ) 						
					);					
		
		IToken functionsDefault = new Token (
				new TextAttribute (  
						manager.getColor( PreferenceConstants.FUNCTIONS_XPATH ),
						null,
						SWT.ITALIC ) 											
				);
		
		IToken functions = new Token (
				new TextAttribute (  
						manager.getColor( PreferenceConstants.FUNCTIONS_XPATH ) 										
				)) ;
		
		
		
		// The list of rules for this scanner.
		List<IRule> rules = new ArrayList<IRule>(12);
		
				
		// Add rule for double quotes string
		rules.add( new SingleLineRule("\"", "\"", string , '\\') ); //$NON-NLS-1$ //$NON-NLS-2$
		// Add a rule for single quotes string
		rules.add( new SingleLineRule("'", "'", string , '\\') ); //$NON-NLS-1$ //$NON-NLS-2$
	
		// Add function calls ...
		
		// Add generic whitespace rule.
		rules.add( new WhitespaceRule(new XPathWhitespaceDetector()) );
		
		// numbers
		rules.add ( new FloatRule ( number )) ;
		
		rules.add ( new SingleOperatorRule ( operator, "+-*=|/<>" ) );	//$NON-NLS-1$
		
		// Operators of sorts ...
		rules.add ( new StringRule ( operator, "!=") );		//$NON-NLS-1$
		rules.add ( new StringRule ( operator, ">=") );		//$NON-NLS-1$
		rules.add ( new StringRule ( operator, "<=") );		//$NON-NLS-1$
		rules.add ( new StringRule ( operator, ">=") );		//$NON-NLS-1$
		
		rules.add ( new SingleCharRule ( brackets, "[]().@," ) );	//$NON-NLS-1$			
		
		rules.add ( new StringRule ( operator, "//") );			//$NON-NLS-1$
		
		rules.add ( new StringRule ( pathSep, "::") );			//$NON-NLS-1$
		
		
		// rule for operators ...
		WordRule wordRule = new WordRule( new XPathWordDetector () );
		
		wordRule.addWord ("mod",operator);	//$NON-NLS-1$
		wordRule.addWord ("div",operator);	//$NON-NLS-1$
		wordRule.addWord ("and",operator);	//$NON-NLS-1$
		wordRule.addWord ("or",operator);	//$NON-NLS-1$		
		rules.add( wordRule );

		AxisRule axisRule = new AxisRule ( fNCNameDetector );
		axisRule.addWords(AXIS, axis);
		rules.add(axisRule);
				
		// The basic XPath functions
		FunctionRule functionRule = new FunctionRule ( fNCNameDetector );
		functionRule.addWords(XPATH_FUNCTIONS,functionsDefault);
		rules.add(functionRule);
		
		// All other functions
		functionRule = new FunctionRule ( fNCNameDetector );
		functionRule.addWord( WordRule.ANY,functions);
		rules.add(functionRule);
				
		wordRule = new WordRule( fWordDetector );
		wordRule.addWord ( WordRule.ANY, defToken );
		rules.add( wordRule );
		
		setDefaultReturnToken( defToken ) ;
		
		setRules ( rules.toArray(new IRule[]{} ));		
	}

	
	
	@SuppressWarnings("nls")
	
	static private final String[] XPATH_FUNCTIONS = {
		
		"last","position","count","id","local-name","namespace-uri","name",
		"string","concat","starts-with","contains","substring-before","substring-after",
		"substring","string-length","normalize-space","translate",
		"boolean","not","true","false","lang",
		"number","sum","floor","ceiling","round"
	};
	
	
	@SuppressWarnings("nls")
	static private final String[] AXIS = {
		"ancestor",
		"ancestor-or-self",
		"attribute",
		"child",
		"descendant",
		"descendant-or-self",
		"following",
		"following-sibling",
		"namespace",
		"parent",
		"preceding",
		"preceding-sibling",
		"self"		
	};
	
	
}
