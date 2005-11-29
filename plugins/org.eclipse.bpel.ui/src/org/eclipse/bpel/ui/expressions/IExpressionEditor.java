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

import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.core.resources.IMarker;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.widgets.Composite;


public interface IExpressionEditor {
	
	public void gotoTextMarker(IMarker marker, String codeType, Object modelObject);

	/**
	 * Creates the editor widgets.
	 */
	void createControls(Composite parent, BPELPropertySection section);

	void dispose();

	/**
	 * Notifies the editor that its controls are about to be hidden.
	 */
	void aboutToBeHidden();

	/**
	 * Notifies the editor that its controls are about to be shown.
	 * Any widget state could be updated at this point.
	 */
	void aboutToBeShown();
	
	/**
	 * This method is used just like IDetailsSection.getUserContext().
	 * 
	 * @see IDetailsSection.getUserContext()
	 */
	Object getUserContext();
	
	/**
	 * This method is used just like IDetailsSection.restoreUserContext().
	 * 
	 * @see IDetailsSection.restoreUserContext(Object)
	 */
	void restoreUserContext(Object userContext);
	
	/**
	 * Return the body
	 */
	Object getBody();
	
	/**
	 * Set the body
	 */
	void setBody(Object body);
	
	void addListener(Listener listener);
	void removeListener(Listener listener);
	
	public static interface Listener {
		public void notifyChanged();
	}
	
	void addExtraStoreCommands(CompoundCommand compoundCommand);
	
	public Object getDefaultBody();	
	
	/**
	 * Returns true if this editor supports this expression type in the given context.
	 * 
	 * Most editors will be able to support a given type of expression (e.g. boolean)
	 * in any context, in which case they can just ignore the exprContext parameter.
	 */
	boolean supportsExpressionType(String exprType, String exprContext);
	
	/**
	 * Informs the editor of the expression type it will be editing, and the context
	 * in which it will be editing the expression.  The supportsExpressionType()
	 * method will always be called first to make sure the editor actually supports
	 * this exprType/exprContext combination.
	 */
	void setExpressionType(String exprType, String exprContext);

	/**
	 * Informs the editor of the underlying model object in which this expression
	 * will be stored.  The model object's type and relationship to other objects
	 * may depend on the expression type and context provided above (for example: the
	 * model for a transition condition is a Link, but the model for a join condition
	 * is an Activity). 
	 */
	void setModelObject(Object modelObject);
	
	/**
	 * Tells the editor to consider itself clean.
	 */
	void markAsClean();
}
