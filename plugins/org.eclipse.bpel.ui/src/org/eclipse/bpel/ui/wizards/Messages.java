/**
 * 
 */
package org.eclipse.bpel.ui.wizards;

import org.eclipse.osgi.util.NLS;

/**
 * @author mchmiele
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.bpel.ui.wizards.messages"; //$NON-NLS-1$

	private Messages() {
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String NewFileWizard_1;
	public static String BPELCreateOperation_0;
	public static String NewFileWizardPage1_2;
	public static String NewFileWizardPage1_3;
	public static String NewFileWizardPage1_4;
	public static String NewFileWizardPage1_5;
	public static String NewFileWizardPage1_6;
	public static String NewFileWizardPage1_7;
	public static String NewFileWizardPage1_8;
	public static String NewFileWizardPage1_10;
}
