package view;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

public class ViewLifeLine {
	/**
	 * This method draws a lifeline underneath parties in the sequence diagram
	 * 
	 * @param g
	 *            The graphics library used
	 * @param x
	 *            The x coordinate of the party
	 * @param startY
	 *            The start y value for the lifeline
	 * @param endY
	 *            The end y value for the lifeline
	 * @throws IllegalArgumentException
	 * 			  Illegal coordinates or size
	 */
	public void draw(Graphics g, int x, int startY, int endY) {
		if (x < 0 || startY < 0 || endY < 0)
			throw new IllegalArgumentException();
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.draw(new Line2D.Double(x, startY, x, endY));
	}
}
