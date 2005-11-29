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

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.Image;


public class LeafBorder extends GradientBorder {
	
	// The horizontal margin between the border and the image/text 
	private static final int leftMargin = 6;
	private static final int rightMargin = 10;
	// The vertical margin between the border and the image/text
	private static final int topMargin = 4;
	private static final int bottomMargin = 3;
	
	// The width of the border and the drawer border.
	private static final int borderWidth = 1;
	
	private int faultImageWidth, faultImageHeight;
	private int compensationImageWidth, compensationImageHeight;
	private int eventImageWidth, eventImageHeight;
	private Image compensationImage;
	private Image faultImage;
	private Image eventImage;
	private boolean showFault;
	private boolean showCompensation;
	private boolean showEvent;
	private Rectangle rectFault;
	private Rectangle rectCompensation;
	private Rectangle rectEvent;
	private Rectangle bounds;
	
	// Parent figure used for absolute bounds conversion
	private IFigure parentFigure;
	
	private Rectangle rectBounds;
	
	public LeafBorder(IFigure parentFigure) {
		super(false, IBPELUIConstants.ARC_WIDTH);
		this.parentFigure = parentFigure;

		// Initialize images for fault, compensation and event handler decorations
		this.faultImage = BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_FAULT_INDICATOR);
		org.eclipse.swt.graphics.Rectangle r = faultImage.getBounds();
		this.faultImageWidth = r.width;
		this.faultImageHeight = r.height;

