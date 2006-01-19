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
 * $Id: ExpressionImpl.java,v 1.3 2006/01/19 21:08:47 james Exp $
 */
package org.eclipse.bpel.model.impl;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Expression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ExpressionImpl#getBody <em>Body</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ExpressionImpl#getExpressionLanguage <em>Expression Language</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ExpressionImpl#getOpaque <em>Opaque</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExpressionImpl extends ExtensibilityElementImpl implements Expression {
	/**
	 * The default value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected static final Object BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected Object body = BODY_EDEFAULT;

	/**
	 * The default value of the '{@link #getExpressionLanguage() <em>Expression Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_LANGUAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpressionLanguage() <em>Expression Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionLanguage()
	 * @generated
	 * @ordered
	 */
	protected String expressionLanguage = EXPRESSION_LANGUAGE_EDEFAULT;

	/**
	 * This is true if the Expression Language attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean expressionLanguageESet = false;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getExpression();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(Object newBody) {
		Object oldBody = body;
		body = newBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.EXPRESSION__BODY, oldBody, body));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExpressionLanguage() {
		return expressionLanguage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpressionLanguage(String newExpressionLanguage) {
		String oldExpressionLanguage = expressionLanguage;
		expressionLanguage = newExpressionLanguage;
		boolean oldExpressionLanguageESet = expressionLanguageESet;
		expressionLanguageESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.EXPRESSION__EXPRESSION_LANGUAGE, oldExpressionLanguage, expressionLanguage, !oldExpressionLanguageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetExpressionLanguage() {
		String oldExpressionLanguage = expressionLanguage;
		boolean oldExpressionLanguageESet = expressionLanguageESet;
		expressionLanguage = EXPRESSION_LANGUAGE_EDEFAULT;
		expressionLanguageESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.EXPRESSION__EXPRESSION_LANGUAGE, oldExpressionLanguage, EXPRESSION_LANGUAGE_EDEFAULT, oldExpressionLanguageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetExpressionLanguage() {
		return expressionLanguageESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.EXPRESSION__OPAQUE, oldOpaque, opaque, !oldOpaqueESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.EXPRESSION__OPAQUE, oldOpaque, OPAQUE_EDEFAULT, oldOpaqueESet));
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.EXPRESSION__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.EXPRESSION__ELEMENT:
				return getElement();
			case BPELPackage.EXPRESSION__REQUIRED:
				return isRequired() ? Boolean.TRUE : Boolean.FALSE;
			case BPELPackage.EXPRESSION__ELEMENT_TYPE:
				return getElementType();
			case BPELPackage.EXPRESSION__BODY:
				return getBody();
			case BPELPackage.EXPRESSION__EXPRESSION_LANGUAGE:
				return getExpressionLanguage();
			case BPELPackage.EXPRESSION__OPAQUE:
				return getOpaque();
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
			case BPELPackage.EXPRESSION__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.EXPRESSION__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.EXPRESSION__REQUIRED:
				setRequired(((Boolean)newValue).booleanValue());
				return;
			case BPELPackage.EXPRESSION__ELEMENT_TYPE:
				setElementType((QName)newValue);
				return;
			case BPELPackage.EXPRESSION__BODY:
				setBody((Object)newValue);
				return;
			case BPELPackage.EXPRESSION__EXPRESSION_LANGUAGE:
				setExpressionLanguage((String)newValue);
				return;
			case BPELPackage.EXPRESSION__OPAQUE:
				setOpaque((Boolean)newValue);
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
			case BPELPackage.EXPRESSION__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.EXPRESSION__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.EXPRESSION__REQUIRED:
				setRequired(REQUIRED_EDEFAULT);
				return;
			case BPELPackage.EXPRESSION__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case BPELPackage.EXPRESSION__BODY:
				setBody(BODY_EDEFAULT);
				return;
			case BPELPackage.EXPRESSION__EXPRESSION_LANGUAGE:
				unsetExpressionLanguage();
				return;
			case BPELPackage.EXPRESSION__OPAQUE:
				unsetOpaque();
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
			case BPELPackage.EXPRESSION__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.EXPRESSION__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.EXPRESSION__REQUIRED:
				return required != REQUIRED_EDEFAULT;
			case BPELPackage.EXPRESSION__ELEMENT_TYPE:
				return ELEMENT_TYPE_EDEFAULT == null ? elementType != null : !ELEMENT_TYPE_EDEFAULT.equals(elementType);
			case BPELPackage.EXPRESSION__BODY:
				return BODY_EDEFAULT == null ? body != null : !BODY_EDEFAULT.equals(body);
			case BPELPackage.EXPRESSION__EXPRESSION_LANGUAGE:
				return isSetExpressionLanguage();
			case BPELPackage.EXPRESSION__OPAQUE:
				return isSetOpaque();
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
		result.append(" (body: ");
		result.append(body);
		result.append(", expressionLanguage: ");
		if (expressionLanguageESet) result.append(expressionLanguage); else result.append("<unset>");
		result.append(", opaque: ");
		if (opaqueESet) result.append(opaque); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ExpressionImpl
