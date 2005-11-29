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
package org.eclipse.bpel.common.ui.editmodel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * EditModel is shared between editor with the same primary file input.
 * It holds on to a command stack, and a resource set.
 * 
 * This class has a reference count cache of EditModels.
 * Once all editors that reference a editModel are closed all resource
 * in the shared resource set are unloaded.
 * 
 * Life-cycle
 * 		call getEditModel using its primary file.
 * 			the shared resource set reference count is incremented;
 * 		call getResourceInfo using any file that need to be loaded
 * 			the shared resource reference count is incremented;
 * 		call releaseReference 
 * 			the shared resource reference count is decremented;
 * 		call release
 * 			if it is the last reference the shared resource set is disposed
 */
public class EditModel {

	protected static EditModelCache cache = new EditModelCache();
	
	protected Map fileToResourceInfo = new HashMap();
	protected ResourceSet resourceSet;
	protected int referenceCount = 0;
	protected IResource primaryFile;
	protected EditModelCommandStack commandStack;

	private List updateListeners = new ArrayList();
	
public static EditModel getEditModel(IResource primaryFile) {
	return cache.getEditModel(primaryFile,new Factory());
}
public static EditModel getEditModel(IResource primaryFile,Factory factory) {
	return cache.getEditModel(primaryFile,factory);
}
/*
 * Private constructor. Use the static factory methods. 
 */
protected EditModel(ResourceSet resourceSet,IResource primaryFile) {
	this.resourceSet = resourceSet;
	this.primaryFile = primaryFile;
	this.updateListeners = new ArrayList(0);
}
public void addListener(IEditModelListener listener) {
	if(updateListeners.contains(listener))
		return;
	updateListeners.add(listener);
}
public void removeListener(IEditModelListener listener) {
	updateListeners.remove(listener);
}
protected void fireModelDirtyStateChanged(ResourceInfo sr) {
	for (int i = 0; i < updateListeners.size(); i++) {
		IEditModelListener listener = (IEditModelListener)updateListeners.get(i);
		listener.modelDirtyStateChanged(sr);
	}
}
protected void fireModelDeleted(ResourceInfo sr) {
	for (int i = 0; i < updateListeners.size(); i++) {
		IEditModelListener listener = (IEditModelListener)updateListeners.get(i);
		listener.modelDeleted(sr);
	}
}
protected void fireModelReloaded(ResourceInfo sr) {
	for (int i = 0; i < updateListeners.size(); i++) {
		IEditModelListener listener = (IEditModelListener)updateListeners.get(i);
		listener.modelReloaded(sr);
	}
}
protected void fireModelLocationChanged(ResourceInfo sr,IFile movedToFile) {
	for (int i = 0; i < updateListeners.size(); i++) {
		IEditModelListener listener = (IEditModelListener)updateListeners.get(i);
		listener.modelLocationChanged(sr,movedToFile);
	}
}

/**
 * Return the resource set associated with this editModel.
 */
public ResourceSet getResourceSet() {
	return resourceSet;
}
/**
 * Returns the cached ResourceInfo for <code>file</code>
 * 
 * Creates a new ResourceInfo it is not found in the cache,
 * otherwise increment the reference count and return it.
 */
private ResourceInfo getResourceInfoForLoadedResource(Resource resource) {
	URI uri = resource.getURI();
	IFile file = getIFileForURI(uri);
	if(file == null)
		return null;
	ResourceInfo resourceInfo = (ResourceInfo)fileToResourceInfo.get(file);
	if(resourceInfo == null) {
		resourceInfo = new ResourceInfo(this,file);
		resourceInfo.setResource(resource); 
		addResourceInfo(resourceInfo);
		resourceInfo.resourceLoaded();
	}
	if(!resourceInfo.loading)
		resourceInfo.referenceCount++;
	return resourceInfo;
}
/**
 * Returns the cached ResourceInfo for <code>file</code>
 * 
 * Creates a new ResourceInfo it is not found in the cache,
 * otherwise increment the reference count and return it.
 */
public ResourceInfo getResourceInfo(IFile file) {
	ResourceInfo resourceInfo = (ResourceInfo)fileToResourceInfo.get(file);
	if(resourceInfo == null) {
		resourceInfo = new ResourceInfo(this,file);
		addResourceInfo(resourceInfo);
		try {
			resourceInfo.load();
		} catch (RuntimeException ex) {
			resourceInfo.referenceCount++;
			releaseReference(resourceInfo);
			throw ex;
		}
	}
	resourceInfo.referenceCount++;
	return resourceInfo;
}
/**
 * 
 */
public ResourceInfo[] getResourceInfos() {
	Collection resources = fileToResourceInfo.values();
	ResourceInfo[] result = new ResourceInfo[resources.size()];
	int i = 0;
	for (Iterator iter = resources.iterator(); iter.hasNext();) {
		result[i]  = (ResourceInfo) iter.next();
		i++;
	}
	return result;
}

private void setPrimaryFile(IFile newFile) {
	IResource oldFile = primaryFile;
	primaryFile = newFile;
	cache.updatePrimaryFile(oldFile, newFile);
}

/**
 * @return
 * @todo Generated comment
 */
public IResource getPrimaryFile() {
	return primaryFile;
}
/**
 * Decrement the reference count for <code>resourceInfo</code>
 * and dispose if it is the last reference.
 */
public void releaseReference(ResourceInfo resourceInfo) {
	resourceInfo.referenceCount--;
	if(resourceInfo.referenceCount == 0) {
		resourceInfo.dispose();
		removeResourceInfo(resourceInfo);
	}
}
/*
 * Add resourceInfo to the chache
 */
protected void addResourceInfo(ResourceInfo sr) {
	fileToResourceInfo.put(sr.getFile(), sr);
}
/*
 * Remove the resourceInfo from the cache.
 */
protected void removeResourceInfo(ResourceInfo sr) {
	fileToResourceInfo.remove(sr.getFile());
}
/**
 * Dispose this EditModel if there is no other reference to it;
 */
public void release() {
	referenceCount--;
	if(referenceCount == 0) {
		cache.remove(this);
		for (Iterator iter = fileToResourceInfo.values().iterator(); iter.hasNext();) {
			ResourceInfo resourceInfo = (ResourceInfo) iter.next();
			// Avoid concurrent modification exceptions because resourceInfo.dispose will attempt
			// to remove from the collection we are iterating over
			iter.remove(); 
			resourceInfo.dispose();
		}
	}
}

protected static IFile getIFileForURI(URI uri) {
	String filePath = null;
	String scheme = uri.scheme(); 
	IFile file = null;
	if ("file".equals(scheme)) { //$NON-NLS-1$
		filePath = uri.toFileString();
		if (filePath == null) return null;
		file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(filePath));
	} else if ("platform".equals(scheme) && uri.segmentCount() > 1 && "resource".equals(uri.segment(0))) {  //$NON-NLS-1$//$NON-NLS-2$
		StringBuffer platformResourcePath = new StringBuffer();
		for (int i = 1, size = uri.segmentCount(); i < size; ++i) {
			platformResourcePath.append('/');
			platformResourcePath.append(uri.segment(i));
		}
		filePath = platformResourcePath.toString();
		if (filePath == null) return null;
		file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath));
	}
	return file;
}
public EditModelCommandStack getCommandStack() {
	return commandStack;
}
public void setCommandStack(EditModelCommandStack commandStack) {
	this.commandStack = commandStack;
}
/**
 * Saves the model
 */
