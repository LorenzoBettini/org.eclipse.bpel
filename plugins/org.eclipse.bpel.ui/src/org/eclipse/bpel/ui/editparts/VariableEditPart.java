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

import java.util.List;

import org.eclipse.bpel.common.ui.tray.TrayCategoryEntryEditPart;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IHoverHelper;
import org.eclipse.bpel.ui.IHoverHelperSupport;
import org.eclipse.bpel.ui.editparts.policies.BPELComponentEditPolicy;
import org.eclipse.bpel.ui.editparts.policies.BPELDirectEditPolicy;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;


public class VariableEditPart extends TrayCategoryEntryEditPart implements IHoverHelperSupport {
	protected MouseMotionListener mouseMotionListener;

	protected void createEditPolicies() {
		super.createEditPolicies();
		// handles deletions
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new BPELComponentEditPolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new BPELDirectEditPolicy());
	}

	public void refreshHoverHelp() {
		// Refresh the tooltip if we can find a helper.
		try {
			IHoverHelper helper = BPELUIRegistry.getInstance().getHoverHelper();
			if (helper != null) {
				String text = helper.getHoverFigure((EObject)getModel());
				if (text == null) {
					getFigure().setToolTip(null);
				} else {
					getFigure().setToolTip(new Label(text));
				}
			}
		} catch (CoreException e) {
			getFigure().setToolTip(null);
			BPELUIPlugin.log(e);
		}		
	}

	/**
	 * HACK
	 * See comments in org.eclipse.bpel.ui.editparts.VariablesEditPart.selectAnotherVariable(VariableEditPart)
	 */
	public void removeNotify() {
		// we only do the following hack if we are dealing with scoped variables
		if (!(((EObject)getParent().getModel()).eContainer() instanceof Scope)) {
			super.removeNotify();
			return;
		}
		if (getSelected() != SELECTED_NONE) {
//			getViewer().deselect(this); 
			// instead of deselecting this variable (which would cause the process to be selected)
			// we should ask the parent edit part (VariablesEditPart) to select some other child
			((VariablesEditPart)getParent()).selectAnotherVariable(this);
		}
		if (hasFocus())
			getViewer().setFocus(null);

		List children = getChildren();
		for (int i = 0; i < children.size(); i++)
			((EditPart)children.get(i))
				.removeNotify();
		unregister();
	}

	protected MouseMotionListener getMouseMotionListener() {
		if (mouseMotionListener == null) {
			this.mouseMotionListener = new MouseMotionListener() {
				public void mouseDragged(MouseEvent me) {
				}
				public void mouseEntered(MouseEvent me) {
				}
				public void mouseExited(MouseEvent me) {
				}
				public void mouseHover(MouseEvent me) {
				}
				public void mouseMoved(MouseEvent me) {
					refreshHoverHelp();
				}
			};
		}
		return mouseMotionListener;
	}
	
	protected IFigure createFigure() {
		IFigure fig =  super.createFigure();
		fig.addMouseMotionListener(getMouseMotionListener());
		return fig;
	}

	protected AccessibleEditPart createAccessible() {
		return new BPELTrayAccessibleEditPart(this);
	}
}
