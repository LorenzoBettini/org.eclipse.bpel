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
package org.eclipse.bpel.ui.editparts.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.bpel.common.ui.figures.InsetResizeHandle;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.BPELDragEditPartsTracker;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.jface.resource.ColorRegistry;


/**
 * Add handles to the selected edit part. We use this instead of
 * NonResizableEditPolicy because we don't want a focus rectangle.
 * 
 * TODO: can this policy be installed higher up in the editpart heirarchy?
 * (search refs to see what I mean)
 */
public class BPELSelectionEditPolicy extends NonResizableEditPolicy {
	
	protected boolean resizable;
	protected boolean movable;
	
	public BPELSelectionEditPolicy(boolean resizable, boolean movable) {
		this.resizable = resizable;
		this.movable = movable;
	}
	protected List createSelectionHandles() {
		// HACK: Avoid creating selection handles for objects that have already been deleted
		// (this can occur in some tricky notification scenarios)
		if (((EObject)getHost().getModel()).eResource() == null) { return Collections.EMPTY_LIST; }
		
		List list = new ArrayList();
		if (resizable) {
			addResizableHandles((GraphicalEditPart)getHost(), list);
		} else {
			addCornerHandles((GraphicalEditPart)getHost(), list);
		}
		return list;
	}
	/**
	 * Dispatches erase requests to more specific methods.
	 * @see org.eclipse.gef.EditPolicy#eraseSourceFeedback(org.eclipse.gef.Request)
	 */
	public void eraseSourceFeedback(Request request) {
		if (resizable && REQ_RESIZE.equals(request.getType()))
			eraseChangeBoundsFeedback((ChangeBoundsRequest)request);
		else
			super.eraseSourceFeedback(request);
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	public Command getCommand(Request request) {
		if (resizable && REQ_RESIZE.equals(request.getType()))
			return getResizeCommand((ChangeBoundsRequest)request);

		return super.getCommand(request);
	}

	/**
	 * Returns the command contribution for the given resize request. By default, the request
	 * is redispatched to the host's parent as a {@link
	 * org.eclipse.gef.RequestConstants#REQ_RESIZE_CHILDREN}.  The parent's editpolicies
	 * determine how to perform the resize based on the layout manager in use.
	 * @param request the resize request
	 * @return the command contribution obtained from the parent
	 */
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_RESIZE_CHILDREN);
		req.setEditParts(getHost());
	