		this.compensationImage = BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_COMPENSATION_INDICATOR);
		r = compensationImage.getBounds();
		this.compensationImageWidth = r.width;
		this.compensationImageHeight = r.height;

		this.eventImage = BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_EVENT_INDICATOR);
		r = eventImage.getBounds();
		this.eventImageWidth = r.width;
		this.eventImageHeight = r.height;
	}

	public void paint(IFigure figure, Graphics graphics, Insets insets) {
		this.bounds = figure.getBounds();
		ColorRegistry registry = BPELUIPlugin.getPlugin().getColorRegistry();
		graphics.setForegroundColor(registry.get(IBPELUIConstants.COLOR_ACTIVITY_BORDER));
		int arcSize = IBPELUIConstants.ARC_WIDTH;
		// Remember the clipping rectangle
		Rectangle oldClip = new Rectangle();
		oldClip = graphics.getClip(oldClip);
		
		IMarker topMarker = getTopMarker();
		IMarker bottomMarker = getBottomMarker();
		if (topMarker != null || bottomMarker != null) {
			// Draw the drawers
			Rectangle clippingRect;
			if (bottomMarker == null) {
				clippingRect = new Rectangle(bounds.x, bounds.y, DRAWER_WIDTH, DRAWER_HALF_HEIGHT + 1);
			} else if (topMarker == null) {
				clippingRect = new Rectangle(bounds.x, bounds.y + DRAWER_HALF_HEIGHT, DRAWER_WIDTH, DRAWER_HALF_HEIGHT + 1);
			} else {
				clippingRect = new Rectangle(bounds.x, bounds.y, DRAWER_WIDTH, DRAWER_HEIGHT + 1);
			}
			graphics.setClip(clippingRect);
			// -1 due to GEF
			graphics.drawRoundRectangle(new Rectangle(bounds.x + DRAWER_INSET, bounds.y, DRAWER_WIDTH * 2, DRAWER_HEIGHT - 1), IBPELUIConstants.ARC_WIDTH, IBPELUIConstants.ARC_WIDTH);
			graphics.setClip(oldClip);
			if (bottomMarker == null || topMarker == null) {
				graphics.drawLine(bounds.x + DRAWER_INSET, bounds.y + DRAWER_HALF_HEIGHT, bounds.x + DRAWER_WIDTH, bounds.y + DRAWER_HALF_HEIGHT);
			}
		}
		
		// Draw the actual breakpoints
		Image topImage = getTopImage();
		if (topImage != null) {
			int x = bounds.x + DRAWER_INSET;
			int y = bounds.y + DRAWER_INSET;
			graphics.drawImage(topImage, x, y);
		}
		Image bottomImage = getBottomImage();
		if (bottomImage != null) {
			int x = bounds.x + DRAWER_INSET;
			int y = bounds.y + DRAWER_INSET + DRAWER_HALF_HEIGHT;
			graphics.drawImage(bottomImage, x, y);
		}
		
		// Determine whether or not square corners are needed on the left edge.
		boolean needSquareCorners = (topImage != null) || (bottomImage != null);
		
		// Calculate the bounding rectangle for the round rectangle
		this.rectBounds = new Rectangle();
		rectBounds.x = bounds.x + DRAWER_WIDTH;
		rectBounds.y = bounds.y;
		rectBounds.width = bounds.width - DRAWER_WIDTH * 2;
		// -1 due to GEF
		rectBounds.height = bounds.height - 1;

		if (needSquareCorners) {
			Rectangle clippingRect = new Rectangle(rectBounds.x + rectBounds.width / 2, rectBounds.y, rectBounds.width / 2 + 2, rectBounds.height + 1);
			graphics.setClip(clippingRect);
			graphics.drawRoundRectangle(rectBounds, arcSize, arcSize);
			clippingRect = new Rectangle(rectBounds.x, rectBounds.y, rectBounds.width / 2 + 1, rectBounds.height + 1);
			graphics.setClip(clippingRect);
			graphics.drawRectangle(rectBounds);
			graphics.setClip(oldClip);
		} else {
			graphics.drawRoundRectangle(rectBounds, arcSize, arcSize);
		}

		// Draw the fault image in the upper right hand corner of the round rectangle
		if (showFault) {
			this.rectFault = new Rectangle(bounds.x + bounds.width - faultImageWidth - DRAWER_WIDTH, bounds.y, faultImageWidth, faultImageHeight);
			graphics.drawImage(faultImage, rectFault.x, rectFault.y);
		}
		// Draw the compensation image in the upper right hand corner of the round rectangle
		if (showCompensation) {
			int compensationImageOffset = bounds.y;
			if (showFault) {
				compensationImageOffset += faultImageHeight + 2;
			}
			this.rectCompensation = new Rectangle(bounds.x + bounds.width - compensationImageWidth - DRAWER_WIDTH, compensationImageOffset, compensationImageWidth, compensationImageHeight);
			graphics.drawImage(compensationImage, rectCompensation.x, rectCompensation.y);
		}
		if (showEvent) {
			int eventImageOffset = bounds.y;
			if (showFault) {
				eventImageOffset += faultImageHeight + 2;
			}
			if (showCompensation) {
				eventImageOffset += compensationImageHeight + 2;
			}
			this.rectEvent = new Rectangle(bounds.x + bounds.width - eventImageWidth - DRAWER_WIDTH, eventImageOffset, eventImageWidth, eventImageHeight);
			graphics.drawImage(eventImage, rectEvent.x, rectEvent.y);
		}
	}

	public Insets getInsets(IFigure figure) {
		return new Insets(borderWidth + topMargin, borderWidth + leftMargin + DRAWER_WIDTH, borderWidth + bottomMargin, borderWidth + rightMargin + DRAWER_WIDTH);
	}

	public void setFaultImage(Image faultImage) {
		this.faultImage = faultImage;
		org.eclipse.swt.graphics.Rectangle r = faultImage.getBounds();
		this.faultImageWidth = r.width;
		this.faultImageHeight = r.height;
	}
	public boolean isPointInFaultImage(int x, int y) {
		if (showFault) {
			Point p = new Point(x, y);
			parentFigure.translateToRelative(p);
			return rectFault.contains(p);
		}
		return false;
	}	
	public void setShowFault(boolean showFault) {
		this.showFault = showFault;
	}
	
	public void setCompensationImage(Image compensationImage) {
		this.compensationImage = compensationImage;
		org.eclipse.swt.graphics.Rectangle r = compensationImage.getBounds();
		this.compensationImageWidth = r.width;
		this.compensationImageHeight = r.height;
	}
	public boolean isPointInCompensationImage(int x, int y) {
		if (showCompensation) {
			Point p = new Point(x, y);
			parentFigure.translateToRelative(p);
			return rectCompensation.contains(p);
		}
		return false;
	}	
	public void setShowCompensation(boolean showCompensation) {
		this.showCompensation = showCompensation;
	}
	
	public void setEventImage(Image eventImage) {
		this.eventImage = eventImage;
		org.eclipse.swt.graphics.Rectangle r = eventImage.getBounds();
		this.eventImageWidth = r.width;
		this.eventImageHeight = r.height;
	}
	public boolean isPointInEventImage(int x, int y) {
		if (showEvent) {
			Point p = new Point(x, y);
			parentFigure.translateToRelative(p);
			return rectEvent.contains(p);
		}
		return false;
	}	
	public void setShowEvent(boolean showEvent) {
		this.showEvent = showEvent;
	}
	
	protected Rectangle getGradientRect() {
		Rectangle bounds = parentFigure.getBounds();
		this.rectBounds = new Rectangle();
		rectBounds.x = bounds.x + DRAWER_WIDTH;
		rectBounds.y = bounds.y;
		rectBounds.width = bounds.width - DRAWER_WIDTH * 2;
		rectBounds.height = bounds.height;
		if (Platform.getWS().equals(Platform.WS_GTK)) {
			rectBounds.height = rectBounds.height - 1;
		}
		return rectBounds;
	}
	public boolean isPointInTopDrawer(int x, int y) {
		if (getTopMarker() == null) return false;
		if (bounds == null) return false;
		Point p = new Point(x, y);
		parentFigure.translateToRelative(p);
		Image image = getTopImage();
		org.eclipse.swt.graphics.Rectangle imageSize = image.getBounds();
		int imageX = bounds.x + DRAWER_INSET;
		int imageY = bounds.y + DRAWER_INSET + 2;
		Rectangle imageBounds = new Rectangle(imageX, imageY, imageSize.width, imageSize.height);
		return imageBounds.contains(p);
	}
	public boolean isPointInBottomDrawer(int x, int y) {
		if (getBottomMarker() == null) return false;
		if (bounds == null) return false;
		Point p = new Point(x, y);
		parentFigure.translateToRelative(p);
		Image image = getBottomImage();
		org.eclipse.swt.graphics.Rectangle imageSize = image.getBounds();
		int imageX = bounds.x + DRAWER_INSET;
		int imageY = bounds.y + DRAWER_INSET + 2 + DRAWER_HALF_HEIGHT;
		Rectangle imageBounds = new Rectangle(imageX, imageY, imageSize.width, imageSize.height);
		return imageBounds.contains(p);
	}	
}
