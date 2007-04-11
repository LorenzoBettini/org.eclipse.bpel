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
package org.eclipse.bpel.validator.test;


import java.io.File;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.Assert;

import org.eclipse.bpel.validator.Main;
import org.eclipse.bpel.validator.model.IFilter;
import org.eclipse.bpel.validator.model.IProblem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jan 19, 2007
 *
 */

@SuppressWarnings("nls")

public class TestRunner  {

	static Main fValidator ;
	
	/** The home folder */
	static File fHome;
	
	/** BPEL source file */
	static File fSrc;
	
	/** Problems generated from the validation */
	static IProblem[] fProblems;
	
	/** Problems read from the problems file */
	static IProblem[] fExpectedProblems;	
	

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
		fValidator = new Main();
		String home = System.getenv( "BPEL_TEST_HOME");
		String testCase = System.getenv( "BPEL_TEST_CASE");
		
		Assert.assertNotNull("BPEL_TEST_HOME is undefined",home);
		Assert.assertNotNull("BPEL_TEST_CASE is undefined",testCase);
		
		fHome = new File (home);
		Assert.assertTrue(fHome + " does not exist", fHome.exists() );
				
		Assert.assertTrue(fHome + " is not a directory", fHome.isDirectory() );
				
		fSrc = new File (fHome + "/tests/" + testCase + "/" + testCase + ".bpel");	
		
		fExpectedProblems = Main.readMessages( new File ( fSrc + ".xml") );
		
		fProblems = fValidator.validate( fSrc );
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	
	/** There should be some errors after validating */
	@Test()
	public void Validate () throws Exception {					
		
		Assert.assertTrue("Problems have been found", 
				fProblems.length > 0);
	}

	/**
	 * Check undefined messages	
	 * @throws Exception
	 */
	
	@Test()
	public void CheckUndefinedMessages () throws Exception {

		checkProblems(IProblem.MESSAGE, new IFilter<String> () {
			public boolean select(String m) {
				return (m == null || m.startsWith("!"));
			}			
		});		
	}

	
	/** Check model pointers 
	 * @throws Exception 
	 */
	
	@Test()
	public void CheckModelPointers () throws Exception {
				
		checkProblems("address.model", new IFilter<String> () {
			public boolean select(String m) {
				return (m == null || m.length() < 1);
			}			
		});
		checkProblems("address.xpath", new IFilter<String> () {
			public boolean select(String m) {
				return (m == null || m.length() < 1);
			}			
		});		
		
	}

	/**
	 * Check if Error code is set
	 * 
	 * @throws Exception
	 */
	@Test()
	public void CheckErrorCode () throws Exception {
				
		checkProblems(IProblem.SEVERITY, new IFilter<String> () {
			public boolean select(String m) {
				if (m == null) {
					return true;
				}
				int s = Integer.parseInt(m);
				return (s != IProblem.SEVERITY_ERROR &&
						s != IProblem.SEVERITY_INFO &&
						s != IProblem.SEVERITY_WARNING );
				
			}			
		});		
	}
	

	/**
	 * Check to make Rule is set everywhere.
	 * 
	 * @throws Exception
	 */
	
	@Test()
	public void CheckIfRuleIsSet () throws Exception {
				
		checkProblems(IProblem.RULE, new IFilter<String> () {
			public boolean select(String m) {
				return (m == null);
			}			
		});		
	}

	
	
	/**
	 * Check problems to expected problems ...
	 * 
	 * @throws Exception
	 */
	
	@Test
	public void CompareProblemsToExpectedProblems () throws Exception {
		
		Assert.assertEquals(fExpectedProblems.length, fProblems.length);
		
		String attribtues [] = {
				IProblem.LINE_NUMBER,
				IProblem.CHAR_END, 
				IProblem.CHAR_START,
				
				IProblem.SEVERITY,
				IProblem.MESSAGE,
				IProblem.FIX,
				IProblem.RULE,
				IProblem.RULE_DESC,
				IProblem.SA_CODE								
		};
		
		
		int matched = 0;
		
		for(IProblem p : fProblems) {
	
			for(IProblem ep : fExpectedProblems) {
	
				boolean bSame = true;
				
				// compare all attributes.
				// if all are the same then we have matched the error from the list.
				// 
				for(String attr : attribtues) {
					
					Object  p_attr = p.getAttribute(attr) ;					
					Object  ep_attr = ep.getAttribute(attr) ;
					
					if (p_attr == null && ep_attr == null) {
						continue;
					}
					if (p_attr == null || ep_attr == null) {
						bSame = false;
						break;
					}
					p_attr = p_attr.toString();
					ep_attr = ep_attr.toString();
					
					// now compare them.
					if (p_attr.equals(ep_attr) == false) {
						bSame = false;
						break;
					}					
				}
				
				// matched, increment the count and get out.
				if (bSame) {
					matched += 1;
					break;
				}				
			}
		}		
		
		
		// 
		Assert.assertEquals("Matched to Expected problems: " , fExpectedProblems.length, matched);
	}
		
		
	void checkProblems ( String attr, IFilter<String> filter) {		
		int count = 0;
		for(IProblem p : fProblems) {
			String m = p.getAttribute(attr,null).toString() ;
			if (filter.select(m)) {
				count += 1;
			}
		}
		// 
		Assert.assertEquals("Attribute: " + attr, 0, count);
	}
	
	
	
	
}
