package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import domain.party.Object;
import domain.party.Party;
import view.components.ViewObject;

class CombinedViewTests {

	@Test
	void testViewObject() {
		Party party = new Object("Object");
		Point2D clickPosition= new Point2D.Double(100,100);
		ViewObject obj = new ViewObject(party, clickPosition);
		
		int height = 20, width = 36;
		
		obj.setHeight(height);
		obj.setWidth(width);
		
		assertEquals(height, obj.getHeight());
		assertEquals(width, obj.getWidth());
	}

}
