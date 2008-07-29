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

package org.eclipse.bpel.xpath10;

public class VariableReferenceExpr extends Expr {
	String fName;
	String fPrefix;

	public VariableReferenceExpr (String prefix, String name) {
		super(null);
		fPrefix = prefix;		
		fName = name;
		fText = getText();
	}

	public String getPrefix() {
		return fPrefix;
	}

	public String getVariableName() {
		return fName;
	}

	public String getQName() {
		if ("".equals(fPrefix) || fPrefix == null) {
			return fName;
		}
		return fPrefix + ":" + fName;
	}

	protected String asText() {
		return "$" + getQName();
	}
}
