package view;

import java.awt.geom.Point2D;

public class LastPositions {
	public Point2D previousClickedPosition;
	public Point2D lastClickedPosition;

	public LastPositions(Point2D previousClickedPosition, Point2D lastClickedPosition) {
		this.previousClickedPosition = previousClickedPosition;
		this.lastClickedPosition = lastClickedPosition;
	}

	public Point2D getPreviousClickedPosition() {
		return previousClickedPosition;
	}

	public void setPreviousClickedPosition(Point2D previousClickedPosition) {
		this.previousClickedPosition = previousClickedPosition;
	}

	public Point2D getLastClickedPosition() {
		return lastClickedPosition;
	}

	public void setLastClickedPosition(Point2D lastClickedPosition) {
		if (lastClickedPosition.getX() != getLastClickedPosition().getX() && lastClickedPosition.getY() != getLastClickedPosition().getY()) {
			this.previousClickedPosition = getLastClickedPosition();
			this.lastClickedPosition = lastClickedPosition;
		}
		
	}
}
