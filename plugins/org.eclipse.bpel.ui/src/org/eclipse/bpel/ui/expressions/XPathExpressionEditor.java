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
import org.eclipse.bpel.ui.editors.TextEditor;
import org.eclipse.bpel.ui.editors.TextEditorInput;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.bpel.ui.properties.DateTimeSelector;
import org.eclipse.bpel.ui.properties.DurationSelector;
import org.eclipse.bpel.ui.properties.TextSection;
import org.eclipse.bpel.ui.util.BPELDateTimeHelpers;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetWidgetFactory;


/**
 * Expression language editor for XPath.
 */
public class XPathExpressionEditor extends AbstractExpressionEditor {

	private static final String TEXT_STRING = Messages.XPathExpressionEditor_Text_0, LITERAL_STRING = Messages.XPathExpressionEditor_Literal_1; 
	private static final int TEXT = 0, LITERAL = 1;

	protected static final String DEFAULT_DURATION_VALUE = "\'P0D\'"; //$NON-NLS-1$

	protected CCombo combo;
	protected Label comboLabel;
	protected Composite editorComposite;
	protected Composite mainComposite;
	protected TextEditor textEditor;
    protected DateTimeSelector dateTimeSelector;
    protected DurationSelector durationSelector;

	protected IPropertyListener propertyListener;
	private SelectionListener selectionListener;

	private int comboValue = -1;
	private String originalBody;

	protected boolean updating;
	
	public XPathExpressionEditor() {
		super();
		updating = false;
	}

	public void createControls(Composite parent, BPELPropertySection aSection) {
		super.createControls(parent, aSection);
		createEditor(parent);
	}

