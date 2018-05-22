package view.components;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import domain.message.Message;
import domain.message.ResultMessage;
import view.ViewInteraction;
import view.windows.DialogBox;
import view.windows.ResultBox;
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
	public ViewResultMessage(ViewMessage message) {
		super(message);
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
	@Override
	public void draw(Graphics2D g, int xSender, int xReceiver, int ySender, int yReceiver) {
		if (xSender < 0 || xReceiver < 0 || ySender < 0 || yReceiver < 0)
			throw new IllegalArgumentException();
		
		setColor(g);
		
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
				new float[] { 9 }, 0);
		g.setStroke(dashed);
		if (xSender > xReceiver) {
			xReceiver += 5;
			xSender -= 5;
		} else {
			xReceiver -= 5;
			xSender += 5;
		}
		drawArrow(xSender, ySender, xReceiver, yReceiver, g);
				
		String label = getMessage().getLabel();
		int labelX = xSender + ((xReceiver - xSender)/2) - ((g.getFontMetrics().stringWidth(label))/2); 
		getViewLabel().draw(g, new Point2D.Double(labelX, ySender+12));
		
		resetColor(g);
	}
	
	/**
     * Draws an arrow with an open arrow head
     */
    public static void drawArrow(int xSender, int ySender, int xReceiver, int yReceiver, Graphics2D g) {
        g.drawLine(xSender, ySender, xReceiver, yReceiver);

        int angle = (int) Math.toDegrees(Math.atan2(yReceiver - ySender, xReceiver - xSender));

        int xCorner1 = (int) (Math.cos(Math.toRadians(angle + 40)) * 10);
        int yCorner1 = (int) (Math.sin(Math.toRadians(angle + 40)) * 10);

        int xCorner2 = (int) (Math.cos(Math.toRadians(angle - 40)) * 10);
        int yCorner2 = (int) (Math.sin(Math.toRadians(angle - 40)) * 10);

        g.drawLine(xReceiver, yReceiver, xReceiver - xCorner1, yReceiver - yCorner1);
        g.drawLine(xReceiver, yReceiver, xReceiver - xCorner2, yReceiver - yCorner2);
    }
	
	@Override
	public ViewMessage copy() {
		return new ViewResultMessage(this);
	}

	@Override
	public DialogBox createDialogBox(ViewInteraction viewInteraction, int x, int y) {
		return new ResultBox(viewInteraction, this, x, y);
	}
}
