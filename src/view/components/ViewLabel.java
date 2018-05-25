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
	Point2D lastPosition;
	
	/**
	 * ViewLabel Constructor
	 * @param label
	 * 		The text of the label
	 */
	public ViewLabel(String label) {
		this.output = label;
		this.lastPosition = null;
	}

	/**
	 * Copy Constructor
	 * @param
	 * 		Copy of the viewlabel
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
	 * @param labelPrefix
	 * 		The label's prefix
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
	
	/**
	 * Draw the label
	 * @param g
	 * 			Graphics class
	 * @param label
	 * 			The label
	 * @param position
	 * 			The position of the label
	 */
	public void drawLabel(Graphics2D g, String label, Point2D position) {
		setLastPosition(position);
		setHeight((int)g.getFontMetrics().getStringBounds(label, g).getHeight());
		setWidth(g.getFontMetrics().stringWidth(label));
		Color colorOrig = g.getColor();
		g.setColor(getColor());
		g.drawString(label, (int)position.getX(), (int)position.getY());
		g.setColor(colorOrig);
	}
	
	/**
	 * Edit the label
	 * @param keyChar
	 * 			The charachter that should be added to the label
	 */
	public void editLabel(char keyChar) {
		String label = getOutput();
		if (label.length() > 0)
			setOutput(label.substring(0, label.length() - 1) + keyChar + "|");
		else setOutput(keyChar + "|");
	}
	
	/**
	 * Check if the ViewLabel was clicked
	 * @param clickedX
	 * 			The x coordinate of the clicked position
	 * @param clickedY
	 * 			The y coordinate of the clicked position
	 * @return	True if the label was clicked
	 * 			False if the label wasn't clicked
	 */
	public boolean clicked(int clickedX, int clickedY) {
		int x = (int)getLastPosition().getX();
		int y = (int)getLastPosition().getY();
		
		return clickedX >= x && clickedX <= x + getWidth() && clickedY >= y - getHeight() && clickedY <= y;
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
		
	public Point2D getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(Point2D lastPosition) {
		this.lastPosition = lastPosition;
	}
}
