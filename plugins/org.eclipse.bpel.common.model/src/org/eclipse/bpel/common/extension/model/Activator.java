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
package org.eclipse.bpel.common.extension.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */

@SuppressWarnings({"boxing","nls"})

public class Activator extends Plugin {

	/**
	 * The plugin id.
	 */
	
	public static final String PLUGIN_ID = "org.eclipse.bpel.common.model"; //$NON-NLS-1$
	
	/** The shared instance. */
	static public  Activator INSTANCE;
	
	/**
	 * The constructor.
	 */
	public Activator() {
		INSTANCE = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		INSTANCE = null;
	}


	/**
	 * Utility methods for logging exceptions.
	 * @param message 
	 * @param e 
	 * @param severity 
	 */
	

	
	public static void log (String message, Throwable e, int severity) {
		
		IStatus status = null;
		
		if (e instanceof CoreException) {
			status = ((CoreException)e).getStatus();
		} else {
			String m = e != null ? e.getMessage() : null;
			
            if (message == null) {
            	if (m == null) {
            		m = "<no message>";
            	}            	
            } else {
            	if (m == null) {
            		m = message;
            	} else {
            		m = message + "[" + m + "]";
            	}
            }
					
			status = new Status(severity, PLUGIN_ID, 0, m, e); //$NON-NLS-1$
		}		
		
		if (Platform.isRunning()) {
			INSTANCE.getLog().log(status);
		} else {
			String msg = java.text.MessageFormat.format(
					"{1,choice,0#msg|1#Info|2#Warning|4#Error}@{0}: {3}",
					status.getPlugin(), 
					status.getSeverity(),
					status.getCode(), 
					status.getMessage() );
			
			System.err.println(msg);
			
			if (status.getException() != null) {
				status.getException().printStackTrace(System.err);
			}
		}		
	}
	
	
	/**
	 * The configuration elements for our extension points
	 * 
	 * @param extensionPointId our extension points 
	 * 
	 * @return the configuration elements.
	 * 
	 */
	
	public IConfigurationElement[] getConfigurationElements (String extensionPointId) {
		String id = getBundle().getSymbolicName();
		
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(id, extensionPointId);
		if (extensionPoint == null) {
			return new IConfigurationElement[] {} ;
		}
		return extensionPoint.getConfigurationElements();
	}
	
}
