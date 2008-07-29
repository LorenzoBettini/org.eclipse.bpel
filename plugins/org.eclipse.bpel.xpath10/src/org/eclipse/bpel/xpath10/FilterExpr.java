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

import java.util.ArrayList;
import java.util.List;

public class FilterExpr extends Expr {

	List<Predicate> fList = new ArrayList<Predicate>();
	Expr fExpr;
	
	
	public FilterExpr (Expr expr) {
		super(expr.toString());
		fExpr = expr;
	}
	
	public Expr getExpr () {
		return fExpr;		
	}
	
    public List<Predicate> getPredicates() {
    	return fList;
    }
   
    public void addPredicate (Predicate p) {
    	fList.add(p);
    }
    
    public boolean hasPredicates ()
    {
    	return fList.size() > 0;
    }
    
    protected String asText()
    {
        StringBuilder sb = new StringBuilder();
        if ( fExpr != null ) {
        	sb.append(fExpr.getText());
        }
        for(Predicate p : fList) {
        	sb.append(p.getText());
        }
        return sb.toString();
    }
    
    
    public int getPosition ()  {
    	return fExpr != null ? fExpr.getPosition() : -1;
    }
    
    public int getEndPosition () {
    	return fExpr != null ? fExpr.getEndPosition() : -1;
    }
    
}
