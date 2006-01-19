/**
 * <copyright>
 * </copyright>
 *
 * $Id: BranchesImpl.java,v 1.2 2006/01/19 21:08:47 james Exp $
 */
package org.eclipse.bpel.model.impl;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Branches;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Branches</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.BranchesImpl#getCountCompletedBranchesOnly <em>Count Completed Branches Only</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BranchesImpl extends ExpressionImpl implements Branches {
	/**
	 * The default value of the '{@link #getCountCompletedBranchesOnly() <em>Count Completed Branches Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountCompletedBranchesOnly()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean COUNT_COMPLETED_BRANCHES_ONLY_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getCountCompletedBranchesOnly() <em>Count Completed Branches Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountCompletedBranchesOnly()
	 * @generated
	 * @ordered
	 */
	protected Boolean countCompletedBranchesOnly = COUNT_COMPLETED_BRANCHES_ONLY_EDEFAULT;

	/**
	 * This is true if the Count Completed Branches Only attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean countCompletedBranchesOnlyESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BranchesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getBranches();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getCountCompletedBranchesOnly() {
		return countCompletedBranchesOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountCompletedBranchesOnly(Boolean newCountCompletedBranchesOnly) {
		Boolean oldCountCompletedBranchesOnly = countCompletedBranchesOnly;
		countCompletedBranchesOnly = newCountCompletedBranchesOnly;
		boolean oldCountCompletedBranchesOnlyESet = countCompletedBranchesOnlyESet;
		countCompletedBranchesOnlyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.BRANCHES__COUNT_COMPLETED_BRANCHES_ONLY, oldCountCompletedBranchesOnly, countCompletedBranchesOnly, !oldCountCompletedBranchesOnlyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCountCompletedBranchesOnly() {
		Boolean oldCountCompletedBranchesOnly = countCompletedBranchesOnly;
		boolean oldCountCompletedBranchesOnlyESet = countCompletedBranchesOnlyESet;
		countCompletedBranchesOnly = COUNT_COMPLETED_BRANCHES_ONLY_EDEFAULT;
		countCompletedBranchesOnlyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.BRANCHES__COUNT_COMPLETED_BRANCHES_ONLY, oldCountCompletedBranchesOnly, COUNT_COMPLETED_BRANCHES_ONLY_EDEFAULT, oldCountCompletedBranchesOnlyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCountCompletedBranchesOnly() {
		return countCompletedBranchesOnlyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.BRANCHES__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.BRANCHES__ELEMENT:
				return getElement();
			case BPELPackage.BRANCHES__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case BPELPackage.BRANCHES__ELEMENT_TYPE:
				return getElementType();
			case BPELPackage.BRANCHES__BODY:
				return getBody();
			case BPELPackage.BRANCHES__EXPRESSION_LANGUAGE:
				return getExpressionLanguage();
			case BPELPackage.BRANCHES__OPAQUE:
				return getOpaque();
			case BPELPackage.BRANCHES__COUNT_COMPLETED_BRANCHES_ONLY:
				return getCountCompletedBranchesOnly();
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
			case BPELPackage.BRANCHES__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.BRANCHES__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.BRANCHES__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case BPELPackage.BRANCHES__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case BPELPackage.BRANCHES__BODY:
				setBody((Object)newValue);
				return;
			case BPELPackage.BRANCHES__EXPRESSION_LANGUAGE:
				setExpressionLanguage((String)newValue);
				return;
			case BPELPackage.BRANCHES__OPAQUE:
				setOpaque((Boolean)newValue);
				return;
			case BPELPackage.BRANCHES__COUNT_COMPLETED_BRANCHES_ONLY:
				setCountCompletedBranchesOnly((Boolean)newValue);
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
			case BPELPackage.BRANCHES__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.BRANCHES__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.BRANCHES__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case BPELPackage.BRANCHES__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case BPELPackage.BRANCHES__BODY:
				setBody(BODY_EDEFAULT);
				return;
			case BPELPackage.BRANCHES__EXPRESSION_LANGUAGE:
				unsetExpressionLanguage();
				return;
			case BPELPackage.BRANCHES__OPAQUE:
				unsetOpaque();
				return;
			case BPELPackage.BRANCHES__COUNT_COMPLETED_BRANCHES_ONLY:
				unsetCountCompletedBranchesOnly();
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
			case BPELPackage.BRANCHES__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.BRANCHES__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.BRANCHES__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case BPELPackage.BRANCHES__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case BPELPackage.BRANCHES__BODY:
				return BODY_EDEFAULT == null ? body != null : !BODY_EDEFAULT.equals(body);
			case BPELPackage.BRANCHES__EXPRESSION_LANGUAGE:
				return isSetExpressionLanguage();
			case BPELPackage.BRANCHES__OPAQUE:
				return isSetOpaque();
			case BPELPackage.BRANCHES__COUNT_COMPLETED_BRANCHES_ONLY:
				return isSetCountCompletedBranchesOnly();
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
		result.append(" (countCompletedBranchesOnly: ");
		if (countCompletedBranchesOnlyESet) result.append(countCompletedBranchesOnly); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //BranchesImpl
