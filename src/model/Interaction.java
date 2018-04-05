package model;

import java.util.ArrayList;

public class Interaction {
	public ArrayList<Party> parties;
	public ArrayList<Message> messages;
	
	public Interaction() {
		parties = new ArrayList<>();
		messages = new ArrayList<>();
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
}
