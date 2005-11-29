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
package org.eclipse.bpel.common.ui.tray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * The adapting selection provider is used by the BPEL graphical editor
 * so that it can provide its selection in terms of model objects rather
 * than in terms of EditPart objects. This class takes care of converting
 * from model objects to EditParts, and vice versa, when necessary.
 */
public class AdaptingSelectionProvider extends MultiViewerSelectionProvider {

	public AdaptingSelectionProvider(EditPartViewer viewer) {
		super(viewer);
	}
	public AdaptingSelectionProvider() {
		super();
	}
	
	protected ISelection calculateSelection(ISelection selection) {
		List list = new ArrayList();
		Set newSet = new HashSet();
		if (selection != null && !selection.isEmpty() && (selection instanceof IStructuredSelection)) {
			Iterator it = ((IStructuredSelection)selection).iterator();
			while (it.hasNext()) {
				Object o = it.next();
				Object model = null;
				if (o instanceof EditPart) {
					model = ((EditPart)o).getModel();
				} else if (o instanceof EObject) {
					model = (EObject)o;
				}
				if ((model != null) && newSet.add(model))  list.add(model);
			}
		}
		if (list.isEmpty()) return StructuredSelection.EMPTY;
		return new StructuredSelection(list.toArray(new Object[list.size()]));
	}
	
	// Set selection to each of the viewers and make sure we ignore callbacks
	protected void internalSetSelection(ISelection selection) {
		if (selection == null || selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
			return;
		}
		try {
			changingSelection = true;
			
			Iterator viewerIt = viewers.iterator();
			while (viewerIt.hasNext()) {
				List newList = new ArrayList();
				EditPartViewer viewer = (EditPartViewer)viewerIt.next();
				Map registry = viewer.getEditPartRegistry();
				Iterator it = ((IStructuredSelection)selection).iterator();
				while (it.hasNext()) {
					Object o = it.next();
					Object editPart = registry.get(o);
					if (editPart != null) newList.add(editPart);
				}
				viewer.setSelection(new StructuredSelection(newList));
			}	
		} finally {
			changingSelection = false;
		}
	}
	
}