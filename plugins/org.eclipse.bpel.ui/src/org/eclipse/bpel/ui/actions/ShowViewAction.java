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
package org.eclipse.bpel.ui.actions;

import org.eclipse.bpel.common.ui.CommonUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;


public class ShowViewAction extends Action {

	protected IWorkbenchPage page;

	protected String viewID;

	protected static ImageDescriptor enabledImage;

	protected static ImageDescriptor disabledImage;

	/**
	 *  
	 */
	public ShowViewAction() {
		super();
	}

	/**
	 * @param text
	 */
	public ShowViewAction(String text) {
		super(text);
	}

	/**
	 * @param text
	 * @param image
	 */
	public ShowViewAction(String text, ImageDescriptor image) {
		super(text, image);
	}

	/**
	 * @param text
	 * @param image
	 * @param page -
	 *            workbench page to open view on
	 * @param viewID -
	 *            ID of the view to open
	 */
	public ShowViewAction(String text, ImageDescriptor image, IWorkbenchPage page, String viewID) {
		super(text, image);
		setPage(page);
		setViewID(viewID);
	}

	/**
	 * @param text
	 * @param page -
	 *            workbench page to open view on
	 * @param viewID -
	 *            ID of the view to open
	 */
	public ShowViewAction(String text, IWorkbenchPage page, String viewID) {
		super(text);
		setPage(page);
		setViewID(viewID);
	}

	/**
	 * @param text
	 * @param style
	 */
	public ShowViewAction(String text, int style) {
		super(text, style);
	}

	/**
	 * @return Returns the page.
	 */
	public IWorkbenchPage getPage() {
		return page;
	}

	/**
	 * @param page
	 *            The page to set.
	 */
	public void setPage(IWorkbenchPage page) {
		this.page = page;
	}

	/**
	 * @return Returns the viewID.
	 */
	public String getViewID() {
		return viewID;
	}

	/**
	 * @param viewID
	 *            The viewID to set.
	 */
	public void setViewID(String viewID) {
		this.viewID = viewID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		try {
			if (page != null && viewID != null) page.showView(viewID);
		} catch (PartInitException pie) {
			CommonUIPlugin.log(pie);
		}
	}
}
