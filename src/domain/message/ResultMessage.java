package domain.message;

import domain.party.Party;

/**
 * A result message class
 * @author  groep 03
 */
public class ResultMessage extends Message {	
	/**
	 * Result message constructor
	 * @param label
	 * 		The message label
	 * @param sender
	 * 		The party that sends the message
	 * @param receiver
	 * 		The party that receives the message
	 */
    public ResultMessage(String label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }
    
    /**
	 * Result message constructor
	 * @param label
	 * 		The message label
	 * @param sender
	 * 		The party that sends the message
	 * @param receiver
	 * 		The party that receives the message
	 * @param invMessage
	 * 		The InvocationMessage for this result message
	 */
    public ResultMessage(String label, Party sender, Party receiver, InvocationMessage invMessage) {
        super(label, sender, receiver);
        setCompanion(invMessage);
    }
}
