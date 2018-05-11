package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import domain.SyntaxChecker;

class SyntaxCheckerTest {
	SyntaxChecker syntaxChecker = new SyntaxChecker();

	@Test
	void testPartyLabelSuccess() {
		
		String label = "label:Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testPartyLabelSuccessStartColon() {
		
		String label = ":Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testPartyLabelFailHasSpace() {
		
		String label = "label :Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testPartyLabelFailNoColon() {
		
		String label = "labelLabel";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}

	@Test
	void testPartyLabelFailNoLowerCase() {
		
		String label = "Label:Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testPartyLabelFailNoUpperCase() {
		
		String label = "label:label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testPartyLabelFailMultipleColons() {
		
		String label = "Label:Label:Label:Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelSuccess() {
		
		String label = "label(arg1, arg2)";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelSuccessNoArgs() {
		
		String label = "label()";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelFailNoLeftParenthesis() {
		
		String label = "label)";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelFailNoRightParenthesis() {
		
		String label = "label(";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelFailRightParenthesisNotLast() {
		
		String label = "label()end";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelFailMultipleLeftParentheses() {
		
		String label = "label((en(d)";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelFailMultipleRightParentheses() {
		
		String label = "label()e)nd)";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
}
