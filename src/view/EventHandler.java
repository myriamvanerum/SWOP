package view;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.*;

import controller.Controller;
import model.Component;
import model.Message;
import model.Party;

/**
 * EventHandler class. Translates user input for Controller
 * 
 * @author groep 03
 */
public class EventHandler {
	Controller controller;
	MainWindow mainwindow;
	SubWindow active;

	ViewComponent labelClickedOnce;
	Party first, second;

	/**
	 * EventHandler Constructor
	 * 
	 * @param window
	 *            Main Window
	 */
	public EventHandler(MainWindow window) {
		controller = new Controller(window);
		this.mainwindow = window;
		active = null;
	}

	/**
	 * When a keyevent occurs, it is handled in this method. If tab is pressed, the
	 * view of the diagram is switched, from communication to sequence and vice
	 * versa, if delete is pressed the focused party gets deleted. Labels can also
	 * be entered with this method. SubWindows can also be created.
	 * 
	 * @param id
	 *            keyEvent id
	 * @param keyCode
	 *            Keyboard key pressed
	 * @param keyChar
	 *            keyboard key pressed keyChar
	 * @throws IllegalArgumentException
	 *             Illegal id or keyCode
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (id < 0 || keyCode < 0)
			throw new IllegalArgumentException();

		active = mainwindow.getActiveWindow();
		LabelState labelState = null;

		if (active != null)
			labelState = active.getLabelState();

		switch (keyCode) {
		case KeyEvent.VK_TAB:
			if (active != null)
				active.changeState();
			break;
		case KeyEvent.VK_DELETE:
			if (active.getSelectedComponent() != null)
				controller.deleteComponent(active.getSelectedComponent());
			break;
		case KeyEvent.VK_N:
			if (keyChar == '' /* keyChar != 'n' && keyChar != 'N' && keyChar != '�' */) {
				controller.createNewInteraction();
			}
			break;
		case KeyEvent.VK_D:
			if (keyChar == '' /* keyChar != 'd' && keyChar != 'D' && keyChar != '�' */) {
				if (mainwindow.getActiveWindow() != null)
					mainwindow.createNewSubWindow(null);
			}
			break;
		case KeyEvent.VK_ENTER:
			if (labelState != null)
				labelState.confirmLabel();
			break;
		case KeyEvent.VK_BACK_SPACE:
			if (labelState != null)
				labelState.removeCharacter();
			break;
		}

		if (labelState != null && keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z
				|| keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || keyCode == KeyEvent.VK_COLON
				|| keyCode == KeyEvent.VK_SEMICOLON || keyCode == KeyEvent.VK_UNDERSCORE
				|| keyCode == KeyEvent.VK_LEFT_PARENTHESIS || keyCode == KeyEvent.VK_RIGHT_PARENTHESIS
				|| keyCode == KeyEvent.VK_SPACE) {

			// TODO voor partylabel enkel bepaalde karakters toelaten
			// TODO voor messagelabel (bijna) alle karakters toelaten
			labelState.addCharacter(keyCode, keyChar);
		}
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

		active = mainwindow.getActiveWindow();

		if (active == null)
			return;

		if (id == MouseEvent.MOUSE_CLICKED) {
			SubWindow closeWindow = mainwindow.checkCloseButtons(x, y);
			if (closeWindow != null) {
				mainwindow.closeClickedSubwindow(closeWindow);
			} else if (active.clickOutsideActiveSubwindow(x, y)) {
				SubWindow sub = mainwindow.findClickedSubwindow(x, y, active);
				if (sub != null)
					mainwindow.setActiveWindow(sub);
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