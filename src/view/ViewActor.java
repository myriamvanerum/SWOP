package view;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import com.sun.javafx.geom.Point2D;

import model.Party;

public class ViewActor extends ViewParty {
	public ViewActor(Party party) {
		super(party);
		// TODO Auto-generated constructor stub
	}

	Point2D position;
	ViewLabel viewLabel = new ViewLabel();
	
	/**
	 * This method paints an actor on the window
	 * 
	 * @param g
	 *            The graphics library used
	 * @param size
	 *            The size of the painted stickfigure
	 * @param label
	 *            The text of the label
	 * @throws IllegalArgumentException
	 * 			  Illegal actor, coordinates or size
	 */
	public void draw(Graphics2D g, int size, String label) {
		if (position.x < 0 || position.y < 0 || size < 0 )
			throw new IllegalArgumentException();
		
		Shape c = new Ellipse2D.Double(position.x - size, position.y - size, 2.0 * size, 2.0 * size);
		g.draw(c);
		// Draw body actor
		g.draw(new Line2D.Double(position.x, position.y + size, position.x, position.y + size + 50));
		// Draw arms actor
		g.draw(new Line2D.Double(position.x - 20, position.y + size + 25, position.x, position.y + size + 5));
		g.draw(new Line2D.Double(position.x, position.y + size + 5, position.x + 20, position.y + size + 25));
		// draw legs actor
		g.draw(new Line2D.Double(position.x - 20, position.y + size + 70, position.x, position.y + size + 50));
		g.draw(new Line2D.Double(position.x, position.y + size + 50, position.x + 20, position.y + size + 70));		

		int labelWidth = g.getFontMetrics().stringWidth(label);		
		viewLabel.draw(g, label, new Point2D(position.x - (labelWidth/2), position.y+115));
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
}
