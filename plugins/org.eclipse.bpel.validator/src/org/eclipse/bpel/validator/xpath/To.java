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
package org.eclipse.bpel.validator.xpath;

import org.eclipse.bpel.validator.model.ARule;
import org.eclipse.bpel.validator.model.IProblem;
import org.jaxen.expr.Expr;
import org.jaxen.expr.PathExpr;
import org.jaxen.expr.VariableReferenceExpr;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Mar 23, 2007
 *
 */

@SuppressWarnings("nls")

public class To extends XPathValidator {


	
	/**
	 * Check to make sure that the expression is a variable reference expressions.
	 */
	
	
	@ARule(
		sa = 33,
		desc = "Check variable reference expression on to nodes",
		author = "michal.chmielewski@oracle.com",
		date = "01/20/2007"
	)

	public void rule_CheckVariableReference_15 () {
				
		IProblem problem;
		Expr expr = xpathExpr.getRootExpr();
		
		if (expr instanceof VariableReferenceExpr) {
			mVisitor.visit( expr );
		} else if (expr instanceof PathExpr) {
			PathExpr pe = (PathExpr) expr;
			if (pe.getFilterExpr() instanceof VariableReferenceExpr) {
				mVisitor.visit(pe);
			} else {
				problem = createError();
				problem.fill("XPATH_EXPRESSION_TYPE",
						toString(mNode.nodeName()),
						exprStringTrimmed,
						fExprByNode
					);
				repointOffsets(problem, expr ); 									
			}
		} else {
			problem = createError();
			problem.fill("XPATH_EXPRESSION_TYPE",
					toString(mNode.nodeName()),
					exprStringTrimmed,
					fExprByNode
				);
			repointOffsets(problem, expr ); 					
		}
	
		
		disableRules();
	}
		
}
