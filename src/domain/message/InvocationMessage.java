package domain.message;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domain.Interaction;
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
	public void remove(Interaction interaction) {
    	ArrayList<Message> messages = interaction.getMessageSequence().removeMessageAndDependents(this);
		interaction.notifyDelete(this);	
		for (Message message : messages)
			interaction.notifyDelete(message);
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
    
    @Override
    public void editLabel(Interaction interaction, String label) {
		setLabel(label);
		
		String method = getMethodFromLabel(label);
		ArrayList<String> arguments = getArgumentsFromLabel(label);
		
		setMethod(method);
		if (arguments != null) 
			setArguments(arguments);
		
		interaction.notifyEditLabel(this);
	}
    

	
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
			list.add(s);
		
		if (list.size() < 1)
			return null;
		return list;
	}
}
