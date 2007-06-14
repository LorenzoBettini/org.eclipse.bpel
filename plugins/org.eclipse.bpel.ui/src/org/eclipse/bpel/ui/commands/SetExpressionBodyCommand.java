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


/** 
 * Sets the body property of an expression.
 */
public class SetExpressionBodyCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_EXPRESSION_BODY; }

	public SetExpressionBodyCommand(Expression target, Object newBody)  {
		super(target, newBody);
	}

	public Object get() {
		return ((Expression)fTarget).getBody();
	}
	public void set(Object o) {
		((Expression)fTarget).setBody(o);
	}
}
