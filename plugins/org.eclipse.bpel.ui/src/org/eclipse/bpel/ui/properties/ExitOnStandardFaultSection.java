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
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.util.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * ExitOnStandardFaultSection provides viewing and editing of the
 * exitOnStandardFault attribute of Process elements.
 */

@SuppressWarnings({ "boxing", "nls" })

public class ExitOnStandardFaultSection extends BPELPropertySection {

	static String V = "value";
	
	protected Button yesRadio, noRadio ;
	
	
	@Override
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				@Override
				public void notify(Notification n) {
					updateWidgets();
				}
			},
		};
	}
	

	
	protected void createWidgets(Composite composite) {
		FlatFormData data;
		
		Label suppressLabel = fWidgetFactory.createLabel(composite, Messages.ExitOnStandardFault_1); 
		
		yesRadio = fWidgetFactory.createButton(composite, Messages.ExitOnStandardFault_Yes_2, SWT.RADIO); 
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(suppressLabel, STANDARD_LABEL_WIDTH_LRG));
		data.top = new FlatFormAttachment(0, 0);
		yesRadio.setLayoutData(data);	
		yesRadio.setData(V, Boolean.TRUE);

		noRadio = fWidgetFactory.createButton(composite, Messages.ExitOnStandardFault_No_3, SWT.RADIO); 
		data = new FlatFormData();
		data.left = new FlatFormAttachment(yesRadio, IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0, 0);
		noRadio.setLayoutData(data);
		noRadio.setData(V, Boolean.FALSE);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(yesRadio, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(yesRadio, 0, SWT.CENTER);
		suppressLabel.setLayoutData(data);

		SelectionListener selectionListener = new SelectionListener() {
			public void widgetSelected (SelectionEvent e) {				
				Boolean value = (Boolean) e.widget.getData(V);
				Command cmd = new SetCommand(getInput(),value,BPELPackage.eINSTANCE.getProcess_ExitOnStandardFault());
			 	getCommandFramework().execute(wrapInShowContextCommand(cmd));					
			}
			public void widgetDefaultSelected (SelectionEvent e) { 
				widgetSelected(e); 
			}
		};
		
		yesRadio.addSelectionListener(selectionListener);
		noRadio.addSelectionListener(selectionListener);		
	}
	
	
	@Override
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		createWidgets(composite);
	}

	

	protected void updateWidgets()  {
		
		Process process = getInput();
		Assert.isNotNull(process);
		
		boolean bValue = process.getExitOnStandardFault();
		yesRadio.setSelection( bValue );
		noRadio.setSelection( ! bValue );
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		updateWidgets();
	}
	
}
