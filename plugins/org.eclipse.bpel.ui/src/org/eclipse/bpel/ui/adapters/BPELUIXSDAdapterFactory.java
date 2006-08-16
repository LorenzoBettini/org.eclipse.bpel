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
import org.eclipse.xsd.util.XSDAdapterFactory;


public class BPELUIXSDAdapterFactory extends XSDAdapterFactory {
	
	static private BPELUIXSDAdapterFactory instance;	
	
	AdapterProvider provider;
	
	/**
	 * The AdapterFactory constructor. Private, because there is only
	 * 1 instance of the factory obtainable via getInstance() method below. 
	 */
	
	private BPELUIXSDAdapterFactory () {
		provider = new AdapterProvider();
	}
	
	public static BPELUIXSDAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIXSDAdapterFactory();
		}
		return instance;
	}
	
	public Adapter createXSDSimpleTypeDefinitionAdatper () {
		return provider.getAdapter( XSDSimpleTypeDefinitionAdapter.class );		
	}
	
	public Adapter createXSDComplexTypeDefinitionAdapter () {
		return provider.getAdapter( XSDComplexTypeDefinitionAdapter.class );		
	}
	
	public Adapter createXSDTypeDefinitionAdapter() {
		return provider.getAdapter( XSDTypeDefinitionAdapter.class );		
	}
	
	public Adapter createXSDAttributeDeclarationAdapter() {
		return provider.getAdapter( XSDAttributeDeclarationAdapter.class );		
	}
	
	public Adapter createXSDElementDeclarationAdapter() {
		return provider.getAdapter( XSDElementDeclarationAdapter.class );		
	}
		
	public Adapter createXSDSchemaAdapter() {
		return provider.getAdapter( XSDSchemaAdapter.class );		
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
