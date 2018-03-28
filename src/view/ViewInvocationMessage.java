package view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class ViewInvocationMessage {
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
	public void draw(Graphics2D g, boolean focused, double xSender, double ySender, double xReceiver,
			double yReceiver) {
		if (xSender < 0 || ySender < 0 || xReceiver < 0 || yReceiver < 0)
			throw new IllegalArgumentException();
		Stroke full = new BasicStroke(getLineWidthInvocation(focused));
		g.setStroke(full);
		g.drawLine((int) xSender, (int) ySender, (int) xReceiver, (int) yReceiver);
	}

	/**
	 * This method determines how wide the line for a invocation message should be
	 * 
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
	 * @return The number of pixels the line width should be
	 */
	public int getLineWidthInvocation(boolean focused) {
		if (focused) {
			return 8;
		}
		return 5;
	}
}
