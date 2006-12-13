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
 * $Id: EndpointReferenceRole.java,v 1.2 2006/12/13 16:17:31 smoser Exp $
 */
package org.eclipse.bpel.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Endpoint Reference Role</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpel.model.BPELPackage#getEndpointReferenceRole()
 * @model
 * @generated
 */
public final class EndpointReferenceRole extends AbstractEnumerator {
	/**
	 * The '<em><b>My Role</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>My Role</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MY_ROLE_LITERAL
	 * @model name="myRole"
	 * @generated
	 * @ordered
	 */
	public static final int MY_ROLE = 0;

	/**
	 * The '<em><b>Partner Role</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Partner Role</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PARTNER_ROLE_LITERAL
	 * @model name="partnerRole"
	 * @generated
	 * @ordered
	 */
	public static final int PARTNER_ROLE = 1;

	/**
	 * The '<em><b>My Role</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MY_ROLE
	 * @generated
	 * @ordered
	 */
	public static final EndpointReferenceRole MY_ROLE_LITERAL = new EndpointReferenceRole(MY_ROLE, "myRole");

	/**
	 * The '<em><b>Partner Role</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PARTNER_ROLE
	 * @generated
	 * @ordered
	 */
	public static final EndpointReferenceRole PARTNER_ROLE_LITERAL = new EndpointReferenceRole(PARTNER_ROLE, "partnerRole");

	/**
	 * An array of all the '<em><b>Endpoint Reference Role</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final EndpointReferenceRole[] VALUES_ARRAY =
		new EndpointReferenceRole[] {
			MY_ROLE_LITERAL,
			PARTNER_ROLE_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Endpoint Reference Role</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Endpoint Reference Role</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EndpointReferenceRole get(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EndpointReferenceRole result = VALUES_ARRAY[i];
			if (result.toString().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Endpoint Reference Role</b></em>' literal with the specified value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EndpointReferenceRole get(int value) {
		switch (value) {
			case MY_ROLE: return MY_ROLE_LITERAL;
			case PARTNER_ROLE: return PARTNER_ROLE_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EndpointReferenceRole(int value, String name) {
		super(value, name);
	}

} //EndpointReferenceRole
