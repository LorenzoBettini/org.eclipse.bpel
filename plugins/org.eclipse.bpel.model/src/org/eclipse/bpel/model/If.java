/**
 * <copyright>
 * </copyright>
 *
 * $Id: If.java,v 1.1 2005/12/09 21:01:02 james Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>If</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.If#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.If#getThen <em>Then</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.If#getElseIf <em>Else If</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.If#getElse <em>Else</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getIf()
 * @model
 * @generated
 */
public interface If extends Activity {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Condition)
	 * @see org.eclipse.bpel.model.BPELPackage#getIf_Condition()
	 * @model containment="true"
	 * @generated
	 */
	Condition getCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.If#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Condition value);

	/**
	 * Returns the value of the '<em><b>Then</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Then</em>' containment reference.
	 * @see #setThen(Then)
	 * @see org.eclipse.bpel.model.BPELPackage#getIf_Then()
	 * @model containment="true"
	 * @generated
	 */
	Then getThen();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.If#getThen <em>Then</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Then</em>' containment reference.
	 * @see #getThen()
	 * @generated
	 */
	void setThen(Then value);

	/**
	 * Returns the value of the '<em><b>Else If</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpel.model.ElseIf}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else If</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Else If</em>' containment reference list.
	 * @see org.eclipse.bpel.model.BPELPackage#getIf_ElseIf()
	 * @model type="org.eclipse.bpel.model.ElseIf" containment="true"
	 * @generated
	 */
	EList getElseIf();

	/**
	 * Returns the value of the '<em><b>Else</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Else</em>' containment reference.
	 * @see #setElse(Else)
	 * @see org.eclipse.bpel.model.BPELPackage#getIf_Else()
	 * @model containment="true"
	 * @generated
	 */
	Else getElse();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.If#getElse <em>Else</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Else</em>' containment reference.
	 * @see #getElse()
	 * @generated
	 */
	void setElse(Else value);

} // If
