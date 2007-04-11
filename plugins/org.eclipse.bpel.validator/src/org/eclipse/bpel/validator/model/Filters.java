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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;


/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Dec 6, 2006
 *
 */

@SuppressWarnings("nls")
public final class Filters implements IConstants {

	
	static String ANY_NAME = "*";
	
	/**
	 * NodeNameFilter filters based on the nodeName returned from INode.
	 * Filter nodes based on their name.
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Dec 7, 2006
	 *
	 */
	
	static public class NodeNameFilter implements IFilter<INode> {
		
		String[] fNodeNames = { ANY_NAME };
		
		/**
		 * 
		 * @param nodeName
		 */
		public NodeNameFilter ( String nodeName ) {
			fNodeNames[0] = nodeName;
		}
		
		/** 
		 * List of node names. 
		 * @param nodeNames
		 */
		public NodeNameFilter (String ... nodeNames) {
			fNodeNames = nodeNames;
			if (fNodeNames == null) {
				fNodeNames = new String[]{};
			} else if (nodeNames.length > 1) {
				Arrays.sort(fNodeNames);
			}
		}
		/** (non-Javadoc)
		 * @param node 
		 * @return true to select, false to not.
		 * @see org.eclipse.bpel.validator.model.IFilter#select(java.lang.Object)
		 */
		public boolean select(INode node) {
			
			String nn = node.nodeName();
			 
			if (fNodeNames.length == 1) {
				if (fNodeNames[0].equals(ANY_NAME)) {
					return true;
				}
				return fNodeNames[0].equals(nn);
			}
			return ( Arrays.binarySearch(fNodeNames, nn ) >= 0) ;
		}
		
		/**
		 * Return a toString of this filter
		 * @return a to string pretty print of this filter 
		 */
		
		@Override
		public String toString() {
			return asString ( fNodeNames );
		}
	}

	/**
	 * Filter that filters based on attribute name and its value.
	 * For example, select part where part "name" has some value.
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Dec 7, 2006
	 *
	 */
	
	static public class NodeAttributeValueFilter implements IFilter<INode> {
		
		String fValue;
		String fAttributeName;
		
		/**
		 * Constructor for the name filter.
		 * @param attrName attribute name
		 * @param attrValue attribute value
		 */
		
		public NodeAttributeValueFilter (String attrName, String attrValue) {
			fAttributeName = attrName;
			fValue = attrValue;			
		}
		
		/** (non-Javadoc)
		 * @param node 
		 * @return true if the node is to be selected, false otherwise.
		 * @see org.eclipse.bpel.validator.model.IFilter#select(java.lang.Object)
		 */
		public boolean select (INode node) {
			return fValue.equals ( node.getAttribute(fAttributeName ) );
		}		
	}
	
	/**
	 * Simple value filter 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Jan 26, 2007
	 *
	 */
	static public class ValueFilter implements IFilter<String> {

		String [] fList = { };
		
		/**
		 * Create a filer for the values indicated.
		 * 
		 * @param values
		 */
		
		public ValueFilter (String ... values) {
			fList = values;
			Arrays.sort(fList);
		}
		
		

		/** (non-Javadoc)
		 * @param node the node (name)
		 * @return whether node passes the filter.
		 * @see org.eclipse.bpel.validator.model.IFilter#select(java.lang.Object)
		 */
		public boolean select(String node) {			
			return ( Arrays.binarySearch(fList, node) >= 0) ;
		}
		
		/**
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return asString ( fList );
		}
	}
	
	/**
	 * Create a readable values list.
	 * 
	 * @param vals the values
	 * @return the possible values passed as "readable" string.
	 */
	
	static String asString ( String vals[] ) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < vals.length; i++) {				
			sb.append("\"").append(vals[i]).append("\"");
			if (i+1 < vals.length) {
				if (i+2 < vals.length) {
					sb.append(", ");
				} else {
					sb.append(" or ");
				}
			} 
		}				
		return sb.toString();
	}
	
	
	/** Scope or process filter. */
	static public final IFilter<INode> SCOPE_OR_PROCESS = new NodeNameFilter ( new String[]{ ND_SCOPE, ND_PROCESS } );

	/** Scope selector */
	public static final IFilter<INode> SCOPE = new NodeNameFilter( ND_SCOPE ) ;
	
	/** Process selector/filter */
	public static final IFilter<INode> PROCESS = new NodeNameFilter ( ND_PROCESS );

	
	/** Flow */
	public static final IFilter<INode> FLOW = new NodeNameFilter( ND_FLOW );
	
	/** Boolean filter values */
	static public final IFilter<String> BOOLEAN_FILTER = new ValueFilter ( BOOLEAN_VALUES );
	
	/** Initiate filter values */
	public static final IFilter<String> INITIATE_FILTER = new ValueFilter (INITIATE_VALUES);

	/** End point values */
	public static final IFilter<String> ENDPOINT_FILTER = new ValueFilter (ENDPOINT_VALUES);

	/** Correlation pattern values */
	public static final IFilter<String> PATTERN_FILTER = new ValueFilter ( PATTERN_VALUES );
	
	/** Repeatable nodes */
	public static final IFilter<INode> REPEATABLE_CONSTRUCT = new NodeNameFilter ( REPEATABLE_NODES );
	
	/** Event handler boundary */
	public static final IFilter<INode> FAULT_HANDLER_BOUNDARY = new NodeNameFilter ( FAULT_HANDLER_BOUNDARY_NODES );
	
	/** FCT-Handlers */
	public static final IFilter<INode> FCT_HANDLER = new NodeNameFilter ( FCT_HANDLERS );
	
	/** The BPEL activities filter */
	static public final IFilter<INode> ACTIVITIES = new NodeNameFilter( BPEL_ACTIVITIES );

	/** Activity containers filter */
	static public final IFilter<INode> ACTIVITY_CONTAINER = new NodeNameFilter( BPEL_ACTIVITIES_CONTAINERS );

	
	/** The Any filter */
	public static final IFilter<INode> ANY = new NodeNameFilter(ANY_NAME);
	
	/** The empty filter */
	public static final IFilter<INode> EMPTY = new NodeNameFilter();
	
	/** An NC_NAME Filter */
	public static final IFilter<String> NC_NAME = new IFilter<String> () {

		public boolean select (String name) {
			// TODO: incomplete
			return name.indexOf(' ') < 0 && name.indexOf(':') < 0;
		}
		
		@Override
		public String toString() {
			return "Valid-NCName";
		}
	};



	
	
	/**
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Jan 18, 2007
	 *
	 */
	static public class QNameFilter implements IFilter<QName> {

		final List<QName> fList = new LinkedList<QName>();			
				
		/**
		 * Return the list.
		 * @return the list of QNames
		 */
		
		public List<QName> list () {
			return fList;
		}
		/** (non-Javadoc)
		 * @param node the node
		 * @return whether the filter selects the node
		 * @see org.eclipse.bpel.validator.model.IFilter#select(java.lang.Object)
		 */
		
		public boolean select (QName node) {
			return fList.contains(node);
		}
		
	}
	
	/**
	 * Standard faults filter.
	 */
	
	static public final QNameFilter STANDARD_FAULTS = new QNameFilter ();


	

	
	
	
	
	static {
		for(String fault : BPEL_STANDARD_FAULTS) {			
			STANDARD_FAULTS.list().add (new QName(XMLNS_BPEL,fault) );
			STANDARD_FAULTS.list().add (new QName(XMLNS_BPEL20_OLD, fault) );
		}
	};

	
}
