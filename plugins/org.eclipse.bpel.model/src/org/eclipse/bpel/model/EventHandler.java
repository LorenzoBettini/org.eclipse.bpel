/**
 * <copyright>
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * </copyright>
 *
 * $Id: EventHandler.java,v 1.1 2005/11/29 18:50:26 james Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.wst.wsdl.ExtensibleElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Handler</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.EventHandler#getAlarm <em>Alarm</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.EventHandler#getEvents <em>Events</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getEventHandler()
 * @model
 * @generated
 */
public interface EventHandler extends ExtensibleElement{
	/**
	 * Returns the value of the '<em><b>Alarm</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpel.model.OnAlarm}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alarm</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alarm</em>' containment reference list.
	 * @see org.eclipse.bpel.model.BPELPackage#getEventHandler_Alarm()
	 * @model type="org.eclipse.bpel.model.OnAlarm" containment="true"
	 * @generated
	 */
	EList getAlarm();

	/**
	 * Returns the value of the '<em><b>Events</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpel.model.OnEvent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Events</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Events</em>' containment reference list.
	 * @see org.eclipse.bpel.model.BPELPackage#getEventHandler_Events()
	 * @model type="org.eclipse.bpel.model.OnEvent" containment="true"
	 * @generated
	 */
	EList getEvents();

} // EventHandler
