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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;

/**
 * Tree node to represent a message-type model object.
 */
public class MessageTypeTreeNode extends TreeNode {

	boolean isPropertyTree;
	boolean displayParticles;
	
	public MessageTypeTreeNode(Message messageType, boolean isCondensed, boolean isPropertyTree) {
		this(messageType, isCondensed, isPropertyTree, true);
	}
	public MessageTypeTreeNode(Message messageType, boolean isCondensed,
		boolean isPropertyTree, boolean displayParticles) {
		super(messageType, isCondensed);
		this.isPropertyTree = isPropertyTree;
		this.displayParticles = displayParticles;
	}

	/* ITreeNode */

	public Object[] getChildren() {
		Message msg = (Message)modelObject;
		if (isPropertyTree) {
			// Find propertyAliases that refer to this message.
			List aliases = BPELUtil.getPropertyAliasesForMessageType(msg);
			List properties = getPropertiesFromPropertyAliases(aliases);
			List list = new ArrayList();
			for (Iterator it = properties.iterator(); it.hasNext(); ) {
				list.add(new PropertyTreeNode((Property)it.next(), isCondensed));
			}
			return list.toArray();
		}

		if (msg.getParts() == null)  return EMPTY_ARRAY;
		List list = new ArrayList();
		for (Iterator it = msg.getParts().values().iterator(); it.hasNext(); ) {
			list.add(new PartTreeNode((Part)it.next(), isCondensed, displayParticles));
		}
		return list.toArray();
	}

	public boolean hasChildren() {
		if (isPropertyTree) {
			// TODO: we need some sort of property map maintained by the same
			// builder that will keep track of the namespaceURI's of WSDL files
			return getChildren().length > 0;
		}

		Message msg = (Message)modelObject;
		return (msg.getParts() != null && !msg.getParts().isEmpty());
	}

	/* other methods */
	
	protected List getPropertiesFromPropertyAliases(List aliases) {
		List properties = new ArrayList();
		Set propertySet = new HashSet();
		for (Iterator it = aliases.iterator(); it.hasNext(); ) {
			PropertyAlias alias = (PropertyAlias)it.next();
			Property property = (Property)alias.getPropertyName();
			if (!propertySet.contains(property)) {
				properties.add(property);
				propertySet.add(property);
			}
		}
		return properties;
	}
}
