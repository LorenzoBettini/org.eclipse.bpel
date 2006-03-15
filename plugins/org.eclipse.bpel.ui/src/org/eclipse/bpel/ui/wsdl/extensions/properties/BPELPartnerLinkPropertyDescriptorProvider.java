package org.eclipse.bpel.ui.wsdl.extensions.properties;

import org.eclipse.bpel.model.partnerlinktype.util.PartnerlinktypeConstants;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.wst.wsdl.ui.internal.WSDLEditor;
import org.eclipse.wst.wsdl.ui.internal.properties.section.IPropertyDescriptorProvider;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.w3c.dom.Element;

public class BPELPartnerLinkPropertyDescriptorProvider implements IPropertyDescriptorProvider{

	public IPropertyDescriptor getPropertyDescriptor(final IEditorPart editor, Element ownerElement, String attributeNamespace, String attributeName) {
		if (PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE.equals(attributeName)) {
			return new WSDLComponentSelectionDialogPropertyDescriptor(PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE,PartnerlinktypeConstants.PORT_TYPE_ATTRIBUTE,(WSDLEditor)editor,WSDLConstants.PORT_TYPE, ownerElement);
		} 
		
		return null;
	}
	
	  
}
