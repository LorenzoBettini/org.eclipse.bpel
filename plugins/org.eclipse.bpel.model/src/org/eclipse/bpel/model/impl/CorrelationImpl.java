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
 * $Id: CorrelationImpl.java,v 1.3 2006/12/13 16:17:31 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.model.CorrelationPattern;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Correlation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.CorrelationImpl#getInitiate <em>Initiate</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CorrelationImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CorrelationImpl#getSet <em>Set</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CorrelationImpl extends ExtensibleElementImpl implements Correlation {
	/**
	 * The default value of the '{@link #getInitiate() <em>Initiate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitiate()
	 * @generated
	 * @ordered
	 */
	protected static final String INITIATE_EDEFAULT = "no";

	/**
	 * The cached value of the '{@link #getInitiate() <em>Initiate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitiate()
	 * @generated
	 * @ordered
	 */
	protected String initiate = INITIATE_EDEFAULT;

	/**
	 * This is true if the Initiate attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean initiateESet = false;

	/**
	 * The default value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected static final CorrelationPattern PATTERN_EDEFAULT = CorrelationPattern.IN_LITERAL;

	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected CorrelationPattern pattern = PATTERN_EDEFAULT;

	/**
	 * This is true if the Pattern attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean patternESet = false;

	/**
	 * The cached value of the '{@link #getSet() <em>Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSet()
	 * @generated
	 * @ordered
	 */
	protected CorrelationSet set = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CorrelationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getCorrelation();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInitiate() {
		return initiate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitiate(String newInitiate) {
		String oldInitiate = initiate;
		initiate = newInitiate;
		boolean oldInitiateESet = initiateESet;
		initiateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.CORRELATION__INITIATE, oldInitiate, initiate, !oldInitiateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInitiate() {
		String oldInitiate = initiate;
		boolean oldInitiateESet = initiateESet;
		initiate = INITIATE_EDEFAULT;
		initiateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.CORRELATION__INITIATE, oldInitiate, INITIATE_EDEFAULT, oldInitiateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInitiate() {
		return initiateESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrelationPattern getPattern() {
		return pattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPattern(CorrelationPattern newPattern) {
		CorrelationPattern oldPattern = pattern;
		pattern = newPattern == null ? PATTERN_EDEFAULT : newPattern;
		boolean oldPatternESet = patternESet;
		patternESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.CORRELATION__PATTERN, oldPattern, pattern, !oldPatternESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPattern() {
		CorrelationPattern oldPattern = pattern;
		boolean oldPatternESet = patternESet;
		pattern = PATTERN_EDEFAULT;
		patternESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.CORRELATION__PATTERN, oldPattern, PATTERN_EDEFAULT, oldPatternESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPattern() {
		return patternESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrelationSet getSet() {
		if (set != null && set.eIsProxy()) {
			CorrelationSet oldSet = set;
			set = (CorrelationSet)eResolveProxy((InternalEObject)set);
			if (set != oldSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.CORRELATION__SET, oldSet, set));
			}
		}
		return set;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrelationSet basicGetSet() {
		return set;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSet(CorrelationSet newSet) {
		CorrelationSet oldSet = set;
		set = newSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.CORRELATION__SET, oldSet, set));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.CORRELATION__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.CORRELATION__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
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
			case BPELPackage.CORRELATION__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.CORRELATION__ELEMENT:
				return getElement();
			case BPELPackage.CORRELATION__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.CORRELATION__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.CORRELATION__INITIATE:
				return getInitiate();
			case BPELPackage.CORRELATION__PATTERN:
				return getPattern();
			case BPELPackage.CORRELATION__SET:
				if (resolve) return getSet();
				return basicGetSet();
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
			case BPELPackage.CORRELATION__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.CORRELATION__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.CORRELATION__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.CORRELATION__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.CORRELATION__INITIATE:
				setInitiate((String)newValue);
				return;
			case BPELPackage.CORRELATION__PATTERN:
				setPattern((CorrelationPattern)newValue);
				return;
			case BPELPackage.CORRELATION__SET:
				setSet((CorrelationSet)newValue);
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
			case BPELPackage.CORRELATION__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.CORRELATION__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.CORRELATION__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.CORRELATION__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.CORRELATION__INITIATE:
				unsetInitiate();
				return;
			case BPELPackage.CORRELATION__PATTERN:
				unsetPattern();
				return;
			case BPELPackage.CORRELATION__SET:
				setSet((CorrelationSet)null);
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
			case BPELPackage.CORRELATION__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.CORRELATION__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.CORRELATION__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.CORRELATION__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.CORRELATION__INITIATE:
				return isSetInitiate();
			case BPELPackage.CORRELATION__PATTERN:
				return isSetPattern();
			case BPELPackage.CORRELATION__SET:
				return set != null;
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
		result.append(" (initiate: ");
		if (initiateESet) result.append(initiate); else result.append("<unset>");
		result.append(", pattern: ");
		if (patternESet) result.append(pattern); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //CorrelationImpl
