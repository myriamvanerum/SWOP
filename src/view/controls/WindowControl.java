package view.controls;

import java.awt.Graphics2D;
import java.util.ArrayList;

import view.components.ViewLabel;
import view.windows.SubWindow;

/**
 * WindowControl class.
 * A window control can be used to get data from the user
 * @author groep 03
 */
public abstract class WindowControl {
	private int x;
	private int y;
	private int height;
	private int width;
	private boolean isActive;
	
	/**
	 * WindowControl Constructor
	 */
	public WindowControl() {
		this.isActive = false;
	}
	
	/**
	 * Draw the window control
	 * @param g
	 * 			Graphics class
	 */
	public abstract void draw(Graphics2D g);
	
	/**
	 * Click action window control
	 */
	public void click() {}
	
	/**
	 * When current control is changed set label state subwindow to "show"
	 * @param subwindow
	 * 			The subwindow to which the window control belongs
	 */
	public void currentControl(SubWindow subwindow) {
		if (!subwindow.editingLabel()) return;
		subwindow.changeLabelState("SHOW");
	}

	/**
	 * Space key is pressed
	 */
	public void space() {}

	/**
	 * Update the control data
	 * @param items
	 */
	public void update(ArrayList<String> items) {}

	/**
	 * Check if the window control is clicked
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 * @return A windowcontrol if it's clicked
	 * 			Null if no windowcontrol is clicked
	 */
	public WindowControl click(int x, int y) {
		if (x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight())
			return this;
		return null;
	}	
	
	/**
	 * Check if a window control should be available in the current situation
	 * @param lowerbound
	 * 			The lowerbound limit
	 * @param upperbound
	 * 			The upperbound limit
	 */
	public void checkAvailability(int lowerbound, int upperbound) {}
	
	/* Getters & Setters */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
		
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public ViewLabel getViewLabel() {
		return null;
	}
}
