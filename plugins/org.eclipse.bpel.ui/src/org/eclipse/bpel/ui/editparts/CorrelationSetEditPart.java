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

import org.eclipse.bpel.common.ui.tray.TrayCategoryEntryEditPart;
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
import org.eclipse.gef.EditPolicy;


public class CorrelationSetEditPart extends TrayCategoryEntryEditPart implements IHoverHelperSupport {

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

	protected AccessibleEditPart createAccessible() {
		return new BPELTrayAccessibleEditPart(this);
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
		IFigure fig = super.createFigure();
		fig.addMouseMotionListener(getMouseMotionListener());
		return fig;
	}
}