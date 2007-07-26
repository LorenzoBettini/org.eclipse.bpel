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
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetExpressionLanguageCommand;
import org.eclipse.bpel.ui.commands.SetQueryLanguageCommand;
import org.eclipse.bpel.ui.commands.util.ModelAutoUndoRecorder;
import org.eclipse.bpel.ui.details.providers.ExpressionEditorDescriptorContentProvider;
import org.eclipse.bpel.ui.details.providers.ExpressionEditorDescriptorLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ExpressionEditorDescriptor;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;


/**
 * Details section for attributes of a Process
 */
public class AttributesSection extends BPELPropertySection {

	protected static final int EXPRESSION_COMBO_CONTEXT = 0; 
	protected static final int QUERY_COMBO_CONTEXT = 1;
	
	protected int lastChangeContext = -1;
	
	protected Process process;
	protected CCombo expressionLanguageCCombo;
	protected CCombo queryLanguageCCombo;
	
	protected CComboViewer expressionLanguageViewer;
	protected CComboViewer queryLanguageViewer;
	protected ChangeHelper expressionChangeHelper;
	protected ChangeHelper queryChangeHelper;

	@Override
	protected void basicSetInput(EObject input) {
		super.basicSetInput(input);
		if (input == null) {
			process = null;
		} else {
			process = (Process) input;
		}
	}

	protected boolean isExpressionLanguageAffected(Notification n) {
		return (n.getFeatureID(Process.class) == BPELPackage.PROCESS__EXPRESSION_LANGUAGE);
	}
	
