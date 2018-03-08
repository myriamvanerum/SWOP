package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.DiagramComponent;

class DiagramComponentTest {

	@Test
	void test() {
		DiagramComponent component = new DiagramComponent();
		
		component.focus();
		assertEquals(true, component.focused());
		component.unfocus();
		assertEquals(false, component.focused());
	}

}
