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

import org.eclipse.bpel.common.ui.CommonUIPlugin;
import org.eclipse.bpel.common.ui.ICommonUIConstants;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.ResizeHandle;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;


/**
 * Override the normal ResizeHandle to provide a custom handle
 * locator. This is required to move the handles out of the absolute
 * corners and closer to the actual figure outline.
 */
public class InsetResizeHandle extends ResizeHandle {
	protected int direction;
	protected Color selectionColor, selectionCornerColor;
	
	public InsetResizeHandle(GraphicalEditPart owner, int direction, int verticalInset, int horizontalInset) {
		super(owner, direction);
		this.direction = direction;
		setLocator(new InsetRelativeHandleLocator(owner.getContentPane(), direction, verticalInset, horizontalInset));
		selectionColor = Display.getCurrent().getSystemColor(SWT.COLOR_LIST_SELECTION);
		ColorRegistry registry = CommonUIPlugin.getDefault().getColorRegistry();
		selectionCornerColor = registry.get(ICommonUIConstants.COLOR_SELECTION_HANDLE_CORNER);
	}
	
}
