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

import java.util.Vector;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.model.messageproperties.Property;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.providers.VariableTreeContentProvider;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDNamedComponent;

import org.eclipse.wst.wsdl.Part;

/**
 * An AssignCategory presenting a tree from which the user can select any of:
 *  - a Variable
 *  - a Part within a Variable
 *  - some XSD element within a Part within a Variable.
 */
public class VariablePartAssignCategory extends AssignCategoryBase {

	Label nameLabel;
	Text nameText;
	Tree variableTree;
	TreeViewer variableViewer;
	Button wizardButton = null;

	VariableTreeContentProvider variableContentProvider;

	protected VariablePartAssignCategory(boolean isFrom, BPELPropertySection ownerSection) {
		super(isFrom, ownerSection);
	}

	public String getName() { return Messages.VariablePartAssignCategory_Variable_or_Part_1; } 

	protected boolean isPropertyTree() { return false; }
	
	protected void updateQueryFieldFromTreeSelection() {
		if (displayQuery() && 
			!getChangeHelper().isNonUserChange()) {
			if (modelCopy == null)
				return;
		
			IStructuredSelection sel = (IStructuredSelection)variableViewer.getSelection();
			Object[] path = variableContentProvider.getPathToRoot(sel.getFirstElement());
			String query = ""; //$NON-NLS-1$
			for (int i = 0; i<path.length; i++) {
				Object modelObject = BPELUtil.resolveXSDObject(((ITreeNode)path[i]).getModelObject());
				if (modelObject instanceof XSDElementDeclaration || modelObject instanceof XSDAttributeDeclaration) {
					String nameSegment = ((XSDNamedComponent)modelObject).getName();
					if (nameSegment != null) {
						if (!query.equals("")) { //$NON-NLS-1$
							query = nameSegment + "/" + query; //$NON-NLS-1$
						} else {
							query = nameSegment;
						}
					}
				}
			}
			if (query.length() > 0)
				query = "/" + query; //$NON-NLS-1$
			nameText.setText(query);
			nameText.setEnabled(true);
			nameLabel.setEnabled(true);
		}
	}
	
	protected ITreeNode getSelectedPart() {
		ITreeNode node = null;
		Copy modelCopy = (Copy) getInput();
		To toOrFrom;
		if (isFrom)
			toOrFrom = modelCopy.getFrom();
		else
			toOrFrom = modelCopy.getTo();

		// First, find the variable node.
		Object modelObject = toOrFrom.getVariable();
		if (modelObject != null) {
			Object[] items = variableContentProvider.getElements(variableViewer.getInput());
			node = variableContentProvider.findModelNode(items, modelObject, 0);
			if (node == null)
				return null;
		}

		// Find the part (or property) node within the container node.
		modelObject = toOrFrom.getPart();
		if (node != null && modelObject != null) {
			Object[] items = variableContentProvider.getChildren(node);
			node =	variableContentProvider.findModelNode(items, modelObject, variableContentProvider.isCondensed() ? 0 : 1);
		}
		
		return node;
	}

