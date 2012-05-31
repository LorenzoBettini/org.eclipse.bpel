package org.eclipse.bpel.ui.properties;

import org.eclipse.bpel.model.AbstractAssignBound;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.adapters.IVirtualCopyRuleSide;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.xtext.ui.editor.embedded.EmbeddedEditor;
import org.xtext.example.mydsl.ui.XtextEmbeddedEditorProvider;

import com.google.inject.Injector;

public class XtextExpressionAssignCategory extends ExpressionSection implements
		IAssignCategory {

	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isHidden()
	 */
	public boolean isHidden() {
		return this.isHidden;
	}

	protected BPELPropertySection fOwnerSection;

	protected Composite composite;

	protected Composite fParent;

	protected XtextExpressionAssignCategory( BPELPropertySection ownerSection ) {
		this.fOwnerSection = ownerSection;
	}


	/*
	 * (non-Javadoc)
	 * @see org.eclipse.bpel.ui.properties.BPELPropertySection
	 * #createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		this.fParent = parent;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory
	 * #getComposite()
	 */
	public Composite getComposite() {
		return this.fParent;
	}


	// This is used by changeHelper to determine what shows up in Undo/Redo.
	// The return value is FlatFormatted with getName() as the only argument.
	// Subclasses may override.
	protected String getLabelFlatFormatString() {
		return IBPELUIConstants.FORMAT_CMD_SELECT;
	}

	protected boolean isToOrFromAffected(Notification n) {
		return true;
	}

	/**
	 * Policy: wrap the command with contexts from the ownerSection (rather
	 * than from the category itself).  On undo, the ownerSection will delegate
	 * to the category's methods.
	 */
	@Override
	protected Command wrapInShowContextCommand(Command inner) {
		return super.wrapInShowContextCommand(inner, this.fOwnerSection);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory
	 * #getName()
	 */
	public String getName() {
		return "Xtext Editor";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory
	 * #isCategoryForModel(org.eclipse.emf.ecore.EObject)
	 */
	public boolean isCategoryForModel ( EObject aModel ) {
		IVirtualCopyRuleSide side = BPELUtil.adapt(aModel, IVirtualCopyRuleSide.class);
		return side != null ? side.getExpression() != null : false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.bpel.ui.properties.ExpressionSection
	 * #getStructuralFeature(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected EStructuralFeature getStructuralFeature(EObject object) {
		return object instanceof AbstractAssignBound ? BPELPackage.eINSTANCE.getAbstractAssignBound_Expression() : null;
	}

	/**
	 * This is just a workaround to keep the AssignCategory from changing too much.
	 * @param model the model object
	 */
	public void setInput (EObject model) {
		basicSetInput(model);
		addAllAdapters();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.bpel.ui.properties.ExpressionSection
	 * #createClient(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected final void createClient(Composite parent) {
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
		//IDocument document = new Document( "" );
		//viewer.setDocument( document );

		this.expressionText = viewer.getTextWidget();
		this.expressionText.addModifyListener( new ModifyListener() {
			@Override
			public void modifyText( ModifyEvent e ) {
				saveExpressionToModel();
			}
		});
	}

	public Injector getInjector() {
		return org.xtext.example.mydsl.ui.internal.MyDslActivator.getInstance().getInjector("org.xtext.example.mydsl.MyDsl");
	}
}