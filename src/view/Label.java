package view;

public class Label {
	private String label;

	protected String getLabel() {
		return label;
	}

	protected void setLabel(String label) {
		this.label = label;
	}
	
	public boolean correctSyntax(String input) {
		char first = input.charAt(0);
		String parts[] = input.split(":");
		
		if (first == ':' && parts.length < 3) {
			return Character.isUpperCase(input.charAt(1));
		}
		else if (first != ':' && parts.length <= 3)
			return input.contains(":") && Character.isLowerCase(first) && Character.isUpperCase(parts[1].charAt(0));
		else return false;
	}

	@Override
	public String toString() {
		return this.getLabel();
	}
}
