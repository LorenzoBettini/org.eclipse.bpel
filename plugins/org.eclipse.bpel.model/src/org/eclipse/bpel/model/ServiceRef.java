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
 * $Id: ServiceRef.java,v 1.2 2007/04/11 20:42:39 mchmielewski Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.wst.wsdl.WSDLElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Service Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.ServiceRef#getReferenceScheme <em>Reference Scheme</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.ServiceRef#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getServiceRef()
 * @model
 * @generated
 */
public interface ServiceRef extends WSDLElement {
	/**
	 * Returns the value of the '<em><b>Reference Scheme</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Scheme</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Scheme</em>' attribute.
	 * @see #setReferenceScheme(String)
	 * @see org.eclipse.bpel.model.BPELPackage#getServiceRef_ReferenceScheme()
	 * @model
	 * @generated
	 */
	String getReferenceScheme();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.ServiceRef#getReferenceScheme <em>Reference Scheme</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference Scheme</em>' attribute.
	 * @see #getReferenceScheme()
	 * @generated
	 */
	void setReferenceScheme(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.eclipse.bpel.model.BPELPackage#getServiceRef_Value()
	 * @model
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.ServiceRef#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

} // ServiceRef
