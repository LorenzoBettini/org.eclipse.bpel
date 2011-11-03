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

package org.eclipse.bpel.ui.wizards;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.junit.Assert;
import org.junit.Test;

/**
 * A set of tests for the BPEL creation wizard.
 * @author Vincent Zurczak - EBM WebSourcing
 */
public class TestNewBpelFileWizard {

	private static final String BPEL_DIR = "bpelContent";
	private static final String BPEL_NAME = "myProcess";
	private static final String BPEL_FILE_NAME = BPEL_NAME + ".bpel";

	private final SWTWorkbenchBot bot = new SWTWorkbenchBot();


	/**
	 * Tests the creation of a BPEL from a template.
	 * <p>
	 * The process is created in the "bpelContent" directory of a BPEL project.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTemplateInBpelDirectory() throws Exception {

		// Test all the templates in a same method
		for( int tplIndex=0; tplIndex<3; tplIndex++ ) {

			try {
				String projectName = createBpelProject( tplIndex );
				SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
				newMenu.menu( "BPEL Process File" ).click();

				this.bot.text( 0 ).setText( BPEL_NAME );
				this.bot.comboBox( 1 ).setText( "http://" + BPEL_NAME );
				this.bot.button( "Next >" ).click();

				this.bot.comboBox( 0 ).setSelection( tplIndex );
				this.bot.button( "Next >" ).click();

				completeBpelFileCreation( true, projectName );
				IPath path = new Path( projectName  ).append( BPEL_DIR ).append( BPEL_FILE_NAME );
				IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
				Assert.assertTrue( bpelFile.isAccessible());

				// The empty template is not valid because it does not start with an starting activity
				if( tplIndex == 1 )
					Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 1 );
				else
					Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 0 );

			} catch( AssertionError e ) {
				String tplName = getTemplateName( tplIndex );
				throw new AssertionError( "The " + tplName + " test failed. " + e.getMessage());
			}
		}
	}


	/**
	 * Tests the creation of a BPEL from a template.
	 * <p>
	 * The process is created at the root of a BPEL project.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTemplateInBpelProject() throws Exception {

		// Test all the templates in a same method
		for( int tplIndex=0; tplIndex<3; tplIndex++ ) {

			try {
				String projectName = createBpelProject( tplIndex );
				SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
				newMenu.menu( "BPEL Process File" ).click();

				this.bot.text( 0 ).setText( BPEL_NAME );
				this.bot.comboBox( 1 ).setText( "http://" + BPEL_NAME );
				this.bot.button( "Next >" ).click();

				this.bot.comboBox( 0 ).setSelection( tplIndex );
				this.bot.button( "Next >" ).click();

				completeBpelFileCreation( true, projectName );
				IPath path = new Path( projectName ).append( BPEL_FILE_NAME );
				IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
				Assert.assertTrue( bpelFile.isAccessible());

				// The empty template is not valid because it does not start with an starting activity
				if( tplIndex == 1 )
					Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 1 );
				else
					Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 0 );

			} catch( AssertionError e ) {
				String tplName = getTemplateName( tplIndex );
				throw new AssertionError( "The " + tplName + " test failed. " + e.getMessage());
			}
		}
	}


	/**
	 * Tests the creation of a BPEL from a template.
	 * <p>
	 * The process is created at the root of a non-BPEL project.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTemplateInNonBpelProject() throws Exception {

		// Test all the templates in a same method
		for( int tplIndex=0; tplIndex<3; tplIndex++ ) {

			try {
				String projectName = createSimpleProject( tplIndex );
				SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
				newMenu.menu( "BPEL Process File" ).click();

				this.bot.text( 0 ).setText( BPEL_NAME );
				this.bot.comboBox( 1 ).setText( "http://" + BPEL_NAME );
				this.bot.button( "Next >" ).click();

				this.bot.comboBox( 0 ).setSelection( tplIndex );
				this.bot.button( "Next >" ).click();

				completeBpelFileCreation( true, projectName );
				IPath path = new Path( projectName ).append( BPEL_FILE_NAME );
				IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
				Assert.assertTrue( bpelFile.isAccessible());

				// The empty template is not valid because it does not start with an starting activity
				if( tplIndex == 1 )
					Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 1 );
				else
					Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 0 );

			} catch( AssertionError e ) {
				String tplName = getTemplateName( tplIndex );
				throw new AssertionError( "The " + tplName + " test failed. " + e.getMessage());
			}
		}
	}


	/**
	 * Tests the creation of a BPEL from a template.
	 * <p>
	 * The process is created in the "bpelContent" directory of a BPEL project.
	 * The created process is abstract.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testTemplateAsAbstract() throws Exception {

		// Test all the templates in a same method
		for( int tplIndex=0; tplIndex<3; tplIndex++ ) {

			try {
				String projectName = createBpelProject( tplIndex );
				SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
				newMenu.menu( "BPEL Process File" ).click();

				this.bot.text( 0 ).setText( BPEL_NAME );
				this.bot.comboBox( 1 ).setText( "http://" + BPEL_NAME );
				this.bot.button( 0 ).click();	// Abstract
				this.bot.button( "Next >" ).click();

				this.bot.comboBox( 0 ).setSelection( tplIndex );
				this.bot.button( "Next >" ).click();

				completeBpelFileCreation( true, projectName );
				IPath path = new Path( projectName ).append( BPEL_DIR ).append( BPEL_FILE_NAME );
				IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
				Assert.assertTrue( bpelFile.isAccessible());

				// The empty template is valid because the process is abstract
				Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 0 );

			} catch( AssertionError e ) {
				String tplName = getTemplateName( tplIndex );
				throw new AssertionError( "The " + tplName + " test failed. " + e.getMessage());
			}
		}
	}


	/**
	 * Tests the creation of a BPEL from a WSDL definition.
	 * <p>
	 * The WSDL is imported in the project.
	 * The process is created in the "bpelContent" directory of a BPEL project.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testGenerationFromWsdlWithImport() throws Exception {

		String[] wsdlFiles = { "test1/tuxDroid.wsdl", "test2/tuxDroid.wsdl", "test3/To.wsdl" };
		for( int index=0; index<wsdlFiles.length; index++ ) {

			String wsdlFile = wsdlFiles[ index ];
			try {
				URL url = getClass().getResource( "/wsdls/" + wsdlFile );
				String projectName = createBpelProject( index );
				SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
				newMenu.menu( "BPEL Process File" ).click();

				this.bot.comboBox().setSelection( 1 );
				this.bot.text( 0 ).setText( BPEL_NAME );
				this.bot.comboBox( 1 ).setText( "http://" + BPEL_NAME );
				this.bot.button( "Next >" ).click();

				this.bot.text( 0 ).setText( url.toString());
				this.bot.comboBox( 0 ).setSelection( 0 );
				this.bot.button( "Next >" ).click();

				completeBpelFileCreation( true, projectName );
				IPath path = new Path( projectName ).append( BPEL_FILE_NAME );
				IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
				Assert.assertTrue( bpelFile.isAccessible());
				Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 0 );

			} catch( AssertionError e ) {
				throw new AssertionError( "The creation with " + wsdlFile + " failed. " + e.getMessage());
			}
		}
	}


	/**
	 * Tests the creation of a BPEL from a WSDL definition.
	 * <p>
	 * The WSDL is not imported in the project.
	 * The process is created in the "bpelContent" directory of a BPEL project.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testGenerationFromWsdlWithoutImport() throws Exception {

		String[] wsdlFiles = { "test1/tuxDroid.wsdl", "test2/tuxDroid.wsdl", "test3/To.wsdl" };
		for( int index=0; index<wsdlFiles.length; index++ ) {

			String wsdlFile = wsdlFiles[ index ];
			try {
				URL url = getClass().getResource( "/wsdls/" + wsdlFile );
				String projectName = createBpelProject( index );
				SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
				newMenu.menu( "BPEL Process File" ).click();

				this.bot.comboBox().setSelection( 1 );
				this.bot.text( 0 ).setText( BPEL_NAME );
				this.bot.comboBox( 1 ).setText( "http://" + BPEL_NAME );
				this.bot.button( "Next >" ).click();

				this.bot.text( 0 ).setText( url.toString());
				this.bot.comboBox( 0 ).setSelection( 0 );
				this.bot.button( 0 ).click();	// Do not import the WSDL
				this.bot.button( "Next >" ).click();

				completeBpelFileCreation( true, projectName );
				IPath path = new Path( projectName ).append( BPEL_FILE_NAME );
				IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
				Assert.assertTrue( bpelFile.isAccessible());
				Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 0 );

			} catch( AssertionError e ) {
				throw new AssertionError( "The creation with " + wsdlFile + " failed. " + e.getMessage());
			}
		}
	}


	/**
	 * Tests the creation of a BPEL from a WSDL definition.
	 * <p>
	 * The WSDL is imported in the project.
	 * The process is created in the "bpelContent" directory of a BPEL project.
	 * The created process is abstract.
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testGenerationFromWsdlAsAbstract() throws Exception {

		String[] wsdlFiles = { "test1/tuxDroid.wsdl", "test2/tuxDroid.wsdl", "test3/To.wsdl" };
		for( int index=0; index<wsdlFiles.length; index++ ) {

			String wsdlFile = wsdlFiles[ index ];
			try {
				URL url = getClass().getResource( "/wsdls/" + wsdlFile );
				String projectName = createBpelProject( index );
				SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
				newMenu.menu( "BPEL Process File" ).click();

				this.bot.comboBox().setSelection( 1 );
				this.bot.text( 0 ).setText( BPEL_NAME );
				this.bot.comboBox( 1 ).setText( "http://" + BPEL_NAME );
				this.bot.button( 0 ).click();	// Abstract
				this.bot.button( "Next >" ).click();

				this.bot.text( 0 ).setText( url.toString());
				this.bot.comboBox( 0 ).setSelection( 0 );
				this.bot.button( "Next >" ).click();

				completeBpelFileCreation( true, projectName );
				IPath path = new Path( projectName ).append( BPEL_FILE_NAME );
				IFile bpelFile = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
				Assert.assertTrue( bpelFile.isAccessible());
				Assert.assertEquals( bpelFile.findMarkers( null, true, IResource.DEPTH_ZERO ).length, 0 );

			} catch( AssertionError e ) {
				throw new AssertionError( "The creation with " + wsdlFile + " failed. " + e.getMessage());
			}
		}
	}


	/**
	 * Gets the name of the template associated with this index.
	 * @param templateIndex the template index
	 * @return the template name (or "Unknown" is it does not match anything)
	 */
	private String getTemplateName( int templateIndex ) {

		String tplName;
		switch( templateIndex ) {
		case 0: tplName = "Asynchronous"; break;
		case 1: tplName = "Empty"; break;
		case 2: tplName = "Synchronous"; break;
		default: tplName = "Unknwon"; break;
		}

		return tplName;
	}


