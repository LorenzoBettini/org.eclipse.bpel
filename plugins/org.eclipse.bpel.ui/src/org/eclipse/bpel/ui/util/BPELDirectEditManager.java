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
package org.eclipse.bpel.ui.util;

import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.bpel.ui.editparts.BPELEditPart;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;


public class BPELDirectEditManager extends DirectEditManager {

	Font scaledFont;
	
	/** Indicates if we are in the middle of commiting. */
	private boolean committing = false;
	
	/** Validates user input. Provided by ctor. */
	private IInputValidator validator;

	public BPELDirectEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator, IInputValidator validator) {
		super(source, editorType, locator);
		this.validator = validator;
	}

	protected void bringDown() {
		//This method might be re-entered when super.bringDown() is called.
		Font disposeFont = scaledFont;
		scaledFont = null;
		super.bringDown();
		if (disposeFont != null)
			disposeFont.dispose();
	}

	protected void commit() {
		if (validator == null)
			super.commit();
		if (committing)
			return;
		committing = true;
		try {
			CellEditor cellEditor = getCellEditor();
			if (cellEditor == null) {
				super.commit();
			} else {
				Text text = (Text) getCellEditor().getControl();
				String newValue = text.getText();
				String validationMessage = validator.isValid(newValue);
				if (validationMessage != null) {
					MessageBox dialog = new MessageBox(text.getShell(), SWT.ICON_ERROR | SWT.OK);
					String message = Messages.BPELEditManager_RenameError; 
					dialog.setText(message);
					message = NLS.bind(Messages.BPELEditManager_RenameErrorMessage, (new Object[] {message, validationMessage})); 
					dialog.setMessage(message);
					dialog.open();
				} else {
					super.commit();
				}
			}
		} finally {
			bringDown();
			committing = false;
		}
	}

	protected void initCellEditor() {
		getEditPart().getFigure().validate();
		Object model = getEditPart().getModel();
		ILabeledElement labeledElement = (ILabeledElement)BPELUtil.adapt(model, ILabeledElement.class);
		String initialLabelText = labeledElement.getLabel(model);
		getCellEditor().setValue(initialLabelText);
		Text text = (Text) getCellEditor().getControl();
		IFigure figure = getEditPart().getFigure();
		scaledFont = figure.getFont();
		FontData data = scaledFont.getFontData()[0];
		Dimension fontSize = new Dimension(0, data.getHeight());
		Label label = ((BPELEditPart) getEditPart()).getLabelFigure();
		label.translateToAbsolute(fontSize);
		data.setHeight(fontSize.height);
		scaledFont = new Font(null, data);

		text.setFont(scaledFont);
		text.selectAll();
	}
}
