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
package org.eclipse.bpel.ui.expressions;

import org.eclipse.bpel.ui.editors.TextEditor;
import org.eclipse.bpel.ui.editors.TextEditorInput;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.bpel.ui.properties.TextSection;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;


/**
 * When an editor is not found for a given expression language this
 * default editor is used instead.
 * 
 * TODO: reconcile differences between this class and XPathExpressionEditor ?
 */
public class DefaultExpressionEditor extends AbstractExpressionEditor {

	protected Composite editorComposite;
	protected TextEditor editor;
	protected IPropertyListener propertyListener;
	protected String undoRedoLabel;
	protected Object body;
	protected boolean updating = false;
	
	public Object getBody() {
		return editor.getContents();
	}

	public void createControls(Composite parent, BPELPropertySection aSection) {
		super.createControls(parent, aSection);
		createEditor(parent);
	}

	public void setBody(Object body) {
	    this.body = body;
	    updating = true;
	    try {
	    	editor.setInput(new TextEditorInput((String)body, getModelObject() ));
	    } finally {
	    	updating = false;
	    }
	}
	
	protected void createEditor(Composite parent) {
		IEditorInput input = new TextEditorInput((String)body, getModelObject() );
		
		//editorComposite = new Composite(parent, SWT.NONE);
		TabbedPropertySheetWidgetFactory wf = getWidgetFactory();
		editorComposite = BPELUtil.createBorderComposite(parent, wf);
		//editorComposite.setLayout(new FillLayout());
		editor = (TextEditor) createEditor(TextEditor.EDITOR_ID, input, editorComposite);
	}

	public void dispose() {
		disposeEditor();
	}
	
	protected void disposeEditor() {
		if (editor != null) {
			getEditorManager().disposeEditor(editor);
			if (editorComposite != null && !editorComposite.isDisposed()) {
				editorComposite.dispose();
				editorComposite = null;
			}
			editor = null;
		}
	}

	public void aboutToBeHidden() {
		editor.removePropertyListener(getPropertyListener());
	}

	public void aboutToBeShown() {
		editor.addPropertyListener(getPropertyListener());
	}

	/**
	 * If the editor is dirty it registers an ongoing change.
	 */
	protected IPropertyListener getPropertyListener() {
		if (propertyListener == null) {
			propertyListener = new IPropertyListener() {
				public void propertyChanged(Object source, int propId) {
					if (!updating && propId == IEditorPart.PROP_DIRTY && editor.isDirty() && !((TextSection)section).isExecutingStoreCommand()) {
						notifyListeners();
					}
				}
			};
		}
		return propertyListener;
	}

	public Object getUserContext() {
		return null;
	}

	public void restoreUserContext(Object userContext) {
		editor.setFocus();
	}

	public Object getDefaultBody() {
		return ""; //$NON-NLS-1$
	}
	
	public void gotoTextMarker(IMarker marker, String codeType, Object modelObject) {
		// TODO
	}
	
	public boolean supportsExpressionType(String exprType, String exprContext) {
		if (IEditorConstants.ET_BOOLEAN.equals(exprType)) return true;
		if (IEditorConstants.ET_DATETIME.equals(exprType)) return true;
		if (IEditorConstants.ET_DURATION.equals(exprType)) return true;
		return false;
	}
	
	public void markAsClean() {
		editor.markAsClean();
	}
}
