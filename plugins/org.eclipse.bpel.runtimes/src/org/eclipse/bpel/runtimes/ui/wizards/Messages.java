/**
 * 
 */
package org.eclipse.bpel.runtimes.ui.wizards;

import org.eclipse.osgi.util.NLS;

/**
 * Internationalization, internationalisation...
 *
 *
 * @author Bruno Wassermann, written Jun 30, 2006
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpel.runtimes.ui.wizards.messages"; //$NON-NLS-1$

	private Messages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	// new project wizard
	public static String NewProjectWizard_1;
	public static String NewProjectWizardPage1_1;
}
