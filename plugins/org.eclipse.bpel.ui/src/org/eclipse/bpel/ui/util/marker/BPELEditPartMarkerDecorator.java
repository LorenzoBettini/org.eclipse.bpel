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
package org.eclipse.bpel.ui.util.marker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.common.ui.decorator.EditPartMarkerDecorator;
import org.eclipse.bpel.common.ui.decorator.IMarkerConstants;
import org.eclipse.bpel.common.ui.markers.IModelMarkerConstants;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.editparts.util.BPELDecorationLayout;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.Layer;
import org.eclipse.emf.ecore.EObject;


public class BPELEditPartMarkerDecorator extends EditPartMarkerDecorator {
	
	protected List listeners = new ArrayList();
	
	public interface MarkerMotionListener {
		public void markerEntered(IMarker marker);
	}
	
	public BPELEditPartMarkerDecorator(EObject modelObject, BPELDecorationLayout decorationLayout) {
		super(modelObject, null, decorationLayout);
		decorationLayout.addAnchorMotionListener(new BPELDecorationLayout.AnchorMotionListener() {
			public void anchorEntered(int position) {
				// Look up the marker for the anchor and fire the listeners
				IMarker[] markers = getMarkers();
				for (int i = 0; i < markers.length; i++) {
					IMarker marker = markers[i];
					Object constraint = getConstraint(marker);
					if (constraint instanceof Integer) {
						int value = ((Integer)constraint).intValue();
						if (value == position) {
							fireMarkerMotionListeners(marker);
							return;
						}
					}
				}
			}
		});
	}
	
	public void addMarkerMotionListener(MarkerMotionListener listener) {
		listeners.add(listener);
	}
	
	public void removeMarkerMotionListener(MarkerMotionListener listener) {
		listeners.remove(listener);
	}
	
	private void fireMarkerMotionListeners(IMarker marker) {
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			MarkerMotionListener listener = (MarkerMotionListener)it.next();
			listener.markerEntered(marker);
		}
	}
	
	public Layer getDecorationLayer() {
		if(decorationLayer == null) {
			decorationLayer = new Layer();
		}
		return decorationLayer;
	}

	protected boolean isAcceptable(IMarker marker) {
		boolean isVisible = marker.getAttribute(IModelMarkerConstants.DECORATION_MARKER_VISIBLE_ATTR, true);
		if (!isVisible) return false;
		// If the marker isn't for this decorator, skip it.
		String anchorString = marker.getAttribute(IModelMarkerConstants.DECORATION_GRAPHICAL_MARKER_ANCHOR_POINT_ATTR, ""); //$NON-NLS-1$
		if (anchorString.equals(IBPELUIConstants.MARKER_ANCHORPOINT_DRAWER_BOTTOM)
				|| anchorString.equals(IBPELUIConstants.MARKER_ANCHORPOINT_DRAWER_TOP)) {
			return false;
		}
		return super.isAcceptable(marker);
	}
	
	protected Object getConstraint(IMarker marker) {
		try {
			// problem markers are always placed in the top left
			if (marker.isSubtypeOf(IMarker.PROBLEM)) {
				return IMarkerConstants.BOTTOM_LEFT;
			}
		} catch (CoreException e) {
			BPELUIPlugin.log(e);
		}
		return super.getConstraint(marker);
	}
	
	protected Object convertAnchorKeyToConstraint(String key) {
		if (key.equals(IBPELUIConstants.MARKER_ANCHORPOINT_DRAWER_TOP)) return new Integer(64);
		if (key.equals(IBPELUIConstants.MARKER_ANCHORPOINT_DRAWER_BOTTOM)) return new Integer(128);
		return super.convertAnchorKeyToConstraint(key);
	}
}