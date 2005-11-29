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
package org.eclipse.bpel.ui.details.tree;

import org.eclipse.bpel.model.Variable;


/**
 * Tree node to represent a Variable model object.  For !isCondensed, a Variable node has
 * one child which describes its Message type.  For isCondensed trees, the Message type node
 * is elided and its label is displayed as a suffix of the Variable node's label.
 */
public class BPELVariableTreeNode extends TreeNode {

	MessageTypeTreeNode messageNode = null;
	XSDElementDeclarationTreeNode elementNode = null;
	XSDTypeDefinitionTreeNode typeNode = null;
	boolean isPropertyTree;
	boolean displayParticles;
	
	public BPELVariableTreeNode(Variable variable, boolean isCondensed, boolean isPropertyTree, boolean displayParticles) {
		super(variable, isCondensed);
		this.displayParticles = displayParticles;
		this.isPropertyTree = isPropertyTree;
		if (isCondensed) {
			if (variable.getMessageType() != null)  {
				messageNode = new MessageTypeTreeNode(variable.getMessageType(),
					isCondensed, isPropertyTree);
			} else if (variable.getType() != null) {
				typeNode = new XSDTypeDefinitionTreeNode(variable.getType(), isCondensed);
			} else if (variable.getXSDElement() != null) {
				elementNode = new XSDElementDeclarationTreeNode(variable.getXSDElement(), isCondensed);			
			}
		}
	}

	/* ITreeNode */

	public String getLabelSuffix() {
		if (isCondensed) {
			if (messageNode != null) return messageNode.getLabel();
			if (typeNode != null) return typeNode.getLabel();
			if (elementNode != null) return elementNode.getLabel();
		}
		return null;
	}

	public Object[] getChildren() {
		if (isCondensed) {
			if (messageNode != null) return messageNode.getChildren();
			if (typeNode != null) return typeNode.getChildren();
			if (elementNode != null) return elementNode.getChildren();
			return EMPTY_ARRAY;
		}
		Variable variable = (Variable)modelObject;
		if (variable.getMessageType() != null) {
			Object[] result = new Object[1];
			result[0] = new MessageTypeTreeNode(variable.getMessageType(),
				isCondensed, isPropertyTree, displayParticles);
			return result;
		}
		if (variable.getType() != null) {
			Object[] result = new Object[1];
			result[0] = new XSDTypeDefinitionTreeNode(variable.getType(), isCondensed);
			return result;
		}
		if (variable.getXSDElement() != null) {
			Object[] result = new Object[1];
			result[0] = new XSDElementDeclarationTreeNode(variable.getXSDElement(), isCondensed);
			return result;
		}
		return EMPTY_ARRAY;
	}

	public boolean hasChildren() {
		if (isCondensed) {
			if (elementNode != null)  return elementNode.hasChildren();
			if (typeNode != null)  return typeNode.hasChildren();
			if (messageNode != null)  return messageNode.hasChildren();
			return false;
		}
		Variable variable = (Variable)modelObject;
		return (variable.getMessageType() != null) ||
			(variable.getType() != null) ||	(variable.getXSDElement() != null);
	}
}
