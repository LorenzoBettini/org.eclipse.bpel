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
 * $Id: ReplyImpl.java,v 1.5 2007/02/09 09:13:42 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.ToPart;
import org.eclipse.bpel.model.Variable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reply</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ReplyImpl#getFaultName <em>Fault Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ReplyImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ReplyImpl#getToPart <em>To Part</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReplyImpl extends PartnerActivityImpl implements Reply {
	/**
	 * The default value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultName()
	 * @generated
	 * @ordered
	 */
	protected static final QName FAULT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFaultName() <em>Fault Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultName()
	 * @generated
	 * @ordered
	 */
	protected QName faultName = FAULT_NAME_EDEFAULT;

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
	 * The cached value of the '{@link #getToPart() <em>To Part</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToPart()
	 * @generated
	 * @ordered
	 */
	protected EList toPart = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReplyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getReply();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QName getFaultName() {
		return faultName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFaultName(QName newFaultName) {
		QName oldFaultName = faultName;
		faultName = newFaultName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.REPLY__FAULT_NAME, oldFaultName, faultName));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.REPLY__VARIABLE, oldVariable, variable));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.REPLY__VARIABLE, oldVariable, variable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getToPart() {
		if (toPart == null) {
			toPart = new EObjectResolvingEList(ToPart.class, this, BPELPackage.REPLY__TO_PART);
		}
		return toPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.REPLY__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.REPLY__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.REPLY__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.REPLY__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.REPLY__CORRELATIONS:
					return basicSetCorrelations(null, msgs);
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
			case BPELPackage.REPLY__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.REPLY__ELEMENT:
				return getElement();
			case BPELPackage.REPLY__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.REPLY__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.REPLY__NAME:
				return getName();
			case BPELPackage.REPLY__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.REPLY__TARGETS:
				return getTargets();
			case BPELPackage.REPLY__SOURCES:
				return getSources();
			case BPELPackage.REPLY__PARTNER_LINK:
				if (resolve) return getPartnerLink();
				return basicGetPartnerLink();
			case BPELPackage.REPLY__CORRELATIONS:
				return getCorrelations();
			case BPELPackage.REPLY__PORT_TYPE:
				if (resolve) return getPortType();
				return basicGetPortType();
			case BPELPackage.REPLY__OPERATION:
				if (resolve) return getOperation();
				return basicGetOperation();
			case BPELPackage.REPLY__FAULT_NAME:
				return getFaultName();
			case BPELPackage.REPLY__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
			case BPELPackage.REPLY__TO_PART:
				return getToPart();
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
			case BPELPackage.REPLY__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.REPLY__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.REPLY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.REPLY__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.REPLY__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.REPLY__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.REPLY__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.REPLY__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.REPLY__PARTNER_LINK:
				setPartnerLink((PartnerLink)newValue);
				return;
			case BPELPackage.REPLY__CORRELATIONS:
				setCorrelations((Correlations)newValue);
				return;
			case BPELPackage.REPLY__PORT_TYPE:
				setPortType((PortType)newValue);
				return;
			case BPELPackage.REPLY__OPERATION:
				setOperation((Operation)newValue);
				return;
			case BPELPackage.REPLY__FAULT_NAME:
				setFaultName((QName)newValue);
				return;
			case BPELPackage.REPLY__VARIABLE:
				setVariable((Variable)newValue);
				return;
			case BPELPackage.REPLY__TO_PART:
				getToPart().clear();
				getToPart().addAll((Collection)newValue);
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
			case BPELPackage.REPLY__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.REPLY__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.REPLY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.REPLY__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.REPLY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.REPLY__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.REPLY__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.REPLY__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.REPLY__PARTNER_LINK:
				setPartnerLink((PartnerLink)null);
				return;
			case BPELPackage.REPLY__CORRELATIONS:
				setCorrelations((Correlations)null);
				return;
			case BPELPackage.REPLY__PORT_TYPE:
				setPortType((PortType)null);
				return;
			case BPELPackage.REPLY__OPERATION:
				setOperation((Operation)null);
				return;
			case BPELPackage.REPLY__FAULT_NAME:
				setFaultName(FAULT_NAME_EDEFAULT);
				return;
			case BPELPackage.REPLY__VARIABLE:
				setVariable((Variable)null);
				return;
			case BPELPackage.REPLY__TO_PART:
				getToPart().clear();
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
			case BPELPackage.REPLY__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.REPLY__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.REPLY__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.REPLY__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.REPLY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.REPLY__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.REPLY__TARGETS:
				return targets != null;
			case BPELPackage.REPLY__SOURCES:
				return sources != null;
			case BPELPackage.REPLY__PARTNER_LINK:
				return partnerLink != null;
			case BPELPackage.REPLY__CORRELATIONS:
				return correlations != null;
			case BPELPackage.REPLY__PORT_TYPE:
				return portType != null;
			case BPELPackage.REPLY__OPERATION:
				return operation != null;
			case BPELPackage.REPLY__FAULT_NAME:
				return FAULT_NAME_EDEFAULT == null ? faultName != null : !FAULT_NAME_EDEFAULT.equals(faultName);
			case BPELPackage.REPLY__VARIABLE:
				return variable != null;
			case BPELPackage.REPLY__TO_PART:
				return toPart != null && !toPart.isEmpty();
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
		result.append(" (faultName: ");
		result.append(faultName);
		result.append(')');
		return result.toString();
	}

} //ReplyImpl
