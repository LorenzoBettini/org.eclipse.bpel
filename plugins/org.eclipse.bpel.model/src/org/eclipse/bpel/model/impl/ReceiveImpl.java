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
 * $Id: ReceiveImpl.java,v 1.3 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.FromPart;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Receive</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ReceiveImpl#getCreateInstance <em>Create Instance</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ReceiveImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ReceiveImpl#getFromPart <em>From Part</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReceiveImpl extends PartnerActivityImpl implements Receive {
	/**
	 * The default value of the '{@link #getCreateInstance() <em>Create Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateInstance()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CREATE_INSTANCE_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getCreateInstance() <em>Create Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateInstance()
	 * @generated
	 * @ordered
	 */
	protected Boolean createInstance = CREATE_INSTANCE_EDEFAULT;

	/**
	 * This is true if the Create Instance attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean createInstanceESet = false;

	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable variable = null;

	/**
	 * The cached value of the '{@link #getFromPart() <em>From Part</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromPart()
	 * @generated
	 * @ordered
	 */
	protected EList fromPart = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReceiveImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getReceive();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getCreateInstance() {
		return createInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreateInstance(Boolean newCreateInstance) {
		Boolean oldCreateInstance = createInstance;
		createInstance = newCreateInstance;
		boolean oldCreateInstanceESet = createInstanceESet;
		createInstanceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.RECEIVE__CREATE_INSTANCE, oldCreateInstance, createInstance, !oldCreateInstanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCreateInstance() {
		Boolean oldCreateInstance = createInstance;
		boolean oldCreateInstanceESet = createInstanceESet;
		createInstance = CREATE_INSTANCE_EDEFAULT;
		createInstanceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.RECEIVE__CREATE_INSTANCE, oldCreateInstance, CREATE_INSTANCE_EDEFAULT, oldCreateInstanceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCreateInstance() {
		return createInstanceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getVariable() {
		if (variable != null && variable.eIsProxy()) {
			Variable oldVariable = variable;
			variable = (Variable)eResolveProxy((InternalEObject)variable);
			if (variable != oldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.RECEIVE__VARIABLE, oldVariable, variable));
			}
		}
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(Variable newVariable) {
		Variable oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.RECEIVE__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFromPart() {
		if (fromPart == null) {
			fromPart = new EObjectResolvingEList(FromPart.class, this, BPELPackage.RECEIVE__FROM_PART);
		}
		return fromPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case BPELPackage.RECEIVE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.RECEIVE__ELEMENT:
				return getElement();
			case BPELPackage.RECEIVE__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.RECEIVE__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.RECEIVE__NAME:
				return getName();
			case BPELPackage.RECEIVE__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.RECEIVE__TARGETS:
				return getTargets();
			case BPELPackage.RECEIVE__SOURCES:
				return getSources();
			case BPELPackage.RECEIVE__PARTNER_LINK:
				if (resolve) return getPartnerLink();
				return basicGetPartnerLink();
			case BPELPackage.RECEIVE__CORRELATIONS:
				return getCorrelations();
			case BPELPackage.RECEIVE__PORT_TYPE:
				if (resolve) return getPortType();
				return basicGetPortType();
			case BPELPackage.RECEIVE__OPERATION:
				if (resolve) return getOperation();
				return basicGetOperation();
			case BPELPackage.RECEIVE__CREATE_INSTANCE:
				return getCreateInstance();
			case BPELPackage.RECEIVE__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case BPELPackage.RECEIVE__FROM_PART:
				return getFromPart();
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
			case BPELPackage.RECEIVE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.RECEIVE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.RECEIVE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.RECEIVE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.RECEIVE__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.RECEIVE__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.RECEIVE__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.RECEIVE__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.RECEIVE__PARTNER_LINK:
				setPartnerLink((PartnerLink)newValue);
				return;
			case BPELPackage.RECEIVE__CORRELATIONS:
				setCorrelations((Correlations)newValue);
				return;
			case BPELPackage.RECEIVE__PORT_TYPE:
				setPortType((PortType)newValue);
				return;
			case BPELPackage.RECEIVE__OPERATION:
				setOperation((Operation)newValue);
				return;
			case BPELPackage.RECEIVE__CREATE_INSTANCE:
				setCreateInstance((Boolean)newValue);
				return;
			case BPELPackage.RECEIVE__VARIABLE:
				setVariable((Variable)newValue);
				return;
			case BPELPackage.RECEIVE__FROM_PART:
				getFromPart().clear();
				getFromPart().addAll((Collection)newValue);
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
			case BPELPackage.RECEIVE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.RECEIVE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.RECEIVE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.RECEIVE__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.RECEIVE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.RECEIVE__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.RECEIVE__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.RECEIVE__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.RECEIVE__PARTNER_LINK:
				setPartnerLink((PartnerLink)null);
				return;
			case BPELPackage.RECEIVE__CORRELATIONS:
				setCorrelations((Correlations)null);
				return;
			case BPELPackage.RECEIVE__PORT_TYPE:
				setPortType((PortType)null);
				return;
			case BPELPackage.RECEIVE__OPERATION:
				setOperation((Operation)null);
				return;
			case BPELPackage.RECEIVE__CREATE_INSTANCE:
				unsetCreateInstance();
				return;
			case BPELPackage.RECEIVE__VARIABLE:
				setVariable((Variable)null);
				return;
			case BPELPackage.RECEIVE__FROM_PART:
				getFromPart().clear();
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
			case BPELPackage.RECEIVE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.RECEIVE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.RECEIVE__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.RECEIVE__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.RECEIVE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.RECEIVE__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.RECEIVE__TARGETS:
				return targets != null;
			case BPELPackage.RECEIVE__SOURCES:
				return sources != null;
			case BPELPackage.RECEIVE__PARTNER_LINK:
				return partnerLink != null;
			case BPELPackage.RECEIVE__CORRELATIONS:
				return correlations != null;
			case BPELPackage.RECEIVE__PORT_TYPE:
				return portType != null;
			case BPELPackage.RECEIVE__OPERATION:
				return operation != null;
			case BPELPackage.RECEIVE__CREATE_INSTANCE:
				return isSetCreateInstance();
			case BPELPackage.RECEIVE__VARIABLE:
				return variable != null;
			case BPELPackage.RECEIVE__FROM_PART:
				return fromPart != null && !fromPart.isEmpty();
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
		result.append(" (createInstance: ");
		if (createInstanceESet) result.append(createInstance); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ReceiveImpl
