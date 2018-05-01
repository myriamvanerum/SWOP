package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SyntaxCheckerTest {
	SyntaxChecker syntaxChecker = new SyntaxChecker();

	@Test
	void testSuccess() {
		
		String label = "label:Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testSuccessStartColon() {
		
		String label = ":Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertTrue(syntaxCorrect);
	}
	
	@Test
	void testFailHasSpace() {
		
		String label = "label :Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testFailNoColon() {
		
		String label = "labelLabel";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}

	@Test
	void testFailNoLowerCase() {
		
		String label = "Label:Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testFailNoUpperCase() {
		
		String label = "label:label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
	
	@Test
	void testFailMultipleColons() {
		
		String label = "Label:Label:Label:Label";
		Boolean syntaxCorrect = syntaxChecker.correctPartyLabelSyntax(label);
		
		assertFalse(syntaxCorrect);
	}
}
