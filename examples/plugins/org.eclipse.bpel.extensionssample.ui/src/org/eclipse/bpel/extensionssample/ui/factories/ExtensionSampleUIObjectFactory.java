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
package org.eclipse.bpel.extensionssample.ui.factories;

import org.eclipse.bpel.extensionsample.model.ModelPackage;
import org.eclipse.bpel.extensionssample.ui.Activator;
import org.eclipse.bpel.extensionssample.ui.ExtensionSampleUIConstants;
import org.eclipse.bpel.ui.factories.IExtensionUIObjectFactory;
import org.eclipse.bpel.ui.factories.AbstractUIObjectFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class ExtensionSampleUIObjectFactory extends AbstractUIObjectFactory implements IExtensionUIObjectFactory {

	private EClass modelType;
	private EClass[] classArray = { ModelPackage.eINSTANCE.getSampleSimpleActivity(), ModelPackage.eINSTANCE.getSampleStructuredActivity()};

	public ExtensionSampleUIObjectFactory(EClass modelType) {
		super();
		this.modelType = modelType;
	}
	
	public ExtensionSampleUIObjectFactory() {
		super();
	}
	
	@Override
	public Image getLargeImage() {
		return Activator.getDefault().getImageRegistry().get(ExtensionSampleUIConstants.DEFAULT_ICON_20);
	}

	@Override
	public ImageDescriptor getLargeImageDescriptor() {
		return Activator.getDefault().getImageDescriptor(ExtensionSampleUIConstants.DEFAULT_ICON_20);
	}

	@Override
	public EClass getModelType() {
		return this.modelType;
	}

	@Override
	public Image getSmallImage() {
		return Activator.getDefault().getImageRegistry().get(ExtensionSampleUIConstants.DEFAULT_ICON_16);
	}

	@Override
	public ImageDescriptor getSmallImageDescriptor() {
		return Activator.getDefault().getImageDescriptor(ExtensionSampleUIConstants.DEFAULT_ICON_16);
	}

	@Override
	public String getTypeLabel() {
		return getModelType().getName();
	}

	@Override
	public EClass[] getClassArray() {
		return this.classArray;
	}

	@Override
	public void setModelType(EClass modelType) {
		this.modelType = modelType;
	}

}
