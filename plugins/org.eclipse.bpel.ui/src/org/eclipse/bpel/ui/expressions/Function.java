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
package org.eclipse.bpel.ui.expressions;

import java.util.ArrayList;

public class Function {
	protected String theName;
	protected String theNSUri;
	protected String theReturnType;
	protected String theHelp;
	protected String theComments;
	protected ArrayList theArgs;
	
	public Function() {
		theName = null;
		theNSUri = null;
		theReturnType = null;
		theHelp = null;
		theComments = null;
		theArgs = null;
	}
	
	public Function(String name, String nsUri, String returnType, 
			String help, String comments, ArrayList args) {
		theName = name;
		theNSUri = nsUri;
		theReturnType = returnType;
		theComments = comments;
		theHelp = help;
		theArgs = args;
	}
	
	public String getName() { return theName; }
	public String getNamespaceURI() { return theNSUri; };
	public String getReturnType() { return theReturnType; }
	public String getHelp() { return theHelp; }
	public String getComments() { return theComments; }
	public ArrayList getArgs() { return theArgs; }
	
	/*
	 * Returns string to be inserted into the text editor.
	 */
	public String getReplacementString(INamespaceResolver resolve) {
		String display = "";
		// add namespace if found
		if ((theNSUri != null) && (theNSUri.length()>0)) {
			String nsprefix = resolve.resolvePrefix(theNSUri);
			if ((nsprefix != null) && (nsprefix.length() > 0))
				display += nsprefix + ":";
		}
		
		display += theName + "("; //$NON-NLS-1$
		
		// add argument types to make it look nice
		if (theArgs != null) {
			for (int i=0; i<theArgs.size(); i++) {			
				FunctionArgument arg = (FunctionArgument)theArgs.get(i);
				if (i > 0) {
					display += ", "; //$NON-NLS-1$
				}
				
				display += "${" + arg.getName() ;  //$NON-NLS-1$
				display += "}"; //$NON-NLS-1$
				if (arg.getOptional() != 0) {
					display += arg.getOptional();
				}
			}
		}
		
		display += ")"; //$NON-NLS-1$
		
		return (display);
	}
	
	/*
	 * Returns string that is used in the content assist popup window.
	 */
	public String getDisplayString() {
		String display = "";
		
		display += theName + "(";
		
		// populate with argument names and types
		if (theArgs != null) {
			for (int i=0; i<theArgs.size(); i++) {			
				FunctionArgument arg = (FunctionArgument)theArgs.get(i);
				
				if (i > 0)
					display += ", ";

				display += arg.getType() + " " + arg.getName();
				
				if (arg.getOptional() != 0)
					display += arg.getOptional();
			}
		}
		display += ")   " + theReturnType;
		
		return (display);		
	}
}
