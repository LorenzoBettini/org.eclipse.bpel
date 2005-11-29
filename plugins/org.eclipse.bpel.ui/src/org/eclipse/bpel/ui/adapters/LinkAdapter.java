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
package org.eclipse.bpel.ui.adapters;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.editparts.LinkEditPart;
import org.eclipse.bpel.ui.uiextensionmodel.UiextensionmodelFactory;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.swt.graphics.Image;


public class LinkAdapter extends AbstractAdapter implements INamedElement,
	EditPartFactory, ILabeledElement, IMarkerHolder, IExtensionFactory
{

	/* INamedElement */
	
	public String getName(Object modelObject) {
		return ((Link)modelObject).getName();
	}

	public void setName(Object modelObject, String name) {
		((Link)modelObject).setName(name);
	}

	public boolean isNameAffected(Object modelObject, Notification n) {
		return (n.getFeatureID(Link.class) == BPELPackage.LINK__NAME);
	}

	/* EditPartFactory */
	
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart result = new LinkEditPart();
		result.setModel(model);
		return result;
	}

	/* ILabeledElement */

	public String getLabel(Object object) {
		String name = getName(object);
		if (name != null) return name;
		return getTypeLabel(object);
	}
	
	public Image getLargeImage(Object object) {
		return BPELUIPlugin.getPlugin().getImage(
				IBPELUIConstants.ICON_LINK_32);
	}

	public Image getSmallImage(Object object) {
		return BPELUIPlugin.getPlugin().getImage(
				IBPELUIConstants.ICON_LINK_16);
	}

	public String getTypeLabel(Object object) {
		return Messages.LinkAdapter_Link_1; 
	}
	
	/* IMarkerHolder */
	
	public IMarker[] getMarkers(Object object) {
		return BPELUtil.getMarkers(object);
	}
	
	/* IExtensionFactory */
	
	public EObject createExtension(EObject object) {
		return UiextensionmodelFactory.eINSTANCE.createLinkExtension();
	}
}
