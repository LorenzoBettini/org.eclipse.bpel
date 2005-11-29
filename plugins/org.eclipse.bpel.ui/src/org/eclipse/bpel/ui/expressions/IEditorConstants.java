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
package org.eclipse.bpel.ui.expressions;

public interface IEditorConstants {
	
	/** expression types */
	public static final String ET_BOOLEAN = "boolean"; //$NON-NLS-1$
	public static final String ET_DATETIME = "deadline"; //$NON-NLS-1$
	public static final String ET_DURATION = "duration"; //$NON-NLS-1$
	// TODO: what should expression type of Assign From be??
	public static final String ET_ASSIGNFROM = "assignFrom"; //$NON-NLS-1$
	public static final String ET_VOID = "void"; //$NON-NLS-1$

	/** expression contexts */
	public static final String EC_JOIN = "joinCondition"; //$NON-NLS-1$
	public static final String EC_CASE = "case"; //$NON-NLS-1$
	public static final String EC_WHILE = "while"; //$NON-NLS-1$
	public static final String EC_TRANSITION = "linkTransition"; //$NON-NLS-1$
	public static final String EC_ASSIGNFROM = "assignFrom"; //$NON-NLS-1$
	public static final String EC_WAIT = "wait"; //$NON-NLS-1$
	public static final String EC_ONALARM = "onAlarm"; //$NON-NLS-1$
	public static final String EC_ONALARM_REPEATEVERY = "onAlarmRepeatEvery"; //$NON-NLS-1$
}
