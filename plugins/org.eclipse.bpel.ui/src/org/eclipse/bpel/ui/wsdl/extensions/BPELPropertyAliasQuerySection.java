package org.eclipse.bpel.ui.wsdl.extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.bpel.common.ui.details.IDetailsAreaConstants;
import org.eclipse.bpel.common.ui.details.viewers.CComboViewer;
import org.eclipse.bpel.common.ui.flatui.FlatFormAttachment;
import org.eclipse.bpel.common.ui.flatui.FlatFormData;
import org.eclipse.bpel.common.ui.flatui.FlatFormLayout;
import org.eclipse.bpel.model.messageproperties.MessagepropertiesFactory;
import org.eclipse.bpel.model.messageproperties.PropertyAlias;
import org.eclipse.bpel.model.messageproperties.Query;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.ExpressionEditorDescriptorContentProvider;
import org.eclipse.bpel.ui.details.providers.ExpressionEditorDescriptorLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.providers.ModelViewerSorter;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ExpressionEditorDescriptor;
import org.eclipse.bpel.ui.properties.BPELPropertySection;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.common.ui.properties.internal.provisional.ISection;
import org.eclipse.wst.common.ui.properties.internal.provisional.TabbedPropertySheetPage;
import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDNamedComponent;

public class BPELPropertyAliasQuerySection implements ISection,
		ISelectionChangedListener {

	public void selectionChanged(SelectionChangedEvent event) {
		if (((IStructuredSelection) queryLanguageViewer.getSelection())
				.getFirstElement() instanceof ExpressionEditorDescriptor) {
			editorAreaComposite.setVisible(true);
			ExpressionEditorDescriptor desc = (ExpressionEditorDescriptor) ((IStructuredSelection) queryLanguageViewer
					.getSelection()).getFirstElement();
			if (palias.getQuery() == null) {
				Query newQuery = MessagepropertiesFactory.eINSTANCE
						.createQuery();
				newQuery.setQueryLanguage(desc.getExpressionLanguage());
				palias.setQuery(newQuery);
			} else {
				palias.getQuery()
						.setQueryLanguage(desc.getExpressionLanguage());
			}

		} else if (NO_QUERY.equals(((IStructuredSelection) queryLanguageViewer
				.getSelection()).getFirstElement())) {
			palias.setQuery(null);
			editorAreaComposite.setVisible(false);
			query.setText("");
			referenceViewer.setSelection(StructuredSelection.EMPTY, false);
			referenceViewer.collapseAll();			
		}
	}

	public void refresh() {
			}

	protected PropertyAlias palias;

	protected CCombo queryLanguageCCombo;

	protected CComboViewer queryLanguageViewer;

	protected TreeViewer referenceViewer;

	protected Text query;

	protected Composite editorAreaComposite, parentComposite;

	protected TabbedPropertySheetPage page;

	// Pseudo-model object to represent no expression at all (in which case no
	// editor
	// is used).
	protected static final Object NO_QUERY = new Object();

	public void aboutToBeHidden() {
		// TODO Auto-generated method stub

	}

	public void aboutToBeShown() {
		refresh();
	}

	public void createControls(Composite parent, TabbedPropertySheetPage page) {
		FlatFormData data;
		this.page = page;
		parentComposite = page.getWidgetFactory().createFlatFormComposite(
				parent);
		FlatFormLayout layout = new FlatFormLayout();
		layout.marginHeight = layout.marginWidth = 0;
		parentComposite.setLayout(layout);

		Label queryLanguageLabel = page.getWidgetFactory().createLabel(
				parentComposite, Messages.BPELPropertyAliasQuerySection_Query_Language);
		queryLanguageCCombo = page.getWidgetFactory().createCCombo(
				parentComposite, SWT.FLAT);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(
				queryLanguageLabel,
				BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(0, 0);
		queryLanguageCCombo.setLayoutData(data);

		// Query language combo layout
		queryLanguageViewer = new CComboViewer(queryLanguageCCombo);

		queryLanguageViewer
				.setLabelProvider(new ExpressionEditorDescriptorLabelProvider() {

					public String getText(Object element) {
						if (NO_QUERY.equals(element))
							return "No query";
						return super.getText(element);
					}

				});
		queryLanguageViewer
				.setContentProvider(new ExpressionEditorDescriptorContentProvider() {

					public Object[] getElements(Object inputElement) {
						Object[] elements = super.getElements(inputElement);
						List newElements = new ArrayList(elements.length + 1);
						newElements.add(NO_QUERY);
						for (int i = 0; i < elements.length; i++) {
							newElements.add(elements[i]);
						}
						return newElements.toArray();
					}

				});
		queryLanguageViewer.setSorter(ModelViewerSorter.getInstance());
		queryLanguageViewer.addSelectionChangedListener(this);
		queryLanguageViewer.setInput(new Object());

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(queryLanguageCCombo,
				-IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(queryLanguageCCombo, 0, SWT.CENTER);
		queryLanguageLabel.setLayoutData(data);

		editorAreaComposite = page.getWidgetFactory().createFlatFormComposite(
				parentComposite);
		layout = new FlatFormLayout();
		layout.marginHeight = layout.marginWidth = 0;
		editorAreaComposite.setLayout(layout);

		data = new FlatFormData();
		data.top = new FlatFormAttachment(queryLanguageCCombo,
				IDetailsAreaConstants.VSPACE);
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(100, 0);
		data.bottom = new FlatFormAttachment(100, 0);
		editorAreaComposite.setLayoutData(data);

		Label queryLabel = page.getWidgetFactory().createLabel(
				editorAreaComposite,
				Messages.BPELPropertyAliasQuerySection_Query);
		query = page.getWidgetFactory().createText(editorAreaComposite, "",
				SWT.READ_ONLY);
		Tree referenceTree = page.getWidgetFactory().createTree(
				editorAreaComposite, SWT.BORDER);
		BPELQueryTreeContentProvider referenceContentProvider = new BPELQueryTreeContentProvider(
				true);
		referenceViewer = new TreeViewer(referenceTree);
		referenceViewer.setContentProvider(referenceContentProvider);
		referenceViewer.setLabelProvider(new ModelTreeLabelProvider());
		referenceViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						updateQueryFieldFromTreeSelection();
					}
				});

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(
				queryLabel, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, -IDetailsAreaConstants.HSPACE);
		data.bottom = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(100, -query.getLineHeight()
				- IDetailsAreaConstants.VSPACE);
		query.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, BPELUtil.calculateLabelWidth(
				queryLabel, BPELPropertySection.STANDARD_LABEL_WIDTH_LRG));
		data.right = new FlatFormAttachment(100, 0);
		data.top = new FlatFormAttachment(queryLanguageCCombo,
				IDetailsAreaConstants.VSPACE, SWT.BOTTOM);
		data.bottom = new FlatFormAttachment(query,
				-IDetailsAreaConstants.VSPACE, SWT.TOP);
		referenceTree.setLayoutData(data);

		data = new FlatFormData();
		data.left = new FlatFormAttachment(0, 0);
		data.right = new FlatFormAttachment(referenceTree,
				-IDetailsAreaConstants.HSPACE);
		data.top = new FlatFormAttachment(referenceTree, 0, SWT.CENTER);
		queryLabel.setLayoutData(data);
	}

	protected void updateQueryFieldFromTreeSelection() {
		IStructuredSelection sel = (IStructuredSelection) referenceViewer
				.getSelection();
		if (palias.getQuery() != null && sel.getFirstElement() != null) {
			Object[] path = ((BPELQueryTreeContentProvider) referenceViewer
					.getContentProvider()).getPathToRoot(sel.getFirstElement());
			String queryText = ""; //$NON-NLS-1$
			for (int i = 0; i < path.length - 1; i++) {
				Object modelObject = BPELUtil
						.resolveXSDObject(((ITreeNode) path[i])
								.getModelObject());
				if (modelObject instanceof XSDElementDeclaration
						|| modelObject instanceof XSDAttributeDeclaration) {
					String nameSegment = ((XSDNamedComponent) modelObject)
							.getName();
					if (nameSegment != null) {
						if (!queryText.equals("")) { //$NON-NLS-1$
							queryText = nameSegment + "/" + queryText; //$NON-NLS-1$
						} else {
							queryText = nameSegment;
						}
					}
				}
			}
			if (queryText.length() > 0) {
				queryText = "/" + queryText; //$NON-NLS-1$
				Query newQuery = MessagepropertiesFactory.eINSTANCE
						.createQuery();
				newQuery.setQueryLanguage(palias.getQuery().getQueryLanguage());
				newQuery.setValue(queryText);
				palias.setQuery(newQuery);
			} else {
				Query newQuery = MessagepropertiesFactory.eINSTANCE
						.createQuery();
				newQuery.setQueryLanguage(palias.getQuery().getQueryLanguage());
				palias.setQuery(newQuery);
			}
			query.setText(queryText);
		}

	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public int getMinimumHeight() {
		// TODO Auto-generated method stub
		return SWT.DEFAULT;
	}

	public void setInput(IWorkbenchPart arg0, ISelection selection) {
		Assert.isTrue(selection instanceof IStructuredSelection);
		palias = (PropertyAlias) ((IStructuredSelection) selection)
				.getFirstElement();
		if (palias != null) {
			referenceViewer.setInput(palias);
			Object model = NO_QUERY;
			if (palias.getQuery() != null) {
				String language = palias.getQuery().getQueryLanguage();
				if (language != null) {
					ExpressionEditorDescriptor descriptor = BPELUIRegistry
							.getInstance().getExpressionEditorDescriptor(
									language);
					if (descriptor != null) {
						model = descriptor;
					}
				}
				editorAreaComposite.setVisible(true);
				String queryText = palias.getQuery().getValue();
				
				if (queryText != null && queryText.length() > 0){					
					Object[] items = ((BPELQueryTreeContentProvider) referenceViewer
							.getContentProvider()).getElements(palias);
					ITreeNode node = (ITreeNode)items[0];
					
					StringTokenizer tokenizer = new StringTokenizer(queryText,"/");
				
					while (tokenizer.hasMoreTokens()){
						String token = tokenizer.nextToken();	
					
						Object[] children = ((BPELQueryTreeContentProvider) referenceViewer
								.getContentProvider()).getChildren(node);
						
						for (int  i = 0 ; i < children.length; i++) {
							Object originalMatch = ((ITreeNode) children[i]).getModelObject();
							Object match = BPELUtil.resolveXSDObject(originalMatch);
							if (match instanceof XSDElementDeclaration	|| match instanceof XSDAttributeDeclaration) {								
								if (token.equals(((XSDNamedComponent) match).getName())) {
									node = (ITreeNode) children[i];
									break;
								}
							}	
						}
					}					
					referenceViewer.expandToLevel(node, 0);
					referenceViewer.setSelection(new StructuredSelection(node),
							true);
				}
			} else {
				editorAreaComposite.setVisible(false);
				referenceViewer.setSelection(StructuredSelection.EMPTY, false);	
				referenceViewer.collapseAll();
			}

			queryLanguageViewer.removeSelectionChangedListener(this);
			queryLanguageViewer.setSelection(new StructuredSelection(model));
			queryLanguageViewer.addSelectionChangedListener(this);

			
		}

		
	}

	public boolean shouldUseExtraSpace() {
		// TODO Auto-generated method stub
		return true;
	}

}
