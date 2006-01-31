/**
 * <copyright>
 * </copyright>
 *
 * $Id: ForEachImpl.java,v 1.4 2006/01/31 14:56:08 james Exp $
 */
package org.eclipse.bpel.model.impl;

import java.util.Collection;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.CompletionCondition;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.Iterator;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.Variable;
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
 * An implementation of the model object '<em><b>For Each</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.impl.ForEachImpl#getParallel <em>Parallel</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ForEachImpl#getCounterName <em>Counter Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ForEachImpl#getCompletionCondition <em>Completion Condition</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ForEachImpl#getIterator <em>Iterator</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.impl.ForEachImpl#getActivity <em>Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ForEachImpl extends ActivityImpl implements ForEach {
	/**
	 * The default value of the '{@link #getParallel() <em>Parallel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParallel()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean PARALLEL_EDEFAULT = Boolean.FALSE;

	/**
	 * The cached value of the '{@link #getParallel() <em>Parallel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParallel()
	 * @generated
	 * @ordered
	 */
	protected Boolean parallel = PARALLEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCounterName() <em>Counter Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCounterName()
	 * @generated
	 * @ordered
	 */
	protected Variable counterName = null;

	/**
	 * The cached value of the '{@link #getCompletionCondition() <em>Completion Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompletionCondition()
	 * @generated
	 * @ordered
	 */
	protected CompletionCondition completionCondition = null;

	/**
	 * The cached value of the '{@link #getIterator() <em>Iterator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterator()
	 * @generated
	 * @ordered
	 */
	protected Iterator iterator = null;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ForEachImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getForEach();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean getParallel() {
		return parallel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParallel(Boolean newParallel) {
		Boolean oldParallel = parallel;
		parallel = newParallel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__PARALLEL, oldParallel, parallel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getCounterName() {
		return counterName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCounterName(Variable newCounterName, NotificationChain msgs) {
		Variable oldCounterName = counterName;
		counterName = newCounterName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__COUNTER_NAME, oldCounterName, newCounterName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCounterName(Variable newCounterName) {
		if (newCounterName != counterName) {
			NotificationChain msgs = null;
			if (counterName != null)
				msgs = ((InternalEObject)counterName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__COUNTER_NAME, null, msgs);
			if (newCounterName != null)
				msgs = ((InternalEObject)newCounterName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__COUNTER_NAME, null, msgs);
			msgs = basicSetCounterName(newCounterName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__COUNTER_NAME, newCounterName, newCounterName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompletionCondition getCompletionCondition() {
		return completionCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompletionCondition(CompletionCondition newCompletionCondition, NotificationChain msgs) {
		CompletionCondition oldCompletionCondition = completionCondition;
		completionCondition = newCompletionCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__COMPLETION_CONDITION, oldCompletionCondition, newCompletionCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompletionCondition(CompletionCondition newCompletionCondition) {
		if (newCompletionCondition != completionCondition) {
			NotificationChain msgs = null;
			if (completionCondition != null)
				msgs = ((InternalEObject)completionCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__COMPLETION_CONDITION, null, msgs);
			if (newCompletionCondition != null)
				msgs = ((InternalEObject)newCompletionCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__COMPLETION_CONDITION, null, msgs);
			msgs = basicSetCompletionCondition(newCompletionCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__COMPLETION_CONDITION, newCompletionCondition, newCompletionCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterator getIterator() {
		return iterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIterator(Iterator newIterator, NotificationChain msgs) {
		Iterator oldIterator = iterator;
		iterator = newIterator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__ITERATOR, oldIterator, newIterator);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIterator(Iterator newIterator) {
		if (newIterator != iterator) {
			NotificationChain msgs = null;
			if (iterator != null)
				msgs = ((InternalEObject)iterator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__ITERATOR, null, msgs);
			if (newIterator != null)
				msgs = ((InternalEObject)newIterator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__ITERATOR, null, msgs);
			msgs = basicSetIterator(newIterator, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__ITERATOR, newIterator, newIterator));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__ACTIVITY, oldActivity, newActivity);
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
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BPELPackage.FOR_EACH__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BPELPackage.FOR_EACH__ACTIVITY, newActivity, newActivity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case BPELPackage.FOR_EACH__EEXTENSIBILITY_ELEMENTS:
					return ((InternalEList)getEExtensibilityElements()).basicRemove(otherEnd, msgs);
				case BPELPackage.FOR_EACH__DOCUMENTATION:
					return basicUnsetDocumentation(msgs);
				case BPELPackage.FOR_EACH__TARGETS:
					return basicSetTargets(null, msgs);
				case BPELPackage.FOR_EACH__SOURCES:
					return basicSetSources(null, msgs);
				case BPELPackage.FOR_EACH__COUNTER_NAME:
					return basicSetCounterName(null, msgs);
				case BPELPackage.FOR_EACH__COMPLETION_CONDITION:
					return basicSetCompletionCondition(null, msgs);
				case BPELPackage.FOR_EACH__ITERATOR:
					return basicSetIterator(null, msgs);
				case BPELPackage.FOR_EACH__ACTIVITY:
					return basicSetActivity(null, msgs);
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
			case BPELPackage.FOR_EACH__DOCUMENTATION_ELEMENT:
				return getDocumentationElement();
			case BPELPackage.FOR_EACH__ELEMENT:
				return getElement();
			case BPELPackage.FOR_EACH__EEXTENSIBILITY_ELEMENTS:
				return getEExtensibilityElements();
			case BPELPackage.FOR_EACH__DOCUMENTATION:
				return getDocumentation();
			case BPELPackage.FOR_EACH__NAME:
				return getName();
			case BPELPackage.FOR_EACH__SUPPRESS_JOIN_FAILURE:
				return getSuppressJoinFailure();
			case BPELPackage.FOR_EACH__TARGETS:
				return getTargets();
			case BPELPackage.FOR_EACH__SOURCES:
				return getSources();
			case BPELPackage.FOR_EACH__PARALLEL:
				return getParallel();
			case BPELPackage.FOR_EACH__COUNTER_NAME:
				return getCounterName();
			case BPELPackage.FOR_EACH__COMPLETION_CONDITION:
				return getCompletionCondition();
			case BPELPackage.FOR_EACH__ITERATOR:
				return getIterator();
			case BPELPackage.FOR_EACH__ACTIVITY:
				return getActivity();
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
			case BPELPackage.FOR_EACH__DOCUMENTATION_ELEMENT:
				setDocumentationElement((Element)newValue);
				return;
			case BPELPackage.FOR_EACH__ELEMENT:
				setElement((Element)newValue);
				return;
			case BPELPackage.FOR_EACH__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				getEExtensibilityElements().addAll((Collection)newValue);
				return;
			case BPELPackage.FOR_EACH__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case BPELPackage.FOR_EACH__NAME:
				setName((String)newValue);
				return;
			case BPELPackage.FOR_EACH__SUPPRESS_JOIN_FAILURE:
				setSuppressJoinFailure((Boolean)newValue);
				return;
			case BPELPackage.FOR_EACH__TARGETS:
				setTargets((Targets)newValue);
				return;
			case BPELPackage.FOR_EACH__SOURCES:
				setSources((Sources)newValue);
				return;
			case BPELPackage.FOR_EACH__PARALLEL:
				setParallel((Boolean)newValue);
				return;
			case BPELPackage.FOR_EACH__COUNTER_NAME:
				setCounterName((Variable)newValue);
				return;
			case BPELPackage.FOR_EACH__COMPLETION_CONDITION:
				setCompletionCondition((CompletionCondition)newValue);
				return;
			case BPELPackage.FOR_EACH__ITERATOR:
				setIterator((Iterator)newValue);
				return;
			case BPELPackage.FOR_EACH__ACTIVITY:
				setActivity((Activity)newValue);
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
			case BPELPackage.FOR_EACH__DOCUMENTATION_ELEMENT:
				setDocumentationElement(DOCUMENTATION_ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FOR_EACH__ELEMENT:
				setElement(ELEMENT_EDEFAULT);
				return;
			case BPELPackage.FOR_EACH__EEXTENSIBILITY_ELEMENTS:
				getEExtensibilityElements().clear();
				return;
			case BPELPackage.FOR_EACH__DOCUMENTATION:
				unsetDocumentation();
				return;
			case BPELPackage.FOR_EACH__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BPELPackage.FOR_EACH__SUPPRESS_JOIN_FAILURE:
				unsetSuppressJoinFailure();
				return;
			case BPELPackage.FOR_EACH__TARGETS:
				setTargets((Targets)null);
				return;
			case BPELPackage.FOR_EACH__SOURCES:
				setSources((Sources)null);
				return;
			case BPELPackage.FOR_EACH__PARALLEL:
				setParallel(PARALLEL_EDEFAULT);
				return;
			case BPELPackage.FOR_EACH__COUNTER_NAME:
				setCounterName((Variable)null);
				return;
			case BPELPackage.FOR_EACH__COMPLETION_CONDITION:
				setCompletionCondition((CompletionCondition)null);
				return;
			case BPELPackage.FOR_EACH__ITERATOR:
				setIterator((Iterator)null);
				return;
			case BPELPackage.FOR_EACH__ACTIVITY:
				setActivity((Activity)null);
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
			case BPELPackage.FOR_EACH__DOCUMENTATION_ELEMENT:
				return DOCUMENTATION_ELEMENT_EDEFAULT == null ? documentationElement != null : !DOCUMENTATION_ELEMENT_EDEFAULT.equals(documentationElement);
			case BPELPackage.FOR_EACH__ELEMENT:
				return ELEMENT_EDEFAULT == null ? element != null : !ELEMENT_EDEFAULT.equals(element);
			case BPELPackage.FOR_EACH__EEXTENSIBILITY_ELEMENTS:
				return eExtensibilityElements != null && !eExtensibilityElements.isEmpty();
			case BPELPackage.FOR_EACH__DOCUMENTATION:
				return isSetDocumentation();
			case BPELPackage.FOR_EACH__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BPELPackage.FOR_EACH__SUPPRESS_JOIN_FAILURE:
				return isSetSuppressJoinFailure();
			case BPELPackage.FOR_EACH__TARGETS:
				return targets != null;
			case BPELPackage.FOR_EACH__SOURCES:
				return sources != null;
			case BPELPackage.FOR_EACH__PARALLEL:
				return PARALLEL_EDEFAULT == null ? parallel != null : !PARALLEL_EDEFAULT.equals(parallel);
			case BPELPackage.FOR_EACH__COUNTER_NAME:
				return counterName != null;
			case BPELPackage.FOR_EACH__COMPLETION_CONDITION:
				return completionCondition != null;
			case BPELPackage.FOR_EACH__ITERATOR:
				return iterator != null;
			case BPELPackage.FOR_EACH__ACTIVITY:
				return activity != null;
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
		result.append(" (parallel: ");
		result.append(parallel);
		result.append(')');
		return result.toString();
	}

} //ForEachImpl
