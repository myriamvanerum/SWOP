package domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntaxChecker {
	/**
	 * Checks if the Party Label's syntax is correct.
	 * Each party has a label, consisting of an optional instance name, 
	 * followed by a colon, followed by a class name. 
	 * An instance name starts with a lowercase letter; 
	 * a class name starts with an uppercase letter. 
	 * @param input
	 * 		The label input
	 * @return true if correct, false if not
	 */
	public boolean correctPartyLabelSyntax(String input) {
		char first = input.charAt(0);
		String parts[] = input.split(":");
		
		if (input.contains(" ")) {
			return false;
		} else if (first == ':' && parts.length < 3) {
			return Character.isUpperCase(input.charAt(1));
		}
		else if (first != ':' && parts.length <= 3)
			return input.contains(":") && Character.isLowerCase(first) && Character.isUpperCase(parts[1].charAt(0));
		else return false;
	}
	
	/**
	 * Checks if the Invocation Message Label's syntax is correct. 
	 * The label of an invocation message consists of a method name and an argument list. 
	 * A method name starts with a lowercase letter and consists only of letters, digits, and underscores. 
	 * An argument list is a parenthesized, comma-separated list of arguments. 
	 * An argument is any sequence of characters, not including commas or parentheses. 
	 * The label of a result message is unconstrained. 
	 * @param input
	 * 		The label input
	 * @return true if correct, false if not
	 */
	public boolean correctInvocationMessageLabelSyntax(String input) {
		char first = input.charAt(0);
		String parts[] = input.split("\\(");
		
		// A method name starts with a lowercase letter
		if (!Character.isLowerCase(first)) return false;
		
		// A method name consists only of letters, digits, and underscores. 
		if (!Pattern.matches("[a-zA-Z0-9_]+", parts[0])) return false;
		
		// An argument list is parenthesized
		if (input.length()-1 != input.lastIndexOf(")")) return false;
		if (parts.length != 2) return false;
			
		// An argument list is a comma-separated list of arguments. 
		String argList = parts[1].substring(0, parts[1].length()-1);
		String args[] = argList.split(",");
		
		// An argument is any sequence of characters, not including commas or parentheses. 
		for (int i = 0; i < args.length; i++) {
			if (args[i].contains("(") || args[i].contains(")")) return false;
		}
		
		return true;
	}
	
	public boolean correctInvocationMessageMethod(String input) {
		String result = "";
		Pattern pattern = Pattern.compile("[a-zA-Z0-9\\_]*");
		Matcher matcher = pattern.matcher(input);
		if (matcher.find())
		{
			result += matcher.group(0);
		}    
		
		char first = input.charAt(0);

		if (result.equals(input) && Character.isLowerCase(first))
			return true;
		return false;    
	}
}
