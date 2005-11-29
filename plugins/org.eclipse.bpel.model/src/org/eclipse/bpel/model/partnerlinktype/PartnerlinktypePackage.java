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
 * $Id: PartnerlinktypePackage.java,v 1.1 2005/11/29 18:50:28 james Exp $
 */
package org.eclipse.bpel.model.partnerlinktype;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.wst.wsdl.WSDLPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory
 * @model kind="package"
 * @generated
 */
public interface PartnerlinktypePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "partnerlinktype";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://schemas.xmlsoap.org/ws/2003/05/partner-link/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "plnk";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PartnerlinktypePackage eINSTANCE = org.eclipse.bpel.model.partnerlinktype.impl.PartnerlinktypePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.bpel.model.partnerlinktype.impl.RolePortTypeImpl <em>Role Port Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpel.model.partnerlinktype.impl.RolePortTypeImpl
	 * @see org.eclipse.bpel.model.partnerlinktype.impl.PartnerlinktypePackageImpl#getRolePortType()
	 * @generated
	 */
	int ROLE_PORT_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Documentation Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PORT_TYPE__DOCUMENTATION_ELEMENT = WSDLPackage.EXTENSIBILITY_ELEMENT__DOCUMENTATION_ELEMENT;

	/**
	 * The feature id for the '<em><b>Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PORT_TYPE__ELEMENT = WSDLPackage.EXTENSIBILITY_ELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PORT_TYPE__REQUIRED = WSDLPackage.EXTENSIBILITY_ELEMENT__REQUIRED;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PORT_TYPE__ELEMENT_TYPE = WSDLPackage.EXTENSIBILITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PORT_TYPE__NAME = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Role Port Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_PORT_TYPE_FEATURE_COUNT = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpel.model.partnerlinktype.impl.PartnerLinkTypeImpl <em>Partner Link Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpel.model.partnerlinktype.impl.PartnerLinkTypeImpl
	 * @see org.eclipse.bpel.model.partnerlinktype.impl.PartnerlinktypePackageImpl#getPartnerLinkType()
	 * @generated
	 */
	int PARTNER_LINK_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Documentation Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE__DOCUMENTATION_ELEMENT = WSDLPackage.EXTENSIBILITY_ELEMENT__DOCUMENTATION_ELEMENT;

	/**
	 * The feature id for the '<em><b>Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE__ELEMENT = WSDLPackage.EXTENSIBILITY_ELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE__REQUIRED = WSDLPackage.EXTENSIBILITY_ELEMENT__REQUIRED;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE__ELEMENT_TYPE = WSDLPackage.EXTENSIBILITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE__NAME = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE__ID = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Role</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE__ROLE = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Partner Link Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTNER_LINK_TYPE_FEATURE_COUNT = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl <em>Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl
	 * @see org.eclipse.bpel.model.partnerlinktype.impl.PartnerlinktypePackageImpl#getRole()
	 * @generated
	 */
	int ROLE = 2;

	/**
	 * The feature id for the '<em><b>Documentation Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__DOCUMENTATION_ELEMENT = WSDLPackage.EXTENSIBILITY_ELEMENT__DOCUMENTATION_ELEMENT;

	/**
	 * The feature id for the '<em><b>Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__ELEMENT = WSDLPackage.EXTENSIBILITY_ELEMENT__ELEMENT;

	/**
	 * The feature id for the '<em><b>Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__REQUIRED = WSDLPackage.EXTENSIBILITY_ELEMENT__REQUIRED;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__ELEMENT_TYPE = WSDLPackage.EXTENSIBILITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__ID = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__NAME = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Port Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__PORT_TYPE = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_FEATURE_COUNT = WSDLPackage.EXTENSIBILITY_ELEMENT_FEATURE_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link org.eclipse.bpel.model.partnerlinktype.RolePortType <em>Role Port Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Port Type</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.RolePortType
	 * @generated
	 */
	EClass getRolePortType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpel.model.partnerlinktype.RolePortType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.RolePortType#getName()
	 * @see #getRolePortType()
	 * @generated
	 */
	EAttribute getRolePortType_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpel.model.partnerlinktype.PartnerLinkType <em>Partner Link Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Partner Link Type</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.PartnerLinkType
	 * @generated
	 */
	EClass getPartnerLinkType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpel.model.partnerlinktype.PartnerLinkType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.PartnerLinkType#getName()
	 * @see #getPartnerLinkType()
	 * @generated
	 */
	EAttribute getPartnerLinkType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpel.model.partnerlinktype.PartnerLinkType#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.PartnerLinkType#getID()
	 * @see #getPartnerLinkType()
	 * @generated
	 */
	EAttribute getPartnerLinkType_ID();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpel.model.partnerlinktype.PartnerLinkType#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Role</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.PartnerLinkType#getRole()
	 * @see #getPartnerLinkType()
	 * @generated
	 */
	EReference getPartnerLinkType_Role();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpel.model.partnerlinktype.Role <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.Role
	 * @generated
	 */
	EClass getRole();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpel.model.partnerlinktype.Role#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.Role#getID()
	 * @see #getRole()
	 * @generated
	 */
	EAttribute getRole_ID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpel.model.partnerlinktype.Role#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.Role#getName()
	 * @see #getRole()
	 * @generated
	 */
	EAttribute getRole_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpel.model.partnerlinktype.Role#getPortType <em>Port Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Port Type</em>'.
	 * @see org.eclipse.bpel.model.partnerlinktype.Role#getPortType()
	 * @see #getRole()
	 * @generated
	 */
	EReference getRole_PortType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PartnerlinktypeFactory getPartnerlinktypeFactory();

} //PartnerlinktypePackage
