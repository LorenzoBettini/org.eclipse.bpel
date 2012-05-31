package org.eclipse.bpel.ui.properties.xtext;

import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.xtext.example.arithmetics.ui.internal.ArithmeticsActivator;

import com.google.inject.Injector;

public class ArithmeticsXtextExpressionAssignCategory extends
		AbstractXtextExpressionAssignCategory {

	public ArithmeticsXtextExpressionAssignCategory(
			BPELPropertySection ownerSection) {
		super(ownerSection);
	}

	@Override
	public String getName() {
		return "Xtext Arithmetics Editor";
	}

	public Injector getInjector() {
		return ArithmeticsActivator
				.getInstance()
				.getInjector(
						ArithmeticsActivator.ORG_ECLIPSE_XTEXT_EXAMPLE_ARITHMETICS_ARITHMETICS);
	}
}
