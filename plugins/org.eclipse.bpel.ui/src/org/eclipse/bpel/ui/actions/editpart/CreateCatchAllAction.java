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

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.CatchAll;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.InsertInContainerCommand;
import org.eclipse.bpel.ui.factories.UIObjectFactoryProvider;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;


public class CreateCatchAllAction extends AbstractAction {

	public CreateCatchAllAction(EditPart editPart) {
		super(editPart);
	}
	
	public Image getIconImg() {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_ACTION_CATCHALL);
	}

	public ImageDescriptor getIcon() {
		return BPELUIPlugin.getPlugin().getImageDescriptor(IBPELUIConstants.ICON_ACTION_CATCHALL);
	}

	public boolean onButtonPressed() {
		CompoundCommand command = new CompoundCommand();
		final CatchAll child = (CatchAll)UIObjectFactoryProvider.getInstance().getFactoryFor(
			BPELPackage.eINSTANCE.getCatchAll()).createInstance();
		command.add(new InsertInContainerCommand((EObject)modelObject, child, null));
		BPELEditor bpelEditor = ModelHelper.getBPELEditor(modelObject);
		bpelEditor.getCommandStack().execute(command);
		return true;
	}

	public String getToolTip() {
		return Messages.CreateCatchAllAction_Add_Catch_All_1; 
	}
	
	public ImageDescriptor getDisabledIcon() { return ImageDescriptor.getMissingImageDescriptor(); }
	public boolean isEnabled() { return true; }	
}