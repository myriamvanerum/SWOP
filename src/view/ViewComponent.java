package view;

import java.awt.Color;
import java.awt.Graphics2D;

import model.Component;

public class ViewComponent implements Selectable {
	public boolean isSelected;
	protected ViewLabel viewLabel;
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
