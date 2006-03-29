/*******************************************************************************
 * Copyright (c) 2006 Oracle Corporation and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle Corporation
 *******************************************************************************/


package org.eclipse.bpel.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.osgi.framework.Bundle;

/**
 * You can think of BPEL templates as a 1 dimensional list of stencils that are used
 * to create an initial BPEL process.
 * <p>
 * Each template for a process may contain just 1 resource - just the template for
 * the BPEL process itself. But it may also contain other resources which are useful
 * in creating that particular process from that particular template. 
 * Simply put, a template may have 1-N template resources that need to be created
 * as a result of creating what appears to be a single process file.
 *  
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 */

public class Templates {

	/** this file defines the properties for a particular template */
	static final String TEMPLATE_PROPERTIES = "template.properties";  //$NON-NLS-1$
	
	/** location within the bundle where we look for templates */	
	static final String TEMPLATE_LOCATION   = "/templates/";		//$NON-NLS-2$
	
	/** default template file encoding, for a given set of template resources */
	static final String DEFAULT_ENCODING    = "UTF-8";		//$NON-NLS-3$
	
	/** the main bpel file has this extension */
	static final String BPEL_FILE_EXTENSION = ".bpel";	//$NON-NLS-4$
	
	/** Entries which are directories of the bundle */
	static final String BUNDLE_DIRECTORY = "/"; //$NON-NLS-5$
		
	/** Key or property under which the name of the template is present */
	static final String PROPERTY_NAME = "name";	//$NON-NLS-5$
	
	/** Key or property under which the encoding information for the template resources is present */
	static final String PROPERTY_ENCODING = "encoding"; //$NON-NLS-6$

	/** Key or property under whic the description of the template is present */
	static final String PROPERTY_DESCRIPTION = "description"; //$NON-NLS-7$
		
	/** avoid empty string */
	static final String EMPTY = ""; //$NON-NLS-8$
	
	/** Templates contribute namespaces to the new file wizard */
	Set mNamespaceNames = new TreeSet();
		
	/** Templates indexed by name, sorted by name, according to the natural ordering */
	Map mTemplateByName = new TreeMap();
	
	
	/**
	 * Initialize the template information from the bundle passed.
	 * This is typically the bundle of the plugin.
	 *
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)	
	 * @param bundle the bundle where the template information ought to be looked for
	 */
	
