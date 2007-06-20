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
package org.eclipse.bpel.validator.adapters;

/**
 * BPEL validation model dependency
 */
import java.util.Collections;
import java.util.List;


/** 
 * BPEL EMF Model dependency 
 */
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.model.PartnerActivity;
import org.eclipse.bpel.validator.model.INode;


/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 20, 2006
 */


public class PartnerActivityAdapter extends BasicAdapter {

	
	/** (non-Javadoc)
	 * @see org.eclipse.bpel.validator.adapters.BasicAdapter#getNodeList(java.lang.String)
	 */
	
	@Override
	public List<INode> getNodeList ( String name ) {
		PartnerActivity pa = (PartnerActivity) getTarget();
		
		if (ND_CORRELATION.equals ( name )) {
			Correlations c = pa.getCorrelations();
			if (c != null) {
				return c.getChildren();
			}
			return Collections.emptyList();
		}	
		return super.getNodeList(name);
	}
	


	
}
