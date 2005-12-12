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
package org.eclipse.bpel.ui.details.tree;

import java.util.Vector;

import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.wst.wsdl.PortType;

/**
 * Tree node to represent a PartnerLink model object.
 */
public class PartnerLinkTreeNode extends TreeNode {

	public PartnerLinkTreeNode(PartnerLink partnerLink, boolean isCondensed) {
		super(partnerLink, isCondensed);
	}

	/* ITreeNode */

	public Object[] getChildren() {
		PartnerLink partnerLink = (PartnerLink)modelObject;
		if (partnerLink == null) return EMPTY_ARRAY;
		Vector v = new Vector();
		if (partnerLink != null) { 
			Role role = partnerLink.getMyRole();
			if (role != null) v.add(new PortTypeTreeNode((PortType)role.getPortType(), isCondensed));
			role = partnerLink.getPartnerRole();
			if (role != null) v.add(new PortTypeTreeNode((PortType)role.getPortType(), isCondensed));
		}
		return v.toArray();
	}

	public boolean hasChildren() {
		PartnerLink partnerLink = (PartnerLink)modelObject;
		if (partnerLink == null)  return false;
		Role role = partnerLink.getMyRole();
		if (role != null && role.getPortType() != null)  return true;
		role = partnerLink.getPartnerRole();
		if (role != null && role.getPortType() != null)  return true;
		return false;
	}
}
