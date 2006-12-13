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

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHelpContextIds;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.editors.TextEditorInput;
import org.eclipse.bpel.ui.editors.xpath.XPathTextEditor;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.bpel.ui.properties.DateTimeSelector;
import org.eclipse.bpel.ui.properties.DurationSelector;
import org.eclipse.bpel.ui.properties.TextSection;
import org.eclipse.bpel.ui.util.BPELDateTimeHelpers;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;


/**
 * Expression language editor for XPath.
 */
public class XPathExpressionEditor extends AbstractExpressionEditor {

	private static final String BOOLEAN_EXPR_DEFAULT = "true()";
	private static final String UNSIGNED_INT_EXPR_DEFAULT = "1";
	
	private static final String TEXT_STRING = Messages.XPathExpressionEditor_Text_0,
			LITERAL_STRING = Messages.XPathExpressionEditor_Literal_1;

	private static final int TEXT = 0, LITERAL = 1;

	protected static final String DEFAULT_DURATION_VALUE = "\'P0D\'"; //$NON-NLS-1$

	protected Combo combo; // Expression type values

	protected Label comboLabel; // Expression type

	protected Composite mainComposite; // high level composite control

	protected XPathTextEditor textEditor; // expression text

	/** The date/time  selector, alternative to just plain text */
	protected DateTimeSelector dateTimeSelector;

	/** The duration selector, alternative to just plain text */
	protected DurationSelector durationSelector;

	protected IPropertyListener propertyListener;


	/** This composite holds a stack of editors, only the top one is shown */
	protected Composite editorComposite;

	/** The text editor composite, a child control of the editorComposite */
	protected Composite textEditorComposite;

	/** The duration editor composite, a child control of the editorComposiste */
	protected Composite durationEditorComposite;

	/** The date time editor composite, a child control of the editorComposiste */
	protected Composite dateTimeEditorComposite;

	// private int comboValue = -1;

	/** The input to the text editor. */
	protected TextEditorInput textEditorInput;

	/**
	 * Create a brand new shiny XPathExpressionEditor ...
	 */

	public XPathExpressionEditor() {
		super();
	}

	/**
	 * Create controls ..
	 */
	@Override
	public void createControls(Composite parent, BPELPropertySection aSection) {
		super.createControls(parent, aSection);
		this.section = aSection;

		createEditor(parent);
	}

