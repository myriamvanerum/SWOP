package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;
import view.components.ViewActor;
import view.components.ViewObject;
import view.components.ViewParty;

class ViewPartyTests {
	Party actor = new Actor("actor");
	Party object = new Object("object");
	Point2D clickPos = new Point2D.Double(100,100);
	Point2D windowPos = new Point2D.Double(50,50);
	ViewParty viewActor = new ViewActor(actor, clickPos, windowPos);
	ViewParty viewObject = new ViewObject(object, clickPos, windowPos);

	@Test
	void testCopy() {
		ViewParty viewParty = viewActor.copy();
		assertEquals(actor, viewParty.getParty());
		assertTrue(viewParty instanceof ViewActor);
		
		viewParty = viewObject.copy();
		assertEquals(object, viewParty.getParty());
		assertTrue(viewParty instanceof ViewObject);
	}

	@Test
	void testChangeType() {
		ViewParty viewParty = viewActor.changeType();
		assertEquals(actor, viewParty.getParty());
		assertTrue(viewParty instanceof ViewObject);
		
		viewParty = viewObject.changeType();
		assertEquals(object, viewParty.getParty());
		assertTrue(viewParty instanceof ViewActor);
	}
	
	@Test
	void testPositionWindow() {
		Point2D pos = viewActor.positionWindow(clickPos, windowPos);
		assertEquals(new Point2D.Double(150,150), pos);
	}
}
