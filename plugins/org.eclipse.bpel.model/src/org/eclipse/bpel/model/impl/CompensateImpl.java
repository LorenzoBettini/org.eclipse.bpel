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
 * $Id: CompensateImpl.java,v 1.1 2005/11/29 18:50:24 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.CatchAll;
import org.eclipse.bpel.model.Compensate;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.proxy.ScopeProxy;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compensate</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.CompensateImpl#getScope <em>Scope</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompensateImpl extends ActivityImpl implements Compensate {
	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected EObject scope = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompensateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getCompensate();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getScope() {
		if (scope != null && scope.eIsProxy()) {
			EObject oldScope = scope;
			scope = eResolveProxy((InternalEObject)scope);
			if (scope != oldScope) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.COMPENSATE__SCOPE, oldScope, scope));
			}
		}
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetScope() {
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScope(EObject newScope) {
		EObject oldScope = scope;
		scope = newScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.COMPENSATE__SCOPE, oldScope, scope));
	}

	public void setScope(String scopeName) {
		
		// From the spec: 
		//
		// <quote>
		//   This activity can be used only in the following parts of a business process: 
		// - In a fault handler of the scope that immediately encloses the scope for which 
		//   compensation is to be performed. 
		// - In the compensation handler of the scope that immediately encloses the scope 
		//   for which compensation is to be performed.
		// </quote>
		//
		// JM: Modified this method to include a more sensible interpretation of
		// "immediately encloses". Rather than a strict parent-child relationship,
		// this simply means the closest enclosing Scope.
		
		EObject enclosingScopeOrProcess = null;
		// Look for the closest enclosing Scope, or the process, ensuring that we pass a
		// Catch, CatchAll or CompensationHandler on the way, as these are
		// the only places where a Compensate activity is valid.
		EObject container = eContainer();
		boolean isValidLocation = false;
		while (container != null) {
			if (container instanceof Catch || container instanceof CatchAll || container instanceof CompensationHandler) {
				isValidLocation = true;
			} else if (container instanceof Scope || container instanceof Process) {
				enclosingScopeOrProcess = container;
				break;
			}
			container = container.eContainer();
		}
		if (enclosingScopeOrProcess == null) {
			// Error, silently fail
			return;
		}
		if (!isValidLocation) {
			// Error, silently fail
		    // CH: Allow this and let the validator mark it as an error.
			//return;
		}
		
		// Find the enclosed scope with the specified scopeName. Since there are
		// many places where scope activities may appear, this search
		// intentionally starts with a broad set of candidates (the entire
		// containment hierarchy) and prunes away the uninteresting parts.
		for (TreeIterator i = enclosingScopeOrProcess.eAllContents(); i.hasNext();) {
			EObject next = (EObject) i.next();
			if (next instanceof Scope) {
				Scope candidate = (Scope) next;
				if (scopeName.equals(candidate.getName())) {
					setScope(candidate);
					return;
				}
				// JM: Do not prune the subtree. A scope here which doesn't match
				// may contain another scope which does.
				//i.prune();
			} else if (next instanceof Invoke) {
				Invoke candidate = (Invoke) next;
				if (scopeName.equals(candidate.getName())) {
					setScope(candidate);
					return;
				}
				i.prune();
			}
		}
		
		// In the case where a match was not found, install a proxy.
		ScopeProxy scopeProxy = new ScopeProxy(eResource().getURI(), scopeName);
		setScope(scopeProxy);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.COMPENSATE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.COMPENSATE__ELEMENT:
				return getElement();
			case BPELPackage.COMPENSATE__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.COMPENSATE__NAME:
				return getName();
			case BPELPackage.COMPENSATE__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.COMPENSATE__TARGETS:
				return getTargets();
			case BPELPackage.COMPENSATE__SOURCES:
				return getSources();
			case BPELPackage.COMPENSATE__SCOPE:
				if (resolve) return getScope();
				return basicGetScope();
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
			case BPELPackage.COMPENSATE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.COMPENSATE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.COMPENSATE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.COMPENSATE__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.COMPENSATE__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.COMPENSATE__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.COMPENSATE__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.COMPENSATE__SCOPE:
				setScope((EObject)newValue);
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
			case BPELPackage.COMPENSATE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.COMPENSATE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.COMPENSATE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.COMPENSATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.COMPENSATE__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.COMPENSATE__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.COMPENSATE__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.COMPENSATE__SCOPE:
				setScope((EObject)null);
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
			case BPELPackage.COMPENSATE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.COMPENSATE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.COMPENSATE__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.COMPENSATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.COMPENSATE__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.COMPENSATE__TARGETS:
				return targets != null;
			case BPELPackage.COMPENSATE__SOURCES:
				return sources != null;
			case BPELPackage.COMPENSATE__SCOPE:
				return scope != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //CompensateImpl
