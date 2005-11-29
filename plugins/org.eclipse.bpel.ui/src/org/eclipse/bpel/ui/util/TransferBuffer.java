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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.common.extension.model.ExtensionMap;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.ui.adapters.IContainer;
import org.eclipse.emf.ecore.EObject;


public class TransferBuffer {

	protected static final boolean DEBUG = false;
	
	public static class Contents {
		Map extensionMap;
		List rootObjects;
		Contents(Map extensionMap, List rootObjects) {
			this.extensionMap = extensionMap;
			this.rootObjects = rootObjects;
		}
	}

	Contents contents;
	
	public Contents getContents() { return contents; }
	public void setContents(Contents contents) { this.contents = contents; }


	public List getOutermostObjects(List sourceObjects) {
		List result = new ArrayList();

		// Find all source objects which are not contained (directly or indirectly)
		// inside another source object.
		for (int i = 0; i<sourceObjects.size(); i++) {
			EObject model1 = (EObject)sourceObjects.get(i);
			if (model1 == null)  continue;
			boolean isOutermost = true;
			for (int j = 0; j<sourceObjects.size(); j++) {
				if (i == j) continue;
				EObject model2 = (EObject)sourceObjects.get(j);
				if (model2 != null && ModelHelper.isChildContainedBy(model2, model1)) {
					isOutermost = false; break;
				}
			}
			if (isOutermost) result.add(model1);
		}
		return result;
	}
	
	public void copyObjectsToTransferBuffer(List sourceObjects, ExtensionMap sourceMap) {
		if (DEBUG) System.out.println("copyObjectsToTransferBuffer("+sourceObjects.size()+")");  //$NON-NLS-1$ //$NON-NLS-2$
		Map targetMap = new HashMap();
		List sourceList = getOutermostObjects(sourceObjects);
		List targetList = new ArrayList();
		// TODO: are there issues here with processsing subtrees one-by-one?  E.g. references
		// to an object which is copied in a different subtree?  (Probably not, for our model)
		for (int i = 0; i<sourceList.size(); i++) {
			EObject source = (EObject)sourceList.get(i);
			EObject target = BPELUtil.cloneSubtree(source, sourceMap, targetMap).targetRoot;
			targetList.add(target);
		}
		
		// Remove links which are referenced by root activities and were not
		// copied!  Otherwise, when the root activites are pasted the stale references from
		// roots to these links will be pasted too.
		for (int i = 0; i<targetList.size(); i++) {
			EObject targetObject = (EObject)targetList.get(i);
			if (targetObject instanceof Activity) {
				Activity activity = (Activity)targetObject;
				Sources sources = activity.getSources();
				if (sources != null) {
					for (Iterator it = sources.getChildren().iterator(); it.hasNext(); ) {
						Source source = (Source)it.next();
						if (!targetMap.containsValue(source.getLink())) {
							source.setLink(null);
							it.remove(); 
						}
					}
					if (sources.getChildren().isEmpty()) {
						activity.setSources(null);
					}
				}
				
				Targets targets = activity.getTargets();
				if (targets != null) {
					for (Iterator it = targets.getChildren().iterator(); it.hasNext(); ) {
						Target target = (Target)it.next();
						if (!targetMap.containsValue(target.getLink())) {
							target.setLink(null);
							it.remove(); 
						}
					}
					if (targets.getChildren().isEmpty()) {
						activity.setTargets(null);
					}
				}
			}
		}
		
		setContents(new Contents(targetMap, targetList));
	}
	
	public boolean canCopyTransferBufferToIContainer(Object targetObject) {
		IContainer container = (IContainer)BPELUtil.adapt(targetObject, IContainer.class);
		if (container == null) return false;
		// check each root object's type against the container..
		for (Iterator it = getContents().rootObjects.iterator(); it.hasNext(); ) {
			EObject source = (EObject)it.next();
			// TODO: do we need to be aware of implicit sequences here??
			if (!container.canAddObject(targetObject, source, null)) return false; 
		}
		return true;
	}
	
	public boolean copyTransferBufferToIContainer(EObject targetObject, ExtensionMap targetMap) {
		if (DEBUG) System.out.println("copyTransferBufferToIContainer("+targetObject+")"); //$NON-NLS-1$ //$NON-NLS-2$
		//if (!canCopyTransferBufferToIContainer(targetObject)) throw new IllegalStateException();
		IContainer container = (IContainer)BPELUtil.adapt(targetObject, IContainer.class);
	
		Process process = ModelHelper.getProcess(targetObject);

		for (Iterator it = getContents().rootObjects.iterator(); it.hasNext(); ) {
			EObject source = (EObject)it.next();
			BPELUtil.CloneResult cloneResult = BPELUtil.cloneSubtree(source,
				getContents().extensionMap, targetMap);

			// Resolve name of the source activity to be unique
			if (source instanceof Activity) {
			    Activity activity = (Activity)cloneResult.targetRoot;
			    String uniqueName = BPELUtil.getUniqueModelName(process, activity.getName(), null);
			    activity.setName(uniqueName);
			}
			
			container.addChild(targetObject, cloneResult.targetRoot, null);
		}
		
		// TODO: return the root objects pasted (i.e. set of targetObjects), so that the
		// Paste command can select them!
		
		return true;
	}	
}
