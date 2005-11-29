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
 * $Id: PartnerlinktypeFactoryImpl.java,v 1.1 2005/11/29 18:50:27 james Exp $
 */
package org.eclipse.bpel.model.partnerlinktype.impl;

import org.eclipse.bpel.model.partnerlinktype.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PartnerlinktypeFactoryImpl extends EFactoryImpl implements PartnerlinktypeFactory {
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerlinktypeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PartnerlinktypePackage.ROLE_PORT_TYPE: return createRolePortType();
			case PartnerlinktypePackage.PARTNER_LINK_TYPE: return createPartnerLinkType();
			case PartnerlinktypePackage.ROLE: return createRole();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RolePortType createRolePortType() {
		RolePortTypeImpl rolePortType = new RolePortTypeImpl();
		return rolePortType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLinkType createPartnerLinkType() {
		PartnerLinkTypeImpl partnerLinkType = new PartnerLinkTypeImpl();
		return partnerLinkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerlinktypePackage getPartnerlinktypePackage() {
		return (PartnerlinktypePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static PartnerlinktypePackage getPackage() {
		return PartnerlinktypePackage.eINSTANCE;
	}

} //PartnerlinktypeFactoryImpl
