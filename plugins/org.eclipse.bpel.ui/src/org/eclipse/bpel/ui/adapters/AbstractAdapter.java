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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

/**
 * Abstract adapter has basic adapter functionality and it has some
 * logic to decide whether adapters are stateful or stateless. 
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 15, 2006
 *
 */

public class AbstractAdapter implements Adapter {
	
	/** The event type that we will consume that indicates a context update */
	static public final int CONTEXT_UPDATE_EVENT_TYPE = 101;
		
	/** Makes sense only when adapters are statefull */
	private Object target = null;
	
	/** additional context that is needed by the adapter to wrap the object */
	private Object context = null;	
	
	/**
     * Adds all the interfaces implemented by the given class.
     * It is used to populate the types available from this
     * adapter.
	 */
	
	public AbstractAdapter() {
		
	}
	
	
	/**
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	
	public void notifyChanged (Notification notification) {
		
		if (notification.getEventType() == CONTEXT_UPDATE_EVENT_TYPE && isStatefull() ) {
			context = notification.getNewValue();
		}
	}

	
	/**
	 * Return the target. This will always be null in case the 
	 * adapter is stateless.
	 * @return the notifier which is our target
	 * 
	 */
	
	public Notifier getTarget() {
		return (Notifier) getTarget(target,Notifier.class);		
	}
	
	/**
	 * Set the target element, only if the adapter is stateful.
	 * 
	 * @param newTarget set the target
	 */
	
	public void setTarget (Notifier newTarget) {		
		if ( isStatefull() ) {
			target = newTarget;
		}
		 		
	}

	
	
	/**
	 * 
	 * @param newTarget
	 */
	
	public void setTarget (Object newTarget) {		
		if ( isStatefull() ) {
			target = newTarget;
		}		 	
	}
	
	
	/**
	 * Answer true if we are an adapter for the type given.
	 * 
	 * @param type the type to check if we can adapt
	 * @return true/false if we can adapt to it.
	 */
	
	public boolean isAdapterForType (Object type) {		
		// what is type ? (an interface)
		if (type instanceof Class) {
			Class<?> clazz = (Class) type;
			return clazz.isInstance(this);
		}
		// what else could it be ?
		return false;
	}
	
	/**
	 * Return any context object that the adapter is holding.
	 * 
	 * @return any context context object that the adapter is holding
	 */
	
	public Object getContext () {
		return context;
	}
	
	/**
	 * Answer if we ware state-full or stateless ...
	 * 
	 * @return true if stateful, false if stateless.
	 */
	
	public boolean isStatefull () {
		return IStatefullAdapter.class.isInstance(this);
	}
	
	
	/**
	 * Some adapters rely on interfaces that are stateless, where the target
	 * is passed as an argument to the adapter. This is not necessarily true for
	 * some interfaces we cannot control (like IContentProposal).
	 * 
	 * @param obj the object which might be the adapted target
	 * @param clazz the class that the target must be an instance of. 
	 * @return the target object
	 */
	
	public Object getTarget ( Object obj , Class<?> clazz ) {

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
