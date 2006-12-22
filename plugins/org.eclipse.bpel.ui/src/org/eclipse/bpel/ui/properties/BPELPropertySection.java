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

import java.util.Collections;
import java.util.List;

import org.eclipse.bpel.common.ui.command.ICommandFramework;
import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.details.viewers.CComboViewer;
import org.eclipse.bpel.common.ui.editmodel.AbstractEditModelCommand;
import org.eclipse.bpel.common.ui.editmodel.EditModelCommandStack;
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELTabbedPropertySheetPage;
import org.eclipse.bpel.ui.actions.ShowPropertiesViewAction;
import org.eclipse.bpel.ui.adapters.IMarkerHolder;
import org.eclipse.bpel.ui.proposal.providers.ModelContentProposalProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.util.MultiObjectAdapter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
// import org.eclipse.wst.common.ui.properties.internal.provisional.AbstractPropertySection;
// import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetPage;
// import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetWidgetFactory;
// import org.eclipse.wst.common.ui.properties.internal.view.TabbedPropertyViewer;

import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyViewer;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * An abstract implementation which provides some adapter support and useful stuff.
 * This was based on the common implementation characteristics of bpel.ui properties
 * pages.
 * 
 * Implementors may subclass this class, or they could extend AbstractPropertySection directly.
 */  
public abstract class BPELPropertySection extends AbstractPropertySection 	
{
	protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	protected static final MultiObjectAdapter[] EMPTY_MULTI_OBJECT_ARRAY = new MultiObjectAdapter[0];
	protected static final List NULL_LIST = Collections.singletonList(null);

	public static final int STANDARD_LABEL_WIDTH_SM = 105;
	public static final int STANDARD_LABEL_WIDTH_AVG = STANDARD_LABEL_WIDTH_SM * 5/4;
	public static final int STANDARD_LABEL_WIDTH_LRG = STANDARD_LABEL_WIDTH_SM * 3/2;
	public static final int STANDARD_BUTTON_WIDTH = 60;
	public static final int SHORT_BUTTON_WIDTH = 45;

	protected MultiObjectAdapter[] adapters;
	protected boolean isCreated;
	protected boolean isHidden;
	protected EObject modelObject;
	protected TabbedPropertySheetWidgetFactory wf;
	protected BPELTabbedPropertySheetPage tabbedPropertySheetPage;

	
		
	final protected ModelContentProposalProvider.ValueProvider inputValueProvider =  new ModelContentProposalProvider.ValueProvider () {
		public Object value() {
			return getModel();
		}		
	};
	

