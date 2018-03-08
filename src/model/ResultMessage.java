package model;

/**
 * A result message class
 * 
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
    public ResultMessage(Label label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }

    

}
