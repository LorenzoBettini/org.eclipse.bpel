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
 * $Id: SequenceImpl.java,v 1.2 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sequence</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.SequenceImpl#getActivities <em>Activities</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SequenceImpl extends ActivityImpl implements Sequence {
	/**
	 * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivities()
	 * @generated
	 * @ordered
	 */
	protected EList activities = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SequenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getSequence();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getActivities() {
		if (activities == null) {
			activities = new EObjectContainmentEList(Activity.class, this, BPELPackage.SEQUENCE__ACTIVITIES);
		}
		return activities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.SEQUENCE__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.SEQUENCE__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.SEQUENCE__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.SEQUENCE__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.SEQUENCE__ACTIVITIES:
					return ((InternalEList)getActivities()).basicRemove(otherEnd, msgs);
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
			case BPELPackage.SEQUENCE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.SEQUENCE__ELEMENT:
				return getElement();
			case BPELPackage.SEQUENCE__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.SEQUENCE__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.SEQUENCE__NAME:
				return getName();
			case BPELPackage.SEQUENCE__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.SEQUENCE__TARGETS:
				return getTargets();
			case BPELPackage.SEQUENCE__SOURCES:
				return getSources();
			case BPELPackage.SEQUENCE__ACTIVITIES:
				return getActivities();
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
			case BPELPackage.SEQUENCE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.SEQUENCE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.SEQUENCE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.SEQUENCE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.SEQUENCE__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.SEQUENCE__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.SEQUENCE__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.SEQUENCE__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.SEQUENCE__ACTIVITIES:
				getActivities().clear();
				getActivities().addAll((Collection)newValue);
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
			case BPELPackage.SEQUENCE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.SEQUENCE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.SEQUENCE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.SEQUENCE__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.SEQUENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.SEQUENCE__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.SEQUENCE__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.SEQUENCE__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.SEQUENCE__ACTIVITIES:
				getActivities().clear();
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
			case BPELPackage.SEQUENCE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.SEQUENCE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.SEQUENCE__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.SEQUENCE__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.SEQUENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.SEQUENCE__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.SEQUENCE__TARGETS:
				return targets != null;
			case BPELPackage.SEQUENCE__SOURCES:
				return sources != null;
			case BPELPackage.SEQUENCE__ACTIVITIES:
				return activities != null && !activities.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} //SequenceImpl
