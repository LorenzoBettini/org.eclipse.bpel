/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.commands;

import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets the expression property of a model object.  This includes the
 * Condition property of Case, the Condition property of While, and
 * the For/Until properties of Wait and OnAlarm.  Also used for Timeout,
 * Expiration, and RepeatEvery.
 */
public class SetExpressionCommand extends SetCommand {

	int exprType, exprSubType;

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_EXPRESSION; }

	public SetExpressionCommand(EObject target, int exprType, int exprSubType, Expression newExpr)  {
		super(target, newExpr);
		this.exprType = exprType;
		this.exprSubType = exprSubType;
	}

	public Object get() {
		return ModelHelper.getExpression(target, exprType);
	}
	public void set(Object o) {
		ModelHelper.setExpression(target, exprType, exprSubType, (Expression)o);
	}
}
