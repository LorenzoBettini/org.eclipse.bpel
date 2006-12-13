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
 * $Id: CopyImpl.java,v 1.4 2006/12/13 16:17:31 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
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
 * An implementation of the model object '<em><b>Copy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.CopyImpl#getTo <em>To</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CopyImpl#getFrom <em>From</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CopyImpl#getKeepSrcElementName <em>Keep Src Element Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CopyImpl#getIgnoreMissingFromData <em>Ignore Missing From Data</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CopyImpl extends ExtensibleElementImpl implements Copy {
	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected To to = null;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected From from = null;

	/**
	 * The default value of the '{@link #getKeepSrcElementName() <em>Keep Src Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeepSrcElementName()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean KEEP_SRC_ELEMENT_NAME_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getKeepSrcElementName() <em>Keep Src Element Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeepSrcElementName()
	 * @generated
	 * @ordered
	 */
	protected Boolean keepSrcElementName = KEEP_SRC_ELEMENT_NAME_EDEFAULT;

	/**
	 * This is true if the Keep Src Element Name attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean keepSrcElementNameESet = false;

	/**
	 * The default value of the '{@link #getIgnoreMissingFromData() <em>Ignore Missing From Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIgnoreMissingFromData()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean IGNORE_MISSING_FROM_DATA_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getIgnoreMissingFromData() <em>Ignore Missing From Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIgnoreMissingFromData()
	 * @generated
	 * @ordered
	 */
	protected Boolean ignoreMissingFromData = IGNORE_MISSING_FROM_DATA_EDEFAULT;

	/**
	 * This is true if the Ignore Missing From Data attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreMissingFromDataESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CopyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getCopy();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public To getTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTo(To newTo, NotificationChain msgs) {
		To oldTo = to;
		to = newTo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.COPY__TO, oldTo, newTo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(To newTo) {
		if (newTo != to) {
			NotificationChain msgs = null;
			if (to != null)
				msgs = ((InternalEObject)to).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COPY__TO, null, msgs);
			if (newTo != null)
				msgs = ((InternalEObject)newTo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COPY__TO, null, msgs);
			msgs = basicSetTo(newTo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.COPY__TO, newTo, newTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public From getFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFrom(From newFrom, NotificationChain msgs) {
		From oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.COPY__FROM, oldFrom, newFrom);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(From newFrom) {
		if (newFrom != from) {
			NotificationChain msgs = null;
			if (from != null)
				msgs = ((InternalEObject)from).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COPY__FROM, null, msgs);
			if (newFrom != null)
				msgs = ((InternalEObject)newFrom).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COPY__FROM, null, msgs);
			msgs = basicSetFrom(newFrom, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.COPY__FROM, newFrom, newFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getKeepSrcElementName() {
		return keepSrcElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeepSrcElementName(Boolean newKeepSrcElementName) {
		Boolean oldKeepSrcElementName = keepSrcElementName;
		keepSrcElementName = newKeepSrcElementName;
		boolean oldKeepSrcElementNameESet = keepSrcElementNameESet;
		keepSrcElementNameESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.COPY__KEEP_SRC_ELEMENT_NAME, oldKeepSrcElementName, keepSrcElementName, !oldKeepSrcElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetKeepSrcElementName() {
		Boolean oldKeepSrcElementName = keepSrcElementName;
		boolean oldKeepSrcElementNameESet = keepSrcElementNameESet;
		keepSrcElementName = KEEP_SRC_ELEMENT_NAME_EDEFAULT;
		keepSrcElementNameESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.COPY__KEEP_SRC_ELEMENT_NAME, oldKeepSrcElementName, KEEP_SRC_ELEMENT_NAME_EDEFAULT, oldKeepSrcElementNameESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetKeepSrcElementName() {
		return keepSrcElementNameESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getIgnoreMissingFromData() {
		return ignoreMissingFromData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIgnoreMissingFromData(Boolean newIgnoreMissingFromData) {
		Boolean oldIgnoreMissingFromData = ignoreMissingFromData;
		ignoreMissingFromData = newIgnoreMissingFromData;
		boolean oldIgnoreMissingFromDataESet = ignoreMissingFromDataESet;
		ignoreMissingFromDataESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.COPY__IGNORE_MISSING_FROM_DATA, oldIgnoreMissingFromData, ignoreMissingFromData, !oldIgnoreMissingFromDataESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIgnoreMissingFromData() {
		Boolean oldIgnoreMissingFromData = ignoreMissingFromData;
		boolean oldIgnoreMissingFromDataESet = ignoreMissingFromDataESet;
		ignoreMissingFromData = IGNORE_MISSING_FROM_DATA_EDEFAULT;
		ignoreMissingFromDataESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.COPY__IGNORE_MISSING_FROM_DATA, oldIgnoreMissingFromData, IGNORE_MISSING_FROM_DATA_EDEFAULT, oldIgnoreMissingFromDataESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIgnoreMissingFromData() {
		return ignoreMissingFromDataESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.COPY__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.COPY__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.COPY__TO:
					return basicSetTo(null, msgs);
				case BPELPackage.COPY__FROM:
					return basicSetFrom(null, msgs);
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
			case BPELPackage.COPY__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.COPY__ELEMENT:
				return getElement();
			case BPELPackage.COPY__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.COPY__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.COPY__TO:
				return getTo();
			case BPELPackage.COPY__FROM:
				return getFrom();
			case BPELPackage.COPY__KEEP_SRC_ELEMENT_NAME:
				return getKeepSrcElementName();
			case BPELPackage.COPY__IGNORE_MISSING_FROM_DATA:
				return getIgnoreMissingFromData();
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
			case BPELPackage.COPY__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.COPY__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.COPY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.COPY__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.COPY__TO:
				setTo((To)newValue);
				return;
			case BPELPackage.COPY__FROM:
				setFrom((From)newValue);
				return;
			case BPELPackage.COPY__KEEP_SRC_ELEMENT_NAME:
				setKeepSrcElementName((Boolean)newValue);
				return;
			case BPELPackage.COPY__IGNORE_MISSING_FROM_DATA:
				setIgnoreMissingFromData((Boolean)newValue);
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
			case BPELPackage.COPY__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.COPY__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.COPY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.COPY__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.COPY__TO:
				setTo((To)null);
				return;
			case BPELPackage.COPY__FROM:
				setFrom((From)null);
				return;
			case BPELPackage.COPY__KEEP_SRC_ELEMENT_NAME:
				unsetKeepSrcElementName();
				return;
			case BPELPackage.COPY__IGNORE_MISSING_FROM_DATA:
				unsetIgnoreMissingFromData();
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
			case BPELPackage.COPY__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.COPY__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.COPY__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.COPY__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.COPY__TO:
				return to != null;
			case BPELPackage.COPY__FROM:
				return from != null;
			case BPELPackage.COPY__KEEP_SRC_ELEMENT_NAME:
				return isSetKeepSrcElementName();
			case BPELPackage.COPY__IGNORE_MISSING_FROM_DATA:
				return isSetIgnoreMissingFromData();
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
		result.append(" (keepSrcElementName: ");
		if (keepSrcElementNameESet) result.append(keepSrcElementName); else result.append("<unset>");
		result.append(", ignoreMissingFromData: ");
		if (ignoreMissingFromDataESet) result.append(ignoreMissingFromData); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //CopyImpl
