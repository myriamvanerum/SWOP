package view;

import domain.Component;
import domain.message.Message;
import domain.party.Party;

/**
 * Observer class
 * @author groep 03
 *
 */
public interface Observer {
	/**
	 * Method to be called when a Party is deleted
	 * @param party
	 * 		The Party that was deleted
	 */
	public void onDeleteParty(Party party);
	
	/**
	 * Method to be called when a Party type is changed
	 * @param party
	 * 		The Party whose type was changed
	 * @param partyNew
	 * 		The party after the type was changed
	 */
	public void onChangeParty(Party party, Party partyNew);
	
	/**
	 * Method to be called when a Party is added
	 * @param party
	 * 		The Party that was added
	 */
	public void onAddParty(Party party);
	
	/**
	 * Method to be called when a Message is deleted
	 * @param message
	 * 		The Message that was deleted
	 */
	public void onDeleteMessage(Message message);
	
	/**
	 * Method to be called when a Message is added
	 * @param message
	 * 		The Message that was added
	 */
	public void onAddMessage(Message message);
	
	/**
	 * Method to be called when a Component's label has been edited
	 * @param component
	 *		The component that has had it's label edited
	 */
	public void onEditLabel(Component component);
}
