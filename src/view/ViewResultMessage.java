package view;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import model.Message;
/**
 * ViewResultMessage class. Controls the drawing of ResultMessages
 * @author groep 03
 *
 */
public class ViewResultMessage extends ViewMessage {
	/**
	 * ViewResultMessage Constructor
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
	public ViewResultMessage(Message message, Point2D position, Point2D windowPosition, ViewParty sender, ViewParty receiver ) {
		super(message, position, windowPosition, sender, receiver);
	}
	
	/**
	 * This method draws a result message on the window
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
	public void draw(Graphics2D g, int xSender, int xReceiver, int ySender, int yReceiver) {
		if (xSender < 0 || xReceiver < 0 || ySender < 0 || yReceiver < 0)
			throw new IllegalArgumentException();
		
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 9 }, 0);
		g.setStroke(dashed);
		g.drawLine(xSender+7, ySender, xReceiver-2, yReceiver);
				
		String label = getMessage().getLabel();
		int labelX = xSender + ((xReceiver - xSender)/2) - ((g.getFontMetrics().stringWidth(label))/2); 
		getViewLabel().draw(g, label, new Point2D.Double(labelX, ySender+12));
	}
}
