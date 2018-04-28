package model;

import java.util.ArrayList;

import model.Component;

/**
 * A Party class
 * @author  groep 03
 */
public abstract class Party extends Component {

    private Message sendingMessage;

    /**
     * Party constructor
	 * @param label
	 * 		The party's label
     */
    public Party(String label) {
        this.label = label;
    }
    
    /**
     * Copy Constructor
     * @param party
     * 		The Party to copy
     * @throws NullPointerException
	 * 		No party supplied
     */
    public Party(Party party) {
    	if (party == null)
    		throw new NullPointerException();
    	
		setLabel(party.label);
		setSendingMessage(party.getSendingMessage());
	}
    
    public Party changeType() { return null;}
    
	@Override
	public void remove(Interaction interaction) {
		interaction.parties.remove(this);
		interaction.notifyDelete(this);
	}

	@Override
	public void removeDependents(Interaction interaction) {
		ArrayList<Message> aux = new ArrayList<>();
		
		for (Message message : interaction.getMessages()) {
			if (message.getSender() != this && message.getReceiver() != this) 
				aux.add(message);
			else interaction.notifyDelete(message);
		}	
		
		interaction.setMessages(aux);
	}

    /* GETTERS AND SETTERS */

	public Message getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessage(Message sendingMessage) {
        this.sendingMessage = sendingMessage;
    }
}