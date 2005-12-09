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
 * $Id: CorrelationPattern.java,v 1.2 2005/12/09 19:30:43 james Exp $
 */
package org.eclipse.bpel.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Correlation Pattern</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpel.model.BPELPackage#getCorrelationPattern()
 * @model
 * @generated
 */
public final class CorrelationPattern extends AbstractEnumerator {
	/**
	 * The '<em><b>In</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>In</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IN_LITERAL
	 * @model name="in"
	 * @generated
	 * @ordered
	 */
	public static final int IN = 0;

	/**
	 * The '<em><b>Out</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Out</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OUT_LITERAL
	 * @model name="out"
	 * @generated
	 * @ordered
	 */
	public static final int OUT = 1;

	/**
	 * The '<em><b>Outin</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Outin</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OUTIN_LITERAL
	 * @model name="outin"
	 * @generated
	 * @ordered
	 */
	public static final int OUTIN = 2;

	/**
	 * The '<em><b>In</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IN
	 * @generated
	 * @ordered
	 */
	public static final CorrelationPattern IN_LITERAL = new CorrelationPattern(IN, "in", "in");

	/**
	 * The '<em><b>Out</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUT
	 * @generated
	 * @ordered
	 */
	public static final CorrelationPattern OUT_LITERAL = new CorrelationPattern(OUT, "out", "out");

	/**
	 * The '<em><b>Outin</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTIN
	 * @customized
	 * @ordered
	 */
	public static final CorrelationPattern OUTIN_LITERAL = new CorrelationPattern(OUTIN, "out-in", "out-in");

	/**
	 * An array of all the '<em><b>Correlation Pattern</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CorrelationPattern[] VALUES_ARRAY =
		new CorrelationPattern[] {
			IN_LITERAL,
			OUT_LITERAL,
			OUTIN_LITERAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Correlation Pattern</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Correlation Pattern</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CorrelationPattern get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CorrelationPattern result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Correlation Pattern</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CorrelationPattern getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CorrelationPattern result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Correlation Pattern</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CorrelationPattern get(int value) {
		switch (value) {
			case IN: return IN_LITERAL;
			case OUT: return OUT_LITERAL;
			case OUTIN: return OUTIN_LITERAL;
		}
		return null;	
	}

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private CorrelationPattern(int value, String name, String literal) {
		super(value, name, literal);
	}

} //CorrelationPattern
