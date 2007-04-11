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

import javax.xml.namespace.QName;

/**
 * 
 * This interface implements meta information about various function
 * present in the expressions in the BPEL source.
 * <p>
 * This information is typically supplied by the model.
 * <p>
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Sep 29, 2006
 */
public interface IFunctionMeta {
	/**
	 * Return the name of the function.
	 * 
	 * @return name of function
	 */	
	
	 public String getName();
		 
	 /**
	  * Return function comment.
	  * 
	  * @return the comment of the function.
	  */
	 public String getComment();
	 
	 /**
	  * Return the class name which implements this function.
	  * 
	  * @return the class name that implements it 
	  */
	  public String getClassName();

	  /**
	   * Get the namespace URI of this function.
	   * 
	   * @return the namespace URI that this function lives under
	   */
	  
	   public String getNamespaceUri( );

	   /**
	    * Get the namespace prefix of this function.
	    * 
	    * @return the namespace prefix
	    */
	    public String getNamespacePrefix( );
	    
	    /**
	     * Answer if the function is depracated.
	     * @return true if depracated, false if not.
	     */
	    
	    public boolean isDeprecated( );

	    /**
	     * Return the QName of this function.
	     * @return the QName of the function
	     */
	    
	    public QName getQName();
	    
	    /**
	     * Return the deprecation comment of this function.
	     * Why it should not be used anymore.
	     * 
	     * @return the depracated comment 
	     */
	    public  String getDeprecateComment( );

	    /**
	     * Return the arity of this function.
	     * 
	     * @return the arity of this function.
	     */
	    
	    public int getArity();
}
