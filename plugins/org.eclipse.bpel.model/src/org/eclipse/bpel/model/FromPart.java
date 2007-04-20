/**
 * <copyright>
 * </copyright>
 *
 * $Id: FromPart.java,v 1.3 2007/04/20 23:31:44 mchmielewski Exp $
 */
package org.eclipse.bpel.model;

import org.eclipse.wst.wsdl.WSDLElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>From Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpel.model.FromPart#getPart <em>Part</em>}</li>
 *   <li>{@link org.eclipse.bpel.model.FromPart#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpel.model.BPELPackage#getFromPart()
 * @model
 * @generated
 */
public interface FromPart extends ExtensibleElement {
	/**
	 * Returns the value of the '<em><b>Part</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Part</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Part</em>' attribute.
	 * @see #setPart(String)
	 * @see org.eclipse.bpel.model.BPELPackage#getFromPart_Part()
	 * @model
	 * @generated
	 */
	String getPart();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.FromPart#getPart <em>Part</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Part</em>' attribute.
	 * @see #getPart()
	 * @generated
	 */
	void setPart(String value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(To)
	 * @see org.eclipse.bpel.model.BPELPackage#getFromPart_To()
	 * @model
	 * @generated
	 */
	To getTo();

	/**
	 * Sets the value of the '{@link org.eclipse.bpel.model.FromPart#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(To value);

} // FromPart
