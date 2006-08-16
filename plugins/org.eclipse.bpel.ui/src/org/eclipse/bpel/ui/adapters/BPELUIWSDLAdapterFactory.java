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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.wst.wsdl.internal.util.WSDLAdapterFactory;

public class BPELUIWSDLAdapterFactory extends WSDLAdapterFactory {
	
	static BPELUIWSDLAdapterFactory instance;
		
	AdapterProvider provider;	
	
	/**
	 * Private constructor to allow only a singleton instances
	 * @param provider
	 */
	
	private BPELUIWSDLAdapterFactory () {	
		this.provider = new AdapterProvider();
	}

	public static BPELUIWSDLAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIWSDLAdapterFactory();
		}
		return instance;
	}
	
	public Adapter createMessageAdapter() {
		return provider.getAdapter( MessageAdapter.class );
	}
	
	public Adapter createFaultAdapter() {
		return provider.getAdapter( FaultAdapter.class );
	}
	
	public Adapter createCompensationAdapter() {
		return provider.getAdapter( CompensateAdapter.class );
	}
	
	public Adapter createOperationAdapter() {
		return provider.getAdapter( OperationAdapter.class );
	}
	
	public Adapter createPartAdapter() {
		return provider.getAdapter( PartAdapter.class );
	}
	
	public Adapter createPortTypeAdapter() {
		return provider.getAdapter( PortTypeAdapter.class );
	}

	
	public Adapter createInputAdapter() {
		return provider.getAdapter(InputMessageAdapter.class);
	}

	public Adapter createOutputAdapter() {
		return provider.getAdapter(OutputMessageAdapter.class);
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
