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
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.INamedElement;
import org.eclipse.bpel.ui.commands.SetNameCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.util.Assert;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;


/**
 * Details section for the "name" property (which is common to most BPEL elements).
 */
public class NameSection extends BPELPropertySection {

	protected INamedElement namedElement;
	protected Text nameText;
	protected StatusLabel2 statusLabel;
	protected ChangeTracker nameTracker;
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (namedElement.isNameAffected(getInput(), n))  updateNameWidgets();
				}
			},
		};
	}

	protected void basicSetInput(EObject input) {
		super.basicSetInput(input);
		if (input == null)  {
			namedElement = null;
		} else  {
			namedElement = (INamedElement)BPELUtil.adapt(input, INamedElement.class);
		}
	}

	protected void createNameWidgets(Composite composite) {
		FlatFormData data;

		Label nameLabel = wf.createLabel(composite, Messages.NameDetails_BPEL_Name__3); 
	
		nameText = wf.createText(composite, ""); //$NON-NLS-1$
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(nameLabel, STANDARD_LABEL_WIDTH_AVG));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		nameText.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(nameText, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(nameText, 0, SWT.CENTER);
		nameLabel.setLayoutData(data);
	}
	
	protected void createChangeTrackers() {
		IOngoingChange change = new IOngoingChange() {
			public String getLabel() {
				return IBPELUIConstants.CMD_EDIT_NAME;
			}
			public Command createApplyCommand() {
				if (validate().isOK() == false)
					return null;
				String newName = nameText.getText();
				if (newName.equals(namedElement.getName(getModel()))) {
					return null; // there is nothing to be done
				}
				if ("".equals(newName)) { //$NON-NLS-1$
					if (getInput() instanceof Process) {
						String filename = ((IFileEditorInput)getBPELEditor().getEditorInput()).getFile().getName();
						newName = filename.substring(0, filename.lastIndexOf(IBPELUIConstants.EXTENSION_DOT_BPEL)); //$NON-NLS-1$
						Process process = (Process) getInput();
						if (newName.equals(process.getName())) {
							return null; // there is nothing to be done
						}
					} else {
						newName = null;
					}
				}
				return wrapInShowContextCommand(new SetNameCommand(getInput(), newName));
			}
			public void restoreOldState() {
				updateNameWidgets();
			}
		};
		nameTracker = new ChangeTracker(nameText, change, getCommandFramework());
	}
	
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		createNameWidgets(composite);
		createChangeTrackers();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			composite, IHelpContextIds.PROPERTY_PAGE_NAME);
	}

	protected void updateNameWidgets()  {
		Assert.isNotNull(getInput());
		String name = namedElement.getName(getInput());
		if (name == null)  name = ""; //$NON-NLS-1$
		if (!name.equals(nameText.getText())) {
			nameTracker.stopTracking();
			try {
				nameText.setText(name);
			} finally {
				nameTracker.startTracking();
			}
		}
	}
	
	public void refresh() {
		super.refresh();
		updateNameWidgets();
	}

	protected IStatus validate() {
		//IStatus status = ValidationHelper.validateXML_NCName(nameText.getText());
		//if (status.isOK())
			return validate(getInput());
		//else
		//   return status;
	}

	protected IStatus validate(Object currentElement) {
		if (currentElement instanceof EObject) { // i.e. PartnerLink
			if (((EObject) currentElement).eContainer() != null) { // i.e. PartnerLinks
				EList el = ((EObject) currentElement).eContainer().eContents();

                if (el != null) {
                    for (int i = 0; i < el.size(); i++) {
                        Object loopElement = el.get(i);

                        if (loopElement instanceof EObject) {
                            EClass eClass = ((EObject) loopElement).eClass();
                            EList eAttributes = eClass.getEAllAttributes();

                            EAttribute eAttribute = getNAMEAttribute(eAttributes);
                    
                            if (eAttribute != null) {
                                Object name = ((EObject) loopElement).eGet(eAttribute);

                                if (name != null) {
                                    // is there another element with the same name?
                                    if (name.equals(nameText.getText()) && !loopElement.equals(currentElement)) {   
                                        String message = NLS.bind(Messages.NameDetails_RenameErrorMessage, (new Object[] {nameText.getText()})); 
                                        return new Status( IStatus.ERROR, BPELUIPlugin.PLUGIN_ID, IStatus.ERROR, message, null);
                                    } 
                                }
                            }
                        }
                    }
                }
			}
		}
        return new Status( IStatus.OK, BPELUIPlugin.PLUGIN_ID, IStatus.OK, "Hi There", null); //$NON-NLS-1$
	} 
    
	protected EAttribute getNAMEAttribute(EList list) {
		if (list != null) {
			for (int i = 0; i < list.size();i++) {
				EObject eo = (EObject)list.get(i);
				
				if (eo instanceof EAttribute) {
					EAttribute ea = (EAttribute)eo;
					
					if (ea.getName().equalsIgnoreCase("name")) { //$NON-NLS-1$
						return (EAttribute)eo;
					}
				}
			}
		}
		return null;					
	}
	
	public Object getUserContext() {
		return null;
	}
	
	public void restoreUserContext(Object userContext) {
		nameText.setFocus();
	}
}
