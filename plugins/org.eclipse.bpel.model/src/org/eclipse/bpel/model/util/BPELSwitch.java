/**
 * <copyright>
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * </copyright>
 *
 * $Id: BPELSwitch.java,v 1.10 2005/12/09 19:47:52 james Exp $
 */
package org.eclipse.bpel.model.util;

import java.util.List;

import javax.wsdl.extensions.ExtensibilityElement;

import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.BooleanExpression;
import org.eclipse.bpel.model.Case;
import org.eclipse.bpel.model.Catch;
import org.eclipse.bpel.model.CatchAll;
import org.eclipse.bpel.model.Compensate;
import org.eclipse.bpel.model.CompensationHandler;
import org.eclipse.bpel.model.Condition;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.model.CorrelationSet;
import org.eclipse.bpel.model.CorrelationSets;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.Empty;
import org.eclipse.bpel.model.EventHandler;
import org.eclipse.bpel.model.Exit;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.Extension;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.Extensions;
import org.eclipse.bpel.model.FaultHandler;
import org.eclipse.bpel.model.Flow;
import org.eclipse.bpel.model.ForEach;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.FromPart;
import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.Invoke;
import org.eclipse.bpel.model.Link;
import org.eclipse.bpel.model.Links;
import org.eclipse.bpel.model.OnAlarm;
import org.eclipse.bpel.model.OnEvent;
import org.eclipse.bpel.model.OnMessage;
import org.eclipse.bpel.model.OpaqueActivity;
import org.eclipse.bpel.model.Otherwise;
import org.eclipse.bpel.model.PartnerActivity;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.PartnerLinks;
import org.eclipse.bpel.model.Pick;
import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.Receive;
import org.eclipse.bpel.model.RepeatUntil;
import org.eclipse.bpel.model.Reply;
import org.eclipse.bpel.model.Rethrow;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.model.Sequence;
import org.eclipse.bpel.model.ServiceRef;
import org.eclipse.bpel.model.Source;
import org.eclipse.bpel.model.Sources;
import org.eclipse.bpel.model.Switch;
import org.eclipse.bpel.model.Target;
import org.eclipse.bpel.model.Targets;
import org.eclipse.bpel.model.TerminationHandler;
import org.eclipse.bpel.model.Throw;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.ToPart;
import org.eclipse.bpel.model.UnknownExtensibilityAttribute;
import org.eclipse.bpel.model.ValidateXML;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.Variables;
import org.eclipse.bpel.model.Wait;
import org.eclipse.bpel.model.While;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.ExtensibleElement;
import org.eclipse.wst.wsdl.UnknownExtensibilityElement;
import org.eclipse.wst.wsdl.WSDLElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpel.model.BPELPackage
 * @generated
 */
