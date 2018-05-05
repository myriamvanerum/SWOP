package model;

import java.util.ArrayList;

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

	public void addMessage(Message message) {
		
	}
	
	public void removeMessageAndDependents(Message message) {
		// TODO notifyDelete for dependents
		if (!getMessages().contains(message) || !getMessages().contains(message.getCompanion()))
            throw new IllegalArgumentException();
        
		Message companion = message.getCompanion();

        int messageIndex = getMessages().indexOf(message);
        int companionMessageIndex = getMessages().indexOf(companion);

        ArrayList<Message> messagesToDelete = new ArrayList<>();
        for (int i = Math.min(messageIndex, companionMessageIndex); i <= Math.max(messageIndex, companionMessageIndex); i++) {
            messagesToDelete.add(getMessages().get(i));
        }
        setMessages(getMessages().minusAll(messagesToDelete));
        setSequenceStrings();
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
		return messages.contains(previous) && previous.getReceiver() == message.getSender();
	}
	
	private void setSequenceStrings() {
		// TODO
//        Stack<Integer> sequenceString = new Stack<>();
//        int count = -1;
//        boolean returned = false;
//        for (Message m : messages) {
//            if (m.getType() == Message.MessageType.INVOCATION) {
//                if (returned) {
//                    sequenceString.set(count, sequenceString.get(count) + 1);
//                } else {
//                    sequenceString.push(1);
//                    count += 1;
//                }
//                m.setSequenceString(formatSequenceString(sequenceString.toString()));
//                returned = false;
//            } else if (m.getType() == Message.MessageType.RESULT && returned) {
//                sequenceString.pop();
//                count -= 1;
//            } else {
//                returned = true;
//            }
//        }
    }
}
