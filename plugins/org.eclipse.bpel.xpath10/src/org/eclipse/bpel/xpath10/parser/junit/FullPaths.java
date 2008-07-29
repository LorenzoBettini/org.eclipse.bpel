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
public class FullPaths extends AbstractTC {

    
    public void testPathExprFullSample1 () throws IOException, RecognitionException
    {
    	String input = "child::para";
    	
    	runTest(CMP_EXPR_TEXT, input, "child::para" );
    	runTest(CMP_AST,       input, "(:: child) para" );
    }
    
    public void testPathExprFullSample1NS () throws IOException, RecognitionException
    {
    	String input = "child::ns:para";
    	
    	runTest(CMP_EXPR_TEXT, input, "child::ns:para" );
    	runTest(CMP_AST,       input, "(:: child) ns : para" );
    }
    
    
    public void testPathExprFullSample2 () throws IOException, RecognitionException
    {
    	String input = "child::*";
    	runTest(CMP_EXPR_TEXT, input, "child::*");
    	runTest(CMP_AST,       input, "(:: child) *" );
    }

    public void testPathExprFullSample2NS () throws IOException, RecognitionException
    {
    	String input = "child::ns:*";
    	runTest(CMP_EXPR_TEXT, input, "child::ns:*");
    	runTest(CMP_AST,       input, "(:: child) ns : *" );
    }

    public void testPathExprFullSample3 () throws IOException, RecognitionException
    {
    	String input = "child::text()";
    	runTest(CMP_EXPR_TEXT,   input,  "child::text()" );
    }
    
    public void testPathExprFullSample3Absolute () throws IOException, RecognitionException
    {
    	String input = "/child::text ( )";
    	runTest(CMP_EXPR_TEXT,    input, "/child::text()" );
    }
    
    public void testPathExprFullSample4 () throws IOException, RecognitionException
    {
    	String input = "attribute::name";
    	runTest(CMP_EXPR_TEXT,input,  "attribute::name");    	
    }

    public void testPathExprFullSample5 () throws IOException, RecognitionException
    {
    	String input = "attribute::*";
    	runTest(CMP_EXPR_TEXT, input,  "attribute::*");
    }
    
    public void testPathExprFullSample6 () throws IOException, RecognitionException
    {
    	String input = "child::para [ 1 ] " ;
    	runTest(CMP_EXPR_TEXT, input, "child::para[1]" );
    }
    
    public void testPathExprFullSample7 () throws IOException, RecognitionException
    {
    	String input = "child::para [ last ( ) ]";
    	runTest(CMP_EXPR_TEXT,input, "child::para[last()]");    			
    }
    
    public void testPathExprFullSample8 () throws IOException, RecognitionException
    {
    	String input = "child::*/child::para";    	
    	runTest(CMP_EXPR_TEXT, input, "child::*/child::para");
    }
    public void testPathExprFullSample9 () throws IOException, RecognitionException
    {
    	String input = " / child::doc / child::chapter  [ 5  ] /  section [ 2   ]" ;
    	runTest(CMP_EXPR_TEXT,input, "/child::doc/child::chapter[5]/child::section[2]" );
    }
    
    public void testPathExprFullSample10 () throws IOException, RecognitionException
    {
    	String input = "child::chapter//child::para" ;
    	runTest(CMP_EXPR_TEXT, input,   "child::chapter/descendant-or-self::node()/child::para" );
    }
    
    public void testPathExprFullSample11 () throws IOException, RecognitionException
    {
    	String input = "//  child::para";
    	runTest(CMP_EXPR_TEXT,input, "/descendant-or-self::node()/child::para");    	
    }
    
    public void testPathExprFullSample12 () throws IOException, RecognitionException
    {
    	String input = "//  child::olist /  child::item";
    	runTest(CMP_EXPR_TEXT,  input , "/descendant-or-self::node()/child::olist/child::item" );
    }
    
    public void testPathExprFullSample13 () throws IOException, RecognitionException
    {
    	String input = "self::node()"; 
    	runTest(CMP_EXPR_TEXT, input, "self::node()");
    }
    public void testPathExprFullSample14 () throws IOException, RecognitionException
    {
    	String input = "self::node() /descendant-or-self::node()  / child::para";
    	
    	runTest(CMP_EXPR_TEXT,  input , "self::node()/descendant-or-self::node()/child::para" );
    }
    
    public void testPathExprFullSample15 () throws IOException, RecognitionException
    {
    	String input = "parent::node ( ) ";    	
    	runTest(CMP_EXPR_TEXT,   input, "parent::node()");
    }
    
    
    public void testPathExprFullSample16 () throws IOException, RecognitionException
    {
    	String input = "parent::node(  ) / attribute::lang";
    	runTest(CMP_EXPR_TEXT, input, "parent::node()/attribute::lang");
    }

    public void testPathExprFullSample17 () throws IOException, RecognitionException
    {
    	String input = "child::para [ attribute::type  =   \"warning\" ] ";
    	runTest(CMP_EXPR_TEXT, input, "child::para[attribute::type = \"warning\"]" );
    }
    
    public void testPathExprFullSample18 () throws IOException, RecognitionException
    {
    	String input = "child::para[ attribute::type = \"warning\" ] [   5   ] ";
    	runTest(CMP_EXPR_TEXT, input, "child::para[attribute::type = \"warning\"][5]" );
    }      
    
    public void testPathExprFullSample19 () throws IOException, RecognitionException
    {
    	String input = "child::para  [ 5 ] [  attribute::type =  \"warning\"  ] ";
    	runTest(CMP_EXPR_TEXT, input, "child::para[5][attribute::type = \"warning\"]" );
    } 

    public void testPathExprFullSample20 () throws IOException, RecognitionException
    {
    	String input = "child::chapter  [  child::title  =   \"Introduction\"]";
    	runTest(CMP_EXPR_TEXT, input, "child::chapter[child::title = \"Introduction\"]" );
    } 

    public void testPathExprFullSample21 () throws IOException, RecognitionException
    {
    	String input = "child::chapter  [  child::title ]";
    	runTest(CMP_EXPR_TEXT, input, "child::chapter[child::title]" );
    } 
    
    public void testPathExprFullSample22 () throws IOException, RecognitionException
    {
    	String input = "child::employee [attribute::secretary and attribute::assistant]";
    	runTest(CMP_EXPR_TEXT, input, "child::employee[attribute::secretary and attribute::assistant]" );
    } 
    
    
}
