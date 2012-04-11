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
package org.eclipse.bpel.extensionsample.model.util;

import org.eclipse.bpel.extensionsample.model.ModelPackage;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.adapters.INamespaceMap;
import org.eclipse.bpel.model.util.BPELUtils;

public class ExtensionSampleUtils {

	/**
	 * Adds the extensionsample namespace to the given process
	 * @param process
	 * @return the preferred namespace prefix
	 */
	public static String addNamespace(Process process) {
		String nsPrefix = ModelPackage.eINSTANCE.getNsPrefix();
		String nsURI = ModelPackage.eINSTANCE.getNsURI();
		INamespaceMap<String, String> nsMap = BPELUtils.getNamespaceMap(process);
		nsMap.put(nsPrefix, nsURI);
		return nsPrefix;
	}

}
