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
 * $Id: UnknownExtensibilityAttributeImpl.java,v 1.1 2005/11/29 18:50:25 james Exp $
 */
package org.eclipse.bpel.model.impl;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.UnknownExtensibilityAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.wst.wsdl.internal.impl.UnknownExtensibilityElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unknown Extensibility Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class UnknownExtensibilityAttributeImpl extends UnknownExtensibilityElementImpl implements UnknownExtensibilityAttribute {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UnknownExtensibilityAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return BPELPackage.eINSTANCE.getUnknownExtensibilityAttribute();
	}

} //UnknownExtensibilityAttributeImpl
