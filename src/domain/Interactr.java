package domain;

import domain.message.InvocationMessage;
import domain.message.Message;
import domain.party.Party;
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
	MessageFactory messageFactory;
	
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
		this.messageFactory = new MessageFactory();
	}
	

	/**
	 * Create a new Party in the active Interaction
	 * @return the created Party
	 */
	public void addParty() {
		Party party = partyFactory.createParty("object");
		Interaction currentInteraction = manager.getInteraction();
		currentInteraction.addParty(party);
		System.out.println("Create New Party.");
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
	 * @throws NullPointerException
	 * 		No sender or receiver supplied
	 */
	public void addMessage(Party sender, Party receiver, Message previous) {
		if (sender == null || receiver == null)
			throw new NullPointerException();
		
		System.out.println("Create New Message.");
		InvocationMessage message = messageFactory.createMessage(sender, receiver);
		
		manager.getInteraction().addMessage(message, previous);	
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
