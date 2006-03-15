package org.eclipse.bpel.ui.wsdl.extensions.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.ui.Messages;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ui.internal.WSDLEditor;
import org.eclipse.wst.wsdl.ui.internal.dialogs.types.WSDLComponentSelectionDialog;
import org.eclipse.wst.wsdl.ui.internal.dialogs.types.WSDLComponentSelectionProvider;
import org.eclipse.wst.wsdl.ui.internal.util.WSDLEditorUtil;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.w3c.dom.Element;

public class WSDLComponentSelectionDialogPropertyDescriptor extends PropertyDescriptor {
	
	private int kind;
	private WSDLEditor editor;
	private Element element;
	public WSDLComponentSelectionDialogPropertyDescriptor(Object id, String displayName, WSDLEditor editor, int kind, Element element) {
		super(id, displayName);
		this.kind = kind;
		this.editor = editor;
		this.element = element;
	}
	
	public static CellEditor createCellEditor (Composite parent, final WSDLEditor editor, final int kind, final Element element) {
		CellEditor celleditor = new DialogCellEditor(parent){			
			
			protected Object openDialogBox(Control cellEditorWindow) {
				WSDLComponentSelectionProvider provider = new WSDLComponentSelectionProvider(((IFileEditorInput) editor.getEditorPart().getEditorInput()).getFile(), editor.getDefinition(), kind);
				WSDLComponentSelectionDialog dialog = new WSDLComponentSelectionDialog(cellEditorWindow.getShell(), Messages.BPELWSDLComponentSelectionDialog, provider){

					protected TreeViewer createTreeViewer(Composite comp, String title) {
						final TreeViewer viewer = super.createTreeViewer(comp, title);
						viewer.addDoubleClickListener(new IDoubleClickListener(){

							public void doubleClick(DoubleClickEvent event) {
								// TODO Auto-generated method stub
								if (viewer.getSelection() instanceof IStructuredSelection && !((IStructuredSelection)viewer.getSelection()).isEmpty())
									okPressed();
							}
							
						});
						return viewer;
					}
											
				}; 
		        provider.setDialog(dialog);
		        dialog.setBlockOnOpen(true);
		        dialog.create();
		        
		        if (dialog.open() == Window.OK){
		        	Display.getCurrent().asyncExec(new Runnable(){

						public void run() {
							if (element == null)
								return;
							if (kind == WSDLConstants.MESSAGE){			        		
				        		element.removeAttribute("element");
				        		element.removeAttribute("type");			    
				        	} else if (kind == WSDLEditorUtil.ELEMENT) {
				        		element.removeAttribute("messageType");
				        		element.removeAttribute("part");
				        		element.removeAttribute("type");
				        	} else if (kind == WSDLEditorUtil.TYPE) {
				        		element.removeAttribute("messageType");
				        		element.removeAttribute("part");
				        		element.removeAttribute("element");
				        	}		
						}
		        		
		        	});
		        	
		        	
		        	String name = (String) dialog.getSelection().getAttributeInfo("name");
		            List prefixes = getPrefixes(editor.getDefinition(), dialog.getSelection().getTargetNamespace());
		            if (prefixes.size() > 0) {
		                name = prefixes.get(0) + ":" + name;
		            }
		        	return name;
		        }
		        return doGetValue();
			}
				
		};
        return celleditor;
	}
		
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor celleditor = createCellEditor(parent,editor,kind, element);
        if (getValidator() != null)
        	celleditor.setValidator(getValidator());
        return celleditor;
			
	}
	
    private static List getPrefixes(Definition definition, String namespace) {
        List list = new ArrayList();
        Map map = definition.getNamespaces();
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            String prefix = (String) i.next();
            String theNamespace = (String) map.get(prefix);
            if (theNamespace != null && theNamespace.equals(namespace)) {
                list.add(prefix);
            }
        }
        return list;
    }
}
