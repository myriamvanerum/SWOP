package view;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import model.Party;
/**
 * ViewObject class. Controls the drawing of Objects
 * @author groep 03
 *
 */
public class ViewObject extends ViewParty {
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
	 * Copy Constructor
	 * @param viewParty
	 * 		The ViewParty to copy
	 */
	public ViewObject(ViewParty viewParty) {
		super(viewParty);
	}

	private int width = 80, height = 80;
	
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
								
		// TODO lifeline andere plaats
		viewLifeLine.setPosition((int) position.getX() + (width/2), (int) position.getY() + (height + 5), 375);
				
		String label = getParty().getLabel();
		viewLabel.setHeight((int)g.getFontMetrics().getStringBounds(label, g).getHeight());
		viewLabel.setWidth(g.getFontMetrics().stringWidth(label));

		// label width dynamisch maken met label width				
		if (viewLabel.getWidth() > width)
			setWidth(viewLabel.getWidth() + 10);
										
		Rectangle r = new Rectangle((int) position.getX(), (int) position.getY(), getWidth(), getHeight());
		g.draw(r);
		getViewLabel().draw(g, label, new Point2D.Double((position.getX() + (getWidth()/2)-(viewLabel.getWidth()/2)), position.getY() + (getHeight()/2)));
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
			!checkLabelPosition(coordinates, position, null);
	}
	
	/**
	 * Checks if the Object's Label is positioned at the clicked coordinates
	 * 
	 * @param coordinates
	 *            The coordinates of a click event
	 * @param positionState
	 * 			  The Object's position
	 * @param windowPosition
	 * 			  The SubWindow's position
	 */
	@Override
	public boolean checkLabelPosition(Point2D coordinates, Point2D positionState, Point2D windowPosition) { 
		Point2D position;
		if (windowPosition != null)
			position = positionWindow(positionState, windowPosition);
		else position = positionState;
		
		position.setLocation(position.getX(), position.getY() + 25); 
		
		double startPositionLabel = position.getX() + (getWidth()/2)-(viewLabel.getWidth()/2);
		double yPositionLabel = position.getY() + (getHeight()/2);
				
		return coordinates.getX() >= startPositionLabel && 
			   coordinates.getX() <= (startPositionLabel + viewLabel.getWidth()) &&
			   coordinates.getY() >= yPositionLabel - viewLabel.getHeight() &&
			   coordinates.getY() <= yPositionLabel;
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
