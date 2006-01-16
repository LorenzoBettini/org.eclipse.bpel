/**
 * <copyright>
 * </copyright>
 *
 * $Id: CompletionConditionImpl.java,v 1.1 2006/01/16 19:47:37 james Exp $
 */
package org.eclipse.bpel.model.impl;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.BooleanExpression;
import org.eclipse.bpel.model.Branches;
import org.eclipse.bpel.model.CompletionCondition;

import org.eclipse.bpel.model.Expression;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Completion Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.CompletionConditionImpl#getBranches <em>Branches</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.CompletionConditionImpl#getBooleanExpression <em>Boolean Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompletionConditionImpl extends EObjectImpl implements CompletionCondition {
	/**
	 * The cached value of the '{@link #getBranches() <em>Branches</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBranches()
	 * @generated
	 * @ordered
	 */
	protected Branches branches = null;

	/**
	 * The cached value of the '{@link #getBooleanExpression() <em>Boolean Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBooleanExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression booleanExpression = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompletionConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getCompletionCondition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Branches getBranches() {
		return branches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBranches(Branches newBranches, NotificationChain msgs) {
		Branches oldBranches = branches;
		branches = newBranches;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.COMPLETION_CONDITION__BRANCHES, oldBranches, newBranches);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBranches(Branches newBranches) {
		if (newBranches != branches) {
			NotificationChain msgs = null;
			if (branches != null)
				msgs = ((InternalEObject)branches).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COMPLETION_CONDITION__BRANCHES, null, msgs);
			if (newBranches != null)
				msgs = ((InternalEObject)newBranches).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COMPLETION_CONDITION__BRANCHES, null, msgs);
			msgs = basicSetBranches(newBranches, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.COMPLETION_CONDITION__BRANCHES, newBranches, newBranches));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getBooleanExpression() {
		return booleanExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBooleanExpression(Expression newBooleanExpression, NotificationChain msgs) {
		Expression oldBooleanExpression = booleanExpression;
		booleanExpression = newBooleanExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION, oldBooleanExpression, newBooleanExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanExpression(Expression newBooleanExpression) {
		if (newBooleanExpression != booleanExpression) {
			NotificationChain msgs = null;
			if (booleanExpression != null)
				msgs = ((InternalEObject)booleanExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION, null, msgs);
			if (newBooleanExpression != null)
				msgs = ((InternalEObject)newBooleanExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION, null, msgs);
			msgs = basicSetBooleanExpression(newBooleanExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION, newBooleanExpression, newBooleanExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.COMPLETION_CONDITION__BRANCHES:
					return basicSetBranches(null, msgs);
				case BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION:
					return basicSetBooleanExpression(null, msgs);
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
			case BPELPackage.COMPLETION_CONDITION__BRANCHES:
				return getBranches();
			case BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION:
				return getBooleanExpression();
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
			case BPELPackage.COMPLETION_CONDITION__BRANCHES:
				setBranches((Branches)newValue);
				return;
			case BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION:
				setBooleanExpression((Expression)newValue);
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
			case BPELPackage.COMPLETION_CONDITION__BRANCHES:
				setBranches((Branches)null);
				return;
			case BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION:
				setBooleanExpression((Expression)null);
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
			case BPELPackage.COMPLETION_CONDITION__BRANCHES:
				return branches != null;
			case BPELPackage.COMPLETION_CONDITION__BOOLEAN_EXPRESSION:
				return booleanExpression != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //CompletionConditionImpl
