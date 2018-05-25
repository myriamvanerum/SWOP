package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import view.inputhandler.InputHandler;

/**
 * An InteractionManager holds a list of all current Interactions. It handles
 * all commands from the eventHandlers and forwards them to the appriopriate
 * class.
 * 
 * @author groep 03
 */
public class UI {
	private InputHandler inputHandler;
	public ViewInteraction activeInteraction = null;
	public ArrayList<ViewInteraction> interactions = new ArrayList<>();

	/**
	 * UI Constructor
	 */
	public UI() {
		setInputHandler(new InputHandler(this));
	}

	/* DRAWING */

	/**
	 * Method to draw all SubWindows (and their contents) on the screen. The active
	 * Interaction (and all its windows) is drawn last, so it is on top
	 * 
	 * @param g
	 *            Graphics class
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
	 * Create a new Interaction. Also triggers the creation of a new DiagramWindow
	 * and sets the window as the active window
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
	 * Sends along the request to duplicate the active DiagramWindow to the active
	 * Interaction
	 */
	public void duplicateActiveWindow() {
		if (getActiveInteraction() == null)
			return;
		System.out.println("Duplicate Active Window.");
		getActiveInteraction().duplicateActiveWindow();
	}

	/**
	 * Sends along the request to open a new DialogBox to the active Interaction
	 */
	public void openDialogBox() {
		System.out.println("Open dialog box.");
		if (getActiveInteraction() == null) return;
		getActiveInteraction().openDialogBox(findLowestWindow());
	}

	/**
	 * Finds the lowest positioned SubWindows out of all the possible Interactions
	 * and their SubWindows
	 * 
	 * @return the lowest position found, or (5,5) if no SubWindows were found
	 */
	private Point2D findLowestWindow() {
		ArrayList<Point2D> positions = new ArrayList<>();
		positions.add(new Point2D.Double(5, 5));
		for (ViewInteraction viewInteraction : getInteractions()) {
			positions.add(viewInteraction.findLowestWindow());
		}
		return Collections.max(positions, Comparator.comparing(s -> s.getY()));
	}

	/**
	 * Close a SubWindow if a CloseButton can be found at the clicked coordinates
	 * 
	 * @param x
	 * 		The x coordinate of the clicked position
	 * @param y
	 * 		The y coordinate of the clicked position
	 */
	public void closeClickedSubwindow(int x, int y) {
		try {
			if (getActiveInteraction().closeWindow(x, y)) {
				if (getActiveInteraction().hasNoWindows()) {
					removeInteraction(getActiveInteraction());
					if (getInteractions().size() > 0) {
						ViewInteraction topInteraction = getInteractions().get(getInteractions().size() - 1);
						setActiveInteraction(topInteraction);
						topInteraction.setActiveWindow(
								topInteraction.getSubWindows().get(topInteraction.getSubWindows().size() - 1));
					} else
						setActiveInteraction(null);
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
		} catch (Exception e) {
			System.out.println("No active interaction");
		}
	}

	/**
	 * Remove an Interaction from the system. Is triggered when an Interaction has
	 * no more SubWindows, meaning it is no longer accessible
	 * 
	 * @param interaction
	 *            The Interacion to remove
	 */
	private void removeInteraction(ViewInteraction interaction) {
		System.out.println("Close Interaction.");
		getInteractions().remove(interaction);
		interaction.removeInteractionObserver();
	}

	/**
	 * Activate a SubWindow (meaning it is moved to the front) if one can be found
	 * 
	 * @param x
	 *            The clicked x coordinates
	 * @param y
	 *            The clicked y coordinates
	 * @return A viewInteraction if found
	 * 		   Null if no viewInteraction is found
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public ViewInteraction activateSubwindow(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		if (getActiveInteraction() == null) return null;
		if (!getActiveInteraction().getActiveWindow().clickOutsideActiveSubwindow(x, y)) return null;
		for (int i = getInteractions().size() - 1; i >= 0; i--) {
			Boolean found = getInteractions().get(i).activateSubwindow(x, y);
			if (found && getActiveInteraction() != getInteractions().get(i)) {
				getActiveInteraction().setActiveWindow(null);
				setActiveInteraction(getInteractions().get(i));
				return getInteractions().get(i);
			}
		}
		return null;
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

	/* LABEL METHODS */

	/**
	 * The user has pressed the enter key and wants to confirm the current label
	 */
	public void confirmLabel() {
		if (getActiveInteraction() == null)
			return;
		getActiveInteraction().confirmLabel();
	}

	/**
	 *  The user has pressed the back space key and wants to remove a character from the current label
	 */
	public void removeLabelCharacter() {
		if (getActiveInteraction() == null)
			return;
		getActiveInteraction().removeLabelCharacter();
	}

	/**
	 *  The user has pressed a key and wants to add a character to the current label
     * @param keyCode
     * 		The keycode for the entered key
     * @param keyChar
     * 		The keyChar for the entered key
	 */
	public void addLabelCharacter(int keyCode, char keyChar) {
		if (getActiveInteraction() == null)
			return;
		getActiveInteraction().addLabelCharacter(keyCode, keyChar);
	}

	/* GETTERS AND SETTERS */

	public ViewInteraction getActiveInteraction() {
		return activeInteraction;
	}

	public void setActiveInteraction(ViewInteraction activeInteraction) {
		this.activeInteraction = activeInteraction;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	public ArrayList<ViewInteraction> getInteractions() {
		return interactions;
	}

	public void setInteractions(ArrayList<ViewInteraction> interactions) {
		this.interactions = interactions;
	}
	
	/* USER OPERATIONS */
	/**
	 * Forward the mouse clicked event
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 */
	public void clickedOnce(int x, int y) {
		closeClickedSubwindow(x, y);
		activateSubwindow(x, y);
		
		if (getActiveInteraction() != null)
			getActiveInteraction().singleClick(x, y);
	}
	
	/**
	 * Forward the mouse double clicked event
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 */
	public void clickedTwice(int x, int y) {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().doubleClick(x, y);
	}
	
	/**
	 * Forward the mouse dragged event
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 */
	public void dragged(int x, int y) {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().moveComponent(x, y);
	}

	/**
	 * Forward the mouse pressed event
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 */
	public void pressed(int x, int y) {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().pressed(x, y);
	}

	/**
	 * Forward the mouse released event
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 */
	public void released(int x, int y) {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().released(x, y);
	}

	/**
	 * Forward the request to select the window control above the current control
	 */
	public void arrowUp() {
		if (getActiveInteraction() == null) return;
		System.out.println("Press arrow up.");
		getActiveInteraction().arrowUp(); 
	}

	/**
	 * Forward the request to select the window control underneath the current control
	 */
	public void arrowDown() {
		if (getActiveInteraction() == null) return;
		System.out.println("Press arrow down.");
		getActiveInteraction().arrowDown(); 
	}

	/**
	 * Forward the request to activate a window control
	 */
	public void pressSpace() {
		if (getActiveInteraction() == null) return;
		getActiveInteraction().pressSpace();		
	}
	
	/**
	 * Forward the request to change the DiagramState for the active DiagramWindow or to change a DialogBox Control
	 * to the active ViewInteraction
	 */
	public void pressTab() {
		if (getActiveInteraction() == null)
			return;
		System.out.println("Press tab.");
		getActiveInteraction().pressTab();
	}
}
