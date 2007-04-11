/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.validator.adapters;


/**
 * Adapts the variable EMF model to the validator's facade.
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 20, 2006
 *
 */
public class VariableAdapter extends BasicAdapter {

	
	/**
	 * Return the right accessor method for the attribute specified.
	 * 
	 * @param attributeName
	 * @return the accessor method name for the attribute given.
	 */
	
	@Override
	public String getAccessorMethodName ( String attributeName ) {
		
		if (AT_ELEMENT.equals (attributeName)) {
			return "XSDElement"; //$NON-NLS-1$
		} 
		return super.getAccessorMethodName( attributeName );
	}

	
	
}
