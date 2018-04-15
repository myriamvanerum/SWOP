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
	 * Checks if the invocation message's Label is positioned at the clicked coordinates
	 * 
	 * @param coordinates
	 *            The coordinates of a click event
	 * @param positionState
	 * 			  The Message's position
	 * @param windowPosition
	 * 			  The SubWindow's position
	 */
	@Override
	public boolean checkLabelPosition(Point2D coordinates, Point2D positionState, Point2D windowPosition) {
		ViewLifeLine senderLifeline = getSender().getViewLifeLine();
		ViewLifeLine receiverLifeline = getReceiver().getViewLifeLine();
		int xSender = senderLifeline.getX();
		int xReceiver = receiverLifeline.getX();
		
		double positionX = xSender + ((xReceiver - xSender)/2) - (viewLabel.getWidth()/2); 
		double positionYSeq = (positionState.getY())-2;
		double positionYCom = getSender().getPositionCom().getY() + windowPosition.getY() + 25;
		
		return coordinates.getX() >= positionX &&
			   coordinates.getX() <= positionX + viewLabel.getWidth() &&
			   coordinates.getY() +5 >= positionYSeq - viewLabel.getHeight() || coordinates.getY() +5 >= positionYCom - viewLabel.getHeight() &&
			   coordinates.getY() +5 >= positionYSeq || coordinates.getY() +5 >= positionYCom;
	}
}
