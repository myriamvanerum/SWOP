package view.windows;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.formelements.WindowControl;

public class DialogBox {
	public ArrayList<WindowControl> controls;
	public int currentControl;
	
	public void drawControls(Graphics2D g2) {
		int x = 15, y = 15;
		
		for (WindowControl control : getControls()) {
			control.draw(g2, new Point2D.Double(x, y));
			y += 20;
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
}
