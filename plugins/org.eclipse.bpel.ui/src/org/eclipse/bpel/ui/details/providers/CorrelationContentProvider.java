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

import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.ui.util.ModelHelper;


public class CorrelationContentProvider extends AbstractContentProvider {

	public Object[] getElements(Object inputElement)  {
		try {
			Correlations c = ModelHelper.getCorrelations(inputElement);
			return (c == null)? EMPTY_ARRAY : c.getChildren().toArray();
		} catch (IllegalArgumentException e) {
			return EMPTY_ARRAY;
		}
	}
}
