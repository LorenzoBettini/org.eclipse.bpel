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

import org.eclipse.wst.wsdl.Message;

/**
 * Content provider for Parts.
 * 
 * Expects a Message type as input.
 */
public class PartContentProvider extends AbstractContentProvider {

	public Object[] getElements(Object input) {
		if (input instanceof Message) {
			return ((Message)input).getEParts().toArray();
		}
		return EMPTY_ARRAY;
	}
}
