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
public class Expressions extends AbstractTC {

    public void testStringDoubleQuote() throws IOException, RecognitionException {
    	String input = "\"a b c d\"";
    	
    	runTest(CMP_EXPR_TEXT, input, "\"a b c d\"");
        runTest(CMP_AST, "\"a b c d\"", "\"a b c d\"");        
    }
    public void testStringSingleQuote() throws IOException, RecognitionException {
    	String input = " 'ab cd' ";
    	runTest(CMP_EXPR_TEXT, input,  "'ab cd'");
        runTest(CMP_AST,       input , "'ab cd'");        
    }

    public void testPositiveInteger() throws IOException, RecognitionException {
    	String input = "  12345  ";
    	runTest(CMP_EXPR_TEXT, input, "12345");
        runTest(CMP_AST,       input, "12345");
    }

    public void testNegativeInteger() throws IOException, RecognitionException {
    	String input = "   -12345  ";
    	runTest(CMP_EXPR_TEXT, input,  "-12345");
        runTest(CMP_AST,       input , "- 12345");
    }
    
    public void testPositiveFloat () throws IOException, RecognitionException {
    	String input = " 12345.01 ";
    	runTest(CMP_EXPR_TEXT,  input, "12345.01");
    	runTest(CMP_AST,        input, "12345.01");
    }

    public void testNegativeFloat () throws IOException, RecognitionException {
    	String input = "   -12345.01  ";
    	runTest(CMP_EXPR_TEXT, input, "-12345.01");
    	runTest(CMP_AST,       input, "- 12345.01");
    }

    public void testNegativeFloatNoLeading0 () throws IOException, RecognitionException {
    	String input = " -.01 ";
    	runTest(CMP_EXPR_TEXT,  input, "-.01");
    	runTest(CMP_AST,        input, "- .01");
    }

    public void testFloatNoLeading0 () throws IOException, RecognitionException {
    	String input = "  .01 ";
    	runTest(CMP_EXPR_TEXT,   input, ".01");
    	runTest(CMP_AST,         input, ".01");
    }

    public void testFloatNoLeading0Dot() throws IOException, RecognitionException {
    	String input = " .  +  01 "; 
    	runTest(CMP_EXPR_TEXT,   input, "self::node() + 01");
    	runTest(CMP_AST,         input, "(+ . 01)");
    }

    
    // Additive Expression with + operator
    public void testAdditiveExpr () throws IOException, RecognitionException
    {
    	String input = " 12    +  13 ";
    	runTest(CMP_EXPR_TEXT, input, "12 + 13");
    	runTest(CMP_AST,       input, "(+ 12 13)");
    }
    public void testAdditiveExpr3 () throws IOException, RecognitionException
    {
    	String input = "  12   +   13 + 7  ";
    	runTest(CMP_EXPR_TEXT,input, "12 + 13 + 7");
    	runTest(CMP_AST,      input, "(+ (+ 12 13) 7)");
    }
    
    public void testAdditiveExpr3P () throws IOException, RecognitionException
    {
    	String input = "    12   +(13+ 7)";
    	runTest(CMP_EXPR_TEXT, input,  "12 + (13 + 7)");
    	runTest(CMP_AST,       input,  "(+ 12 (+ 13 7))");
    	
    }
    
    // Additive Expression with - operator
    public void testAdditiveExprMinus() throws IOException, RecognitionException
    {
    	String input = "   12-14";
    	runTest(CMP_EXPR_TEXT, input, "12 - 14");
    	runTest(CMP_AST,       input, "(- 12 14)");
    }
    public void testAdditiveExprMinus3() throws IOException, RecognitionException
    {
    	String input = " 12 - 14-15  ";
    	runTest(CMP_EXPR_TEXT,  input, "12 - 14 - 15");
    	runTest(CMP_AST,        input, "(- (- 12 14) 15)");
    }
    
