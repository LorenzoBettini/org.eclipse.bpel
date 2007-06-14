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
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createRepeatUntilAdapter()
	 */
	@Override
	public Adapter createRepeatUntilAdapter() {
		return provider.getAdapter(RepeatUntilAdapter.class);
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createAssignAdapter()
	 */
	@Override
	public Adapter createAssignAdapter() {
		return provider.getAdapter(AssignAdapter.class);		
	}
	
	/** (non-Javadoc)
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCopyAdapter()
	 */
	@Override
	public Adapter createCopyAdapter() {
		return provider.getAdapter(CopyAdapter.class);		
	}
	
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createElseIfAdapter()
	 */
	@Override
	public Adapter createElseIfAdapter() {
		return provider.getAdapter(ElseIfAdapter.class);		
	}
	
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCatchAdapter()
	 */
	@Override
	public Adapter createCatchAdapter() {
		return provider.getAdapter(CatchAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createFaultHandlerAdapter()
	 */
	@Override	
	public Adapter createFaultHandlerAdapter() {
		return provider.getAdapter(FaultHandlerAdapter.class);		
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCompensationHandlerAdapter()
	 */
	@Override
	public Adapter createCompensationHandlerAdapter() {
		return provider.getAdapter(CompensationHandlerAdapter.class);
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createTerminationHandlerAdapter()
	 */
	@Override
	public Adapter createTerminationHandlerAdapter() {
		return provider.getAdapter(TerminationHandlerAdapter.class);		
	}
	
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createEventHandlerAdapter()
	 */
	@Override
	public Adapter createEventHandlerAdapter() {
		return provider.getAdapter(EventHandlerAdapter.class);
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createVariableAdapter()
	 */
	@Override
	public Adapter createVariableAdapter() {
		return provider.getAdapter(VariableAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createEmptyAdapter()
	 */
	@Override
	public Adapter createEmptyAdapter() {
		return provider.getAdapter(EmptyAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createFlowAdapter()
	 */
	@Override
	public Adapter createFlowAdapter() {
		return provider.getAdapter(FlowAdapter.class);
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createInvokeAdapter()
	 */
	@Override
	public Adapter createInvokeAdapter() {
		return provider.getAdapter(InvokeAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createLinkAdapter()
	 */
	@Override
	public Adapter createLinkAdapter() {
		return provider.getAdapter(LinkAdapter.class);
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createOnAlarmAdapter()
	 */
	@Override
	public Adapter createOnAlarmAdapter() {
		return provider.getAdapter(OnAlarmAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createOnMessageAdapter()
	 */
	@Override
	public Adapter createOnMessageAdapter() {
		return provider.getAdapter(OnMessageAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createOnEventAdapter()
	 */
	@Override
	public Adapter createOnEventAdapter() {
		return provider.getAdapter(OnEventAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createPartnerLinkAdapter()
	 */
	@Override
	public Adapter createPartnerLinkAdapter() {
		return provider.getAdapter(PartnerLinkAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createPickAdapter()
	 */
	@Override
	public Adapter createPickAdapter() {
		return provider.getAdapter(PickAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createProcessAdapter()
	 */
	@Override
	public Adapter createProcessAdapter() {
		return provider.getAdapter(ProcessAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createIfAdapter()
	 */
	@Override
	public Adapter createIfAdapter() {
		return provider.getAdapter(IfAdapter.class);
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createReceiveAdapter()
	 */
	@Override
	public Adapter createReceiveAdapter() {
		return provider.getAdapter(ReceiveAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createReplyAdapter()
	 */
	@Override
	public Adapter createReplyAdapter() {
		return provider.getAdapter(ReplyAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createSequenceAdapter()
	 */
	@Override
	public Adapter createSequenceAdapter() {
		return provider.getAdapter(SequenceAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createScopeAdapter()
	 */
	@Override
	public Adapter createScopeAdapter() {
		return provider.getAdapter(ScopeAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createThrowAdapter()
	 */
	@Override
	public Adapter createThrowAdapter() {
		return provider.getAdapter(ThrowAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createWaitAdapter()
	 */
	@Override
	public Adapter createWaitAdapter() {
		return provider.getAdapter(WaitAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createWhileAdapter()
	 */
	@Override
	public Adapter createWhileAdapter() {
		return provider.getAdapter(WhileAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createForEachAdapter()
	 */
	@Override
	public Adapter createForEachAdapter() {
		return provider.getAdapter(ForEachAdapter.class);
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCorrelationSetAdapter()
	 */
	@Override
	public Adapter createCorrelationSetAdapter() {
		return provider.getAdapter(CorrelationSetAdapter.class);		
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCorrelationSetsAdapter()
	 */
	@Override
	public Adapter createCorrelationSetsAdapter() {
		return provider.getAdapter(CorrelationSetsAdapter.class);		
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createPartnerLinksAdapter()
	 */
	@Override
	public Adapter createPartnerLinksAdapter() {
		return provider.getAdapter(PartnerLinksAdapter.class);		
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createVariablesAdapter()
	 */
	@Override
	public Adapter createVariablesAdapter() {
		return provider.getAdapter(VariablesAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCatchAllAdapter()
	 */
	@Override
	public Adapter createCatchAllAdapter() {
		return provider.getAdapter(CatchAllAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createElseAdapter()
	 */
	@Override
	public Adapter createElseAdapter() {
		return provider.getAdapter(ElseAdapter.class);
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCompensateAdapter()
	 */
	@Override
	public Adapter createCompensateAdapter() {
		return provider.getAdapter(CompensateAdapter.class);		
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createCompensateScopeAdapter()
	 */
	@Override
	public Adapter createCompensateScopeAdapter() {
		return provider.getAdapter(CompensateScopeAdapter.class);		
	}

	/**
	 * 
	 * @return the custom activity adapter.
	 */
	public Adapter createCustomActivityAdapter() {
		return provider.getAdapter(CustomActivityAdapter.class);		
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createRethrowAdapter()
	 */
	@Override
	public Adapter createRethrowAdapter() {
		return provider.getAdapter(RethrowAdapter.class);		
	}	
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createExitAdapter()
	 */
	@Override
	public Adapter createExitAdapter() {
		return provider.getAdapter(ExitAdapter.class);		
	}
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createValidateAdapter()
	 */
	@Override
	public Adapter createValidateAdapter() {
		return provider.getAdapter(ValidateAdapter.class);		
	}
	
	
	
	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createToAdapter()
	 */	
	@Override
	public Adapter createToAdapter() {		
		return provider.getAdapter(ToAdapter.class);
	}

	/**
	 * @see org.eclipse.bpel.model.util.BPELAdapterFactory#createFromAdapter()
	 */
	@Override
	public Adapter createFromAdapter() { 
		return provider.getAdapter(FromAdapter.class);		
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
