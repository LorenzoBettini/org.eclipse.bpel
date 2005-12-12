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

import org.eclipse.bpel.model.util.BPELAdapterFactory;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;


public class BPELUIAdapterFactory extends BPELAdapterFactory {

	static BPELUIAdapterFactory instance;
	
	CaseAdapter caseAdapter;
	FlowAdapter flowAdapter;
	OnMessageAdapter onMessageAdapter;
	OnEventAdapter onEventAdapter;
	OnAlarmAdapter onAlarmAdapter;
	PickAdapter pickAdapter;
	ProcessAdapter processAdapter;
	SequenceAdapter sequenceAdapter;
	SwitchAdapter switchAdapter;
	WhileAdapter whileAdapter;
	AssignAdapter assignAdapter;
	CopyAdapter copyAdapter;
	VariableAdapter variableAdapter;
	EmptyAdapter emptyAdapter;
	InvokeAdapter invokeAdapter;
	LinkAdapter linkAdapter;
	PartnerLinkAdapter partnerAdapter;
	ReceiveAdapter receiveAdapter;
	ReplyAdapter replyAdapter;
	ScopeAdapter scopeAdapter;
	ExitAdapter exitAdapter;
	ThrowAdapter throwAdapter;
	WaitAdapter waitAdapter;
	CorrelationSetAdapter correlationSetAdapter;
	FaultHandlerAdapter faultHandlerAdapter;
	CompensationHandlerAdapter compensationHandlerAdapter;
	TerminationHandlerAdapter terminationHandlerAdapter;
	EventHandlerAdapter eventHandlerAdapter;
	CatchAdapter catchAdapter;
	VariablesAdapter variablesAdapter;
	PartnerLinksAdapter partnerLinksAdapter;
	CorrelationSetsAdapter correlationSetsAdapter;
	CatchAllAdapter catchAllAdapter;
	OtherwiseAdapter otherwiseAdapter;
	CompensateAdapter compensateAdapter;
	RethrowAdapter rethrowAdapter;
	CustomActivityAdapter customActivityAdapter;
	
