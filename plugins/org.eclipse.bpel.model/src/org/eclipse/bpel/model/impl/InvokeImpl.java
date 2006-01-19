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
 * $Id: InvokeImpl.java,v 1.3 2006/01/19 21:08:48 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.FromPart;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.PartnerLink;
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
 * An implementation of the model object '<em><b>Invoke</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.InvokeImpl#getOutputVariable <em>Output Variable</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.InvokeImpl#getInputVariable <em>Input Variable</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.InvokeImpl#getCompensationHandler <em>Compensation Handler</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.InvokeImpl#getFaultHandler <em>Fault Handler</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.InvokeImpl#getToPart <em>To Part</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.InvokeImpl#getFromPart <em>From Part</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InvokeImpl extends PartnerActivityImpl implements Invoke {
	/**
	 * The cached value of the '{@link #getOutputVariable() <em>Output Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable outputVariable = null;

	/**
	 * The cached value of the '{@link #getInputVariable() <em>Input Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable inputVariable = null;

	/**
	 * The cached value of the '{@link #getCompensationHandler() <em>Compensation Handler</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompensationHandler()
	 * @generated
	 * @ordered
	 */
	protected CompensationHandler compensationHandler = null;

	/**
	 * The cached value of the '{@link #getFaultHandler() <em>Fault Handler</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultHandler()
	 * @generated
	 * @ordered
	 */
	protected FaultHandler faultHandler = null;

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
	protected InvokeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getInvoke();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getOutputVariable() {
		if (outputVariable != null && outputVariable.eIsProxy()) {
			Variable oldOutputVariable = outputVariable;
			outputVariable = (Variable)eResolveProxy((InternalEObject)outputVariable);
			if (outputVariable != oldOutputVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.INVOKE__OUTPUT_VARIABLE, oldOutputVariable, outputVariable));
			}
		}
		return outputVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetOutputVariable() {
		return outputVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputVariable(Variable newOutputVariable) {
		Variable oldOutputVariable = outputVariable;
		outputVariable = newOutputVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.INVOKE__OUTPUT_VARIABLE, oldOutputVariable, outputVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getInputVariable() {
		if (inputVariable != null && inputVariable.eIsProxy()) {
			Variable oldInputVariable = inputVariable;
			inputVariable = (Variable)eResolveProxy((InternalEObject)inputVariable);
			if (inputVariable != oldInputVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.INVOKE__INPUT_VARIABLE, oldInputVariable, inputVariable));
			}
		}
		return inputVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetInputVariable() {
		return inputVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputVariable(Variable newInputVariable) {
		Variable oldInputVariable = inputVariable;
		inputVariable = newInputVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.INVOKE__INPUT_VARIABLE, oldInputVariable, inputVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompensationHandler getCompensationHandler() {
		return compensationHandler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompensationHandler(CompensationHandler newCompensationHandler, NotificationChain msgs) {
		CompensationHandler oldCompensationHandler = compensationHandler;
		compensationHandler = newCompensationHandler;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.INVOKE__COMPENSATION_HANDLER, oldCompensationHandler, newCompensationHandler);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompensationHandler(CompensationHandler newCompensationHandler) {
		if (newCompensationHandler != compensationHandler) {
			NotificationChain msgs = null;
			if (compensationHandler != null)
				msgs = ((InternalEObject)compensationHandler).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.INVOKE__COMPENSATION_HANDLER, null, msgs);
			if (newCompensationHandler != null)
				msgs = ((InternalEObject)newCompensationHandler).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.INVOKE__COMPENSATION_HANDLER, null, msgs);
			msgs = basicSetCompensationHandler(newCompensationHandler, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.INVOKE__COMPENSATION_HANDLER, newCompensationHandler, newCompensationHandler));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FaultHandler getFaultHandler() {
		return faultHandler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFaultHandler(FaultHandler newFaultHandler, NotificationChain msgs) {
		FaultHandler oldFaultHandler = faultHandler;
		faultHandler = newFaultHandler;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.INVOKE__FAULT_HANDLER, oldFaultHandler, newFaultHandler);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFaultHandler(FaultHandler newFaultHandler) {
		if (newFaultHandler != faultHandler) {
			NotificationChain msgs = null;
			if (faultHandler != null)
				msgs = ((InternalEObject)faultHandler).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.INVOKE__FAULT_HANDLER, null, msgs);
			if (newFaultHandler != null)
				msgs = ((InternalEObject)newFaultHandler).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.INVOKE__FAULT_HANDLER, null, msgs);
			msgs = basicSetFaultHandler(newFaultHandler, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.INVOKE__FAULT_HANDLER, newFaultHandler, newFaultHandler));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getToPart() {
		if (toPart == null) {
			toPart = new EObjectResolvingEList(ToPart.class, this, BPELPackage.INVOKE__TO_PART);
		}
		return toPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFromPart() {
		if (fromPart == null) {
			fromPart = new EObjectResolvingEList(FromPart.class, this, BPELPackage.INVOKE__FROM_PART);
		}
		return fromPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.INVOKE__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.INVOKE__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.INVOKE__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.INVOKE__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.INVOKE__CORRELATIONS:
					return basicSetCorrelations(null, msgs);
				case BPELPackage.INVOKE__COMPENSATION_HANDLER:
					return basicSetCompensationHandler(null, msgs);
				case BPELPackage.INVOKE__FAULT_HANDLER:
					return basicSetFaultHandler(null, msgs);
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
			case BPELPackage.INVOKE__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.INVOKE__ELEMENT:
				return getElement();
			case BPELPackage.INVOKE__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.INVOKE__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.INVOKE__NAME:
				return getName();
			case BPELPackage.INVOKE__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.INVOKE__TARGETS:
				return getTargets();
			case BPELPackage.INVOKE__SOURCES:
				return getSources();
			case BPELPackage.INVOKE__PARTNER_LINK:
				if (resolve) return getPartnerLink();
				return basicGetPartnerLink();
			case BPELPackage.INVOKE__CORRELATIONS:
				return getCorrelations();
			case BPELPackage.INVOKE__PORT_TYPE:
				if (resolve) return getPortType();
				return basicGetPortType();
			case BPELPackage.INVOKE__OPERATION:
				if (resolve) return getOperation();
				return basicGetOperation();
			case BPELPackage.INVOKE__OUTPUT_VARIABLE:
				if (resolve) return getOutputVariable();
				return basicGetOutputVariable();
			case BPELPackage.INVOKE__INPUT_VARIABLE:
				if (resolve) return getInputVariable();
				return basicGetInputVariable();
			case BPELPackage.INVOKE__COMPENSATION_HANDLER:
				return getCompensationHandler();
			case BPELPackage.INVOKE__FAULT_HANDLER:
				return getFaultHandler();
			case BPELPackage.INVOKE__TO_PART:
				return getToPart();
			case BPELPackage.INVOKE__FROM_PART:
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
			case BPELPackage.INVOKE__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.INVOKE__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.INVOKE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.INVOKE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.INVOKE__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.INVOKE__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.INVOKE__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.INVOKE__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.INVOKE__PARTNER_LINK:
				setPartnerLink((PartnerLink)newValue);
				return;
			case BPELPackage.INVOKE__CORRELATIONS:
				setCorrelations((Correlations)newValue);
				return;
			case BPELPackage.INVOKE__PORT_TYPE:
				setPortType((PortType)newValue);
				return;
			case BPELPackage.INVOKE__OPERATION:
				setOperation((Operation)newValue);
				return;
			case BPELPackage.INVOKE__OUTPUT_VARIABLE:
				setOutputVariable((Variable)newValue);
				return;
			case BPELPackage.INVOKE__INPUT_VARIABLE:
				setInputVariable((Variable)newValue);
				return;
			case BPELPackage.INVOKE__COMPENSATION_HANDLER:
				setCompensationHandler((CompensationHandler)newValue);
				return;
			case BPELPackage.INVOKE__FAULT_HANDLER:
				setFaultHandler((FaultHandler)newValue);
				return;
			case BPELPackage.INVOKE__TO_PART:
				getToPart().clear();
				getToPart().addAll((Collection)newValue);
				return;
			case BPELPackage.INVOKE__FROM_PART:
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
			case BPELPackage.INVOKE__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.INVOKE__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.INVOKE__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.INVOKE__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.INVOKE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.INVOKE__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.INVOKE__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.INVOKE__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.INVOKE__PARTNER_LINK:
				setPartnerLink((PartnerLink)null);
				return;
			case BPELPackage.INVOKE__CORRELATIONS:
				setCorrelations((Correlations)null);
				return;
			case BPELPackage.INVOKE__PORT_TYPE:
				setPortType((PortType)null);
				return;
			case BPELPackage.INVOKE__OPERATION:
				setOperation((Operation)null);
				return;
			case BPELPackage.INVOKE__OUTPUT_VARIABLE:
				setOutputVariable((Variable)null);
				return;
			case BPELPackage.INVOKE__INPUT_VARIABLE:
				setInputVariable((Variable)null);
				return;
			case BPELPackage.INVOKE__COMPENSATION_HANDLER:
				setCompensationHandler((CompensationHandler)null);
				return;
			case BPELPackage.INVOKE__FAULT_HANDLER:
				setFaultHandler((FaultHandler)null);
				return;
			case BPELPackage.INVOKE__TO_PART:
				getToPart().clear();
				return;
			case BPELPackage.INVOKE__FROM_PART:
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
			case BPELPackage.INVOKE__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.INVOKE__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.INVOKE__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.INVOKE__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.INVOKE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.INVOKE__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.INVOKE__TARGETS:
				return targets != null;
			case BPELPackage.INVOKE__SOURCES:
				return sources != null;
			case BPELPackage.INVOKE__PARTNER_LINK:
				return partnerLink != null;
			case BPELPackage.INVOKE__CORRELATIONS:
				return correlations != null;
			case BPELPackage.INVOKE__PORT_TYPE:
				return portType != null;
			case BPELPackage.INVOKE__OPERATION:
				return operation != null;
			case BPELPackage.INVOKE__OUTPUT_VARIABLE:
				return outputVariable != null;
			case BPELPackage.INVOKE__INPUT_VARIABLE:
				return inputVariable != null;
			case BPELPackage.INVOKE__COMPENSATION_HANDLER:
				return compensationHandler != null;
			case BPELPackage.INVOKE__FAULT_HANDLER:
				return faultHandler != null;
			case BPELPackage.INVOKE__TO_PART:
				return toPart != null && !toPart.isEmpty();
			case BPELPackage.INVOKE__FROM_PART:
				return fromPart != null && !fromPart.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} //InvokeImpl
