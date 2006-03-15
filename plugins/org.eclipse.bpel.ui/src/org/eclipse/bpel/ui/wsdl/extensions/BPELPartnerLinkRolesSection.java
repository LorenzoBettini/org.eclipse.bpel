package org.eclipse.bpel.ui.wsdl.extensions;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.model.partnerlinktype.Role;
import org.eclipse.bpel.model.partnerlinktype.util.PartnerlinktypeConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.AbstractContentProvider;
import org.eclipse.bpel.ui.details.providers.ColumnTableProvider;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.TableCursor;
import org.eclipse.bpel.ui.wsdl.extensions.properties.WSDLComponentSelectionDialogPropertyDescriptor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.common.ui.properties.internal.provisional.AbstractPropertySection;
import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetPage;
import org.eclipse.wst.wsdl.ui.internal.WSDLEditor;
import org.eclipse.wst.wsdl.ui.internal.actions.AddElementAction;
import org.eclipse.wst.wsdl.ui.internal.actions.DeleteAction;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.w3c.dom.Element;

public class BPELPartnerLinkRolesSection extends AbstractPropertySection {
	
	protected PartnerLinkType plink;
	
	protected Table rolesTable;
	protected TableViewer rolesViewer;
	protected ColumnTableProvider tableProvider;
	protected TableCursor tableCursor = null;

	protected Button addButton;
	protected Button removeButton;
	
	protected WSDLEditor editor;
	
	public void aboutToBeHidden() {
		// TODO Auto-generated method stub
		
	}

	public void aboutToBeShown() {
		refresh();
	}
	
	public class PortTypeColumn extends ColumnTableProvider.Column implements ILabelProvider, ICellModifier {
		public String getHeaderText() { return Messages.BPELPartnerLinkRolesSection_Role_PortType; } 
		public String getProperty() { return PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE; } //$NON-NLS-1$
		public int getInitialWeight() { return 10; }
		
		public CellEditor createCellEditor(Composite parent) {
			return WSDLComponentSelectionDialogPropertyDescriptor.createCellEditor(parent, editor, WSDLConstants.PORT_TYPE, null);
		}

		public String getText(Object element) {
			if (((Role)element).getElement().hasAttribute(PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE))
				return ((Role)element).getElement().getAttribute(PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE);
			else 
				return ""; 
		}
		public boolean canModify(Object element, String property) {
			return true;
		}
		public Object getValue(Object element, String property) {
			return ((Role)element).getElement().getAttribute(PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE);
		}
		public void modify(Object element, String property, Object value) {
			((Role)element).getElement().setAttribute(PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE, (String)value);
			refresh();
		}
	}

	public class NameColumn extends ColumnTableProvider.Column implements ILabelProvider, ICellModifier {
		public String getHeaderText() { return Messages.BPELPartnerLinkRolesSection_Role_Name; } 
		public String getProperty() { return PartnerlinktypeConstants.NAME_ATTRIBUTE; } //$NON-NLS-1$
		public int getInitialWeight() { return 10; }
		