	@Override
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		TabbedPropertySheetWidgetFactory wf = super.getWidgetFactory();
		wf.setBorderStyle(SWT.BORDER);
		return wf;
	}

	public BPELPropertySection() {
		super();
		adapters = createAdapters();
	}

	/**
	 * Subclasses must override this to return DetailsAdapters.
	 * The default implementation returns an empty array.
	 */
	protected MultiObjectAdapter[] createAdapters() {
		return EMPTY_MULTI_OBJECT_ARRAY;
	}

	/**
	 * Removes all adapters from whatever they are attached to.
	 * Subclasses may override.
	 */
	protected void removeAllAdapters() {
//		Assert.isTrue(isCreated);
		for (int i = 0; i < adapters.length; i++)
			adapters[i].removeFromAll();
	}

	/**
	 * This implementation just hooks the first adapter on the input object.
	 * Subclasses may override.
	 */
	protected void addAllAdapters() {
		Assert.isTrue(isCreated);
		if (adapters.length > 0)
			adapters[0].addToObject(getModel());
	}

	/**
	 * Convenience method for removing and re-adding adapters.  This is a simple and general 
	 * way to react to model changes that grow or shrink the set of model objects we want our
	 * adapters to be on.  
	 */
	protected void refreshAdapters() {
		removeAllAdapters();
		addAllAdapters();
	}

	/**
	 * This method is intended to set the input object.  Subclasses may override this
	 * method to perform necessary cleanup before changing the input object, and/or
	 * perform initialization after changing the input object.
	 * 
	 * Subclasses may also override to change the policy of which object is used as
	 * the input for a particular properties section.
	 * 
	 * For example: a section for a custom activity, may wish to override this method
	 * to use the custom activity ExtensibilityElement as the "main" input object.
	 */
	protected void basicSetInput(EObject newInput) {
		modelObject = newInput;
	}
	
	public final void setInput(IWorkbenchPart part, ISelection selection) {
		
		if ((selection instanceof IStructuredSelection) == false) {
			return ;
		}
		
		Object model = ((IStructuredSelection)selection).getFirstElement();
		if (model == modelObject) {
			return;
		}
				
		removeAllAdapters();
		
		super.setInput(part, selection);
		
	    basicSetInput((EObject)model);
	    
		// Careful: don't assume input == newInput.
		// There are basicSetInput() hacks that violate that assumption  =)
		// TODO: is this comment related to the custom activities?
		addAllAdapters();		
	}

	
	public void aboutToBeHidden() {
//		Assert.isTrue(!isHidden);
		isHidden = true;
	}

	public void aboutToBeShown() {
//		Assert.isTrue(isHidden && (getModel() != null));
		isHidden = false;
	}

	protected EObject getModel() {
		return modelObject;
	}

	protected final EObject getInput() {
		return getModel();
	}

	/*&
	 * 
	 */
	public Object value() {
		return getModel();
	}
	
	
	/**
	 * Refresh the given CComboViewer, and also make sure selectedObject is selected in it.
	 */
	protected void refreshCCombo(CComboViewer viewer, Object selectedObject) {
		viewer.refresh();
		if (selectedObject == null) {
			// work-around the null check in StructuredSelection(Object) ctor.
			viewer.setSelectionNoNotify(new StructuredSelection(NULL_LIST), false);
		} else {
			viewer.setSelectionNoNotify(new StructuredSelection(selectedObject), false);
		}
	}

	/**
	 * Create the controls.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	
	@Override
	public void createControls (final Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		
		super.createControls(parent, aTabbedPropertySheetPage);
		this.tabbedPropertySheetPage = (BPELTabbedPropertySheetPage)aTabbedPropertySheetPage;
		this.wf = getWidgetFactory();
		Assert.isTrue(!isCreated);
		
		Composite marginComposite = wf.createComposite(parent); 
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = IDetailsAreaConstants.HMARGIN;
		fillLayout.marginHeight = IDetailsAreaConstants.VMARGIN/2;
		marginComposite.setLayout(fillLayout);
		createClient(marginComposite);
		isHidden = true;
		isCreated = true;
		parent.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				removeAllAdapters();
			}
		});
	}
	
	/**
	 * Subclasses should override this to create the child controls of the section.
	 */
	protected abstract void createClient(Composite parent);

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#dispose()
	 */
	@Override
	public void dispose() {
		if (isCreated) {
			// TODO HACK: this shouldn't really be here!  But where should it be??
			getCommandFramework().applyCurrentChange();
			removeAllAdapters();
			isCreated = false;
		}
	}

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		updateStatusLabels();
	}

	/**
	 * Subclasses must override this method in order to refresh the status labels 
	 */
	protected void updateStatusLabels() {
	}

	/**
	 * Gets all IMarker according to the passed input model.
	 * @see IMarkerHolder.getMarkers 
	 */
	protected IMarker[] getMarker(Object input) {
		IMarkerHolder markerHolder = (IMarkerHolder) BPELUtil.adapt(input, IMarkerHolder.class);
		if (markerHolder != null) {
			return markerHolder.getMarkers(input);
		}
		return new IMarker[0];
	}

	protected ICommandFramework getCommandFramework() {
		return getBPELEditor().getCommandFramework();
	}

	/**
	 * @return the BPELEditor
	 */
	
	public BPELEditor getBPELEditor() {
		return getTabbedPropertySheetPage().getEditor();
	}

	/**
	 * @return the BPEL process
	 */
	public Process getProcess()  {
		return getBPELEditor().getProcess();
	}

	/*
	 * Convenience accessor with default policy (this is overridden in certain subclasses).
	 */
	protected Command wrapInShowContextCommand(final Command inner) {
		return wrapInShowContextCommand(inner, this);
	}

	protected Command wrapInShowContextCommand(final Command inner, BPELPropertySection section) {
		final Object previousInput = getInput(); // keep the old input model object
		final TabbedPropertyViewer viewer = getTabbedPropertySheetPage().getTabbedPropertyViewer();
		final int tabIndex = viewer.getSelectionIndex();
		final int sectionIndex = getTabbedPropertySheetPage().getCurrentTab().getSectionIndex(section);
		
		if (!inner.canExecute()) {
			System.out.println("WARNING: unexecutable command passed to wrapInShowContextCommand():"); //$NON-NLS-1$
			System.out.println("    "+inner.getDebugLabel()); //$NON-NLS-1$
		}
		
		return new AbstractEditModelCommand() {
			Object beforeContext, afterContext;
			public String getLabel() {
				return inner.getLabel();
			}
			public void setLabel(String label) {
				inner.setLabel(label);
			}
			public String getDebugLabel() {
				return "ShowContext wrapper:[" + inner.getDebugLabel() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			public boolean canExecute() {
				return inner.canExecute();
			}
			public void execute() {
				BPELPropertySection aSection = getSection(sectionIndex); 
				beforeContext = (aSection==null)? null : aSection.getUserContext();
				inner.execute();
				afterContext = (aSection==null)? null : aSection.getUserContext();
			}
			public boolean canUndo() {
				return inner.canUndo();
			}
			public void undo() {
				inner.undo();
				showPropertiesTab();
				BPELPropertySection aSection = getSection(sectionIndex);
				if (aSection != null) aSection.restoreUserContext(beforeContext);
			}
			public void redo() {
				inner.redo();
				showPropertiesTab();
				BPELPropertySection aSection = getSection(sectionIndex);
				if (aSection != null) aSection.restoreUserContext(afterContext);
			}
			public void dispose() {
				inner.dispose();
			}
			protected BPELPropertySection getSection(int index) {
				return (BPELPropertySection) getTabbedPropertySheetPage().getCurrentTab().getSectionAtIndex(sectionIndex);
			}
			protected void showPropertiesTab() {
				// TODO: Try to avoid selecting the model object all
				// the time, as it could cause unnecessary flashing.
				getBPELEditor().selectModelObject(previousInput);
				
				if (tabIndex != viewer.getSelectionIndex()) {
					Object selectedTab = viewer.getElementAt(tabIndex);
					viewer.setSelection(new StructuredSelection(selectedTab));
				}
			}
			// TODO: THIS IS A HACK.. these helpers might belong somewhere else.
			public Resource[] getResources() { return EditModelCommandStack.getResources(inner); }
			public Resource[] getModifiedResources() { return EditModelCommandStack.getModifiedResources(inner); }
		};
	}

	/**
	 * Creates a composite with a flat border around it.
	 */
	protected Composite createBorderComposite(Composite parent) {
	    return BPELUtil.createBorderComposite(parent, wf);
	}
	
	/**
	 * NOTE: use this method, NOT the method in TabbedPropertySheetWidgetFactory,
	 * whose semantics were inexplicably changed.
	 *
	 * 	TODO: We need a new/better story for layouts and borders ??
	 */
	protected Composite createFlatFormComposite(Composite parent) {
		Composite result = wf.createFlatFormComposite(parent);
		FlatFormLayout formLayout = new FlatFormLayout();
		formLayout.marginWidth = formLayout.marginHeight = 0;		
		result.setLayout(formLayout);
		return result;
	}
	
	/**
	 * @return the BPEL Tabbed Property Sheet page.
	 */
	
	public BPELTabbedPropertySheetPage getTabbedPropertySheetPage() {
		return tabbedPropertySheetPage;
	}
	
	
	/**
	 * @return the IFile that the editor is editing.
	 */
	
	public IFile getBPELFile() {
		return ((IFileEditorInput) getBPELEditor().getEditorInput()).getFile();
	}

	/** 
	 * Returns a token indicating which widget should have focus.  Note that the token can't
	 * be tied to this particular instance of the section; after the section is destroyed
	 * and re-created, the token must still be valid.
	 * @return the user context 
	 */
	public Object getUserContext() {
		return null;
	}

	/** 
	 * Accepts a token created by getUserContext() and gives focus to the widget represented
	 * by the token.
	 * @param userContext the user context to restore.
	 */
	public void restoreUserContext(Object userContext) {
	}
	
	/**
	 * Shows the given marker.
	 * @param marker
	 */
	public void gotoMarker (IMarker marker) {
		
	}
	
	/**
	 * Returns true if this section knows how to show the given marker.
	 * 
	 * @param marker the marker to be checked. 
	 * @return true if so, false otherwise ... 
	 */
	
	public boolean isValidMarker (IMarker marker) {
		
		EObject obj = BPELUtil.getObjectFromMarker( marker, modelObject );
		
		if (obj == null) {
			return false ;
		}
		
		// do the easy check
		if ( obj.equals( modelObject ) ) {
			return true;
		}
		
		return false;
	}

	
	/**
	 * Return the Context names that allows us to point markers correctly at this 
	 * section.
	 * 
	 * @return an array of context names 
	 */
	
	public String[] getContextNames () {
		return new String[] {}; 
	}
	
	
	/**
	 * Given a model object, selects it in the BPEL Editor and makes sure the
	 * properties pages are also shown for it.
	 */
	protected void selectModelObject(final EObject target) {
		Runnable runnable = new Runnable() {
			public void run() {
				getBPELEditor().selectModelObject(target);
                new ShowPropertiesViewAction().run();
			}
		};
		Display.getDefault().asyncExec(runnable);
	}
}