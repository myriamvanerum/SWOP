package controller;

import java.util.ArrayList;

import model.Interaction;
import model.Party;
import model.PartyFactory;
import view.MainWindow;
import view.SubWindow;

public class Controller {
	MainWindow mainWindow;
	PartyFactory partyFactory;
	
	public Controller(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.partyFactory = new PartyFactory();
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
		if (mainWindow.getActiveWindow() != null)
			mainWindow.createNewSubWindow(null);
	}
	
	public void createParty() {
		Party party = partyFactory.createParty("object");
		
		if (party == null)
			throw new IllegalArgumentException();
		
		Interaction currentInteraction = mainWindow.getActiveWindow().getInteraction();
		currentInteraction.getParties().add(party);
	}

	public void findClickedSubwindow() {
		// TODO
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
	}
	
	public void changePartyType() {
		// TODO Auto-generated method stub		
	}

	public void switchDiagramType() {
		mainWindow.getActiveWindow().changeState();
	}
}
