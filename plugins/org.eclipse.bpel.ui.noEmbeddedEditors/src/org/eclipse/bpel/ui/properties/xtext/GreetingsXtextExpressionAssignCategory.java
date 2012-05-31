package org.eclipse.bpel.ui.properties.xtext;

import org.eclipse.bpel.ui.properties.BPELPropertySection;

import com.google.inject.Injector;

public class GreetingsXtextExpressionAssignCategory extends
		AbstractXtextExpressionAssignCategory {

	public GreetingsXtextExpressionAssignCategory(
			BPELPropertySection ownerSection) {
		super(ownerSection);
	}

	@Override
	public String getName() {
		return "Xtext Greeting Editor";
	}

	public Injector getInjector() {
		return org.xtext.example.mydsl.ui.internal.MyDslActivator.getInstance()
				.getInjector("org.xtext.example.mydsl.MyDsl");
	}
}
