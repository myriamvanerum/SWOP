package view.components;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import domain.party.Party;
/**
 * ViewObject class. Controls the drawing of Objects
 * @author groep 03
 *
 */
public class ViewObject extends ViewParty {
	private int width = 80, height = 80;
	
	/**
	 * ViewObject Constructor
	 * @param party
	 * 		Party to draw
	 * @param clickPosition
	 * 		Chosen position
	 * @param windowPosition
	 * 		SubWindow position
	 */
	public ViewObject(Party party, Point2D clickPosition, Point2D windowPosition) {
		super(party, clickPosition, windowPosition);
	}
	
	/**
	 * ViewObject Constructor
	 * @param party
	 * 		Party to draw
	 * @param clickPosition
	 * 		Chosen position
	 */
	public ViewObject(Party party, Point2D clickPosition) {
		super(party, clickPosition);
	}
	
	/**
	 * Copy Constructor
	 * @param viewParty
	 * 		The ViewParty to copy
	 */
	public ViewObject(ViewParty viewParty) {
		super(viewParty);
	}
	
	/**
	 * This method paints an object on the window
	 * 
	 * @param g
	 *            The graphics class
	 * @param position
	 * 			  The position to draw the Object
	 */
	@Override
	public void draw(Graphics2D g, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();
		
		setColor(g);
										
		getViewLifeLine().setPosition((int) position.getX() + (width/2), (int) position.getY() + (height + 5), ((int) position.getY() + (height + 5)) + 230);		
		
		// label width dynamisch maken met label width				
		if (viewLabel.getWidth() > 80)
			setWidth(viewLabel.getWidth() + 10);
		else setWidth(80);
										
		Rectangle r = new Rectangle((int) position.getX(), (int) position.getY(), getWidth(), getHeight());
		g.draw(r);
		getViewLabel().draw(g, new Point2D.Double((position.getX() + (getWidth()/2)-(viewLabel.getWidth()/2)), position.getY() + (getHeight()/2)));
	
		resetColor(g);
	}
		
	/**
	 * Checks if the Object is positioned at the clicked coordinates
	 * @param coordinates
	 * 			The coordinates of a click event
	 * @param positionState
	 * 			  The Object's position
	 * @param windowPosition
	 * 			  The SubWindow's position
	 */
	@Override
	public boolean checkCoordinates(Point2D coordinates, Point2D positionState, Point2D windowPosition) {	
		Point2D position = positionWindow(positionState, windowPosition);
		
		return coordinates.getX() >= position.getX() - 5 && 
			coordinates.getX() <= position.getX() - 5 + getWidth() && 
			coordinates.getY() >= position.getY() - 5 &&
			coordinates.getY() <= position.getY() + getHeight() + 25 &&
			!getViewLabel().clicked((int)coordinates.getX(), (int)coordinates.getX());
	}
		
	@Override
	public ViewParty copy() {
		return new ViewObject(this);
	}
	
	@Override
	public ViewParty changeType() {
		return new ViewActor(this);
	}
	
	/* GETTERS AND SETTERS */

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
