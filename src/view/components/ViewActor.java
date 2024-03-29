package view.components;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import domain.party.Party;
/**
 * ViewActor class. Controls the drawing of Actors
 * @author groep 03
 *
 */
public class ViewActor extends ViewParty {
	private int size = 20;

	/**
	 * ViewActor Constructor
	 * @param party
	 * 		Party to draw
	 * @param clickPosition
	 * 		Chosen position
	 * @param windowPosition
	 * 		SubWindow position
	 */
	public ViewActor(Party party, Point2D clickPosition, Point2D windowPosition) {
		super(party, clickPosition, windowPosition);
	}
	
	/**
	 * Copy Constructor
	 * @param viewParty
	 * 		The ViewParty to copy
	 */
	public ViewActor(ViewParty viewParty) {
		super(viewParty);
	}

	/**
	 * This method paints an actor on the window
	 * 
	 * @param g
	 *            The graphics class
	 * @param position
	 * 			  The position to draw the Actor
	 */
	@Override
	public void draw(Graphics2D g, Point2D position) {	
		setColor(g);
		
		viewLifeLine.setPosition((int) position.getX(), (int) position.getY() + 110, 375);
		
		drawStickman(g, position, size);

		getViewLabel().draw(g, new Point2D.Double(position.getX() - (viewLabel.getWidth() / 2), position.getY() + 100));
		
		resetColor(g);
	}
	
	/**
	 * Draw a stickman as actor
	 * @param g
	 * 			Graphics class.
	 * @param position
	 * 			The position of were to draw the stickman
	 * @param size
	 * 			The size of the stickman
	 */
	public void drawStickman(Graphics2D g, Point2D position, Integer size) {
		Shape c = new Ellipse2D.Double(position.getX() - size, position.getY() - size, 2.0 * size, 2.0 * size);
		g.draw(c);
		
		// Draw body actor
		g.draw(new Line2D.Double(position.getX(), position.getY() + size, position.getX(), position.getY() + size + 50));
		// Draw arms actor
		g.draw(new Line2D.Double(position.getX() - 20, position.getY() + size + 25, position.getX(), position.getY() + size + 5));
		g.draw(new Line2D.Double(position.getX(), position.getY() + size + 5, position.getX() + 20, position.getY() + size + 25));
		// draw legs actor
		g.draw(new Line2D.Double(position.getX() - 20, position.getY() + size + 70, position.getX(), position.getY() + size + 50));
		g.draw(new Line2D.Double(position.getX(), position.getY() + size + 50, position.getX() + 20, position.getY() + size + 70));
	}
	
	/**
	 * Checks if the actor is positioned at the clicked coordinates
	 * 
	 * @param coordinates
	 *            The coordinates of a click event
	 * @param positionState
	 * 			  The Actor's position
	 * @param windowPosition
	 * 			  The SubWindow's position
	 */
	@Override
	public boolean checkCoordinates(Point2D coordinates, Point2D positionState, Point2D windowPosition) {
		Point2D position = positionWindow(positionState, windowPosition);
		position = new Point2D.Double(position.getX(), position.getY()+25);
				
		return new Ellipse2D.Double(position.getX() - size, position.getY() - size, 2.0 * size, 2.0 * size).contains(coordinates) ||	// head actor
		coordinates.getX() >= position.getX() -2 && coordinates.getX() <= position.getX() +2 && coordinates.getY() >= position.getY() + size && coordinates.getY() <= position.getY() + size + 50 || 	// body actor
		new Line2D.Double(position.getX() - 20, position.getY() + size + 25, position.getX(), position.getY() + size + 5).contains(coordinates.getX(), coordinates.getY()) ||	// arms actor
		new Line2D.Double(position.getX(), position.getY() + size + 5, position.getX() + 20, position.getY() + size + 25).contains(coordinates.getX(), coordinates.getY()) ||	// arms actor
		new Line2D.Double(position.getX() - 20, position.getY() + size + 70, position.getX(), position.getY() + size + 50).contains(coordinates.getX(), coordinates.getY()) ||	// legs actor
		new Line2D.Double(position.getX(), position.getY() + size + 50, position.getX() + 20, position.getY() + size + 70).contains(coordinates.getX(), coordinates.getY());	// legs actor
	}
		
	/**
	 * Copy the ViewActor
	 * @return A copy of this ViewActor
	 */
	@Override
	public ViewParty copy() {
		return new ViewActor(this);
	}
	
	/**
	 * Change the the party type, the actor becomes an object
	 * @return The ViewParty as ViewObject
	 */
	@Override
	public ViewParty changeType() {
		return new ViewObject(this);
	}
}
