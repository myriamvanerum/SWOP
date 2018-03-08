package model;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;

import org.junit.jupiter.api.Test;

import model.Label;

class LabelTest {

	Label label = new Label(1,1,"");
	Graphics g;
	
	// Incorrect label format	
	@Test
	void correctSyntaxTestNoColon() {
		label.setText("test");
		assertFalse(label.correctSyntax());
	}
	
	@Test
	void correctSyntaxTestMultipleColons() {
		label.setText("test:test:test:test");
		assertFalse(label.correctSyntax());
	}
	
	@Test
	void correctSyntaxTestInstanceNameUpperCaseClassNameLowerCase() {
		label.setText("InstanceName:className");
		assertFalse(label.correctSyntax());
	}
	
	@Test
	void correctSyntaxTestInstanceNameUpperCaseClassNameUpperCase() {
		label.setText("InstanceName:className");
		assertFalse(label.correctSyntax());
	}
	
	@Test
	void correctSyntaxTestInstanceNameLowerCaseClassNameLowerCase() {
		label.setText("instanceName:className");
		assertFalse(label.correctSyntax());
	}
	
	@Test
	void correctSyntaxTestNoInstanceNameClassNameLowerCase() {
		label.setText(":className");
		assertFalse(label.correctSyntax());
	}
	
	// Correct label format
	@Test
	void correctSyntaxTestNoInstanceNameClassNameUpperCase() {
		label.setText(":ClassName");
		assertTrue(label.correctSyntax());
	}
		
	@Test
	void correctSyntaxTestInstanceNameLowerCaseClassNameUpperCase() {
		label.setText("instanceName:ClassName");
		assertTrue(label.correctSyntax());
	}
	
	// width
	@Test
	void labelWidthTest() {
		assertEquals(0, label.getWidth());
	}
	
	@Test
	void labelTest() {
		Label newLabel = new Label();
		assertEquals(null, newLabel.getText());
		assertEquals(0, newLabel.getWidth());
		assertEquals(0, newLabel.getX());
		assertEquals(0, newLabel.getY());
		
		newLabel.setText("test");
		newLabel.setX(10);
		newLabel.setY(10);
		
		assertEquals("test", newLabel.getText());
		assertEquals(10, newLabel.getX());
		assertEquals(10, newLabel.getY());
		assertEquals("test", newLabel.toString());
	}

}
