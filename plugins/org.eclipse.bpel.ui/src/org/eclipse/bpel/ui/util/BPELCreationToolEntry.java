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
package org.eclipse.bpel.ui.util;

import org.eclipse.bpel.ui.factories.AbstractUIObjectFactory;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;


public class BPELCreationToolEntry extends CreationToolEntry {

	public BPELCreationToolEntry(String label, String shortDesc, AbstractUIObjectFactory factory) {
		super(label, shortDesc, factory, factory.getSmallImageDescriptor(), factory.getLargeImageDescriptor());
		setType("not-a-builtin-type"); // hack! //$NON-NLS-1$
	}

	public Tool createTool() {
		CreationFactory factory = (CreationFactory)getToolProperty(CreationTool.PROPERTY_CREATION_FACTORY);
		CreationTool tool = new BPELCreationTool(factory);
//		tool.setUnloadWhenFinished(false);
		return tool;
	}

	public AbstractUIObjectFactory getUIObjectFactory() {
		CreationFactory factory = (CreationFactory)getToolProperty(CreationTool.PROPERTY_CREATION_FACTORY);
		return (AbstractUIObjectFactory)factory;
	}
}