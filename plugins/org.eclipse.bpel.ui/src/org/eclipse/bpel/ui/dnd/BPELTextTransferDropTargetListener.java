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

import java.util.List;

import org.eclipse.bpel.ui.util.TransferBuffer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Aug 20, 2007
 *
 */
public class BPELTextTransferDropTargetListener extends AbstractTransferDropTargetListener  {
	
	@SuppressWarnings("nls")
	
	class FromSourceFactory implements CreationFactory {
		
		org.eclipse.bpel.model.resource.BPELReader fReader = new org.eclipse.bpel.model.resource.BPELReader();
		
		List<EObject> fList = null;
		
		void setContent ( String content ) {
			fList = fReader.fromXML(TransferBuffer.adjustXMLSource(content), "Drag-Session");
		}
		 
		/**
		 * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
		 */
		public Object getNewObject() {
			if (fList != null && fList.size() > 0) {
				return fList.get(0);
			}
			return null;
		}

		/**
		 * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
		 */
		
		public Object getObjectType() {
			EObject eObj = (EObject) getNewObject();
			if (eObj != null) {
				return eObj.eClass();
			}
			return null;
		}		
		
	}
	
	
	FromSourceFactory fFromSourceFactory = new FromSourceFactory();
	
	private CreateRequest fCreateRequest;
	
	
	/**
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragEnter(DropTargetEvent event) {		
		super.dragEnter(event);
		fCreateRequest = null;
	}

	/**
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragLeave(DropTargetEvent event) {		
		super.dragLeave(event);		
	}

	
	/**
	 * Constructs a listener on the specified viewer.
	 * @param viewer the EditPartViewer
	 */
	public BPELTextTransferDropTargetListener(EditPartViewer viewer) {
		super(viewer, TextTransfer.getInstance() );
	}

	/**
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#createTargetRequest()
	 */
	@Override
	protected Request createTargetRequest() {
		
		if (fCreateRequest != null) {
			return fCreateRequest;
		}
		fCreateRequest = new CreateRequest();
		
		String data = (String) TextTransfer.getInstance().nativeToJava(getCurrentEvent().currentDataType);		
		fFromSourceFactory.setContent( data );		
		fCreateRequest.setFactory( fFromSourceFactory );
		
		return fCreateRequest;
	}

	/**
	 * A helper method that casts the target Request to a CreateRequest.
	 * @return CreateRequest
	 */
	protected final CreateRequest getCreateRequest() {
		return ((CreateRequest)getTargetRequest());
	}


	/**
	 * 
	 * @see AbstractTransferDropTargetListener#handleDragOperationChanged()
	 */
	@Override
	protected void handleDragOperationChanged() {
		if (getCreateRequest().getNewObject() == null) {
			getCurrentEvent().detail = DND.DROP_NONE;
		} else {
			getCurrentEvent().detail = DND.DROP_COPY;
		}
		super.handleDragOperationChanged();
	}

	/**
	 * The purpose of a template is to be copied. Therefore, the Drop operation is set to
	 * <code>DND.DROP_COPY</code> by default.
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDragOver()
	 */
	@Override
	protected void handleDragOver() {
		
		if (getCreateRequest().getNewObject() == null) {
			getCurrentEvent().detail = DND.DROP_NONE;
			getCurrentEvent().detail = DND.FEEDBACK_NONE;
		} else {
			getCurrentEvent().detail = DND.DROP_COPY;
			getCurrentEvent().feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_EXPAND;
		}
		super.handleDragOver();
	}

	/**
	 * Overridden to select the created object.
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDrop()
	 */
	@Override
	protected void handleDrop() {
		super.handleDrop();
		fCreateRequest = null;
		selectAddedObject();
	}

	private void selectAddedObject() {
		Object model = getCreateRequest().getNewObject();
		if (model == null)
			return;
		EditPartViewer viewer = getViewer();
		viewer.getControl().forceFocus();
		Object editpart = viewer.getEditPartRegistry().get(model);
		if (editpart instanceof EditPart) {
			//Force a layout first.
			getViewer().flush();
			viewer.select((EditPart)editpart);
		}
	}

	/**
	 * Assumes that the target request is a {@link CreateRequest}. 
	 */
	@Override
	protected void updateTargetRequest() {
		CreateRequest request = getCreateRequest();
		request.setLocation(getDropLocation());
	}

}
