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
 * $Id: Flow.java,v 1.1 2005/11/29 18:50:26 james Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Enables concurrent execution of its contained activities. Contrast with Sequence which executes its contained activities in order. Links can be used in a flow to define arbitrary predecessor and/or successor activities. A flow activity completes when all of its concurrent activities have  been completed.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.Flow#getActivities <em>Activities</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.Flow#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getFlow()
 * @model
 * @generated
 */
public interface Flow extends Activity{
	/**
	 * Returns the value of the '<em><b>Activities</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpel.model.Activity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activities</em>' containment reference list.
	 * @see org.eclipse.bpel.model.BPELPackage#getFlow_Activities()
	 * @model type="org.eclipse.bpel.model.Activity" containment="true" required="true"
	 * @generated
	 */
	EList getActivities();

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference.
	 * @see #setLinks(Links)
	 * @see org.eclipse.bpel.model.BPELPackage#getFlow_Links()
	 * @model containment="true"
	 * @generated
	 */
	Links getLinks();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.Flow#getLinks <em>Links</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Links</em>' containment reference.
	 * @see #getLinks()
	 * @generated
	 */
	void setLinks(Links value);

} // Flow
