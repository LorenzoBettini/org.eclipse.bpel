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
 * $Id: TargetsImpl.java,v 1.5 2007/08/01 21:02:31 mchmielewski Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Target;
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
public class TargetsImpl extends ExtensibleElementImpl implements Targets {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Target> children;

	/**
	 * The cached value of the '{@link #getJoinCondition() <em>Join Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition joinCondition;

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
	@Override
	protected EClass eStaticClass() {
		return BPELPackage.Literals.TARGETS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Target> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<Target>(Target.class, this,
					BPELPackage.TARGETS__CHILDREN);
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
	public NotificationChain basicSetJoinCondition(Condition newJoinCondition,
			NotificationChain msgs) {
		Condition oldJoinCondition = joinCondition;
		joinCondition = newJoinCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, BPELPackage.TARGETS__JOIN_CONDITION,
					oldJoinCondition, newJoinCondition);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
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
				msgs = ((InternalEObject) joinCondition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- BPELPackage.TARGETS__JOIN_CONDITION, null,
						msgs);
			if (newJoinCondition != null)
				msgs = ((InternalEObject) newJoinCondition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- BPELPackage.TARGETS__JOIN_CONDITION, null,
						msgs);
			msgs = basicSetJoinCondition(newJoinCondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BPELPackage.TARGETS__JOIN_CONDITION, newJoinCondition,
					newJoinCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BPELPackage.TARGETS__CHILDREN:
				return ((InternalEList<?>) getChildren()).basicRemove(otherEnd,
						msgs);
			case BPELPackage.TARGETS__JOIN_CONDITION:
				return basicSetJoinCondition(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BPELPackage.TARGETS__CHILDREN:
				return getChildren();
			case BPELPackage.TARGETS__JOIN_CONDITION:
				return getJoinCondition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BPELPackage.TARGETS__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Target>) newValue);
				return;
			case BPELPackage.TARGETS__JOIN_CONDITION:
				setJoinCondition((Condition) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BPELPackage.TARGETS__CHILDREN:
				getChildren().clear();
				return;
			case BPELPackage.TARGETS__JOIN_CONDITION:
				setJoinCondition((Condition) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BPELPackage.TARGETS__CHILDREN:
				return children != null && !children.isEmpty();
			case BPELPackage.TARGETS__JOIN_CONDITION:
				return joinCondition != null;
		}
		return super.eIsSet(featureID);
	}

} //TargetsImpl
