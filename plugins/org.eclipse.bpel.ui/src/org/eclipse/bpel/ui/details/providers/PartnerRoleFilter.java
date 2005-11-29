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

import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


/**
 * Filter which excludes from a set of PartnerLinks those which are missing one or both roles.
 * 
 * For situations when one or the other role (or both) are required. 
 */
public class PartnerRoleFilter extends ViewerFilter {

	private boolean requireMyRole, requirePartnerRole;
	
	public PartnerRoleFilter(boolean requireMyRole, boolean requirePartnerRole) {
		this.requireMyRole = requireMyRole;
		this.requirePartnerRole = requirePartnerRole;
	}
	
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (requireMyRole || requirePartnerRole) {
			if (element == null)  return false;
			PartnerLink partnerLink = (PartnerLink)element;
			if (requireMyRole && partnerLink.getMyRole() == null) return false;
			if (requirePartnerRole && partnerLink.getPartnerRole() == null) return false;
		}		
		return true;
	}
}
