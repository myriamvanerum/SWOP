package view.formelements;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import view.components.ViewLabel;

public class TextBox extends WindowControl{
	private ViewLabel label;
	private String description;
	private int height = 20;
	private int width = 150;

	public TextBox(String description) {
		this.description = description;
	}
	
	public TextBox(String description, ViewLabel label) {
		this.description = description;
		this.label = label;
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
	public void draw(Graphics2D g, Point2D position) {	
		int x = (int)position.getX();
		int y = (int)position.getY();		

		g.drawString(getDescription(), x , y + 4);		
		
		int boxX = x + g.getFontMetrics().stringWidth(getDescription()) + 5; 
		int boxY = y - height/2;
		
		String value = getLabel().getOutput();		
		Rectangle box = new Rectangle(boxX, boxY, width, height);
		g.draw(box);
		g.drawString(value, boxX + 10, boxY + 15);
	}
}
