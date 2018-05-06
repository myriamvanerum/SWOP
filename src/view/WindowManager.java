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
import view.components.ViewMessage;
import view.windows.SubWindow;

public class WindowManager {
	public SubWindow activeWindow = null;	
	public ArrayList<SubWindow> subWindows = new ArrayList<>();
	
	public void draw(Graphics2D g) {        
        // Draw all but active window first
        for (SubWindow window : subWindows) {
        	if (window != activeWindow)
        		window.draw(g);
		}
        
        // Draw active window on top
        if (activeWindow != null)
        	activeWindow.draw(g);
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
	
	/**
	 * Method to create a new SubWindow
	 * Is triggered with a new Interaction if Ctrl N
	 * Is triggered with null if Ctrl D 
	 * @param interaction
	 * 		The Interaction to use if Ctrl N, or null if Ctrl D
	 */
	public void createNewSubWindow(Interaction interaction) {
		SubWindow subWindow;
		Integer x = 5;
		Integer y = 5;
		
		// find lowest SubWindow to get lowest coordinates
		if (getSubWindows().size() > 0) {
			SubWindow lowestSubWindow =  Collections.max(getSubWindows(), Comparator.comparing(s -> s.getY()));
			x = lowestSubWindow.getX() + 10;
			y = lowestSubWindow.getY() + 10;
		}
		
		if (interaction != null)
			// create new subwindow for new interaction
			subWindow = new SubWindow(interaction, x, y);
		else
			// copy active subwindow
			subWindow = new SubWindow(getActiveWindow(), x, y);
		
		subWindows.add(subWindow);
		setActiveWindow(subWindow);
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

	public void duplicateActiveWindow() {
		System.out.println("Duplicate Active Window.");
		if (getActiveWindow() != null)
			createNewSubWindow(null);
	}

	public void deleteComponent() {
		System.out.println("Forward Delete Component.");
		getActiveWindow().deleteComponent();
	}

	public void createNewInteraction() {
		// tell model to make new interaction
		// TODO code verplaatsen, hoort hier niet
		Interaction interaction = new Interaction();
		
		// make new subwindow
		createNewSubWindow(interaction);
		System.out.println("Create New Interaction.");
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
