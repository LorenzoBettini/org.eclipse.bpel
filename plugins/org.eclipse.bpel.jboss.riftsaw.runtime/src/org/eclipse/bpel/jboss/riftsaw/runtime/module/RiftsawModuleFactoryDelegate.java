/*******************************************************************************
 * Copyright (c) 2008, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.jboss.riftsaw.runtime.module;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.jboss.riftsaw.runtime.IRiftsawModuleFacetConstants;
import org.eclipse.bpel.runtimes.IBPELModuleFacetConstants;
import org.eclipse.bpel.runtimes.module.FlatComponentDeployable;
import org.eclipse.core.resources.IProject;
import org.eclipse.wst.common.componentcore.internal.util.FacetedProjectUtilities;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.server.core.IModule;

public class RiftsawModuleFactoryDelegate extends org.eclipse.bpel.runtimes.module.BPELModuleFactoryDelegate {

	@Override
	protected boolean canHandleProject(IProject p) {
		boolean result = FacetedProjectUtilities.isProjectOfType(p,
				IRiftsawModuleFacetConstants.JBT_BPEL_MODULE_TYPE);
		return result;
	}
	
	@Override
	protected IModule[] createModuleDelegates(IVirtualComponent component) {
		if(component == null){
			return null;
		}
		
		List<IModule> projectModules = new ArrayList<IModule>();
		try {
			if (canHandleProject(component.getProject())) {
				canHandleProject(component.getProject());
				String type = IRiftsawModuleFacetConstants.JBT_BPEL_MODULE_TYPE;
				String version = IBPELModuleFacetConstants.BPEL20_MODULE_VERSION;
				IModule module = createModule(component.getName(), component.getName(), type, version, component.getProject());
				FlatComponentDeployable moduleDelegate = createModuleDelegate(component.getProject(), component);
				moduleDelegates.put(module, moduleDelegate);
				projectModules.add(module);

				// Check to add any binary modules
//				if (J2EEProjectUtilities.ENTERPRISE_APPLICATION.equals(type))
//					projectModules.addAll(LEGACY_createBinaryModules(component));
			} else {
				return null;
			}
		} catch (Exception e) {
//			e.printStackTrace();
//			J2EEPlugin.logError(e);
		}
		return projectModules.toArray(new IModule[projectModules.size()]);
	}

}
