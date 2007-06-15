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
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetSpecCompliantProcessCommand;
import org.eclipse.bpel.ui.uiextensionmodel.ProcessExtension;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;


/**
 * Changes a process from spec-compliant to spec + extensions.
 */
public class SpecCompliantSection extends BPELPropertySection  {

	protected Button specCompliantButton;
	protected ChangeTracker changeTracker;

	protected void createBusinessRelevantWidgets(Composite composite) {
		FlatFormData data;
		Composite parent = fWidgetFactory.createComposite(composite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		parent.setLayoutData(data);
		parent.setLayout(new FillLayout());
		specCompliantButton = fWidgetFactory.createButton(parent, Messages.SpecCompliantSection_Disable_WebSphere_Process_Server_BPEL_Extensions_1, SWT.CHECK); 
	}
	
	protected void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			public String getLabel() {
				return IBPELUIConstants.CMD_SELECT_SPECCOMPLIANT;
			}
			public Command createApplyCommand() {
				Shell shell = getBPELEditor().getSite().getShell();
				if (!specCompliantButton.getSelection()) {
					// ask the user if this is really the intention
					if (!MessageDialog.openConfirm(shell,
							Messages.SpecCompliantSection_Are_you_sure_2, 
							Messages.SpecCompliantSection_Cannot_be_reverted_warning_3
							)) {
						// the user has canceled the operation
						return null;
					}
				}
				// set the spec-compliance flag
				ProcessExtension extension = (ProcessExtension) ModelHelper.getExtension(getInput());
				getBPELEditor().getCommandStack().execute(new SetSpecCompliantProcessCommand(extension, specCompliantButton.getSelection()));
				
				// save and reopen the editor
				shell.getDisplay().asyncExec(new Runnable() {
					public void run() {
						final BPELEditor editor = getBPELEditor();
						IWorkbenchPage page = editor.getSite().getPage();
						IFile file = getBPELFile();
						IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
							public void run(IProgressMonitor monitor) throws CoreException {
								editor.doSave(null);
								editor.getSite().getPage().closeEditor(editor, false);
							}
						};
						try {
							ResourcesPlugin.getWorkspace().run(runnable, null);
							IDE.openEditor(page, file, IBPELUIConstants.BPEL_EDITOR_ID);
						} catch (CoreException e) {
							BPELUIPlugin.log(e);
						}
					}
				});
				return null;
			}
			public void restoreOldState() {
				updateSpecCompliantWidgets();
			}
		};
		changeTracker = new ChangeTracker(specCompliantButton, change, getCommandFramework());
	}
	
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		// HACK: the checkbox by itself looks cramped..give it a little extra space
		((FlatFormLayout)composite.getLayout()).marginHeight += 3;

		createBusinessRelevantWidgets(composite);
		createChangeTrackers();
	}

	protected void updateSpecCompliantWidgets() {
		changeTracker.stopTracking();
		try {
			ProcessExtension extension = (ProcessExtension) ModelHelper.getExtension(getInput());
			boolean modelValue = extension.isSpecCompliant();
			if (specCompliantButton.getSelection() != modelValue) {
				specCompliantButton.setSelection(modelValue);
			}
		} finally {
			changeTracker.startTracking();
		}
	}

	public void refresh() {
		super.refresh();
		updateSpecCompliantWidgets();
	}

}
