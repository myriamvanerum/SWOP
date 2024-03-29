package view.windows;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Titlebar class.
 * Represents the Titlebar of a subwindow
 * @author groep 03
 *
 */
public class Titlebar {
	private Integer x, y, width, height = 25;
	private CloseButton closeButton;
	
	/**
	 * Titlebar Constructor
	 * @param x
	 * 			The x postion of the titlebar
	 * @param y
	 * 			The y postion of the titlebar
	 * @param width
	 * 			The width of the titlebar
	 */
	public Titlebar(Integer x, Integer y, Integer width) {
		setX(x);
		setY(y);
		setWidth(width);
		setCloseButton(new CloseButton());
	}
	
	/* GETTERS AND SETTERS */
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public CloseButton getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(CloseButton closeButton) {
		this.closeButton = closeButton;
	}

	/**
	 * Draw the titlebar
	 * @param g
	 *          Graphics class
	 * @param title
	 * 			The title that is displayed by the titlebar
	 */
	public void draw(Graphics2D g, String title) {
		Integer padding = 7;
		Integer paddingBig = padding + 10;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		// Draw title bar text
		g.setColor(Color.BLACK);
		g.drawString(title, getX() + padding, getY() + paddingBig);		

		// Draw close button
		getCloseButton().draw(g, getX() + getWidth(), getY(), getHeight(), padding, paddingBig);
	}
}
