package model;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.Observer;
import view.SubWindow;
import view.ViewObject;
import view.ViewParty;

public class Interaction implements Observable {
	public ArrayList<Party> parties;
	public ArrayList<Message> messages;
	private ArrayList<Observer> observers;
	
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
	
	public void addParty(Party party, Point2D position) {
		this.parties.add(party);
		notifyAdd(party, position);
	}
	
	public void removeParty(Party party) {
		this.parties.remove(party);
		notifyDelete(party);
	}
	
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

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyDelete(Party party) {
		for (Observer observer : observers)
			observer.onDeleteParty(party);
	}
	
	@Override
	public void notifyChangeType(Party party, Party partyNew) {
		for (Observer observer : observers)
			observer.onChangeParty(party, partyNew);
	}
	
	@Override
	public void notifyAdd(Party party, Point2D position) {
		for (Observer observer : observers)
			observer.onAddParty(party, position);
	}
}
