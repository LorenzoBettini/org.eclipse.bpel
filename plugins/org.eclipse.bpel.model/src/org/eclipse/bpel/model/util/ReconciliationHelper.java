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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Branches;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.CatchAll;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.CompletionCondition;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.Documentation;
import org.eclipse.bpel.model.Else;
import org.eclipse.bpel.model.ElseIf;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.Extension;
import org.eclipse.bpel.model.Extensions;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.FromPart;
import org.eclipse.bpel.model.FromParts;
import org.eclipse.bpel.model.If;
import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Links;
import org.eclipse.bpel.model.MessageExchange;
import org.eclipse.bpel.model.MessageExchanges;
import org.eclipse.bpel.model.OnAlarm;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Pick;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.RepeatUntil;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.ServiceRef;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.TerminationHandler;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.ToPart;
import org.eclipse.bpel.model.ToParts;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.model.While;
import org.eclipse.bpel.model.impl.ServiceRefImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.WSDLElement;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ReconciliationHelper {
	private static ReconciliationHelper helper;
	private HashMap<Document, ReconciliationBPELReader> document2reader = new HashMap<Document, ReconciliationBPELReader>();
	
	public static ReconciliationHelper getInstance() {
		if (helper == null) {
			helper = new ReconciliationHelper();
		}
		return helper;
	}
	
	public void reconcile(WSDLElement element, Element changedElement) {
//		if (isLoading(element)) {
//			System.err.println("**LOADING***");
//			(new Exception()).printStackTrace();
//			return;
//		}
//		System.err.println("**RECONCILE***");
//		System.err.println(element);
//		(new Exception()).printStackTrace();
		ReconciliationBPELReader reader = getReader(element, changedElement);
		if (element instanceof Activity) {
			reader.xml2Activity((Activity)element, changedElement);
		} else if (element instanceof Process) {
			reader.xml2Process(changedElement);
		} else if (element instanceof Import) {
			reader.xml2Import((Import)element, changedElement);
		} else if (element instanceof Condition) {
			reader.xml2Condition((Condition)element, changedElement);
		} else if (element instanceof CompletionCondition) {
			reader.xml2CompletionCondition((CompletionCondition)element, changedElement);
		} else if (element instanceof Branches) {
			reader.xml2Branches((Branches)element, changedElement);
		} else if (element instanceof Expression) {
			reader.xml2Expression((Expression)element, changedElement);
		} else if (element instanceof Documentation) {
			reader.xml2Documentation((Documentation)element, changedElement);
		} else if (element instanceof Link) {
			reader.xml2Link((Link)element, changedElement);
		} else if (element instanceof Links) {
			reader.xml2Links((Links)element, changedElement);
		} else if (element instanceof ElseIf) {
			reader.xml2ElseIf((ElseIf)element, changedElement);
		} else if (element instanceof Else) {
			reader.xml2Else((Else)element, changedElement);
		} else if (element instanceof Variable) {
			reader.xml2Variable((Variable)element, changedElement);
		} else if (element instanceof Variables) {
			reader.xml2Variables((Variables)element, changedElement);
		} else if (element instanceof From) {
			reader.xml2From((From)element, changedElement);
		} else if (element instanceof FromPart) {
			reader.xml2FromPart((FromPart)element, changedElement);
		} else if (element instanceof FromParts) {
			reader.xml2FromParts((FromParts)element, changedElement);
		} else if (element instanceof To) {
			reader.xml2To((To)element, changedElement);
		} else if (element instanceof ToPart) {
			reader.xml2ToPart((ToPart)element, changedElement);
		} else if (element instanceof ToParts) {
			reader.xml2ToParts((ToParts)element, changedElement);
		} else if (element instanceof Query) {
			reader.xml2Query((Query)element, changedElement);
		} else if (element instanceof ServiceRef) {
			reader.xml2ServiceRef((ServiceRef)element, changedElement);
		} else if (element instanceof PartnerLinks) {
			reader.xml2PartnerLinks((PartnerLinks) element, changedElement);
		} else if (element instanceof PartnerLink) {
			reader.xml2PartnerLink((PartnerLink) element, changedElement);
		} else if (element instanceof Catch){
			reader.xml2Catch((Catch)element, changedElement);
		} else if (element instanceof CatchAll){
			reader.xml2CatchAll((CatchAll)element, changedElement);
		} else if (element instanceof Copy){
			reader.xml2Copy((Copy)element, changedElement);
		} else if (element instanceof FaultHandler) {
			reader.xml2FaultHandler((FaultHandler)element, changedElement);
		} else if (element instanceof EventHandler) {
			reader.xml2EventHandler((EventHandler)element, changedElement);
		} else if (element instanceof TerminationHandler) {
			reader.xml2TerminationHandler((TerminationHandler)element, changedElement);
		} else if (element instanceof Correlation) {
			reader.xml2Correlation((Correlation)element, changedElement);
		} else if (element instanceof Correlations) {
			reader.xml2Correlations((Correlations)element, changedElement);
		} else if (element instanceof CorrelationSet) {
			reader.xml2CorrelationSet((CorrelationSet)element, changedElement);
		} else if (element instanceof CorrelationSets) {
			reader.xml2CorrelationSets((CorrelationSets)element, changedElement);
		} else if (element instanceof OnAlarm){
			reader.xml2OnAlarm((OnAlarm)element, changedElement);
		} else if (element instanceof OnMessage){
			reader.xml2OnMessage((OnMessage)element, changedElement);
		} else if (element instanceof OnEvent){
			reader.xml2OnEvent((OnEvent)element, changedElement);
		} else if (element instanceof MessageExchange){
			reader.xml2MessageExchange((MessageExchange)element, changedElement);
		} else if (element instanceof MessageExchanges){
			reader.xml2MessageExchanges((MessageExchanges)element, changedElement);
		} else if (element instanceof Extension){
			reader.xml2Extension((Extension)element, changedElement);
		} else if (element instanceof Extensions){
			reader.xml2Extensions((Extensions)element, changedElement);
		} else if (element instanceof Source){
			reader.xml2Source((Source)element, changedElement);
		} else if (element instanceof Sources){
			reader.xml2Sources((Sources)element, changedElement);
		} else if (element instanceof Target){
			reader.xml2Target((Target)element, changedElement);
		} else if (element instanceof Targets){
			reader.xml2Targets((Targets)element, changedElement);
		} else {
			System.err.println("Cannot reconcile: " + element.getClass());
//			throw new NotImplementedException(element.getClass().toString());
		}
	}

	private static boolean isLoading(WSDLElement element) {
		Process process = BPELUtils.getProcess(element);
		return process == null || process.eResource() == null || !process.eResource().isLoaded();
	}
	
	public static void replaceChild(WSDLElement parent, WSDLElement oldElement, WSDLElement newElement) {
		if (isLoading(parent)) {
			return;
		}
		if (parent.getElement() == null) {
			System.err.println("trying to replace child on null element: " + parent.getClass());
			return;
		}
		if (oldElement == newElement) {
			return;
		}
		if (newElement != null) {
			if (newElement.getElement() == null) {
				newElement.setElement(ElementFactory.getInstance().createElement(newElement, parent));
			}
			if (oldElement != null && oldElement.getElement() != null && parent.getElement() == oldElement.getElement().getParentNode()) {
				parent.getElement().replaceChild(newElement.getElement(), oldElement.getElement());
			} else {
				ElementPlacer.placeChild(parent.getElement(), newElement.getElement());
			}
		} else if (oldElement != null && oldElement.getElement() != null && parent.getElement() == oldElement.getElement().getParentNode())  {
			parent.getElement().removeChild(oldElement.getElement());
		}
	}
	
	public static void replaceAttribute(WSDLElement element, String attributeName, String attributeValue) {
		if (isLoading(element)) {
			return;
		}
		if (element.getElement() == null) {
			System.err.println("trying to replace attribute on null element:" + element.getClass());
			return;
		}
		if (attributeValue != null) {
			element.getElement().setAttribute(attributeName, attributeValue);
		} else {
			element.getElement().removeAttribute(attributeName);
		}	
	}
	
	public static void replaceAttribute(WSDLElement element, String attributeName, QName attributeValue) {
		if (isLoading(element)) {
			return;
		}
		if (element.getElement() == null) {
			System.err.println("trying to replace attribute on null element: " + element.getClass());
			return;
		}
		replaceAttribute(element, attributeName, attributeValue == null ? null : ElementFactory.getInstance().createName(element, attributeValue));
	}
		
	public static void replaceLiteral(From from, String literal) {
		Element parentElement = from.getElement();
		if (parentElement == null || isLoading(from)) {
			return;
		}
		Element oldLiteral = ReconciliationBPELReader.getBPELChildElementByLocalName(parentElement, BPELConstants.ND_LITERAL);
		Node newLiteral = literal == null ? null : ElementFactory.getInstance().createLiteral(from, literal);
		if (oldLiteral == null) {
			if (newLiteral != null) {
				ElementPlacer.placeChild(parentElement, newLiteral);
			}
		} else {
			if (newLiteral != null) {
				parentElement.replaceChild(newLiteral, oldLiteral);
			} else {
				parentElement.removeChild(oldLiteral);
			}
		}
	}
	
	public static void replaceText(Element element, Object text) {
		if (element == null) {
			System.err.println("trying to replace text on null element");
			return;
		}		
		
		ArrayList<Node> nodesToRemove = new ArrayList<Node>();
		Node node = element.getFirstChild();		
		boolean bCData = false;		
		while (node != null) {
			switch (node.getNodeType()) {
			case Node.TEXT_NODE:
				if (bCData) {
					break;
				}
				nodesToRemove.add(node);
				break;
			case Node.CDATA_SECTION_NODE:
				if (bCData == false) {
					nodesToRemove.clear();
					bCData = true;
				}
				nodesToRemove.add(node);
				break;
			}
			node = node.getNextSibling();
		}
		for (Node n : nodesToRemove) {
			element.removeChild(n);
		}
		
		// TODO: (DU) Here must be some method like in BPELWriter.expression2XML
		if (text != null) {
			CDATASection cdata = BPELUtils.createCDATASection(element.getOwnerDocument(), text.toString());
			ElementPlacer.placeChild(element, cdata);
		}
	}
	
	public static void replaceExpression(WSDLElement parent, Expression expression) {
		if (isLoading(parent)) {
			return;
		}
		if (parent.getElement() == null) {
			System.err.println("trying to replace expression on null element:" + parent.getClass());
			return;
		}
		Element element = parent.getElement();
		if (expression != null) {
			if (expression.getExpressionLanguage() != null) {
				element.setAttribute("expressionLanguage", expression.getExpressionLanguage());
			}
			replaceText(element, expression.getBody());			
		} else {
			replaceText(element, null);
		}
		
	}
	
	public static void replaceFaultHandler(WSDLElement parent, FaultHandler newFaultHandler) {
		if (isLoading(parent)) {
			return;
		}
		if (parent.getElement() == null) {
			System.err.println("trying to replace faultHandler on null element:" + parent.getClass());
			return;
		}
		Element parentElement = parent.getElement();
		for (Element node : ReconciliationBPELReader.getBPELChildElementsByLocalName(parentElement, BPELConstants.ND_CATCH)) {
			parentElement.removeChild(node);
		}
		parentElement.removeChild(ReconciliationBPELReader.getBPELChildElementByLocalName(parentElement, BPELConstants.ND_CATCH_ALL));
		if (newFaultHandler != null) {
			ElementFactory.getInstance().writeFaultHandler(newFaultHandler, parent);
		}
	}
	
	public static void replaceValue(ServiceRef serviceRef, Node oldValueNode, Node newValueNode) {
		if (isLoading(serviceRef)) {
			return;
		}
		if (serviceRef.getElement() == null) {
			return;
		}
		if (oldValueNode == null && newValueNode != null) {
			ElementPlacer.placeChild(serviceRef.getElement(), newValueNode);
		} else if (oldValueNode != null && newValueNode != null) {
			serviceRef.getElement().replaceChild(newValueNode, oldValueNode);
		}
	}
	
	private ReconciliationBPELReader getReader(WSDLElement element, Element changedElement) {
		ReconciliationBPELReader reader = document2reader.get(changedElement.getOwnerDocument());
		if (reader == null) {
			reader = new ReconciliationBPELReader(BPELUtils.getProcess(element));
			document2reader.put(changedElement.getOwnerDocument(), reader);			
		}
		return reader;
	}
	
	public void patchDom(EObject child, EObject parent, Node parentElement, EObject before, Node beforeElement) {
		if (child instanceof Variable) {
			parentElement = patchParentElement(child, parent, parentElement, BPELConstants.ND_VARIABLE);
		} else if (child instanceof CorrelationSet) {
			parentElement = patchParentElement(child, parent, parentElement, BPELConstants.ND_CORRELATION_SET);
		} else if (child instanceof PartnerLink) {
			parentElement = patchParentElement(child, parent, parentElement, BPELConstants.ND_PARTNER_LINK);
		}
		
		// TODO: (DU) probably this if will be no longer needed when sync work is finished
	    if (!BPELUtils.isTransparent(parent, child)) {
	    	Element childElement = ((WSDLElement)child).getElement();
			if (childElement == null) {
	    		childElement = ElementFactory.getInstance().createElement(((ExtensibleElement)child), parent);
	    		((ExtensibleElement)child).setElement(childElement);
	    	}
			if (childElement.getParentNode() != parentElement) {
				parentElement.insertBefore(childElement, beforeElement);
			}
	    } else if (child instanceof FaultHandler){
	    	((FaultHandler)child).setElement((Element)parentElement);
	    }
	    
	    // This code is to handle particular types that are created with their children
	    if (child instanceof ForEach) {
	    	ForEach forEach = (ForEach)child;
	    	Variable counter = forEach.getCounterName();
	    	if (counter.getElement() == null) {
	    		replaceAttribute(forEach, BPELConstants.AT_COUNTER_NAME, counter.getName());
	    	}
	    } else if (child instanceof Scope) {
	    	
	    } else if (child instanceof Pick) {
	    	
	    } else if (child instanceof Assign) {
	    	
	    } else if (child instanceof Catch) {
	    	Catch c = (Catch)child;
	    	reconcile(c, c.getElement());
			System.err.println("Catch patch ok");
	    } else if (child instanceof CatchAll) {
	    	CatchAll c = (CatchAll)child;
	    	reconcile(c, c.getElement());
			System.err.println("CatchAll patch ok");
	    } else if (child instanceof TerminationHandler) {
	    	TerminationHandler th = (TerminationHandler)child;
	    	reconcile(th, th.getElement());
			System.err.println("TerminationHandler patch ok");
	    } else if (child instanceof OnEvent) {
	    	
	    } else if (child instanceof OnAlarm) {
		    
	    } else if (child instanceof FaultHandler) {
	    	FaultHandler c = (FaultHandler)child;
	    	EList<Catch> _catch = c.getCatch();
			if (_catch.size() == 1 && _catch.get(0).getElement() == null) {
				Catch ch = _catch.get(0);
				Element catchElement = ReconciliationBPELReader.getBPELChildElementByLocalName(c.getElement(), BPELConstants.ND_CATCH);
				ch.setElement(catchElement);
				reconcile(ch, catchElement);
	    	}
			System.err.println("FaultHandler patch ok");
	    } 
	    	
	}

	private Node patchParentElement(EObject child, EObject parent, Node parentElement, String nodeName) {
		WSDLElement variables = (WSDLElement)parent;
		WSDLElement	var = (WSDLElement)child;
		if (variables.getElement() == null) {
			WSDLElement container = (WSDLElement)variables.eContainer();
			variables.setElement(ElementFactory.getInstance().createElement(variables, container));
			var.setElement(ReconciliationBPELReader.getBPELChildElementByLocalName(variables.getElement(), nodeName));
			parentElement = variables.getElement();
			ElementPlacer.placeChild(container.getElement(), parentElement);
		}
		return parentElement;
	}
	
	public static void updateVariableName(WSDLElement parent, String varName) {
		if (parent == null || parent.getElement() == null) {
			return;
		}
		if (parent instanceof Catch) {
			replaceAttribute(parent, BPELConstants.AT_FAULT_VARIABLE, varName);
		}
	}
	
	public static void adoptChild(WSDLElement parent, List<? extends WSDLElement> children, WSDLElement newChild, String nodeName) {
		if (isLoading(parent) || parent.getElement() == null) {
			return;
		}
		if (newChild.getElement() == null) {
			newChild.setElement(ElementFactory.getInstance().createElement(newChild, parent));
		}
		if (newChild.getElement().getParentNode() == parent.getElement()) {
			// already in the dom tree
			return;
		}
		int index = children.indexOf(newChild);
		List<Element> domChildren = ReconciliationBPELReader.getBPELChildElementsByLocalName(parent.getElement(), nodeName);
		if (index >= domChildren.size()) {
			ElementPlacer.placeChild(parent.getElement(), newChild.getElement());
		} else {
			parent.getElement().insertBefore(newChild.getElement(), domChildren.get(index));
		}
	}

	public static void orphanChild(WSDLElement parent, WSDLElement child) {
		if (isLoading(parent) || parent.getElement() == null) {
			return;
		}
		if (parent.getElement() != null && child.getElement() != null && child.getElement().getParentNode() == parent.getElement()) {
			parent.getElement().removeChild(child.getElement());
		}
	}
		
//    public static Collection<Element> getContentNodes(WSDLElement element, Element changedElement) {
//        Collection<Element> result = new ArrayList<Element>();
//        for (Node child = changedElement.getFirstChild(); child != null; child = child.getNextSibling()) {
//            if (child.getNodeType() == Node.ELEMENT_NODE) {
//                result.add((Element)child);
//            } else {
//                System.err.println("getContentNodes:" + element.getClass() + ", child: " + child);
//            }
//        }
//        return result;
//    }
//    
//    private static class MyBPELReader extends BPELReader {
//        protected void setProperties(Element element, EObject object, String propertyName) {
//            super.setProperties(element, object, propertyName);
//        }
//    }
//    
//    static public Activity createActivity(Element child) {
//        String bpelType = child.getLocalName();
//
//        if (bpelType.equals(BPELConstants.ND_EMPTY)) {
//            Empty empty = BPELFactory.eINSTANCE.createEmpty();
//            empty.setElement(child);
//            return empty;
//        } else if (bpelType.equals(BPELConstants.ND_INVOKE)) {
//            Invoke invoke = BPELFactory.eINSTANCE.createInvoke();
//            invoke.setElement(child);
//            return invoke;
//        } else if (bpelType.equals(BPELConstants.ND_RECEIVE)) {
//            Receive receive = BPELFactory.eINSTANCE.createReceive();
//            receive.setElement(child);
//            return receive;
//        } else if (bpelType.equals(BPELConstants.ND_REPLY)) {
//            Reply reply = BPELFactory.eINSTANCE.createReply();
//            reply.setElement(child);
//            return reply;
//        } else if (bpelType.equals(BPELConstants.ND_VALIDATE)) {
//            Validate validate = BPELFactory.eINSTANCE.createValidate();
//            validate.setElement(child);
//            return validate;
//        } else if (bpelType.equals(BPELConstants.ND_IF)) {
//            If _if = BPELFactory.eINSTANCE.createIf();
//            _if.setElement(child);
//            return _if;
//        } else if (bpelType.equals(BPELConstants.ND_PICK)) {
//            Pick pick = BPELFactory.eINSTANCE.createPick();
//            pick.setElement(child);
//            return pick;
//        } else if (bpelType.equals(BPELConstants.ND_WHILE)) {
//            While _while = BPELFactory.eINSTANCE.createWhile();
//            _while.setElement(child);
//            return _while;
//        } else if (bpelType.equals(BPELConstants.ND_FOR_EACH)) {
//            ForEach foreach = BPELFactory.eINSTANCE.createForEach();
//            foreach.setElement(child);
//            return foreach;
//        } else if (bpelType.equals(BPELConstants.ND_REPEAT_UNTIL)) {
//            RepeatUntil repeatUntil = BPELFactory.eINSTANCE.createRepeatUntil();
//            repeatUntil.setElement(child);
//            return repeatUntil;
//        } else if (bpelType.equals(BPELConstants.ND_WAIT)) {
//            Wait wait = BPELFactory.eINSTANCE.createWait();
//            wait.setElement(child);
//            return wait;
//        } else if (bpelType.equals(BPELConstants.ND_SEQUENCE)) {
//            Sequence sequence = BPELFactory.eINSTANCE.createSequence();
//            sequence.setElement(child);
//            return sequence;
//        } else if (bpelType.equals(BPELConstants.ND_SCOPE)) {
//            Scope scope = BPELFactory.eINSTANCE.createScope();
//            scope.setElement(child);
//            return scope;
//        } else if (bpelType.equals(BPELConstants.ND_FLOW)) {
//            Flow flow = BPELFactory.eINSTANCE.createFlow();
//            flow.setElement(child);
//            return flow;
//        } else if (bpelType.equals(BPELConstants.ND_EXIT)) {
//            Exit exit = BPELFactory.eINSTANCE.createExit();
//            exit.setElement(child);
//            return exit;
//        } else if (bpelType.equals(BPELConstants.ND_THROW)) {
//            Throw _throw = BPELFactory.eINSTANCE.createThrow();
//            _throw.setElement(child);
//            return _throw;
//        } else if (bpelType.equals(BPELConstants.ND_RETHROW)) {
//            Rethrow rethrow = BPELFactory.eINSTANCE.createRethrow();
//            rethrow.setElement(child);
//            return rethrow;
//        } else if (bpelType.equals(BPELConstants.ND_COMPENSATE)) {
//            Compensate compensate = BPELFactory.eINSTANCE.createCompensate();
//            compensate.setElement(child);
//            return compensate;
//        } else if (bpelType.equals(BPELConstants.ND_COMPENSATE_SCOPE)) {
//            CompensateScope compensateScope = BPELFactory.eINSTANCE.createCompensateScope();
//            compensateScope.setElement(child);
//            return compensateScope;
//        } else if (bpelType.equals(BPELConstants.ND_ASSIGN)) {
//            Assign assign = BPELFactory.eINSTANCE.createAssign();
//            assign.setElement(child);
//            return assign;
//        } 
//        return null;
//    }
//    
//    public static void setProperties(Element element, EObject eObject, String propertyName) {
//        MyBPELReader reader = new MyBPELReader();
//        reader.setProperties(element, eObject, propertyName);
//    }
//
    public static boolean isSingleActivityContainer(Object context) {
        if (context instanceof If)  return true;
        if (context instanceof ForEach)  return true;
        if (context instanceof ElseIf)  return true;
        if (context instanceof Else)  return true;
        if (context instanceof Catch)  return true;
        if (context instanceof CatchAll)  return true;
        if (context instanceof OnAlarm)  return true;
        if (context instanceof OnMessage)  return true;
        if (context instanceof OnEvent)  return true;
        if (context instanceof Process)  return true;
        if (context instanceof While)  return true;
        if (context instanceof RepeatUntil)  return true;
        if (context instanceof CompensationHandler) return true;
        return false;
    }
//
//    public static EList getActivities(Object context) {
//        if (context instanceof Sequence) {
//            return ((Sequence)context).getActivities();         
//        }
//        if (context instanceof Flow) {
//            return ((Flow)context).getActivities();         
//        }
//        throw new IllegalArgumentException();
//    }
//    
    public static Activity getActivity(Object context) {
        if (context instanceof ElseIf)  return ((ElseIf)context).getActivity();
        if (context instanceof ForEach) return ((ForEach)context).getActivity();
        if (context instanceof Else)  return ((Else)context).getActivity();
        if (context instanceof Catch)  return ((Catch)context).getActivity();
        if (context instanceof CatchAll)  return ((CatchAll)context).getActivity();
        if (context instanceof OnAlarm)  return ((OnAlarm)context).getActivity();
        if (context instanceof OnMessage)  return ((OnMessage)context).getActivity();
        if (context instanceof OnEvent)  return ((OnEvent)context).getActivity();
        if (context instanceof Process)  return ((Process)context).getActivity();
        if (context instanceof While)  return ((While)context).getActivity();
        if (context instanceof RepeatUntil)  return ((RepeatUntil)context).getActivity();
        if (context instanceof Scope)  return ((Scope)context).getActivity();
        if (context instanceof FaultHandler) return getCatchAll((FaultHandler)context);
        if (context instanceof CompensationHandler)  return ((CompensationHandler)context).getActivity();
        if (context instanceof TerminationHandler)  return ((TerminationHandler)context).getActivity();
        if (context instanceof If) return ((If) context).getActivity();
        System.err.println("Missing getActivity():" + context.getClass());
        throw new IllegalArgumentException();
    }
//
//    public static void setActivity(Object context, Activity activity) {
//        if (context instanceof ElseIf) {
//            ((ElseIf)context).setActivity(activity); return;
//        }
//        if (context instanceof Else) {
//            ((Else)context).setActivity(activity); return;
//        }
//        if (context instanceof Catch) {
//            ((Catch)context).setActivity(activity); return;
//        }
//        if (context instanceof CatchAll) {
//            ((CatchAll)context).setActivity(activity); return;
//        }
//        if (context instanceof OnAlarm) {
//            ((OnAlarm)context).setActivity(activity); return;
//        }
//        if (context instanceof OnMessage) {
//            ((OnMessage)context).setActivity(activity); return;
//        }
//        if (context instanceof OnEvent) {
//            ((OnEvent)context).setActivity(activity); return;
//        }
//        if (context instanceof Process) {
//            ((Process)context).setActivity(activity); return;
//        }
//        if (context instanceof While) {
//            ((While)context).setActivity(activity); return;
//        }
//        if (context instanceof RepeatUntil) {
//            ((RepeatUntil)context).setActivity(activity); return;
//        }
//        if (context instanceof Scope) {
//            ((Scope)context).setActivity(activity); return;
//        }
//        if (context instanceof FaultHandler) {
//            setCatchAll((FaultHandler)context, activity); return;
//        }
//        if (context instanceof CompensationHandler) {
//            ((CompensationHandler)context).setActivity(activity); return;
//        }
//        if (context instanceof TerminationHandler) {
//            ((TerminationHandler)context).setActivity(activity); return;
//        }
//        if (context instanceof If) {
//            ((If)context).setActivity(activity); return;
//        }       
//        throw new IllegalArgumentException();
//    }
//
    private static Activity getCatchAll(FaultHandler faultHandler) {
        CatchAll catchAll = faultHandler.getCatchAll();
        return (catchAll == null)? null : catchAll.getActivity();
    }
//    
//    public static void setCatchAll(FaultHandler faultHandler, Activity activity) {
//        if (activity == null)  {
//            faultHandler.setCatchAll(null);
//        } else if (faultHandler.getCatchAll() == null) {
//            CatchAll catchAll = BPELFactory.eINSTANCE.createCatchAll();
//            catchAll.setElement(ElementFactory.createElement(catchAll, faultHandler));          
//            faultHandler.setCatchAll(catchAll);
//            faultHandler.getElement().appendChild(catchAll.getElement());
//            catchAll.setActivity(activity);
//        } else {
//            faultHandler.getCatchAll().setActivity(activity);
//        }
//    }   
//    
//    public static void addActivity(WSDLElement parent, Activity activity) {
//        if (isSingleActivityContainer(parent)) {
//            Activity oldActivity = getActivity(parent);         
//            if (oldActivity != null) {      
//                boolean oldFirst = parent.getElement().getFirstChild() == oldActivity.getElement();             
//                Sequence sequence;
//                if (oldActivity instanceof Sequence) {
//                    sequence = (Sequence) oldActivity;
//                } else if (activity instanceof Sequence) {
//                     sequence = (Sequence)activity;                  
//                } else {
//                    sequence = BPELFactory.eINSTANCE.createSequence();
//                    sequence.setElement(ElementFactory.createElement(sequence, parent));
//                    parent.getElement().removeChild(oldActivity.getElement());
//                }
//                setActivity(parent, activity);
//                if (oldFirst) {
//                    sequence.getActivities().add(oldActivity);
//                    sequence.getElement().appendChild(oldActivity.getElement());
//                }
//                sequence.getActivities().add(activity);
//                sequence.getElement().appendChild(activity.getElement());
//                if (!oldFirst) {
//                    sequence.getActivities().add(oldActivity);
//                    sequence.getElement().appendChild(oldActivity.getElement());
//                }
////              parent.getElement().removeChild(oldActivity.getElement());
//            } else {
//                setActivity(parent, activity);
//            }
//        } else {
//            getActivities(parent).add(activity);
//        }
//    }   	
}
