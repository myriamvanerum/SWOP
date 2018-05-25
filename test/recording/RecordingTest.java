package recording;

import static view.canvaswindow.CanvasWindow.replayRecording;

import java.io.File;

import org.junit.jupiter.api.Test;

import view.canvaswindow.MainWindow;

class RecordingTest {
	MainWindow main;
	String directory = System.getProperty("user.dir") + File.separator + "test" + File.separator + "recordings" + File.separator;
	String suffix = File.separator + "recording";
	
	@Test
	void testCreateInteraction() {
		String name = "createInteraction";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}

	@Test
	void testSwitchDiagramType() {
		String name = "switchDiagramType";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
//	@Test
//	void testSwitchDiagramTypeFilled() {
//		String name = "switchDiagramTypeFilled";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
	
	@Test
	void testCloseWindow() {
		String name = "closeWindow";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testCloseActiveWindow() {
		String name = "closeActiveWindow";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testActivateWindow() {
		String name = "activateWindow";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testCreateParty() {
		String name = "createParty";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testCreatePartyLongLabel() {
		String name = "createPartyLongLabel";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testCreatePartyIncorrectLabel() {
		String name = "createPartyIncorrectLabel";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testSwitchPartyType() {
		String name = "switchPartyType";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testSwitchPartyTypeWithMessage() {
		String name = "switchPartyTypeWithMessage";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
//	@Test
//	void testEditPartyLabel() {
//		String name = "editPartyLabel";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
	
	@Test
	void testRemoveParty() {
		String name = "removeParty";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
//	@Test
//	void testRemovePartyWithMessage() {
//		String name = "removePartyWithMessage";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//	
//	@Test
//	void testMoveParty() {
//		String name = "moveParty";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
	
//	@Test
//	void testMovePartyWithMessage() {
//		String name = "movePartyWithMessage";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//	
//	@Test
//	void testCreateMessage() {
//		String name = "createMessage";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//
//	@Test
//	void testEditMessageLabel() {
//		String name = "editMessageLabel";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//	
//	@Test
//	void testRemoveMessage() {
//		String name = "removeMessage";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
	
	@Test
	void testDuplicateEmptyWindow() {
		String name = "duplicateEmptyWindow";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
//	@Test
//	void testDuplicateFilledWindow() {
//		String name = "duplicateFilledWindow";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
	
	@Test
	void testDeleteNothingSelected() {
		String name = "deleteNothingSelected";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testCreateInteractionNoCtrl() {
		String name = "createInteractionNoCtrl";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testDuplicateInteractionNoActivewindow() {
		String name = "duplicateInteractionNoActivewindow";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testMouseClickEmptyScreen() {
		String name = "mouseClickEmptyScreen";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testMouseClickOutsideWindows() {
		String name = "mouseClickOutsideWindows";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
//	@Test
//	void testOpenDialogBoxInvocation() {
//		String name = "openDialogBoxInvocation";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//	
//	@Test
//	void testOpenDialogBoxResult() {
//		String name = "openDialogBoxResult";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//	
//	@Test
//	void testDialogBoxInvocationEdit() {
//		String name = "dialogBoxInvocationEdit";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//	
//	@Test
//	void testDialogBoxResult() {
//		String name = "dialogBoxResultEdit";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
//	
//	@Test
//	void testThorough() {
//		String name = "thoroughTest";
//		main = new MainWindow(name);
//		replayRecording(directory + name + suffix, main);
//	}
}
