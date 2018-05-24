package view.canvaswindow;

import java.awt.Graphics;
import java.awt.Graphics2D;

import view.UI;
import view.inputhandler.InputHandler;
/**
 * MainWindow class, inherits CanvasWindow class
 * @author groep 03
 *
 */
public class MainWindow extends CanvasWindow {
	
	public static void main(String[] args){
        java.awt.EventQueue.invokeLater(() -> {new MainWindow("Interactr").show();});
    }
	
	private UI ui;
	private InputHandler inputHandler;

	/**
	 * MainWindow Constructor
	 * @param title
	 * 		 	Title for the application
	 */
	public MainWindow(String title) {
        super(title);
        setUI(new UI());
        setInputHandler(new InputHandler(getUI()));
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
        getUI().draw(g2);
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
		getInputHandler().handleMouseEvent(id, x, y, clickCount);
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
    	getInputHandler().handleKeyEvent(id, keyCode, keyChar);
        repaint();
    }
    
    /* GETTERS AND SETTERS */

	public UI getUI() {
		return ui;
	}

	public void setUI(UI ui) {
		this.ui = ui;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}
}
