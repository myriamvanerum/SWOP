package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import facade.Interactr;
import model.Actor;
import model.Interaction;
import model.Party;
import view.canvaswindow.MainWindow;
import view.components.ViewComponent;
import view.components.ViewParty;


class EventHandlerTest {

	MainWindow mainwindow = new MainWindow("");
	
	@Test
	void testMainWindowNewInteraction() {
		Interaction interaction = new Interaction();
		
		mainwindow.createNewSubWindow(interaction);
		SubWindow activeWindow = mainwindow.getActiveWindow();
		
		assertEquals(activeWindow.getInteraction(), interaction);
		assertEquals(mainwindow.getSubWindows().size(), 1);
	}
	
	@Test
	void testMainWindowDuplicateWindow() {
		Interaction interaction = new Interaction();
		
		mainwindow.createNewSubWindow(interaction);
		SubWindow firstWindow = mainwindow.getActiveWindow();
		mainwindow.createNewSubWindow(null);
		SubWindow activeWindow = mainwindow.getActiveWindow();
		
		assertEquals(activeWindow.getInteraction(), interaction);
		assertEquals(activeWindow.getInteraction(), firstWindow.getInteraction());
		assertEquals(mainwindow.getSubWindows().size(), 2);
	}
	
	@Test
	void testMainWindowCloseWindow() {
		Interaction interaction = new Interaction();
		
		mainwindow.createNewSubWindow(interaction);
		SubWindow firstWindow = mainwindow.getActiveWindow();
		mainwindow.createNewSubWindow(null);
		SubWindow activeWindow = mainwindow.getActiveWindow();
		
		mainwindow.closeClickedSubwindow(activeWindow);
		
		assertNotEquals(activeWindow, mainwindow.getActiveWindow());
		assertEquals(firstWindow, mainwindow.getActiveWindow());
		assertEquals(mainwindow.getSubWindows().size(), 1);
		
		mainwindow.closeClickedSubwindow(firstWindow);
		
		assertNotEquals(firstWindow, mainwindow.getActiveWindow());
		assertEquals(mainwindow.getSubWindows().size(), 0);
		assertNull(mainwindow.getActiveWindow());
	}
	
	@Test
	void testControllerClosedWindowNullError() {
		try {
			mainwindow.closeClickedSubwindow(null);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testMainWindowSelectComponent() {
		Interaction interaction = new Interaction();
		
		mainwindow.createNewSubWindow(interaction);
		SubWindow activeWindow = mainwindow.getActiveWindow();
		
		Party party = new Actor("Actor");
		Point2D clickPosition = new Point2D.Double(50,50);
		Point2D windowPosition = new Point2D.Double(activeWindow.getX(),activeWindow.getY());
		ViewComponent viewComponent = new ViewParty(party, clickPosition, windowPosition);
		activeWindow.selectedComponent = viewComponent;
		activeWindow.selectComponent();
		
		assertTrue(viewComponent.selected());
		
		activeWindow.selectComponent();
		
		assertFalse(viewComponent.selected());
	}
	
	@Test
	void testControllerSelectComponentNullError() {
		Interaction interaction = new Interaction();
		mainwindow.createNewSubWindow(interaction);
		
		try {
			mainwindow.getActiveWindow().selectComponent();
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
//	@Test
//	void testMainWindowMoveComponentSeq() {
//		Interaction interaction = new Interaction();
//		
//		mainwindow.createNewSubWindow(interaction);
//		SubWindow activeWindow = mainwindow.getActiveWindow();
//		
//		Party party = new Actor("Actor");
//		Point2D clickPosition = new Point2D.Double(50,50);
//		Point2D windowPosition = new Point2D.Double(activeWindow.getX(),activeWindow.getY());
//		ViewComponent viewComponent = new ViewParty(party, clickPosition, windowPosition);
//		
//		int x = 150, y = 200;
//		mainwindow.moveComponent(viewComponent, x, y);
//		
//		assertEquals(x, viewComponent.getPositionSeq().getX() + activeWindow.getX());
//		assertEquals(40, viewComponent.getPositionSeq().getY());
//	}
//	
//	@Test
//	void testMainWindowMoveComponentCom() {
//		Interaction interaction = new Interaction();
//		
//		mainwindow.createNewSubWindow(interaction);
//		SubWindow activeWindow = mainwindow.getActiveWindow();
//		activeWindow.changeState();
//		
//		Party party = new Actor("Actor");
//		Point2D clickPosition = new Point2D.Double(50,50);
//		Point2D windowPosition = new Point2D.Double(activeWindow.getX(),activeWindow.getY());
//		ViewComponent viewComponent = new ViewParty(party, clickPosition, windowPosition);
//		
//		int x = 150, y = 200;
//		mainwindow.moveComponent(viewComponent, x, y);
//		
//		assertEquals(x, viewComponent.getPositionCom().getX() + activeWindow.getX());
//		assertEquals(y, viewComponent.getPositionCom().getY() + activeWindow.getY() + activeWindow.getHeightTitlebar());
//	}
//	
//	@Test
//	void testControllerMoveComponentNullError() {
//		try {
//			int x = 150, y = 200;
//			mainwindow.moveComponent(null, x, y);
//	        assert false;
//	    } catch (NullPointerException e) {
//	        assert true;
//	    }
//	}
//	
//	@Test
//	void testControllerMoveComponentPositionError() {
//		Interaction interaction = new Interaction();
//		mainwindow.createNewSubWindow(interaction);
//		SubWindow activeWindow = mainwindow.getActiveWindow();
//		Party party = new Actor("Actor");
//		Point2D clickPosition = new Point2D.Double(50,50);
//		Point2D windowPosition = new Point2D.Double(activeWindow.getX(),activeWindow.getY());
//		ViewComponent viewComponent = new ViewParty(party, clickPosition, windowPosition);
//		
//		try {
//			int x = -150, y = 200; 
//			mainwindow.moveComponent(viewComponent, x, y);
//	        assert false;
//	    } catch (IllegalArgumentException e) {
//	        assert true;
//	    }
//	}

}
