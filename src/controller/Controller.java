package controller;

import java.awt.geom.Point2D;

import model.Interaction;
import model.InvocationMessage;
import model.Message;
import model.Party;
import model.PartyFactory;
import model.ResultMessage;
import view.MainWindow;
import view.State;
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
	
	public Party createParty(Point2D position) {
		Party party = partyFactory.createParty("object");
		
		if (party == null)
			throw new IllegalArgumentException();
		
		Interaction currentInteraction = mainWindow.getActiveWindow().getInteraction();
		currentInteraction.addParty(party, position);
		
		System.out.println("Create New Party.");
		return party;
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

	public void moveComponent(ViewComponent selectedViewComponent, int x, int y) {
		if (selectedViewComponent == null || x < 0 || y < 0)
			throw new IllegalArgumentException();
		SubWindow activeWindow = mainWindow.getActiveWindow();
		State state = activeWindow.getState();
		if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
			selectedViewComponent.setPositionSeq(new Point2D.Double(x - activeWindow.getX(), 40));
		} else {
			selectedViewComponent.setPositionCom(new Point2D.Double(x - activeWindow.getX(), y - activeWindow.getY() - 25));
		}
	}

	public Message addMessage(Party sender, Party receiver, int x, int y) {
		System.out.println("Add message.");
		InvocationMessage invocationMessage = new InvocationMessage("|", sender, receiver);
		ResultMessage resultMessage = new ResultMessage("", sender, receiver);
		
		Interaction currentInteraction = mainWindow.getActiveWindow().getInteraction();
		
		currentInteraction.addMessage(invocationMessage, new Point2D.Double(x,y));	
		currentInteraction.addMessage(resultMessage, new Point2D.Double(x,y+20));
						
		return invocationMessage;
	}
}
