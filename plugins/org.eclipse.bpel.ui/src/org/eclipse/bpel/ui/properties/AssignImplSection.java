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

import java.util.List;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.AddCopyCommand;
import org.eclipse.bpel.ui.commands.RemoveCopyCommand;
import org.eclipse.bpel.ui.commands.SetCopyFromCommand;
import org.eclipse.bpel.ui.commands.SetCopyToCommand;
import org.eclipse.bpel.ui.uiextensionmodel.CopyExtension;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


/**
 * AssignImplDetails allows viewing and editing of the copy elements in an Assign.
 */
public class AssignImplSection extends BPELPropertySection {
	
	public static class AssignCategoryLabelProvider implements ILabelProvider {

		public String getText(Object element) {
			return ((IAssignCategory)element).getName();
		}
		public Image getImage(Object element) { return null; }
		public void dispose() { }
		public void addListener(ILabelProviderListener listener) { }
		public void removeListener(ILabelProviderListener listener) { }
		public boolean isLabelProperty(Object element, String property) { return true; }
	}
	
	public class CategoryInfo {
		public IAssignCategory category;
		public Composite composite;
		public CategoryInfo(IAssignCategory category) {
			this.category = category;
		}
	}
	CategoryInfo[][] categories = {
		/* From categories */ {
			new CategoryInfo(new NullAssignCategory(true, this)),
			new CategoryInfo(new VariablePartAssignCategory(true, this)),
			new CategoryInfo(new VariablePropertyAssignCategory(true, this)),
			new CategoryInfo(new PartnerRoleAssignCategory(true, this)),
			new CategoryInfo(new EndpointReferenceAssignCategory(true, this)),
			new CategoryInfo(new LiteralAssignCategory(true, this)),
			new CategoryInfo(new ExpressionAssignCategory(true, this)),
			new CategoryInfo(new OpaqueAssignCategory(true, this)),
		},
		/* To categories */ {
			new CategoryInfo(new NullAssignCategory(false, this)),
			new CategoryInfo(new VariablePartAssignCategory(false, this)),
			new CategoryInfo(new VariablePropertyAssignCategory(false, this)),
			new CategoryInfo(new PartnerRoleAssignCategory(false, this)),
		}
	};
	
	CCombo[] categoryCombo;
	Label[] categoryLabel;
	Composite[] outerComposite;
	int[] currentCategoryIndex;
	boolean isProgramSelection = false;
	boolean updatingCategories = false;
	boolean isComboStoreCommand = false;

	Copy currentCopy;
	int currentCopyIndex;
	org.eclipse.swt.widgets.List copyList;
	
	Composite copySelectComposite;
	Button insertCopy, deleteCopy;
	//Label selectedCopy;
	
	public AssignImplSection()  {
		super();
		categoryCombo = new CCombo[2];
		categoryLabel = new Label[2];
		outerComposite = new Composite[2];
		currentCategoryIndex = new int[2];
		currentCategoryIndex[0] = currentCategoryIndex[1] = -1;
	}
	
