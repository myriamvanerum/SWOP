package view;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import model.Party;

public class ViewObject extends ViewParty{
	public ViewObject(Party party) {
		super(party);
	}

	private Point2D position;
	private Point2D positionCom; 
	private Point2D positionSeq;
	private ViewLabel viewLabel = new ViewLabel();
	private int width = 80, height = 80;
	private Party party;
	
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
	public void draw(Graphics2D g) {
		if (position.getX() < 0 || position.getY() < 0)
			throw new IllegalArgumentException();

		// label width dynamisch maken met label width
		int labelWidth = g.getFontMetrics().stringWidth(party.getLabel());
		if (labelWidth > width)
			setWidth(labelWidth + 10);
		
		Rectangle r = new Rectangle((int) position.getX(), (int) position.getY(), width, height);
		viewLabel.draw(g, party.getLabel(), new Point2D.Double((position.getX() + (width/2)-(labelWidth/2)), position.getY() + (height/2)));
		g.draw(r);
	}
	
	/**
	 * Checks if the object is positioned at the clicked coordinates
	 * @param coordinates
	 * 			The coordinates of a click event
	 */
	@Override
	public boolean checkCoordinates(Point2D coordinates) {
		return coordinates.getX() >= position.getX() && 
			coordinates.getX() <= position.getX() + getWidth() && 
			coordinates.getY() >= position.getY() &&
			coordinates.getY() <= position.getY() + getHeight();
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
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
