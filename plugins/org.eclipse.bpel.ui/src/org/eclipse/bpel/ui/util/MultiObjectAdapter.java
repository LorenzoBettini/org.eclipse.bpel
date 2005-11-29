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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

/**
 * An adapter which automatically keeps track of the notifiers it has been added to
 * (so it may be conveniently removed).
 */
public abstract class MultiObjectAdapter implements Adapter {

	// TODO: this was a Set before, I changed it to a List to work around unexpected
	// behaviour (eAdapter() lists can contain the same adapter more than once).
	List addedTo = new ArrayList();
	
	/**
	 * Subclasses must override this.
	 */
	public abstract void notify(Notification n); 
	
	/**
	 * Subclasses may override this (e.g. to add other adapters)
	 */
	public void addedTo(Object target) { }

	/**
	 * Subclasses may override this (e.g. to remove other adapters)
	 */
	public void removedFrom(Object target) { }

	/**
	 * Subclasses may use this helper to add themselves as a listener to a target object.
	 * addToObject() only adds this adapter if it has not already been added to target. 
	 */
	public void addToObject(Notifier target) {
		for (Iterator it = addedTo.iterator(); it.hasNext(); ) {
			if (it.next().equals(target)) return;
		}
		target.eAdapters().add(this);
	}
	
	/**
	 * Removes this adapter from all notifiers it has been added to.
	 */
	public final void removeFromAll() {
		Notifier[] oldTarget = (Notifier[])addedTo.toArray(new Notifier[addedTo.size()]);
		for (int i = 0; i<oldTarget.length; i++)  oldTarget[i].eAdapters().remove(this);
		if (!addedTo.isEmpty())  throw new IllegalStateException();			
	}
	
	/* Adapter */

	// this is meant for implementation use (i.e. BatchedMultiObjectAdapter).
	// if you override in a subclass, call the super implementation.
	protected void doNotify(Notification n) { notify(n); }
	
	public final void notifyChanged(Notification n) {
		if (n == null)  throw new IllegalStateException();
		if (n.getEventType() == Notification.REMOVING_ADAPTER) {
			Object notifier = n.getNotifier();
			addedTo.remove(notifier);
			removedFrom(notifier);
		} else {
			doNotify(n);
		}
	}
	public final boolean isAdapterForType(Object type) { return false; }
	public final Notifier getTarget() { return null; }
	public final void setTarget(Notifier newTarget) {
		addedTo.add(newTarget);  // <--- hack
		addedTo(newTarget);
	}
}