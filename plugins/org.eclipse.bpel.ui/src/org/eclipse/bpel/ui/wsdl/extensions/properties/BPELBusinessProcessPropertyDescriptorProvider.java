package org.eclipse.bpel.ui.wsdl.extensions.properties;

import org.eclipse.bpel.model.messageproperties.util.MessagepropertiesConstants;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.wst.wsdl.ui.internal.WSDLEditor;
import org.eclipse.wst.wsdl.ui.internal.properties.section.IPropertyDescriptorProvider;
import org.eclipse.wst.wsdl.ui.internal.util.WSDLEditorUtil;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.w3c.dom.Element;

public class BPELBusinessProcessPropertyDescriptorProvider implements IPropertyDescriptorProvider{

	public IPropertyDescriptor getPropertyDescriptor(final IEditorPart editor, Element ownerElement, String attributeNamespace, String attributeName) {
		if (MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE.equals(attributeName)) {
			return new WSDLComponentSelectionDialogPropertyDescriptor(MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE,MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE,(WSDLEditor)editor,WSDLConstants.MESSAGE, ownerElement);
		} else if (MessagepropertiesConstants.PROPERTY_ALIAS_XSD_ELEMENT_ATTRIBUTE.equals(attributeName)) {
			return new WSDLComponentSelectionDialogPropertyDescriptor(MessagepropertiesConstants.PROPERTY_ALIAS_XSD_ELEMENT_ATTRIBUTE,MessagepropertiesConstants.PROPERTY_ALIAS_XSD_ELEMENT_ATTRIBUTE,(WSDLEditor)editor,WSDLEditorUtil.ELEMENT, ownerElement);
		} else if (MessagepropertiesConstants.PROPERTY_ALIAS_TYPE_ATTRIBUTE.equals(attributeName)) {
			return new WSDLComponentSelectionDialogPropertyDescriptor(MessagepropertiesConstants.PROPERTY_ALIAS_TYPE_ATTRIBUTE,MessagepropertiesConstants.PROPERTY_ALIAS_TYPE_ATTRIBUTE,(WSDLEditor)editor,WSDLEditorUtil.TYPE, ownerElement);
		} else if (MessagepropertiesConstants.PROPERTY_ALIAS_PROPERTY_NAME_ATTRIBUTE.equals(attributeName)) {		    
		    return new BPELPropertyAliasPropertyNamePropertyDescriptor(MessagepropertiesConstants.PROPERTY_ALIAS_PROPERTY_NAME_ATTRIBUTE,MessagepropertiesConstants.PROPERTY_ALIAS_PROPERTY_NAME_ATTRIBUTE, (WSDLEditor)editor);   		    
		} else if (MessagepropertiesConstants.PROPERTY_ALIAS_PART_ATTRIBUTE.equals(attributeName) && ownerElement.hasAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE)) {
			return new BPELPropertyAliasPartPropertyDescriptor(MessagepropertiesConstants.PROPERTY_ALIAS_PART_ATTRIBUTE,MessagepropertiesConstants.PROPERTY_ALIAS_PART_ATTRIBUTE,ownerElement.getAttribute(MessagepropertiesConstants.PROPERTY_ALIAS_MESSAGE_TYPE_ATTRIBUTE),(WSDLEditor)editor);
		} else if (MessagepropertiesConstants.QUERY_QUERYLANGUAGE_ATTRIBUTE.equals(attributeName)) {
			return new BPELQueryLanguagePropertyDescriptor(MessagepropertiesConstants.QUERY_QUERYLANGUAGE_ATTRIBUTE,MessagepropertiesConstants.QUERY_QUERYLANGUAGE_ATTRIBUTE);
		} 
		
		return null;
	}
	
	  
}
