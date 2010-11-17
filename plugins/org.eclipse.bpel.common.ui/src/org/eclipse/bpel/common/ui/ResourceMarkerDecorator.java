/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.eclipse.bpel.common.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

/**
 * Lightweight icon decorator for error/warning/info icons in Navigator view
 *
 * @see https://jira.jboss.org/browse/JBIDE-6016
 * @author Bob Brodt
 * @date Nov 16, 2010
 */
public class ResourceMarkerDecorator implements ILightweightLabelDecorator, ICommonUIConstants {

	private static ImageDescriptor img_error;
	private static ImageDescriptor img_warning;
	private static ImageDescriptor img_info;
	
	public ResourceMarkerDecorator()
	{
		super();
		
		img_error = CommonUIPlugin.getDefault().getImageRegistry().getDescriptor(ICommonUIConstants.ICON_ERROR);
		img_warning = CommonUIPlugin.getDefault().getImageRegistry().getDescriptor(ICommonUIConstants.ICON_WARNING);
		img_info = CommonUIPlugin.getDefault().getImageRegistry().getDescriptor(ICommonUIConstants.ICON_INFO);
	}
	
	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

	public void decorate(Object element, IDecoration decoration) {
		try {
			int severity = ((IResource)element).findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_ONE);
			switch (severity) {
			case IMarker.SEVERITY_ERROR:
				decoration.addOverlay(img_error);
				break;
			case IMarker.SEVERITY_WARNING:
				decoration.addOverlay(img_warning);
				break;
			case IMarker.SEVERITY_INFO:
				decoration.addOverlay(img_info);
				break;
			}
		}
		catch(Exception e) {
		}
	}
}
