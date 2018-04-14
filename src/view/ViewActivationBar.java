package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class ViewActivationBar {
	public void draw(Graphics2D g, int x, int y) {
		Rectangle rectangle = new Rectangle(x, y, 12, 30);
		g.setColor(new Color(255,255,255));
		g.fill(rectangle);
		g.setColor(new Color(0,0,0));
		g.draw(rectangle);
	}
}
