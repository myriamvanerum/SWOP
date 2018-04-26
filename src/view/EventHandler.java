package view;

import java.awt.Color;
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

		if (active != null) {
			if (active.getLabelMode() != LabelMode.SHOW) {
				ViewComponent selectedComponent = active.getSelectedComponent();
				if (selectedComponent == null)
					throw new IllegalArgumentException("No component found!");

				Component currentComponent = selectedComponent.getComponent();
				ViewLabel viewLabel = selectedComponent.getViewLabel();
				String label = viewLabel.getOutput();
				viewLabel.setLabelMode(active.getLabelMode());

				if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z || keyCode == KeyEvent.VK_COLON
						|| keyCode == KeyEvent.VK_SEMICOLON || keyCode == KeyEvent.VK_BACK_SPACE
						|| keyCode == KeyEvent.VK_SPACE) {
					if (keyCode == KeyEvent.VK_BACK_SPACE && label.length() > 1)
						viewLabel.setOutput(label.substring(0, label.length() - 2) + "|");
					else if (label != null && label.length() > 0)
						viewLabel.setOutput(label.substring(0, label.length() - 1) + keyChar + "|");
						
					if (active.getLabelMode() == LabelMode.PARTY && !controller.checkLabelSyntax(viewLabel.getOutput()))
						viewLabel.setColor(Color.RED);
					else
						viewLabel.setColor(Color.GREEN);
				}

				if (keyCode == 10) {
					if (active.getLabelMode() == LabelMode.PARTY && controller.checkLabelSyntax(label) || active.getLabelMode() == LabelMode.MESSAGE)
					{	viewLabel.setColor(Color.BLACK);
						viewLabel.setOutput(currentComponent.getLabel());
						
						if (selectedComponent.isSelected)
							active.selectComponent();

						String newLabel = label.substring(0, label.length() - 1);
						controller.editLabel(active.getInteraction(), currentComponent, newLabel);
					}					
				}
			}
		}

		if (active == null || active.getLabelMode() == LabelMode.SHOW) {
			switch (keyCode) {
			case KeyEvent.VK_TAB:
				if (active != null)
					mainwindow.getActiveWindow().changeState();
				break;
			case KeyEvent.VK_DELETE:
				if (active.getSelectedComponent() != null)
					controller.deleteComponent(active.getSelectedComponent());
				break;
			case KeyEvent.VK_N:
				if (keyChar == '' /* keyChar != 'n' && keyChar != 'N' && keyChar != 'ñ' */) {
					controller.createNewInteraction();
				}
				break;
			case KeyEvent.VK_D:
				if (keyChar == '' /* keyChar != 'd' && keyChar != 'D' && keyChar != 'ð' */) {
					if (mainwindow.getActiveWindow() != null)
						mainwindow.createNewSubWindow(null);
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
		
		active = mainwindow.getActiveWindow();

		if (active == null)
			return;

		if (id == MouseEvent.MOUSE_CLICKED) {
			SubWindow closeWindow = checkCloseButtons(x, y, mainwindow.getSubWindows(), active);
			if (closeWindow != null) {
				mainwindow.closeClickedSubwindow(closeWindow);
			} else if (clickOutsideActiveSubwindow(x, y, active)) {
				SubWindow sub = findClickedSubwindow(x, y, active, mainwindow.getSubWindows());
				if (sub != null)
					mainwindow.setActiveWindow(sub);
			}
		}
		
		if (clickOutsideActiveSubwindow(x, y, active))
			return;

		if (active.getLabelMode() == LabelMode.SHOW) {
			switch (id) {
			case MouseEvent.MOUSE_PRESSED:
				first = clickLifeline(x, y, active);
				active.setSelectedComponent(clickParty(x, y, active));
				break;
			case MouseEvent.MOUSE_DRAGGED:
				ViewComponent selectedComponent = active.getSelectedComponent();
				if (selectedComponent != null && !selectedComponent.isSelected)
					mainwindow.moveComponent(selectedComponent, x, y);
				break;
			case MouseEvent.MOUSE_RELEASED:
				second = clickLifeline(x, y, active);
				if (first != null && second != null && checkCallStack(first, second, active)) {
					Message message = controller.addMessage(first, second, x, y);
					ViewMessage viewMessage = active.findViewMessage(message);
					active.setLabelMode(LabelMode.MESSAGE);
					viewMessage.getViewLabel().setLabelMode(LabelMode.MESSAGE);
					active.setSelectedComponent(viewMessage);
				}
				break;
			case MouseEvent.MOUSE_CLICKED:
				ViewLabel viewLabel = null;
				active.setSelectedComponent(clickParty(x, y, active));

				if (active.getSelectedComponent() != null) {
					if (clickCount == 2)
						controller.changePartyType((ViewParty) active.getSelectedComponent());
				} else if ((viewLabel = clickLabel(x, y, active)) != null) {
					System.out.println("label clicked");
					selectedComponent = active.getSelectedComponent();
					
					if (clickCount == 1 && labelClickedOnce == null) {
						active.selectComponent();
						labelClickedOnce = selectedComponent;
					} else if (selectedComponent == labelClickedOnce) {
						if (selectedComponent instanceof ViewParty) {
							active.setLabelMode(LabelMode.PARTY);
							viewLabel.setLabelMode(LabelMode.PARTY);
						} else if (selectedComponent instanceof ViewMessage) {
							active.setLabelMode(LabelMode.MESSAGE);
							viewLabel.setLabelMode(LabelMode.MESSAGE);
						}
						
						Component currentComponent = selectedComponent.getComponent();
						String label = currentComponent.getLabel() + "|";
						viewLabel.setOutput(label);
						labelClickedOnce = null;
					}
				} else if (clickCount == 2) {
					Party party = controller.createParty(new Point2D.Double(x, y));
					active.setSelectedComponent(active.findViewParty(party));
					active.setLabelMode(LabelMode.PARTY);
				}
				break;
			}
		}
	}

	/**
	 * Check the Message Call Stack
	 * 
	 * @param sender
	 *            Message sender
	 * @param receiver
	 *            Message receiver
	 * @param subwindow
	 *            SubWindow that contains the Message
	 * @return true if the call stack is correct
	 * @throws NullPointerException
	 *             No sender, receiver or subwindow supplied
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
	 *             No subwindow supplied
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
					subwindow.setSelectedComponent(party);
					return party.getViewLabel();
				}
			} else {
				if (party.checkLabelPosition(new Point2D.Double(x, y), party.getPositionCom(),
						new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
					subwindow.setSelectedComponent(party);
					return party.getViewLabel();
				}
			}
		}

		for (ViewMessage message : messages) {
			if (message.getClass() == ViewInvocationMessage.class) {
				if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
					if (message.checkLabelPosition(new Point2D.Double(x, y), message.getPositionSeq(),
							new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
						subwindow.setSelectedComponent(message);
						return message.getViewLabel();
					}
				} else {
					if (message.checkLabelPosition(new Point2D.Double(x, y), message.getPositionCom(),
							new Point2D.Double(subwindow.getX(), subwindow.getY()))) {
						subwindow.setSelectedComponent(message);
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
	 *            The active subwindow
	 * @return Null if no close button is clicked The Subwindow of which the close
	 *         button was clicked
	 * @throws NullPointerException
	 *             No subwindows supplied
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	private SubWindow checkCloseButtons(int x, int y, ArrayList<SubWindow> subWindows, SubWindow active) {
		if (subWindows.size() == 0)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		if (clickCloseButton(x, y, active))
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
	 *             No subwindow supplied
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
	 *             No subwindow supplied
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
	 *             No subwindow supplied
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
	 *             No subwindow or list of subwindows supplied
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
	 *             No subwindow or list of subwindows supplied
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