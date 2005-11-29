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
 * $Id: RolePortTypeImpl.java,v 1.1 2005/11/29 18:50:27 james Exp $
 */
package org.eclipse.bpel.model.partnerlinktype.impl;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.partnerlinktype.PartnerlinktypePackage;
import org.eclipse.bpel.model.partnerlinktype.RolePortType;
import org.eclipse.bpel.model.partnerlinktype.util.PartnerlinktypeConstants;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.PortType;
import org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role Port Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.partnerlinktype.impl.RolePortTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RolePortTypeImpl extends ExtensibilityElementImpl implements RolePortType {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final Object NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected Object name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RolePortTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return PartnerlinktypePackage.eINSTANCE.getRolePortType();
	}

	/**
	 * @customized
	 */
	public Object getName() {
		if (name instanceof PortType && ((PortType)name).eIsProxy()) {
			PortType oldName = (PortType)name;
			name = (PortType)eResolveProxy((InternalEObject)name);
			if (name != oldName) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PartnerlinktypePackage.ROLE_PORT_TYPE__NAME, oldName, name));
			}
		}
        return name;
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(Object newName) {
		Object oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PartnerlinktypePackage.ROLE_PORT_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case PartnerlinktypePackage.ROLE_PORT_TYPE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT:
				return getElement();
			case PartnerlinktypePackage.ROLE_PORT_TYPE__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT_TYPE:
				return getElementType();
			case PartnerlinktypePackage.ROLE_PORT_TYPE__NAME:
				return getName();
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
			case PartnerlinktypePackage.ROLE_PORT_TYPE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT:
				setElement((Element)newValue);
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__NAME:
				setName((Object)newValue);
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
			case PartnerlinktypePackage.ROLE_PORT_TYPE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__NAME:
				setName(NAME_EDEFAULT);
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
			case PartnerlinktypePackage.ROLE_PORT_TYPE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case PartnerlinktypePackage.ROLE_PORT_TYPE__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case PartnerlinktypePackage.ROLE_PORT_TYPE__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case PartnerlinktypePackage.ROLE_PORT_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
			elementType = new QName(PartnerlinktypeConstants.NAMESPACE, PartnerlinktypeConstants.PORT_TYPE_ELEMENT_TAG);
		return elementType;
    }

	//
	// Reconcile methods: DOM -> Model
	//

	public void reconcileAttributes(Element changedElement)
	{
	    super.reconcileAttributes(changedElement);
	    
	    reconcileReferences(false);
	}
	
	public void reconcileReferences(boolean deep) {
		// Reconcile the PortType reference.
		if (element != null && element.hasAttribute(PartnerlinktypeConstants.NAME_ATTRIBUTE))
	    {
	    	Definition definition = getEnclosingDefinition();
	    	if (definition != null)
	    	{
		    	QName portTypeQName = createQName(definition, element.getAttribute(PartnerlinktypeConstants.NAME_ATTRIBUTE));
		    	PortType newPortType = (portTypeQName != null) ? (PortType) definition.getPortType(portTypeQName) : null;
		    	if (newPortType != null && newPortType !=  getName())
		    		setName(newPortType);
	    	}
	    }
		super.reconcileReferences(deep);
	}

	//
	// For reconciliation: Model -> DOM
	//

	protected void changeAttribute(EAttribute eAttribute)
	{
	    //System.out.println("RolePortTypeImpl.changeAttribute("+eAttribute+")");
	    if (isReconciling)
	        return;

	    super.changeAttribute(eAttribute);
	    Element theElement = getElement();
	    if (theElement != null)
	    {
	        if (eAttribute == null || eAttribute == PartnerlinktypePackage.eINSTANCE.getRolePortType_Name())
	        {
	            PortType pt = (PortType) getName();
	            QName qname = (pt == null) ? null : pt.getQName();
	            if (qname != null)
	            	niceSetAttributeURIValue(theElement, PartnerlinktypeConstants.NAME_ATTRIBUTE, qname.getNamespaceURI() + "#" + qname.getLocalPart());
	        }
	    }
	}

    protected String niceCreateNamespaceAttribute(String namespace) {
        Definition definition = getEnclosingDefinition();
        String prefix = definition.getPrefix(namespace);
        if (prefix == null)
        {
            String base = "ns";
            prefix = base;
            for (int i = 0; definition.getNamespace(prefix) != null; i++) {
                prefix = base + i;
            }
            definition.addNamespace(prefix, namespace);
        }
        return prefix;
    }
    

} //RolePortTypeImpl
