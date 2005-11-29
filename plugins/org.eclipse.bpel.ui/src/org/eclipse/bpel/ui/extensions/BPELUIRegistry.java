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
package org.eclipse.bpel.ui.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.IHoverHelper;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.bpelactions.AbstractBPELAction;
import org.eclipse.bpel.ui.expressions.DefaultExpressionEditor;
import org.eclipse.bpel.ui.expressions.IExpressionEditor;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;


/**
 * Responsible for getting information from the BPEL UI extension points.
 */
public class BPELUIRegistry {

	static final String EXTPT_HOVERHELPERS = "hoverHelpers"; //$NON-NLS-1$
	static final String ELEMENT_HOVERHELPER = "hoverHelper"; //$NON-NLS-1$
	static final String EXTPT_EXPRESSION_EDITORS = "expressionEditors"; //$NON-NLS-1$
	static final String ELEMENT_EDITOR = "editor"; //$NON-NLS-1$
	static final String ATT_EXPRESSION_LANGUAGE = "expressionLanguage"; //$NON-NLS-1$
	static final String ATT_CLASS = "class"; //$NON-NLS-1$
	static final String ATT_LABEL = "label"; //$NON-NLS-1$
	static final String EXTPT_ACTIONS = "actions"; //$NON-NLS-1$
	static final String ELEMENT_CATEGORY = "category"; //$NON-NLS-1$
	static final String ATT_NAME = "name"; //$NON-NLS-1$
	static final String ATT_ID = "id"; //$NON-NLS-1$
	static final String ELEMENT_ACTION = "action"; //$NON-NLS-1$
	static final String ATT_CATEGORY_ID = "categoryId"; //$NON-NLS-1$
	static final String EXTPT_MODELLISTENER = "modelListener"; //$NON-NLS-1$
	static final String ELEMENT_LISTENER = "listener"; //$NON-NLS-1$
	static final String ATT_SPEC_COMPLIANT = "specCompliant"; //$NON-NLS-1$

	private static BPELUIRegistry instance;

	private Map languageToEditorDescriptor;
	private HoverHelperDescriptor hoverHelper;
	private ActionCategoryDescriptor[] actionCategoryDescriptors;
	private ActionDescriptor[] actionDescriptors;
	private ListenerDescriptor[] listenerDescriptors;
	
	private BPELUIRegistry() {
		readExpressionLanguageEditors();
		readHoverHelpers();
		readActions();
		readListeners();
	}

	public static BPELUIRegistry getInstance() {
		if (instance == null) {
			instance = new BPELUIRegistry();
		}
		return instance;
	}

	public IHoverHelper getHoverHelper() throws CoreException {
		if (hoverHelper == null) return null;
		return hoverHelper.createHoverHelper();
	}
	
	/**
	 * Returns an expression editor for the given expression language.
	 */
	public IExpressionEditor getExpressionEditor(String expressionLanguage) throws CoreException {
		ExpressionEditorDescriptor descriptor = (ExpressionEditorDescriptor) languageToEditorDescriptor.get(expressionLanguage);
		if (descriptor == null) {
			return new DefaultExpressionEditor();
		}
		IExpressionEditor editor = descriptor.createEditor();
		return editor;
	}

	/**
	 * Returns an expression editor descriptor for the given expression language.
	 */
	public ExpressionEditorDescriptor getExpressionEditorDescriptor(String expressionLanguage) {
		return (ExpressionEditorDescriptor) languageToEditorDescriptor.get(expressionLanguage);
	}

