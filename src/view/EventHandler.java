package view;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.ArrayList;

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
	ViewComponent selectedComponent;
	LabelMode labelMode;
	int labelClicked;
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
		labelMode = LabelMode.SHOW;
		labelClicked = 0;
	}

	/**
	 * When a keyevent occurs, it is handled in this method. If tab is pressed, the
	 * view of the diagram is switched, from communication to sequence and vice
	 * versa, if delete is pressed the focused party gets deleted. Labels can also be entered 
	 * with this method. SubWindows can also be created.
	 * 
	 * @param id
	 *            keyEvent id
	 * @param keyCode
	 *            Keyboard key pressed
	 * @param keyChar
	 *            keyboard key pressed keyChar
	 * @throws IllegalArgumentException
	 *            Illegal id or keyCode
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		if (id < 0 || keyCode < 0)
			throw new IllegalArgumentException();

		if (labelMode == LabelMode.PARTY || labelMode == LabelMode.MESSAGE) {
			if (selectedComponent == null)
				throw new IllegalArgumentException("No component found!");

			Component currentComponent = selectedComponent.getComponent();
			String label = currentComponent.getLabel();
			ViewLabel viewLabel = selectedComponent.getViewLabel();
			viewLabel.setLabelMode(labelMode);

			if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z || keyCode == KeyEvent.VK_COLON || keyCode == KeyEvent.VK_SEMICOLON
					|| keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_SPACE) {
				if (keyCode == KeyEvent.VK_BACK_SPACE && label.length() > 1)
					currentComponent.setLabel(label.substring(0, label.length() - 2) + "|");
				else if (label != null && label.length() > 0)
					currentComponent.setLabel(label.substring(0, label.length() - 1) + keyChar + "|");
			}

			if (keyCode == 10) {
				if (labelMode == LabelMode.PARTY && viewLabel.correctSyntax(label) || labelMode == LabelMode.MESSAGE)
					currentComponent.setLabel(label.substring(0, label.length() - 1));
				labelMode = LabelMode.SHOW;
				viewLabel.setLabelMode(LabelMode.SHOW);
				
				if (selectedComponent.isSelected)
					controller.selectComponent(selectedComponent);
				
				selectedComponent = null;
			}
		}

		if (labelMode == LabelMode.SHOW) {
			switch (keyCode) {
			case KeyEvent.VK_TAB:
				controller.switchDiagramType();
				break;
			case KeyEvent.VK_DELETE:
				if (selectedComponent != null)
					controller.deleteComponent(selectedComponent);
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

		SubWindow activeWindow = mainwindow.getActiveWindow();
		if (activeWindow == null)
			return;

		if (labelMode == LabelMode.SHOW) {
			switch (id) {
			case MouseEvent.MOUSE_PRESSED:
				first = clickLifeline(x, y, activeWindow);
				break;
			case MouseEvent.MOUSE_DRAGGED:
				if (selectedComponent != null && !selectedComponent.isSelected)
					controller.moveComponent(selectedComponent, x, y);
				break;
			case MouseEvent.MOUSE_RELEASED:
				second = clickLifeline(x, y, activeWindow);
				if (first != null && second != null && checkCallStack(first, second, activeWindow)) {
					Message message = controller.addMessage(first, second, x, y);
					ViewMessage viewMessage = activeWindow.findViewMessage(message);
					labelMode = LabelMode.MESSAGE;
					viewMessage.getViewLabel().setLabelMode(LabelMode.MESSAGE);
					selectedComponent = viewMessage;
				}
				break;
			case MouseEvent.MOUSE_CLICKED:
				SubWindow subwindow = activeWindow;
				SubWindow closeWindow = checkCloseButtons(x, y, mainwindow.getSubWindows(), activeWindow);
				ViewLabel viewLabel = null;
				selectedComponent = null;

				if (closeWindow != null) {
					controller.closeClickedSubwindow(closeWindow);
					labelClicked = 0;
				} else if (clickOutsideActiveSubwindow(x, y, subwindow)) {
					SubWindow sub = findClickedSubwindow(x, y, subwindow, mainwindow.getSubWindows());
					if (sub != null)
						controller.changeActiveSubwindow(sub);
					labelClicked = 0;
				} else if ((selectedComponent = clickParty(x, y, subwindow)) != null) {
					if (clickCount == 2)
						controller.changePartyType((ViewParty) selectedComponent);
					labelClicked = 0;
				} else if ((viewLabel = clickLabel(x, y, subwindow)) != null) {
					System.out.println("label clicked");
					if (clickCount == 1) {
						controller.selectComponent(selectedComponent);
						labelClicked += 1;
					}
					if (labelClicked == 2) {
						if (selectedComponent instanceof ViewParty)
							labelMode = LabelMode.PARTY;
						else if (selectedComponent instanceof ViewMessage)
							labelMode = LabelMode.MESSAGE;
						
						viewLabel.setLabelMode(labelMode);
						labelClicked = 0;
						Component currentComponent = selectedComponent.getComponent();
						String label = currentComponent.getLabel() + "|";
						currentComponent.setLabel(label);
					}
				} else if (clickCount == 2) {
					// TODO clicked empty area
					Party party = controller.createParty(new Point2D.Double(x, y));
					selectedComponent = subwindow.findViewParty(party);
					labelMode = LabelMode.PARTY;
					labelClicked = 0;
				}

				// TODO Lifeline + (invocation) message clicked
				break;
			}
		}
	}

	/**
	 * Check the Message Call Stack
	 * @param sender
	 * 		Message sender
	 * @param receiver
	 * 		Message receiver
	 * @param subwindow
	 * 		SubWindow that contains the Message
	 * @return true if the call stack is correct
	 * @throws NullPointerException
	 * 		No sender, receiver or subwindow supplied
	 */
	private boolean checkCallStack(Party sender, Party receiver, SubWindow subwindow) {
		if (sender == null || receiver == null || subwindow == null)
			throw new NullPointerException();
		
		ViewParty first = subwindow.findViewParty(sender);
		ViewParty second = subwindow.findViewParty(receiver);

		return first.positionSeq.getX() < second.positionSeq.getX();
	}

	/**
	 * Checks if a Label was clicked
	 * 
	 * @param x
	 *            Clicked x coordinates
	 * @param y
	 *            Clicked y coordinates
	 * @param subwindow
	 *            SubWindow that contains this Label
	 * @return the clicked Label, if there is one, otherwise null
	 * @throws NullPointerException
	 * 		No subwindow supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private ViewLabel clickLabel(int x, int y, SubWindow subwindow) {
		if (subwindow == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		State state = subwindow.getState();
		ArrayList<ViewParty> parties = subwindow.getViewParties();
		ArrayList<ViewMessage> messages = subwindow.getViewMessages();

		for (ViewParty party : parties) {
			if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
				if (party.checkLabelPosition(new Point2D.Double(x, y), party.getPositionSeq(),
						new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
					selectedComponent = party;
					return party.getViewLabel();
				}
			} else {
				if (party.checkLabelPosition(new Point2D.Double(x, y), party.getPositionCom(),
						new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
					selectedComponent = party;
					return party.getViewLabel();
				}
			}
		}

		// TODO click label invocation messages
		for (ViewMessage message : messages) {
		if (message.getClass() == ViewInvocationMessage.class) {
				if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
					if (message.checkLabelPosition(new Point2D.Double(x, y), message.getPositionSeq(),
							new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
						selectedComponent = message;
						return message.getViewLabel();
					}
				} else {
					if (message.checkLabelPosition(new Point2D.Double(x, y), message.getPositionCom(),
							new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
						selectedComponent = message;
						return message.getViewLabel();
					}
				}
			}
		}

		return null;
	}

	/**
	 * Checks if the close button of a subwindow that isn't the active subwindow is
	 * clicked.
	 * 
	 * @param x
	 *            The x coordinate of the clicked position
	 * @param y
	 *            The y coordinate of the clicked position
	 * @param subWindows
	 *            ArrayList of all subwindows
	 * @param active
	 * 			  The active subwindow
	 * @return Null if no close button is clicked The Subwindow of which the close
	 *         button was clicked
	 * @throws NullPointerException
	 * 		No subwindows supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private SubWindow checkCloseButtons(int x, int y, ArrayList<SubWindow> subWindows, SubWindow active) {
		if (subWindows.size() == 0)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		if (clickCloseButton(x,y,active))
			return active;
		
		for (int i = subWindows.size() - 1; i >= 0; i--) {
			SubWindow item = subWindows.get(i);
			if (item != active && clickCloseButton(x, y, item))
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
	 * @throws NullPointerException
	 * 		No subwindow supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private ViewParty clickParty(int x, int y, SubWindow subwindow) {
		if (subwindow == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		ArrayList<ViewParty> parties = subwindow.getViewParties();
		for (ViewParty party : parties) {
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
	 *            The current active subwindow
	 * @return True if the close button of the subwindow is clicked False if the
	 *         close butten of the subwindow isn't clicked
	 * @throws NullPointerException
	 * 		No subwindow supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private boolean clickCloseButton(int x, int y, SubWindow subwindow) {
		if (subwindow == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
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
	 * @return true if the clickevent occured outside of the active subwindow
	 * @throws NullPointerException
	 * 		No subwindow supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private boolean clickOutsideActiveSubwindow(int x, int y, SubWindow subwindow) {
		if (subwindow == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		return x < subwindow.getX() || y < subwindow.getY() || x > subwindow.getX() + subwindow.getWidth()
				|| y > subwindow.getY() + subwindow.getHeight();
	}

	/**
	 * Find the SubWindow that was clicked. Ths method loops over all the subwindows
	 * except the active window, from the front to the back
	 * 
	 * @param x
	 *            The clicked x coordinates
	 * @param y
	 *            The clicked y coordinates
	 * @param subwindow
	 *            The active subwindow
	 * @param subWindows
	 *            The list of all subwindows
	 * @return The clicked subwindow, or null
	 * @throws NullPointerException
	 * 		No subwindow or list of subwindows supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private SubWindow findClickedSubwindow(int x, int y, SubWindow subwindow, ArrayList<SubWindow> subWindows) {
		if (subwindow == null || subWindows.size() == 0)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
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

	/**
	 * Checks if a LifeLine was clicked
	 * 
	 * @param x
	 *            The clicked x coordinates
	 * @param y
	 *            The clicked y coordinates
	 * @return the clicked LifeLine or null
	 * @throws NullPointerException
	 * 		No subwindow or list of subwindows supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private Party clickLifeline(int x, int y, SubWindow subwindow) {
		if (subwindow == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		for (ViewParty party : subwindow.getViewParties()) {
			ViewLifeLine lifeline = party.getViewLifeLine();
			if (x >= lifeline.getX() - 3 && x <= lifeline.getX() + 3 && y >= lifeline.getStartY()
					&& y <= lifeline.getEndY())
				return party.getParty();
		}

		return null;
	}
}