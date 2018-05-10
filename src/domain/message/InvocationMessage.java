package domain.message;

import java.util.ArrayList;

import domain.party.Party;

/**
 * An Invocation Message class, a type of Message
 * @author  groep 03
 */
public class InvocationMessage extends Message {
	private String method;
	private ArrayList<String> arguments;
		
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
    
    public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public ArrayList<String> getArguments() {
		return arguments;
	}

	public void setArguments(ArrayList<String> arguments) {
		this.arguments = arguments;
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
        setCompanion(resMessage);
    }
    
    @Override
    public void removeDependents(MessageSequence messageSequence) {
    	messageSequence.removeMessageAndDependents(this);
    }
    
    public String argumentsToString() {
    	String value = "";
    	for (String argument : arguments) {
    		if (argument.equals(arguments.get(arguments.size()-1)))
    			value += argument;
    		value += argument +", ";
    	}
    	return value;
    }
}
