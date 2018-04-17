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

/**
 * A Controller Class
 * @author groep 03
 */
public class Controller {
	MainWindow mainWindow;
	PartyFactory partyFactory;
	
	/**
	 * Controller constructor
	 * @param mainWindow
	 * 		Canvas for this controller
	 * @throws NullPointerException
	 * 		No window supplied
	 */
	public Controller(MainWindow mainWindow) {
		if (mainWindow == null)
			throw new NullPointerException();
		
		this.mainWindow = mainWindow;
		this.partyFactory = new PartyFactory();
		System.out.println("Starting Interactr.");
	}
	
	/**
	 * Create a new Interaction and make a new SubWindow for this Interaction
	 */
	public void createNewInteraction() {
		// tell model to make new interaction
		Interaction interaction = new Interaction();
		
		// make new subwindow
		mainWindow.createNewSubWindow(interaction);
		System.out.println("Create New Interaction.");
	}
	
	/**
	 * Create a new SubWindow for an Interaction
	 */
	public void duplicateActiveWindow() {
		// make new subwindow
		if (mainWindow.getActiveWindow() != null)
			mainWindow.createNewSubWindow(null);
		System.out.println("Duplicate SubWindow.");
		
	}
	
	/**
	 * Create a new Party in the active Interaction
	 * @param position
	 * 		The clicked position, where the party must be placed on the screen
	 * @return the created Party
	 * @throws NullPointerException
	 * 		No position supplied
	 * @throws IllegalArgumentException
	 * 		Illegal position
	 */
	public Party createParty(Point2D position) {
		if (position == null)
			throw new NullPointerException();
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();
		
		Party party = partyFactory.createParty("object");
		
		if (party == null)
			throw new IllegalArgumentException();
		
		Interaction currentInteraction = mainWindow.getActiveWindow().getInteraction();
		currentInteraction.addParty(party, position);
		
		System.out.println("Create New Party.");
		return party;
	}

	/**
	 * Set a new active SubWindow, to be placed at the front
	 * @param subwindow
	 * 		the SubWindow to be placed at the front
	 * @throws NullPointerException
	 * 		No subwindow supplied
	 */
	public void changeActiveSubwindow(SubWindow subwindow) {
		if (subwindow == null)
			throw new NullPointerException();
		
		System.out.println("Change Active Window.");
		mainWindow.setActiveWindow(subwindow);
	}

	/**
	 * Close a SubWindow
	 * @param subwindow
	 * 		the SubWindow to be closed
	 * @throws NullPointerException
	 * 		No subwindow supplied
	 */
	public void closeClickedSubwindow(SubWindow subwindow) {	
		if (subwindow == null)
			throw new NullPointerException();
		
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
	
	/**
	 * Change the type of a Party. An Actor becomes an Object and vice versa
	 * @param viewParty
	 * 		The ViewParty that was clicked
	 * @throws NullPointerException
	 * 		No ViewParty supplied
	 */
	public void changePartyType(ViewParty viewParty) {
		if (viewParty == null)
			throw new NullPointerException();
		
		System.out.println("Change Party Type.");
		mainWindow.getActiveWindow().getInteraction().changePartyType(viewParty.getParty());
	}

	/**
	 * Switch a SubWindow from Sequence View to Communication View or vice versa
	 */
	public void switchDiagramType() {
		mainWindow.getActiveWindow().changeState();
		System.out.println("Change Diagram Type.");
	}

	/** 
	 * Select a Party or Message
	 * @param viewComponent
	 * 		The Party or Message to select
	 * @throws NullPointerException
	 * 		No ViewComponent supplied
	 */
	public void selectComponent(ViewComponent viewComponent) {
		if (viewComponent == null)
			throw new NullPointerException();
		
		System.out.println("Select party.");
		
		if (viewComponent.selected())
			viewComponent.unselect();
		else
			viewComponent.select();
	}

	/**
	 * Delete a Party or Message from an Interaction
	 * @param viewComponent
	 * 		The Party or Message to delete
	 * @throws NullPointerException
	 * 		No ViewComponent supplied
	 */
	public void deleteComponent(ViewComponent viewComponent) {
		if (viewComponent == null)
			throw new NullPointerException();
		
		System.out.println("Delete component.");
		// party verwijderd uit model
		if (viewComponent instanceof ViewParty)
			mainWindow.getActiveWindow().getInteraction().removeParty((Party)viewComponent.getComponent());
		// TODO delete messages
	}

	/**
	 * Move a Party on the screen
	 * @param selectedViewComponent
	 * 		The Party to move
	 * @param x
	 * 		The new x coordinates
	 * @param y
	 * 		The new y coordinates
	 * @throws NullPointerException
	 * 		No ViewComponent supplied
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	public void moveComponent(ViewComponent selectedViewComponent, int x, int y) {
		if (selectedViewComponent == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		SubWindow activeWindow = mainWindow.getActiveWindow();
		State state = activeWindow.getState();
		if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
			selectedViewComponent.setPositionSeq(new Point2D.Double(x - activeWindow.getX(), 40));
		} else {
			selectedViewComponent.setPositionCom(new Point2D.Double(x - activeWindow.getX(), y - activeWindow.getY() - 25));
		}
	}

	/**
	 * Add a new Message between Parties in an Interaction
	 * @param sender
	 * 		The Message sender
	 * @param receiver
	 * 		The Message receiver
	 * @param x
	 * 		The clicked x coordinates
	 * @param y
	 * 		The clicked y coordinates
	 * @return
	 * 		The created Message
	 * @throws NullPointerException
	 * 		No sender or receiver supplied
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	public Message addMessage(Party sender, Party receiver, int x, int y) {
		if (sender == null || receiver == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		System.out.println("Add message.");
		InvocationMessage invocationMessage = new InvocationMessage("|", sender, receiver);
		ResultMessage resultMessage = new ResultMessage("", receiver, sender);
		
		Interaction currentInteraction = mainWindow.getActiveWindow().getInteraction();
		
		currentInteraction.addMessage(invocationMessage, new Point2D.Double(x,y));	
		currentInteraction.addMessage(resultMessage, new Point2D.Double(x,y+20));
						
		return invocationMessage;
	}
}
