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

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Common base class for our content providers.
 */
public abstract class AbstractContentProvider implements IStructuredContentProvider {

	protected static final Object[] EMPTY_ARRAY = new Object[0];

	public void dispose()  { }
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)  { }
}
