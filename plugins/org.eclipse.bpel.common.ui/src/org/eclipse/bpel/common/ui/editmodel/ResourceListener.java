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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
/**
 * A eclipse resource listener used by the shared resource framework
 * to support updating/reloading the model, informing the user, or closing the editor
 * when a resource is changed by another editor kind or outside the
 * workspace.
 * 
 * Internal - Should not be used by clients.
 */
class ResourceListener implements IResourceChangeListener {
	
	ResourceInfo modelInfo;
	IFile file;
	
	public static Runner runner = null;
	
	protected ResourceListener(ResourceInfo modelInfo,IFile file) {
		this.modelInfo = modelInfo;
		this.file = file;
	}
	
	/*
	 * This method can be called if the file for a ResourceInfo changes.
	 */
	public void setFile(IFile file) {
		this.file = file;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent e) {			
		IResourceDelta delta = e.getDelta();
		if (delta != null) delta = delta.findMember(file.getFullPath());

		if (delta != null) {				
			switch (delta.getKind()) {
				case IResourceDelta.CHANGED:
					if ((delta.getFlags() & IResourceDelta.CONTENT) != 0) {			
						if (!isDirty())
							fireModelRefresh();
					}
					break;

				case IResourceDelta.REMOVED:
					if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
						fireModelMoved(delta.getMovedToPath());
					} else if (!isDirty()) {
						fireModelDeleted();	
					}
					break;
			}
		}
	}
	
	private void fireModelRefresh() {
		Runnable runnable = new Runnable() {
			public void run() {
				modelInfo.refresh();
			}
		};
		run(runnable);
	}
	
	private void fireModelMoved(final IPath path) {
		Runnable runnable = new Runnable() {
			public void run() {
				IFile movedTofile = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
				if (file == null)
					return;
				modelInfo.move(movedTofile);
				file = movedTofile;
			}
		};
		
		run(runnable);
	}
	
	private void fireModelDeleted() {
		Runnable runnable = new Runnable() {
			public void run() {
				modelInfo.deleted();
				modelInfo.dispose();
			}
		};
		run(runnable);
	}
	protected boolean isDirty() {
		// if we are not initialized we are not dirty
		if (modelInfo.getResource() == null)
			return false;
		return modelInfo.getResource().isModified();
	}
	public void run(Runnable runnable) {
		if(runner == null)
			runnable.run();
		runner.run(runnable);
	}
	
	public static interface Runner {
		public void run(Runnable runnable);
	}
}
