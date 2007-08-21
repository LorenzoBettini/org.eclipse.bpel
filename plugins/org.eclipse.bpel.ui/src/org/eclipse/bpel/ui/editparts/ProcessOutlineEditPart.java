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
package org.eclipse.bpel.ui.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.adapters.delegates.ActivityContainer;
import org.eclipse.bpel.ui.uiextensionmodel.ReferencePartnerLinks;
import org.eclipse.bpel.ui.uiextensionmodel.UiextensionmodelFactory;


public class ProcessOutlineEditPart extends OutlineTreeEditPart {

	// protected ReferencePartnerLinks referencePartners = UiextensionmodelFactory.eINSTANCE.createReferencePartnerLinks();

	protected void createEditPolicies() {
		super.createEditPolicies();
		// installEditPolicy(EditPolicy.LAYOUT_ROLE, new ProcessLayoutEditPolicy());
	}
	protected void addAllAdapters() {
		super.addAllAdapters();
		if (getModel() instanceof Process) {
			Process process = (Process)getModel();
			if (process.getVariables() != null)  adapter.addToObject(process.getVariables());
			if (process.getPartnerLinks() != null)  adapter.addToObject(process.getPartnerLinks());
			if (process.getCorrelationSets() != null)  adapter.addToObject(process.getCorrelationSets());
		}
	}

	protected List getModelChildren() {
		Process process = (Process)getModel();
		List list = new ArrayList();

		PartnerLinks links = process.getPartnerLinks();
		if (links != null) {
			list.add(links);
			// 
			// referencePartners.setPartnerLinks(links);
			// list.add(referencePartners);
		}

		Variables variables = process.getVariables();
		if (variables != null) {
			list.add(variables);
		}
		
		CorrelationSets sets = process.getCorrelationSets();
		if (sets != null) {
			list.add(sets);
		}

		IContainer container = new ActivityContainer(BPELPackage.eINSTANCE.getProcess_Activity());
		list.addAll(container.getChildren(process));
		return list;
	}
}
