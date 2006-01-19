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
 * $Id: SwitchImpl.java,v 1.2 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Case;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Otherwise;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Switch;
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
 * An implementation of the model object '<em><b>Switch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.SwitchImpl#getCases <em>Cases</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.SwitchImpl#getOtherwise <em>Otherwise</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SwitchImpl extends ActivityImpl implements Switch {
	/**
	 * The cached value of the '{@link #getCases() <em>Cases</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCases()
	 * @generated
	 * @ordered
	 */
	protected EList cases = null;

	/**
	 * The cached value of the '{@link #getOtherwise() <em>Otherwise</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOtherwise()
	 * @generated
	 * @ordered
	 */
	protected Otherwise otherwise = null;

	/**
	 * This is true if the Otherwise containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean otherwiseESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SwitchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getSwitch();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getCases() {
		if (cases == null) {
			cases = new EObjectContainmentEList(Case.class, this, BPELPackage.SWITCH__CASES);
		}
		return cases;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Otherwise getOtherwise() {
		return otherwise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOtherwise(Otherwise newOtherwise, NotificationChain msgs) {
		Otherwise oldOtherwise = otherwise;
		otherwise = newOtherwise;
		boolean oldOtherwiseESet = otherwiseESet;
		otherwiseESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.SWITCH__OTHERWISE, oldOtherwise, newOtherwise, !oldOtherwiseESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOtherwise(Otherwise newOtherwise) {
		if (newOtherwise != otherwise) {
			NotificationChain msgs = null;
			if (otherwise != null)
				msgs = ((InternalEObject)otherwise).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.SWITCH__OTHERWISE, null, msgs);
			if (newOtherwise != null)
				msgs = ((InternalEObject)newOtherwise).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.SWITCH__OTHERWISE, null, msgs);
			msgs = basicSetOtherwise(newOtherwise, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldOtherwiseESet = otherwiseESet;
			otherwiseESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.SWITCH__OTHERWISE, newOtherwise, newOtherwise, !oldOtherwiseESet));
    	}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicUnsetOtherwise(NotificationChain msgs) {
		Otherwise oldOtherwise = otherwise;
		otherwise = null;
		boolean oldOtherwiseESet = otherwiseESet;
		otherwiseESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, BPELPackage.SWITCH__OTHERWISE, oldOtherwise, null, oldOtherwiseESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOtherwise() {
		if (otherwise != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)otherwise).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.SWITCH__OTHERWISE, null, msgs);
			msgs = basicUnsetOtherwise(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldOtherwiseESet = otherwiseESet;
			otherwiseESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.SWITCH__OTHERWISE, null, null, oldOtherwiseESet));
    	}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOtherwise() {
		return otherwiseESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.SWITCH__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.SWITCH__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.SWITCH__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.SWITCH__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.SWITCH__CASES:
					return ((InternalEList)getCases()).basicRemove(otherEnd, msgs);
				case BPELPackage.SWITCH__OTHERWISE:
					return basicUnsetOtherwise(msgs);
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
			case BPELPackage.SWITCH__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.SWITCH__ELEMENT:
				return getElement();
			case BPELPackage.SWITCH__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.SWITCH__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.SWITCH__NAME:
				return getName();
			case BPELPackage.SWITCH__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.SWITCH__TARGETS:
				return getTargets();
			case BPELPackage.SWITCH__SOURCES:
				return getSources();
			case BPELPackage.SWITCH__CASES:
				return getCases();
			case BPELPackage.SWITCH__OTHERWISE:
				return getOtherwise();
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
			case BPELPackage.SWITCH__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.SWITCH__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.SWITCH__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.SWITCH__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.SWITCH__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.SWITCH__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.SWITCH__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.SWITCH__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.SWITCH__CASES:
				getCases().clear();
				getCases().addAll((Collection)newValue);
				return;
			case BPELPackage.SWITCH__OTHERWISE:
				setOtherwise((Otherwise)newValue);
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
			case BPELPackage.SWITCH__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.SWITCH__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.SWITCH__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.SWITCH__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.SWITCH__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.SWITCH__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.SWITCH__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.SWITCH__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.SWITCH__CASES:
				getCases().clear();
				return;
			case BPELPackage.SWITCH__OTHERWISE:
				unsetOtherwise();
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
			case BPELPackage.SWITCH__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.SWITCH__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.SWITCH__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.SWITCH__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.SWITCH__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.SWITCH__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.SWITCH__TARGETS:
				return targets != null;
			case BPELPackage.SWITCH__SOURCES:
				return sources != null;
			case BPELPackage.SWITCH__CASES:
				return cases != null && !cases.isEmpty();
			case BPELPackage.SWITCH__OTHERWISE:
				return isSetOtherwise();
		}
		return eDynamicIsSet(eFeature);
	}

} //SwitchImpl
