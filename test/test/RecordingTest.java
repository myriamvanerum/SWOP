package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import view.MainWindow;
import static view.CanvasWindow.replayRecording;

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
	
	@Test
	void testCreateParty() {
		String name = "createParty";
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
	void testRemoveParty() {
		String name = "removeParty";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testCreateMessage() {
		String name = "createMessage";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
	
	@Test
	void testDuplicateEmptyWindow() {
		String name = "duplicateEmptyWindow";
		main = new MainWindow(name);
		replayRecording(directory + name + suffix, main);
	}
}
