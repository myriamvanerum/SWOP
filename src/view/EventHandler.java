package view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;

import controller.Controller;

public class EventHandler {
	Controller controller;
	ViewParty selectedViewParty;

	public EventHandler(MainWindow window) {
		controller = new Controller(window);
	}

	/**
	 * When a keyevent occurs, it is handled in this method. If tab is pressed, the
	 * view of the diagram is switched, from communication to sequence and vice
	 * versa, if delete is pressed (or backspace on a mac, which doesn't have a
	 * deletebutton) the focused party gets deleted.
	 * 
	 * @param id
	 *            keyEvent id
	 * @param keyCode:
	 *            Keyboard key pressed
	 * @param keyChar:
	 *            keyboard key pressed keyChar
	 * @throws IllegalArgumentException
	 *             Illegal id or keyCode
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar, MainWindow mainwindow) {
		if (id < 0 || keyCode < 0)
			throw new IllegalArgumentException();

		// if (inputMode == true)
		// {
		// int index = parties.indexOf(currentComponent);
		// String inputLabel = currentComponent.getLabel().getText();
		//
		// // Enkel letters (hoofdletters & kleineletters)
		// // 513 is de keyCode voor ":"
		// // 65-90 zijn de keyCodes voor alle letters
		// // 8 is de keyCode voor backspace
		// // 10 is de keyCode voor enter
		// if (keyCode >= 65 && keyCode <= 90 || keyCode == KeyEvent.VK_COLON || keyCode
		// == 8) {
		//
		// if (keyCode == 8 && inputLabel.length() > 1)
		// currentComponent.getLabel().setText(inputLabel.substring(0,
		// inputLabel.length() - 2) + "|");
		// else if (inputLabel != null && inputLabel.length() > 0) {
		// currentComponent.getLabel().setText(inputLabel.substring(0,
		// inputLabel.length() - 1) + keyChar + "|");
		// }
		// }
		//
		// if (currentComponent.getLabel().correctSyntax() && keyCode == 10) {
		// currentComponent.getLabel().setText(inputLabel.substring(0,
		// inputLabel.length() - 1));
		// setInputMode(false);
		// currentComponent = null;
		// }
		//
		// if (currentComponent instanceof Party)
		// parties.set(index, (Party)currentComponent);
		// if (currentComponent instanceof Message)
		// messages.set(index, (Message)currentComponent);
		//
		// } else {

		switch (keyCode) {

		case KeyEvent.VK_TAB:
			controller.switchDiagramType();
			break;

		case KeyEvent.VK_DELETE:
				if (selectedViewParty != null)
					controller.deleteParty(selectedViewParty, mainwindow.activeWindow);
			break;
		case KeyEvent.VK_N:
			if (keyChar == '' /* keyChar != 'n' && keyChar != 'N' && keyChar != 'ñ' */) {
				controller.createNewInteraction();
			}
			break;
		case KeyEvent.VK_D:
			if (keyChar == '' /* keyChar != 'd' && keyChar != 'D' && keyChar != 'ð' */) {
				controller.duplicateActiveWindow();
			}
			break;
		}
		// }
	}

	/**
	 * When a mouse event occurs, it is handled in this method. If the mouse is
	 * pressed, the object is focused, if it's dragged, the object should move (if
	 * it's a party), if the mouse has clicked, it gets focused or unfocused at one
	 * click, it draws a new party.
	 * 
	 * @param id
	 *            mouseEvent id
	 * @param x:
	 *            coordinate x
	 * @param y:
	 *            coordinate y
	 * @param clickCount:
	 *            the number of times the mouse has clicked.
	 * @throws IllegalArgumentException
	 *             Illegal id, coordinates or clickCount
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount, MainWindow mainwindow) {
		if (id < 0 || x < 0 || y < 0 || clickCount < 0 || mainwindow.getActiveWindow() == null)
			throw new IllegalArgumentException();

		// if ( inputMode == false) {
		switch (id) {
		case MouseEvent.MOUSE_PRESSED:
			// checkAndFocus(x, y);
			break;
		case MouseEvent.MOUSE_DRAGGED:
			// if (getFocusedObject() != null && getFocusedObject() instanceof Party) {
			// moveComponent((Party) getFocusedObject(), x, y);
			// }
			// getFocusedObject().unfocus();
			if (selectedViewParty != null)
				controller.moveComponent(selectedViewParty, x, y);
			break;
		case MouseEvent.MOUSE_CLICKED:
			SubWindow subwindow = mainwindow.getActiveWindow();
			SubWindow closeWindow = checkCloseButtons(x, y, mainwindow.getSubWindows());
			ViewLabel viewLabel = null;

			if (clickCloseButton(x, y, subwindow)) {
				controller.closeClickedSubwindow(subwindow);
			} else if (closeWindow != null) {
				controller.closeClickedSubwindow(closeWindow);
			} else if (clickOutsideActiveSubwindow(x, y, subwindow)) {
				SubWindow sub = findClickedSubwindow(x, y, subwindow, mainwindow.getSubWindows());
				if (sub != null)
					controller.changeActiveSubwindow(sub);
			} else if ((selectedViewParty = clickParty(x, y, subwindow)) != null) {
				if (clickCount == 2)
					controller.changePartyType(selectedViewParty);
			} else if ((viewLabel = clickLabel(x, y, subwindow)) != null) {
				System.out.println("label clicked");
				if (clickCount == 1)
					controller.selectParty(selectedViewParty);
				// TODO edit label
			} else if (clickCount == 2) {
				// TODO clicked empty area
				controller.createParty(new Point2D.Double(x, y));
			}

			// TODO Lifeline + (invocation) message clicked
			break;
		}
	}

	private ViewLabel clickLabel(int x, int y, SubWindow subwindow) {
		ArrayList<ViewParty> parties = subwindow.getViewParties();
		for (ViewParty party : parties) {
			State state = subwindow.getState();
			if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
				if (party.checkLabelPosition(new Point2D.Double(x, y), party.getPositionSeq(), new Point2D.Double(subwindow.getX(), subwindow.getY()))) {		
					selectedViewParty = party;
					return party.getViewLabel();
				}
			} else {
				if (party.checkLabelPosition(new Point2D.Double(x, y), party.getPositionCom(),new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
					selectedViewParty = party;
					return party.getViewLabel();
				}
			}
		}

		// TODO click label invocation messages

		return null;
	}

	/**
	 * Checks if the close button of a subwindow that isn't the active subwindow is
	 * clicked
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @param subWindows
	 *            ArrayList of all subwindows
	 * @return Null if no close button is clicked The Subwindow of which the close
	 *         button was clicked
	 */
	private SubWindow checkCloseButtons(int x, int y, ArrayList<SubWindow> subWindows) {
		for (int i = subWindows.size() - 1; i >= 0; i--) {
			if (clickCloseButton(x, y, subWindows.get(i)))
				return subWindows.get(i);
		}

		return null;
	}

	/**
	 * Checks if there is a party at the clicked position
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @param subwindow
	 *            The current active subwindow
	 * @return Null if there is no party on the position given by the coordinates x
	 *         and y The ViewParty that is on the position given by the coordinates
	 *         x and y
	 */
	private ViewParty clickParty(int x, int y, SubWindow subwindow) {
		ArrayList<ViewParty> parties = subwindow.getViewParties();
		for (ViewParty party : parties) {
			// TODO ik ben geen fan van deze code (instanceof), mss eens kijken of dit beter
			// kan?
			State state = subwindow.getState();
			if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
				if (party.checkCoordinates(new Point2D.Double(x, y), party.getPositionSeq(),
						new Point2D.Double(subwindow.getX(), subwindow.getY())))
					return party;
			} else {
				if (party.checkCoordinates(new Point2D.Double(x, y), party.getPositionCom(),
						new Point2D.Double(subwindow.getX(), subwindow.getY())))
					return party;
			}

		}
		return null;
	}

	/**
	 * Checks if the close button of a subwindow is clicked
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @param subwindow
	 *            The subwindow that has to be checked
	 * @return True if the close button of the subwindow is clicked False if the
	 *         close butten of the subwindow isn't clicked
	 */
	private boolean clickCloseButton(int x, int y, SubWindow subwindow) {
		return subwindow != null && x >= subwindow.getX() + (subwindow.getWidth() - subwindow.getHeightTitlebar())
				&& x <= subwindow.getX() + subwindow.getWidth() && y >= subwindow.getY()
				&& y <= (subwindow.getY() + subwindow.getHeightTitlebar());
	}

	/**
	 * Checks if clicked position is part of the active subwindow
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @param subwindow
	 *            The current active subwindow
	 * @return
	 */
	private boolean clickOutsideActiveSubwindow(int x, int y, SubWindow subwindow) {
		return x < subwindow.getX() || y < subwindow.getY() || x > subwindow.getX() + subwindow.getWidth()
				|| y > subwindow.getY() + subwindow.getHeight();
	}

	private SubWindow findClickedSubwindow(int x, int y, SubWindow subwindow, ArrayList<SubWindow> subWindows) {
		for (int i = subWindows.size() - 1; i >= 0; i--) {
			if (subWindows.get(i) != subwindow) {
				int xSub = subWindows.get(i).getX();
				int ySub = subWindows.get(i).getY();
				int width = subWindows.get(i).getWidth();
				int height = subWindows.get(i).getHeight();

				if (x >= xSub && x <= xSub + width && y >= ySub && y <= ySub + height)
					return subWindows.get(i);
			}
		}

		return null;
	}
	
	private void clickLifeline(int x, int y) {
		// TODO
	}
}
