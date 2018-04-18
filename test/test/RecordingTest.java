package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import view.MainWindow;

class RecordingTest {
	MainWindow main;
	String directory = System.getProperty("user.dir") + File.separator + "test" + File.separator + "recordings" + File.separator;
	String suffix = File.separator + "recording";
	
	@Test
	void test() {
		String name = "test";
		main = new MainWindow(name);
		//main.replayRecording(directory + name + suffix, main);
	}

}
