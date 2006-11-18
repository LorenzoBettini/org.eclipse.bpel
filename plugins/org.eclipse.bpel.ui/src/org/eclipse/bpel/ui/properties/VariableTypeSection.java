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
package org.eclipse.bpel.ui.properties;

import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.commands.SetVariableKindCommand;
import org.eclipse.bpel.ui.commands.SetVariableTypeCommand;
import org.eclipse.bpel.ui.uiextensionmodel.VariableExtension;
import org.eclipse.bpel.ui.util.BatchedMultiObjectAdapter;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.wsdl.Message;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;


/**
 * VariableTypeSection provides viewing and editing of the type of a BPEL variable
 * (whether that be an XSD type, WSDL message, or built-in simple type).
 */
public class VariableTypeSection extends BPELPropertySection {

	/**
	 * Make this section use all the vertical space it can get. 
	 * 
	 */
	@Override
	public boolean shouldUseExtraSpace() { 
		return true;
	}
	
	protected VariableTypeSelector variableTypeSelector;
	
	protected Composite parentComposite;
	

	protected boolean isMessageTypeAffected(Notification n) {
		return (n.getFeatureID(Variable.class) == BPELPackage.VARIABLE__MESSAGE_TYPE);
	}
	
	protected boolean isTypeAffected(Notification n) {
		return (n.getFeatureID(Variable.class) == BPELPackage.VARIABLE__TYPE);
	}
	
	protected boolean isElementAffected(Notification n) {
		return (n.getFeatureID(Variable.class) == BPELPackage.VARIABLE__ELEMENT);
	}
	
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new BatchedMultiObjectAdapter() {
				boolean update = false;
				public void notify(Notification n) {
					if (isMessageTypeAffected(n)) update = true;
					if (isTypeAffected(n)) update = true;
					if (isElementAffected(n)) update = true;
					if (n.getNotifier() instanceof VariableExtension) update = true;
				}
				public void finish() {
					if (update) {
						updateVariableTypeSelector();
					}
					update = false;
				}
			}
		};
	}

	protected void addAllAdapters() {
		super.addAllAdapters();
		VariableExtension varExt = (VariableExtension)ModelHelper.getExtension(getInput());
		if (varExt != null) adapters[0].addToObject(varExt);
	}
	
	public class VariableTypeCallback implements VariableTypeSelector.Callback {
		public void selectRadioButton(int index) {
			Variable var = (Variable)getInput();
			VariableExtension varExt = (VariableExtension)ModelHelper.getExtension(var);
			CompoundCommand command = new CompoundCommand();
			if ((var.getMessageType() != null) || (var.getType() != null) || (var.getXSDElement()) != null) {
				command.add(new SetVariableTypeCommand(var, (Message)null));
			}
			if ((varExt.getVariableKind() != index)) {
				command.add(new SetVariableKindCommand(varExt, index));
			}
			if (!command.isEmpty()) getCommandFramework().execute(wrapInShowContextCommand(command));
		}
		public void selectXSDType(XSDTypeDefinition xsdType) {
			Variable variable = (Variable)getInput();
	    	Command cmd = new SetVariableTypeCommand(variable, xsdType);
			getCommandFramework().execute(wrapInShowContextCommand(cmd));
		}
		public void selectXSDElement(XSDElementDeclaration xsdElement) {
			Variable variable = (Variable)getInput();
	    	Command cmd = new SetVariableTypeCommand(variable, xsdElement);
			getCommandFramework().execute(wrapInShowContextCommand(cmd));
		}
		public void selectMessageType(Message message) {
			Variable variable = (Variable)getInput();
			Command cmd = new SetVariableTypeCommand(variable, message);
			getCommandFramework().execute(wrapInShowContextCommand(cmd));
		}
	}
	
	protected void createClient(Composite parent) {
		Composite composite = parentComposite = createFlatFormComposite(parent);
		
		variableTypeSelector = new VariableTypeSelector(composite, SWT.NONE, getBPELEditor(),
			wf, new VariableTypeCallback(), true);
		FlatFormData data = new FlatFormData();
		data.top = new FlatFormAttachment(0,0);
		data.left = new FlatFormAttachment(0,0);
		data.right = new FlatFormAttachment(100,0);
		data.bottom = new FlatFormAttachment(100,0);
		variableTypeSelector.setLayoutData(data);
	}
	
	public void updateVariableTypeSelector() {
		variableTypeSelector.setVariable((Variable)getInput());
		//variableTypeSelector.refresh();
	}
	
	
	protected void basicSetInput(EObject newInput) {
		super.basicSetInput(newInput);
		updateVariableTypeSelector();
	}


	public Object getUserContext() {
		return variableTypeSelector.getUserContext();
	}
	public void restoreUserContext(Object userContext) {
		variableTypeSelector.restoreUserContext(userContext);
	}
}