	public void initializeFrom  ( Bundle bundle )
	{
		Enumeration list = bundle.getEntryPaths( TEMPLATE_LOCATION );
		if (list == null) {
			return ;
		}
		// got some elements, look for "template.properties"
		
		int count = 0;
		
		while (list.hasMoreElements()) {
			String nextRoot = (String) list.nextElement();
			if ( nextRoot.endsWith(BUNDLE_DIRECTORY) == false ) {
				continue;
			}
						
			String nextEntry = nextRoot + TEMPLATE_PROPERTIES;
			// found another template
			
			URL nextURL = bundle.getEntry(nextEntry);
			if (nextURL == null) {
				// no such thing
				continue;
			}
			
			// looks like we have properties
			count += 1;
			
			Properties props = new Properties ();
			InputStream is = null;
			
			try {
				is = nextURL.openStream();
				props.load( is );
			} catch (IOException e) {
				BPELUIPlugin.log(e);
				
				
				// skip to the next entry
				continue;
				
			} finally {
				try { is.close() ; } catch (Throwable t) {} 
			}
			
			
			String name = props.getProperty(PROPERTY_NAME);
			
			// No name, na game.
			if (name == null) {
				continue ;
			}
			
			String enc  = props.getProperty(PROPERTY_ENCODING,DEFAULT_ENCODING);
			String desc = props.getProperty(PROPERTY_DESCRIPTION,EMPTY);
			
		
			// add any namespaces we are supplying ...
			mNamespaceNames.addAll( findProperties (props,"namespace.{0}") );
			
			
			ProcessTemplate template = new ProcessTemplate();
			template.setName ( name );
			template.setDescription ( desc );			
			mTemplateByName.put ( name, template );
			
			
			
			int hole = 3;
			for(int i=0; hole >= 0; i++) {
				
				Object args[] = new Object[] { new Integer(i) } ;			
				String key = MessageFormat.format("resource.{0}",args);  //$NON-NLS-10$
				String resourceName = props.getProperty(key);
				if (resourceName == null) {
					hole--;
					continue;
				}
				hole = 3;
				
				key = MessageFormat.format("resource.{0}.name", args);
				String nameTemplate = props.getProperty(key);
				
				String entryLoc = nextRoot + resourceName;
				
				TemplateResource resource = new TemplateResource() ;
				resource.setName ( resourceName );						
				resource.setLocation ( entryLoc );
				resource.setNameTemplate( nameTemplate );
				resource.setContent ( slurpContent ( bundle.getEntry(entryLoc), enc ) ) ;
				
				// add the resource which makes up this "template" 
				template.getResources().add ( resource );
				
			}
			Iterator ii = findProperties(props,"resource.{0}").iterator();
			
			while (ii.hasNext()) {
				String resourceName = (String) ii.next();
				
				String entryLoc = nextRoot + resourceName;
				
				TemplateResource resource = new TemplateResource() ;
				resource.setName ( resourceName );						
				resource.setLocation ( entryLoc );
				resource.setContent ( slurpContent ( bundle.getEntry(entryLoc), enc ) ) ;
				
				// add the resource which makes up this "template" 
				template.getResources().add ( resource );									
			}
		}
		
	}
	
	
	/**
	 * Slurp the resource into memory and return as a String. If an exception
	 * occurs, it is logged, and the return value is empty string.
	 * 
	 * @param loc the location from which we should slurp ...
	 * @param enc the encoding to use
	 * @return the text 
	 */
	
	String slurpContent ( URL loc, String enc) {
		
		if (loc == null) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer ( 2 * 1048 );
		char[] buf = new char[ 256 ];				
		InputStreamReader isr = null;
		
		try {
			isr = new InputStreamReader ( loc.openStream(), enc);
			
			do {
				int cnt = isr.read(buf);
				if (cnt < 0) {
					break;
				}
				sb.append( buf, 0, cnt );
			} while (true);
			
		} catch (Exception ex) {
			BPELUIPlugin.log(ex);
		} finally {
			try {isr.close(); } catch (Throwable t) {}
		}
		
		
		return sb.toString();		
	}
	
	
	
	
	List findProperties ( Properties props, String pattern ) {
		
		LinkedList list = new LinkedList();
		int hole = 3;
		
		for(int i=0; hole >= 0; i++) {
			
			String key = MessageFormat.format(pattern, new Object[] { new Integer(i) } );
			String val = props.getProperty(key,null);
			if (val != null) {
				list.addLast ( val );
				hole = 3;
			} else {
				hole--;
			}
		}
		
		return list;
	}
	
	
	/** 
	 * Return the template definition (which includes other resources that
	 * may be present) to the caller. 
	 * 
	 * @param name name of the template
	 * @return the template definition, including template resources
	 */
	
	public ProcessTemplate getTemplateByName ( String name )
	{
		return (ProcessTemplate) mTemplateByName.get(name);
	}
	
	
	/** 
	 * Return the namespaces contributed by the templates.	
	 * @return
	 */
	
	public String[] getNamespaceNames ()
	{
		String[] names = new String[ mNamespaceNames.size() ];
		mNamespaceNames.toArray( names );
		return names;
	}
	
	
	
	/**
	 * Return the template names that have been discovered.
	 * 
	 * @return Return the template names.
	 */
	
	public String [] getTemplateNames ()
	{
		String[] names = new String[ mTemplateByName.size() ];		
		mTemplateByName.keySet().toArray( names );
		return names;
	}
	
	
	
