package view.controls;

import java.awt.Graphics2D;
import java.util.ArrayList;

import view.components.ViewLabel;
import view.windows.SubWindow;

public abstract class WindowControl {
	private int x;
	private int y;
	private int height;
	private int width;
	private boolean isActive;
	
	public WindowControl() {
		this.isActive = false;
	}
	
	public abstract void draw(Graphics2D g);

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

	public void currentControl(SubWindow subwindow) {
		if (!subwindow.actionAllowed())
			subwindow.changeLabelState("SHOW");
	}

	public ViewLabel getViewLabel() {
		return null;
	}

	public void space() {
		System.out.println("Activate control.");
	}

	public void update(ArrayList<String> items) {
		System.out.println("Update control.");
	}

	public WindowControl click(int x, int y) {
		if (x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight())
			return this;
		return null;
	}	

	public abstract void click();
}
