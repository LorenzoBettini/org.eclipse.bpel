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
package org.jaxen.expr;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Feb 13, 2007
 *
 */
public interface Position {

	/** 
	 * @return Return the position (start offset) from the beginning of buffer.
	 */
	int getPosition();
	
	/**
	 * 
	 * @return return the end position (end offset) from the beginning of buffer.
	 */
	int getEndPosition ();
	
	/**
	 * Set the position from the beginning of the buffer.
	 * @param pos position to set.
	 * @param endPos the end position to set
	 */
	
	void setPosition( int pos, int endPos );	
}
