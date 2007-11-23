/**
 * <copyright>
 * </copyright>
 *
 * $Id: MessageExchangesImpl.java,v 1.5 2007/11/23 17:30:13 smoser Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.MessageExchange;
import org.eclipse.bpel.model.MessageExchanges;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.model.util.ReconciliationHelper;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.w3c.dom.Element;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message Exchanges</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.MessageExchangesImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessageExchangesImpl extends ExtensibleElementImpl implements
		MessageExchanges {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<MessageExchange> children;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MessageExchangesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BPELPackage.Literals.MESSAGE_EXCHANGES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MessageExchange> getChildren() {
		if (children == null) {
			children = new EObjectResolvingEList<MessageExchange>(
					MessageExchange.class, this,
					BPELPackage.MESSAGE_EXCHANGES__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BPELPackage.MESSAGE_EXCHANGES__CHILDREN:
				return getChildren();
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
			case BPELPackage.MESSAGE_EXCHANGES__CHILDREN:
				getChildren().clear();
				getChildren().addAll(
						(Collection<? extends MessageExchange>) newValue);
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
			case BPELPackage.MESSAGE_EXCHANGES__CHILDREN:
				getChildren().clear();
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
			case BPELPackage.MESSAGE_EXCHANGES__CHILDREN:
				return children != null && !children.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	protected void adoptContent(EReference reference, Object object) {
		if (object instanceof MessageExchange) {
			ReconciliationHelper.adoptChild(this, children, (MessageExchange)object, BPELConstants.ND_MESSAGE_EXCHANGE);
		}
		super.adoptContent(reference, object);
	}
	
	protected void orphanContent(EReference reference, Object obj) {
		if (obj instanceof MessageExchange) {
			ReconciliationHelper.orphanChild(this, (MessageExchange)obj);
		}
		super.orphanContent(reference, obj);
	}

} //MessageExchangesImpl
