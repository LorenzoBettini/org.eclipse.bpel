package org.eclipse.bpel.ui.wsdl.extensions;

import java.util.Iterator;

import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.bpel.ui.details.providers.ModelTreeContentProvider;
import org.eclipse.bpel.ui.details.tree.PartTreeNode;
import org.eclipse.bpel.ui.details.tree.XSDElementDeclarationTreeNode;
import org.eclipse.bpel.ui.details.tree.XSDTypeDefinitionTreeNode;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;

public class BPELQueryTreeContentProvider extends ModelTreeContentProvider{

	public BPELQueryTreeContentProvider(boolean isCondensed) {
		super(isCondensed);
		// TODO Auto-generated constructor stub
	}
	
	public Object[] primGetElements(Object inputElement) {
		
		if (inputElement instanceof PropertyAlias){
			PropertyAlias palias = (PropertyAlias)inputElement;			
			if (palias.getMessageType() != null )  {
				Message msg = (Message)palias.getMessageType();
				Iterator partsIterator = msg.getEParts().iterator();
				while (partsIterator.hasNext()) {
					Part part = (Part)partsIterator.next();
					if (part.getName().equals(palias.getPart())) {
						return new Object[]{ new PartTreeNode(part, isCondensed, true)};
					}
				}			
			} else if (palias.getType() != null) {
				return new Object[]{new XSDTypeDefinitionTreeNode((XSDTypeDefinition)palias.getType(), isCondensed)};
			} else if (palias.getXSDElement() != null) {
				return new Object[]{ new XSDElementDeclarationTreeNode((XSDElementDeclaration)palias.getXSDElement(), isCondensed)};			
			}
		}
		throw new IllegalStateException();		
	}

}
