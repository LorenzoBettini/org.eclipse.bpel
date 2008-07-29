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

public class Expr  {
	
	String fText ;
	int fStartPosition = -1;
	int fEndPosition = -1;
	
	boolean fWrapWithParenthesis = false;
	
	public Expr (String text) {
		fText = text;
	}
	
	public final String getText() {
		StringBuilder sb = new StringBuilder();
		if (fWrapWithParenthesis) {
			sb.append("(");
		}
		sb.append(asText());
		if (fWrapWithParenthesis) {
			sb.append(")");
		}
		return sb.toString();
	};
	
	public String toString () {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(getClass().getSimpleName()).append(",").append(asString()).append("}") ;
		return sb.toString();
	}

	protected String asString () {
		return getText();
	}
	
	protected String asText () {
		return fText;
	}
	
	public boolean isWrapParen () {
		return fWrapWithParenthesis;
	}
	public void setWrapParen ( boolean wrap ) {
		fWrapWithParenthesis = wrap;
	}
	
	public int getEndPosition() {
		return fEndPosition;
	}

	public int getPosition() {
		return fStartPosition;
	}
	
	public void setPosition (int start, int end) {
		fStartPosition = start;		
		fEndPosition = end;
	}
}
