package org.eclipse.bpel.ui.properties.xtext;

import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.xtext.example.domainmodel.ui.internal.DomainmodelActivator;

import com.google.inject.Injector;

public class DomainmodelXtextExpressionAssignCategory extends
		AbstractXtextExpressionAssignCategory {

	public DomainmodelXtextExpressionAssignCategory(
			BPELPropertySection ownerSection) {
		super(ownerSection);
	}

	@Override
	public String getName() {
		return "Xtext Domainmodel Editor";
	}

	public Injector getInjector() {
		return DomainmodelActivator
				.getInstance()
				.getInjector(
						DomainmodelActivator.ORG_ECLIPSE_XTEXT_EXAMPLE_DOMAINMODEL_DOMAINMODEL);
	}
}
