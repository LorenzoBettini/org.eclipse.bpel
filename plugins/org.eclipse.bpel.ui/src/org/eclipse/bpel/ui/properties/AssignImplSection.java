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

import org.eclipse.bpel.common.ui.details.ButtonIValue;
import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.adapters.IProperty;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.AddCopyCommand;
import org.eclipse.bpel.ui.commands.RemoveCopyCommand;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;


/**
 * AssignImplDetails allows viewing and editing of the copy elements in an Assign.
 * 
 * The input of this section is the Assign activity.
 *  
 *
 * @author IBM Original Contribution
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jun 7, 2007
 *
 */

@SuppressWarnings("nls")
public class AssignImplSection extends BPELPropertySection {
				
	static String  CURRENT_INDEX_PROPERTY = "currentIndex";
	
	
	/** The to section */
	CategorySection fToSection   = new CategorySection ( this );
	
	/** The from section */
	CategorySection fFromSection = new CategorySection ( this );
	
	/** The current copy rule being edited. */
	Copy fCurrentCopy;
	
	org.eclipse.swt.widgets.List fCopyList;
	
	Composite copySelectComposite;

	protected ListViewer fCopyListViewer;

	Button fDeleteCopy;

	Button fKeepSrcElement;
	Button fIgnoreMissingSourceData;
	
	EditController fKeepSrcElementController;
	EditController fIgnoreMissingSourceDataController;
	
	/**
	 * 
	 */
	
	public AssignImplSection()  {
		super();
				
		fToSection.fAllowed = new IAssignCategory[] {				
			new VariablePartAssignCategory(this ),
			new ExpressionAssignCategory(this),
			new VariablePropertyAssignCategory(this),
			new PartnerRoleAssignCategory(this, false )
		};

		fFromSection.fAllowed = new IAssignCategory[] {				
			new VariablePartAssignCategory(this ),
			new ExpressionAssignCategory(this),
			new LiteralAssignCategory(this ),
			new VariablePropertyAssignCategory(this ),
			new PartnerRoleAssignCategory(this , true ),
			new EndpointReferenceAssignCategory(this ),						
			new OpaqueAssignCategory(this )
		};
		
	}
		
	@Override
	protected MultiObjectAdapter[] createAdapters() {
		return new MultiObjectAdapter[] {
			/* model object */
			new MultiObjectAdapter() {
				
				@Override
				public void notify (Notification n) {
					// System.out.println("AssignImplSection: notification() " + n);
					
					if ( n.getFeature() == BPELPackage.eINSTANCE.getAssign_Copy() ) {
						
						adjustCopyRulesList();
						
						Copy copy = (Copy) n.getNewValue();

						// Delete
						if (copy == null) {
							Assign assign = getModel();
							int sz = assign.getCopy().size();
							if (sz > 0) {
								copy = (Copy) assign.getCopy().get(sz-1);
							}
						}
						
						selectCategoriesForInput( copy );						
					}
				}
			},
		};
	}

