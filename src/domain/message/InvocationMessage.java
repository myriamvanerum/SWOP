package domain.message;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.Interaction;
import domain.SyntaxChecker;
import domain.party.Party;

/**
 * An Invocation Message class, a type of Message
 * @author  groep 03
 */
public class InvocationMessage extends Message {
	private String method;
	private ArrayList<String> arguments;
	
	SyntaxChecker syntaxChecker = new SyntaxChecker();
		
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
	 * Remove the message and all its dependents
	 * 
	 * @param interaction
	 * 			the interaction to which the message belangs
	 */
    @Override
	public void remove(Interaction interaction) {
    	ArrayList<Message> messages = interaction.getMessageSequence().removeMessageAndDependents(this);
		interaction.notifyDelete(this);	
		for (Message message : messages)
			interaction.notifyDelete(message);
	}
    
    /**
     * Make a string of all the arguments of the invocation message
     * @return a string of all the arguments of the invocation message
     */
    public String argumentsToString() {
    	String value = "";
    	if (getArguments().size() > 0) {
	    	for (String argument : getArguments()) {
	    		value += argument.trim();
	    		if (!argument.equals(arguments.get(arguments.size()-1)))
	    			value += ", ";
	    	}
    	}
    	return value;
    }
    
    /**
     * Edit the label of the message
     * 
     * @param label
     * 		the label of the message.
     */
    @Override
    public Boolean editLabel(String label) {
		if (!syntaxChecker.correctInvocationMessageLabelSyntax(label)) return false;

		setLabel(label);
		
		String method = getMethodFromLabel(label);
		ArrayList<String> arguments = getArgumentsFromLabel(label);
		
		setMethod(method);
		setArguments(arguments);
		
		return true;
	}
    	
    /**
     * Get the method of the invocation message from a string
     * @param string
     * 		The label of the invocation message
     * @return the method of the invocation message
     */
	public String getMethodFromLabel(String string) {		
		String result = "";
		Pattern pattern = Pattern.compile("^(.*?)\\(");
		Matcher matcher = pattern.matcher(string);
		if (matcher.find())
		{
		    result += matcher.group(1);
		}
		
		return result;
	}
	
	/**
	 * Makes an arraylist of all the arguments in the label
	 * @param string
     * 		The label of the invocation message
	 * @return an arraylist of all the arguments of the invocation message
	 */
	public ArrayList<String> getArgumentsFromLabel(String string) {
		ArrayList<String> list = new ArrayList<>();
		String result = "";
		Pattern pattern = Pattern.compile("\\(([^\\)]+)\\)");
		Matcher matcher = pattern.matcher(string);
		if (matcher.find())
		{
		    result += matcher.group(1);
		}
		
		String[] split = result.split(",");
		
		for (String s : split)
	        if (s.trim().length() > 0) 
	        	list.add(s);
		
		if (list.size() < 1)
			return new ArrayList<String>();
		return list;
	}
		
	/**
     * Formats the sequence String, the toString of an arrayList has commas and spaces.
     * Spaces will be removed and commas will be replaced by dots.
     *
     * @param sequenceString the string to format.
     * @return the formatted sequence string.
     */
    private String formatMessageNumber(String messageNumber) {
        return messageNumber.substring(1, messageNumber.length() - 1).replace(" ", "").replace(',', '.');
    }
	
    /* Getters & Setters */
    @Override
	public Object[] setMessageNumber(Stack<Integer> messageNumberStack, int count, boolean foundRes) {
		if (foundRes)
        	messageNumberStack.set(count, messageNumberStack.get(count) + 1);
        else {
        	messageNumberStack.push(1);
            count += 1;
        }
        
        setMessageNumber(formatMessageNumber(messageNumberStack.toString()));
        foundRes = false;
        
        return makeArray(messageNumberStack, count, foundRes);
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
}
