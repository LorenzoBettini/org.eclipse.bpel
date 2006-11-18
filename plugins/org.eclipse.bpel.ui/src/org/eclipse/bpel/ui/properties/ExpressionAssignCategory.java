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

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetExpressionCommand;
import org.eclipse.bpel.ui.expressions.IEditorConstants;
import org.eclipse.bpel.ui.util.BatchedMultiObjectAdapter;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


/**
 * TODO: when you first set the model object into the details page, it should query the
 * sections to find out which one thinks it "owns" the model object.  If NONE of them
 * claim to own it, then we will use whatever combo selection is stored in the transient
 * CopyExtension.  The same procedure is followed when refreshing the contents of the
 * page.
 * 
 * When you select a *different* category in the combo, we must update the value in the
 * CopyExtension.  We should also replace the existing Copy with an *empty* Copy.
 * 
 * Categories should become responsible for storing the value into the model themselves.
 */
public class ExpressionAssignCategory extends ExpressionSection implements IAssignCategory {

	protected String getExpressionType() { return IEditorConstants.ET_ASSIGNFROM; }
	protected String getExpressionContext() { return IEditorConstants.EC_ASSIGNFROM; }

	public boolean isHidden() { return isHidden; }

	protected boolean isFrom;
	protected BPELPropertySection ownerSection;

	protected Composite composite;
	
	protected ExpressionAssignCategory(boolean isFrom, BPELPropertySection ownerSection) {
		this.isFrom = isFrom;
		this.ownerSection = ownerSection;
	}

	// This is used by changeHelper to determine what shows up in Undo/Redo.
	// The return value is FlatFormatted with getName() as the only argument.
	// Subclasses may override.
	protected String getLabelFlatFormatString() {
		return IBPELUIConstants.FORMAT_CMD_SELECT;
	}

	protected boolean isToOrFromAffected(Notification n) {
		// hack:
		return true;
//		if (isFrom) {
//			return (n.getFeatureID(Copy.class) == BPELPackage.COPY__FROM);
//		}
//		return (n.getFeatureID(Copy.class) == BPELPackage.COPY__TO);
	}

	protected MultiObjectAdapter[] createAdapters() {
		MultiObjectAdapter adapter = new BatchedMultiObjectAdapter() {
			boolean needRefresh = false;
			boolean toOrFromAffected = false;
			public void notify(Notification n) {
				needRefresh = isBodyAffected(n);
				
				// if (isBodyAffected(n) && !isExecutingStoreCommand) needRefresh = true;
				if (isToOrFromAffected(n)) toOrFromAffected = true;
				refreshAdapters();
			}
			public void finish() {
				if (needRefresh || toOrFromAffected) {
					updateWidgets();
				}
				if (toOrFromAffected) {
					updateCategoryWidgets();
				}
				toOrFromAffected = false;
				needRefresh = false;
			}
		};
		return new MultiObjectAdapter[] { adapter };
	}

	protected void addAllAdapters() {
		// HACK because of the hack in getModel(): get the Copy containing our From
		super.addAllAdapters();
		adapters[0].addToObject(/*(Copy)*/modelObject);
	}
	
	protected void updateCategoryWidgets() {
		updateEditor();
	}

	/**
	 * Policy: wrap the command with contexts from the ownerSection (rather
	 * than from the category itself).  On undo, the ownerSection will delegate
	 * to the category's methods. 
	 */
	protected Command wrapInShowContextCommand(Command inner) {
		return super.wrapInShowContextCommand(inner, ownerSection);
	}
	
	public String getName() { return Messages.ExpressionAssignCategory_Expression_1; } 

	public boolean isCategoryForModel(To toOrFrom) {
		if (!isFrom || toOrFrom == null)  return false;
		From from = (From)toOrFrom;
		if (from.getExpression() != null)  return true;
		return false;
	}

	protected Command newStoreToModelCommand(Object body) {
		CompoundCommand result = new CompoundCommand();
		// If there is no condition, create one.
		Expression oldExp = getExprFromModel();
		Expression exp = BPELFactory.eINSTANCE.createCondition();
		// Don't set the language, because if the user has changed the
		// language, a condition would already exist at this point.
		if (oldExp != null) {
			exp.setExpressionLanguage(oldExp.getExpressionLanguage());
		}
		exp.setBody(body);
		result.add(new SetExpressionCommand(getInput(), getModelExpressionType(),
			getModelExpressionSubType(), exp));

		editor.addExtraStoreCommands(result);
		return result;
	}

	/**
	 * This is just a workaround to keep the AssignCategory from changing too much.
	 */
	public void setInput(EObject model) {
		basicSetInput(model);
		addAllAdapters();
	}
	
	protected final void createClient(Composite parent) {
		// ugly HACK to make subclasses work
		//FlatFormLayout layout = new FlatFormLayout();
		//layout.marginHeight = layout.marginWidth = 0;
		//parent.setLayout(layout);
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = fillLayout.marginWidth = 0;
		parent.setLayout(fillLayout);
		super.createClient(parent);
		
		
	}
	
	// HACK
	protected EObject getModel() {
		if (modelObject == null) return null;
		return ((Copy)modelObject).getFrom();
	}
}