	protected void createCopySelectWidgets(Composite parent) {
		
		FlatFormData data;
		
		Composite c = copySelectComposite = createFlatFormComposite(parent);
		
		Button insertCopy = fWidgetFactory.createButton(c, Messages.AssignImplDetails_New__5, SWT.PUSH);  
		fDeleteCopy = fWidgetFactory.createButton(c, Messages.AssignImplDetails_Delete__6, SWT.PUSH);  

		fCopyList = fWidgetFactory.createList(c, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE);
	
		
		int preferredWidth = BPELUtil.calculateButtonWidth(insertCopy, SHORT_BUTTON_WIDTH);
		preferredWidth = Math.max(preferredWidth, BPELUtil.calculateButtonWidth(fDeleteCopy, SHORT_BUTTON_WIDTH));
		
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
		fCopyList.setLayoutData(data);
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(fDeleteCopy, -IDetailsAreaConstants.VSPACE);
		insertCopy.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		fDeleteCopy.setLayoutData(data);		
		
		insertCopy.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				
				Copy copy = BPELFactory.eINSTANCE.createCopy();
				
				getCommandFramework().execute(wrapInShowContextCommand(
						new AddCopyCommand((Assign)getInput(), copy)));
											
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		fDeleteCopy.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				
				getCommandFramework().execute(wrapInShowContextCommand(
						new RemoveCopyCommand((Assign)getInput(), fCurrentCopy)));
				
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	
		
		fCopyList.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				int index = fCopyList.getSelectionIndex();
				fCopyList.select(index);
				Assign assign = getModel();
				Copy copy = (Copy) assign.getCopy().get( index );
				selectCategoriesForInput( copy );				
				
			}
			public void widgetDefaultSelected(SelectionEvent e) { 
				widgetSelected(e);
			}
		});

	}

	protected void createCategorySectionWidgets (Composite composite, final CategorySection section, boolean isFrom ) {
		
		FlatFormData data;
				
		section.fLabel = fWidgetFactory.createLabel(composite, isFrom ? Messages.AssignImplDetails_From__1:Messages.AssignImplDetails_To__2); 
		section.fCombo = new Combo(composite,SWT.FLAT | SWT.BORDER | SWT.READ_ONLY );
		data = new FlatFormData();
		
		if (isFrom)  {
			data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(section.fLabel, STANDARD_LABEL_WIDTH_SM));
			data.right = new FlatFormAttachment(50, -IDetailsAreaConstants.CENTER_SPACE);
		} else {
			data.left = new FlatFormAttachment(50, IDetailsAreaConstants.CENTER_SPACE + BPELUtil.calculateLabelWidth(section.fLabel, STANDARD_LABEL_WIDTH_SM));
			data.right = new FlatFormAttachment(100, 0);
		}

		data.top = new FlatFormAttachment(0,0);
		section.fCombo.setLayoutData(data);
		
		data = new FlatFormData();
		if (isFrom)  {
			data.left = new FlatFormAttachment(0, 0);
		} else {
			data.left = new FlatFormAttachment(50, IDetailsAreaConstants.CENTER_SPACE);
		}
		data.right = new FlatFormAttachment(section.fCombo, -IDetailsAreaConstants.CENTER_SPACE);
		data.top = new FlatFormAttachment(section.fCombo, 0, SWT.CENTER);
		section.fLabel.setLayoutData(data);
		
		for (IAssignCategory category : section.fAllowed ) {
			if (category.getName() != null) {
				section.fCombo.add( category.getName() );
			}
		}
		
		section.fCombo.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected (SelectionEvent e) {		
				int index = section.fCombo.getSelectionIndex();
				
				updateCategorySelection ( section , index , true );
				
			}
			// TODO: is this correct?
			public void widgetDefaultSelected(SelectionEvent e) { 
				widgetSelected(e); 
			}
		});
		
		section.fOuterComposite = createFlatFormComposite(composite);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(section.fLabel, 0, SWT.LEFT);
		data.right = new FlatFormAttachment(section.fCombo, 0, SWT.RIGHT);
		data.top = new FlatFormAttachment(section.fCombo, IDetailsAreaConstants.VSPACE);
		data.bottom = new FlatFormAttachment(100,-25);
		section.fOuterComposite.setLayoutData(data);
		
		
		// Create the keepSrcElement check box button
		if (isFrom) {
			fIgnoreMissingSourceData = fWidgetFactory.createButton(composite, Messages.AssignImplDetails_IgnoreMissingSourceData, SWT.CHECK );
			data = new FlatFormData();
			data.right = new FlatFormAttachment(50,-IDetailsAreaConstants.HSPACE);
			data.bottom = new FlatFormAttachment(100,0);
			fIgnoreMissingSourceData.setLayoutData(data);
			
			fIgnoreMissingSourceDataController = createEditController();
			fIgnoreMissingSourceDataController.setFeature( BPELPackage.eINSTANCE.getCopy_IgnoreMissingFromData() );
			fIgnoreMissingSourceDataController.setViewIValue( new ButtonIValue(fIgnoreMissingSourceData) );
			fIgnoreMissingSourceDataController.startListeningTo(fIgnoreMissingSourceData);
			
		} else {
			
			fKeepSrcElement = fWidgetFactory.createButton(composite, Messages.AssignImplDetails_KeepSrcElementName, SWT.CHECK );
			data = new FlatFormData();
			data.right = new FlatFormAttachment(100,-IDetailsAreaConstants.HSPACE);
			data.bottom = new FlatFormAttachment(100,0);
			fKeepSrcElement.setLayoutData(data);

			fKeepSrcElementController = createEditController();
			fKeepSrcElementController.setFeature( BPELPackage.eINSTANCE.getCopy_KeepSrcElementName() );
			fKeepSrcElementController.setViewIValue( new ButtonIValue(fKeepSrcElement) );
			fKeepSrcElementController.startListeningTo(fKeepSrcElement);
						
		} 
				
	}
	
	
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() { 
		return true; 
	}
	
	
	@Override
	protected void createClient(Composite parent) {
		
		Composite composite = createFlatFormComposite(parent);
		createCopySelectWidgets(composite);
		Composite mainComposite = createFlatFormComposite(composite);
		FlatFormData data = new FlatFormData();
		data.left = new FlatFormAttachment (copySelectComposite, IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(0,0);
		data.right = new FlatFormAttachment(100,0);
		data.bottom = new FlatFormAttachment(100,0);
		mainComposite.setLayoutData(data);
		
		createCategorySectionWidgets(mainComposite,fFromSection,true);
		createCategorySectionWidgets(mainComposite,fToSection,false);
		
	}

	
	// Total Hack until we have Copy objects in graphical editor
	@Override
	protected void basicSetInput (EObject newInput) {
		
		saveUserContextToInput();
		
		super.basicSetInput(newInput);		
		adjustCopyRulesList ();		
				
		restoreUserContextFromInput();
				
		if (fToSection.fCurrent != null) {
			fToSection.fCurrent.refresh();
		}
		if (fFromSection.fCurrent != null) {
			fFromSection.fCurrent.refresh();	
		}
		
	}
	
	 

	/** 
	 * Called when the copy rule changes or is created.
	 *
	 */
	@SuppressWarnings("boxing")
	protected void selectCategoriesForInput (Copy copy) {
			
		fCurrentCopy = copy;
		
		fKeepSrcElementController.setInput(fCurrentCopy);
		fIgnoreMissingSourceDataController.setInput( fCurrentCopy );
		
		if (fCurrentCopy == null) {			
			fToSection.hideCurrent();
			fFromSection.hideCurrent();		
			fKeepSrcElement.setEnabled(false);
			fIgnoreMissingSourceData.setEnabled(false);
			return ;
		}
				
		Assign assign = getModel();		
		int pos = assign.getCopy().indexOf(fCurrentCopy);
		fCopyList.select( pos );
		
		fKeepSrcElement.setEnabled(true);
		fIgnoreMissingSourceData.setEnabled(true);
		
		
		
		// Find the proper copy-from category
		boolean bFound = false;
		for (IAssignCategory category : fFromSection.fAllowed) {
			if (category.isCategoryForModel( fCurrentCopy.getFrom() )) {				
				updateCategorySelection(fFromSection,category,false);
				bFound = true;
				break;
			}
		}
		
		/** In case we can't find the appropriate one, just display the first one */
		if (bFound == false || fFromSection.fCurrent == null)  {
			updateCategorySelection(fFromSection,0,false);
		}
		
		
		// Find the proper copy-to category
		bFound = false;
		for(IAssignCategory category : fToSection.fAllowed) {
			if (category.isCategoryForModel(fCurrentCopy.getTo())) {
				updateCategorySelection(fToSection,category,false);
				bFound = true;
				break;
			}
		}		
		
		/** In case we can't find the appropriate one, just display the first one */
		if (bFound == false || fToSection.fCurrent == null) {
			updateCategorySelection(fToSection,0,false);
		}
	}
	
	
	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#aboutToBeHidden()
	 */
	@Override
	public void aboutToBeHidden() {
		super.aboutToBeHidden();
		
		if (fToSection.fCurrent != null) {
			fToSection.fCurrent.aboutToBeHidden();
		}
		if (fFromSection.fCurrent != null) {
			fFromSection.fCurrent.aboutToBeHidden();
		}
		
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#aboutToBeShown()
	 */
	@Override
	public void aboutToBeShown() {
		super.aboutToBeShown();
		if (fToSection.fCurrent != null) {
			fToSection.fCurrent.aboutToBeShown();
		}
		if (fFromSection.fCurrent != null) {
			fFromSection.fCurrent.aboutToBeShown();
		}
	}

	
	protected void adjustCopyRulesList() {

		Assign assign = getModel();
		int sz = assign.getCopy().size();
		
		String[] items = new String[sz];
		for (int i = 0; i<sz; i++) {
			items[i] = String.valueOf(i+1);
		}
		fCopyList.setItems( items );		
		fDeleteCopy.setEnabled(sz > 0);
		
		//
		fToSection.fCombo.setEnabled( sz > 0 );
		fFromSection.fCombo.setEnabled ( sz > 0 ) ;
	}

	
	
	void updateCategorySelection ( CategorySection section, int index , boolean bVisual ) {
		updateCategorySelection(section, section.fAllowed[index], bVisual);
	}

	void updateCategorySelection ( CategorySection section, IAssignCategory newCurrent, boolean bVisual) {
		
		/** Hide current */
		section.hideCurrent();						
		
		/** Update current to the one that picked from */
		section.fCurrent = newCurrent;		
		section.ensureCategoryCompositeCreated();
		
		if (bVisual == false) {
			section.updateCombo();
		}
				
		
		if (section == fToSection) {
			if (bVisual || fCurrentCopy.getTo() == null) {
				fCurrentCopy.setTo( BPELFactory.eINSTANCE.createTo() );
			}
			section.fCurrent.setInput ( fCurrentCopy.getTo() );
			
		} else {
			
			if (bVisual || fCurrentCopy.getFrom() == null) {
				fCurrentCopy.setFrom( BPELFactory.eINSTANCE.createFrom() );
			}			
			section.fCurrent.setInput ( fCurrentCopy.getFrom() );
		}
				
		section.showCurrent();
		section.fCurrent.refresh();
		
		// TODO: should the categories only store when a widget change is committed?
		// Cons of that idea:
		//   - Changing the category in the combo, but *not* changing anything else,
		//     then clicking elsewhere and back, would cause the combo to revert. 
		//   - The OpaqueAssignCategory doesn't have any widgets..
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#getUserContext()
	 */
	@SuppressWarnings("boxing")
	@Override
	public Object getUserContext() {
		Assign assign = getModel();
		if (assign == null) {
			return -1;
		}
		return assign.getCopy().indexOf(fCurrentCopy);		
	}
	
	
	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#restoreUserContext(java.lang.Object)
	 */
	@SuppressWarnings("boxing")
	@Override
	public void restoreUserContext (Object userContext) {
		
		int idx = 0;
		if (userContext instanceof Number ) {
			Number num = (Number) userContext;
			idx = num.intValue();
		}
				
		Assign assign = getModel();
		
		int sz = assign.getCopy().size();
		Copy copy = null;
		if (sz > 0) {					
			if (idx < sz && idx >= 0) {
				copy = (Copy) assign.getCopy().get(idx);
			} else {
				copy = (Copy) assign.getCopy().get(0);
			}
		}
		selectCategoriesForInput(copy);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#gotoMarker(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		
		String uriFragment = marker.getAttribute(IBPELUIConstants.MARKER_ATT_FROM, EMPTY_STRING);
		EObject from = fModelObject.eResource().getEObject(uriFragment);
		EObject copy = from.eContainer();
		// currentCopyIndex = ((Assign)getModel()).getCopy().indexOf(copy);
		refresh();
	}
}
