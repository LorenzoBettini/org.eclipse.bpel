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
	
	MessageAdapter messageAdapter;
	FaultAdapter faultAdapter;
	CompensateAdapter compensationAdapter;
	OperationAdapter operationAdapter;
	PartAdapter partAdapter;
	PortTypeAdapter portTypeAdapter;
	
	public static BPELUIWSDLAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIWSDLAdapterFactory();
		}
		return instance;
	}
	
	public Adapter createMessageAdapter() {
		if (messageAdapter == null) {
			messageAdapter = new MessageAdapter();	
		}		
		return messageAdapter;
	}
	
	public Adapter createFaultAdapter() {
		if (faultAdapter == null) {
			faultAdapter = new FaultAdapter();	
		}		
		return faultAdapter;
	}
	
	public Adapter createCompensationAdapter() {
		if (compensationAdapter == null) {
			compensationAdapter = new CompensateAdapter();	
		}		
		return compensationAdapter;
	}
	
	public Adapter createOperationAdapter() {
		if (operationAdapter == null) {
			operationAdapter = new OperationAdapter();	
		}		
		return operationAdapter;
	}
	
	public Adapter createPartAdapter() {
		if (partAdapter == null) {
			partAdapter = new PartAdapter();	
		}		
		return partAdapter;
	}
	
	public Adapter createPortTypeAdapter() {
		if (portTypeAdapter == null) {
			portTypeAdapter = new PortTypeAdapter();	
		}		
		return portTypeAdapter;
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
