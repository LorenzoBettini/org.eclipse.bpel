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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.common.ui.composite.EditorInViewManager;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;



public abstract class AbstractExpressionEditor implements IExpressionEditor {

	protected List listeners = new ArrayList();
	protected EditorInViewManager manager;
	protected BPELPropertySection section;
	private Object modelObject;
	private String exprType, exprContext;

	public void createControls(Composite parent, BPELPropertySection aSection) {
		this.section = aSection;
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	protected void notifyListeners() {
		Iterator it = listeners.iterator();
		while (it.hasNext()) {
			((Listener)it.next()).notifyChanged();
		}
	}
	
	public abstract Object getBody();

	/**
	 * Implementors of this method should call refresh after
	 * setting the body.
	 */
	public abstract void setBody(Object body);

	protected void refresh() {
	}

	public void addExtraStoreCommands(CompoundCommand compoundCommand) {
		// Default is to do nothing.
	}

	protected IEditorPart createEditor(String editorID, IEditorInput input, Composite parent) {
		try {
			return getEditorManager().createEditor(editorID, input, parent);
		} catch (CoreException e) {
			BPELUIPlugin.log(e);
		}
		return null;
	}

	protected EditorInViewManager getEditorManager() {
		if (manager == null) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
			IViewPart view = page.findView(IBPELUIConstants.PROPERTY_VIEW_ID);
			try {
				if (view == null) {
					view = page.showView(IBPELUIConstants.PROPERTY_VIEW_ID);
				}
				manager = new EditorInViewManager(view.getViewSite());
			} catch (PartInitException e) {
				BPELUIPlugin.log(e);
			}
		}
		return manager;
	}
	
	public BPELPropertySection getSection() {
		return section;
	}
	public void setExpressionType(String exprType, String exprContext) {
	    this.exprType = exprType;
	    this.exprContext = exprContext;
	}
	public void setModelObject(Object modelObject) {
	    this.modelObject = modelObject;
	}

	protected String getExprContext() { return exprContext; }
	protected String getExprType() { return exprType; }
	protected Object getModelObject() { return modelObject; }
	
	protected TabbedPropertySheetWidgetFactory getWidgetFactory() {
	    return section.getWidgetFactory();
	}
}
