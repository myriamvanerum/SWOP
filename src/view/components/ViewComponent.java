package view.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import domain.Component;
import view.ViewInteraction;
/**
 * ViewComponent class. Controls the drawing of Components
 * @author groep 03
 *
 */
import view.windows.DialogBox;
import view.windows.SubWindow;
public abstract class ViewComponent implements Selectable {
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
	
	/**
	 * Create a dialogbox for the viewcomponent
	 * @param viewInteraction
	 * 			The ViewInteraction to which the ViewComponent belongs
	 * @param x
	 * 			The x positon of the new DialogBox
	 * @param y
	 * 			The y positon of the new DialogBox
	 * @return Null if there is no DialogBox available for the ViewComponent
	 */
	public DialogBox createDialogBox(ViewInteraction viewInteraction, int x, int y) {
		return null;
	}
	
	/**
	 * Update the label of the ViewComponent
	 * @param component
	 * 			The component that belongs to the ViewComponent
	 * @param label
	 * 			The new label
	 * @return  True if the label of the given component had to be updated
	 * 			False if the label of the given component had to be updated
	 */
	public boolean updateLabel(Component component, String label) {
		if (getComponent() == component) {
			ViewLabel viewLabel = getViewLabel();
			viewLabel.setColor(Color.BLACK);
			viewLabel.setOutput(label);
			return true;
		}
		return false;
	}
	
	/* GETTERS AND SETTERS */
	
	public abstract Component getComponent();
	
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
	
	public abstract void setLabelState(SubWindow subwindow);
	
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
}
