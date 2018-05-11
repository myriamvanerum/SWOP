package view.windows;

import java.awt.Graphics2D;
import java.util.ArrayList;

import view.formelements.WindowControl;

public class DialogBox extends SubWindow {
	public ArrayList<WindowControl> controls;
	public int currentControl;
	public String title;
	
	public DialogBox(Integer x, Integer y, Integer width, Integer height, Titlebar titlebar) {
		super(x, y, width, height, titlebar);
	}
		
	public void drawControls(Graphics2D g2) {		
		for (WindowControl control : getControls()) {
			control.draw(g2);
		}
	}
	
	public ArrayList<WindowControl> getControls() {
		return controls;
	}
	
	public void setControls(ArrayList<WindowControl> controls) {
		this.controls = controls;
	}
	
	public int getCurrentControl() {
		return currentControl;
	}
	
	public void setCurrentControl(int currentControl) {
		this.currentControl = currentControl;
	}	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void draw(Graphics2D g) {
		drawWhiteField(g);	
		getTitlebar().draw(g, getTitle());
		drawBlackBorder(g);
		drawControls(g);
	}
}
