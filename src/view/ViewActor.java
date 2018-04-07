package view;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;

import model.Party;

public class ViewActor extends ViewParty {
	private Point2D position;
	private ViewLabel viewLabel = new ViewLabel();
	private int size = 20;
	private Party party;

	public ViewActor(Party party) {
		super(party);
	}

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
	 *             Illegal actor, coordinates or size
	 */
	public void draw(Graphics2D g) {
		Shape c = new Ellipse2D.Double(position.getX() - size, position.getY() - size, 2.0 * size, 2.0 * size);
		g.draw(c);
		// Draw body actor
		g.draw(new Line2D.Double(position.getX(), position.getY() + size, position.getX(),
				position.getY() + size + 50));
		// Draw arms actor
		g.draw(new Line2D.Double(position.getX() - 20, position.getY() + size + 25, position.getX(),
				position.getY() + size + 5));
		g.draw(new Line2D.Double(position.getX(), position.getY() + size + 5, position.getX() + 20,
				position.getY() + size + 25));
		// draw legs actor
		g.draw(new Line2D.Double(position.getX() - 20, position.getY() + size + 70, position.getX(),
				position.getY() + size + 50));
		g.draw(new Line2D.Double(position.getX(), position.getY() + size + 50, position.getX() + 20,
				position.getY() + size + 70));

		int labelWidth = g.getFontMetrics().stringWidth(party.getLabel());
		viewLabel.draw(g, party.getLabel(), new Point2D.Double(position.getX() - (labelWidth / 2), position.getY() + 115));
	}

	/**
	 * Checks if the actor is positioned at the clicked coordinates
	 * 
	 * @param coordinates
	 *            The coordinates of a click event
	 */
	@Override
	public boolean checkCoordinates(Point2D coordinates) {
		return new Ellipse2D.Double(position.getX() - size, position.getY() - size, 2.0 * size, 2.0 * size).contains(coordinates) ||	// head actor
			coordinates.getX() == position.getX() && coordinates.getY() >= position.getY() + size && coordinates.getY() <= position.getY() + size + 50 ||							// body actor
			new Line2D.Double(position.getX() - 20, position.getY() + size + 25, position.getX(), position.getY() + size + 5).contains(coordinates.getX(), coordinates.getY()) ||	// arms actor
			new Line2D.Double(position.getX(), position.getY() + size + 5, position.getX() + 20, position.getY() + size + 25).contains(coordinates.getX(), coordinates.getY()) ||	// arms actor
			new Line2D.Double(position.getX() - 20, position.getY() + size + 70, position.getX(), position.getY() + size + 50).contains(coordinates.getX(), coordinates.getY()) ||	// legs actor
			new Line2D.Double(position.getX(), position.getY() + size + 50, position.getX() + 20, position.getY() + size + 70).contains(coordinates.getX(), coordinates.getY());	// legs actor
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
}