	protected void createEditor(Composite parent) {
	    TabbedPropertySheetWidgetFactory wf = getWidgetFactory();
	    this.mainComposite = wf.createComposite(parent, SWT.NONE);
	    FlatFormLayout layout = new FlatFormLayout();
	    layout.marginWidth = layout.marginHeight = 0;
	    mainComposite.setLayout(layout);
	    mainComposite.setBackground(BPELUIPlugin.getPlugin().getColorRegistry().get(IBPELUIConstants.COLOR_WHITE));
	    comboLabel = wf.createLabel(mainComposite, Messages.XPathExpressionEditor_Expression_Type_2); 
	    combo = wf.createCCombo(mainComposite);

	    combo.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                int newComboValue = combo.getSelectionIndex();
                if (newComboValue == comboValue) return;
                setBody(getDefaultBody(newComboValue));
                refresh();
            }
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });
	    
	    FlatFormData data = new FlatFormData();
	    data.top = new FlatFormAttachment(combo, 0, SWT.CENTER);
	    data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(combo, -IDetailsAreaConstants.HSPACE);	    
		comboLabel.setLayoutData(data);

	    data = new FlatFormData();
	    data.top = new FlatFormAttachment(0, 0);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(comboLabel, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, 0);
	    combo.setLayoutData(data);
	}
	protected void populateCombo() {
		combo.removeAll();
		combo.add(TEXT_STRING);
	    if (IEditorConstants.ET_DATETIME.equals(getExprType()) ||
	    	IEditorConstants.ET_DURATION.equals(getExprType()))
	    {
	    	combo.add(LITERAL_STRING);
    	}
	    rearrangeComboAndEditorComposite();
	}
	protected void rearrangeComboAndEditorComposite() {
	    boolean comboVisible = (combo.getItemCount() > 1);
	    combo.setVisible(comboVisible);
	    comboLabel.setVisible(comboVisible);
		if (editorComposite != null) {
		    FlatFormData data = (FlatFormData)editorComposite.getLayoutData();
		    if (comboVisible) {
		    	data.top = new FlatFormAttachment(combo, IDetailsAreaConstants.VSPACE);
			} else {
				data.top = new FlatFormAttachment(0, 0);
			}
		}
	}
	
	protected void createEditorComposite(int comboValue) {
	    TabbedPropertySheetWidgetFactory wf = getWidgetFactory();
	    if (comboValue != TEXT) {
	    	editorComposite = wf.createComposite(mainComposite);
	    	editorComposite.setLayout(new FillLayout());
	    } else {
	    	editorComposite = BPELUtil.createBorderComposite(mainComposite, wf);
	    }
	    FlatFormData data = new FlatFormData();
	    data.top = new FlatFormAttachment(combo, IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
	    editorComposite.setLayoutData(data);
	    rearrangeComboAndEditorComposite();
	}
	
	protected void createTextEditor() {
		IEditorInput input = new TextEditorInput(originalBody);
		textEditor = (TextEditor) createEditor(TextEditor.EDITOR_ID, input, editorComposite);
	    textEditor.addPropertyListener(getPropertyListener());
	}

	protected void createDateTimeEditor() {
	    TabbedPropertySheetWidgetFactory wf = getWidgetFactory();
	    Composite dateTimeComposite = wf.createComposite(editorComposite, SWT.NONE);
	    FlatFormLayout layout = new FlatFormLayout();
	    layout.marginWidth = layout.marginHeight = 0;
	    dateTimeComposite.setLayout(layout);
	    
	    Label label = wf.createLabel(dateTimeComposite, Messages.XPathExpressionEditor_Date_Time_UTC_3); 
	    dateTimeSelector = new DateTimeSelector(wf, dateTimeComposite, SWT.NONE,
	    	BPELDateTimeHelpers.yearMin, BPELDateTimeHelpers.yearMax);
	    
	    int[] dateTime = BPELDateTimeHelpers.parseXPathDateTime(originalBody, false);
	    dateTimeSelector.setValues(dateTime);
	    
	    FlatFormData data = new FlatFormData();
	    data.top = new FlatFormAttachment(0, 10);
	    data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(dateTimeSelector, -IDetailsAreaConstants.HSPACE);	    
	    label.setLayoutData(data);

	    data = new FlatFormData();
	    data.top = new FlatFormAttachment(0, 10);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(label, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		//data.right = new FlatFormAttachment(100, 0);
	    dateTimeSelector.setLayoutData(data);

	    if (selectionListener == null) {
	        selectionListener = new SelectionListener() {
                public void widgetSelected(SelectionEvent e) {
                	if (!((TextSection)section).isExecutingStoreCommand() && !updating) {
                		notifyListeners();
                	}
                }
                public void widgetDefaultSelected(SelectionEvent e) {
                }
            };
	    }
	    dateTimeSelector.addSelectionListener(selectionListener);

	    PlatformUI.getWorkbench().getHelpSystem().setHelp(
	    	dateTimeComposite, IHelpContextIds.PROPERTY_PAGE_WAIT_DATE);
	}
	
	protected void createDurationEditor() {
	    TabbedPropertySheetWidgetFactory wf = getWidgetFactory();
	    Composite durationComposite = wf.createComposite(editorComposite, SWT.NONE);
	    FlatFormLayout layout = new FlatFormLayout();
	    layout.marginWidth = layout.marginHeight = 0;
	    durationComposite.setLayout(layout);
	    
	    Label label = wf.createLabel(durationComposite, Messages.XPathExpressionEditor_Duration_4); 
	    durationSelector = new DurationSelector(wf, durationComposite, SWT.NONE);
	    
	    FlatFormData data = new FlatFormData();
	    data.top = new FlatFormAttachment(0, 10);
	    data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(durationSelector, -IDetailsAreaConstants.HSPACE);	    
	    label.setLayoutData(data);

	    data = new FlatFormData();
	    data.top = new FlatFormAttachment(0, 10);
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(label, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		//data.right = new FlatFormAttachment(100, 0);
		durationSelector.setLayoutData(data);

	    if (selectionListener == null) {
	        selectionListener = new SelectionListener() {
                public void widgetSelected(SelectionEvent e) {
                	if (!((TextSection)section).isExecutingStoreCommand() && !updating) {
                		notifyListeners();
                	}
                }
                public void widgetDefaultSelected(SelectionEvent e) {
                }
            };
	    }
	    durationSelector.addSelectionListener(selectionListener);

	    PlatformUI.getWorkbench().getHelpSystem().setHelp(
	    	durationComposite, IHelpContextIds.PROPERTY_PAGE_WAIT_DURATION);
	}
	
	public void dispose() {
		disposeEditors();
	}

	protected void disposeEditors() {
		if (textEditor != null) {
			getEditorManager().disposeEditor(textEditor);
			textEditor = null;
		}
	    if (dateTimeSelector != null) {
	        dateTimeSelector = null;
	    }
	    if (durationSelector != null) {
	        durationSelector = null;
	    }
		if (editorComposite != null && !editorComposite.isDisposed()) {
			editorComposite.dispose();
			editorComposite = null;
		}
	}
	
	public void refresh() {
//		int oldComboValue = comboValue;
		boolean isDuration = IEditorConstants.ET_DURATION.equals(getExprType());
		
		// TODO: try literal widgets!
		comboValue = TEXT;
		if (isDuration && BPELDateTimeHelpers.parseXPathDuration(originalBody) != null) {
			comboValue = LITERAL;
		} else if (BPELDateTimeHelpers.parseXPathDateTime(originalBody, false) != null) {
			comboValue = LITERAL;
		}
		combo.select(comboValue);
		
	    disposeEditors();
	    createEditorComposite(comboValue);
	    boolean oldValue = updating;
	    updating = true;
	    try {
		    switch (comboValue) {
		    	case TEXT:
		    	    createTextEditor();
		    	    break;
		    	case LITERAL:
		    		if (IEditorConstants.ET_DURATION.equals(getExprType())) {
		    			createDurationEditor();
		    			int[] duration = BPELDateTimeHelpers.parseXPathDuration(originalBody);
		    			if (duration != null) durationSelector.setValues(duration);
		    		} else {
		    			createDateTimeEditor();
			        	int[] dateTime = BPELDateTimeHelpers.parseXPathDateTime(originalBody, false);
			        	if (dateTime != null) dateTimeSelector.setValues(dateTime);
		    		}
		    	    break;
		    }
	    } finally {
	    	updating = oldValue;
	    }
	    mainComposite.layout(true);
	}

	public void aboutToBeHidden() {
	}
	
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
					if (!updating && propId == IEditorPart.PROP_DIRTY) {
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
		if (textEditor != null) textEditor.setFocus();
	}

	public Object getBody() {
	    if (comboValue == LITERAL) {
	    	if (IEditorConstants.ET_DURATION.equals(getExprType())) {
	    		int[] duration = durationSelector.getValues();
		        return BPELDateTimeHelpers.createXPathDuration(duration);
	    	}
	    	int[] dateTime = dateTimeSelector.getValues();
		    return BPELDateTimeHelpers.createXPathDateTime(dateTime, false);
	    }
		return textEditor.getContents();
	}
	
	public void setBody(Object body) {
		this.originalBody = (body instanceof String)? (String)body : ""; //$NON-NLS-1$
	    populateCombo();
		refresh();
	}

	public Object getDefaultBody() {
		if (IEditorConstants.ET_DURATION.equals(getExprType()) ||
			IEditorConstants.ET_DATETIME.equals(getExprType()))
		{
			return getDefaultBody(LITERAL);
		}
		return getDefaultBody(TEXT);
	}

	protected String getDefaultBody(int comboValue) {
		String exprType = getExprType();
		switch (comboValue) {
			case TEXT: {
				if (IEditorConstants.ET_BOOLEAN.equals(exprType)) {
					return "true"; //$NON-NLS-1$
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
	
	public void gotoTextMarker(IMarker marker, String codeType, Object modelObject) {
		// TODO
	}
	
	public boolean supportsExpressionType(String exprType, String exprContext) {
		// TODO: are there other contexts where XPath is supported?
		// Should we just return true everywhere?
		if (IEditorConstants.ET_BOOLEAN.equals(exprType)) return true;
		if (IEditorConstants.ET_DATETIME.equals(exprType)) return true;
		if (IEditorConstants.ET_DURATION.equals(exprType)) return true;
		if (IEditorConstants.ET_ASSIGNFROM.equals(exprType)) return true;
		return false;
	}
	
	public void markAsClean() {
		if (textEditor != null) textEditor.markAsClean();
	}
}