    public void testAdditiveExprMinus3P() throws IOException, RecognitionException
    {
    	String input = "    12-(14-15)";
    	runTest(CMP_EXPR_TEXT, input, "12 - (14 - 15)");
    	runTest(CMP_AST,       input, "(- 12 (- 14 15))");
    }

    // Multiplicative Expression with * operator
    public void testMultiplicativeExprTimes() throws IOException, RecognitionException
    {
    	String input = " 12 * 14 ";
    	runTest(CMP_EXPR_TEXT, input, "12 * 14");
    	runTest(CMP_AST,       input, "(* 12 14)");
    }
    
    public void testMultiplicativeExprTimes3() throws IOException, RecognitionException
    {
    	String input = "12*14*15";
    	runTest(CMP_EXPR_TEXT,  input, "12 * 14 * 15");
    	runTest(CMP_AST,        input, "(* (* 12 14) 15)");
    }
    
    public void testMultiplicativeExprTimes3P() throws IOException, RecognitionException
    {
    	String input = "12*(14*15)";
    	
    	runTest(CMP_EXPR_TEXT, input, "12 * (14 * 15)");
    	runTest(CMP_AST,       input, "(* 12 (* 14 15))");
    }
    
    // Multiplicative Expression with MOD operator
    public void testMultiplicativeExprMOD() throws IOException, RecognitionException
    {
    	String input = "12 mod 14";
    	runTest(CMP_EXPR_TEXT, input, "12 mod 14");
    	runTest(CMP_AST,       input, "(mod 12 14)");
    }
    
    public void testMultiplicativeExprMOD3() throws IOException, RecognitionException
    {
    	String input = "12 mod   14 mod 7";
    	runTest(CMP_EXPR_TEXT, input, "12 mod 14 mod 7");
    	runTest(CMP_AST,       input, "(mod (mod 12 14) 7)");
    }

    public void testMultiplicativeExprMOD3P() throws IOException, RecognitionException
    {
    	String input = " 12  mod ( 14  mod  7)";
    	runTest(CMP_EXPR_TEXT, input, "12 mod (14 mod 7)");
    	runTest(CMP_AST,       input, "(mod 12 (mod 14 7))");
    }

    // Multiplicative Expression with DIV operator
    public void testMultiplicativeExprDIV() throws IOException, RecognitionException
    {
    	String input = "12 div 14";
    	runTest(CMP_EXPR_TEXT, input, "12 div 14");
    	runTest(CMP_AST,       input, "(div 12 14)");
    }

    public void testMultiplicativeExprDIV3() throws IOException, RecognitionException
    {
    	String input = " 12 div   14   div 7";
    	runTest(CMP_EXPR_TEXT, input, "12 div 14 div 7");
    	runTest(CMP_AST,       input, "(div (div 12 14) 7)");
    }

    public void testEqualityExprEQ() throws IOException, RecognitionException
    {
    	String input = " 12  = 14";
    	
    	runTest(CMP_EXPR_TEXT, input, "12 = 14");
    	runTest(CMP_AST,       input, "(= 12 14)");
    }
    
    
    public void testEqualitylExprNEQ() throws IOException, RecognitionException
    {
    	String input = " 12 != 14 ";
    	runTest(CMP_EXPR_TEXT, input, "12 != 14");
    	runTest(CMP_AST,       input, "(!= 12 14)");
    }
    
    // AND Logical Expressions
    public void testLogicalAndExpr() throws IOException, RecognitionException
    {
    	String input = "12 and 14";
    	runTest(CMP_EXPR_TEXT, input, "12 and 14");
    	runTest(CMP_AST,       input, "(and 12 14)");
    }

    public void testLogicalAndExpr3() throws IOException, RecognitionException
    {
    	String input = " 12 and 14 and 15";
    	
    	runTest(CMP_EXPR_TEXT, input, "12 and 14 and 15");
    	runTest(CMP_AST,       input, "(and (and 12 14) 15)");
    }

