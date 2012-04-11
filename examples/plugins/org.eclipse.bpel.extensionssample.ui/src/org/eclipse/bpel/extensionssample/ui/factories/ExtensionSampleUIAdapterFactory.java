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

import org.eclipse.bpel.extensionsample.model.util.ModelAdapterFactory;
import org.eclipse.bpel.extensionssample.ui.adapters.SampleSimpleActivityAdapter;
import org.eclipse.bpel.extensionssample.ui.adapters.SampleStructuredActivityAdapter;
import org.eclipse.emf.common.notify.Adapter;

public class ExtensionSampleUIAdapterFactory extends ModelAdapterFactory {

	// Bugzilla 324115
	private static ExtensionSampleUIAdapterFactory instance;
	private SampleSimpleActivityAdapter sampleSimpleActivityAdapter;
	private SampleStructuredActivityAdapter sampleStructuredActivityAdapter;
	private ExtensionSampleUIAdapterFactory() {
		super();
	}
	
	public static ExtensionSampleUIAdapterFactory getInstance() {
		if (instance == null) {
			instance = new ExtensionSampleUIAdapterFactory();
		}
		return instance;
	}
		
	@Override
	public Adapter createSampleSimpleActivityAdapter() {
		if (this.sampleSimpleActivityAdapter == null) {
			this.sampleSimpleActivityAdapter = new SampleSimpleActivityAdapter();
		}
		return this.sampleSimpleActivityAdapter;
	}
	
	@Override
	public Adapter createSampleStructuredActivityAdapter() {
		if (this.sampleStructuredActivityAdapter == null) {
			this.sampleStructuredActivityAdapter = new SampleStructuredActivityAdapter();
		}
		return this.sampleStructuredActivityAdapter;
	}
	
}
