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
package org.eclipse.bpel.ui.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Text editor for IText objects.
 */

public class TextEditor extends AbstractDecoratedTextEditor {

	public static final String EDITOR_ID = "org.eclipse.bpel.ui.editors.TextEditor"; //$NON-NLS-1$

	/**
	 * 
	 */
	public TextEditor() {
		super();
		setDocumentProvider(new TextDocumentProvider());
		
	}

	/**
	 * Remove unnecessary actions.
	 */
	protected void createActions() {
		super.createActions();
		setAction(ITextEditorActionConstants.PRINT, null);
		setAction(ITextEditorActionConstants.SAVE, null);
		setAction(ITextEditorActionConstants.REVERT_TO_SAVED, null);
	}

	/**
	 * Returns the current contents of this editor.
	 * @return the contents of the editor
	 */
	
	public String getContents() {
		IEditorInput input = getEditorInput();
		IDocument doc = getDocumentProvider().getDocument(input);
		return doc.get();
	}

	/**
	 * Removes the dirty state.
	 */
	public void markAsClean() {
		// this is almost a no-op
		doSave(null);
	}
}