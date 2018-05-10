package domain;

import java.awt.geom.Point2D;

import domain.message.InvocationMessage;
import domain.message.Message;
import domain.message.ResultMessage;
import domain.party.Party;
import domain.party.PartyFactory;
import view.ViewInteraction;
import view.components.ViewComponent;
import view.components.ViewParty;

/**
 * A Controller Class
 * @author groep 03
 */
public class Interactr {
	ViewInteraction manager;
	PartyFactory partyFactory;
	
	/**
	 * Controller constructor
	 * @param manager
	 * @throws NullPointerException
	 * 		No window supplied
	 */
	public Interactr(ViewInteraction manager) {
		if (manager == null)
			throw new NullPointerException();
		
		this.manager = manager;
		this.partyFactory = new PartyFactory();
		System.out.println("Starting Interactr.");
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
	public Party addParty(Point2D position) {
		if (position == null)
			throw new NullPointerException();
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();
		
		Party party = partyFactory.createParty("object");
		
		Interaction currentInteraction = manager.getInteraction();
		currentInteraction.addParty(party, position);
		
		System.out.println("Create New Party.");
		return party;
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
		manager.getInteraction().changePartyType(viewParty.getParty());
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
		manager.getInteraction().removeComponent(viewComponent.getComponent());
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
		// TODO message factory / interaction maakt message
		InvocationMessage invocationMessage = new InvocationMessage("|", sender, receiver);
		ResultMessage resultMessage = new ResultMessage("", receiver, sender);
		invocationMessage.setCompanion(resultMessage);
		resultMessage.setCompanion(invocationMessage);
		
		Interaction currentInteraction = manager.getInteraction();
		
		// TODO get previous message
		Message previous = null;
		
		currentInteraction.addMessage(invocationMessage, previous, new Point2D.Double(x,y));	
		//currentInteraction.addMessage(resultMessage, new Point2D.Double(x,y+20));
						
		return invocationMessage;
	}
	
	/**
	 * Check the syntax of a Party's Label
	 * @param label
	 * 		The label to check
	 * @return true if syntax correct
	 */
	public boolean checkPartyLabelSyntax(String label) {
		SyntaxChecker syntaxChecker = new SyntaxChecker();
		return syntaxChecker.correctPartyLabelSyntax(label);
	}

	/**
	 * Method to be called when a Label has to be saved
	 * @param interaction
	 * 		The Interaction the component belongs to
	 * @param currentComponent
	 * 		The component with a new label
	 */
	public void editLabel(Interaction interaction, Component currentComponent, String label) {
		currentComponent.editLabel(interaction, label);
	}


	public Interaction addInteraction() {
		return new Interaction();
	}
}