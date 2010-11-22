/*
 * Copyright (c) 2010 JBoss, Inc. and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.bpel.ui.dialogs;

import org.eclipse.bpel.ui.details.providers.XSDTypeOrElementContentProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/*
 * New class added to support browsing for XSD Elements only.
 * This dialog extends the general-purpose Type Selector but limits the selection
 * to known XSD Elements only. This makes for a less confusing user experience.
 *
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=330813
 * @see https://jira.jboss.org/browse/JBIDE-7107
 * @author Bob Brodt
 * @date Oct 12, 2010
 */
public class XSDElementSelectorDialog extends TypeSelectorDialog {

	public XSDElementSelectorDialog(Shell parent, EObject eObj) {
		super(parent, eObj);
		FILTER_TYPES = XSDTypeOrElementContentProvider.INCLUDE_ELEMENT_DECLARATIONS;
		showMessages = false;
	}
	
	public void setRequireElementSelection(boolean enabled)
	{
		this.requireLowerTreeSelection = enabled;
	}

	@Override
	protected Control createContents(Composite parent) {
		// TODO Auto-generated method stub
		Control ctl = super.createContents(parent);
		checkButtonGroup.setEnabled(false);
		for (Control c : checkButtonGroup.getChildren())
			c.setEnabled(false);
		return ctl;
	}

}
