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
import java.util.Collections;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDNamedComponent;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDTypeDefinition;

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

	VariableTreeContentProvider variableContentProvider;
	Shell shell;

	protected VariablePartAssignCategory(boolean aIsFrom, BPELPropertySection anOwnerSection) {
		super(aIsFrom, anOwnerSection);
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#getName()
	 */
	public String getName() {
		return Messages.VariablePartAssignCategory_Variable_or_Part_1; 
	} 

	
	protected boolean isPropertyTree() {
		return false; 
	}
	
	
	@SuppressWarnings("nls")
	protected void updateQueryFieldFromTreeSelection() {
		
		if (displayQuery() == false || 
				getChangeHelper().isNonUserChange() || 
				modelCopy == null) 
		{
			return ;
		}
				
		IStructuredSelection sel = (IStructuredSelection) variableViewer.getSelection();
		Object[] path = variableContentProvider.getPathToRoot(sel.getFirstElement());
				
		StringBuilder builder = new StringBuilder();
		ArrayList<String> querySegments = new ArrayList<String>();
		
		for(Object next : path ) {
			
			Object eObj = BPELUtil.resolveXSDObject( BPELUtil.adapt(next,ITreeNode.class).getModelObject() );
			builder.setLength(0);
						
			String targetNamespace = null;
			String namespacePrefix = null;						
						
			if (eObj instanceof XSDAttributeDeclaration) {
				
				XSDAttributeDeclaration att = (XSDAttributeDeclaration) eObj;
				targetNamespace = att.getTargetNamespace();
				builder.append("/@");
				
				if (targetNamespace != null) {
					
					namespacePrefix = BPELUtil.lookupOrCreateNamespacePrefix( modelObject , targetNamespace , shell);
					if (namespacePrefix == null) {						
						break ;
					}
					
					builder.append(namespacePrefix).append(":");				
				}
				builder.append(att.getName());
				
			} else if (eObj instanceof XSDElementDeclaration) {
				
				XSDElementDeclaration elm = (XSDElementDeclaration) eObj;
				targetNamespace = elm.getTargetNamespace();
				
				XSDTypeDefinition typeDef = elm.getType();				
				XSDParticle particle = typeDef.getComplexType();				
				
				boolean isArray = particle != null && 
									(particle.getMaxOccurs() == XSDParticle.UNBOUNDED || particle.getMaxOccurs() > 1);
				
				builder.append("/");
				if (targetNamespace != null) {
					namespacePrefix = BPELUtil.lookupOrCreateNamespacePrefix( modelObject , targetNamespace , shell);
					if (namespacePrefix == null) {
						break;
					}
					builder.append(namespacePrefix).append(":");
				}
				
				builder.append(elm.getName()) ;
				if (isArray) {
					builder.append("[1]");					
				}				
			}
			
			// If the current builder has length > 0, then there is a query segment for us to put in.
			if (builder.length() > 0) {
				querySegments.add( builder.toString() );
			}								
		}
		
		Collections.reverse(querySegments);
		builder.setLength(0);
		for(String s : querySegments ) {
			builder.append(s);
		}
		
		nameText.setText( builder.toString() );
		nameText.setEnabled(true);
		nameLabel.setEnabled(true);		
	}
	
	
	
	
	
	protected ITreeNode getSelectedPart() {
		ITreeNode node = null;
		Copy copyRule = (Copy) getInput();
		To toOrFrom = isFrom ? copyRule.getFrom()  : copyRule.getTo(); 

		// First, find the variable node.
		Object context = toOrFrom.getVariable();
		if (context != null) {
			Object[] items = variableContentProvider.getElements(variableViewer.getInput());
			node = variableContentProvider.findModelNode(items, context, 0);
			if (node == null) {
				return null;
			}
		}

		// Find the part (or property) node within the container node.
		context = toOrFrom.getPart();
		if (node != null && context != null) {
			Object[] items = variableContentProvider.getChildren(node);
			node =	variableContentProvider.findModelNode(items, context, variableContentProvider.isCondensed() ? 0 : 1);
		}
		
		return node;
	}

	
	
	@Override
	protected void createClient2(Composite parent) {
		
		FlatFormData data; 
		
		variableTree = wf.createTree(parent, SWT.NONE /*SWT.BORDER*/);

		if (displayQuery()) {
			// area for query string and wizard button
			nameLabel = wf.createLabel(parent, Messages.VariablePartAssignCategory_Query__8); 
			nameText = wf.createText(parent, ""); //$NON-NLS-1$
			data = new FlatFormData();
			data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(nameText, STANDARD_LABEL_WIDTH_SM));
			data.right = new FlatFormAttachment(100, -IDetailsAreaConstants.HSPACE);
			data.bottom = new FlatFormAttachment(100, 0);
			
			data.top = new FlatFormAttachment(100, (-1)* (nameText.getLineHeight() + 4*nameText.getBorderWidth()) - IDetailsAreaConstants.VSPACE);
			nameText.setLayoutData(data);
			
			getChangeHelper().startListeningTo(nameText);
			getChangeHelper().startListeningForEnter(nameText);

			data = new FlatFormData();
			data.left = new FlatFormAttachment(0, 0);
			data.right = new FlatFormAttachment(nameText, -IDetailsAreaConstants.HSPACE);
			data.top = new FlatFormAttachment(nameText, 0, SWT.CENTER);
			nameLabel.setLayoutData(data);
		} 

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0); 
		data.top = new FlatFormAttachment(0, 0); 
		data.right = new FlatFormAttachment(100, 0); 
		if (displayQuery()) {
			data.bottom = new FlatFormAttachment(nameText, -IDetailsAreaConstants.VSPACE, SWT.TOP);
		} else {
			data.bottom = new FlatFormAttachment(100, 0);
		}
			
