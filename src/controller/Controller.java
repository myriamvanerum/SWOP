package controller;

import java.util.ArrayList;

import model.Interaction;
import view.MainWindow;

public class Controller {
	MainWindow mainWindow;
	
	public Controller(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public ArrayList<Interaction> interactions = new ArrayList<>();
	
	public void createNewInteraction() {
		// tell model to make new interaction
		Interaction interaction = new Interaction();
		interactions.add(interaction);
		
		// make new subwindow
		mainWindow.createNewSubWindow(interaction);
	}
	
	public void duplicateActiveWindow() {
		// make new subwindow
		mainWindow.createNewSubWindow(null);
	}
	
	public void switchDiagramType() {
		mainWindow.getActiveWindow().changeState();
	}
}
