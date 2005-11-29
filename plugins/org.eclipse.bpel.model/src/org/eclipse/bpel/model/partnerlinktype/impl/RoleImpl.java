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
 * $Id: RoleImpl.java,v 1.1 2005/11/29 18:50:27 james Exp $
 */
package org.eclipse.bpel.model.partnerlinktype.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypeFactory;
import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.model.partnerlinktype.RolePortType;
import org.eclipse.bpel.model.partnerlinktype.util.PartnerlinktypeConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.partnerlinktype.impl.RoleImpl#getPortType <em>Port Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RoleImpl extends ExtensibilityElementImpl implements Role {
	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

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
	 * The cached value of the '{@link #getPortType() <em>Port Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortType()
	 * @generated
	 * @ordered
	 */
	protected RolePortType portType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RoleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PartnerlinktypePackage.eINSTANCE.getRole();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getID() {
		// TODO: implement this method to return the 'ID' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
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
			eNotify(new ENotificationImpl(this, Notification.SET, PartnerlinktypePackage.ROLE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RolePortType getPortType() {
		return portType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPortType(RolePortType newPortType, NotificationChain msgs) {
		RolePortType oldPortType = portType;
		portType = newPortType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PartnerlinktypePackage.ROLE__PORT_TYPE, oldPortType, newPortType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPortType(RolePortType newPortType) {
		if (newPortType != portType) {
			NotificationChain msgs = null;
			if (portType != null)
				msgs = ((InternalEObject)portType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PartnerlinktypePackage.ROLE__PORT_TYPE, null, msgs);
			if (newPortType != null)
				msgs = ((InternalEObject)newPortType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PartnerlinktypePackage.ROLE__PORT_TYPE, null, msgs);
			msgs = basicSetPortType(newPortType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PartnerlinktypePackage.ROLE__PORT_TYPE, newPortType, newPortType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case PartnerlinktypePackage.ROLE__PORT_TYPE:
					return basicSetPortType(null, msgs);
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
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case PartnerlinktypePackage.ROLE__ELEMENT:
				return getElement();
			case PartnerlinktypePackage.ROLE__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				return getElementType();
			case PartnerlinktypePackage.ROLE__ID:
				return getID();
			case PartnerlinktypePackage.ROLE__NAME:
				return getName();
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				return getPortType();
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
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT:
				setElement((Element)newValue);
				return;
			case PartnerlinktypePackage.ROLE__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case PartnerlinktypePackage.ROLE__NAME:
				setName((String)newValue);
				return;
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				setPortType((RolePortType)newValue);
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
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				setPortType((RolePortType)null);
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
			case PartnerlinktypePackage.ROLE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case PartnerlinktypePackage.ROLE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case PartnerlinktypePackage.ROLE__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case PartnerlinktypePackage.ROLE__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case PartnerlinktypePackage.ROLE__ID:
				return ID_EDEFAULT == null ? getID() != null : !ID_EDEFAULT.equals(getID());
			case PartnerlinktypePackage.ROLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PartnerlinktypePackage.ROLE__PORT_TYPE:
				return portType != null;
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

	/**
	 * Override the XML element token.
	 */
	public QName getElementType()
	{
		if (elementType == null)
			elementType = new QName(PartnerlinktypeConstants.NAMESPACE, PartnerlinktypeConstants.ROLE_ELEMENT_TAG);
		return elementType;
    }
	
	//
	// Reconcile methods: DOM -> Model
	//

	public void reconcileAttributes(Element changedElement)
	{
	    //System.out.println("RoleImpl.reconcileAttributes("+changedElement+")");
	    super.reconcileAttributes(changedElement);

	    setName
    	(PartnerlinktypeConstants.getAttribute(changedElement, PartnerlinktypeConstants.NAME_ATTRIBUTE));

	    reconcileReferences(true); // TODO true?
	}

	public void handleUnreconciledElement(Element child, Collection remainingModelObjects)
    {
	    //System.out.println("RoleImpl.handleUnreconciledElement()");
	    if (PartnerlinktypeConstants.PORT_TYPE_ELEMENT_TAG.equals(child.getLocalName()))
	    {
	        RolePortType rolePortType = PartnerlinktypeFactory.eINSTANCE.createRolePortType();
	        rolePortType.setEnclosingDefinition(getEnclosingDefinition());
	        rolePortType.setElement(child);
	        setPortType(rolePortType);
	    }
    }

	//
	// For reconciliation: Model -> DOM
	//

	protected void changeAttribute(EAttribute eAttribute)
	{
	    //System.out.println("RoleImpl.changeAttribute("+eAttribute+")");
	    if (isReconciling)
	        return;

	    super.changeAttribute(eAttribute);
	    Element theElement = getElement();
	    if (theElement != null)
	    {
	        if (eAttribute == null || eAttribute == PartnerlinktypePackage.eINSTANCE.getRole_Name())
	            niceSetAttribute(theElement,PartnerlinktypeConstants.NAME_ATTRIBUTE,getName());
	    }
	}

	public Element createElement()
    {
	    //System.out.println("RoleImpl.createElement()");
	    Element newElement = super.createElement();
	    
	    RolePortType pt = getPortType();
	    if (pt != null)
	    {
	        Element child = ((RolePortTypeImpl) pt).createElement();
	        newElement.appendChild(child);
	    }
	    
	    return newElement;
    }	
} //RoleImpl
