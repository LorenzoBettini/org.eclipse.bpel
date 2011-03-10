package org.eclipse.bpel.extensionsample.model.util;

import javax.xml.namespace.QName;

import org.eclipse.bpel.extensionsample.model.ModelPackage;
import org.eclipse.bpel.extensionsample.model.SampleSimpleActivity;
import org.eclipse.bpel.extensionsample.model.SampleStructuredActivity;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.extensions.BPELActivitySerializer;
import org.eclipse.bpel.model.resource.BPELWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/*
 * Bug 120110 - this class has been updated to include a Variable
 * reference for the SampleSimpleActivity and a Variable definition
 * for the SampleStructuredActivity.
 */
public class ExtensionSampleActivitySerializer implements BPELActivitySerializer {

	@Override
	public void marshall(QName elementType, Activity activity, Node parentNode, Process process,
			BPELWriter bpelWriter) {

		Document document = parentNode.getOwnerDocument();

		/*
		 * SampleSimpleActivity
		 */
		if (activity instanceof SampleSimpleActivity) {
			SampleSimpleActivity sa = (SampleSimpleActivity)activity;

			// create a new DOM element for our Activity
			Element saElement = document.createElementNS(elementType.getNamespaceURI(),
					ExtensionsampleConstants.ND_SAMPLE_SIMPLE_ACTIVITY);
			saElement.setPrefix(ExtensionSampleUtils.addNamespace(process));

			// handle the SampleExtensionAttribute
			if (sa.getSampleExtensionAttribute() != null) {
				String attName = ModelPackage.eINSTANCE
						.getSampleSimpleActivity_SampleExtensionAttribute().getName();
				saElement.setAttribute(attName, sa.getSampleExtensionAttribute());
				
				// add variable name and type as attributes to this SampleSimpleActivity
				if (sa.getVariable()!=null) {
					String name = sa.getVariable().getName();
					if (name!=null && !"".equals(name.trim()) )
						saElement.setAttribute("variable",name);
				}
			}

			// insert the DOM element into the DOM tree
			parentNode.appendChild(saElement);
		}

		/*
		 * SampleStructuredActivity
		 */
		if (activity instanceof SampleStructuredActivity) {

			// create a new DOM element for our Activity
			Element activityElement = document.createElementNS(elementType.getNamespaceURI(),
					ExtensionsampleConstants.ND_SAMPLE_STRUCTURED_ACTIVITY);
			activityElement.setPrefix(ExtensionSampleUtils.addNamespace(process));

			// handle child activity
			Activity childActivity = ((SampleStructuredActivity) activity).getActivity();
			if (childActivity != null) {
				activityElement.appendChild(bpelWriter.activity2XML(childActivity));
			}

			// insert the DOM element into the DOM tree
			parentNode.appendChild(activityElement);
		}
	}

}
