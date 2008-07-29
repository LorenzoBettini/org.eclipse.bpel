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

public class TestRunner extends TestCase {

	SimpleRunner fRunner;
	
	String fTestId = "t2";
	
	static ThreadLocal<SimpleRunner> fRunners = new ThreadLocal<SimpleRunner>();
		
	
	public void setTestId (String id) {
		fTestId = id;
	}
	
	/**
	 * @throws Exception
	 */
	@Override
	public void setUp () throws Exception {		
		fRunner = fRunners.get();
		if (fRunner == null) {			
			fRunner =  new SimpleRunner(fTestId) ;
			fRunners.set( fRunner );
			fRunner.run();
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
		
		Assert.assertTrue("Problems have been found", 
				fRunner.fProblems.length > 0);
	}

	/**
	 * Check undefined messages	
	 * @throws Exception
	 */
	
	
	public void testCheckUndefinedMessages () throws Exception {

		checkProblems(IProblem.MESSAGE, new IFilter<String> () {
			public boolean select(String m) {
				return (m == null || m.startsWith("!"));
			}			
		});		
	}

	
	/** Check model pointers 
	 * @throws Exception 
	 */
	
	
	public void testCheckModelPointers () throws Exception {
				
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
	
	public void testCheckErrorCode () throws Exception {
				
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
	
	
	public void testCheckIfRuleIsSet () throws Exception {
				
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
		
	public void testCompareProblemsToExpectedProblems () throws Exception {
		
		Assert.assertEquals(fRunner.fExpectedProblems.length, fRunner.fProblems.length);
		
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
		for(IProblem p : fRunner.fProblems) {
			String m = p.getAttribute(attr,null).toString() ;
			if (filter.select(m)) {
				count += 1;
			}
		}
		// 
		Assert.assertEquals("Attribute: " + attr, 0, count);
	}
	
	
	
	
}
