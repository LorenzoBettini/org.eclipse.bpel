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
package org.eclipse.bpel.ui.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.adapters.IMarkerHolder;
import org.eclipse.bpel.ui.commands.util.AutoUndoCommand;
import org.eclipse.bpel.ui.commands.util.ModelAutoUndoRecorder;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.FlowLinkUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.osgi.util.NLS;

import org.eclipse.wst.wsdl.Definition;

/**
 * Deletes a model object from an IContainer, and removes any references to it from other
 * model objects.
 * 
 * This is more or less the opposite of InsertInContainerCommand.
 * 
 * TODO: simplify this command now that it's an AutoUndoCommand!
 */
public class DeleteChildCommand extends AutoUndoCommand {

	private EObject child;
	private EObject parent;
	
	IContainer container;
	
	Resource[] resourcesToModify = null;
	
	CompoundCommand deleteLinksCmd;
	CompoundCommand deletePLTsCmd;
	
	ModelAutoUndoRecorder modelAutoUndoRecorder;

	// TODO: hack: necessary for multi-delete to work properly
	protected ModelAutoUndoRecorder getRecorder() {
		if (modelAutoUndoRecorder != null) return modelAutoUndoRecorder;
		return super.getRecorder();
	}
	
	public DeleteChildCommand(EObject child) {
		super(new ArrayList(2));

		parent = BPELUtil.getIContainerParent(child);
		
		if (parent instanceof FaultHandler || parent instanceof EventHandler) {
			// If the child of the FH/EH is the *only* child, then we want to delete the
			// entire FH/EH, since it has no meaningful attributes of its own.
			IContainer container = (IContainer)BPELUtil.adapt(parent, IContainer.class);
			List children = container.getChildren(parent);
			if (children.size() == 1 && children.contains(child)) {
				// delete the FH instead
				child = parent;
				parent = BPELUtil.getIContainerParent(child);
			}
		}
		this.child = child;

		if (parent != null) {
			BPELEditor bpelEditor = ModelHelper.getBPELEditor(parent);
			this.modelAutoUndoRecorder = bpelEditor.getModelAutoUndoRecorder();
			addModelRoot(parent);
		}
		
		container = (IContainer)BPELUtil.adapt(parent, IContainer.class);
		
		String childType = null;

		ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(child, ILabeledElement.class);
		if (labeledElement != null)  childType = labeledElement.getTypeLabel(child);
		if (childType == null)  childType = Messages.DeleteChildCommand_Item_1; 
		setLabel(NLS.bind(Messages.DeleteChildCommand_Delete_2, (new Object[] { childType }))); 
	}

	public boolean canDoExecute() {
		if ((child==null) || (parent==null) || (container==null))  return false;
		return true;
	}

	// TODO: this is a hack.
	public Resource[] getResources() {
		if (resourcesToModify == null) {
			Process process = ModelHelper.getProcess(parent);
			if (process == null) return EMPTY_RESOURCE_ARRAY;
			BPELEditor bpelEditor = ModelHelper.getBPELEditor(process);
			
			Set resultSet = new HashSet();
			resultSet.add(parent.eResource());
			
			// Figure out which model objects are being deleted.
			HashSet deletingSet = new HashSet();
			ModelHelper.addSubtreeToCollection(child, deletingSet);
		
			// If we are deleting any PartnerLinks which reference PLTs in the Artifacts WSDL
			// file, also delete the referenced PLTs.
			Set partnerLinkTypes = null;
			Definition artifactsDefinition = bpelEditor.getArtifactsDefinition();
	
			for (Iterator it = deletingSet.iterator(); it.hasNext(); ) {
				Object object = it.next();
				if (object instanceof PartnerLink) {
					PartnerLinkType plt = ((PartnerLink)object).getPartnerLinkType();
					if ((plt != null) && (plt.getEnclosingDefinition() == artifactsDefinition)) {
						if (partnerLinkTypes == null) partnerLinkTypes = new HashSet();
						if (partnerLinkTypes.add(plt)) {
							resultSet.add(plt.eResource());
						}
					}
				}
			}
			resourcesToModify = (Resource[])resultSet.toArray(new Resource[resultSet.size()]); 
		}
		return resourcesToModify;
	}
	
