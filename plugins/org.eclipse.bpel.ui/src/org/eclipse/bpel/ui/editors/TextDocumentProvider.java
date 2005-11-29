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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.AbstractDocumentProvider;

/**
 * Document provider for TextEditor.
 */
public class TextDocumentProvider extends AbstractDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		if (element instanceof TextEditorInput) {
			TextEditorInput input = (TextEditorInput) element;
			Document doc = new Document(input.getBody());
			return doc;
		}
		return null;
	}

	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		return null;
	}

	/**
	 * Does not do anything. For this editor we do not have a save concept. All the changes
	 * are stored in a certain model object and saved in a model file.
	 */
	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {
	}

	public boolean isReadOnly(Object element) {
		return false;
	}

	public boolean isModifiable(Object element) {
		return true;
	}

	protected IRunnableContext getOperationRunner(IProgressMonitor monitor) {
		// TODO Need to implement for 6.0
		return null;
	}
}
