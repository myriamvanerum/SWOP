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
	private Integer height;
	
	public ViewActivationBar() {
		super();
		setHeight(40);
	}
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
		Rectangle rectangle = new Rectangle(x, y, 12, getHeight());
		System.out.println("draw height " + getHeight());
		g.setColor(new Color(150,150,255));
		g.fill(rectangle);
//		g.setColor(new Color(0,0,0));
//		g.draw(rectangle);
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public void increaseHeight(Integer heightIncrease) {
		this.height += heightIncrease;
		System.out.println("increase " + getHeight());
	}
}
