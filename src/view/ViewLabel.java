package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;
/**
 * ViewLabel class. Controls the drawing of Labels
 * @author groep 03
 *
 */
public class ViewLabel {
	int width, height;
	LabelMode labelMode;
	
	/**
	 * ViewLabel Constructor
	 */
	public ViewLabel() {
		labelMode = LabelMode.SHOW;
	}
	
	/**
	 * This method draws a label for the components
	 * @param g
	 * 		The graphics class
	 * @param label
	 * 		The text of the label
	 * @param position
	 * 		The position to draw the label at
	 * @throws IllegalArgumentException
	 * 		Illegal label or position
	 */
	protected void draw(Graphics2D g, String label, Point2D position) {
		if (label == null || position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();		
		
		setHeight((int)g.getFontMetrics().getStringBounds(label, g).getHeight());
		setWidth(g.getFontMetrics().stringWidth(label));

		if (labelMode == LabelMode.PARTY) {
			if (correctSyntax(label))
				g.setColor(new Color(0,255,0));
			else g.setColor(new Color(255,0,0));
		}
			
		g.drawString(label, (int)position.getX(), (int)position.getY());
		g.setColor(new Color(0,0,0));
	}
	
	/**
	 * Checks if the Label's syntax is correct.
	 * @param input
	 * 		The label input
	 * @return true if correct, false if not
	 */
	public boolean correctSyntax(String input) {
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
	
	/* GETTERS AND SETTERS */

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public LabelMode getLabelMode() {
		return labelMode;
	}

	public void setLabelMode(LabelMode labelMode) {
		this.labelMode = labelMode;
	}
}
