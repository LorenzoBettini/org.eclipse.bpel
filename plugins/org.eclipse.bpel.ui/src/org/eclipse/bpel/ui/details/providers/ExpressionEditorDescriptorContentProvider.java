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

import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ExpressionEditorDescriptor;


public class ExpressionEditorDescriptorContentProvider extends AbstractContentProvider {

	protected boolean specCompliant;
	
	public ExpressionEditorDescriptorContentProvider() {
		super();
	}
	
	public Object[] getElements(Object inputElement) {
		ExpressionEditorDescriptor[] descriptors = BPELUIRegistry.getInstance().getExpressionEditorDescriptors();
		if (specCompliant) {
			// on spec-compliant mode only return XPATH
			for (int i = 0; i < descriptors.length; i++) {
				ExpressionEditorDescriptor descriptor = descriptors[i];
				if (IBPELUIConstants.EXPRESSION_LANGUAGE_XPATH.equals(descriptor.getExpressionLanguage())) {
					return new ExpressionEditorDescriptor[]{descriptor};
				}
			}
		}
		return descriptors;
	}
	
	public void setSpecCompliant(boolean specCompliant) {
		this.specCompliant = specCompliant;
	}
}
