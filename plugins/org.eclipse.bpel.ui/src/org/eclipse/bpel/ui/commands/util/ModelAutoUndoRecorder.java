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
package org.eclipse.bpel.ui.commands.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.bpel.common.extension.model.Extension;
import org.eclipse.bpel.common.extension.model.ExtensionMap;
import org.eclipse.bpel.common.extension.model.ExtensionmodelPackage;
import org.eclipse.bpel.common.extension.model.impl.ExtensionImpl;
import org.eclipse.bpel.common.extension.model.impl.ExtensionMapImpl;
import org.eclipse.bpel.common.extension.model.notify.ExtensionModelNotification;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.jface.util.Assert;


/**
* Records the exact changes made to the model during each 'user change'.  This
* history information can be used to Undo and Redo user changes automatically.
*/
public class ModelAutoUndoRecorder implements IAutoUndoRecorder  {
	
	protected Set ignoreResources = new HashSet();

	protected boolean VERBOSE_DEBUG = false;
	protected boolean DEBUG = false || VERBOSE_DEBUG;
	
	protected Set listenerRootSet = new HashSet(); 
	
	protected List currentChangeList = null;
	
	/**
	 * IUndoHandler to undo/redo a change to the ExtensionMap (adding, changing or
	 * removing an extension entry).
	 */
	class EMapSingleChangeHandler implements IUndoHandler {
		ExtensionMap extensionMap;
		EObject extendedObject, oldExtension, newExtension;
		public EMapSingleChangeHandler(ExtensionMap extensionMap, EObject extendedObject,
			EObject oldExtension, EObject newExtension)
		{
			this.extensionMap = extensionMap; this.extendedObject = extendedObject;
			this.oldExtension = oldExtension; this.newExtension = newExtension;
		}
		public void undo() {
			if (DEBUG) System.out.println("undo single change"); //$NON-NLS-1$
			if (oldExtension == null) {
				if (extensionMap.containsKey(extendedObject)) {
					extensionMap.remove(extendedObject);
				}
			} else {
				extensionMap.put(extendedObject, oldExtension);
			}
		}
		public void redo() {
			if (DEBUG) System.out.println("redo single change"); //$NON-NLS-1$
			if (newExtension == null) {
				if (extensionMap.containsKey(extendedObject)) {
					extensionMap.remove(extendedObject);
				}
			} else {
				extensionMap.put(extendedObject, newExtension);
			}
		}
	}
	
	/**
	 * IUndoHandler to undo/redo a bulk change to the ExtensionMap (i.e. putAll or clear).
	 */
	class EMapMultiChangeHandler implements IUndoHandler {
		ExtensionMap extensionMap;
		Map oldContents, newContents;
		public EMapMultiChangeHandler(ExtensionMap extensionMap, Map oldContents, Map newContents) {
			this.extensionMap = extensionMap;
			this.oldContents = oldContents;
			this.newContents = newContents;
		}
		public void undo() {
			if (DEBUG) System.out.println("undo multi-change"); //$NON-NLS-1$
			extensionMap.clear();
			if (oldContents != null) extensionMap.putAll(oldContents);
		}
		public void redo() {
			if (DEBUG) System.out.println("redo multi-change"); //$NON-NLS-1$
			extensionMap.clear();
			if (newContents != null) extensionMap.putAll(newContents);
		}
	}
	
	class ModelAutoUndoAdapter extends EContentAdapter {

		protected ModelAutoUndoRecorder getAutoUndoRecorder() { return ModelAutoUndoRecorder.this; }
		
