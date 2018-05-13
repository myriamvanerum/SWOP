package view.components;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import domain.message.InvocationMessage;
import domain.message.Message;
import view.ViewInteraction;
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
		
		Stroke full = new BasicStroke(1);
		g.setStroke(full);
		g.drawLine(xSender, ySender, xReceiver, yReceiver);
		// draw arrow
		int[] xCoord = {xReceiver - 10, xReceiver - 10, xReceiver - 5};
		int[] yCoord = {yReceiver - 5, yReceiver + 5, yReceiver};
		g.fillPolygon(xCoord, yCoord, 3);
		
		String label = getMessage().getLabel();
		int labelX = xSender + ((xReceiver - xSender)/2) - ((g.getFontMetrics().stringWidth(label))/2); 
		getViewLabel().draw(g, getMessage().getMessageNumber(), new Point2D.Double(labelX, ySender-(ySender-yReceiver)/2));
	}
		
	@Override
	public ViewMessage copy() {
		return new ViewInvocationMessage(this);
	}
	
	@Override
	public DialogBox createDialogBox(ViewInteraction viewInteraction, int x, int y) {
		return new InvocationBox(viewInteraction, (InvocationMessage)getMessage(), x, y);
	}
	
	@Override
	public void lengthenActivationBar(double y) {
		if (getPositionSeq().getY() < y - 30 || getPositionSeq().getY() > y) return;
		System.out.println("Lengthening Activation Bar.");
		Integer heightIncrease = 30;
		getActivationBar().increaseHeight(heightIncrease);
	}
	
	@Override
	public void drawActivationBar(Graphics2D g, int xSender, int xReceiver, int y) {
		getActivationBar().draw(g, xSender, y);
		getActivationBar().draw(g, xReceiver, y);	
	}
}
