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

import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

public class AbstractAdapter implements Adapter {
	
	/** The event type that we will consume that indicates a context update */
	static public final int CONTEXT_UPDATE_EVENT_TYPE = 101;
		
	/** Makes sense only when adapters are statefull */
	private Notifier target = null;
	
	/** additional context that is needed by the adapter to wrap the object */
	private Object context = null;	
	
	/**
     * Adds all the interfaces implemented by the given class.
     * It is used to populate the types available from this
     * adapter.
	 */
	
	public AbstractAdapter() {
		
	}
	
	
	public void notifyChanged (Notification notification) {
		
		if (notification.getEventType() == CONTEXT_UPDATE_EVENT_TYPE && isStatefull() ) {
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
		if ( isStatefull() ) {
			target = newTarget;
		}
		 		
	}

	/**
	 * Answer true if we are an adapter for the type given
	 * 
	 */
	
	public boolean isAdapterForType (Object type) {
		Class clazz = null;
		// what is type ? (an interface)
		if (type instanceof Class) {
			clazz = (Class) type;
			return clazz.isInstance(this);
		}
		// what else could it be ?
		return false;
	}
	
	/**
	 * Return any context object that the adapter is holding.
	 * @return
	 */
	
	public Object getContext () {
		return context;
	}
	
	public boolean isStatefull () {
		return IStatefullAdapter.class.isInstance(this);
	}
	
	
	/**
	 * Some adapters rely on interfaces that are stateless, where the target
	 * is passed as an argument to the adapter. This is not necessarily true for
	 * some interfaces we cannot control (like IContentProposal).
	 * @param obj the object which might be the adapted target
	 * @param clazz the class that the target must be an instance of. 
	 * @return the target object
	 */
	
	public Object getTarget ( Object obj , Class clazz ) {

		// If the object is passed and it matches the adapted object class,
		// then we return it, because most likely it is it.
		if ( obj != null && clazz.isInstance(obj) ) {
			return obj ;
		}
		
		// if the adapter is stateful
		if ( isStatefull() ) {
			if (clazz.isInstance(target)) {
				return target;
			}
		}		
	
		throw new RuntimeException("Cannot figure out target object of class " + clazz.getName()); //$NON-NLS-1$
	}

}
