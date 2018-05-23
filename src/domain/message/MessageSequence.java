package domain.message;

import java.util.ArrayList;
import java.util.Stack;

import domain.party.Party;
import purecollections.PList;

/**
 * MessageSequence class.
 * @author  groep 03
 */
public class MessageSequence {
	private PList<Message> messages;
	
	/**
     * Constructor of MessageSequence.
     */
	public MessageSequence() {
		messages = PList.empty();
	}
	
	public PList<Message> getMessages() {
		return messages;
	}

	public void setMessages(PList<Message> messages) {
		this.messages = messages;
	}

	/**
	 * Add a message to the message sequence
	 * @param message
	 * 			The message that will be added to the sequence
	 * @param previous
	 * 			The previous message in the callstack
	 * @return  True if the message has been added to the sequenced
	 * 			False if the message couldn't be added to the sequence
	 */
	public boolean addMessage(Message message, Message previous) {
        if (!checkCallStack(previous, message)) {
        	System.out.println("Call Stack Rejection.");
        	return false;
        }
        
        System.out.println("Call Stack OK.");
        // insert Invocation and Result message
        int insertIndex = getMessages().indexOf(previous) + 1;
        setMessages(getMessages().plus(insertIndex, message).plus(insertIndex + 1, message.getCompanion()));
        
        // recalculate message numbers
        setMessageNumbers();
        
        return true;
	}
	
	/**
	 * Get the message and all its dependents that should be removed
	 * 
	 * @param message
	 * 			The message that should be removed
	 * @return an arraylist of messages the should be removed
	 */
	public ArrayList<Message> removeMessageAndDependents(Message message) {
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
        
        return messagesToDelete;
	}
	
	/**
	 * Get all messages that should be removed with a party
	 * @param party
	 * 			The party that is going to be removed
	 * @return an arraylist of message that belong to the given party
	 */
	public ArrayList<Message> removePartyDependents(Party party) {
		ArrayList<Message> messagesToDelete = new ArrayList<>();
		Message message = findPartyMessage(party);
        // no message found for this party
        if (message == null) return messagesToDelete;
        messagesToDelete.addAll(removeMessageAndDependents(message));
        // delete possible other messages for this party
        messagesToDelete.addAll(removePartyDependents(party));
        
        return messagesToDelete;
	}
	
	/**
	 * Find the message that belongs to a party
	 * @param party
	 * @return A message that belongs to the given party
	 * 		   Null if the party has no messages
	 */
	private Message findPartyMessage(Party party) {
        for (Message message : getMessages()) {
            if (message.getSender() == party || message.getReceiver() == party) {
                return message;
            }
        }
        return null;
    }
	
	/**
	 * Check the call stack of messages, to see if this message can be added
	 * @param previous
	 * 		  The previous message in the callstack
	 * @param message
	 * 		  The new message in the callstack
	 * @return  True if callstack is valid
	 * 			False if callstack is invalid
	 */
	public boolean checkCallStack(Message previous, Message message) {
		return ((
					(getMessages().contains(previous) && previous.getReceiver() == message.getSender()) 
					|| getMessages().isEmpty()
				) 
				&& message.getSender() != message.getReceiver());
	}
	
	/**
	 * Reset all message numbers in the message sequence
	 */
	@SuppressWarnings("unchecked")
	private void setMessageNumbers() {
        Stack<Integer> messageNumber = new Stack<>();
        int count = -1;
        boolean foundRes = false;
        for (Message message : getMessages()) {
        	Object temp[] = message.setMessageNumber(messageNumber, count, foundRes);
        	
        	messageNumber = (Stack<Integer>) temp[0];
        	count = (int) temp[1];
        	foundRes = (boolean) temp[2];
        }
    }
}
