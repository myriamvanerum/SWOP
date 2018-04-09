package view;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

public class ViewLifeLine {
	ViewParty viewParty;
	int x, startY,  endY;
		
	public ViewLifeLine(ViewParty viewParty) {
		super();
		this.viewParty = viewParty;
	}

	public ViewParty getViewParty() {
		return viewParty;
	}

	public void setViewParty(ViewParty viewParty) {
		this.viewParty = viewParty;
	}

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
	
	public void setPosition(int x, int startY, int endY) {
		this.setX(x);
		this.setStartY(startY);
		this.setEndY(endY);
	}

	/**
	 * This method draws a lifeline underneath parties in the sequence diagram
	 * 
	 * @param g
	 *            The graphics library used
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
}
