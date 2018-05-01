package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Interaction;
/**
 * MainWindow class, inherits CanvasWindow class
 * @author groep 03
 *
 */
public class MainWindow extends CanvasWindow {
	
	public static void main(String[] args){

        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow("Interactr").show();
        });

    }
	
	public SubWindow activeWindow = null;	
	public ArrayList<SubWindow> subWindows = new ArrayList<>();
	public EventHandler eventHandler;

	public MainWindow(String title) {
        super(title);
        setEventHandler(new EventHandler(this));
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
		subwindow.getInteraction().removeObserver(subwindow);

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
     * Override for the standard paint method.
     * Paints everything the user sees onto the screen.
     * @param g
     * 		Graphics class
     */
    @Override
    protected void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw all but active window first
        for (SubWindow window : subWindows) {
        	if (window != activeWindow)
        		window.draw(g2);
		}
        
        // Draw active window on top
        if (activeWindow != null)
        	activeWindow.draw(g2);
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
	@Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
		eventHandler.handleMouseEvent(id, x, y, clickCount);
		repaint();
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
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        eventHandler.handleKeyEvent(id, keyCode, keyChar);
        repaint();
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
	protected SubWindow findClickedSubwindow(int x, int y, SubWindow subwindow) {
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

				if (x >= xSub && x <= xSub + width && y >= ySub && y <= ySub + height)
					return getSubWindows().get(i);
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
	 *             No subwindows 
	 * @throws IllegalArgumentException
	 *             Illegal coordinates
	 */
	protected SubWindow checkCloseButtons(int x, int y) {
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
    
    /* GETTERS AND SETTERS */

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
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
}
