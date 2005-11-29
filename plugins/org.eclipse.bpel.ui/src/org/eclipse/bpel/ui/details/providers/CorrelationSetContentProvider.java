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
import org.eclipse.bpel.model.Scope;

/**
 * Content provider for CorrelationSets.
 * 
 * Expects a Process or a Scope as input.
 */
public class CorrelationSetContentProvider extends AbstractContentProvider  {

	public Object[] getElements(Object input)  {
		if (input instanceof Process) {
			if (((Process)input).getCorrelationSets() == null) return EMPTY_ARRAY;
			return ((Process)input).getCorrelationSets().getChildren().toArray();
		}
		if (input instanceof Scope) {
			if (((Scope)input).getCorrelationSets() == null) return EMPTY_ARRAY;
			return ((Scope)input).getCorrelationSets().getChildren().toArray();
		}
		
		// TODO: fix this to return all visible correlation sets?
		// see BPELUtil.getVisiblePartnerLinks() etc.
		
		return EMPTY_ARRAY;
	}
}
