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

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.common.ui.layouts.AlignedFlowLayout;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.BPELEditDomain;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.adapters.delegates.ActivityContainer;
import org.eclipse.bpel.ui.editparts.policies.BPELContainerEditPolicy;
import org.eclipse.bpel.ui.editparts.policies.BPELOrderedLayoutEditPolicy;
import org.eclipse.bpel.ui.editparts.util.GraphAnimation;
import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.jface.viewers.StructuredSelection;


public class ProcessEditPart extends BPELEditPart {

	private IFigure layeredPane;
	private IFigure activityAndHandlerHolder;
	private IFigure activityHolder;
	private IFigure handlerHolder;
	private Layer activityLayer;
	
	// Whether to show each of the actual handlers.
	// TODO: Initialize these from the preferences store
	private boolean showFH = false, showEH = false;

	public class ProcessOrderedLayoutEditPolicy extends BPELOrderedLayoutEditPolicy {
		// return list of children to create vertical connections for.
		protected List getConnectionChildren(BPELEditPart editPart) {
			List originalChildren = getChildren();
			List newChildren = new ArrayList();
			Iterator it = originalChildren.iterator();
			while (it.hasNext()) {
				Object next = it.next();
				if (next instanceof FaultHandlerEditPart) {
					continue;
				}
				newChildren.add(next);
			}
			return newChildren;
		}

		protected Command getCreateCommand(CreateRequest request) {
			EditPart insertBefore = getInsertionReference(request);
			if (insertBefore == null) return null;
			if (insertBefore instanceof StartNodeEditPart) return null;
			return super.getCreateCommand(request);
		}
	}
	
	CommandStackListener stackListener = new CommandStackListener() {
		public void commandStackChanged(EventObject event) {
			if (!GraphAnimation.captureLayout(getFigure()))
				return;
			while (GraphAnimation.step())
				getFigure().getUpdateManager().performUpdate();
			GraphAnimation.end();
		}
	};
		
