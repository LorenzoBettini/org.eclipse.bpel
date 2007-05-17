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
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.adapters.AbstractAdapter;
import org.eclipse.bpel.model.adapters.IStatefullAdapter;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.editparts.OutlineTreeEditPart;
import org.eclipse.bpel.ui.editparts.VariableEditPart;
import org.eclipse.bpel.ui.properties.PropertiesLabelProvider;
import org.eclipse.bpel.ui.uiextensionmodel.UiextensionmodelFactory;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.swt.graphics.Image;


public class VariableAdapter extends AbstractAdapter implements INamedElement, ILabeledElement,
	EditPartFactory, IOutlineEditPartFactory, IMarkerHolder, 
	ITrayEditPartFactory, IExtensionFactory, IContentProposal,
	IStatefullAdapter
{

	Variable myVariable ( Object obj ) {
		return (Variable) getTarget(obj, Variable.class) ;
	}
	/* INamedElement */
	
	public String getName(Object modelObject) {
		return myVariable(modelObject).getName();
	}
	
	public void setName(Object modelObject, String name) {
		myVariable(modelObject).setName(name);
	}
	
	public boolean isNameAffected(Object modelObject, Notification n) {
		return (n.getFeatureID(Variable.class) == BPELPackage.VARIABLE__NAME);
	}

	/* ILabeledElement */
	
	public Image getSmallImage(Object object) {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_VARIABLE_16);
	}
	
	public Image getLargeImage(Object object) {
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_VARIABLE_32);
	}
	
	public String getTypeLabel(Object object) {
		return Messages.BPELVariableAdapter_Variable_1; 
	}	
	
	public String getLabel(Object object) {
		String name = getName(object);
		if (name != null) {
			return name;
		}
		return getTypeLabel(object);
	}
	
	/* EditPartFactory */
	
	public EditPart createEditPart(EditPart context, Object model) {
		VariableEditPart result = new VariableEditPart();
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

	/* IExtensionFactory */
	
	public EObject createExtension(EObject object) {
		return UiextensionmodelFactory.eINSTANCE.createVariableExtension();
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getContent()
	 */
	public String getContent() {
		return getLabel ( getTarget() );		
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
		// TODO: When we have a DOM representation, then description could be returned
		// Variable v = (Variable) getTarget ( null, Variable.class );
		// 
		// Element elm = v.getDocumentationElement();
		// return (elm != null ? elm.getNodeValue() : null);
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getLabel()
	 */
	public String getLabel() {
		return Messages.bind(Messages.VariableAdapter_0, 
				getLabel( getTarget() ),
				getLabel ( getTarget() ) );
	}
		
}
