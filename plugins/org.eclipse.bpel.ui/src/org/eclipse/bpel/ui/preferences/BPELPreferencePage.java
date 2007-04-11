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
package org.eclipse.bpel.ui.preferences;


import java.io.File;
import java.net.URI;
import java.net.MalformedURLException;

import org.eclipse.bpel.model.terms.BPELTerms;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;


public class BPELPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button useAnimation;
	private Text wsilURL;
	
	protected Control createContents(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);	
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 10;
		result.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		result.setLayoutData(data);

		useAnimation = new Button(result, SWT.CHECK);
		useAnimation.setText(Messages.BPELPreferencePage_0); 
		useAnimation.setToolTipText(Messages.BPELPreferencePage_1); 
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 3;
		useAnimation.setLayoutData(data);
		
		// wsil directory
		Label wsilLabel = new Label(result, SWT.NONE);
		wsilLabel.setText(Messages.BPELPreferencePage_2);
		wsilLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		
		wsilURL = new Text(result, SWT.BORDER);
		wsilURL.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Button browse = new Button(result, SWT.NONE);
		browse.setText(Messages.BPELPreferencePage_3);
		browse.setLayoutData(new GridData(SWT.RIGHT));
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
				String fileName = fd.open();
				if ((fileName != null) && (fileName.length() > 0)) {
					// parse to file url
					File file = new File(fileName);
					URI uri = file.toURI();
					try {
						wsilURL.setText(uri.toURL().toString());
					}
					catch (MalformedURLException ex) {
						// do nothing
					}
				}
			}
		}
		);

		initializeValues();
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parent, IHelpContextIds.PREFERENCES_PAGE);
		
		return result;
	}

	public void init(IWorkbench workbench) {
	}
	
	protected void performDefaults() {
		super.performDefaults();
		initializeDefaults();
	}

	public boolean performOk() {
		storeValues();
		return true;
	}

	protected void performApply() {
		performOk();
	}
	
	/**
	 * Initializes states of the controls using default values in the preference store.
	 */
	private void initializeDefaults() {
		useAnimation.setSelection(BPELUIPlugin.getPlugin().getPreferenceStore().getDefaultBoolean(IBPELUIConstants.PREF_USE_ANIMATION));
	}

	/**
	 * Initializes states of the controls from the preference store.
	 */
	private void initializeValues() {
		useAnimation.setSelection(BPELUIPlugin.getPlugin().getPreferenceStore().getBoolean(IBPELUIConstants.PREF_USE_ANIMATION));
		wsilURL.setText(BPELUIPlugin.getPlugin().getPreferenceStore().getString(IBPELUIConstants.PREF_WSIL_URL));
	}

	/**
	 * Stores the values of the controls back to the preference store.
	 */
	private void storeValues() {
		BPELUIPlugin.getPlugin().getPreferenceStore().setValue(IBPELUIConstants.PREF_USE_ANIMATION, useAnimation.getSelection());
		BPELUIPlugin.getPlugin().getPreferenceStore().setValue(IBPELUIConstants.PREF_WSIL_URL, wsilURL.getText());
		BPELTerms.getDefault().savePluginPreferences();
	}
}
