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
public class Namespace extends AbstractTC {

    public void testQNameSteps() throws IOException, RecognitionException {
    	String input = "ns1:foo";    	
        runTest(CMP_EXPR_TEXT, input ,   "child::ns1:foo");
        runTest(CMP_AST,       input ,   "ns1 : foo");
    }

    public void testQNameFunctionCall() throws IOException, RecognitionException {
    	String input = "ns1:foo ( ns2:bar () )";    	        
        runTest(CMP_EXPR_TEXT, input ,   "ns1:foo(ns2:bar())");
    }

    public void testQNameFunctionCallArg1LocationPath () throws IOException, RecognitionException {
    	String input = "ns1:foo ( bar )";    	        
        runTest(CMP_EXPR_TEXT, input ,   "ns1:foo(child::bar)");
    }

    public void testQNameFunctionCallArg1LocationPathNS () throws IOException, RecognitionException {
    	String input = "ns1:foo ( ns:bar )";    	        
        runTest(CMP_EXPR_TEXT, input ,   "ns1:foo(child::ns:bar)");
    }

    public void testQNameStepsAbs() throws IOException, RecognitionException {
    	String input = "/ns1:foo/ns2:bar";    	     
        runTest(CMP_EXPR_TEXT, input ,   "/child::ns1:foo/child::ns2:bar");
    }
    public void testQNameStepsVarRef() throws IOException, RecognitionException {
    	String input = "$ns1:var/ns1:foo/ns2:foo";
    	
        runTest(CMP_EXPR_TEXT, input ,   "$ns1:var/child::ns1:foo/child::ns2:foo");
        runTest(CMP_AST,       input ,   "($ (: ns1 var)) / ns1 : foo / ns2 : foo");
    }

    public void testQNameVar () throws IOException, RecognitionException {
    	String input = "$ns1:foo";
    	
        runTest(CMP_AST,       input ,   "($ (: ns1 foo))");
        runTest(CMP_EXPR_TEXT, input ,   "$ns1:foo");
    }

    public void testQNameFunction () throws IOException, RecognitionException {
    	String input = "ns1:func ( 1, 'A', 3.14 )";
    	
        runTest(CMP_AST,       input ,   "(: ns1 func) (Args 1 'A' 3.14)");
        runTest(CMP_EXPR_TEXT, input ,   "ns1:func(1, 'A', 3.14)");
    }
    
    
}
