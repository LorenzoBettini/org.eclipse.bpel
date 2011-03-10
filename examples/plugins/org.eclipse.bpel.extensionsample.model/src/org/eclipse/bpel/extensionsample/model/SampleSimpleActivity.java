/**
 * <copyright>
 * </copyright>
 *
 * $Id: SampleSimpleActivity.java,v 1.2 2011/03/10 18:18:18 rbrodt Exp $
 */
package org.eclipse.bpel.extensionsample.model;

import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.Variable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sample Simple Activity</b></em>'.
 * 
 * Bug 120110 - the model has been updated to include a Variable
 * reference for the SampleSimpleActivity and a Variable definition
 * for the SampleStructuredActivity.
 * 
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.extensionsample.model.SampleSimpleActivity#getSampleExtensionAttribute <em>Sample Extension Attribute</em>}</li>
 *   <li>{@link org.eclipse.bpel.extensionsample.model.SampleSimpleActivity#getVariable <em>Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.extensionsample.model.ModelPackage#getSampleSimpleActivity()
 * @model
 * @generated
 */
public interface SampleSimpleActivity extends ExtensionActivity {

	/**
	 * Returns the value of the '<em><b>Sample Extension Attribute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sample Extension Attribute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sample Extension Attribute</em>' attribute.
	 * @see #setSampleExtensionAttribute(String)
	 * @see org.eclipse.bpel.extensionsample.model.ModelPackage#getSampleSimpleActivity_SampleExtensionAttribute()
	 * @model
	 * @generated
	 */
	String getSampleExtensionAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.extensionsample.model.SampleSimpleActivity#getSampleExtensionAttribute <em>Sample Extension Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sample Extension Attribute</em>' attribute.
	 * @see #getSampleExtensionAttribute()
	 * @generated
	 */
	void setSampleExtensionAttribute(String value);

	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(Variable)
	 * @see org.eclipse.bpel.extensionsample.model.ModelPackage#getSampleSimpleActivity_Variable()
	 * @model
	 * @generated
	 */
	Variable getVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.extensionsample.model.SampleSimpleActivity#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(Variable value);
} // SampleSimpleActivity
