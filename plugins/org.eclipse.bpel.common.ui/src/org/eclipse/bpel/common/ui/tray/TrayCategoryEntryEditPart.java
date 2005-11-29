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
package org.eclipse.bpel.common.ui.tray;

import org.eclipse.bpel.common.ui.decorator.EditPartMarkerDecorator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;


/**
 * An entry in a Tray category.
 */
public abstract class TrayCategoryEntryEditPart extends TrayEditPart {

	protected class TrayCategoryEntrySelectionEditPolicy extends TraySelectionEditPolicy {
		protected Handle createHandle(GraphicalEditPart owner) {
			return new TraySelectionHandle(owner, entryFigure);
		}
	}
	
	protected EditPartMarkerDecorator decorator;
	protected TrayCategoryEntryFigure entryFigure;
	
	public TrayCategoryEntryEditPart() {
		super();
	}

	protected IFigure createFigure() {
		IFigure reference = ((TrayCategoryEditPart)getParent()).getLabelPositionReference();
		entryFigure = new TrayCategoryEntryFigure(reference, this);
		entryFigure.setText(getLabelProvider().getText(getModel()));
		decorator = new TrayMarkerDecorator((EObject)getModel(), new ToolbarLayout());
		return decorator.createFigure(entryFigure);
	}
	
	protected void refreshVisuals() {
		super.refreshVisuals();
		entryFigure.setText(getLabelProvider().getText(getModel()));
		decorator.refresh();
	}
	
	protected void createEditPolicies() {
		// Show selection handles
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new TrayCategoryEntrySelectionEditPolicy());
	}

	public Label getDirectEditLabel() {
		return entryFigure.getLabel();
	}
}