package controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.Interaction;
import model.Party;
import model.PartyFactory;
import view.MainWindow;
import view.SubWindow;
import view.ViewParty;

public class Controller {
	MainWindow mainWindow;
	PartyFactory partyFactory;
	
	public Controller(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.partyFactory = new PartyFactory();
		System.out.println("Starting Interactr.");
	}
	
	//public ArrayList<Interaction> interactions = new ArrayList<>();
	
	public void createNewInteraction() {
		// tell model to make new interaction
		Interaction interaction = new Interaction();
	//	interactions.add(interaction);
		
		// make new subwindow
		mainWindow.createNewSubWindow(interaction);
		System.out.println("Create New Interaction.");
	}
	
	public void duplicateActiveWindow() {
		// make new subwindow
		if (mainWindow.getActiveWindow() != null)
			mainWindow.createNewSubWindow(null);
		System.out.println("Duplicate SubWindow.");
		
	}
	
	public void createParty(Point2D position) {
		Party party = partyFactory.createParty("object");
		
		if (party == null)
			throw new IllegalArgumentException();
		
		Interaction currentInteraction = mainWindow.getActiveWindow().getInteraction();
		currentInteraction.getParties().add(party);
		
		SubWindow subwindow = mainWindow.getActiveWindow();
		Point2D windowPosition = new Point2D.Double(subwindow.getX(), subwindow.getY();
		ViewParty viewParty = new ViewParty(party, position, windowPosition));
		subwindow.getViewParties().add(viewParty);
		
		System.out.println("Create New Party.");
		
		// TODO dit moet ook het aanmaken van een nieuwe ViewParty triggeren. 
		// Vraag: waar moet die code? 
		// Vraag: dit moet gebeuren in alle subwindows voor die interactie? 
		// Observer pattern?
	}

	public void changeActiveSubwindow(SubWindow subwindow) {
		System.out.println("Change Active Window");
		mainWindow.setActiveWindow(subwindow);
	}

	public void closeClickedSubwindow(SubWindow subwindow) {		
		mainWindow.getSubWindows().remove(subwindow);

		if (subwindow == mainWindow.getActiveWindow()) {
			int index = mainWindow.getSubWindows().size();
			
			if (index <= 0)
				mainWindow.setActiveWindow(null);
			else
				mainWindow.setActiveWindow(mainWindow.getSubWindows().get(index-1));
		}
		System.out.println("Close SubWindow.");
	}
	
	public void changePartyType() {
		// TODO Auto-generated method stub	
		// Vraag: dit moet gebeuren in alle subwindows voor die interactie? 
		// Observer pattern?
		System.out.println("Change Party Type.");
	}

	public void switchDiagramType() {
		mainWindow.getActiveWindow().changeState();
		System.out.println("Change Diagram Type.");
	}
}
