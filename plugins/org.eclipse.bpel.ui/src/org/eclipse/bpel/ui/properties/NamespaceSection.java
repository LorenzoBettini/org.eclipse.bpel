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
import org.eclipse.bpel.common.ui.details.IOngoingChange;
import org.eclipse.bpel.common.ui.details.widgets.StatusLabel2;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.commands.SetTargetNamespaceCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.bpel.ui.util.NamespaceUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.commands.Command;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;


public class NamespaceSection extends BPELPropertySection {

	protected Text namespaceText;
	protected StatusLabel2 statusLabel;
	protected ChangeTracker namespaceTracker;
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (n.getFeatureID(Process.class) == BPELPackage.PROCESS__TARGET_NAMESPACE) {
						updateNamespaceWidgets();
					}
				}
			},
		};
	}

	protected void createNamespaceWidgets(Composite composite) {
		FlatFormData data;

		Label namespaceLabel = wf.createLabel(composite, Messages.NamespaceSection_Target_namespace_1); 
	
		namespaceText = wf.createText(composite, ""); //$NON-NLS-1$
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(namespaceLabel, STANDARD_LABEL_WIDTH_AVG));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		namespaceText.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(namespaceText, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(namespaceText, 0, SWT.CENTER);
		namespaceLabel.setLayoutData(data);
	}
	
	protected void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			public String getLabel() {
				return IBPELUIConstants.CMD_SELECT_TARGETNAMESPACE;
			}
			public Command createApplyCommand() {
				String s = namespaceText.getText();
				if ("".equals(s)) { //$NON-NLS-1$
					s = null;
				} else {
					s = NamespaceUtils.convertNamespaceToUri(s);
				}
				//IStatus status = ValidationHelper.validateTargetNamespace(s);
				IStatus status = Status.OK_STATUS;
				if (status.isOK()) {
					return wrapInShowContextCommand(new SetTargetNamespaceCommand(
						getInput(), s)); //$NON-NLS-1$
				}

				MessageBox dialog = new MessageBox(namespaceText.getShell(), SWT.ICON_ERROR | SWT.OK);
				dialog.setText(Messages.ServerDetails_TargetNamespaceError); 
				ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(getInput(), ILabeledElement.class);
				String message = NLS.bind(Messages.ServerDetails_TargetNamespaceErrorMessage, (new Object[] {labeledElement.getTypeLabel(getInput()), labeledElement.getLabel(getInput())}));
				dialog.setMessage(message);
				dialog.open();
				return null;
			}
			public void restoreOldState() {
				updateNamespaceWidgets();
			}
		};
		namespaceTracker = new ChangeTracker(namespaceText, change, getCommandFramework());
	}
	
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		createNamespaceWidgets(composite);
		createChangeTrackers();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			composite, IHelpContextIds.PROPERTY_PAGE_NAME);
	}

	protected void updateNamespaceWidgets()  {
		namespaceTracker.stopTracking();
		try {
			String objValue = ((Process)getInput()).getTargetNamespace();
			if (objValue == null) {
				objValue = ""; //$NON-NLS-1$
			} else {
				objValue = NamespaceUtils.convertUriToNamespace(objValue);
			}
			namespaceText.setText(objValue);
		} finally {
			namespaceTracker.startTracking();
		}
	}
	
	public void refresh() {
		super.refresh();
		updateNamespaceWidgets();
	}

	public Object getUserContext() {
		return null;
	}
	
	public void restoreUserContext(Object userContext) {
		namespaceText.setFocus();
	}
}
