package domain.message;

import domain.Component;
import domain.party.Party;

/**
 * A message has a sender party, a receiver party, and a label.  In this iteration,
 * there are no constraints on the format of the label.  A message is either an
 * invocation message (continuous line) or a result message (dashed line)
 * 
 * @author groep 03
 */
public abstract class Message extends Component {
    private Party sender;
    private Party receiver;
    private Message companion;
	private String messageNumber;


    /**
     * Constructor of Message.
     * 
     * @param label
     * 		the label of the message.
     * @param sender
     * 		the sender of the message.
     * @param receiver
     * 		the receiver of the message.
     */
    public Message(String label, Party sender, Party receiver) {
        this.label = label;
        this.sender = sender;
        this.receiver = receiver;
        this.messageNumber = "";
    }    
    
    /**
     * Edit the label of the message
     * 
     * @param label
     * 		the label of the message.
     */
    @Override
    public Boolean editLabel(String label) {
		setLabel(label);
		return true;
	}
        
    /* GETTERS AND SETTERS */

	/**
     * Gets the sender of the message.
     * @return a party
     */
    public Party getSender() {
        return sender;
    }

    /**
     * Gets the receiver of the message
     * @return a party
     */
    public Party getReceiver() {
        return receiver;
    }

    /* GETTERS & SETTERS */
	public Message getCompanion() {
		return companion;
	}

	public void setCompanion(Message companion) {
		this.companion = companion;
	}

	public String getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}
}