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
 * $Id: VariableImpl.java,v 1.3 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.VariableImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.VariableImpl#getMessageType <em>Message Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.VariableImpl#getXSDElement <em>XSD Element</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.VariableImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.VariableImpl#getFrom <em>From</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VariableImpl extends ExtensibleElementImpl implements Variable {
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
	 * The cached value of the '{@link #getMessageType() <em>Message Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageType()
	 * @generated
	 * @ordered
	 */
	protected Message messageType = null;

	/**
	 * The cached value of the '{@link #getXSDElement() <em>XSD Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSDElement()
	 * @generated
	 * @ordered
	 */
	protected XSDElementDeclaration xsdElement = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected XSDTypeDefinition type = null;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected From from = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getVariable();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.VARIABLE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message getMessageType() {
		if (messageType != null && messageType.eIsProxy()) {
			Message oldMessageType = messageType;
			messageType = (Message)eResolveProxy((InternalEObject)messageType);
			if (messageType != oldMessageType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.VARIABLE__MESSAGE_TYPE, oldMessageType, messageType));
			}
		}
		return messageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message basicGetMessageType() {
		return messageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessageType(Message newMessageType) {
		Message oldMessageType = messageType;
		messageType = newMessageType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.VARIABLE__MESSAGE_TYPE, oldMessageType, messageType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XSDElementDeclaration getXSDElement() {
		if (xsdElement != null && xsdElement.eIsProxy()) {
			XSDElementDeclaration oldXSDElement = xsdElement;
			xsdElement = (XSDElementDeclaration)eResolveProxy((InternalEObject)xsdElement);
			if (xsdElement != oldXSDElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.VARIABLE__XSD_ELEMENT, oldXSDElement, xsdElement));
			}
		}
		return xsdElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XSDElementDeclaration basicGetXSDElement() {
		return xsdElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXSDElement(XSDElementDeclaration newXSDElement) {
		XSDElementDeclaration oldXSDElement = xsdElement;
		xsdElement = newXSDElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.VARIABLE__XSD_ELEMENT, oldXSDElement, xsdElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XSDTypeDefinition getType() {
		if (type != null && type.eIsProxy()) {
			XSDTypeDefinition oldType = type;
			type = (XSDTypeDefinition)eResolveProxy((InternalEObject)type);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.VARIABLE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XSDTypeDefinition basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(XSDTypeDefinition newType) {
		XSDTypeDefinition oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.VARIABLE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public From getFrom() {
		if (from != null && from.eIsProxy()) {
			From oldFrom = from;
			from = (From)eResolveProxy((InternalEObject)from);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.VARIABLE__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public From basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(From newFrom) {
		From oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.VARIABLE__FROM, oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.VARIABLE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.VARIABLE__ELEMENT:
				return getElement();
			case BPELPackage.VARIABLE__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.VARIABLE__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.VARIABLE__NAME:
				return getName();
			case BPELPackage.VARIABLE__MESSAGE_TYPE:
				if (resolve) return getMessageType();
				return basicGetMessageType();
			case BPELPackage.VARIABLE__XSD_ELEMENT:
				if (resolve) return getXSDElement();
				return basicGetXSDElement();
			case BPELPackage.VARIABLE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case BPELPackage.VARIABLE__FROM:
				if (resolve) return getFrom();
				return basicGetFrom();
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
			case BPELPackage.VARIABLE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.VARIABLE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.VARIABLE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.VARIABLE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.VARIABLE__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.VARIABLE__MESSAGE_TYPE:
				setMessageType((Message)newValue);
				return;
			case BPELPackage.VARIABLE__XSD_ELEMENT:
				setXSDElement((XSDElementDeclaration)newValue);
				return;
			case BPELPackage.VARIABLE__TYPE:
				setType((XSDTypeDefinition)newValue);
				return;
			case BPELPackage.VARIABLE__FROM:
				setFrom((From)newValue);
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
			case BPELPackage.VARIABLE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.VARIABLE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.VARIABLE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.VARIABLE__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.VARIABLE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.VARIABLE__MESSAGE_TYPE:
				setMessageType((Message)null);
				return;
			case BPELPackage.VARIABLE__XSD_ELEMENT:
				setXSDElement((XSDElementDeclaration)null);
				return;
			case BPELPackage.VARIABLE__TYPE:
				setType((XSDTypeDefinition)null);
				return;
			case BPELPackage.VARIABLE__FROM:
				setFrom((From)null);
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
			case BPELPackage.VARIABLE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.VARIABLE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.VARIABLE__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.VARIABLE__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.VARIABLE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.VARIABLE__MESSAGE_TYPE:
				return messageType != null;
			case BPELPackage.VARIABLE__XSD_ELEMENT:
				return xsdElement != null;
			case BPELPackage.VARIABLE__TYPE:
				return type != null;
			case BPELPackage.VARIABLE__FROM:
				return from != null;
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

} //VariableImpl
