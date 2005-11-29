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

import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.emf.common.util.EList;

import org.eclipse.wst.wsdl.Definition;

/**
 * Content provider for PartnerLinkTypes.
 * 
 * Expects a Definition as input.
 */
public class PartnerLinkTypeContentProvider extends AbstractContentProvider  {

	public Object[] getElements(Object input)  {
		if (input instanceof Definition) {
			EList extensibilityElementList = ((Definition)input).getEExtensibilityElements();
			Vector v = new Vector();
			for (int i = 0; i < extensibilityElementList.size(); i++) {
				Object maybePlt = extensibilityElementList.get(i);
				if (maybePlt instanceof PartnerLinkType) v.add(maybePlt);
			}
			return v.toArray();
		}
		return EMPTY_ARRAY;
	}
}
