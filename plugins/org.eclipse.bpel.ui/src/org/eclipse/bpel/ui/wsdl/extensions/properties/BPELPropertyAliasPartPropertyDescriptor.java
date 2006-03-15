package org.eclipse.bpel.ui.wsdl.extensions.properties;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.ui.internal.WSDLEditor;

public class BPELPropertyAliasPartPropertyDescriptor extends PropertyDescriptor {
	WSDLEditor editor;
	String message;
	List parts = new ArrayList();
	
	public BPELPropertyAliasPartPropertyDescriptor(Object id, String displayName, String message,WSDLEditor editor) {
		super(id, displayName);
		this.editor = editor;
		this.message = message;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		QName messageQName = createQName(((WSDLEditor)editor).getDefinition(), message);
    	Message newMessage = (messageQName != null) ? (Message) ((WSDLEditor)editor).getDefinition().getMessage(messageQName) : null;
		parts.addAll(newMessage.getParts().keySet());
		CellEditor editor = new ComboBoxCellEditor(parent, (String[])parts.toArray(new String[]{}),SWT.READ_ONLY){

			protected Object doGetValue() {							
				Integer i = (Integer)super.doGetValue();
				return parts.get(i.intValue());
			}

			protected void doSetValue(Object value) {
				if (parts.contains(value)) {
					super.doSetValue(new Integer(parts.indexOf(value)));
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
				return (String)parts.get(((Integer)element).intValue());
			}
			
		};
	}	
	
//	 Some subclasses use this method
	  protected QName createQName(Definition definition, String prefixedName)
	  {
	    QName qname = null;
	    if (prefixedName != null)
	    {
	      int index = prefixedName.indexOf(":");
	      String prefix = (index == -1) ? "" : prefixedName.substring(0, index);
	      String namespace = definition.getNamespace(prefix);
	      if (namespace != null)
	      {
	        String localPart = prefixedName.substring(index + 1);
	        qname = new QName(namespace, localPart);       
	      }
	    }
	    return qname;
	  }


}
