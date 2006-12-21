package org.eclipse.bpel.ui.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.ui.texteditor.templates.TemplatePreferencePage;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.bpel.ui.BPELUIPlugin;

import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.jface.text.templates.persistence.TemplateStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;
import org.eclipse.ui.editors.text.templates.ContributionContextTypeRegistry;

import org.eclipse.bpel.ui.editors.xpath.templates.XPathEditorTemplateAccess;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class XPathTemplatePreferencePage
//	extends FieldEditorPreferencePage
	extends TemplatePreferencePage 
	implements IWorkbenchPreferencePage {

	/**
	 * @see org.eclipse.jface.preference.PreferencePage
	 */

		//private FormattingPreferences fFormattingPreferences= new FormattingPreferences();
		public XPathTemplatePreferencePage() {
	        setPreferenceStore(BPELUIPlugin.getDefault().getPreferenceStore());
	        setContextTypeRegistry(XPathEditorTemplateAccess.getDefault().getContextTypeRegistry());
	        XPathEditorTemplateAccess.getDefault().getContextTypeRegistry().addContextType("xpath");
	        XPathEditorTemplateAccess.getDefault().getContextTypeRegistry().addContextType("jscript");
	        setTemplateStore(XPathEditorTemplateAccess.getDefault().getTemplateStore());
	        

		}


	    /* (non-Javadoc)
	     * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	     */
		
	    public boolean performOk() {
	    	  boolean ok = super.performOk();
	    	  BPELUIPlugin.getDefault().savePluginPreferences();
	    	  return ok;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see org.eclipse.ui.texteditor.templates.TemplatePreferencePage#createViewer(org.eclipse.swt.widgets.Composite)
	     */
	    /*
	    protected SourceViewer createViewer(Composite parent) {
	    	SourceViewer viewer = new SourceViewer(parent, null, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

			SourceViewerConfiguration configuration = new AntTemplateViewerConfiguration();        
			IDocument document = new Document();       
			new AntDocumentSetupParticipant().setup(document);
			viewer.configure(configuration);
			viewer.setDocument(document);
			viewer.setEditable(false);	
			Font font= JFaceResources.getFont(JFaceResources.TEXT_FONT);
			viewer.getTextWidget().setFont(font);    
	        
			return viewer;	
	    }
	    
		*/
	    
	    
	    /* (non-Javadoc)
	     * @see org.eclipse.ui.texteditor.templates.TemplatePreferencePage#getFormatterPreferenceKey()
	     */
		/*
	    protected String getFormatterPreferenceKey() {
			return AntEditorPreferenceConstants.TEMPLATES_USE_CODEFORMATTER;
		}
		
		/*
		 * @see org.eclipse.ui.texteditor.templates.TemplatePreferencePage#updateViewerInput()
		 */
		/*
		protected void updateViewerInput() {
			IStructuredSelection selection= (IStructuredSelection) getTableViewer().getSelection();
			SourceViewer viewer= getViewer();
			
			if (selection.size() == 1 && selection.getFirstElement() instanceof TemplatePersistenceData) {
				TemplatePersistenceData data= (TemplatePersistenceData) selection.getFirstElement();
				Template template= data.getTemplate();
				if (AntUIPlugin.getDefault().getPreferenceStore().getBoolean(getFormatterPreferenceKey())) {
					String formatted= XmlFormatter.format(template.getPattern(), fFormattingPreferences);
					viewer.getDocument().set(formatted);
				} else {
					viewer.getDocument().set(template.getPattern());
				}
			} else {
				viewer.getDocument().set(""); //$NON-NLS-1$
			}		
		}
		/* (non-Javadoc)
		 * @see org.eclipse.ui.texteditor.templates.TemplatePreferencePage#isShowFormatterSetting()
		 */
		/*
		protected boolean isShowFormatterSetting() {
			return false;
		}
	}
	
	/*
	public XPathTemplatePreferencePage() {
		super(GRID);
		setPreferenceStore(BPELUIPlugin.getDefault().getPreferenceStore());
		setDescription("A demonstration of a preference page implementation");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	/*
	public void createFieldEditors() {
		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
				"&Directory preference:", getFieldEditorParent()));
		addField(
			new BooleanFieldEditor(
				PreferenceConstants.P_BOOLEAN,
				"&An example of a boolean preference",
				getFieldEditorParent()));

		addField(new RadioGroupFieldEditor(
				PreferenceConstants.P_CHOICE,
			"An example of a multiple-choice preference",
			1,
			new String[][] { { "&Choice 1", "choice1" }, {
				"C&hoice 2", "choice2" }
		}, getFieldEditorParent()));
		addField(
			new StringFieldEditor(PreferenceConstants.P_STRING, "A &text preference:", getFieldEditorParent()));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 *//*
	public void init(IWorkbench workbench) {
	}
	*/
}