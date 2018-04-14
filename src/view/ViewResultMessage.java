package view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import model.Message;

public class ViewResultMessage extends ViewMessage {
	
	public ViewResultMessage(Message message, Point2D position, Point2D windowPosition, ViewParty sender, ViewParty receiver ) {
		super(message, position, windowPosition, sender, receiver);
	}
	
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
	public void draw(Graphics2D g, int xSender, int xReceiver, int y) {
		if (xSender < 0 || xReceiver < 0 || y < 0)
			throw new IllegalArgumentException();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 9 }, 0);
		g.setStroke(dashed);
		g.drawLine(xSender+7, y, xReceiver-2, y);
				
		String label = getMessage().getLabel();
		int labelX = (xReceiver - xSender) - ((g.getFontMetrics().stringWidth(label))/2);
		getViewLabel().draw(g, label, new Point2D.Double(labelX, y+12));
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
