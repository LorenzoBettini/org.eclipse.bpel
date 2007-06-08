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

import org.eclipse.bpel.model.adapters.AdapterProvider;
import org.eclipse.bpel.model.util.BPELAdapterFactory;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;




/**
 * BPELUIAdapterFactory for generating adapters.
 * 
 * We use an instance of AdapterProvider that caches singleton adapters.
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date May 23, 2007
 *
 */

public class BPELUIAdapterFactory extends BPELAdapterFactory {

	static BPELUIAdapterFactory instance;
		
	AdapterProvider provider;
	
	private BPELUIAdapterFactory () {
		provider = new AdapterProvider();
	}
	
	/**
	 * Get the instance of this factory.
	 * 
	 * @return an instance of this factory.
	 */
	
	public static BPELUIAdapterFactory getInstance() {
		if (instance == null) {
			instance = new BPELUIAdapterFactory();
		}
		return instance;
	}
	
	@Override
	public Adapter createRepeatUntilAdapter() {
		return provider.getAdapter(RepeatUntilAdapter.class);
	}

	public Adapter createAssignAdapter() {
		return provider.getAdapter(AssignAdapter.class);		
	}
	
	public Adapter createCopyAdapter() {
		return provider.getAdapter(CopyAdapter.class);		
	}
	
	public Adapter createElseIfAdapter() {
		return provider.getAdapter(ElseIfAdapter.class);		
	}
	
	public Adapter createCatchAdapter() {
		return provider.getAdapter(CatchAdapter.class);		
	}
	
	public Adapter createFaultHandlerAdapter() {
		return provider.getAdapter(FaultHandlerAdapter.class);		
	}

	public Adapter createCompensationHandlerAdapter() {
		return provider.getAdapter(CompensationHandlerAdapter.class);
	}
	
	public Adapter createTerminationHandlerAdapter() {
		return provider.getAdapter(TerminationHandlerAdapter.class);		
	}
	
	public Adapter createEventHandlerAdapter() {
		return provider.getAdapter(EventHandlerAdapter.class);
	}
	
	public Adapter createVariableAdapter() {
		return provider.getAdapter(VariableAdapter.class);		
	}
	
	public Adapter createEmptyAdapter() {
		return provider.getAdapter(EmptyAdapter.class);		
	}
	
	public Adapter createFlowAdapter() {
		return provider.getAdapter(FlowAdapter.class);
	}
	
	public Adapter createInvokeAdapter() {
		return provider.getAdapter(InvokeAdapter.class);		
	}
	
	public Adapter createLinkAdapter() {
		return provider.getAdapter(LinkAdapter.class);
	}
	
	public Adapter createOnAlarmAdapter() {
		return provider.getAdapter(OnAlarmAdapter.class);		
	}
	
	public Adapter createOnMessageAdapter() {
		return provider.getAdapter(OnMessageAdapter.class);		
	}
	
	public Adapter createOnEventAdapter() {
		return provider.getAdapter(OnEventAdapter.class);		
	}
	
	public Adapter createPartnerLinkAdapter() {
		return provider.getAdapter(PartnerLinkAdapter.class);		
	}
	
	public Adapter createPickAdapter() {
		return provider.getAdapter(PickAdapter.class);		
	}
	
	public Adapter createProcessAdapter() {
		return provider.getAdapter(ProcessAdapter.class);		
	}
	
	public Adapter createIfAdapter() {
		return provider.getAdapter(IfAdapter.class);
	}
	
	public Adapter createReceiveAdapter() {
		return provider.getAdapter(ReceiveAdapter.class);		
	}
	
	public Adapter createReplyAdapter() {
		return provider.getAdapter(ReplyAdapter.class);		
	}
	
	public Adapter createSequenceAdapter() {
		return provider.getAdapter(SequenceAdapter.class);		
	}
	
	public Adapter createScopeAdapter() {
		return provider.getAdapter(ScopeAdapter.class);		
	}
	
	public Adapter createThrowAdapter() {
		return provider.getAdapter(ThrowAdapter.class);		
	}
	
	public Adapter createWaitAdapter() {
		return provider.getAdapter(WaitAdapter.class);		
	}
	
	public Adapter createWhileAdapter() {
		return provider.getAdapter(WhileAdapter.class);		
	}
	
	public Adapter createForEachAdapter() {
		return provider.getAdapter(ForEachAdapter.class);
	}
	
	public Adapter createCorrelationSetAdapter() {
		return provider.getAdapter(CorrelationSetAdapter.class);		
	}

	public Adapter createCorrelationSetsAdapter() {
		return provider.getAdapter(CorrelationSetsAdapter.class);		
	}

	public Adapter createPartnerLinksAdapter() {
		return provider.getAdapter(PartnerLinksAdapter.class);		
	}

	public Adapter createVariablesAdapter() {
		return provider.getAdapter(VariablesAdapter.class);		
	}
	
	public Adapter createCatchAllAdapter() {
		return provider.getAdapter(CatchAllAdapter.class);		
	}
	
	public Adapter createElseAdapter() {
		return provider.getAdapter(ElseAdapter.class);
	}
	
	public Adapter createCompensateAdapter() {
		return provider.getAdapter(CompensateAdapter.class);		
	}

	public Adapter createCompensateScopeAdapter() {
		return provider.getAdapter(CompensateScopeAdapter.class);		
	}

	public Adapter createCustomActivityAdapter() {
		return provider.getAdapter(CustomActivityAdapter.class);		
	}

	public Adapter createRethrowAdapter() {
		return provider.getAdapter(RethrowAdapter.class);		
	}	
	
	public Adapter createExitAdapter() {
		return provider.getAdapter(ExitAdapter.class);		
	}
	
	public Adapter createValidateAdapter() {
		return provider.getAdapter(ValidateAdapter.class);		
	}
	
	
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createExpressionAdapter()
	 */
	@Override
	public Adapter createExpressionAdapter() {		
		return provider.getAdapter(ExpressionAdapter.class);
	}
	

	/**
	 * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#adaptNew(org.eclipse.emf.common.notify.Notifier, java.lang.Object)
	 */
	@Override
	public Adapter adaptNew(Notifier target, Object type) {
		Adapter adapter = createAdapter(target, type);
		if (adapter == null) {
			return null;
		}
		associate(adapter,target);
		return adapter.isAdapterForType(type) ? adapter : null;		
	}
	
	@Override
	protected Object resolve (Object object, Object type) {
		return null;
	}
	
	
	
    @Override
	protected Adapter createAdapter(Notifier target, Object type) {
    	if (BPELUtil.isCustomActivity(target)) {
            // If we have a custom activity that did not provide a
            // custom adapter we create a default adapter. 
            return createCustomActivityAdapter();
    	}
        return super.createAdapter(target, type);
    }

	
}
