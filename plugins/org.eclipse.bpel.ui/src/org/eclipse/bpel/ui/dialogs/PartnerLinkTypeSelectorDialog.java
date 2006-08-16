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
package org.eclipse.bpel.ui.dialogs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpel.model.partnerlinktype.PartnerLinkType;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.CompositeContentProvider;
import org.eclipse.bpel.ui.details.providers.GatedContentProvider;
import org.eclipse.bpel.ui.details.providers.ModelLabelProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkTypeContentProvider;
import org.eclipse.bpel.ui.details.providers.PartnerLinkTypeTreeContentProvider;
import org.eclipse.bpel.ui.details.providers.PortTypeContentProvider;
import org.eclipse.bpel.ui.details.providers.WSDLDefinitionFromResourceContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.bpel.ui.wizards.CreatePartnerLinkWizard;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.wsdl.PortType;


/**
 * Browse for partner link types available and choose that partner link type.
 * 
 * In BPEL, partner link types are extensions on the WSDL definitions.  
 * 
 * When browsing for partner link types, two modes are supported by this dialog.
 * 1) Browsing partner link types which exist in imported WSDLs.
 * 2) Browsing for available partner links. This includes partner 
 * link types visible in the workspace.
 * 
 * In addition, one can arbitrarily add an import to the process if the WSDL
 * representing the partner links is somewhere else (URL, outside file, etc).
 * 
 *  @author Michal Chmielewski (michal.chmielewski@oracle.com)   
 */


public class PartnerLinkTypeSelectorDialog extends BrowseSelectorDialog {	
	
	private static final int BID_SHOW_PORT_TYPES = IDialogConstants.CLIENT_ID + 200;

	private static final String SHOW_PORT_TYPES = Messages.PartnerLinkTypeSelectorDialog_0;
	
	private boolean showPortTypes = false;
	
	GatedContentProvider portTypeContentProvider;

	PartnerLinkType fPartnerLinkType;
	

	/**
	 * The modelObject is the model element that indicates the scope in which the
	 * variable should be visible.
	 */
	
	public PartnerLinkTypeSelectorDialog (Shell parent, EObject modelObject ) {
		super(parent, new ModelLabelProvider(modelObject));
		
		this.modelObject = modelObject;		
		
		portTypeContentProvider = new GatedContentProvider ( new PortTypeContentProvider () );
		
		// This content provider sets up elements in the list
		CompositeContentProvider ccp = new CompositeContentProvider ();
		ccp.add ( new PartnerLinkTypeContentProvider() );
		ccp.add ( portTypeContentProvider );
		 
		
		contentProvider = ccp;
		
		// This content provider sets up WSDLDefinitions resources
		resourceContentProvider = new WSDLDefinitionFromResourceContentProvider( modelObject.eResource().getResourceSet() );
		// that's for the tree view
		treeContentProvider = new PartnerLinkTypeTreeContentProvider(true);
		
		setTitle(Messages.PartnerLinkTypeSelectorDialog_1);

		setLowerViewLabel(Messages.PartnerLinkTypeSelectorDialog_2);	
		setBrowseFromLabel(Messages.PartnerLinkTypeSelectorDialog_3);
		
		IDialogSettings settings = getDialogSettings();
		try {
			showPortTypes = settings.getBoolean(SHOW_PORT_TYPES);
		} catch (Exception ex) {
			showPortTypes = false;
		}		
		portTypeContentProvider.setEnabled( showPortTypes );
	}
	

	protected Control createContents(Composite parent) {
		
		Control control = super.createContents(parent);
					
		refresh();		

		return control;
	}


	protected void saveSettings () {
		super.saveSettings();		
		IDialogSettings settings = getDialogSettings();				
		settings.put ( SHOW_PORT_TYPES, showPortTypes);		
	}	
	
	/**
	 * Handle the checkbutton and radio button callbacks.
	 * 
	 * @param id
	 * @param checked
	 */
	
	protected void buttonPressed (int id, boolean checked) {
		
		boolean bRefresh = true;
				
		switch (id) {

		case BID_SHOW_PORT_TYPES :
			showPortTypes = checked;
			break;
							
		default :
			bRefresh = false;
			super.buttonPressed(id, checked);			
			break;
		}

		
		if (bRefresh) {
			portTypeContentProvider.setEnabled( checked );
			refresh();
		}
	}
		
	
	protected void computeResult() {
		
		if (fPartnerLinkType != null) {
			setResult(0, fPartnerLinkType);
		} else {
			super.computeResult();
		}
    }
	  
	
	protected void okPressed() {
		
		computeResult();
		
		Object obj = getFirstResult();
		
		if (obj instanceof PortType) {
		
			PortType pt = (PortType) obj;
			CreatePartnerLinkWizard wizard = new CreatePartnerLinkWizard();
			wizard.setPortType( pt );
			
			wizard.setBPELEditor( ModelHelper.getBPELEditor( pt ) );
			WizardDialog dialog = new WizardDialog(getShell(), wizard);
			
			if (dialog.open() == Window.CANCEL) {
				return ;
			}
			
			fPartnerLinkType = wizard.getPartnerLinkType();
			if (fPartnerLinkType == null) { 
				return ;
			}		
			super.okPressed();
						
			return ;
		}
		
		
		if (obj instanceof PartnerLinkType) {			
			super.okPressed();
			return;
		}
		// 
		
		throw new IllegalStateException(Messages.PartnerLinkTypeSelectorDialog_4);
	}
	
	protected void createBrowseFilterGroupButtons ( Group  group ) {
        
		createCheckButton(group,Messages.PartnerLinkTypeSelectorDialog_5, BID_SHOW_PORT_TYPES, 
				showPortTypes  );
		
		super.createBrowseFilterGroupButtons( group );
	}
	
	
	/**
	 * Add an import using an explict import dialog selection. 
	 * 
	 * We safeguard against adding duplicate types to the BPEL model here as well.
	 * 
	 */
	protected void handleAddImport() {
	
		SchemaImportDialog dialog = new SchemaImportDialog(getShell(),modelObject);
		dialog.configureAsWSDLImport();
		
		if (dialog.open() == Window.CANCEL) {
			return ;
		}
		
		Object obj[] = dialog.getResult();
		if (obj == null || obj.length < 1) {
			return ;
		}
		
		if (handleAddImport( obj [0] )) {
			showImportedTypes();
		}
	}
	
	
	/**
	 * In the case of partner link types, we nee to see a few more levels
	 *  
	 */
	
	protected int getAutoExpandLevel () {
		return 5;
	}
	
	
	
	protected List collectItemsFromImports ( ) {
		return ModelHelper.getDefinitions(modelObject);
	}
			
}
