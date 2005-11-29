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

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.ui.expressions.IExpressionEditor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;


/**
 * An abstract ExpressionDetails which supports the selection of one or more
 * radio buttons, and passes the IDs of the selected buttons to the expression
 * editor as context info. 
 */
public abstract class RadioChoiceExpressionSection extends ExpressionSection {

	protected Composite radioComposite;
	protected Button[] radioButtons;
	
	protected int currentButtonIndex;
	
	protected String getExpressionType() { return getButtonExprType(currentButtonIndex); }
	protected String getExpressionContext() { return getButtonExprContext(currentButtonIndex); }
	
	protected abstract String[] getButtonLabels();
	protected abstract String getButtonExprType(int buttonIndex);
	protected abstract String getButtonExprContext(int buttonIndex);
	
	protected abstract int getButtonIndexFromModel();
	
	protected void basicSetInput(EObject newInput) {
		super.basicSetInput(newInput);
		currentButtonIndex = getButtonIndexFromModel();
	}
	
	// TODO: call this at the proper time
	protected void calculateEnablement() {
		IExpressionEditor editor = getExpressionEditor();
		if (editor == null) throw new IllegalStateException();
		for (int i = 0; i<radioButtons.length; i++) {
			String btnType = getButtonExprType(i);
			String btnContext = getButtonExprContext(i);
			boolean enabled = editor.supportsExpressionType(btnType, btnContext);
			radioButtons[i].setEnabled(enabled);
		}
	}
	
	protected void updateRadioButtonWidgets() {
		if (editor == null) {
			// We are probably looking at the noEditorComposite right now.
			// The radio buttons are hidden in this situation.
		} else {
			calculateEnablement();
			currentButtonIndex = getButtonIndexFromModel();
			if (currentButtonIndex >= 0) radioButtons[currentButtonIndex].setSelection(true);
			for (int i = 0; i<radioButtons.length; i++) {
				if (i != currentButtonIndex) radioButtons[i].setSelection(false);
			}
		}
	}
	
	protected void updateWidgets() {
		super.updateWidgets();
		updateRadioButtonWidgets();
	}
	
	protected void createRadioButtonWidgets(Composite parent) {
		FlatFormData data;
		radioComposite = createFlatFormComposite(parent);
		
		String[] labels = getButtonLabels();
		radioButtons = new Button[labels.length];
		FlatFormAttachment lastLeft = new FlatFormAttachment(0, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG);
		for (int i = 0; i<labels.length; i++) {
			radioButtons[i] = wf.createButton(radioComposite, labels[i], SWT.RADIO);
			data = new FlatFormData();
			data.left = lastLeft;
			data.top = new FlatFormAttachment(0, 0);
			radioButtons[i].setLayoutData(data);
			lastLeft = new FlatFormAttachment(radioButtons[i], IDetailsAreaConstants.HSPACE);
			final int index = i;
			radioButtons[i].addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					if (radioButtons[index].getSelection()) {
						currentButtonIndex = index;
						String newExprType = getButtonExprType(currentButtonIndex);
						String newExprContext = getButtonExprContext(currentButtonIndex);
						Object defaultBody = getDefaultBody(
							editorLanguage,	newExprType, newExprContext);
						// hack?
						CompoundCommand result = new CompoundCommand() {
						    public void execute() {
						        isExecutingStoreCommand = true;
						        try {
						            super.execute();
						        } finally {
						            isExecutingStoreCommand = false;
						        }
		                    }
						};
						result.add(newStoreToModelCommand(defaultBody));
						getCommandFramework().execute(wrapInShowContextCommand(result));
						refresh();
						// TODO!
					}
				}
				public void widgetDefaultSelected(SelectionEvent e) { }
			});
			
		}
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(expressionLanguageCCombo, IDetailsAreaConstants.VSPACE + 2);
		radioComposite.setLayoutData(data);
	}

	protected void createClient(Composite parent) {
		super.createClient(parent);
		createRadioButtonWidgets(parentComposite);
	}
	
	protected boolean showRadioButtons() { return !hasNoEditor; }
	
    protected void createEditorArea(Composite parent) {
        super.createEditorArea(parent);
        boolean showRadio = showRadioButtons();
	    radioComposite.setVisible(showRadio);
        if (showRadio) {
    	    FlatFormData data = (FlatFormData)editorAreaComposite.getLayoutData();
    	    data.top = new FlatFormAttachment(radioComposite, IDetailsAreaConstants.VSPACE);
    	    parentComposite.layout(true);
        }
    }

	/**
	 * Allow the editor in the combo if *any* of the radio buttons would be supported
	 * by that editor.  (calculateEnabled() will decide which radio buttons to actually
	 * enable for the selected editor).
	 */
	protected boolean isEditorSupported(IExpressionEditor editor) {
		for (int i = 0; i<radioButtons.length; i++) {
			String btnType = getButtonExprType(i);
			String btnContext = getButtonExprContext(i);
			if (editor.supportsExpressionType(btnType, btnContext)) return true;
		}
		return false;
	}
}