	/**
	 * A given "BPEL Process" Template has a name, description, and
	 * a list of resources (file templates) that will be used to create the inital
	 * process source file.
	 * 
	 * @author Michal Chmielewski (michal.chmielewski@oracle.com)	
	 *
	 */
	
	static public class ProcessTemplate {
		
		/** Name of the process template */
		String mName;
		
		/** Description of this process template */
		String mDescription ;
		
		/** list of resources that this template has (1-N) */		
		List mResources = new LinkedList();

		/**
		 * @param desc
		 */
		public void setDescription(String desc) {
			mDescription = desc;		
		}


		public String getName() {
			return mName;
		}

		public void setName(String name) {
			mName = name;
		}

		public List getResources() {
			return mResources;
		}

		/**
		 * @return
		 */
		public String getDescription() {
			return mDescription;
		}	
		
		
	}
	
	/**
	 * A template resource is the actual file which will be used to create 
	 * the source file or other auxiliary files for the BPEL process source.
	 * 
	 * @author Michal Chmielewski, (michal.chmielewski@oracle.com)
	 * 
	 */
	
	static public class TemplateResource {
		
		/** Name of the resource (from the bundle) */
		String mName ;
		
		/** Location of the resource (from the bundle) */
		String mLocation;
		
		/** The content of the resource (slurped from the bundle) */
		String mContent;

		/** The name template, that is, the file name template if depended on process name */
		String mNameTemplate;
				
		
		public String getContent() {
			return mContent;
		}
		
		public void setContent(String content) {
			mContent = content;
		}
		
		public String getLocation() {
			return mLocation;
		}
		
		public void setLocation(String location) {
			mLocation = location;
		}
		
		public String getName() {
			return mName;
		}
		
		public void setName(String name) {
			mName = name;
		}
		
		public void setNameTemplate ( String name) {
			mNameTemplate = name;
		}

		
		/**
		 * Process the content of the template and replace anything within
		 * ${...} by the corresponding key prent in the map passed.
		 * 
		 * @param args the keys that will be replaced in the content 
		 * @return the replaced content
		 */
		
		public String process ( Map args ) {
			
			return process ( mContent, args );
		}
		
		
		/**
		 * Process the content of the template and replace anything within
		 * ${...} by the corresponding key prent in the map passed.
		 * 
		 * @param args the keys that will be replaced in the content 
		 * @return the replaced content
		 */
		
		String process (String src, Map args )
		{
			StringBuffer sb = new StringBuffer ( src.length() );
			// empty content, empty result
			if (src == null) {
				return sb.toString();
			}
			int cursor = 0;			
			do {
				int openReplace  = src.indexOf("${", cursor);
				if (openReplace < 0) {
					break;
				}
				sb.append( src.substring(cursor,openReplace));
				cursor = openReplace + 2;
				int closeReplace = src.indexOf("}",cursor);
				if (closeReplace < 0) {					
					return sb.toString();
				}
				
				String expr = src.substring(cursor, closeReplace).trim() ;
				sb.append( args.get(expr) );				
				cursor = closeReplace + 1;
			} while (true);
			
			// the last segment
			sb.append( src.substring(cursor) );			
			return sb.toString();
		}
		
		/**
		 * Return the name of the resource 
		 * 
		 * @param args map of arguments that are used in replacing
		 * @return the name of the resource, after token replacement.
		 */
		
		public String getName (Map args) {
			
			if (mNameTemplate == null) {
				return mName;
			}			
			return process (mNameTemplate, args);			
		}
		
		
		/**
		 * Ask if this TemplateResource is specifically a BPEL source file. 
		 * 
		 * @return Answer true if the extension is .bpel 
		 */
		
		public boolean isProcess() 
		{
			return mName.endsWith( BPEL_FILE_EXTENSION );
		}		
	}
		
}
