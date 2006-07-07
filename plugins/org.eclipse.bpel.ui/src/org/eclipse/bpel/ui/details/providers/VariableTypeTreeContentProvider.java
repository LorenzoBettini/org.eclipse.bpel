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

import org.eclipse.bpel.ui.details.tree.MessageTypeTreeNode;
import org.eclipse.bpel.ui.details.tree.XSDElementDeclarationTreeNode;
import org.eclipse.bpel.ui.details.tree.XSDSchemaTreeNode;
import org.eclipse.bpel.ui.details.tree.XSDTypeDefinitionTreeNode;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDTypeDefinition;


/**
 * Provides a tree of model objects representing some expansion of the underlying graph
 * of model objects whose roots are the Variables of a Process. 
 */
public class VariableTypeTreeContentProvider extends ModelTreeContentProvider {

	boolean isPropertyTree;
	boolean displayParticles;
	
	public VariableTypeTreeContentProvider(boolean isCondensed, boolean displayParticles) {
		super(isCondensed);
		this.displayParticles = displayParticles;
	}

	public boolean isPropertyTree() { return isPropertyTree; }

	public Object[] primGetElements (Object inputElement) {
		
		Object [] list = new Object[1];
		
		if (inputElement instanceof XSDTypeDefinition) {
			XSDTypeDefinition typeDef = (XSDTypeDefinition) inputElement;
			list[0] =  new XSDTypeDefinitionTreeNode(typeDef, isCondensed);
		} else if (inputElement instanceof XSDElementDeclaration) {
			XSDElementDeclaration decl = (XSDElementDeclaration) inputElement;
			list[0] = new XSDElementDeclarationTreeNode(decl, isCondensed); 
		} else if (inputElement instanceof Message) {
			Message msg = (Message) inputElement;
			list[0] = new MessageTypeTreeNode(msg,isCondensed, false);
		} else if (inputElement instanceof XSDSchema) {
			XSDSchema schema = (XSDSchema) inputElement;
			list[0] = new XSDSchemaTreeNode(schema,isCondensed);
		}
				
		return list; 
	}	
}