	public static BPELUIAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIAdapterFactory();
		}
		return instance;
	}
	
	public Adapter createAssignAdapter() {
		if (assignAdapter == null) {
			assignAdapter = new AssignAdapter();	
		}		
		return assignAdapter;
	}
	public Adapter createCopyAdapter() {
		if (copyAdapter == null) {
			copyAdapter = new CopyAdapter();	
		}		
		return copyAdapter;
	}
	public Adapter createCaseAdapter() {
		if (caseAdapter == null) {
			caseAdapter = new CaseAdapter();	
		}		
		return caseAdapter;
	}
	public Adapter createCatchAdapter() {
		if (catchAdapter == null) {
			catchAdapter = new CatchAdapter();	
		}		
		return catchAdapter;
	}
	public Adapter createFaultHandlerAdapter() {
		if (faultHandlerAdapter == null) {
			faultHandlerAdapter = new FaultHandlerAdapter();	
		}		
		return faultHandlerAdapter;
	}
	public Adapter createCompensationHandlerAdapter() {
		if (compensationHandlerAdapter == null) {
			compensationHandlerAdapter = new CompensationHandlerAdapter();	
		}		
		return compensationHandlerAdapter;
	}
	public Adapter createTerminationHandlerAdapter() {
		if (terminationHandlerAdapter == null) {
			terminationHandlerAdapter = new TerminationHandlerAdapter();
		}
		return terminationHandlerAdapter;
	}
	public Adapter createEventHandlerAdapter() {
		if (eventHandlerAdapter == null) {
			eventHandlerAdapter = new EventHandlerAdapter();	
		}		
		return eventHandlerAdapter;
	}
	public Adapter createVariableAdapter() {
		if (variableAdapter == null) {
			variableAdapter = new VariableAdapter();	
		}		
		return variableAdapter;
	}
	public Adapter createEmptyAdapter() {
		if (emptyAdapter == null) {
			emptyAdapter = new EmptyAdapter();	
		}		
		return emptyAdapter;
	}
	public Adapter createFlowAdapter() {
		if (flowAdapter == null) {
			flowAdapter = new FlowAdapter();	
		}		
		return flowAdapter;
	}
	public Adapter createInvokeAdapter() {
		if (invokeAdapter == null) {
			invokeAdapter = new InvokeAdapter();	
		}		
		return invokeAdapter;
	}
	public Adapter createLinkAdapter() {
		if (linkAdapter == null) {
			linkAdapter = new LinkAdapter();	
		}		
		return linkAdapter;
	}
	public Adapter createOnAlarmAdapter() {
		if (onAlarmAdapter == null) {
			onAlarmAdapter = new OnAlarmAdapter();	
		}		
		return onAlarmAdapter;
	}
	public Adapter createOnMessageAdapter() {
		if (onMessageAdapter == null) {
			onMessageAdapter = new OnMessageAdapter();	
		}		
		return onMessageAdapter;
	}
	public Adapter createOnEventAdapter() {
		if (onEventAdapter == null) {
			onEventAdapter = new OnEventAdapter();	
		}		
		return onEventAdapter;
	}
	public Adapter createPartnerLinkAdapter() {
		if (partnerAdapter == null) {
			partnerAdapter = new PartnerLinkAdapter();	
		}		
		return partnerAdapter;
	}
	public Adapter createPickAdapter() {
		if (pickAdapter == null) {
			pickAdapter = new PickAdapter();	
		}		
		return pickAdapter;
	}
	public Adapter createProcessAdapter() {
		if (processAdapter == null) {
			processAdapter = new ProcessAdapter();	
		}		
		return processAdapter;
	}
	public Adapter createSwitchAdapter() {
		if (switchAdapter == null) {
			switchAdapter = new SwitchAdapter();	
		}		
		return switchAdapter;
	}
	public Adapter createReceiveAdapter() {
		if (receiveAdapter == null) {
			receiveAdapter = new ReceiveAdapter();	
		}		
		return receiveAdapter;
	}
	public Adapter createReplyAdapter() {
		if (replyAdapter == null) {
			replyAdapter = new ReplyAdapter();	
		}		
		return replyAdapter;
	}
	public Adapter createSequenceAdapter() {
		if (sequenceAdapter == null) {
			sequenceAdapter = new SequenceAdapter();	
		}		
		return sequenceAdapter;
	}
	public Adapter createScopeAdapter() {
		if (scopeAdapter == null) {
			scopeAdapter = new ScopeAdapter();	
		}		
		return scopeAdapter;
	}
	public Adapter createThrowAdapter() {
		if (throwAdapter == null) {
			throwAdapter = new ThrowAdapter();	
		}		
		return throwAdapter;
	}
	public Adapter createWaitAdapter() {
		if (waitAdapter == null) {
			waitAdapter = new WaitAdapter();	
		}		
		return waitAdapter;
	}
	public Adapter createWhileAdapter() {
		if (whileAdapter == null) {
			whileAdapter = new WhileAdapter();	
		}		
		return whileAdapter;
	}
	public Adapter createCorrelationSetAdapter() {
		if (correlationSetAdapter == null) {
			correlationSetAdapter = new CorrelationSetAdapter();	
		}		
		return correlationSetAdapter;
	}

	public Adapter createCorrelationSetsAdapter() {
		if (correlationSetsAdapter == null) {
			correlationSetsAdapter = new CorrelationSetsAdapter();	
		}		
		return correlationSetsAdapter;
	}

	public Adapter createPartnerLinksAdapter() {
		if (partnerLinksAdapter == null) {
			partnerLinksAdapter = new PartnerLinksAdapter();	
		}		
		return partnerLinksAdapter;
	}

	public Adapter createVariablesAdapter() {
		if (variablesAdapter == null) {
			variablesAdapter = new VariablesAdapter();	
		}		
		return variablesAdapter;
	}
	public Adapter createCatchAllAdapter() {
		if (catchAllAdapter == null) {
			catchAllAdapter = new CatchAllAdapter();
		}
		return catchAllAdapter;
	}
	public Adapter createOtherwiseAdapter() {
		if (otherwiseAdapter == null) {
			otherwiseAdapter = new OtherwiseAdapter();
		}
		return otherwiseAdapter;
	}
	public Adapter createCompensateAdapter() {
		if (compensateAdapter == null) {
			compensateAdapter = new CompensateAdapter();
		}
		return compensateAdapter;
	}
	public Adapter createCustomActivityAdapter() {
		if (customActivityAdapter == null) {
		    customActivityAdapter = new CustomActivityAdapter();
		}
		return customActivityAdapter;
	}

	public Adapter createRethrowAdapter() {
		if (rethrowAdapter == null) {
			rethrowAdapter = new RethrowAdapter();
		}
		return rethrowAdapter;
	}	
	
	public Adapter createExitAdapter() {
		if (exitAdapter == null) {
			exitAdapter = new ExitAdapter();	
		}		
		return exitAdapter;
	}

	// Anyone creating a new adapter factory needs these three methods verbatim.
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

    protected Adapter createAdapter(Notifier target, Object type) {
    	if (BPELUtil.isCustomActivity(target)) {
            // If we have a custom activity that did not provide a
            // custom adapter we create a default adapter. 
            return createCustomActivityAdapter();
    	}
        return super.createAdapter(target, type);
    }
}