public class BPELSwitch {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BPELPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BPELSwitch() {
		if (modelPackage == null) {
			modelPackage = BPELPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case BPELPackage.PROCESS: {
				org.eclipse.bpel.model.Process process = (org.eclipse.bpel.model.Process)theEObject;
				Object result = caseProcess(process);
				if (result == null) result = caseExtensibleElement(process);
				if (result == null) result = caseWSDLElement(process);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.PARTNER_LINK: {
				PartnerLink partnerLink = (PartnerLink)theEObject;
				Object result = casePartnerLink(partnerLink);
				if (result == null) result = caseExtensibleElement(partnerLink);
				if (result == null) result = caseWSDLElement(partnerLink);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.FAULT_HANDLER: {
				FaultHandler faultHandler = (FaultHandler)theEObject;
				Object result = caseFaultHandler(faultHandler);
				if (result == null) result = caseExtensibleElement(faultHandler);
				if (result == null) result = caseWSDLElement(faultHandler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.ACTIVITY: {
				Activity activity = (Activity)theEObject;
				Object result = caseActivity(activity);
				if (result == null) result = caseExtensibleElement(activity);
				if (result == null) result = caseWSDLElement(activity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CORRELATION_SET: {
				CorrelationSet correlationSet = (CorrelationSet)theEObject;
				Object result = caseCorrelationSet(correlationSet);
				if (result == null) result = caseExtensibleElement(correlationSet);
				if (result == null) result = caseWSDLElement(correlationSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.INVOKE: {
				Invoke invoke = (Invoke)theEObject;
				Object result = caseInvoke(invoke);
				if (result == null) result = casePartnerActivity(invoke);
				if (result == null) result = caseActivity(invoke);
				if (result == null) result = caseExtensibleElement(invoke);
				if (result == null) result = caseWSDLElement(invoke);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.LINK: {
				Link link = (Link)theEObject;
				Object result = caseLink(link);
				if (result == null) result = caseExtensibleElement(link);
				if (result == null) result = caseWSDLElement(link);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CATCH: {
				Catch catch_ = (Catch)theEObject;
				Object result = caseCatch(catch_);
				if (result == null) result = caseExtensibleElement(catch_);
				if (result == null) result = caseWSDLElement(catch_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.REPLY: {
				Reply reply = (Reply)theEObject;
				Object result = caseReply(reply);
				if (result == null) result = casePartnerActivity(reply);
				if (result == null) result = caseActivity(reply);
				if (result == null) result = caseExtensibleElement(reply);
				if (result == null) result = caseWSDLElement(reply);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.PARTNER_ACTIVITY: {
				PartnerActivity partnerActivity = (PartnerActivity)theEObject;
				Object result = casePartnerActivity(partnerActivity);
				if (result == null) result = caseActivity(partnerActivity);
				if (result == null) result = caseExtensibleElement(partnerActivity);
				if (result == null) result = caseWSDLElement(partnerActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.RECEIVE: {
				Receive receive = (Receive)theEObject;
				Object result = caseReceive(receive);
				if (result == null) result = casePartnerActivity(receive);
				if (result == null) result = caseActivity(receive);
				if (result == null) result = caseExtensibleElement(receive);
				if (result == null) result = caseWSDLElement(receive);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.THROW: {
				Throw throw_ = (Throw)theEObject;
				Object result = caseThrow(throw_);
				if (result == null) result = caseActivity(throw_);
				if (result == null) result = caseExtensibleElement(throw_);
				if (result == null) result = caseWSDLElement(throw_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.WAIT: {
				Wait wait = (Wait)theEObject;
				Object result = caseWait(wait);
				if (result == null) result = caseActivity(wait);
				if (result == null) result = caseExtensibleElement(wait);
				if (result == null) result = caseWSDLElement(wait);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.EMPTY: {
				Empty empty = (Empty)theEObject;
				Object result = caseEmpty(empty);
				if (result == null) result = caseActivity(empty);
				if (result == null) result = caseExtensibleElement(empty);
				if (result == null) result = caseWSDLElement(empty);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.SEQUENCE: {
				Sequence sequence = (Sequence)theEObject;
				Object result = caseSequence(sequence);
				if (result == null) result = caseActivity(sequence);
				if (result == null) result = caseExtensibleElement(sequence);
				if (result == null) result = caseWSDLElement(sequence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.SWITCH: {
				Switch switch_ = (Switch)theEObject;
				Object result = caseSwitch(switch_);
				if (result == null) result = caseActivity(switch_);
				if (result == null) result = caseExtensibleElement(switch_);
				if (result == null) result = caseWSDLElement(switch_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CASE: {
				Case case_ = (Case)theEObject;
				Object result = caseCase(case_);
				if (result == null) result = caseExtensibleElement(case_);
				if (result == null) result = caseWSDLElement(case_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.WHILE: {
				While while_ = (While)theEObject;
				Object result = caseWhile(while_);
				if (result == null) result = caseActivity(while_);
				if (result == null) result = caseExtensibleElement(while_);
				if (result == null) result = caseWSDLElement(while_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.PICK: {
				Pick pick = (Pick)theEObject;
				Object result = casePick(pick);
				if (result == null) result = caseActivity(pick);
				if (result == null) result = caseExtensibleElement(pick);
				if (result == null) result = caseWSDLElement(pick);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.FLOW: {
				Flow flow = (Flow)theEObject;
				Object result = caseFlow(flow);
				if (result == null) result = caseActivity(flow);
				if (result == null) result = caseExtensibleElement(flow);
				if (result == null) result = caseWSDLElement(flow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.ON_ALARM: {
				OnAlarm onAlarm = (OnAlarm)theEObject;
				Object result = caseOnAlarm(onAlarm);
				if (result == null) result = caseExtensibleElement(onAlarm);
				if (result == null) result = caseWSDLElement(onAlarm);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.ASSIGN: {
				Assign assign = (Assign)theEObject;
				Object result = caseAssign(assign);
				if (result == null) result = caseActivity(assign);
				if (result == null) result = caseExtensibleElement(assign);
				if (result == null) result = caseWSDLElement(assign);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.COPY: {
				Copy copy = (Copy)theEObject;
				Object result = caseCopy(copy);
				if (result == null) result = caseExtensibleElement(copy);
				if (result == null) result = caseWSDLElement(copy);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.EXTENSION: {
				Extension extension = (Extension)theEObject;
				Object result = caseExtension(extension);
				if (result == null) result = caseExtensibleElement(extension);
				if (result == null) result = caseWSDLElement(extension);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.SCOPE: {
				Scope scope = (Scope)theEObject;
				Object result = caseScope(scope);
				if (result == null) result = caseActivity(scope);
				if (result == null) result = caseExtensibleElement(scope);
				if (result == null) result = caseWSDLElement(scope);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.COMPENSATE: {
				Compensate compensate = (Compensate)theEObject;
				Object result = caseCompensate(compensate);
				if (result == null) result = caseActivity(compensate);
				if (result == null) result = caseExtensibleElement(compensate);
				if (result == null) result = caseWSDLElement(compensate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.COMPENSATION_HANDLER: {
				CompensationHandler compensationHandler = (CompensationHandler)theEObject;
				Object result = caseCompensationHandler(compensationHandler);
				if (result == null) result = caseExtensibleElement(compensationHandler);
				if (result == null) result = caseWSDLElement(compensationHandler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.TO: {
				To to = (To)theEObject;
				Object result = caseTo(to);
				if (result == null) result = caseExtensibleElement(to);
				if (result == null) result = caseWSDLElement(to);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.FROM: {
				From from = (From)theEObject;
				Object result = caseFrom(from);
				if (result == null) result = caseTo(from);
				if (result == null) result = caseExtensibleElement(from);
				if (result == null) result = caseWSDLElement(from);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.ON_MESSAGE: {
				OnMessage onMessage = (OnMessage)theEObject;
				Object result = caseOnMessage(onMessage);
				if (result == null) result = caseExtensibleElement(onMessage);
				if (result == null) result = caseWSDLElement(onMessage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.EXPRESSION: {
				Expression expression = (Expression)theEObject;
				Object result = caseExpression(expression);
				if (result == null) result = caseExtensibilityElement(expression);
				if (result == null) result = caseWSDLElement(expression);
				if (result == null) result = caseIExtensibilityElement(expression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.BOOLEAN_EXPRESSION: {
				BooleanExpression booleanExpression = (BooleanExpression)theEObject;
				Object result = caseBooleanExpression(booleanExpression);
				if (result == null) result = caseExpression(booleanExpression);
				if (result == null) result = caseExtensibilityElement(booleanExpression);
				if (result == null) result = caseWSDLElement(booleanExpression);
				if (result == null) result = caseIExtensibilityElement(booleanExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CORRELATION: {
				Correlation correlation = (Correlation)theEObject;
				Object result = caseCorrelation(correlation);
				if (result == null) result = caseExtensibleElement(correlation);
				if (result == null) result = caseWSDLElement(correlation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.EVENT_HANDLER: {
				EventHandler eventHandler = (EventHandler)theEObject;
				Object result = caseEventHandler(eventHandler);
				if (result == null) result = caseExtensibleElement(eventHandler);
				if (result == null) result = caseWSDLElement(eventHandler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.SOURCE: {
				Source source = (Source)theEObject;
				Object result = caseSource(source);
				if (result == null) result = caseExtensibleElement(source);
				if (result == null) result = caseWSDLElement(source);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.TARGET: {
				Target target = (Target)theEObject;
				Object result = caseTarget(target);
				if (result == null) result = caseExtensibleElement(target);
				if (result == null) result = caseWSDLElement(target);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.PARTNER_LINKS: {
				PartnerLinks partnerLinks = (PartnerLinks)theEObject;
				Object result = casePartnerLinks(partnerLinks);
				if (result == null) result = caseExtensibleElement(partnerLinks);
				if (result == null) result = caseWSDLElement(partnerLinks);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.VARIABLES: {
				Variables variables = (Variables)theEObject;
				Object result = caseVariables(variables);
				if (result == null) result = caseExtensibleElement(variables);
				if (result == null) result = caseWSDLElement(variables);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CORRELATION_SETS: {
				CorrelationSets correlationSets = (CorrelationSets)theEObject;
				Object result = caseCorrelationSets(correlationSets);
				if (result == null) result = caseExtensibleElement(correlationSets);
				if (result == null) result = caseWSDLElement(correlationSets);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.LINKS: {
				Links links = (Links)theEObject;
				Object result = caseLinks(links);
				if (result == null) result = caseExtensibleElement(links);
				if (result == null) result = caseWSDLElement(links);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CATCH_ALL: {
				CatchAll catchAll = (CatchAll)theEObject;
				Object result = caseCatchAll(catchAll);
				if (result == null) result = caseExtensibleElement(catchAll);
				if (result == null) result = caseWSDLElement(catchAll);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CORRELATIONS: {
				Correlations correlations = (Correlations)theEObject;
				Object result = caseCorrelations(correlations);
				if (result == null) result = caseExtensibleElement(correlations);
				if (result == null) result = caseWSDLElement(correlations);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				Object result = caseVariable(variable);
				if (result == null) result = caseExtensibleElement(variable);
				if (result == null) result = caseWSDLElement(variable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.OTHERWISE: {
				Otherwise otherwise = (Otherwise)theEObject;
				Object result = caseOtherwise(otherwise);
				if (result == null) result = caseExtensibleElement(otherwise);
				if (result == null) result = caseWSDLElement(otherwise);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.UNKNOWN_EXTENSIBILITY_ATTRIBUTE: {
				UnknownExtensibilityAttribute unknownExtensibilityAttribute = (UnknownExtensibilityAttribute)theEObject;
				Object result = caseUnknownExtensibilityAttribute(unknownExtensibilityAttribute);
				if (result == null) result = caseUnknownExtensibilityElement(unknownExtensibilityAttribute);
				if (result == null) result = caseExtensibilityElement(unknownExtensibilityAttribute);
				if (result == null) result = caseWSDLElement(unknownExtensibilityAttribute);
				if (result == null) result = caseIExtensibilityElement(unknownExtensibilityAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.ON_EVENT: {
				OnEvent onEvent = (OnEvent)theEObject;
				Object result = caseOnEvent(onEvent);
				if (result == null) result = caseExtensibleElement(onEvent);
				if (result == null) result = caseWSDLElement(onEvent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.IMPORT: {
				Import import_ = (Import)theEObject;
				Object result = caseImport(import_);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.RETHROW: {
				Rethrow rethrow = (Rethrow)theEObject;
				Object result = caseRethrow(rethrow);
				if (result == null) result = caseActivity(rethrow);
				if (result == null) result = caseExtensibleElement(rethrow);
				if (result == null) result = caseWSDLElement(rethrow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.CONDITION: {
				Condition condition = (Condition)theEObject;
				Object result = caseCondition(condition);
				if (result == null) result = caseExpression(condition);
				if (result == null) result = caseExtensibilityElement(condition);
				if (result == null) result = caseWSDLElement(condition);
				if (result == null) result = caseIExtensibilityElement(condition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.TARGETS: {
				Targets targets = (Targets)theEObject;
				Object result = caseTargets(targets);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.SOURCES: {
				Sources sources = (Sources)theEObject;
				Object result = caseSources(sources);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.QUERY: {
				Query query = (Query)theEObject;
				Object result = caseQuery(query);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.SERVICE_REF: {
				ServiceRef serviceRef = (ServiceRef)theEObject;
				Object result = caseServiceRef(serviceRef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.EXIT: {
				Exit exit = (Exit)theEObject;
				Object result = caseExit(exit);
				if (result == null) result = caseActivity(exit);
				if (result == null) result = caseExtensibleElement(exit);
				if (result == null) result = caseWSDLElement(exit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.EXTENSIONS: {
				Extensions extensions = (Extensions)theEObject;
				Object result = caseExtensions(extensions);
				if (result == null) result = caseExtensibleElement(extensions);
				if (result == null) result = caseWSDLElement(extensions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.EXTENSION_ACTIVITY: {
				ExtensionActivity extensionActivity = (ExtensionActivity)theEObject;
				Object result = caseExtensionActivity(extensionActivity);
				if (result == null) result = caseActivity(extensionActivity);
				if (result == null) result = caseExtensibleElement(extensionActivity);
				if (result == null) result = caseWSDLElement(extensionActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.FROM_PART: {
				FromPart fromPart = (FromPart)theEObject;
				Object result = caseFromPart(fromPart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.TO_PART: {
				ToPart toPart = (ToPart)theEObject;
				Object result = caseToPart(toPart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.OPAQUE_ACTIVITY: {
				OpaqueActivity opaqueActivity = (OpaqueActivity)theEObject;
				Object result = caseOpaqueActivity(opaqueActivity);
				if (result == null) result = caseActivity(opaqueActivity);
				if (result == null) result = caseExtensibleElement(opaqueActivity);
				if (result == null) result = caseWSDLElement(opaqueActivity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.FOR_EACH: {
				ForEach forEach = (ForEach)theEObject;
				Object result = caseForEach(forEach);
				if (result == null) result = caseActivity(forEach);
				if (result == null) result = caseExtensibleElement(forEach);
				if (result == null) result = caseWSDLElement(forEach);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.REPEAT_UNTIL: {
				RepeatUntil repeatUntil = (RepeatUntil)theEObject;
				Object result = caseRepeatUntil(repeatUntil);
				if (result == null) result = caseActivity(repeatUntil);
				if (result == null) result = caseExtensibleElement(repeatUntil);
				if (result == null) result = caseWSDLElement(repeatUntil);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.TERMINATION_HANDLER: {
				TerminationHandler terminationHandler = (TerminationHandler)theEObject;
				Object result = caseTerminationHandler(terminationHandler);
				if (result == null) result = caseExtensibleElement(terminationHandler);
				if (result == null) result = caseWSDLElement(terminationHandler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BPELPackage.VALIDATE_XML: {
				ValidateXML validateXML = (ValidateXML)theEObject;
				Object result = caseValidateXML(validateXML);
				if (result == null) result = caseActivity(validateXML);
				if (result == null) result = caseExtensibleElement(validateXML);
				if (result == null) result = caseWSDLElement(validateXML);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Process</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Process</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseProcess(org.eclipse.bpel.model.Process object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Partner Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Partner Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePartnerLink(PartnerLink object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Fault Handler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Fault Handler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFaultHandler(FaultHandler object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseActivity(Activity object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Correlation Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Correlation Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCorrelationSet(CorrelationSet object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Invoke</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Invoke</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseInvoke(Invoke object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLink(Link object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Catch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Catch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCatch(Catch object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Reply</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Reply</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReply(Reply object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Partner Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Partner Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePartnerActivity(PartnerActivity object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Receive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Receive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseReceive(Receive object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Throw</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Throw</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseThrow(Throw object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Wait</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Wait</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseWait(Wait object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Empty</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Empty</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEmpty(Empty object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Sequence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Sequence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSequence(Sequence object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Switch</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Switch</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSwitch(Switch object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCase(Case object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>While</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>While</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseWhile(While object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Pick</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Pick</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePick(Pick object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFlow(Flow object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>On Alarm</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>On Alarm</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOnAlarm(OnAlarm object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Assign</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Assign</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseAssign(Assign object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Copy</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Copy</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCopy(Copy object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extension</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extension</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtension(Extension object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Scope</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Scope</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseScope(Scope object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Compensate</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Compensate</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCompensate(Compensate object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Compensation Handler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Compensation Handler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCompensationHandler(CompensationHandler object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>To</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>To</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTo(To object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>From</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>From</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFrom(From object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>On Message</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>On Message</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOnMessage(OnMessage object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExpression(Expression object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Boolean Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Boolean Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseBooleanExpression(BooleanExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Correlation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Correlation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCorrelation(Correlation object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Event Handler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Event Handler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseEventHandler(EventHandler object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSource(Source object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Target</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Target</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTarget(Target object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Partner Links</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Partner Links</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object casePartnerLinks(PartnerLinks object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Variables</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Variables</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseVariables(Variables object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Correlation Sets</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Correlation Sets</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCorrelationSets(CorrelationSets object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Links</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Links</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseLinks(Links object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Catch All</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Catch All</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCatchAll(CatchAll object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Correlations</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Correlations</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCorrelations(Correlations object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Otherwise</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Otherwise</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOtherwise(Otherwise object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Unknown Extensibility Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Unknown Extensibility Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUnknownExtensibilityAttribute(UnknownExtensibilityAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>On Event</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>On Event</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOnEvent(OnEvent object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Import</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseImport(Import object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Rethrow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Rethrow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRethrow(Rethrow object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseCondition(Condition object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Targets</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Targets</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTargets(Targets object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Sources</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Sources</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseSources(Sources object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Query</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Query</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseQuery(Query object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Service Ref</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Service Ref</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseServiceRef(ServiceRef object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Exit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Exit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExit(Exit object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extensions</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extensions</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensions(Extensions object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extension Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extension Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensionActivity(ExtensionActivity object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>From Part</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>From Part</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseFromPart(FromPart object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>To Part</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>To Part</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseToPart(ToPart object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Opaque Activity</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Opaque Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseOpaqueActivity(OpaqueActivity object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>For Each</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>For Each</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseForEach(ForEach object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Repeat Until</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Repeat Until</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseRepeatUntil(RepeatUntil object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Termination Handler</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Termination Handler</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseTerminationHandler(TerminationHandler object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Validate XML</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Validate XML</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseValidateXML(ValidateXML object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseWSDLElement(WSDLElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extensible Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extensible Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensibleElement(ExtensibleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>IExtensibility Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>IExtensibility Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseIExtensibilityElement(ExtensibilityElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Extensibility Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Extensibility Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseExtensibilityElement(org.eclipse.wst.wsdl.ExtensibilityElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Unknown Extensibility Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Unknown Extensibility Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseUnknownExtensibilityElement(UnknownExtensibilityElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public Object defaultCase(EObject object) {
		return null;
	}

} //BPELSwitch
