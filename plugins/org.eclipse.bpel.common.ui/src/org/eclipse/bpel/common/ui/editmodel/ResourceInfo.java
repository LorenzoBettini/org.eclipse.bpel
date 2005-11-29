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
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public class ResourceInfo {
	
	private EditModel editModel;
	private Resource resource;
	private IFile file;
	private ResourceListener resourceListener;
	private Adapter resourceAdapter;
	private long synchronizeStamp = 0;
	private Map loadOptions = new HashMap();
	protected int referenceCount = 0;
	protected boolean loading = false;
	protected boolean fileExists = false;
	
ResourceInfo(EditModel editModel,IFile file) {
	this.editModel = editModel;
	setFile(file);
	resetSynchronizeStamp();
	resourceListener = new ResourceListener(this,this.file);
	ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener);
	resourceAdapter = new AdapterImpl() {
		public void notifyChanged(Notification msg) {
			if((msg.getEventType() == Notification.SET)
					&& (resource != null && msg.getNotifier() == resource)) {
				if(msg.getFeatureID(Resource.class) == Resource.RESOURCE__IS_MODIFIED) {
					ResourceInfo.this.editModel.fireModelDirtyStateChanged(ResourceInfo.this);
				}
			}
		}
	};
}

public IFile getFile() {
	return file;
}
protected void setFile(IFile file) {
	this.file = file;
}
protected boolean getFileExists() {
	return fileExists;
}
public boolean isDirty() {
	if(resource == null)
		return false;
	return resource.isModified();
}
public Resource getResource() {
	return resource;
}
protected void setResource(Resource resource) {
	this.resource = resource;
}
public Map getLoadOptions() {
	return loadOptions;
}
public void setLoadOptions(Map loadOptions) {
	this.loadOptions = loadOptions;
}
protected void move(IFile movedToFile) {
	editModel.removeResourceInfo(this);	
	file = movedToFile;
	resource.setURI(URI.createPlatformResourceURI(file.getFullPath().toString()));
	resetSynchronizeStamp();
	resourceListener.setFile(movedToFile);
	editModel.addResourceInfo(this);
	editModel.fireModelLocationChanged(this,movedToFile);
}

protected void deleted() {
	if (editModel == null) return;
	editModel.fireModelDeleted(this);
}
/**
* Should not be used by clients
*/
public void refresh() {
	if (getSynchronizeStamp() == getCurrentFileModified())
		return;
	load();
	editModel.fireModelReloaded(this);
}
protected void dispose() {
	if (editModel == null)
		return;
	if(resource != null)
		resource.eAdapters().remove(resourceAdapter);
	editModel.removeResourceInfo(this);
	ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
	if (resource != null)
		resource.unload();
	editModel = null;
	resource = null;
	resourceListener = null;		
}
protected void load() {
	if (resource != null)
		resource.unload();
	try {
		loading = true;
		resource = editModel.getResourceSet().getResource(
			URI.createPlatformResourceURI(file.getFullPath().toString()),
			true);
	} finally {
		loading = false;
	}
	resourceLoaded();
}
protected void resourceLoaded() {
	if(loading)
		return;
	resetSynchronizeStamp();
	resource.eAdapters().add(resourceAdapter);
	fileExists = file.exists();
}

/**
* Should not be used by clients
*/
public void save() throws CoreException, IOException {
	getResource().save(getLoadOptions());
	fileExists = true;
	resetSynchronizeStamp();
}
/**
* Should not be used by clients
*/
public void saveAs(IFile savedFile) throws CoreException, IOException {
	// TODO Revert to old file name if save fails
	move(savedFile);
	save();
}
/**
 * Resets the syncronication stamp.
 * Should not be used by clients
 */
public void resetSynchronizeStamp() {
	synchronizeStamp = getCurrentFileModified();
}
/**
 * Returns the last synchronication stamp.
 * Should not be used by clients
 */
public long getSynchronizeStamp() {
	return synchronizeStamp;
}
/*
 * Returns the resource current modified time.
 */
private long getCurrentFileModified() {
	return file.getLocation().toFile().lastModified();
}
}
