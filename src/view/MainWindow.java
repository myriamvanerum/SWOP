package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Interaction;

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
     * Override for the standard paint method.
     * Paints everything the user sees onto the screen.
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
		eventHandler.handleMouseEvent(id, x, y, clickCount, this);
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
        eventHandler.handleKeyEvent(id, keyCode, keyChar, this);
        repaint();
    }

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
