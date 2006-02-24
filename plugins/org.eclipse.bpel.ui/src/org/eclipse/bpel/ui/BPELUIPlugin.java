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
package org.eclipse.bpel.ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.eclipse.bpel.common.ui.ColorUtils;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ISavedState;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


public class BPELUIPlugin extends AbstractUIPlugin {
	public static final String PLUGIN_ID = "org.eclipse.bpel.ui"; //$NON-NLS-1$

	static BPELUIPlugin plugin;

	private ColorRegistry colorRegistry;
	protected boolean imagesAndColorsInitialized;
	
	// takes care of changes to the BPEL file
	private BPELResourceChangeListener resourceChangeListener;
	private ISaveParticipant saveParticipant;

	public BPELUIPlugin() {
		super();
		plugin = this;
	}

	/**
	 * Creates an image descriptor and places it in the image registry.
	 */
	private void createImageDescriptor(String id, URL baseURL) {
		URL url = null;
		try {
			url = new URL(baseURL, IBPELUIConstants.ICON_PATH + id);
		} catch (MalformedURLException e) {
			BPELUIPlugin.log(e);
		}
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		getImageRegistry().put(id, desc);
	}

	/**
	 * Returns the image descriptor for the given image ID.
	 * Returns null if there is no such image.
	 */
	public ImageDescriptor getImageDescriptor(String id) {
		return getImageRegistry().getDescriptor(id);
	}
	public Image getImage(String id) {
		return getImageRegistry().get(id);
	}

	public static BPELUIPlugin getPlugin() {
		return plugin;
	}
	
	private void initializeColors() {
		colorRegistry = new ColorRegistry();
		
		RGB color_240_240_240 = ColorUtils.getRelativeRGB(240, 240, 240);
		RGB color_255_255_255 = ColorUtils.getRelativeRGB(255, 255, 255);
		colorRegistry.put(IBPELUIConstants.COLOR_FLOW_BORDER, ColorUtils.getRelativeRGB(112, 152, 224));
		
		colorRegistry.put(IBPELUIConstants.COLOR_GRADIENT_FROM, color_255_255_255);
		colorRegistry.put(IBPELUIConstants.COLOR_GRADIENT_TO, color_240_240_240);
		
		if (ColorUtils.isInvertedColorScheme()) {
			colorRegistry.put(IBPELUIConstants.COLOR_ACTIVITY_BORDER, ColorUtils.getRelativeRGB(25, 25, 25));
			colorRegistry.put(IBPELUIConstants.COLOR_COMPOSITE_ACTIVITY_BORDER, ColorUtils.getRelativeRGB(25, 25, 25));
			colorRegistry.put(IBPELUIConstants.COLOR_SCOPE_BORDER, ColorUtils.getRelativeRGB(25, 25, 25));
			colorRegistry.put(IBPELUIConstants.COLOR_IMPLICIT_LINK, ColorUtils.getRelativeRGB(25, 25, 25));
		} else {
			colorRegistry.put(IBPELUIConstants.COLOR_ACTIVITY_BORDER, ColorUtils.getRelativeRGB(176, 176, 176));
			colorRegistry.put(IBPELUIConstants.COLOR_COMPOSITE_ACTIVITY_BORDER, ColorUtils.getRelativeRGB(205, 205, 205));
			colorRegistry.put(IBPELUIConstants.COLOR_SCOPE_BORDER, ColorUtils.getRelativeRGB(205, 205, 205));
			colorRegistry.put(IBPELUIConstants.COLOR_IMPLICIT_LINK, ColorUtils.getRelativeRGB(200, 200, 200));
		}
		colorRegistry.put(IBPELUIConstants.COLOR_HILIGHT_NODE, ColorUtils.getRelativeRGB(255, 255, 0));		
		colorRegistry.put(IBPELUIConstants.COLOR_VARIABLE_BACKGROUND, ColorUtils.getRelativeRGB(255, 255, 255));
		colorRegistry.put(IBPELUIConstants.COLOR_VARIABLE_SEPARATOR, ColorUtils.getRelativeRGB(230, 230, 230));
		colorRegistry.put(IBPELUIConstants.COLOR_VARIABLE_REFERENCE, ColorUtils.getRelativeRGB(99, 151, 245));
		colorRegistry.put(IBPELUIConstants.COLOR_LINK_ONE, ColorUtils.getRelativeRGB(238, 197, 253));
		colorRegistry.put(IBPELUIConstants.COLOR_LINK_TWO, ColorUtils.getRelativeRGB(73, 0, 107));
		colorRegistry.put(IBPELUIConstants.COLOR_LINK_THREE, ColorUtils.getRelativeRGB(222, 144, 254));
		
		colorRegistry.put(IBPELUIConstants.COLOR_WHITE, ColorUtils.getRelativeRGB(255, 255, 255));
		colorRegistry.put(IBPELUIConstants.COLOR_BLACK, ColorUtils.getRelativeRGB(0,0,0)); 
		colorRegistry.put(IBPELUIConstants.COLOR_RED, ColorUtils.getRelativeRGB(255,0,0));
		colorRegistry.put(IBPELUIConstants.COLOR_DARK_RED, ColorUtils.getRelativeRGB(0x80, 0, 0));
		colorRegistry.put(IBPELUIConstants.COLOR_GREEN, ColorUtils.getRelativeRGB( 0, 255, 0));
		colorRegistry.put(IBPELUIConstants.COLOR_DARK_GREEN, ColorUtils.getRelativeRGB(0, 0x80, 0));
		colorRegistry.put(IBPELUIConstants.COLOR_YELLOW, ColorUtils.getRelativeRGB(255, 255, 0));
		colorRegistry.put(IBPELUIConstants.COLOR_DARK_YELLOW, ColorUtils.getRelativeRGB(0x80, 0x80, 0));
		colorRegistry.put(IBPELUIConstants.COLOR_BLUE, ColorUtils.getRelativeRGB( 0, 0 ,255));
		colorRegistry.put(IBPELUIConstants.COLOR_DARK_BLUE, ColorUtils.getRelativeRGB(0, 0, 0x80));
		colorRegistry.put(IBPELUIConstants.COLOR_MAGENTA, ColorUtils.getRelativeRGB( 255, 0, 255));
		colorRegistry.put(IBPELUIConstants.COLOR_DARK_MAGENTA, ColorUtils.getRelativeRGB( 0x80, 0, 0x80));
		colorRegistry.put(IBPELUIConstants.COLOR_CYAN, ColorUtils.getRelativeRGB( 0, 255, 255));
		colorRegistry.put(IBPELUIConstants.COLOR_DARK_CYAN, ColorUtils.getRelativeRGB( 0, 0x80, 0x80));
		colorRegistry.put(IBPELUIConstants.COLOR_GRAY, ColorUtils.getRelativeRGB( 0xC0, 0xC0, 0xC0));
		colorRegistry.put(IBPELUIConstants.COLOR_DARK_GRAY, ColorUtils.getRelativeRGB(0x80, 0x80, 0x80));
	}
	
