package model;

/*
 * A Message class
 * 
 * @author SWOP groep 03
 */
public abstract class Message extends DiagramComponent {
    private Party sender;
    private Party receiver;
    private String label;

    public Message(String label, Party sender, Party receiver) {
        this.label = label;
        this.sender = sender;
        this.receiver = receiver;

        sender.setSendingMessage(this);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Party getSender() {
        return sender;
    }

    public void setSender(Party sender) {
        this.sender = sender;
        sender.setSendingMessage(this);
    }

    public Party getReceiver() {
        return receiver;
    }

    public void setReceiver(Party receiver) {
        this.receiver = receiver;
    }
}