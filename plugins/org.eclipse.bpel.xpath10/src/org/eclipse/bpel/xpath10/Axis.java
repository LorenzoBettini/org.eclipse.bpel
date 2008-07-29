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

public class Axis {
	static final public int INVALID_AXIS       = 0;
	static final public int CHILD              = 1;
	static final public int DESCENDANT         = 2;
	static final public int PARENT             = 3;
	static final public int ANCESTOR           = 4;
	static final public int FOLLOWING_SIBLING  = 5;
	static final public int PRECEDING_SIBLING  = 6;
	static final public int FOLLOWING          = 7;
	static final public int PRECEDING          = 8;
	static final public int ATTRIBUTE          = 9;
	static final public int NAMESPACE          = 10;
	static final public int SELF               = 11;
	static final public int DESCENDANT_OR_SELF = 12; 
	static final public int ANCESTOR_OR_SELF   = 13;
	
	static final private String [] NAMES = { 
		
		"unknown-axis-name",   /** at [0] we have null */
		
		"child",
		"descendant",
		"parent",
		"ancestor",
		"following-sibling",
		"preceding-sibling",
		"following",
		"preceding",
		"attribute",
		"namespace",
		"self",
		"descendant-or-self",
		"ancestor-or-self"
	};
	
	static public String getName (int axis) {
		if (axis < 1 || axis >= NAMES.length) {
			return NAMES[0];
		}
		return NAMES[axis];
	}
	
	static public int getAxis ( String axisName ){
		for(int i=1; i<NAMES.length; i++) {
			if (NAMES[i].equals(axisName)) {
				return i;
			}
		}
		return -1;
	}
	
}
