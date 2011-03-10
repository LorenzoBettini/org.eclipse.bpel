/**
 * <copyright>
 * </copyright>
 *
 * $Id: SampleStructuredActivityImpl.java,v 1.3 2011/03/10 18:18:18 rbrodt Exp $
 */
package org.eclipse.bpel.extensionsample.model.impl;

import javax.xml.namespace.QName;

import org.eclipse.bpel.extensionsample.model.ModelPackage;
import org.eclipse.bpel.extensionsample.model.SampleStructuredActivity;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.impl.ExtensionActivityImpl;
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.model.util.ReconciliationHelper;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '
 * <em><b>Sample Structured Activity</b></em>'.
 * 
 * Bug 120110 - the model has been updated to include a Variable
 * reference for the SampleSimpleActivity and a Variable definition
 * for the SampleStructuredActivity.
 * 
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.extensionsample.model.impl.SampleStructuredActivityImpl#getActivity <em>Activity</em>}</li>
 *   <li>{@link org.eclipse.bpel.extensionsample.model.impl.SampleStructuredActivityImpl#getVariable <em>Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SampleStructuredActivityImpl extends ExtensionActivityImpl implements
		SampleStructuredActivity {
	/**
	 * The cached value of the '{@link #getActivity() <em>Activity</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getActivity()
	 * @generated
	 * @ordered
	 */
	protected Activity activity;

	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable variable;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected SampleStructuredActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SAMPLE_STRUCTURED_ACTIVITY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @customized
	 */
	public NotificationChain basicSetActivity(Activity newActivity, NotificationChain msgs) {
		Activity oldActivity = activity;
		if (!isReconciling) {
			ReconciliationHelper.replaceChild(this, oldActivity, newActivity);
		}
		activity = newActivity;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY, oldActivity, newActivity);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivity(Activity newActivity) {
		if (newActivity != activity) {
			NotificationChain msgs = null;
			if (activity != null)
				msgs = ((InternalEObject)activity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY, null, msgs);
			if (newActivity != null)
				msgs = ((InternalEObject)newActivity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY, null, msgs);
			msgs = basicSetActivity(newActivity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY, newActivity, newActivity));
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
		if (!isReconciling) {
			
			ReconciliationHelper.replaceAttribute(this,
					"variable",
					newVariable == null ? null : newVariable
							.getName());
			ReconciliationHelper.replaceAttribute(this,
					"messageType",
					newVariable == null || newVariable.getMessageType() == null ?
							null :
							newVariable.getMessageType().getQName());
			ReconciliationHelper.replaceAttribute(this,
					"type",
					newVariable == null || newVariable.getType() == null ?
							null :
							newVariable.getType().getQName());
			ReconciliationHelper.replaceAttribute(this,
					"element",
					newVariable == null || newVariable.getXSDElement() == null ?
							null :
							newVariable.getXSDElement().getQName());
		}
		variable = newVariable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE, oldVariable, newVariable);
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
				msgs = ((InternalEObject)variable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE, null, msgs);
			if (newVariable != null)
				msgs = ((InternalEObject)newVariable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE, null, msgs);
			msgs = basicSetVariable(newVariable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE, newVariable, newVariable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY:
				return basicSetActivity(null, msgs);
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE:
				return basicSetVariable(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY:
				return getActivity();
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE:
				return getVariable();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY:
				setActivity((Activity)newValue);
				return;
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE:
				setVariable((Variable)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY:
				setActivity((Activity)null);
				return;
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE:
				setVariable((Variable)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__ACTIVITY:
				return activity != null;
			case ModelPackage.SAMPLE_STRUCTURED_ACTIVITY__VARIABLE:
				return variable != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	protected void adoptContent(EReference reference, Object object) {
		if (object instanceof Activity) {
			ReconciliationHelper.replaceChild(this, activity, (Activity) object);
		}
		super.adoptContent(reference, object);

	}
	
	@Override
	protected void orphanContent(EReference reference, Object obj) {
		if (obj instanceof Activity) {
			ReconciliationHelper.orphanChild(this, (Activity)obj);
		}
		super.orphanContent(reference, obj);
	}
	
} // SampleStructuredActivityImpl
