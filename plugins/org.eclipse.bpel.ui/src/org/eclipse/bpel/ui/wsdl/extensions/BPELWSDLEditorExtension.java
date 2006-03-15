package org.eclipse.bpel.ui.wsdl.extensions;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.common.ui.properties.internal.provisional.ISectionDescriptor;
import org.eclipse.wst.common.ui.properties.internal.provisional.ISectionDescriptorProvider;
import org.eclipse.wst.wsdl.ui.internal.WSDLEditor;
import org.eclipse.wst.wsdl.ui.internal.extension.WSDLEditorExtension;
import org.w3c.dom.Node;

public class BPELWSDLEditorExtension extends LabelProvider implements WSDLEditorExtension {

	public Object createExtensionObject(int type, final WSDLEditor wsdlEditor) {
		if (type == PROPERTY_SECTION_DESCRIPTOR_PROVIDER){
			return new ISectionDescriptorProvider(){

				public ISectionDescriptor[] getSectionDescriptors() {
					// TODO Auto-generated method stub
					return new ISectionDescriptor[]{new BPELPartnerLinkTypeSectionDescriptor(),new BPELPropertyAliasSectionDescriptor()};
					
				}
				
			};
		} 
		return null;
	}

	public boolean isApplicable(Object modelObject) {
		return true;
	}

	public boolean isExtensionTypeSupported(int type) {
		return type == PROPERTY_SECTION_DESCRIPTOR_PROVIDER;
	}

	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return BPELUIPlugin.getPlugin().getImageDescriptor(IBPELUIConstants.ICON_PROCESS_16).createImage();
	}

	public String getText(Object element) {
		// TODO Auto-generated method stub
		return ((Node)element).getNodeName();
	}

}
