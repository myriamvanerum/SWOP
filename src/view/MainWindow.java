package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import controller.Controller;
import model.Interaction;
import model.Party;

public class MainWindow extends CanvasWindow {
	EventHandler eventHandler;
	public MainWindow(String title) {
        super(title);
        eventHandler = new EventHandler(this);
    }
	
	SubWindow activeWindow = null;
	// betere manier dan arraylist?
	public ArrayList<SubWindow> subWindows = new ArrayList<>();
	
	
	
	public void createNewSubWindow(Interaction interaction) {
		SubWindow subWindow = new SubWindow();
		subWindows.add(subWindow);
		activeWindow = subWindow;
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
        		window.draw(g2, 5, 5);
		}
        
        // Draw active window on top
        if (activeWindow != null)
        	activeWindow.draw(g2, 5, 5);
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
//		if(controller.isInputMode() == false) {
//			controller.handleMouseEvent(id, x, y, clickCount); //pass it to controller
//			repaint();
//		}
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
}
