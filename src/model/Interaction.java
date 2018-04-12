package model;

import java.util.ArrayList;

import view.Observer;

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
	
	public void addParty(Party party) {
		this.parties.add(party);
		notifyObserver();
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
	public void notifyObserver() {
		for (Observer observer : observers)
			observer.update();
	}
}
