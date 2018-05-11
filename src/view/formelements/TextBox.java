package view.formelements;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import view.components.ViewLabel;

public class TextBox extends WindowControl{
	private ViewLabel label;
	private String description;

	public TextBox(String description, int x, int y) {
		this.description = description;
		setX(x);
		setY(y);
		setHeight(20);
		setWidth(150);
	}
	
	public TextBox(String description, int x, int y, ViewLabel label) {
		this.description = description;
		this.label = label;
		setX(x);
		setY(y);
		setHeight(20);
		setWidth(150);
	}

	public ViewLabel getLabel() {
		return label;
	}

	public void setLabel(ViewLabel label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawString(getDescription(), getX() , getY() + 4);		
		
		int boxX = getX() + g.getFontMetrics().stringWidth(getDescription()) + 5; 
		int boxY = getY() - getHeight()/2;
				
		Rectangle box = new Rectangle(boxX, boxY, getWidth(), getHeight());
		g.draw(box);
		
		if (getLabel() != null) {
			String value = getLabel().getOutput();
			g.drawString(value, boxX + 10, boxY + 15);
		}
	}
}
