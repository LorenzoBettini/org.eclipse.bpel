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

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

public class AbstractAdapter implements Adapter {
	
	/** The event type that we will consume that indicates a context update */
	static public final int CONTEXT_UPDATE_EVENT_TYPE = 101;
	
	/** a static map of all interfaces indexed by adapter class */
	private static final HashMap interfacesByAdapterClass = new HashMap();
	
	/** My supported types */
	private HashSet supportedTypes = null;

	/** Makes sense only when adapters are stateful */
	private Notifier target = null;
	
	/** additional context that is needed by the adapter to wrap the object */
	private Object context = null;
	
	
	/**
     * Adds all the interfaces implemented by the given class.
     * It is used to populate the types available from this
     * adapter.
	 */
	
	public AbstractAdapter() {
		
		Class clazz = this.getClass();
		supportedTypes = (HashSet) interfacesByAdapterClass.get(clazz);
		
		if (supportedTypes == null) {
			supportedTypes = createSupportedTypes();
			interfacesByAdapterClass.put(clazz,supportedTypes);
		}
	}
	
	/**
	 * Create a set of supported types based on the interfaces I implement.
	 * @return a set of supported interfaces
	 */
	
	private HashSet createSupportedTypes () {
		HashSet myTypes = new HashSet(11);
		for (Class c = getClass(); c != null; c = c.getSuperclass()) {
			Class[] a = c.getInterfaces();
			for (int i = 0; i < a.length; i++) {
				myTypes.add(a[i]);			
			}
		}
		return myTypes;
	}

	
	public void notifyChanged (Notification notification) {
		
		if (notification.getEventType() == CONTEXT_UPDATE_EVENT_TYPE && !isStateless() ) {
			context = notification.getNewValue();
		}
	}

	
	/**
	 * Return the target. This will always be null in case the 
	 * adapter is stateless.
	 * 
	 */
	
	public Notifier getTarget() {
		return target;
	}
	
	/**
	 * Set the target element, only if the adapter is stateless.
	 * 
	 */
	
	public void setTarget(Notifier newTarget) {		
		if (!isStateless()) {
			target = newTarget;
		}
		 		
	}

	public boolean isAdapterForType (Object type) {
		return supportedTypes.contains(type);
	}
	
	/**
	 * Return any context object that the adapter is holding.
	 * @return
	 */
	
	public Object getContext () {
		return context;
	}
	
	/**
	 * Most adapters are statelss, some that are stateful will override this
	 * method and return true.
	 * 
	 * @return
	 */
	
	public boolean isStateless () {
		return true;
	}
}