	protected boolean isCopyFromAffected(Notification n) {
		return (n.getFeatureID(Copy.class) == BPELPackage.COPY__FROM);
	}
	protected boolean isCopyToAffected(Notification n) {
		return (n.getFeatureID(Copy.class) == BPELPackage.COPY__TO);
	}

	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				public void notify(Notification n) {
					if (isCopyFromAffected(n) || isCopyToAffected(n)) {
						if (!isComboStoreCommand)  selectCategoriesForInput();
					} 
				}
			},
		};
	}

	protected void createCopySelectWidgets(Composite parent) {
		FlatFormData data;
		Composite c = copySelectComposite = createFlatFormComposite(parent);
		
		insertCopy = wf.createButton(c, Messages.AssignImplDetails_New__5, SWT.PUSH);  
		deleteCopy = wf.createButton(c, Messages.AssignImplDetails_Delete__6, SWT.PUSH);  

		copyList = wf.createList(c, SWT.READ_ONLY | SWT.BORDER | SWT.V_SCROLL);

		int preferredWidth = BPELUtil.calculateButtonWidth(insertCopy, SHORT_BUTTON_WIDTH);
		preferredWidth = Math.max(preferredWidth, BPELUtil.calculateButtonWidth(deleteCopy, SHORT_BUTTON_WIDTH));
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0,0);
		data.right = new FlatFormAttachment(0,preferredWidth);
		data.top = new FlatFormAttachment(0,0);
		data.bottom = new FlatFormAttachment(100,0);
		c.setLayoutData(data);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0,0);
		data.right = new FlatFormAttachment(100,0);
		data.top = new FlatFormAttachment(0,0);
		data.bottom = new FlatFormAttachment(insertCopy, -IDetailsAreaConstants.VSPACE);
		copyList.setLayoutData(data);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(deleteCopy, -IDetailsAreaConstants.VSPACE);
		insertCopy.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		deleteCopy.setLayoutData(data);

		insertCopy.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Copy copy = BPELFactory.eINSTANCE.createCopy();
				getCommandFramework().execute(wrapInShowContextCommand(
						new AddCopyCommand((Assign)getInput(), copy)));
				currentCopyIndex = ((Assign)getInput()).getCopy().size()-1; 
				selectCategoriesForInput();
			}
			public void widgetDefaultSelected(SelectionEvent e) { /* nothing */ }
		});

		deleteCopy.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				getCommandFramework().execute(wrapInShowContextCommand(
						new RemoveCopyCommand((Assign)getInput(), currentCopy)));
				currentCopyIndex = ((Assign)getInput()).getCopy().size()-1; 
				selectCategoriesForInput();
			}
			public void widgetDefaultSelected(SelectionEvent e) { /* nothing */ }
		});
	
		copyList.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				currentCopyIndex = copyList.getSelectionIndex();
				if (currentCopyIndex < 0) currentCopyIndex = 0;
				refresh();
			}
			public void widgetDefaultSelected(SelectionEvent e) { /* nothing */ }
		});

	}

	protected void createCategoryComposite(int fromOrTo, int index) {
		if (categories[fromOrTo][index].composite == null) {
			Composite c = createFlatFormComposite(outerComposite[fromOrTo]);
			FlatFormData data = new FlatFormData();
			data.left = new FlatFormAttachment(0,0);
			data.right = new FlatFormAttachment(100,0);
			data.top = new FlatFormAttachment(0,0);
			data.bottom = new FlatFormAttachment(100,0);
			c.setLayoutData(data);
			FillLayout fillLayout = new FillLayout();
			fillLayout.marginHeight = fillLayout.marginWidth = 0;
			c.setLayout(fillLayout);
			categories[fromOrTo][index].composite = c;
			categories[fromOrTo][index].category.createControls(c, getTabbedPropertySheetPage());
			outerComposite[fromOrTo].layout(true);
		}
	}
	
	protected void createCategoryComboWidgets(Composite composite, final int fromOrTo) {
		FlatFormData data;
		final boolean isFrom = (fromOrTo==0);
		
		categoryLabel[fromOrTo] = wf.createLabel(composite, isFrom?Messages.AssignImplDetails_From__1:Messages.AssignImplDetails_To__2); 
		categoryCombo[fromOrTo] = wf.createCCombo(composite);
		data = new FlatFormData();
		
		if (isFrom)  {
			data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(categoryLabel[fromOrTo], STANDARD_LABEL_WIDTH_SM));
			data.right = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE);
		} else {
			data.left = new FlatFormAttachment(50, IDetailsAreaConstants.CENTER_SPACE + BPELUtil.calculateLabelWidth(categoryLabel[fromOrTo], STANDARD_LABEL_WIDTH_SM));
			data.right = new FlatFormAttachment(100, 0);
		}
		// the following line is basically a hack
		// data.top = new FlatFormAttachment(copySelectComposite, IDetailsAreaConstants.VSPACE);
		data.top = new FlatFormAttachment(0,0);
		categoryCombo[fromOrTo].setLayoutData(data);
		
		data = new FlatFormData();
		if (isFrom)  {
			data.left = new FlatFormAttachment(0, 0);
		} else {
			data.left = new FlatFormAttachment(50, IDetailsAreaConstants.CENTER_SPACE);
		}
		data.right = new FlatFormAttachment(categoryCombo[fromOrTo], -IDetailsAreaConstants.CENTER_SPACE);
		data.top = new FlatFormAttachment(categoryCombo[fromOrTo], 0, SWT.CENTER);
		categoryLabel[fromOrTo].setLayoutData(data);
		
		for (int i = 0; i<categories[fromOrTo].length; i++)  {
			categoryCombo[fromOrTo].add(categories[fromOrTo][i].category.getName());
		}
		categoryCombo[fromOrTo].addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateCategorySelection(fromOrTo);
				IAssignCategory category = categories[fromOrTo][currentCategoryIndex[fromOrTo]].category;
				category.refresh();
			}
			// TODO: is this correct?
			public void widgetDefaultSelected(SelectionEvent e) { widgetSelected(e); }
		});
		
		outerComposite[fromOrTo] = createFlatFormComposite(composite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(categoryLabel[fromOrTo], 0, SWT.LEFT);
		data.right = new FlatFormAttachment(categoryCombo[fromOrTo], 0, SWT.RIGHT);
		data.top = new FlatFormAttachment(categoryCombo[fromOrTo], IDetailsAreaConstants.VSPACE);
		data.bottom = new FlatFormAttachment(100,0);
		outerComposite[fromOrTo].setLayoutData(data);
	}
	
	public boolean shouldUseExtraSpace() { return true; }
	
	protected void createClient(Composite parent) {
		Composite composite = createFlatFormComposite(parent);
		createCopySelectWidgets(composite);
		Composite mainComposite = createFlatFormComposite(composite);
		FlatFormData data = new FlatFormData();
		data.left = new FlatFormAttachment(copySelectComposite, IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0,0);
		data.right = new FlatFormAttachment(100,0);
		data.bottom = new FlatFormAttachment(100,0);
		mainComposite.setLayoutData(data);
		for (int i = 0; i<2; i++)  createCategoryComboWidgets(mainComposite, i);
	}

	// Total Hack until we have Copy objects in graphical editor
	protected void basicSetInput(EObject newInput) {
		if (newInput instanceof Assign) {
			EList copies = ((Assign)newInput).getCopy();
			if (copies.size() == 0) {
				Copy copy = BPELFactory.eINSTANCE.createCopy();
				copies.add(copy);
				ModelHelper.createExtensionIfNecessary(getBPELEditor().getExtensionMap(), copy);
			}
		}

		super.basicSetInput(newInput);
		currentCopyIndex = 0;
		selectCategoriesForInput();
	}

	protected void selectCategoriesForInput() {
		Assign modelAssign = (Assign)getInput();
		List copies = modelAssign.getCopy();
		if (currentCopyIndex > copies.size()-1)  currentCopyIndex = copies.size()-1;
		if (currentCopyIndex < 0)  currentCopyIndex = 0;
		Copy modelCopy = currentCopy = (Copy)copies.get(currentCopyIndex);

		// TODO: first, just store the value in the extension.
		// TODO: then, arrange for the widget-updating code to rely on it.
		
		updateSelectCopyWidgets();

		ModelHelper.createExtensionIfNecessary(getBPELEditor().getExtensionMap(), modelCopy);
		CopyExtension copyExtension = (CopyExtension)ModelHelper.getExtension(modelCopy);
		
		for (int fromOrTo = 0; fromOrTo<2; fromOrTo++)  {
			To modelFromOrTo = (modelCopy==null)? null :
				(fromOrTo==0?modelCopy.getFrom():modelCopy.getTo());
			int newIndex = 0;
			// find the proper category.
			for (int i = 0; i<categories[fromOrTo].length; i++)  {
				if (categories[fromOrTo][i].category.isCategoryForModel(modelFromOrTo)) {
					newIndex = i;
					// TODO: is this bogus?
					if (fromOrTo==0) {
						copyExtension.setFromType(newIndex);
					} else {
						copyExtension.setToType(newIndex);
					}
					break;
				}
			}
			if (newIndex == 0) {
				// the extension might know which category it should be.
				if (fromOrTo==0) {
					newIndex = copyExtension.getFromType();
				} else {
					newIndex = copyExtension.getToType();
				}
			}
			
			// TODO: the updatingCategories seems to be a hack
			// we should find a better solution.
			if (!updatingCategories) {
				isProgramSelection = true;
				categoryCombo[fromOrTo].select(newIndex);
				updateCategorySelection(fromOrTo);
				isProgramSelection = false;
			}
		}
	}

	public void refresh() {
		super.refresh();
		selectCategoriesForInput();
		for (int i = 0; i < 2; i++) {
			IAssignCategory category = categories[i][currentCategoryIndex[i]].category;
			category.refresh();
		}
	}

	public void aboutToBeHidden() {
		super.aboutToBeHidden();
		for (int i = 0; i < 2; i++) {
			IAssignCategory category = categories[i][currentCategoryIndex[i]].category;
			category.aboutToBeHidden();
		}
	}

	public void aboutToBeShown() {
		super.aboutToBeShown();
		for (int i = 0; i < 2; i++) {
			IAssignCategory category = categories[i][currentCategoryIndex[i]].category;
			category.aboutToBeShown();
		}
	}

	protected void updateSelectCopyWidgets() {
//		int current = (currentCopyIndex+1);
		int max = ((Assign)getInput()).getCopy().size();
//		selectedCopy.setText(BPELUtil.formatString(
//			Messages.getString("AssignImplDetails.NofM__4"),  //$NON-NLS-1$
//			String.valueOf(current), String.valueOf(max)));
		
		String[] items = new String[max];
		for (int i = 0; i<max; i++) items[i] = String.valueOf(i+1);
		copyList.setItems(items);
		copyList.select(currentCopyIndex);
		
//		if (current>1)  previousCopy.setEnabled(true);
//		nextCopy.setEnabled(current<max);
//		if (!(current>1))  previousCopy.setEnabled(false);

		deleteCopy.setEnabled(max>1);
	}

	private void updateCategorySelection(int fromOrTo) {
		updatingCategories = true;
		try {
			int oldIndex = currentCategoryIndex[fromOrTo];
			int newIndex = categoryCombo[fromOrTo].getSelectionIndex();
					
			if (!isProgramSelection && (oldIndex == newIndex))  return;
			
			CopyExtension copyExtension = (CopyExtension)ModelHelper.getExtension(currentCopy);
			
			if (fromOrTo==0) {
				copyExtension.setFromType(newIndex);
			} else {
				copyExtension.setToType(newIndex);
			}

			if (oldIndex >= 0 && oldIndex != newIndex) {
				// if we *ARE* visible and the child *IS* visible 
				IAssignCategory category = categories[fromOrTo][oldIndex].category;
				if (!isHidden && !category.isHidden()) {
					category.aboutToBeHidden();
				}
				categories[fromOrTo][oldIndex].composite.setVisible(false);
			} 
			currentCategoryIndex[fromOrTo] = newIndex;
			createCategoryComposite(fromOrTo, newIndex);

			if (!isProgramSelection)  {
				// TODO: fix this!!
				Command cmd = null;//categories[fromOrTo][newIndex].category.newStoreModelCommand();

				if (fromOrTo==0) {
					cmd = new SetCopyFromCommand(currentCopy, BPELFactory.eINSTANCE.createFrom());
				} else {
					cmd = new SetCopyToCommand(currentCopy, BPELFactory.eINSTANCE.createTo());
				}	
				isComboStoreCommand = true;
				getCommandFramework().execute(wrapInShowContextCommand(cmd));
				isComboStoreCommand = false;
			}
			// Set the input of the category after we insert the to or from into tte model.
			categories[fromOrTo][newIndex].category.setInput(currentCopy);
			// if we *ARE* visible and the child *IS* hidden
			IAssignCategory category = categories[fromOrTo][newIndex].category;
			if (!isHidden && category.isHidden()) {
				category.aboutToBeShown();
			}
			categories[fromOrTo][newIndex].composite.setVisible(true);
			
			// TODO: should the categories only store when a widget change is committed?
			// Cons of that idea:
			//   - Changing the category in the combo, but *not* changing anything else,
			//     then clicking elsewhere and back, would cause the combo to revert. 
			//   - The OpaqueAssignCategory doesn't have any widgets..
		} finally {
			updatingCategories = false;
		}
	}

	protected static class AssignUserContext {
		
		public int fromCategory, toCategory;
		public Object fromUserContext, toUserContext;
		public int currentCopyIndex;
		
		public AssignUserContext(AssignImplSection source) {
			fromCategory = source.currentCategoryIndex[0];
			fromUserContext = source.categories[0][fromCategory].category.getUserContext();
			toCategory = source.currentCategoryIndex[1];
			toUserContext = source.categories[1][toCategory].category.getUserContext();
			currentCopyIndex = source.currentCopyIndex;
		}
		public void restoreOn(AssignImplSection target) {
			target.currentCopyIndex = currentCopyIndex;
			target.selectCategoriesForInput();
			// TODO: does this make sense?
			if (target.currentCategoryIndex[0] == fromCategory) {
				target.categories[0][fromCategory].category.restoreUserContext(fromUserContext);
			}
			if (target.currentCategoryIndex[1] == toCategory) {
				target.categories[1][toCategory].category.restoreUserContext(toUserContext);
			}
		}
	}

	public Object getUserContext() {
		return new AssignUserContext(this);
	}
	public void restoreUserContext(Object userContext) {
		((AssignUserContext)userContext).restoreOn(this);
	}
	
	public void gotoMarker(IMarker marker) {
		String uriFragment = marker.getAttribute(IBPELUIConstants.MARKER_ATT_FROM, ""); //$NON-NLS-1$
		EObject from = modelObject.eResource().getEObject(uriFragment);
		EObject copy = from.eContainer();
		currentCopyIndex = ((Assign)getModel()).getCopy().indexOf(copy);
		refresh();
	}
}
