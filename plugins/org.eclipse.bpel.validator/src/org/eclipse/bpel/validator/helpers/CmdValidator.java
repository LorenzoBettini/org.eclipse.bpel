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
package org.eclipse.bpel.validator.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.bpel.validator.model.ARule;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.IProblem;
import org.eclipse.bpel.validator.model.Problem;
import org.eclipse.bpel.validator.model.RuleFactory;
import org.eclipse.bpel.validator.model.Runner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jan 2, 2007
 *
 */

@SuppressWarnings("nls")

public class CmdValidator {
	
	static protected PrintStream OUT = System.out;
	
	static final protected IProblem[] EMPTY_RESULT = {};
	
	protected Runner fRunner = null;
	
	/**
	 * Create a brand new 
	 */
	@SuppressWarnings("unchecked")	
	public CmdValidator () {
		
		//
		// register the rule factories for the validator. This is done via
		// the org.eclipse.bpel.validator.factories extension point in the plug-in.
		
		RuleFactory.registerFactory( new org.eclipse.bpel.validator.rules.Factory());
		RuleFactory.registerFactory( new org.eclipse.bpel.validator.vprop.Factory());
		RuleFactory.registerFactory( new org.eclipse.bpel.validator.plt.Factory());
		RuleFactory.registerFactory( new org.eclipse.bpel.validator.wsdl.Factory());
		RuleFactory.registerFactory( new org.eclipse.bpel.validator.xpath.Factory());
		
	}
	
	/**
	 * Validate the given file.
	 * @param file
	 * @return the list of validation errors.
	 */
	
	public IProblem[] validate (File file) {
		//
		//Step 1. Read the BPEL process using the Model API.
		Element elm = null;
		
		try {
			
			LocationCapturingDOMParser parser = new LocationCapturingDOMParser();
			parser.parse( file.toString() );
			elm = parser.getDocument().getDocumentElement();
			
		} catch (Exception ex) {
			
			OUT.printf("Error: Cannot read BPEL Process in %1$s", file);
			ex.printStackTrace(OUT);
			return EMPTY_RESULT;
			
		}
		
		if (elm == null) {
			OUT.printf("Error: Cannot read BPEL Process in %1$s",file);	
			return EMPTY_RESULT;
		}
		// Step 2. Preparation for the validator.
				
		// Process as INode 		
		INode node = ModelQueryImpl.getModelQuery().adapt(elm, INode.class);
		
		// Step 3. Run it
		fRunner = new Runner ( ModelQueryImpl.getModelQuery(), node);
		return fRunner.run();		
	}
	
	/**
	 * Log to a print stream 
	 * @param problems
	 * @param runner 
	 * @param ps
	 */
	@SuppressWarnings("boxing")
	public void log ( IProblem[] problems, Runner runner, PrintStream ps ) {
		
		ps.printf("<problems count=\"%1$d\">\n", problems.length);

		// Which SA-analysis cases are covered ?
		if (runner != null) {
			
			Set<ARule> saChecks = new TreeSet<ARule>(new Comparator<ARule> ( ){
				public int compare(ARule o1, ARule o2) {
					return o1.sa() - o2.sa();
				} 
			});
			
			saChecks.addAll( runner.getSAChecks() );
			ps.printf(" <sa-cases>");			
			for(ARule a : saChecks) {
				ps.printf("%1$d,", a.sa() );				
			}
			ps.printf("0</sa-cases>\n");
		}

		// Step 5. Dump problems.
		for(IProblem problem : problems) {
			Map<String,Object> map = problem.getAttributes();
			ps.println(" <problem>");
			for(Map.Entry<String,Object> entry : map.entrySet()) {
				Object value = entry.getValue();
				if (value == null) {
					continue;
				}
				String v = value.toString();
				
				ps.printf("  <%1$s>%2$s</%1$s>\n",entry.getKey(), toSafeXML(v) );				
			}
			ps.println(" </problem>\n");
		}
		ps.println("</problems>");		
		// done.
	}
	

