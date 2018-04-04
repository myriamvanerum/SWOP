package view;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.javafx.geom.Point2D;

public class ViewLabel {
	/**
	 * This method draws a label for the components
	 * @param g
	 * 		The graphics library used
	 * @param label
	 * 		The text of the label
	 * @param position
	 * 		The color to draw the label in
	 * @throws IllegalArgumentException
	 * 		Illegal label or color
	 */
	protected void draw(Graphics2D g, String label, Point2D position) {
		if (label == null || position.x < 0 || position.y < 0)
			throw new IllegalArgumentException();

		// TODO check label state
		g.setColor(new Color(0,0,0));
		g.drawString(label, position.x, position.y);
		g.setColor(new Color(0,0,0));
	}
}
