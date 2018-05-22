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
	SyntaxChecker syntaxChecker = new SyntaxChecker();
	
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
    	
		setLabel(party.getLabel());
	}
    
    /**
     * Edit the label of the party
     * 
     * @param label
     * 		the label of the party
     */
    @Override
    public Boolean editLabel(String label) {
		if (!syntaxChecker.correctPartyLabelSyntax(label)) return false;
		setLabel(label);
		return true;
    }

    /**
     * Change the type of a party
     * @return The new party
     */
    public abstract Party changeType();
    
    /**
     * Remove a party from an interaction
     * 
     * @param interaction
     * 			The interaction to which the party belongs
     */
	@Override
	public void remove(Interaction interaction) {
		interaction.parties.remove(this);
		interaction.notifyDelete(this);
		ArrayList<Message> messages = interaction.getMessageSequence().removePartyDependents(this);
		
		for (Message message : messages)
			interaction.notifyDelete(message);
	}
}