    public void testLogicalAndExpr3P () throws IOException, RecognitionException
    {
    	String input = " 12 and (14 and 15)";
    	
    	runTest(CMP_EXPR_TEXT, input, "12 and (14 and 15)");    	
    	runTest(CMP_AST,       input, "(and 12 (and 14 15))");
    	
    }

    // OR Logical Expressions
    public void testLogicalORExpr() throws IOException, RecognitionException
    {
    	String input = "12 or 14";
    	runTest(CMP_EXPR_TEXT, input, "12 or 14");
    	runTest(CMP_AST,       input, "(or 12 14)");
    }

    public void testLogicalOrExpr3() throws IOException, RecognitionException
    {
    	String input = "12 or 14 or 15";
    	runTest(CMP_EXPR_TEXT, input, "12 or 14 or 15");
    	runTest(CMP_AST,       input, "(or (or 12 14) 15)");
    }

    public void testLogicalOrExpr3P () throws IOException, RecognitionException
    {
    	String input =   "12 or (14 or 15))";
    	
    	runTest(CMP_EXPR_TEXT, input, "12 or (14 or 15)");
    	runTest(CMP_AST,       input, "(or 12 (or 14 15))");
    }

          
    public void testVariableReference ()  throws IOException, RecognitionException
    {
    	String input = "$foo";
    	runTest(CMP_EXPR_TEXT, input, "$foo");
    	runTest(CMP_AST, input, "($ foo)");
    }
    
    public void testVariableReference2 ()  throws IOException, RecognitionException
    {
    	String input = "$foo:bar";
    	runTest(CMP_EXPR_TEXT, input, "$foo:bar");
    	runTest(CMP_AST,       input, "($ (: foo bar))");
    }
    
    public void testExpr2() throws IOException, RecognitionException
    {
    	String input = " ( 12+ $foo )-(12   +$bar)";
    	runTest(CMP_EXPR_TEXT, input, "(12 + $foo) - (12 + $bar)");
    	runTest(CMP_AST,       input, "(- (+ 12 ($ foo)) (+ 12 ($ bar)))");
    }

    public void testExpr3() throws IOException, RecognitionException
    {
    	String input = " 12 + ( $foo   - 13)";
    	
    	runTest(CMP_EXPR_TEXT, input, "12 + ($foo - 13)");    	
    	runTest(CMP_AST, input,   "(+ 12 (- ($ foo) 13))");
    }

   
    public void testFuncCallNoArgs () throws IOException, RecognitionException
    {
    	String input = "foobar()";
    	runTest(CMP_EXPR_TEXT, input, "foobar()");
    	runTest(CMP_AST,       input, "foobar");
    }
    public void testFuncCall1 () throws IOException, RecognitionException
    {
    	String input = "foobar('Hello')";
    	runTest(CMP_EXPR_TEXT, input, "foobar('Hello')");    	
    	runTest(CMP_AST, input, "foobar (Args 'Hello')");
    }
    
    public void testFuncCall2 () throws IOException, RecognitionException
    {
    	String input = "foobar(2+$var)";
    	runTest(CMP_EXPR_TEXT, input, "foobar(2 + $var)");
    	runTest(CMP_AST, input, "foobar (Args (+ 2 ($ var)))");
    }
    
    public void testFuncCall3 () throws IOException, RecognitionException
    {
    	String input = "foobar(2+$var,'Hello',12)";
    	runTest(CMP_EXPR_TEXT, input, "foobar(2 + $var, 'Hello', 12)");
    	runTest(CMP_AST,       input, "foobar (Args (+ 2 ($ var)) 'Hello' 12)" );
    }
    
    public void testExpr4() throws IOException, RecognitionException
    {
    	String input = "$foo mod 5";
    	runTest(CMP_EXPR_TEXT, input, "$foo mod 5");    	
    	runTest(CMP_AST, input ,"(mod ($ foo) 5)");
    }
    
