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
package org.eclipse.bpel.common.ui.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.handles.RelativeHandleLocator;

/**
 * Override the normal relative handle locator. This is required
 * to move the handles out of the absolute corners and closer to
 * the "apparent" bounds of the figure.
 */
public class InsetRelativeHandleLocator extends RelativeHandleLocator {
	int verticalInset;
	int horizontalInset;
	int location;
	
	public InsetRelativeHandleLocator(IFigure reference, int location, int verticalInset, int horizontalInset) {
		super(reference, location);
		this.verticalInset = verticalInset;
		this.horizontalInset = horizontalInset;
		this.location = location;
	}
	public void relocate(IFigure target) {
		super.relocate(target);
		Rectangle bounds = target.getBounds();
		switch (location & PositionConstants.EAST_WEST) {
			case PositionConstants.WEST:
				bounds.x += horizontalInset;
				break;
			case PositionConstants.EAST:
				bounds.x -= horizontalInset;
				break;
		}
		switch (location & PositionConstants.NORTH_SOUTH) {
			case PositionConstants.NORTH:
				bounds.y += verticalInset;
				break;
			case PositionConstants.SOUTH:
				bounds.y -= verticalInset;
				break;
		}
		target.setBounds(bounds);
	}
}
