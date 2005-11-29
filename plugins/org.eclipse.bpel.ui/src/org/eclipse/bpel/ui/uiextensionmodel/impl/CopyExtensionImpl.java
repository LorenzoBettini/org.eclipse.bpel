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
 * $Id: CopyExtensionImpl.java,v 1.1 2005/11/29 18:51:09 james Exp $
 */
package org.eclipse.bpel.ui.uiextensionmodel.impl;

import org.eclipse.bpel.ui.uiextensionmodel.CopyExtension;
import org.eclipse.bpel.ui.uiextensionmodel.UiextensionmodelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Copy Extension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.ui.uiextensionmodel.impl.CopyExtensionImpl#getFromType <em>From Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.ui.uiextensionmodel.impl.CopyExtensionImpl#getToType <em>To Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CopyExtensionImpl extends EObjectImpl implements CopyExtension {
	/**
	 * The default value of the '{@link #getFromType() <em>From Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromType()
	 * @generated
	 * @ordered
	 */
	protected static final int FROM_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFromType() <em>From Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromType()
	 * @generated
	 * @ordered
	 */
	protected int fromType = FROM_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getToType() <em>To Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToType()
	 * @generated
	 * @ordered
	 */
	protected static final int TO_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getToType() <em>To Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToType()
	 * @generated
	 * @ordered
	 */
	protected int toType = TO_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CopyExtensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return UiextensionmodelPackage.eINSTANCE.getCopyExtension();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFromType() {
		return fromType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFromType(int newFromType) {
		int oldFromType = fromType;
		fromType = newFromType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UiextensionmodelPackage.COPY_EXTENSION__FROM_TYPE, oldFromType, fromType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getToType() {
		return toType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToType(int newToType) {
		int oldToType = toType;
		toType = newToType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UiextensionmodelPackage.COPY_EXTENSION__TO_TYPE, oldToType, toType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UiextensionmodelPackage.COPY_EXTENSION__FROM_TYPE:
				return new Integer(getFromType());
			case UiextensionmodelPackage.COPY_EXTENSION__TO_TYPE:
				return new Integer(getToType());
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UiextensionmodelPackage.COPY_EXTENSION__FROM_TYPE:
				setFromType(((Integer)newValue).intValue());
				return;
			case UiextensionmodelPackage.COPY_EXTENSION__TO_TYPE:
				setToType(((Integer)newValue).intValue());
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UiextensionmodelPackage.COPY_EXTENSION__FROM_TYPE:
				setFromType(FROM_TYPE_EDEFAULT);
				return;
			case UiextensionmodelPackage.COPY_EXTENSION__TO_TYPE:
				setToType(TO_TYPE_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case UiextensionmodelPackage.COPY_EXTENSION__FROM_TYPE:
				return fromType != FROM_TYPE_EDEFAULT;
			case UiextensionmodelPackage.COPY_EXTENSION__TO_TYPE:
				return toType != TO_TYPE_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (fromType: ");
		result.append(fromType);
		result.append(", toType: ");
		result.append(toType);
		result.append(')');
		return result.toString();
	}

} //CopyExtensionImpl
