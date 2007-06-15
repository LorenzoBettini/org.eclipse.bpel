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

import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Scope;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetIsolatedCommand;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;


/**
 * ScopeImplDetails provides specifying variable access serialization
 */
public class ScopeImplSection extends BPELPropertySection {

	protected static final int ISOLATED_CONTEXT = 0;
	
	protected int lastChangeContext = -1;
	protected Button isolatedButton;
	protected ChangeTracker isolatedTracker;	
	protected Composite parentComposite;
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (isIsolatedAffected(getInput(), n))
						updateIsolatedWidgets();
				}
			},
		};
	}
	
	public static boolean isIsolatedAffected(Object context, Notification n) {
		return (n.getFeatureID(Process.class) == BPELPackage.SCOPE__ISOLATED);
	}

	protected void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			 public String getLabel() {
				 return IBPELUIConstants.CMD_SELECT_ISOLATED;
			 }
			public Command createApplyCommand() {
				lastChangeContext = ISOLATED_CONTEXT;
				return wrapInShowContextCommand(new SetIsolatedCommand(
					getInput(),	new Boolean(isolatedButton.getSelection())));
			}
			 public void restoreOldState() {
				 updateIsolatedWidgets();
			 }
		};
		isolatedTracker = new ChangeTracker(isolatedButton, change, getCommandFramework());
	}

	protected void createScopeWidgets(Composite composite) {
		FlatFormData data;
		
		 // serializable
		 data = new FlatFormData();
		 isolatedButton = fWidgetFactory.createButton(composite, Messages.VariableImplDetails_Isolated__6, SWT.CHECK); 
		 data.left = new FlatFormAttachment(0, 0);
		 isolatedButton.setLayoutData(data);
	}

	protected void createClient(Composite parent) {
		Composite composite = parentComposite = fWidgetFactory.createComposite(parent, SWT.NONE);
		FlatFormLayout layout = new FlatFormLayout();
		layout.marginWidth = layout.marginHeight = 0;
		composite.setLayout(layout);

		createScopeWidgets(composite);
		createChangeTrackers();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parentComposite, IHelpContextIds.PROPERTY_PAGE_SCOPE_IMPLEMENTATION);
	}
	
	protected void updateIsolatedWidgets() {
		isolatedTracker.stopTracking();
		try {
			boolean modelValue = Boolean.TRUE.equals(((Scope)getInput()).getIsolated());
			if (isolatedButton.getSelection() != modelValue) {
				isolatedButton.setSelection(modelValue);
			}
		} finally {
			isolatedTracker.startTracking();
		}
	}
	
	public void refresh() {
		super.refresh();
		updateIsolatedWidgets();
	}

	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	public void restoreUserContext(Object userContext) {
		int i = ((Integer)userContext).intValue();
		switch (i) {
		case ISOLATED_CONTEXT: isolatedButton.setFocus(); return;
		}
		throw new IllegalStateException();
	}
}
