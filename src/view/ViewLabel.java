package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.*;

public class ViewLabel {
	public ViewLabel() {
		// TODO Auto-generated constructor stub
	}
	
	public ViewLabel(ViewLabel viewLabel) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method draws a label for the components
	 * @param g
	 * 		The graphics librar.getY() used
	 * @param label
	 * 		The text of the label
	 * @param position
	 * 		The color to draw the label in
	 * @throws IllegalArgumentException
	 * 		Illegal label or color
	 */
	protected void draw(Graphics2D g, String label, Point2D position) {
		if (label == null || position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();

		// TODO check label state
		g.setColor(new Color(0,0,0));
		g.drawString(label, (int)position.getX(), (int)position.getY());
		g.setColor(new Color(0,0,0));
	}
}
