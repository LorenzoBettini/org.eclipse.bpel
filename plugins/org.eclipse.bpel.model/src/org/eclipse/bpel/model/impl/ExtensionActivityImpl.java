/**
 * <copyright>
 * </copyright>
 *
 * $Id: ExtensionActivityImpl.java,v 1.4 2007/02/09 09:13:42 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.ExtensionActivity;
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
 * An implementation of the model object '<em><b>Extension Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ExtensionActivityImpl extends ActivityImpl implements ExtensionActivity {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensionActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getExtensionActivity();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.EXTENSION_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.EXTENSION_ACTIVITY__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.EXTENSION_ACTIVITY__SOURCES:
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
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.EXTENSION_ACTIVITY__ELEMENT:
				return getElement();
			case BPELPackage.EXTENSION_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.EXTENSION_ACTIVITY__NAME:
				return getName();
			case BPELPackage.EXTENSION_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.EXTENSION_ACTIVITY__TARGETS:
				return getTargets();
			case BPELPackage.EXTENSION_ACTIVITY__SOURCES:
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
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__SOURCES:
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
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.EXTENSION_ACTIVITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.EXTENSION_ACTIVITY__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.EXTENSION_ACTIVITY__SOURCES:
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
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.EXTENSION_ACTIVITY__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.EXTENSION_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.EXTENSION_ACTIVITY__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.EXTENSION_ACTIVITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.EXTENSION_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.EXTENSION_ACTIVITY__TARGETS:
				return targets != null;
			case BPELPackage.EXTENSION_ACTIVITY__SOURCES:
				return sources != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //ExtensionActivityImpl
