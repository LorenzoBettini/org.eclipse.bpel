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
package org.eclipse.bpel.common.ui.decorator;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.common.ui.layouts.AlignedFlowLayout;
import org.eclipse.bpel.common.ui.layouts.FillParentLayout;
import org.eclipse.bpel.common.ui.markers.IModelMarkerConstants;
import org.eclipse.bpel.common.ui.markers.ModelMarkerUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.AbstractLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;


/**
 * A class to encapsulate marker support for an <code>EditPart</code>. 
 * The <code>EditPart</code> should hold an instance of this class in a field.
 * It makes use of the EMF markers support.
 * 
 * <p> 
 * In its <code>refreshVisuals()</code> method, the <code>EditPart</code> should call <code>refresh().</code>
 * 
 * In its <code>createFigure()</code> method, the <code>EditPart</code> should call the 
 * <code>createFigure(IFigure)</code> of this class to decorate its figure.
 * </p>
 */

public class EditPartMarkerDecorator {

	private EObject modelObject;
	
	// Multiple model objects that use this decorator to display markers
	private List modelObjects = new ArrayList();
	
	private boolean fillParent = false;
	
    public void setFillParent(boolean fillParent) {
        this.fillParent = fillParent;
    }
	// A Layer to contain the Marker Images.
	protected Layer decorationLayer;
	// The layout for the decorationLayer
	private AbstractLayout decorationLayout;
	
	//The layout for the layer that contains the figure to be decorated.
	private AbstractLayout figureLayout;
	
	// by default, the children are bounded to their natural size, otherwise, the size of the children are not
	// touched and somebody else is responsible for sizing them
	protected boolean resizeChildren = true; 

	private Object defaultConstraint = IMarkerConstants.CENTER;

	public EditPartMarkerDecorator(EObject modelObject) {
		this(modelObject, null, null);
	}
	
	public EditPartMarkerDecorator(List modelObjects) {
		this(modelObjects, null, null);
	}
	
	public EditPartMarkerDecorator(EObject modelObject, AbstractLayout figureLayout) {
		this(modelObject, figureLayout, null);
	}
	
	public EditPartMarkerDecorator(List modelObjects, AbstractLayout figureLayout) {
		this(modelObjects, figureLayout, null);
	}
	
	public EditPartMarkerDecorator(EObject modelObject, AbstractLayout figureLayout, AbstractLayout decorationLayout) {
		this.modelObject = modelObject;
		setFigureLayout(figureLayout);
		if (decorationLayout == null) {
			decorationLayout = new DecorationLayout();
		}
		setDecorationLayout(decorationLayout);
	}
	
	public EditPartMarkerDecorator(List modelObjects, AbstractLayout figureLayout, AbstractLayout decorationLayout) {
		this((EObject)null, figureLayout, decorationLayout);
		this.modelObjects = modelObjects;
	}

	/**
	 * Draws the markers. This method should be called from the EditPart's refreshVisuals()
	 * method.
	 */
	protected void refreshMarkers() {
		//	Refresh any decorations on this edit part
		 if(decorationLayer != null) {	
			 IMarker[] markers = getMarkers();
			 for (int i = 0; i < markers.length; i++) {			 
			 	IMarker marker = markers[i];
				Object constraint = getConstraint(markers[i]);
			 	IFigure markerFigure = createFigureForMarker(marker);
			 	if (markerFigure != null) {
			 		decorationLayer.add(markerFigure, constraint);
			 	}
			 }
		 }
	}
	
	/**
	 * Draws the markers. This method should be called from the EditPart's refreshVisuals() method.
	 */
	public void refresh(){
		 if(decorationLayer != null) {
		 	 decorationLayer.removeAll();
		 }
		 refreshMarkers();
	}
	
	/** 
	 * Get the image to be drawn for the marker's figure.  This is obtained 
	 * from an IModelMarkerContentProvider that must be implemented by the client. 
	 * If we can't find an image using the content provider we check to see if the
	 * marker is a problem marker and get the correct icon for it.
	 * 
	 * May be overriden by subclasses to change the image.
	 * 
	 * @param marker 
	 * @return an image representing the marker or null if none is available
	 */
	protected Image getImage(IMarker marker) {
		return ModelMarkerUtil.getImage(marker);
	}
	
	protected IMarker[] getMarkers() {
		return (IMarker[]) getMarkerMap().values().toArray(new IMarker[getMarkerMap().values().size()]);
	}

	/**
	 * The EditPart's createFigure() method should call this method in order to decorate
	 * it's figure.
	 * 
	 * @param figure The figure to be decorated
	 */
	public IFigure createFigure(IFigure figure) {
		LayeredPane pane = new LayeredPane();
		Layer layer = new Layer();
		if (figureLayout == null) {
		    if (fillParent) {
		        figureLayout = new FillParentLayout();
		    } else {
				figureLayout = new AlignedFlowLayout() {
					protected void setBoundsOfChild(IFigure parent,	IFigure child,	Rectangle bounds) {
						parent.getClientArea(Rectangle.SINGLETON);
						bounds.translate(Rectangle.SINGLETON.x, Rectangle.SINGLETON.y);
						if (resizeChildren)
							child.setBounds(bounds);
						else 
							child.setLocation(bounds.getLocation());
					}
				};
		    }
		}
		layer.setLayoutManager(figureLayout);
		pane.add(layer);
		layer.add(figure);
		if (decorationLayer == null) {
			decorationLayer = new Layer();
		}
		decorationLayer.setLayoutManager(decorationLayout);
		
		pane.add(decorationLayer);
		
		return pane;
	}

