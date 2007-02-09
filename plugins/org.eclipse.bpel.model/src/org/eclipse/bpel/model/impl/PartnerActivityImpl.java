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
 * $Id: PartnerActivityImpl.java,v 1.4 2007/02/09 09:13:42 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.PartnerActivity;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.model.proxy.OperationProxy;
import org.eclipse.bpel.model.proxy.PortTypeProxy;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.wst.wsdl.Operation;
import org.eclipse.wst.wsdl.PortType;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Partner Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerActivityImpl#getPartnerLink <em>Partner Link</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerActivityImpl#getCorrelations <em>Correlations</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerActivityImpl#getPortType <em>Port Type</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.PartnerActivityImpl#getOperation <em>Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PartnerActivityImpl extends ActivityImpl implements PartnerActivity {
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
	 * The cached value of the '{@link #getPortType() <em>Port Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortType()
	 * @generated
	 * @ordered
	 */
	protected PortType portType = null;

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
     * The deserialized value of the operation name.
     * @customized
     */
    protected String operationName;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PartnerActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getPartnerActivity();
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_ACTIVITY__PARTNER_LINK, oldPartnerLink, partnerLink));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_ACTIVITY__PARTNER_LINK, oldPartnerLink, partnerLink));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_ACTIVITY__CORRELATIONS, oldCorrelations, newCorrelations);
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
				msgs = ((InternalEObject)correlations).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PARTNER_ACTIVITY__CORRELATIONS, null, msgs);
			if (newCorrelations != null)
				msgs = ((InternalEObject)newCorrelations).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.PARTNER_ACTIVITY__CORRELATIONS, null, msgs);
			msgs = basicSetCorrelations(newCorrelations, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_ACTIVITY__CORRELATIONS, newCorrelations, newCorrelations));
	}

    /**
     * Customizes {@link #getPortTypeGen()} to handle the case where the port type is not specified.
     * @customized
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
                Role role = null;
                if (this instanceof Invoke) {
                    role = link.getPartnerRole();
                } else {
                    role = link.getMyRole();
                }
                if (role != null) {
                	portType = (PortType)role.getPortType();
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_ACTIVITY__PORT_TYPE, oldPortType, portType));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_ACTIVITY__PORT_TYPE, oldPortType, portType));
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
            } else {
            	portType = new PortTypeProxy(eResource().getURI(), new QName("null"));
            	operation = new OperationProxy(eResource().getURI(), portType, operationName);
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BPELPackage.PARTNER_ACTIVITY__OPERATION, oldOperation, operation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.PARTNER_ACTIVITY__OPERATION, oldOperation, operation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.PARTNER_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.PARTNER_ACTIVITY__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.PARTNER_ACTIVITY__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.PARTNER_ACTIVITY__CORRELATIONS:
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
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.PARTNER_ACTIVITY__ELEMENT:
				return getElement();
			case BPELPackage.PARTNER_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.PARTNER_ACTIVITY__NAME:
				return getName();
			case BPELPackage.PARTNER_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.PARTNER_ACTIVITY__TARGETS:
				return getTargets();
			case BPELPackage.PARTNER_ACTIVITY__SOURCES:
				return getSources();
			case BPELPackage.PARTNER_ACTIVITY__PARTNER_LINK:
				if (resolve) return getPartnerLink();
				return basicGetPartnerLink();
			case BPELPackage.PARTNER_ACTIVITY__CORRELATIONS:
				return getCorrelations();
			case BPELPackage.PARTNER_ACTIVITY__PORT_TYPE:
				if (resolve) return getPortType();
				return basicGetPortType();
			case BPELPackage.PARTNER_ACTIVITY__OPERATION:
				if (resolve) return getOperation();
				return basicGetOperation();
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
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__PARTNER_LINK:
				setPartnerLink((PartnerLink)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__CORRELATIONS:
				setCorrelations((Correlations)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__PORT_TYPE:
				setPortType((PortType)newValue);
				return;
			case BPELPackage.PARTNER_ACTIVITY__OPERATION:
				setOperation((Operation)newValue);
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
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PARTNER_ACTIVITY__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.PARTNER_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.PARTNER_ACTIVITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.PARTNER_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.PARTNER_ACTIVITY__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.PARTNER_ACTIVITY__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.PARTNER_ACTIVITY__PARTNER_LINK:
				setPartnerLink((PartnerLink)null);
				return;
			case BPELPackage.PARTNER_ACTIVITY__CORRELATIONS:
				setCorrelations((Correlations)null);
				return;
			case BPELPackage.PARTNER_ACTIVITY__PORT_TYPE:
				setPortType((PortType)null);
				return;
			case BPELPackage.PARTNER_ACTIVITY__OPERATION:
				setOperation((Operation)null);
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
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.PARTNER_ACTIVITY__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.PARTNER_ACTIVITY__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.PARTNER_ACTIVITY__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.PARTNER_ACTIVITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.PARTNER_ACTIVITY__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.PARTNER_ACTIVITY__TARGETS:
				return targets != null;
			case BPELPackage.PARTNER_ACTIVITY__SOURCES:
				return sources != null;
			case BPELPackage.PARTNER_ACTIVITY__PARTNER_LINK:
				return partnerLink != null;
			case BPELPackage.PARTNER_ACTIVITY__CORRELATIONS:
				return correlations != null;
			case BPELPackage.PARTNER_ACTIVITY__PORT_TYPE:
				return portType != null;
			case BPELPackage.PARTNER_ACTIVITY__OPERATION:
				return operation != null;
		}
		return eDynamicIsSet(eFeature);
	}

    /**
     * Set the deserialized value of the operation name.
     * @customized
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
    
} //PartnerActivityImpl
