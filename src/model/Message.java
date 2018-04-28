package model;

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

        sender.setSendingMessage(this);
    }    
    
    @Override
	public void remove(Interaction interaction) {
    	interaction.messages.remove(this);
		interaction.notifyDelete(this);	
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
}