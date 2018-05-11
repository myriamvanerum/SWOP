package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import domain.party.Party;
import view.eventhandlers.KeyEventHandler;
import view.eventhandlers.MouseEventHandler;
import view.windows.DiagramWindow;
/**
 * An InteractionManager holds a list of all current Interactions. It handles all commands from the eventHandlers
 * and forwards them to the appriopriate class.
 * 
 * @author groep 03
 */
public class InteractionManager {
	private KeyEventHandler keyEventHandler;
	private MouseEventHandler mouseEventHandler;

	public ViewInteraction activeInteraction = null;	
	public ArrayList<ViewInteraction> interactions = new ArrayList<>();
	
	/* CONSTRUCTOR */

	public InteractionManager() {
		setKeyEventHandler(new KeyEventHandler(this));
		setMouseEventHandler(new MouseEventHandler(this));
	}
	
	/* KEY EVENTS AND MOUSE EVENTS */

	/**
	 * Method to pick up mouse events
	 * @param id
	 * 		The mouseEvent id
	 * @param x
	 * 		The clicked x coordinates
	 * @param y
	 * 		The clicked y coordinates
	 * @param clickCount
	 * 		The number of clicks
	 */
	public void handleMouseEvent(int id, int x, int y, int clickCount) {
		getMouseEventHandler().handleMouseEvent(id, x, y, clickCount);
	}

	/**
	 * Method to pick up keyboard events
	 * @param id
	 * 		The keyEvent id
	 * @param keyCode
	 * 		The keycode for the entered key
	 * @param keyChar
	 * 		The keyChar for the entered key
	 */
	public void handleKeyEvent(int id, int keyCode, char keyChar) {
		getKeyEventHandler().handleKeyEvent(id, keyCode, keyChar);
	}
	
	/* DRAWING */

	/**
	 * Method to draw all SubWindows (and their contents) on the screen. The active Interaction (and all its windows)
	 * is drawn last, so it is on top
	 * @param g Graphics class
	 */
	public void draw(Graphics2D g) {        
		// Draw all but active interaction first
		for (ViewInteraction interaction : getInteractions()) {
			if (interaction != activeInteraction)
				interaction.drawWindows(g);
		}

		// Draw active interaction on top
		if (activeInteraction != null)
			activeInteraction.drawWindows(g);
	}
	
	/* WINDOW OPERATIONS */

	/**
	 * Create a new Interaction. Also triggers the creation of a new DiagramWindow and sets the window as the active window
	 */
	public void createNewInteraction() {
		System.out.println("Create New Interaction.");
		ViewInteraction viewInteraction = new ViewInteraction();
		getInteractions().add(viewInteraction);
		if (getActiveInteraction() != null)
			getActiveInteraction().setActiveWindow(null);
		setActiveInteraction(viewInteraction);
		
		Point2D lowestPos = findLowestWindow();

		viewInteraction.createDiagramWindow(lowestPos);
	}
	
	/**
	 * Sends along the request to duplicate the active DiagramWindow to the active Interaction
	 */
	public void duplicateActiveWindow() {
		if (getActiveInteraction() == null) return;
		System.out.println("Duplicate Active Window.");
		getActiveInteraction().duplicateActiveWindow();
	}
	
	/**
	 * Sends along the request to open a new DialogBox to the active Interaction
	 */
	public void openDialogBox() {
		System.out.println("Open dialog box.");
		if (getActiveInteraction() != null)
			getActiveInteraction().openDialogBox();
	}

	/**
	 * Finds the lowest positioned SubWindows out of all the possible Interactions and their SubWindows
	 * @return the lowest position found, or (5,5) if no SubWindows were found
	 */
	private Point2D findLowestWindow() {
		ArrayList<Point2D> positions = new ArrayList<>();
		positions.add(new Point2D.Double(5, 5));
		for (ViewInteraction viewInteraction : getInteractions()) {
			if (viewInteraction.getSubWindows().size() > 0) {
				DiagramWindow lowestWindow =  (DiagramWindow)Collections.max(viewInteraction.getSubWindows(), Comparator.comparing(s -> s.getY()));
				positions.add(new Point2D.Double(lowestWindow.getX() + 10, lowestWindow.getY() + 10));
			}
		}
		return Collections.max(positions, Comparator.comparing(s -> s.getY()));	
	}

