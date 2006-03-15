package org.eclipse.bpel.ui.wsdl.extensions.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ExpressionEditorDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class BPELQueryLanguagePropertyDescriptor extends PropertyDescriptor {
	List languages = new ArrayList();
	List uris = new ArrayList();
	
	public BPELQueryLanguagePropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		ExpressionEditorDescriptor[] descriptors = BPELUIRegistry.getInstance().getExpressionEditorDescriptors();
		for (int  i = 0 ; i < descriptors.length; i++){
			languages.add(descriptors[i].getLabel());
			uris.add(descriptors[i].getExpressionLanguage());
		}
	}

	public CellEditor createPropertyEditor(Composite parent) {


		CellEditor editor = new ComboBoxCellEditor(parent, (String[])languages.toArray(new String[]{}),SWT.READ_ONLY){

			protected Object doGetValue() {							
				Integer i = (Integer)super.doGetValue();
				return uris.get(i.intValue());
			}

			protected void doSetValue(Object value) {				
				if (uris.contains(value)) {
					super.doSetValue(new Integer(uris.indexOf(value)));
				} else {
					super.doSetValue(new Integer(0));
				}
			}						
			
		};
        if (getValidator() != null)
            editor.setValidator(getValidator());
        return editor;
	}

	
}
