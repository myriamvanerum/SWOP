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
import view.windows.DiagramWindow;
import view.windows.SubWindow;

class ViewInteractionTests {
	ViewInteraction viewInteraction = new ViewInteraction();
	Point2D pos = new Point2D.Double(50,50);
	Point2D posLow = new Point2D.Double(150,150);

	@Test
	void testCreateDiagramWindow() {
		assertEquals(0, viewInteraction.getSubWindows().size());
		assertNull(viewInteraction.getActiveWindow());
		viewInteraction.createDiagramWindow(pos);
		assertEquals(1, viewInteraction.getSubWindows().size());
		assertNotNull(viewInteraction.getActiveWindow());
	}

	@Test
	void testDuplicateDiagramWindow() {
		viewInteraction.createDiagramWindow(pos);
		assertEquals(1, viewInteraction.getSubWindows().size());
		viewInteraction.duplicateActiveWindow();
		assertEquals(2, viewInteraction.getSubWindows().size());
	}
	
	@Test
	void testActivateSubWindow() {
		viewInteraction.createDiagramWindow(pos);
		SubWindow activeFirst = viewInteraction.getActiveWindow();
		viewInteraction.duplicateActiveWindow();
		viewInteraction.activateSubwindow(51, 51);
		assertEquals(activeFirst, viewInteraction.getActiveWindow());
	}
	
	@Test
	void testfindLowestWindow() {
		viewInteraction.createDiagramWindow(pos);
		viewInteraction.duplicateActiveWindow();
		viewInteraction.createDiagramWindow(posLow);
		SubWindow active = viewInteraction.getActiveWindow();
		Point2D lowest = viewInteraction.findLowestWindow();
		assertEquals((int)active.getX() + 10, (int)lowest.getX());
		assertEquals((int)active.getY() + 10, (int)lowest.getY());
	}
	
	@Test
	void testCloseActiveWindow() {
		viewInteraction.createDiagramWindow(pos);
		viewInteraction.duplicateActiveWindow();
		viewInteraction.createDiagramWindow(posLow);
		SubWindow active = viewInteraction.getActiveWindow();
		assertEquals(3, viewInteraction.getSubWindows().size());
		viewInteraction.closeWindow(640, 155);
		
		assertNotEquals(active, viewInteraction.getActiveWindow());
		assertEquals(2, viewInteraction.getSubWindows().size());
	}
	
	@Test
	void testCloseWindow() {
		viewInteraction.createDiagramWindow(pos);
		viewInteraction.duplicateActiveWindow();
		viewInteraction.createDiagramWindow(posLow);
		
		SubWindow active = viewInteraction.getActiveWindow();
		viewInteraction.closeWindow(540, 55);
		
		assertEquals(active, viewInteraction.getActiveWindow());
		assertEquals(2, viewInteraction.getSubWindows().size());
	}
	
	@Test
	void testCloseNoWindow() {
		viewInteraction.createDiagramWindow(pos);
		viewInteraction.duplicateActiveWindow();
		viewInteraction.createDiagramWindow(posLow);
		viewInteraction.closeWindow(800, 55);
		assertEquals(3, viewInteraction.getSubWindows().size());
	}
	
	@Test
	void testOpenDialogBox() {
		viewInteraction.createDiagramWindow(pos);
		DiagramWindow active = (DiagramWindow)viewInteraction.getActiveWindow();
		Party actor = new Actor("actor");
		Party object = new Object("object");
		InvocationMessage invocation = new InvocationMessage("message", actor, object);	
		ResultMessage result = new ResultMessage("result", object, actor);
		invocation.setCompanion(result);
		result.setCompanion(invocation);
		active.addViewParty(actor, pos);
		active.addViewParty(object, pos);
		active.addViewMessage(invocation, pos);
		ViewMessage mes = active.getViewMessages().get(0);
		active.setSelectedComponent(mes);
		
		viewInteraction.openDialogBox(pos);
	}
}
