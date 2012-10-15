package org.eclipse.bpel.ui.properties.xtext;

import org.eclipse.bpel.ui.properties.AbstractExpressionAssignCategory;
import org.eclipse.bpel.ui.properties.IAssignCategory;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;

import com.google.inject.Injector;

/**
 * An {@link IAssignCategory} specialized to provide a StyledText configured
 * for an Xtext DSL, relying on {@link EmbeddedEditor} which is NOT an
 * editor, but provides a {@link XtextSourceViewer}.
 * 
 * @author Lorenzo Bettini
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractXtextExpressionAssignCategory extends AbstractExpressionAssignCategory implements
		IAssignCategory {

	/**
	 * @param styledTextComposite
	 *            the Composite for the StyledText
	 * @return the created and configured StyledText based on an {@link EmbeddedEditor}
	 */
	protected StyledText createStyledText(Composite styledTextComposite) {
		Composite editor = getWidgetFactory().createComposite( this.fEditorArea, SWT.BORDER );
		editor.setLayout( new GridLayout ());

		GridData layoutData = new GridData( GridData.FILL_BOTH );
		editor.setLayoutData( layoutData );
		
		EmbeddedEditor embeddedEditor = getInjector().getInstance(
				XtextEmbeddedEditorProvider.class).getXtextEmbeddedEditor(
				editor);
		final ISourceViewer viewer = embeddedEditor.getViewer();
		StyledText textWidget = viewer.getTextWidget();
		//textWidget.setLayoutData(new GridData(GridData.FILL_BOTH));

		return textWidget;
	}


	/**
	 * This must redefined so to return the Injector for your own
	 * Xtext DSL.
	 * @return
	 */
	public abstract Injector getInjector();
	public abstract String getExpressionLanguage();
}
