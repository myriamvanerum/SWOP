/**
 * Tests to test the controller.
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
	void test() {
		SwitchDiagramTest();
		TestAddParty();
	}
	
	@Test
	void SwitchDiagramTest() {
		
		c.setDiagramType(dt);
		c.switchDiagram();
		
		assertEquals(DiagramType.COMMUNICATION, dtc);
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
	
	

}
