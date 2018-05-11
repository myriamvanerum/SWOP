package domain.party;

import java.util.ArrayList;

import domain.Component;
import domain.Interaction;
import domain.SyntaxChecker;
import domain.message.Message;

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
    
    @Override
    public void editLabel(Interaction interaction, String label) {
		SyntaxChecker syntaxChecker = new SyntaxChecker();
		if (syntaxChecker.correctPartyLabelSyntax(label)) {
			setLabel(label);
			interaction.notifyEditLabel(this);
		}
    }

    public Party changeType() { return null;}
    
	@Override
	public void remove(Interaction interaction) {
		interaction.parties.remove(this);
		interaction.notifyDelete(this);
		ArrayList<Message> messages = interaction.getMessageSequence().removePartyDependents(this);
		
		for (Message message : messages)
			interaction.notifyDelete(message);
	}

    /* GETTERS AND SETTERS */

	public Message getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessage(Message sendingMessage) {
        this.sendingMessage = sendingMessage;
    }
}