	protected String toSafeXML ( String value ) {		
		if (value.indexOf("&") >= 0) {
			value = value.replaceAll("\\&", "&amp;");
		}
		if (value.indexOf("<") >= 0) {
			value = value.replaceAll("\\<", "&lt;");
		}
		if (value.indexOf(">") >= 0) {
			value = value.replaceAll("\\>", "&gt;");
		}
		return value;
	}
	
	
	
	
	
	
	/** 
	 * Do the actual run
	 *  
	 * @param opt 
	 * @throws Exception 
	 */
	
	public void run (GetOpt opt) throws Exception {
		
		// process validation as before
		for(String a : opt.parameters() ) {			
			
			File aFile = new File(a);
			
			OUT.printf("Validating %1$s\n\n",aFile);
			
			IProblem problems[] = validate (aFile);
			
			log(problems,fRunner,OUT);
			
			File log = new File(aFile + ".log.xml");			
			OUT.printf("Writing to log %1$s\n\n", log );
			PrintStream ps = null;
			try {
				ps = new PrintStream( log );
				log(problems, fRunner, ps);
			} catch (FileNotFoundException e) {
				// 
			} finally {
				
				try { 
					if (ps != null) {
						ps.close();
					}
				} catch(Throwable t) {}

			}								
		}
	}
	
	
	/**
	 * Main entry point for the command line validator.
	 * @param argv
	 * @throws Exception 
	 */
	
	static public void main (String argv[]) throws Exception {
		
		
		CmdValidator builder = new CmdValidator();
		GetOpt opt = new GetOpt("-h",argv);		
		builder.run(opt);
	}	

	


	/**
	 * Reads the messages file into a problems array so that comparison of 
	 * results can take place.
	 * 
	 * @param aFile
	 * @return a list of problems.
	 * @throws Exception 
	 */
		
	
	static public IProblem[]  readMessages ( File aFile ) throws Exception {
		
		LocationCapturingDOMParser parser = new LocationCapturingDOMParser();
		parser.parse(aFile.toString());
		Document doc = parser.getDocument() ;
		
		Element elm = doc.getDocumentElement();
		
		if ("problems".equals (elm.getTagName()) == false) {
			return EMPTY_RESULT;
		}
		
		List<IProblem> list = new LinkedList<IProblem>();
		
		NodeList nl = elm.getElementsByTagName("problem");
		
		for(int i=0, j=nl.getLength(); i<j; i++) {
			Element e = (Element) nl.item(i);
			list.add( Element2Problem (e) );
		}
		
		return list.toArray(EMPTY_RESULT);
	}
	
	
	static Set<String> KEY_SET = null;
	
	
	static IProblem Element2Problem ( Element e ) {
		
		IProblem p = new Problem ();
		
		if (KEY_SET == null) {
			KEY_SET = keys(IProblem.class);
		}
		
		Node n = e.getFirstChild();
		while (n != null) {			
			if (n instanceof Element) {				
				Element ne = (Element) n;
				String key = ne.getTagName();
				if (KEY_SET.contains(key)) {
					p.setAttribute(key, ne.getTextContent());
				}
			}
			n = n.getNextSibling();
		}
			
		return p;
	}
	
	
	static Set<String> keys ( Class<?> klazz ) {
		
		Set<String> set = new HashSet<String>();
		
		for(Field f: klazz.getFields() ) {
			int modifies = f.getModifiers();
			String name = f.getName();
			if (name.equals ( name.toUpperCase() ) == false) {
				continue;
			}
			if ((modifies & Member.PUBLIC) != Member.PUBLIC ) {
				continue;
			}
			if (f.getType() != String.class) {
				continue;
			}
			
			try {
				String value = (String) f.get(null);				
				set.add( value );				
			} catch (Throwable t) {
				// should not happen.
			}			
		}
		
		return set;
	}
}
