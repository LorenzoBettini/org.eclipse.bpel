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
 * $Id: CatchImpl.java,v 1.1 2005/11/29 18:50:24 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.Variable;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.wst.wsdl.Message;

import org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Catch</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.CatchImpl#getFaultName <em>Fault Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CatchImpl#getFaultVariable <em>Fault Variable</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CatchImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CatchImpl#getFaultMessageType <em>Fault Message Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CatchImpl extends ExtensibleElementImpl implements Catch {
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
	 * The cached value of the '{@link #getFaultVariable() <em>Fault Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable faultVariable = null;

	/**
	 * The cached value of the '{@link #getActivity() <em>Activity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivity()
	 * @generated
	 * @ordered
	 */
	protected Activity activity = null;

	/**
	 * The cached value of the '{@link #getFaultMessageType() <em>Fault Message Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultMessageType()
	 * @generated
	 * @ordered
	 */
	protected Message faultMessageType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getCatch();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.CATCH__FAULT_NAME, oldFaultName, faultName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getFaultVariable() {
		return faultVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFaultVariable(Variable newFaultVariable, NotificationChain msgs) {
		Variable oldFaultVariable = faultVariable;
		faultVariable = newFaultVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.CATCH__FAULT_VARIABLE, oldFaultVariable, newFaultVariable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFaultVariable(Variable newFaultVariable) {
		if (newFaultVariable != faultVariable) {
			NotificationChain msgs = null;
			if (faultVariable != null)
				msgs = ((InternalEObject)faultVariable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.CATCH__FAULT_VARIABLE, null, msgs);
			if (newFaultVariable != null)
				msgs = ((InternalEObject)newFaultVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.CATCH__FAULT_VARIABLE, null, msgs);
			msgs = basicSetFaultVariable(newFaultVariable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.CATCH__FAULT_VARIABLE, newFaultVariable, newFaultVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivity(Activity newActivity, NotificationChain msgs) {
		Activity oldActivity = activity;
		activity = newActivity;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.CATCH__ACTIVITY, oldActivity, newActivity);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivity(Activity newActivity) {
		if (newActivity != activity) {
			NotificationChain msgs = null;
			if (activity != null)
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.CATCH__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.CATCH__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.CATCH__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @customized
	 */
	public Message getFaultMessageType() {
		if (faultVariable != null && faultVariable instanceof Variable) {
			return ((Variable)faultVariable).getMessageType();
		}
		if (faultMessageType != null && faultMessageType.eIsProxy()) {
			Message oldFaultMessageType = faultMessageType;
			faultMessageType = (Message)eResolveProxy((InternalEObject)faultMessageType);
			if (faultMessageType != oldFaultMessageType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.CATCH__FAULT_MESSAGE_TYPE, oldFaultMessageType, faultMessageType));
			}
		}
		return faultMessageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message basicGetFaultMessageType() {
		return faultMessageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @customized
	 */
	public void setFaultMessageType(Message newFaultMessageType) {
		Variable variable = getFaultVariable();
		if (variable != null && variable instanceof Variable) {
			((Variable)variable).setMessageType(newFaultMessageType);
		}
		Message oldFaultMessageType = faultMessageType;
		faultMessageType = newFaultMessageType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.CATCH__FAULT_MESSAGE_TYPE, oldFaultMessageType, faultMessageType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.CATCH__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.CATCH__FAULT_VARIABLE:
					return basicSetFaultVariable(null, msgs);
				case BPELPackage.CATCH__ACTIVITY:
					return basicSetActivity(null, msgs);
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
			case BPELPackage.CATCH__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.CATCH__ELEMENT:
				return getElement();
			case BPELPackage.CATCH__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.CATCH__FAULT_NAME:
				return getFaultName();
			case BPELPackage.CATCH__FAULT_VARIABLE:
				return getFaultVariable();
			case BPELPackage.CATCH__ACTIVITY:
				return getActivity();
			case BPELPackage.CATCH__FAULT_MESSAGE_TYPE:
				if (resolve) return getFaultMessageType();
				return basicGetFaultMessageType();
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
			case BPELPackage.CATCH__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.CATCH__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.CATCH__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.CATCH__FAULT_NAME:
				setFaultName((QName)newValue);
				return;
			case BPELPackage.CATCH__FAULT_VARIABLE:
				setFaultVariable((Variable)newValue);
				return;
			case BPELPackage.CATCH__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case BPELPackage.CATCH__FAULT_MESSAGE_TYPE:
				setFaultMessageType((Message)newValue);
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
			case BPELPackage.CATCH__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.CATCH__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.CATCH__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.CATCH__FAULT_NAME:
				setFaultName(FAULT_NAME_EDEFAULT);
				return;
			case BPELPackage.CATCH__FAULT_VARIABLE:
				setFaultVariable((Variable)null);
				return;
			case BPELPackage.CATCH__ACTIVITY:
				setActivity((Activity)null);
				return;
			case BPELPackage.CATCH__FAULT_MESSAGE_TYPE:
				setFaultMessageType((Message)null);
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
			case BPELPackage.CATCH__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.CATCH__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.CATCH__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.CATCH__FAULT_NAME:
				return FAULT_NAME_EDEFAULT == null ? faultName != null : !FAULT_NAME_EDEFAULT.equals(faultName);
			case BPELPackage.CATCH__FAULT_VARIABLE:
				return faultVariable != null;
			case BPELPackage.CATCH__ACTIVITY:
				return activity != null;
			case BPELPackage.CATCH__FAULT_MESSAGE_TYPE:
				return faultMessageType != null;
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

} //CatchImpl
