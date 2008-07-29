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

public class FunctionCallExpr extends Expr {
	
	final List<Expr> fParams = new ArrayList<Expr>();
	String fName;
	String fPrefix;
	
	public FunctionCallExpr (String pfx, String name) {
		super(null);
		fName = name;
		fPrefix = pfx;
	}
	
	public String getPrefix() {
		return fPrefix;
	}
	
    public String getFunctionName() {
    	return fName;
    }
    
    public List<Expr> getParameters() {
    	return fParams;
    }

    public void addParameter (Expr p) {
    	fParams.add(p);
    }
    
    protected String asText() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(fnName()).append(argsToString(false));
    	return sb.toString();
    }
    
    public String toString () {
    	StringBuilder sb = new StringBuilder();
    	sb.append("{FunctionCall,").append(getFunctionName()).append(",").append( argsToString(true) ).append("}");
    	return sb.toString();
    }
    
    String fnName () {
    	if ("".equals(fPrefix) || fPrefix == null) {
    		return fName;
    	}
    	return fPrefix + ":" + fName;
    }
    
    String argsToString (boolean asString ) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("(");
    	int count = 0;
    	for(Expr n : fParams) {
    		if (count > 0) {
    			sb.append(", ");
    		}
    		if (asString) {
    			sb.append(n);
    		} else {
    			sb.append(n.getText());
    		}    		    	
    		count ++;
    	}
    	sb.append(")");
    	return sb.toString();
    }
}
