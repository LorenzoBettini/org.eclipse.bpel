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
package org.eclipse.bpel.ui.editparts.borders;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

public class RoundRectangleBorderWithDecoration extends RoundRectangleBorder {
	Image decoration;
	
	public RoundRectangleBorderWithDecoration(Image decoration) {
		this.decoration = decoration;
	}
	public RoundRectangleBorderWithDecoration(Insets insets, Image decoration) {
		super(insets);
		this.decoration = decoration;
	}
	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		super.paint(figure, graphics, insets);
		if (decoration != null) {
			Rectangle r = figure.getBounds();
			graphics.drawImage(decoration, r.x + r.width - decoration.getBounds().width, r.y);
		}
	}
}
