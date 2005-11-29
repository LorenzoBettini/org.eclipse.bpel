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
 * $Id: Switch.java,v 1.1 2005/11/29 18:50:25 james Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Switch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Allows selection of exactly one branch of execution from a set of choices. The choices are evaluated in order and the first one that is true is selected. If no case is selected, the otherwise activity is selected if any.
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.Switch#getCases <em>Cases</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.Switch#getOtherwise <em>Otherwise</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getSwitch()
 * @model
 * @generated
 */
public interface Switch extends Activity{
	/**
	 * Returns the value of the '<em><b>Cases</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpel.model.Case}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cases</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cases</em>' containment reference list.
	 * @see org.eclipse.bpel.model.BPELPackage#getSwitch_Cases()
	 * @model type="org.eclipse.bpel.model.Case" containment="true" required="true"
	 * @generated
	 */
	EList getCases();

	/**
	 * Returns the value of the '<em><b>Otherwise</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Otherwise</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Otherwise</em>' containment reference.
	 * @see #isSetOtherwise()
	 * @see #unsetOtherwise()
	 * @see #setOtherwise(Otherwise)
	 * @see org.eclipse.bpel.model.BPELPackage#getSwitch_Otherwise()
	 * @model containment="true" unsettable="true"
	 * @generated
	 */
	Otherwise getOtherwise();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.Switch#getOtherwise <em>Otherwise</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Otherwise</em>' containment reference.
	 * @see #isSetOtherwise()
	 * @see #unsetOtherwise()
	 * @see #getOtherwise()
	 * @generated
	 */
	void setOtherwise(Otherwise value);

	/**
	 * Unsets the value of the '{@link org.eclipse.bpel.model.Switch#getOtherwise <em>Otherwise</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOtherwise()
	 * @see #getOtherwise()
	 * @see #setOtherwise(Otherwise)
	 * @generated
	 */
	void unsetOtherwise();

	/**
	 * Returns whether the value of the '{@link org.eclipse.bpel.model.Switch#getOtherwise <em>Otherwise</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Otherwise</em>' containment reference is set.
	 * @see #unsetOtherwise()
	 * @see #getOtherwise()
	 * @see #setOtherwise(Otherwise)
	 * @generated
	 */
	boolean isSetOtherwise();

} // Switch
