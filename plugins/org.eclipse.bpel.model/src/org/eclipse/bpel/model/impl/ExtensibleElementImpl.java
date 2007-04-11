/**
 * <copyright>
 * </copyright>
 *
 * $Id: ExtensibleElementImpl.java,v 1.2 2007/04/11 20:45:15 mchmielewski Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.w3c.dom.Element;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Extensible Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ExtensibleElementImpl#getDocumentation <em>Documentation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExtensibleElementImpl extends org.eclipse.wst.wsdl.internal.impl.ExtensibleElementImpl implements ExtensibleElement {
	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected Documentation documentation = null;

	/**
	 * This is true if the Documentation containment reference has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean documentationESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExtensibleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getExtensibleElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Documentation getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(Documentation newDocumentation, NotificationChain msgs) {
		Documentation oldDocumentation = documentation;
		documentation = newDocumentation;
		boolean oldDocumentationESet = documentationESet;
		documentationESet = true;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION, oldDocumentation, newDocumentation, !oldDocumentationESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(Documentation newDocumentation) {
		if (newDocumentation != documentation) {
			NotificationChain msgs = null;
			if (documentation != null)
				msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION, null, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION, null, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDocumentationESet = documentationESet;
			documentationESet = true;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION, newDocumentation, newDocumentation, !oldDocumentationESet));
    	}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicUnsetDocumentation(NotificationChain msgs) {
		Documentation oldDocumentation = documentation;
		documentation = null;
		boolean oldDocumentationESet = documentationESet;
		documentationESet = false;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.UNSET, BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION, oldDocumentation, null, oldDocumentationESet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDocumentation() {
		if (documentation != null) {
			NotificationChain msgs = null;
			msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION, null, msgs);
			msgs = basicUnsetDocumentation(msgs);
			if (msgs != null) msgs.dispatch();
		}
		else {
			boolean oldDocumentationESet = documentationESet;
			documentationESet = false;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.UNSET, BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION, null, null, oldDocumentationESet));
    	}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDocumentation() {
		return documentationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.EXTENSIBLE_ELEMENT__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
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
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.EXTENSIBLE_ELEMENT__ELEMENT:
				return getElement();
			case BPELPackage.EXTENSIBLE_ELEMENT__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION:
				return getDocumentation();
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
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.EXTENSIBLE_ELEMENT__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.EXTENSIBLE_ELEMENT__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
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
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.EXTENSIBLE_ELEMENT__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.EXTENSIBLE_ELEMENT__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION:
				unsetDocumentation();
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
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.EXTENSIBLE_ELEMENT__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.EXTENSIBLE_ELEMENT__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.EXTENSIBLE_ELEMENT__DOCUMENTATION:
				return isSetDocumentation();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * Set the DOM element which has been read and which is the facade for this EMF 
	 * object.
	 * 
	 * @see org.eclipse.wst.wsdl.internal.impl.WSDLElementImpl#setElement(org.w3c.dom.Element)
	 */
	
	@Override
	public void setElement(Element elm) {		
		super.setElement(elm);
		// a pointer back to the EMF model.
		elm.setUserData("emf.model", this, null); //$NON-NLS-1$
	}
	
	/**
	 * @see org.eclipse.wst.wsdl.internal.impl.WSDLElementImpl#getElement()
	 */
	
	@Override
	public Element getElement () {
		return super.getElement();
	}
	
	
	/**
	 *  
	 */	
	
} //ExtensibleElementImpl