	/**
	 * Close a SubWindow if a CloseButton can be found at the clicked coordinates
	 */
	public void closeClickedSubwindow(int x, int y) {
		if (getActiveInteraction().closeWindow(x, y)) {
			if (getActiveInteraction().hasNoWindows()) {
				removeInteraction(getActiveInteraction());
				if (getInteractions().size() > 0) {
					ViewInteraction topInteraction = getInteractions().get(getInteractions().size()-1);
					setActiveInteraction(topInteraction);
					topInteraction.setActiveWindow(topInteraction.getSubWindows().get(topInteraction.getSubWindows().size()-1));
				} else setActiveInteraction(null);
			}
			return;
		}

		for (int i = getInteractions().size() - 1; i >= 0; i--) {
			ViewInteraction interaction = getInteractions().get(i);
			if (interaction != getActiveInteraction() && interaction.closeWindow(x, y)) {
				if (interaction.hasNoWindows())
					removeInteraction(interaction);
				return;
			}
		}
	}

	/**
	 * Remove an Interaction from the system. Is triggered when an Interaction has no more SubWindows,
	 * meaning it is no longer accessible
	 * @param interaction 
	 * 		The Interacion to remove
	 */
	private void removeInteraction(ViewInteraction interaction) {
		System.out.println("Close Interaction.");
		getInteractions().remove(interaction);
		interaction.removeInteractionObserver();		
	}

	/**
	 * Activate a SubWindow (meaning it is moved to the front) if one can be found
	 * @param x
	 *            The clicked x coordinates
	 * @param y
	 *            The clicked y coordinates
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public void activateSubwindow(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		for (int i = getInteractions().size() - 1; i >= 0; i--) {
			Boolean found = getInteractions().get(i).activateSubwindow(x, y);
			if (found && getActiveInteraction() != getInteractions().get(i)) {
				getActiveInteraction().setActiveWindow(null);
				setActiveInteraction(getInteractions().get(i));
			}	
		}
	}

	/**
	 * Forward the request to change the DiagramState for the active DiagramWindow to the active ViewInteraction
	 */
	public void changeDiagramState() {
		if (getActiveInteraction() == null) return;
		System.out.println("Change Window State.");
		getActiveInteraction().changeActiveWindowState();
	}
	
	/* COMPONENT OPERATIONS */

	/**
	 * Forward the request to delete the selected Component
	 */
	public void deleteComponent() {
		if (getActiveInteraction() == null) return;
		System.out.println("Forward Delete Component.");
		getActiveInteraction().deleteComponent();
	}

	/**
	 * Forward the request to add a new message
	 * @param sender
	 * 		The Message sender
	 * @param receiver
	 * 		The message receiver
	 * @param x
	 * 		The clicked x coordinate
	 * @param y
	 * 		The clicked y coordinate
	 */
	public void addMessage(Party sender, Party receiver, int x, int y) {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().addMessage(sender, receiver, x, y);		
	}

	/**
	 * Forward the request to add a new Party
	 * @param position
	 * 		The position to place the Party at
	 */
	public void addParty(Point2D position) {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().addParty(position);
	}

	/**
	 * Forward the request to change the type of the selected Party
	 */
	public void changePartyType() {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().changePartyType();
	}
	
	/* GETTERS AND SETTERS */

	public ViewInteraction getActiveInteraction() {
		return activeInteraction;
	}

	public void setActiveInteraction(ViewInteraction activeInteraction) {
		this.activeInteraction = activeInteraction;
	}

	public KeyEventHandler getKeyEventHandler() {
		return keyEventHandler;
	}

	public void setKeyEventHandler(KeyEventHandler keyEventHandler) {
		this.keyEventHandler = keyEventHandler;
	}

	public MouseEventHandler getMouseEventHandler() {
		return mouseEventHandler;
	}

	public void setMouseEventHandler(MouseEventHandler mouseEventHandler) {
		this.mouseEventHandler = mouseEventHandler;
	}

	public ArrayList<ViewInteraction> getInteractions() {
		return interactions;
	}

	public void setInteractions(ArrayList<ViewInteraction> interactions) {
		this.interactions = interactions;
	}
}
