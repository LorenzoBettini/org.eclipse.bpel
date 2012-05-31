package org.xtext.example.mydsl.ui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceFactory;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorFactory;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditorModelAccess;
import org.eclipse.xtext.ui.editor.embedded.IEditedResourceProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class XtextEmbeddedEditorProvider {

	@Inject
	protected EmbeddedEditorFactory editorFactory;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private XtextResourceFactory resourceFactory;
	
	@Inject
	@Named(Constants.FILE_EXTENSIONS)
	public String fileExtension;

	/**
	 * The constructor.
	 */
	public XtextEmbeddedEditorProvider() {
	}

	public EmbeddedEditor getXtextEmbeddedEditor(Composite parent) {
		IEditedResourceProvider resourceProvider = new IEditedResourceProvider() {

			@Override
			public XtextResource createResource() {
				XtextResourceSet xtextResourceSet = resourceSetProvider.get();
				Resource resource = resourceFactory
						.createResource(computeUnusedUri(xtextResourceSet));
				xtextResourceSet.getResources().add(resource);
				return (XtextResource) resource;
			}

			protected URI computeUnusedUri(ResourceSet resourceSet) {
				String name = "__synthetic";
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					URI syntheticUri = URI.createURI(name + i + "." + fileExtension);
					if (resourceSet.getResource(syntheticUri, false) == null)
						return syntheticUri;
				}
				throw new IllegalStateException();
			}
		};

		EmbeddedEditor editor = editorFactory.newEditor(resourceProvider)
				.showErrorAndWarningAnnotations().withParent(parent);

		EmbeddedEditorModelAccess partialEditor = editor
				.createPartialEditor(true);

		return editor;
	}

}