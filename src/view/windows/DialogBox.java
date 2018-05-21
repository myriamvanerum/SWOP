package view.windows;

import java.awt.Graphics2D;
import java.util.ArrayList;

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
	
	public DialogBox(ViewInteraction viewInteraction, Integer x, Integer y, Integer width, Integer height, Titlebar titlebar) {
		super(x, y, width, height, titlebar);
		setViewInteraction(viewInteraction);
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
		if (getCurrentControlIndex() >= 0)
			return getCurrentControl();
		return null;
	}

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
	
	@Override
	public ViewLabel getCurrentViewLabel() {
		if (getCurrentControl().getViewLabel() != null) 
			return getCurrentControl().getViewLabel();
		return null;
	}

	/*@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel();
		}
	}*/

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
	
	/**
	 * Set label state according to the current viewlabel
	 */
	public void setLabelState(ViewLabel viewLabel) {
		System.out.println("Set label mode dialog box.");
	}
	
	@Override
	public void singleClick(int x, int y) {
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
}
