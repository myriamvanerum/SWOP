package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
		
		String label = "label(arg1,arg2)";
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
	void testInvocationMessageLabelFailNoLowerCase() {
		
		String label = "Label()";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
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
	
	@Test
	void testInvocationMessageLabelFailOnlyComma() {
		String label = "label(,)";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelFailArgumentsEndWithComma() {
		String label = "label(argument,)";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelFailHasSpace() {
		String label = "label(argume nt, arg2)";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageLabelSyntax(label);
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelMethodSuccess() {
		
		String label = "labEL123";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageMethod(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelMethodFailNoLowerCase() {
		
		String label = "LabEL123";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageMethod(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelMethodFailCharacterNotAllowed() {
		
		String label = "label@123";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageMethod(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelArgumentSuccess() {
		
		String label = "arg1_here";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageArgument(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelArgumentFailContainsComma() {
		
		String label = "arg1_he,re";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageArgument(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelArgumentFailContainsParentheses() {
		String label = "arg1_he)re";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageArgument(label);
		assertFalse(syntaxCorrect);
		
		label = "arg1_he(re";
		syntaxCorrect = syntaxChecker.correctInvocationMessageArgument(label);
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testInvocationMessageLabelArgumentFailContainsSpace() {
		String label = "arg1 here";
		Boolean syntaxCorrect = syntaxChecker.correctInvocationMessageArgument(label);
		assertFalse(syntaxCorrect);
	}
}
