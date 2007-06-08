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
package org.eclipse.bpel.ui;

import org.eclipse.bpel.ui.commands.AddImportCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;

import org.eclipse.swt.dnd.Transfer;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDSchema;



/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Nov 16, 2006
 *
 */
public class BPELDropTargetListener implements DropTargetListener {

	static FileTransfer gFileTransfer = FileTransfer.getInstance();
	
	static TextTransfer gTextTransfer = TextTransfer.getInstance();
	
	static final Transfer[] dropTransfers = { gFileTransfer, gTextTransfer };
	
	BPELEditor fEditor;
	
	/**
	 * @return the transfers that we allow.
	 */
	
	static public final Transfer [] getTransferTypes() {
		return dropTransfers;
	}
	
	
	/**
	 * 
	 * @param viewer
	 * @param editor
	 */
	
	public BPELDropTargetListener ( GraphicalViewer viewer, BPELEditor editor) {
		super();
		fEditor = editor;
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
			
		if (gFileTransfer.isSupportedType(event.currentDataType)) {
			String [] files = (String[]) gFileTransfer.nativeToJava(event.dataTypes[0]);
			if (files == null) {
				return ;
			}
			
			// Assume that these are WSDL or XSD files that make sense for inclusion into 
			// the BPEL process.
			
			for(String f : files) {											
				attemptImport (  URI.createFileURI(f) );							
			}
			
		} else if (gTextTransfer.isSupportedType(event.currentDataType)) {
		     String data = (String) gTextTransfer.nativeToJava(event.currentDataType);
		     if (data == null) {
		    	 return ;
		     }
		     data = data.trim();
		     URI uri = null;
		     try {
		    	 uri = URI.createURI(data);		    	 
		     } catch (Throwable t) {
		    	 BPELUIPlugin.log(t);
		    	 return ; 
		     }		     	
		     attemptImport ( uri );		    
		}
		
	}

	/** (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	
	public void dropAccept (DropTargetEvent event) {
		for(Transfer t : getTransferTypes () ) {
			if (t.isSupportedType(event.currentDataType)) {
				return ;
			}
		}
		event.detail = DND.DROP_NONE;				
	}

	
	void attemptImport ( URI uri ) {
		
		Resource resource = null;
		
		System.out.println( "URI: " + uri);
		ResourceSet editorResourceSet = fEditor.getResourceSet();
	
		try {
			resource = editorResourceSet.getResource(uri, true);
		} catch (Throwable t) {					
			BPELUIPlugin.log(t);
			return ;
		}
		
		if (resource.getContents().size() == 0) {					
			return ;
		}
				
		Object obj = resource.getContents().get(0);
		
		if (obj instanceof XSDSchema || obj instanceof Definition) {
			AddImportCommand cmd = new AddImportCommand(
					fEditor.getProcess(), obj );
			if (cmd.canDoExecute() && cmd.wouldCreateDuplicateImport() == false) {
				fEditor.getCommandFramework().execute(cmd);
			}
		}
	}
	
}
