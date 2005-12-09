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
 * $Id: Assign.java,v 1.2 2005/12/09 19:47:52 james Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assign</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Used to update values of containers with new data. Assigns the Form r-value to the To l-value. An Assign is an atomic Activity. All copy elements succeed or none of them do. Exceptions are raised by incompatible types.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.Assign#getCopy <em>Copy</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.Assign#getValidateXML <em>Validate XML</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getAssign()
 * @model
 * @generated
 */
public interface Assign extends Activity{
	/**
	 * Returns the value of the '<em><b>Copy</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpel.model.Copy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Copy</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Copy</em>' containment reference list.
	 * @see org.eclipse.bpel.model.BPELPackage#getAssign_Copy()
	 * @model type="org.eclipse.bpel.model.Copy" containment="true" required="true"
	 * @generated
	 */
	EList getCopy();

	/**
	 * Returns the value of the '<em><b>Validate XML</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validate XML</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validate XML</em>' attribute.
	 * @see #setValidateXML(Boolean)
	 * @see org.eclipse.bpel.model.BPELPackage#getAssign_ValidateXML()
	 * @model default="false"
	 * @generated
	 */
	Boolean getValidateXML();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.Assign#getValidateXML <em>Validate XML</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validate XML</em>' attribute.
	 * @see #getValidateXML()
	 * @generated
	 */
	void setValidateXML(Boolean value);

} // Assign
