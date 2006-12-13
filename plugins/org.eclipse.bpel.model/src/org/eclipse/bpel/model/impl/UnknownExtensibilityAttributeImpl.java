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
 * $Id: UnknownExtensibilityAttributeImpl.java,v 1.2 2006/12/13 16:17:31 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.UnknownExtensibilityAttribute;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.wst.wsdl.internal.impl.UnknownExtensibilityElementImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unknown Extensibility Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class UnknownExtensibilityAttributeImpl extends UnknownExtensibilityElementImpl implements UnknownExtensibilityAttribute {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UnknownExtensibilityAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getUnknownExtensibilityAttribute();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
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
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT:
				return getElement();
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT_TYPE:
				return getElementType();
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__CHILDREN:
				return getChildren();
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
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
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
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__CHILDREN:
				getChildren().clear();
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
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE__CHILDREN:
				return children != null && !children.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} //UnknownExtensibilityAttributeImpl