		req.setMoveDelta(request.getMoveDelta());
		req.setSizeDelta(request.getSizeDelta());
		req.setLocation(request.getLocation());
		req.setResizeDirection(request.getResizeDirection());
		return getHost().getParent().getCommand(req);
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#showSourceFeedback(org.eclipse.gef.Request)
	 */
	public void showSourceFeedback(Request request) {
		if (resizable && REQ_RESIZE.equals(request.getType()))
			showChangeBoundsFeedback((ChangeBoundsRequest)request);
		else
			super.showSourceFeedback(request);
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#understandsRequest(org.eclipse.gef.Request)
	 */
	public boolean understandsRequest(Request request) {
		if (resizable && REQ_RESIZE.equals(request.getType()))
			return true;
// TODO: This code is more correct than the above code. Change it in the future.
//		if (REQ_RESIZE.equals(request.getType())) return resizable;
		if (REQ_MOVE.equals(request.getType())) return movable;
		return super.understandsRequest(request);
	}
	
	void addCornerHandles(GraphicalEditPart part, List handles) {
		handles.add(createHandle(part, PositionConstants.SOUTH_EAST, getSouthInset(), getEastInset()));
		handles.add(createHandle(part, PositionConstants.SOUTH_WEST, getSouthInset(), getWestInset()));
		handles.add(createHandle(part, PositionConstants.NORTH_WEST, getNorthInset(), getWestInset()));
		handles.add(createHandle(part, PositionConstants.NORTH_EAST, getNorthInset(), getEastInset()));
	}
	Handle createHandle(GraphicalEditPart owner, int direction, int verticalInset, int horizontalInset) {
		//ResizeHandle handle = new ResizeHandle(owner,direction);
		InsetResizeHandle handle = new InsetResizeHandle(owner, direction, verticalInset, horizontalInset);
		handle.setCursor(SharedCursors.SIZEALL);
		handle.setDragTracker(new BPELDragEditPartsTracker(owner));
		return handle;
	}
	void addResizableHandles(GraphicalEditPart part, List handles) {
		handles.add(createResizableHandle(part, PositionConstants.EAST, 0, getEastInset()));
		handles.add(createResizableHandle(part, PositionConstants.SOUTH_EAST, getSouthInset(), getEastInset()));
		handles.add(createResizableHandle(part, PositionConstants.SOUTH, getSouthInset(), 0));
		handles.add(createResizableHandle(part, PositionConstants.SOUTH_WEST, getSouthInset(), getWestInset()));
		handles.add(createResizableHandle(part, PositionConstants.WEST, 0, getWestInset()));
		handles.add(createResizableHandle(part, PositionConstants.NORTH_WEST, getNorthInset(), getWestInset()));
		handles.add(createResizableHandle(part, PositionConstants.NORTH, getNorthInset(), 0));
		handles.add(createResizableHandle(part, PositionConstants.NORTH_EAST, getNorthInset(), getEastInset()));
		
	}
	Handle createResizableHandle(GraphicalEditPart owner, int direction, int verticalInset, int horizontalInset) {
		//ResizeHandle handle = new ResizeHandle(owner, direction);
		InsetResizeHandle handle = new InsetResizeHandle(owner, direction, verticalInset, horizontalInset);
		handle.setDragTracker(new ResizeTracker(owner, direction));
		return handle;
	}
	
	protected void eraseChangeBoundsFeedback(ChangeBoundsRequest request) {
		if (REQ_MOVE.equals(request.getType()) && !movable)
			return;
		super.eraseChangeBoundsFeedback(request);
	}

	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		if (REQ_MOVE.equals(request.getType()) && !movable)
			return;
		
		// Take the super implementation to account for the drawer width on the ghost figure.
		IFigure p = getDragSourceFeedbackFigure();
		
		Rectangle r = getHostFigure().getBounds().getCopy();
		getHostFigure().translateToAbsolute(r);
		r.translate(request.getMoveDelta());
		Dimension resize = request.getSizeDelta();
		r.width += resize.width;
		r.height += resize.height;

		// Adjust for drawer width
		r.x += getDrawerInset();
		r.width -= getDrawerInset() * 2;
		p.translateToRelative(r);
		p.setBounds(r);
	}

	/**
	 * Override method from superclass to adjust for drawer width on ghost figure
	 */
	protected IFigure createDragSourceFeedbackFigure() {
		// Use a ghost rectangle for feedback
		ColorRegistry registry = BPELUIPlugin.getPlugin().getColorRegistry();
		RectangleFigure r = new RectangleFigure();
		FigureUtilities.makeGhostShape(r);
		r.setLineStyle(Graphics.LINE_DASHDOT);
		r.setForegroundColor(registry.get(IBPELUIConstants.COLOR_BLACK));
		Rectangle bounds = ((GraphicalEditPart)getHost()).getFigure().getBounds().getCopy();
		// Adjust for drawer width if necessary
		bounds.x += getDrawerInset();
		bounds.width -= getDrawerInset() * 2;
		r.setBounds(bounds);
		addFeedback(r);
		return r;
	}
	
	// Subclasses may override these methods to locate feedback and handles.
	protected int getDrawerInset() {
		return 0;
	}
	protected int getNorthInset() {
		return 0;
	}
	protected int getSouthInset() {
		return 0;
	}
	protected int getEastInset() {
		return 0;
	}
	protected int getWestInset() {
		return 0;
	}
}
