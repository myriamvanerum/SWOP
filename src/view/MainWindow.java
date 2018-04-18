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
	EventHandler eventHandler;
	public MainWindow(String title) {
        super(title);
        setEventHandler(new EventHandler(this));
    }
	
	SubWindow activeWindow = null;
	
	public ArrayList<SubWindow> subWindows = new ArrayList<>();
	
	/**
	 * Method to create a new SubWindow
	 * Is triggered with a new Interaction if Ctrl N
	 * Is triggered with null if Ctrl D 
	 * @param interaction
	 * 		The Interaction to use if Ctrl D, or null if Ctrl N
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
	 * Select a Party or Message
	 * @param viewComponent
	 * 		The Party or Message to select
	 * @throws NullPointerException
	 * 		No ViewComponent supplied
	 */
	public void selectComponent(ViewComponent viewComponent) {
		if (viewComponent == null)
			throw new NullPointerException();
		
		System.out.println("Select component.");
		
		if (viewComponent.selected())
			viewComponent.unselect();
		else
			viewComponent.select();
	}
	
	/**
	 * Move a Party on the screen
	 * @param selectedViewComponent
	 * 		The Party to move
	 * @param x
	 * 		The new x coordinates
	 * @param y
	 * 		The new y coordinates
	 * @throws NullPointerException
	 * 		No ViewComponent supplied
	 * @throws IllegalArgumentException
	 * 		Illegal coordinates
	 */
	public void moveComponent(ViewComponent selectedViewComponent, int x, int y) {
		if (selectedViewComponent == null)
			throw new NullPointerException();
		if (x < 0 || y < 0)
			throw new IllegalArgumentException();
		
		State state = activeWindow.getState();
		if ("SEQ".equalsIgnoreCase(state.getCurrentState())) {
			selectedViewComponent.setPositionSeq(new Point2D.Double(x - activeWindow.getX(), 40));
		} else {
			selectedViewComponent.setPositionCom(new Point2D.Double(x - activeWindow.getX(), y - activeWindow.getY() - 25));
		}
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
