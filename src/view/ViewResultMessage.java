package view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class ViewResultMessage {
	/**
	 * This method draws a result message on the window
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
		Stroke dashed = new BasicStroke(getLineWidthResult(focused), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 9 }, 0);
		g.setStroke(dashed);
		g.drawLine((int) xSender, (int) ySender, (int) xReceiver, (int) yReceiver);
	}

	/**
	 * This method determines how wide the line for a result message should be
	 * 
	 * @param focused
	 *            Boolean that signifies is the message is focused or not
	 * @return The number of pixels the line width should be
	 */
	public int getLineWidthResult(boolean focused) {
		if (focused) {
			return 5;
		}
		return 3;
	}
}
