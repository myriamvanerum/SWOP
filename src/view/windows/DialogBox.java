package view.windows;

import java.awt.Graphics2D;
import java.util.ArrayList;

import view.ViewInteraction;
import view.components.ViewLabel;
import view.formelements.WindowControl;

public class DialogBox extends SubWindow {
	public ArrayList<WindowControl> controls;
	public int currentControlIndex;
	public String title;
	
	public DialogBox(ViewInteraction viewInteraction, Integer x, Integer y, Integer width, Integer height, Titlebar titlebar) {
		super(x, y, width, height, titlebar);
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

	@Override
	public void removeLabelCharacter() {
		if (!actionAllowed()) {
			getLabelState().setViewLabel(getCurrentViewLabel());
			getLabelState().removeCharacter();
		}
	}

	@Override
	public void addLabelCharacter(int keyCode, char keyChar) {
		if (!actionAllowed() && getCurrentViewLabel() != null) {
				getLabelState().setViewLabel(getCurrentViewLabel());
				getLabelState().addCharacter(keyCode, keyChar);	
		}
	}
	

	@Override
	public void pressTab() {
		System.out.println("Change active control.");
		if(actionAllowed() && getControls().size() > 1) {
			if (getCurrentControlIndex() < getControls().size() - 1) 
				setCurrentControlIndex(getCurrentControlIndex() + 1);
			else 
				setCurrentControlIndex(0);
			
			getCurrentControl().changeLabelState(this);
		}
	}
	
	@Override
	public void pressSpace() {
		System.out.println("Press space.");
	}

	public void setLabelState(ViewLabel viewLabel) {
		System.out.println("Set label mode dialog box.");
	}
}
