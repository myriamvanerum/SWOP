package view.formelements;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class WindowControl {
	public Point2D position;
	
	public abstract void draw(Graphics2D g);
}
