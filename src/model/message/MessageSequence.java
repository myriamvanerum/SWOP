package model.message;

import java.util.ArrayList;
import java.util.Stack;

import model.party.Party;
import purecollections.PList;

public class MessageSequence {
	private PList<Message> messages;
	
	public MessageSequence() {
		messages = PList.empty();
	}
	
	public PList<Message> getMessages() {
		return messages;
	}

	public void setMessages(PList<Message> messages) {
		this.messages = messages;
	}

	public boolean addMessage(Message message, Message previous) {
        if (!checkCallStack(previous, message)) return false;
        
        // insert Invocation and Result message
        int insertIndex = getMessages().indexOf(previous) + 1;
        setMessages(getMessages().plus(insertIndex, message).plus(insertIndex + 1, message.getCompanion()));
        
        // recalculate message numbers
        setMessageNumbers();
        
        return true;
	}
	
	public void removeMessageAndDependents(Message message) {
		// TODO notifyDelete for dependents
		if (!getMessages().contains(message) || !getMessages().contains(message.getCompanion()))
            throw new IllegalArgumentException();

        int messageIndex = getMessages().indexOf(message);
        int companionMessageIndex = getMessages().indexOf(message.getCompanion());

        // remove invocation, result and all messages in between
        ArrayList<Message> messagesToDelete = new ArrayList<>();
        for (int i = Math.min(messageIndex, companionMessageIndex); i <= Math.max(messageIndex, companionMessageIndex); i++) {
            messagesToDelete.add(getMessages().get(i));
        }
        setMessages(getMessages().minusAll(messagesToDelete));
        
        // recalculate message numbers
        setMessageNumbers();
	}
	
	public void removePartyDependents(Party party) {
		Message message = findPartyMessage(party);
        // no message found for this party
        if (message == null) return;
        removeMessageAndDependents(message);
        // delete possible other messages for this party
        removePartyDependents(party);
	}
	
	private Message findPartyMessage(Party party) {
        for (Message message : getMessages()) {
            if (message.getSender() == party || message.getReceiver() == party) {
                return message;
            }
        }
        return null;
    }
	
	public boolean checkCallStack(Message previous, Message message) {
		return (getMessages().contains(previous) && previous.getReceiver() == message.getSender() || getMessages().isEmpty());
	}
	
	private void setMessageNumbers() {
		// TODO
        Stack<Integer> messageNumber = new Stack<>();
        int count = -1;
        boolean foundRes = false;
        for (Message message : getMessages()) {
        	// new message is invocation message
            if (message instanceof InvocationMessage) {
            	// a result message was found before this invocation message
                if (foundRes) {
                	messageNumber.set(count, messageNumber.get(count) + 1);
                // no result message was found before this invocation message
                } else {
                	messageNumber.push(1);
                    count += 1;
                }
                
                message.setMessageNumber(formatMessageNumber(messageNumber.toString()));
                foundRes = false;
            // new message is result message, and result message was found before this result message
            } else if (message instanceof ResultMessage && foundRes) {
            	messageNumber.pop();
                count -= 1;
            // new message is result message, and no result message was found before this result message
            } else {
            	foundRes = true;
            }
        }
    }
	
	/**
     * Formats the sequence String, the toString of an arrayList has commas and spaces.
     * Spaces will be removed and commas will be replaced by dots.
     *
     * @param sequenceString the string to format.
     * @return the formatted sequence string.
     */
    private String formatMessageNumber(String messageNumber) {
        return messageNumber.substring(1, messageNumber.length() - 1).replace(" ", "").replace(',', '.');
    }
}
