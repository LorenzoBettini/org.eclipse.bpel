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

public class BinaryExpr extends Expr {
	
	String fOperator;
	Expr fLHS;
	Expr fRHS;
	
	public BinaryExpr (String op, Expr lhs, Expr rhs) {
		super("");
		fOperator = op;
		fLHS = lhs;
		fRHS = rhs;
	}
	
	public String getOperator() {
		return fOperator;
	}
	public Expr getLHS() {
		return fLHS;
	}
    public Expr getRHS() {
    	return fRHS;
    }
    
    protected String asText() {
    	StringBuilder sb = new StringBuilder();
    	if (isWrapParen()) {
    		sb.append("(");
    	}
    	sb.append( fLHS != null ? fLHS.getText() : "?" );
    	sb.append(" ");
    	sb.append(getOperator());
    	sb.append(" ");
    	sb.append( fRHS != null ? fRHS.getText() : "?" );    
    	if (isWrapParen()) {
    		sb.append(")");
    	}
    	return sb.toString();
    }
    

    protected String asString () {
    	StringBuilder sb = new StringBuilder();
    	if (isWrapParen()) {
    		sb.append("(");
    	}
    	sb.append(getLHS()).append(" ").append(getOperator()).append(" ").append(getRHS());
    	if (isWrapParen()) {
    		sb.append(")");
    	}
    	return sb.toString();
    }
    
     
    public int getPosition () {    	
    	return getLHS().getPosition();
    }
    
    public int getEndPosition () {
    	return getRHS().getEndPosition();
    }
    
}
