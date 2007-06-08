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

import org.eclipse.bpel.ui.editparts.borders.DrawerBorder;
import org.eclipse.bpel.ui.editparts.policies.BPELOrderedLayoutEditPolicy;
import org.eclipse.bpel.ui.editparts.policies.ContainerHighlightEditPolicy;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;


/**
 * @author IBM, Original contribution.
 * @author Michal Chmielewski (michal.chmielewski@oracle.com) 
 */


public class SequenceEditPart extends CollapsableEditPart {
	
	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		
		// Show the selection rectangle
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new ContainerHighlightEditPolicy(false, true) {
			@Override
			protected int getDrawerInset() {
				return DrawerBorder.DRAWER_WIDTH;
			}
			@Override
			protected int getNorthInset() {
				if (isCollapsed()) {
					return 0;
				}
				
				// This one is tricky, it depends on the font size.
				
				return 10 ;
			}
			
			@Override
			protected int getSouthInset() {
				return isCollapsed() ? 8 : 2;
			}
			@Override
			protected int getEastInset() {
				return DrawerBorder.DRAWER_WIDTH ;
			}
			@Override
			protected int getWestInset() {
				return DrawerBorder.DRAWER_WIDTH ;
			}
		});
		
		// The sequence must lay out its child activities
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new BPELOrderedLayoutEditPolicy());
	}
	
	@Override
	protected void configureExpandedFigure(IFigure aFigure) {
		
		FlowLayout layout = new FlowLayout();
		layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
		layout.setHorizontal(false);
		layout.setMajorSpacing(SPACING);
		layout.setMinorSpacing(SPACING);
		aFigure.setLayoutManager(layout);
	}
}
