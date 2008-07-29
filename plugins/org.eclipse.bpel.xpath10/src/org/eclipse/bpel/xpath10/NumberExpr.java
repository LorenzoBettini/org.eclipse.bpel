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

public class NumberExpr extends Expr {
	
	Number fNumber = new Integer(0);
	
	public NumberExpr (String number) {
		super( number );
		fNumber = extractNumber(number);
	}
	
	public NumberExpr (Number num) {
		super(num.toString());
		fNumber = num;
	}
	
	public Number getNumber() {
		return fNumber;
	}
	
	Number extractNumber(String numberBody) {       
        boolean isReal = numberBody.indexOf('.') >= 0; 
        if (!isReal) {
            return new Integer(numberBody);
        } else {
            double result = Double.parseDouble(numberBody);
            return new Double(result);
        }
    }
   
	
	public void negate () {
		
		if (fNumber instanceof Float) {
			fNumber = new Float(-1 * fNumber.floatValue());
		} else if (fNumber instanceof Short) {
			fNumber = new Short((short) (-1 * fNumber.shortValue()));
		} else if (fNumber instanceof Integer) {
			fNumber = new Integer(-1*fNumber.intValue());
		} else if (fNumber instanceof Long) {
			fNumber = new Long( -1*fNumber.longValue());
		} else {
			fNumber = new Double(-1.0 * fNumber.doubleValue());
		}		
	}
	
}
