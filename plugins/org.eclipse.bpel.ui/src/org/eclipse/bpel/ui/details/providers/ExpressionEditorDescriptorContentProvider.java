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

import java.util.List;

import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ExpressionEditorDescriptor;


public class ExpressionEditorDescriptorContentProvider extends AbstractContentProvider {

	protected boolean specCompliant;
	
	public ExpressionEditorDescriptorContentProvider() {
		super();
	}
	
	public void collectElements (Object input, List list) {
		ExpressionEditorDescriptor[] descriptors = BPELUIRegistry.getInstance().getExpressionEditorDescriptors();
		
		if (specCompliant) {
			// on spec-compliant mode only return XPATH
			for (int i = 0; i < descriptors.length; i++) {
				ExpressionEditorDescriptor descriptor = descriptors[i];
				if (IBPELUIConstants.EXPRESSION_LANGUAGE_XPATH.equals(descriptor.getExpressionLanguage())) {
					list.add(descriptor);
				}
			}
			
			return ;
		}
		
		
		for(int i=0; i<descriptors.length; i++) {
			list.add(descriptors[i]);
		}
	}
	
	public void setSpecCompliant(boolean specCompliant) {
		this.specCompliant = specCompliant;
	}
}
