/**
 * <copyright>
 * </copyright>
 *
 * $Id: ElseIfImpl.java,v 1.1 2005/12/09 21:01:02 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.ElseIf;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl;

import org.w3c.dom.Element;

import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Else If</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ElseIfImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ElseIfImpl#getActivity <em>Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElseIfImpl extends ExtensibleElementImpl implements ElseIf {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Condition condition = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElseIfImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getElseIf();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(Condition newCondition, NotificationChain msgs) {
		Condition oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ELSE_IF__CONDITION, oldCondition, newCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(Condition newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ELSE_IF__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ELSE_IF__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ELSE_IF__CONDITION, newCondition, newCondition));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ELSE_IF__ACTIVITY, oldActivity, newActivity);
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
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ELSE_IF__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ELSE_IF__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ELSE_IF__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.ELSE_IF__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.ELSE_IF__CONDITION:
					return basicSetCondition(null, msgs);
				case BPELPackage.ELSE_IF__ACTIVITY:
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
			case BPELPackage.ELSE_IF__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.ELSE_IF__ELEMENT:
				return getElement();
			case BPELPackage.ELSE_IF__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.ELSE_IF__CONDITION:
				return getCondition();
			case BPELPackage.ELSE_IF__ACTIVITY:
				return getActivity();
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
			case BPELPackage.ELSE_IF__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.ELSE_IF__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.ELSE_IF__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.ELSE_IF__CONDITION:
				setCondition((Condition)newValue);
				return;
			case BPELPackage.ELSE_IF__ACTIVITY:
				setActivity((Activity)newValue);
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
			case BPELPackage.ELSE_IF__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ELSE_IF__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ELSE_IF__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.ELSE_IF__CONDITION:
				setCondition((Condition)null);
				return;
			case BPELPackage.ELSE_IF__ACTIVITY:
				setActivity((Activity)null);
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
			case BPELPackage.ELSE_IF__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.ELSE_IF__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.ELSE_IF__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.ELSE_IF__CONDITION:
				return condition != null;
			case BPELPackage.ELSE_IF__ACTIVITY:
				return activity != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //ElseIfImpl
