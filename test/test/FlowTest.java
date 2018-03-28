package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

class FlowTest {
	
//	DiagramWindow window = new DiagramWindow("Test case");
//	Controller controller = new Controller(window);
//
//	// Use case 4.1	Switch diagram view
//	@Test
//	void switchDiagramviewTestSuccess() {
//		assertEquals(DiagramType.SEQUENCE, controller.getDiagramType());
//		controller.handleKeyEvent(KeyEvent.VK_TAB, KeyEvent.VK_TAB, (char)KeyEvent.VK_TAB);
//		assertEquals(DiagramType.COMMUNICATION, controller.getDiagramType());
//	}
//	
//	// Use case 4.2 Add party - success
//	@Test
//	void addPartyTestSuccess() {		
//		int x = 5, y = 50;
//		
//		createParty(x, y);
//		
//		assertEquals(":T", controller.checkCoordinate(x, y).getLabelText());		
//	}
//		
//	// Use case 4.3 Set party type - success	
//	@Test
//	void setPartyTypeTestSuccess() {		
//		int x = 5, y = 50;
//		
//		createParty(x, y);
//
//		Party party = controller.checkCoordinate(x, y);			
//		assertEquals(":T", party.getLabelText());	
//		assertEquals(Object.class, party.getClass());
//		
//		// set party type --> actor
//		controller.handleMouseEvent(0, x, y, 2);
//		assertNotEquals(Actor.class, party.getClass());
//	}
//		
//	// Use case 4.4 Move party - success
//	@Test
//	void movePartySuccess() {
//		int x = 5, y = 50;
//		
//		// create party
//		createParty(x, y);
//
//		Party party = controller.checkCoordinate(x, y);			
//		assertEquals(":T", party.getLabelText());	
//		assertEquals(Object.class, party.getClass());
//		
//		// start position party
//		assertEquals(x, party.getXSeq());
//		assertEquals(y, party.getYSeq());
//		
//		controller.handleMouseEvent(MouseEvent.MOUSE_PRESSED, x, y, 1);
//		controller.handleMouseEvent(MouseEvent.MOUSE_DRAGGED, 20, y, 1);
//
//		// start position party
//		assertEquals(20, party.getXSeq());
//		assertEquals(y, party.getYSeq());
//	}
//		
//	// Use case 4.5 
//		
//	// Use case 4.6 Edit label - success
//	@Test
//	void editLabelSuccess() {
//		int x = 5, y = 50;
//		
//		createParty(x, y);
//
//		Party party = controller.checkCoordinate(x, y);			
//		assertEquals(":T", party.getLabelText());	
//		assertEquals(Object.class, party.getClass());
//				
//		// click label (1)
//		assertFalse(controller.isInputMode());
//		controller.handleMouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 1);	
//		assertFalse(controller.isInputMode());
//		// click label (2)
//		controller.handleMouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 1);		
//		assertTrue(controller.isInputMode());
//		
//		// edit label
//		controller.handleKeyEvent(84, 84, 't');
//		controller.handleKeyEvent(10, 10, (char)KeyEvent.VK_ENTER);
//		assertFalse(controller.isInputMode());
//				
//		// new label
//		assertEquals(":Tt", party.getLabelText());	
//		assertEquals(Object.class, party.getClass());
//	}
//	
//	private void createParty(int x, int y) {
//		// create party
//		assertFalse(controller.isInputMode());
//		assertEquals(null, controller.checkCoordinate(x,y));
//		controller.handleMouseEvent(MouseEvent.MOUSE_CLICKED, x, y, 2);
//		assertTrue(controller.isInputMode());
//		
//		// label input
//		controller.handleKeyEvent(513, 513, ':');
//		controller.handleKeyEvent(84, 84, 'T');
//		controller.handleKeyEvent(10, 10, (char)KeyEvent.VK_ENTER);
//		assertFalse(controller.isInputMode());
//	}
}
