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

import junit.framework.Assert;

import org.eclipse.bpel.validator.Main;
import org.eclipse.bpel.validator.helpers.CmdValidator;
import org.eclipse.bpel.validator.model.IProblem;
import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.internal.resources.WorkspaceRoot;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jul 28, 2008
 *
 */
@SuppressWarnings("nls")
public class SimpleRunner {

	Main fValidator ;
	
	/** The home folder */
	File fHome;
	
	/** BPEL source file */
	File fSrc;
	
	/** Problems generated from the validation */
	IProblem[] fProblems;
	
	/** Problems read from the problems file */
	IProblem[] fExpectedProblems;	
	
	String fTestCase = "t1";
	
	/**
	 * @param testCase
	 */
	public SimpleRunner (String testCase) {
		fTestCase = testCase;
	}
	
	/**
	 * @throws Exception
	 */
	
	@SuppressWarnings("nls")
	public void run () throws Exception {
		
		fValidator = new Main();
		String home = System.getenv( "BPEL_TEST_HOME");		
		
		Assert.assertNotNull("BPEL_TEST_HOME is undefined",home);		
						
		fHome = new File (home);
		Assert.assertTrue(fHome + " does not exist", fHome.exists() );
				
		Assert.assertTrue(fHome + " is not a directory", fHome.isDirectory() );
				
		fSrc = new File (fHome + "/tests/" + fTestCase + "/" + fTestCase + ".bpel");	
		
		fExpectedProblems = CmdValidator.readMessages( new File ( fSrc + ".xml") );
		
		fProblems = fValidator.validate( fSrc );
	}

}
