package view.diagramstate;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.components.ViewComponent;
import view.components.ViewInvocationMessage;
import view.components.ViewMessage;
import view.components.ViewParty;
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
	 * Draw the Parties and Messages in the SubWindow
	 * @param g
	 * 		Graphics class
	 * @param windowPosition
	 * 		Window top left coordinates
	 * @param viewParties
	 * 		The Parties in the SubWindow
	 * @param viewMessages
	 * 		The Messages in the SubWindow
	 * @throws IllegalArgumentException
	 * 		Illegal window position
	 */
	@Override
	public void drawContents(Graphics2D g, Point2D windowPosition, ArrayList<ViewParty> viewParties, ArrayList<ViewMessage> viewMessages) {
		if (windowPosition.getX() < 0 || windowPosition.getY() < 0)
			throw new IllegalArgumentException();
		
		for (ViewParty viewParty : viewParties) {
	        viewParty.draw(g, viewParty.positionWindow(viewParty.getPositionCom(), windowPosition));
	    }
	    for (ViewMessage viewMessage : viewMessages) {
	    	// TODO message labels boven elkaar tonen als meerdere messages tussen zelfde parties
	    	if (viewMessage instanceof ViewInvocationMessage) {
	    		Point2D sender = viewMessage.getSender().getPositionCom();
	    		Point2D receiver = viewMessage.getReceiver().getPositionCom();
	    		
	    		Integer padding = -40;
	    		String mesNumber = viewMessage.getMessage().getMessageNumber();
	    		padding += 30 * Integer.parseInt(mesNumber.substring(0, 1));
	    		if (mesNumber.length() > 1)
	    			padding += 15 * Integer.parseInt(mesNumber.substring(2, 3));
	    		
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
	    }
	}
	
	@Override
	public void moveComponent(ViewComponent component, Point2D clickPosition, Point2D windowPosition) {
		component.setPositionCom(new Point2D.Double(clickPosition.getX() - windowPosition.getX(), clickPosition.getY() - windowPosition.getY() - 25));
	}
	
	@Override
	public boolean checkCoordinates(ViewParty party, Point2D clickPosition, Point2D windowPosition) {
		return party.checkCoordinates(clickPosition, party.getPositionCom(), windowPosition);
	}
}
