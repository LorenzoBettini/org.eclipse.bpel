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
package org.eclipse.bpel.model.util;

public class BPELConstants {
	/**
	 * The BPEL namespace.
	 */
	public static final String NAMESPACE_2004 = "http://schemas.xmlsoap.org/ws/2004/03/business-process/";

	/**
	 * The current namespace.
	 */
	public static final String NAMESPACE = NAMESPACE_2004;

    /**
     * The preferred namespace prefix.
     */
    public static final String PREFIX = "bpws";
    
    /**
     * Standard faults defined in the BPEL specification.
     */
	public static final String[] standardFaults = {
		"selectionFailure", "conflictingReceive", "conflictingRequest",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		"mismatchedAssignmentFailure", "joinFailure", "uninitializedVariable", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		"repeatedCompensation",  "invalidReply", "missingReply", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		"missingRequest", "subLanguageExecutionFault", "unsupportedReference", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		"invalidVariables", "uninitializedPartnerRole", "scopeInitializationFailure", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	};
    
}