		/**
		 * Handles a containment change by adding and removing the adapter as appropriate.
		 */
		protected void handleContainment(Notification notification) {
			switch (notification.getEventType()) {
			case Notification.SET:
			case Notification.UNSET: {
		        Notifier newValue = (Notifier)notification.getNewValue();
		    	if (newValue != null && !newValue.eAdapters().contains(this)) {
		    		newValue.eAdapters().add(this);
		    	}
		        break;
			}
		    case Notification.ADD: {
		    	Notifier newValue = (Notifier)notification.getNewValue();
		    	if (newValue != null && !newValue.eAdapters().contains(this)) {
	    			newValue.eAdapters().add(this);
		    	}
		        break;
		    }
		    case Notification.ADD_MANY: {
		    	Collection newValues = (Collection)notification.getNewValue();
		    	for (Iterator i = newValues.iterator(); i.hasNext(); ) {
		    		Notifier newValue = (Notifier)i.next();
		    		if (!newValue.eAdapters().contains(this)) {
		    			newValue.eAdapters().add(this);
		    		}
		        }
		    	break;
		    }
		    
		    //if (n.getNotifier() instanceof ResourceSet)  return;
			}
	
		}

		// note: super implementation doesn't handle overlapping targets very well 
		public void setTarget(Notifier target) {
			this.target = target;

		    Collection contents = (target instanceof EObject)?
		    	((EObject)target).eContents() : (target instanceof ResourceSet)?
		        ((ResourceSet)target).getResources() : (target instanceof Resource)?
		        ((Resource)target).getContents() : null;

		    if (contents != null) {
		    	for (Iterator i = contents.iterator(); i.hasNext(); ) {
		    		Notifier notifier = (Notifier)i.next();
		    		if (!notifier.eAdapters().contains(this)) {
	    			notifier.eAdapters().add(this);
		    		}
		    	}
		    }
		}
		
		public void notifyChanged(Notification n) {
			switch (n.getEventType()) {
			case Notification.ADD_MANY:
			case Notification.REMOVE_MANY:
			case Notification.ADD:
			case Notification.REMOVE:
			case Notification.SET:
			case Notification.UNSET:
			case Notification.MOVE:
				if (!ignoreChange(n)) recordChange(n);
			}
			super.notifyChanged(n);
		}
		
	}

	protected ModelAutoUndoAdapter modelAutoUndoAdapter = new ModelAutoUndoAdapter();

	protected boolean ignoreChange(Notification n) {
		Resource res = null;
		if (n.getNotifier() instanceof ResourceSet) {
			// don't record resources being added (usually this is due to demand-loading).
			// TODO: what about resources being deleted?  Is this a problem? 
			return true;
		}
		if (n.getNotifier() instanceof Resource) {
			res = (Resource)n.getNotifier();
		} else if (n.getNotifier() instanceof EObject) {
			res = ((EObject)n.getNotifier()).eResource();
		} else {
			// we won't know how to undo this notification anyways, so ignore it.
			// TODO: this should never occur
			return true;
		}
		if (res != null && ignoreResources.contains(res)) {
			if (VERBOSE_DEBUG) System.out.println("IGNORING -- "+ //$NON-NLS-1$
				(n.isTouch()?"<t>  " : "CHG: ")+BPELUtil.debug(n)); //$NON-NLS-1$ //$NON-NLS-2$
			return true;
		}
		return false;
	}
	
