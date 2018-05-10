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

public class InteractionManager {
	private KeyEventHandler keyEventHandler;
	private MouseEventHandler mouseEventHandler;

	public ViewInteraction activeInteraction = null;	
	public ArrayList<ViewInteraction> interactions = new ArrayList<>();

	public InteractionManager() {
		setKeyEventHandler(new KeyEventHandler(this));
		setMouseEventHandler(new MouseEventHandler(this));
	}

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

	public void createNewInteraction() {
		System.out.println("Create New Interaction.");
		ViewInteraction viewInteraction = new ViewInteraction();
		getInteractions().add(viewInteraction);
		if (getActiveInteraction() != null)
			getActiveInteraction().setActiveWindow(null);
		setActiveInteraction(viewInteraction);
		
		Point2D lowestPos = findLowestWindow();

		viewInteraction.addWindow(lowestPos);
	}

	private Point2D findLowestWindow() {
		ArrayList<Point2D> positions = new ArrayList<>();
		positions.add(new Point2D.Double(10,10));
		for (ViewInteraction viewInteraction : getInteractions()) {
			if (viewInteraction.getSubWindows().size() > 0) {
				DiagramWindow lowestWindow =  Collections.max(viewInteraction.getSubWindows(), Comparator.comparing(s -> s.getY()));
				positions.add(new Point2D.Double(lowestWindow.getX() + 10, lowestWindow.getY() + 10));
			}
		}
		return Collections.max(positions, Comparator.comparing(s -> s.getY()));	
	}

	public void duplicateActiveWindow() {
		System.out.println("Duplicate Active Window.");
		getActiveInteraction().duplicateActiveWindow();
	}

	/**
	 * Close a SubWindow
	 * @param subwindow
	 * 		the SubWindow to be closed
	 */
	public void closeClickedSubwindow(int x, int y) {
		if (getActiveInteraction().closeWindow(x, y)) {
			if (getActiveInteraction().hasNoWindows()) {
				removeInteraction(getActiveInteraction());
				if (getInteractions().size() > 0)
					setActiveInteraction(getInteractions().get(getInteractions().size()-1));
				else setActiveInteraction(null);
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

	private void removeInteraction(ViewInteraction interaction) {
		getInteractions().remove(interaction);
		interaction.removeInteractionObserver();		
	}

	/**
	 * Find the SubWindow that was clicked. Ths method loops over all the subwindows
	 * except the active window, from the front to the back
	 * 
	 * @param x
	 *            The clicked x coordinates
	 * @param y
	 *            The clicked y coordinates
	 * @return The clicked subwindow, or null
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

	public void changeDiagramState() {
		System.out.println("Change Window State.");
		if (getActiveInteraction() != null)
			getActiveInteraction().changeActiveWindowState();
	}

	public void deleteComponent() {
		System.out.println("Forward Delete Component.");
		getActiveInteraction().deleteComponent();
	}

	public void addMessageToActiveWindow(Party sender, Party receiver, int x, int y) {
		if (getActiveInteraction() != null) 
			getActiveInteraction().addMessage(sender, receiver, x, y);		
	}

	public void addPartyToActiveWindow(Point2D position) {
		if (getActiveInteraction() != null)
			getActiveInteraction().addParty(position);
	}

	public void changePartyTypeInActiveWindow() {
		if (getActiveInteraction() != null)
			getActiveInteraction().changePartyType();
	}
}
