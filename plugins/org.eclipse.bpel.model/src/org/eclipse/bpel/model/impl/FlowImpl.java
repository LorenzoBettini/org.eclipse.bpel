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
 * $Id: FlowImpl.java,v 1.3 2006/01/19 21:08:47 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.CompletionCondition;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.Links;
import org.eclipse.bpel.model.Sources;
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
 * An implementation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.FlowImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FlowImpl#getLinks <em>Links</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FlowImpl#getCompletionCondition <em>Completion Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FlowImpl extends ActivityImpl implements Flow {
	/**
	 * The cached value of the '{@link #getActivities() <em>Activities</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivities()
	 * @generated
	 * @ordered
	 */
	protected EList activities = null;

	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected Links links = null;

	/**
	 * The cached value of the '{@link #getCompletionCondition() <em>Completion Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompletionCondition()
	 * @generated
	 * @ordered
	 */
	protected CompletionCondition completionCondition = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getFlow();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getActivities() {
		if (activities == null) {
			activities = new EObjectContainmentEList(Activity.class, this, BPELPackage.FLOW__ACTIVITIES);
		}
		return activities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Links getLinks() {
		return links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLinks(Links newLinks, NotificationChain msgs) {
		Links oldLinks = links;
		links = newLinks;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FLOW__LINKS, oldLinks, newLinks);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLinks(Links newLinks) {
		if (newLinks != links) {
			NotificationChain msgs = null;
			if (links != null)
				msgs = ((InternalEObject)links).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FLOW__LINKS, null, msgs);
			if (newLinks != null)
				msgs = ((InternalEObject)newLinks).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FLOW__LINKS, null, msgs);
			msgs = basicSetLinks(newLinks, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FLOW__LINKS, newLinks, newLinks));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompletionCondition getCompletionCondition() {
		return completionCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompletionCondition(CompletionCondition newCompletionCondition, NotificationChain msgs) {
		CompletionCondition oldCompletionCondition = completionCondition;
		completionCondition = newCompletionCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FLOW__COMPLETION_CONDITION, oldCompletionCondition, newCompletionCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompletionCondition(CompletionCondition newCompletionCondition) {
		if (newCompletionCondition != completionCondition) {
			NotificationChain msgs = null;
			if (completionCondition != null)
				msgs = ((InternalEObject)completionCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FLOW__COMPLETION_CONDITION, null, msgs);
			if (newCompletionCondition != null)
				msgs = ((InternalEObject)newCompletionCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FLOW__COMPLETION_CONDITION, null, msgs);
			msgs = basicSetCompletionCondition(newCompletionCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FLOW__COMPLETION_CONDITION, newCompletionCondition, newCompletionCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.FLOW__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.FLOW__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.FLOW__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.FLOW__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.FLOW__ACTIVITIES:
					return ((InternalEList)getActivities()).basicRemove(otherEnd, msgs);
				case BPELPackage.FLOW__LINKS:
					return basicSetLinks(null, msgs);
				case BPELPackage.FLOW__COMPLETION_CONDITION:
					return basicSetCompletionCondition(null, msgs);
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
			case BPELPackage.FLOW__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.FLOW__ELEMENT:
				return getElement();
			case BPELPackage.FLOW__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.FLOW__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.FLOW__NAME:
				return getName();
			case BPELPackage.FLOW__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.FLOW__TARGETS:
				return getTargets();
			case BPELPackage.FLOW__SOURCES:
				return getSources();
			case BPELPackage.FLOW__ACTIVITIES:
				return getActivities();
			case BPELPackage.FLOW__LINKS:
				return getLinks();
			case BPELPackage.FLOW__COMPLETION_CONDITION:
				return getCompletionCondition();
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
			case BPELPackage.FLOW__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.FLOW__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.FLOW__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.FLOW__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.FLOW__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.FLOW__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.FLOW__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.FLOW__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.FLOW__ACTIVITIES:
				getActivities().clear();
				getActivities().addAll((Collection)newValue);
				return;
			case BPELPackage.FLOW__LINKS:
				setLinks((Links)newValue);
				return;
			case BPELPackage.FLOW__COMPLETION_CONDITION:
				setCompletionCondition((CompletionCondition)newValue);
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
			case BPELPackage.FLOW__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FLOW__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FLOW__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.FLOW__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.FLOW__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.FLOW__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.FLOW__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.FLOW__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.FLOW__ACTIVITIES:
				getActivities().clear();
				return;
			case BPELPackage.FLOW__LINKS:
				setLinks((Links)null);
				return;
			case BPELPackage.FLOW__COMPLETION_CONDITION:
				setCompletionCondition((CompletionCondition)null);
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
			case BPELPackage.FLOW__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.FLOW__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.FLOW__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.FLOW__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.FLOW__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.FLOW__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.FLOW__TARGETS:
				return targets != null;
			case BPELPackage.FLOW__SOURCES:
				return sources != null;
			case BPELPackage.FLOW__ACTIVITIES:
				return activities != null && !activities.isEmpty();
			case BPELPackage.FLOW__LINKS:
				return links != null;
			case BPELPackage.FLOW__COMPLETION_CONDITION:
				return completionCondition != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //FlowImpl
