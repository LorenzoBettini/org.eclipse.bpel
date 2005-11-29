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

import java.util.ArrayList;

import org.eclipse.bpel.common.ui.details.ChangeHelper;
import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.details.viewers.CComboViewer;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.commands.SetExpressionCommand;
import org.eclipse.bpel.ui.details.providers.ExpressionEditorDescriptorContentProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.expressions.IExpressionEditor;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ExpressionEditorDescriptor;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;


/**
 * Base class with some shared behaviour for details panes that edit
 * an expression.
 */
public abstract class ExpressionSection extends TextSection {

	protected String editorLanguage;
	protected CCombo expressionLanguageCCombo;
	protected CComboViewer expressionLanguageViewer;
	protected ChangeHelper expressionChangeHelper;
	protected ExpressionComboContentProvider expressionComboContentProvider;
	protected Composite editorAreaComposite, parentComposite;
	
	protected boolean hasNoEditor;
	
	// Pseudo-model object to represent no expression at all (in which case no editor
	// is used).
	protected static final Object NO_EXPRESSION = new Object();
	
	// Pseudo-model object to represent the absence of an expression language within
	// the expression (i.e. the expression language is inherited from the Process).
	protected static final Object SAME_AS_PARENT = new Object();
	
	protected static boolean objectsEqual(Object lhs, Object rhs) {
		if (lhs == null) return (rhs == null);
		return lhs.equals(rhs);
	}
	

	/**
	 * A content provider which (1) adds the NO_EXPRESSION and SAME_AS_PARENT elements,
	 * (2) ensures that the selected object is in the list, and (3) removes any
	 * elements from the list which are not supported (unless they happen to be
	 * the selected object at the moment--an error case).
	 */
	class ExpressionComboContentProvider extends ExpressionEditorDescriptorContentProvider {
		Object selectedObject;
		public Object[] getElements(Object inputElement) {
			Object[] descriptors = super.getElements(inputElement);
			int descriptorCount = descriptors.length;
			ArrayList result = new ArrayList(descriptorCount+2);
			if (!isExpressionOptional() || selectedObject==NO_EXPRESSION) {
				result.add(NO_EXPRESSION);
			}
			if (selectedObject==SAME_AS_PARENT || allowItem(SAME_AS_PARENT)) {
				result.add(SAME_AS_PARENT);
			}
			for (int i = 0; i < descriptorCount; ++i) {
				if (objectsEqual(selectedObject, descriptors[i]) ||
					allowItem(descriptors[i])) result.add(descriptors[i]);
			}
			if (!result.contains(selectedObject)) result.add(selectedObject);
			return result.toArray();
		}
		public boolean allowItem(Object element) {
			String language = getEffectiveLanguage(getExpressionLanguage(element));
			try {
				IExpressionEditor editor = BPELUIRegistry.getInstance().getExpressionEditor(language);
				return (editor == null)? false : isEditorSupported(editor);
			} catch (CoreException e) {
				BPELUIPlugin.log(e);
				return false;
			}
		}
		public Object getSelectedObject() {
			return selectedObject;
		}
		public void setSelectedObject(Object selectedObject) {
			this.selectedObject = selectedObject;
		}
	}
	
	protected void addAllAdapters() {
		super.addAllAdapters();
		Expression e = getExprFromModel();
		if (e != null) {
			adapters[0].addToObject(e);
		}
	}

	protected void disposeEditor() {
	    super.disposeEditor();
		editorLanguage = null;
	}
	
	protected Object getDefaultBody(String newLanguage, String exprType, String exprContext) {
		IExpressionEditor editor = null;
		try {
			newLanguage = getEffectiveLanguage(newLanguage);
			editor = BPELUIRegistry.getInstance().getExpressionEditor(newLanguage);
		} catch (CoreException e) {
			BPELUIPlugin.log(e);
		}
		// TODO: call supportsExpressionType in the right place
		editor.setExpressionType(exprType, exprContext);
		editor.setModelObject(getInput());
		return editor.getDefaultBody();
	}
	
