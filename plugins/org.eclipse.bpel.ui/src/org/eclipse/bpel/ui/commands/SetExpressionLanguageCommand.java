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
		if (fTarget instanceof Process) {
			return ((Process)fTarget).getExpressionLanguage();
		} else if (fTarget instanceof Expression) {
			return ((Expression)fTarget).getExpressionLanguage();
		}
		throw new IllegalArgumentException();
	}
	public void set(Object o) {
		if (o == null) {
			if (fTarget instanceof Process) {
				((Process)fTarget).unsetExpressionLanguage();
			} else if (fTarget instanceof Expression) {
				((Expression)fTarget).unsetExpressionLanguage();
			}
			return;
		}
		if (fTarget instanceof Process) {
			((Process)fTarget).setExpressionLanguage((String)o);
		} else if (fTarget instanceof Expression) {
			((Expression)fTarget).setExpressionLanguage((String)o);
		}
	}
}
