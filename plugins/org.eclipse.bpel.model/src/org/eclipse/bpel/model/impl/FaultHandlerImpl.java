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
 * $Id: FaultHandlerImpl.java,v 1.1 2005/11/29 18:50:24 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.CatchAll;
import org.eclipse.bpel.model.FaultHandler;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fault Handler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.FaultHandlerImpl#getCatch <em>Catch</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FaultHandlerImpl#getCatchAll <em>Catch All</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FaultHandlerImpl extends ExtensibleElementImpl implements FaultHandler {
	/**
	 * The cached value of the '{@link #getCatch() <em>Catch</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatch()
	 * @generated
	 * @ordered
	 */
	protected EList catch_ = null;

	/**
	 * The cached value of the '{@link #getCatchAll() <em>Catch All</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCatchAll()
	 * @generated
	 * @ordered
	 */
	protected CatchAll catchAll = null;

	/**
	 * This is true if the Catch All containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean catchAllESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FaultHandlerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getFaultHandler();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getCatch() {
		if (catch_ == null) {
			catch_ = new EObjectContainmentEList(Catch.class, this, BPELPackage.FAULT_HANDLER__CATCH);
		}
		return catch_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CatchAll getCatchAll() {
		return catchAll;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCatchAll(CatchAll newCatchAll, NotificationChain msgs) {
		CatchAll oldCatchAll = catchAll;
		catchAll = newCatchAll;
		boolean oldCatchAllESet = catchAllESet;
		catchAllESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FAULT_HANDLER__CATCH_ALL, oldCatchAll, newCatchAll, !oldCatchAllESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCatchAll(CatchAll newCatchAll) {
		if (newCatchAll != catchAll) {
			NotificationChain msgs = null;
			if (catchAll != null)
				msgs = ((InternalEObject)catchAll).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FAULT_HANDLER__CATCH_ALL, null, msgs);
			if (newCatchAll != null)
				msgs = ((InternalEObject)newCatchAll).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FAULT_HANDLER__CATCH_ALL, null, msgs);
			msgs = basicSetCatchAll(newCatchAll, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldCatchAllESet = catchAllESet;
			catchAllESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FAULT_HANDLER__CATCH_ALL, newCatchAll, newCatchAll, !oldCatchAllESet));
    	}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicUnsetCatchAll(NotificationChain msgs) {
		CatchAll oldCatchAll = catchAll;
		catchAll = null;
		boolean oldCatchAllESet = catchAllESet;
		catchAllESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, BPELPackage.FAULT_HANDLER__CATCH_ALL, oldCatchAll, null, oldCatchAllESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCatchAll() {
		if (catchAll != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)catchAll).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FAULT_HANDLER__CATCH_ALL, null, msgs);
			msgs = basicUnsetCatchAll(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldCatchAllESet = catchAllESet;
			catchAllESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.FAULT_HANDLER__CATCH_ALL, null, null, oldCatchAllESet));
    	}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCatchAll() {
		return catchAllESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.FAULT_HANDLER__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.FAULT_HANDLER__CATCH:
					return ((InternalEList)getCatch()).basicRemove(otherEnd, msgs);
				case BPELPackage.FAULT_HANDLER__CATCH_ALL:
					return basicUnsetCatchAll(msgs);
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
			case BPELPackage.FAULT_HANDLER__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.FAULT_HANDLER__ELEMENT:
				return getElement();
			case BPELPackage.FAULT_HANDLER__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.FAULT_HANDLER__CATCH:
				return getCatch();
			case BPELPackage.FAULT_HANDLER__CATCH_ALL:
				return getCatchAll();
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
			case BPELPackage.FAULT_HANDLER__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.FAULT_HANDLER__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.FAULT_HANDLER__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.FAULT_HANDLER__CATCH:
				getCatch().clear();
				getCatch().addAll((Collection)newValue);
				return;
			case BPELPackage.FAULT_HANDLER__CATCH_ALL:
				setCatchAll((CatchAll)newValue);
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
			case BPELPackage.FAULT_HANDLER__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FAULT_HANDLER__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FAULT_HANDLER__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.FAULT_HANDLER__CATCH:
				getCatch().clear();
				return;
			case BPELPackage.FAULT_HANDLER__CATCH_ALL:
				unsetCatchAll();
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
			case BPELPackage.FAULT_HANDLER__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.FAULT_HANDLER__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.FAULT_HANDLER__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.FAULT_HANDLER__CATCH:
				return catch_ != null && !catch_.isEmpty();
			case BPELPackage.FAULT_HANDLER__CATCH_ALL:
				return isSetCatchAll();
		}
		return eDynamicIsSet(eFeature);
	}

} //FaultHandlerImpl