	protected void recordChange(Notification n) {
		if (currentChangeList == null) {
			// ignore.
			//System.out.println("IGNORING!! -- "+ //$NON-NLS-1$
			//		(n.isTouch()?"<t>  " : "CHG: ")+BPELUtil.debug(n)); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		// hackedy-hack hack.
		if (n.getNotifier() instanceof Extension) {
			// ignore (these are an implementation detail of ExtensionMapImpl).
			return;
		}
		if (n.getNotifier() instanceof ExtensionMapImpl) {
			// ignore "real" events concerning the EXTENSION_MAP__EXTENSIONS list.
			// record only "semantic" events (tagged with EXTENSION_MAP__EXTENSIONS_KEY).
			// those represent put() and remove() calls to the extension map.
			if (n.getFeatureID(ExtensionMap.class) == ExtensionmodelPackage.EXTENSION_MAP__EXTENSIONS) {
				if (DEBUG) System.out.println("ignoring impl notification: "+BPELUtil.debug(n)); //$NON-NLS-1$
				return;
			}
			if (n instanceof ExtensionModelNotification) {
				// A semantic notification just for us.  Thanks Sebastian!
				ExtensionModelNotification emn = (ExtensionModelNotification)n;
				ExtensionMap extensionMap = (ExtensionMap)n.getNotifier();
				// handle put()
				if (n.getFeatureID(ExtensionMap.class) == ExtensionModelNotification.EXTENSION_MAP_PUT) {
					EObject object = (EObject)emn.getArg1();
					EObject oldExt = (EObject)emn.getArg2();
					EObject newExt = (EObject)extensionMap.get(object);
					if (DEBUG) System.out.println("record PUT: "+BPELUtil.debugObject(object)+": "+ //$NON-NLS-1$ //$NON-NLS-2$
						BPELUtil.debugObject(oldExt)+" ==> "+BPELUtil.debugObject(newExt)); //$NON-NLS-1$
					currentChangeList.add(new EMapSingleChangeHandler(extensionMap, object, oldExt, newExt));
				} else if (n.getFeatureID(ExtensionMap.class) == ExtensionModelNotification.EXTENSION_MAP_REMOVE) {
					EObject object = (EObject)emn.getArg1();
					EObject oldExt = (EObject)emn.getArg2();
					EObject newExt = null;
					if (DEBUG) System.out.println("record REMOVE: "+BPELUtil.debugObject(object)+": "+ //$NON-NLS-1$ //$NON-NLS-2$
						BPELUtil.debugObject(oldExt)+" ==> "+BPELUtil.debugObject(newExt)); //$NON-NLS-1$
					currentChangeList.add(new EMapSingleChangeHandler(extensionMap, object, oldExt, newExt));
				} else if (n.getFeatureID(ExtensionMap.class) == ExtensionModelNotification.EXTENSION_MAP_PUTALL) {
					Map oldContents = (Map)emn.getArg1();
					Map newContents = new HashMap(extensionMap);
					if (DEBUG) System.out.println("record PUTALL: "+oldContents.size()+" items ==> "+newContents.size()+" items"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					currentChangeList.add(new EMapMultiChangeHandler(extensionMap, oldContents, newContents));
				} else if (n.getFeatureID(ExtensionMap.class) == ExtensionModelNotification.EXTENSION_MAP_CLEAR) {
					Map oldContents = (Map)emn.getArg1();
					Map newContents = Collections.EMPTY_MAP;
					if (DEBUG) System.out.println("record CLEAR: "+oldContents.size()+" items ==> "+newContents.size()+" items"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					currentChangeList.add(new EMapMultiChangeHandler(extensionMap, oldContents, newContents));
				} else {
					if (DEBUG) System.out.println("WARNING: ModelAutoUndoRecorder.recordChange(): unknown event type from ExtensionMapImpl"); //$NON-NLS-1$
				}
				return;
			}
			if (DEBUG) System.out.println("ExtensionMap: "+BPELUtil.debug(n)); //$NON-NLS-1$
		}

		// handle all other notifications.
		if (DEBUG) System.out.println((n.isTouch()?"<t>  " : "CHG: ")+BPELUtil.debug(n)); //$NON-NLS-1$ //$NON-NLS-2$
		currentChangeList.add(n);
	}

	public void startIgnoringResource(Resource resource) {
		ignoreResources.add(resource);
	}
	public void stopIgnoringResource(Resource resource) {
		ignoreResources.remove(resource);
	}
	
	public void startChanges(List modelRoots) {
		if (VERBOSE_DEBUG) System.out.println("startChanges()"); //$NON-NLS-1$
		if (currentChangeList != null) throw new IllegalStateException(); 
		currentChangeList = new ArrayList();
		addModelRoots(modelRoots);
	}
	
	public List finishChanges() {
		if (currentChangeList == null) throw new IllegalStateException(); 
		if (VERBOSE_DEBUG) System.out.println("finishChanges(): "+currentChangeList.size()); //$NON-NLS-1$
		List result = currentChangeList;
		currentChangeList = null;
		clearModelRoots();
		return result;
	}

	protected void addModelRoot(Object root) {
		// TODO: TEMPORARY HACK!!  This is to work around the problems with
		// duplicate/overlapping adapters when we use non-resource roots.
		// I don't know what the real solution is for this problem.
		if (root instanceof EObject) { root = ((EObject)root).eResource(); }
		
		if (root instanceof Notifier) {
			List adapters = ((Notifier)root).eAdapters();
			// careful: only add adapter if it hasn't already been added, or duplicate
			// notifications will be recorded and things will break.
			if (!adapters.contains(modelAutoUndoAdapter)) {
				if (VERBOSE_DEBUG) System.out.println(" >>> Add Root: "+root); //$NON-NLS-1$
				adapters.add(modelAutoUndoAdapter);
				listenerRootSet.add(root);
			} else {
				if (VERBOSE_DEBUG) System.out.println(" >>> Overlapping Root: "+root); //$NON-NLS-1$
			}
		}
	}
	
	public void addModelRoots(List modelRoots) {
		boolean gotExtensionMap = false;
		for (Iterator it = modelRoots.iterator(); it.hasNext(); ) {
			Object root = it.next(); 
			addModelRoot(root);
			// HACK! treat the ExtensionMap as a model root
			if (!gotExtensionMap) {
				BPELEditor bpelEditor = ModelHelper.getBPELEditor(root);
				if (bpelEditor != null) addModelRoot(bpelEditor.getExtensionMap());
				gotExtensionMap = true;
			}
		}
	}
	
	protected void clearModelRoots() {
		if (VERBOSE_DEBUG) System.out.println(" <<< Clear Model Roots"); //$NON-NLS-1$
		for (Iterator it = listenerRootSet.iterator(); it.hasNext(); ) {
			Notifier notifier = (Notifier)it.next();
			// HACK!
			while ((notifier instanceof EObject) && ((EObject)notifier).eContainer().eAdapters().contains(modelAutoUndoAdapter)) {
				notifier = ((EObject)notifier).eContainer();
			}
			notifier.eAdapters().remove(modelAutoUndoAdapter);
		}
		listenerRootSet.clear();
	}
	
	public boolean isRecordingChanges() {
		return (currentChangeList != null);
	}

	public void insertUndoHandler(IUndoHandler undoHandler) {
		if (currentChangeList != null) {
			currentChangeList.add(undoHandler);
		} else {
			if (DEBUG) System.out.println("WARNING: insertUndoHandler() while not recording changes!"); //$NON-NLS-1$
		}
	}

	protected void undoNotification(Notification n) {
		List list;

		// hack to work around side-effect ordering problems!
		if (n.getNotifier() instanceof ExtensionImpl) {
			if (DEBUG) System.out.println("ignore ExtensionImpl change: "+BPELUtil.debug(n)); //$NON-NLS-1$
			return;
		}

		if (DEBUG) System.out.println((n.isTouch()? "<t> " : "undo: ")+BPELUtil.debug(n)); //$NON-NLS-1$ //$NON-NLS-2$
		
		EStructuralFeature feature = (EStructuralFeature)n.getFeature();
		
		if (n.getNotifier() instanceof EObject) {
			EObject obj = (EObject)n.getNotifier();

			switch (n.getEventType()) {
			case Notification.ADD_MANY:
			case Notification.REMOVE_MANY:
				Assert.isTrue(feature.isMany());
				obj.eSet(feature, n.getOldValue());
				break;
			
			case Notification.ADD:
				Assert.isTrue(feature.isMany());
				list = (List)obj.eGet(feature, true);
				try {
					list.remove(n.getPosition());
				} catch (ClassCastException e) {
				    // it shouldn't be happening but there
				    // is a bug in the WSDL model
					if (DEBUG) e.printStackTrace();
				}
				break;
				
			case Notification.REMOVE:
				Assert.isTrue(feature.isMany());
				list = (List)obj.eGet(feature, true);
				if (n.getPosition() == Notification.NO_INDEX) {
					list.add(n.getOldValue());
				} else {
					list.add(n.getPosition(), n.getOldValue());
				}
				break;

			case Notification.SET:
			case Notification.UNSET:
				if (feature.isMany() && n.getPosition() >= 0) {
					list = (List)obj.eGet(feature, true);
					list.set(n.getPosition(), n.getOldValue());
				} else if (n.wasSet()) {
					obj.eSet(feature, n.getOldValue());
				} else {
					obj.eUnset(feature);
				}
				break;
			
			case Notification.MOVE:
				Assert.isTrue(feature.isMany());
				// TODO: does this even work?!
				obj.eSet(feature, n.getOldValue());
				break;
				
			default: throw new IllegalStateException();
			}
			
		} else {
			// TODO: adding and removing things from resources ??
			System.err.println("undoNotification on non-EObject not implemented yet"); //$NON-NLS-1$
			(new Exception()).printStackTrace(System.err);
		}
	}
	
	// TODO: this stuff should be refactored/moved somewhere else.
	protected void redoNotification(Notification n) {
		List list;
		if (DEBUG) System.out.println((n.isTouch()? "<t> " : "redo: ")+BPELUtil.debug(n)); //$NON-NLS-1$ //$NON-NLS-2$
		
		EStructuralFeature feature = (EStructuralFeature)n.getFeature();
		
		
		if (n.getNotifier() instanceof EObject) {
			EObject obj = (EObject)n.getNotifier();

			switch (n.getEventType()) {
			case Notification.ADD_MANY:
			case Notification.REMOVE_MANY:
				Assert.isTrue(feature.isMany());
				obj.eSet(feature, n.getNewValue());
				break;
			
			case Notification.ADD:
				Assert.isTrue(feature.isMany());
				list = (List)obj.eGet(feature, true);
					list.add(n.getPosition(), n.getNewValue());
				break;
				
			case Notification.REMOVE:
				Assert.isTrue(feature.isMany());
				list = (List)obj.eGet(feature, true);
				if (n.getPosition() == Notification.NO_INDEX) {
					list.remove(n.getOldValue());
				} else {
					list.remove(n.getPosition());
				}
				break;

			case Notification.SET:
				if (feature.isMany() && n.getPosition() >= 0) {
					list = (List)obj.eGet(feature, true);
					list.set(n.getPosition(), n.getNewValue());
				} else {
					obj.eSet(feature, n.getNewValue());
				}
				break;
			
			case Notification.UNSET:
				obj.eUnset(feature);
				break;
			
			case Notification.MOVE:
				Assert.isTrue(feature.isMany());
				// TODO: does this even work?!
				obj.eSet(feature, n.getNewValue());
				break;
				
			default: throw new IllegalStateException();
			}
			
		} else {
			// TODO: adding and removing things from resources ??
			System.err.println("redoNotification on non-EObject not implemented yet"); //$NON-NLS-1$
			(new Exception()).printStackTrace(System.err);
		}
	}
	
	public void undo(List changes) {
		if (VERBOSE_DEBUG) System.out.println("UNDOING "+changes.size()+" changes"); //$NON-NLS-1$ //$NON-NLS-2$
		for (int i = changes.size(); --i >= 0; ) {
			Object change = changes.get(i);
			if (change instanceof Notification) {
				try {
					undoNotification((Notification)change);
				} catch (RuntimeException e) {
					// TODO! remove this debugging hack
					BPELUIPlugin.log(e);
				}
			} else if (change instanceof IUndoHandler) {
				((IUndoHandler)change).undo();
			}
		}
	}
	
	public void redo(List changes) {
		if (VERBOSE_DEBUG) System.out.println("REDOING "+changes.size()+" changes"); //$NON-NLS-1$ //$NON-NLS-2$
		for (int i = 0; i<changes.size(); i++) {
			Object change = changes.get(i);
			if (change instanceof Notification) {
				try {
					redoNotification((Notification)change);
				} catch (RuntimeException e) {
					// TODO! remove this debugging hack
					BPELUIPlugin.log(e);
				}
			} else if (change instanceof IUndoHandler) {
				((IUndoHandler)change).redo();
			}
		}
	}
	
	public static IAutoUndoRecorder getFromAdapter(Notifier notifier) {
		for (Iterator it = notifier.eAdapters().iterator(); it.hasNext(); ) {
			Adapter a = (Adapter)it.next();
			if (a instanceof ModelAutoUndoAdapter) {
				return ((ModelAutoUndoAdapter)a).getAutoUndoRecorder();
			}
		}
		return null;
	}
}
