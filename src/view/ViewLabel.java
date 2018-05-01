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
	 * 		Illegal label or position
	 */
	protected void draw(Graphics2D g, String label, Point2D position) {
		if (label == null || position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();		
		
		Color colorOrig = g.getColor();
	
		// TODO show
		//	g.setColor(getColor());
		//	g.drawString(label, (int)position.getX(), (int)position.getY());

			g.setColor(getColor());
			g.drawString(output, (int)position.getX(), (int)position.getY());

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
}
