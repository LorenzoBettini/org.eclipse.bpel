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

import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.TerminationHandler;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.editparts.borders.RoundRectangleBorderWithDecoration;
import org.eclipse.bpel.ui.editparts.policies.BPELContainerEditPolicy;
import org.eclipse.bpel.ui.editparts.policies.BPELOrderedLayoutEditPolicy;
import org.eclipse.bpel.ui.editparts.policies.ContainerHighlightEditPolicy;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;


public class FaultHandlerEditPart extends BPELEditPart {

	private Image image;
	
	protected void createEditPolicies() {
		super.createEditPolicies();
		
		// Show the selection rectangle
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy(false, false));

		installEditPolicy(EditPolicy.CONTAINER_ROLE, new BPELContainerEditPolicy());
		
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new BPELOrderedLayoutEditPolicy());
	}

	protected IFigure createFigure() {
		IFigure figure = new Figure();
		FlowLayout layout = new FlowLayout();
		layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
		boolean vertical = (getModel() instanceof CompensationHandler) || (getModel() instanceof TerminationHandler);
		layout.setHorizontal(!vertical);
		figure.setLayoutManager(layout);
		if (image == null) {
			// Get Image from registry
			if (getModel() instanceof EventHandler) {
				image = BPELUIPlugin.INSTANCE.getImage(IBPELUIConstants.ICON_EVENT_INDICATOR);
			} else if (getModel() instanceof CompensationHandler) {
				image = BPELUIPlugin.INSTANCE.getImage(IBPELUIConstants.ICON_COMPENSATION_INDICATOR);
			} else if (getModel() instanceof TerminationHandler) {
				image = BPELUIPlugin.INSTANCE.getImage(IBPELUIConstants.ICON_TERMINATION_INDICATOR);
			} else {
				image = BPELUIPlugin.INSTANCE.getImage(IBPELUIConstants.ICON_FAULT_INDICATOR);
			}		
		}
		figure.setBorder(new RoundRectangleBorderWithDecoration(new Insets(20, 10, 20, 10), image));
		figure.setOpaque(true);
		return figure;
	}

	public void deactivate() {
		if (!isActive()) return;
		super.deactivate();
		if (this.image != null) {
			//this.image.dispose();
			this.image = null;
		}
	
	}	
}
