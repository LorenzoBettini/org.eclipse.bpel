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

import org.eclipse.bpel.model.Process;

/**
 * Content provider for impots.
 * 
 * Expects a Process as input.
 */
public class ImportContentProvider extends AbstractContentProvider  {

	public ImportContentProvider() {}
	
	public Object[] getElements(Object input)  {
		if (input instanceof Process) {
			return ((Process)input).getImports().toArray();
		}
		return EMPTY_ARRAY;
	}
}