	protected void createClient2(Composite parent) {
		FlatFormData data; 
		
		variableTree = wf.createTree(parent, SWT.NONE/*SWT.BORDER*/);

		if (displayQuery()) {
			// area for query string and wizard button
			nameLabel = wf.createLabel(parent, Messages.VariablePartAssignCategory_Query__8); 
			nameText = wf.createText(parent, ""); //$NON-NLS-1$
			data = new FlatFormData();
			data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(nameText, STANDARD_LABEL_WIDTH_SM));
			data.right = new FlatFormAttachment(100, -IDetailsAreaConstants.HSPACE);
			data.bottom = new FlatFormAttachment(100, 0);
			data.top = new FlatFormAttachment(100, -nameText.getLineHeight() - IDetailsAreaConstants.VSPACE);
			nameText.setLayoutData(data);
			
			getChangeHelper().startListeningTo(nameText);
			getChangeHelper().startListeningForEnter(nameText);

			data = new FlatFormData();
			data.left = new FlatFormAttachment(0, 0);
			data.right = new FlatFormAttachment(nameText, -IDetailsAreaConstants.HSPACE);
			data.top = new FlatFormAttachment(nameText, 0, SWT.CENTER);
			nameLabel.setLayoutData(data);

//TODO: This won't work....so disabled for now
//			if (false)	{
//			wizardButton = wf.createButton(parent, "Build Query...", SWT.PUSH);
//			data = new FlatFormData();
//			data.left = new FlatFormAttachment(100, - STANDARD_BUTTON_WIDTH * 2 -IDetailsAreaConstants.HSPACE);
//			data.right = new FlatFormAttachment(100, 0);
//			data.top = new FlatFormAttachment(nameText, 0, SWT.TOP);
//			data.bottom = new FlatFormAttachment(nameText, 0, SWT.BOTTOM);
//			wizardButton.setLayoutData(data);
//			
//			final Shell shell = parent.getShell();
//			wizardButton.addSelectionListener(new SelectionListener() {
//				public void widgetSelected(SelectionEvent e) {
//					ITreeNode node = getSelectedPart();
//					if (node == null)
//						return;
//						
//					PartTreeNode tn = (PartTreeNode) node;
//					XSDTypeDefinitionTreeNode xtn = tn.getXSDTypeDefinitionTreeNode();
//					if (xtn == null)
//						return;
//						
//					XSDTypeDefinition td = (XSDTypeDefinition) xtn.getModelObject();
//					XSDParticle pc = null;
//					URI uri = null;
//					if (td instanceof XSDComplexTypeDefinition) {
//						pc = (XSDParticle) ((XSDComplexTypeDefinition)td).getContent();
//						Resource r = td.eResource();
//						uri = r.getURI();
//					}
//					else
//						return;
//						
//					if (uri == null)
//						return;
//						
//					Document xmlDocument = null;
//					try {
//						//TODO: we have to provide an xml file with the schema element for the type we are interested in.
//						// unfortunately, this CMDocument api isn't flexible enough to allow us to construct a CMDocument
//						// from the XSDType that we have on hand. Need alternate solutions!!!!!!!!
//					
//						CMDocument cmDocument =	CMDocumentBuilderRegistry.getInstance().buildCMDocument(uri.devicePath());
//						CMNamedNodeMap elements = cmDocument.getElements();
//						
//						Iterator iter = elements.iterator();
//						CMElementDeclaration cmElementDeclaration = null;
//						while (iter.hasNext()) {
//							CMElementDeclaration temp = (CMElementDeclaration) iter.next();
//							if (true) {
//								cmElementDeclaration = temp;
//							}
//						}
//						xmlDocument = new org.apache.xerces.dom.DocumentImpl();
//						DOMContentBuilderImpl contentBuilder = new DOMContentBuilderImpl(xmlDocument);
//						contentBuilder.createDefaultRootContent(cmDocument, cmElementDeclaration, Collections.EMPTY_LIST);
//					} catch (Exception e) {
//						BPELUIPlugin.log(e);
//					}
//					
//					if (xmlDocument == null)
//						return;
//
//					XPathWizard wizard = new XPathWizard();
//					BPELEditor bpelEditor = ModelHelper.getBPELEditor(getInput());
//					wizard.init(bpelEditor.getSite().getWorkbenchWindow().getWorkbench(), (IStructuredSelection) StructuredSelection.EMPTY);
//					WizardDialog dialog = new WizardDialog(shell, wizard);
//					dialog.create();
//					dialog.open();
//					if (dialog.getReturnCode() == Dialog.OK)
//						nameText.setText(wizard.getXPathQuery());
//				}
//				public void widgetDefaultSelected(SelectionEvent e) {
//					widgetSelected(e);
//				}
//			});
//			} // end false
		} 

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0); 
		data.top = new FlatFormAttachment(0, 0); 
		data.right = new FlatFormAttachment(100, 0); 
		if (displayQuery())
			data.bottom = new FlatFormAttachment(nameText, -IDetailsAreaConstants.VSPACE, SWT.TOP);
		else
			data.bottom = new FlatFormAttachment(100, 0);
			
