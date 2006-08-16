
package org.eclipse.bpel.ui.details.providers;

import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;


/**
 * Filter which excludes from a set of Variables those variables whose
 * types do not match the appropriate settings in the filter. 
 */ 

public class VariableFilter extends ViewerFilter
	implements IFilter
{
	
	Message fMessage;
	XSDElementDeclaration fElementDeclaration;
	XSDTypeDefinition fTypeDefinition;
	
	public VariableFilter () {
		
	}
	
	public void clear () {
		fMessage = null;
		fElementDeclaration = null;
		fTypeDefinition = null;		
	}
	
	public void setType ( Message msg ) {
		fMessage = msg;
	}
	public void setType ( XSDElementDeclaration decl ) {
		fElementDeclaration = decl;
	}
	
	public void setType ( XSDTypeDefinition typeDef) {
		fTypeDefinition = typeDef;
	}
	
	public boolean select (Viewer viewer, Object parentElement, Object element) {
		
		if (element instanceof ITreeNode) {
			return select ( ((ITreeNode)element).getModelObject() );			
		}
		return select ( element );
	}

	/** 
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	
	public boolean select (Object toTest) {

		if (toTest == null) {
			return false;
		}
		
		
		
		if ( Variable.class.isInstance(toTest) ) {
			
			Variable v = (Variable) toTest;
			
			if (fMessage == null && fElementDeclaration == null && fTypeDefinition == null) {
				return true;
			}
			
			if (fMessage != null) {			
				if (fMessage.equals ( v.getMessageType() )) {
					return true;
				}
			}
			
			if (fElementDeclaration != null) {
				if (fElementDeclaration.equals( v.getXSDElement() )) {
					return true;
				}
			}
			
			if (fTypeDefinition != null) {
				if (fTypeDefinition.equals( v.getType() )) {
					return true;
				}				
			}
		
			return false;			
		}
		
		return true;
	}
	
	
}
