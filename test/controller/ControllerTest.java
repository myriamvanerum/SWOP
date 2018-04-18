/**
 * Tests to test the controller.
 */
package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Actor;
import model.InvocationMessage;
import model.Message;
import model.Object;
import model.Party;
import view.MainWindow;
import view.State;
import view.SubWindow;

/**
 * @author groep 03
 * 
 */
class ControllerTest {

	@Test
	void testControllerBasic() {
		MainWindow mainwindow = new MainWindow("");
		Controller controller = new Controller(mainwindow);
		
		assertEquals(mainwindow, controller.mainWindow);
		
		
		controller.createNewInteraction();
		
		assertTrue(mainwindow.getSubWindows().size() == 1);
		
		
		Point2D position = new Point2D.Double(50,50);
		Party party = controller.createParty(position);
		
		assertTrue(mainwindow.getActiveWindow().getInteraction().getParties().contains(party));
		assertNotNull(mainwindow.getActiveWindow().findViewParty(party));
		
		
		controller.changePartyType(mainwindow.getActiveWindow().findViewParty(party));
		
		assertFalse(mainwindow.getActiveWindow().getInteraction().getParties().contains(party));
		assertNull(mainwindow.getActiveWindow().findViewParty(party));
	}
}
