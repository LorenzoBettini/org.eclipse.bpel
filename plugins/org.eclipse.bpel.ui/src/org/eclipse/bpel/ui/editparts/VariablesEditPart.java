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

import java.util.List;

import org.eclipse.bpel.common.ui.tray.TrayCategoryEditPart;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.editparts.policies.TrayContainerEditPolicy;
import org.eclipse.bpel.ui.factories.UIObjectFactoryProvider;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.viewers.StructuredSelection;


public class VariablesEditPart extends TrayCategoryEditPart {

	protected void createEditPolicies() {
		super.createEditPolicies();
		// handles creations
		installEditPolicy(EditPolicy.CONTAINER_ROLE, new TrayContainerEditPolicy());
	}

	protected List getModelChildren() {
		return getVariables().getChildren();
	}

	protected Variables getVariables() {
		return (Variables)getModel();
	}

	protected CreationFactory getCreationFactory() {
		return UIObjectFactoryProvider.getInstance().getFactoryFor(BPELPackage.eINSTANCE.getVariable());
	}
	
	protected IFigure getAddToolTip() {
	    return new Label(Messages.VariablesEditPart_Add_Variable_1); 
	}
	
	protected IFigure getRemoveToolTip() {
	    return new Label(Messages.VariablesEditPart_Remove_Variable_1); 
	}
	
	/**
	 * WARNING: only call this method if you know what you're doing!
	 * 
	 * HACK: The following hack avoids the Process from being selected when a variable
	 * is deleted. If the Process is selected, this edit part (VariablesEditPart)
	 * has its parent set to null and when its time for the variable being deleted
	 * to "unregister visuals" it will try to get the viewer and that will cause a
	 * NPR because the parent is null.
	 */
	protected void selectAnotherVariable(VariableEditPart variable) {
		int size = getModelChildren().size();
		if (size > 0) {
			selectEditPart(getModelChildren().get(0));
		} else {
			// if we are executing this method we are dealing with scoped variables
			Scope scope = (Scope)((EObject)getModel()).eContainer();
			BPELEditor editor = ModelHelper.getBPELEditor(scope);
			EditPart editPart = (EditPart)editor.getGraphicalViewer().getEditPartRegistry().get(scope);
			if (editPart != null) {
				getViewer().setSelection(new StructuredSelection(editPart));
			}
		}
	}

	protected AccessibleEditPart createAccessible() {
		return new BPELTrayAccessibleEditPart(this);
	}
}
