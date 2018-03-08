package model;

/**
 * An Invocation Message class
 * 
 * @author  groep 03
 */
public class InvocationMessage extends Message {
	/**
	 * Invocation message constructor
	 * @param label
	 * 		The message label
	 * @param sender
	 * 		The party that sends the message
	 * @param receiver
	 * 		The party that receives the message
	 */
    public InvocationMessage(Label label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }
}
