package view;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import model.Party;

public class ViewObject extends ViewParty{
	public ViewObject(Party party, Point2D clickPosition, Point2D windowPosition) {
		super(party, clickPosition, windowPosition);
		viewLifeLine.setPosition((int) positionSeq.getX() + (width/2), (int) positionSeq.getY() + (height + 5), 300);
	}

	private int width = 80, height = 80;
	
	/**
	 * This method paints an object on the window
	 * 
	 * @param g
	 *            The graphics library used
	 * @param size
	 *            The size of the rectangle to paint
	 * @param label
	 *            The text of the label
	 * @throws IllegalArgumentException
	 * 			  Illegal object, coordinates or size
	 */
	@Override
	public void draw(Graphics2D g, Point2D position) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();
		
		// TODO label width + heigth

		// label width dynamisch maken met label width				
		if (viewLabel.getWidth() > width)
			setWidth(viewLabel.getWidth() + 10);
				
		Rectangle r = new Rectangle((int) position.getX(), (int) position.getY(), getWidth(), getHeight());
		getViewLabel().draw(g, getParty().getLabel(), new Point2D.Double((position.getX() + (getWidth()/2)-(viewLabel.getWidth()/2)), position.getY() + (getHeight()/2)));
		g.draw(r);
	}
		
	/**
	 * Checks if the object is positioned at the clicked coordinates
	 * @param coordinates
	 * 			The coordinates of a click event
	 */
	@Override
	public boolean checkCoordinates(Point2D coordinates, Point2D position, Point2D windowPosition) {	
		return coordinates.getX() >= position.getX() && 
			coordinates.getX() <= position.getX() + getWidth() && 
			coordinates.getY() >= position.getY() &&
			coordinates.getY() <= position.getY() + getHeight() &&
			!checkLabelPosition(coordinates, position);
	}
	
	@Override
	public boolean checkLabelPosition(Point2D coordinates, Point2D position) { 
		double startPositionLabel = position.getX() + (getWidth()/2)-(viewLabel.getWidth()/2);
		double yPositionLabel = position.getY() + (getHeight()/2);
		
		return coordinates.getX() >= startPositionLabel && 
			   coordinates.getX() <= (startPositionLabel + viewLabel.getWidth()) &&
			   coordinates.getY() >= yPositionLabel - viewLabel.getHeight() &&
			   coordinates.getY() <= yPositionLabel;
	}

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
