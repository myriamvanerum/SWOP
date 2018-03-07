package controller;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import model.*;
import model.Object;
import view.DiagramWindow;

/**
 * The controller class is a link between the model and the view. It accepts
 * input and converts it to commands for the model or view.
 * 
 * @author groep 03
 *
 */
public class Controller extends ObjectFocusListener implements Draw {

	private DiagramType diagramType = DiagramType.SEQUENCE;

	public ArrayList<Party> parties = new ArrayList<>();
	public ArrayList<Message> messages = new ArrayList<>();

	public DiagramComponent selectedParty = null;

	private DiagramWindow view;

	public Controller(DiagramWindow view) {
		this.view = view;
	}

	/* GETTERS AND SETTERS */
	
	public DiagramType getDiagramType() {
		return diagramType;
	}

	public void setDiagramType(DiagramType diagramType) {
		this.diagramType = diagramType;
	}
	
	public ArrayList<Party> getParties() {
		return parties;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	/**
	 * This method will loop over all the components and paint them on the window. 
	 * It paints the components all on the same height on a sequence diagram
	 * and at the clicked position on a communication diagram.
	 * @param g
	 */
	public void paintScreen(Graphics2D g) {
		/*Draw actors and objects*/
		if (getDiagramType() == DiagramType.COMMUNICATION) {
			for (Party component : getParties()) {
				setColor(component, g);
				if (component instanceof Actor)
					drawActor(g, component.getXCom(), component.getYCom(), component.getLabel(), 20, 120);
				else if (component instanceof Object)
					drawObject(g, component.getXCom(), component.getYCom(), component.getLabel(), 80, 80);
			}
		} else if (getDiagramType() == DiagramType.SEQUENCE) {
			for (Party component : getParties()) {
				setColor(component, g);
				drawParty(g, component);
			}
		}

		/*Draw messages*/
		for (Message message : getMessages()) {
			if (message instanceof InvocationMessage) {
				drawInvocationMessage(g, message.focused(), message.getSender().getXCom(),
						message.getSender().getYCom(), message.getReceiver().getXCom(),
						message.getReceiver().getYCom());
			} else if (message instanceof ResultMessage) {
				drawResultMessage(g, message.focused(), message.getSender().getXCom(), message.getSender().getYCom(),
						message.getReceiver().getXCom(), message.getReceiver().getYCom());
			}
		}
	}

	/**
	 * When a keyevent occurs, it is handled in this method. If tab is pressed, the
	 * view of the diagram is switched, from communication to sequence and vice
	 * versa, if delete is pressed (or backspace on a mac, which doesn't have a
	 * deletebutton) the focused party gets deleted.
	 * 
	 * @param id
	 * @param keyCode:
	 * @param keyChar:
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {

		switch (keyCode) {
		case KeyEvent.VK_I:
			addMessage(new InvocationMessage(new Label(1,1, ""), parties.get(0), parties.get(1)));
			break;

		case KeyEvent.VK_TAB:
			switchDiagram();
			break;

		case KeyEvent.VK_DELETE:
			deleteFocused();
			break;
		}
	}

	/**
	 * When a mouse event occurs, it is handled in this method. If the mouse is
	 * pressed, the object is focused, if it's dragged, the object should move (if
	 * it's a party), if the mouse has clicked, it gets focused or unfocused at one
	 * click, it draws a new party.
	 * 
	 * @param id
	 * @param x:
	 *            coordinate x
	 * @param y:
	 *            coordinate y
	 * @param clickCount:
	 *            the number of times the mouse has clicked.
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount) {

		switch (id) {

		case MouseEvent.MOUSE_PRESSED:
			checkAndFocus(x, y);
			break;

		case MouseEvent.MOUSE_DRAGGED:
			if (getFocusedObject() != null && getFocusedObject() instanceof Party) {
				moveComponent((Party) getFocusedObject(), x, y);
			}
			getFocusedObject().unfocus();
			break;

		case MouseEvent.MOUSE_CLICKED:
			Party party = checkCoordinate(x, y);
			switch (clickCount) {
			case 1:
				if (party == null && getFocusedObject() != null) {
					unFocus();
				}
				break;

			case 2:
				if (party == null) {
					addComponent(x, y);
				} else {
					if (getDiagramType() == DiagramType.COMMUNICATION) {
						changeParty(party, (int) party.getXCom(), (int) party.getYCom());

					} else if (getDiagramType() == DiagramType.SEQUENCE) {
						changeParty(party, (int) party.getXSeq(), (int) party.getYSeq());
					}
				}
			}
		}
	}

	/**
	 * A clicked location is compared with a component location, to see if the component is located at the clicked spot.
	 * 
	 * @param component:
	 * 			  the component whose location is checked
	 * @param x:
	 *            x coordinate
	 * @param y:
	 *            y coordinate
	 * @param componentX:
	 * 			  component x coordinate
	 * @param componentY:
	 * 			  component y coordinate
	 * @return true if the component covers the clicked position, false if it doesn't
	 */
	private boolean isComponent(Party component, int x, int y, double componentX, double componentY) {
		if (component instanceof Actor) {
			return x >= x - 20 / 2 && x <= x + 20 / 2 && y <= 140;
		} else if (component instanceof Object) {
			return x >= componentX && x <= componentX + 80 && y >= componentY && y <= componentY + 80;
		} else
			return false;
	}

	/* HELP METHODS */

	/**
	 * The coordinates are sent to the controller and checked, when a clickevent
	 * occurs.
	 * 
	 * @param x:
	 *            x coordinate
	 * @param y:
	 *            y coordinate
	 * @return a party.
	 */
	private Party checkCoordinate(int x, int y) {
		for (Party component : getParties()) {
			if (getDiagramType() == DiagramType.COMMUNICATION) {
				if (isComponent(component, x, y, component.getXCom(), component.getYCom()))
					return component;
			} else if (getDiagramType() == DiagramType.SEQUENCE) {
				if (isComponent(component, x, y, component.getXSeq(), component.getYSeq()))
					return component;
			}
		}
		return null;
	}

	/**
	 * Diagram is switched. 
	 * If the previous screen showed a sequence diagram, it will now show a communication diagram, and vice versa.
	 */
	public void switchDiagram() {
		switch (getDiagramType()) {
		case SEQUENCE:
			setDiagramType(DiagramType.COMMUNICATION);
			break;
		case COMMUNICATION:
			setDiagramType(DiagramType.SEQUENCE);
			break;
		default:
			break;
		}
	}

	/* PARTY MESSAGES */

	/**
	 * A party is added on the given coordinates.
	 * 
	 * @param x:
	 *            x coordinate
	 * @param y:
	 *            y coordinate
	 */
	private void addComponent(int x, int y) {
		Party component = new Object(x, y, new Label(x,y,""));
		System.out.println("ADDED COMP X:" + x + " Y:" + y);
		addParty(component);
	}

	/**
	 * The party moves to other coordinates.
	 * 
	 * If it's in sequence diagram view, it only gets moved horizontally.
	 * 
	 * @param component:
	 *            the party to be moved.
	 * @param x:
	 *            x coordinate
	 * @param y:
	 *            y coordinate
	 */
	private void moveComponent(Party component, int x, int y) {
		if (getDiagramType() == DiagramType.COMMUNICATION) {
			component.setXCom(x);
			component.setY(y);
		} else {
			component.setXSeq(x);
		}
	}

	/**
	 * The party gets added to the view (and to an arrayList).
	 * 
	 * @param object:
	 *            the party to be added.
	 */
	public void addParty(Party party) {
		parties.add(party);
	}

	/**
	 * The party gets removed from the view (and the arrayList).
	 * 
	 * @param object:
	 *            the party to be deleted.
	 */
	public void removeParty(Party party) {
		parties.remove(party);
		messages.remove(party.getSendingMessage());
	}

	/**
	 * The party changes from actor to object and vice versa. The party is deleted
	 * from the view and the other party is added at the same coordinates.
	 * 
	 * @param party:
	 *            the first party.
	 * @param x:
	 *            x coordinate of the first party.
	 * @param y:
	 *            y coordinate of the first party.
	 */
	private void changeParty(Party party, int x, int y) {
		if (party instanceof Actor) {
			Object object = new Object(x, y, new Label(x,y,party.getLabelText()));
			removeParty(party);
			addParty(object);
		} else if (party instanceof Object){
			Actor actor = new Actor(x, y, new Label(x,y,party.getLabelText()));
			removeParty(party);
			addParty(actor);
		}
	}

	/**
	 * A message is added.
	 * 
	 * @param message:
	 *            the message to be added.
	 */
	public void addMessage(Message message) {
		messages.add(message);
	}

	/**
	 * A message gets removed.
	 * 
	 * @param message:
	 *            the message to removed.
	 */
	public void removeMessage(Message message) {
		messages.remove(message);
	}

	/**
	 * A component gets removed.
	 * 
	 * @param component:
	 *            the component to removed.
	 */
	public void removeComponent(DiagramComponent component) {
		if (component instanceof Party) {
			removeParty((Party) component);
		} else {
			removeMessage((Message) component);
		}
	}

	/* FOCUS METHODS */

	/**
	 * A component gets focused.
	 * 
	 * @param component:
	 *            the component to focus.
	 */
	private void focusComponent(Focusable component) {
		if (component != null) {
			focusGained(component);
		}
	}

	/**
	 * If a component is focused, it gets unfocused.
	 */
	private void unFocus() {
		focusLost();
	}

	/**
	 * A party gets focused, or unfocused and checked.
	 * 
	 * @param x:
	 *            x coordinate
	 * @param y:
	 *            y coordinate
	 */
	private void checkAndFocus(int x, int y) {
		Focusable selectable = checkCoordinate(x, y);

		if (getFocusedObject() != null) {
			focusLost();
		}
		focusComponent(selectable);
	}

	/**
	 * The component is focused and gets removed.
	 */
	private void deleteFocused() {
		Focusable focusable = getFocusedObject();
		assert focusable != null;
		if (focusable instanceof Party) {
			removeParty((Party) focusable);
		} else
			removeMessage((Message) focusable);
	}
}