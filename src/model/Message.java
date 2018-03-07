package model;

/**
 * A message has a sender party, a receiver party, and a label.  In this iteration,
 * there are no constraints on the format of the label.  A message is either an
 * invocation message (continuous line) or a result message (dashed line)
 * 
 * @author groep 03
 *
 */
public abstract class Message extends DiagramComponent {
    private Party sender;
    private Party receiver;
    private String label;

    /**
     * Constructor of Message.
     * 
     * @param label: the label of the message.
     * @param sender: the sender of the message.
     * @param receiver: the receiver of the message.
     */
    public Message(String label, Party sender, Party receiver) {
        this.label = label;
        this.sender = sender;
        this.receiver = receiver;

        sender.setSendingMessage(this);
    }

    /**
     * Gets the label of the message.
     * 
     * @return label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the message.
     * 
     * @param label: the label of the message.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Gets the sender of the message.
     * 
     * @return a party
     */
    public Party getSender() {
        return sender;
    }

    /**
     * gets the receiver of the message
     * 
     * @return a party
     */
    public Party getReceiver() {
        return receiver;
    }
}