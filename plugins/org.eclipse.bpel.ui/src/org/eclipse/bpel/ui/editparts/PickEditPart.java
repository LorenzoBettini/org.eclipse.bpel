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

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;

public class PickEditPart extends SequenceEditPart {
	
	protected void configureExpandedFigure(IFigure figure) {
		super.configureExpandedFigure(figure);
		FlowLayout layout = (FlowLayout)figure.getLayoutManager();
		layout.setHorizontal(true);
		layout.setMinorAlignment(FlowLayout.ALIGN_LEFTTOP);
		layout.setStretchMinorAxis(true);
	}
}
