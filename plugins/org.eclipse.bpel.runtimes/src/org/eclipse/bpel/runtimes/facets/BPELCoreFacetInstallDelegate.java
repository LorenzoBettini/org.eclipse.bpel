/*******************************************************************************
 * Copyright (c) 2006 University College London Software Systems Engineering
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contribution by:
 *     University College London Software Systems Engineering
 *******************************************************************************/
package org.eclipse.bpel.runtimes.facets;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * BPEL Facet implementation of <code>IDelegate</code>. 
 *
 * @author Bruno Wassermann, written Jun 7, 2006
 */
public class BPELCoreFacetInstallDelegate implements IDelegate {

	/**
	 * At the moment, there does not appear to be any opportunity to do some
	 * common setup of stuff here (e.g., set up a WEB-INF folder, etc.). 
	 * Maybe some common requirements will become apparent at some later stage.
	 */
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.common.project.facet.core.IDelegate#execute(org.eclipse.core.resources.IProject, org.eclipse.wst.common.project.facet.core.IProjectFacetVersion, java.lang.Object, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void execute(IProject proj, 
						IProjectFacetVersion ver, 
						Object obj,
						IProgressMonitor progMon) 
		throws CoreException 
	{
		progMon.beginTask( "", 2 );

        try
        {
        	progMon.worked( 1 );
        	progMon.worked( 1 );
        }
        finally
        {
            progMon.done();
        }
	}
	
}
