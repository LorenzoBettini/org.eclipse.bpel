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

import org.eclipse.bpel.model.partnerlinktype.util.PartnerlinktypeAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;


public class BPELUIPartnerLinkTypeAdapterFactory extends PartnerlinktypeAdapterFactory {

	static BPELUIPartnerLinkTypeAdapterFactory instance;
		
	AdapterProvider provider;
	
	/**
	 * There is only 1 instance of this class, so the default constructor
	 * is private.
	 *
	 */
	private BPELUIPartnerLinkTypeAdapterFactory () {
		provider = new AdapterProvider ();
	}
	
	public static BPELUIPartnerLinkTypeAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIPartnerLinkTypeAdapterFactory();
		}
		return instance;
	}
	
	
	public Adapter createPartnerLinkTypeAdapter() {
		return provider.getAdapter ( PartnerLinkTypeAdapter.class );
	}
	
	public Adapter createRoleAdapter() {
		return provider.getAdapter( RoleAdapter.class );
	}
	
	
	public Adapter createRolePortTypeAdapter() {
		return provider.getAdapter( PortTypeAdapter.class );		
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
