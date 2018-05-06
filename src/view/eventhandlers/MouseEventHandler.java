package view.eventhandlers;

import java.awt.event.MouseEvent;
import java.awt.geom.*;

import facade.Interactr;
import model.Component;
import model.Message;
import model.Party;
import view.InteractionManager;
import view.components.ViewComponent;
import view.components.ViewLabel;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.windows.SubWindow;

/**
 * EventHandler class. Translates user input for Controller
 * 
 * @author groep 03
 */
public class MouseEventHandler {
	Interactr controller;
	InteractionManager interactionManager;

	ViewComponent labelClickedOnce;
	Party first, second;

	/**
	 * EventHandler Constructor
	 * 
	 * @param window
	 *            Main Window
	 */
	public MouseEventHandler(InteractionManager interactionManager) {
		controller = new Interactr(interactionManager);
		this.interactionManager = interactionManager;
	}

	/**
	 * When a mouse event occurs, it is handled in this method. If the mouse is
	 * pressed, the object is focused, if it's dragged, the object should move (if
	 * it's a party), if the mouse has clicked, it gets focused or unfocused at one
	 * click, it draws a new party.
	 * 
	 * @param id
	 *            mouseEvent id
	 * @param x
	 *            coordinate x
	 * @param y
	 *            coordinate y
	 * @param clickCount
	 *            the number of times the mouse has clicked.
	 * @throws IllegalArgumentException
	 *             Illegal id, coordinates or clickCount
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		if (id < 0 || x < 0 || y < 0 || clickCount < 0)
			throw new IllegalArgumentException();

		// TODO labelstate !!! aanmaken

		SubWindow active = interactionManager.getActiveWindow();

		if (active == null)
			return;

		if (id == MouseEvent.MOUSE_CLICKED) {
			SubWindow closeWindow = interactionManager.checkCloseButtons(x, y);
			if (closeWindow != null) {
				interactionManager.closeClickedSubwindow(closeWindow);
			} else if (active.clickOutsideActiveSubwindow(x, y)) {
				SubWindow sub = interactionManager.findClickedSubwindow(x, y, active);
				if (sub != null)
					interactionManager.setActiveWindow(sub);
			}
		}

		if (active.clickOutsideActiveSubwindow(x, y))
			return;

		if (active.getShowState() == active.getLabelState()) {
			switch (id) {
			case MouseEvent.MOUSE_PRESSED:
				first = active.clickLifeline(x, y);
				active.setSelectedComponent(active.clickParty(x, y));
				break;
			case MouseEvent.MOUSE_DRAGGED:
				ViewComponent selectedComponent = active.getSelectedComponent();
				if (selectedComponent != null && !selectedComponent.isSelected)
					active.moveComponent(selectedComponent, x, y);
				break;
			case MouseEvent.MOUSE_RELEASED:
				second = active.clickLifeline(x, y);
				if (first != null && second != null && active.checkCallStack(first, second)) {
					// TODO add previous message
					Message message = controller.addMessage(first, second, x, y);
					ViewMessage viewMessage = active.findViewMessage(message);
					active.changeLabelState("MESSAGE");
					active.setSelectedComponent(viewMessage);
				}
				break;
			case MouseEvent.MOUSE_CLICKED:
				ViewLabel viewLabel = null;
				active.setSelectedComponent(active.clickParty(x, y));

				if (active.getSelectedComponent() != null) {
					if (clickCount == 2)
						controller.changePartyType((ViewParty) active.getSelectedComponent());
				} else if ((viewLabel = active.clickLabel(x, y)) != null) {
					System.out.println("label clicked");
					selectedComponent = active.getSelectedComponent();

					if (clickCount == 1 && labelClickedOnce == null) {
						active.selectComponent();
						labelClickedOnce = selectedComponent;
					} else if (selectedComponent == labelClickedOnce) {
						selectedComponent.setLabelState(active);

						Component currentComponent = selectedComponent.getComponent();
						String label = currentComponent.getLabel() + "|";
						viewLabel.setOutput(label);
						labelClickedOnce = null;
					}
				} else if (clickCount == 2) {
					Party party = controller.createParty(new Point2D.Double(x, y));
					active.setSelectedComponent(active.findViewParty(party));
					active.changeLabelState("PARTY");
				}
				break;
			}
		}
	}
}