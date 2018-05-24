package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import domain.message.InvocationMessage;
import domain.message.ResultMessage;
import domain.party.Actor;
import domain.party.Object;
import domain.party.Party;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.diagramstate.ComState;
import view.diagramstate.SeqState;
import view.windows.DiagramWindow;

class DiagramWindowTests {
	ViewInteraction viewInteraction = new ViewInteraction();
	DiagramWindow activeWindow = new DiagramWindow(viewInteraction, 10, 10);
	Party actor = new Actor("actor");
	Party object = new Object("object");
	Party party = new Actor("party");
	InvocationMessage invocation = new InvocationMessage("message", actor, object);	
	ResultMessage result = new ResultMessage("result", object, actor);
	InvocationMessage message = new InvocationMessage("message", actor, object);	
	Point2D clickPos = new Point2D.Double(100,100);
	
	public void setup() {
		invocation.setCompanion(result);
		result.setCompanion(invocation);
		
		activeWindow.addViewParty(actor, clickPos);
		activeWindow.addViewParty(object, clickPos);
		activeWindow.addViewMessage(invocation, clickPos);
	}

	@Test
	void testConstruct() {
		setup();
		
		Integer x = 20, y = 20;
		DiagramWindow diagram = new DiagramWindow(activeWindow, x, y);
		assertEquals(x, diagram.getX());
		assertEquals(y, diagram.getY());
		assertEquals(viewInteraction, diagram.getViewInteraction());
		assertEquals(2, diagram.getViewParties().size());
		assertEquals(2, diagram.getViewMessages().size());
	}

	@Test
	void testChangeState() {
		setup();
		assertTrue(activeWindow.getState() instanceof SeqState);
		
		activeWindow.changeState();
		assertTrue(activeWindow.getState() instanceof ComState);
		
		activeWindow.changeState();
		assertTrue(activeWindow.getState() instanceof SeqState);
	}
	
	@Test
	void testFindViewParty() {
		setup();
		
		ViewParty viewParty = activeWindow.findViewParty(actor);
		assertEquals(actor, viewParty.getParty());
		
		viewParty = activeWindow.findViewParty(party);
		assertEquals(null, viewParty);
	}
	
	@Test
	void testFindViewMessage() {
		setup();
		
		ViewMessage viewMessage = activeWindow.findViewMessage(invocation);
		assertEquals(invocation, viewMessage.getMessage());
		
		viewMessage = activeWindow.findViewMessage(message);
		assertEquals(null, viewMessage);
	}
}
