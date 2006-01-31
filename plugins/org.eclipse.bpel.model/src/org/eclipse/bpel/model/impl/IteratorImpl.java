/**
 * <copyright>
 * </copyright>
 *
 * $Id: IteratorImpl.java,v 1.1 2006/01/31 14:56:08 james Exp $
 */
package org.eclipse.bpel.model.impl;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Iterator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.IteratorImpl#getFinalCounterValue <em>Final Counter Value</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.IteratorImpl#getStartCounterValue <em>Start Counter Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IteratorImpl extends EObjectImpl implements Iterator {
	/**
	 * The cached value of the '{@link #getFinalCounterValue() <em>Final Counter Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFinalCounterValue()
	 * @generated
	 * @ordered
	 */
	protected Expression finalCounterValue = null;

	/**
	 * The cached value of the '{@link #getStartCounterValue() <em>Start Counter Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartCounterValue()
	 * @generated
	 * @ordered
	 */
	protected Expression startCounterValue = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IteratorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getIterator();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getFinalCounterValue() {
		return finalCounterValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFinalCounterValue(Expression newFinalCounterValue, NotificationChain msgs) {
		Expression oldFinalCounterValue = finalCounterValue;
		finalCounterValue = newFinalCounterValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ITERATOR__FINAL_COUNTER_VALUE, oldFinalCounterValue, newFinalCounterValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFinalCounterValue(Expression newFinalCounterValue) {
		if (newFinalCounterValue != finalCounterValue) {
			NotificationChain msgs = null;
			if (finalCounterValue != null)
				msgs = ((InternalEObject)finalCounterValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ITERATOR__FINAL_COUNTER_VALUE, null, msgs);
			if (newFinalCounterValue != null)
				msgs = ((InternalEObject)newFinalCounterValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ITERATOR__FINAL_COUNTER_VALUE, null, msgs);
			msgs = basicSetFinalCounterValue(newFinalCounterValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ITERATOR__FINAL_COUNTER_VALUE, newFinalCounterValue, newFinalCounterValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getStartCounterValue() {
		return startCounterValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartCounterValue(Expression newStartCounterValue, NotificationChain msgs) {
		Expression oldStartCounterValue = startCounterValue;
		startCounterValue = newStartCounterValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ITERATOR__START_COUNTER_VALUE, oldStartCounterValue, newStartCounterValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartCounterValue(Expression newStartCounterValue) {
		if (newStartCounterValue != startCounterValue) {
			NotificationChain msgs = null;
			if (startCounterValue != null)
				msgs = ((InternalEObject)startCounterValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ITERATOR__START_COUNTER_VALUE, null, msgs);
			if (newStartCounterValue != null)
				msgs = ((InternalEObject)newStartCounterValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ITERATOR__START_COUNTER_VALUE, null, msgs);
			msgs = basicSetStartCounterValue(newStartCounterValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ITERATOR__START_COUNTER_VALUE, newStartCounterValue, newStartCounterValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.ITERATOR__FINAL_COUNTER_VALUE:
					return basicSetFinalCounterValue(null, msgs);
				case BPELPackage.ITERATOR__START_COUNTER_VALUE:
					return basicSetStartCounterValue(null, msgs);
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
			case BPELPackage.ITERATOR__FINAL_COUNTER_VALUE:
				return getFinalCounterValue();
			case BPELPackage.ITERATOR__START_COUNTER_VALUE:
				return getStartCounterValue();
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
			case BPELPackage.ITERATOR__FINAL_COUNTER_VALUE:
				setFinalCounterValue((Expression)newValue);
				return;
			case BPELPackage.ITERATOR__START_COUNTER_VALUE:
				setStartCounterValue((Expression)newValue);
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
			case BPELPackage.ITERATOR__FINAL_COUNTER_VALUE:
				setFinalCounterValue((Expression)null);
				return;
			case BPELPackage.ITERATOR__START_COUNTER_VALUE:
				setStartCounterValue((Expression)null);
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
			case BPELPackage.ITERATOR__FINAL_COUNTER_VALUE:
				return finalCounterValue != null;
			case BPELPackage.ITERATOR__START_COUNTER_VALUE:
				return startCounterValue != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //IteratorImpl
