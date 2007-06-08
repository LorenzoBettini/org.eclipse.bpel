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
package org.eclipse.bpel.ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.common.extension.model.ExtensionMap;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.emf.ecore.EObject;

/**
 * @author IBM, Original contribution. 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jun 4, 2007
 * 
 */

public class TransferBuffer {

	protected static final boolean DEBUG = false;

	/**
	 * @author IBM, Original contribution.
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Jun 4, 2007
	 *
	 */
	public static class Contents {
		
		Map fExtensionMap;
		List<EObject> fRootObjects;

		Contents(Map extensionMap, List<EObject> rootList ) {
			this.fExtensionMap = extensionMap;
			this.fRootObjects = rootList;
		}
	}

	Contents fContents;

	/**
	 * Return the contents of the transfer buffer.
	 * 
	 * @return contents of the transfer buffer.
	 */

	public Contents getContents() {
		return fContents;
	}

	/**
	 * Set the contents of this transfer buffer.
	 * 
	 * @param aContents
	 *            contents of this transfer buffer.
	 */

	public void setContents(Contents aContents) {
		this.fContents = aContents;
	}

	/**
	 * Get the list of outermost objects from the list of objects passed.
	 * If a sequence and an activity in the sequence are present in aList, then only
	 * the sequence is returned.
	 * 
	 * @param aList
	 * @return the list of outermost objects.
	 */
	
	public List<EObject> getOutermostObjects(List<EObject> aList) {

		ArrayList<EObject> trimmedList = new ArrayList<EObject>(aList.size());

		for (EObject next : aList) {
			boolean skipNext = false;
			for (EObject parent : aList) {
				if (next != parent
						&& ModelHelper.isChildContainedBy(parent, next)) {
					skipNext = true;
					break;
				}
			}

			if (skipNext) {
				continue;
			}

			trimmedList.add(next);
		}
		return trimmedList;
	}

	
	
	
	/**
	 * Copy the passed source objects to the transfer buffer.
	 * 
	 * @param sourceObjects
	 * @param sourceMap
	 */
	
	public void copyObjectsToTransferBuffer(List<EObject> sourceObjects, ExtensionMap sourceMap) {
		if (DEBUG) {
			System.out
					.println("copyObjectsToTransferBuffer(" + sourceObjects.size() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		Map targetMap = new HashMap();
		List<EObject> sourceList = getOutermostObjects (sourceObjects);
		List<EObject> targetList = new ArrayList<EObject>();
		
		// TODO: are there issues here with processing subtrees one-by-one?
		// E.g. references
		// to an object which is copied in a different subtree? (Probably not,
		// for our model)
		
		for (EObject source : sourceList) {
			EObject target = BPELUtil
					.cloneSubtree(source, sourceMap, targetMap).targetRoot;
			targetList.add(target);
		}

		// Remove links which are referenced by root activities and were not
		// copied! Otherwise, when the root activities are pasted the stale
		// references from
		// roots to these links will be pasted too.
		
		for (EObject targetObject : targetList ) {
			if (targetObject instanceof Activity == false) {
				continue;
			}
			
			Activity activity = (Activity) targetObject;
			Sources sources = activity.getSources();
			if (sources != null) {
				for(Object n : sources.getChildren()) {
					Source source = (Source) n;
					if (!targetMap.containsValue(source.getLink())) {
						source.setLink(null);
						sources.getChildren().remove(source);						
					}
				}
				if (sources.getChildren().isEmpty()) {
					activity.setSources(null);
				}
			}

			Targets targets = activity.getTargets();
			if (targets != null) {
				for (Object n : targets.getChildren()) {
					Target target = (Target) n;
					if (!targetMap.containsValue(target.getLink())) {
						target.setLink(null);
						targets.getChildren().remove(target);
					}
				}
				if (targets.getChildren().isEmpty()) {
					activity.setTargets(null);
				}
			}
		}

		setContents(new Contents(targetMap, targetList));
	}

	/**
	 * Copy the transfer buffer to the targetObject.
	 * 
	 * @param targetObject
	 *            the target object to act as an anchor point.
	 * 
	 * @param targetMap
	 * @param bReference treat target as reference.
	 * 
	 * @return the list of new objects added.
	 */

	public List<EObject> copyTransferBuffer(EObject targetObject,
			ExtensionMap targetMap, boolean bReference ) {

		EObject target = targetObject;
		EObject refObj = null;
		IContainer container = BPELUtil.adapt(target, IContainer.class);

		if (container == null || bReference) {
			// check its container
			refObj = targetObject;
			target = targetObject.eContainer();
			container = BPELUtil.adapt(target, IContainer.class);
		}
		
		Process process = ModelHelper.getProcess(targetObject);
		ArrayList<EObject> newObjects = new ArrayList<EObject>();

		for (EObject source : getContents().fRootObjects) {

			BPELUtil.CloneResult cloneResult = BPELUtil.cloneSubtree(source,
					getContents().fExtensionMap, targetMap);
						
			// Resolve name of the source activity to be unique
			if (source instanceof Activity) {
				Activity node = (Activity) cloneResult.targetRoot;								
				String uniqueName = BPELUtil.generateUniqueModelName (process, node.getName(), node );				
				node.setName(BPELUtil.upperCaseFirstLetter (uniqueName) );
				
			} else if (source instanceof Variable) {
				Variable node = (Variable) cloneResult.targetRoot;								
				String uniqueName = BPELUtil.generateUniqueModelName (process, node.getName(), node );
				node.setName(uniqueName);				
			} else if (source instanceof PartnerLink) {
				PartnerLink node = (PartnerLink) cloneResult.targetRoot;								
				String uniqueName = BPELUtil.generateUniqueModelName (process, node.getName(), node );
				node.setName(uniqueName);								
			} else if (source instanceof CorrelationSet ) {
				CorrelationSet node = (CorrelationSet) cloneResult.targetRoot;								
				String uniqueName = BPELUtil.generateUniqueModelName (process, node.getName(), node );
				node.setName(uniqueName);								
			}

			container.addChild(target, cloneResult.targetRoot, refObj);
			newObjects.add(cloneResult.targetRoot);
		}

		return newObjects;
	}

	/**
	 * 
	 * 
	 * @param targetObject
	 *            the target reference object around which the copy-from-buffer
	 *            should be made.
	 * @param bReference - treat the target as a reference, even if it is a container.
	 * @return true of copy of transfer buffer can be made, false otherwise.
	 */

	public boolean canCopyTransferBufferTo (EObject targetObject, boolean bReference  ) {
		
		if (targetObject == null) {
			return false;
		}
		
		EObject target = targetObject;
		EObject refObj = null;
		IContainer container = BPELUtil.adapt(target, IContainer.class);

		if (container == null || bReference ) {
			// check its container
			refObj = targetObject;
			target = targetObject.eContainer();
			container = BPELUtil.adapt(target, IContainer.class);
		}

		if (container != null && fContents != null ) {
			// check each root object's type against the container..
			for (EObject next : fContents.fRootObjects ) {				
				if (container.canAddObject(target, next, refObj) == false) {
					return false;
				}
			}
			return true;
		}

		
		return false;
	}

}
