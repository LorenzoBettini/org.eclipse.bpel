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
package org.eclipse.bpel.ui.adapters;

import org.eclipse.bpel.model.messageproperties.util.MessagepropertiesAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;


public class BPELUIMessagePropertiesAdapterFactory extends MessagepropertiesAdapterFactory {
	
	static BPELUIMessagePropertiesAdapterFactory instance;
	
	PropertyAdapter propertyAdapter;
	
	public static BPELUIMessagePropertiesAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIMessagePropertiesAdapterFactory();
		}
		return instance;
	}

	public Adapter createPropertyAdapter() {
		if (propertyAdapter == null) {
			propertyAdapter = new PropertyAdapter();	
		}		
		return propertyAdapter;
	}
	
	public Adapter adaptNew(Notifier target, Object type) {
		Adapter adapter = createAdapter(target, type);
		if (adapter != null && adapter.isAdapterForType(type)) {
			associate(adapter, target);
			return adapter;
		}
		return null;
	}
	protected Object resolve(Object object, Object type) {
		return null;
	}

}
