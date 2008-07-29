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

public class ProcessingInstructionNodeStep extends Step {
	
	String fName; 
	public ProcessingInstructionNodeStep (int axis, String name) {
		super(axis);
		fName = name;
		
	}
	
	public String getName () {
		return fName;
	}	
	
	public String asString () {
		StringBuilder sb = new StringBuilder ();
		sb.append("processing-instruction(");
		if (fName != null) {
			sb.append(fName);
		}
		sb.append(")");
		return sb.toString();
	}
	
	public String asText() {
		return asString();
	}
}
