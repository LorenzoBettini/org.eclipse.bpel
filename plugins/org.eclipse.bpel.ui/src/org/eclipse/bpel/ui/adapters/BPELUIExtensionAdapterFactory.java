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

import org.eclipse.bpel.ui.uiextensionmodel.util.UiextensionmodelAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;


public class BPELUIExtensionAdapterFactory extends UiextensionmodelAdapterFactory {
	
	StartNodeAdapter startNodeAdapter;
	EndNodeAdapter endNodeAdapter;
	ReferencePartnerLinksAdapter referencePartnerLinkAdapter;
	static BPELUIExtensionAdapterFactory instance = null;
	AdapterProvider provider = new AdapterProvider();
	
	private BPELUIExtensionAdapterFactory () {
		provider = new AdapterProvider();
	}
	
	public static BPELUIExtensionAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIExtensionAdapterFactory();
		}
		return instance;
	}
	
	public Adapter createStartNodeAdapter() {
		return provider.getAdapter( StartNodeAdapter.class);
	}
	
	public Adapter createEndNodeAdapter() {
		return provider.getAdapter( EndNodeAdapter.class);
	}
	
	public Adapter createReferencePartnerLinksAdapter() {
		return provider.getAdapter( ReferencePartnerLinksAdapter.class );
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
