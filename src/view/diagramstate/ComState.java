package view.diagramstate;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewMessage;
import view.components.ViewParty;
import view.components.ViewResultMessage;
/**
 * Communication Diagram State class
 * @author groep 03
 */
public class ComState implements State {
	
	/**
	 * Get the SubWindow title
	 */
	@Override
	public String getTitle() {
		return "COMMUNICATION DIAGRAM";		
	}
	
	/**
	 * Draw ViewParty
	 * @param g
	 * 			Graphics class
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @param viewParty
	 * 			ViewParty that has to be drawn
	 */
	public void draw(Graphics2D g, Point2D windowPosition, ViewParty viewParty) {
        viewParty.draw(g, viewParty.positionWindow(viewParty.getPositionCom(), windowPosition));
	}
	
	/** 
	 * Draw ViewInvocationMessage
	 * @param g
	 * 			Graphics class
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @param viewMessage
	 * 			viewMessage that has to be drawn
	 */
	public void draw(Graphics2D g, Point2D windowPosition, ViewInvocationMessage viewMessage) {
		Point2D sender = viewMessage.getSender().getPositionCom();
		Point2D receiver = viewMessage.getReceiver().getPositionCom();
		
		Integer padding = setPadding(viewMessage);
		
		Integer senderExtra, receiverExtra;
		if (sender.getX() <= receiver.getX()) {
			senderExtra = 80;
			receiverExtra = 0;
		} else {
			senderExtra = 0;
			receiverExtra = 80;
		}
		viewMessage.draw(g, (int) (sender.getX() + windowPosition.getX() + senderExtra), 
				(int) (receiver.getX() + windowPosition.getX() + receiverExtra), 
				(int) (sender.getY() + windowPosition.getY() + 25  + padding),
				(int) (receiver.getY() + windowPosition.getY() + 25 + padding));
	}
	
	/**
	 * Method to add padding so messages are not drawn across each other
	 * @param viewMessage
	 * 		The viewMessage to add padding for
	 * @return the amount of padding necessary
	 */
	public Integer setPadding(ViewMessage viewMessage) {
		Integer padding = -40;
		String mesNumber = viewMessage.getMessage().getMessageNumber();
		padding += 30 * Integer.parseInt(mesNumber.substring(0, 1));
		if (mesNumber.length() > 1)
			padding += 15 * Integer.parseInt(mesNumber.substring(2, 3));
		return padding;
	}
	
	/** 
	 * Draw ViewResultMessage
	 * @param g
	 * 			Graphics class
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @param viewMessage
	 * 			viewMessage that has to be drawn
	 */
	public void draw(Graphics2D g, Point2D windowPosition, ViewResultMessage viewMessage) {
		// do nothing
	}
		
	/**
	 * Move a ViewComponent
	 * @param component
	 * 			Component that is going to be moved
	 * @param clickPosition
	 * 			The clicked position
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 */
	@Override
	public void moveComponent(ViewComponent component, Point2D clickPosition, Point2D windowPosition) {
		component.setPositionCom(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), clickPosition.getY() - windowPosition.getY() - 25));
	}
	
	/**
	 * Check if there is a party position at the clicked position
	 * @param party
	 * 			ViewParty that is going to be checked
	 * @param clickPosition
	 * 			The clicked position
	 * @param windowPosition
	 * 			Position of the diagramwindow
	 * @return  True if the party is positioned at the clicked position
	 * 			False if the party is not positioned at the clicked position
	 */
	@Override
	public boolean checkCoordinates(ViewParty party, Point2D clickPosition, Point2D windowPosition) {
		return party.checkCoordinates(clickPosition, party.getPositionCom(), windowPosition);
	}
}