	protected void createEditPolicies() {
		super.createEditPolicies();

		// Override the component policy, as you can't delete the process
		installEditPolicy(EditPolicy.COMPONENT_ROLE, null);

		installEditPolicy(EditPolicy.CONTAINER_ROLE, new BPELContainerEditPolicy());

		// The process must lay out its child activity
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ProcessOrderedLayoutEditPolicy());
	}

	
	protected IFigure createFigure() {
		LayeredPane result = new LayeredPane();
		this.layeredPane = result;
		
		Layer layer2 = new Layer();
		this.activityLayer = layer2;
		activityLayer.setLayoutManager(new ToolbarLayout());
		activityLayer.setOpaque(true);
		result.add(layer2, "activityLayer", 0); //$NON-NLS-1$
		
		this.activityAndHandlerHolder = new Figure();
		AlignedFlowLayout layout = new AlignedFlowLayout();
		layout.setHorizontal(false);
		layout.setHorizontalAlignment(AlignedFlowLayout.ALIGN_CENTER);
		layout.setVerticalAlignment(AlignedFlowLayout.ALIGN_BEGIN);
		layout.setSecondaryAlignment(AlignedFlowLayout.ALIGN_CENTER);
		this.activityAndHandlerHolder.setLayoutManager(layout);
		
		this.handlerHolder = new Figure();
		this.handlerHolder.setBorder(new AbstractBorder() {
			public Insets getInsets(IFigure arg0) {
				return new Insets(20, 0, 10, 0);
			}
			public void paint(IFigure arg0, Graphics arg1, Insets arg2) {
			}
		});
		layout = new AlignedFlowLayout(true);
		layout.setHorizontalAlignment(AlignedFlowLayout.ALIGN_BEGIN);
		layout.setVerticalAlignment(AlignedFlowLayout.ALIGN_BEGIN);
		layout.setSecondaryAlignment(AlignedFlowLayout.ALIGN_BEGIN);
		this.handlerHolder.setLayoutManager(layout);
		
		this.activityHolder = new Figure();
		layout = new AlignedFlowLayout();
		layout.setHorizontal(false);
		layout.setHorizontalAlignment(AlignedFlowLayout.ALIGN_CENTER);
		layout.setVerticalAlignment(AlignedFlowLayout.ALIGN_BEGIN);
		layout.setSecondaryAlignment(AlignedFlowLayout.ALIGN_CENTER);
		layout.setVerticalSpacing(SPACING);
		activityHolder.setLayoutManager(layout);
		activityHolder.setOpaque(true);
		activityAndHandlerHolder.add(handlerHolder);
		activityAndHandlerHolder.add(activityHolder);
		layer2.add(activityAndHandlerHolder);
		
		return layeredPane;
	}

	public void setLayoutConstraint(EditPart child, IFigure childFigure, Object constraint) {
		getContentPane(child).setConstraint(childFigure, constraint);
	}
	
	protected IFigure getContentPane(EditPart childEditPart) {
		if (childEditPart instanceof StartNodeEditPart) {
			return handlerHolder;
		} else if (childEditPart instanceof FaultHandlerEditPart) {
			return handlerHolder;
		}
		return activityHolder;
	}
	
	public IFigure getContentPane() {
        return activityHolder;
    }

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren() {
		Process process = getProcess();
		BPELEditDomain domain = (BPELEditDomain)getViewer().getEditDomain();
		List list = new ArrayList();
		
		/* NOTE: The layoutEditPolicy relies on this order to identify the
		 * area the user is mousing over. Do not change the order in which these
		 * children are added unless you change the layoutEditPolicy too!!!!!!!!!!!!!!!!!!!!
		 */

		// TODO: this is way too magic.  can we get rid of this?
		
		list.add(domain.getStartNode());
		IContainer container = new ActivityContainer(BPELPackage.eINSTANCE.getProcess_Activity());
		list.addAll(container.getChildren(process));
		list.add(domain.getEndNode());
		
		/* END OF NOTE */
		
		if (showFH) {
			FaultHandler faultHandler = process.getFaultHandlers();
			if (faultHandler != null) list.add(faultHandler);
		}
		if (showEH) {
			EventHandler eventHandler = process.getEventHandlers();
			if (eventHandler != null) list.add(eventHandler);
		}
    	
		return list;
	}

	protected Process getProcess() {
		return (Process)getModel();
	}

	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		IFigure content = getContentPane(childEditPart);
		if (content != null) {
			if (content == activityHolder) {
				// The index includes the start node, which isn't in this
				// content pane. Subtract one.
				content.add(child, index - 1);
			} else {
				// The handlerHolder currently doesn't care about indexes,
				// just add it.
				content.add(child);
			}
		}
	}
	
	protected void removeChildVisual(EditPart childEditPart) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		IFigure content = getContentPane(childEditPart);
		if (content != null)
			content.remove(child);
	}

	/**
	 * Also overridden to call getContentPane(child) in the appropriate place.
	 */
	protected void reorderChild(EditPart child, int index) {
		// Save the constraint of the child so that it does not
		// get lost during the remove and re-add.
		IFigure childFigure = ((GraphicalEditPart) child).getFigure();
		LayoutManager layout = getContentPane(child).getLayoutManager();
		Object constraint = null;
		if (layout != null)
			constraint = layout.getConstraint(childFigure);

		removeChildVisual(child);
		List children = getChildren();
		children.remove(child);
		children.add(index, child);
		addChildVisual(child, index);
		
		setLayoutConstraint(child, childFigure, constraint);
	}
	
	/**
	 * Override to handle direct edit requests
	 */
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			// let's not activate the rename or direct edit functionality because it's annoying when
			// the user clicks on the canvas and the rename box comes up.
			//	performDirectEdit();
			return;
		}
		super.performRequest(request);
	}
	
	public void setShowFaultHandler(boolean showFaultHandler) {
		this.showFH = showFaultHandler;
		// Call refresh so that both refreshVisuals and refreshChildren will be called.
		refresh();
	}

	public void setShowEventHandler(boolean showEventHandler) {
		this.showEH = showEventHandler;
		// Call refresh so that both refreshVisuals and refreshChildren will be called.
		refresh();
	}
	
	public DragTracker getDragTracker(Request request) {
		return new MarqueeDragTracker() {
			protected void handleFinished() {
				if (getViewer().getSelection().isEmpty()) {
					// if nothing has been select we should select the process
					getViewer().setSelection(new StructuredSelection(ProcessEditPart.this));
				}
			}
		};
	}
}
