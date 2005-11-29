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
 * $Id: ThrowImpl.java,v 1.1 2005/11/29 18:50:25 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.Throw;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Throw</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ThrowImpl#getFaultName <em>Fault Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ThrowImpl#getFaultVariable <em>Fault Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ThrowImpl extends ActivityImpl implements Throw {
	/**
	 * The default value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultName()
	 * @generated
	 * @ordered
	 */
	protected static final QName FAULT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultName()
	 * @generated
	 * @ordered
	 */
	protected QName faultName = FAULT_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFaultVariable() <em>Fault Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable faultVariable = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ThrowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getThrow();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QName getFaultName() {
		return faultName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFaultName(QName newFaultName) {
		QName oldFaultName = faultName;
		faultName = newFaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.THROW__FAULT_NAME, oldFaultName, faultName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getFaultVariable() {
		if (faultVariable != null && faultVariable.eIsProxy()) {
			Variable oldFaultVariable = faultVariable;
			faultVariable = (Variable)eResolveProxy((InternalEObject)faultVariable);
			if (faultVariable != oldFaultVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.THROW__FAULT_VARIABLE, oldFaultVariable, faultVariable));
			}
		}
		return faultVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetFaultVariable() {
		return faultVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFaultVariable(Variable newFaultVariable) {
		Variable oldFaultVariable = faultVariable;
		faultVariable = newFaultVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.THROW__FAULT_VARIABLE, oldFaultVariable, faultVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.THROW__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.THROW__ELEMENT:
				return getElement();
			case BPELPackage.THROW__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.THROW__NAME:
				return getName();
			case BPELPackage.THROW__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.THROW__TARGETS:
				return getTargets();
			case BPELPackage.THROW__SOURCES:
				return getSources();
			case BPELPackage.THROW__FAULT_NAME:
				return getFaultName();
			case BPELPackage.THROW__FAULT_VARIABLE:
				if (resolve) return getFaultVariable();
				return basicGetFaultVariable();
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
			case BPELPackage.THROW__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.THROW__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.THROW__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.THROW__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.THROW__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.THROW__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.THROW__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.THROW__FAULT_NAME:
				setFaultName((QName)newValue);
				return;
			case BPELPackage.THROW__FAULT_VARIABLE:
				setFaultVariable((Variable)newValue);
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
			case BPELPackage.THROW__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.THROW__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.THROW__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.THROW__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.THROW__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.THROW__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.THROW__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.THROW__FAULT_NAME:
				setFaultName(FAULT_NAME_EDEFAULT);
				return;
			case BPELPackage.THROW__FAULT_VARIABLE:
				setFaultVariable((Variable)null);
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
			case BPELPackage.THROW__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.THROW__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.THROW__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.THROW__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.THROW__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.THROW__TARGETS:
				return targets != null;
			case BPELPackage.THROW__SOURCES:
				return sources != null;
			case BPELPackage.THROW__FAULT_NAME:
				return FAULT_NAME_EDEFAULT == null ? faultName != null : !FAULT_NAME_EDEFAULT.equals(faultName);
			case BPELPackage.THROW__FAULT_VARIABLE:
				return faultVariable != null;
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
		result.append(" (faultName: ");
		result.append(faultName);
		result.append(')');
		return result.toString();
	}

} //ThrowImpl
