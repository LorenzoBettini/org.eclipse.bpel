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

import java.util.ArrayList;
import java.util.List;

public class Step {

	int fAxis = 0;
	List<Predicate> fList = new ArrayList<Predicate>();
	
	int fStartPosition = -1;
	int fEndPosition = -1;
	
	public Step (int axis) {
		fAxis = axis;			
	}
	
	
	public String getText()
    {
        StringBuilder buf = new StringBuilder();
        buf.append(getAxisName());
        buf.append("::");
        buf.append(asText());
        
        for(Predicate p : fList) {
        	buf.append(p.getText());
        }
        return buf.toString();
    }
	
	protected String asText () {
		return "?";
	}
	
	public int getAxis () {
		return fAxis;
	}
	
	public void setAxis (int axis) {
		fAxis = axis;
	}
	
	public void addPredicate(Predicate predicate)
	{
		if (predicate != null) {
			fList.add(predicate);
		}
	}
	
    public List<Predicate> getPredicates() {
    	return fList;
    }
    
    public String getAxisName ()
    {
    	return Axis.getName(fAxis);
    }
    
    public String toString () {
    	StringBuilder sb = new StringBuilder();
    	sb.append("{").append(getClass().getSimpleName()).append(",").append(getAxisName()).append(",");
    	sb.append(asString());
    	sb.append(",[");
    	for(Predicate p : fList) {
    		sb.append(p);
    		sb.append(",");
    	}
    	sb.append("]");
    	sb.append("}");
    	return sb.toString();
    }
    
    protected String asString () {
    	return null;
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
