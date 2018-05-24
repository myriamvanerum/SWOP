package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import view.windows.DiagramWindow;
import view.windows.SubWindow;

class SubWindowTests {
	ViewInteraction viewInteraction = new ViewInteraction();
	SubWindow activeWindow = new DiagramWindow(viewInteraction, 10, 10);
	Point2D clickPos = new Point2D.Double(100,100);

	@Test
	void testConstruct() {
		Integer width = 500, height = 400;
		assertEquals(width, activeWindow.getWidth());
		assertEquals(height, activeWindow.getHeight());
		
		Integer x = 10, y = 10;
		assertEquals(x, activeWindow.getX());
		assertEquals(y, activeWindow.getY());
	}
	
	@Test
	void testClickOutsideActiveWindow() {
		Boolean outside = activeWindow.clickOutsideActiveSubwindow(5, 5);
		assertTrue(outside);
		
		outside = activeWindow.clickOutsideActiveSubwindow(50, 50);
		assertFalse(outside);
		
		outside = activeWindow.clickOutsideActiveSubwindow(800, 50);
		assertTrue(outside);
	}

	@Test
	void testClickCloseButton() {
		Boolean close = activeWindow.clickCloseButton(5, 5);
		assertFalse(close);
		
		close = activeWindow.clickCloseButton(500, 15);
		assertTrue(close);
	}
}
