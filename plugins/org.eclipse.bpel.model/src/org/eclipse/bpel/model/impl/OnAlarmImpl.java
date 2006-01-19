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
 * $Id: OnAlarmImpl.java,v 1.2 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.OnAlarm;
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
 * An implementation of the model object '<em><b>On Alarm</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.OnAlarmImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnAlarmImpl#getFor <em>For</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnAlarmImpl#getUntil <em>Until</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnAlarmImpl#getRepeatEvery <em>Repeat Every</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OnAlarmImpl extends ExtensibleElementImpl implements OnAlarm {
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
	 * The cached value of the '{@link #getFor() <em>For</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFor()
	 * @generated
	 * @ordered
	 */
	protected Expression for_ = null;

	/**
	 * The cached value of the '{@link #getUntil() <em>Until</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUntil()
	 * @generated
	 * @ordered
	 */
	protected Expression until = null;

	/**
	 * The cached value of the '{@link #getRepeatEvery() <em>Repeat Every</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepeatEvery()
	 * @generated
	 * @ordered
	 */
	protected Expression repeatEvery = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OnAlarmImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getOnAlarm();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__ACTIVITY, oldActivity, newActivity);
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
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getFor() {
		return for_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFor(Expression newFor, NotificationChain msgs) {
		Expression oldFor = for_;
		for_ = newFor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__FOR, oldFor, newFor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFor(Expression newFor) {
		if (newFor != for_) {
			NotificationChain msgs = null;
			if (for_ != null)
				msgs = ((InternalEObject)for_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__FOR, null, msgs);
			if (newFor != null)
				msgs = ((InternalEObject)newFor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__FOR, null, msgs);
			msgs = basicSetFor(newFor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__FOR, newFor, newFor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getUntil() {
		return until;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUntil(Expression newUntil, NotificationChain msgs) {
		Expression oldUntil = until;
		until = newUntil;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__UNTIL, oldUntil, newUntil);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUntil(Expression newUntil) {
		if (newUntil != until) {
			NotificationChain msgs = null;
			if (until != null)
				msgs = ((InternalEObject)until).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__UNTIL, null, msgs);
			if (newUntil != null)
				msgs = ((InternalEObject)newUntil).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__UNTIL, null, msgs);
			msgs = basicSetUntil(newUntil, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__UNTIL, newUntil, newUntil));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getRepeatEvery() {
		return repeatEvery;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRepeatEvery(Expression newRepeatEvery, NotificationChain msgs) {
		Expression oldRepeatEvery = repeatEvery;
		repeatEvery = newRepeatEvery;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__REPEAT_EVERY, oldRepeatEvery, newRepeatEvery);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepeatEvery(Expression newRepeatEvery) {
		if (newRepeatEvery != repeatEvery) {
			NotificationChain msgs = null;
			if (repeatEvery != null)
				msgs = ((InternalEObject)repeatEvery).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__REPEAT_EVERY, null, msgs);
			if (newRepeatEvery != null)
				msgs = ((InternalEObject)newRepeatEvery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_ALARM__REPEAT_EVERY, null, msgs);
			msgs = basicSetRepeatEvery(newRepeatEvery, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_ALARM__REPEAT_EVERY, newRepeatEvery, newRepeatEvery));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.ON_ALARM__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.ON_ALARM__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.ON_ALARM__ACTIVITY:
					return basicSetActivity(null, msgs);
				case BPELPackage.ON_ALARM__FOR:
					return basicSetFor(null, msgs);
				case BPELPackage.ON_ALARM__UNTIL:
					return basicSetUntil(null, msgs);
				case BPELPackage.ON_ALARM__REPEAT_EVERY:
					return basicSetRepeatEvery(null, msgs);
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
			case BPELPackage.ON_ALARM__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.ON_ALARM__ELEMENT:
				return getElement();
			case BPELPackage.ON_ALARM__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.ON_ALARM__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.ON_ALARM__ACTIVITY:
				return getActivity();
			case BPELPackage.ON_ALARM__FOR:
				return getFor();
			case BPELPackage.ON_ALARM__UNTIL:
				return getUntil();
			case BPELPackage.ON_ALARM__REPEAT_EVERY:
				return getRepeatEvery();
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
			case BPELPackage.ON_ALARM__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.ON_ALARM__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.ON_ALARM__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.ON_ALARM__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.ON_ALARM__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case BPELPackage.ON_ALARM__FOR:
				setFor((Expression)newValue);
				return;
			case BPELPackage.ON_ALARM__UNTIL:
				setUntil((Expression)newValue);
				return;
			case BPELPackage.ON_ALARM__REPEAT_EVERY:
				setRepeatEvery((Expression)newValue);
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
			case BPELPackage.ON_ALARM__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ON_ALARM__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ON_ALARM__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.ON_ALARM__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.ON_ALARM__ACTIVITY:
				setActivity((Activity)null);
				return;
			case BPELPackage.ON_ALARM__FOR:
				setFor((Expression)null);
				return;
			case BPELPackage.ON_ALARM__UNTIL:
				setUntil((Expression)null);
				return;
			case BPELPackage.ON_ALARM__REPEAT_EVERY:
				setRepeatEvery((Expression)null);
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
			case BPELPackage.ON_ALARM__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.ON_ALARM__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.ON_ALARM__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.ON_ALARM__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.ON_ALARM__ACTIVITY:
				return activity != null;
			case BPELPackage.ON_ALARM__FOR:
				return for_ != null;
			case BPELPackage.ON_ALARM__UNTIL:
				return until != null;
			case BPELPackage.ON_ALARM__REPEAT_EVERY:
				return repeatEvery != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //OnAlarmImpl
