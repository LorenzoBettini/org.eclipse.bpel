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
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;


/**
 * This is the border for scope.
 */
public class ScopeBorder extends CollapsableBorder {
	
	// TODO: Move these or remove them
	static final int borderWidth = 1;
	static final int margin = 11;
	// space between the inside of the border and the contents
	static final int hBorderInsets = 3;
	static final int vBorderInsets = 16;
	// extra horizontal space to use when we have no children.
	static final int extraHorizontalSpace = 50;
	
	// Location of the "-" icons when the border is expanded
	private Rectangle rectExpandedTop, rectExpandedBottom;

	// The bounds of the border of the scope when expanded. Takes
	// into account space for the drawer.
	private Rectangle expandedBounds;
	
	// Fault handler, Compensation handler and Event handler support
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
	
	// Whether or not we have children; used to determine whether
	// to add extraSpace
	private boolean hasChildren = false;
	
	public ScopeBorder(IFigure parentFigure, String labelText, Image image) {
		super(true, IBPELUIConstants.ARC_WIDTH, parentFigure, labelText, image);
		
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

	public Dimension getPreferredSize(IFigure f) {
		calculate(f);
		if (isCollapsed()) {
			return new Dimension(rectCollapsed.width + DRAWER_WIDTH * 2, rectCollapsed.height + expandedHeight);
		}
		int extraSpace = 0;
		if (!hasChildren) {
			extraSpace = extraHorizontalSpace;
		}
		return new Dimension(expandedBounds.width + extraSpace, expandedBounds.height + expandedHeight);
	}
	
	protected void doPaint(IFigure figure, Graphics graphics, Insets insets) {
		super.doPaint(figure, graphics, insets);
		
		ColorRegistry registry = BPELUIPlugin.getPlugin().getColorRegistry();

		Color old = graphics.getForegroundColor();
		graphics.setForegroundColor(registry.get(IBPELUIConstants.COLOR_SCOPE_BORDER));
		graphics.drawRoundRectangle(expandedBounds, IBPELUIConstants.ARC_WIDTH, IBPELUIConstants.ARC_WIDTH);
		graphics.drawRectangle(expandedBounds.x, expandedBounds.y, expandedBounds.width, 3);
		graphics.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		graphics.drawRectangle(expandedBounds.x + 1, expandedBounds.y + 1, expandedBounds.width - 2, 1);
		graphics.setForegroundColor(old);
		if (isCollapsed()) {
			graphics.drawImage(collapsedImage, rectCollapsed.getLocation());
		} else {
			graphics.drawImage(expandedImage, rectExpandedTop.getLocation());
			graphics.drawImage(expandedImage, rectExpandedBottom.getLocation());
		}

		// Draw the fault image in the upper right hand corner of the round rectangle
		if (showFault) {
			graphics.drawImage(faultImage, rectFault.x, rectFault.y);
		}
		// Draw the compensation image in the upper right hand corner of the round rectangle,
		// leaving room for the fault image.
		if (showCompensation) {
			graphics.drawImage(compensationImage, rectCompensation.x, rectCompensation.y);
		}
		// Draw the event image in the upper right hand corner of the round rectangle,
		// leaving room for fault and compensation.
		if (showEvent) {
			graphics.drawImage(eventImage, rectEvent.x, rectEvent.y);
		}
	}
	
	protected void calculate(IFigure figure) {
		super.calculate(figure);

		// Bounds of the figure that we are given
		Rectangle figureBounds = figure.getBounds().getCopy();
		
		// area for plus/minus buttons
		rectExpandedTop = new Rectangle(figureBounds.x + figureBounds.width / 2 - expandedWidth/2, figureBounds.y, expandedWidth, expandedHeight);
		rectExpandedBottom = new Rectangle(figureBounds.x + figureBounds.width/2 - expandedWidth/2, figureBounds.y + figureBounds.height - expandedHeight - borderWidth, expandedWidth, expandedHeight);
				
		this.expandedBounds = figureBounds.getCopy();
		// Leave room for the drawers
		expandedBounds.x += DRAWER_WIDTH;
		expandedBounds.width -= DRAWER_WIDTH * 2;
		// Leave room on the top for part of the top expanded image
		expandedBounds.y += expandedHeight / 2 - 1;
		expandedBounds.height -= expandedHeight / 2 - 1;
		// Room for the bottom
		expandedBounds.height -= expandedHeight / 2;

		// Calculate the location of the fault/event/compensation decorations
		// Draw the fault image in the upper right hand corner of the round rectangle
		if (showFault) {
			int x = expandedBounds.x + expandedBounds.width - faultImageWidth;
			this.rectFault = new Rectangle(x, expandedBounds.y, faultImageWidth, faultImageHeight);
		}
		// Draw the compensation image in the upper right hand corner of the round rectangle,
		// leaving room for the fault image.
		if (showCompensation) {
			int x = expandedBounds.x + expandedBounds.width - compensationImageWidth;
			if (showFault) {
				x -= faultImageWidth + 2;
			}
		 	this.rectCompensation = new Rectangle(x, expandedBounds.y, compensationImageWidth, compensationImageHeight);
		}
		// Draw the event image in the upper right hand corner of the round rectangle,
		// leaving room for fault and compensation.
		if (showEvent) {
			int x = expandedBounds.x + expandedBounds.width - eventImageWidth;
			if (showFault) {
				x -= faultImageWidth + 2;
			}
			if (showCompensation) {
				x -= compensationImageWidth + 2;
			}
			this.rectEvent = new Rectangle(x, expandedBounds.y, eventImageWidth, eventImageHeight);
		}		

		// Top drawer
		IMarker topMarker = getTopMarker();
		if (topMarker != null) {
			// Draw the image for the top drawer
			if (isCollapsed()) {
				topDrawerLocation.x = expandedBounds.x - DRAWER_WIDTH + DRAWER_INSET;
				topDrawerLocation.y = expandedBounds.y;
			} else {
				topDrawerLocation.x = expandedBounds.x - DRAWER_WIDTH;
				topDrawerLocation.y = expandedBounds.y + IBPELUIConstants.ARC_WIDTH;
			}
		}
		// Bottom drawer
		IMarker bottomMarker = getBottomMarker();
		if (bottomMarker != null) {
			// Draw the image for the bottom drawer
			if (isCollapsed()) {
				bottomDrawerLocation.x = expandedBounds.x - DRAWER_WIDTH + DRAWER_INSET;
				bottomDrawerLocation.y = expandedBounds.y + DRAWER_HALF_HEIGHT;
			} else {
				bottomDrawerLocation.x = expandedBounds.x - DRAWER_WIDTH;
				bottomDrawerLocation.y = expandedBounds.y + DRAWER_HALF_HEIGHT + IBPELUIConstants.ARC_WIDTH;
			}
		}
		// Top drawer marker image
		Image topImage = getTopImage();
		if (topImage != null) {
			if (isCollapsed()) {
				topImageLocation.x = expandedBounds.x - DRAWER_WIDTH + DRAWER_INSET + 1;
				topImageLocation.y = expandedBounds.y + DRAWER_INSET;
			} else {
				topImageLocation.x = expandedBounds.x - DRAWER_WIDTH + DRAWER_INSET;
				topImageLocation.y = expandedBounds.y + IBPELUIConstants.ARC_WIDTH + DRAWER_INSET;
			}
		}
		// Bottom drawer marker image
		Image bottomImage = getBottomImage();
		if (bottomImage != null) {
			if (isCollapsed()) {
				bottomImageLocation.x = expandedBounds.x - DRAWER_WIDTH + DRAWER_INSET + 1;
				bottomImageLocation.y = expandedBounds.y + DRAWER_INSET + DRAWER_HALF_HEIGHT;
			} else {
				bottomImageLocation.x = expandedBounds.x - DRAWER_WIDTH + DRAWER_INSET;
				bottomImageLocation.y = expandedBounds.y + IBPELUIConstants.ARC_WIDTH + DRAWER_HALF_HEIGHT + DRAWER_INSET;
			}
		}
	}
	

	public Insets getInsets(IFigure figure) {
		calculate(figure);
		Insets result;
		// TODO: Fix
		if (isCollapsed()) {
			result = new Insets(borderWidth + margin, borderWidth + margin + DRAWER_WIDTH, borderWidth + margin, borderWidth + margin + DRAWER_WIDTH);
		} else {	
			result = new Insets(vBorderInsets + expandedHeight, hBorderInsets, vBorderInsets + expandedHeight, hBorderInsets);
			result.left += DRAWER_WIDTH;
			result.right += DRAWER_WIDTH;
		}
		return result;
	}
	
	/**
	 * Throw away values that determine the layout
	 */
	public void invalidate() {
		rectExpandedTop = null;
		rectExpandedBottom = null;
	}

	public boolean isPointInFaultImage(int x, int y) {
		if (showFault) {
			Point p = new Point(x, y);
			parentFigure.translateToRelative(p);
			return rectFault.contains(p);
		}
		return false;
	}	

	public boolean isPointInCompensationImage(int x, int y) {
		if (showCompensation) {
			Point p = new Point(x, y);
			parentFigure.translateToRelative(p);
			return rectCompensation.contains(p);
		}
		return false;
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

	public void setShowCompensation(boolean showCompensation) {
		this.showCompensation = showCompensation;
	}

	public void setShowFault(boolean showFault) {
		this.showFault = showFault;
	}
	
	/**
	 * Tests whether the given point is inside the collapse image. The superclass
	 * does not know where the collapse image(s) is located.
	 */
	public boolean isPointInCollapseImage(int x, int y) {
		if (isCollapsed()) return super.isPointInCollapseImage(x, y);
		Point p = new Point(x,y);
		parentFigure.translateToRelative(p);
		Rectangle rect = rectExpandedTop.getCopy();
		rect.expand(new Insets(1,1,1,1));
		if (rect.contains(p)) return true;
		if (!isCollapsed()) {
			rect = rectExpandedBottom.getCopy();
			rect.expand(new Insets(1,1,1,1));
			return rect.contains(p);
		}
		return false;
	}
	/**
	 * Provide gradient rectangle.
	 */
	protected Rectangle getGradientRect() {
		if (isCollapsed()) return super.getGradientRect();
		invalidate();
		calculate(parentFigure);
		return expandedBounds;
	}
	
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
}