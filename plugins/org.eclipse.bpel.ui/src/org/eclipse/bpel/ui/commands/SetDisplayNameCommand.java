/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.commands;

import java.util.Collections;

import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.adapters.INamedElement;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;


/** 
 * Sets the display name of the target object. 
 * TODO: Display Name should probably be renamed to "Label", as that's
 * a more concise name for it.
 */
public class SetDisplayNameCommand extends SetCommand {

	// override display name setting to also set the name if possible

	private String oldNameValue;
	private String newNameValue;
	
	public String getDefaultLabel() { return IBPELUIConstants.CMD_EDIT_DISPLAYNAME; }

	public SetDisplayNameCommand(EObject target, String newDisplayName)  {
		super(target, newDisplayName);
		// TODO
//		if (!ModelHelper.supportsUIExtensionDisplayName(target)) {
//			bpelExtensionCmd = new SetBPELExtensionDisplayNameCommand(target, newDisplayName);
//		}
	}

	public Object get() {
		return ModelHelper.getDisplayName(target);
	}
	public void set(Object o) {
		ModelHelper.setBPELUIExtensionDisplayName(target, (String)o);
	}

	protected boolean hasNoEffect() {
//		if (bpelExtensionCmd != null) return bpelExtensionCmd.hasNoEffect();
		return super.hasNoEffect();
	}
	
	public boolean canDoExecute() {
//		if (bpelExtensionCmd != null) return bpelExtensionCmd.canExecute();
		return super.canDoExecute();
	}
	
	public void doExecute() {
//		if (bpelExtensionCmd != null) { 
//			bpelExtensionCmd.execute();
//		} else {
			super.doExecute();
//		}
		INamedElement namedElement = (INamedElement) BPELUtil.adapt(target, INamedElement.class);
		if (namedElement != null) {
			oldNameValue = namedElement.getName(target);
			newNameValue = oldNameValue;

			if (newValue == null || ((String) newValue).length() == 0) {
				// get the label
				ILabeledElement le =
					(ILabeledElement) BPELUtil.adapt(target, ILabeledElement.class);
				if (le != null)
					newValue = le.getTypeLabel(target);
			}
			newNameValue =
				BPELUtil.getUniqueModelName(ModelHelper.getProcess(target), (String) newValue,
					Collections.singletonList(target));

			namedElement.setName(target, newNameValue);
		}
	}
}
