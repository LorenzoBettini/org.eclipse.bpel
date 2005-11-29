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

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.ui.actions.editpart.SetPartnerLinkAction;
import org.eclipse.bpel.ui.actions.editpart.SetVariableAction;
import org.eclipse.bpel.ui.adapters.delegates.MultiContainer;
import org.eclipse.bpel.ui.adapters.delegates.ReferenceContainer;
import org.eclipse.bpel.ui.editparts.InvokeEditPart;
import org.eclipse.bpel.ui.editparts.OutlineTreeEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;


public class InvokeAdapter extends ContainerActivityAdapter implements EditPartFactory,
	IOutlineEditPartFactory, IFaultHandlerHolder, ICompensationHandlerHolder
{

	/* IContainer delegate */
	
	public IContainer createContainerDelegate() {
		MultiContainer omc = new MultiContainer();
		omc.add(new ReferenceContainer(BPELPackage.eINSTANCE.getInvoke_FaultHandler()));
		omc.add(new ReferenceContainer(BPELPackage.eINSTANCE.getInvoke_CompensationHandler()));
		return omc;
	}

	/* IOutlineEditPartFactory */
	
	public EditPart createOutlineEditPart(EditPart context, Object model) {
		EditPart result = new OutlineTreeEditPart();
		result.setModel(model);
		return result;
	}
	
	/* IFaultHandlerHolder */

	public FaultHandler getFaultHandler(Object object) {
		return ((Invoke)object).getFaultHandler();
	}
	
	public void setFaultHandler(Object object, FaultHandler faultHandler) {
		((Invoke)object).setFaultHandler(faultHandler);
	}
	
	/* ICompensationHandlerHolder */

	public CompensationHandler getCompensationHandler(Object object) {
		return ((Invoke)object).getCompensationHandler();
	}

	public void setCompensationHandler(Object object, CompensationHandler compensationHandler) {
		((Invoke)object).setCompensationHandler(compensationHandler);
	}
	
	/* IEditPartActionContributor */
	
	public List getEditPartActions(final EditPart editPart) {
		List actions = super.getEditPartActions(editPart);
		actions.add(new SetPartnerLinkAction(editPart));
		actions.add(new SetVariableAction(editPart, SetVariableAction.REQUEST));
		actions.add(new SetVariableAction(editPart, SetVariableAction.RESPONSE));
		return actions;
	}
	
	/* EditPartFactory */

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart result = new InvokeEditPart();
		result.setModel(model);
		return result;
	}
}
