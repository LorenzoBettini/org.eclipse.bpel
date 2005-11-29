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
package org.eclipse.bpel.ui.figures;

/**
 * This is a hack to get the vertical connection lines perfectly
 * straight. Relies on assumption
 * that vertical lines are sometimes crooked by 1 pixel depending
 * on the magnification.
 */
public class ConnectionXCoordHistory {
	private static ConnectionXCoordHistory instance;

	// x-coord of 1st anchor in aligned vertical parts
	private static int firstX = 0;	
	
	private ConnectionXCoordHistory(){}		
	
	public static ConnectionXCoordHistory getInstance() {
		if (instance == null)  instance = new ConnectionXCoordHistory();
		return instance;
	}	
	
	public int adjustX(int x){
		if (firstX ==0 ){
			firstX = x;
			return x;			
		}else if (Math.abs(firstX - x) > 2){
			/* assume any anchor point outside 2px of firstX means
			 * that the anchor is at the start of a new container
			 */
			firstX = x;
			return x;			
		}
		return firstX;
	}
}
