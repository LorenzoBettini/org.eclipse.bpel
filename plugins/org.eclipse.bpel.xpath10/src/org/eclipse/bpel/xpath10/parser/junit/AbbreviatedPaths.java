package org.eclipse.bpel.xpath10.parser.junit;

import org.antlr.runtime.*;


import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: rdclark
 * Date: May 30, 2007
 * Time: 8:11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbbreviatedPaths extends AbstractTC {

    // These come from http://www.w3.org/TR/xpath#path-abbrev 
    public void testPathExprAbbreviatedSample1 () throws IOException, RecognitionException
    {
    	String input = "para";
    	
    	runTest(CMP_AST,       input, "para" );
    	runTest(CMP_EXPR_TEXT, input, "child::para" );
    }
    
    public void testPathExprAbbreviatedSample1NS () throws IOException, RecognitionException
    {
    	String input = "ns:para";
    	
    	runTest(CMP_EXPR_TEXT, input, "child::ns:para" );
    	runTest(CMP_AST,       input, "ns : para" );
    }
    
    
    public void testPathExprAbbreviatedSample2 () throws IOException, RecognitionException
    {
    	String input = "*";
    	runTest(CMP_AST,       input, "*" );
    	runTest(CMP_EXPR_TEXT, input, "child::*");
    }

    public void testPathExprAbbreviatedSample2NS () throws IOException, RecognitionException
    {
    	String input = "ns:*";
    	runTest(CMP_EXPR_TEXT, input, "child::ns:*");
    	runTest(CMP_AST,       input, "ns : *" );
    }

    public void testPathExprAbbreviatedSample3 () throws IOException, RecognitionException
    {
    	String input = " text () ";
    	runTest(CMP_EXPR_TEXT,   input, "child::text()");
    }
    
    public void testPathExprAbbreviatedSample3Absolute () throws IOException, RecognitionException
    {
    	String input = "/text ( )";
    	runTest(CMP_EXPR_TEXT,    input, "/child::text()" );
    }
    
    public void testPathExprAbbreviatedSample4 () throws IOException, RecognitionException
    {
    	String input = "@name";
    	
    	runTest(CMP_AST,      input,  "@ name" );
    	runTest(CMP_EXPR_TEXT,input,  "attribute::name");    	
    }

    public void testPathExprAbbreviatedSample5 () throws IOException, RecognitionException
    {
    	String input = "@*";
    	
    	runTest(CMP_AST,       input,  "@ *" );
    	runTest(CMP_EXPR_TEXT, input,  "attribute::*");
    }
    
    public void testPathExprAbbreviatedSample6 () throws IOException, RecognitionException
    {
    	String input = "para[1]" ;
    	runTest(CMP_AST,       input, "para ([ 1)" );
    	runTest(CMP_EXPR_TEXT, input, "child::para[1]" );
    }
    
    public void testPathExprAbbreviatedSample7 () throws IOException, RecognitionException
    {
    	String input = "para [ last ( ) ]";
    	runTest(CMP_AST,      input, "para ([ last)");
    	runTest(CMP_EXPR_TEXT,input, "child::para[last()]");    			
    }
    
    public void testPathExprAbbreviatedSample8 () throws IOException, RecognitionException
    {
    	String input = "*/para";    	
    	runTest(CMP_AST,       input, "* / para" );
    	runTest(CMP_EXPR_TEXT, input, "child::*/child::para");
    }
    public void testPathExprAbbreviatedSample9 () throws IOException, RecognitionException
    {
    	String input = "/doc/chapter[5]/section[2]" ;
    	runTest(CMP_AST,      input, "(/ doc / chapter ([ 5) / section ([ 2))");
    	runTest(CMP_EXPR_TEXT,input, "/child::doc/child::chapter[5]/child::section[2]" );
    }
    
    public void testPathExprAbbreviatedSample10 () throws IOException, RecognitionException
    {
    	String input = "chapter//para" ;
    	runTest(CMP_AST,       input,   "chapter // para" );
    	runTest(CMP_EXPR_TEXT, input,   "child::chapter/descendant-or-self::node()/child::para" );
    }
    
    public void testPathExprAbbreviatedSample11 () throws IOException, RecognitionException
    {
    	String input = "//para";
    	runTest(CMP_AST,      input, "(// para)" );
    	runTest(CMP_EXPR_TEXT,input, "/descendant-or-self::node()/child::para");    	
    }
    
    public void testPathExprAbbreviatedSample12 () throws IOException, RecognitionException
    {
    	String input = "//olist/item";
    	runTest(CMP_EXPR_TEXT,  input , "/descendant-or-self::node()/child::olist/child::item" );
    }
    
    public void testPathExprAbbreviatedSample13 () throws IOException, RecognitionException
    {
    	String input = "."; 
    	runTest(CMP_AST,       input , "." );
    	runTest(CMP_EXPR_TEXT, input, "self::node()");
    }
    public void testPathExprAbbreviatedSample14 () throws IOException, RecognitionException
    {
    	String input = ".//para";
    	
    	runTest(CMP_EXPR_TEXT,  input , "self::node()/descendant-or-self::node()/child::para" );
    }
    
    public void testPathExprAbbreviatedSample15 () throws IOException, RecognitionException
    {
    	String input = "..";    	
    	runTest(CMP_AST,         input, ".." );
    	runTest(CMP_EXPR_TEXT,   input, "parent::node()");
    }
    
    
    public void testPathExprAbbreviatedSample16 () throws IOException, RecognitionException
    {
    	String input = "../@lang";
    	
    	runTest(CMP_AST,       input, ".. / @ lang" );
    	runTest(CMP_EXPR_TEXT, input, "parent::node()/attribute::lang");
    }

    public void testPathExprAbbreviatedSample17 () throws IOException, RecognitionException
    {
    	String input = "para[@type=\"warning\"]";
    	runTest(CMP_AST,       input, "para ([ (= @ type \"warning\"))" );
    	runTest(CMP_EXPR_TEXT, input, "child::para[attribute::type = \"warning\"]" );
    }
    
    public void testPathExprAbbreviatedSample18 () throws IOException, RecognitionException
    {
    	String input = "para[@type=\"warning\"][5]";
    	runTest(CMP_AST,       input, "para ([ (= @ type \"warning\")) ([ 5)" );
    	runTest(CMP_EXPR_TEXT, input, "child::para[attribute::type = \"warning\"][5]" );
    }      
    
    public void testPathExprAbbreviatedSample19 () throws IOException, RecognitionException
    {
    	String input = "para[5][@type=\"warning\"]";
    	runTest(CMP_AST,       input, "para ([ 5) ([ (= @ type \"warning\"))" );
    	runTest(CMP_EXPR_TEXT, input, "child::para[5][attribute::type = \"warning\"]" );
    } 

    public void testPathExprAbbreviatedSample20 () throws IOException, RecognitionException
    {
    	String input = "chapter[title=\"Introduction\"]";
    	runTest(CMP_AST,       input, "chapter ([ (= title \"Introduction\"))" );
    	runTest(CMP_EXPR_TEXT, input, "child::chapter[child::title = \"Introduction\"]" );
    } 

    public void testPathExprAbbreviatedSample21 () throws IOException, RecognitionException
    {
    	String input = "chapter[title]";
    	runTest(CMP_AST,       input, "chapter ([ title)" );
    	runTest(CMP_EXPR_TEXT, input, "child::chapter[child::title]" );
    } 
    
    public void testPathExprAbbreviatedSample22 () throws IOException, RecognitionException
    {
    	String input = "employee[@secretary and @assistant]";
    	runTest(CMP_AST,       input, "employee ([ (and @ secretary @ assistant))" );
    	runTest(CMP_EXPR_TEXT, input, "child::employee[attribute::secretary and attribute::assistant]" );
    } 
    
    
}
