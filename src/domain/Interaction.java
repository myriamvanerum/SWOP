package domain;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import model.message.Message;
import model.message.MessageSequence;
import model.party.Party;
import view.Observer;

/**
 * An Interaction is composed of a number of Parties and Messages, and can be shown in multiple SubWindows
 * @author groep 03
 */
public class Interaction implements Observable {
	public ArrayList<Party> parties;
	public MessageSequence messageSequence;
	private ArrayList<Observer> observers;
	
	/**
	 * Interaction Constructor
	 */
	public Interaction() {
		parties = new ArrayList<>();
		messageSequence = new MessageSequence();
		observers = new ArrayList<>();
	}
	
	/* GETTERS AND SETTERS */

	public ArrayList<Party> getParties() {
		return parties;
	}

	public void setParties(ArrayList<Party> parties) {
		this.parties = parties;
	}
	
	public MessageSequence getMessageSequence() {
		return messageSequence;
	}

	public void setMessageSequence(MessageSequence messageSequence) {
		this.messageSequence = messageSequence;
	}

	/** 
	 * Add a new Party to the Interaction
	 * @param party
	 * 		The Party to add
	 * @param position
	 * 		The position for this Party
	 * @throws NullPointerException
	 * 		No party or position supplied
	 * @throws IllegalArgumentException
	 * 		Illegal position
	 */
	public void addParty(Party party, Point2D position) {
		if (party == null || position == null)
			throw new NullPointerException();
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();
		
		this.parties.add(party);
		notifyAdd(party, position);
	}
	
	/** 
	 * Add a new Message to the Interaction
	 * @param message
	 * 		The Message to add
	 * @param position
	 * 		The position for this Message
	 * @throws NullPointerException
	 * 		No message or position supplied
	 * @throws IllegalArgumentException
	 * 		Illegal position
	 */
	public void addMessage(Message message, Message previous, Point2D position) {
		if (message == null || position == null)
			throw new NullPointerException();
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();
		
		// add message and companion to flow. If successfull, tell observers
		if (getMessageSequence().addMessage(message, previous)) {
			// TODO verbeter zodat positie niet moet worden doorgestuurd
			notifyAdd(message, position);
			position.setLocation(position.getX(), position.getY() + 20);
			notifyAdd(message.getCompanion(), position);
		}
	}
	
	public void removeComponent(Component component) {
		component.remove(this);
		component.removeDependents(getMessageSequence());
	}
	
	/**
	 * Change the type of a Party in the Interaction. An Actor becomes an Object and vice versa
	 * @param party
	 * 		The Party this ViewParty belongs to
	 * @throws NullPointerException
	 * 		No Party supplied
	 */
	public void changePartyType(Party party) {
		if (party == null)
			throw new NullPointerException();
		
		this.parties.remove(party);
		Party newParty = party.changeType();
		this.parties.add(newParty);
		notifyChangeType(party, newParty);
	}

	/**
	 * Add an Observer to this Interaction
	 * @param o
	 * 		The observer to add
	 */
	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Remove an Observer from this Observable
	 * @param o
	 * 		The observer to remove
	 */
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	/**
	 * Notify all Observers a Party has been deleted
	 * @param party
	 * 		The party that has been deleted
	 */
	@Override
	public void notifyDelete(Party party) {
		for (Observer observer : observers)
			observer.onDeleteParty(party);
	}
	
	/**
	 * Notify all Observers a Message has been deleted
	 * @param message
	 * 		The message that has been deleted
	 */
	@Override
	public void notifyDelete(Message message) {
		for (Observer observer : observers)
			observer.onDeleteMessage(message);
	}
	
	/**
	 * Notify all Observers the type of a Party has been changed
	 * @param party
	 * 		The party before its type was changed
	 * @param partyNew
	 * 		The party after its type was changed
	 */
	@Override
	public void notifyChangeType(Party party, Party partyNew) {
		for (Observer observer : observers)
			observer.onChangeParty(party, partyNew);
	}
	
	/**
	 * Notify all Observers a Party has been added
	 * @param party
	 * 		The party that was added
	 * @param position
	 * 		The position the party must be painted at
	 */
	@Override
	public void notifyAdd(Party party, Point2D position) {
		for (Observer observer : observers)
			observer.onAddParty(party, position);
	}
	
	/**
	 * Notify all Observers a Message has been added
	 * @param message
	 * 		The message that was added
	 * @param position
	 * 		The position the message must be painted at
	 */
	@Override
	public void notifyAdd(Message message, Point2D position) {
		for (Observer observer : observers)
			observer.onAddMessage(message, position);
	}

	/**
	 * Notify all Observers a Label has been changed
	 * @param component
	 * 		The component with a changed Label
	 */
	@Override
	public void notifyEditLabel(Component component) {
		for (Observer observer : observers)
			observer.onEditLabel(component);
	}
}
