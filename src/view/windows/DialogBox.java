package view.windows;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import domain.Component;
import domain.message.Message;
import domain.party.Party;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.formelements.WindowControl;

public class DialogBox extends SubWindow {
	public ArrayList<WindowControl> controls;
	public int currentControl;
	public String title;
	
	public DialogBox(Integer x, Integer y, Integer width, Integer height, Titlebar titlebar) {
		super(x, y, width, height, titlebar);
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
	public void draw(Graphics2D g) {
		getTitlebar().draw(g, getTitle());
		drawWhiteField(g);	
		drawControls(g);
		drawBlackBorder(g);
	}

	@Override
	public void removeViewParty(Party party) {}

	@Override
	public void changeViewParty(Party party, Party partyNew) {}

	@Override
	public void addViewParty(Party party, Point2D position) {}

	@Override
	public ViewParty findViewParty(Party party) {return null;}

	@Override
	public void changeLabelState(String string) {}

	@Override
	public void removeViewMessage(Message message) {}

	@Override
	public void addViewMessage(Message message, Point2D position) {}

	@Override
	public ViewMessage findViewMessage(Message message) {return null;}

	@Override
	public void editViewLabel(Component component) {}

	@Override
	public void setSelectedComponent(ViewComponent viewComponent) {}

	@Override
	public ViewComponent getSelectedComponent() {return null;}

	@Override
	public ViewComponent clickParty(int x2, int y2) {return null;}

	@Override
	public Party clickLifeline(int x2, int y2) {return null;}

	@Override
	public void moveComponent(ViewComponent selectedComponent, int x2, int y2) {}

	@Override
	public ViewLabel clickLabel(int x, int y) {return null;}

	@Override
	public void selectComponent() {}
}
