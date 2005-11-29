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

/**
 * The ResourceInfo, which is a reference count cache to a model,
 * will call the model update listeners when the model
 * is deleted, reloaded, moved, or its dirty state is changed. 
 */
public interface IEditModelListener {
		
	/**
	 * The model has been deleted. All cached references to the model
	 * should be deleted.
	 * 
	 * If this is model the primary model and the editor is not dirty,
	 * the editor should be closed. If this is a secondary model,
	 * the editor may not need to be closed. 
	 */
	public void modelDeleted(ResourceInfo resourceInfo);
	
	/**
	 * The model was changed outside this framework and reloaded
	 * by the editmodel framework. It may be needed to refresh visuals.
	 */
	public void modelReloaded(ResourceInfo resourceInfo);
	
	/**
	 * The model file was moved from its location. All references
	 * to this file should be updated.
	 */
	public void modelLocationChanged(ResourceInfo resourceInfo,IFile movedToFile);
	
	/**
	 * The model dirty state was changed.
	 */
	public void modelDirtyStateChanged(ResourceInfo resourceInfo);

}