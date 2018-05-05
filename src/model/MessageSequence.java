package model;

import java.util.ArrayList;

public class MessageSequence {
	private ArrayList<Message> messages;
	
	public MessageSequence() {
		messages = new ArrayList<>();
	}
	
	public void addMessage(Message message) {
		
	}
	
	public void removeMessage(Message message) {
		
	}
	
	public boolean checkCallStack(Message previous, Message message) {
		return messages.contains(previous) && previous.getReceiver() == message.getSender();
	}
}
