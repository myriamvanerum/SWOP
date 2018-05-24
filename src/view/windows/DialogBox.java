package view.windows;

import java.awt.Graphics2D;
import java.util.ArrayList;

import view.LastPositions;
import view.ViewInteraction;
import view.components.ViewLabel;
import view.controls.WindowControl;
import view.listboxcommand.Operator;

/**
 * DialogBox class.
 * Represents a dialogbox, subclass of the SubWindow class
 * @author groep 03
 *
 */
public class DialogBox extends SubWindow {
	public ArrayList<WindowControl> controls;
	public int currentControlIndex;
	public String title;
	public Operator operator;
	
	/**
	 * DialogBox Constructor
	 * @param viewInteraction
	 * 			The ViewInteraction to which the dialogbox belongs
	 * @param x
	 * 			The x postion of the dialogbox
	 * @param y
	 * 			The y postion of the dialogbox
	 * @param width
	 * 			The width of the dialogbox
	 * @param height
	 * 			The height of the dialogbox
	 * @param titlebar
	 * 			The title of the dialogbox
	 */
	public DialogBox(ViewInteraction viewInteraction, Integer x, Integer y, Integer width, Integer height, Titlebar titlebar) {
		super(x, y, width, height, titlebar);
		setViewInteraction(viewInteraction);
	}
	
	/* DRAWING */
	
	/**
	 *  Draw the dialogbox
	 */
	@Override
	public void draw(Graphics2D g) {
		drawWhiteField(g);	
		getTitlebar().draw(g, getTitle());
		drawBlackBorder(g);
		drawControls(g);
	}
		
	/**
	 * Draw all the controls of the dialogbox
	 * @param g2
	 *          Graphics class
	 */
	public void drawControls(Graphics2D g2) {		
		for (WindowControl control : getControls()) {
			control.draw(g2);
		}
	}
	
	/* LABEL METHODS */
	
	/**
	 * Add a character to the label that is being edited
	 * 
     * @param keyCode
     * 		The keycode for the entered key
     * @param keyChar
     * 		The keyChar for the entered key
	 */
	@Override
	public void addLabelCharacter(int keyCode, char keyChar) {
		if (editingLabel() && getCurrentViewLabel() != null) {
				getLabelState().setViewLabel(getCurrentViewLabel());
				getLabelState().addCharacter(keyCode, keyChar);	
		}
	}

	/**
	 * Remove a character of the label that is being edited
	 */
	@Override
	public void removeLabelCharacter() {
		if (!editingLabel()) return;
		getLabelState().setViewLabel(getCurrentViewLabel());
		getLabelState().removeCharacter();
	}

	/**
	 * Set label state according to the current viewlabel
	 */
	// TODO WAAR WORDT DIT GEBRUIKT???
	public void setLabelState(ViewLabel viewLabel) {
		System.out.println("Set label mode dialog box.");
	}
	
	/* USER INPUT */
	
	/**
	 *  The tab key is pressed
	 */
	@Override
	public void pressTab() {
		System.out.println("Change active control.");
		
		changeCurrentControl(false);
		
		if (getCurrentControlIndex() < getControls().size() - 1) 
			setCurrentControlIndex(getCurrentControlIndex() + 1);
		else 
			setCurrentControlIndex(0);

		changeCurrentControl(true);
	}
	
	@Override
	public void singleClick(LastPositions lastPositions) {
		int x = (int)lastPositions.getLastClickedPosition().getX();
		int y = (int)lastPositions.getLastClickedPosition().getY();
		
		for (WindowControl control : getControls()) {
			if (control.click(x, y) != null) {
				changeCurrentControl(false);
				setCurrentControlIndex(getControls().indexOf(control));
				changeCurrentControl(true);
				control.click();
			}
		}
	}

	private void changeCurrentControl(boolean isNew) {
		getCurrentControl().setActive(isNew);
		getCurrentControl().currentControl(this);
	}
	
	/* GETTERS & SETTERS */
	
	public ArrayList<WindowControl> getControls() {
		return controls;
	}
	
	public void setControls(ArrayList<WindowControl> controls) {
		this.controls = controls;
	}
	
	public WindowControl getCurrentControl() {
		return getControls().get(getCurrentControlIndex());
	}
	
	public int getCurrentControlIndex() {
		return currentControlIndex;
	}
	
	public void setCurrentControlIndex(int index) {
		this.currentControlIndex = index;
	}	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public WindowControl getControl() {
		if (getCurrentControlIndex() < 0) return null;
		return getCurrentControl();
	}
	
	@Override
	public ViewLabel getCurrentViewLabel() {
		if (getCurrentControl().getViewLabel() == null) return null;
		return getCurrentControl().getViewLabel();
	}
}
