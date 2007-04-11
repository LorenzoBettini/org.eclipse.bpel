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
package org.eclipse.bpel.validator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.resource.BPELResourceFactoryImpl;
import org.eclipse.bpel.validator.model.ARule;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.IProblem;
import org.eclipse.bpel.validator.model.Problem;
import org.eclipse.bpel.validator.model.RuleFactory;
import org.eclipse.bpel.validator.model.Runner;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
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

public class Main {

	IAdapterManager fManager ;
	ResourceSet fResourceSet;
	
	static PrintStream OUT = System.out;
	
	static final IProblem[] EMPTY_RESULT = {};
	
	/**
	 * Create a brand new 
	 */
	@SuppressWarnings("unchecked")	
	public Main () {
	
		// Create the Quasi-Eclipse environment ...
		
		fManager = AdapterManagerHelper.getAdapterManager();
		
		IAdapterFactory factory = new org.eclipse.bpel.validator.factory.AdapterFactory();		
		Class<?> list[] = { Element.class, EObject.class };		
		for(Class<?> c : list) {
			fManager.registerAdapters(factory, c);
		}				
		
		
		//	Create a resource set.
		fResourceSet = new ResourceSetImpl();
	    
		// Register the resource factories for .bpel, .wsdl, and .xsd resources.
		//   - bpel reads BPEL resources (our model)
		
		fResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
	    		"bpel", new BPELResourceFactoryImpl() 
	    );

		fResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
	    		"wsdl", new WSDLResourceFactoryImpl() 
	    );
		//   - wsdl reads WSDL resources (from wst project)
		
		// WSDL also needs to know about the extensions to WSDl that we provide, namely
		// partner links, variable properties, etc.
		// We need to register them someplace here ...
		
		fResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
	    		"xsd", new XSDResourceFactoryImpl() 
	    );
		//   - xsd reads WSDL resources (from wst project)
		
		
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
		BPELReader reader = new BPELReader();
			
		reader.read( file, fResourceSet );
		Process process = reader.getProcess();
		
		if (process == null) {
			OUT.printf("Error: Cannot read BPEL Process in %1$s",file);	
			return EMPTY_RESULT;
		}
		// Step 2. Preparation for the validator.
		linkModels(process);
				
		// Process as INode 
		INode node = (INode) fManager.getAdapter( process.getElement(), INode.class );
		
		// Step 3. Run it
		return new Runner ( ModelQuery.getModelQuery(), node).run();
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
	

	String toSafeXML ( String value ) {		
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
	
	
	void linkModels ( EObject process ) {
		
		// 
		// Each extensible element points to the DOM element that 
		// comprises it. This is done in the BPEL reader as well as
		// the WSDL readers. Here we add a pointer to the 
		// emf objects from the DOM objects.
	
		Iterator<?> emfIterator = process.eAllContents();
		while (emfIterator.hasNext()) {
			Object obj = emfIterator.next();
			// This is because only ExtensibleElement has a reference to
			// a DOM element.
			if (obj instanceof WSDLElement) {
				WSDLElement wsdle = (WSDLElement) obj;
				Element el = wsdle.getElement();
				if (el != null) {
					el.setUserData("emf.model", obj, null); //$NON-NLS-1$
				}
			}
		}
	}
	
	
	/**
	 * @param file
	 */
	void dumpModel(File file, PrintStream ps ) {
		
		// Step 1. Read the BPEL process using the Model API.
		BPELReader reader = new BPELReader();
			
		reader.read( file, fResourceSet );
		Process process = reader.getProcess();
		
		
		if (process == null) {
			OUT.printf("Error: Cannot read BPEL Process in %1$s",file);
			return ;
		}
		
		ps.println("<dump xmlns='http://emf/dump'>");
		
		Iterator<EObject> emfIterator = process.eAllContents();
		int count = 0;
		while (emfIterator.hasNext()) {
		
			EObject eObj = emfIterator.next();
			count += 1;
			
			ps.println("<EObject count=\"" + count + "\">" + toSafeXML(eObj.toString()) );

			Method methods [] = eObj.getClass().getMethods();
			for(Method m : methods) {
				
				if (	(m.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC ||
						(m.getModifiers() & Modifier.STATIC) == Modifier.STATIC || 
							((m.getName().startsWith("get")) == false &&
							 (m.getName().startsWith("is")) == false) ||
						(m.getReturnType() == null) ||
						(m.getParameterTypes().length > 0)) {				
					continue;
				}
				
				
								
				
				Object result = null ;
				// otherwise an interesting method to call ... so
				try {
					result = m.invoke(eObj, null);
				} catch (Throwable t) {				
					continue;
				}
				if (result == null) {					
					continue;				
				}
				
				ps.print("<" + m.getName() + " result='" + result.getClass() + "'");
				EObject eObjResult = null;
				if (result instanceof EObject) {
					eObjResult = (EObject) result;
					ps.print(" isProxy='" + eObjResult.eIsProxy() + "'");
				}
				
				ps.print(">");
				
				if (result instanceof List) {
					
				} else {
					ps.println( toSafeXML( result.toString() ) );
				}
				ps.println("</" + m.getName() + ">");
			}
			
			ps.println("</EObject>");
		}	
		
		ps.println("</dump>");
	}
	
	
	
	/** 
	 * Do the actual run
	 *  
	 * @param opt 
	 * @throws Exception 
	 */
	
	public void run (GetOpt opt) throws Exception {

		if (opt.hasOption('d')) {			
			// dump 
			File aFile = new File(opt.getOption('d'));
			OUT.printf("Dumping Model of %1$s\n\n",aFile);
			dumpModel (aFile,OUT);	
			File log = new File(aFile + ".model_dump.xml");			
			OUT.printf("Writing to log %1$s\n\n", log );
			
			PrintStream ps = null;
			try {
				ps = new PrintStream( log );
				dumpModel(aFile,ps);
			} catch (FileNotFoundException e) {
				
			} finally {
				try { ps.close(); } catch(Throwable t) {} 
			}			
			
		}
		
		// process validation as before
		for(String a : opt.parameters() ) {			
			
			File aFile = new File(a);
			
			OUT.printf("Validating %1$s\n\n",aFile);
			
			IProblem problems[] = EMPTY_RESULT;
			Runner runner = null;
			
			//
			//Step 1. Read the BPEL process using the Model API.
			BPELReader reader = new BPELReader();
				
			reader.read( aFile, fResourceSet );
			Process process = reader.getProcess();
			
			if (process == null) {
				OUT.printf("Error: Cannot read BPEL Process in %1$s",aFile);				
			} else {
				// Step 2. Preparation for the validator.
				linkModels(process);
						
				// Process as INode 
				INode node = (INode) fManager.getAdapter( process.getElement(), INode.class );
				
				// Step 3. Run it
				runner = new Runner ( ModelQuery.getModelQuery(), node);
				problems = runner.run();
				
				log(problems,runner,OUT);
			}			

			
			File log = new File(aFile + ".xml");			
			OUT.printf("Writing to log %1$s\n\n", log );
			PrintStream ps = null;
			try {
				ps = new PrintStream( log );
				log(problems, runner, ps);
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
		
		Main builder = new Main();
		GetOpt opt = new GetOpt("-d:-h",argv);
		
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
				
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		Document doc = builder.parse(aFile);
		
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
