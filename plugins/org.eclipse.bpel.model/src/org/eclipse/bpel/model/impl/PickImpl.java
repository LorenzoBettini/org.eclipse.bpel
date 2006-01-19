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
 * $Id: PickImpl.java,v 1.2 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.OnAlarm;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.Pick;
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
 * An implementation of the model object '<em><b>Pick</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.PickImpl#getCreateInstance <em>Create Instance</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PickImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PickImpl#getAlarm <em>Alarm</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PickImpl extends ActivityImpl implements Pick {
	/**
	 * The default value of the '{@link #getCreateInstance() <em>Create Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateInstance()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CREATE_INSTANCE_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getCreateInstance() <em>Create Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateInstance()
	 * @generated
	 * @ordered
	 */
	protected Boolean createInstance = CREATE_INSTANCE_EDEFAULT;

	/**
	 * This is true if the Create Instance attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean createInstanceESet = false;

	/**
	 * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessages()
	 * @generated
	 * @ordered
	 */
	protected EList messages = null;

	/**
	 * The cached value of the '{@link #getAlarm() <em>Alarm</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlarm()
	 * @generated
	 * @ordered
	 */
	protected EList alarm = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PickImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getPick();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getCreateInstance() {
		return createInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreateInstance(Boolean newCreateInstance) {
		Boolean oldCreateInstance = createInstance;
		createInstance = newCreateInstance;
		boolean oldCreateInstanceESet = createInstanceESet;
		createInstanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PICK__CREATE_INSTANCE, oldCreateInstance, createInstance, !oldCreateInstanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCreateInstance() {
		Boolean oldCreateInstance = createInstance;
		boolean oldCreateInstanceESet = createInstanceESet;
		createInstance = CREATE_INSTANCE_EDEFAULT;
		createInstanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.PICK__CREATE_INSTANCE, oldCreateInstance, CREATE_INSTANCE_EDEFAULT, oldCreateInstanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCreateInstance() {
		return createInstanceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMessages() {
		if (messages == null) {
			messages = new EObjectContainmentEList(OnMessage.class, this, BPELPackage.PICK__MESSAGES);
		}
		return messages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAlarm() {
		if (alarm == null) {
			alarm = new EObjectContainmentEList(OnAlarm.class, this, BPELPackage.PICK__ALARM);
		}
		return alarm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.PICK__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.PICK__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.PICK__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.PICK__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.PICK__MESSAGES:
					return ((InternalEList)getMessages()).basicRemove(otherEnd, msgs);
				case BPELPackage.PICK__ALARM:
					return ((InternalEList)getAlarm()).basicRemove(otherEnd, msgs);
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
			case BPELPackage.PICK__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.PICK__ELEMENT:
				return getElement();
			case BPELPackage.PICK__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.PICK__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.PICK__NAME:
				return getName();
			case BPELPackage.PICK__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.PICK__TARGETS:
				return getTargets();
			case BPELPackage.PICK__SOURCES:
				return getSources();
			case BPELPackage.PICK__CREATE_INSTANCE:
				return getCreateInstance();
			case BPELPackage.PICK__MESSAGES:
				return getMessages();
			case BPELPackage.PICK__ALARM:
				return getAlarm();
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
			case BPELPackage.PICK__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.PICK__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.PICK__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.PICK__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.PICK__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.PICK__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.PICK__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.PICK__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.PICK__CREATE_INSTANCE:
				setCreateInstance((Boolean)newValue);
				return;
			case BPELPackage.PICK__MESSAGES:
				getMessages().clear();
				getMessages().addAll((Collection)newValue);
				return;
			case BPELPackage.PICK__ALARM:
				getAlarm().clear();
				getAlarm().addAll((Collection)newValue);
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
			case BPELPackage.PICK__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PICK__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PICK__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.PICK__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.PICK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.PICK__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.PICK__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.PICK__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.PICK__CREATE_INSTANCE:
				unsetCreateInstance();
				return;
			case BPELPackage.PICK__MESSAGES:
				getMessages().clear();
				return;
			case BPELPackage.PICK__ALARM:
				getAlarm().clear();
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
			case BPELPackage.PICK__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.PICK__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.PICK__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.PICK__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.PICK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.PICK__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.PICK__TARGETS:
				return targets != null;
			case BPELPackage.PICK__SOURCES:
				return sources != null;
			case BPELPackage.PICK__CREATE_INSTANCE:
				return isSetCreateInstance();
			case BPELPackage.PICK__MESSAGES:
				return messages != null && !messages.isEmpty();
			case BPELPackage.PICK__ALARM:
				return alarm != null && !alarm.isEmpty();
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
		result.append(" (createInstance: ");
		if (createInstanceESet) result.append(createInstance); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PickImpl
