package view.components;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
/**
 * ViewLifeLine class. Controls the drawing of LifeLines
 * @author groep 03
 *
 */
public class ViewLifeLine {
	int x, startY,  endY;
		
	/**
	 * ViewLifeLine Constructor
	 */
	public ViewLifeLine() {
		super();
	}
	
	/**
	 * This method draws a lifeline underneath parties in the sequence diagram
	 * 
	 * @param g
	 *            The graphics class
	 * @throws IllegalArgumentException
	 * 			  Illegal coordinates or size
	 */
	public void draw(Graphics g) {
		if (x < 0 || startY < 0 || endY < 0)
			throw new IllegalArgumentException();
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.draw(new Line2D.Double(x, startY, x, endY));
	}
	
	/* GETTERS AND SETTERS */

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}
	
	/**
	 * Set a position for the LifeLine
	 * @param x
	 * 		X coordinate
	 * @param startY
	 * 		Highest y coordinate
	 * @param endY
	 * 		Lowest y coordinate
	 */
	public void setPosition(int x, int startY, int endY) {
		this.setX(x);
		this.setStartY(startY);
		this.setEndY(endY);
	}
}
