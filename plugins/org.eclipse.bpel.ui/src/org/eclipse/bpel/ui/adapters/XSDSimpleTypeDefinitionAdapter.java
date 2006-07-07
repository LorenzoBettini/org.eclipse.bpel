/**
 * 
 */
package org.eclipse.bpel.ui.adapters;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xsd.XSDComplexTypeDefinition;

/**
 * @author mchmiele
 *
 */
public class XSDSimpleTypeDefinitionAdapter extends XSDAbstractAdapter  {

	public Image getSmallImage(Object object) {		
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_XSD_SIMPLE_TYPE_DEFINITION_16);
	}
		
	public String getTypeLabel(Object object) {
		return Messages.XSDSimpleTypeDefinitionAdapter_0; 
	}	
}
