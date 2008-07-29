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

public class Predicate {
	
	Expr fExpr ;
	public Predicate (Expr expr) {
		fExpr = expr;
	}
	
	public Expr getExpr () {
		return fExpr;
	}
	
	public String getText() {
		return "["  + fExpr.getText() + "]";
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(getClass().getName()).append(",");
		sb.append("[").append(fExpr).append("]");
		return sb.toString();
	}	
	
	public int getPosition () 
	{
		return fExpr != null ? fExpr.getPosition() : -1;
	}
	
	public int getEndPosition () 
	{
		return fExpr != null ? fExpr.getEndPosition() : -1;
	}
}
