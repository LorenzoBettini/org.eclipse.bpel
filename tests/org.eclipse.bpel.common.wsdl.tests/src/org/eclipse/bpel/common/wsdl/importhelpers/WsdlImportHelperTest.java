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

package org.eclipse.bpel.common.wsdl.importhelpers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import junit.framework.Assert;

import org.eclipse.bpel.common.wsdl.parsers.WsdlParser;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.Definition;
import org.junit.Test;

/**
 * Tests the WSDL importer helper (import and update).
 * @author Vincent Zurczak - EBM WebSourcing
 */
public class WsdlImportHelperTest {

	/**
	 * Tests the import of a simple WSDL 1.1.
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport1() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test1/tuxDroid.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 1 );
			Assert.assertEquals( tmpDir.listFiles().length, 1 );
			testWsdlParsing( map.values().iterator().next(), 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a simple WSDL 1.1 with name conflict.
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport2() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			// First import: OK
			URL url = getClass().getResource( "/wsdls/test1/tuxDroid.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 1 );
			testWsdlParsing( map.values().iterator().next(), 1 );

			// Second import: should be renamed
			map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 1 );
			Assert.assertEquals( tmpDir.listFiles().length, 2 );
			testWsdlParsing( map.values().iterator().next(), 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a simple WSDL 2.0.
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport3() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test2/testwsdl20.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 1 );
			Assert.assertEquals( tmpDir.listFiles().length, 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a WSDL 1.1 which imports a XSD (same level).
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport4() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test3/To.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 2 );
			Assert.assertEquals( tmpDir.listFiles().length, 2 );

			File f = map.get( url.toString());
			Assert.assertNotNull( f );
			testWsdlParsing( f, 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a WSDL 1.1 which imports a XSD (1 sub-level).
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport5() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test4/To.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 2 );
			Assert.assertEquals( tmpDir.listFiles().length, 2 );

			File level1Dir = new File( tmpDir, "level1" );
			Assert.assertTrue( level1Dir.exists());
			Assert.assertEquals( level1Dir.listFiles().length, 1 );

			File f = map.get( url.toString());
			Assert.assertNotNull( f );
			testWsdlParsing( f, 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a WSDL 1.1 which imports a XSD (2 sub-levels).
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport6() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test5/To.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 2 );
			Assert.assertEquals( tmpDir.listFiles().length, 2 );

			File level2Dir = new File( tmpDir, "level1/level2" );
			Assert.assertTrue( level2Dir.exists());
			Assert.assertEquals( level2Dir.listFiles().length, 1 );
			Assert.assertEquals( level2Dir.getParentFile().listFiles().length, 1 );

			File f = map.get( url.toString());
			Assert.assertNotNull( f );
			testWsdlParsing( f, 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a WSDL 1.1 which imports several XSD (2 sub-levels).
	 * <p>
	 * And to limit the number of tests, these two XSDs import each other.
	 * This way, we can test the support of loops in the imports.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport7() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test6/To.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 3 );
			Assert.assertEquals( tmpDir.listFiles().length, 2 );

			File level2Dir = new File( tmpDir, "level1/level2" );
			Assert.assertTrue( level2Dir.exists());
			Assert.assertEquals( level2Dir.listFiles().length, 1 );
			Assert.assertEquals( level2Dir.getParentFile().listFiles().length, 2 );

			File f = map.get( url.toString());
			Assert.assertNotNull( f );
			testWsdlParsing( f, 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a XSD with an import of another XSD (upper-level => the import will be relocated).
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport8() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test6/level1/level2/To_schema1.xsd" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 2 );
			Assert.assertEquals( tmpDir.listFiles().length, 2 );

			File relocatedDir = new File( tmpDir, WsdlImportHelper.RELOCATED_DIRECTORY );
			Assert.assertTrue( relocatedDir.exists());
			Assert.assertEquals( relocatedDir.listFiles().length, 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests the import of a XSD with an import of another XSD (sub-level).
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport9() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test6/level1/To_schema2.xsd" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 2 );
			Assert.assertEquals( tmpDir.listFiles().length, 2 );

			File level2Dir = new File( tmpDir, "level2" );
			Assert.assertTrue( level2Dir.exists());
			Assert.assertEquals( level2Dir.listFiles().length, 1 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests a WSDL 1.1 which imports a WSDL and a XML schema (same level).
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport10() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test7/ToConcrete.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 3 );
			Assert.assertEquals( tmpDir.listFiles().length, 3 );

			File f = map.get( url.toString());
			Assert.assertNotNull( f );
			testWsdlParsing( f, 2 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Tests a WSDL 1.1 which imports a WSDL and a XML schema (same level).
	 * <p>
	 * There is a cyclic import in the WSDL.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testWsdlImport11() throws Exception {

		File tmpDir = new File( System.getProperty( "java.io.tmpdir" ), UUID.randomUUID().toString());
		Assert.assertTrue( tmpDir.mkdir());
		try {
			URL url = getClass().getResource( "/wsdls/test8/ToConcrete.wsdl" );
			Map<String,File> map = new WsdlImportHelper().importWsdlOrXsdAndDependencies( tmpDir, url.toString());

			Assert.assertEquals( map.size(), 3 );
			Assert.assertEquals( tmpDir.listFiles().length, 3 );

			File f = map.get( url.toString());
			Assert.assertNotNull( f );
			testWsdlParsing( f, 2 );

		} finally {
			deleteFilesRecursively( tmpDir );
			Assert.assertFalse( tmpDir.exists());
		}
	}


	/**
	 * Deletes files recursively.
	 * @param files the files to delete
	 * @throws IOException if a file could not be deleted
	 */
	private static void deleteFilesRecursively( File... files ) throws IOException {

		if( files == null )
			return;

		for( File file : files ) {
			if( file.exists()) {
				if( file.isDirectory())
					deleteFilesRecursively( file.listFiles());

				if( ! file.delete())
					throw new IOException( file.getAbsolutePath() + " could not be deleted." );
			}
		}
	}


	/**
	 * Tests the parsing of an imported WSDL.
	 * @param wsdlUrl the WSDL URL
	 * @param expectedNumberOfDefinitions the number of definitions, including those found from import declarations
	 */
	private static void testWsdlParsing( File wsdlFile, int expectedNumberOfDefinitions ) {

		// Parse the imported WSDL
		URI emfUri = URI.createFileURI( wsdlFile.getAbsolutePath());
		Definition def = WsdlParser.loadWsdlDefinition( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertNotNull( def );

		// Check the imports
		Collection<Definition> definitions = WsdlParser.loadAllWsdlDefinitions( emfUri, WsdlParser.createBasicResourceSetForWsdl());
		Assert.assertEquals( definitions.size(), expectedNumberOfDefinitions );
	}
}
