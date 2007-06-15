/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.properties;

import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Validate;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.AddVariableToValidateCommand;
import org.eclipse.bpel.ui.commands.RemoveValidateVariableCommand;
import org.eclipse.bpel.ui.details.providers.AbstractContentProvider;
import org.eclipse.bpel.ui.details.providers.ColumnTableProvider;
import org.eclipse.bpel.ui.dialogs.VariableSelectorDialog;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.bpel.ui.util.TableCursor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;


/**
 * Details section for the variables of a Validate.
 */
public class ValidateVariablesSection extends BPELPropertySection {
	
	protected Table variablesTable;
	protected TableViewer variablesViewer;
	protected ColumnTableProvider tableProvider;
	protected TableCursor tableCursor = null;

	protected Button addButton;
	protected Button removeButton;
	
	public class VariableColumn extends ColumnTableProvider.Column implements ILabelProvider{
		public String getHeaderText() { return Messages.ValidateXML_Details_1; } 
		public String getProperty() { return "name"; } //$NON-NLS-1$
		public int getInitialWeight() { return 100; }
		public String getText(Object element) {
			// TODO Auto-generated method stub
			return ((Variable)element).getName();
		}		
		
	}


	public boolean shouldUseExtraSpace() { return true; }

	protected boolean isVariablesListAffected(Notification n) {		 
		if (n.getNotifier() instanceof Validate) {
			return (n.getFeatureID(Validate.class) == BPELPackage.VALIDATE__VARIABLES);
		}		
		return false;
	}

	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object and Correlations object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (isVariablesListAffected(n)) {
						updateVariableWidgets(null);
						refreshAdapters();
					}
				}
			},
			/* correlation(s) and correlation set(s) */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (n.getNotifier() instanceof Variable) {
						updateVariableWidgets((Variable)n.getNotifier());
						refreshAdapters();
					} 
				}
			}
		};
	}
	
	protected void addAllAdapters() {
		super.addAllAdapters();
		EList l = ModelHelper.getValidateVariables(getInput());
		if (l != null) {			
			for (Iterator it = l.iterator(); it.hasNext(); ) {
				Variable var = (Variable)it.next();
				adapters[1].addToObject(var);
			}
		}
	}

	protected void createVariablesWidgets(final Composite composite)  {
		FlatFormData data;
		
		addButton = fWidgetFactory.createButton(composite, Messages.ValidateDetails_1, SWT.PUSH); 
		removeButton = fWidgetFactory.createButton(composite, Messages.ValidateDetails_2, SWT.PUSH); 
		Label validateVariablesLabel = fWidgetFactory.createLabel(composite, Messages.ValidateDetails_3); 
		variablesTable = fWidgetFactory.createTable(composite, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		validateVariablesLabel.setLayoutData(data);
		
		data = new FlatFormData();
		data.width = BPELUtil.calculateButtonWidth(addButton, STANDARD_BUTTON_WIDTH);
		data.right = new FlatFormAttachment(removeButton, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0, 0);
		addButton.setLayoutData(data);
		
		data = new FlatFormData();
		data.width = BPELUtil.calculateButtonWidth(removeButton, STANDARD_BUTTON_WIDTH);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		removeButton.setLayoutData(data);
		removeButton.setEnabled(false);
		
		addButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Shell shell = composite.getShell();
				EObject model = getInput();
				VariableSelectorDialog dialog = new VariableSelectorDialog(shell, model, ModelHelper.getVariableType(model, ModelHelper.OUTGOING)){

					protected void computeResult() {
						Object[] results = getSelectedElements() ;
				        setResult(Arrays.asList(results));
					}
					
					
				};
				dialog.setMultipleSelection(true);
				dialog.setTitle(Messages.ValidateDetails_4); 
				if (dialog.open() == Window.OK) {					
					Object[] result = dialog.getResult();
					for (int i = 0 ; i < result.length; i++) {					
						Variable variable = (Variable)result[i];
						Command cmd = new AddVariableToValidateCommand(getInput(), variable);
						getCommandFramework().execute(wrapInShowContextCommand(cmd));					
						variablesViewer.setSelection(new StructuredSelection(variable));
						removeButton.setEnabled(true);
						if (tableCursor != null)
							tableCursor.refresh();
					}
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
		Listener removeListener = new Listener() {
			public void handleEvent(Event e) {
				if (e.type == SWT.KeyUp && e.character != SWT.DEL)  return;
				IStructuredSelection sel = (IStructuredSelection)variablesViewer.getSelection();				
				int selectionIndex = variablesTable.getSelectionIndex();
				for (Iterator i = sel.iterator(); i.hasNext();){
					Variable var = (Variable)i.next();
					if (var != null)  {
						Command cmd = new RemoveValidateVariableCommand(getInput(), var);
						getCommandFramework().execute(wrapInShowContextCommand(cmd));
					}					
				}
				// selects the element at the deleted element position
				int items = variablesTable.getItemCount();
				if (items > 0) {
					selectionIndex = (selectionIndex < items) ? selectionIndex : 0;
					variablesTable.setSelection(selectionIndex);
				}
				if (tableCursor != null)
					tableCursor.refresh();
				updateButtonEnablement();
			}
		};
		variablesTable.addListener(SWT.KeyUp, removeListener);
		
		removeButton.addListener(SWT.Selection, removeListener);
		removeButton.addListener(SWT.DefaultSelection, removeListener);

		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(addButton, IDetailsAreaConstants.VSPACE);
		data.bottom = new FlatFormAttachment(100, 0);
		variablesTable.setLayoutData(data);
				
		tableProvider = new ColumnTableProvider();
		tableProvider.add(new VariableColumn());
		
		variablesTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				removeButton.setEnabled(!variablesViewer.getSelection().isEmpty());
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
	
		variablesViewer = new TableViewer(variablesTable);
		tableProvider.createTableLayout(variablesTable);
		variablesViewer.setLabelProvider(tableProvider);
		variablesViewer.setCellModifier(tableProvider);
		variablesViewer.setContentProvider(new AbstractContentProvider(){
			public Object[] getElements(Object inputElement)  {
				try {
					EList l = ModelHelper.getValidateVariables(inputElement);
					return (l == null)? EMPTY_ARRAY : l.toArray();
				} catch (IllegalArgumentException e) {
					return EMPTY_ARRAY;
				}
			}			
		});
		// TODO: should this have a sorter?
		variablesViewer.setColumnProperties(tableProvider.getColumnProperties());
		variablesViewer.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtonEnablement();
			}
		});
		
		tableCursor = BPELUtil.createTableCursor(variablesTable, variablesViewer);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				composite, IHelpContextIds.PROPERTY_PAGE_VALIDATE_DETAILS);
	}
	
	protected void createClient(Composite parent)  {
		Composite composite = createFlatFormComposite(parent);
		createVariablesWidgets(composite);
	}

	protected void updateVariableWidgets(Variable var) {		
		Object input = getInput();		
		if (input == null)  throw new IllegalStateException();
		
		variablesViewer.setInput(getInput());
		if (var != null) {
			variablesViewer.refresh(var, true);
		} else {
			variablesViewer.refresh();
		}
		if (tableCursor != null)
			tableCursor.refresh();
	}
	
	public void refresh() {
		super.refresh();
		updateVariableWidgets(null);
	}

	public Object getUserContext() {
		return ((StructuredSelection)variablesViewer.getSelection()).getFirstElement();
	}
	public void restoreUserContext(Object userContext) {
		variablesTable.setFocus();
		if (userContext != null) {
			variablesViewer.setSelection(new StructuredSelection(userContext));
		}
	}
	
	protected void basicSetInput(EObject newInput) {
		super.basicSetInput(newInput);
		variablesViewer.setCellEditors(tableProvider.createCellEditors(variablesTable));
	}
	

	protected void updateButtonEnablement(){
		boolean hasSelection = !variablesViewer.getSelection().isEmpty();
		removeButton.setEnabled(hasSelection);
	}	
}
