package model;

import java.awt.*;

public abstract class Message extends Selectable {
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

    public Boolean isSelected(){
        return super.isSelected();
    }

    public void setSelected(Boolean selected){
        this.setSelected(selected);
    }
}