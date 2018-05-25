package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import domain.Component;
import domain.Interaction;
import domain.Interactr;
import domain.message.Message;
import domain.party.Party;
import view.components.ViewComponent;
import view.windows.DiagramWindow;
import view.windows.DialogBox;
import view.windows.SubWindow;

/**
 * ViewInteraction class
 * Contains all the subwindows that belong to an interation
 * @author groep 03
 *
 */
public class ViewInteraction implements Observer {
	private Interactr interactr;
	private Interaction interaction;
	
	private SubWindow activeWindow = null;
	private ArrayList<SubWindow> subWindows = new ArrayList<>();
	
	private LastPositions lastPositions;

	/**
	 * ViewInteraction Constructor
	 */
	public ViewInteraction() {
		setInteraction(new Interaction());
		setInteractr(new Interactr(this));
		getInteraction().addObserver(this);
		setLastPositions(new LastPositions(new Point2D.Double(0, 0), new Point2D.Double(0,0)));
	}
	
	/* DRAWING */
	
	/**
	 * Draw all subwindows of the viewinteraction
	 * @param g
	 * 			Graphics class
	 */
	public void drawWindows(Graphics2D g) {
		for (SubWindow window : getSubWindows()) {
        	if (window != activeWindow)
        		window.draw(g);
		}
		
		// Draw active window on top
        if (activeWindow != null)
        	activeWindow.draw(g);
	}
	
	/* WINDOW OPERATIONS */
	
	/**
	 * Create an empty diagram window
	 * @param lowestPosition
	 * 			The position of the lowest diagram
	 */
	public void createDiagramWindow(Point2D lowestPosition) {
		System.out.println("Create New DiagramWindow.");
		DiagramWindow subWindow = new DiagramWindow(this, (int)lowestPosition.getX(), (int)lowestPosition.getY());
		
		addWindow(subWindow);
	}
	
	/**
	 * Duplicate the current active window
	 */
	public void duplicateActiveWindow() {
		Integer x = getActiveWindow().getX() + 10;
		Integer y = getActiveWindow().getY() + 10;
		
		SubWindow window;
		if ((window = getActiveWindow().duplicateWindow(x, y)) == null) return;
		addWindow(window);
	}
	
	/**
	 * Add a new window to the interaction
	 * @param subWindow
	 * 			The subwindow that should be added to the viewinteraction
	 */
	public void addWindow(SubWindow subWindow) {
		getSubWindows().add(subWindow);
		setActiveWindow(subWindow);
	}
	
	/**
	 * Check if there is a subwindow at position (x,y)
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 * @return	True if a subwindow is found at the clicked position
	 * 			False if no subwindow was found at the clicked position
	 */
	public Boolean activateSubwindow(int x, int y) {
		SubWindow subwindow = findSubWindow(x, y);
		
		if (subwindow != null) {
			System.out.println("Activate Window.");
			setActiveWindow(subwindow); 
			return true;
		}

		return false;
	}
	
