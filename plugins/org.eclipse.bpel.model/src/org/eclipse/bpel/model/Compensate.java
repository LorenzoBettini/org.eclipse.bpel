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
 * $Id: Compensate.java,v 1.1 2005/11/29 18:50:26 james Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compensate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Used to invoke compensation of an inner scope that has already completed its execution normally. This construct can  be invoked only from within a fault handler or the compensation handler of the scope that immediately encloses the scope for which compensation is to be performed.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.Compensate#getScope <em>Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getCompensate()
 * @model
 * @generated
 */
public interface Compensate extends Activity {
	/**
	 * Returns the value of the '<em><b>Scope</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specifies the scope whose compensation handler is to be invoked.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Scope</em>' reference.
	 * @see #setScope(EObject)
	 * @see org.eclipse.bpel.model.BPELPackage#getCompensate_Scope()
	 * @model
	 * @generated
	 */
	EObject getScope();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.Compensate#getScope <em>Scope</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' reference.
	 * @see #getScope()
	 * @generated
	 */
	void setScope(EObject value);

	/**
	 * Sets the scope for which compensation is to be performed.
	 * 
	 * The specified <code>scopeName</code> must correspond with the name of a
	 * scope enclosed by the same scope that encloses this compensate activity.
	 * 
	 * @param scopeName
	 *            the scope name
	 * @see #setScope(Scope)
	 * @see #getScope()
	 * @customized
	 */
	void setScope(String scopeName);
	
} // Compensate
