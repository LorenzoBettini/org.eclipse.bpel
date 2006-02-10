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
 * $Id: AssignImpl.java,v 1.4 2006/02/10 16:12:48 rodrigo Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assign</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.AssignImpl#getCopy <em>Copy</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.AssignImpl#getValidate <em>Validate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssignImpl extends ActivityImpl implements Assign {
	/**
	 * The cached value of the '{@link #getCopy() <em>Copy</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCopy()
	 * @generated
	 * @ordered
	 */
	protected EList copy = null;

	/**
	 * The default value of the '{@link #getValidate() <em>Validate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidate()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean VALIDATE_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getValidate() <em>Validate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidate()
	 * @generated
	 * @ordered
	 */
	protected Boolean validate = VALIDATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssignImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getAssign();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getCopy() {
		if (copy == null) {
			copy = new EObjectContainmentEList(Copy.class, this, BPELPackage.ASSIGN__COPY);
		}
		return copy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getValidate() {
		return validate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidate(Boolean newValidate) {
		Boolean oldValidate = validate;
		validate = newValidate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ASSIGN__VALIDATE, oldValidate, validate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.ASSIGN__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.ASSIGN__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.ASSIGN__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.ASSIGN__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.ASSIGN__COPY:
					return ((InternalEList)getCopy()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.ASSIGN__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.ASSIGN__ELEMENT:
				return getElement();
			case BPELPackage.ASSIGN__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.ASSIGN__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.ASSIGN__NAME:
				return getName();
			case BPELPackage.ASSIGN__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.ASSIGN__TARGETS:
				return getTargets();
			case BPELPackage.ASSIGN__SOURCES:
				return getSources();
			case BPELPackage.ASSIGN__COPY:
				return getCopy();
			case BPELPackage.ASSIGN__VALIDATE:
				return getValidate();
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
			case BPELPackage.ASSIGN__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.ASSIGN__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.ASSIGN__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.ASSIGN__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.ASSIGN__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.ASSIGN__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.ASSIGN__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.ASSIGN__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.ASSIGN__COPY:
				getCopy().clear();
				getCopy().addAll((Collection)newValue);
				return;
			case BPELPackage.ASSIGN__VALIDATE:
				setValidate((Boolean)newValue);
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
			case BPELPackage.ASSIGN__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ASSIGN__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ASSIGN__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.ASSIGN__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.ASSIGN__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.ASSIGN__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.ASSIGN__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.ASSIGN__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.ASSIGN__COPY:
				getCopy().clear();
				return;
			case BPELPackage.ASSIGN__VALIDATE:
				setValidate(VALIDATE_EDEFAULT);
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
			case BPELPackage.ASSIGN__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.ASSIGN__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.ASSIGN__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.ASSIGN__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.ASSIGN__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.ASSIGN__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.ASSIGN__TARGETS:
				return targets != null;
			case BPELPackage.ASSIGN__SOURCES:
				return sources != null;
			case BPELPackage.ASSIGN__COPY:
				return copy != null && !copy.isEmpty();
			case BPELPackage.ASSIGN__VALIDATE:
				return VALIDATE_EDEFAULT == null ? validate != null : !VALIDATE_EDEFAULT.equals(validate);
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
		result.append(" (validate: ");
		result.append(validate);
		result.append(')');
		return result.toString();
	}

} //AssignImpl
