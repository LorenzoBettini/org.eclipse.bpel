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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Operation;

/**
 * Tree node to represent an Operation model object.
 */
public class OperationTreeNode extends TreeNode {

	public OperationTreeNode(Operation operation, boolean isCondensed) {
		super(operation, isCondensed);
	}

	/* ITreeNode */

	public Object[] getChildren() {
		Operation op = (Operation)modelObject;
		List list = new ArrayList();
		if (op.getInput() != null && op.getInput().getMessage() != null) {
			list.add(new MessageTypeTreeNode((Message)op.getInput().getMessage(),
				isCondensed, false));
		}
		if (op.getOutput() != null && op.getOutput().getMessage() != null) {
			list.add(new MessageTypeTreeNode((Message)op.getOutput().getMessage(),
				isCondensed, false));
		}
		return list.toArray();
	}

	public boolean hasChildren() {
		Operation op = (Operation)modelObject;
		if (op.getInput() != null && op.getInput().getMessage() != null) return true;
		if (op.getOutput() != null && op.getOutput().getMessage() != null) return true;
		return false;
	}
}
