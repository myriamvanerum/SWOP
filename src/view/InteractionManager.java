package view;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Interaction;
import model.Message;
import model.Party;
import purecollections.PList;
import view.components.ViewMessage;
import view.eventhandlers.KeyEventHandler;
import view.eventhandlers.MouseEventHandler;
import view.windows.SubWindow;

public class InteractionManager {
	private KeyEventHandler keyEventHandler;
	private MouseEventHandler mouseEventHandler;
	
	public ViewInteraction activeInteraction = null;	
	public PList<ViewInteraction> interactions = PList.empty();
	
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
        // Draw all but active window first
        for (ViewInteraction interaction : getInteractions()) {
        	if (interaction != activeInteraction)
	        	interaction.drawWindows(g);
		}
        
        // Draw active window on top
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
	
	public PList<ViewInteraction> getInteractions() {
		return interactions;
	}

	public void setInteractions(PList<ViewInteraction> interactions) {
		this.interactions = interactions;
	}
	
	public void createNewInteraction() {
		System.out.println("Create New Interaction.");
		ViewInteraction viewInteraction = new ViewInteraction();
		getActiveInteraction().setActiveWindow(null);
		setActiveInteraction(viewInteraction);
		
		viewInteraction.addWindow();
	}
	
	public void duplicateActiveWindow() {
		System.out.println("Duplicate Active Window.");
		getActiveInteraction().duplicateActiveWindow();
	}
	
	/**
	 * Close a SubWindow
	 * @param subwindow
	 * 		the SubWindow to be closed
	 * @throws NullPointerException
	 * 		No subwindow supplied
	 */
	public void closeClickedSubwindow(SubWindow subwindow) {	
		if (subwindow == null)
			throw new NullPointerException();
		
		getSubWindows().remove(subwindow);
		subwindow.getInteractionManager().getInteraction().removeObserver(subwindow);

		if (subwindow == getActiveWindow()) {
			int index = getSubWindows().size();
			
			if (index <= 0)
				setActiveWindow(null);
			else
				setActiveWindow(getSubWindows().get(index-1));
		}
		System.out.println("Close SubWindow.");
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
	public void findClickedSubwindow(int x, int y, SubWindow subwindow) {
		if (subwindow == null || getSubWindows().size() == 0)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		for (int i = getSubWindows().size() - 1; i >= 0; i--) {
			if (getSubWindows().get(i) != subwindow) {
				int xSub = getSubWindows().get(i).getX();
				int ySub = getSubWindows().get(i).getY();
				int width = getSubWindows().get(i).getWidth();
				int height = getSubWindows().get(i).getHeight();

				if (x >= xSub && x <= xSub + width && y >= ySub && y <= ySub + height) {	
					setActiveWindow(subwindow); 
					break;
				}
			}
		}
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
	 *             No subwindows 
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	public SubWindow checkCloseButtons(int x, int y) {
		if (getSubWindows().size() == 0)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();

		if (getActiveWindow().clickCloseButton(x, y))
			return getActiveWindow();

		for (int i = getSubWindows().size() - 1; i >= 0; i--) {
			SubWindow window = getSubWindows().get(i);
			if (window != getActiveWindow() && window.clickCloseButton(x, y))
				return getSubWindows().get(i);
		}

		return null;
	}

	public void changeDiagramState() {
		System.out.println("Change Window State.");
		if (getActiveWindow() != null)
			getActiveWindow().changeState();
	}

	public void deleteComponent() {
		System.out.println("Forward Delete Component.");
		getActiveWindow().deleteComponent();
	}

	public void addMessageToActiveWindow(Party first, Party second, int x, int y) {
		if (getActiveWindow() != null) 
			getActiveWindow().addMessage(first, second, x, y);		
	}

	public void addPartyToActiveWindow(Point2D position) {
		if (getActiveWindow() != null)
			getActiveWindow().addParty(position);
	}

	public void changePartyTypeInActiveWindow() {
		if (getActiveWindow() != null)
			getActiveWindow().changePartyType();
	}
}
