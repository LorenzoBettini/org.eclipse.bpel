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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;

import org.apache.xerces.util.DOMUtil;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Branches;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.CatchAll;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.CompletionCondition;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.Else;
import org.eclipse.bpel.model.ElseIf;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.ExtensibleElement;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.OnAlarm;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.Validate;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.model.resource.BPELResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.WSDLElement;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ElementFactory {
	private HashMap<Document, MyBPELWriter> document2Writers = new HashMap<Document, MyBPELWriter>();
	
	private static class MyBPELWriter extends org.eclipse.bpel.model.resource.BPELWriter {
		public MyBPELWriter(org.eclipse.bpel.model.resource.BPELResource bpelResource, Document document) {
			super(bpelResource, document);
		}

		protected Element createBPELElement(String tagName) {
			return super.createBPELElement(tagName);
		}
		
		protected Element activity2XML(org.eclipse.bpel.model.Activity activity) {
			return super.activity2XML(activity);
		}
		
		protected Element variable2XML(Variable variable) {
			return super.variable2XML(variable);
		}
		
		protected Element variables2XML(Variables variables) {
			return super.variables2XML(variables);
		}
		
		protected Element completionCondition2XML(CompletionCondition completionCondition) {
			return super.completionCondition2XML(completionCondition);
		}
		
		protected Element expression2XML(Expression expression, String elementName) {
			return super.expression2XML(expression, elementName);
		}
		
		protected Element branches2XML(Branches branches) {
			return super.branches2XML(branches);
		}
		
		protected Element extensibilityElement2XML(ExtensibilityElement extensibilityElement) {
			return super.extensibilityElement2XML(extensibilityElement);
		}
		
		protected Element correlationSets2XML(CorrelationSets correlationSets) {
			return super.correlationSets2XML(correlationSets);
		}
		
		protected Element correlationSet2XML(CorrelationSet correlationSet) {
			return super.correlationSet2XML(correlationSet);
		}
		
		protected Element partnerLinks2XML(PartnerLinks partnerLinks) {
			return super.partnerLinks2XML(partnerLinks);
		}
		
		protected Element elseIf2XML(ElseIf elseIf) {
			return super.elseIf2XML(elseIf);
		}
		
		protected Element else2XML(Else _else) {
			return super.else2XML(_else);
		}
		
		protected Element onAlarm2XML(OnAlarm onAlarm) {
			return super.onAlarm2XML(onAlarm);
		}
		
		protected Element onMessage2XML(OnMessage onMsg) {
			return super.onMessage2XML(onMsg);
		}
		
		protected Element copy2XML(Copy copy) {
			return super.copy2XML(copy);
		}
		
		protected Element catch2XML(Catch _catch) {
			return super.catch2XML(_catch);
		}
		
		protected Element catchAll2XML(CatchAll catchAll) {
			return super.catchAll2XML(catchAll);
		}
		
		protected Element compensationHandler2XML(CompensationHandler compensationHandler) {
			return super.compensationHandler2XML(compensationHandler);
		}
		
		protected void to2XML(To to, Element toElement) {
			super.to2XML(to, toElement);
		}
		
		protected void from2XML(From from, Element fromElement) {
			super.from2XML(from, fromElement);
		}
		
		protected Element eventHandler2XML(EventHandler eventHandler) {
			return super.eventHandler2XML(eventHandler);
		}
		
		protected Element partnerLink2XML(PartnerLink partnerLink) {
			return super.partnerLink2XML(partnerLink);
		}
		
		protected Element validate2XML(Validate activity) {
			return super.validate2XML(activity);
		}
		
		protected Element query2XML(Query query) {
			return super.query2XML(query);
		}
		
		protected Element faultHandlers2XML(FaultHandler faultHandler) {
			return super.faultHandlers2XML(faultHandler);
		}
	}
	private static ElementFactory factory;
	
	public static ElementFactory getInstance() {
		if (factory == null) {
			factory = new ElementFactory();
		}
		return factory;
	}
	
	
	public Element createElement(WSDLElement element, Object parent) {
		MyBPELWriter writer = getWriter(parent);
		if (element instanceof Activity) {
			return writer.activity2XML((Activity)element);
		}
		if (element instanceof Variable) {
			return writer.variable2XML((Variable)element);
		}
		if (element instanceof Variables) {
			return writer.variables2XML((Variables)element);
		}
		if (element instanceof CompletionCondition) {
			return writer.completionCondition2XML((CompletionCondition)element);
		}		
		if (element instanceof PartnerLinks) {
			return writer.partnerLinks2XML((PartnerLinks)element);
		}
		if (element instanceof CorrelationSets) {
			return writer.correlationSets2XML((CorrelationSets)element);
		}
		if (element instanceof ElseIf) {
			return writer.elseIf2XML((ElseIf)element);
		}
		if (element instanceof Else) {
			return writer.else2XML((Else)element);
		}
		if (element instanceof OnAlarm) {
			return writer.onAlarm2XML((OnAlarm)element);			
		}
		if (element instanceof OnMessage) {
			return writer.onMessage2XML((OnMessage)element);
		}
		if (element instanceof Copy) {
			return writer.copy2XML((Copy)element);
		}
		if (element instanceof Catch) {
			return writer.catch2XML((Catch)element);
		}
		if (element instanceof CatchAll) {
			return writer.catchAll2XML((CatchAll)element);
		}
		if (element instanceof CompensationHandler) {
			return writer.compensationHandler2XML((CompensationHandler)element);
		}
		if (element instanceof To) {
			Element toElement = writer.createBPELElement("to");
			writer.to2XML((To)element, toElement);
			return toElement;
		}
		if (element instanceof From) {
			Element fromElement = writer.createBPELElement("from");
			writer.from2XML((From)element, fromElement);
			return fromElement;
		}
		if (element instanceof CorrelationSet) {
			return writer.correlationSet2XML((CorrelationSet)element);
		}
		if (element instanceof EventHandler) {
			return writer.eventHandler2XML((EventHandler)element);
		}
		if (element instanceof Branches) {
			Element branches = writer.branches2XML((Branches)element);
			branches.setAttribute(BPELConstants.AT_SUCCESSFUL_BRANCHES_ONLY, BPELUtils.boolean2XML(((Branches)element).getCountCompletedBranchesOnly()));
			return branches;
		}
		if (element instanceof Condition) {
			return writer.expression2XML((Condition)element, BPELConstants.ND_CONDITION);
		}
		if (element instanceof PartnerLink) {
			return writer.partnerLink2XML((PartnerLink)element);
		}
		if (element instanceof Validate) {
			return writer.validate2XML((Validate)element);
		}
		if (element instanceof Query) {
			return writer.query2XML((Query)element);
		}
		if (element instanceof FaultHandler) {
			return writer.faultHandlers2XML((FaultHandler)element);
		}
		System.err.println("Cannot create: " + element.toString());
		return writer.createBPELElement("error");
//		throw new IllegalArgumentException("Unhandled type: " + element.toString());
	}	
	
	public Node createLiteral(From from, String text) {
		MyBPELWriter writer = getWriter(from);
		Node node = null;
		Element literal = writer.createBPELElement("literal");			
			
		if (Boolean.TRUE.equals(from.getUnsafeLiteral())) {
			node = BPELUtils.convertStringToNode(text, writer.getResource());
		}
			
		if (node != null) {
			for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
				DOMUtil.copyInto(child, literal);
			}
		} else {
			CDATASection cdata = BPELUtils.createCDATASection(from.getElement().getOwnerDocument(), from.getLiteral());
			return cdata;
		}
		return literal;
	}
	
	public Element createExpressionElement(Expression element, Object parent, String name) {
		MyBPELWriter writer = getWriter(parent);		
		return writer.expression2XML(element, name);
	}
	
	public String createName(WSDLElement element, QName name) {
		return getWriter(element).getNamespacePrefixManager().qNameToString(element, name);
	}
	
	private MyBPELWriter getWriter(Object parent) {
		Document ownerDocument = getOwnerDocument(parent);
		MyBPELWriter writer = document2Writers.get(ownerDocument);
		if (writer == null) {
			// TODO: (DU) check if adding namespace is ok
			BPELResource resource = (BPELResource)((EObject)parent).eResource();
			Map nsMap = (Map)resource.getPrefixToNamespaceMap(resource.getProcess());
			if (resource.getOptionUseNSPrefix()) {
	            // Check for existing prefix.
	            String prefix = null;
	            for (Iterator i = nsMap.entrySet().iterator(); i.hasNext();) {
	                Map.Entry entry = (Map.Entry) i.next();
	                if (resource.getNamespaceURI().equals(entry.getValue())) {
	                    // Remove the entry if it is the default namespace.
	                    if ("".equals(entry.getKey())) {
	                        i.remove();
	                    } else {
	                        prefix = (String) entry.getKey();
	                    }
	                }
	            }
	            if (prefix == null) {
	                nsMap.put(BPELConstants.PREFIX, resource.getNamespaceURI());
	            }
	        } else {
	            nsMap.put("", resource.getNamespaceURI());
	        }			
			writer = new MyBPELWriter(resource, ownerDocument);			
			document2Writers.put(ownerDocument, writer);
		}
		return writer;
	}

	private static Document getOwnerDocument(Object parent) {
		Document ownerDocument;
//		if (!BPELUtils.isTransparentObject(parent)) {
			ownerDocument = ((ExtensibleElement)parent).getElement().getOwnerDocument();
//		} else {
//			ownerDocument = ((ExtensibleElement)((EObject)parent).eContainer()).getElement().getOwnerDocument();
//		}
		return ownerDocument;
	}
}