/*******************************************************************************
 * Copyright (c) 2006 University College London Software Systems Engineering
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	Bruno Wassermann - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.runtimes.facets;

import org.eclipse.bpel.runtimes.IBPELModuleFacetConstants;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jem.util.logger.proxy.Logger;
import org.eclipse.wst.common.componentcore.datamodel.FacetDataModelProvider;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.componentcore.internal.operation.FacetDataModelOperation;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.frameworks.internal.datamodel.DataModelImpl;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * BPEL Facet implementation of <code>IDelegate</code>. 
 * <p>
 * Note: Must not call IFacetedProject.modify() to install facet as this is
 * a prohibited operation from a delegate and will throw <code>CoreException</code>.
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
						IProgressMonitor monitor) 
		throws CoreException 
	{
		monitor.beginTask("", 3);
		monitor.worked(1);
		
		FacetInstallDataModelProvider dmProvider = new FacetInstallDataModelProvider();
		IDataModel dataModel = new DataModelImpl(dmProvider);
		monitor.worked(1);
		
		try {
			dataModel.setProperty(
					IFacetDataModelProperties.FACET_ID, 
					IBPELModuleFacetConstants.BPEL20_PROJECT_FACET);
			dataModel.setProperty(
					IFacetDataModelProperties.FACET_PROJECT_NAME, 
					proj.getName());
			dataModel.setProperty(
					IFacetDataModelProperties.FACET_VERSION, 
					ver);
			dataModel.setProperty(
					IFacetDataModelProperties.FACET_VERSION_STR, 
					ver.getVersionString());
			dataModel.setProperty(IFacetDataModelProperties.SHOULD_EXECUTE, Boolean.TRUE);

			monitor.worked(1);

		} catch (Exception e) {
			Logger.getLogger().logError(e);
		}		
		monitor.done();
	}
}
