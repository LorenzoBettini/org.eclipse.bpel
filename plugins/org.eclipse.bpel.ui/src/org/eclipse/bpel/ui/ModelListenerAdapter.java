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
package org.eclipse.bpel.ui;

import java.util.EventObject;

import org.eclipse.bpel.common.extension.model.ExtensionMap;
import org.eclipse.bpel.common.ui.editmodel.EditModelCommandStack;
import org.eclipse.bpel.common.ui.editmodel.EditModelCommandStack.SharedCommandStackListener;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ListenerDescriptor;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.gef.commands.CommandStackListener;


public class ModelListenerAdapter extends EContentAdapter implements CommandStackListener {
	ExtensionMap extensionMap = null;
	
	Adapter linkNotificationAdapter;
	
	boolean inExecute = false;
	boolean ignoreChanges = true;
	
	public void setLinkNotificationAdapter(Adapter adapter) {
		this.linkNotificationAdapter = adapter;
	}
	
	public void notifyChanged(Notification n) {
		super.notifyChanged(n);
		ListenerDescriptor[] descriptors = BPELUIRegistry.getInstance().getListenerDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
		    descriptors[i].getModelListener().handleChange(n);
		}
		// TODO: should the descriptor-based listeners be protected by this as well?
		if (!ignoreChanges) {
			if (linkNotificationAdapter != null) {
				linkNotificationAdapter.notifyChanged(n);
			}
		}
	}
	public void setTarget(Notifier notifier) {
		super.setTarget(notifier);
		if ((notifier instanceof EObject) && (extensionMap != null)) {
			if (inExecute) {
				ModelHelper.createExtensionIfNecessary(extensionMap, (EObject)notifier);
			}
		}
	}
	public void setExtensionMap(ExtensionMap extensionMap) {
		this.extensionMap = extensionMap;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CommandStackListener#commandStackChanged(java.util.EventObject)
	 */
	public void commandStackChanged(EventObject e) {
		if (e instanceof EditModelCommandStack.SharedCommandStackChangedEvent) {
			switch (((EditModelCommandStack.SharedCommandStackChangedEvent)e).getProperty()) {
			case SharedCommandStackListener.EVENT_START_EXECUTE:
				inExecute = true; ignoreChanges = false; break;
			case SharedCommandStackListener.EVENT_START_UNDO:
			case SharedCommandStackListener.EVENT_START_REDO:
				inExecute = ignoreChanges = false; break;
			case SharedCommandStackListener.EVENT_FINISH_EXECUTE:
			case SharedCommandStackListener.EVENT_FINISH_UNDO:
			case SharedCommandStackListener.EVENT_FINISH_REDO:
				inExecute = false; ignoreChanges = true; break;
			}
		}
	}
}
