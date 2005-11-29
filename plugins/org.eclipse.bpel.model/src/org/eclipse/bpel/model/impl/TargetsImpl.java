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
 * $Id: TargetsImpl.java,v 1.1 2005/11/29 18:50:24 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.Targets;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Targets</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.TargetsImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.TargetsImpl#getJoinCondition <em>Join Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TargetsImpl extends EObjectImpl implements Targets {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList children = null;

	/**
	 * The cached value of the '{@link #getJoinCondition() <em>Join Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition joinCondition = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TargetsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getTargets();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList(Target.class, this, BPELPackage.TARGETS__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getJoinCondition() {
		return joinCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinCondition(Condition newJoinCondition, NotificationChain msgs) {
		Condition oldJoinCondition = joinCondition;
		joinCondition = newJoinCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.TARGETS__JOIN_CONDITION, oldJoinCondition, newJoinCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJoinCondition(Condition newJoinCondition) {
		if (newJoinCondition != joinCondition) {
			NotificationChain msgs = null;
			if (joinCondition != null)
				msgs = ((InternalEObject)joinCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.TARGETS__JOIN_CONDITION, null, msgs);
			if (newJoinCondition != null)
				msgs = ((InternalEObject)newJoinCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.TARGETS__JOIN_CONDITION, null, msgs);
			msgs = basicSetJoinCondition(newJoinCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.TARGETS__JOIN_CONDITION, newJoinCondition, newJoinCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.TARGETS__CHILDREN:
					return ((InternalEList)getChildren()).basicRemove(otherEnd, msgs);
				case BPELPackage.TARGETS__JOIN_CONDITION:
					return basicSetJoinCondition(null, msgs);
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
			case BPELPackage.TARGETS__CHILDREN:
				return getChildren();
			case BPELPackage.TARGETS__JOIN_CONDITION:
				return getJoinCondition();
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
			case BPELPackage.TARGETS__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection)newValue);
				return;
			case BPELPackage.TARGETS__JOIN_CONDITION:
				setJoinCondition((Condition)newValue);
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
			case BPELPackage.TARGETS__CHILDREN:
				getChildren().clear();
				return;
			case BPELPackage.TARGETS__JOIN_CONDITION:
				setJoinCondition((Condition)null);
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
			case BPELPackage.TARGETS__CHILDREN:
				return children != null && !children.isEmpty();
			case BPELPackage.TARGETS__JOIN_CONDITION:
				return joinCondition != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //TargetsImpl
