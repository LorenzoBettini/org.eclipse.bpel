package org.eclipse.bpel.extensionsample.model.util;

import java.util.Map;

import javax.wsdl.extensions.ExtensionRegistry;
import javax.xml.namespace.QName;

import org.eclipse.bpel.extensionsample.model.ModelFactory;
import org.eclipse.bpel.extensionsample.model.ModelPackage;
import org.eclipse.bpel.extensionsample.model.SampleSimpleActivity;
import org.eclipse.bpel.extensionsample.model.SampleStructuredActivity;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Import;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.extensions.BPELActivityDeserializer;
import org.eclipse.bpel.model.resource.BPELReader;
import org.eclipse.bpel.model.util.BPELUtils;
import org.eclipse.bpel.model.util.ImportResolver;
import org.eclipse.bpel.model.util.ImportResolverRegistry;
import org.eclipse.bpel.model.util.WSDLUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * Bug 120110 - this class has been updated to include a Variable
 * reference for the SampleSimpleActivity and a Variable definition
 * for the SampleStructuredActivity.
 */
public class ExtensionSampleActivityDeserializer implements BPELActivityDeserializer {

	@Override
	public Activity unmarshall(QName elementType, Node node, Activity activity, Process process,
			Map nsMap, ExtensionRegistry extReg, URI uri, BPELReader bpelReader) {

		/*
		 * SampleSimpleActivity
		 */
		if (ExtensionsampleConstants.ND_SAMPLE_SIMPLE_ACTIVITY.equals(elementType.getLocalPart())) {

			// create a new SampleSimpleActivity model object if not already created
			SampleSimpleActivity sa;
			Element saElement = (Element)node;
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=334424
			if (activity instanceof SampleSimpleActivity) {
				sa = (SampleSimpleActivity)activity;
			}
			else {
				sa = ModelFactory.eINSTANCE
					.createSampleSimpleActivity();

				// attach the DOM node to our new activity
				sa.setElement(saElement);
			}

			// handle the SampleExtensionAttribute
			String attName = ModelPackage.eINSTANCE
					.getSampleSimpleActivity_SampleExtensionAttribute().getName();
			if (saElement.getAttribute(attName) != null) {
				sa.setSampleExtensionAttribute(saElement.getAttribute(attName));
			}
			
			// handle variable name: find this variable is in a visible scope
			String value = saElement.getAttribute("variable");
			if (value!=null && !"".equals(value.trim())) {
				Variable[] vars = ModelHelper.getVisibleVariables(activity);
				for (int i=vars.length-1; i>=0; --i) {
					if (value.equals(vars[i].getName())) {
						sa.setVariable(vars[i]);
						break;
					}
				}
			}

			return sa;
		}

		/*
		 * SampleStructuredActivity
		 */
		if (ExtensionsampleConstants.ND_SAMPLE_STRUCTURED_ACTIVITY.equals(elementType
				.getLocalPart())) {

			Element saElement = (Element) node;

			// create a new SampleStructuredActivity model object
			SampleStructuredActivity sa;
			if (activity instanceof SampleStructuredActivity) {
				sa = (SampleStructuredActivity)activity;
			}
			else
			{
				sa = ModelFactory.eINSTANCE
					.createSampleStructuredActivity();
				
				// attach the DOM node to our new activity
				sa.setElement(saElement);
			}

			// handle variable definition
			String value = saElement.getAttribute("variable");
			if (value!=null && !"".equals(value.trim())) {
				Variable variable = sa.getVariable();
				if (variable==null)
					variable = BPELFactory.eINSTANCE.createVariable();
				variable.setName(value);
				value = saElement.getAttribute("messageType");
				if (value!=null && !"".equals(value.trim())) {
					QName qname = BPELUtils.createQName(process.getElement(), value);
					Message message = (Message)scanImports( process, qname, WSDLUtil.WSDL_MESSAGE );
					if (message!=null)
						variable.setMessageType(message);
				}
				
				value = saElement.getAttribute("type");
				if (value!=null && !"".equals(value.trim())) {
					QName qname = BPELUtils.createQName(process.getElement(), value);
					XSDTypeDefinition type = (XSDTypeDefinition)scanImports( process, qname, WSDLUtil.XSD_TYPE_DEFINITION );
					if (type!=null)
						variable.setType(type);
				}
				
				value = saElement.getAttribute("element");
				if (value!=null && !"".equals(value.trim())) {
					QName qname = BPELUtils.createQName(process.getElement(), value);
					XSDElementDeclaration elem = (XSDElementDeclaration)scanImports( process, qname, WSDLUtil.XSD_ELEMENT_DECLARATION );
					if (elem!=null)
						variable.setXSDElement(elem);
				}
				sa.setVariable(variable);
			}
			
			// handle the child activity
			NodeList childElements = saElement.getChildNodes();
			Element activityElement = null;
			if (childElements != null && childElements.getLength() > 0) {
				for (int i = 0; i < childElements.getLength(); i++) {

					// the only element node is the child activity
					if ((childElements.item(i).getNodeType() == Node.ELEMENT_NODE)) {
						activityElement = (Element) childElements.item(i);
						Activity childActivity = bpelReader.xml2Activity(activityElement);
						if (childActivity != null) {
							sa.setActivity(childActivity);
						}
					}
				}
			}

			return sa;
		}

		System.err.println("Cannot handle this kind of element");
		return null;
	}

	static EObject scanImports( Process process, QName qname , String refType ) {
		
		EObject result = null;
		
		for(Object n : process.getImports()) {
            Import imp = (Import) n;                                    
            if (imp.getLocation() == null ) {
            	continue;
            }
            
    	    ImportResolver[] resolvers = ImportResolverRegistry.INSTANCE.getResolvers(imp.getImportType());
    	    for(ImportResolver r : resolvers) {
                result = r.resolve(imp, qname, null, refType);
                if (result != null) {
                	return result;
                }                
            }
    	    
        } 
        
        return null;
	}

}
