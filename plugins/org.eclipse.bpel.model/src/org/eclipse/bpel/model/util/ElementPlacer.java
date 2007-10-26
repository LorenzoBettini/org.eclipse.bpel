/*******************************************************************************
 * Copyright (c) 2007 Intel Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Dennis Ushakov, Intel - Initial API and Implementation
 *
 *******************************************************************************/

package org.eclipse.bpel.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ElementPlacer {
	private static HashMap<String, List<String>> mapper = new HashMap<String, List<String>>();
	private static final String ACTIVITY = "activity";
	
	static {		
		// TODO: (DU) add other activities
		// Process
		String processElements[] = { BPELConstants.ND_EXTENSIONS,
                					 BPELConstants.ND_IMPORT,
                					 BPELConstants.ND_PARTNER_LINKS,
                					 BPELConstants.ND_MESSAGE_EXCHANGES,
                					 BPELConstants.ND_VARIABLES,
                					 BPELConstants.ND_CORRELATION_SETS,
                					 BPELConstants.ND_FAULT_HANDLERS,
                					 BPELConstants.ND_EVENT_HANDLERS,
                					 ACTIVITY };
		mapper.put(BPELConstants.ND_PROCESS, Arrays.asList(processElements));
		// FaultHandlers
		String faultHandlersElements[] = { BPELConstants.ND_CATCH,
				                           BPELConstants.ND_CATCH_ALL };
		mapper.put(BPELConstants.ND_FAULT_HANDLERS, Arrays.asList(faultHandlersElements));
		// EventHandlers
		String eventHandlersElements[] = { BPELConstants.ND_ON_EVENT,
                						   BPELConstants.ND_ON_ALARM };
		mapper.put(BPELConstants.ND_EVENT_HANDLERS, Arrays.asList(eventHandlersElements));
		// Invoke
		String invokeElements[] = { BPELConstants.ND_CORRELATIONS,
									BPELConstants.ND_CATCH,
                					BPELConstants.ND_CATCH_ALL,
                					BPELConstants.ND_COMPENSATION_HANDLER,
                					BPELConstants.ND_TO_PARTS,
                					BPELConstants.ND_FROM_PARTS };
		mapper.put(BPELConstants.ND_INVOKE, Arrays.asList(invokeElements));
		// While
		String whileElements[] = { BPELConstants.ND_CONDITION,
                				   ACTIVITY };
		mapper.put(BPELConstants.ND_WHILE, Arrays.asList(whileElements));
		// RepeatUntil
		String repeatElements[] = { ACTIVITY,
									BPELConstants.ND_CONDITION };
		mapper.put(BPELConstants.ND_REPEAT_UNTIL, Arrays.asList(repeatElements));
		// If
		String ifElements[] = { BPELConstants.ND_CONDITION,
								ACTIVITY,
								BPELConstants.ND_ELSEIF,
								BPELConstants.ND_ELSE };
		mapper.put(BPELConstants.ND_IF, Arrays.asList(ifElements));
		// ElseIf
		String elseIfElements[] = { BPELConstants.ND_CONDITION,
									ACTIVITY };
		mapper.put(BPELConstants.ND_ELSEIF, Arrays.asList(elseIfElements));
	}
	
	public static void placeChild(Element parent, Node child) {
		List<String> nodeTypeList = mapper.get(parent.getLocalName());
		if (nodeTypeList != null) {
			String nodeName = child.getLocalName();
			String nodeType = findType(nodeName, nodeTypeList);			
			if (nodeType != null) {
				Node beforeElement = parent.getFirstChild();
				while (beforeElement != null && !isPreviousType(nodeType, findType(beforeElement.getLocalName(), nodeTypeList), nodeTypeList)) {
					beforeElement = beforeElement.getNextSibling();
				}
				while (beforeElement != null && isType(beforeElement.getLocalName(), nodeType)) {
					beforeElement = beforeElement.getNextSibling();
				}
				parent.insertBefore(child, beforeElement);
				return;
			}
		}
		parent.appendChild(child);
	}
	
	private static String findType(String nodeName, List<String> nodeTypeList) {
		for (String nodeType : nodeTypeList) {
			if (isType(nodeName, nodeType)) {
//				System.err.println("name: " + nodeName + ", found type: " + nodeType);
				return nodeType;
			}
		}
		return null;
	}
	
	private static boolean isPreviousType(String typeName1, String typeName2, List<String> nodeTypeList) {
		int type1Index = nodeTypeList.indexOf(typeName1);
		int type2Index = nodeTypeList.indexOf(typeName2);
		return type1Index < type2Index || (type2Index < 0 && type1Index >=0);
	}
	
	private static boolean isType(String nodeName, String typeName) {
		return ACTIVITY.equals(typeName) ? isActivity(nodeName) : typeName.equals(nodeName);
	}
	
	private static boolean isActivity(String nodeName) {
		return BPELConstants.ND_ASSIGN.equals(nodeName) ||
        BPELConstants.ND_COMPENSATE.equals(nodeName) ||
        BPELConstants.ND_COMPENSATE_SCOPE.equals(nodeName) ||
        BPELConstants.ND_EMPTY.equals(nodeName) ||
        BPELConstants.ND_EXIT.equals(nodeName) ||
        BPELConstants.ND_EXTENSION_ACTIVITY.equals(nodeName) ||
        BPELConstants.ND_FLOW.equals(nodeName) ||
        BPELConstants.ND_FOR_EACH.equals(nodeName) ||
        BPELConstants.ND_IF.equals(nodeName) ||
        BPELConstants.ND_INVOKE.equals(nodeName) ||
        BPELConstants.ND_PICK.equals(nodeName) ||
        BPELConstants.ND_RECEIVE.equals(nodeName) ||
        BPELConstants.ND_REPEAT_UNTIL.equals(nodeName) ||
        BPELConstants.ND_REPLY.equals(nodeName) ||
        BPELConstants.ND_RETHROW.equals(nodeName) ||
        BPELConstants.ND_SCOPE.equals(nodeName) ||
        BPELConstants.ND_SEQUENCE.equals(nodeName) ||
        BPELConstants.ND_THROW.equals(nodeName) ||
        BPELConstants.ND_VALIDATE.equals(nodeName) ||
        BPELConstants.ND_WAIT.equals(nodeName) ||
        BPELConstants.ND_WHILE.equals(nodeName);
	}
}