	protected void createEditor(Composite parent) {

		TabbedPropertySheetWidgetFactory wf = getWidgetFactory();

		this.mainComposite = wf.createComposite(parent, SWT.NONE);
		FlatFormLayout layout = new FlatFormLayout();
		layout.marginWidth = layout.marginHeight = 0;
		mainComposite.setLayout(layout);
		mainComposite.setBackground(BPELUIPlugin.getPlugin().getColorRegistry()
				.get(IBPELUIConstants.COLOR_WHITE));
		comboLabel = wf.createLabel(mainComposite,
				Messages.XPathExpressionEditor_Expression_Type_2);

		combo = new Combo(mainComposite, SWT.BORDER | SWT.READ_ONLY | SWT.FLAT);

		// combo = wf.createCCombo(mainComposite);

		combo.add(TEXT_STRING);
		combo.add(LITERAL_STRING);

		combo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				updateEditorToType(combo.getSelectionIndex());
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		FlatFormData data = new FlatFormData();
		data.top = new FlatFormAttachment(combo, 0, SWT.CENTER);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(combo,
				-IDetailsAreaConstants.HSPACE);
		comboLabel.setLayoutData(data);

		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(
				comboLabel, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, 0);
		combo.setLayoutData(data);

		// Create the editor composite
		// The editor composite will contain one of N editors.
		// We use stack layout, because only one editor is visible at a time.

		editorComposite = wf.createComposite(mainComposite);
		editorComposite.setLayout(new StackLayout());

		data = new FlatFormData();
		data.top = new FlatFormAttachment(combo,
				2 * IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		editorComposite.setLayoutData(data);
	}

	protected void rearrangeComboAndEditorComposite() {

		boolean comboVisible = isLiteralType();

		combo.setVisible(comboVisible);
		comboLabel.setVisible(comboVisible);

		if (editorComposite != null) {
			FlatFormData data = (FlatFormData) editorComposite.getLayoutData();
			if (comboVisible) {
				data.top = new FlatFormAttachment(combo,
						2 * IDetailsAreaConstants.VSPACE);
			} else {
				data.top = new FlatFormAttachment(0,
						2 * IDetailsAreaConstants.VSPACE);
			}
		}

	}

	/**
	 * Gets the text editor composite, creates it if necessary and makes it
	 * visible by default.
	 * 
	 * @return the composite for the text editor ...
	 */

	Composite getTextEditorComposite() {

		if (textEditorComposite != null) {
			return textEditorComposite;
		}

		// otherwise create it ...
		TabbedPropertySheetWidgetFactory wf = getWidgetFactory();

		textEditorComposite = wf.createComposite(editorComposite);
		// Fill Layout ... and add border.
		FillLayout layout = new FillLayout();
		final int margin = 1;
		layout.marginHeight = margin;
		layout.marginWidth = margin;
		textEditorComposite.setLayout(layout);
		textEditorComposite.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				org.eclipse.swt.graphics.Rectangle bounds = textEditorComposite
						.getBounds();
				bounds.x = margin - 1;
				bounds.y = margin - 1;
				bounds.width = bounds.width - (margin * 2) + 1;
				bounds.height = bounds.height - (margin * 2) + 1;
				e.gc.drawRectangle(bounds);
			}
		});

		textEditor = (XPathTextEditor) createEditor(XPathTextEditor.EDITOR_ID,
				this.textEditorInput, textEditorComposite);

		textEditor.addPropertyListener(getPropertyListener());	

		return textEditorComposite;
	}

	Composite getDateTimeEditor() {
		if (dateTimeEditorComposite != null) {
			return dateTimeEditorComposite;
		}

		TabbedPropertySheetWidgetFactory wf = getWidgetFactory();

		dateTimeEditorComposite = wf.createComposite(editorComposite, SWT.NONE);

		FlatFormLayout layout = new FlatFormLayout();
		layout.marginWidth = layout.marginHeight = 0;
		dateTimeEditorComposite.setLayout(layout);

		Label label = wf.createLabel(dateTimeEditorComposite,
				Messages.XPathExpressionEditor_Date_Time_UTC_3);
		dateTimeSelector = new DateTimeSelector(wf, dateTimeEditorComposite,
				SWT.NONE, BPELDateTimeHelpers.yearMin,
				BPELDateTimeHelpers.yearMax);

		String body = (String) textEditorInput.getAdapter(String.class);

		int[] dateTime = BPELDateTimeHelpers.parseXPathDateTime(body, false);
		dateTimeSelector.setValues(dateTime);

		FlatFormData data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 10);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(dateTimeSelector,
				-IDetailsAreaConstants.HSPACE);
		label.setLayoutData(data);

		data = new FlatFormData();
		data.top = new FlatFormAttachment(0, 10);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(
				label, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		// data.right = new FlatFormAttachment(100, 0);
		dateTimeSelector.setLayoutData(data);

		dateTimeSelector.addSelectionListener(  new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// Push new values to the text editor document so that
				// it is reflected next time we shift
				// to the text editor.
				
				if (textEditorInput != null) {
					int[] values = dateTimeSelector.getValues();         		        
					textEditorInput.setBody ( BPELDateTimeHelpers.createXPathDateTime(values, false) , getModelObject() );
				}
				
				if (!((TextSection) section).isExecutingStoreCommand()) {
					notifyListeners();
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});	

		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				dateTimeEditorComposite,
				IHelpContextIds.PROPERTY_PAGE_WAIT_DATE);

		return dateTimeEditorComposite;
	}

	/**
	 * Get or create the duration editor.
	 * 
	 * @return
	 */

	Composite getDurationEditor() {
		
		if (durationEditorComposite != null) {			
			return durationEditorComposite;
		}
		
	    TabbedPropertySheetWidgetFactory wf = getWidgetFactory();
	    durationEditorComposite = wf.createComposite(editorComposite, SWT.NONE);
	    FlatFormLayout layout = new FlatFormLayout();
	    layout.marginWidth = layout.marginHeight = 0;
	    durationEditorComposite.setLayout(layout);
	    
	    Label label = wf.createLabel(durationEditorComposite, Messages.XPathExpressionEditor_Duration_4); 
	    durationSelector = new DurationSelector(wf, durationEditorComposite, SWT.NONE);
	    
	    FlatFormData data = new FlatFormData();
	    data.top = new FlatFormAttachment(0, 10);
	    data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(durationSelector, -IDetailsAreaConstants.HSPACE);	    
	    label.setLayoutData(data);

	    data = new FlatFormData();
	    data.top = new FlatFormAttachment(0, 10);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(label, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		// data.right = new FlatFormAttachment(100, 0);
		durationSelector.setLayoutData(data);

	    durationSelector.addSelectionListener( new SelectionListener() {	    	
			public void widgetSelected(SelectionEvent e) {
			 	
				// Push new values to the text editor document so that
				// it is reflected next time we shift
				// to the text editor.
				
				if (textEditorInput != null) {
					int[] duration = durationSelector.getValues();         		        
					textEditorInput.setBody ( BPELDateTimeHelpers.createXPathDuration(duration) , getModelObject() );
				}
			
				// 
			 	if (!((TextSection)section).isExecutingStoreCommand() ) {
			 		notifyListeners();
			 	}
			 }
			 public void widgetDefaultSelected(SelectionEvent e) { }
	    });
	    
	    PlatformUI.getWorkbench().getHelpSystem().setHelp(
	    		durationEditorComposite, IHelpContextIds.PROPERTY_PAGE_WAIT_DURATION);
	    
	    return durationEditorComposite;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#dispose()
	 */

	public void dispose() {

		if (textEditor != null) {
			getEditorManager().disposeEditor(textEditor);
			textEditor = null;
		}

		// This is the main composite of all the editors.
		if (editorComposite != null && !editorComposite.isDisposed()) {
			editorComposite.dispose();
			editorComposite = null;
		}
	}

	/**
	 * This is called from a number of places and we have to be smart to not
	 * create and re-create the editors every time refresh is called.
	 * 
	 */

	@Override
	public void refresh() {

		int editorType = combo.getSelectionIndex();

		if (editorType < 0) {
			String body = (String) textEditorInput.getAdapter(String.class);

			// TODO: try literal widgets!
			editorType = TEXT;
			if (BPELDateTimeHelpers.parseXPathDuration(body) != null) {
				editorType = LITERAL;
			} else if (BPELDateTimeHelpers.parseXPathDateTime(body, false) != null) {
				editorType = LITERAL;
			}
			// update the editor type
			combo.select(editorType);
		}

		updateEditorToType(editorType);
	}

	/**
	 * Update the editor to the right type.
	 * 
	 * @param editorType
	 */

	void updateEditorToType(int editorType) {

		String body = (String) textEditorInput.getAdapter(String.class);

		switch (editorType) {
		case TEXT:
			restackEditorComposite(getTextEditorComposite());
			break;
		case LITERAL:
			if (IEditorConstants.ET_DURATION.equals(getExprType())) {
				restackEditorComposite(getDurationEditor());

				int[] duration = BPELDateTimeHelpers.parseXPathDuration(body);
				if (duration != null) {
					durationSelector.setValues(duration);
				}
			} else {

				restackEditorComposite(getDateTimeEditor());

				int[] dateTime = BPELDateTimeHelpers.parseXPathDateTime(body,
						false);
				if (dateTime != null) {
					dateTimeSelector.setValues(dateTime);
				}
			}
			break;
		}
	}

	/**
	 * We re-stack the editors and have stack layout only show the top child
	 * control.
	 * 
	 * @param c
	 *            the editor composite for one of our editors.
	 */

	void restackEditorComposite(Composite c) {

		Layout layout = c.getParent().getLayout();

		if (layout instanceof StackLayout) {
			StackLayout stackLayout = (StackLayout) layout;
			if (c != stackLayout.topControl) {
				stackLayout.topControl = c;

				rearrangeComboAndEditorComposite();

				c.getParent().layout();
			}
		}
	}

	/**
	 * About to be hidden.
	 */

	public void aboutToBeHidden() {

	}

	/**
	 * The editor is about to be shown.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#aboutToBeShown()
	 */

	public void aboutToBeShown() {
		refresh();
	}

	/**
	 * If the editor is dirty it registers an ongoing change.
	 */
	protected IPropertyListener getPropertyListener() {

		if (propertyListener == null) {
			propertyListener = new IPropertyListener() {
				public void propertyChanged(Object source, int propId) {
					if (propId == IEditorPart.PROP_DIRTY) {
						boolean isEditorDirty = false;
						if (textEditor != null) {
							isEditorDirty = textEditor.isDirty();
						}
						if (isEditorDirty) {
							notifyListeners();
						}
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

		// TODO! this is bogus
		if (textEditor != null) {
			textEditor.setFocus();
		}
	}

	/**
	 * Return the body of the editor.
	 * 
	 * @see org.eclipse.bpel.ui.expressions.AbstractExpressionEditor#getBody()
	 */
	@Override
	public Object getBody() {

		int editorType = combo.getSelectionIndex();

		if (editorType == LITERAL) {
			if (IEditorConstants.ET_DURATION.equals(getExprType())) {
				int[] duration = durationSelector.getValues();
				return BPELDateTimeHelpers.createXPathDuration(duration);
			}
			int[] dateTime = dateTimeSelector.getValues();
			return BPELDateTimeHelpers.createXPathDateTime(dateTime, false);
		}
		return textEditor.getContents();
	}

	@Override
	public void setBody (Object body) {

		String value = (body instanceof String) ? (String) body : ""; //$NON-NLS-1$

		this.textEditorInput = new TextEditorInput(value, getModelObject());
		// Refresh the text editor input, if not set
		if (textEditor != null) {
			textEditor.setInput(this.textEditorInput);
		}
	}

	/**
	 * Answer if the editor edits literal type expressions. These have their own
	 * editor UI to deal with that.
	 * 
	 * @return
	 */

	boolean isLiteralType() {
		return IEditorConstants.ET_DURATION.equals(getExprType())
				|| IEditorConstants.ET_DATETIME.equals(getExprType());
	}

	/**
	 * Return the default body for the given edit UI type.
	 * 
	 * @return the default body to throw into the editor.
	 */

	public Object getDefaultBody() {

		if (isLiteralType()) {
			return getDefaultBody(LITERAL);
		}
		return getDefaultBody(TEXT);
	}

	protected String getDefaultBody(int comboValue) {
		String exprType = getExprType();
		switch (comboValue) {
		case TEXT: {
			if (IEditorConstants.ET_BOOLEAN.equals(exprType)) {
				return BOOLEAN_EXPR_DEFAULT;
			} else if (IEditorConstants.ET_UNSIGNED_INT.equals(exprType)) {
				return UNSIGNED_INT_EXPR_DEFAULT;
			}
			return ""; //$NON-NLS-1$
		}
		case LITERAL: {
			if (IEditorConstants.ET_DURATION.equals(exprType)) {
				return DEFAULT_DURATION_VALUE;
			}
			int[] dateTime = BPELDateTimeHelpers.getCurrentLocalDateTime();
			BPELDateTimeHelpers.convertLocalToGMT(dateTime);
			return BPELDateTimeHelpers.createXPathDateTime(dateTime, false);
		}
		}
		return ""; //$NON-NLS-1$
	}

	public void gotoTextMarker(IMarker marker, String codeType,
			Object modelObject) {
		// TODO
	}

	public boolean supportsExpressionType(String exprType, String exprContext) {
		// TODO: are there other contexts where XPath is supported?
		// Should we just return true everywhere?
		if (IEditorConstants.ET_BOOLEAN.equals(exprType))
			return true;
		if (IEditorConstants.ET_DATETIME.equals(exprType))
			return true;
		if (IEditorConstants.ET_DURATION.equals(exprType))
			return true;
		if (IEditorConstants.ET_ASSIGNFROM.equals(exprType))
			return true;
		if (IEditorConstants.ET_UNSIGNED_INT.equals(exprType))
			return true;
		return false;
	}

	/**
	 * 
	 * @see org.eclipse.bpel.ui.expressions.IExpressionEditor#markAsClean()
	 */

	public void markAsClean() {
		if (textEditor != null) {
			textEditor.markAsClean();
		}
	}
}
