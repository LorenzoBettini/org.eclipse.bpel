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
package org.eclipse.bpel.validator.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;


/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jan 5, 2007
 *
 */

public class RuleFactory  {
	
	/**
	 * Create a validator class.
	 * 
	 * @param qname the QName that we want the validator for.
	 * @return the validator class or potentially null
	 */
	
	static public Validator createValidator ( QName qname ) {
		Validator top = null;
		
		for (IFactory<Validator> factory: FACTORIES) {
			Validator next = factory.create(qname);
			if (next == null) {
				continue;
			}
			if (top == null) {
				top = next;
			} else {
				top.attach( next );
			}			
		}
		return top;
	}
	
	
	static List<IFactory<Validator>> FACTORIES = new ArrayList<IFactory<Validator>>(16);
			
	
	/**
	 * 
	 * @return the factories registered in the list.
	 */
	static public List<IFactory<Validator>> getFactories () {
		return FACTORIES;
	}
	
	
	
	/**
	 * Register the factory passed in the rules factories 
	 * 
	 * @param factory
	 */
	
	static public void registerFactory ( IFactory<Validator> factory ) {
		FACTORIES.add( factory );		
	}
}
