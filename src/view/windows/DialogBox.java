package view.windows;

import java.awt.Graphics2D;
import java.util.ArrayList;

import view.ViewInteraction;
import view.components.ViewLabel;
import view.formelements.WindowControl;

public class DialogBox extends SubWindow {
	public ArrayList<WindowControl> controls;
	public int currentControl;
	public String title;
	private ViewLabel currentViewLabel;
	
	public DialogBox(ViewInteraction viewInteraction, Integer x, Integer y, Integer width, Integer height, Titlebar titlebar) {
		super(x, y, width, height, titlebar);
		this.currentViewLabel = null;
		setViewInteraction(viewInteraction);
	}
		
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
	
	public int getCurrentControl() {
		return currentControl;
	}
	
	public void setCurrentControl(int currentControl) {
		this.currentControl = currentControl;
	}	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public ViewLabel getCurrentViewLabel() {
		return currentViewLabel;
	}

	public void setCurrentViewLabel(ViewLabel currentViewLabel) {
		this.currentViewLabel = currentViewLabel;
	}

	@Override
	public WindowControl getControl() {
		if (getCurrentControl() >= 0)
			return getControls().get(getCurrentControl());
		return null;
	}

	@Override
	public void draw(Graphics2D g) {
		drawWhiteField(g);	
		getTitlebar().draw(g, getTitle());
		drawBlackBorder(g);
		drawControls(g);
	}

	/*@Override
	public void confirmLabel() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().confirmLabel();
		}
	}*/

	@Override
	public void removeLabelCharacter() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().removeCharacter();
		}
	}

	@Override
	public void addLabelCharacter(int keyCode, char keyChar) {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().addCharacter(keyCode, keyChar);
		}
	}
	

	@Override
	public void pressTab() {
		// TODO 	
		//if(actionAllowed())
		//	changeControl()
	}
}
