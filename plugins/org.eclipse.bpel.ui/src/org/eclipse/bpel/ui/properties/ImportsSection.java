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
import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.ColumnTableProvider;
import org.eclipse.bpel.ui.details.providers.ImportContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.bpel.ui.util.NamespaceUtils;
import org.eclipse.bpel.ui.util.TableCursor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.PlatformUI;


/**
 * Edits the import statements of the BPEL file.
 */
public class ImportsSection extends BPELPropertySection {

	protected Composite parentComposite;
	protected Composite activityComposite;	
	protected Label importLabel;
	
	protected Table importTable;
	protected TableViewer importViewer;
	protected ColumnTableProvider tableProvider;
	protected TableCursor tableCursor = null;	
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
				new MultiObjectAdapter(){
					public void notify(Notification n) {
						refresh();
					}
				},
		};
	}	

	protected void createImportWidgets(Composite parent) {	
		FlatFormData data;

		importLabel = wf.createLabel(parent, Messages.ImportDetails_Imports_20); 
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		importLabel.setLayoutData(data);
		
		//create table
		importTable = wf.createTable(parent, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.READ_ONLY);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(importLabel, IDetailsAreaConstants.VSPACE);
		data.bottom = new FlatFormAttachment(100, 0);
		importTable.setLayoutData(data);
		
		//set up table
		importTable.setLinesVisible(true);
		importTable.setHeaderVisible(true);
				
		tableProvider = new ColumnTableProvider();
		tableProvider.add(new LocationColumn());
		tableProvider.add(new NamespaceColumn());

		importViewer = new TableViewer(importTable);
		tableProvider.createTableLayout(importTable);
		importViewer.setLabelProvider(tableProvider);
		importViewer.setCellModifier(tableProvider);
		importViewer.setContentProvider(new ImportContentProvider());
		importViewer.setColumnProperties(tableProvider.getColumnProperties());
		importViewer.setCellEditors(tableProvider.createCellEditors(importTable));
		
		tableCursor = BPELUtil.createTableCursor(importTable, importViewer);			
	}
	
	public class NamespaceColumn extends ColumnTableProvider.Column implements ILabelProvider {
		public String getHeaderText() { return Messages.ImportDetails_Import_Namespace_12; } 
		public String getProperty() { return "setNamespace"; } //$NON-NLS-1$
		public int getInitialWeight() { return 50; }

		public String getText(Object element) {
			String s = ((Import)element).getNamespace();
			return (s == null)? "" : NamespaceUtils.convertUriToNamespace(s); //$NON-NLS-1$
		}
	}
	
	public class LocationColumn extends ColumnTableProvider.Column implements ILabelProvider {
		public String getHeaderText() { return Messages.ImportDetails_Import_Location_12; } 
		public String getProperty() { return "setLocation"; } //$NON-NLS-1$
		public int getInitialWeight() { return 50; }

		public String getText(Object element) {
			String s = ((Import)element).getLocation();
			return (s == null)? "" : s; //$NON-NLS-1$			
		}		
	}
		
	public void refresh() {
		super.refresh();
		if (getInput() != null){
			importViewer.setInput(getInput());
		}
	}	
	
	protected void createClient(Composite parent) {
		Composite composite = parentComposite = createFlatFormComposite(parent);
		createImportWidgets(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
			parentComposite, IHelpContextIds.PROPERTY_PAGE_BPEL_IMPORTS);
	}

	public Object getUserContext() {
		return ((StructuredSelection)importViewer.getSelection()).getFirstElement();
	}
	public void restoreUserContext(Object userContext) {
		importTable.setFocus();
		if (userContext != null) {
			importViewer.setSelection(new StructuredSelection(userContext));
		}
	}
}
