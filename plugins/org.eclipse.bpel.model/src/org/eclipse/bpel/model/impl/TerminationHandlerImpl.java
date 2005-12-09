/**
 * <copyright>
 * </copyright>
 *
 * $Id: TerminationHandlerImpl.java,v 1.1 2005/12/09 19:22:30 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.TerminationHandler;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Termination Handler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.TerminationHandlerImpl#getActivity <em>Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TerminationHandlerImpl extends ExtensibleElementImpl implements TerminationHandler {
	/**
	 * The cached value of the '{@link #getActivity() <em>Activity</em>}' reference.
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
	protected TerminationHandlerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getTerminationHandler();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getActivity() {
		if (activity != null && activity.eIsProxy()) {
			Activity oldActivity = activity;
			activity = (Activity)eResolveProxy((InternalEObject)activity);
			if (activity != oldActivity) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.TERMINATION_HANDLER__ACTIVITY, oldActivity, activity));
			}
		}
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity basicGetActivity() {
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivity(Activity newActivity) {
		Activity oldActivity = activity;
		activity = newActivity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.TERMINATION_HANDLER__ACTIVITY, oldActivity, activity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.TERMINATION_HANDLER__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.TERMINATION_HANDLER__ELEMENT:
				return getElement();
			case BPELPackage.TERMINATION_HANDLER__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.TERMINATION_HANDLER__ACTIVITY:
				if (resolve) return getActivity();
				return basicGetActivity();
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
			case BPELPackage.TERMINATION_HANDLER__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.TERMINATION_HANDLER__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.TERMINATION_HANDLER__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.TERMINATION_HANDLER__ACTIVITY:
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
			case BPELPackage.TERMINATION_HANDLER__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.TERMINATION_HANDLER__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.TERMINATION_HANDLER__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.TERMINATION_HANDLER__ACTIVITY:
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
			case BPELPackage.TERMINATION_HANDLER__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.TERMINATION_HANDLER__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.TERMINATION_HANDLER__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.TERMINATION_HANDLER__ACTIVITY:
				return activity != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //TerminationHandlerImpl
