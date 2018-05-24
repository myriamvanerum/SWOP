package view;

import java.awt.geom.Point2D;
/**
 * LastPositions class
 * LastPositions is a data structure containing 2 values, the previousClickedPosition and the lastClickedPosition
 * @author groep 03
 *
 */
public class LastPositions {
	public Point2D previousClickedPosition;
	public Point2D lastClickedPosition;

	/**
	 * LastPositions Constructor
	 * @param previousClickedPosition
	 * 			The position clicked before the lastClickedPosition
	 * @param lastClickedPosition
	 * 			The position that was last clicked
	 */
	public LastPositions(Point2D previousClickedPosition, Point2D lastClickedPosition) {
		this.previousClickedPosition = previousClickedPosition;
		this.lastClickedPosition = lastClickedPosition;
	}

	/* Getters & Setters */
	public Point2D getPreviousClickedPosition() {
		return previousClickedPosition;
	}

	public void setPreviousClickedPosition(Point2D previousClickedPosition) {
		this.previousClickedPosition = previousClickedPosition;
	}

	public Point2D getLastClickedPosition() {
		return lastClickedPosition;
	}

	public void setLastClickedPosition(Point2D last) {
		if (last.getX() == getLastClickedPosition().getX() && last.getY() == getLastClickedPosition().getY()) return;
		setPreviousClickedPosition(getLastClickedPosition());
		this.lastClickedPosition = last; 
	}
}
