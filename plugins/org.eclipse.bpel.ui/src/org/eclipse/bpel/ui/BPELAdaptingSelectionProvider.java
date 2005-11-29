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
package org.eclipse.bpel.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.common.ui.tray.AdaptingSelectionProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;


/**
 * A version of AdaptingSelectionProvider with extra support for also using EditPart-based
 * selections, which are needed by some of our actions.  (Basically some code copied from
 * MultiViewerSelectionProvider!)
 */
public class BPELAdaptingSelectionProvider extends AdaptingSelectionProvider {

	protected ISelection cachedEditPartSelection;
	
	public BPELAdaptingSelectionProvider(EditPartViewer viewer) {
		super(viewer);
	}
	public BPELAdaptingSelectionProvider() {
		super();
	}

	public void setSelection(ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) return;
		cachedSelection = calculateSelection(selection);
		internalSetSelection(cachedSelection);
		cachedEditPartSelection = calculateEditPartSelection();
		fireSelectionChanged(this, cachedSelection);
	}
	
	protected ISelection calculateEditPartSelection() {
		List result = new ArrayList();
		Iterator it = viewers.iterator();
		Set modelObjectSet = new HashSet();
		while (it.hasNext()) {
			List viewerParts = ((EditPartViewer)it.next()).getSelectedEditParts();
			// NOTE: filter out duplicate edit parts, so that we only return
			// one edit part per model object.
			for (Iterator it2 = viewerParts.iterator(); it2.hasNext(); ) {
				EditPart ep = (EditPart)it2.next();
				Object model = ep.getModel();
				if (modelObjectSet.add(model)) result.add(ep);
			}
		}
		if (result.isEmpty()) return StructuredSelection.EMPTY;
		return new StructuredSelection(result);
	}
	
	// This is for the benefit of our actions which require an EditPart-based selection.
	public ISelection getEditPartSelection() {
		if (cachedEditPartSelection == null) {
			cachedEditPartSelection = calculateEditPartSelection();
		}
		return cachedEditPartSelection;
	}
}
