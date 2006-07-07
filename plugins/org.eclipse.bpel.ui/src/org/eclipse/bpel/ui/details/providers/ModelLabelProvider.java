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
package org.eclipse.bpel.ui.details.providers;

import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;


/**
 * A label provider for objects which adapt to the ILabeledElement interface.
 * This can be used as a "generic" label provider for details pages, etc.
 */
public class ModelLabelProvider implements ILabelProvider {

	public static final Object[] EMPTY_ARRAY = new Object[0];

	private Object context = null;
	
	/* ILabelProvider */
	public ModelLabelProvider () {
		
	}
	
	public ModelLabelProvider (Object obj) {
		context = obj;
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO: hook model listener?
	}

	public Image getImage(Object object) {
		
		if (object == null) {
			return null;
		}
		ILabeledElement label = (ILabeledElement)BPELUtil.adapt(object, ILabeledElement.class, context);
		if (label == null) {
			return null;
		}
		
		Image image = label.getSmallImage(object);
		
		// TODO: The adapters must provide images with a lifecycle outlasting any uses,
		// since there is no protocol for destroying the images returned by adapters.
		// If we add methods for releasing the images, call them here.
		//if (image != null)  stringToImage.put(image.toString(), image);	
		return image;
	}

	public String getText(Object object) {
		if (object == null) {
			return Messages.ModelLabelProvider____None____1; 
		}
		ILabeledElement label = (ILabeledElement)BPELUtil.adapt(object, ILabeledElement.class, context );
		if (label == null)  {
			return "<???>"; //$NON-NLS-1$
		}		
		return label.getLabel(object);
	}

	/* IBaseLabelProvider */

	public void dispose() {
		// Dispose images in hashmap- not registry
		//System.out.println("ModelLabelProvider dispose!");
//		if (stringToImage != null) {
//			for (Iterator it = stringToImage.values().iterator(); it.hasNext(); ) {
//				Image image = (Image)it.next();
//				image.dispose();
//			}
//		}
//		stringToImage = null;
	}

	public boolean isLabelProperty(Object element, String property) {
		return true;
	}
	public void addListener(ILabelProviderListener listener) {
		// do nothing
	}
	public void removeListener(ILabelProviderListener listener) {
		// do nothing
	}
}
