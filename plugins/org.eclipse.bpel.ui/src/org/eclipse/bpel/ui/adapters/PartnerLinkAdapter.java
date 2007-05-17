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
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.adapters.AbstractAdapter;
import org.eclipse.bpel.model.adapters.IStatefullAdapter;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.editparts.OutlineTreeEditPart;
import org.eclipse.bpel.ui.editparts.PartnerLinkEditPart;
import org.eclipse.bpel.ui.properties.PropertiesLabelProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.swt.graphics.Image;


public class PartnerLinkAdapter extends AbstractAdapter implements INamedElement,
	ILabeledElement, EditPartFactory, IOutlineEditPartFactory, IMarkerHolder,
	ITrayEditPartFactory, IContentProposal, IStatefullAdapter
{
	
	PartnerLink myPartnerLink ( Object obj ) {
		return (PartnerLink) getTarget(obj,PartnerLink.class);
	}
	
	
	/* INamedElement */
	

	public String getName (Object namedElement) {
		return myPartnerLink( namedElement ).getName();		
	}
	
	public void setName(Object namedElement, String name) {
		myPartnerLink(namedElement).setName(name);		
	}
	
	public boolean isNameAffected(Object modelObject, Notification n) {
		return (n.getFeatureID(PartnerLink.class) == BPELPackage.PARTNER_LINK__NAME);
	}
	
	/* ILabeledElement */
	
	public Image getSmallImage(Object object) {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_PARTNER_16);
	}
	
	public Image getLargeImage(Object object) {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_PARTNER_32);
	}
	
	public String getTypeLabel(Object object) {
		return Messages.PartnerLinkAdapter_Partner_1; 
	}	
	
	public String getLabel(Object object) {
		String name = getName(object);
		if (name != null)  {
			return name;
		}
		return getTypeLabel(object);
	}

	/* EditPartFactory */
	
	public EditPart createEditPart(EditPart context, Object model) {
		PartnerLinkEditPart result = new PartnerLinkEditPart();
		result.setLabelProvider(PropertiesLabelProvider.getInstance());
		result.setModel(model);
		return result;
	}

	/* IOutlineEditPartFactory */

	public EditPart createOutlineEditPart(EditPart context, Object model) {
		EditPart result = new OutlineTreeEditPart();
		result.setModel(model);
		return result;
	}
	
	/* ITrayEditPartFactory */
	
	public EditPart createTrayEditPart(EditPart context, Object model) {
		return createEditPart(context, model);
	}

	/* IMarkerHolder */
	
	public IMarker[] getMarkers(Object object) {
		return BPELUtil.getMarkers(object);
	}

	/*
	 * IContentProposal
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getContent()
	 */
	
	public String getContent() {
		return getLabel( getTarget() );		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getCursorPosition()
	 */
	public int getCursorPosition() {
		// TODO Auto-generated method stub
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getDescription()
	 */
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getLabel()
	 */
	public String getLabel() {		
		Object obj = getTarget();
		return Messages.bind(Messages.PartnerLinkAdapter_0, 
				getTypeLabel( obj ) ,
				getName( obj ) );
	}
}
