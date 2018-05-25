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
	public ViewActivationBar() {
		super();
	}
	/**
	 * Draw an Activation Bar at the specified location
	 * @param g
	 * 		Graphics class
	 * @param x
	 * 		X coordinate
	 * @param y
	 * 		Y coordinate
	 * @param height
	 * 		required height
	 */
	public void draw(Graphics2D g, int x, int y, int height) {
		Rectangle rectangle = new Rectangle(x, y, 12, height);
		g.setColor(new Color(150,150,255));
		g.fill(rectangle);
	}
}
