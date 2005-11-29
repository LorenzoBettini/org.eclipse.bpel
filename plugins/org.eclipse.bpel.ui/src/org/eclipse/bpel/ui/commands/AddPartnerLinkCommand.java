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

import java.util.List;

import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.uiextensionmodel.PartnerLinkExtension;
import org.eclipse.bpel.ui.util.ModelHelper;


/**
 * Adds a PartnerLink to the Process.
 */
public class AddPartnerLinkCommand extends AddToListCommand {

	Process process;
	PartnerLink partnerLink;
	PartnerLinkExtension extension = null;
	PartnerLinkExtension oldExtension = null;
	
	public AddPartnerLinkCommand(Process process, PartnerLink partnerLink, PartnerLinkExtension extension) {
		super(process, partnerLink, IBPELUIConstants.CMD_ADD_PARTNERLINK);
		this.process = process;
		this.extension = extension;
	}
	
	protected List getList() {
		return process.getPartnerLinks().getChildren();
	}

	public void doExecute() {
		if (extension != null)
			ModelHelper.getBPELEditor(process).getExtensionMap().put(partnerLink, extension);
		super.doExecute();
	}
}
