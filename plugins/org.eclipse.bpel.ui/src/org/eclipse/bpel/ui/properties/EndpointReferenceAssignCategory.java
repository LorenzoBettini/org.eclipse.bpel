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
package org.eclipse.bpel.ui.properties;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.ServiceRef;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * An AssignCategory for editing an EndpointReference.  This is a special type of
 * literal (though it is recognized by the deserializer, and represented in the model
 * as an EndpointReference object rather than as a literal).
 */
public class EndpointReferenceAssignCategory extends AssignCategoryBase {

	protected int lastChangeContext = -1;

	protected EndpointReferenceAssignCategory(boolean isFrom, BPELPropertySection ownerSection) {
		super(isFrom, ownerSection);
		if (!isFrom) throw new IllegalStateException();
	}

	protected class ContextModifyListener implements ModifyListener {
		int context;
		public ContextModifyListener(int context) {
			this.context = context;
		}
		public void modifyText(ModifyEvent e) {
			lastChangeContext = context;
		}
	}
	
	public String getName() { return Messages.EndpointReferenceAssignCategory_Endpoint_Reference; } 

	protected void createClient2(Composite parent) {
		// TODO: Delegate to the endpoint handler to create the widgets
	}	

	public boolean isCategoryForModel(To toOrFrom) {
		if (!(toOrFrom instanceof From))  return false;
		ServiceRef serviceRef = ((From)toOrFrom).getServiceRef(); 
		if (serviceRef != null) {
			return true;
		}
		return false;
	}
	
	protected void loadToOrFrom(To toOrFrom) {
		if (!(toOrFrom instanceof From))  return;
		// TODO: Delegate to the endpoint handler to populate the widgets
	}

	protected void storeToOrFrom(To toOrFrom) {
		From from = (From)toOrFrom;
		ServiceRef serviceRef = from.getServiceRef();  
		if (serviceRef == null) {
			serviceRef = BPELFactory.eINSTANCE.createServiceRef();
			from.setServiceRef(serviceRef);
		}
		// TODO: Delegate to the endpoint handler to store the endpoint
		// into the ServiceRef.
	}
}
