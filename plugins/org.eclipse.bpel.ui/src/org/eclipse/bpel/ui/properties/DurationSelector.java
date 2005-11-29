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
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetWidgetFactory;


/**
 * Collection of widgets for specifying a duration.
 * 
 * TODO: rewrite this to be independent of serialization format.  Move that code elsewhere.
 */
public class DurationSelector extends Composite {

	protected Composite topComposite;
	protected Composite[] composite;
	protected Label[] label;
	protected Text[] text;

	protected int lastWidgetChanged = -1;

	protected static final int YEAR=0, MONTH=1, DAY=2, HOUR=3, MINUTE=4, SECOND=5; 
	protected static final String[] labelStrings = {
		Messages.DurationSelector_Years_1, Messages.DurationSelector_Months_1, Messages.DurationSelector_Days_2, Messages.DurationSelector_Hours_3, Messages.DurationSelector_Minutes_4, Messages.DurationSelector_Seconds_5
	};
	
//	private Label timeSep, timeSep2;
//	
//	protected String dateSepText = "/";
//	protected String timeSepText = ":";
	
	private TabbedPropertySheetWidgetFactory wf;

	public DurationSelector(TabbedPropertySheetWidgetFactory factory, Composite parent, int style) {
		super(parent, style);
		this.wf = factory;
		setLayout(new FillLayout());
		createControls(this);
	}
	
	private void createControls(Composite parent){
		FlatFormData data;

		topComposite = wf.createComposite(parent);
		GridLayout gridLayout = new GridLayout(3, true);
		gridLayout.marginWidth = gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = IDetailsAreaConstants.VSPACE;
		gridLayout.horizontalSpacing = IDetailsAreaConstants.HSPACE;
		topComposite.setLayout(gridLayout);

		composite = new Composite[6];
		text = new Text[6];
		label = new Label[6];

		for (int i = 0; i<6; i++) {
			composite[i] = wf.createComposite(topComposite);
			composite[i].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			FlatFormLayout formLayout = new FlatFormLayout();
			formLayout.marginWidth = formLayout.marginHeight = 0;
			composite[i].setLayout(formLayout);
			
			// Accessibility: create each Label right before the corresponding Text.
			label[i] = wf.createLabel(composite[i], labelStrings[i], SWT.CENTER);
			text[i] = wf.createText(composite[i], "0"); //$NON-NLS-1$
		
			data = new FlatFormData();
			data.left = new FlatFormAttachment(0,0);
			data.right = new FlatFormAttachment(100,0);
			data.top = new FlatFormAttachment(0,0); 
			label[i].setLayoutData(data);

			data = new FlatFormData();
			data.left = new FlatFormAttachment(0,0);
			data.right = new FlatFormAttachment(100,0);
			data.top = new FlatFormAttachment(label[i], IDetailsAreaConstants.VSPACE);
			text[i].setLayoutData(data);
		}

//		timeSep  = wf.createLabel(composite, timeSepText, SWT.CENTER);
//		timeSep2 = wf.createLabel(composite, timeSepText, SWT.CENTER);
		
		addListeners();
		layout(true);
	}
	
	private void addListeners(){
		// TODO: if we use text widgets, we need to support committers.
		// when spinners are implemented we might still need to support committers..
		ModifyListener commonListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				for (int i = 0; i<6; i++) {
					if (e.widget == text[i])  lastWidgetChanged = i;
				}
				selectionChanged();
			}
		};
		for (int i = 0; i<6; i++)  text[i].addModifyListener(commonListener);
	}
	
	protected int numberValue(String s)  {
		int result = 0;
		try {
			result = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			// do nothing
		}
		return result;
	}

	public int[] getValues() {
		int[] result = new int[6];
		for (int i = 0; i<6; i++) {
			result[i] = numberValue(text[i].getText());
		}
		return result;
	}
	
	public boolean setValues(int[] duration) {
		// TODO: check validity?
		int last = lastWidgetChanged;
		for (int i = 0; i<6; i++)  text[i].setText(String.valueOf(duration[i]));
		lastWidgetChanged = last;
		return true;
	}
	
	void selectionChanged() {
		Event e = new Event();
		e.type = SWT.Selection;
		e.widget = this;
		e.widget.notifyListeners(e.type, e);
	}
	
	public void addSelectionListener(SelectionListener listener){
		TypedListener typedListener = new TypedListener (listener);
		addListener (SWT.Selection,typedListener);
		addListener (SWT.DefaultSelection,typedListener);
	}

	public Object getUserContext() {
		return new Integer(lastWidgetChanged);
	}
	
	public void restoreUserContext(Object userContext) {
		int i = ((Integer)userContext).intValue();
		if (i >= 0)  text[i].setFocus();
	}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		for (int i = 0; i < text.length; i++) {
			text[i].setEnabled(enabled);
		}
	}
}
