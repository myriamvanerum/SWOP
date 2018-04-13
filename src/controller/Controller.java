package controller;

import java.awt.geom.Point2D;

import model.Interaction;
import model.Party;
import model.PartyFactory;
import view.MainWindow;
import view.SubWindow;
import view.ViewComponent;
import view.ViewObject;
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
	
	public ViewParty createParty(Point2D position) {
		Party party = partyFactory.createParty("object");
		
		if (party == null)
			throw new IllegalArgumentException();
		
		Interaction currentInteraction = mainWindow.getActiveWindow().getInteraction();
		currentInteraction.addParty(party);
		
		SubWindow subwindow = mainWindow.getActiveWindow();
		ViewParty viewParty = new ViewObject(party, position, new Point2D.Double(subwindow.getX(), subwindow.getY()));
		subwindow.getViewParties().add(viewParty);
		
		System.out.println("Create New Party.");
		return viewParty;
		
		// TODO dit moet ook het aanmaken van een nieuwe ViewParty triggeren in andere subwindows. 
		// Observer pattern?
	}

	public void changeActiveSubwindow(SubWindow subwindow) {
		System.out.println("Change Active Window");
		mainWindow.setActiveWindow(subwindow);
	}

	public void closeClickedSubwindow(SubWindow subwindow) {		
		mainWindow.getSubWindows().remove(subwindow);
		subwindow.getInteraction().removeObserver(subwindow);

		if (subwindow == mainWindow.getActiveWindow()) {
			int index = mainWindow.getSubWindows().size();
			
			if (index <= 0)
				mainWindow.setActiveWindow(null);
			else
				mainWindow.setActiveWindow(mainWindow.getSubWindows().get(index-1));
		}
		System.out.println("Close SubWindow.");
	}
	
	public void changePartyType(ViewParty viewParty) {
		// TODO Auto-generated method stub	
		// dit moet gebeuren in alle subwindows voor die interactie
		// Observer pattern?
		System.out.println("Change Party Type.");
		mainWindow.getActiveWindow().getInteraction().changePartyType(viewParty.getParty());
	}

	public void switchDiagramType() {
		mainWindow.getActiveWindow().changeState();
		System.out.println("Change Diagram Type.");
	}

	public void selectComponent(ViewComponent viewComponent) {
		System.out.println("Select party.");
		
		if (viewComponent.selected())
			viewComponent.unselect();
		else
			viewComponent.select();
	}

	public void deleteComponent(ViewComponent viewComponent, SubWindow activeWindow) {
		System.out.println("Delete component.");
		// party verwijderd uit model
		if (viewComponent instanceof ViewParty)
			activeWindow.getInteraction().removeParty((Party)viewComponent.getComponent());
		// TODO delete messages
	}

	public void moveComponent(ViewComponent selectedViewComponent) {
		// TODO Auto-generated method stub
		
	}
	
	/*public void activateInputMode() {
		mainWindow.repaint();
	}*/
}
