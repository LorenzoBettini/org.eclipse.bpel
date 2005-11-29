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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;


public class BPELPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Button useAnimation;
	
	protected Control createContents(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);	
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		result.setLayout(layout);
		result.setLayoutData(new GridData(GridData.FILL_BOTH));

		useAnimation = new Button(result, SWT.CHECK);
		useAnimation.setText(Messages.BPELPreferencePage_0); 
		useAnimation.setToolTipText(Messages.BPELPreferencePage_1); 

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
	}

	/**
	 * Stores the values of the controls back to the preference store.
	 */
	private void storeValues() {
		BPELUIPlugin.getPlugin().getPreferenceStore().setValue(IBPELUIConstants.PREF_USE_ANIMATION, useAnimation.getSelection());
		BPELTerms.getDefault().savePluginPreferences();
	}
}
