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
package org.eclipse.bpel.ui.expressions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Hashtable;
import java.net.URL;

import org.eclipse.bpel.ui.BPELUIPlugin;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class Functions {
	
	protected TreeMap[] functions;
	protected Document document;
	protected TreeMap allFunctions;
	
	protected static final String FUNC_FILE_LOCATION = "/templates/fn.xml";  // relative to bundle
	protected static final String FUNC_XML_ROOT_PATH = "/bpel-xpath-functions/function";
	protected static final int RETURN_TYPE_NUMBER = 0;
	protected static final int RETURN_TYPE_STRING = 1;
	protected static final int RETURN_TYPE_BOOLEAN = 2;
	protected static final int RETURN_TYPE_NODE = 3;
	protected static final int RETURN_TYPE_NODESET = 4;
	protected static final String[] RETURN_TYPES = {"number", "string", "boolean", "node", "node-set"};
	
	
	private static Functions singleton;
	
	
	private Functions() {
		init();
		parseUsingXpathAPI();
	};
	
	
	static public final Functions getInstance () {
		if (singleton != null) {
			return singleton ;
		}
		
		synchronized ( Functions.class ) {
			singleton = new Functions ();
		}
		
		return singleton;
	}
	
	
	public TreeMap getNumberFunctions() { return functions[RETURN_TYPE_NUMBER]; }
	public TreeMap getStringFunctions() { return functions[RETURN_TYPE_STRING]; }
	public TreeMap getBooleanFunctions() { return functions[RETURN_TYPE_BOOLEAN]; }
	public TreeMap getNodeFunctions() { return functions[RETURN_TYPE_NODE]; }
	public TreeMap getNodesetFunctions() { return functions[RETURN_TYPE_NODESET]; }
	public TreeMap getFunctions() { return allFunctions; }
	
	/*
	 * Use xpath API to parse functions document.
	 * Document assumed to have following structure to define functions:
	 *  ...
	 *  <function id="functionName" returns="type">
   	 *  ...
   	 *  <help>...</help>
   	 *  <comment>...</comment>
     *  <arg name="nameOfArgument" type="string" default=""? optional="true"? >
     *     <comment>?
     *     <assistant name="internalName"?>?
     *      <option value="value">Text Display</option>*
     *     </assistant>
     *  </arg>
     *  <arg xpath="//args[@name='nameOfArgument']"/>
     *   ...
     *  </function>
	 */
	public int parseUsingXpathAPI() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();

			URL baseURL = BPELUIPlugin.getPlugin().getBundle().getEntry("/");
			String url = baseURL.toString() + FUNC_FILE_LOCATION;
			document = builder.parse(url);
			if (document != null) {
				Map tempArgs = new HashMap();				
				XPathFactory xpfactory = XPathFactory.newInstance();
				XPath xpath = xpfactory.newXPath();
				
				// find all "supported" functions using specified path and parse
				// out necessary information from xml
				NodeList funcList = (NodeList)xpath.evaluate(FUNC_XML_ROOT_PATH, document, XPathConstants.NODESET);
				for (int i=0; i<funcList.getLength(); i++) {
					Element funcNode = (Element) funcList.item(i);
					
					String name = null;
					String nsprefix = null;
					String returntype = null;
					String help = null;
					String comments = null;
					
					name = funcNode.getAttribute("id");									
					returntype = funcNode.getAttribute("returns");
					
					help = xpath.evaluate("help/text()", funcNode);
					comments = xpath.evaluate("comment/text()", funcNode);
					String nsexpr = "property[@id='namespace-prefix']/value/text()";
					nsprefix = xpath.evaluate(nsexpr, funcNode);
					
					// retrieve child nodes for arguments
					ArrayList args = new ArrayList();
					NodeList argNodes = (NodeList)xpath.evaluate("arg", funcNode, XPathConstants.NODESET);
					if (argNodes != null) {
						for (int j=0; j < argNodes.getLength(); j++) {
							Node argNode = argNodes.item(j);
							NamedNodeMap argAttribs = argNode.getAttributes();
							if (argAttribs != null) {
								String argname = "";
								String argtype = "";
								String argxpath = "";
								char argoptional = 0;
								Node argAttribNode = argAttribs.getNamedItem("name");
								if (argAttribNode != null)
									argname = argAttribNode.getNodeValue();
								
								argAttribNode = argAttribs.getNamedItem("type");
								if (argAttribNode != null)
									argtype = argAttribNode.getNodeValue();
								
								argAttribNode = argAttribs.getNamedItem("optional");
								if (argAttribNode != null)
									argoptional = evaluateOptional(argAttribNode.getNodeValue());
								
								argAttribNode = argAttribs.getNamedItem("xpath");
								if (argAttribNode != null) {
									argxpath = argAttribNode.getNodeValue();

									// attempt to resolve xpath argument declaration
									if (argxpath != null) {
										FunctionArgument tempArg = (FunctionArgument)tempArgs.get(argxpath);
										if (tempArg != null) {
											argtype = tempArg.getType();
											argname = tempArg.getName();
											argoptional = tempArg.getOptional();
										}
										else {
											// retrieve argument information and store in temporary in-memory
											// repository for later retrieval if needed
											argtype = xpath.evaluate(argxpath+"/@type", document);
											argname = xpath.evaluate(argxpath+"/@name", document);
											argoptional = evaluateOptional(xpath.evaluate(argxpath+"/@optional", document));
											tempArgs.put(argxpath, new FunctionArgument(argname, argtype, argxpath, argoptional));
										}
										
									}
								}
								// store argument information in ordered list
								FunctionArgument funcarg = new FunctionArgument(argname, argtype, argxpath, argoptional);
								args.add(funcarg);
							}
						}
					}

					// store function information into in-memory ordered map
					if ((name != null) && (returntype != null)) {			
						int index;
						for (index=0; index<RETURN_TYPES.length; index++) {
							if (returntype.compareToIgnoreCase(RETURN_TYPES[index]) == 0)
								break;
						}
						// categorize function by return type and store internally
						if (index < RETURN_TYPES.length)
							functions[index].put(name, new Function(name, nsprefix, returntype, help, comments, args));
						else
							System.out.println("unknown return type - Function: " + name + ", Returns: " + returntype);
						// keep an alphabetized list of all functions	
						allFunctions.put(name, new Function(name, nsprefix, returntype, help, comments, args));
					}					
				}
				// return ok status
				return 0;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return -1;
	}
	
	/*
	 * Use DOM traversal to parse functions document.
	 * Document assumed to have following structure to define functions:
	 *  ...
	 *  <function id="functionName" returns="type">
   	 *  ...
   	 *  <help>...</help>
   	 *  <comment>...</comment>
     *  <arg name="nameOfArgument" type="string" default=""? optional="true"? >
     *     <comment>?
     *     <assistant name="internalName"?>?
     *      <option value="value">Text Display</option>*
     *     </assistant>
     *  </arg>
     *  <arg xpath="//args[@name='nameOfArgument']"/>
     *   ...
     *  </function>
	 */
	public int parseUsingDomTraversal() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			XPathFactory xpfactory = XPathFactory.newInstance();
			XPath xpath = xpfactory.newXPath();
			
			document = builder.parse(FUNC_FILE_LOCATION);
			if (document != null) {
				Map tempArgs = new HashMap();
				NodeList nodelist2 = document.getElementsByTagName("function");
				for (int i=0; i<nodelist2.getLength(); i++) {
					Node node2 = nodelist2.item(i);
					NamedNodeMap attribs = node2.getAttributes();
					String name = null;
					String nsprefix = null;
					String returntype = null;
					String help = null;
					
					Node node3 = attribs.getNamedItem("id");
					if (node3 != null)
						name = node3.getNodeValue();
					
					node3 = attribs.getNamedItem("returns");
					if (node3 != null)
						returntype = node3.getNodeValue();
					
					// retrieve child nodes for arguments
					NodeList childNodes = node2.getChildNodes();
					ArrayList args = new ArrayList();
					for (int k=0; k < childNodes.getLength(); k++) {
						Node childNode = childNodes.item(k);
						if (childNode.getNodeName().compareTo("arg") == 0) {
							NamedNodeMap childattribs = childNode.getAttributes();
							if (childattribs != null) {
								String argname = "";
								String argtype = "";
								String argxpath = "";
								char argoptional = 0;
								Node childNode2 = childattribs.getNamedItem("name");
								if (childNode2 != null)
									argname = childNode2.getNodeValue();
								
								childNode2 = childattribs.getNamedItem("type");
								if (childNode2 != null)
									argtype = childNode2.getNodeValue();
								
								childNode2 = childattribs.getNamedItem("xpath");
								if (childNode2 != null) {
									argxpath = childNode2.getNodeValue();
									if (argxpath != null) {
										// attempt to resolve argument type/name
										FunctionArgument tempArg = (FunctionArgument)tempArgs.get(argxpath);
										if (tempArg != null) {
											argtype = tempArg.getType();
											argname = tempArg.getName();
											argoptional = tempArg.getOptional();
										}
										else {
											argtype = xpath.evaluate(argxpath + "/@type", document);
											argname = xpath.evaluate(argxpath+"/@name", document);
											argoptional = evaluateOptional(xpath.evaluate(argxpath+"/@optional", document));
											tempArgs.put(argxpath, new FunctionArgument(argname, argtype, argxpath, argoptional));
										}
									}
								}
									
								childNode2 = childattribs.getNamedItem("optional");
								if (childNode2 != null) {
									argoptional = evaluateOptional(childNode2.getNodeValue());
								}
	
								FunctionArgument funcarg = new FunctionArgument(argname, argtype, argxpath, argoptional);
								args.add(funcarg);
							}
						}
						if (childNode.getNodeName().compareTo("help") == 0) {
							if (childNode.getNodeType() == Node.ELEMENT_NODE) {
								NodeList textList = childNode.getChildNodes();
								for (int m=0; m<textList.getLength(); m++) {
									if (textList.item(m).getNodeType() == Node.TEXT_NODE) {
										help = textList.item(m).getNodeValue();
										break;
									}
								}
							}
						}
						
						if (childNode.getNodeName().compareTo("property") == 0) {
							NamedNodeMap propattribs = childNode.getAttributes();
							if (propattribs != null) {
								Node childNode3 = (Node)propattribs.getNamedItem("id");
								if (childNode3 != null) {
									if (childNode3.getNodeValue().compareTo("namespace-prefix") == 0) {
										NodeList childNode4 = childNode.getChildNodes();
										for (int z=0; z<childNode4.getLength(); z++) {
											Node valueNode = childNode4.item(z);
											if (valueNode.getNodeName().compareTo("value") == 0) {
												if (valueNode.getNodeType() == Node.ELEMENT_NODE) {
													NodeList valueList = valueNode.getChildNodes();
													for (int a=0; a<valueList.getLength(); a++) {
														if (valueList.item(a).getNodeType() == Node.TEXT_NODE) {
															nsprefix = valueList.item(a).getNodeValue();
															break;
														}
													}
												}
												break;
											}
										}
									}
								}
							}
						}
						
					}
					
					// store function information into in-memory ordered map						
					if ((name != null) && (returntype != null)) {
						int index;
						for (index=0; index<RETURN_TYPES.length; index++) {
							if (returntype.compareToIgnoreCase(RETURN_TYPES[index]) == 0)
								break;
						}
						// categorize function by return type and store internally
						if (index < RETURN_TYPES.length)
							functions[index].put(name, new Function(name, nsprefix, returntype, help, null, args));
						else
							System.out.println("unknown return type - Function: " + name + ", Returns: " + returntype);
						// keep an alphabetized list of all functions	
						allFunctions.put(name, new Function(name, nsprefix, returntype, help, "", args));
					}
				}
				// return ok status 
				return 0;
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		return -1;
	}
		
	private void init() {
		// num of types - number, string, boolean, node, nodeset
		functions = new TreeMap[RETURN_TYPES.length];  
		for (int i=0; i<RETURN_TYPES.length; i++) {
			functions[i] = new TreeMap();
		}
		allFunctions = new TreeMap();
		document = null;
		}
	
	private char evaluateOptional(String optional) {
		if (optional != null) {
			if (optional.compareTo("true") == 0)
				return '?';
			else if (optional.compareTo("*") == 0)
				return '*';
		}
		return 0;
	}
	
	// internal helper function
	private void printNode(Node node) {
		NodeList nodelist = node.getChildNodes();
		for (int i=0; i<nodelist.getLength(); i++) {
			Node node2 = nodelist.item(i);
			printNode(node2);
		}
		System.out.println("Node type: " + node.getNodeType() + ", Node name: " + node.getNodeName() + ", Node Value: " + node.getNodeValue());
	}
	
	// internal helper function
	private void printFunctions() {
		for (int i=0; i<5; i++) {
			System.out.println("FUNCTIONS THAT RETURN " + RETURN_TYPES[i]);
			
			Iterator list = functions[i].values().iterator();
			while (list.hasNext()) {
				Function func = (Function)list.next();
				System.out.println("Name: " + func.getName());
				System.out.println("Arguments:");
				ArrayList args = func.getArgs();
				if (args != null) {
					for (int j=0; j<args.size(); j++) {
						FunctionArgument arg = (FunctionArgument)args.get(j);
						System.out.println("   " + j + ") " + arg.getName() + " - " + arg.getType() + ", XPATH = " + arg.getXpath());
					}
				}
				System.out.println("");
			}
		}
	}
}
