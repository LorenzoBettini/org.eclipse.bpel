/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.bpel.xpath10;

public class NameStep extends Step {
	
	String fPrefix ;
	String fLocalName;
	boolean matchesAnyName = false;
	
	public NameStep ( int axis, String prefix, String name ) {
		super(axis);
		fPrefix = prefix;
		fLocalName = name;
		matchesAnyName = "*".equals(fLocalName);
	}
	
	public String getPrefix () {
		return fPrefix;
	}
	public String getLocalName() {
		return fLocalName;
	}

	public boolean isMatchesAnyName() {
        return matchesAnyName;
    }

    
	protected String asString () {
		return asText();
	}
	
	protected String asText() {
		StringBuilder buf = new StringBuilder(64);

		if (getPrefix() != null && getPrefix().length() > 0) {
			buf.append(getPrefix()).append(':');
		}
		buf.append(getLocalName());
		return buf.toString();
	}
}