	/**
	 * Completes the creation of a new BPEL file.
	 * @param selectBpelDir true to create the BPEL in the {@link #BPEL_DIR} directory, false for the root of the project
	 * @param projectName the project name
	 */
	private void completeBpelFileCreation( boolean selectBpelDir, String projectName ) {

		if( selectBpelDir )
			this.bot.tree( 0 ).select( projectName ).expandNode( projectName ).select( BPEL_DIR );
		else
			this.bot.tree( 0 ).select( projectName );

		this.bot.button( "Finish" ).click();
		this.bot.waitUntil( new ICondition() {

			@Override
			public boolean test() throws Exception {
				String title = TestNewBpelFileWizard.this.bot.activeEditor().getTitle();
				return (BPEL_NAME + ".bpel").equals( title );
			}

			@Override
			public void init( SWTBot bot ) {
				// nothing
			}

			@Override
			public String getFailureMessage() {
				return "Could not wait for the BPEL Designer to be open";
			}
		});
	}


	/**
	 * Creates a BPEL project.
	 * @param projectNameSuffix the suffix for the project name
	 * @return the project name
	 */
	private String createBpelProject( Object projectNameSuffix ) {

		String projectName = "TestProject" + String.valueOf( projectNameSuffix );
		SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
		newMenu.menu( "BPEL Project" ).click();
		this.bot.text( 0 ).setText( projectName );
		this.bot.button( "Next >" ).click();
		this.bot.button( "Finish" ).click();

		return projectName;
	}


	/**
	 * Creates a simple project.
	 * @param projectNameSuffix the suffix for the project name
	 * @return the project name
	 */
	private String createSimpleProject( Object projectNameSuffix ) {

		String projectName = "TestProject" + String.valueOf( projectNameSuffix );
		SWTBotMenu newMenu = this.bot.menu( "File" ).menu( "New" );
		newMenu.menu( "Project" ).click();
		this.bot.text( 0 ).setText( projectName );
		this.bot.button( "Finish" ).click();

		return projectName;
	}
}
