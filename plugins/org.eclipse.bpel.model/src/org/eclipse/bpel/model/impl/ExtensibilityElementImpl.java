/*******************************************************************************
 * Copyright (c) 2007 Intel Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dennis Ushakov, Intel - Initial API and Implementation
 *
 *******************************************************************************/
package org.eclipse.bpel.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.model.util.ReconciliationHelper;
import org.eclipse.wst.wsdl.ExtensibilityElement;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.internal.impl.WSDLElementImpl;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ExtensibilityElementImpl extends org.eclipse.wst.wsdl.internal.impl.ExtensibilityElementImpl implements ExtensibilityElement {
	// Reconciliation stuff. Has copy in ExtensibleElement
	// TODO: (DU) remove duplication					
	protected void reconcile(Element changedElement) {
//	    reconcileAttributes(changedElement);
//	    reconcileContents(changedElement);
		ReconciliationHelper.getInstance().reconcile(this, changedElement);
	}
	
	public void elementChanged(Element changedElement) {
		if (!isUpdatingDOM()) {
			if (!isReconciling) {
				isReconciling = true;
				try {
					reconcile(changedElement);

					WSDLElement theContainer = getContainer();
					if (theContainer != null && theContainer.getElement() == changedElement) {
						((WSDLElementImpl)theContainer).elementChanged(changedElement);
					}
				} finally {
					isReconciling = false;
				}
				traverseToRootForPatching();
			} 
	    } 
	}
}
