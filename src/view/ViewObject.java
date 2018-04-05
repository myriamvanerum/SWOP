package view;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.sun.javafx.geom.Point2D;

import model.Party;

public class ViewObject extends ViewParty{
	public ViewObject(Party party) {
		super(party);
		// TODO Auto-generated constructor stub
	}

	Point2D position;
	ViewLabel viewLabel = new ViewLabel();
	
	/**
	 * This method paints an object on the window
	 * 
	 * @param g
	 *            The graphics library used
	 * @param height
	 *            The height of the rectangle to paint
	 * @param label
	 *            The text of the label
	 * @throws IllegalArgumentException
	 * 			  Illegal object, coordinates or size
	 */
	public void draw(Graphics2D g, int height, String label) {
		if (position.x < 0 || position.y < 0 || height < 0 || label == null )
			throw new IllegalArgumentException();
		
		// label width dynamisch maken met label width
		int labelWidth = g.getFontMetrics().stringWidth(label);
		int width = labelWidth + 10;
		
		if (labelWidth < height)
			width = height;
		
		Rectangle r = new Rectangle((int) position.x, (int) position.y, width, height);
		viewLabel.draw(g, label, new Point2D((position.x + (width/2)-(labelWidth/2)), position.y + (height/2)));
		g.draw(r);
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
}
