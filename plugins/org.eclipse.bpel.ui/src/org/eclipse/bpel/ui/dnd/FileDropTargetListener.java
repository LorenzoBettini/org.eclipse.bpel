/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.dnd;

import org.eclipse.bpel.ui.BPELEditor;
import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.commands.AddImportCommand;
import org.eclipse.bpel.ui.util.BrowseUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDSchema;



/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Nov 16, 2006
 *
 */
public class FileDropTargetListener implements TransferDropTargetListener , DropPopup.CommandListener {
		
	protected DropPopup fDropPopup;
	
	protected BPELEditor fEditor ;
			
	/**
	 * 
	 * @param viewer
	 * @param editor
	 */
	
	public FileDropTargetListener ( GraphicalViewer viewer, BPELEditor editor) {
		super();
		fEditor = editor;	
		
		fDropPopup = new DropPopup(null,  SWT.ON_TOP  ,"Imports processed", "Press any key or click mouse outside to dismiss.");
		fDropPopup.setCommandListener(this);
	}
	
	
	/** (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	
	public void dragEnter (DropTargetEvent event) {

	}

	/**
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragLeave (DropTargetEvent event) {		
		event.detail = DND.DROP_NONE;
	}

	/**
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOperationChanged (DropTargetEvent event) {
		
	}

	/**
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOver (DropTargetEvent event) {
		
	}

	/** (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void drop (DropTargetEvent event) {		
			
		if (getTransfer().isSupportedType(event.currentDataType)) {
			
			String [] files = (String[]) getFileTransfer().nativeToJava(event.dataTypes[0]);
			if (files == null) {
				return ;
			}
			
			// Assume that these are WSDL or XSD files that make sense for inclusion into 
			// the BPEL process.
			startImport();
			for(String f : files) {				
				URI uri =  URI.createFileURI(f);
				addImport(attemptLoad(uri), uri);
			}	
			endImport(event);
		}			
		
	}

	
	StringBuilder fDropMessage = new StringBuilder (256);
	
	protected void startImport () {
		fDropMessage.setLength(0);		
	}
	
	protected void endImport (DropTargetEvent event ) {
		
		fDropPopup.open();
		if (fDropPopup.hasBrowser()) {					
			fDropPopup.getShell().setBounds(event.x-150, event.y-75, 300, 150);
			fDropPopup.setInput(fDropMessage.toString());
		} else {
			fDropPopup.close();
		}
	}
	
	
	protected void addImport ( Object result , URI f) {
		String path = f.path();
		int x = path.lastIndexOf('/');
		if (x > 0) {
			path = path.substring(x+1);
		}
		
		if (result == null) {
			fDropMessage.append("<li><a href=\"").append(f).append("\" target=\"notthis\"><b>");
			fDropMessage.append(path).append("</b></a> is not importable.");
			
		} else if (result instanceof XSDSchema || result instanceof Definition) {
			if (result instanceof XSDSchema) {
				fDropMessage.append("<li>XSD Schema ");
			} else if (result instanceof Definition) {
				fDropMessage.append("<li>WSDL Definitions ");
			}
			fDropMessage.append("<a href=\"").append(f).append("\" target=\"notthis\"><b>");
			fDropMessage.append(path).append("</b></a> ");
			
			AddImportCommand cmd = new AddImportCommand(fEditor.getProcess(), result );
			if (cmd.canDoExecute() && cmd.wouldCreateDuplicateImport() == false) {
				
				fEditor.getCommandFramework().execute(cmd);
				fDropMessage.append(" added to imports.");
				
			} else {
				fDropMessage.append(" skipped - already present.");
			}
		}	
		fDropMessage.append("\n");
	}
	
	
	/** (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	
	public void dropAccept (DropTargetEvent event) {
		if (isEnabled(event) == false) {
			return ;
		}
		event.detail = DND.DROP_NONE;				
	}

	
	protected Object attemptLoad ( URI uri ) {
		
		Resource resource = null;				
		ResourceSet editorResourceSet = fEditor.getResourceSet();
	
		try {
			resource = editorResourceSet.getResource(uri, true);
		} catch (Throwable t) {		
			BPELUIPlugin.log(t);
			return null ;
		}
		
		if (resource.getContents().size() == 0) {					
			return null ;
		}
				
		return resource.getContents().get(0);		
	}


	/** (non-Javadoc)
	 * @see org.eclipse.jface.util.TransferDropTargetListener#getTransfer()
	 */
	public Transfer getTransfer() {		
		return FileTransfer.getInstance();
	}

	/**
	 * This is only needed because nativeToJava() in transfer is not public. Yikes.
	 * 
	 * @return
	 */
	
	public FileTransfer getFileTransfer () {
		return (FileTransfer) getTransfer();
	}
	
	/** (non-Javadoc)
	 * @see org.eclipse.jface.util.TransferDropTargetListener#isEnabled(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public boolean isEnabled (DropTargetEvent event) {
		if (getTransfer().isSupportedType(event.currentDataType)) {			
			return true;
		}
		return false;
	}


	/**
	 * @see org.eclipse.bpel.ui.dnd.DropPopup.CommandListener#execute(java.lang.String)
	 */
	@SuppressWarnings("nls")
	
	public void execute(String cmd) {	
		if ("typeBrowser".equals(cmd)) {			
			BrowseUtil.browseForXSDTypeOrElement(fEditor.getProcess(), fEditor.getSite().getShell() );
		} else if ("wsdlBrowser".equals(cmd)) {
			BrowseUtil.browseForPartnerLinkType(fEditor.getProcess(), fEditor.getSite().getShell() );	
		} else { 
			System.out.println("Cmd: " + cmd);
		}
	}
	
}
