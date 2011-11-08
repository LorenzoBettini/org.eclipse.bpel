/******************************************************************************
 * Copyright (c) 2011, EBM WebSourcing
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     EBM WebSourcing - initial API and implementation
 *******************************************************************************/

package org.eclipse.bpel.common.wsdl.parsers;

import java.net.URL;
import java.util.Collection;

import junit.framework.Assert;

import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.Definition;
import org.junit.Test;

/**
 * Tests the WSDL parser.
 * @author Vincent Zurczak - EBM WebSourcing
 */
public class WsdlParserTest {

	/**
	 * Tests the parsing of a simple WSDL 1.1.
	 * @throws Exception
	 */
	@Test
	public void testWsdlParsing1() throws Exception {

		URL url = getClass().getResource( "/wsdls/test1/tuxDroid.wsdl" );
		URI emfUri = URI.createURI( url.toString());
		Definition def = WsdlParser.loadWsdlDefinition( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertNotNull( def );

		Collection<Definition> definitions = WsdlParser.loadAllWsdlDefinitions( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertEquals( definitions.size(), 1 );
	}


	/**
	 * Tests the parsing of a simple WSDL 2.0 (should not work).
	 * @throws Exception
	 */
	@Test
	public void testWsdlParsing2() throws Exception {

		URL url = getClass().getResource( "/wsdls/test2/testwsdl20.wsdl" );
		URI emfUri = URI.createURI( url.toString());
		Definition def = WsdlParser.loadWsdlDefinition( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertFalse( WsdlParser.seemsValidDefinition( def ));
	}


	/**
	 * Tests the parsing of a WSDL 1.1 with XML schema and WSDL imports (same level).
	 * @throws Exception
	 */
	@Test
	public void testWsdlParsing3() throws Exception {

		URL url = getClass().getResource( "/wsdls/test7/ToConcrete.wsdl" );
		URI emfUri = URI.createURI( url.toString());
		Definition def = WsdlParser.loadWsdlDefinition( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertNotNull( def );

		Collection<Definition> definitions = WsdlParser.loadAllWsdlDefinitions( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertEquals( definitions.size(), 2 );
	}


	/**
	 * Tests the parsing of a WSDL 1.1 with XML schema and WSDL imports (cyclic import).
	 * @throws Exception
	 */
	@Test
	public void testWsdlParsing4() throws Exception {

		URL url = getClass().getResource( "/wsdls/test8/ToConcrete.wsdl" );
		URI emfUri = URI.createURI( url.toString());
		Definition def = WsdlParser.loadWsdlDefinition( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertNotNull( def );

		Collection<Definition> definitions = WsdlParser.loadAllWsdlDefinitions( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertEquals( definitions.size(), 2 );
	}
}
