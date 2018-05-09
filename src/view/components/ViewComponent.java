package view.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Component;
import view.Selectable;
import view.windows.DiagramWindow;
/**
 * ViewComponent class. Controls the drawing of Components
 * @author groep 03
 *
 */
public class ViewComponent implements Selectable {
	public boolean isSelected;
	protected ViewLabel viewLabel;

	Point2D positionCom;
	Point2D positionSeq;
	
	/**
	 * This method sets the color for all components. Selected components will be blue, others black.
	 * @param g
	 * 		The graphics class
	 */
	public void setColor(Graphics2D g) {
		if (this.selected()) {
			g.setPaint(new Color(70, 170, 220));
		} else {
			g.setPaint(new Color(0, 0, 0));
		}
	}
	
	public void resetColor(Graphics2D g) {
		g.setPaint(new Color(0, 0, 0));
	}
	
	/* GETTERS AND SETTERS */
	
	public Component getComponent() {
		return null;
	}
	
	public ViewLabel getViewLabel() {
		return viewLabel;
	}

	public void setViewLabel(ViewLabel viewLabel) {
		this.viewLabel = viewLabel;
	}
	
	public Point2D getPositionCom() {
		return positionCom;
	}

	public void setPositionCom(Point2D positionCom) {
		this.positionCom = positionCom;
	}

	public Point2D getPositionSeq() {
		return positionSeq;
	}

	public void setPositionSeq(Point2D positionSeq) {
		this.positionSeq = positionSeq;
	}
	
	/**
	 * Checks if the Component's Label is positioned at the clicked coordinates
	 * 
	 * @param coordinates
	 *            The coordinates of a click event
	 * @param positionState
	 * 			  The Party's position
	 * @param windowPosition
	 * 			  The SubWindow's position
	 * @return true if label positioned at clicked coordinates
	 */
	public boolean checkLabelPosition(Point2D coordinates, Point2D positionState, Point2D windowPosition) {
		return false;
	}
	
	/**
	 * Add the Party position and SubWindow position to get new coordinates
	 * @param position
	 * 		Party position
	 * @param windowPosition
	 * 		SubWindow position
	 * @return added coordinates
	 */
	public Point2D positionWindow(Point2D position, Point2D windowPosition) {		
		return new Point2D.Double(position.getX() + windowPosition.getX(), position.getY() + windowPosition.getY());
	}
	
	/**
     * This method determines if the Component is selected
	 * @return true is selected, false if not selected
	 */
	@Override
	public boolean selected() {
		return isSelected;
	}

	/**
     * This method selects a Component
     */
	@Override
	public void select() {
		isSelected = true;
	}

	/**
     * This method unselects a Component
     */
	@Override
	public void unselect() {
		isSelected = false;
	}

	public void setLabelState(DiagramWindow subwindow) {
		System.out.println("Set label state active subwindow");
	}
}
