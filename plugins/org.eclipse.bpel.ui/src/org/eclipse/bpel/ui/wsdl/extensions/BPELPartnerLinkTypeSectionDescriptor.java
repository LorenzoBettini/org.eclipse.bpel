package org.eclipse.bpel.ui.wsdl.extensions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.common.ui.properties.internal.provisional.ISection;
import org.eclipse.wst.wsdl.ui.internal.properties.section.AbstractSectionDescriptor;

public class BPELPartnerLinkTypeSectionDescriptor extends AbstractSectionDescriptor{

	/* (non-Javadoc)
	   * @see org.eclipse.wst.common.ui.properties.internal.provisional.ISectionDescriptor#appliesTo(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	   */
	  public boolean appliesTo(IWorkbenchPart part, ISelection selection)
	  {
	    if (selection instanceof StructuredSelection)
	    {
	      StructuredSelection structuredSelection = (StructuredSelection)selection;	      
	      return structuredSelection.getFirstElement() instanceof PartnerLinkType;
	    }
	    return false;
	  }


	public String getId() {
		// TODO Auto-generated method stub
		return "org.eclipse.bpel.ui.section.other.partnerLinkType";
	}

	public List getInputTypes() {
		List list = new ArrayList();
	    list.add(PartnerLinkType.class);	    
	    return list;
	}

	public ISection getSectionClass() {
		// TODO Auto-generated method stub
		return new BPELPartnerLinkRolesSection();
	}

	public String getTargetTab() {
		// TODO Auto-generated method stub
		return "org.eclipse.wst.xmlwebservices.other";
	}

}
