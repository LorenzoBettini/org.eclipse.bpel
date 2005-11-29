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
package org.eclipse.bpel.ui.adapters;

import java.util.List;

import org.eclipse.bpel.ui.actions.editpart.SetPartnerLinkAction;
import org.eclipse.bpel.ui.actions.editpart.SetVariableAction;
import org.eclipse.gef.EditPart;


public class ReceiveAdapter extends ActivityAdapter {

	/* IEditPartActionContributor */
	
	public List getEditPartActions(final EditPart editPart) {
		List actions = super.getEditPartActions(editPart);

		actions.add(new SetPartnerLinkAction(editPart));
		actions.add(new SetVariableAction(editPart, SetVariableAction.REQUEST));

		return actions;
	}
}
