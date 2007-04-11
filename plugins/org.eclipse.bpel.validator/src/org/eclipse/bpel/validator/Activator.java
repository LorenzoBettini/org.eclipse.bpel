package org.eclipse.bpel.validator;


import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.eclipse.bpel.validator.model.IFactory;
import org.eclipse.bpel.validator.model.IModelQuery;
import org.eclipse.bpel.validator.model.RuleFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */

@SuppressWarnings("nls")
public class Activator extends Plugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.eclipse.bpel.validator"; //$NON-NLS-1$

	/** The shared instance */
	static Activator plugin;
	
	
	/**
	 * The constructor
	 */
	public Activator() {
		plugin = this;
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	

	@Override
	public void start (BundleContext context) throws Exception {
		super.start(context);
		
		// hook up Java logging to the Eclipse error log		
		Logger logger = Logger.getLogger( PLUGIN_ID );
		Handler handler = new LogHandler();
		logger.addHandler( handler );	
				
		
		
		// factories 
		IExtensionPoint ep = Platform.getExtensionRegistry().getExtensionPoint(PLUGIN_ID,"factories");
				
		for(IExtension e : ep.getExtensions() ) {
			for(IConfigurationElement ce : e.getConfigurationElements() ) {
				Object obj = ce.createExecutableExtension("class");
				if (obj instanceof IFactory) {
					RuleFactory.registerFactory( (IFactory) obj);
				}
			}
		}
		
		
		// modelQuery
		ep = Platform.getExtensionRegistry().getExtensionPoint(PLUGIN_ID,"modelQuery");
		
		for(IExtension e : ep.getExtensions() ) {
			for(IConfigurationElement ce : e.getConfigurationElements() ) {
				Object obj = ce.createExecutableExtension("class");
				if (obj instanceof IModelQuery) {
					ModelQuery.register( (IModelQuery) obj);
				}
			}
		}
		
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}


	
	/**
	 * Utility methods for logging exceptions.
	 * @param e exception to log
	 * @param severity the severity to log the exception as.
	 */
	public static void log (Exception e, int severity) {
		
		IStatus status = null;
		if (e instanceof CoreException) {
			status = ((CoreException)e).getStatus();
		} else {
			String m = e.getMessage();
			status = new Status(severity, PLUGIN_ID, 0, m==null? "<no message>" : m, e); //$NON-NLS-1$
		}
		
		plugin.getLog().log(status);
	}
	
	
	/** 
	 * Log an exception.
	 * @param e exception the log
	 */
	
	public static void log(Exception e) { 
		log(e, IStatus.ERROR); 
	}
	
		
	class LogHandler extends Handler {

		/** (non-Javadoc)
		 * @see java.util.logging.Handler#close()
		 */
		@Override
		public void close() throws SecurityException {			
			
		}

		/** (non-Javadoc)
		 * @see java.util.logging.Handler#flush()
		 */
		@Override
		public void flush() {
			
		}

		/** (non-Javadoc)
		 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
		 */
		
		@SuppressWarnings("nls")
		@Override
		public void publish (LogRecord record) {								
			
			StringBuilder sb = new StringBuilder();
			sb.append(record.getThreadID());
			sb.append(":");
			sb.append(record.getLoggerName());
			sb.append(":");
			sb.append(record.getMessage());
			
			Throwable t = record.getThrown();
			int severity = IStatus.INFO;
			
			if (record.getLevel() == Level.SEVERE) {
				severity = IStatus.ERROR;
			} else if (record.getLevel() == Level.WARNING) {
				severity = IStatus.WARNING;
			}
			
			IStatus status = new Status(severity, PLUGIN_ID, 0,sb.toString() , t); //$NON-NLS-1$
			plugin.getLog().log(status);
		}
		
	}
}
