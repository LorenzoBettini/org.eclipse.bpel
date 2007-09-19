package org.eclipse.bpel.ui;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.impl.ActivityImpl;
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.ui.util.BPELEditorUtil;

import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.internal.impl.DefinitionImpl;
import org.eclipse.wst.wsdl.internal.impl.WSDLElementImpl;
import org.eclipse.wst.wsdl.internal.impl.XSDSchemaExtensibilityElementImpl;
import org.eclipse.wst.wsdl.util.WSDLConstants;
import org.eclipse.wst.xsd.ui.internal.util.ModelReconcileAdapter;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


class BPELModelReconcileAdapter extends ModelReconcileAdapter {                             
	protected Process process;

	public BPELModelReconcileAdapter(Document document, Process process) {           
		super(document);
		this.process = process;
	} 

	// This method is clever enough to deal with 'bad' documents that happen 
	// to have more than one root element.  It picks of the first 'matching' element.
	//
	// TODO (cs) why aren't we calling this from the WSDLModelAdapter when the model is initialized?
	//
	private Element getProcessElement(Document document) {
		Element processElement = null;    
		for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;
				if (BPELEditorUtil.getInstance().getBPELType(element).equals(BPELConstants.ND_PROCESS)) {
					processElement = element;
					break;
				}
			}
		}
		return processElement;
	}
  
	protected void handleNodeChanged(Node node) {
		if (node instanceof Element) {
			reconcileModelObjectForElement((Element)node);      
		} else if (node instanceof Document) {
			// The document changed so we may need to fix up the 
			// definition's root element
			Document document = (Document)node;    
			Element processElement = getProcessElement(document);
			/*if (definitionElement != null && definitionElement != process.getElement()) {   
        	// here we handle the case where a new 'definition' element was added
        	//(e.g. the file was totally blank and then we type in the root element)        
        	// See Bug 5366
        	//
        	if (definitionElement.getLocalName().equals(WSDLConstants.DEFINITION_ELEMENT_TAG)) {  
          		//System.out.println("****** Setting new definition");
          		process.setElement(definitionElement);
        	}
      	} else if (definitionElement != null) {       
        	// handle the case where the definition element's content has changed
        	// 
        	((ProcessImpl)process).elementChanged(definitionElement);
      	} else if (definitionElement == null) {
        	// if there's no definition element clear out the WSDL
        	//
        	((ProcessImpl)process).removeAll();
        	// The removeAll() call does not remove namespaces as well and the model
        	// does not reconcile well in this case. Also reset the definition name and target
        	// namespace.
        	process.getNamespaces().clear();
        	process.setQName(null);
        	process.setTargetNamespace(null);
        
        	// Reset the document because removeAll() sets the document to null as well.
        	process.setDocument(document);
		}*/
		}         
	}
       
	private void reconcileModelObjectForElement(Element element) {
		Object modelObject = BPELEditorUtil.getInstance().findModelObjectForElement(process, element);  	
		if (modelObject != null) {
			if (modelObject instanceof ActivityImpl) {
				((ActivityImpl)modelObject).elementChanged(element);
			} else if (modelObject instanceof XSDSchemaExtensibilityElementImpl) {
				XSDSchemaExtensibilityElementImpl ee = (XSDSchemaExtensibilityElementImpl)modelObject;
				((XSDSchemaImpl)ee.getSchema()).elementChanged(element);
				ee.elementChanged(element);            	
			} else if (modelObject instanceof WSDLElementImpl) {
				((WSDLElementImpl)modelObject).elementChanged(element);
			} else if (modelObject instanceof XSDConcreteComponent) {
				((XSDConcreteComponent)modelObject).elementChanged(element);        
			}  
		}     
	}  


	  public void modelDirtyStateChanged(IStructuredModel model, boolean isDirty)
  {
    if (!isDirty)
    {  
      // cs : At this time (when a save occurs) it's a good opportunity
      // to update the model to ensure it's in sync with the source. 
      // That way if the incremental sync between DOM and model has gotten
      // the model out of whack we'll be able to put things right at this point.
      //   
      // TODO (cs) need to do more to ensure model is sync'd up properly      

    	//FIXME uncomment
    	//((ProcessImpl)process).reconcileReferences(true);
    }
  }
}
