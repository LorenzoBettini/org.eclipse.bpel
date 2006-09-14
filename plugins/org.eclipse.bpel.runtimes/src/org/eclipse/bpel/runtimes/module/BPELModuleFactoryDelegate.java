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
package org.eclipse.bpel.runtimes.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.bpel.runtimes.IBPELModuleFacetConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jem.util.logger.proxy.Logger;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.internal.Trace;
import org.eclipse.wst.server.core.model.ModuleDelegate;
import org.eclipse.wst.server.core.model.ModuleFactoryDelegate;

import sun.security.action.GetPropertyAction;

/**
 * <code>ModuleFactoryDelegate</code> implementation for discovering BPEL modules
 * (for now, just BPEL files not entire projects).
 *
 * @author Bruno Wassermann, written Aug 4, 2006
 */
public class BPELModuleFactoryDelegate extends ModuleFactoryDelegate {
	
	/**
	 * Stores a mapping from <code>IModules</code> to <code>BPELModuleDelegates</code>
	 */
	protected Map moduleDelegates = new HashMap(5);
	
	/**
	 * List of all BPEL modules that exist in workspace.
	 */
	private List modules;
	
	public BPELModuleFactoryDelegate() {
		// 0-arg constructor used by when setting this in ModuleFactory
	}
	
	
	/**
	 * Create a module from given information and invoke creation of 
	 * corresponding module delegate.
	 * 
	 * @param project <code>IProject</code> the module belongs to
	 * @param file <code>IFile</code> representing the module (or is it vice versa?)
	 * @return
	 */
	protected IModule createModule(IProject project, IFile file) {
		try {
//			IVirtualComponent comp = ComponentCore.createComponent(project);
			return createModuleDelegates(project, file);
		} catch (Exception e) {
			Logger.getLogger().write(e);
		}
		return null;
	}
	
	protected IModule createModuleDelegates(IProject project, IFile file) {
		try {			
			IModule module = createModule(
					project.getName().concat(file.getName()),
					file.getName(), 
					IBPELModuleFacetConstants.BPEL20_MODULE_TYPE,
					getVersion(project),
					project);
			BPELModuleDelegate moduleDelegate = new BPELModuleDelegate(project, file);
			moduleDelegates.put(module, moduleDelegate);
			return module;
		} catch (Exception e) {
			Logger.getLogger().write(e);
		}
		return null;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.core.model.ModuleFactoryDelegate#getModuleDelegate(org.eclipse.wst.server.core.IModule)
	 */
	public ModuleDelegate getModuleDelegate(IModule module) {
		return (ModuleDelegate) moduleDelegates.get(module);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.server.core.model.ModuleFactoryDelegate#getModules()
	 */
	public IModule[] getModules() {
		cacheModules();
		
		IModule[] modules2 = new IModule[modules.size()];
		modules.toArray(modules2);
		return modules2;
	}
	
	protected void clearCache() {
		moduleDelegates = new HashMap(5);
	}
	
	/**
	 * Discovers any BPEL modules and stores them in a <code>List</code> in case
	 * <code>getModules</code> is called for the first time in this session.
	 * This is to make sure that we don't miss any pre-existing modules.
	 * <p>
	 * PUBLIC FOR TESTING ONLY 
	 */
	public final void cacheModules() {
		if (modules != null) {
			return; // has already been initialised with pre-existing modules
		}
		
		try {
			clearCache();
			IProject[] projects2 = getWorkspaceRoot().getProjects();
			int size = projects2.length;
			modules = new ArrayList(size);
			
			for (int i = 0; i < size; i++) {
				final ArrayList bpelFileResources = new ArrayList();

				if (projects2[i].isAccessible()) {
					IResource[] prjResources = projects2[i].members();
					
					for (int x=0; x<prjResources.length; x++) {
						
						if (IResource.FOLDER == prjResources[x].getType()) {
							prjResources[x].accept(new IResourceVisitor() {
								public boolean visit(IResource resource) {
									
									switch (resource.getType()) {
									case IResource.FOLDER:
										return true;
									
									case IResource.FILE:
										if (isBPELFile((IFile) resource)) {
											bpelFileResources.add(resource);
										}
										/* falls through */
									
									default:
										return false;
									}
								}
							});
						} 
						if (IResource.FILE == prjResources[x].getType()) {
						
							if (isBPELFile((IFile) prjResources[x])) {
								bpelFileResources.add(prjResources[x]);
							}
						}
					}
					
					try {
						for (Iterator iter = bpelFileResources.iterator(); iter.hasNext(); ) {
							IModule module = createModule(
									projects2[i], 
									(IFile) iter.next());
							
							if (module != null) {							
								modules.add(module);
							}
						}
					} catch (Throwable t) {
						Trace.trace(Trace.SEVERE, "Error creating module", t);
					}
				}
			}
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error caching modules", e);
		}
	}
	
	/**
	 * Return the workspace root.
	 * 
	 * @return the workspace root
	 */
	private static IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}
	
	private boolean isBPELFile(IFile file) {
		if (IBPELModuleFacetConstants.BPEL_FILE_EXTENSION.equals(file.getFileExtension())) {
			return true;
		}
		return false;
	}
	
	/*
	 * TODO in case fixed BPEL facet is not set anymore, may be able to set
	 * the facet programmatically as a fix for the time being
	 */
	private String getVersion(IProject project) {
		IFacetedProject facetedProject = null;
		try {
			facetedProject = ProjectFacetsManager.create(project);
			if (facetedProject != null 
					&& ProjectFacetsManager.isProjectFacetDefined(IBPELModuleFacetConstants.BPEL20_PROJECT_FACET)) 
			{
				IProjectFacet projectFacet = ProjectFacetsManager.getProjectFacet(IBPELModuleFacetConstants.BPEL20_PROJECT_FACET);
				
				return facetedProject.getInstalledVersion(projectFacet).getVersionString();
			}
		} catch (Exception e) {
			Logger.getLogger().write(e);
		}
		return "2.0"; //$NON-NLS-1$
	}
	
	/**
	 * FOR TESTING PURPOSES ONLY
	 */
	public List getCachedModules() {
		return modules;
	}

}
