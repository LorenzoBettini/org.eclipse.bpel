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
package org.eclipse.bpel.ui.proposal.providers;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.adapters.ILabeledElement;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jul 25, 2006
 *
 */
public class AbstractContentProposal 
	implements IContentProposal , ILabelProvider, ILabeledElement
	
{	
	/**
	 * 
	 */
	public AbstractContentProposal() {
		super();
	}

	public String getContent() {
		return null;
	}

	public int getCursorPosition() {
		return (-1);
	}

	public String getDescription() {
		return null;
	}

	public String getLabel() {
		return "----------------------------------------"; //$NON-NLS-1$
	}

	public Image getImage (Object element) {			
		return BPELUIPlugin.getPlugin().getImage(IBPELUIConstants.ICON_SEPARATOR_16);
	}

	public String getText (Object element) {
		return getLabel();
	}

	public void addListener(ILabelProviderListener listener) {		
		
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	public void removeListener(ILabelProviderListener listener) {
		
	}

	public String getLabel(Object object) {
		return getLabel();
	}

	public Image getLargeImage(Object object) {		
		return null;
	}

	public Image getSmallImage(Object object) {
		return getImage( object );
	}

	public String getTypeLabel(Object object) {	
		return null;
	}

}