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
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.ui.editparts.policies.BPELOrderedLayoutEditPolicy;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;

public class IfEditPart extends PickEditPart {

	/**
	 * This is a specical EditPart that represents the If node in the top left
	 * corner of an If activity. It looks like an ElseIfEditPart but has no own
	 * model representation.
	 * 
	 * It displays the activity children of the If activty and layouts together
	 * with the other ElseIfEditParts (representing Else and ElseIf).
	 */
	private class IfNodeEditPart extends ElseIfEditPart {

		/**
		 * This EditPart does not have its own children but displays the
		 * activity children of its parent (the IfEditPart). Therefor we return
		 * the parent's children list without the non-activity elements.
		 */
		@Override
		public List getChildren() {
			List<EditPart> children = new ArrayList<EditPart>();
			for (Iterator i = getParent().getChildren().iterator(); i.hasNext();) {
				EditPart child = (EditPart) i.next();
				if (!(child instanceof ElseIfEditPart)) {
					children.add(child);
				}
			}
			return children;
		}
		
		@Override
		protected void handleModelChanged() {
			/*
			 * don't react on model changes - everything is handled by the
			 * hosting IfEditPart
			 */
			return;
		}

		@Override
		protected void refreshChildren() {
			/*
			 * refreshing children is done by the hosting IfEditPart
			 */
			return;
		}

		@Override
		protected void addChildVisual(EditPart childEditPart, int index) {
			super.addChildVisual(childEditPart, index);
		}
		
		@Override
		protected void removeChildVisual(EditPart childEditPart) {
			super.removeChildVisual(childEditPart);
		}
	}

	/**
	 * Modified layout policy for the IfNodeEditPart
	 */
	private class IfNodeOrderedLayoutPolicy extends BPELOrderedLayoutEditPolicy {

		/**
		 * This is a HACK - I increased the visibility of the
		 * getFeedbackIndexFor() method to make it callable from the IfEditPart
		 */
		public int getFeedbackIndexFor(Request request) {
			return super.getFeedbackIndexFor(request);
		}

	}

	/**
	 * Modified layout policy for the IfEditPart
	 */
	private class IfOrderedLayoutEditPolicy extends BPELOrderedLayoutEditPolicy {

		/*
		 * This one is tricky - it works together with the overwritten
		 * getConnectionChildren() method below.
		 * 
		 * We create horizontal connections for the connection children of the
		 * IfEditPart - getConnectionChildren() returns all else and elseIf
		 * children AND the "fake" IfNodeEditPart.
		 * 
		 * We create vertical connections for the connection children of the
		 * IfNodeEditPart - getConnectionChildren() returns all activity
		 * (everything except else and elseIf) of the IfEditPart.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void refreshConnections() {
			// remove connections before redrawing
			clearConnections();

			if (!isCollapsed()) {
				polyLineConnectionList = createHorizontalConnections((BPELEditPart) getHost());
				polyLineConnectionList
						.addAll(createVerticalConnections(getIfNodeEditPart()));
			}
		}

		/*
		 * The IfNodeEditPart's layout policy is responsible for handling the
		 * target feedbacks.
		 */
		@Override
		protected void eraseLayoutTargetFeedback(Request request) {
			EditPolicy policy = ifEditPart
					.getEditPolicy(EditPolicy.LAYOUT_ROLE);
			policy.eraseTargetFeedback(request);
		}

		/**
		 * Returns list of children to create connections for.
		 */
		@SuppressWarnings("unchecked")
		@Override
		protected List getConnectionChildren(BPELEditPart editPart) {
			List<EditPart> children = getChildren();
			List<EditPart> newChildren = new ArrayList<EditPart>();

			if (editPart instanceof ElseIfEditPart) {
				// For the IfNodeEditPart we return all activity children of the
				// IfEditPart.
				for (EditPart child : children) {
					if (!(child instanceof ElseIfEditPart)) {
						newChildren.add(child);
					}
				}
			} else {
				// For the IfEditPart we return all non-activity children...
				for (EditPart child : children) {
					if (child instanceof ElseIfEditPart) {
						newChildren.add(child);
					}
				}
				// ... and additionaly add our "fake" IfNodeEditPart.
				newChildren.add(getIfNodeEditPart());
			}
			return newChildren;
		}

		/*
		 * The IfNodeEditPart's layout policy is responsible for handling the
		 * target feedbacks.
		 */
		@Override
		protected int getFeedbackIndexFor(Request request) {
			EditPolicy policy = ifEditPart
					.getEditPolicy(EditPolicy.LAYOUT_ROLE);
			return ((IfNodeOrderedLayoutPolicy) policy)
					.getFeedbackIndexFor(request);
		}

		/*
		 * The IfNodeEditPart's layout policy is responsible for handling the
		 * target feedbacks.
		 */
		@Override
		protected void showLayoutTargetFeedback(Request request) {
			EditPolicy policy = ifEditPart
					.getEditPolicy(EditPolicy.LAYOUT_ROLE);
			policy.showTargetFeedback(request);
		}
	}

	private IfNodeEditPart ifEditPart;

	@Override
	public void activate() {
		super.activate();
		getIfNodeEditPart().activate();
	}

	@Override
	public void deactivate() {
		getIfNodeEditPart().deactivate();
		super.deactivate();
	}

	@Override
	public void refreshVisuals() {
		getIfNodeEditPart().refreshVisuals();
		super.refreshVisuals();
	}

	/**
	 * creates the "fake" IfNodeEditPart that is displayed in the top left
	 * corner of the IfEditPart.
	 * 
	 */
	private void createIfNode() {
		this.ifEditPart = new IfNodeEditPart();
		this.ifEditPart.setModel(getModel());
		this.ifEditPart.setParent(this);
		this.ifEditPart.createEditPolicies();

		// we need a special layout policy since this is only a "fake" node
		this.ifEditPart.installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new IfNodeOrderedLayoutPolicy());
	}

	/**
	 * Checks whether the IfNodeEditPart of the current IfEditPart is present
	 * and creates it if not.
	 * 
	 * @return the IfNodeEditPart of the current IfEditPart
	 */
	private IfNodeEditPart getIfNodeEditPart() {
		if (this.ifEditPart == null) {
			createIfNode();
		}
		return this.ifEditPart;
	}

	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (childEditPart instanceof ElseIfEditPart) {
			// add the "nodes" to the IfEditPart
			super.addChildVisual(childEditPart, -1);
		} else {
			// add everything else to the IfNodeEditPart
			getIfNodeEditPart().addChildVisual(childEditPart, index);
		}
	}

	protected void configureExpandedFigure(IFigure figure) {
		super.configureExpandedFigure(figure);

		/*
		 * Add the if node as "fake" visual child. Doning this here ensures the
		 * if node is alway present when the IfEditPart is expanded.
		 */
		this.addChildVisual(getIfNodeEditPart(), 0);
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new IfOrderedLayoutEditPolicy());
	}

	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		if (childEditPart instanceof ElseIfEditPart) {
			// remove the "nodes" form the IfEditPart
			super.removeChildVisual(childEditPart);
		} else {
			// remove everything else from the IfNodeEditPart
			getIfNodeEditPart().removeChildVisual(childEditPart);
		}
	}

	@Override
	protected void unregisterVisuals() {
		getIfNodeEditPart().unregisterVisuals();
		super.unregisterVisuals();
	}

}
