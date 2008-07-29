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
public class Specials extends AbstractTC {

 
    public void testSpecialsText () throws IOException, RecognitionException
    {
    	String input = "text () ";
    	runTest(CMP_EXPR_TEXT, input, "child::text()" );
    }
    public void testSpecialsTextAbs () throws IOException, RecognitionException
    {
    	String input = "/text () ";
    	runTest(CMP_EXPR_TEXT, input, "/child::text()" );
    }
    
    public void testSpecialsNode () throws IOException, RecognitionException
    {
    	String input = "node () ";
    	runTest(CMP_EXPR_TEXT, input, "child::node()" );
    }
    

    public void testSpecialsNodeAbs () throws IOException, RecognitionException
    {
    	String input = "/node () ";
    	runTest(CMP_EXPR_TEXT, input, "/child::node()" );
    }
    
    public void testSpecialsPI () throws IOException, RecognitionException
    {
    	String input = "processing-instruction () ";
    	runTest(CMP_EXPR_TEXT, input, "child::processing-instruction()" );
    }
    

    public void testSpecialsPIAbs () throws IOException, RecognitionException
    {
    	String input = "/processing-instruction () ";
    	runTest(CMP_EXPR_TEXT, input, "/child::processing-instruction()" );
    }
    
    public void testTextAsNamespacedFunctionCall () throws IOException, RecognitionException
    {
    	String input = "ns1:text ( 1 , 5, 9 ) ";
    	runTest(CMP_EXPR_TEXT, input, "ns1:text(1, 5, 9)" );
    }
    public void testNodeAsNamespacedFunctionCall () throws IOException, RecognitionException
    {
    	String input = "ns1:node ( 1 , 5, 9 ) ";
    	runTest(CMP_EXPR_TEXT, input, "ns1:node(1, 5, 9)" );
    }
    public void testCommentAsNamespacedFunctionCall () throws IOException, RecognitionException
    {
    	String input = "ns1:comment ( 1 , 5, 9 ) ";
    	runTest(CMP_EXPR_TEXT, input, "ns1:comment(1, 5, 9)" );
    }
    public void testPIAsNamespacedFunctionCall () throws IOException, RecognitionException
    {
    	String input = "ns1:processing-instruction ( 1 , 5, 9 ) ";
    	runTest(CMP_EXPR_TEXT, input, "ns1:processing-instruction(1, 5, 9)" );
    }
    
}
