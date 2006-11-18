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

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Nov 16, 2006
 *
 */
public class BPELDropTargetListener implements DropTargetListener {

	static final Transfer[] dropTransfers = { 
			TextTransfer.getInstance(),
			FileTransfer.getInstance()			
	};
	
	/**
	 * @return
	 */
	static public final Transfer [] getTransferTypes() {
		return dropTransfers;
	}
	
	
	public BPELDropTargetListener ( GraphicalViewer viewer, BPELEditor editor) {
		
	}
	
	/** (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	
	public void dragEnter(DropTargetEvent event) {
		System.out.println("DragEnter: " + event);
		
	}

	/**
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragLeave(DropTargetEvent event) {
		System.out.println("DragLeave: " + event);
		
	}

	/**
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOperationChanged(DropTargetEvent event) {
		System.out.println("DragOperationChanged: " + event);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOver(DropTargetEvent event) {
		System.out.println("DragOver: " + event);
		
	}

	/** (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void drop(DropTargetEvent event) {
		System.out.println("Drop: " + event);		 //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dropAccept(DropTargetEvent event) {
		System.out.println("DropAccepted: " + event);		
	}

}
