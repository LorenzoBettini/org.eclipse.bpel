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
package org.eclipse.bpel.ui.commands;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.ui.IBPELUIConstants;


/** 
 * Sets the "initiate" property of a Correlation (NOTE: not a CorrelationSet!)
 */
public class SetCorrelationInitiateCommand extends SetCommand {

	/**
	 * @see org.eclipse.bpel.ui.commands.SetCommand#getDefaultLabel()
	 */
	@Override
	public String getDefaultLabel() { 
		return IBPELUIConstants.CMD_EDIT_CORRELATION; 
	}

	/**
	 * Brand new SetCorrelationInitiateCommand
	 * @param target 
	 * @param newInitiation
	 */
	public SetCorrelationInitiateCommand(Correlation target, String newInitiation)  {
		super(target, newInitiation,BPELPackage.eINSTANCE.getCorrelation_Initiate() );
	}
}
