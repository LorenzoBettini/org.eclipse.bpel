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
	
	public static BPELUIXSDAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIXSDAdapterFactory();
		}
		return instance;
	}
	
	public Adapter createXSDSimpleTypeDefinitionAdatper () {
		return new XSDSimpleTypeDefinitionAdapter();
	}
	
	public Adapter createXSDComplexTypeDefinitionAdapter () {
		return new XSDComplexTypeDefinitionAdapter();
	}
	
	public Adapter createXSDTypeDefinitionAdapter() {
		return new XSDTypeDefinitionAdapter();
	}
	
	public Adapter createXSDAttributeDeclarationAdapter() {
		return new XSDAttributeDeclarationAdapter();
	}
	
	public Adapter createXSDElementDeclarationAdapter() {
		return new XSDElementDeclarationAdapter();
	}
	
	

	public Adapter createXSDSchemaAdapter() {
		return new XSDSchemaAdapter();
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
