package domain;

import java.util.ArrayList;

import domain.message.InvocationMessage;
import domain.message.Message;
import domain.message.MessageSequence;
import domain.message.ResultMessage;
import domain.party.Object;
import domain.party.Party;
import view.Observer;

/**
 * Interaction Class.
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
	 */
	public void addParty() {
		Party party = new Object("|");
		this.parties.add(party);
		notifyAdd(party);
	}
	
	/** 
	 * Add a new Message to the Interaction
	 * 
	 * @param sender
	 * 		The party that sends the message
	 * @param receiver
	 * 		The party that receives the message 
	 * @param previous
	 * 		The previous message in the callstack
	 */
	public void addMessage(Party sender, Party receiver, Message previous) {
		InvocationMessage message = new InvocationMessage("|", sender, receiver);
		ResultMessage resultMessage = new ResultMessage("return", receiver, sender);
		message.setCompanion(resultMessage);
		resultMessage.setCompanion(message);
		
		// add message and companion to sequence. If successfull, tell observers
		if (getMessageSequence().addMessage(message, previous)) {
			notifyAdd(message);
		}
	}
	
	/** 
	 * Remove a component from the Interaction
	 * @param component
	 * 		The component to remove
	 */
	public void removeComponent(Component component) {
		component.remove(this);
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
	 */
	@Override
	public void notifyAdd(Party party) {
		for (Observer observer : observers)
			observer.onAddParty(party);
	}
	
	/**
	 * Notify all Observers a Message has been added
	 * @param message
	 * 		The message that was added
	 */
	@Override
	public void notifyAdd(Message message) {
		for (Observer observer : observers)
			observer.onAddMessage(message);
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
