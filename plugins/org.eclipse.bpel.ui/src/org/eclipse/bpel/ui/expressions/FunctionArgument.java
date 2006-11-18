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
package org.eclipse.bpel.ui.expressions;

public class FunctionArgument {
	private String theName;
	private String theType;
	private String theXpath;
	private char theOptional;
	
	public FunctionArgument(String name, String type, String xpath, char optional) {
		theName = name;
		theType = type;
		theXpath = xpath;
		theOptional = optional;
	}
	
	public String getName() { return theName; }
	public String getType() { return theType; }
	public String getXpath() { return theXpath; }
	public char getOptional() { return theOptional; }
	
}
