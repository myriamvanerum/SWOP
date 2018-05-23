package view.components;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import domain.message.InvocationMessage;
import domain.message.Message;
import view.ViewInteraction;
import view.diagramstate.State;
import view.windows.DialogBox;
import view.windows.InvocationBox;
/**
 * ViewInvocationMessage class. Controls the drawing of InvocationMessages
 * @author groep 03
 *
 */
public class ViewInvocationMessage extends ViewMessage {
	/**
	 * ViewInvocationMessage Constructor
	 * @param message
	 * 		Message to draw
	 * @param position
	 * 		Mesage position
	 * @param windowPosition
	 * 		SubWindow position
	 * @param sender
	 * 		Message sender
	 * @param receiver
	 * 		Message receiver
	 */
	public ViewInvocationMessage(Message message, Point2D position, Point2D windowPosition, ViewParty sender, ViewParty receiver ) {
		super(message, position, windowPosition, sender, receiver);
	}
	
	/**
	 * Copy Constructor
	 * @param message
	 * 		Message to draw
	 * @param position
	 * 		Mesage position
	 * @param sender
	 * 		Message sender
	 * @param receiver
	 * 		Message receiver
	 */
	public ViewInvocationMessage(ViewMessage message) {
		super(message);
	}
	
	/**
	 * This method draws an invocation message on the window
	 * 
	 * @param g
	 *            The graphics class
	 * @param xSender
	 *            The x coordinate of the message sender
	 * @param ySender
	 *            The y coordinate of the message sender
	 * @param xReceiver
	 *            The x coordinate of the message receiver
	 * @param yReceiver
	 *            The y coordinate of the message receiver
	 * @throws IllegalArgumentException
	 * 			  Illegal party coordinates
	 */
	@Override
	public void draw(Graphics2D g, int xSender, int xReceiver, int ySender, int yReceiver) {
		if (xSender < 0 || xReceiver < 0 || ySender < 0 || yReceiver < 0)
			throw new IllegalArgumentException();
		
		setColor(g);
		
		Stroke full = new BasicStroke(1);
		g.setStroke(full);
		drawArrow(xSender, ySender, xReceiver, yReceiver, g);
		
		String label = getMessage().getLabel();
		int labelX = xSender + ((xReceiver - xSender)/2) - ((g.getFontMetrics().stringWidth(label))/2); 
		getViewLabel().draw(g, getMessage().getMessageNumber(), new Point2D.Double(labelX, ySender-(ySender-yReceiver)/2));
		
		resetColor(g);
	}
	
	/**
     * Draws an arrow with a filled arrow head
     */
    public static void drawArrow(int xSender, int ySender, int xReceiver, int yReceiver, Graphics2D g) {
        g.drawLine(xSender, ySender, xReceiver, yReceiver);

        int angle = (int) Math.toDegrees(Math.atan2(yReceiver - ySender, xReceiver - xSender));

        int xCorner1 = (int) (Math.cos(Math.toRadians(angle + 40)) * 10);
        int yCorner1 = (int) (Math.sin(Math.toRadians(angle + 40)) * 10);

        int xCorner2 = (int) (Math.cos(Math.toRadians(angle - 40)) * 10);
        int yCorner2 = (int) (Math.sin(Math.toRadians(angle - 40)) * 10);
        
        int[] xCoord = {xReceiver, xReceiver - xCorner1, xReceiver - xCorner2};
		int[] yCoord = {yReceiver, yReceiver - yCorner1, yReceiver - yCorner2};
		g.fillPolygon(xCoord, yCoord, 3);
    }
    
    @Override
    public void draw(Graphics2D g, State state, Point2D windowPosition) {
		state.draw(g, windowPosition, this);
	}
		
	@Override
	public ViewMessage copy() {
		return new ViewInvocationMessage(this);
	}
	
	@Override
	public DialogBox createDialogBox(ViewInteraction viewInteraction, int x, int y) {
		return new InvocationBox(viewInteraction, this, x, y);
	}
	
	@Override
	public void drawActivationBar(Graphics2D g, int xSender, int xReceiver, int yStart, int yEnd) {
		getActivationBar().draw(g, xSender, yStart, yEnd - yStart);
		getActivationBar().draw(g, xReceiver, yStart, yEnd - yStart);	
	}
}