		public CellEditor createCellEditor(Composite parent) {
			CellEditor result = new TextCellEditor();
			result.create(parent);
			return result;
		}
		public String getText(Object element) {
			return ((Role)element).getName();
		}
		public boolean canModify(Object element, String property) {
			return true;
		}
		public Object getValue(Object element, String property) {
			return ((Role)element).getName();
		}
		public void modify(Object element, String property, Object value) {
			((Role)element).setName((String)value);
			refresh();
		}
	}

	
	public void createControls(Composite parent, TabbedPropertySheetPage page) {
		FlatFormData data;
		Composite composite = page.getWidgetFactory().createFlatFormComposite(parent);
		FlatFormLayout formLayout = new FlatFormLayout();
		formLayout.marginWidth = formLayout.marginHeight = 0;
		composite.setLayout(formLayout);
		
		addButton = page.getWidgetFactory().createButton(composite, Messages.BPELPartnerLinkRolesSection_Add, SWT.PUSH); 
		removeButton = page.getWidgetFactory().createButton(composite, Messages.BPELPartnerLinkRolesSection_Remove, SWT.PUSH); 
		Label rolesLabel = page.getWidgetFactory().createLabel(composite, Messages.BPELPartnerLinkRolesSection_Roles); 
		rolesTable = page.getWidgetFactory().createTable(composite, SWT.FULL_SELECTION | SWT.V_SCROLL);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.top = new FlatFormAttachment(0, 0);
		rolesLabel.setLayoutData(data);
		
		data = new FlatFormData();
		data.width = BPELUtil.calculateButtonWidth(addButton, BPELPropertySection.STANDARD_BUTTON_WIDTH);
		data.right = new FlatFormAttachment(removeButton, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0, 0);
		addButton.setLayoutData(data);
		
		data = new FlatFormData();
		data.width = BPELUtil.calculateButtonWidth(removeButton, BPELPropertySection.STANDARD_BUTTON_WIDTH);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		removeButton.setLayoutData(data);
		removeButton.setEnabled(false);
		
		addButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Action action = new AddElementAction("Add role",getPartnerLinkType().getElement(),getPartnerLinkType().getElement().getPrefix(),PartnerlinktypeConstants.ROLE_ELEMENT_TAG){

					protected void addAttributes(Element newElement) {
						newElement.setAttribute(PartnerlinktypeConstants.NAME_ATTRIBUTE, "NCName");												
					}
									
				};
				action.run();
				updateButtonEnablement();
				if (tableCursor != null) 
					tableCursor.refresh();					
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
		Listener removeListener = new Listener() {
			public void handleEvent(Event e) {
				if (e.type == SWT.KeyUp && e.character != SWT.DEL)  return;
				int selectionIndex = rolesTable.getSelectionIndex();
				IStructuredSelection sel = (IStructuredSelection)rolesViewer.getSelection();
				Action action = new DeleteAction(((IStructuredSelection)sel).getFirstElement(),null);
				action.run();
				// selects the element at the deleted element position
				int items = rolesTable.getItemCount();
				if (items > 0) {
					selectionIndex = (selectionIndex < items) ? selectionIndex : 0;
					rolesTable.setSelection(selectionIndex);
				}
				if (tableCursor != null)
					tableCursor.refresh();
				updateButtonEnablement();
			}
		};
		rolesTable.addListener(SWT.KeyUp, removeListener);
		
		removeButton.addListener(SWT.Selection, removeListener);
		removeButton.addListener(SWT.DefaultSelection, removeListener);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(addButton, IDetailsAreaConstants.VSPACE);
		data.bottom = new FlatFormAttachment(100, 0);
		rolesTable.setLayoutData(data);

		rolesTable.setLinesVisible(true);
		rolesTable.setHeaderVisible(true);
		
		tableProvider = new ColumnTableProvider();
		tableProvider.add(new NameColumn());
		tableProvider.add(new PortTypeColumn());
		
		rolesTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				removeButton.setEnabled(!rolesViewer.getSelection().isEmpty());
			}
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
	
		rolesViewer = new TableViewer(rolesTable);
		tableProvider.createTableLayout(rolesTable);
		rolesViewer.setLabelProvider(tableProvider);
		rolesViewer.setCellModifier(tableProvider);
		rolesViewer.setContentProvider(new AbstractContentProvider(){

			public Object[] getElements(Object inputElement) {
				// TODO Auto-generated method stub
				return ((PartnerLinkType)inputElement).getRole().toArray();
			}
			
		});
		// TODO: should this have a sorter?
		rolesViewer.setColumnProperties(tableProvider.getColumnProperties());
		rolesViewer.addSelectionChangedListener(new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				updateButtonEnablement();
			}
		});
		
		tableCursor = BPELUtil.createTableCursor(rolesTable, rolesViewer);
	}
	
	protected void updateButtonEnablement(){
		boolean hasSelection = !rolesViewer.getSelection().isEmpty();
		removeButton.setEnabled(hasSelection && getPartnerLinkType().getRole().size() == 2);
		addButton.setEnabled(getPartnerLinkType().getRole().size() < 2);
	}	

	
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void refresh(){
		rolesViewer.refresh();
	}

	public int getMinimumHeight() {
		// TODO Auto-generated method stub
		return SWT.DEFAULT;
	}

	public void setInput(IWorkbenchPart arg0, ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		plink = (PartnerLinkType)((IStructuredSelection)selection).getFirstElement();		
		editor = (WSDLEditor)arg0;		
		rolesViewer.setCellEditors(tableProvider.createCellEditors(rolesTable));	
		rolesViewer.setInput(plink);
		updateButtonEnablement();
	}
	
	public PartnerLinkType getPartnerLinkType() {
		return plink;
	}

	public boolean shouldUseExtraSpace() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
}
