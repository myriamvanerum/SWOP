package model;

/**
 * An Invocation Message class, a type of Message
 * @author  groep 03
 */
public class InvocationMessage extends Message {
	private ResultMessage resultMessage;
	
	/**
	 * Invocation message constructor
	 * @param label
	 * 		The message label
	 * @param sender
	 * 		The party that sends the message
	 * @param receiver
	 * 		The party that receives the message
	 */
    public InvocationMessage(String label, Party sender, Party receiver) {
        super(label, sender, receiver);
    }
    
    /**
	 * Invocation message constructor
	 * @param label
	 * 		The message label
	 * @param sender
	 * 		The party that sends the message
	 * @param receiver
	 * 		The party that receives the message
	 * @param resMessage
	 * 		The resultMessage for this invocation message
	 */
    public InvocationMessage(String label, Party sender, Party receiver, ResultMessage resMessage) {
        super(label, sender, receiver);
        setResultMessage(resMessage);
    }
    
    @Override
    public void removeDependents(MessageSequence messageSequence) {
    	messageSequence.removeMessageDependents(this);
    }

	public ResultMessage getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(ResultMessage resultMessage) {
		this.resultMessage = resultMessage;
	}
}
