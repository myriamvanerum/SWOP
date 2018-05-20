package recording;

import java.io.File;
import java.util.Scanner;

import view.canvaswindow.MainWindow;

public class RecordingTool {
	private static String name;
	private static Scanner scanner = new Scanner(System.in);
	
	private static String directory = System.getProperty("user.dir") + File.separator + "test" + File.separator + "recordings";
	
	public static void main(String[] args) {
		make(directory);
		
		System.out.println("Recording name:");
		
		name = scanner.nextLine();
		String file = directory + File.separator + name;
		
		Boolean exists = make(file);
		if (exists)
			return;
		
		java.awt.EventQueue.invokeLater(() -> {
			MainWindow main = new MainWindow(file);
			main.recordSession(file + File.separator + "recording");
			main.show();
		});
	}
	
	private static boolean make(String dir) {
		File f = new File(dir);
		if (!f.exists()) {
			f.mkdir();
			return false;
		}
		return true;
	}
}
