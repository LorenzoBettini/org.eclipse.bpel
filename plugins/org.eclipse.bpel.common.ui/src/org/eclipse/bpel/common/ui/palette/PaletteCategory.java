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
package org.eclipse.bpel.common.ui.palette;

import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Model object for a palette category.
 */
public class PaletteCategory extends PaletteDrawer {

	protected ToolEntry defaultTool;

	public PaletteCategory(String label) {
		this(label, null);
	}

	public PaletteCategory(String label, ImageDescriptor icon) {
		super(label, icon);
	}

	public ToolEntry getDefaultTool() {
		if (defaultTool != null) {
			return defaultTool;
		}
		if (getChildren().size() > 0) {
			return (ToolEntry) getChildren().get(0);
		}
		return null;
	}

	public void setDefaultTool(ToolEntry entry) {
		defaultTool = entry;
	}

}
