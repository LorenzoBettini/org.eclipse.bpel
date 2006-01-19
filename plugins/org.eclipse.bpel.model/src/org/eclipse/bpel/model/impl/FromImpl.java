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
 * $Id: FromImpl.java,v 1.2 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.EndpointReferenceRole;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.ServiceRef;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>From</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.FromImpl#getOpaque <em>Opaque</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FromImpl#getEndpointReference <em>Endpoint Reference</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FromImpl#getLiteral <em>Literal</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FromImpl#getUnsafeLiteral <em>Unsafe Literal</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FromImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FromImpl#getServiceRef <em>Service Ref</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.FromImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FromImpl extends ToImpl implements From {
	/**
	 * The default value of the '{@link #getOpaque() <em>Opaque</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpaque()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean OPAQUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOpaque() <em>Opaque</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpaque()
	 * @generated
	 * @ordered
	 */
	protected Boolean opaque = OPAQUE_EDEFAULT;

	/**
	 * This is true if the Opaque attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean opaqueESet = false;

	/**
	 * The default value of the '{@link #getEndpointReference() <em>Endpoint Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndpointReference()
	 * @generated
	 * @ordered
	 */
	protected static final EndpointReferenceRole ENDPOINT_REFERENCE_EDEFAULT = EndpointReferenceRole.MY_ROLE_LITERAL;

	/**
	 * The cached value of the '{@link #getEndpointReference() <em>Endpoint Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndpointReference()
	 * @generated
	 * @ordered
	 */
	protected EndpointReferenceRole endpointReference = ENDPOINT_REFERENCE_EDEFAULT;

	/**
	 * This is true if the Endpoint Reference attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean endpointReferenceESet = false;

	/**
	 * The default value of the '{@link #getLiteral() <em>Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteral()
	 * @generated
	 * @ordered
	 */
	protected static final String LITERAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLiteral() <em>Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteral()
	 * @generated
	 * @ordered
	 */
	protected String literal = LITERAL_EDEFAULT;

	/**
	 * This is true if the Literal attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean literalESet = false;

	/**
	 * The default value of the '{@link #getUnsafeLiteral() <em>Unsafe Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsafeLiteral()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean UNSAFE_LITERAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUnsafeLiteral() <em>Unsafe Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnsafeLiteral()
	 * @generated
	 * @ordered
	 */
	protected Boolean unsafeLiteral = UNSAFE_LITERAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression expression = null;

	/**
	 * The cached value of the '{@link #getServiceRef() <em>Service Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceRef()
	 * @generated
	 * @ordered
	 */
	protected ServiceRef serviceRef = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FromImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getFrom();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getOpaque() {
		return opaque;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpaque(Boolean newOpaque) {
		Boolean oldOpaque = opaque;
		opaque = newOpaque;
		boolean oldOpaqueESet = opaqueESet;
		opaqueESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__OPAQUE, oldOpaque, opaque, !oldOpaqueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOpaque() {
		Boolean oldOpaque = opaque;
		boolean oldOpaqueESet = opaqueESet;
		opaque = OPAQUE_EDEFAULT;
		opaqueESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.FROM__OPAQUE, oldOpaque, OPAQUE_EDEFAULT, oldOpaqueESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOpaque() {
		return opaqueESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EndpointReferenceRole getEndpointReference() {
		return endpointReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndpointReference(EndpointReferenceRole newEndpointReference) {
		EndpointReferenceRole oldEndpointReference = endpointReference;
		endpointReference = newEndpointReference == null ? ENDPOINT_REFERENCE_EDEFAULT : newEndpointReference;
		boolean oldEndpointReferenceESet = endpointReferenceESet;
		endpointReferenceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__ENDPOINT_REFERENCE, oldEndpointReference, endpointReference, !oldEndpointReferenceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEndpointReference() {
		EndpointReferenceRole oldEndpointReference = endpointReference;
		boolean oldEndpointReferenceESet = endpointReferenceESet;
		endpointReference = ENDPOINT_REFERENCE_EDEFAULT;
		endpointReferenceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.FROM__ENDPOINT_REFERENCE, oldEndpointReference, ENDPOINT_REFERENCE_EDEFAULT, oldEndpointReferenceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEndpointReference() {
		return endpointReferenceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLiteral(String newLiteral) {
		String oldLiteral = literal;
		literal = newLiteral;
		boolean oldLiteralESet = literalESet;
		literalESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__LITERAL, oldLiteral, literal, !oldLiteralESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLiteral() {
		String oldLiteral = literal;
		boolean oldLiteralESet = literalESet;
		literal = LITERAL_EDEFAULT;
		literalESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.FROM__LITERAL, oldLiteral, LITERAL_EDEFAULT, oldLiteralESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLiteral() {
		return literalESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getUnsafeLiteral() {
		return unsafeLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnsafeLiteral(Boolean newUnsafeLiteral) {
		Boolean oldUnsafeLiteral = unsafeLiteral;
		unsafeLiteral = newUnsafeLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__UNSAFE_LITERAL, oldUnsafeLiteral, unsafeLiteral));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs) {
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__EXPRESSION, oldExpression, newExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(Expression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FROM__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FROM__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceRef getServiceRef() {
		return serviceRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetServiceRef(ServiceRef newServiceRef, NotificationChain msgs) {
		ServiceRef oldServiceRef = serviceRef;
		serviceRef = newServiceRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__SERVICE_REF, oldServiceRef, newServiceRef);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceRef(ServiceRef newServiceRef) {
		if (newServiceRef != serviceRef) {
			NotificationChain msgs = null;
			if (serviceRef != null)
				msgs = ((InternalEObject)serviceRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FROM__SERVICE_REF, null, msgs);
			if (newServiceRef != null)
				msgs = ((InternalEObject)newServiceRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FROM__SERVICE_REF, null, msgs);
			msgs = basicSetServiceRef(newServiceRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__SERVICE_REF, newServiceRef, newServiceRef));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.FROM__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FROM__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.FROM__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.FROM__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.FROM__QUERY:
					return basicSetQuery(null, msgs);
				case BPELPackage.FROM__EXPRESSION:
					return basicSetExpression(null, msgs);
				case BPELPackage.FROM__SERVICE_REF:
					return basicSetServiceRef(null, msgs);
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
			case BPELPackage.FROM__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.FROM__ELEMENT:
				return getElement();
			case BPELPackage.FROM__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.FROM__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.FROM__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case BPELPackage.FROM__PART:
				if (resolve) return getPart();
				return basicGetPart();
			case BPELPackage.FROM__PARTNER_LINK:
				if (resolve) return getPartnerLink();
				return basicGetPartnerLink();
			case BPELPackage.FROM__PROPERTY:
				if (resolve) return getProperty();
				return basicGetProperty();
			case BPELPackage.FROM__QUERY:
				return getQuery();
			case BPELPackage.FROM__OPAQUE:
				return getOpaque();
			case BPELPackage.FROM__ENDPOINT_REFERENCE:
				return getEndpointReference();
			case BPELPackage.FROM__LITERAL:
				return getLiteral();
			case BPELPackage.FROM__UNSAFE_LITERAL:
				return getUnsafeLiteral();
			case BPELPackage.FROM__EXPRESSION:
				return getExpression();
			case BPELPackage.FROM__SERVICE_REF:
				return getServiceRef();
			case BPELPackage.FROM__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case BPELPackage.FROM__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.FROM__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.FROM__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.FROM__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.FROM__VARIABLE:
				setVariable((Variable)newValue);
				return;
			case BPELPackage.FROM__PART:
				setPart((Part)newValue);
				return;
			case BPELPackage.FROM__PARTNER_LINK:
				setPartnerLink((PartnerLink)newValue);
				return;
			case BPELPackage.FROM__PROPERTY:
				setProperty((Property)newValue);
				return;
			case BPELPackage.FROM__QUERY:
				setQuery((Query)newValue);
				return;
			case BPELPackage.FROM__OPAQUE:
				setOpaque((Boolean)newValue);
				return;
			case BPELPackage.FROM__ENDPOINT_REFERENCE:
				setEndpointReference((EndpointReferenceRole)newValue);
				return;
			case BPELPackage.FROM__LITERAL:
				setLiteral((String)newValue);
				return;
			case BPELPackage.FROM__UNSAFE_LITERAL:
				setUnsafeLiteral((Boolean)newValue);
				return;
			case BPELPackage.FROM__EXPRESSION:
				setExpression((Expression)newValue);
				return;
			case BPELPackage.FROM__SERVICE_REF:
				setServiceRef((ServiceRef)newValue);
				return;
			case BPELPackage.FROM__TYPE:
				setType((XSDTypeDefinition)newValue);
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
			case BPELPackage.FROM__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FROM__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FROM__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.FROM__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.FROM__VARIABLE:
				setVariable((Variable)null);
				return;
			case BPELPackage.FROM__PART:
				setPart((Part)null);
				return;
			case BPELPackage.FROM__PARTNER_LINK:
				setPartnerLink((PartnerLink)null);
				return;
			case BPELPackage.FROM__PROPERTY:
				setProperty((Property)null);
				return;
			case BPELPackage.FROM__QUERY:
				setQuery((Query)null);
				return;
			case BPELPackage.FROM__OPAQUE:
				unsetOpaque();
				return;
			case BPELPackage.FROM__ENDPOINT_REFERENCE:
				unsetEndpointReference();
				return;
			case BPELPackage.FROM__LITERAL:
				unsetLiteral();
				return;
			case BPELPackage.FROM__UNSAFE_LITERAL:
				setUnsafeLiteral(UNSAFE_LITERAL_EDEFAULT);
				return;
			case BPELPackage.FROM__EXPRESSION:
				setExpression((Expression)null);
				return;
			case BPELPackage.FROM__SERVICE_REF:
				setServiceRef((ServiceRef)null);
				return;
			case BPELPackage.FROM__TYPE:
				setType((XSDTypeDefinition)null);
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
			case BPELPackage.FROM__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.FROM__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.FROM__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.FROM__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.FROM__VARIABLE:
				return variable != null;
			case BPELPackage.FROM__PART:
				return part != null;
			case BPELPackage.FROM__PARTNER_LINK:
				return partnerLink != null;
			case BPELPackage.FROM__PROPERTY:
				return property != null;
			case BPELPackage.FROM__QUERY:
				return query != null;
			case BPELPackage.FROM__OPAQUE:
				return isSetOpaque();
			case BPELPackage.FROM__ENDPOINT_REFERENCE:
				return isSetEndpointReference();
			case BPELPackage.FROM__LITERAL:
				return isSetLiteral();
			case BPELPackage.FROM__UNSAFE_LITERAL:
				return UNSAFE_LITERAL_EDEFAULT == null ? unsafeLiteral != null : !UNSAFE_LITERAL_EDEFAULT.equals(unsafeLiteral);
			case BPELPackage.FROM__EXPRESSION:
				return expression != null;
			case BPELPackage.FROM__SERVICE_REF:
				return serviceRef != null;
			case BPELPackage.FROM__TYPE:
				return type != null;
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
		result.append(" (opaque: ");
		if (opaqueESet) result.append(opaque); else result.append("<unset>");
		result.append(", endpointReference: ");
		if (endpointReferenceESet) result.append(endpointReference); else result.append("<unset>");
		result.append(", literal: ");
		if (literalESet) result.append(literal); else result.append("<unset>");
		result.append(", unsafeLiteral: ");
		result.append(unsafeLiteral);
		result.append(')');
		return result.toString();
	}

} //FromImpl
