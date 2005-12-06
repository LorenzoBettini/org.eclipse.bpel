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
 * $Id: OnEventImpl.java,v 1.2 2005/12/06 02:05:30 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.FromPart;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.model.partnerlinktype.RolePortType;
import org.eclipse.bpel.model.proxy.OperationProxy;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;

import org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl;

import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>On Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getPartnerLink <em>Partner Link</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getCorrelations <em>Correlations</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getOperation <em>Operation</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getPortType <em>Port Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getMessageType <em>Message Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.OnEventImpl#getFromPart <em>From Part</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OnEventImpl extends ExtensibleElementImpl implements OnEvent {
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
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable variable = null;

	/**
	 * The cached value of the '{@link #getPartnerLink() <em>Partner Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartnerLink()
	 * @generated
	 * @ordered
	 */
	protected PartnerLink partnerLink = null;

	/**
	 * The cached value of the '{@link #getCorrelations() <em>Correlations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrelations()
	 * @generated
	 * @ordered
	 */
	protected Correlations correlations = null;

	/**
	 * The cached value of the '{@link #getOperation() <em>Operation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperation()
	 * @generated
	 * @ordered
	 */
	protected Operation operation = null;

	/**
	 * The cached value of the '{@link #getPortType() <em>Port Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortType()
	 * @generated
	 * @ordered
	 */
	protected PortType portType = null;

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
	 * The cached value of the '{@link #getFromPart() <em>From Part</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFromPart()
	 * @generated
	 * @ordered
	 */
	protected EList fromPart = null;

    /**
     * The deserialized value of the operation name.
     * @generated NOT
     */
    protected String operationName;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OnEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getOnEvent();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__ACTIVITY, oldActivity, newActivity);
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
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_EVENT__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_EVENT__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getVariable() {
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVariable(Variable newVariable, NotificationChain msgs) {
		Variable oldVariable = variable;
		variable = newVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__VARIABLE, oldVariable, newVariable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(Variable newVariable) {
		if (newVariable != variable) {
			NotificationChain msgs = null;
			if (variable != null)
				msgs = ((InternalEObject)variable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_EVENT__VARIABLE, null, msgs);
			if (newVariable != null)
				msgs = ((InternalEObject)newVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_EVENT__VARIABLE, null, msgs);
			msgs = basicSetVariable(newVariable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__VARIABLE, newVariable, newVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLink getPartnerLink() {
		if (partnerLink != null && partnerLink.eIsProxy()) {
			PartnerLink oldPartnerLink = partnerLink;
			partnerLink = (PartnerLink)eResolveProxy((InternalEObject)partnerLink);
			if (partnerLink != oldPartnerLink) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.ON_EVENT__PARTNER_LINK, oldPartnerLink, partnerLink));
			}
		}
		return partnerLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PartnerLink basicGetPartnerLink() {
		return partnerLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPartnerLink(PartnerLink newPartnerLink) {
		PartnerLink oldPartnerLink = partnerLink;
		partnerLink = newPartnerLink;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__PARTNER_LINK, oldPartnerLink, partnerLink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Correlations getCorrelations() {
		return correlations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCorrelations(Correlations newCorrelations, NotificationChain msgs) {
		Correlations oldCorrelations = correlations;
		correlations = newCorrelations;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__CORRELATIONS, oldCorrelations, newCorrelations);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorrelations(Correlations newCorrelations) {
		if (newCorrelations != correlations) {
			NotificationChain msgs = null;
			if (correlations != null)
				msgs = ((InternalEObject)correlations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_EVENT__CORRELATIONS, null, msgs);
			if (newCorrelations != null)
				msgs = ((InternalEObject)newCorrelations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.ON_EVENT__CORRELATIONS, null, msgs);
			msgs = basicSetCorrelations(newCorrelations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__CORRELATIONS, newCorrelations, newCorrelations));
	}

    /**
     * Customizes {@link #getOperationGen()} to handle the case where the port type is not specified.
     * @customized
     */
    public Operation getOperation() {
        if (operation == null && operationName != null) {
            PortType portType = getPortType();
            if (portType != null) {
                // Create an operation proxy with the deserialized operation name.
                operation = new OperationProxy(eResource().getURI(), portType, operationName);
                operationName = null;
            }
        }
        return getOperationGen();
    }
    
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation getOperationGen() {
		if (operation != null && operation.eIsProxy()) {
			Operation oldOperation = operation;
			operation = (Operation)eResolveProxy((InternalEObject)operation);
			if (operation != oldOperation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.ON_EVENT__OPERATION, oldOperation, operation));
			}
		}
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation basicGetOperation() {
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperation(Operation newOperation) {
		Operation oldOperation = operation;
		operation = newOperation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__OPERATION, oldOperation, operation));
	}

    /**
     * Set the deserialized value of the operation name.
     * @generated NOT
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
    
    /**
     * Customizes {@link #getPortTypeGen()} to handle the case where the port type is not specified.
     * @generated NOT
     */
    public PortType getPortType() {
        if (portType != null) {
            return getPortTypeGen();
        } else {
            // portType is now optional. If the user hasn't set it, then
            // infer it from the partnerLink attribute and the 
            // direction of this activity.
            PartnerLink link = getPartnerLink();
            if (link != null) {
                Role role = link.getMyRole();
                if (role != null) {
                    RolePortType rpt = role.getPortType();
                    if (rpt != null) {
                        portType = (PortType)rpt.getName();
                    }
                }
            }
            return portType;
        }
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortType getPortTypeGen() {
		if (portType != null && portType.eIsProxy()) {
			PortType oldPortType = portType;
			portType = (PortType)eResolveProxy((InternalEObject)portType);
			if (portType != oldPortType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.ON_EVENT__PORT_TYPE, oldPortType, portType));
			}
		}
		return portType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PortType basicGetPortType() {
		return portType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPortType(PortType newPortType) {
		PortType oldPortType = portType;
		portType = newPortType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__PORT_TYPE, oldPortType, portType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @customized
	 */
	public Message getMessageType() {
		Variable variable = getVariable();
		if (variable != null && variable instanceof Variable) {
			return ((Variable)variable).getMessageType();
		}
		if (messageType != null && messageType.eIsProxy()) {
			Message oldMessageType = messageType;
			messageType = (Message)eResolveProxy((InternalEObject)messageType);
			if (messageType != oldMessageType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.ON_EVENT__MESSAGE_TYPE, oldMessageType, messageType));
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
	 * @customized
	 */
	public void setMessageType(Message newMessageType) {
		Variable variable = getVariable();
		if (variable != null && variable instanceof Variable) {
			((Variable)variable).setMessageType(newMessageType);
		}
		Message oldMessageType = messageType;
		messageType = newMessageType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.ON_EVENT__MESSAGE_TYPE, oldMessageType, messageType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFromPart() {
		if (fromPart == null) {
			fromPart = new EObjectResolvingEList(FromPart.class, this, BPELPackage.ON_EVENT__FROM_PART);
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
				case BPELPackage.ON_EVENT__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.ON_EVENT__ACTIVITY:
					return basicSetActivity(null, msgs);
				case BPELPackage.ON_EVENT__VARIABLE:
					return basicSetVariable(null, msgs);
				case BPELPackage.ON_EVENT__CORRELATIONS:
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
			case BPELPackage.ON_EVENT__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.ON_EVENT__ELEMENT:
				return getElement();
			case BPELPackage.ON_EVENT__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.ON_EVENT__ACTIVITY:
				return getActivity();
			case BPELPackage.ON_EVENT__VARIABLE:
				return getVariable();
			case BPELPackage.ON_EVENT__PARTNER_LINK:
				if (resolve) return getPartnerLink();
				return basicGetPartnerLink();
			case BPELPackage.ON_EVENT__CORRELATIONS:
				return getCorrelations();
			case BPELPackage.ON_EVENT__OPERATION:
				if (resolve) return getOperation();
				return basicGetOperation();
			case BPELPackage.ON_EVENT__PORT_TYPE:
				if (resolve) return getPortType();
				return basicGetPortType();
			case BPELPackage.ON_EVENT__MESSAGE_TYPE:
				if (resolve) return getMessageType();
				return basicGetMessageType();
			case BPELPackage.ON_EVENT__FROM_PART:
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
			case BPELPackage.ON_EVENT__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.ON_EVENT__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.ON_EVENT__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.ON_EVENT__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case BPELPackage.ON_EVENT__VARIABLE:
				setVariable((Variable)newValue);
				return;
			case BPELPackage.ON_EVENT__PARTNER_LINK:
				setPartnerLink((PartnerLink)newValue);
				return;
			case BPELPackage.ON_EVENT__CORRELATIONS:
				setCorrelations((Correlations)newValue);
				return;
			case BPELPackage.ON_EVENT__OPERATION:
				setOperation((Operation)newValue);
				return;
			case BPELPackage.ON_EVENT__PORT_TYPE:
				setPortType((PortType)newValue);
				return;
			case BPELPackage.ON_EVENT__MESSAGE_TYPE:
				setMessageType((Message)newValue);
				return;
			case BPELPackage.ON_EVENT__FROM_PART:
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
			case BPELPackage.ON_EVENT__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ON_EVENT__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.ON_EVENT__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.ON_EVENT__ACTIVITY:
				setActivity((Activity)null);
				return;
			case BPELPackage.ON_EVENT__VARIABLE:
				setVariable((Variable)null);
				return;
			case BPELPackage.ON_EVENT__PARTNER_LINK:
				setPartnerLink((PartnerLink)null);
				return;
			case BPELPackage.ON_EVENT__CORRELATIONS:
				setCorrelations((Correlations)null);
				return;
			case BPELPackage.ON_EVENT__OPERATION:
				setOperation((Operation)null);
				return;
			case BPELPackage.ON_EVENT__PORT_TYPE:
				setPortType((PortType)null);
				return;
			case BPELPackage.ON_EVENT__MESSAGE_TYPE:
				setMessageType((Message)null);
				return;
			case BPELPackage.ON_EVENT__FROM_PART:
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
			case BPELPackage.ON_EVENT__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.ON_EVENT__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.ON_EVENT__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.ON_EVENT__ACTIVITY:
				return activity != null;
			case BPELPackage.ON_EVENT__VARIABLE:
				return variable != null;
			case BPELPackage.ON_EVENT__PARTNER_LINK:
				return partnerLink != null;
			case BPELPackage.ON_EVENT__CORRELATIONS:
				return correlations != null;
			case BPELPackage.ON_EVENT__OPERATION:
				return operation != null;
			case BPELPackage.ON_EVENT__PORT_TYPE:
				return portType != null;
			case BPELPackage.ON_EVENT__MESSAGE_TYPE:
				return messageType != null;
			case BPELPackage.ON_EVENT__FROM_PART:
				return fromPart != null && !fromPart.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

} //OnEventImpl