	private void readExpressionLanguageEditors() {
		languageToEditorDescriptor = new HashMap();
		IConfigurationElement[] extensions = getConfigurationElements(EXTPT_EXPRESSION_EDITORS);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement editor = extensions[i];
			if (editor.getName().equals(ELEMENT_EDITOR)) {
				String language = editor.getAttribute(ATT_EXPRESSION_LANGUAGE);
				String clazz = editor.getAttribute(ATT_CLASS);
				if (language == null || clazz == null) {
					String pluginId = BPELUIPlugin.getPlugin().getBundle().getSymbolicName(); 
					IStatus status = new Status(IStatus.ERROR, pluginId, IBPELUIConstants.MISSING_ATTRIBUTE, Messages.BPELUIRegistry_Expression_language_editors_must_provide_expressionLanguage_and_class__8, null); 
					BPELUIPlugin.getPlugin().getLog().log(status);
				} else {
					ExpressionEditorDescriptor descriptor = new ExpressionEditorDescriptor();
					descriptor.setExpressionLanguage(language);
					descriptor.setElement(editor);
					String label = editor.getAttribute(ATT_LABEL);
					descriptor.setLabel(label);
					languageToEditorDescriptor.put(language, descriptor);
				}
			}
		}
	}

	public ActionDescriptor[] getActionDescriptors() {
	    return actionDescriptors;
	}
	
	/**
	 * Returns the ActionDescriptor for the given EClass.
	 */
	public ActionDescriptor getActionDescriptor(EClass target) {
		for (int i = 0; i < actionDescriptors.length; i++) {
			ActionDescriptor descriptor = actionDescriptors[i];
			if (descriptor.getAction().getModelType() == target) {
				return descriptor;
			}
		}
	    return null;
	}
	
	public ActionCategoryDescriptor[] getActionCategoryDescriptors() {
	    return actionCategoryDescriptors;
	}
	
	public ListenerDescriptor[] getListenerDescriptors() {
	    return listenerDescriptors;
	}
	
	private void readHoverHelpers() {
		IConfigurationElement[] extensions = getConfigurationElements(EXTPT_HOVERHELPERS);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement helper = extensions[i];
			if (helper.getName().equals(ELEMENT_HOVERHELPER)) {
				String clazz = helper.getAttribute(ATT_CLASS);
				if (clazz != null) {
					HoverHelperDescriptor descriptor = new HoverHelperDescriptor();
					descriptor.setElement(helper);
					this.hoverHelper = descriptor;
				}
			}
		}
	}

	/**
	 * Read all the actions and categories.
	 */
	private void readActions() {
	    List categories = new ArrayList();
	    List actions = new ArrayList();
		IConfigurationElement[] extensions = getConfigurationElements(EXTPT_ACTIONS);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			if (element.getName().equals(ELEMENT_CATEGORY)) {
				String name = element.getAttribute(ATT_NAME);
				String id = element.getAttribute(ATT_ID);
				if (name != null && id != null) {
					ActionCategoryDescriptor descriptor = new ActionCategoryDescriptor();
					descriptor.setName(name);
					descriptor.setId(id);
					categories.add(descriptor);
				}
			} else if (element.getName().equals(ELEMENT_ACTION)) {
				String id = element.getAttribute(ATT_ID);
				String category = element.getAttribute(ATT_CATEGORY_ID);
				String specCompliant = element.getAttribute(ATT_SPEC_COMPLIANT);
				if (category != null && id != null) {
					ActionDescriptor descriptor = new ActionDescriptor();
					descriptor.setId(id);
					descriptor.setCategoryId(category);
					descriptor.setSpecCompliant(Boolean.valueOf(specCompliant).booleanValue());
					try {
					    AbstractBPELAction action = (AbstractBPELAction)element.createExecutableExtension(ATT_CLASS);						
						descriptor.setAction(action);
					} catch (CoreException e) {
						BPELUIPlugin.log(e);
					}
					actions.add(descriptor);
					
					// register AdapterFactory - since it has to be done only once we do it here
					AdapterFactory factory = descriptor.getAction().getAdapterFactory();
					if (factory != null) {
					    BPELUtil.registerAdapterFactory(descriptor.getAction().getModelType(), factory);
					}
				}
			}
		}
		
		actionCategoryDescriptors = new ActionCategoryDescriptor[categories.size()];
		categories.toArray(actionCategoryDescriptors);
		actionDescriptors = new ActionDescriptor[actions.size()];
		actions.toArray(actionDescriptors);
	}

	/**
	 * Read all the model listeners
	 */
	private void readListeners() {
	    List listeners = new ArrayList();
		IConfigurationElement[] extensions = getConfigurationElements(EXTPT_MODELLISTENER);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			if (element.getName().equals(ELEMENT_LISTENER)) {
				String id = element.getAttribute(ATT_ID);
				if (id != null) {
					ListenerDescriptor descriptor = new ListenerDescriptor();
					descriptor.setId(id);
					try {
					    IModelListener listener = (IModelListener)element.createExecutableExtension(ATT_CLASS);
						descriptor.setModelListener(listener);
					} catch (CoreException e) {
						BPELUIPlugin.log(e);
					}
					listeners.add(descriptor);
				}
			}
		}
		
		listenerDescriptors = new ListenerDescriptor[listeners.size()];
		listeners.toArray(listenerDescriptors);
	}

	/**
	 * Given an extension point name returns its configuration elements.
	 */
	private IConfigurationElement[] getConfigurationElements(String extensionPointId) {
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
			BPELUIPlugin.PLUGIN_ID, extensionPointId); 
		if (extensionPoint == null) {
			return null;
		}
		return extensionPoint.getConfigurationElements();
	}

	public ExpressionEditorDescriptor[] getExpressionEditorDescriptors() {
		Collection descriptors = languageToEditorDescriptor.values();
		return (ExpressionEditorDescriptor[]) descriptors.toArray(new ExpressionEditorDescriptor[descriptors.size()]);
	}
}
