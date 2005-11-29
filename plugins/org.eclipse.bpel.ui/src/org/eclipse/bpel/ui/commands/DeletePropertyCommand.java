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

import java.util.Iterator;

import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.gef.commands.CompoundCommand;

import org.eclipse.wst.wsdl.Definition;

/**
 * Deletes a Property from the shadow WSDL file.  Also deletes all propertyAliases to the property
 * and all references to the property or any of its propertyAliases.
 * 
 * NOTE: Not to be confused with AddPropertyCommand which adds an existing Property to
 * a CorrelationSet!
 */
public class DeletePropertyCommand extends DeleteWSDLExtensibilityElementCommand {

	CompoundCommand deleteAliasesCmd;

	public String getDefaultLabel() { return IBPELUIConstants.CMD_DELETE_PROPERTY; }

	public DeletePropertyCommand(Property property) {
		super(property);
	}

	public void doExecute() {
		deleteAliasesCmd = new CompoundCommand();
		
		Definition definition = ((Property)element).getEnclosingDefinition();
		for (Iterator it = definition.getEExtensibilityElements().iterator(); it.hasNext(); ) {
			Object object = it.next();
			if (object instanceof PropertyAlias) {
				PropertyAlias alias = (PropertyAlias)object;
				if (alias.getMessageType() == (Property)element) {
					deleteAliasesCmd.add(new DeletePropertyAliasCommand(alias));
				}
			}
		}
	
		deleteAliasesCmd.execute();
		super.execute();
	}
}
