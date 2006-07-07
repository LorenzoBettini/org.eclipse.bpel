/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation
 *******************************************************************************/
package org.eclipse.bpel.ui.details.providers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.util.XSDResourceImpl;

/**
 * Content provider for XSDSchema. 
 * 
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 */

public class XSDSchemaContentProvider extends AbstractContentProvider  {

	static private final String KIND [] = { "xsd" }; //$NON-NLS-1$
	
	static private final String SLASH = "/"; //$NON-NLS-1$
	
	/**
	 * Append the schemas that are present in the object passed to the list
	 * indicated.   
	 * 
	 * @param input an object that has or is schema definitions.
	 * @param list the list where the schemas are put.
	 */
	
	ResourceSet fResourceSet;
	 
	public XSDSchemaContentProvider ( ResourceSet set ) {

		fResourceSet = set;
	}
	
	
	protected void appendElements ( Object input, List list) throws CoreException {
		
		if (input == null) {
			return ;
		}
		
		if (input instanceof IContainer) {			
			findCandidateSchemas((IContainer) input,list);
			return;
		}
		
		if (input instanceof XSDSchema) {
			list.add(input);			
			return;
		}

		Object[] arr = null;
		
		if (input.getClass().isArray()) {			
			arr = (Object[]) input;
		} else if (input instanceof List) {
			arr = ((List)input).toArray();
		}
		
		if (arr == null) {
			return;
		}
		
		for(int i=0; i < arr.length; i++) {
			appendElements ( arr[i], list );
		}					
	}
	
	
	public Object[] getElements(Object input)  {
		LinkedList list = new LinkedList();
		try {
			appendElements ( input, list );
		} catch (CoreException e) {
			BPELUIPlugin.log(e);
		}				
		return list.isEmpty()? EMPTY_ARRAY : list.toArray( );
	}

			
	
	
	List findCandidateSchemas ( IContainer container, List list) throws CoreException {
		
		if (list == null) {
			list = new LinkedList();
		}
		
		Iterator i = findCandidates( container ).iterator();
		while (i.hasNext()) {
			IResource r = (IResource) i.next();
			try {
				list.add ( loadSchema (r));
			} catch (Exception ex) {
				BPELUIPlugin.log(ex);
			}
		}
		return list;
	}
	

	
	/**
	 * Load the schema from the indicated. This may fail, but the provider will 
	 * throw away any failed schema loads and only return the ones that succeed
	 * in being loaded.
	 *  
	 * @param r
	 * @return
	 */
	
	XSDSchema loadSchema ( IResource r ) 
    {
    	// Format: /Project/path    	
    	String uri = SLASH + r.getProject().getName() + SLASH + r.getProjectRelativePath();
        URI locationURI = URI.createPlatformResourceURI( uri );       
            
        XSDResourceImpl resource = (XSDResourceImpl) fResourceSet.getResource(locationURI, true);        
        
        return resource.getSchema();        
    }

	
	/**
	 * Find candidate schemas in the container passed. The container is searched
	 * up to a certain depth. If schemas are found in that container then the next
	 * level is searched. Otherwise the search at that level stops.
	 * 
	 * @param container the container to search (project, folder, workspace root)
	 * @return the list of schemas found
	 * @throws CoreException
	 */
	List findCandidates ( IContainer container ) throws CoreException {
		
		LinkedList list = new LinkedList();
		
		// Workspace root contains Projects ...
		if (container instanceof IWorkspaceRoot) {
			return findCandidates( container, list, KIND, 3 );
		}		
		return findCandidates( container, list, KIND, 2 );		
	}
	
	
	
	
	List findCandidates ( IContainer container, List list, String [] kind , int depth ) throws CoreException {
		
		if (depth <= 0 || container.isAccessible() == false) {
			return list;
		}
		
		depth -= 1;
		
		boolean bFound = false;
		
		IResource [] rlist = container.members();
			
		for(int i=0; i< rlist.length; i++) {
			
			IResource r = rlist[i];
			if ( r.getType() != IResource.FILE) {
				continue;
			}			
			String name = r.getFileExtension();
			for(int j=0; j < kind.length; j++) {
				if (name != null && name.equalsIgnoreCase(kind[j])) {
					bFound = true;
					list.add( r );
					break;
				}
			}				
		}
		
		// if found some candidates at this level, look only in the next level (and
		// so on). Eventually, not all levels are searched, just the ones that contain
		// xsd resources and their descendant folders
		
		if (bFound) {
			depth = 1;
		}
			
		for(int i=0; i < rlist.length; i++) {
			IResource r = rlist[i];
			if (r instanceof IContainer) {
				findCandidates((IContainer) r, list, kind, depth);
			}
		}
		
		return list;
	}
	
	
}
