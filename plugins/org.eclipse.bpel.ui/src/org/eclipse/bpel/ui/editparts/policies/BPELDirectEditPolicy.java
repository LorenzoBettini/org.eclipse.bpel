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
package org.eclipse.bpel.ui.editparts.policies;

import org.eclipse.bpel.common.ui.tray.TrayEditPart;
import org.eclipse.bpel.ui.commands.SetNameCommand;
import org.eclipse.bpel.ui.editparts.BPELEditPart;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;


public class BPELDirectEditPolicy extends DirectEditPolicy {

	protected Command getDirectEditCommand(DirectEditRequest edit) {
		String labelText = ""; //$NON-NLS-1$
		Command cmd = null;
		if (edit.getCellEditor() != null
			&& ((labelText = (String) edit.getCellEditor().getValue()) != null)) {
			cmd = getFinalizeCommand(getHost().getModel(), labelText);
		}
		return cmd;
	}

	protected void showCurrentEditValue(DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		if (getHost() instanceof BPELEditPart) {
			BPELEditPart part = (BPELEditPart) getHost();
			part.getLabelFigure().setText(value);
		} else if (getHost() instanceof TrayEditPart) {
			TrayEditPart part = (TrayEditPart) getHost();
			part.getDirectEditLabel().setText(value);
		}
		//hack to prevent async layout from placing the cell editor twice.
		getHostFigure().getUpdateManager().performUpdate();
	}

	static public Command getFinalizeCommand(Object model, String name) {
		Command command = null;
		// JM - btw, is this a bug? missing supports uiextensionmodel
		/*if (ModelHelper.supportsUIExtension etc.) {
			command = new SetDisplayNameCommand((EObject)model, name);
		} else {*/
			command = new SetNameCommand((EObject)model, name);
		//}
		if (command.canExecute()) return command;
		return null;
	}
}
