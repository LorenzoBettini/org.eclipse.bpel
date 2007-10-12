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
 * $Id: InvokeImpl.java,v 1.6 2007/10/12 08:14:58 smoser Exp $
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
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.model.util.ReconciliationHelper;
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
@SuppressWarnings("restriction")
public class InvokeImpl extends PartnerActivityImpl implements Invoke {
	/**
	 * The cached value of the '{@link #getOutputVariable() <em>Output Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable outputVariable;

	/**
	 * The cached value of the '{@link #getInputVariable() <em>Input Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable inputVariable;

	/**
	 * The cached value of the '{@link #getCompensationHandler() <em>Compensation Handler</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompensationHandler()
	 * @generated
	 * @ordered
	 */
	protected CompensationHandler compensationHandler;

	/**
	 * The cached value of the '{@link #getFaultHandler() <em>Fault Handler</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultHandler()
	 * @generated
	 * @ordered
	 */
	protected FaultHandler faultHandler;

	/**
	 * The cached value of the '{@link #getToPart() <em>To Part</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToPart()
	 * @generated
	 * @ordered
	 */
	protected EList<ToPart> toPart;

	/**
	 * The cached value of the '{@link #getFromPart() <em>From Part</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromPart()
	 * @generated
	 * @ordered
	 */
	protected EList<FromPart> fromPart;

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
	@Override
	protected EClass eStaticClass() {
		return BPELPackage.Literals.INVOKE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getOutputVariable() {
		if (outputVariable != null && outputVariable.eIsProxy()) {
			InternalEObject oldOutputVariable = (InternalEObject) outputVariable;
			outputVariable = (Variable) eResolveProxy(oldOutputVariable);
			if (outputVariable != oldOutputVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BPELPackage.INVOKE__OUTPUT_VARIABLE,
							oldOutputVariable, outputVariable));
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
		if (!isReconciling){
			ReconciliationHelper.replaceAttribute(this, BPELConstants.AT_OUTPUT_VARIABLE, newOutputVariable == null ? null : newOutputVariable.getName());
		}
		outputVariable = newOutputVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BPELPackage.INVOKE__OUTPUT_VARIABLE, oldOutputVariable,
					outputVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getInputVariable() {
		if (inputVariable != null && inputVariable.eIsProxy()) {
			InternalEObject oldInputVariable = (InternalEObject) inputVariable;
			inputVariable = (Variable) eResolveProxy(oldInputVariable);
			if (inputVariable != oldInputVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BPELPackage.INVOKE__INPUT_VARIABLE,
							oldInputVariable, inputVariable));
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
		if (!isReconciling){
			ReconciliationHelper.replaceAttribute(this, BPELConstants.AT_INPUT_VARIABLE, newInputVariable == null ? null : newInputVariable.getName());
		}
		inputVariable = newInputVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BPELPackage.INVOKE__INPUT_VARIABLE, oldInputVariable,
					inputVariable));
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
	public NotificationChain basicSetCompensationHandler(
			CompensationHandler newCompensationHandler, NotificationChain msgs) {
		CompensationHandler oldCompensationHandler = compensationHandler;
		compensationHandler = newCompensationHandler;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, BPELPackage.INVOKE__COMPENSATION_HANDLER,
					oldCompensationHandler, newCompensationHandler);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/*
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompensationHandler(
			CompensationHandler newCompensationHandler) {
		// FIXME: (DO) sync should be implemented 
		if (newCompensationHandler != compensationHandler) {
			NotificationChain msgs = null;
			if (compensationHandler != null)
				msgs = ((InternalEObject) compensationHandler).eInverseRemove(
						this, EOPPOSITE_FEATURE_BASE
								- BPELPackage.INVOKE__COMPENSATION_HANDLER,
						null, msgs);
			if (newCompensationHandler != null)
				msgs = ((InternalEObject) newCompensationHandler).eInverseAdd(
						this, EOPPOSITE_FEATURE_BASE
								- BPELPackage.INVOKE__COMPENSATION_HANDLER,
						null, msgs);
			msgs = basicSetCompensationHandler(newCompensationHandler, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BPELPackage.INVOKE__COMPENSATION_HANDLER,
					newCompensationHandler, newCompensationHandler));
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
	public NotificationChain basicSetFaultHandler(FaultHandler newFaultHandler,
			NotificationChain msgs) {
		FaultHandler oldFaultHandler = faultHandler;
//		if (!isReconciling){
//			ReconciliationHelper.replaceChild(this, oldFaultHandler, newFaultHandler);
//		}
		faultHandler = newFaultHandler;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, BPELPackage.INVOKE__FAULT_HANDLER,
					oldFaultHandler, newFaultHandler);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFaultHandler(FaultHandler newFaultHandler) {
		// FIXME: (DO) sync should be implemented 
		if (newFaultHandler != faultHandler) {
			NotificationChain msgs = null;
			if (faultHandler != null)
				msgs = ((InternalEObject) faultHandler)
						.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
								- BPELPackage.INVOKE__FAULT_HANDLER, null, msgs);
			if (newFaultHandler != null)
				msgs = ((InternalEObject) newFaultHandler)
						.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
								- BPELPackage.INVOKE__FAULT_HANDLER, null, msgs);
			msgs = basicSetFaultHandler(newFaultHandler, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BPELPackage.INVOKE__FAULT_HANDLER, newFaultHandler,
					newFaultHandler));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ToPart> getToPart() {
		if (toPart == null) {
			toPart = new EObjectResolvingEList<ToPart>(ToPart.class, this,
					BPELPackage.INVOKE__TO_PART);
		}
		return toPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FromPart> getFromPart() {
		if (fromPart == null) {
			fromPart = new EObjectResolvingEList<FromPart>(FromPart.class,
					this, BPELPackage.INVOKE__FROM_PART);
		}
		return fromPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BPELPackage.INVOKE__COMPENSATION_HANDLER:
				return basicSetCompensationHandler(null, msgs);
			case BPELPackage.INVOKE__FAULT_HANDLER:
				return basicSetFaultHandler(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BPELPackage.INVOKE__OUTPUT_VARIABLE:
				if (resolve)
					return getOutputVariable();
				return basicGetOutputVariable();
			case BPELPackage.INVOKE__INPUT_VARIABLE:
				if (resolve)
					return getInputVariable();
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
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BPELPackage.INVOKE__OUTPUT_VARIABLE:
				setOutputVariable((Variable) newValue);
				return;
			case BPELPackage.INVOKE__INPUT_VARIABLE:
				setInputVariable((Variable) newValue);
				return;
			case BPELPackage.INVOKE__COMPENSATION_HANDLER:
				setCompensationHandler((CompensationHandler) newValue);
				return;
			case BPELPackage.INVOKE__FAULT_HANDLER:
				setFaultHandler((FaultHandler) newValue);
				return;
			case BPELPackage.INVOKE__TO_PART:
				getToPart().clear();
				getToPart().addAll((Collection<? extends ToPart>) newValue);
				return;
			case BPELPackage.INVOKE__FROM_PART:
				getFromPart().clear();
				getFromPart().addAll((Collection<? extends FromPart>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BPELPackage.INVOKE__OUTPUT_VARIABLE:
				setOutputVariable((Variable) null);
				return;
			case BPELPackage.INVOKE__INPUT_VARIABLE:
				setInputVariable((Variable) null);
				return;
			case BPELPackage.INVOKE__COMPENSATION_HANDLER:
				setCompensationHandler((CompensationHandler) null);
				return;
			case BPELPackage.INVOKE__FAULT_HANDLER:
				setFaultHandler((FaultHandler) null);
				return;
			case BPELPackage.INVOKE__TO_PART:
				getToPart().clear();
				return;
			case BPELPackage.INVOKE__FROM_PART:
				getFromPart().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
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
		return super.eIsSet(featureID);
	}

} //InvokeImpl
