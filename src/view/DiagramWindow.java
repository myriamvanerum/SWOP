package view;

import controller.Controller;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A DiagramWindow class
 * 
 * @author  groep 03
 */
public class DiagramWindow extends CanvasWindow {
    private Controller controller;

    public DiagramWindow(String title) {
        super(title);
        controller = new Controller(this);
    }

    /**
     * Override for the standard paint method.
     * Paints everything the user sees onto the screen.
     */
    @Override
    protected void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g.drawString(controller.getDiagramType().toString() + " DIAGRAM", 10, 10);
        
        controller.paintScreen(g2);
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
		if(controller.isInputMode() == false) {
			controller.handleMouseEvent(id, x, y, clickCount); //pass it to controller
			repaint();
		}
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
        controller.handleKeyEvent(id, keyCode, keyChar);
        repaint();
    }
}