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

package org.eclipse.bpel.ui.proposal.providers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.bpel.ui.util.ListMap;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;

/**
 * 
 * A generic proposal provider for any model elements. This class takes a 
 *   <ul>
 *     <li> value provider,
 *     <li> a structured content provider, and 
 *     <li> a filter.
 *   </ul>
 *   
 * The value provider provides the value for the model element. Rather then passing a reference to the
 * model object in question, a value provider can provide a read only value that can change over time.
 * This is useful in the properties sections, where an input of a property section may change.
 * <p>
 * The structured content provider provides a list of content for that model object.
 * <p> 
 * The filter applies a selection criteria as to whether the proposal ought to be shown.
 * <p>
 * In addition, proposals may be added at the beginning or end of the list generated from the filtered content provider.
 * <p>
 *  
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jul 21, 2006
 * 
 *
 */

public class ModelContentProposalProvider implements
		IContentProposalProvider {

	static final IContentProposal [] NO_PROPOSALS = {} ;
	
	private ValueProvider valueProvider;

	private List fEndProposals = new LinkedList();
	private List fStartProposals = new LinkedList();
	
	private IStructuredContentProvider contentProvider;

	private IFilter filter;
	
	/** 
	 * A simple input provider to delay input providing when we don't have it
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
	 * @date Jul 27, 2006
	 */
	
	static public abstract class ValueProvider {
		public abstract Object value () ;
	}
	

	public ModelContentProposalProvider (ValueProvider valueProvider, IStructuredContentProvider contentProvider ) {
		this (valueProvider,contentProvider, null);
	}
	
	/**
	 * @param valueProvider the value provider for the model object.
	 * @param contentProvider the content provider to use
	 * @param filter to filter out proposal given by the content provider
	 */
	
	public ModelContentProposalProvider(ValueProvider valueProvider, IStructuredContentProvider contentProvider, IFilter filter) {
		super();
		this.valueProvider = valueProvider;
		this.contentProvider = contentProvider;
		this.filter = filter;
	}

	
	public void addProposalToStart ( IContentProposal proposal ) {
		fStartProposals.add ( proposal );
	}
	
	public void addProposalToEnd ( IContentProposal proposal ) {
		fEndProposals.add ( proposal );
	}
	
		
	/* (non-Javadoc)
	 * @see org.eclipse.jface.fieldassist.IContentProposalProvider#getProposals(java.lang.String, int)
	 */
	public IContentProposal[] getProposals (String contents, int position) {
				
		List all = new LinkedList();
		
		all.addAll ( fStartProposals );
				
		all.addAll ( (List) ListMap.Map ( 

				// provider gives us the right elements, which we then filter, and ... 
				doFilter(  contentProvider.getElements( valueProvider.value() ) ),

				// ... the visitor just adapts
				new ListMap.Visitor () {
					public Object visit (Object obj) {
						Object adapter = BPELUtil.adapt(obj, IContentProposal.class );
						return (adapter != null ? adapter : ListMap.IGNORE);
					}			
				}					
		)) ;

		all.addAll ( fEndProposals );
		
		return (IContentProposal[]) all.toArray( NO_PROPOSALS );
	}
	

	
	Object[] doFilter (Object [] elements) {
		
		if (filter == null || elements.length < 1) {
			return elements;
		}
		
		int size = elements.length;
		ArrayList out = new ArrayList(size);
		for (int i = 0; i < size; ++i) {
			Object element = elements[i];
			if (filter.select(element)) {
				out.add(element);
			}
		}
		return out.toArray();
	}
	
}

