package view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import model.Message;

public class ViewInvocationMessage extends ViewMessage {
	
	public ViewInvocationMessage(Message message, Point2D position, Point2D windowPosition, ViewParty sender, ViewParty receiver ) {
		super(message, position, windowPosition, sender, receiver);
	}
	
	/**
	 * This method draws an invocation message on the window
	 * 
	 * @param g
	 *            The graphics library used
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
	public void draw(Graphics2D g, int xSender, int xReceiver, int y) {
		if (y < 0 || xSender < 0 || xReceiver < 0)
			throw new IllegalArgumentException();
		Stroke full = new BasicStroke(1);
		g.setStroke(full);
		g.drawLine(xSender, y, xReceiver, y);
		// TODO draw label
		Message m = getMessage();
		String label = m.getLabel();
		int labelX = (xReceiver - xSender) - (g.getFontMetrics().stringWidth(label))/2;
		getViewLabel().draw(g, label, new Point2D.Double(labelX, y-2));
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
