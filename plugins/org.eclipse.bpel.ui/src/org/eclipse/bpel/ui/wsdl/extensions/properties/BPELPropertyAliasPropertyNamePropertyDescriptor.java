package org.eclipse.bpel.ui.wsdl.extensions.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Import;
import org.eclipse.wst.wsdl.internal.impl.ImportImpl;
import org.eclipse.wst.wsdl.ui.internal.WSDLEditor;

public class BPELPropertyAliasPropertyNamePropertyDescriptor extends PropertyDescriptor{
	
	
	WSDLEditor editor;
	List properties = new ArrayList();
	
	public BPELPropertyAliasPropertyNamePropertyDescriptor(Object id, String displayName, WSDLEditor editor) {
		super(id, displayName);
		this.editor = editor;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		Definition d = ((WSDLEditor)editor).getDefinition();
		createPropertyObjects(properties,d.getExtensibilityElements(),null);
		
	    Iterator importsIt = getWSDLFileImports(d.getEImports()).iterator();
	    while (importsIt.hasNext()) {
	        Import importItem = (Import) importsIt.next();		        
	        Definition importDefinition = importItem.getEDefinition();		        		        
	        createPropertyObjects(properties, importDefinition.getExtensibilityElements(),d.getPrefix(importItem.getNamespaceURI()));
	    }   
		CellEditor editor = new ComboBoxCellEditor(parent, (String[])properties.toArray(new String[]{}),SWT.READ_ONLY){

			protected Object doGetValue() {							
				Integer i = (Integer)super.doGetValue();
				return properties.get(i.intValue());
			}

			protected void doSetValue(Object value) {
				if (properties.contains(value)) {
					super.doSetValue(new Integer(properties.indexOf(value)));
				} else {
					super.doSetValue(new Integer(0));
				}
			}						
			
		};
        if (getValidator() != null)
            editor.setValidator(getValidator());
        return editor;
	}

	public ILabelProvider getLabelProvider() {					
		return new LabelProvider(){

			public String getText(Object element) {
				// TODO Auto-generated method stub
				return (String)properties.get(((Integer)element).intValue());
			}
			
		};
	}	
	
	private void createPropertyObjects(List components, List extensibilityElements, String prefix){
		Iterator i = extensibilityElements.iterator();
		while (i.hasNext()) {
			Object ee = i.next();
			if (ee instanceof Property){				
				components.add(prefix != null ? prefix+":"+((Property)ee).getName() : ((Property)ee).getName());
			}
		}
	}
	
    private List getWSDLFileImports(List wsdlImports) {
        List list = new ArrayList();
        Iterator it = wsdlImports.iterator();
        while (it.hasNext()) {
            ImportImpl importItem = (ImportImpl) it.next();
            importItem.importDefinitionOrSchema();          // Load if necessary
            if (importItem.getESchema() == null) {            	
                list.add(importItem);
            }
        }
        
        return list;
    }


}
