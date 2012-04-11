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
package org.eclipse.bpel.jboss.riftsaw.runtime;

import java.io.File;

import org.eclipse.bpel.runtimes.publishers.GenericBPELPublisher;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jst.server.core.PublishUtil;
import org.eclipse.wst.server.core.IModule;
import org.eclipse.wst.server.core.IModuleArtifact;
import org.eclipse.wst.server.core.IRuntime;
import org.eclipse.wst.server.core.model.IModuleResource;
import org.eclipse.wst.server.core.model.ModuleDelegate;

public class RiftsawPublisher extends GenericBPELPublisher {

	public RiftsawPublisher() {
		super();
	}

	@Override
	public IStatus[] publish(IModuleArtifact[] artifacts, IProgressMonitor monitor) {
		// resources will always be null for some weird reason :(
		// therefore we generate a BPELModuleArtifact
		// the module id value enables us to get BPEL file path relative to its project
		IModule[] modules = super.getModule();
		
		try {
			IModule last = modules[modules.length-1];
			IPath root = createDeploymentDestination(last);
			ModuleDelegate delegate = (ModuleDelegate)last.loadAdapter(ModuleDelegate.class, new NullProgressMonitor());
			IModuleResource[] resources = delegate.members();
			PublishUtil.publishFull(resources, root, monitor);
		} catch(  CoreException ce ) {
			// TODO return bad status
		}
		return new IStatus[]{Status.OK_STATUS};

	}

	@Override
	public IStatus[] unpublish(IProgressMonitor monitor) {
		IModule[] modules = super.getModule();
		IModule last = modules[modules.length - 1];
		IStatus[] result = new Status[modules.length];
		IPath root = createDeploymentDestination(last);
		PublishUtil.deleteDirectory(root.toFile(), monitor);
		return result;
	}

	/**
	 * This method will create a folder inside the server/default/deploy subfolder
	 * of the JBoss AS installation.
	 * TODO: use actual server configuration instead of /server/default
	 */
	protected IPath createDeploymentDestination(IModule module) {
		String moduleName = module.getName();
		String deployAppName = moduleName;

		// get JBOSS_HOME
		IRuntime serverDef = getServerRuntime().getRuntime();
		IPath jbossHome = serverDef.getLocation();

		// append default server directory and app name
		IPath deployTarget = jbossHome.append("server").append("default")
				.append("deploy").append(deployAppName);

		File f = deployTarget.toFile();
		if (!f.exists()) {
			f.mkdir();
		}

		return deployTarget;
	}
}