    public void testExpr5() throws IOException, RecognitionException
    {
    	String input = "$foo/bar mod 5";
    	runTest(CMP_EXPR_TEXT,  input, "$foo/child::bar mod 5");
    	runTest(CMP_AST, "$foo//bar mod 5", "(mod ($ foo) // bar 5)");
    }
   
    public void testExpr6() throws IOException, RecognitionException
    {
    	String input = "$foo//bar mod 5";
    	runTest(CMP_EXPR_TEXT,  input, "$foo/descendant-or-self::node()/child::bar mod 5");
    	runTest(CMP_AST, "$foo//bar mod 5", "(mod ($ foo) // bar 5)");
    }
   
    
    public void testPathExprAxis () throws IOException, RecognitionException
    {
    	String input = "/descendant::ns1:foo/parent::ns2:bar/child::child";
    	
    	runTest(CMP_EXPR_TEXT, input, "/descendant::ns1:foo/parent::ns2:bar/child::child");
    	runTest(CMP_AST,       input, "(/ (:: descendant) ns1 : foo / (:: parent) ns2 : bar / (:: child) child)");
    }
    
    public void testPathExprAxis2 () throws IOException, RecognitionException
    {
    	String input = "/ancestor::ns1:foo/following-sibling::ns2:sib/preceding-sibling::sib2";
    	runTest(CMP_EXPR_TEXT, input, "/ancestor::ns1:foo/following-sibling::ns2:sib/preceding-sibling::sib2");
    	runTest(CMP_AST,       input, "(/ (:: ancestor) ns1 : foo / (:: following-sibling) ns2 : sib / (:: preceding-sibling) sib2)");
    }
    
    public void testPathExprAxis3 () throws IOException, RecognitionException
    {
    	String input = "/following::ns1:foo/preceding::ns2:sib/attribute::attr2";
    	runTest(CMP_EXPR_TEXT, input, "/following::ns1:foo/preceding::ns2:sib/attribute::attr2" );
    	runTest(CMP_AST,       input, "(/ (:: following) ns1 : foo / (:: preceding) ns2 : sib / (:: attribute) attr2)");
    }
    public void testPathExprAxis4 () throws IOException, RecognitionException
    {
    	String input = "/namespace::ns1:foo/self::ns2:sib/descendant-or-self::ds/ancestor-or-self::ns:as";
    	runTest(CMP_EXPR_TEXT, input, "/namespace::ns1:foo/self::ns2:sib/descendant-or-self::ds/ancestor-or-self::ns:as");
    	runTest(CMP_AST,       input, "(/ (:: namespace) ns1 : foo / (:: self) ns2 : sib / (:: descendant-or-self) ds / (:: ancestor-or-self) ns : as)");
    }
        

    public void testPathExprAxisBadAxis () throws IOException, RecognitionException
    {
    	String input = "/badaxis::foo";
    	
    	runTest(CMP_EXPR_TEXT, input, "/unknown-axis-name::foo") ;
    	runTest(CMP_AST,       input, "(/ (:: badaxis) foo)");
    }
    
 
    
    public void testPathExprSlashSlash () throws IOException, RecognitionException
    {
    	String input = "//foo//bar";
    	runTest(CMP_EXPR_TEXT, input, "/descendant-or-self::node()/child::foo/descendant-or-self::node()/child::bar");    	
    	runTest(CMP_AST, input, "(// foo // bar)");
    }
     
 

    
    public void testQuery () throws IOException, RecognitionException
    {
    	String input = "$v//bar";
    	runTest(CMP_EXPR_TEXT, input, "$v/descendant-or-self::node()/child::bar");
    	runTest(CMP_AST, input, "($ v) // bar");
    }    

    public void testQuery2 () throws IOException, RecognitionException
    {
    	String input = "$v/bar";
    	runTest(CMP_EXPR_TEXT, input, "$v/child::bar");
    	runTest(CMP_AST, input, "($ v) / bar");
    }    

}
