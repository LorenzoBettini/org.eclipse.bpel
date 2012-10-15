package org.eclipse.bpel.ui.properties.xtext;

import org.eclipse.bpel.ui.properties.AbstractExpressionAssignCategory;
import org.eclipse.bpel.ui.properties.IAssignCategory;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
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
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.bpel.ui.properties.ExpressionSection
	 * #createClient(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createClient(Composite parent) {
		// ugly HACK to make subclasses work
		//FlatFormLayout layout = new FlatFormLayout();
		//layout.marginHeight = layout.marginWidth = 0;
		//parent.setLayout(layout);
		FillLayout fillLayout = new FillLayout();
		fillLayout.marginHeight = fillLayout.marginWidth = 0;
		parent.setLayout(fillLayout);
		

		// The top
		this.fEditorArea = getWidgetFactory().createComposite( parent );
		this.fEditorArea.setLayout( new GridLayout());
		if( this.title != null ) {

			// The font
			FontData[] fontData = parent.getDisplay().getSystemFont().getFontData();
			fontData[ 0 ].setStyle( SWT.BOLD );
			this.boldFont = new Font( parent.getDisplay(), fontData[ 0 ]);

			// The title
			this.titleLabel = this.fWidgetFactory.createLabel( this.fEditorArea, this.title);
			this.titleLabel.setFont(this.boldFont);
		}

		// The expression editor
		getWidgetFactory().createLabel( this.fEditorArea, "Edit the associated Xtext Expression." );
		Composite editor = getWidgetFactory().createComposite( this.fEditorArea, SWT.BORDER );
		editor.setLayout( new GridLayout ());

		GridData layoutData = new GridData( GridData.FILL_BOTH );
		editor.setLayoutData( layoutData );

		EmbeddedEditor embeddedEditor =
		getInjector().getInstance(XtextEmbeddedEditorProvider.class).getXtextEmbeddedEditor(editor);
		final ISourceViewer viewer = embeddedEditor.getViewer();

		viewer.getTextWidget().setLayoutData( new GridData( GridData.FILL_BOTH ));

		this.expressionText = viewer.getTextWidget();
		this.expressionText.addModifyListener( new ModifyListener() {
			@Override
			public void modifyText( ModifyEvent e ) {
				saveExpressionToModel();
			}
		});
	}

	/**
	 * @param styledTextComposite
	 *            the Composite for the StyledText
	 * @return the created and configured StyledText based on an {@link EmbeddedEditor}
	 */
	protected StyledText createStyledText(Composite styledTextComposite) {
		EmbeddedEditor embeddedEditor = getInjector().getInstance(
				XtextEmbeddedEditorProvider.class).getXtextEmbeddedEditor(
				styledTextComposite);
		final ISourceViewer viewer = embeddedEditor.getViewer();
		StyledText textWidget = viewer.getTextWidget();
		textWidget.setLayoutData(new GridData(GridData.FILL_BOTH));

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
