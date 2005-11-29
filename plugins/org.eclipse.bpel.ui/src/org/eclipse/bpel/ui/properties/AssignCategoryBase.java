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

import org.eclipse.bpel.common.ui.command.ICommandFramework;
import org.eclipse.bpel.common.ui.details.ChangeHelper;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.BPELTabbedPropertySheetPage;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.commands.SetCopyFromCommand;
import org.eclipse.bpel.ui.commands.SetCopyToCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetPage;


/**
 * An IDetailsSection representing a panel of widgets for one specifying one kind
 * of From/To contents.  Subclasses of AssignCategory can answer whether they apply
 * to the contents of a particular From or To, and they provide widgets specific to
 * that kind of contents.
 * 
 * @see AssignImplSection.categories
 */
public abstract class AssignCategoryBase extends BPELPropertySection implements IAssignCategory {

	protected boolean isFrom;
	protected BPELPropertySection ownerSection;

	protected Copy modelCopy;
	protected Composite composite;
	protected ChangeHelper changeHelper;
	
	protected AssignCategoryBase(boolean isFrom, BPELPropertySection ownerSection) {
		this.isFrom = isFrom;
		this.ownerSection = ownerSection;
	}

	/**
	 * Initialize the widgets from the state in the toOrFrom object.
	 * The toOrFrom object may contain additional state which isn't used by the widgets
	 * of this category; this state will be preserved in the model unless the user changes
	 * something in the category's widgets and causes a storeIntoModel() to occur.
	 * 
	 * If isFrom is true, toOrFrom will be a From object, otherwise it will be a To object.
	 */
	protected abstract void loadToOrFrom(To toOrFrom);

	/**
	 * Store the state represented by the widgets into the toOrFrom object.
	 * If isFrom is true, toOrFrom will be a From object, otherwise it will be a To object.
	 */
	protected abstract void storeToOrFrom(To toOrFrom);

	// This is used by changeHelper to determine what shows up in Undo/Redo.
	// The return value is FlatFormatted with getName() as the only argument.
	// Subclasses may override.
	protected String getLabelFlatFormatString() {
		return IBPELUIConstants.FORMAT_CMD_SELECT;
	}
	
	protected ChangeHelper getChangeHelper() { return changeHelper; }

	/**
	 * Policy: wrap the command with contexts from the ownerSection (rather
	 * than from the category itself).  On undo, the ownerSection will delegate
	 * to the category's methods. 
	 */
	protected Command wrapInShowContextCommand(Command inner) {
		return super.wrapInShowContextCommand(inner, ownerSection);
	}
	
	protected boolean isToOrFromAffected(Notification n) {
		if (isFrom) {
			return (n.getFeatureID(Copy.class) == BPELPackage.COPY__FROM);
		}
		return (n.getFeatureID(Copy.class) == BPELPackage.COPY__TO);
	}

	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (isToOrFromAffected(n))  updateCategoryWidgets();
				}
			},
		};
	}

	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		// hack - we have to do this in order to get the command framework before calling super.createControls
		ICommandFramework commandFramework = ((BPELTabbedPropertySheetPage)aTabbedPropertySheetPage).getEditor().getCommandFramework();
		
		changeHelper = new ChangeHelper(commandFramework) {
			public String getLabel() {
				return BPELUtil.formatString(getLabelFlatFormatString(), getName());
			}
			public Command createApplyCommand() {
				return wrapInShowContextCommand(newStoreModelCommand());
			}
			public void restoreOldState() {
				updateCategoryWidgets();
			}
		};
		
		super.createControls(parent, aTabbedPropertySheetPage);
		
//		createClient(parent);
//		parent.addDisposeListener(new DisposeListener() {
//			public void widgetDisposed(DisposeEvent e) {
//				removeAllAdapters();
//			}
//		});
	}

	protected void basicSetInput(EObject newInput) {
		super.basicSetInput(newInput);
		this.modelCopy = (Copy)newInput;
	}

	protected void updateCategoryWidgets() {
		if (isFrom) {
			loadToOrFrom((modelCopy==null)? null : modelCopy.getFrom());
		} else {
			loadToOrFrom((modelCopy==null)? null : modelCopy.getTo());
		}
	}

	public Command newStoreModelCommand()  {
		if (modelCopy == null)  return null;
		if (isFrom) {
			From newFrom = BPELFactory.eINSTANCE.createFrom();
			storeToOrFrom(newFrom);
			return new SetCopyFromCommand(modelCopy, newFrom);
		}
		To newTo = BPELFactory.eINSTANCE.createTo();
		storeToOrFrom(newTo);
		return new SetCopyToCommand(modelCopy, newTo);
	}

	protected boolean isDefaultCompositeOpaque() { return true; }

	protected final void createClient(Composite parent) {
		// ugly HACK to make subclasses work
		FlatFormLayout layout = new FlatFormLayout();
		layout.marginHeight = layout.marginWidth = 0;
		parent.setLayout(layout);
		createClient2(parent);
	}
		
	/**
	 * Override this method.  Call this version if the subclass wants its own composite
	 * with margin and black border. 
	 */
	protected void createClient2(Composite parent) {
		if (isDefaultCompositeOpaque()) {
			composite = wf.createPlainComposite(parent, SWT.NONE);
		} else {
			composite = wf.createComposite(parent);
		}
		FlatFormLayout layout = new FlatFormLayout();
		layout.marginWidth = 0;//IDetailsAreaConstants.HMARGIN;
		layout.marginHeight = 0;//IDetailsAreaConstants.VMARGIN;
		composite.setLayout(layout);
		FlatFormData data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		data.bottom = new FlatFormAttachment(100, 0);
//		data.borderType = IBorderConstants.BORDER_1P1_BLACK;
		composite.setLayoutData(data);
	}

	public void refresh() {
		super.refresh();
		updateCategoryWidgets();
	}

	public void aboutToBeHidden() {
		if (isCreated) {
			// hack!
			ChangeHelper changeHelper = getChangeHelper();
			if (changeHelper != null) getCommandFramework().notifyChangeDone(changeHelper);
		}
		super.aboutToBeHidden();
	}
	
	/**
	 * This is just a workaround to keep the AssignCategory from changing too much.
	 */
	public void setInput(EObject model) {
		this.modelObject = model;
		basicSetInput(model);
	}
	
	public boolean isHidden() { return isHidden; }
}
