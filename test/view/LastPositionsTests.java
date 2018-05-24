package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

class LastPositionsTests {
	Point2D previousClickedPosition = new Point2D.Double(50,50);
	Point2D lastClickedPosition = new Point2D.Double(100,100);
	Point2D newClickedPosition = new Point2D.Double(150,150);
	LastPositions lastPositions = new LastPositions(previousClickedPosition, lastClickedPosition);

	@Test
	void testGetters() {
		assertEquals(previousClickedPosition, lastPositions.getPreviousClickedPosition());
		assertEquals(lastClickedPosition, lastPositions.getLastClickedPosition());
	}

	@Test
	void testClickSamePos() {
		lastPositions.setLastClickedPosition(lastClickedPosition);
		assertEquals(previousClickedPosition, lastPositions.getPreviousClickedPosition());
		assertEquals(lastClickedPosition, lastPositions.getLastClickedPosition());
	}
	
	@Test
	void testClickNewPos() {		
		lastPositions.setLastClickedPosition(newClickedPosition);
		assertEquals(lastClickedPosition, lastPositions.getPreviousClickedPosition());
		assertEquals(newClickedPosition, lastPositions.getLastClickedPosition());
	}
}
