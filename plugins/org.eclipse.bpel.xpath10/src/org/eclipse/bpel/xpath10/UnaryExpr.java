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


public class UnaryExpr extends Expr {

	Expr fExpr;
	String fOparand;
		
	public UnaryExpr (Expr expr) {
		super(expr.toString());
		fExpr = expr;
	}
	
	public Expr getExpr () {
		return fExpr;		
	}
    
	public void setOperand  (String operand)
	{
		fOparand = operand;
	}
	
    protected String asText()
    {
    	StringBuilder sb = new StringBuilder();
    	if (fOparand != null) {
    		sb.append(fOparand);    		
    	}    	
    	if (fExpr != null) {
    		sb.append(fExpr.asText());
    	}
    	return sb.toString();
    }
    
    protected String asString () {
    	StringBuilder sb = new StringBuilder();
    	if (fOparand != null) {
    		sb.append(fOparand);
    		sb.append(",");
    	}    	
    	if (fExpr != null) {
    		sb.append(fExpr);
    	}
    	return sb.toString();
    }
    
    public boolean isWrapParen () {
    	return fExpr != null ? fExpr.isWrapParen() : false;
    }
    
    public int getPosition ()  {
    	return fExpr != null ? fExpr.getPosition() : -1;
    }
    
    public int getEndPosition () {
    	return fExpr != null ? fExpr.getEndPosition() : -1;
    }
    
}
