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
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets the "expressionLanguage" property of a process or expression.
 */
public class SetExpressionLanguageCommand extends SetCommand {

	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_EXPRESSIONLANGUAGE; }

	public SetExpressionLanguageCommand(EObject target, String newExpressionLanguage)  {
		super(target, newExpressionLanguage);
	}

	public Object get() {
		if (target instanceof Process) {
			return ((Process)target).getExpressionLanguage();
		} else if (target instanceof Expression) {
			return ((Expression)target).getExpressionLanguage();
		}
		throw new IllegalArgumentException();
	}
	public void set(Object o) {
		if (o == null) {
			if (target instanceof Process) {
				((Process)target).unsetExpressionLanguage();
			} else if (target instanceof Expression) {
				((Expression)target).unsetExpressionLanguage();
			}
			return;
		}
		if (target instanceof Process) {
			((Process)target).setExpressionLanguage((String)o);
		} else if (target instanceof Expression) {
			((Expression)target).setExpressionLanguage((String)o);
		}
	}
}
