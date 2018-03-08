/**
 * Tests to test the controller.
 */
package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import controller.Controller;
import model.Actor;
import model.DiagramType;
import model.InvocationMessage;
import model.Label;
import model.Message;
import model.Party;
import view.DiagramWindow;
import model.Object;

/**
 * @author groep 03
 *
 */
class ControllerTest {
	
	DiagramWindow dg = new DiagramWindow("test");
	Controller c = new Controller(dg);
	DiagramType dt = DiagramType.SEQUENCE;
	DiagramType dtc = DiagramType.COMMUNICATION;
	Label label = new Label();
	Party party1 = new Actor(1, 2, label);
	Party party2 = new Actor(3, 4, label);
	
	@Test
	void SwitchDiagramSeqTest() {
		
		c.setDiagramType(dt);
		c.switchDiagram();
		
		assertEquals(DiagramType.COMMUNICATION, c.getDiagramType());
	}
	
	@Test
	void SwitchDiagramComTest() {
		
		c.setDiagramType(dtc);
		c.switchDiagram();
		
		assertEquals(DiagramType.SEQUENCE, c.getDiagramType());
	}
	
	@Test
	void controllerTest() {
		ArrayList<Party> parties = new ArrayList<>();
		ArrayList<Message> messages = new ArrayList<>();
		/*
		parties.add(new Object(1,1,new Label(1,1,"")));
		parties.add(new Object(2,2,new Label(2,2,"")));
		parties.add(new Actor(3,3,new Label(3,3,"")));*/
		
		assertEquals(parties, c.getParties());
		assertEquals(messages, c.getMessages());		
		assertEquals(false, c.isInputMode());
		
		
	}
	
	@Test
	void TestAddParty() {
		int x1 = 1;
		int y1 = 2;
		int lengte = c.parties.size();
		
		
		Party party = new Actor(x1, y1, label);
		c.addParty(party);
		assertEquals(lengte+1, c.parties.size());
	}
	
	@Test
	void TestRemoveParty() {
		int x1 = 1;
		int y1 = 2;
		
		Party party = new Actor(x1, y1, label);
		c.addParty(party);
		int lengte = c.parties.size();
		
		c.removeParty(party);
		assertEquals(lengte-1, c.parties.size());				
	}	
	
	@Test
	void TestAddMessage() {
		int x1 = 1;
		int y1 = 2;
		int lengte = c.messages.size();
			
		Message message = new InvocationMessage(label,party1, party2);
		c.addMessage(message);
		assertEquals(lengte+1, c.messages.size());
	}
	
	@Test
	void TestRemoveMessage() {
		int x1 = 1;
		int y1 = 2;
				
		Message message = new InvocationMessage(label,party1, party2);
		c.addMessage(message);
		int lengte = c.messages.size();
		
		c.removeMessage(message);;
		assertEquals(lengte-1, c.messages.size());
	}
	
	@Test
	void labelClickedPartyTest() {
		assertTrue(c.labelClickedParty(new Object(1,1,new Label(5,5,"")), 5, 5));
	}
	
	// Illegal argument 

	@Test
	 void removePartyNullTest() {		
		try {
			c.removeParty(null);
		    fail( "My method didn't throw when I expected it to" );
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}
	
	@Test
	 void addPartyNullTest() {
		try {
			c.addParty(null);
		    fail( "No exception" );
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}
	
	@Test
	public void changePartyNullTest() {
		try {
			c.changeParty(null, 0, 0);
		    fail( "No exception" );
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}
	
	@Test
	public void changePartySubZeroXTest() {
		try {
			c.changeParty(party1, -2, 0);
		    fail( "No exception" );
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}
	
	@Test
	public void changePartySubZeroYTest() {
		try {
			c.changeParty(party1, 0, -2);
		    fail( "No exception" );
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}
	
//	@Test
//	public void changePartyObjectTest() {
//		try {
//			c.changeParty(null, -2, -2);
//		    fail( "No exception" );
//		} catch (Exception expectedException) {
//			assertEquals(IllegalArgumentException.class, expectedException.getClass());
//		}
//	}
//	
//	@Test
//	public void changePartyActorTest() {
//		ArrayList<Party> parties = new ArrayList<>();
//		Party actor = new Actor(1, 2, label);
//		parties.add(actor);
//		c.changeParty(actor, 0, 0);
//		assertTrue(parties.contains(actor));
//	}
	
	@Test
	void addMessageNullTest() {
		try {
			c.addMessage(null);
		    fail( "No exception" );
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}

	@Test
	void removeMessageNullTest() {
		try {
			c.removeMessage(null);
		    fail( "No exception" );
		} catch (Exception expectedException) {
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
	}
	
	@Test
	void testIsComponent() {
		int x1 = 1;
		int y1 = 2;
		int x2 = -1;
		int y2 = -2;

		try {
		c.isComponent(party1, x2, y1, x1, y1);
		fail("No exception");
		} catch (Exception expectedException){
			assertEquals(IllegalArgumentException.class, expectedException.getClass());
		}
		
		//c.isComponent(party1, x1, y1, 3, 4);
	}
}