	protected boolean isQueryLanguageAffected(Notification n) {
			return (n.getFeatureID(Process.class) == BPELPackage.PROCESS__QUERY_LANGUAGE);
	}
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (isExpressionLanguageAffected(n))  updateExpressionLanguageWidgets();
					if (isQueryLanguageAffected(n)) updateQueryLanguageWidgets();
				}
			}, };
	}

	protected void createChangeTrackers() {
		expressionChangeHelper = new ChangeHelper(getCommandFramework()) {
			public String getLabel() {
				return IBPELUIConstants.CMD_EDIT_EXPRESSIONLANGUAGE;
			}
			public Command createApplyCommand() {
				String value = expressionLanguageCCombo.getText();
				boolean found = false;
				int foundIndex = 0;
				if (value != null) {
					// Check if the text matches one of the combo items!
					String[] items = expressionLanguageCCombo.getItems();
					for (int i = 0; !found && i<items.length; i++) {
						if (value.equals(items[i])) { found = true; foundIndex = i; }
					}
				}
				String language = value;
				if (found) {
					IStructuredSelection selection = (IStructuredSelection) expressionLanguageViewer.getSelection();
					Object firstElement = selection.getFirstElement();
					if (firstElement == null) {
						firstElement = expressionLanguageViewer.getElementAt(foundIndex);
					}
					if (firstElement instanceof ExpressionEditorDescriptor) {
						ExpressionEditorDescriptor descriptor = (ExpressionEditorDescriptor)firstElement;
						if (descriptor != null) {
							language = descriptor.getExpressionLanguage();
						}
					} else if (firstElement instanceof String) {
						language = (String)firstElement;
					}
				}
				lastChangeContext = EXPRESSION_COMBO_CONTEXT;
				return wrapInShowContextCommand(new SetExpressionLanguageCommand(
					getInput(), "".equals(language) ? null : language) { //$NON-NLS-1$
					protected ModelAutoUndoRecorder getRecorder() {
						return getBPELEditor().getModelAutoUndoRecorder();
					}
				}); //$NON-NLS-1$
			}
			public void restoreOldState() {
				updateExpressionLanguageWidgets();
			}
		};
		expressionChangeHelper.startListeningTo(expressionLanguageCCombo);
		expressionChangeHelper.startListeningForEnter(expressionLanguageCCombo);

		queryChangeHelper = new ChangeHelper(getCommandFramework()) {
			public String getLabel() {
				return IBPELUIConstants.CMD_EDIT_QUERYLANGUAGE;
			}
			public Command createApplyCommand() {
				String value = queryLanguageCCombo.getText();
				boolean found = false;
				int foundIndex = 0;
				if (value != null) {
					// Check if the text matches one of the combo items!
					String[] items = queryLanguageCCombo.getItems();
					for (int i = 0; !found && i<items.length; i++) {
						if (value.equals(items[i])) { found = true; foundIndex = i; }
					}
				}
				String language = value;
				if (found) {
					IStructuredSelection selection = (IStructuredSelection) queryLanguageViewer.getSelection();
					Object firstElement = selection.getFirstElement();
					if (firstElement == null) {
						firstElement = queryLanguageViewer.getElementAt(foundIndex);
					}
					if (firstElement instanceof ExpressionEditorDescriptor) {
						ExpressionEditorDescriptor descriptor = (ExpressionEditorDescriptor)firstElement;
						if (descriptor != null) {
							language = descriptor.getExpressionLanguage();
						}
					} else if (firstElement instanceof String) {
						language = (String)firstElement;
					}
				}
				lastChangeContext = QUERY_COMBO_CONTEXT;
				return wrapInShowContextCommand(new SetQueryLanguageCommand(
					getInput(), "".equals(language) ? null : language)); //$NON-NLS-1$
			}
			public void restoreOldState() {
				updateQueryLanguageWidgets();
			}
		};
		queryChangeHelper.startListeningTo(queryLanguageCCombo);
		queryChangeHelper.startListeningForEnter(queryLanguageCCombo);
	}

	protected void createAttributesWidgets(Composite composite) {
		FlatFormData data;

		Label expressionLanguageLabel = fWidgetFactory.createLabel(composite, Messages.AttributesDetails_Expression_Language__2); 
		expressionLanguageCCombo = fWidgetFactory.createCCombo(composite, SWT.FLAT);
		
		// Expression language combo layout
		expressionLanguageViewer = new CComboViewer(expressionLanguageCCombo);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(expressionLanguageLabel, STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		expressionLanguageCCombo.setLayoutData(data);

		expressionLanguageViewer.setContentProvider(new ExpressionEditorDescriptorContentProvider());
		expressionLanguageViewer.setLabelProvider(new ExpressionEditorDescriptorLabelProvider());
		expressionLanguageViewer.setSorter(ModelViewerSorter.getInstance());

		// Expression language label layout
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(expressionLanguageCCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(expressionLanguageCCombo, 0, SWT.CENTER);
		expressionLanguageLabel.setLayoutData(data);

		expressionLanguageViewer.setInput(new Object());
		
		Label queryLanguageLabel = fWidgetFactory.createLabel(composite, Messages.AttributesDetails_Query_Language__2); 
		queryLanguageCCombo = fWidgetFactory.createCCombo(composite, SWT.FLAT);

		// Query language combo layout
		queryLanguageViewer = new CComboViewer(queryLanguageCCombo);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(queryLanguageLabel, STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(expressionLanguageCCombo, IDetailsAreaConstants.VSPACE);
		queryLanguageCCombo.setLayoutData(data);

		queryLanguageViewer.setLabelProvider(new ExpressionEditorDescriptorLabelProvider());
		queryLanguageViewer.setContentProvider(new ExpressionEditorDescriptorContentProvider());
		queryLanguageViewer.setSorter(ModelViewerSorter.getInstance());
//		queryLanguageViewer.addFilter(new ExpressionLanguageFilter(new String[0]));

		// Query language label layout
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(queryLanguageCCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(queryLanguageCCombo, 0, SWT.CENTER);
		queryLanguageLabel.setLayoutData(data);

		queryLanguageViewer.setInput(new Object());
	}

	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		createAttributesWidgets(composite);
		createChangeTrackers();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			composite, IHelpContextIds.PROPERTY_PAGE_PROCESS_DETAILS);
	}

	protected void updateExpressionLanguageWidgets() {
		if (getInput() == null)
			throw new IllegalStateException();

		expressionChangeHelper.startNonUserChange();
		try {
			if (expressionLanguageViewer != null && process != null) {
				String language = process.getExpressionLanguage();
				if (language != null) {
					ExpressionEditorDescriptor descriptor = BPELUIRegistry.getInstance().getExpressionEditorDescriptor(language);
					if (descriptor != null) {
						if (descriptor.getLabel() != null) {
							language = descriptor.getLabel();
						}
					}
				} else {
					// user-defined: leave the language itself as the value.
				}
				expressionLanguageCCombo.setText(language);
			}
		} finally {
			expressionChangeHelper.finishNonUserChange();
		}
	}
	
	protected void updateQueryLanguageWidgets() {
		Assert.isNotNull(getInput());
		queryChangeHelper.startNonUserChange();
		try {
			if (queryLanguageViewer != null && process != null) {
				String language = process.getQueryLanguage();
				if (language != null) {
					ExpressionEditorDescriptor descriptor = BPELUIRegistry.getInstance().getExpressionEditorDescriptor(language);
					if (descriptor != null) {
						if (descriptor.getLabel() != null) {
							language = descriptor.getLabel();
						}
					}
				} else {
					// user-defined: leave the language itself as the value.
				}
				queryLanguageCCombo.setText(language);
			}
		} finally {
			queryChangeHelper.finishNonUserChange();
		}
	}
		
	public void refresh() {
		super.refresh();
		updateExpressionLanguageWidgets();
		updateQueryLanguageWidgets();
	}

	public Object getUserContext() {
		return new Integer(lastChangeContext);
	}
	public void restoreUserContext(Object userContext) {
		int i = ((Integer)userContext).intValue();
		switch (i) {
			case EXPRESSION_COMBO_CONTEXT: expressionLanguageCCombo.setFocus(); return;
			case QUERY_COMBO_CONTEXT: queryLanguageCCombo.setFocus(); return;
		}
		throw new IllegalStateException();
	}
}
