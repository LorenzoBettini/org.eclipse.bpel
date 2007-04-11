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
 * $Id: ServiceRefImpl.java,v 1.3 2007/04/11 20:45:15 mchmielewski Exp $
 */
package org.eclipse.bpel.model.impl;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.ServiceRef;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.wst.wsdl.internal.impl.WSDLElementImpl;

import org.w3c.dom.Element;

import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ServiceRefImpl#getReferenceScheme <em>Reference Scheme</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ServiceRefImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceRefImpl extends WSDLElementImpl implements ServiceRef {
	/**
	 * The default value of the '{@link #getReferenceScheme() <em>Reference Scheme</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenceScheme()
	 * @generated
	 * @ordered
	 */
	protected static final String REFERENCE_SCHEME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReferenceScheme() <em>Reference Scheme</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenceScheme()
	 * @generated
	 * @ordered
	 */
	protected String referenceScheme = REFERENCE_SCHEME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final Object VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Object value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getServiceRef();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReferenceScheme() {
		return referenceScheme;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferenceScheme(String newReferenceScheme) {
		String oldReferenceScheme = referenceScheme;
		referenceScheme = newReferenceScheme;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.SERVICE_REF__REFERENCE_SCHEME, oldReferenceScheme, referenceScheme));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(Object newValue) {
		Object oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.SERVICE_REF__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.SERVICE_REF__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.SERVICE_REF__ELEMENT:
				return getElement();
			case BPELPackage.SERVICE_REF__REFERENCE_SCHEME:
				return getReferenceScheme();
			case BPELPackage.SERVICE_REF__VALUE:
				return getValue();
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
			case BPELPackage.SERVICE_REF__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.SERVICE_REF__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.SERVICE_REF__REFERENCE_SCHEME:
				setReferenceScheme((String)newValue);
				return;
			case BPELPackage.SERVICE_REF__VALUE:
				setValue((Object)newValue);
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
			case BPELPackage.SERVICE_REF__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.SERVICE_REF__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.SERVICE_REF__REFERENCE_SCHEME:
				setReferenceScheme(REFERENCE_SCHEME_EDEFAULT);
				return;
			case BPELPackage.SERVICE_REF__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case BPELPackage.SERVICE_REF__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.SERVICE_REF__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.SERVICE_REF__REFERENCE_SCHEME:
				return REFERENCE_SCHEME_EDEFAULT == null ? referenceScheme != null : !REFERENCE_SCHEME_EDEFAULT.equals(referenceScheme);
			case BPELPackage.SERVICE_REF__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(" (referenceScheme: ");
		result.append(referenceScheme);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //ServiceRefImpl
