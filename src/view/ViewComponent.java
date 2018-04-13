package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import model.Component;

public class ViewComponent implements Selectable {
	public boolean isSelected;
	protected ViewLabel viewLabel;

	// ik zou de positie relatief tegenover het subwindow bijhouden, dat lijkt mij
	// het gemakkelijkste
	Point2D positionCom;
	Point2D positionSeq;
	
	/**
	 * This method sets the color for all components. Focused components will be blue, others black.
	 * @param focusable
	 * 		The component to be painted
	 * @param g
	 * 		The graphics library used
	 * @throws IllegalArgumentException
	 * 		Illegal party
	 */
	public void setColor(Selectable selectable, Graphics2D g) {
		if (selectable == null)
			throw new IllegalArgumentException();
		if (selectable.selected()) {
			g.setPaint(new Color(70, 170, 220));
		} else {
			g.setPaint(new Color(0, 0, 0));
		}
	}
	
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
	
	@Override
	public boolean selected() {
		return isSelected;
	}

	@Override
	public void select() {
		isSelected = true;
	}

	@Override
	public void unselect() {
		isSelected = false;
	}
}