	public void setDecorationLayout(AbstractLayout layout) {
		decorationLayout = layout;
		if(decorationLayer != null) {
			decorationLayer.setLayoutManager(decorationLayout);
		}
	}

	public void setResizeChildren(boolean resizeChildren) {
		this.resizeChildren = resizeChildren;
	}

	public void setFigureLayout(AbstractLayout layout) {
		figureLayout = layout;
	}

	/**
	 * @return Returns the modelObject.
	 */
	public EObject getModelObject() {
		return modelObject;
	}
	
	public List getModelObjects() {
		return modelObjects;
	}

	/**
	 * Returns a map where the keys are layout constraints and the values are the
	 * IMarkers that should be displayed for the corresponding constraint.
	 * 
	 * May be overriden by subclasses.
	 * 
	 * @return Map
	 */
	protected Map getMarkerMap() {
		return Collections.EMPTY_MAP;
	}
	
	/**
	 * Returns the priority of the given marker
	 * @param marker
	 * @return
	 */
	protected int getPriority(IMarker marker) {
		Integer priority = null;
		// first see if we have a priority attribute
		try {
			priority = (Integer)marker.getAttribute(IModelMarkerConstants.DECORATION_MARKER_PRIORITY_ATTR);
		} catch	(CoreException e) {
			// do nothing
		} catch (ClassCastException e) {
			// do nothing
		}
		if (priority != null)
			return priority.intValue();

		// now see if is a problem marker 
		try {
			if (marker.isSubtypeOf(IMarker.PROBLEM)) {
				int severity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
				if (severity == IMarker.SEVERITY_ERROR)
					return IMarkerConstants.PRIORITY_ERROR_INDICATOR;
				if (severity == IMarker.SEVERITY_WARNING)
					return IMarkerConstants.PRIORITY_WARNING_INDICATOR;
				if (severity == IMarker.SEVERITY_INFO)
					return IMarkerConstants.PRIORITY_INFO_INDICATOR;
			}
		} catch (CoreException e) {
			// do nothing
		}
		
		// return the default priority
		return IMarkerConstants.PRIORITY_DEFAULT;
	}

	/**
	 * Default behavior.  May be overriden by subclasses.
	 * 
	 * @param marker 
	 * @return a layout constraint
	 */
	protected Object getConstraint(IMarker marker) {
		try {
			if (marker.isSubtypeOf(IModelMarkerConstants.DECORATION_GRAPHICAL_MARKER_ID)) {
				String key = marker.getAttribute(IModelMarkerConstants.DECORATION_GRAPHICAL_MARKER_ANCHOR_POINT_ATTR, ""); //$NON-NLS-1$
				Object constraint = convertAnchorKeyToConstraint(key);
				if (constraint != null) return constraint;
			}
		} catch (CoreException e) {
        	// Just ignore exceptions getting marker info.
        	// It is possible that the marker no longer exists.
        	// Eventually the UI will be notified that the
        	// marker is removed and it will update.
		}
		return defaultConstraint;
	}

	protected Object convertAnchorKeyToConstraint(String key) {
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_TOP_CENTRE)) return IMarkerConstants.TOP;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_BOTTOM_CENTRE)) return IMarkerConstants.BOTTOM;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_LEFT)) return IMarkerConstants.LEFT;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_RIGHT)) return IMarkerConstants.RIGHT;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_CENTRE)) return IMarkerConstants.CENTER;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_TOP_LEFT)) return IMarkerConstants.TOP_LEFT;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_TOP_RIGHT)) return IMarkerConstants.TOP_RIGHT;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_BOTTOM_LEFT)) return IMarkerConstants.BOTTOM_LEFT;
		if (key.equals(IMarkerConstants.MARKER_ANCHORPOINT_BOTTOM_RIGHT)) return IMarkerConstants.BOTTOM_RIGHT;
		return null;
	}
	
	public Object getDefaultConstraint() {
		return defaultConstraint;
	}

	public void setDefaultConstraint(Object defaultConstraint) {
		this.defaultConstraint = defaultConstraint;
	}

	/**
	 * Creates a figure for the given marker.
	 * 
	 * May be overriden by subclasses to change the figure created.
	 * @param marker
	 * @return
	 */
	protected IFigure createFigureForMarker(IMarker marker) {
		Image image = getImage(marker);
		String text = getText(marker);
		if (image != null) {
			ImageFigure imageFigure = new ImageFigure(image);
			if (text != null) {
				imageFigure.setToolTip(new Label(text));
			}
			return imageFigure;
		}
		return null;
	}
	
	/** 
	 * Get the tooltip text for the marker's figure.  This is obtained 
	 * from an IModelMarkerContentProvider that must be implemented by the client. 
	 * If we can't get the text using a content provider we check to see if the
	 * marker is a problem marker and get the correct text for it.
	 * 
	 * May be overriden by subclasses to change the tooltip text.
	 * 
	 * @param  marker for which to retrieve the tolltip text
	 * @return a String of text to display as a tooltip for the marker
	 */
	protected String getText(IMarker marker) {
		return ModelMarkerUtil.getText(marker);
	}

	/**
	 * Determines whether the marker is acceptable and should be shown
	 * for this edit part.
	 *
	 * @param marker the marker
	 * @return <code>true</code> if the marker is acceptable
	 */
	protected boolean isAcceptable(IMarker marker) {
		return true;
	}
}