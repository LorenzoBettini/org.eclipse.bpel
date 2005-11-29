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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public class MultiViewerSelectionProvider implements ISelectionProvider {

	protected List viewers = new ArrayList();
	protected List listeners;
	protected boolean changingSelection = false;
	protected boolean broadcastingSelectionChange = false;
	protected ISelection cachedSelection;
	
	public MultiViewerSelectionProvider(EditPartViewer viewer) {
		this();
		addViewer(viewer);
	}
	public MultiViewerSelectionProvider() {
		this.listeners = new ArrayList();
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	public void addViewer(EditPartViewer viewer) {
		viewers.add(viewer);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (changingSelection) return;
				setSelection(event.getSelection());
			}
		});
	}
	
	public ISelection getSelection() {
		if (cachedSelection == null) {
			List result = new ArrayList();
			Iterator it = viewers.iterator();
			while (it.hasNext()) {
				List viewerParts = ((EditPartViewer)it.next()).getSelectedEditParts();
				result.addAll(viewerParts);
			}
			cachedSelection = calculateSelection(new StructuredSelection(result));
		}
		return cachedSelection;
	}

	public void setSelection(ISelection selection) {
		if (!(selection instanceof IStructuredSelection)) return;
		cachedSelection = calculateSelection(selection);
		internalSetSelection(cachedSelection);
		fireSelectionChanged(this, cachedSelection);
	}
	
	protected void fireSelectionChanged(ISelectionProvider provider, ISelection selection) {
		broadcastingSelectionChange = true;
		try {
			Object listenersCopy[] = this.listeners.toArray();
			SelectionChangedEvent event = new SelectionChangedEvent(provider, selection);
			for (int i = 0; i < listenersCopy.length; i++) {
				((ISelectionChangedListener)listenersCopy[i]).selectionChanged(event);
			}
		} finally {
			broadcastingSelectionChange = false;
		}
	}
	
	protected ISelection calculateSelection(ISelection baseSelection) {
		List result = new ArrayList();
		for (Iterator viewerIt = viewers.iterator(); viewerIt.hasNext(); ) {
			EditPartViewer viewer = (EditPartViewer)viewerIt.next();
			Map registry = viewer.getEditPartRegistry();
			Iterator it = ((IStructuredSelection)baseSelection).iterator();
			while (it.hasNext()) {
				EditPart part = (EditPart)it.next();
				Object model = part.getModel();
				EditPart viewerEditPart = (EditPart)registry.get(model);
				if (viewerEditPart != null) result.add(viewerEditPart);
			}
		}
		if (result.isEmpty()) return StructuredSelection.EMPTY;
		return new StructuredSelection(result);
	}
	
	// TODO: try getting rid of the isEmpty() check here and in the same
	// place in AdaptingSelectionProvider.
	
	// Set selection to each of the viewers and make sure we ignore callbacks
	protected void internalSetSelection(ISelection selection) {
		if (selection == null || selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
			return;
		}
		try {
			changingSelection = true;
			Iterator viewerIt = viewers.iterator();
			while (viewerIt.hasNext()) {
				EditPartViewer viewer = (EditPartViewer)viewerIt.next();
				Map registry = viewer.getEditPartRegistry();
				List newList = new ArrayList();
				Set newSet = new HashSet();
				Iterator it = ((IStructuredSelection)selection).iterator();
				while (it.hasNext()) {
					EditPart part = (EditPart)it.next();
					Object model = part.getModel();
					EditPart viewerEditPart = (EditPart)registry.get(model);
					if (viewerEditPart != null && newSet.add(viewerEditPart)) newList.add(viewerEditPart);
				}
				viewer.setSelection(new StructuredSelection(newList));
			}
		} finally {
			changingSelection = false;
		}
	}
	
	public boolean isBroadcastingSelectionChange() {
		return broadcastingSelectionChange;
	}
}
