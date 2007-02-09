/**
 * <copyright>
 * </copyright>
 *
 * $Id: ValidateImpl.java,v 1.3 2007/02/09 09:13:42 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.Validate;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Validate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ValidateImpl#getVariables <em>Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ValidateImpl extends ActivityImpl implements Validate {
	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList variables = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ValidateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getValidate();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getVariables() {
		if (variables == null) {
			variables = new EObjectResolvingEList(Variable.class, this, BPELPackage.VALIDATE__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.VALIDATE__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.VALIDATE__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.VALIDATE__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.VALIDATE__SOURCES:
					return basicSetSources(null, msgs);
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
			case BPELPackage.VALIDATE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.VALIDATE__ELEMENT:
				return getElement();
			case BPELPackage.VALIDATE__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.VALIDATE__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.VALIDATE__NAME:
				return getName();
			case BPELPackage.VALIDATE__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.VALIDATE__TARGETS:
				return getTargets();
			case BPELPackage.VALIDATE__SOURCES:
				return getSources();
			case BPELPackage.VALIDATE__VARIABLES:
				return getVariables();
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
			case BPELPackage.VALIDATE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.VALIDATE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.VALIDATE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.VALIDATE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.VALIDATE__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.VALIDATE__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.VALIDATE__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.VALIDATE__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.VALIDATE__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection)newValue);
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
			case BPELPackage.VALIDATE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.VALIDATE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.VALIDATE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.VALIDATE__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.VALIDATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.VALIDATE__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.VALIDATE__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.VALIDATE__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.VALIDATE__VARIABLES:
				getVariables().clear();
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
			case BPELPackage.VALIDATE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.VALIDATE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.VALIDATE__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.VALIDATE__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.VALIDATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.VALIDATE__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.VALIDATE__TARGETS:
				return targets != null;
			case BPELPackage.VALIDATE__SOURCES:
				return sources != null;
			case BPELPackage.VALIDATE__VARIABLES:
				return variables != null && !variables.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} //ValidateImpl
