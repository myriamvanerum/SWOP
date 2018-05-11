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
	        viewParty.setColor(g);
	        viewParty.draw(g, viewParty.positionWindow(viewParty.getPositionCom(), windowPosition));
	        viewParty.resetColor(g);
	    }
	    for (ViewMessage viewMessage : viewMessages) {
	    	viewMessage.setColor(g);
	    	if (viewMessage.getClass() == ViewInvocationMessage.class) {
	    		Point2D sender = viewMessage.getSender().getPositionCom();
	    		Point2D receiver = viewMessage.getReceiver().getPositionCom();
	    		viewMessage.draw(g, (int) (sender.getX() + windowPosition.getX() + 80), 
	    				(int) (receiver.getX()+ windowPosition.getX()), 
	    				(int) (sender.getY() + windowPosition.getY() + 25),
	    				(int) (receiver.getY() + windowPosition.getY() + 25));
	    	}
	    	viewMessage.resetColor(g);
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
	
	@Override
	public boolean checkLabelPosition(ViewComponent component, Point2D clickPosition, Point2D windowPosition) {
		return component.checkLabelPosition(clickPosition, component.getPositionCom(), windowPosition);
	}

	/**
	 * Get the State of this SubWindow
	 */
	@Override
	public String getCurrentState() {
		return "COM";
	}

}
