package view;

import controller.Controller;

import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * A DiagramWindow class
 * 
 * @author SWOP groep 03
 */
public class DiagramWindow extends CanvasWindow {
    private Controller controller;

    public DiagramWindow(String title) {
        super(title);
        controller = new Controller(this);
    }

    @Override
    protected void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g.drawString(controller.getDiagramType().toString() + " DIAGRAM", 10, 10);
        
        controller.paintScreen(g2);
    }

	@Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        controller.handleMouseEvent(id, x, y, clickCount); //pass it to controller
        repaint();
    }
    
    // Keyboard keys
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        controller.handleKeyEvent(id, keyCode, keyChar);
        repaint();
    }
}