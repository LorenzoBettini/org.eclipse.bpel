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
package org.eclipse.bpel.ui.actions.editpart;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.InsertInContainerCommand;
import org.eclipse.bpel.ui.commands.SetNameAndDirectEditCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;


public class CreateCompensationHandlerAction extends AbstractAction {

	public CreateCompensationHandlerAction(EditPart editPart) {
		super(editPart);
	}

	public Image getIconImg() {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_ACTION_COMPENSATIONHANDLER);
	}

	public ImageDescriptor getIcon() {
		return BPELUIPlugin.getPlugin().getImageDescriptor(IBPELUIConstants.ICON_ACTION_COMPENSATIONHANDLER);
	}

	public boolean onButtonPressed() {
		CompoundCommand command = new CompoundCommand(IBPELUIConstants.CMD_ADD_COMPENSATIONHANDLER);
		final EObject child = BPELFactory.eINSTANCE.createCompensationHandler();
		command.add(new InsertInContainerCommand((EObject)modelObject, child, null)); 
		command.add(new SetNameAndDirectEditCommand(child, viewer));
		ModelHelper.getBPELEditor(modelObject).getCommandStack().execute(command);
		BPELUtil.setShowCompensationHandler(editPart, true);
		return true;
	}

	public String getToolTip() {
		return Messages.CreateCompensationHandlerAction_Add_Compensation_Handler_1; 
	}
	
	public ImageDescriptor getDisabledIcon() { return ImageDescriptor.getMissingImageDescriptor(); }
	public boolean isEnabled() { return true; }	
}