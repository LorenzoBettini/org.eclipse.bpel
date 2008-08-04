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
package org.eclipse.bpel.validator.junit;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.bpel.validator.model.IFilter;
import org.eclipse.bpel.validator.model.IProblem;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jan 19, 2007
 *
 */

@SuppressWarnings("nls")

public abstract class TestRunner extends TestCase {

	SimpleRunner fRunner;
		
	static Map<String,SimpleRunner> fRunners = new HashMap<String,SimpleRunner>();
	
				
	/**
	 * @throws Exception
	 */
	
	@Override
	public void setUp () throws Exception {
		String id  = getClass().getSimpleName().toLowerCase();		
		fRunner = fRunners.get(id);
		if (fRunner == null) {						
			fRunner =  new SimpleRunner(id) ;
			fRunners.put( id, fRunner );			
		}
	}
	
	/**
	 * @throws Exception
	 */
	@Override
	public void tearDown () throws Exception {
		
	}
	
	
	/** There should be some errors after validating 
	 * @throws Exception 
	 **/
	
	public void testValidate () throws Exception {					
		
		fRunner.run();
		
		Assert.assertTrue("Problems have been found",fRunner.fProblems.length >= 0);
	}

	/**
	 * Attempt to save data.
	 * @throws Exception
	 */
	
	public void testSaveCurrentRunToFile () throws Exception
	{
		fRunner.save();
	}

	/**
	 * @throws Exception
	 */
	
	public void testListingToFile () throws Exception
	{
		fRunner.saveListing();
	}

	/**
	 * Check undefined messages	
	 * @throws Exception
	 */
	
	
	public void testCheckUndefinedMessageCodes () throws Exception {

		checkProblems(IProblem.MESSAGE, new IFilter<String> () {
			public boolean select(String m) {
				return (m == null || m.startsWith("!"));
			}			
		});		
	}

	
	/** Check model pointers 
	 * @throws Exception 
	 */
	
	
	public void testXPathModelPointers () throws Exception {
				
		checkProblems("address.xpath", new IFilter<String> () {
			public boolean select(String m) {
				return (m == null || m.length() < 1);
			}			
		});		
		
	}

	/**
	 * @throws Exception
	 */
	public void testModelSpecificPointers () throws Exception {
		
		checkProblems("address.model", new IFilter<String> () {
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
	
	public void testErrorCodeSetInProblem () throws Exception {
				
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
	
	
	public void testRuleNameSetInProblem () throws Exception {
				
		checkProblems(IProblem.RULE, new IFilter<String> () {
			public boolean select(String m) {
				return (m == null);
			}			
		});		
	}

	
	void checkProblemArrays () throws Exception
	{
		Assert.assertNotNull("Problems array is null (setup?)", fRunner.fProblems);
		Assert.assertNotNull("ExpectedProblems array is null (setup?)", fRunner.fExpectedProblems);		
	}
	
	/**
	 * Check problems to expected problems ...
	 * 
	 * @throws Exception
	 */
		
	public void testCountOfProblemsToExpectedProblems () throws Exception {
		
		checkProblemArrays();
		Assert.assertEquals(fRunner.fExpectedProblems.length, fRunner.fProblems.length);			
	}

	/**
	 * @throws Exception
	 */
	public void testCountOfInfoProblemsToExpectedInfoProblems () throws Exception
	{
		checkProblemArrays();
		final String info = new Integer(IProblem.SEVERITY_INFO).toString();
		
		IFilter<String> filter = new IFilter<String> () {
			public boolean select (String node) {
				return info.equals(node);
			}			
		};
		int expectedInfoProblems = count (fRunner.fExpectedProblems, IProblem.SEVERITY,filter);
		int infoProblems = count(fRunner.fProblems, IProblem.SEVERITY, filter);
		Assert.assertEquals(expectedInfoProblems, infoProblems);
	}

	/**
	 * @throws Exception
	 */
	public void testCountOfWarningProblemsToExpectedWarningProblems () throws Exception
	{
		checkProblemArrays();
		final String info = new Integer(IProblem.SEVERITY_WARNING).toString();
		
		IFilter<String> filter = new IFilter<String> () {
			public boolean select (String node) {
				return info.equals(node);
			}			
		};
		int expectedInfoProblems = count (fRunner.fExpectedProblems, IProblem.SEVERITY,filter);
		int infoProblems = count(fRunner.fProblems, IProblem.SEVERITY, filter);
		Assert.assertEquals(expectedInfoProblems, infoProblems);
		
	}

	/**
	 * @throws Exception
	 */
	public void testCountOfErrorProblemsToExpectedErrorProblems () throws Exception
	{
		checkProblemArrays();
		final String info = new Integer(IProblem.SEVERITY_ERROR).toString();
		
		IFilter<String> filter = new IFilter<String> () {
			public boolean select (String node) {
				return info.equals(node);
			}			
		};
		int expectedInfoProblems = count (fRunner.fExpectedProblems, IProblem.SEVERITY,filter);
		int infoProblems = count(fRunner.fProblems, IProblem.SEVERITY, filter);
		Assert.assertEquals(expectedInfoProblems, infoProblems);
	}

	
	
	
	public void testCompareProblemsToExpectedProblems () throws Exception 
	{
		
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
		
		for(IProblem p : fRunner.fProblems) {
	
			for(IProblem ep : fRunner.fExpectedProblems) {
	
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
		Assert.assertEquals("Matched to Expected problems: " , fRunner.fExpectedProblems.length, matched);
	}
		
		
	void checkProblems ( String attr, IFilter<String> filter) {		
		int count = 0;
		
		Assert.assertNotNull("The problem array is null (setup?) ", fRunner.fProblems);
		
		for(IProblem p : fRunner.fProblems) {
			Object value = p.getAttribute(attr,null);			
			String m = value != null ? value.toString() : "";
			if (filter.select(m)) {
				count += 1;
			}
		}
		// 
		Assert.assertEquals("Attribute: " + attr, 0, count);
	}
	
	int count ( IProblem[] list, String attr,  IFilter<String> filter ) {		
		int count = 0;
		
		Assert.assertNotNull("The problem array is null (setup?) ", list);
		for(IProblem p : list) {
			Object value = p.getAttribute(attr,null);			
			String m = value != null ? value.toString() : "";
			if (filter.select(m)) {
				count += 1;
			}
		}
		
		return count;
	}
	
	
	/**
	 * Add the tests that you want to run here.
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Jul 29, 2008
	 *
	 *
	 */
	
	static public class T0 extends TestRunner {	}
	static public class T1 extends TestRunner {	}
	static public class T2 extends TestRunner {	}
	static public class T3 extends TestRunner {	}
	static public class T4 extends TestRunner {	}
	static public class T5 extends TestRunner {	}	
}
