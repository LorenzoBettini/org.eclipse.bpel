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

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetSuppressJoinFailureCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.util.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * JoinFailureDetails provides viewing and editing of the
 * suppressJoinFailure attribute of Process and Activity elements.
 */
public class JoinFailureSection extends BPELPropertySection {

	protected Label suppressDesc;
	protected Button yesRadio, noRadio, sameAsParentRadio;
	protected ChangeTracker joinFailureTracker;
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (!isCreated) {
						// HACK: we've been removed already, but the removal occurred
						// during another notification, which is still sent to us.
						// TODO: where else might this late notification occur?
						// TODO: is there a generic workaround which isn't too costly?
						return;
					}
					if (ModelHelper.isSuppressJoinFailureAffected(n.getNotifier(), n)) {
						updateSuppressJoinFailureWidgets();
					}
					if (n.getNotifier().equals(getInput().eContainer()) &&
						getInput().eContainmentFeature().equals(n.getFeature())) {
						// The model object was reparented, so the meaning of our default
						// value may have changed.  Reset our adapters and update the widget.
						removeAllAdapters(); 
						addAllAdapters();
						updateSuppressJoinFailureWidgets();
					}
				}
			},
		};
	}

	protected void addAllAdapters() {
		super.addAllAdapters();
		// Also add adapter[0] to each object in our eContainer() chain which
		// supports it.
		// This is to cover the case where a suppressJoinFailure change in an
		// ancestor changes the meaning of our default setting.
		for (EObject p = getInput().eContainer(); p != null; p = p.eContainer()) {
			if (ModelHelper.supportsJoinFailure(p))  adapters[0].addToObject(p);			
		}
	}

	int suppressJoinFailureIndex(Boolean b) {
		return (b == null)? 2 : (b.booleanValue()? 0 : 1);
	}
	
	protected boolean inheritSuppressed() {
		for (EObject p = getInput().eContainer(); p != null; p = p.eContainer()) {
			if (!ModelHelper.supportsJoinFailure(p))  continue;
			Boolean b = ModelHelper.getSuppressJoinFailure(p);
			if (b != null)  return b.booleanValue();
		}
		return false;
	}

	final Boolean[] bvals = { Boolean.TRUE, Boolean.FALSE, null };
	//final String[] items = {"Yes","No","Same as Parent"};
	
	protected int getSelectedIndex() {
		return yesRadio.getSelection()? 0 : (noRadio.getSelection()? 1 : 2);
	}
	
	protected void createSuppressJoinFailureWidgets(Composite composite) {
		FlatFormData data;
		Label suppressLabel = fWidgetFactory.createLabel(composite, Messages.JoinFailureDetails_Suppress_Join_Failure__1); 
		
		yesRadio = fWidgetFactory.createButton(composite, Messages.JoinFailureDetails_Yes_2, SWT.RADIO); 
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(suppressLabel, STANDARD_LABEL_WIDTH_LRG));
		data.top = new FlatFormAttachment(0, 0);
		yesRadio.setLayoutData(data);

		noRadio = fWidgetFactory.createButton(composite, Messages.JoinFailureDetails_No_3, SWT.RADIO); 
		data = new FlatFormData();
		data.left = new FlatFormAttachment(yesRadio, IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0, 0);
		noRadio.setLayoutData(data);

		sameAsParentRadio = fWidgetFactory.createButton(composite, Messages.JoinFailureDetails_Use_Same_Value_as_Parent_4, SWT.RADIO); 
		data = new FlatFormData();
		data.left = new FlatFormAttachment(noRadio, IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0, 0);
		sameAsParentRadio.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(yesRadio, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(yesRadio, 0, SWT.CENTER);
		suppressLabel.setLayoutData(data);

		SelectionListener selectionListener = new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Boolean value = bvals[getSelectedIndex()];
			 	if ((getInput() instanceof Process) && (Boolean.FALSE.equals(value))) {
			 		value = null;
			 	}
			 	getCommandFramework().execute(wrapInShowContextCommand(
					new SetSuppressJoinFailureCommand(getInput(),value)));
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		};
		yesRadio.addSelectionListener(selectionListener);
		noRadio.addSelectionListener(selectionListener);
		sameAsParentRadio.addSelectionListener(selectionListener);
	}
	
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		createSuppressJoinFailureWidgets(composite);
	}

	protected void updateSuppressJoinFailureWidgets()  {
		Assert.isNotNull(getInput());

		//TODO: need boolean flag here? joinFailureChangeHelper.startNonUserChange();
		try {
			String newDefault = inheritSuppressed()?
				Messages.JoinFailureDetails_Use_Same_Value_as_Parent__Yes__5 : 
				Messages.JoinFailureDetails_Use_Same_Value_as_Parent__No__6; 
			if (!sameAsParentRadio.getText().equals(newDefault)) {
				sameAsParentRadio.setText(newDefault);
				sameAsParentRadio.getParent().layout(true);
			}
			int index = suppressJoinFailureIndex(ModelHelper.getSuppressJoinFailure(getInput()));
			if ((index==2) && (getInput() instanceof Process))  index = 1;
			yesRadio.setSelection(index==0);
			noRadio.setSelection(index==1);
			sameAsParentRadio.setSelection(index==2);
		} finally {
			//TODO: need boolean flag here? joinFailureChangeHelper.finishNonUserChange();
		}
	}

	public void refresh() {
		super.refresh();
		updateSuppressJoinFailureWidgets();
		doChildLayout();
	}
	
	protected void doChildLayout(){
		if(getInput() instanceof Process){
			sameAsParentRadio.setVisible(false);
		}
		else{
			sameAsParentRadio.setVisible(true);
		}
	}

	public Object getUserContext() {
		return null;
	}
	public void restoreUserContext(Object userContext) {
		switch (getSelectedIndex()) {
		case 0: yesRadio.setFocus(); break;
		case 1: noRadio.setFocus(); break;
		case 2: sameAsParentRadio.setFocus(); break;
		}
	}
}