//		data.borderType = IBorderConstants.BORDER_2P2_BLACK;
		variableTree.setLayoutData(data);
		
		variableContentProvider = new VariableTreeContentProvider(true, isPropertyTree(), displayQuery());
		variableViewer = new TreeViewer(variableTree);
		variableViewer.setContentProvider(variableContentProvider);
		variableViewer.setLabelProvider(new ModelTreeLabelProvider());
		// TODO: does this sorter work at the top level?  does it affect nested levels too?
		//variableViewer.setSorter(ModelViewerSorter.getInstance());
		variableViewer.setInput(((AssignImplSection)ownerSection).getModel());
		
		variableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateQueryFieldFromTreeSelection();
			}
		});
		
		getChangeHelper().startListeningTo(variableTree);
	}
	
	public boolean isCategoryForModel(To toOrFrom) {
		if (toOrFrom == null)  return false;
		if (toOrFrom.getVariable() != null &&
			((toOrFrom.getProperty() != null)==isPropertyTree()))  return true;
		return false;
	}
	
	protected void loadToOrFrom(To toOrFrom) {
		if (toOrFrom == null)  return;

		getChangeHelper().startNonUserChange();
		try {
			variableViewer.setSelection(StructuredSelection.EMPTY, false);
			variableViewer.collapseAll();
		} finally {
			getChangeHelper().finishNonUserChange();
		}
		
		//TODO: we need this in the future
		//Object firstTreeNode = null;
		//Object selNode = null;
		
		// TODO: in the future, if we can't find the item referenced by some part of the
		// toOrFrom, we could add some sort of placeholder/proxy objects to pathToNode,
		// and then take that into account somehow in the tree, e.g. by recreating it with
		// the necessary proxies??
		Vector pathToNode = new Vector();
		ITreeNode node = null;

		// First, find the variable node.
		Object modelObject = toOrFrom.getVariable();
		if (modelObject != null) {
			Object[] items = variableContentProvider.getElements(variableViewer.getInput());
			//TODO: if (items.length > 0)  firstTreeNode = items[0];  // for later
			node = variableContentProvider.findModelNode(items, modelObject, 0);
			if (node != null)  pathToNode.add(node);
		}
		// Find the part (or property) node within the container node.
		if (isPropertyTree())  {
			modelObject = toOrFrom.getProperty();
		} else {
			modelObject = toOrFrom.getPart();
		}
		
		if (node != null && modelObject != null) {
			Object[] items = variableContentProvider.getChildren(node);
			node = variableContentProvider.findModelNode(items, modelObject,
				variableContentProvider.isCondensed()? 0 : 1);
			if (node != null) {
				pathToNode.add(node);
			}
		}
		if (modelObject == null) {
			modelObject = toOrFrom.getVariable();
		}
		
		String query = ""; //$NON-NLS-1$
		if (displayQuery()) {
			nameText.setEnabled(true);
			nameLabel.setEnabled(true);
			
			if (node != null && modelObject != null) {
				Query queryObject = toOrFrom.getQuery();
				query = null;
				if (queryObject != null) {
					// TODO: we shouldn't ignore the queryLanguage here!!
					query = queryObject.getValue();
				}
				if (query != null && !query.equals("")) { //$NON-NLS-1$
					Object[] items = variableContentProvider.getChildren(node);
	
					// decompose the xpath query string and try to match it up in the tree				
					int index = 0;
					String token;
					boolean continueLoop = true;
					while (index >= 0 && index < query.length() && continueLoop) {
						int end = query.indexOf('/', index);
						if (end >= 0) {
							token = query.substring(index, end);
							index = end + 1; // skip over delimiter
						}
						else {
							token = query.substring(index);
							index = -1;
						}
						if (token.length() > 0) { //TODO: possible bug if user has two // in a row
							// match it against the part children
							continueLoop = false;
							for (int j = 0; j < items.length; j++) {
								Object originalMatch = ((ITreeNode)items[j]).getModelObject();
								Object match = BPELUtil.resolveXSDObject(originalMatch);
								if (match instanceof XSDElementDeclaration || match instanceof XSDAttributeDeclaration) {
									if (token.equals(((XSDNamedComponent)match).getName())) {
										// NOTE: use originalMatch here, because the TreeNode contains
										// the original model object (which is not neccesarily the same as
										// the resolved object).
										node = variableContentProvider.findModelNode(items, originalMatch, 0);
										if (node != null)  {
											pathToNode.add(node);
											continueLoop = true;
											items = variableContentProvider.getChildren(node);
											break;
										}
									}
								}	
							}
						}
					}
				}
			}
		}
		
		if (pathToNode.size() > 0) {  
			node = (ITreeNode)pathToNode.get(pathToNode.size()-1);
		}
		
		if (node != null)  {
			getChangeHelper().startNonUserChange();
			try {
				if (displayQuery()) 
					nameText.setText(query == null? "" : query); //$NON-NLS-1$
				variableViewer.expandToLevel(node, 0);
				variableViewer.setSelection(new StructuredSelection(node), true);
			} finally {	
				getChangeHelper().finishNonUserChange();
			}
		}			
	}

	protected void storeToOrFrom(To toOrFrom) {
		IStructuredSelection sel = (IStructuredSelection)variableViewer.getSelection();
		Object[] path = variableContentProvider.getPathToRoot(sel.getFirstElement());
		String query = displayQuery() ? nameText.getText() : ""; //$NON-NLS-1$
		for (int i = 0; i<path.length; i++) {
			Object modelObject = ((ITreeNode)path[i]).getModelObject();
			if (modelObject instanceof Variable) {
				toOrFrom.setVariable((Variable)modelObject);
			}
			if (modelObject instanceof Part) {
				toOrFrom.setPart((Part)modelObject);
			}
			if (modelObject instanceof Property) {
				toOrFrom.setProperty((Property)modelObject);
			}
		}
		if (query != null && query.trim().length() == 0)
			query = null;
		
		if (displayQuery() && query != null) {
			Query queryObject = BPELFactory.eINSTANCE.createQuery();
			queryObject.setQueryLanguage(IBPELUIConstants.EXPRESSION_LANGUAGE_XPATH);
			queryObject.setValue(query);
			toOrFrom.setQuery(queryObject);
		} else {
			toOrFrom.setQuery(null);
		}
	}

	public Object getUserContext() {
		return null;
	}
	
	public void restoreUserContext(Object userContext) {
		variableTree.setFocus();
	}
	
	private boolean displayQuery() {
		return !isPropertyTree();
	}
}