//		data.borderType = IBorderConstants.BORDER_2P2_BLACK;
		variableTree.setLayoutData(data);
		
		variableContentProvider = new VariableTreeContentProvider(true, isPropertyTree(), displayQuery());
		variableViewer = new TreeViewer(variableTree);
		variableViewer.setContentProvider(variableContentProvider);
		variableViewer.setLabelProvider(new ModelTreeLabelProvider());
		// TODO: does this sorter work at the top level?  does it affect nested levels too?
		//variableViewer.setSorter(ModelViewerSorter.getInstance());
		variableViewer.setInput(ownerSection.getModel());
		
		variableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateQueryFieldFromTreeSelection();
			}
		});
		
		getChangeHelper().startListeningTo(variableTree);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.bpel.model.To)
	 */
	
	public boolean isCategoryForModel(To toOrFrom) {
		if (toOrFrom == null)  return false;
		if (toOrFrom.getVariable() != null &&
			((toOrFrom.getProperty() != null)==isPropertyTree()))  return true;
		return false;
	}
	
	
	@Override
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
		
		ArrayList<ITreeNode> pathToNode = new ArrayList<ITreeNode>();
		ITreeNode node = null;

		// First, find the variable node.
		Object context = toOrFrom.getVariable();
		if (context != null) {
			Object[] items = variableContentProvider.getElements(variableViewer.getInput());
			//TODO: if (items.length > 0)  firstTreeNode = items[0];  // for later
			node = variableContentProvider.findModelNode(items, context, 0);
			if (node != null)  {
				pathToNode.add(node);
			}
		}
		// Find the part (or property) node within the container node.
		
		if (isPropertyTree())  {
			context = toOrFrom.getProperty();
		} else {
			context = toOrFrom.getPart();
		}
		
		if (node != null && context != null) {
			Object[] items = variableContentProvider.getChildren(node);
			node = variableContentProvider.findModelNode(items, context, variableContentProvider.isCondensed()? 0 : 1);
			if (node != null) {
				pathToNode.add(node);
			}
		}
		if (context == null) {
			context = toOrFrom.getVariable();
		}
		
		String query = ""; //$NON-NLS-1$
		if (displayQuery()) {
			nameText.setEnabled(true);
			nameLabel.setEnabled(true);
			
			if (node != null && context != null) {
				Query queryObject = toOrFrom.getQuery();
				query = null;
				if (queryObject != null) {
					// TODO: we shouldn't ignore the queryLanguage here!!
					query = queryObject.getValue();
				}
				if (query != null && !query.equals("")) { //$NON-NLS-1$
					Object[] items = variableContentProvider.getChildren(node);
	
					// decompose the XPath query string and try to match it up in the tree				
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
										// the original model object (which is not always the same as
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
			node = pathToNode.get(pathToNode.size()-1);
		}
		
		if (node != null)  {
			getChangeHelper().startNonUserChange();
			try {
				if (displayQuery()) {
					nameText.setText(query == null? "" : query); //$NON-NLS-1$
				}
				variableViewer.expandToLevel(node, 0);
				variableViewer.setSelection(new StructuredSelection(node), true);
			} finally {	
				getChangeHelper().finishNonUserChange();
			}
		}			
	}

	
	
	@Override
	protected void storeToOrFrom(To toOrFrom) {
		IStructuredSelection sel = (IStructuredSelection)variableViewer.getSelection();
		
		Object[] path = variableContentProvider.getPathToRoot(sel.getFirstElement());
		String query = displayQuery() ? nameText.getText() : ""; //$NON-NLS-1$
		
		for(Object n : path ) {
			ITreeNode treeNode = BPELUtil.adapt(n, ITreeNode.class);			
			
			Object model = treeNode.getModelObject();
			if (model instanceof Variable) {
				toOrFrom.setVariable((Variable)model);
			}
			if (model instanceof Part) {
				toOrFrom.setPart((Part)model);
			}
			if (model instanceof Property) {
				toOrFrom.setProperty((Property)model);
			}
		}
		
		if (query != null && query.trim().length() == 0) {
			query = null;
		}
		
		if (displayQuery() && query != null) {
			Query queryObject = BPELFactory.eINSTANCE.createQuery();
			queryObject.setQueryLanguage(IBPELUIConstants.EXPRESSION_LANGUAGE_XPATH);
			queryObject.setValue(query);
			toOrFrom.setQuery(queryObject);
		} else {
			toOrFrom.setQuery(null);
		}
	}

	
	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#getUserContext()
	 */
	@Override
	public Object getUserContext() {
		return null;
	}
	
	/**
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection#restoreUserContext(java.lang.Object)
	 */
	@Override
	public void restoreUserContext(Object userContext) {
		variableTree.setFocus();
	}
	
	
	private boolean displayQuery() {
		return !isPropertyTree();
	}
}
