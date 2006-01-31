/**
 * <copyright>
 * </copyright>
 *
 * $Id: ForEach.java,v 1.3 2006/01/31 14:56:08 james Exp $
 */
package org.eclipse.bpel.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>For Each</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.ForEach#getParallel <em>Parallel</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.ForEach#getCounterName <em>Counter Name</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.ForEach#getCompletionCondition <em>Completion Condition</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.ForEach#getIterator <em>Iterator</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.ForEach#getActivity <em>Activity</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getForEach()
 * @model
 * @generated
 */
public interface ForEach extends Activity{
	/**
	 * Returns the value of the '<em><b>Parallel</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parallel</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parallel</em>' attribute.
	 * @see #setParallel(Boolean)
	 * @see org.eclipse.bpel.model.BPELPackage#getForEach_Parallel()
	 * @model default="false"
	 * @generated
	 */
	Boolean getParallel();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.ForEach#getParallel <em>Parallel</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parallel</em>' attribute.
	 * @see #getParallel()
	 * @generated
	 */
	void setParallel(Boolean value);

	/**
	 * Returns the value of the '<em><b>Counter Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Counter Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Counter Name</em>' containment reference.
	 * @see #setCounterName(Variable)
	 * @see org.eclipse.bpel.model.BPELPackage#getForEach_CounterName()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variable getCounterName();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.ForEach#getCounterName <em>Counter Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Counter Name</em>' containment reference.
	 * @see #getCounterName()
	 * @generated
	 */
	void setCounterName(Variable value);

	/**
	 * Returns the value of the '<em><b>Completion Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Completion Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Completion Condition</em>' containment reference.
	 * @see #setCompletionCondition(CompletionCondition)
	 * @see org.eclipse.bpel.model.BPELPackage#getForEach_CompletionCondition()
	 * @model containment="true"
	 * @generated
	 */
	CompletionCondition getCompletionCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.ForEach#getCompletionCondition <em>Completion Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Completion Condition</em>' containment reference.
	 * @see #getCompletionCondition()
	 * @generated
	 */
	void setCompletionCondition(CompletionCondition value);

	/**
	 * Returns the value of the '<em><b>Iterator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iterator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iterator</em>' containment reference.
	 * @see #setIterator(Iterator)
	 * @see org.eclipse.bpel.model.BPELPackage#getForEach_Iterator()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Iterator getIterator();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.ForEach#getIterator <em>Iterator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iterator</em>' containment reference.
	 * @see #getIterator()
	 * @generated
	 */
	void setIterator(Iterator value);

	/**
	 * Returns the value of the '<em><b>Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activity</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activity</em>' containment reference.
	 * @see #setActivity(Activity)
	 * @see org.eclipse.bpel.model.BPELPackage#getForEach_Activity()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Activity getActivity();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.ForEach#getActivity <em>Activity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activity</em>' containment reference.
	 * @see #getActivity()
	 * @generated
	 */
	void setActivity(Activity value);

} // ForEach