	/**
	 * Find the subwindow at position (x,y)
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 * @return The subwindow that was found at the clicked position
	 */
	public SubWindow findSubWindow(int x, int y) {
		for (int i = getSubWindows().size() - 1; i >= 0; i--) {
			if (getSubWindows().get(i) != getActiveWindow()) {
				int xSub = getSubWindows().get(i).getX();
				int ySub = getSubWindows().get(i).getY();
				int width = getSubWindows().get(i).getWidth();
				int height = getSubWindows().get(i).getHeight();

				if (x >= xSub && x <= xSub + width && y >= ySub && y <= ySub + height) {	
					return getSubWindows().get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Find the coordinates of the lowest positioned window
	 * @return The coordinates of the lowest positioned window
	 */
	public Point2D findLowestWindow() {
		Point2D position = new Point2D.Double(0,0);
		if (getSubWindows().size() > 0) {
			SubWindow lowestWindow = Collections.max(getSubWindows(), Comparator.comparing(s -> s.getY()));
			position = new Point2D.Double(lowestWindow.getX() + 10, lowestWindow.getY() + 10);
		}
		return position;
	}

	/**
	 * When there is clicked on the closebutton of a subwindow, this window will be closed
	 * @param x
	 * 			The x coordinate of the clicked position
	 * @param y
	 * 			The y coordinate of the clicked position
	 * @return	True if a closebutton was clicked
	 * 			False if a closebutton wasn't clicked
	 */
	public boolean closeWindow(int x, int y) {
		if (getActiveWindow() != null && getActiveWindow().clickCloseButton(x, y)) {
			removeWindow(getActiveWindow());
			
			if (hasNoWindows())
				setActiveWindow(null);
			else
				setActiveWindow(getSubWindows().get(getSubWindows().size()-1));
			
			return true;
		}

		for (int i = getSubWindows().size() - 1; i >= 0; i--) {
			SubWindow window = getSubWindows().get(i);
			if (window.clickCloseButton(x, y)) {
				removeWindow(window);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove the subwindow from the viewinteraction
	 * @param window
	 * 			The subwindow that should be removed from the viewinteraction
	 */
	private void removeWindow(SubWindow window) {
		System.out.println("Close SubWindow.");
		getSubWindows().remove(window);			
	}

	/**
	 * Check if the viewinteraction has subwindows
	 * @return  True if the viewinteraction has no subwindows
	 * 			False if the viewinteraction has subwindows
	 */
	public boolean hasNoWindows() {
		return (getSubWindows().size() == 0);
	}
	
	/**
	 * Remove the observer from this interaction
	 */
	public void removeInteractionObserver() {
		getInteraction().removeObserver(this);
	}

	/**
	 * The tab key was pressed
	 */
	public void pressTab() {
		getActiveWindow().pressTab();
	}

	/**
	 * Open a dialogbox for the selected component
	 * @param lowestPosition
	 * 			The position of the lowest diagram
	 */
	public void openDialogBox(Point2D lowestPosition) {
		if (selectedComponent() == null) return;
		DialogBox dialogBox = selectedComponent().createDialogBox(this, (int)lowestPosition.getX(), (int)lowestPosition.getY());
		getSubWindows().add(dialogBox);
		setActiveWindow(dialogBox);
	}
	
	/* DOMAIN OPERATIONS */
	/**
	 * Delete the selected component
	 */
	public void deleteComponent() {
		if (getActiveWindow().editingLabel()) return;
		ViewComponent viewComponent = selectedComponent();
		if (viewComponent == null || !getActiveWindow().getSelectedComponent().isSelected) return;
		getInteractr().deleteComponent(viewComponent.getComponent());
	}

	/**
	 * Add a message to the current interaction
	 * @param sender
	 * 			The sender party of the message
	 * @param receiver
	 * 			The receiver party of the message
	 * @param x
	 * 			The x coordinate of the added message
	 * @param y
	 * 		    The y coordinate of the added message
	 */
	public void addMessage(Party sender, Party receiver, int x, int y) {
		Message previous = getActiveWindow().getPreviousMessage(y);
		getInteractr().addMessage(sender, receiver, previous);
	}
	
	/**
	 * Edit the label of the given component
	 * @param component
	 * 			The component whose label has been edited
	 * @param label
	 * 			The new label
	 */
	public void editLabel(Component component, String label) {
		getInteractr().editLabel(component, label);
	}
	
	/* COMPONENT OPERATIONS */
	/**
	 * Set the selected component 
	 * @param x
	 * 			The x coordinate of the mouse event
	 * @param y
	 * 			The y coordinate of the mouse event
	 */
	public void setSelectedComponent(int x, int y) {
		getActiveWindow().selectedComponent(x, y);
	}
	
	/**
	 * Get the selected component
	 * @return The selected component
	 * 			Null if there is no selected component
	 */
	private ViewComponent selectedComponent() {
		return getActiveWindow().getSelectedComponent();
	}
	
	/**
	 * Move the component to the new position
	 * @param x
	 * 			The x coordinate of the mouse event
	 * @param y
	 * 			The y coordinate of the mouse event
	 */
	public void moveComponent(int x, int y) {
		setLastClickedPosition(new Point2D.Double(x, y));
		getActiveWindow().moveComponent(x, y);
	}

	/**
	 * Check if a lifeline was clicke
	 * @param x
	 * 			The x coordinate of the mouse event
	 * @param y
	 * 			The y coordinate of the mouse event
	 * @return  The party whose lifeline was clicked
	 * 			Null if no lifeline was clicked
	 */
	public Party checkLifeLine(int x, int y) {
		return getActiveWindow().clickLifeline(x, y);
	}
	
	/* USER EVENTS */
	/**
	 * A double click mouse event
	 * @param x
	 * 			The x coordinate of the mouse event
	 * @param y
	 * 			The x coordinate of the mouse event
	 */
	public void doubleClick(int x, int y) {
		unselectCurrentComponent();
		setLastClickedPosition(new Point2D.Double(x, y));
		Party party;
		if ((party = getActiveWindow().getSelectedParty()) != null)
			getInteractr().changePartyType(party);
		else if (!getActiveWindow().editingLabel() && getActiveWindow().doubleClick())
			getInteractr().addParty();
	}
	
	/**
	 * Unselect the current component
	 */
	private void unselectCurrentComponent() {
		if (getActiveWindow().getSelectedComponent() == null || !getActiveWindow().getSelectedComponent().isSelected) return;
		getActiveWindow().getSelectedComponent().unselect();
	}

	/**
	 * A single click mouse event
	 * @param x
	 * 			The x coordinate of the mouse event
	 * @param y
	 * 			The x coordinate of the mouse event
	 */
	public void singleClick(int x, int y) {
		unselectCurrentComponent();
		setLastClickedPosition(new Point2D.Double(x, y));
		getActiveWindow().singleClick(lastPositions);
	}	
	
	Party sender, receiver;
	/**
	 * Pressed mouse event
	 * @param x
	 * 			The x coordinate of the mouse event
	 * @param y
	 * 			The y coordinate of the mouse event
	 */
	public void pressed(int x, int y) {
		unselectCurrentComponent();
		setLastClickedPosition(new Point2D.Double(x, y));

		sender = checkLifeLine(x, y);
		setSelectedComponent(x, y);
	}

	/**
	 * Released mouse event
	 * @param x
	 * 			The x coordinate of the mouse event
	 * @param y
	 * 			The y coordinate of the mouse event
	 */
	public void released(int x, int y) {
		unselectCurrentComponent();
		setLastClickedPosition(new Point2D.Double(x, y));
		receiver = checkLifeLine(x, y);
		if (sender != null && receiver != null) {
			addMessage(sender, receiver, x, y);
		}
		sender = null;
		receiver = null;
	}
	
	/**
	 * Forward the request to select the window control above the current control
	 */
	public void arrowUp() {
		getActiveWindow().scrollUp();
	}

	/**
	 * Forward the request to select the window control underneath the current control
	 */
	public void arrowDown() {
		getActiveWindow().scrollDown();
	}

	/**
	 * Forward the request to activate a window control
	 */
	public void pressSpace() {
		getActiveWindow().pressSpace();		
	}
	
	/* LABEL METHODS */
	/**
	 * The user has pressed the enter key and wants to confirm the current label
	 */
	public void confirmLabel() {
		getActiveWindow().confirmLabel();
	}
	
	/**
	 *  The user has pressed the back space key and wants to remove a character from the current label
	 */
	public void removeLabelCharacter() {
		getActiveWindow().removeLabelCharacter();
	}
	
	/**
	 *  The user has pressed a key and wants to add a character to the current label
	 */
	public void addLabelCharacter(int keyCode, char keyChar) {
		getActiveWindow().addLabelCharacter(keyCode, keyChar);
	}
	
	/* OBSERVER METHODS */

	/**
	 * Method to be called when a Party type is changed
	 * 
	 * @param party
	 *            The Party whose type was changed
	 */
	@Override
	public void onChangeParty(Party party, Party partyNew) {
		for (SubWindow window : getSubWindows()) {
			window.changeViewParty(party, partyNew);
		}
	}
	
	/**
	 * Method to be called when a Party is deleted
	 * 
	 * @param party
	 *            The Party that was deleted
	 */
	@Override
	public void onDeleteParty(Party party) {
		for (SubWindow window : getSubWindows()) {
			window.removeViewParty(party);
		}
	}

	/**
	 * Method to be called when a Party is added
	 * 
	 * @param party
	 *            The Party that was added
	 */
	@Override
	public void onAddParty(Party party) {
		for (SubWindow window : getSubWindows()) {
			window.addViewParty(party, getLastClickedPosition());
		}
		getActiveWindow().selectParty(party);
	}

	/**
	 * Method to be called when a Message is deleted
	 * 
	 * @param message
	 *            The Message that was deleted
	 */
	@Override
	public void onDeleteMessage(Message message) {
		for (SubWindow window : getSubWindows()) {
			window.removeViewMessage(message);
		}
	}

	/**
	 * Method to be called when a Message is added
	 * 
	 * @param message
	 *            The Message that was added
	 */
	@Override
	public void onAddMessage(Message message) {
		
		for (SubWindow window : getSubWindows()) {
			window.addViewMessage(message, getLastClickedPosition());
		}
		getActiveWindow().selectMessage(message);
	}
	
	/**
	 * Method to be called when a Component's label has been edited
	 * @param component
	 *		The component that has had it's label edited
	 */
	@Override
	public void onEditLabel(Component component) {
		for (SubWindow window : getSubWindows()) {
			window.editViewLabel(component);
		}
	}
	
	/* GETTERS AND SETTERS */

	public Interactr getInteractr() {
		return interactr;
	}

	public void setInteractr(Interactr interactr) {
		this.interactr = interactr;
	}

	public Interaction getInteraction() {
		return interaction;
	}

	public void setInteraction(Interaction interaction) {
		this.interaction = interaction;
	}
	
	public SubWindow getActiveWindow() {
		return activeWindow;
	}

	public void setActiveWindow(SubWindow activeWindow) {
		this.activeWindow = activeWindow;
	}
	
	public ArrayList<SubWindow> getSubWindows() {
		return subWindows;
	}

	public void setSubWindows(ArrayList<SubWindow> subWindows) {
		this.subWindows = subWindows;
	}

	public LastPositions getLastPositions() {
		return lastPositions;
	}

	public void setLastPositions(LastPositions lastPositions) {
		this.lastPositions = lastPositions;
	}
	
	private void setLastClickedPosition(Point2D position) {
		getLastPositions().setLastClickedPosition(position);
	}
	
	private Point2D getLastClickedPosition() {
		return getLastPositions().getLastClickedPosition();
	}
}
