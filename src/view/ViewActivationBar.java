package view;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ViewActivationBar {
	public void draw(Graphics2D g, int yInvocation, int yReceiver, int x) {
		Rectangle rectangle = new Rectangle(x, yInvocation, x+10, yReceiver);
		g.draw(rectangle);
	}
}
