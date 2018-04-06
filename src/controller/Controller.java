package controller;

import java.util.ArrayList;

import model.Interaction;
import model.Party;
import model.PartyFactory;
import view.MainWindow;

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

	public void closeClickedSubwindow() {
		// TODO Auto-generated method stub
	}

	
	public void changePartyType() {
		// TODO Auto-generated method stub		
	}
}
