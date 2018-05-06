package view.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
/**
 * ViewActivationBar class. Controls the drawing of Activation Bars on LifeLines
 * @author groep 03
 *
 */
public class ViewActivationBar {
	/**
	 * Draw an Activation Bar at the specified location
	 * @param g
	 * 		Graphics class
	 * @param x
	 * 		X coordinate
	 * @param y
	 * 		Y coordinate
	 */
	public void draw(Graphics2D g, int x, int y) {
		Rectangle rectangle = new Rectangle(x, y, 12, 30);
		g.setColor(new Color(255,255,255));
		g.fill(rectangle);
		g.setColor(new Color(0,0,0));
		g.draw(rectangle);
	}
}
