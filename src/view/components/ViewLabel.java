package view.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
/**
 * ViewLabel class. Controls the drawing of Labels
 * @author groep 03
 *
 */
public class ViewLabel {
	int width, height;
	Color color;
	String output;
	
	/**
	 * ViewLabel Constructor
	 * @param label
	 * 		The text of the label
	 */
	public ViewLabel(String label) {
		output = label;
	}
	
	/**
	 * Copy Constructor
	 */
	public ViewLabel(ViewLabel viewLabel) {
		output = viewLabel.output;
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
	 * 		Illegal position
	 */
	protected void draw(Graphics2D g, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();	
		
		drawLabel(g, output, position);
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
	 * 		Illegal position
	 */
	protected void draw(Graphics2D g, String labelPrefix, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();		
		
		drawLabel(g, labelPrefix + "  " + output, position);
	}
	
	public void drawLabel(Graphics2D g, String label, Point2D position) {
		Color colorOrig = g.getColor();
		g.setColor(getColor());
		g.drawString(label, (int)position.getX(), (int)position.getY());
		g.setColor(colorOrig);
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public void editLabel(char keyChar) {
		String label = getOutput();
		if (label.length() > 0)
			setOutput(label.substring(0, label.length() - 1) + keyChar + "|");
		else setOutput(keyChar + "|");
	
	}
}
