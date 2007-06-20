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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;

import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.resource.BPELResourceFactoryImpl;
import org.eclipse.bpel.validator.helpers.CmdValidator;
import org.eclipse.bpel.validator.helpers.GetOpt;
import org.eclipse.bpel.validator.helpers.ModelQueryImpl;
import org.eclipse.bpel.validator.model.INode;
import org.eclipse.bpel.validator.model.IProblem;
import org.eclipse.bpel.validator.model.Runner;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.wst.wsdl.WSDLElement;
import org.eclipse.wst.wsdl.internal.util.WSDLResourceFactoryImpl;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.w3c.dom.Element;

/**
 * @author Michal Chmielewski (michal.chmielewski@oracle.com)
 * @date Jan 2, 2007
 *
 */

@SuppressWarnings("nls")

public class Main extends CmdValidator {

	IAdapterManager fManager ;
	ResourceSet fResourceSet;
	
	
	/**
	 * Create a brand new 
	 */
	@SuppressWarnings("unchecked")	
	public Main () {
	
		super();
		
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
		
		ModelQueryImpl.register( new ModelQuery() );
	}
	
	
	/**
	 * Validate the given file.
	 * @param file
	 * @return the list of validation errors.
	 */
	
	@Override
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
		fRunner = new Runner ( ModelQueryImpl.getModelQuery(), node);
		return fRunner.run();		
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
	
	@Override
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
		
		super.run(opt);
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
}
