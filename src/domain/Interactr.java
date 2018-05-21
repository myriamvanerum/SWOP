package domain;

import domain.message.Message;
import domain.party.Party;
import view.ViewInteraction;

/**
 * A Facade Class
 * @author groep 03
 */
public class Interactr {
	Interaction interaction;
	
	/**
	 * Interactr constructor
	 * @param viewInteraction
	 */
	public Interactr(ViewInteraction viewInteraction) {
		this.interaction = viewInteraction.getInteraction();
	}

	/**
	 * Create a new Party in the active Interaction
	 */
	public void addParty() {
		interaction.addParty();
		System.out.println("Create New Party.");
	}
	
	/**
	 * Change the type of a Party. An Actor becomes an Object and vice versa
	 * @param party
	 * 		The Party to be changed
	 * @throws NullPointerException
	 * 		No Party supplied
	 */
	public void changePartyType(Party party) {
		if (party == null)
			throw new NullPointerException();
		
		System.out.println("Change Party Type.");
		interaction.changePartyType(party);
	}

	/**
	 * Delete a Party or Message from an Interaction
	 * @param component
	 * 		The Party or Message to delete
	 * @throws NullPointerException
	 * 		No Component supplied
	 */
	public void deleteComponent(Component component) {
		if (component == null)
			throw new NullPointerException();
		
		System.out.println("Delete component.");
		interaction.removeComponent(component);
	}

	/**
	 * Add a new Message between Parties in an Interaction
	 * @param sender
	 * 		The Message sender
	 * @param receiver
	 * 		The Message receiver
	 * @param previous
	 * 		The previous message in the callstack
	 * @throws NullPointerException
	 * 		No sender or receiver supplied
	 */
	public void addMessage(Party sender, Party receiver, Message previous) {
		if (sender == null || receiver == null)
			throw new NullPointerException();
		
		System.out.println("Create New Message.");
		
		interaction.addMessage(sender, receiver, previous);	
	}

	/**
	 * Method to be called when a Label has to be saved
	 * @param component
	 * 		The component with a new label
	 * @param label
	 * 		The new label of the component
	 */
	public void editLabel(Component component, String label) {
		if (component.editLabel(label))
			interaction.notifyEditLabel(component);
	}
}