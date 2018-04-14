package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.Observer;
import view.SubWindow;
import view.ViewObject;
import view.ViewParty;

/**
 * An Interaction is composed of a number of Parties and Messages, and can be shown in multiple SubWindows
 * @author groep 03
 */
public class Interaction implements Observable {
	public ArrayList<Party> parties;
	public ArrayList<Message> messages;
	private ArrayList<Observer> observers;
	
	/**
	 * Interaction Constructor
	 */
	public Interaction() {
		parties = new ArrayList<>();
		messages = new ArrayList<>();
		observers = new ArrayList<>();
	}

	public ArrayList<Party> getParties() {
		return parties;
	}

	public void setParties(ArrayList<Party> parties) {
		this.parties = parties;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	/** 
	 * Add a new Party to the Interaction
	 * @param party
	 * 		The Party to add
	 * @param position
	 * 		The position for this Party
	 */
	public void addParty(Party party, Point2D position) {
		this.parties.add(party);
		notifyAdd(party, position);
	}
	
	/** 
	 * Add a new Message to the Interaction
	 * @param message
	 * 		The Message to add
	 * @param position
	 * 		The position for this Message
	 */
	public void addMessage(Message message, Point2D position) {
		this.messages.add(message);
		notifyAdd(message, position);
	}
	
	/**
	 * Remove a Party from the Interaction
	 * @param party
	 * 		The Party to remove
	 */
	public void removeParty(Party party) {
		this.parties.remove(party);
		notifyDelete(party);
	}
	
	/**
	 * Remove a Message from the Interaction
	 * @param message
	 * 		The Message to remove
	 */
	public void removeMessage(Message message) {
		this.messages.remove(message);
		notifyDelete(message);
	}
	
	/**
	 * Change the type of a Party in the Interaction. An Actor becomes an Object and vice versa
	 * @param party
	 */
	public void changePartyType(Party party) {
		this.parties.remove(party);
		Party newParty;
		if (party instanceof Actor) {
			newParty = new Object(party);
		} else {
			newParty = new Actor(party);
		}
		this.parties.add(party);
		notifyChangeType(party, newParty);
	}

	/**
	 * Add an Observer to this Interaction
	 */
	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Remove an Observer from this Interaction
	 */
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	/**
	 * Notify all Observers a Party has been deleted
	 */
	@Override
	public void notifyDelete(Party party) {
		for (Observer observer : observers)
			observer.onDeleteParty(party);
	}
	
	/**
	 * Notify all Observers a Message has been deleted
	 */
	@Override
	public void notifyDelete(Message message) {
		for (Observer observer : observers)
			observer.onDeleteMessage(message);
	}
	
	/**
	 * Notify all Observers the type of a Party has been changed
	 */
	@Override
	public void notifyChangeType(Party party, Party partyNew) {
		for (Observer observer : observers)
			observer.onChangeParty(party, partyNew);
	}
	
	/**
	 * Notify all Observers a Party has been added
	 */
	@Override
	public void notifyAdd(Party party, Point2D position) {
		for (Observer observer : observers)
			observer.onAddParty(party, position);
	}
	
	/**
	 * Notify all Observers a Message has been added
	 */
	@Override
	public void notifyAdd(Message message, Point2D position) {
		for (Observer observer : observers)
			observer.onAddMessage(message, position);
	}
}
