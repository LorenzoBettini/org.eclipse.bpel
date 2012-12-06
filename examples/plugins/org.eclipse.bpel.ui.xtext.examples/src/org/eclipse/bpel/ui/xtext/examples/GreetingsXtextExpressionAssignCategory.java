package org.eclipse.bpel.ui.xtext.examples;

import org.eclipse.bpel.ui.properties.xtext.AbstractXtextExpressionAssignCategory;

import com.google.inject.Injector;

/**
 * @author Lorenzo Bettini
 *
 */
public class GreetingsXtextExpressionAssignCategory extends
		AbstractXtextExpressionAssignCategory {

//	public GreetingsXtextExpressionAssignCategory(
//			BPELPropertySection ownerSection) {
//		super(ownerSection);
//	}

	@Override
	public String getName() {
		return "Xtext Greeting Editor";
	}

	public Injector getInjector() {
		return org.xtext.example.mydsl.ui.internal.MyDslActivator.getInstance()
				.getInjector("org.xtext.example.mydsl.MyDsl");
	}
	
	@Override
	public String getExpressionLanguage() {
		return "greetings";
	}

	@Override
	protected String getStyledTextLabelString() {
		return "Edit the greeting expression";
	}

}
