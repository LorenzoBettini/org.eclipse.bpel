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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;

/**
 * Deletes an ExtensibilityElement from its enclosing WSDL resource.
 * 
 * Also runs a DeleteNonContainmentRefsCommand using the WSDL resource and
 * the BPEL resource as model roots. 
 */
public abstract class DeleteWSDLExtensibilityElementCommand extends AutoUndoCommand {

	Definition definition;
	ExtensibilityElement element;
	
	DeleteNonContainmentRefsCommand deleteRefsCmd;
	
	public abstract String getDefaultLabel();

	public DeleteWSDLExtensibilityElementCommand(ExtensibilityElement element) {
		super(new ArrayList(1));
		this.element = element;
		setLabel(getDefaultLabel());
		addModelRoot(element.getEnclosingDefinition());
	}

	public boolean canDoExecute() {
		if (element.getEnclosingDefinition() == null)  return false;
		return super.canDoExecute();
	}
	
	public void doExecute() {
		definition = element.getEnclosingDefinition();
	
		Set modelRootSet = new HashSet();
		modelRootSet.add(element.eResource());
		modelRootSet.add(ModelHelper.getBPELEditor(element).getResource());
		deleteRefsCmd = new DeleteNonContainmentRefsCommand(Collections.singleton(element), modelRootSet);
		
		element.setEnclosingDefinition(null);
		definition.getEExtensibilityElements().remove(element);

		deleteRefsCmd.execute();
	}
}
