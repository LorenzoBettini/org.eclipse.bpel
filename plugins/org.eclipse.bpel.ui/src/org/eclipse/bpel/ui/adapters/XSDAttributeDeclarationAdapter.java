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
package org.eclipse.bpel.ui.adapters;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.util.XSDConstants;


public class XSDAttributeDeclarationAdapter extends AbstractAdapter implements
	ILabeledElement
{

	/* ILabeledElement */
	
	public Image getSmallImage(Object object) {
		// TODO: fix this icon
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_PART_16);
	}
	
	public Image getLargeImage(Object object) {
		// TODO: fix this icon
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_PART_32);
	}
	
	public String getTypeLabel(Object object) {
		return Messages.XSDAttributeDeclarationAdapter_XSD_Attribute_1; 
	}
	
	public String getLabel(Object object) {
		XSDAttributeDeclaration attribute = (XSDAttributeDeclaration)BPELUtil.resolveXSDObject(object);
		// hack hack hack!
		if (XSDConstants.SCHEMA_FOR_SCHEMA_URI_2001.equals(attribute.getTargetNamespace())) {
			String name = attribute.getName();
			return "xsd:"+name; //$NON-NLS-1$
		}
		if (attribute.getName() != null)  return attribute.getName();
		return getTypeLabel(object);
	}
}