	/**
	 * TODO: registry should be moved to editors and removed from plug-in
	 */
	public ColorRegistry getColorRegistry() {
		if (colorRegistry == null) {
			initialize();
		}
		return colorRegistry;
	}
	
	public ImageRegistry getImageRegistry() {
		ImageRegistry result = super.getImageRegistry();
		initialize();
		return result;
	}
	
	/**
	 * Initializes the table of images used in this plugin.
	 */
	private void initializeImages() {
		URL baseURL = getBundle().getEntry("/"); //$NON-NLS-1$

		createImageDescriptor(IBPELUIConstants.ICON_ASSIGN_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ASSIGN_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_EMPTY_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_EMPTY_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_FLOW_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_FLOW_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_INVOKE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_INVOKE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PICK_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PICK_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_RECEIVE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_RECEIVE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_REPLY_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_REPLY_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SCOPE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SCOPE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SEQUENCE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SEQUENCE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SWITCH_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SWITCH_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_EXIT_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_EXIT_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_THROW_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_THROW_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_RETHROW_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_RETHROW_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_COMPENSATE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_COMPENSATE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_WAIT_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_WAIT_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_WHILE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_WHILE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_REPEAT_UNTIL_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_REPEAT_UNTIL_32, baseURL);		
		createImageDescriptor(IBPELUIConstants.ICON_VALIDATE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_VALIDATE_32, baseURL);		
		createImageDescriptor(IBPELUIConstants.ICON_PARTNER_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PARTNER_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PARTNER_IN_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PARTNER_OUT_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_VARIABLE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_VARIABLE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_CORRELATIONSET_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_CORRELATIONSET_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_LINK_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_LINK_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PROCESS_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PROCESS_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_STARTNODE_16_GIF, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ENDNODE_16_GIF, baseURL);
		
		createImageDescriptor(IBPELUIConstants.ICON_CASE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_CASE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_OTHERWISE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_OTHERWISE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ONMESSAGE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ONMESSAGE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ONALARM_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ONALARM_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_FAULTHANDLER_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_FAULTHANDLER_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_COMPENSATIONHANDLER_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_COMPENSATIONHANDLER_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_TERMINATIONHANDLER_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_TERMINATIONHANDLER_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_EVENTHANDLER_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_EVENTHANDLER_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_CATCH_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_CATCH_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_CATCHALL_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_CATCHALL_32, baseURL);
		
		createImageDescriptor(IBPELUIConstants.ICON_FAULT_INDICATOR, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_COMPENSATION_INDICATOR, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_EVENT_INDICATOR, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_TERMINATION_INDICATOR, baseURL);

		createImageDescriptor(IBPELUIConstants.ICON_ACTION_INCOMING, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ACTION_INCOMING_GIF, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ACTION_OUTGOING, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ACTION_OUTGOING_GIF, baseURL);
		
		createImageDescriptor(IBPELUIConstants.ICON_FIGURE_EXPANDED, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_FIGURE_COLLAPSED, baseURL);

		createImageDescriptor(IBPELUIConstants.ICON_OUTLINE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_OVERVIEW_16, baseURL);
		
		// Wizard banner
		createImageDescriptor(IBPELUIConstants.ICON_WIZARD_BANNER, baseURL);
		
		// Cursors
		createImageDescriptor(IBPELUIConstants.CURSOR_ZOOM_MASK, baseURL);
		createImageDescriptor(IBPELUIConstants.CURSOR_ZOOM_IN, baseURL);
		createImageDescriptor(IBPELUIConstants.CURSOR_ZOOM_OUT, baseURL);

		createImageDescriptor(IBPELUIConstants.ICON_LINK_BOTTOMLEFT, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_LINK_BOTTOMRIGHT, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_LINK_TOPLEFT, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_LINK_TOPRIGHT, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_LINK_ARROWDOWN, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_LINK_CONDITIONAL, baseURL);

		createImageDescriptor(IBPELUIConstants.ICON_MESSAGE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_MESSAGE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_OPERATION_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PART_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PART_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PORTTYPE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PORTTYPE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ROLE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_ROLE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PARTNERLINKTYPE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PARTNERLINKTYPE_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PROPERTY_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_PROPERTY_32, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SERVICE_16, baseURL);
		createImageDescriptor(IBPELUIConstants.ICON_SERVICE_32, baseURL);
	}

