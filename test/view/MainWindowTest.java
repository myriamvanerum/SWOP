package view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Interaction;


class MainWindowTest {

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

}
