package domain;

import java.awt.geom.Point2D;

import model.message.Message;
import model.party.Party;
import view.Observer;
/**
 * Observable class
 * @author groep 03
 *
 */
public interface Observable {
	/**
	 * Add an Observer to this Observable
	 * @param o
	 * 		The observer to add
	 */
	public void addObserver(Observer o);
	
	/**
	 * Remove an Observer from this Observable
	 * @param o
	 * 		The observer to remove
	 */
	public void removeObserver(Observer o);
	
	/**
	 * Notify all Observers a Party has been deleted
	 * @param party
	 * 		The party that has been deleted
	 */
	public void notifyDelete(Party party);
	
	/**
	 * Notify all Observers the type of a Party has been changed
	 * @param party
	 * 		The party before its type was changed
	 * @param partyNew
	 * 		The party after its type was changed
	 */
	public void notifyChangeType(Party party, Party partyNew);
	
	/**
	 * Notify all Observers a Party has been added
	 * @param party
	 * 		The party that was added
	 * @param position
	 * 		The position the party must be painted at
	 */
	public void notifyAdd(Party party, Point2D position);
	
	/**
	 * Notify all Observers a Message has been deleted
	 * @param message
	 * 		The message that has been deleted
	 */
	public void notifyDelete(Message message);
	
	/**
	 * Notify all Observers a Message has been added
	 * @param message
	 * 		The message that was added
	 * @param position
	 * 		The position the message must be painted at
	 */
	public void notifyAdd(Message message, Point2D position);
	
	/**
	 * Notify all Observers a label has been edited
	 * @param component
	 *		The component that has had it's label edited
	 */
	public void notifyEditLabel(Component component);
}
