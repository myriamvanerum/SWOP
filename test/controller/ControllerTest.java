/**
 * Tests to test the controller.
 */
package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import facade.Interactr;
import model.InvocationMessage;
import model.Message;
import model.Party;
import model.ResultMessage;
import view.MainWindow;
import view.Components.ViewComponent;

/**
 * @author groep 03
 * 
 */
class ControllerTest {
	MainWindow mainwindow = new MainWindow("");
	Interactr controller = new Interactr(mainwindow);
	
	@Test
	void testControllerBasic() {
		controller.createNewInteraction();
		
		assertTrue(mainwindow.getSubWindows().size() == 1);
		
		
		Point2D position = new Point2D.Double(50,50);
		Party party = controller.createParty(position);
		
		assertTrue(mainwindow.getActiveWindow().getInteraction().getParties().contains(party));
		assertNotNull(mainwindow.getActiveWindow().findViewParty(party));
		
		
		controller.changePartyType(mainwindow.getActiveWindow().findViewParty(party));
		
		assertFalse(mainwindow.getActiveWindow().getInteraction().getParties().contains(party));
		assertNull(mainwindow.getActiveWindow().findViewParty(party));
		
		
		position = new Point2D.Double(150,150);
		Party party2 = controller.createParty(position);
		
		Message message = controller.addMessage(party, party2, 150, 150);
		
		assertEquals(message.getSender(), party);
		assertEquals(message.getReceiver(), party2);
		
		String label = "label:Label";
		Boolean syntaxCorrect = controller.checkPartyLabelSyntax(label);
		
		assertTrue(syntaxCorrect);
		
		
		ViewComponent viewComponent = mainwindow.getActiveWindow().findViewParty(party2);
		controller.deleteComponent(viewComponent);
		
		assertFalse(mainwindow.getActiveWindow().getInteraction().getParties().contains(party2));
		//assertFalse(mainwindow.getActiveWindow().getInteraction().getMessages().contains(message));
	}
	
	@Test
	void testControllerNullError() {
		try {
			controller = new Interactr(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testControllerPartyNullError() {
		try {
			controller.createParty(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testControllerPartyPositionError() {
		try {
			Point2D position = new Point2D.Double(-10,150);
			controller.createParty(position);
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
	
	@Test 
	void testControllerPartyTypeNullError() {
		try {
			controller.changePartyType(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testControllerRemoveParty() {
		controller.createNewInteraction();		
		
		Point2D position = new Point2D.Double(50,50);
		Party party = controller.createParty(position);		
		
		position = new Point2D.Double(150,150);
		Party party2 = controller.createParty(position);
		
		Message message = controller.addMessage(party, party2, 150, 150);
		
		ViewComponent viewComponent = mainwindow.getActiveWindow().findViewParty(party);
		controller.deleteComponent(viewComponent);
		
		assertFalse(mainwindow.getActiveWindow().getInteraction().getParties().contains(party));
		//assertFalse(mainwindow.getActiveWindow().getInteraction().getMessages().contains(message));
	}
	
	@Test
	void testControllerRemoveMessage() {
		controller.createNewInteraction();		
		
		Point2D position = new Point2D.Double(50,50);
		Party party = controller.createParty(position);		
		
		position = new Point2D.Double(150,150);
		Party party2 = controller.createParty(position);
		
		Message message = controller.addMessage(party, party2, 150, 150);
		//ResultMessage resMessage = ((InvocationMessage) message).getResultMessage();
		
		ViewComponent viewComponent = mainwindow.getActiveWindow().findViewMessage(message);
		controller.deleteComponent(viewComponent);
		
		//assertFalse(mainwindow.getActiveWindow().getInteraction().getMessages().contains(message));
		//assertFalse(mainwindow.getActiveWindow().getInteraction().getMessages().contains(resMessage));
	}
	
	@Test 
	void testControllerDeleteNullError() {
		try {
			controller.deleteComponent(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testControllerMessageNullError() {
		try {
			controller.addMessage(null, null, 150, 150);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testControllerMessagePositionError() {
		controller.createNewInteraction();
		
		Point2D position = new Point2D.Double(50,50);
		Party party = controller.createParty(position);		
		
		position = new Point2D.Double(150,150);
		Party party2 = controller.createParty(position);
		
		try {
			controller.addMessage(party, party2, 150, -10);
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
}
