package model;

public class SyntaxChecker {
	/**
	 * Checks if the Label's syntax is correct.
	 * @param input
	 * 		The label input
	 * @return true if correct, false if not
	 */
	public boolean correctLabelSyntax(String input) {
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
}
