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
import java.util.List;

import org.eclipse.wst.wsdl.Definition;

/**
 * Content provider for Message types.
 * 
 * Expects a Definition as input.
 */
public class MessageTypeContentProvider extends AbstractContentProvider  {

	protected void appendElements ( Object input, List list) {
		
		if (input instanceof Definition) {
			list.addAll( ((Definition)input).getEMessages() );
			return;
		}
		
		Object[] arr = null;
		
		if (input.getClass().isArray()) {			
			arr = (Object[]) input;
		} else if (input instanceof List) {
			arr = ((List)input).toArray();
		}
		
		if (arr == null) {
			return;
		}
		
		for(int i=0; i < arr.length; i++) {
			appendElements ( arr[i], list );
		}					
	}
	
	
	public Object[] getElements(Object input)  {
		ArrayList list = new ArrayList();
		appendElements ( input, list );		
		return list.isEmpty()? EMPTY_ARRAY : list.toArray();
	}
	
}
