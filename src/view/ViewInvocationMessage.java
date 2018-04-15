package view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import model.Message;
/**
 * ViewInvocationMessage class. Controls the drawing of InvocationMessages
 * @author groep 03
 *
 */
public class ViewInvocationMessage extends ViewMessage {
	/**
	 * ViewInvocationMessage Constructor
	 * @param message
	 * @param position
	 * @param windowPosition
	 * @param sender
	 * @param receiver
	 */
	public ViewInvocationMessage(Message message, Point2D position, Point2D windowPosition, ViewParty sender, ViewParty receiver ) {
		super(message, position, windowPosition, sender, receiver);
	}
	
	/**
	 * This method draws an invocation message on the window
	 * 
	 * @param g
	 *            The graphics class
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
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
	public void draw(Graphics2D g, int xSender, int xReceiver, int ySender, int yReceiver) {
		if (xSender < 0 || xReceiver < 0 || ySender < 0 || yReceiver < 0)
			throw new IllegalArgumentException();
		
		Stroke full = new BasicStroke(1);
		g.setStroke(full);
		g.drawLine(xSender, ySender, xReceiver, yReceiver);
		
		String label = getMessage().getLabel();
		int labelX = xSender + ((xReceiver - xSender)/2) - ((g.getFontMetrics().stringWidth(label))/2); 
		getViewLabel().draw(g, label, new Point2D.Double(labelX, ySender-2));
	}

	/**
	 * This method determines how wide the line for a invocation message should be
	 * 
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
	 * @return The number of pixels the line width should be
	 */
	/*public int getLineWidthInvocation(boolean focused) {
		if (focused) {
			return 8;
		}
		return 5;
	}*/
}