	/**
	 * @see org.eclipse.core.runtime.Plugin#start(BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		initializePreferences();
		initializeResourceChangeListener();
	}

	protected void initializePreferences() {
		IPreferenceStore store = getPreferenceStore();		
		store.setDefault(IBPELUIConstants.PREF_SHOW_FREEFORM_FLOW, true);
		store.setDefault(IBPELUIConstants.PREF_WARN_LINKS, true);
		store.setDefault(IBPELUIConstants.PREF_AUTO_FLOW_LAYOUT, false);
		store.setDefault(IBPELUIConstants.PREF_USE_ANIMATION, true);
		store.setDefault(IBPELUIConstants.PREF_CREATE_SPEC_COMPLIANT_PROCESS, false);
	}
	
	/**
	 * Installs the IResourceChangeListener for the BPEL Plugin. Also
	 * checks if there were any changes to BPEL files while the plug-in
	 * was not active.
	 */
	private void initializeResourceChangeListener() throws CoreException {
		resourceChangeListener = new BPELResourceChangeListener();
		// Add the save participant in a separate thread
		// to make sure that it doesn't block the UI thread and potentially cause
		// deadlocks with the code that caused our plugin to be started.
		Thread initSaveParticipantThread = new Thread(new Runnable() {
			public void run() {
				try {
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					workspace.addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_BUILD);
					ISavedState savedState = workspace.addSaveParticipant(BPELUIPlugin.this, getSaveParticipant());
					if (savedState != null) {
						savedState.processResourceChangeEvents(resourceChangeListener);
					}
				} catch (CoreException e) {
					throw new RuntimeException(e);
				}
			}
		});
		initSaveParticipantThread.setName("BPELUIPlugin init"); //$NON-NLS-1$
		initSaveParticipantThread.start();
	}

	public boolean activateZoomSupport() {
		return false;
	}

	protected void initialize() {
		if (!imagesAndColorsInitialized) {
			imagesAndColorsInitialized = true;
			initializeImages();
			initializeColors();
		}
	}
	
	/**
	 * @see org.eclipse.core.runtime.Plugin#stop(BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		disposeColors();
		super.stop(context);
	}
	
	private void disposeColors() {
		if (colorRegistry != null) {
			Iterator it = colorRegistry.getKeySet().iterator();
			while (it.hasNext()) {
				Color c = colorRegistry.get((String)it.next());
				c.dispose();
			}
			colorRegistry = null;
		}
	}
	
	/**
	 * We are only interested in the resource delta while the plugin was
	 * not active and don't really care about the plug-in save lifecycle.
	 */
	private ISaveParticipant getSaveParticipant() {
		if (saveParticipant == null) {
			saveParticipant = new ISaveParticipant() {
				public void doneSaving(ISaveContext context) {
				}
				public void prepareToSave(ISaveContext context) throws CoreException {
				}
				public void rollback(ISaveContext context) {
				}
				public void saving(ISaveContext context) throws CoreException {
					context.needDelta();
				}
			};
		}
		return saveParticipant;
	}
	
	/**
	 * Returns the resource change listener.
	 */
	public BPELResourceChangeListener getResourceChangeListener() {
		return resourceChangeListener;
	}

	/**
	 * Utility methods for logging exceptions.
	 */
	public static void log(Exception e, int severity) {
		IStatus status = null;
		if (e instanceof CoreException) {
			status = ((CoreException)e).getStatus();
		} else {
			String m = e.getMessage();
			status = new Status(severity, PLUGIN_ID, 0, m==null? "<no message>" : m, e); //$NON-NLS-1$
		}
		System.out.println(e.getClass().getName()+": "+status); //$NON-NLS-1$
		BPELUIPlugin.getPlugin().getLog().log(status);
	}
	
	public static void log(Exception e) { log(e, IStatus.ERROR); }
}
