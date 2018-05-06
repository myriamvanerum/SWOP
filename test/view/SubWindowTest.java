package view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import facade.Interactr;
import model.Actor;
import model.Object;
import model.Interaction;
import model.InvocationMessage;
import model.Message;
import model.Party;
import model.ResultMessage;
import view.canvaswindow.MainWindow;
import view.components.ViewActor;
import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.components.ViewResultMessage;
import view.windows.SubWindow;


class SubWindowTest {

	MainWindow mainwindow = new MainWindow("");
	
	@Test
	void testSubWindowConstruct() {
		Interaction interaction = new Interaction();
		
		SubWindow subwindow = new SubWindow(interaction, 10, 10);
		
		int height = 50, width = 40, heightTitlebar = 5;
		subwindow.setHeight(height);
		subwindow.setWidth(width);
		//subwindow.setHeightTitlebar(heightTitlebar);
		
		assert(height == subwindow.getHeight());
		assert(width == subwindow.getWidth());
		//assert(heightTitlebar == subwindow.getHeightTitlebar());
	}
	
	@Test
	void testSubWindowConstructNullError() {
		Interaction interaction = new Interaction();
		
		try {
			SubWindow subwindow = new SubWindow(interaction, -10, 10);
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
	
	@Test
	void testSubWindowConstructNullError2() {
		SubWindow nullWindow = null;
		try {
			SubWindow subwindow = new SubWindow(nullWindow, 10, 10);
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}
	
	@Test
	void testSubWindowConstructNullError3() {
		Interaction interaction = new Interaction();
		mainwindow.createNewSubWindow(interaction);		
		try {
			SubWindow subwindow = new SubWindow(mainwindow.getActiveWindow(), -10, 10);
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
	
	@Test
	void testSubWindowSelectComponent() {
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
	void testSubWindowSelectComponentNullError() {
		Interaction interaction = new Interaction();
		mainwindow.createNewSubWindow(interaction);
		
		try {
			mainwindow.getActiveWindow().selectComponent();
	        assert false;
	    } catch (NullPointerException e) {
	        assert true;
	    }
	}

	@Test
	void testSubWindowAddPartyError() {
		Interaction interaction = new Interaction();
		
		mainwindow.createNewSubWindow(interaction);
		SubWindow activeWindow = mainwindow.getActiveWindow();
		
		Party party = new Actor("Actor");
		Point2D position = new Point2D.Double(-50,50);
		
		try {
			activeWindow.onAddParty(party, position);
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
	
	@Test
	void testSubWindowAddMessageError() {
		Interaction interaction = new Interaction();
		
		mainwindow.createNewSubWindow(interaction);
		SubWindow activeWindow = mainwindow.getActiveWindow();
		
		Party party = new Actor("Actor");
		Party party2 = new Actor("Actor");
		Message message = new InvocationMessage("message", party, party2);
		Point2D position = new Point2D.Double(-50,50);
		
		try {
			activeWindow.onAddMessage(message, position);
	        assert false;
	    } catch (IllegalArgumentException e) {
	        assert true;
	    }
	}
	
	@Test
	void testSubWindowCopyPartiesAndMessages() {
		Interaction interaction = new Interaction();
		
		mainwindow.createNewSubWindow(interaction);
		SubWindow activeWindow = mainwindow.getActiveWindow();
		
		Party actor = new Actor("Actor");
		Party object = new Object("Object");
		Message resmessage = new ResultMessage("", actor, object);
		Message message = new InvocationMessage("message", actor, object);
		Point2D clickPosition = new Point2D.Double(50,50);
		Point2D windowPosition = new Point2D.Double(activeWindow.getX(),activeWindow.getY());
		ViewParty v1 = new ViewActor(actor, clickPosition, windowPosition);
		ViewParty v2 = new ViewActor(object, clickPosition, windowPosition);
		ViewMessage m1 = new ViewInvocationMessage(message, clickPosition, windowPosition, v1, v2);
		ViewMessage m2 = new ViewResultMessage(resmessage, clickPosition, windowPosition, v2, v1);

		
		ArrayList<ViewParty> list = new ArrayList<>();
		list.add(v1);
		list.add(v2);
		
		activeWindow.setViewParties(list);
		
		ArrayList<ViewMessage> list2 = new ArrayList<>();
		list2.add(m1);
		list2.add(m2);
		
		activeWindow.setViewMessages(list2);
		
		mainwindow.createNewSubWindow(null);
		
		SubWindow newActiveWindow = mainwindow.getActiveWindow();
		
		assertEquals(newActiveWindow.getViewMessages().size(),2);
		assertEquals(newActiveWindow.getViewParties().size(),2);
		
		assertNotNull(newActiveWindow.findViewParty(actor));
		assertNotNull(newActiveWindow.findViewParty(object));
		assertNotNull(newActiveWindow.findViewMessage(message));
		assertNotNull(newActiveWindow.findViewMessage(resmessage));
	}
}
