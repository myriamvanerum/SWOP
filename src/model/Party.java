package model;

import model.Component;

/**
 * A Party class
 * 
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
    
    public Party(Party party) {
		this.label = party.label;
		setSendingMessage(party.getSendingMessage());
	}

    /* GETTERS AND SETTERS */

    

	public Message getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessage(Message sendingMessage) {
        this.sendingMessage = sendingMessage;
    }
}