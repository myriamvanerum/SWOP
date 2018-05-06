package view.canvaswindow;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.Interaction;
import view.InteractionManager;
import view.SubWindow;
import view.eventhandlers.KeyEventHandler;
import view.eventhandlers.MouseEventHandler;
/**
 * MainWindow class, inherits CanvasWindow class
 * @author groep 03
 *
 */
public class MainWindow extends CanvasWindow {
	
	public static void main(String[] args){
        java.awt.EventQueue.invokeLater(() -> {new MainWindow("Interactr").show();});
    }
	
	private InteractionManager interactionManager;
	private KeyEventHandler keyEventHandler;
	private MouseEventHandler mouseEventHandler;

	public MainWindow(String title) {
        super(title);
        setInteractionManager(new InteractionManager());
        setKeyEventHandler(new KeyEventHandler(interactionManager));
        setMouseEventHandler(new MouseEventHandler(interactionManager));
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
        interactionManager.draw(g2);
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
		mouseEventHandler.handleMouseEvent(id, x, y, clickCount);
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
        keyEventHandler.handleKeyEvent(id, keyCode, keyChar);
        repaint();
    }
    
    /* GETTERS AND SETTERS */

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

	public InteractionManager getInteractionManager() {
		return interactionManager;
	}

	public void setInteractionManager(InteractionManager interactionManager) {
		this.interactionManager = interactionManager;
	}
}
