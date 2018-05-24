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

class UITests {
	UI ui = new UI();
	
	public void setup() {
		ui.createNewInteraction();
		ViewInteraction viewInteraction = ui.getActiveInteraction();
		DiagramWindow active = (DiagramWindow)viewInteraction.getActiveWindow();
		Party actor = new Actor("actor");
		Party object = new Object("object");
		InvocationMessage invocation = new InvocationMessage("message", actor, object);	
		ResultMessage result = new ResultMessage("result", object, actor);
		Point2D pos = new Point2D.Double(50,50);
		invocation.setCompanion(result);
		result.setCompanion(invocation);
		active.addViewParty(actor, pos);
		active.addViewParty(object, pos);
		active.addViewMessage(invocation, pos);
		ViewMessage mes = active.getViewMessages().get(0);
		active.setSelectedComponent(mes);
	}

	@Test
	void testCreateInteraction() {
		ui.createNewInteraction();
		assertEquals(1, ui.getInteractions().size());
		assertNotNull(ui.getActiveInteraction());
	}

	@Test
	void testDuplicateWindow() {
		ui.createNewInteraction();
		ui.duplicateActiveWindow();
		assertEquals(1, ui.getInteractions().size());
		assertEquals(2, ui.getActiveInteraction().getSubWindows().size());
	}
	
	@Test
	void testOpenDialogBox() {
		setup();
		ui.openDialogBox();
		assertEquals(1, ui.getInteractions().size());
		assertEquals(2, ui.getActiveInteraction().getSubWindows().size());
	}
	
	@Test
	void testCloseActiveWindow() {
		ui.createNewInteraction();
		ui.createNewInteraction();
		ui.duplicateActiveWindow();
		assertEquals(2, ui.getInteractions().size());
		assertEquals(2, ui.getActiveInteraction().getSubWindows().size());
		ui.closeClickedSubwindow(525, 35);
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
	}
	
	@Test
	void testCloseActiveWindowInteraction() {
		ui.createNewInteraction();
		ui.createNewInteraction();
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
		ui.closeClickedSubwindow(515, 25);
		assertEquals(1, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
	}
	
	@Test
	void testCloseWindow() {
		ui.createNewInteraction();
		ui.duplicateActiveWindow();
		ui.createNewInteraction();
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
		ui.closeClickedSubwindow(505, 15);
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
	}
	
	@Test
	void testCloseWindowInteraction() {
		ui.createNewInteraction();
		ui.createNewInteraction();
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
		ui.closeClickedSubwindow(505, 15);
		assertEquals(1, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
	}
	
	@Test
	void testCloseLastWindowInteraction() {
		ui.createNewInteraction();
		assertEquals(1, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
		ui.closeClickedSubwindow(505, 15);
		assertEquals(0, ui.getInteractions().size());
		assertNull(ui.getActiveInteraction());
	}
	
	@Test
	void testActivateSubWindow() {
		ui.createNewInteraction();
		ui.duplicateActiveWindow();
		ui.createNewInteraction();
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
		ui.activateSubwindow(505, 15);
		assertEquals(2, ui.getInteractions().size());
		assertEquals(2, ui.getActiveInteraction().getSubWindows().size());
	}
	
	@Test
	void testActivateNoSubWindow() {
		ui.createNewInteraction();
		ui.duplicateActiveWindow();
		ui.createNewInteraction();
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
		ui.activateSubwindow(800, 15);
		assertEquals(2, ui.getInteractions().size());
		assertEquals(1, ui.getActiveInteraction().getSubWindows().size());
	}
}
