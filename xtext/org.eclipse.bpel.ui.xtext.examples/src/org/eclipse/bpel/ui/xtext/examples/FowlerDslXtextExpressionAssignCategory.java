package org.eclipse.bpel.ui.xtext.examples;

import org.eclipse.bpel.ui.properties.xtext.AbstractXtextExpressionAssignCategory;
import org.eclipse.xtext.example.fowlerdsl.ui.internal.StatemachineActivator;

import com.google.inject.Injector;

public class FowlerDslXtextExpressionAssignCategory extends
		AbstractXtextExpressionAssignCategory {

//	public FowlerDslXtextExpressionAssignCategory(
//			BPELPropertySection ownerSection) {
//		super(ownerSection);
//	}

	@Override
	public String getName() {
		return "Xtext FowlerDsl Editor";
	}

	public Injector getInjector() {
		return StatemachineActivator
				.getInstance()
				.getInjector(
						StatemachineActivator.ORG_ECLIPSE_XTEXT_EXAMPLE_FOWLERDSL_STATEMACHINE);
	}
	
	@Override
	public String getExpressionLanguage() {
		return "fowler";
	}

}