	protected Command newEraseModelCommand() {
		return new SetExpressionCommand(getInput(), getModelExpressionType(), getModelExpressionSubType(), null);
	}
	
	protected void createExpressionLanguageWidgets(final Composite composite) {
		FlatFormData data;

		// TODO: Maybe replace this with a standard viewer listener if we are
		// sure there is no selection badness that occurs with combos
		expressionChangeHelper = new ChangeHelper(getCommandFramework()) {
			public String getLabel() {
				return IBPELUIConstants.CMD_EDIT_EXPRESSIONLANGUAGE;
			}
			public Command createApplyCommand() {
				String value = expressionLanguageCCombo.getText();
				boolean found = false;
				int foundIndex = 0;
				if (value != null) {
					// Check if the text matches one of the combo items!
					String[] items = expressionLanguageCCombo.getItems();
					for (int i = 0; !found && i<items.length; i++) {
						if (value.equals(items[i])) { found = true; foundIndex = i; }
					}
				}
				String language = value;
				if (found) {
					IStructuredSelection selection = (IStructuredSelection) expressionLanguageViewer.getSelection();
					Object firstElement = selection.getFirstElement();
					if (firstElement == null) {
						firstElement = expressionLanguageViewer.getElementAt(foundIndex);
					}
					if (firstElement == NO_EXPRESSION) {
						return wrapInShowContextCommand(newEraseModelCommand());
					}
					language = getExpressionLanguage(firstElement);
				}
				CompoundCommand cmd = new CompoundCommand();
				Expression exp = BPELFactory.eINSTANCE.createCondition();
				exp.setExpressionLanguage(language);
				Object newDefaultBody = getDefaultBody(language, getExpressionType(), getExpressionContext()); 
				// Figure out what the new default value should be for this expression,
				// and install it in the model.  It is necessary to do this before we
				// properly create the editor (which happens when we update widgets in
				// response to the model change).
				exp.setBody(newDefaultBody);
				cmd.add(new SetExpressionCommand(getInput(), getModelExpressionType(),
					getModelExpressionSubType(), exp));

				return wrapInShowContextCommand(cmd);
			}
			public void restoreOldState() {
				updateExpressionLanguageWidgets();
			}
			public void handleEvent(Event event) {
				if (event.type == SWT.Selection) {
					finish();
				} else {
					super.handleEvent(event);
				}
			}
		};
		Label expressionLanguageLabel = wf.createLabel(composite, Messages.ExpressionSection_Expression_language_1); 
		expressionLanguageCCombo = wf.createCCombo(composite, SWT.FLAT);

		expressionLanguageViewer = new CComboViewer(expressionLanguageCCombo);
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(expressionLanguageLabel, STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		expressionLanguageCCombo.setLayoutData(data);

		expressionLanguageViewer.setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				if (element == NO_EXPRESSION) return Messages.ExpressionSection_No_Expression_2; 
				if (element == SAME_AS_PARENT) {
					String text = getBPELEditor().getProcess().getExpressionLanguage();
					if (text == null) text = IBPELUIConstants.EXPRESSION_LANGUAGE_XPATH;
					ExpressionEditorDescriptor descriptor = BPELUIRegistry.getInstance().getExpressionEditorDescriptor(text);
					if (descriptor != null) text = descriptor.getLabel();
					return NLS.bind(Messages.ExpressionSection_Same_as_Process_1, (new Object[] { text })); 
				}
				if (element instanceof String) return (String)element;
				ExpressionEditorDescriptor descriptor = (ExpressionEditorDescriptor) element;
				String text = descriptor.getLabel();
				return (text != null) ? text : descriptor.getExpressionLanguage();
			}
		});

		expressionComboContentProvider = new ExpressionComboContentProvider();
		expressionLanguageViewer.setContentProvider(expressionComboContentProvider);
		expressionLanguageViewer.setSorter(ModelViewerSorter.getInstance());
		
		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(expressionLanguageCCombo, -IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(expressionLanguageCCombo, 0, SWT.CENTER);
		expressionLanguageLabel.setLayoutData(data);

		expressionChangeHelper.startListeningTo(expressionLanguageCCombo);
		expressionChangeHelper.startListeningForEnter(expressionLanguageCCombo);
	}
	
	/**
	 * This method is used by subclasses who need to select a language programmatically.
	 */
	protected void doChooseExpressionLanguage(Object model) {
		if (((StructuredSelection)expressionLanguageViewer.getSelection()).getFirstElement() == model) return;
		expressionComboContentProvider.setSelectedObject(model);
		refreshCCombo(expressionLanguageViewer, model);
		expressionChangeHelper.finish();
		updateWidgets();
	}
	
	protected void updateExpressionLanguageWidgets() {
        Object model = NO_EXPRESSION;
        if (getExprFromModel() != null) {
			String language = getExpressionLanguageFromModel();
	        if (language == null) {
	            model = SAME_AS_PARENT;
	        } else {
	            model = BPELUIRegistry.getInstance().getExpressionEditorDescriptor(language);
	            if (model == null) model = language;
	        }
        }
        expressionComboContentProvider.setSelectedObject(model);

		if (expressionLanguageViewer != null) {
			expressionChangeHelper.startNonUserChange();
			try {
				expressionLanguageViewer.setInput(new Object());
		        refreshCCombo(expressionLanguageViewer, model);
			} finally {
				expressionChangeHelper.finishNonUserChange();
			}
		}
	}
	
	protected void basicSetInput(EObject newInput) {
		super.basicSetInput(newInput);
		
		// since now we have a model object we should update the content providers
		// to reflect the spec-compliance mode of the current process
		expressionChangeHelper.startNonUserChange();
		try {
			expressionComboContentProvider.setSpecCompliant(ModelHelper.isSpecCompliant(getInput()));
			expressionLanguageViewer.refresh();
		} finally {
			expressionChangeHelper.finishNonUserChange();
		}
		
	}
	
	protected boolean isExpressionOptional() { return false; }

	protected abstract String getExpressionType();
	protected abstract String getExpressionContext();

	
	/**
	 * Returns the expressionLanguage string underlying the given combo element.  For
	 * cases other than NO_EXPRESSION, this is the proper value to store into the model.
	 */
	protected String getExpressionLanguage(Object comboElement) {
		if (comboElement == NO_EXPRESSION || comboElement == SAME_AS_PARENT) return null;
		String language = null;
		if (comboElement instanceof ExpressionEditorDescriptor) {
		    language = ((ExpressionEditorDescriptor)comboElement).getExpressionLanguage();
		} else if (comboElement instanceof String) {
			language = (String)comboElement;
		}
		if ("".equals(language))  language = null; //$NON-NLS-1$
		return language;
	}
	
	/**
	 * Returns the expression language which will be in effect if this language is
	 * read from/stored into the model.  (i.e. if language is null, the value is
	 * inherited from the process or the default, XPath). 
	 */
	protected String getEffectiveLanguage(String language) {
		if (language == null) {
			language = getBPELEditor().getProcess().getExpressionLanguage();
			if (language == null) {
				language = IBPELUIConstants.EXPRESSION_LANGUAGE_XPATH;
			}
		}
		return language;
	}
	
	/**
	 * Return the actual namespace from the expression, or null if not set.
	 */
	protected String getExpressionLanguageFromModel() {
		Expression expression = getExprFromModel();
		if (expression == null) return null;
		return expression.getExpressionLanguage();
	}
	
	// Do not override this method (except in WaitConditionSection)
	protected int getModelExpressionType() {
		return ModelHelper.expressionTypeAndContext2Kind(getExpressionType(), getExpressionContext());
	}
	protected int getModelExpressionSubType() {
		return ModelHelper.expressionTypeAndContext2SubKind(getExpressionType(), getExpressionContext());
	}
	
	protected Expression getExprFromModel() {
		return ModelHelper.getExpression(getInput(), getModelExpressionType());
	}
	
	protected boolean isBodyAffected(Notification n) {
		if (n.getOldValue() instanceof Expression ||
			n.getNewValue() instanceof Expression ||
			n.getNotifier() instanceof Expression) return true;
		return ModelHelper.isExpressionAffected(getInput(), n, getModelExpressionType());
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
	
	protected void createClient(Composite parent) {
		parentComposite = createFlatFormComposite(parent);
		createExpressionLanguageWidgets(parentComposite);
		createChangeHelper();
	}
	
	protected void createEditorArea(Composite parent) {
		if (editorAreaComposite != null) {
			disposeEditor();
			editorAreaComposite.dispose();
			editorAreaComposite = null;
		}
		editorAreaComposite = createFlatFormComposite(parent);
		FlatFormData data = new FlatFormData();
		data.top = new FlatFormAttachment(expressionLanguageCCombo, IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		editorAreaComposite.setLayoutData(data);

		Expression expr = getExprFromModel();
		if (expr == null) {
			hasNoEditor = true;
			createNoEditorWidgets(editorAreaComposite);
		} else {
			hasNoEditor = false;
		    editorAreaComposite.setLayout(new FillLayout());
			createEditor(editorAreaComposite);
			IExpressionEditor editor = getExpressionEditor();
			// TODO: call supportsExpressionType in the right place
			editor.setExpressionType(getExpressionType(), getExpressionContext());
			editor.setModelObject(getInput());
			editor.setBody(expr.getBody());
			if (!isHidden) {
				// We destroyed and re-created the editor, so it doesn't know
				// that it's supposed to be visible. Tell it now.
				editor.aboutToBeShown();
			}
		}
		parentComposite.layout(true);
	}

	protected void createNoEditorWidgets(Composite composite) {}
	
	protected void createEditor(Composite parent) {
		try {
			String language = getEffectiveLanguage(getExpressionLanguageFromModel());
			editor = BPELUIRegistry.getInstance().getExpressionEditor(language);
			editorLanguage = language;
			editor.createControls(parent, this);
			editor.addListener(new IExpressionEditor.Listener() {
				public void notifyChanged() {
					if (!updating) { // && !isExecutingStoreCommand()) {
						notifyEditorChanged();
					}
				}
			});
		} catch (CoreException e) {
			BPELUIPlugin.log(e);
		}
	}
	
	protected void updateEditor() {
	    Expression e = getExprFromModel();
	    boolean newEditorArea = false;
		if (e == null) {
			newEditorArea = true;
		} else {
			if (editor == null) {
			    createEditorArea(parentComposite);
			} else {
				String newLanguage = getEffectiveLanguage(getExpressionLanguageFromModel());
				if (!newLanguage.equals(editorLanguage)) {
				    createEditorArea(parentComposite);
				}
			}
		}
		if (newEditorArea) {
			createEditorArea(parentComposite);
		} else if (e != null) {
			editor.setExpressionType(getExpressionType(), getExpressionContext());
			editor.setModelObject(getInput());
			editor.setBody(e.getBody());
		}

		updateExpressionLanguageWidgets();
		editorAreaComposite.layout(true);
	}
	
	/**
	 * This is used by the Expression Language combo to filter out editors that
	 * can't be used with the current type/context.
	 */
	protected boolean isEditorSupported(IExpressionEditor editor) {
		return editor.supportsExpressionType(getExpressionType(), getExpressionContext());
	}
	
	/**
	 * Whether the marker use type is useful for this section. 
	 */
	protected boolean isValidClientUseType(String useType) {
		return false;
	}
	
	public void aboutToBeHidden() {
		super.aboutToBeHidden();
		if (expressionChangeHelper != null) {
			getCommandFramework().notifyChangeDone(expressionChangeHelper);
		}
	}
}