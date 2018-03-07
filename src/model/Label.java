package model;

public class Label {
	int x, y;
	String text;
	
	public Label() {
		
	}
	
	public Label(int x, int y, String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	public boolean correctSyntax() {
		char first = text.charAt(0);
		String parts[] = text.split(":");
		
		if (first == ':' && parts.length < 3) {
			return Character.isUpperCase(text.charAt(1));
		}
		else if (first != ':' && parts.length <= 3)
			return text.contains(":") && Character.isLowerCase(first) && Character.isUpperCase(parts[1].charAt(0));
		else return false;
	}
}
