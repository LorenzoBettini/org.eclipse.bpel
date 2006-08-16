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
 * $Id: PropertyAliasImpl.java,v 1.3 2006/08/16 22:11:16 mchmielewski Exp $
 */
package org.eclipse.bpel.model.messageproperties.impl;

import java.util.Collection;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.messageproperties.MessagepropertiesFactory;
import org.eclipse.bpel.model.messageproperties.MessagepropertiesPackage;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.bpel.model.messageproperties.Query;
import org.eclipse.bpel.model.messageproperties.util.MessagepropertiesConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.wst.wsdl.internal.impl.DefinitionImpl;
import org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Alias</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getMessageType <em>Message Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getPart <em>Part</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getPropertyName <em>Property Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getWsdlPart <em>Wsdl Part</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getQuery <em>Query</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.messageproperties.impl.PropertyAliasImpl#getXSDElement <em>XSD Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyAliasImpl extends ExtensibilityElementImpl implements PropertyAlias {

	/**
	 * The default value of the '{@link #getMessageType() <em>Message Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageType()
	 * @generated
	 * @ordered
	 */
	protected static final Object MESSAGE_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessageType() <em>Message Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageType()
	 * @generated
	 * @ordered
	 */
	protected Object messageType = MESSAGE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPart() <em>Part</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPart()
	 * @generated
	 * @ordered
	 */
	protected static final String PART_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPart() <em>Part</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPart()
	 * @generated
	 * @ordered
	 */
	protected String part = PART_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertyName() <em>Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyName()
	 * @generated
	 * @ordered
	 */
	protected static final Object PROPERTY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyName() <em>Property Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyName()
	 * @generated
	 * @ordered
	 */
	protected Object propertyName = PROPERTY_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getWsdlPart() <em>Wsdl Part</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWsdlPart()
	 * @generated
	 * @ordered
	 */
	protected Part wsdlPart = null;

	/**
	 * The cached value of the '{@link #getQuery() <em>Query</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected Query query = null;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final Object TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Object type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getXSDElement() <em>XSD Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSDElement()
	 * @generated
	 * @ordered
	 */
	protected static final Object XSD_ELEMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getXSDElement() <em>XSD Element</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSDElement()
	 * @generated
	 * @ordered
	 */
	protected Object xsdElement = XSD_ELEMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyAliasImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return MessagepropertiesPackage.eINSTANCE.getPropertyAlias();
	}

	/**
	 * @customized
	 */
	public Object getMessageType() {
		if (messageType instanceof Message && ((Message)messageType).eIsProxy()) {
			Message oldMessageType = (Message)messageType;
			messageType = (Message)eResolveProxy((InternalEObject)messageType);
			if (messageType != oldMessageType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MessagepropertiesPackage.PROPERTY_ALIAS__MESSAGE_TYPE, oldMessageType, messageType));
			}
		}
		return messageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessageType(Object newMessageType) {
		Object oldMessageType = messageType;
		messageType = newMessageType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__MESSAGE_TYPE, oldMessageType, messageType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPart() {
		return part;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPart(String newPart) {
		String oldPart = part;
		part = newPart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__PART, oldPart, part));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getPropertyName() {
		return propertyName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyName(Object newPropertyName) {
		Object oldPropertyName = propertyName;
		propertyName = newPropertyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__PROPERTY_NAME, oldPropertyName, propertyName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getID() {
        return "";
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Part getWsdlPart() {
		if (wsdlPart != null && wsdlPart.eIsProxy()) {
			Part oldWsdlPart = wsdlPart;
			wsdlPart = (Part)eResolveProxy((InternalEObject)wsdlPart);
			if (wsdlPart != oldWsdlPart) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MessagepropertiesPackage.PROPERTY_ALIAS__WSDL_PART, oldWsdlPart, wsdlPart));
			}
		}
		return wsdlPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Part basicGetWsdlPart() {
		return wsdlPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWsdlPart(Part newWsdlPart) {
		Part oldWsdlPart = wsdlPart;
		wsdlPart = newWsdlPart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__WSDL_PART, oldWsdlPart, wsdlPart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Query getQuery() {
		return query;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQuery(Query newQuery, NotificationChain msgs) {
		Query oldQuery = query;
		query = newQuery;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__QUERY, oldQuery, newQuery);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQuery(Query newQuery) {
		if (newQuery != query) {
			NotificationChain msgs = null;
			if (query != null)
				msgs = ((InternalEObject)query).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MessagepropertiesPackage.PROPERTY_ALIAS__QUERY, null, msgs);
			if (newQuery != null)
				msgs = ((InternalEObject)newQuery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MessagepropertiesPackage.PROPERTY_ALIAS__QUERY, null, msgs);
			msgs = basicSetQuery(newQuery, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__QUERY, newQuery, newQuery));
	}

	/**
	 * @customized
	 */
	public Object getType() {
		if (type instanceof XSDTypeDefinition && ((XSDTypeDefinition)type).eIsProxy()) {
			XSDTypeDefinition oldType = (XSDTypeDefinition)type;
			type = (XSDTypeDefinition)eResolveProxy((InternalEObject)type);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MessagepropertiesPackage.PROPERTY_ALIAS__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Object newType) {
		Object oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__TYPE, oldType, type));
	}

	/**
	 * @customized
	 */
	public Object getXSDElement() {
		if (xsdElement instanceof XSDElementDeclaration && ((XSDElementDeclaration)xsdElement).eIsProxy()) {
			XSDElementDeclaration oldElement = (XSDElementDeclaration)xsdElement;
			xsdElement = (XSDElementDeclaration)eResolveProxy((InternalEObject)xsdElement);
			if (xsdElement != oldElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MessagepropertiesPackage.PROPERTY_ALIAS__XSD_ELEMENT, oldElement, xsdElement));
			}
		}
		return xsdElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXSDElement(Object newXSDElement) {
		Object oldXSDElement = xsdElement;
		xsdElement = newXSDElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MessagepropertiesPackage.PROPERTY_ALIAS__XSD_ELEMENT, oldXSDElement, xsdElement));
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case MessagepropertiesPackage.PROPERTY_ALIAS__QUERY:
					return basicSetQuery(null, msgs);
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
			case MessagepropertiesPackage.PROPERTY_ALIAS__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT:
				return getElement();
			case MessagepropertiesPackage.PROPERTY_ALIAS__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT_TYPE:
				return getElementType();
			case MessagepropertiesPackage.PROPERTY_ALIAS__MESSAGE_TYPE:
				return getMessageType();
			case MessagepropertiesPackage.PROPERTY_ALIAS__PART:
				return getPart();
			case MessagepropertiesPackage.PROPERTY_ALIAS__PROPERTY_NAME:
				return getPropertyName();
			case MessagepropertiesPackage.PROPERTY_ALIAS__ID:
				return getID();
			case MessagepropertiesPackage.PROPERTY_ALIAS__WSDL_PART:
				if (resolve) return getWsdlPart();
				return basicGetWsdlPart();
			case MessagepropertiesPackage.PROPERTY_ALIAS__QUERY:
				return getQuery();
			case MessagepropertiesPackage.PROPERTY_ALIAS__TYPE:
				return getType();
			case MessagepropertiesPackage.PROPERTY_ALIAS__XSD_ELEMENT:
				return getXSDElement();
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
			case MessagepropertiesPackage.PROPERTY_ALIAS__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT:
				setElement((Element)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__MESSAGE_TYPE:
				setMessageType((Object)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__PART:
				setPart((String)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__PROPERTY_NAME:
				setPropertyName((Object)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__WSDL_PART:
				setWsdlPart((Part)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__QUERY:
				setQuery((Query)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__TYPE:
				setType((Object)newValue);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__XSD_ELEMENT:
				setXSDElement((Object)newValue);
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
			case MessagepropertiesPackage.PROPERTY_ALIAS__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__MESSAGE_TYPE:
				setMessageType(MESSAGE_TYPE_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__PART:
				setPart(PART_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__PROPERTY_NAME:
				setPropertyName(PROPERTY_NAME_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__WSDL_PART:
				setWsdlPart((Part)null);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__QUERY:
				setQuery((Query)null);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case MessagepropertiesPackage.PROPERTY_ALIAS__XSD_ELEMENT:
				setXSDElement(XSD_ELEMENT_EDEFAULT);
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
			case MessagepropertiesPackage.PROPERTY_ALIAS__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case MessagepropertiesPackage.PROPERTY_ALIAS__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case MessagepropertiesPackage.PROPERTY_ALIAS__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case MessagepropertiesPackage.PROPERTY_ALIAS__MESSAGE_TYPE:
				return MESSAGE_TYPE_EDEFAULT == null ? messageType != null : !MESSAGE_TYPE_EDEFAULT.equals(messageType);
			case MessagepropertiesPackage.PROPERTY_ALIAS__PART:
				return PART_EDEFAULT == null ? part != null : !PART_EDEFAULT.equals(part);
			case MessagepropertiesPackage.PROPERTY_ALIAS__PROPERTY_NAME:
				return PROPERTY_NAME_EDEFAULT == null ? propertyName != null : !PROPERTY_NAME_EDEFAULT.equals(propertyName);
			case MessagepropertiesPackage.PROPERTY_ALIAS__ID:
				return ID_EDEFAULT == null ? getID() != null : !ID_EDEFAULT.equals(getID());
			case MessagepropertiesPackage.PROPERTY_ALIAS__WSDL_PART:
				return wsdlPart != null;
			case MessagepropertiesPackage.PROPERTY_ALIAS__QUERY:
				return query != null;
			case MessagepropertiesPackage.PROPERTY_ALIAS__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case MessagepropertiesPackage.PROPERTY_ALIAS__XSD_ELEMENT:
				return XSD_ELEMENT_EDEFAULT == null ? xsdElement != null : !XSD_ELEMENT_EDEFAULT.equals(xsdElement);
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
		result.append(" (messageType: ");
		result.append(messageType);
		result.append(", part: ");
		result.append(part);
		result.append(", propertyName: ");
		result.append(propertyName);
		result.append(", type: ");
		result.append(type);
		result.append(", XSDElement: ");
		result.append(xsdElement);
		result.append(')');
		return result.toString();
	}

	/**
	 * Override the XML element token.
	 */
	public QName getElementType()
	{
		if (elementType == null) 
			elementType = new QName(MessagepropertiesConstants.NAMESPACE, MessagepropertiesConstants.PROPERTY_ALIAS_ELEMENT_TAG);
		return elementType;
    }
	
	//
	// Reconcile methods: DOM -> Model
	//

	public void reconcileAttributes(Element changedElement)
	{
	    super.reconcileAttributes(changedElement);

	    setPart
	    	(MessagepropertiesConstants.getAttribute(changedElement, MessagepropertiesConstants.PROPERTY_ALIAS_PART_ATTRIBUTE));

	    reconcileReferences(false);
	}

	public void reconcileReferences(boolean deep) {
		Definition definition = getEnclosingDefinition();
		Element element = getElement();
		if (element != null && element.hasAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_PROPERTY_NAME_ATTRIBUTE))
	    {
			if (definition != null)
			{
		    	QName qname = createQName(definition, element.getAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_PROPERTY_NAME_ATTRIBUTE));
		    	// resolve the qname to a property
		    	// TODO could this reference an imported property?
		    	for (Iterator i = definition.getExtensibilityElements().iterator(); i.hasNext();)
		    	{
		    		ExtensibilityElement extensibilityElement = (ExtensibilityElement) i.next();
		    		if (extensibilityElement instanceof Property)
		    		{
		    			Property property = (Property) extensibilityElement;
		    			if (property.getQName().equals(qname) && property != getPropertyName())
		    			{
		    				setPropertyName(property);
		    				break;
		    			}
		    		}
		    	}
			}
	    }
		if (element != null && element.hasAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE))
	    {
			if (definition != null)
			{
		    	QName messageQName = createQName(definition, element.getAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE));
		    	Message newMessage = (messageQName != null) ? (Message) definition.getMessage(messageQName) : null;
		    	if (newMessage != null && newMessage!=  getMessageType())
		    		setMessageType(newMessage);
			}
	    } else {
	    	setMessageType(null);
	    }
		
		
		if (element != null && element.hasAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_TYPE_ATTRIBUTE))
	    {
			if (definition != null)
			{
		    	QName typeQName = createQName(definition, element.getAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_TYPE_ATTRIBUTE));
		    	XSDTypeDefinition newType = ((DefinitionImpl)definition).resolveTypeDefinition(typeQName.getNamespaceURI(), typeQName.getLocalPart());		    	
		    	if (newType != null && newType != getType())
		    		setType(newType);        
			}
	    } else {
	    	setType(null);
	    }
		
		if (element != null && element.hasAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_XSD_ELEMENT_ATTRIBUTE))
	    {
			if (definition != null)
			{
		    	QName elementQName = createQName(definition, element.getAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_XSD_ELEMENT_ATTRIBUTE));
		    	XSDElementDeclaration newElement = ((DefinitionImpl)definition).resolveElementDeclaration(elementQName.getNamespaceURI(), elementQName.getLocalPart());
		    	if (newElement != null && newElement != getXSDElement()) {
		    		setXSDElement(newElement);
		    	}
		        
			}
	    } else {
	    	setXSDElement(null);
	    }
		super.reconcileReferences(deep);
	}

	public void handleUnreconciledElement(Element child, Collection remainingModelObjects)
    {
	    //System.out.println("PropertyAliasImpl.handleUnreconciledElement()");
	    if (MessagepropertiesConstants.QUERY_ELEMENT_TAG.equals(child.getLocalName()))
	    {
	    	Query query = MessagepropertiesFactory.eINSTANCE.createQuery();
	    	query.setEnclosingDefinition(getEnclosingDefinition());
	    	query.setElement(child);
	        setQuery(query);
	    }
    }

	//
	// For reconciliation: Model -> DOM
	//

	protected void changeAttribute(EAttribute eAttribute)
	{
	    if (isReconciling)
	        return;

	    super.changeAttribute(eAttribute);
	    Element theElement = getElement();
	    if (theElement != null)
	    {
	        if (eAttribute == null || eAttribute == MessagepropertiesPackage.eINSTANCE.getPropertyAlias_PropertyName())
	        {
	        	Property property = (Property) getPropertyName();
	        	QName qname = (property == null) ? null : property.getQName();
	        	if (qname != null) 
	        		niceSetAttributeURIValue(theElement, MessagepropertiesConstants.PROPERTY_ALIAS_PROPERTY_NAME_ATTRIBUTE, qname.getNamespaceURI() + "#" + qname.getLocalPart());
	        }

	        if (eAttribute == null || eAttribute == MessagepropertiesPackage.eINSTANCE.getPropertyAlias_MessageType())
	        {
	        	Message message = (Message) getMessageType();
	        	QName qname = (message == null) ? null : message.getQName();
	        	if (qname != null)
	        		niceSetAttributeURIValue(theElement, MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE, qname.getNamespaceURI() + "#" + qname.getLocalPart());
	        }

	        if (eAttribute == null || eAttribute == MessagepropertiesPackage.eINSTANCE.getPropertyAlias_Part())
	            niceSetAttribute(theElement,MessagepropertiesConstants.PROPERTY_ALIAS_PART_ATTRIBUTE,getPart());

	    }
	}
	
	public Element createElement()
    {
		// Register a prefix for the namespace.  
		// If we don't do this, the WSDL model will deserialize our elements as UnknownExtensibilityElements.
		//if (getEnclosingDefinition().getPrefix(MessagepropertiesConstants.NAMESPACE) == null) {
		//	getEnclosingDefinition().addNamespace(MessagepropertiesPackage.eNS_PREFIX, MessagepropertiesConstants.NAMESPACE);
		//}
		
	    Element newElement = super.createElement();
	    
	    Query query = getQuery();
	    if (query != null) {
            Element child = ((QueryImpl)query).createElement();
            newElement.appendChild(child);
        }
	    
	    return newElement;
    }
	
} //PropertyAliasImpl
