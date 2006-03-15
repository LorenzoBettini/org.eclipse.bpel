package org.eclipse.bpel.ui.wsdl.extensions;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.ui.internal.dialogs.types.WSDLComponentSelectionProvider;
import org.eclipse.wst.xsd.ui.internal.dialogs.types.common.IComponentList;

public class BPELWSDLComponentSelectionProvider extends WSDLComponentSelectionProvider{

	public static int PROPERTY = 1809;
	
	private int kind;
	
	public BPELWSDLComponentSelectionProvider(IFile file, Definition definition, int kind, List validExt) {
		super(file, definition, kind, validExt);
		this.kind = kind;
	}

	public BPELWSDLComponentSelectionProvider(IFile file, Definition definition, int kind) {
		super(file, definition, kind);
		this.kind = kind;
	}

	public void getComponents(IComponentList list, boolean quick) {
		if (PROPERTY == kind){
			
		} else {
			super.getComponents(list, quick);
		}
	}

	public ILabelProvider getLabelProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getListTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNameFieldTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getType(Object component) {
		// TODO Auto-generated method stub
		return null;
	}


}
