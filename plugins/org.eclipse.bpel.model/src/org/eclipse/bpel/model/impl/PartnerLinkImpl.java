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
 * $Id: PartnerLinkImpl.java,v 1.1 2005/11/29 18:50:24 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partner Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getMyRole <em>My Role</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getPartnerRole <em>Partner Role</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerLinkImpl#getPartnerLinkType <em>Partner Link Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PartnerLinkImpl extends ExtensibleElementImpl implements PartnerLink {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMyRole() <em>My Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMyRole()
	 * @generated
	 * @ordered
	 */
	protected Role myRole = null;

	/**
	 * The cached value of the '{@link #getPartnerRole() <em>Partner Role</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartnerRole()
	 * @generated
	 * @ordered
	 */
	protected Role partnerRole = null;

	/**
	 * The cached value of the '{@link #getPartnerLinkType() <em>Partner Link Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartnerLinkType()
	 * @generated
	 * @ordered
	 */
	protected PartnerLinkType partnerLinkType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PartnerLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getPartnerLink();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role getMyRole() {
		if (myRole != null && myRole.eIsProxy()) {
			Role oldMyRole = myRole;
			myRole = (Role)eResolveProxy((InternalEObject)myRole);
			if (myRole != oldMyRole) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_LINK__MY_ROLE, oldMyRole, myRole));
			}
		}
		return myRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role basicGetMyRole() {
		return myRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMyRole(Role newMyRole) {
		Role oldMyRole = myRole;
		myRole = newMyRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__MY_ROLE, oldMyRole, myRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role getPartnerRole() {
		if (partnerRole != null && partnerRole.eIsProxy()) {
			Role oldPartnerRole = partnerRole;
			partnerRole = (Role)eResolveProxy((InternalEObject)partnerRole);
			if (partnerRole != oldPartnerRole) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_LINK__PARTNER_ROLE, oldPartnerRole, partnerRole));
			}
		}
		return partnerRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role basicGetPartnerRole() {
		return partnerRole;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartnerRole(Role newPartnerRole) {
		Role oldPartnerRole = partnerRole;
		partnerRole = newPartnerRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__PARTNER_ROLE, oldPartnerRole, partnerRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLinkType getPartnerLinkType() {
		if (partnerLinkType != null && partnerLinkType.eIsProxy()) {
			PartnerLinkType oldPartnerLinkType = partnerLinkType;
			partnerLinkType = (PartnerLinkType)eResolveProxy((InternalEObject)partnerLinkType);
			if (partnerLinkType != oldPartnerLinkType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE, oldPartnerLinkType, partnerLinkType));
			}
		}
		return partnerLinkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLinkType basicGetPartnerLinkType() {
		return partnerLinkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartnerLinkType(PartnerLinkType newPartnerLinkType) {
		PartnerLinkType oldPartnerLinkType = partnerLinkType;
		partnerLinkType = newPartnerLinkType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE, oldPartnerLinkType, partnerLinkType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.PARTNER_LINK__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.PARTNER_LINK__ELEMENT:
				return getElement();
			case BPELPackage.PARTNER_LINK__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.PARTNER_LINK__NAME:
				return getName();
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				if (resolve) return getMyRole();
				return basicGetMyRole();
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				if (resolve) return getPartnerRole();
				return basicGetPartnerRole();
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				if (resolve) return getPartnerLinkType();
				return basicGetPartnerLinkType();
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
			case BPELPackage.PARTNER_LINK__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.PARTNER_LINK__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.PARTNER_LINK__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.PARTNER_LINK__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				setMyRole((Role)newValue);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				setPartnerRole((Role)newValue);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				setPartnerLinkType((PartnerLinkType)newValue);
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
			case BPELPackage.PARTNER_LINK__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PARTNER_LINK__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PARTNER_LINK__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.PARTNER_LINK__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				setMyRole((Role)null);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				setPartnerRole((Role)null);
				return;
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				setPartnerLinkType((PartnerLinkType)null);
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
			case BPELPackage.PARTNER_LINK__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.PARTNER_LINK__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.PARTNER_LINK__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.PARTNER_LINK__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.PARTNER_LINK__MY_ROLE:
				return myRole != null;
			case BPELPackage.PARTNER_LINK__PARTNER_ROLE:
				return partnerRole != null;
			case BPELPackage.PARTNER_LINK__PARTNER_LINK_TYPE:
				return partnerLinkType != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PartnerLinkImpl
