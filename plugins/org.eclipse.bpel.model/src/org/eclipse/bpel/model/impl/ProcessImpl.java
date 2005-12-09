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
 * $Id: ProcessImpl.java,v 1.4 2005/12/09 19:30:43 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.Extensions;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Variables;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getTargetNamespace <em>Target Namespace</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getQueryLanguage <em>Query Language</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getExpressionLanguage <em>Expression Language</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getSuppressJoinFailure <em>Suppress Join Failure</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getVariableAccessSerializable <em>Variable Access Serializable</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getPartnerLinks <em>Partner Links</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getFaultHandlers <em>Fault Handlers</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getEventHandlers <em>Event Handlers</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getCorrelationSets <em>Correlation Sets</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ProcessImpl#getExitOnStandardFault <em>Exit On Standard Fault</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessImpl extends ExtensibleElementImpl implements org.eclipse.bpel.model.Process {
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
	 * The default value of the '{@link #getTargetNamespace() <em>Target Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_NAMESPACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetNamespace() <em>Target Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetNamespace()
	 * @generated
	 * @ordered
	 */
	protected String targetNamespace = TARGET_NAMESPACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getQueryLanguage() <em>Query Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String QUERY_LANGUAGE_EDEFAULT = "http://www.w3.org/TR/1999/REC-xpath-19991116";

	/**
	 * The cached value of the '{@link #getQueryLanguage() <em>Query Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueryLanguage()
	 * @generated
	 * @ordered
	 */
	protected String queryLanguage = QUERY_LANGUAGE_EDEFAULT;

	/**
	 * This is true if the Query Language attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queryLanguageESet = false;

	/**
	 * The default value of the '{@link #getExpressionLanguage() <em>Expression Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_LANGUAGE_EDEFAULT = "http://www.w3.org/TR/1999/REC-xpath-19991116";

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
	 * The default value of the '{@link #getSuppressJoinFailure() <em>Suppress Join Failure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuppressJoinFailure()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean SUPPRESS_JOIN_FAILURE_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getSuppressJoinFailure() <em>Suppress Join Failure</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuppressJoinFailure()
	 * @generated
	 * @ordered
	 */
	protected Boolean suppressJoinFailure = SUPPRESS_JOIN_FAILURE_EDEFAULT;

	/**
	 * This is true if the Suppress Join Failure attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean suppressJoinFailureESet = false;

	/**
	 * The default value of the '{@link #getVariableAccessSerializable() <em>Variable Access Serializable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariableAccessSerializable()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean VARIABLE_ACCESS_SERIALIZABLE_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getVariableAccessSerializable() <em>Variable Access Serializable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariableAccessSerializable()
	 * @generated
	 * @ordered
	 */
	protected Boolean variableAccessSerializable = VARIABLE_ACCESS_SERIALIZABLE_EDEFAULT;

	/**
	 * This is true if the Variable Access Serializable attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean variableAccessSerializableESet = false;

	/**
	 * The cached value of the '{@link #getPartnerLinks() <em>Partner Links</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartnerLinks()
	 * @generated
	 * @ordered
	 */
	protected PartnerLinks partnerLinks = null;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected Variables variables = null;

	/**
	 * The cached value of the '{@link #getActivity() <em>Activity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActivity()
	 * @generated
	 * @ordered
	 */
	protected Activity activity = null;

	/**
	 * The cached value of the '{@link #getFaultHandlers() <em>Fault Handlers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFaultHandlers()
	 * @generated
	 * @ordered
	 */
	protected FaultHandler faultHandlers = null;

	/**
	 * The cached value of the '{@link #getEventHandlers() <em>Event Handlers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventHandlers()
	 * @generated
	 * @ordered
	 */
	protected EventHandler eventHandlers = null;

	/**
	 * The cached value of the '{@link #getCorrelationSets() <em>Correlation Sets</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrelationSets()
	 * @generated
	 * @ordered
	 */
	protected CorrelationSets correlationSets = null;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList imports = null;

	/**
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
	protected Extensions extensions = null;

	/**
	 * The default value of the '{@link #getExitOnStandardFault() <em>Exit On Standard Fault</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExitOnStandardFault()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean EXIT_ON_STANDARD_FAULT_EDEFAULT = Boolean.TRUE;

	/**
	 * The cached value of the '{@link #getExitOnStandardFault() <em>Exit On Standard Fault</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExitOnStandardFault()
	 * @generated
	 * @ordered
	 */
	protected Boolean exitOnStandardFault = EXIT_ON_STANDARD_FAULT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getProcess();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTargetNamespace() {
		return targetNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetNamespace(String newTargetNamespace) {
		String oldTargetNamespace = targetNamespace;
		targetNamespace = newTargetNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__TARGET_NAMESPACE, oldTargetNamespace, targetNamespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQueryLanguage() {
		return queryLanguage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueryLanguage(String newQueryLanguage) {
		String oldQueryLanguage = queryLanguage;
		queryLanguage = newQueryLanguage;
		boolean oldQueryLanguageESet = queryLanguageESet;
		queryLanguageESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__QUERY_LANGUAGE, oldQueryLanguage, queryLanguage, !oldQueryLanguageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueryLanguage() {
		String oldQueryLanguage = queryLanguage;
		boolean oldQueryLanguageESet = queryLanguageESet;
		queryLanguage = QUERY_LANGUAGE_EDEFAULT;
		queryLanguageESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.PROCESS__QUERY_LANGUAGE, oldQueryLanguage, QUERY_LANGUAGE_EDEFAULT, oldQueryLanguageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueryLanguage() {
		return queryLanguageESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__EXPRESSION_LANGUAGE, oldExpressionLanguage, expressionLanguage, !oldExpressionLanguageESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.PROCESS__EXPRESSION_LANGUAGE, oldExpressionLanguage, EXPRESSION_LANGUAGE_EDEFAULT, oldExpressionLanguageESet));
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
	public Boolean getSuppressJoinFailure() {
		return suppressJoinFailure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuppressJoinFailure(Boolean newSuppressJoinFailure) {
		Boolean oldSuppressJoinFailure = suppressJoinFailure;
		suppressJoinFailure = newSuppressJoinFailure;
		boolean oldSuppressJoinFailureESet = suppressJoinFailureESet;
		suppressJoinFailureESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__SUPPRESS_JOIN_FAILURE, oldSuppressJoinFailure, suppressJoinFailure, !oldSuppressJoinFailureESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSuppressJoinFailure() {
		Boolean oldSuppressJoinFailure = suppressJoinFailure;
		boolean oldSuppressJoinFailureESet = suppressJoinFailureESet;
		suppressJoinFailure = SUPPRESS_JOIN_FAILURE_EDEFAULT;
		suppressJoinFailureESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.PROCESS__SUPPRESS_JOIN_FAILURE, oldSuppressJoinFailure, SUPPRESS_JOIN_FAILURE_EDEFAULT, oldSuppressJoinFailureESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSuppressJoinFailure() {
		return suppressJoinFailureESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getVariableAccessSerializable() {
		return variableAccessSerializable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariableAccessSerializable(Boolean newVariableAccessSerializable) {
		Boolean oldVariableAccessSerializable = variableAccessSerializable;
		variableAccessSerializable = newVariableAccessSerializable;
		boolean oldVariableAccessSerializableESet = variableAccessSerializableESet;
		variableAccessSerializableESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__VARIABLE_ACCESS_SERIALIZABLE, oldVariableAccessSerializable, variableAccessSerializable, !oldVariableAccessSerializableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetVariableAccessSerializable() {
		Boolean oldVariableAccessSerializable = variableAccessSerializable;
		boolean oldVariableAccessSerializableESet = variableAccessSerializableESet;
		variableAccessSerializable = VARIABLE_ACCESS_SERIALIZABLE_EDEFAULT;
		variableAccessSerializableESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.PROCESS__VARIABLE_ACCESS_SERIALIZABLE, oldVariableAccessSerializable, VARIABLE_ACCESS_SERIALIZABLE_EDEFAULT, oldVariableAccessSerializableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetVariableAccessSerializable() {
		return variableAccessSerializableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLinks getPartnerLinks() {
		return partnerLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPartnerLinks(PartnerLinks newPartnerLinks, NotificationChain msgs) {
		PartnerLinks oldPartnerLinks = partnerLinks;
		partnerLinks = newPartnerLinks;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__PARTNER_LINKS, oldPartnerLinks, newPartnerLinks);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartnerLinks(PartnerLinks newPartnerLinks) {
		if (newPartnerLinks != partnerLinks) {
			NotificationChain msgs = null;
			if (partnerLinks != null)
				msgs = ((InternalEObject)partnerLinks).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__PARTNER_LINKS, null, msgs);
			if (newPartnerLinks != null)
				msgs = ((InternalEObject)newPartnerLinks).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__PARTNER_LINKS, null, msgs);
			msgs = basicSetPartnerLinks(newPartnerLinks, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__PARTNER_LINKS, newPartnerLinks, newPartnerLinks));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variables getVariables() {
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVariables(Variables newVariables, NotificationChain msgs) {
		Variables oldVariables = variables;
		variables = newVariables;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__VARIABLES, oldVariables, newVariables);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariables(Variables newVariables) {
		if (newVariables != variables) {
			NotificationChain msgs = null;
			if (variables != null)
				msgs = ((InternalEObject)variables).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__VARIABLES, null, msgs);
			if (newVariables != null)
				msgs = ((InternalEObject)newVariables).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__VARIABLES, null, msgs);
			msgs = basicSetVariables(newVariables, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__VARIABLES, newVariables, newVariables));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivity(Activity newActivity, NotificationChain msgs) {
		Activity oldActivity = activity;
		activity = newActivity;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__ACTIVITY, oldActivity, newActivity);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivity(Activity newActivity) {
		if (newActivity != activity) {
			NotificationChain msgs = null;
			if (activity != null)
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FaultHandler getFaultHandlers() {
		return faultHandlers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFaultHandlers(FaultHandler newFaultHandlers, NotificationChain msgs) {
		FaultHandler oldFaultHandlers = faultHandlers;
		faultHandlers = newFaultHandlers;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__FAULT_HANDLERS, oldFaultHandlers, newFaultHandlers);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFaultHandlers(FaultHandler newFaultHandlers) {
		if (newFaultHandlers != faultHandlers) {
			NotificationChain msgs = null;
			if (faultHandlers != null)
				msgs = ((InternalEObject)faultHandlers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__FAULT_HANDLERS, null, msgs);
			if (newFaultHandlers != null)
				msgs = ((InternalEObject)newFaultHandlers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__FAULT_HANDLERS, null, msgs);
			msgs = basicSetFaultHandlers(newFaultHandlers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__FAULT_HANDLERS, newFaultHandlers, newFaultHandlers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Extensions getExtensions() {
		return extensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtensions(Extensions newExtensions, NotificationChain msgs) {
		Extensions oldExtensions = extensions;
		extensions = newExtensions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__EXTENSIONS, oldExtensions, newExtensions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensions(Extensions newExtensions) {
		if (newExtensions != extensions) {
			NotificationChain msgs = null;
			if (extensions != null)
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getExitOnStandardFault() {
		return exitOnStandardFault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExitOnStandardFault(Boolean newExitOnStandardFault) {
		Boolean oldExitOnStandardFault = exitOnStandardFault;
		exitOnStandardFault = newExitOnStandardFault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__EXIT_ON_STANDARD_FAULT, oldExitOnStandardFault, exitOnStandardFault));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventHandler getEventHandlers() {
		return eventHandlers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEventHandlers(EventHandler newEventHandlers, NotificationChain msgs) {
		EventHandler oldEventHandlers = eventHandlers;
		eventHandlers = newEventHandlers;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__EVENT_HANDLERS, oldEventHandlers, newEventHandlers);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventHandlers(EventHandler newEventHandlers) {
		if (newEventHandlers != eventHandlers) {
			NotificationChain msgs = null;
			if (eventHandlers != null)
				msgs = ((InternalEObject)eventHandlers).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__EVENT_HANDLERS, null, msgs);
			if (newEventHandlers != null)
				msgs = ((InternalEObject)newEventHandlers).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__EVENT_HANDLERS, null, msgs);
			msgs = basicSetEventHandlers(newEventHandlers, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__EVENT_HANDLERS, newEventHandlers, newEventHandlers));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrelationSets getCorrelationSets() {
		return correlationSets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCorrelationSets(CorrelationSets newCorrelationSets, NotificationChain msgs) {
		CorrelationSets oldCorrelationSets = correlationSets;
		correlationSets = newCorrelationSets;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__CORRELATION_SETS, oldCorrelationSets, newCorrelationSets);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorrelationSets(CorrelationSets newCorrelationSets) {
		if (newCorrelationSets != correlationSets) {
			NotificationChain msgs = null;
			if (correlationSets != null)
				msgs = ((InternalEObject)correlationSets).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__CORRELATION_SETS, null, msgs);
			if (newCorrelationSets != null)
				msgs = ((InternalEObject)newCorrelationSets).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PROCESS__CORRELATION_SETS, null, msgs);
			msgs = basicSetCorrelationSets(newCorrelationSets, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PROCESS__CORRELATION_SETS, newCorrelationSets, newCorrelationSets));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getImports() {
		if (imports == null) {
			imports = new EObjectContainmentEList(Import.class, this, BPELPackage.PROCESS__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.PROCESS__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.PROCESS__PARTNER_LINKS:
					return basicSetPartnerLinks(null, msgs);
				case BPELPackage.PROCESS__VARIABLES:
					return basicSetVariables(null, msgs);
				case BPELPackage.PROCESS__ACTIVITY:
					return basicSetActivity(null, msgs);
				case BPELPackage.PROCESS__FAULT_HANDLERS:
					return basicSetFaultHandlers(null, msgs);
				case BPELPackage.PROCESS__EVENT_HANDLERS:
					return basicSetEventHandlers(null, msgs);
				case BPELPackage.PROCESS__CORRELATION_SETS:
					return basicSetCorrelationSets(null, msgs);
				case BPELPackage.PROCESS__IMPORTS:
					return ((InternalEList)getImports()).basicRemove(otherEnd, msgs);
				case BPELPackage.PROCESS__EXTENSIONS:
					return basicSetExtensions(null, msgs);
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
			case BPELPackage.PROCESS__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.PROCESS__ELEMENT:
				return getElement();
			case BPELPackage.PROCESS__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.PROCESS__NAME:
				return getName();
			case BPELPackage.PROCESS__TARGET_NAMESPACE:
				return getTargetNamespace();
			case BPELPackage.PROCESS__QUERY_LANGUAGE:
				return getQueryLanguage();
			case BPELPackage.PROCESS__EXPRESSION_LANGUAGE:
				return getExpressionLanguage();
			case BPELPackage.PROCESS__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.PROCESS__VARIABLE_ACCESS_SERIALIZABLE:
				return getVariableAccessSerializable();
			case BPELPackage.PROCESS__PARTNER_LINKS:
				return getPartnerLinks();
			case BPELPackage.PROCESS__VARIABLES:
				return getVariables();
			case BPELPackage.PROCESS__ACTIVITY:
				return getActivity();
			case BPELPackage.PROCESS__FAULT_HANDLERS:
				return getFaultHandlers();
			case BPELPackage.PROCESS__EVENT_HANDLERS:
				return getEventHandlers();
			case BPELPackage.PROCESS__CORRELATION_SETS:
				return getCorrelationSets();
			case BPELPackage.PROCESS__IMPORTS:
				return getImports();
			case BPELPackage.PROCESS__EXTENSIONS:
				return getExtensions();
			case BPELPackage.PROCESS__EXIT_ON_STANDARD_FAULT:
				return getExitOnStandardFault();
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
			case BPELPackage.PROCESS__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.PROCESS__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.PROCESS__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.PROCESS__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.PROCESS__TARGET_NAMESPACE:
				setTargetNamespace((String)newValue);
				return;
			case BPELPackage.PROCESS__QUERY_LANGUAGE:
				setQueryLanguage((String)newValue);
				return;
			case BPELPackage.PROCESS__EXPRESSION_LANGUAGE:
				setExpressionLanguage((String)newValue);
				return;
			case BPELPackage.PROCESS__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.PROCESS__VARIABLE_ACCESS_SERIALIZABLE:
				setVariableAccessSerializable((Boolean)newValue);
				return;
			case BPELPackage.PROCESS__PARTNER_LINKS:
				setPartnerLinks((PartnerLinks)newValue);
				return;
			case BPELPackage.PROCESS__VARIABLES:
				setVariables((Variables)newValue);
				return;
			case BPELPackage.PROCESS__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case BPELPackage.PROCESS__FAULT_HANDLERS:
				setFaultHandlers((FaultHandler)newValue);
				return;
			case BPELPackage.PROCESS__EVENT_HANDLERS:
				setEventHandlers((EventHandler)newValue);
				return;
			case BPELPackage.PROCESS__CORRELATION_SETS:
				setCorrelationSets((CorrelationSets)newValue);
				return;
			case BPELPackage.PROCESS__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection)newValue);
				return;
			case BPELPackage.PROCESS__EXTENSIONS:
				setExtensions((Extensions)newValue);
				return;
			case BPELPackage.PROCESS__EXIT_ON_STANDARD_FAULT:
				setExitOnStandardFault((Boolean)newValue);
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
			case BPELPackage.PROCESS__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PROCESS__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PROCESS__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.PROCESS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.PROCESS__TARGET_NAMESPACE:
				setTargetNamespace(TARGET_NAMESPACE_EDEFAULT);
				return;
			case BPELPackage.PROCESS__QUERY_LANGUAGE:
				unsetQueryLanguage();
				return;
			case BPELPackage.PROCESS__EXPRESSION_LANGUAGE:
				unsetExpressionLanguage();
				return;
			case BPELPackage.PROCESS__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.PROCESS__VARIABLE_ACCESS_SERIALIZABLE:
				unsetVariableAccessSerializable();
				return;
			case BPELPackage.PROCESS__PARTNER_LINKS:
				setPartnerLinks((PartnerLinks)null);
				return;
			case BPELPackage.PROCESS__VARIABLES:
				setVariables((Variables)null);
				return;
			case BPELPackage.PROCESS__ACTIVITY:
				setActivity((Activity)null);
				return;
			case BPELPackage.PROCESS__FAULT_HANDLERS:
				setFaultHandlers((FaultHandler)null);
				return;
			case BPELPackage.PROCESS__EVENT_HANDLERS:
				setEventHandlers((EventHandler)null);
				return;
			case BPELPackage.PROCESS__CORRELATION_SETS:
				setCorrelationSets((CorrelationSets)null);
				return;
			case BPELPackage.PROCESS__IMPORTS:
				getImports().clear();
				return;
			case BPELPackage.PROCESS__EXTENSIONS:
				setExtensions((Extensions)null);
				return;
			case BPELPackage.PROCESS__EXIT_ON_STANDARD_FAULT:
				setExitOnStandardFault(EXIT_ON_STANDARD_FAULT_EDEFAULT);
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
			case BPELPackage.PROCESS__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.PROCESS__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.PROCESS__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.PROCESS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.PROCESS__TARGET_NAMESPACE:
				return TARGET_NAMESPACE_EDEFAULT == null ? targetNamespace != null : !TARGET_NAMESPACE_EDEFAULT.equals(targetNamespace);
			case BPELPackage.PROCESS__QUERY_LANGUAGE:
				return isSetQueryLanguage();
			case BPELPackage.PROCESS__EXPRESSION_LANGUAGE:
				return isSetExpressionLanguage();
			case BPELPackage.PROCESS__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.PROCESS__VARIABLE_ACCESS_SERIALIZABLE:
				return isSetVariableAccessSerializable();
			case BPELPackage.PROCESS__PARTNER_LINKS:
				return partnerLinks != null;
			case BPELPackage.PROCESS__VARIABLES:
				return variables != null;
			case BPELPackage.PROCESS__ACTIVITY:
				return activity != null;
			case BPELPackage.PROCESS__FAULT_HANDLERS:
				return faultHandlers != null;
			case BPELPackage.PROCESS__EVENT_HANDLERS:
				return eventHandlers != null;
			case BPELPackage.PROCESS__CORRELATION_SETS:
				return correlationSets != null;
			case BPELPackage.PROCESS__IMPORTS:
				return imports != null && !imports.isEmpty();
			case BPELPackage.PROCESS__EXTENSIONS:
				return extensions != null;
			case BPELPackage.PROCESS__EXIT_ON_STANDARD_FAULT:
				return EXIT_ON_STANDARD_FAULT_EDEFAULT == null ? exitOnStandardFault != null : !EXIT_ON_STANDARD_FAULT_EDEFAULT.equals(exitOnStandardFault);
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
		result.append(", targetNamespace: ");
		result.append(targetNamespace);
		result.append(", queryLanguage: ");
		if (queryLanguageESet) result.append(queryLanguage); else result.append("<unset>");
		result.append(", expressionLanguage: ");
		if (expressionLanguageESet) result.append(expressionLanguage); else result.append("<unset>");
		result.append(", suppressJoinFailure: ");
		if (suppressJoinFailureESet) result.append(suppressJoinFailure); else result.append("<unset>");
		result.append(", variableAccessSerializable: ");
		if (variableAccessSerializableESet) result.append(variableAccessSerializable); else result.append("<unset>");
		result.append(", exitOnStandardFault: ");
		result.append(exitOnStandardFault);
		result.append(')');
		return result.toString();
	}

	/**
	 * @customized
	 */
	private List fieldPostLoadRunnables;
	
	/**
	 * @see org.eclipse.bpel.model.Process#getPostLoadRunnables()
	 * @customized
	 */
	public List getPostLoadRunnables() {
		if (fieldPostLoadRunnables == null)
			fieldPostLoadRunnables = new ArrayList();
		return fieldPostLoadRunnables;
	}

} //ProcessImpl
