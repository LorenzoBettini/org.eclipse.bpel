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

import junit.framework.TestSuite;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Apr 14, 2008
 *
 */
public class AllTests extends TestSuite {
	
	/**
	 * 
	 */
	@SuppressWarnings("nls")
	
	public AllTests () {
		
		for(int i=0; i<=5; i++) {
			TestRunner tc = new TestRunner();
			tc.setTestId("t"+i);
			addTest(tc) ;			
		}
		
	}
}