public boolean saveAll(IProgressMonitor progressMonitor) {
	WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
		protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
			getCommandStack().markSaveLocation();
			try {
				ResourceInfo[] sResource = getResourceInfos();
				for (int i = 0; i < sResource.length; i++) {
					ResourceInfo resource = sResource[i];
					if(resource.isDirty())
						resource.save();
				}
			} catch (IOException e) {
				throw new InvocationTargetException(e);
			}
		}
	};

	try {
		operation.run(progressMonitor);
	} catch (InvocationTargetException e) {
		return false;
	} catch (InterruptedException e) {
		return false;
	}
	return true;
}

public boolean savePrimaryResourceAs(final ResourceInfo resourceInfo,final IFile savedFile, IProgressMonitor progressMonitor) {
	boolean result = saveResourceAs(resourceInfo, savedFile, progressMonitor);
	if (result)
		setPrimaryFile(savedFile);
	return result;
}

public boolean saveResourceAs(final ResourceInfo resourceInfo,final IFile savedFile, IProgressMonitor progressMonitor) {
	WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
		protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
			try {
			    getCommandStack().markSaveLocation();
				resourceInfo.saveAs(savedFile);
			} catch (IOException e) {
				throw new InvocationTargetException(e);
			}
		}
	};

	try {
		operation.run(progressMonitor);
	} catch (InvocationTargetException e) {
		return false;
	} catch (InterruptedException e) {
		return false;
	}
	return true;
}
public static class Factory {
	protected EditModel createEditModel(ResourceSet resourceSet,IResource primaryFile) {
		return new EditModel(resourceSet,primaryFile);
	}
}
private static class EditModelCache {
	
	protected Map resourceSetToEditModel = new HashMap();
	protected Map fileToResourceSet = new HashMap();
	
	/**
	 * Return a new ResourceSet for the specified file.
	 */
	public EditModel getEditModel(IResource primaryFile,Factory factory) {
		ResourceSet resourceSet = getResourceSet(primaryFile);
		return getEditModel(resourceSet,primaryFile,factory);
	}
	/*
	 * Return the EditModel for specified resource set.
	 * 
	 * Creates a new EditModel it is not found in the cache,
	 * otherwise increment the reference count and return it.
	 */
	private EditModel getEditModel(ResourceSet resourceSet,IResource primaryFile,Factory factory) {
		EditModel editModel = (EditModel)resourceSetToEditModel.get(resourceSet);
		if(editModel != null) {
			editModel.referenceCount++;
			return editModel;
		}
		editModel = factory.createEditModel(resourceSet,primaryFile);
		editModel.referenceCount++;
		resourceSetToEditModel.put(resourceSet,editModel);
		final EditModel finalEditModel = editModel; 
		resourceSet.eAdapters().add(new AdapterImpl() {
			public void notifyChanged(Notification msg) {
				Resource r = (Resource)msg.getNewValue();
				finalEditModel.getResourceInfoForLoadedResource(r);
			}
		});
		return editModel;
	}
	/*
	 * Return a new ResourceSet for the specified file.
	 */
	private ResourceSet getResourceSet(IResource primaryFile) {
		ResourceSet resourceSet = (ResourceSet)fileToResourceSet.get(primaryFile);
		if(resourceSet != null)
			return resourceSet;
		// TODO: Extensibility
		resourceSet = new ResourceSetImpl();
		fileToResourceSet.put(primaryFile,resourceSet);
		return resourceSet;	
	}
	public void remove(EditModel editModel) {
		resourceSetToEditModel.remove(editModel.resourceSet);
		fileToResourceSet.remove(editModel.primaryFile);
	}
	private void updatePrimaryFile(IResource oldFile, IResource newFile) {
		ResourceSet rs = (ResourceSet)fileToResourceSet.get(oldFile);
		fileToResourceSet.remove(oldFile);
		fileToResourceSet.put(newFile, rs);
	}
}
}
