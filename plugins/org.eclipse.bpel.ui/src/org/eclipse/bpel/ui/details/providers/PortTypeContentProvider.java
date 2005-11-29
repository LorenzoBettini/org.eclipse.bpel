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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.PortType;

/**
 * Content provider for PortTypes.
 * 
 * Expects a Role or a Definition or a context as input.
 */
public class PortTypeContentProvider extends AbstractContentProvider  {

	public Object[] getElements(Object input)  {
		if (input instanceof Definition) {
			return ((Definition)input).getEPortTypes().toArray();
		}			
		if (input instanceof Role) {
			PortType portType = ModelHelper.getRolePortType((Role)input);
			if (portType == null)  return EMPTY_ARRAY;
			return new Object[] { portType };
		}

		// everything else is expected to be a Process or something inside a Process.
		
		PartnerLink[] partnerLinks = BPELUtil.getVisiblePartnerLinks((EObject)input);
		List portTypes = new ArrayList();
		Set portTypeSet = new HashSet();
		for (int i = 0; i<partnerLinks.length; i++) {
			PortType pt = ModelHelper.getPartnerPortType(partnerLinks[i], ModelHelper.INCOMING);
			if (pt != null) {
				if (portTypeSet.add(pt)) portTypes.add(pt);
			}
			pt = ModelHelper.getPartnerPortType(partnerLinks[i], ModelHelper.OUTGOING);
			if (pt != null) {
				if (portTypeSet.add(pt)) portTypes.add(pt);
			}
		}
		if (portTypes.isEmpty()) return EMPTY_ARRAY;
		return portTypes.toArray();
	}
}