	public void doExecute() {
		if (!canExecute())  throw new IllegalStateException();

		Process process = ModelHelper.getProcess(parent);
		if (process == null) return;
		BPELEditor bpelEditor = ModelHelper.getBPELEditor(process);
		
		EObject topModelObject = process;
		
		// Multi-delete safety: if the child does not have a resource, assume it has
		// already been deleted, and do nothing.
		if (child.eResource() == null) return;
		
		// Figure out which model objects are being deleted.
		HashSet deletingSet = new HashSet();
		ModelHelper.addSubtreeToCollection(child, deletingSet);
	
		// If we are deleting any PartnerLinks which reference PLTs in the Artifacts WSDL
		// file, also delete the referenced PLTs.
		Set partnerLinkTypes = null;
		Definition artifactsDefinition = bpelEditor.getArtifactsDefinition();

		for (Iterator it = deletingSet.iterator(); it.hasNext(); ) {
			Object object = it.next();
			if (object instanceof PartnerLink) {
				PartnerLinkType plt = ((PartnerLink)object).getPartnerLinkType();
				if ((plt != null) && (plt.getEnclosingDefinition() == artifactsDefinition)) {
					if (partnerLinkTypes == null) partnerLinkTypes = new HashSet();
					if (partnerLinkTypes.add(plt)) {
						if (deletePLTsCmd == null) deletePLTsCmd = new CompoundCommand();
						deletePLTsCmd.add(new DeletePartnerLinkTypeCommand(plt));
					}
				}
			}
		}

		// TODO: Build the set of "all model objects" and subtract...
		HashSet notDeletingSet = new HashSet();
		ModelHelper.addSubtreeToCollection(topModelObject, notDeletingSet);
		notDeletingSet.removeAll(deletingSet);

		// We also need to find any flow links which involve a deleted object and remove them.
		// This is a hack, but it could be worse..

		// step 1: find all the flows which contain deleted objects
		HashSet flowSet = new HashSet();
		for (Iterator it = deletingSet.iterator(); it.hasNext(); ) {
			Flow [] flws = FlowLinkUtil.getParentFlows((EObject)it.next());
			flowSet.addAll(Arrays.asList(flws));
		}
		// step 2: if any of the flows is being deleted, we can ignore it
		// this is safe because the source, dest and link itself are all children of the flow
		flowSet.removeAll(deletingSet);
				
		// step 3: check each link in each of the remaining flows to see if it involves a
		// deleted object.  Even if both source and target are being deleted, we should still
		// delete the link, since it is a child of the Flow which is not being deleted.
		deleteLinksCmd = new CompoundCommand();
		for (Iterator flowIt = flowSet.iterator(); flowIt.hasNext(); ) {
			Flow flow = (Flow)flowIt.next();
			for (Iterator it = FlowLinkUtil.getFlowLinks(flow).iterator(); it.hasNext(); ) {
				Link link = (Link)it.next();
				if (deletingSet.contains(FlowLinkUtil.getLinkSource(link)) || deletingSet.contains(FlowLinkUtil.getLinkTarget(link))) {
					// NOTE: this is safe even if the link is scheduled for deletion by
					// a GEF DeleteAction, see comment in DeleteLinkCommand.
					DeleteLinkCommand child = new DeleteLinkCommand(link);
					if (child.canExecute())  deleteLinksCmd.add(child);
				}
			}
		}
		
		deleteLinksCmd.execute();
		if (deletePLTsCmd != null) deletePLTsCmd.execute();
		
		// remove any markers associated with the child.
		// TODO: We need an undo/redo story for marker removal.
		IMarkerHolder markerHolder = (IMarkerHolder)BPELUtil.adapt(child, IMarkerHolder.class);
		if (markerHolder != null) {
			IMarker[] markers = markerHolder.getMarkers(child);
			for (int i = 0; i < markers.length; i++) {
				try {
					markers[i].delete();
				} catch (CoreException e) {
					BPELUIPlugin.log(e);
				}
			}
		}
		
		// finally, we can remove the child.
		container.removeChild(parent, child);

		// IMPORTANT: since the parent is a not-deleted object and the child will be
		// a deleted object, this step must be done *after* the IContainer removal  ;)
		for (Iterator it = notDeletingSet.iterator(); it.hasNext(); ) {
			BPELUtil.deleteNonContainmentRefs((EObject)it.next(), deletingSet);
		}
	}
}
