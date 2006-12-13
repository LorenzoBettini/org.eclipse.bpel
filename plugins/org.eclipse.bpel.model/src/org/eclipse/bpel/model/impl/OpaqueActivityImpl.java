/**
 * <copyright>
 * </copyright>
 *
 * $Id: OpaqueActivityImpl.java,v 1.3 2006/12/13 16:17:31 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.OpaqueActivity;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Opaque Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class OpaqueActivityImpl extends ActivityImpl implements OpaqueActivity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OpaqueActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getOpaqueActivity();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.OPAQUE_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.OPAQUE_ACTIVITY__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.OPAQUE_ACTIVITY__SOURCES:
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
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.OPAQUE_ACTIVITY__ELEMENT:
				return getElement();
			case BPELPackage.OPAQUE_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.OPAQUE_ACTIVITY__NAME:
				return getName();
			case BPELPackage.OPAQUE_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.OPAQUE_ACTIVITY__TARGETS:
				return getTargets();
			case BPELPackage.OPAQUE_ACTIVITY__SOURCES:
				return getSources();
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
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__SOURCES:
				setSources((Sources)newValue);
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
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.OPAQUE_ACTIVITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.OPAQUE_ACTIVITY__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.OPAQUE_ACTIVITY__SOURCES:
				setSources((Sources)null);
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
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.OPAQUE_ACTIVITY__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.OPAQUE_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.OPAQUE_ACTIVITY__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.OPAQUE_ACTIVITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.OPAQUE_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.OPAQUE_ACTIVITY__TARGETS:
				return targets != null;
			case BPELPackage.OPAQUE_ACTIVITY__SOURCES:
				return sources != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //OpaqueActivityImpl
