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

import java.util.Map;

import org.eclipse.bpel.ui.editparts.borders.ContainerBorder;
import org.eclipse.bpel.ui.editparts.borders.LeafBorder;
import org.eclipse.bpel.ui.editparts.policies.BPELOrderedLayoutEditPolicy;
import org.eclipse.bpel.ui.editparts.policies.ContainerHighlightEditPolicy;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;


public class SequenceEditPart extends CollapsableEditPart {

	protected Map sourceMap, targetMap;

	protected void createEditPolicies() {
		super.createEditPolicies();
		
		// Show the selection rectangle
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy(false, true) {
			protected int getDrawerInset() {
				return LeafBorder.DRAWER_WIDTH;
			}
			protected int getNorthInset() {
				if (isCollapsed()) {
					return 2;
				} else {
					// This one is tricky, it depends on the font size.
					// Ask the border for help.
					ContainerBorder border = (ContainerBorder)getContentPane().getBorder();
					// HACK!
					if (border == null) return 2;
					return border.getTopInset() + 2;
				}
			}
			protected int getSouthInset() {
				if (isCollapsed()) {
					return 9;
				} else {
					return 3;
				}
			}
			protected int getEastInset() {
				return LeafBorder.DRAWER_WIDTH;
			}
			protected int getWestInset() {
				return LeafBorder.DRAWER_WIDTH + 2;
			}
		});
		
		// The sequence must lay out its child activities
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new BPELOrderedLayoutEditPolicy());
	}
	
	protected void configureExpandedFigure(IFigure figure) {
		FlowLayout layout = new FlowLayout();
		layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
		layout.setHorizontal(false);
		layout.setMajorSpacing(SPACING);
		layout.setMinorSpacing(SPACING);
		figure.setLayoutManager(layout);
	}
}
