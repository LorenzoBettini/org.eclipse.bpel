/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.details.providers;

import java.util.Vector;

import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.model.partnerlinktype.RolePortType;
import org.eclipse.bpel.ui.details.tree.PortTypeTreeNode;
import org.eclipse.wst.wsdl.PortType;

/**
 * Provides a tree of model objects representing some expansion of the underlying graph
 * of model objects whose roots are the PortTypes of a Role. 
 */
public class PortTypeTreeContentProvider extends ModelTreeContentProvider {

	public PortTypeTreeContentProvider(boolean isCondensed) {
		super(isCondensed);
	}

	public Object[] primGetElements(Object inputElement) {
		Vector v = new Vector();
		if (inputElement instanceof Role) {
			RolePortType rpt = ((Role)inputElement).getPortType();
			v.add(new PortTypeTreeNode((PortType)rpt.getName(), isCondensed));
		}
		return v.toArray();
	}
}
