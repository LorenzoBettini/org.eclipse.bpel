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

import org.eclipse.bpel.common.ui.details.ChangeHelper;
import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.details.viewers.CComboViewer;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.EndpointReferenceRole;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.PartnerLink;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkContentProvider;
import org.eclipse.bpel.ui.details.providers.RoleContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;


/**
 * An AssignCategory allowing the selection of a Partner.  If the category is used
 * for a From (not for a To), it also provides a combo for choosing which Role of
 * the Partner is referenced (myRole or partnerRole).
 */
public class PartnerRoleAssignCategory extends AssignCategoryBase {

	protected static final int PARTNER_CONTEXT = 0;
	protected static final int ROLE_CONTEXT = 1;
	
	protected int lastChangeContext = -1;

	Table partnerTable;
	TableViewer partnerViewer;
	ChangeHelper partnerChangeHelper, roleChangeHelper;
	
	CCombo roleCombo;
	CComboViewer roleViewer;
	
	protected PartnerRoleAssignCategory(boolean isFrom, BPELPropertySection ownerSection) {
		super(isFrom, ownerSection);
	}

	public String getName() { return Messages.PartnerRoleAssignCategory_Partner_Reference_1; } 

	protected void createClient2(Composite parent) {
		FlatFormData data; 
		
		if (fIsFrom)  {
			roleChangeHelper = new ChangeHelper(getCommandFramework()) {
				public String getLabel() {
					return Messages.PartnerRoleAssignCategory_Role_Change_2; 
				}
				public Command createApplyCommand() {
					lastChangeContext = ROLE_CONTEXT;
					return wrapInShowContextCommand(newStoreModelCommand());
				}
				public void restoreOldState() {
					updateCategoryWidgets();
				}
			};

			Label roleLabel = wf.createLabel(parent, Messages.PartnerRoleAssignCategory_Role__3); 
			roleCombo = wf.createCCombo(parent);
			
			data = new FlatFormData();
			data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(roleLabel, STANDARD_LABEL_WIDTH_SM));
			data.right = new FlatFormAttachment(100, 0);
			data.bottom = new FlatFormAttachment(100, 0);
			roleCombo.setLayoutData(data);
		
			data = new FlatFormData();
			data.left = new FlatFormAttachment(0, 0);
			data.right = new FlatFormAttachment(roleCombo, -IDetailsAreaConstants.HSPACE);
			data.top = new FlatFormAttachment(roleCombo, 0, SWT.CENTER);
			roleLabel.setLayoutData(data);

			roleViewer = new CComboViewer(roleCombo);		
			roleViewer.setContentProvider(new RoleContentProvider());
			roleViewer.setLabelProvider(new ModelLabelProvider());
			// TODO: does this need a sorter?
			
			roleChangeHelper.startListeningTo(roleCombo);
		}
		
		partnerChangeHelper = new ChangeHelper(getCommandFramework()) {
			public String getLabel() {
				return Messages.PartnerRoleAssignCategory_Partner_Change_4; 
			}
			public Command createApplyCommand() {
				lastChangeContext = PARTNER_CONTEXT;
				return wrapInShowContextCommand(newStoreModelCommand());
			}
			public void restoreOldState() {
				updateCategoryWidgets();
			}
		};

		partnerTable = wf.createTable(parent, SWT.NONE);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0); 
		data.top = new FlatFormAttachment(0, 0); 
		data.right = new FlatFormAttachment(100, 0); 
		if (fIsFrom) {
			data.bottom = new FlatFormAttachment(roleCombo, -IDetailsAreaConstants.VSPACE);
		} else {
			data.bottom = new FlatFormAttachment(100, 0);
		}
			 
//		data.borderType = IBorderConstants.BORDER_1P1_BLACK;
		partnerTable.setLayoutData(data);
		
		partnerViewer = new TableViewer(partnerTable);
		partnerViewer.setContentProvider(new PartnerLinkContentProvider());
		partnerViewer.setLabelProvider(new ModelLabelProvider());
		//partnerViewer.setSorter(ModelViewerSorter.getInstance());
		partnerViewer.setInput(getProcess());

		partnerChangeHelper.startListeningTo(partnerTable);

		partnerTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection sel = (IStructuredSelection)partnerViewer.getSelection();
				PartnerLink partnerLink = (PartnerLink)sel.getFirstElement();
				if (roleViewer != null)  roleViewer.setInput(partnerLink);
				if (!partnerChangeHelper.isNonUserChange()) {
					if (roleCombo != null) {
						roleChangeHelper.startNonUserChange();
						try {
							roleCombo.select(0);
						} finally {
							roleChangeHelper.finishNonUserChange();
						}
					}
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
	}	

	public boolean isCategoryForModel(To toOrFrom) {
		if (toOrFrom == null)  return false;
		if (toOrFrom.getPartnerLink() != null)  return true;
		return false;
	}
	protected void loadToOrFrom(To toOrFrom) {
		if (toOrFrom == null)  return;

		PartnerLink selPartnerLink = toOrFrom.getPartnerLink();
		boolean isMyRole = false;
		if (fIsFrom)  {
			EndpointReferenceRole reference = ((From)toOrFrom).getEndpointReference();
			if (reference != null) {
				int roleMarker = reference.getValue();
				if (roleMarker == EndpointReferenceRole.MY_ROLE)  isMyRole = true;
			}
		}

		partnerChangeHelper.startNonUserChange();
		try {
			if (selPartnerLink == null)  {
				partnerViewer.setSelection(StructuredSelection.EMPTY, false);
				if (roleViewer != null) {
					roleChangeHelper.startNonUserChange();
					try {
						roleViewer.setInput(null);
					} finally {
						roleChangeHelper.finishNonUserChange();
					}
				} 
			} else {
				partnerViewer.setSelection(new StructuredSelection(selPartnerLink), true);
				if (roleViewer != null) {
					roleChangeHelper.startNonUserChange();
					try {
						roleViewer.setInput(selPartnerLink); 
						Role role = isMyRole ? selPartnerLink.getMyRole() :
							selPartnerLink.getPartnerRole();
						if (role != null) {
							roleViewer.setSelection(new StructuredSelection(role), true);
						} else {
							roleViewer.setSelection(StructuredSelection.EMPTY);
						}
					} finally {
						roleChangeHelper.finishNonUserChange();
					}
				}
			}
		} finally {
			partnerChangeHelper.finishNonUserChange();
		}	
	}

	protected void storeToOrFrom(To toOrFrom) {
		IStructuredSelection sel = (IStructuredSelection)partnerViewer.getSelection();
		PartnerLink partnerLink = (PartnerLink)sel.getFirstElement();
		toOrFrom.setPartnerLink(partnerLink);
		
		if (fIsFrom) {
			Object role = ((IStructuredSelection)roleViewer.getSelection()).getFirstElement();
			if (role != null) {
				if (role.equals(partnerLink.getMyRole())) {
					((From)toOrFrom).setEndpointReference(EndpointReferenceRole.MY_ROLE_LITERAL);
				} else if (role.equals(partnerLink.getPartnerRole())) {
					((From)toOrFrom).setEndpointReference(EndpointReferenceRole.PARTNER_ROLE_LITERAL);
				}
			}
		}
	}

	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	public void restoreUserContext(Object userContext) {
		int i = ((Integer)userContext).intValue();
		switch (i) {
		case PARTNER_CONTEXT: partnerTable.setFocus(); return;
		case ROLE_CONTEXT: roleCombo.setFocus(); return;
		}
		throw new IllegalStateException();
	}
}
