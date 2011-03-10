/**
 * <copyright>
 * </copyright>
 *
 * $Id: SampleSimpleActivityImpl.java,v 1.2 2011/03/10 18:18:18 rbrodt Exp $
 */
package org.eclipse.bpel.extensionsample.model.impl;

import org.eclipse.bpel.extensionsample.model.ModelPackage;
import org.eclipse.bpel.extensionsample.model.SampleSimpleActivity;

import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.impl.ExtensionActivityImpl;
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.model.util.ReconciliationHelper;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '
 * <em><b>Sample Simple Activity</b></em>'.
 * 
 * Bug 120110 - the model has been updated to include a Variable
 * reference for the SampleSimpleActivity and a Variable definition
 * for the SampleStructuredActivity.
 * 
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpel.extensionsample.model.impl.SampleSimpleActivityImpl#getSampleExtensionAttribute <em>Sample Extension Attribute</em>}</li>
 *   <li>{@link org.eclipse.bpel.extensionsample.model.impl.SampleSimpleActivityImpl#getVariable <em>Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SampleSimpleActivityImpl extends ExtensionActivityImpl implements SampleSimpleActivity {
	/**
	 * The default value of the '{@link #getSampleExtensionAttribute()
	 * <em>Sample Extension Attribute</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getSampleExtensionAttribute()
	 * @generated
	 * @ordered
	 */
	protected static final String SAMPLE_EXTENSION_ATTRIBUTE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSampleExtensionAttribute()
	 * <em>Sample Extension Attribute</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getSampleExtensionAttribute()
	 * @generated
	 * @ordered
	 */
	protected String sampleExtensionAttribute = SAMPLE_EXTENSION_ATTRIBUTE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVariable() <em>Variable</em>}' reference.
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
	protected SampleSimpleActivityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SAMPLE_SIMPLE_ACTIVITY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getSampleExtensionAttribute() {
		return sampleExtensionAttribute;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @customized
	 */
	public void setSampleExtensionAttribute(String newSampleExtensionAttribute) {
		String oldSampleExtensionAttribute = sampleExtensionAttribute;
		if (!isReconciling) {
			ReconciliationHelper.replaceAttribute(this, ModelPackage.eINSTANCE
					.getSampleSimpleActivity_SampleExtensionAttribute().getName(),
					newSampleExtensionAttribute);
		}
		sampleExtensionAttribute = newSampleExtensionAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ModelPackage.SAMPLE_SIMPLE_ACTIVITY__SAMPLE_EXTENSION_ATTRIBUTE,
					oldSampleExtensionAttribute, sampleExtensionAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getVariable() {
		if (variable != null && variable.eIsProxy()) {
			InternalEObject oldVariable = (InternalEObject)variable;
			variable = (Variable)eResolveProxy(oldVariable);
			if (variable != oldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.SAMPLE_SIMPLE_ACTIVITY__VARIABLE, oldVariable, variable));
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
	 * @generated NOT
	 */
	public void setVariable(Variable newVariable) {
		if (newVariable != variable) {
			ENotificationImpl notification = null;
			Variable oldVariable = variable;
			if (!isReconciling) {
				ReconciliationHelper.replaceAttribute(this,
						"variable",
						newVariable == null ? null : newVariable.getName());
			}
			variable = newVariable;
			if (eNotificationRequired()) {
				notification = new ENotificationImpl(this,
						Notification.SET,
						ModelPackage.SAMPLE_SIMPLE_ACTIVITY__VARIABLE, oldVariable,
						newVariable);
				notification.dispatch();
			}
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ModelPackage.SAMPLE_SIMPLE_ACTIVITY__VARIABLE, newVariable,
					newVariable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__SAMPLE_EXTENSION_ATTRIBUTE:
				return getSampleExtensionAttribute();
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__VARIABLE:
				if (resolve) return getVariable();
				return basicGetVariable();
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
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__SAMPLE_EXTENSION_ATTRIBUTE:
				setSampleExtensionAttribute((String)newValue);
				return;
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__VARIABLE:
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
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__SAMPLE_EXTENSION_ATTRIBUTE:
				setSampleExtensionAttribute(SAMPLE_EXTENSION_ATTRIBUTE_EDEFAULT);
				return;
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__VARIABLE:
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
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__SAMPLE_EXTENSION_ATTRIBUTE:
				return SAMPLE_EXTENSION_ATTRIBUTE_EDEFAULT == null ? sampleExtensionAttribute != null : !SAMPLE_EXTENSION_ATTRIBUTE_EDEFAULT.equals(sampleExtensionAttribute);
			case ModelPackage.SAMPLE_SIMPLE_ACTIVITY__VARIABLE:
				return variable != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (SampleExtensionAttribute: ");
		result.append(sampleExtensionAttribute);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void updateElementReferences(EObject object, String attrName, String attrValue) {
		// Some attribute of the given EObject has changed.
		// If we keep a reference to it, we need to update
		// our XML fragment to reflect the change.
		if (object!=null && getVariable() == object) {
			// has the variable name changed?
			if (BPELConstants.AT_NAME.equals(attrName)) {
				// update our "variable" attribute with the name change
				ReconciliationHelper.replaceAttribute(this, "variable", attrValue);
			}
		}
	}
	
} // SampleSimpleActivityImpl
