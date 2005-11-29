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
package org.eclipse.bpel.ui.adapters;

import java.util.HashSet;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

public class AbstractAdapter implements Adapter {

	public static final Object[] EMPTY_ARRAY = new Object[0];
	protected HashSet supportedTypes = new HashSet();

	/**
     * Adds all the interfaces implemented by the given class.
     * It is used to populate the types available from this
     * adapter.
	 */
	public AbstractAdapter() {
		for (Class c = getClass(); c != null; c = c.getSuperclass()) {
			Class[] a = c.getInterfaces();
			for (int i = 0; i < a.length; i++) supportedTypes.add(a[i]);
		}
	}

	public void notifyChanged(Notification notification) { }

	public final Notifier getTarget() { return null; }
	public final void setTarget(Notifier newTarget) { /* do nothing */ }

	public boolean isAdapterForType(Object type) {
		return supportedTypes.contains(type);
	}
}
