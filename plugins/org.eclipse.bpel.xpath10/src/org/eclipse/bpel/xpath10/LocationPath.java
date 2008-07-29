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
import java.util.Iterator;
import java.util.List;

public class LocationPath extends Expr {

	List<Step> fSteps = new ArrayList<Step>();
	boolean fAbsolute = false;
	
	public LocationPath ( boolean abs, Step ... steps) {
		super(null);
		fAbsolute = abs;
		for(Step s : steps) {
			addStep(s);			
		}		
	}
	
	public void addStep (Step s) {
		if (s != null) {
			fSteps.add(s);
		}
	}
	public void addFirstStep (Step s) {
		if (s != null) {
			fSteps.add(0, s);
		}
	}
	
	public List<Step> getSteps () {
		return fSteps;
	}
	
	public boolean isAbsolute() {
		return fAbsolute;
	}
	
	public void setIsAbsolute (boolean v) {
		fAbsolute = v;
	}
	
	protected String asText()
    {
        StringBuilder buf = new StringBuilder();
        if (isAbsolute()) {
        	buf.append("/");
        }
        Iterator<Step> stepIter = getSteps().iterator();
        while (stepIter.hasNext())
        {
            buf.append(stepIter.next().getText());
            if (stepIter.hasNext())
            {
                buf.append("/");
            }
        }
        return buf.toString();
    }
	
	public String toString()
    {
        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append(getClass().getSimpleName());
        buf.append(",").append(isAbsolute()).append(",");
        
        Iterator<Step> stepIter = getSteps().iterator();
        while (stepIter.hasNext())
        {
            buf.append( stepIter.next() );
            if (stepIter.hasNext())
            {
                buf.append("/");
            }
        }
        buf.append("}");
        return buf.toString();
    }

	public int getPosition () 
	{
		return fSteps.size() > 0 ? fSteps.get(0).getPosition() : -1;
	}
	
	public int getEndPosition () {
		int size = fSteps.size();
		return size > 0 ? fSteps.get(size-1).getEndPosition() : -1;
	}
}
