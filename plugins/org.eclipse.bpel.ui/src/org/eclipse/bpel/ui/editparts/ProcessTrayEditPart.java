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
package org.eclipse.bpel.ui.editparts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.common.ui.tray.MainTrayEditPart;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.editparts.policies.BPELDirectEditPolicy;
import org.eclipse.bpel.ui.uiextensionmodel.ReferencePartnerLinks;
import org.eclipse.bpel.ui.uiextensionmodel.UiextensionmodelFactory;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;


public class ProcessTrayEditPart extends MainTrayEditPart {

	protected ISelectionChangedListener selectionListener;
	protected Object lastSelection = null;
	protected ReferencePartnerLinks referencePartners;

	protected void createEditPolicies() {
		super.createEditPolicies();
		// The DIRECT_EDIT_ROLE policy determines how in-place editing takes place.
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new BPELDirectEditPolicy());
	}
	
	protected List getModelChildren() {
		
		//Process process = getProcess();
		List list = new ArrayList();

		PartnerLinks links = getPartnerLinks();
		if (links != null) {			
			list.add(links);
			referencePartners = UiextensionmodelFactory.eINSTANCE.createReferencePartnerLinks();
			referencePartners.setPartnerLinks(links);
			list.add(referencePartners);
		}

		Variables variables = getVariables();
		if (variables != null) {
			list.add(variables);
		}
		
		CorrelationSets sets = getCorrelationSets();
		if (sets != null) {
			list.add(sets);
		}

		return list;
	}
	
	/**
	 * We show scoped partnerLinks if a Scope is the current selection,
	 * otherwise we show the process partnerLinks.
	 */
	protected PartnerLinks getPartnerLinks() {		
		if (lastSelection instanceof Scope) {
			return ((Scope)lastSelection).getPartnerLinks();
		} 
		return getProcess().getPartnerLinks();
	}

	/**
	 * We show scoped variables if a Scope is the current selection,
	 * otherwise we show the process variables.
	 */
	protected Variables getVariables() {		
		if (lastSelection instanceof Scope) {
			return ((Scope)lastSelection).getVariables();
		} 
		return getProcess().getVariables();
	}
	
	/**
	 * We show scoped correlationSets if a Scope is the current selection,
	 * otherwise we show the process correlationSets.
	 */
	protected CorrelationSets getCorrelationSets() {		
		if (lastSelection instanceof Scope) {
			return ((Scope)lastSelection).getCorrelationSets();
		} 
		return getProcess().getCorrelationSets();
	}
	
	protected Process getProcess() {
		return (Process)getModel();
	}
	
	public void activate() {
		super.activate();
		BPELEditor editor = ModelHelper.getBPELEditor(getProcess());
		editor.getGraphicalViewer().addSelectionChangedListener(getSelectionChangedListener());		
	}
	
	public void deactivate() {
		super.deactivate();
		// There is a chance that by the time we deactivate, we can't find the editor anymore.
		// This is okay because there is a hack in BPELEditor.modelReloaded that manually
		// removes this selection change listener from the graphical viewer.
		try {
			BPELEditor editor = ModelHelper.getBPELEditor(getProcess());
			editor.getGraphicalViewer().removeSelectionChangedListener(getSelectionChangedListener());
		} catch (Exception e) {	
		}
	}

	/**
	 * Selection listeners that tracks the canvas selection and causes the variables
	 * to refresh accordingly.
	 */
	public ISelectionChangedListener getSelectionChangedListener() {
		if (selectionListener == null) {
			selectionListener = new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					Object currentSelection = getModelObjectFromSelection(event.getSelection());
					if (shouldRefresh(currentSelection)) {
						lastSelection = currentSelection;
						refreshChildren();
					}
				}
				protected boolean shouldRefresh(Object currentSelection) {
					return (lastSelection != currentSelection && (lastSelection instanceof Scope || currentSelection instanceof Scope));
				}
			};
		}
		return selectionListener;
	}

	protected AccessibleEditPart createAccessible() {
		return new BPELTrayAccessibleEditPart(this);
	}
}
